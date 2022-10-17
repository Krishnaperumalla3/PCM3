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

package com.pe.pcm.seas.user;

import com.pe.pcm.config.seas.SsoSeasPropertiesConfig;
import com.pe.pcm.constants.ProfilesConstants;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.exception.GlobalAuthenticationException;
import com.pe.pcm.login.CommunityManagerUserModel;
import com.pe.pcm.miscellaneous.AppShutDownService;
import com.pe.pcm.user.UserAuditEventRepository;
import com.pe.pcm.user.UserModel;
import com.pe.pcm.user.UserRepository;
import com.pe.pcm.user.UserService;
import com.pe.pcm.user.entity.TpUserEntity;
import com.pe.pcm.user.entity.UserAuditEventEntity;
import com.pe.pcm.user.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

import static com.pe.pcm.constants.AuthoritiesConstants.*;
import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;
import static com.pe.pcm.utils.CommonFunctions.convertStringToBoolean;
import static com.pe.pcm.utils.PCMConstants.EXTERNAL;
import static com.pe.pcm.utils.PCMConstants.INTERNAL;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.springframework.util.StringUtils.hasLength;
import static org.springframework.util.StringUtils.hasText;

/**
 * @author Kiran Reddy.
 */
@Service
@Profile(ProfilesConstants.SSO_SSP_SEAS)
public class ManageLdapUserService {

    private final UserService userService;
    private final SsoSeasPropertiesConfig ssoSeasPropertiesConfig;
    private final AppShutDownService appShutDownService;
    private final UserAuditEventRepository userAuditEventRepository;
    private final UserRepository userRepository;
    private final Map<String, String> rolesMap = new LinkedHashMap<>();

    private static final String STATUS_FAIL = "STATUS_FAIL";
    private static final Logger LOGGER = LoggerFactory.getLogger(ManageLdapUserService.class);

    @Autowired
    public ManageLdapUserService(UserService userService,
                                 SsoSeasPropertiesConfig ssoSeasPropertiesConfig,
                                 AppShutDownService appShutDownService, UserAuditEventRepository userAuditEventRepository, UserRepository userRepository) {
        this.userService = userService;
        this.ssoSeasPropertiesConfig = ssoSeasPropertiesConfig;
        this.appShutDownService = appShutDownService;
        this.userAuditEventRepository = userAuditEventRepository;
        this.userRepository = userRepository;
    }

