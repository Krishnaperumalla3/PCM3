/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://pragmaedge.com/licenseagreement
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pe.pcm.seas;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.pe.pcm.config.seas.SsoSeasPropertiesConfig;
import com.pe.pcm.constants.AuthoritiesConstants;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.exception.GlobalAuthenticationException;
import com.pe.pcm.login.CommunityManagerLoginModel;
import com.pe.pcm.login.CommunityManagerUserModel;
import com.pe.pcm.properties.CMProperties;
import com.pe.pcm.seas.request.SeasAuthRequestModel;
import com.pe.pcm.seas.request.SeasSsoAuthRequestModel;
import com.pe.pcm.seas.request.SeasValidateTokenRequestModel;
import com.pe.pcm.seas.response.SeasPropertyModel;
import com.pe.pcm.seas.user.ManageLdapUserService;
import com.pe.pcm.seas.user.UserDetailsImpl;
import com.pe.pcm.user.UserService;
import com.pe.pcm.user.entity.UserEntity;
import com.sterlingcommerce.hadrian.api.remoteproxy.HadrianServer;
import com.sterlingcommerce.hadrian.common.net.ClientConnectionException;
import com.sterlingcommerce.hadrian.common.net.ConnectParams;
import com.sterlingcommerce.hadrian.common.net.ConnectionException;
import com.sterlingcommerce.hadrian.common.net.SslInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BinaryOperator;

import static com.pe.pcm.constants.ProfilesConstants.SSO_SSP_SEAS;
import static com.pe.pcm.exception.GlobalExceptionHandler.customError;
import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;
import static com.pe.pcm.utils.PCMConstants.IGNORE;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.util.StringUtils.hasLength;
import static org.springframework.util.StringUtils.hasText;

/**
 * @author Kiran Reddy.
 */
@Service
@Profile(SSO_SSP_SEAS)
public class ManageSEASClientService implements UserDetailsService {
    private static final String HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    private static final String CUSTOM_P_ERROR = " custom property not available in SEAS response, please contact system admin.";
    private static final String UN_AUTH_ERROR = "Unauthorized to access the resource. Please contact system admin.";

    private final UserService userService;
    private final SsoSeasPropertiesConfig ssoSeasPropertiesConfig;
    private final ManageLdapUserService manageLdapUserService;

    private final String dbType;
    private final Boolean isCm;
    private final String color;
    private final Boolean sfgEnabled;
    private final String appCustomName;
    private final CMProperties cmProperties;

    private final ObjectMapper xmlMapper = new XmlMapper();

    private static final Logger LOGGER = LoggerFactory.getLogger(ManageSEASClientService.class);

    @Autowired
    public ManageSEASClientService(UserService userService,
                                   SsoSeasPropertiesConfig ssoSeasPropertiesConfig,
                                   ManageLdapUserService manageLdapUserService,
                                   @Value("${dbType}") String dbType,
                                   @Value("${cm.color}") String color,
                                   @Value("${server.serverHeader}") String appCustomName,
                                   @Value("${cm.cm-deployment}") Boolean isCm,
                                   @Value("${sterling-b2bi.b2bi-api.sfg-api.active}") Boolean sfgEnabled, CMProperties cmProperties) {
        this.userService = userService;
        this.ssoSeasPropertiesConfig = ssoSeasPropertiesConfig;
        this.manageLdapUserService = manageLdapUserService;
        this.dbType = dbType;
        this.color = color;
        this.appCustomName = appCustomName;
        this.isCm = isCm;
        this.sfgEnabled = sfgEnabled;
        this.cmProperties = cmProperties;
    }

