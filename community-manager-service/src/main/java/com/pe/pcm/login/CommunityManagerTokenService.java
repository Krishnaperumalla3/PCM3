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

package com.pe.pcm.login;

import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.exception.GlobalAuthenticationException;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.functions.TriFunction;
import com.pe.pcm.login.entity.SoUsersEntity;
import com.pe.pcm.login.entity.YfsUserEntity;
import com.pe.pcm.logout.UserTokenExpRepository;
import com.pe.pcm.logout.entity.UserTokenExpEntity;
import com.pe.pcm.miscellaneous.PasswordUtilityService;
import com.pe.pcm.properties.CMJwtProperties;
import com.pe.pcm.properties.CMProperties;
import com.pe.pcm.user.UserAttemptsService;
import com.pe.pcm.user.entity.UserAttemptsEntity;
import com.pe.pcm.utils.SIUtility;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

import static com.pe.pcm.constants.AuthoritiesConstants.CM_APP_VERSION;
import static com.pe.pcm.constants.AuthoritiesConstants.SUPER_ADMIN;
import static com.pe.pcm.constants.SecurityConstants.*;
import static com.pe.pcm.utils.CommonFunctions.isNotNull;
import static com.pe.pcm.utils.PCMConstants.IGNORE;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.time.ZoneOffset.UTC;

/**
 * @author Kiran Reddy.
 */