    public void manageUser(CommunityManagerUserModel communityManagerUserModel) {
        LOGGER.info("manageUser()");
        if (communityManagerUserModel.getUserRole().equals(SUPER_ADMIN)) return;

        try {
            Optional<List<UserEntity>> optionalUserEntities = userService.findAllByExternalId(communityManagerUserModel.getExternalId());
            if (optionalUserEntities.isPresent()) {
                if (optionalUserEntities.get().isEmpty()) {
                    LOGGER.info("User Account is not identified with ExternalId");
                    if (!communityManagerUserModel.getUserRole().equals(SUPER_ADMIN)) {
                        Optional<UserEntity> userEntityOptional = userService.getOptional(communityManagerUserModel.getUserId());
                        if (userEntityOptional.isPresent()) {
                            if (userEntityOptional.get().getUserType().equals(INTERNAL)) {
                                throw internalServerError("Please contact the System admin for migrate your account. userid: " + communityManagerUserModel.getUserId());
                            }
                            userService.update(serialize.apply(communityManagerUserModel).setStatus(FALSE).setUserRole(userEntityOptional.get().getRole())
                                    .setExternalId(userEntityOptional.get().getExternalId()));
                            throw internalServerError("ExternalId changed for " + communityManagerUserModel.getUserId() + ", please contact system admin.");
                        } else {
                            userService.create(serialize.apply(communityManagerUserModel).setStatus(TRUE), null);
                        }
                    } else {
                        LOGGER.info("User account role is super_admin so we are skipping next steps.");
                    }
                } else if (optionalUserEntities.get().size() > 1) {
                    Set<String> partnersSet = new HashSet<>();
                    Set<String> groupSet = new HashSet<>();
                    AtomicBoolean userStatus = new AtomicBoolean(true);
                    optionalUserEntities.get().forEach(userEntity -> {
                        if (userEntity.getStatus().equals("N")) {
                            userStatus.set(FALSE);
                            communityManagerUserModel.setAccountNonLocked(convertStringToBoolean(userEntity.getAccountNonLocked()));
                        }
                        userService.getTpUser(userEntity.getUserid()).ifPresent(tpUserEntity -> {
                            if (hasText(tpUserEntity.getPartnerList())) {
                                partnersSet.addAll(Arrays.asList(tpUserEntity.getPartnerList().split(",")));
                            }

                            if (hasText(tpUserEntity.getGroupList())) {
                                groupSet.addAll(Arrays.asList(tpUserEntity.getGroupList().split(",")));
                            }
                        });
                        userService.delete(userEntity.getUserid());
                    });
                    userService.create(serialize.apply(communityManagerUserModel)
                                    .setPartnersList(new ArrayList<>(partnersSet))
                                    .setGroupsList(new ArrayList<>(groupSet))
                                    .setStatus(userStatus.get()),
                            null);
                    if (!userStatus.get()) {
                        throw internalServerError(communityManagerUserModel.getUserId() + " user dont have permissions to access Application, Please contact system admin.");
                    }
                } else {
                    try {
                        String status = validate(communityManagerUserModel, optionalUserEntities.get().get(0));
                        if (status.equals("FAIL") || status.equals(STATUS_FAIL)) {
                            userService.update(
                                    serialize.apply(communityManagerUserModel)
                                            .setStatus(Boolean.parseBoolean(optionalUserEntities.get().get(0).getStatus()))
                                            .setAccountNonLocked(convertStringToBoolean(optionalUserEntities.get().get(0).getAccountNonLocked()))
                                            .setUserRole(optionalUserEntities.get().get(0).getRole())
                            );
                            if (status.equals(STATUS_FAIL)) {
                                throw internalServerError(communityManagerUserModel.getUserId() + " user dont have permissions to access Application, Please contact Administration team for access Approvals.");
                            }
                        }

                    } catch (GlobalAuthenticationException gae) {
                        if (gae.getMessage().contains("Role miss matched")) {
                            userService.update(
                                    serialize.apply(communityManagerUserModel)
                                            .setStatus(FALSE)
                                            .setAccountNonLocked(convertStringToBoolean(optionalUserEntities.get().get(0).getAccountNonLocked()))
                                            .setUserRole(optionalUserEntities.get().get(0).getRole())
                            );
                        } else {
                            TpUserEntity tpUserEntity = optionalUserEntities.get().get(0).getTpUserEntity();
                            userService.deleteModifying(communityManagerUserModel.getUserId());
                            userService.create(
                                    serialize.apply(communityManagerUserModel)
                                            .setStatus(FALSE)
                                            .setGroupsList(hasText(tpUserEntity.getGroupList()) ? Arrays.asList(tpUserEntity.getGroupList().split(",")) : new ArrayList<>())
                                            .setPartnersList(hasText(tpUserEntity.getPartnerList()) ? Arrays.asList(tpUserEntity.getPartnerList().split(",")) : new ArrayList<>())
                                    , null);
                        }
                        throw internalServerError(gae.getMessage());
                    }
                }
            } else {
                Optional<UserEntity> userEntityOptional = userService.getOptional(communityManagerUserModel.getUserId());
                if (userEntityOptional.isPresent()) {
                    if (userEntityOptional.get().getUserType().equals(INTERNAL)) {
                        throw internalServerError("Please contact the system admin for migrate your account. userid: " + communityManagerUserModel.getUserId());
                    }
                    userService.update(serialize.apply(communityManagerUserModel)
                            .setStatus(FALSE)
                            .setAccountNonLocked(convertStringToBoolean(userEntityOptional.get().getAccountNonLocked()))
                            .setUserRole(userEntityOptional.get().getRole()));
                    throw internalServerError("ExternalId changed for " + communityManagerUserModel.getUserId() + ", please contact system admin.");
                } else {
                    userService.create(serialize.apply(communityManagerUserModel).setStatus(TRUE), null);
                }
            }
        } catch (CommunityManagerServiceException cme) {
            LOGGER.error(cme.getErrorMessage());
            throw new GlobalAuthenticationException(cme.getErrorMessage());
        } catch (Exception e) {
            throw internalServerError(e.getMessage());
        }
    }