    public CommunityManagerUserModel validateAndManage(CommunityManagerLoginModel communityManagerLoginModel) {
        LOGGER.debug("in validateAndManage()");
        HadrianServer hadrianServer = loadHadrianServer();
        boolean userUpdated = false;
        try {
            String xmlStringRequest = HEADER + xmlMapper.writeValueAsString(
                    new SeasRequestResponseModel().setSeasSsoAuthRequestModel(
                            new SeasSsoAuthRequestModel().setSeasAuthRequestModel(
                                    new SeasAuthRequestModel().setProfile(ssoSeasPropertiesConfig.getSeas().getAuthProfile())
                                            .setUserId(communityManagerLoginModel.getUserName())
                                            .setPassword(communityManagerLoginModel.getPassword())
                            )
                    ).setVersion("1.1")
            );

            LOGGER.debug("Request(Token) : {}", xmlStringRequest);
            String xmlStringResponse = hadrianServer.responseFor(xmlStringRequest);
            LOGGER.debug("Response(Token) : {}", xmlStringResponse);

            SeasRequestResponseModel seasResponse = xmlMapper.readValue(xmlStringResponse, SeasRequestResponseModel.class);
            CommunityManagerUserModel communityManagerUserModel =
                    new CommunityManagerUserModel()
                            .setUserId(communityManagerLoginModel.getUserName())
                            .setToken(hasText(seasResponse.getSeasSsoAuthResponseModel().getToken()) ? seasResponse.getSeasSsoAuthResponseModel().getToken().trim() : "")
                            .setAuthenticated(seasResponse.getSeasSsoAuthResponseModel().getAuthenticated());
            if (!communityManagerUserModel.getAuthenticated()) {
                throw internalServerError("username/password invalid.");
            }
            if (!seasResponse.getSeasSsoAuthResponseModel().getSeasPropertyModels().isEmpty()) {
                try {
                    communityManagerUserModel.setLang(hasText(communityManagerUserModel.getLang()) ? communityManagerUserModel.getLang() : "en");
                    loadAdditionalProperties(seasResponse.getSeasSsoAuthResponseModel().getSeasPropertyModels(), communityManagerUserModel);
                } catch (CommunityManagerServiceException cme) {
                    LOGGER.error("In ManageSeasClient  {}", cme.getErrorMessage());
                    userUpdated = true;
                    manageLdapUserService.manageUser(communityManagerUserModel);
                }
            } else {
                throw new GlobalAuthenticationException(CUSTOM_P_ERROR);
            }
            if (!userUpdated) manageLdapUserService.manageUser(communityManagerUserModel);

            return setAdditionalInfo(communityManagerUserModel);
        } catch (CommunityManagerServiceException ce) {
            LOGGER.error(ce.getErrorMessage());
            if (hasText(ce.getErrorMessage()) && ce.getErrorMessage().startsWith("username/password")) {
                throw customError(NOT_ACCEPTABLE.value(), "username/password invalid.");
            }
            throw customError(NOT_ACCEPTABLE.value(), UN_AUTH_ERROR);
        } catch (GlobalAuthenticationException ge) {
            LOGGER.error(ge.getMessage());
            throw new GlobalAuthenticationException(ge.getMessage());
        } catch (Exception e) {
            LOGGER.error("", e);
            throw customError(NOT_ACCEPTABLE.value(), "Internal server error, please contact system admin.");
        } finally {
            hadrianServer.disconnect();
        }
    }