@Service
public class CommunityManagerTokenService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommunityManagerTokenService.class);

    private final CommunityManagerLoginService communityManagerLoginService;
    private PasswordUtilityService passwordUtilityService;
    private String color;
    private String appCustomName;
    private Boolean isCm;
    private Boolean sfgEnabled;
    private final Environment environment;
    //    private final UserAuditEventService userAuditEventService
    private UserAttemptsService userAttemptsService;
    private Integer maxWrongLoginAttempts;
    private Integer resetFalseAttempts;
    private final CMProperties cmProperties;
    private final CMJwtProperties cmJwtProperties;
    private final UserTokenExpRepository userTokenExpRepository;

    private static final Function<YfsUserEntity, CommunityManagerUserModel> serialize = CommunityManagerTokenService::apply;

    @Autowired
    public CommunityManagerTokenService(CommunityManagerLoginService communityManagerLoginService,
                                        PasswordUtilityService passwordUtilityService,
                                        @Value("${cm.color}") String color,
                                        @Value("${server.serverHeader}") String appCustomName,
                                        @Value("${cm.cm-deployment}") Boolean isCm,
                                        @Value("${sterling-b2bi.b2bi-api.sfg-api.active}") Boolean sfgEnabled,
                                        Environment environment,
                                        UserAttemptsService userAttemptsService,
                                        @Value("${login.max-false-attempts}") Integer maxWrongLoginAttempts,
                                        @Value("${login.reset-false-attempts}") Integer resetFalseAttempts,
                                        CMProperties cmProperties, CMJwtProperties cmJwtProperties, UserTokenExpRepository userTokenExpRepository) {
        this.communityManagerLoginService = communityManagerLoginService;
        this.passwordUtilityService = passwordUtilityService;
        this.color = color;
        this.appCustomName = appCustomName;
        this.isCm = isCm;
        this.sfgEnabled = sfgEnabled;
        this.environment = environment;
        this.userAttemptsService = userAttemptsService;
        this.maxWrongLoginAttempts = maxWrongLoginAttempts;
        this.resetFalseAttempts = resetFalseAttempts;
        this.cmProperties = cmProperties;
        this.cmJwtProperties = cmJwtProperties;
        this.userTokenExpRepository = userTokenExpRepository;
    }

    public Optional<CommunityManagerUserModel> authenticate(CommunityManagerLoginModel cmProfile, boolean isSmLogin) {
        LOGGER.debug("In CMTokenService, authenticate Method");
        if (!isNotNull(cmProfile.getUserName())) {
            throw GlobalExceptionHandler.internalServerError("User name should not be null, Please provide a valid user name.");
        }

        Optional<UserAttemptsEntity> userAttemptsEntityOptional = userAttemptsService.getUserAttempts(cmProfile.getUserName());
        if (userAttemptsEntityOptional.isPresent()) {
            boolean isAccountRest = userAttemptsService.autoResetFailAttempts(userAttemptsEntityOptional.get(), resetFalseAttempts);
            if (!isAccountRest && userAttemptsEntityOptional.get().getAttempts() >= maxWrongLoginAttempts) {
                throw new CommunityManagerServiceException(HttpStatus.NOT_ACCEPTABLE.value(), "Exceeded max no of failed login attempts, please try after some time or contact system admin.");
            }
        }

        String dbType = environment.getProperty("dbType");
        Optional<YfsUserEntity> yfsUserOptional = Optional.empty();
        try {
            yfsUserOptional = communityManagerLoginService.getYfsUser(cmProfile.getUserName());
        } catch (CommunityManagerServiceException e) {
            LOGGER.error(e.getErrorMessage());
        } catch (InvalidDataAccessResourceUsageException ide) {
            LOGGER.error(ide.getMessage());
            LOGGER.info("SI table 'YFS_USER' not available in current DB");
        }
        if (isSmLogin) {
            LOGGER.debug("In SM-SI User checking");
            if (yfsUserOptional.isPresent()) {
                AtomicReference<String> atomicRole = new AtomicReference<>(SUPER_ADMIN);
                communityManagerLoginService.getPcmUserNotThrow(cmProfile.getUserName()).ifPresent(user -> atomicRole.set(user.getRole()));
                return Optional.of(serialize.apply(yfsUserOptional.get().setBusinessKey(CM_APP_VERSION))
                        .setUserRole(atomicRole.get())
                        .setColor(color)
                        .setAppCustomName(appCustomName)
                        .setSfgEnabled(sfgEnabled)
                        .setCmDeployment(isCm)
                        .setDbInfo(dbType)
                        .setApiConnect(cmProperties.getApiConnectEnabled())
                        .setSfgPcDReports(cmProperties.getSfgPcdReports()));
            }
            LOGGER.debug("In SM-PCM User checking");
            Optional<SoUsersEntity> petpeUserOptional = communityManagerLoginService.getPcmUser(cmProfile.getUserName());
            if (petpeUserOptional.isPresent()) {
                return Optional.of(soEntityToUserModel.apply(petpeUserOptional.get(), cmProfile, TRUE)
                        .setDbInfo(dbType)
                        .setApiConnect(cmProperties.getApiConnectEnabled())
                        .setSfgPcDReports(cmProperties.getSfgPcdReports()));
            } else {
                throw new CommunityManagerServiceException(HttpStatus.NOT_FOUND.value(),
                        "SiteMinder User doesn't configured in Sterling Integrator / PCM. Please contact B2B Admin Team to setup SiteMinder RemoteUser for User : "
                                + cmProfile.getUserName());
            }
        } else if (yfsUserOptional.isPresent()) {
            LOGGER.debug("In SI User checking");
            AtomicReference<String> atomicRole = new AtomicReference<>(SUPER_ADMIN);
            communityManagerLoginService.getPcmUserNotThrow(cmProfile.getUserName()).ifPresent(user -> atomicRole.set(user.getRole()));
            YfsUserEntity yfsUser = yfsUserOptional.get();
            String encryptedPwd = SIUtility.getEncryptedPassword(cmProfile.getPassword(), yfsUser.getSalt());
            if (encryptedPwd.equals(yfsUser.getPassword())) {
                userAttemptsService.resetFailAttempts(userAttemptsEntityOptional, cmProfile.getUserName());
                return Optional.of(serialize.apply(yfsUser.setBusinessKey(CM_APP_VERSION))
                        .setUserRole(atomicRole.get())
                        .setColor(color)
                        .setAppCustomName(appCustomName)
                        .setSfgEnabled(sfgEnabled)
                        .setCmDeployment(isCm)
                        .setDbInfo(dbType)
                        .setApiConnect(cmProperties.getApiConnectEnabled())
                        .setSfgPcDReports(cmProperties.getSfgPcdReports()));
            } else {
                userAttemptsService.updateFailAttempts(cmProfile.getUserName());
                throw new CommunityManagerServiceException(HttpStatus.UNAUTHORIZED.value(), "userName/password Invalid");
            }
        } else {
            LOGGER.debug("In PCM User checking");
            Optional<SoUsersEntity> petpeUserOptional = communityManagerLoginService.getPcmUser(cmProfile.getUserName());
            if (petpeUserOptional.isPresent()) {
                return Optional.of(soEntityToUserModel.apply(petpeUserOptional.get(), cmProfile, FALSE)
                        .setDbInfo(dbType)
                        .setApiConnect(cmProperties.getApiConnectEnabled())
                        .setSfgPcDReports(cmProperties.getSfgPcdReports()));
            } else {
                throw new CommunityManagerServiceException(HttpStatus.NOT_FOUND.value(), "username/password Invalid");
            }
        }
//        } catch (CommunityManagerServiceException cme) {
//            status = false;
//            throw GlobalExceptionHandler.customError(cme.getStatusCode(), cme.getErrorMessage());
//        } finally {
//
//            //userAuditEventService.add(cmProfile.getUserName(), status ? AUTHENTICATION_SUCCESS : AUTHENTICATION_FAILURE, status ? "" : "Bad credentials");
//        }
    }

    public String tokenFor(CommunityManagerUserModel user) {
        LOGGER.debug("In CMTokenService, tokenFor Method ");
        Date expiration = Date.from(LocalDateTime.now(UTC).plusMinutes(cmJwtProperties.getSessionExpire()).toInstant(UTC));
        return Jwts.builder().setSubject(SUBJECT).setExpiration(expiration)
                .setIssuer(ISSUER)
                .claim(USERNAME, user.getUserId())
                .claim(ROLE, user.getUserRole())
                .claim(IS_SI_USER, user.isSiUser())
                .signWith(SignatureAlgorithm.HS512, cmJwtProperties.getSecretkey())
                .compact();
    }

    public Optional<CommunityManagerUserModel> verifyToken(String token) {
        LOGGER.debug("In CMTokenService, verifyToken Method");
        Jws<Claims> claims = Jwts.parser().setSigningKey(cmJwtProperties.getSecretkey()).parseClaimsJws(token);
        return communityManagerLoginService.minimal(claims.getBody().get(USERNAME).toString().trim(),
                claims.getBody().get(ROLE).toString().trim(), Boolean.parseBoolean(claims.getBody().get(IS_SI_USER).toString().trim()));
    }

    //This will be used for SSO JWT
    public Optional<CommunityManagerUserModel> verifyToken1(String token) {
        LOGGER.debug("In CMTokenService, verifyToken Method");
        Jws<Claims> claims = Jwts.parser().setSigningKey(cmJwtProperties.getSecretkey()).parseClaimsJws(token);
        if (claims.getBody().get(ROLE).toString().trim().equals(SUPER_ADMIN)) {
            return Optional.of(new CommunityManagerUserModel()
                    .setUserId(claims.getBody().get(USERNAME).toString().trim())
                    .setUserRole(SUPER_ADMIN)
                    .setRoles(Collections.singletonList(SUPER_ADMIN))
                    .setEmail("superAdmin@**.com")
                    .setUserName(StringUtils.capitalize(claims.getBody().get(USERNAME).toString().trim()))
            );
        }
        return communityManagerLoginService.minimal(claims.getBody().get(USERNAME).toString().trim(),
                claims.getBody().get(ROLE).toString().trim(), Boolean.parseBoolean(claims.getBody().get(IS_SI_USER).toString().trim()));
    }

    //check if the token has expired
    public boolean isTokenExpired(String token) {
        String userName = Jwts.parser().setSigningKey(cmJwtProperties.getSecretkey()).parseClaimsJws(token).getBody().get(USERNAME).toString().trim();
        List<UserTokenExpEntity> userTokenExpEntityList = userTokenExpRepository.findAllByUserid(userName).orElse(new ArrayList<>());
        userTokenExpEntityList.forEach(userTokenExpEntity -> {
            if (token.equals(userTokenExpEntity.getToken())) {
                LOGGER.info("User is trying to access the resource with invalid Token");
                throw new GlobalAuthenticationException("UnAuthorized to access the resource.");
            }
        });
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //retrieve expiration date from jwt token
    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieving any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(cmJwtProperties.getSecretkey()).parseClaimsJws(token).getBody();
    }

    private static CommunityManagerUserModel apply(YfsUserEntity yfsUserEntity) {
        if (isNotNull(yfsUserEntity.getOrganizationKey()) && (yfsUserEntity.getOrganizationKey().trim().equalsIgnoreCase("PCMadmin")
                || yfsUserEntity.getOrganizationKey().trim().equalsIgnoreCase("CMadmin"))) {
            return new CommunityManagerUserModel(yfsUserEntity.getLoginid().trim(), SUPER_ADMIN)
                    .setB2bUser(true)
                    .setFaxUser(false)
                    .setSiUser(true)
                    .setUserName(StringUtils.capitalize(yfsUserEntity.getUsername()))
                    .setFaxQueue(IGNORE)
                    .setFaxQueueAccess(IGNORE)
                    .setFaxQueueName(IGNORE)
                    .setAppVersion(yfsUserEntity.getBusinessKey())
                    .setLang("en");
        } else {
            throw new CommunityManagerServiceException(HttpStatus.UNAUTHORIZED.value(),
                    "RemoteUser Account is not having authorization to access. Please contact B2B Admin Team.");
        }
    }

    private final TriFunction<SoUsersEntity, CommunityManagerLoginModel, Boolean, CommunityManagerUserModel> soEntityToUserModel = (soUsersEntity, communityManagerLoginModel, isSmUser) -> {
        if (isSmUser || passwordUtilityService.encrypt(communityManagerLoginModel.getPassword()).equals(soUsersEntity.getPassword())) {
            CommunityManagerUserModel cmUser = new CommunityManagerUserModel(soUsersEntity.getUserid(), soUsersEntity.getRole());
            if (isNotNull(soUsersEntity.getStatus()) && soUsersEntity.getStatus().equalsIgnoreCase("Y")) {
                if (isNotNull(soUsersEntity.getActivationKey())) {
                    throw new CommunityManagerServiceException(HttpStatus.UNAUTHORIZED.value(), "User account should be activated in first login attempt, Please check your mail for activation)");
                }
                userAttemptsService.resetFailAttempts(soUsersEntity.getUserid());
                cmUser.setB2bUser(true)
                        .setFaxUser(false)
                        .setSiUser(false)
                        .setUserName(StringUtils.capitalize(soUsersEntity.getFirstName()) + " " + StringUtils.capitalize(soUsersEntity.getLastName()))
                        .setEmail(soUsersEntity.getEmail())
                        .setFaxQueue(IGNORE)
                        .setFaxQueueAccess(IGNORE)
                        .setFaxQueueName(IGNORE)
                        .setAppVersion(CM_APP_VERSION)
                        .setColor(color)
                        .setAppCustomName(appCustomName)
                        .setLang(soUsersEntity.getLang())
                        .setSfgEnabled(sfgEnabled)
                        .setCmDeployment(isCm);
            } else {
                throw new CommunityManagerServiceException(HttpStatus.UNAUTHORIZED.value(), "User account is not having authorization to access. Please contact PCM Admin Team.");
            }
            return cmUser;
        } else {
            userAttemptsService.updateFailAttempts(soUsersEntity.getUserid());
            throw new CommunityManagerServiceException(HttpStatus.UNAUTHORIZED.value(), "username/password invalid");
        }
    };

    @PostConstruct
    void loadDefaultValues() {
        if (maxWrongLoginAttempts == null || maxWrongLoginAttempts == 0) {
            maxWrongLoginAttempts = 5;
        }

        if (resetFalseAttempts == null || resetFalseAttempts == 0) {
            resetFalseAttempts = 30; //minutes
        }
    }

}