    private String validate(CommunityManagerUserModel communityManagerUserModel, UserEntity userEntity) {
        String status = "SUCCESS";
        if (userEntity.getStatus().equals("N")) {
            status = STATUS_FAIL;
        }
        //Note: Design gets reviewed and may modify soon.
        /*if (!communityManagerUserModel.getUserId().equals(userEntity.getUserid())) {
            LOGGER.error("Access denied (UserId mismatched), please contact System Admin team for access Approvals. userid: {}", communityManagerUserModel.getUserId());
            throw new GlobalAuthenticationException("Access denied (UserId miss matched), please contact Administration team for access Approvals.");
        }
        if (!communityManagerUserModel.getUserRole().equals(userEntity.getRole())) {
            LOGGER.error("Access denied (Role mismatched), please contact System Admin team for access Approvals. userid: {}", communityManagerUserModel.getUserId());
            throw new GlobalAuthenticationException("Access denied (Role miss matched), please contact Administration team for access Approvals.");
        }
        if (!communityManagerUserModel.getEmail().equals(userEntity.getEmail())) {
            status = "FAIL";
        }
        if (!communityManagerUserModel.getFirstName().equals(userEntity.getFirstName())) {
            status = "FAIL";
        }
        if (!communityManagerUserModel.getLastName().equals(userEntity.getLastName())) {
            status = "FAIL";
        }
        if (!communityManagerUserModel.getPhone().equals(userEntity.getPhone())) {
            status = "FAIL";
        }
        if (isNotNull(userEntity.getLang()) && !communityManagerUserModel.getLang().equals(userEntity.getLang())) {
            status = "FAIL";
        }*/

        return status;
    }

    public String getCmRole(String ldapRole, CommunityManagerUserModel communityManagerUserModel) {
        Map<String, String> cmRole = new HashMap<>();
        Arrays.stream(ldapRole.split(ssoSeasPropertiesConfig.getUserRequest().getRoleDelimiter())).forEach(lRole -> {
            String role = rolesMap.get(lRole.trim());
            if (hasText(role)) {
                cmRole.put(role, lRole.trim());
            }
        });
        if (cmRole.size() > 1) {
            String activity = "The User is matched with two or more allowed Roles. Please check with System Admin.";
            LOGGER.error(activity);
            userRepository.findById(communityManagerUserModel.getUserId()).ifPresent(userEntity -> userRepository.save(userEntity.setAccountNonLocked("N")
            ));
            userAuditEventRepository.save(new UserAuditEventEntity()
                    .setEventData(activity)
                    .setEventType("LDAP User Login")
                    .setPrinciple(communityManagerUserModel.getUserId()));

            throw new GlobalAuthenticationException(activity);
        } else if (!cmRole.isEmpty()) {
            userRepository.findById(communityManagerUserModel.getUserId()).ifPresent(userEntity -> {
                if (!cmRole.keySet().toArray()[0].toString().equals(userEntity.getRole())) {
                    userEntity.setRole(cmRole.keySet().toArray()[0].toString());
                }
                userRepository.save(userEntity.setAccountNonLocked("Y"));
            });
            return cmRole.keySet().toArray()[0].toString();
        } else {
            throw new GlobalAuthenticationException("The User does not match any allowed roles. Please check with System Admin.");
        }
    }


    private final Function<CommunityManagerUserModel, UserModel> serialize = communityManagerUserModel ->
            new UserModel().setUserType(EXTERNAL)
                    .setUserId(communityManagerUserModel.getUserId())
                    .setFirstName(communityManagerUserModel.getFirstName())
                    .setLastName(communityManagerUserModel.getLastName())
                    .setEmail(communityManagerUserModel.getEmail())
                    .setPhone(communityManagerUserModel.getPhone())
                    .setUserRole(communityManagerUserModel.getUserRole())
                    .setLang(communityManagerUserModel.getLang())
                    .setStatus(communityManagerUserModel.getStatus())
                    .setPartnersList(communityManagerUserModel.getPartnersList())
                    .setGroupsList(communityManagerUserModel.getGroupsList())
                    .setExternalId(communityManagerUserModel.getExternalId())
                    .setAccountNonLocked(communityManagerUserModel.getAccountNonLocked())
                    .setUpdateBy("PCM");