    public Optional<CommunityManagerUserModel> getTokenDetails(String userId, String token, Cookie[] cookies) {
        if (!hasText(token)) {
            throw customError(NOT_ACCEPTABLE.value(), "Token Should not be null/empty");
        }
        if (!hasText(userId)) {
            throw customError(NOT_ACCEPTABLE.value(), "userId Should not be null/empty");
        }
        boolean userUpdated = FALSE;
        HadrianServer hadrianServer = loadHadrianServer();
        CommunityManagerUserModel communityManagerUserModel = new CommunityManagerUserModel().setToken(token).setUserId(userId);
        try {
            String request = xmlMapper.writeValueAsString(
                    new SeasRequestResponseModel().setSeasValidateTokenRequestModel(
                            new SeasValidateTokenRequestModel()
                                    .setToken(token.trim().replace(' ', '+'))
                                    .setSubject(userId)
                                    .setRefreshIfAboutToExpire("false")
                    )
            );
            LOGGER.debug("getTokenDetails() : Request: {}", request);
            String response = hadrianServer.responseFor(request);
            LOGGER.debug("getTokenDetails() : Response: {}", response);
            SeasRequestResponseModel responseModel = xmlMapper.readValue(response, SeasRequestResponseModel.class);
            if (!responseModel.getSeasValidateTokenResponseModel().getValidated()) {
                if (responseModel.getSeasValidateTokenResponseModel().getLogs() != null && !responseModel.getSeasValidateTokenResponseModel().getLogs().isEmpty()) {
                    throw internalServerError(responseModel.getSeasValidateTokenResponseModel().getLogs().get(0).replace("subject", "user"));
                }
                throw internalServerError("Session Expired");
            }

            if (!responseModel.getSeasValidateTokenResponseModel().getSeasPropertyModels().isEmpty()) {
                try {
                    loadAdditionalProperties(responseModel.getSeasValidateTokenResponseModel().getSeasPropertyModels(), communityManagerUserModel);
                    communityManagerUserModel.setLang(hasText(communityManagerUserModel.getLang()) ? communityManagerUserModel.getLang() : "en");
                } catch (CommunityManagerServiceException cme) {
                    userUpdated = TRUE;
                    manageLdapUserService.manageUser(communityManagerUserModel);
                }
            } else {
                throw new GlobalAuthenticationException(CUSTOM_P_ERROR);
            }

            if (isSsp(cookies) && responseModel.getSeasValidateTokenResponseModel().getValidated() && !userUpdated) {
                manageLdapUserService.manageUser(communityManagerUserModel);
            }
            setAdditionalInfo(communityManagerUserModel);

            LOGGER.info("End of getTokenDetails().");
            return Optional.of(communityManagerUserModel.setValidated(responseModel.getSeasValidateTokenResponseModel().getValidated()));
        } catch (CommunityManagerServiceException cme) {
            LOGGER.error("Exception in getTokenDetails()[CME]: {}", cme.getErrorMessage());
            throw customError(NOT_ACCEPTABLE.value(), UN_AUTH_ERROR);
        } catch (Exception gae) {
            LOGGER.error("Exception in getTokenDetails()[EX]: {}", gae.getMessage());
            throw customError(NOT_ACCEPTABLE.value(), UN_AUTH_ERROR);
        } finally {
            hadrianServer.disconnect();
        }

    }

    /**
     * This will be used for Base Authentication
     **/
    public CommunityManagerUserModel authenticate(CommunityManagerLoginModel communityManagerLoginModel) {
        CommunityManagerUserModel communityManagerUserModel;
        HadrianServer hadrianServer = loadHadrianServer();
        try {
            String xmlStringRequest = HEADER + xmlMapper.writeValueAsString(
                    new SeasRequestResponseModel().setSeasAuthRequestModel(
                            new SeasAuthRequestModel().
                                    setProfile(ssoSeasPropertiesConfig.getSeas().getAuthProfile())
                                    .setUserId(communityManagerLoginModel.getUserName())
                                    .setPassword(communityManagerLoginModel.getPassword())
                    ).setVersion("1.1")
            );

            LOGGER.debug("Request(Base Auth): {}", xmlStringRequest);
            String xmlStringResponse = hadrianServer.responseFor(xmlStringRequest);
            LOGGER.debug("Response(Base Auth): {}", xmlStringResponse);

            SeasRequestResponseModel seasResponse = xmlMapper.readValue(xmlStringResponse, SeasRequestResponseModel.class);
            communityManagerUserModel =
                    new CommunityManagerUserModel()
                            .setUserId(communityManagerLoginModel.getUserName())
                            .setAuthenticated(seasResponse.getSeasAuthResponseModel().getAuthenticated());

            if (!seasResponse.getSeasAuthResponseModel().getAuthenticated()) {
                throw internalServerError("Invalid userid or password");
            }

            if (!seasResponse.getSeasAuthResponseModel().getSeasPropertyModels().isEmpty()) {
                loadAdditionalProperties(seasResponse.getSeasAuthResponseModel().getSeasPropertyModels(), communityManagerUserModel);
            } else {
                throw internalServerError(CUSTOM_P_ERROR);
            }
            if (!communityManagerUserModel.getUserRole().equals(AuthoritiesConstants.SUPER_ADMIN)) {
                userService.findAllByExternalId(communityManagerUserModel.getExternalId()).ifPresent(userEntities -> {
                    if (userEntities.isEmpty()) {
                        throw internalServerError("User is not available in Application, please consume Authentication API or create user API to onboard the user account.");
                    } else {
                        if (userEntities.get(0).getStatus().equals("N")) {
                            throw internalServerError("User dont have permissions to access application, please contact system admin for approvals.");
                        }
                    }
                });
            }
        } catch (CommunityManagerServiceException cme) {
            LOGGER.error(cme.getErrorMessage());
            if (hasText(cme.getErrorMessage()) && cme.getErrorMessage().startsWith("Invalid userid")) {
                throw customError(NOT_ACCEPTABLE.value(), "Invalid userid or password");
            }
            throw customError(NOT_ACCEPTABLE.value(), UN_AUTH_ERROR);
        } catch (Exception ge) {
            LOGGER.error(ge.getMessage());
            throw customError(NOT_ACCEPTABLE.value(), UN_AUTH_ERROR);
        } finally {
            hadrianServer.disconnect();
        }
        return communityManagerUserModel;
    }

    public String getSspLogoutURL() {
        return ssoSeasPropertiesConfig.getSsp().getLogoutEndpoint().startsWith("/") ? ssoSeasPropertiesConfig.getSsp().getLogoutEndpoint() : "/" + ssoSeasPropertiesConfig.getSsp().getLogoutEndpoint();
    }

    private HadrianServer loadHadrianServer() {
        HadrianServer hadrianServer = new HadrianServer();
        ConnectParams connectParams = new ConnectParams();
        connectParams.setHost(ssoSeasPropertiesConfig.getSeas().getHost());
        connectParams.setPort(ssoSeasPropertiesConfig.getSeas().getPort());

        if (ssoSeasPropertiesConfig.getSeas().getSsl().isEnabled()) {
            LOGGER.info("SEAS SSL enabled. loading SSL configurations");
            connectParams.setSslInfo(getSslInfo());
        }

        try {
            hadrianServer.connect(connectParams);
            return hadrianServer;
        } catch (ClientConnectionException | ConnectionException e) {
            LOGGER.error("Unable to connect SEAS Server, please contact System admin.{}", e.getMessage());
            throw customError(NOT_ACCEPTABLE.value(), "Unable to connect SEAS Server, please contact System admin.");
        }
    }