    @PostConstruct
    void sspSeasConfigValidations() {

        if (ssoSeasPropertiesConfig.getUserRequest() != null &&
                ssoSeasPropertiesConfig.getUserRequest().getUserRoles() != null) {

            if (!hasText(ssoSeasPropertiesConfig.getUserRequest().getUserRoles().getSuper_admin())
                    || !hasText(ssoSeasPropertiesConfig.getUserRequest().getUserRoles().getAdmin())
                    || !hasText(ssoSeasPropertiesConfig.getUserRequest().getUserRoles().getOn_boarder())
                    || !hasText(ssoSeasPropertiesConfig.getUserRequest().getUserRoles().getBusiness_admin())
                    || !hasText(ssoSeasPropertiesConfig.getUserRequest().getUserRoles().getBusiness_user())
                    || !hasText(ssoSeasPropertiesConfig.getUserRequest().getUserRoles().getData_processor())
                    || !hasText(ssoSeasPropertiesConfig.getUserRequest().getUserRoles().getData_processor_restricted())
            ) {
                appShutDownService.initiateShutdown("sso-ssp-seas.user-request.user-roles properties should not be empty, please map PCM roles with LDAP roles");
            }

            rolesMap.put(ssoSeasPropertiesConfig.getUserRequest().getUserRoles().getSuper_admin(), SUPER_ADMIN);
            rolesMap.put(ssoSeasPropertiesConfig.getUserRequest().getUserRoles().getAdmin(), ADMIN);
            rolesMap.put(ssoSeasPropertiesConfig.getUserRequest().getUserRoles().getOn_boarder(), ON_BOARDER);
            rolesMap.put(ssoSeasPropertiesConfig.getUserRequest().getUserRoles().getBusiness_admin(), BUSINESS_ADMIN);
            rolesMap.put(ssoSeasPropertiesConfig.getUserRequest().getUserRoles().getBusiness_user(), BUSINESS_USER);
            rolesMap.put(ssoSeasPropertiesConfig.getUserRequest().getUserRoles().getData_processor(), DATA_PROCESSOR);
            rolesMap.put(ssoSeasPropertiesConfig.getUserRequest().getUserRoles().getData_processor_restricted(), DATA_PROCESSOR_RESTRICTED);
        }

        if (ssoSeasPropertiesConfig.getSeas().getSsl().isEnabled() &&
                (ssoSeasPropertiesConfig.getSeas().getSsl().getKeyStore() == null ||
                        !hasText(ssoSeasPropertiesConfig.getSeas().getSsl().getKeyStore().getName()) ||
                        !hasText(ssoSeasPropertiesConfig.getSeas().getSsl().getKeyStore().getCmks()) ||
                        !hasText(ssoSeasPropertiesConfig.getSeas().getSsl().getKeyStore().getAlias()) ||
                        !hasText(ssoSeasPropertiesConfig.getSeas().getSsl().getKeyStore().getType()) ||
                        ssoSeasPropertiesConfig.getSeas().getSsl().getTrustStore() == null ||
                        !hasText(ssoSeasPropertiesConfig.getSeas().getSsl().getTrustStore().getName()) ||
                        !hasText(ssoSeasPropertiesConfig.getSeas().getSsl().getTrustStore().getCmks()) ||
                        !hasText(ssoSeasPropertiesConfig.getSeas().getSsl().getTrustStore().getAlias()) ||
                        !hasText(ssoSeasPropertiesConfig.getSeas().getSsl().getTrustStore().getType()))

        ) {
            appShutDownService.initiateShutdown("Please provide the proper info regarding SSL in YML file");
        }


        if (!hasLength(ssoSeasPropertiesConfig.getUserRequest().getUser().getEmail()) ||
                !hasLength(ssoSeasPropertiesConfig.getUserRequest().getUser().getRole()) ||
                !hasLength(ssoSeasPropertiesConfig.getUserRequest().getUser().getFirstName()) ||
                !hasLength(ssoSeasPropertiesConfig.getUserRequest().getUser().getLastName()) ||
                !hasLength(ssoSeasPropertiesConfig.getUserRequest().getUser().getPhone()) ||
                !hasLength(ssoSeasPropertiesConfig.getUserRequest().getUser().getExternalId())) {
            appShutDownService.initiateShutdown("sso-ssp-seas.user-request.user properties should not be empty, please provide valid properties names which was configured in SEAS.");
        }
    }


}