    private void loadAdditionalProperties(List<SeasPropertyModel> seasPropertyModels, CommunityManagerUserModel communityManagerUserModel) {

        AtomicBoolean emailCheck = new AtomicBoolean(FALSE);
        AtomicBoolean extIdCheck = new AtomicBoolean(FALSE);
        AtomicBoolean roleCheck = new AtomicBoolean(FALSE);
        AtomicBoolean fNameCheck = new AtomicBoolean(FALSE);
        AtomicBoolean lNameCheck = new AtomicBoolean(FALSE);
        AtomicBoolean phoneCheck = new AtomicBoolean(FALSE);

        final List<String> roles = new ArrayList<>();

        seasPropertyModels.forEach(seasPropertyModel -> {
            if (hasText(seasPropertyModel.getName())) {
                if (ssoSeasPropertiesConfig.getUserRequest().getUser().getEmail().equals(seasPropertyModel.getName())) {
                    communityManagerUserModel.setEmail(throwIfCustomPropertyIsNull.apply(seasPropertyModel.getValue(), "Email"));
                    emailCheck.set(TRUE);
                } else if (ssoSeasPropertiesConfig.getUserRequest().getUser().getExternalId().equals(seasPropertyModel.getName())) {
                    communityManagerUserModel.setExternalId(throwIfCustomPropertyIsNull.apply(seasPropertyModel.getValue(), "External Id"));
                    extIdCheck.set(TRUE);
                } else if (ssoSeasPropertiesConfig.getUserRequest().getUser().getRole().equals(seasPropertyModel.getName())) {
                    final String role = manageLdapUserService.getCmRole(throwIfCustomPropertyIsNull.apply(seasPropertyModel.getValue(), "Role"), communityManagerUserModel);
                    communityManagerUserModel.setUserRole(role).setRoles(Collections.singletonList(role));
                    roleCheck.set(TRUE);
                    LOGGER.info("SEAS: Processed custom property role/roles data");
                } else if (ssoSeasPropertiesConfig.getUserRequest().getUser().getFirstName().equals(seasPropertyModel.getName())) {
                    communityManagerUserModel.setFirstName(throwIfCustomPropertyIsNull.apply(seasPropertyModel.getValue(), "First Name"));
                    communityManagerUserModel.setUserName(StringUtils.capitalize(seasPropertyModel.getValue()));
                    fNameCheck.set(TRUE);
                } else if (ssoSeasPropertiesConfig.getUserRequest().getUser().getLastName().equals(seasPropertyModel.getName())) {
                    communityManagerUserModel.setLastName(throwIfCustomPropertyIsNull.apply(seasPropertyModel.getValue(), "Last Name"));
                    lNameCheck.set(TRUE);
                } else if (ssoSeasPropertiesConfig.getUserRequest().getUser().getPhone().equals(seasPropertyModel.getName())) {
                    communityManagerUserModel.setPhone(throwIfCustomPropertyIsNull.apply(seasPropertyModel.getValue(), "Phone"));
                    phoneCheck.set(TRUE);
                } else if (ssoSeasPropertiesConfig.getUserRequest().getUser().getPreferredLanguage().equals(seasPropertyModel.getName())) {
                    if (hasText(seasPropertyModel.getValue())) {
                        communityManagerUserModel.setLang(seasPropertyModel.getValue());
                    } else {
                        communityManagerUserModel.setLang("en");
                    }
                } else if (seasPropertyModel.getName().startsWith("role-") && hasText(seasPropertyModel.getValue())
                        && seasPropertyModel.getValue().contains("CN") &&
                        !roleCheck.get()) {
                    LOGGER.info("SEAS: Loading Role from Groups");
                    String role = Arrays.stream(seasPropertyModel.getValue().split(","))
                            .filter(va -> va.startsWith("CN"))
                            .toArray()[0].toString().replace("CN=", "");
                    if (hasText(role)) {
                        roles.add(role);
                    }
                }
            }
        });

        if (!roleCheck.get()) {
            if (!roles.isEmpty()) {
                LOGGER.info("SEAS: Processing Multiple Roles from Groups.");
                final String role = manageLdapUserService.getCmRole(String.join(",", roles), communityManagerUserModel);
                communityManagerUserModel.setUserRole(role).setRoles(Collections.singletonList(role));
            } else {
                throw new GlobalAuthenticationException(ssoSeasPropertiesConfig.getUserRequest().getUser().getRole() + CUSTOM_P_ERROR);
            }
        }

        if (!emailCheck.get()) {
            throw new GlobalAuthenticationException(ssoSeasPropertiesConfig.getUserRequest().getUser().getEmail() + CUSTOM_P_ERROR);
        }
        if (!extIdCheck.get()) {
            throw new GlobalAuthenticationException(ssoSeasPropertiesConfig.getUserRequest().getUser().getExternalId() + CUSTOM_P_ERROR);
        }
        if (!fNameCheck.get()) {
            throw new GlobalAuthenticationException(ssoSeasPropertiesConfig.getUserRequest().getUser().getFirstName() + CUSTOM_P_ERROR);
        }
        if (!lNameCheck.get()) {
            throw new GlobalAuthenticationException(ssoSeasPropertiesConfig.getUserRequest().getUser().getLastName() + CUSTOM_P_ERROR);
        }
        if (!phoneCheck.get()) {
            throw new GlobalAuthenticationException(ssoSeasPropertiesConfig.getUserRequest().getUser().getPhone() + CUSTOM_P_ERROR);
        }

    }

    private static final BinaryOperator<String> throwIfCustomPropertyIsNull = (value, display) -> {
        if (hasLength(value)) {
            return value;
        } else {
            throw new GlobalAuthenticationException(display + " custom property value is null/empty in SEAS response, please contact system admin.");
        }
    };

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userService.getUser(username);
        return UserDetailsImpl.build(new CommunityManagerUserModel()
                .setUserId(userEntity.getUserid())
                .setEmail(userEntity.getEmail())
                .setRoles(Collections.singletonList(userEntity.getRole()))
        );
    }

    private boolean isSsp(Cookie[] cookies) {
        List<String> sspNames = Arrays.asList(("SspWebSessionId,SSOPP," + ssoSeasPropertiesConfig.getSsp().getTokenCookieName()).split(","));
        AtomicBoolean isSSp = new AtomicBoolean(false);
        if (cookies != null) {
            Arrays.stream(cookies)
                    .forEach(c -> {
                        if (sspNames.contains(c.getName())) {
                            isSSp.set(true);
                        }
                    });
        }
        return isSSp.get();
    }

    private CommunityManagerUserModel setAdditionalInfo(CommunityManagerUserModel communityManagerUserModel) {
        return communityManagerUserModel.setColor(color)
                .setAppCustomName(appCustomName)
                .setSfgEnabled(sfgEnabled)
                .setCmDeployment(isCm)
                .setDbInfo(dbType)
                .setAppVersion(AuthoritiesConstants.CM_APP_VERSION)
                .setFaxQueue(IGNORE)
                .setFaxQueueAccess(IGNORE)
                .setFaxQueueName(IGNORE)
                .setB2bUser(FALSE)
                .setApiConnect(cmProperties.getApiConnectEnabled())
                .setSfgPcDReports(cmProperties.getSfgPcdReports());
    }

    private SslInfo getSslInfo() {
        SslInfo sslInfo = new SslInfo();
        sslInfo.setKeyStoreFile(ssoSeasPropertiesConfig.getSeas().getSsl().getKeyStore().getName());
        sslInfo.setKeyStorePassword(ssoSeasPropertiesConfig.getSeas().getSsl().getKeyStore().getCmks());
        sslInfo.setKeyStoreType(ssoSeasPropertiesConfig.getSeas().getSsl().getKeyStore().getType());
        sslInfo.setClientAlias(ssoSeasPropertiesConfig.getSeas().getSsl().getKeyStore().getAlias());

        sslInfo.setTrustStoreFile(ssoSeasPropertiesConfig.getSeas().getSsl().getTrustStore().getName());
        sslInfo.setTrustStorePassword(ssoSeasPropertiesConfig.getSeas().getSsl().getTrustStore().getCmks());
        sslInfo.setTrustStoreType(ssoSeasPropertiesConfig.getSeas().getSsl().getTrustStore().getType());
        sslInfo.setServerAlias(ssoSeasPropertiesConfig.getSeas().getSsl().getTrustStore().getAlias());

        if (hasText(ssoSeasPropertiesConfig.getSeas().getSsl().getProtocol())) {
            sslInfo.setProtocol(ssoSeasPropertiesConfig.getSeas().getSsl().getProtocol());
        }
        if (hasText(ssoSeasPropertiesConfig.getSeas().getSsl().getCipherSuits())) {
            sslInfo.setCipherSuites(Collections.singletonList(ssoSeasPropertiesConfig.getSeas().getSsl().getCipherSuits()));
        }

        return sslInfo;
    }

}
