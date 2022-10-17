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

package com.pe.pcm.sterling.yfs;

import com.pe.pcm.apiconnecct.APIAuthDataRepository;
import com.pe.pcm.apiconnecct.entity.APIAuthDataEntity;
import com.pe.pcm.b2b.B2BUserService;
import com.pe.pcm.common.CommunityManagerNameModel;
import com.pe.pcm.config.b2bi.SterlingB2biPropertiesConfig;
import com.pe.pcm.constants.AuthoritiesConstants;
import com.pe.pcm.generator.PrimaryKeyGeneratorService;
import com.pe.pcm.login.PwdPolicyRepository;
import com.pe.pcm.login.YfsUserRepository;
import com.pe.pcm.login.entity.YfsUserEntity;
import com.pe.pcm.miscellaneous.PasswordUtilityService;
import com.pe.pcm.pem.PemAccountExpiryModel;
import com.pe.pcm.protocol.as2.si.YFSOrganizationRepository;
import com.pe.pcm.protocol.as2.si.entity.YfsOrganizationDupEntity;
import com.pe.pcm.protocol.si.YfsOrganizationServiceDup;
import com.pe.pcm.sterling.SterlingAuthUserXrefSshService;
import com.pe.pcm.sterling.YfsUserModel;
import com.pe.pcm.sterling.partner.SciCodeUserXrefService;
import com.pe.pcm.sterling.yfs.entity.YfsResourceEntity;
import com.pe.pcm.user.UserModel;
import com.pe.pcm.user.UserService;
import com.pe.pcm.user.entity.UserEntity;
import com.pe.pcm.utils.CommonFunctions;
import com.pe.pcm.utils.PCMConstants;
import com.pe.pcm.utils.SIUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;
import static com.pe.pcm.protocol.function.SiProtocolFunctions.convertYfsUserModelToYfsPersonInfoDTO;
import static com.pe.pcm.protocol.function.SterlingFunctions.validateAndApplyDefaultValues;
import static com.pe.pcm.utils.CommonFunctions.getRandomSalt;
import static com.pe.pcm.utils.CommonFunctions.isNotNull;
import static com.pe.pcm.utils.PCMConstants.*;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.Integer.parseInt;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.springframework.util.StringUtils.hasText;

/**
 * @author Kiran Reddy.
 */
@Service
public class YfsUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YfsUserService.class);

    private final YfsUserRepository yfsUserRepository;
    private final YfsPersonInfoService yfsPersonInfoService;
    private final YfsResourceRepository yfsResourceRepository;

    private final YfsUserGroupListService yfsUserGroupListService;
    private final PrimaryKeyGeneratorService primaryKeyGeneratorService;
    private final SterlingAuthUserXrefSshService sterlingAuthUserXrefSshService;
    private final YfsResourcePermissionService yfsResourcePermissionService;

    private final PwdPolicyRepository pwdPolicyRepository;

    private final APIAuthDataRepository apiAuthDataRepository;

    private final B2BUserService b2BUserService;
    private final boolean b2biApiEnabled;
    private final SterlingB2biPropertiesConfig sterlingB2biPropertiesConfig;

    /*This is only for B2BI support*/
    private final SciCodeUserXrefService sciCodeUserXrefService;
    private final YfsOrganizationServiceDup yfsOrganizationServiceDup;
    private final UserService userService;
    private final PasswordUtilityService passwordUtilityService;

    private final YFSOrganizationRepository yfsOrganizationRepository;

    private static final String DEFAULT = "DEFAULT";
    private static final String CM_ADMIN = "CMAdmin";


    @Autowired
    public YfsUserService(YfsUserRepository yfsUserRepository,
                          PrimaryKeyGeneratorService primaryKeyGeneratorService,
                          YfsPersonInfoService yfsPersonInfoService,
                          YfsResourceRepository yfsResourceRepository,
                          YfsUserGroupListService yfsUserGroupListService,
                          SterlingAuthUserXrefSshService sterlingAuthUserXrefSshService,
                          YfsResourcePermissionService yfsResourcePermissionService,
                          PwdPolicyRepository pwdPolicyRepository, APIAuthDataRepository apiAuthDataRepository, B2BUserService b2BUserService,
                          @Value("${sterling-b2bi.b2bi-api.active}") boolean b2biApiEnabled,
                          SterlingB2biPropertiesConfig sterlingB2biPropertiesConfig,
                          SciCodeUserXrefService sciCodeUserXrefService,
                          YfsOrganizationServiceDup yfsOrganizationServiceDup,
                          UserService userService, PasswordUtilityService passwordUtilityService, YFSOrganizationRepository yfsOrganizationRepository) {
        this.yfsUserRepository = yfsUserRepository;
        this.primaryKeyGeneratorService = primaryKeyGeneratorService;
        this.yfsPersonInfoService = yfsPersonInfoService;
        this.yfsResourceRepository = yfsResourceRepository;
        this.yfsUserGroupListService = yfsUserGroupListService;
        this.sterlingAuthUserXrefSshService = sterlingAuthUserXrefSshService;
        this.yfsResourcePermissionService = yfsResourcePermissionService;
        this.pwdPolicyRepository = pwdPolicyRepository;
        this.apiAuthDataRepository = apiAuthDataRepository;
        this.b2BUserService = b2BUserService;
        this.b2biApiEnabled = b2biApiEnabled;
        this.sterlingB2biPropertiesConfig = sterlingB2biPropertiesConfig;
        this.sciCodeUserXrefService = sciCodeUserXrefService;
        this.yfsOrganizationServiceDup = yfsOrganizationServiceDup;
        this.userService = userService;
        this.passwordUtilityService = passwordUtilityService;
        this.yfsOrganizationRepository = yfsOrganizationRepository;
    }

    @Transactional
    public void create(YfsUserModel yfsUserModel) {
        save(yfsUserModel, false, false);
    }

    @Transactional
    public void update(YfsUserModel yfsUserModel) {
        save(yfsUserModel, true, false);
    }

    public YfsUserModel findByOrganizationKey(String organizationKey) {
        return assignData(yfsUserRepository.findFirstByOrganizationKey(organizationKey).orElseThrow(() -> internalServerError("User Entity not found, Organization key : " + organizationKey)));
    }

    private YfsUserModel assignData(YfsUserEntity yfsUserEntity) {
        YfsUserModel yfsUserModel = new YfsUserModel()
                .setPermissions(yfsResourcePermissionService.findAllPermissionsDesByUserKey(yfsUserEntity.getUsername()))
                .setPolicy(yfsUserEntity.getPasswordPolicyId())
                .setGroups(yfsUserGroupListService.findAllGroupsByUserKey(yfsUserEntity.getUserKey()))
                .setAuthorizedUserKeys(sterlingAuthUserXrefSshService.findAllByUser(yfsUserEntity.getUsername())
                        .stream().map(CommunityManagerNameModel::getName)
                        .collect(Collectors.toList()))
                .setUserName(yfsUserEntity.getUsername());
        yfsPersonInfoService.findById(yfsUserEntity.getBillingaddressKey()).ifPresent(yfsPersonInfoEntity ->
                yfsUserModel.setGivenName(yfsPersonInfoEntity.getFirstName())
                        .setSurname(yfsPersonInfoEntity.getLastName())
                        .setEmailId(yfsPersonInfoEntity.getEmailId())
        );
        return yfsUserModel;
    }

    public Optional<YfsUserEntity> getOnlyUser(String userName) {
        return yfsUserRepository.findFirstByUsername(userName);
    }

    public void delete(String userId) {
        getOnlyUser(userId).ifPresent(this::deleteUser);
    }

    private void deleteUser(YfsUserEntity yfsUserEntity) {
        if (isNotNull(yfsUserEntity.getBillingaddressKey())) {
            yfsPersonInfoService.delete(yfsUserEntity.getBillingaddressKey());
        }

        yfsUserGroupListService.delete(yfsUserEntity.getUserKey());
        sterlingAuthUserXrefSshService.deleteByUserId(yfsUserEntity.getUsername());
        yfsResourcePermissionService.deleteAllByUserKey(yfsUserEntity.getUsername());
        yfsUserRepository.deleteAllByUsername(yfsUserEntity.getUsername());
    }

    public void deleteByOrganizationKey(String organizationKey) {
        yfsUserRepository.findFirstByOrganizationKey(organizationKey).ifPresent(this::deleteUser);
    }

    public void save(YfsUserEntity yfsUserEntity) {
        yfsUserRepository.save(yfsUserEntity);
    }

    public void save(YfsUserModel yfsUserModel, boolean isUpdate, boolean isPatch) {

        String salt;
        String userKey;
        String billingKey;
        String encryptedPwd;
        String organizationKey;
        boolean isPwdChanged = false;
        final String orgKey = isNotNull(yfsUserModel.getOrganizationKey()) ?
                yfsOrganizationServiceDup.findFirstByOrganizationName(yfsUserModel.getOrganizationKey().trim())
                        .orElseThrow(() -> internalServerError("UserIdentity not fount, Identity:" + yfsUserModel.getOrganizationKey())).getOrganizationKey()
                : "";

        if (isUpdate || yfsUserModel.isMergeUser()) {
            Optional<YfsUserEntity> yfsUserEntityOptional = yfsUserRepository.findFirstByUsername(yfsUserModel.getUserName());
            if (yfsUserEntityOptional.isPresent()) {
                userKey = yfsUserEntityOptional.get().getUserKey();
                billingKey = yfsUserEntityOptional.get().getBillingaddressKey();
                salt = yfsUserEntityOptional.get().getSalt();
                if (isNotNull(yfsUserModel.getPassword())) {
                    if (!hasText(yfsUserModel.getPassword()) || yfsUserModel.getPassword().equals(PCMConstants.PRAGMA_EDGE_S)) {
                        encryptedPwd = yfsUserEntityOptional.get().getPassword();
                    } else {
                        isPwdChanged = true;
                        encryptedPwd = SIUtility.getEncryptedPassword(yfsUserModel.getPassword(), salt);
                    }
                } else {
                    encryptedPwd = yfsUserEntityOptional.get().getPassword();
                }
                yfsUserModel.setPwdLastChangeDon(isPwdChanged ? new Timestamp(new Date().getTime()) : yfsUserEntityOptional.get().getPwdlastchangedon());
                if (yfsUserModel.isMergeUser()) {
                    if (!hasText(yfsUserModel.getPwdPolicyId())) {
                        yfsUserModel.setPwdPolicyId(yfsUserEntityOptional.get().getPasswordPolicyId());
                    }
                    if (yfsUserModel.getSessionTimeOut() == null || yfsUserModel.getSessionTimeOut() == 0) {
                        yfsUserModel.setSessionTimeOut(yfsUserEntityOptional.get().getSessionTimeout());
                    }
                }
                //Apply Organization
                if (hasText(orgKey)) {
                    organizationKey = orgKey;
                } else {
                    if (hasText(yfsUserEntityOptional.get().getOrganizationKey())) {
                        organizationKey = yfsUserEntityOptional.get().getOrganizationKey();
                    } else {
                        organizationKey = DEFAULT;
                    }
                }

            } else {
                userKey = primaryKeyGeneratorService.generatePrimaryKey("", 4);
                billingKey = primaryKeyGeneratorService.generatePrimaryKey("B", 4);
                salt = getRandomSalt.apply(parseInt(randomNumeric(1)));
                if (!yfsUserModel.getUserType().toUpperCase().equals(EXTERNAL)) {
                    if (!hasText(yfsUserModel.getPassword()) || yfsUserModel.getPassword().equals(PCMConstants.PRAGMA_EDGE_S)) {
                        throw internalServerError(yfsUserModel.getUserName() + " user is not available, so please provide the user password.");
                    }
                    encryptedPwd = isNotNull(yfsUserModel.getPassword()) ? SIUtility.getEncryptedPassword(yfsUserModel.getPassword(), salt) : " ";
                } else {
                    encryptedPwd = "";
                }
                yfsUserModel.setPwdLastChangeDon(new Timestamp(new Date().getTime()));

                //Apply Organization
                if (hasText(orgKey)) {
                    organizationKey = orgKey;
                } else {
                    organizationKey = DEFAULT;
                }
            }

        } else {

            duplicateCheck(yfsUserModel.getUserName());

            userKey = primaryKeyGeneratorService.generatePrimaryKey("", 4);
            billingKey = primaryKeyGeneratorService.generatePrimaryKey("B", 4);
            /*Just to Skip the first count value*/
            randomNumeric(1);
            if (!yfsUserModel.getUserType().toUpperCase().equals(EXTERNAL)) {
                salt = getRandomSalt.apply(parseInt(randomNumeric(1)));
                encryptedPwd = isNotNull(yfsUserModel.getPassword()) ? SIUtility.getEncryptedPassword(yfsUserModel.getPassword(), salt) : " ";
            } else {
                salt = "";
                encryptedPwd = "";
            }
            yfsUserModel.setPwdLastChangeDon(new Timestamp(new Date().getTime()));

            //Apply Organization
            if (hasText(orgKey)) {
                organizationKey = orgKey;
            } else {
                organizationKey = DEFAULT;
            }
        }

        /*On alok request added new boolean param in remoteProfileModel as isPatch which will update only password
         * without calling any GET API(B2B) using patch call*/

        /* ---> Password change is not recognizing by SI, so we are using B2Bi API for update the password
         * Soon we need to remove this when we find the solution for cache issue in SI */
        if (isPatch && b2biApiEnabled && isPwdChanged) {
            LOGGER.info("Entered into B2Bi API consumer where we are updating user account password using PATCH.");
            b2BUserService.restUserAccountsByB2BiApiPatch(yfsUserModel.getPassword(), yfsUserModel.getUserName(), FALSE);

        } else if (b2biApiEnabled && isPwdChanged) {
            LOGGER.info("Entered into B2Bi API consumer where we are updating user account password.");
            b2BUserService.restUserAccountsByB2BiApi(
                    new PemAccountExpiryModel()
                            .setUserName(yfsUserModel.getUserName())
                            .setPass(yfsUserModel.getPassword())
                    , FALSE);

        }/*Password update error changes should be handled*/

        if (yfsUserModel.isResetPermissions() && !organizationKey.equals(DEFAULT)) {
            LOGGER.info("Deleting the Permissions from other child accounts");
            deletePermissionsFromOrg(yfsUserModel.getUserIdentity(), organizationKey, yfsUserModel.getPermissionsToDelete());
        }

        //Loading Given Name and SurName from DB if its is Merge
        if (yfsUserModel.isMergeUser()) {
            yfsPersonInfoService.findById(billingKey).ifPresent(yfsPersonInfoEntity -> {
                yfsUserModel.setGivenName(yfsPersonInfoEntity.getFirstName());
                yfsUserModel.setSurname(yfsPersonInfoEntity.getLastName());
            });
        }

        /*Create or Update Person Info Into SI*/
        yfsPersonInfoService.save(convertYfsUserModelToYfsPersonInfoDTO.apply(yfsUserModel).setPersonInfoKey(billingKey));

        /*Create or Update User Groups into SI*/
        yfsUserGroupListService.save(userKey, yfsUserModel.getGroups(), yfsUserModel.isMergeUser());

        /*Create or update Authorized userKeys*/
        sterlingAuthUserXrefSshService.saveAll(yfsUserModel.getUserName(), yfsUserModel.getAuthorizedUserKeys(), yfsUserModel.isMergeUser());

        /*Create or Update Permissions*/
        yfsResourcePermissionService.save(userKey, yfsUserModel.getPermissions(), yfsUserModel.isMergeUser());

        /*Create or Update the User Account*/
        YfsUserEntity yfsUserEntity = serialize.apply(yfsUserModel)
                .setUserKey(userKey)
                .setBillingaddressKey(billingKey)
                .setOrganizationKey(yfsUserModel.isPcmUserLogin() ? CM_ADMIN : organizationKey);
        if (isNotNull(yfsUserModel.getUserType()) && yfsUserModel.getUserType().equalsIgnoreCase(EXTERNAL)) {
            yfsUserEntity.setPassword("not applicable");
            yfsUserEntity.setSalt(null);
        } else {
            yfsUserEntity.setPassword(encryptedPwd);
            yfsUserEntity.setSalt(salt);
        }
        //Here we need to check EXTERNAL, and then we need to handle the request
        if (isNotNull(yfsUserModel.getUserType()) && isNotNull(yfsUserModel.getAuthenticationHost())) {
            Integer authHostInt;
            if (!sterlingB2biPropertiesConfig.getB2biApi().getAuthHost().isEmpty()) {
                authHostInt = sterlingB2biPropertiesConfig.getB2biApi().getAuthHost().get(yfsUserModel.getAuthenticationHost());
            } else {
                throw internalServerError("Authentication Profile not configured properly. Please contact system admin...");
            }
            if (isNotNull(authHostInt)) {
                yfsUserEntity.setUsertype(yfsUserModel.getUserType() + "_" + authHostInt);
            } else {
                throw internalServerError("Provided Authentication Host " + yfsUserModel.getAuthenticationHost() + " is not configured please contact system admin");
            }
        } else if (isNotNull(yfsUserModel.getUserType()) && yfsUserModel.getUserType().equalsIgnoreCase(EXTERNAL) && !isNotNull(yfsUserModel.getAuthenticationHost())) {
            yfsUserEntity.setUsertype(yfsUserModel.getUserType() + "_" + DEFAULT_HOST);
        } else {
            yfsUserEntity.setUsertype(INTERNAL);
            yfsUserEntity.setPassword(encryptedPwd);
            yfsUserEntity.setSalt(salt);
        }

        yfsUserRepository.save(yfsUserEntity);
    }

    private void duplicateCheck(String userName) {
        yfsUserRepository.findFirstByUsername(userName.trim()).ifPresent(yfsUserEntity -> {
            throw internalServerError("User already exist, User Name : " + userName);
        });
    }

    public List<YfsUserEntity> findAllByUserName(String userName, boolean isLike) {
        return (isLike ? yfsUserRepository.findAllByUsernameContainingIgnoreCaseOrderByUsername(userName.toLowerCase()) :
                yfsUserRepository.findAllByUsernameOrderByUsername(userName)).orElse(new ArrayList<>());
    }

    private static final Function<YfsUserModel, YfsUserEntity> serialize = yfsUserModel -> {
        YfsUserEntity yfsUserEntity = new YfsUserEntity().setUsername(yfsUserModel.getUserName().trim())
                .setLoginid(yfsUserModel.getUserName())
                .setIsPasswordEncrypted("Y")
                .setBusinessKey(" ")
                .setUsergroupKey(" ")
                .setContactaddressKey(" ")
                .setImagefile(" ")
                .setNoteKey(" ")
                .setPreferenceKey(" ")
                .setPwdlastchangedon(yfsUserModel.getPwdLastChangeDon())
                .setActivateflag("Y")
                .setLongdesc(" ")
                .setLocalecode("en")
                .setParentUserKey(isNotNull(yfsUserModel.getParentUserKey()) ? yfsUserModel.getParentUserKey() : " ")
                .setMenuId("PLTADM_MENU")
                .setTheme("sapphire")
                .setCreatorOrganizationKey(" ")
                .setSystemname(" ")
                .setDataSecurityGroupId(" ")
                .setDepartmentKey(" ")
                .setPasswordPolicyId(yfsUserModel.getPwdPolicyId())
                .setSessionTimeout(yfsUserModel.getSessionTimeOut() != null ? yfsUserModel.getSessionTimeOut() : 0)
                .setSuperUser("0")
                .setChangePassNext(0);
        yfsUserEntity.setLockid(0);
        yfsUserEntity.setCreateuserid(" ");
        yfsUserEntity.setModifyuserid(" ");
        yfsUserEntity.setCreateprogid(" ");
        yfsUserEntity.setModifyprogid(" ");

        return yfsUserEntity;
    };


    public Optional<List<YfsUserEntity>> findAllByUsernameIn(List<String> usersList) {
        return yfsUserRepository.findAllByUsernameIn(usersList);
    }

    public List<YfsUserEntity> loadOrgUsers(String organizationKey) {
        int count = 24;
        StringBuilder sb = new StringBuilder(organizationKey);
        if (count > organizationKey.length()) {
            IntStream.range(0, (count - organizationKey.length())).forEach(num -> sb.append(" "));
        }
        return yfsUserRepository.findAllByOrganizationKey(sb.toString());
    }

    public void updatePasswordLastChangeDone(String userName) {
        yfsUserRepository.findFirstByUsername(userName).ifPresent(yfsUserEntity ->
                yfsUserRepository.save(yfsUserEntity.setPwdlastchangedon(new Timestamp(new Date().getTime()))));
    }

    @Transactional
    public void assignGroupsAndPermissions(YfsUserModel yfsUserModel) {
        yfsUserModel.setMergeUser(TRUE);//This needs to get from user, for demo we keep like this
        Optional<YfsUserEntity> yfsUserEntityOptional = getOnlyUser(yfsUserModel.getUserName());
        Optional<UserEntity> userEntityOptional = userService.getOptional(yfsUserModel.getUserName());
        if (!userEntityOptional.isPresent()) {
            if (yfsUserEntityOptional.isPresent()) {
                UserModel userModel = new UserModel().setUserId(yfsUserEntityOptional.get().getUsername());
                if (hasText(yfsUserEntityOptional.get().getBillingaddressKey())) {
                    yfsPersonInfoService.findById(yfsUserEntityOptional.get().getBillingaddressKey()).ifPresent(yfsPersonInfoEntity ->
                            userModel.setFirstName(yfsPersonInfoEntity.getFirstName())
                                    .setLastName(yfsPersonInfoEntity.getLastName())
                                    .setEmail(yfsPersonInfoEntity.getEmailId())
                    );
                } else {
                    userModel.setFirstName(yfsUserEntityOptional.get().getUsername())
                            .setLastName(yfsUserEntityOptional.get().getUsername())
                            .setEmail(yfsUserEntityOptional.get().getUserKey());
                }
                userModel.setUserType(EXTERNAL_SI)
                        .setPhone("123456789")
                        .setStatus(TRUE)
                        .setUserRole(AuthoritiesConstants.FILE_OPERATOR);
                userEntityOptional = Optional.of(userService.createUser(userModel, FALSE));
            } else {
                throw internalServerError("User must be available at least in Sterling B2Bi or Community Manager");
            }
        }

        if (yfsUserEntityOptional.isPresent()) {
            /*Create or Update User Groups into SI*/
            yfsUserGroupListService.save(yfsUserEntityOptional.get().getUserKey(), yfsUserModel.getGroups(), yfsUserModel.isMergeUser());
            /*Create or Update Permissions*/
            yfsResourcePermissionService.save(yfsUserEntityOptional.get().getUserKey(), yfsUserModel.getPermissions(), yfsUserModel.isMergeUser());
        } else {
            LOGGER.info("YFS user not available");
            save(validateAndApplyDefaultValues.apply(
                            yfsUserModel.setPassword(userEntityOptional.get().getUserType().equals(EXTERNAL) ? "not applicable" : passwordUtilityService.decrypt(userEntityOptional.get().getPassword()))
                                    .setPcmUserLogin(TRUE),
                            userEntityOptional.get()),
                    FALSE,
                    FALSE
            );
        }
    }

    public YfsUserModel getGroupsAndPermissions(String userName) {
        YfsUserModel yfsUserModel = new YfsUserModel();
        getOnlyUser(userName).ifPresent(yfsUserEntity -> yfsUserModel.setUserName(userName)
                .setGroups(yfsUserGroupListService.findAllGroupsByUserKey(yfsUserEntity.getUserKey()))
                .setPermissions(yfsResourcePermissionService.findAllPermissionsDesByUserKey(yfsUserEntity.getUserKey()).stream()
                        .filter(per -> per.endsWith(SPACE_MAILBOX))
                        .map(per -> per.replace(SPACE_MAILBOX, ""))
                        .collect(Collectors.toList()))
        );
        return yfsUserModel;
    }

    /*This is only for Permission handling when we use User B2Bi API*/
    private void deletePermissionsFromOrg(String userIdentity, String
            organizationKey, List<String> permissionsToDelete) {

        if (!permissionsToDelete.isEmpty()) {
            LOGGER.info("Permissions to delete from child accounts, permissions : {}", permissionsToDelete);
            List<String> resourceKeys = yfsResourceRepository.findAllByResourceDescIn(permissionsToDelete)
                    .stream()
                    .map(YfsResourceEntity::getResourceKey)
                    .collect(Collectors.toList());
            if (!resourceKeys.isEmpty()) {
                final List<String> masterAccounts;
                List<String> orgProfileObjectIds = yfsOrganizationServiceDup.findAllByOrganizationName(userIdentity)
                        .stream()
                        .map(YfsOrganizationDupEntity::getObjectId)
                        .collect(Collectors.toList());

                if (!orgProfileObjectIds.isEmpty()) {
                    masterAccounts = sciCodeUserXrefService.findAllByTpObjectIdIn(orgProfileObjectIds)
                            .stream()
                            .map(sciCodeUserXrefEntity -> sciCodeUserXrefEntity.getSciCodeUserXrefIdentity().getUserId().trim())
                            .collect(Collectors.toList());
                } else {
                    masterAccounts = new ArrayList<>();
                }

                yfsUserRepository.findAllByOrganizationKey(CommonFunctions.getRequiredLengthString.apply(organizationKey, 24)).forEach(yfsUserEntity -> {
                    if (!masterAccounts.contains(yfsUserEntity.getUsername().trim())) {
                        yfsResourcePermissionService.findAllByUserKeyAndResourceKeyIn(yfsUserEntity.getUserKey(), resourceKeys).forEach(yfsResourcePermissionEntity ->
                                yfsResourcePermissionService.deleteById(yfsResourcePermissionEntity.getResourcePermissionKey()));
                        LOGGER.info("Deleting Permissions from Accounts");
                    } else {
                        LOGGER.info("Skipping Master account permission delete process, Master account");
                    }
                });

            }
        } else {
            LOGGER.info("No Permissions to delete from child accounts");
        }
    }

    public List<String> getOrganisationNames() {
        return yfsOrganizationRepository.findAllByOrderByOrganizationName().stream()
                .map(yfsOrganizationEntity -> yfsOrganizationEntity.getOrganizationKey().trim()).collect(Collectors.toList());
    }

    public List<String> getManagerIds() {
        return yfsUserRepository.findAllByOrderByLoginid().stream()
                .map(yfsUserEntity -> yfsUserEntity.getLoginid().trim()).collect(Collectors.toList());
    }

    public List<String> getPolicyId() {
        List<String> policyId = new ArrayList<>();
        pwdPolicyRepository.findAllByOrderByPolicyId().ifPresent(pwdPolicyEntities -> pwdPolicyEntities.forEach(pwdPolicyEntity -> policyId.add(pwdPolicyEntity.getPolicyId().trim())));
        return policyId;
    }

    public List<String> getAuthHost() {
        return apiAuthDataRepository.findDistinctByOrderByAuthType().stream().map(APIAuthDataEntity::getAuthType).distinct().collect(Collectors.toList());
    }

    public YfsUserModel getYfsUser(String username) {
        YfsUserModel yfsUserModel = getGroupsAndPermissions(username);
        getOnlyUser(username).ifPresent(yfsUserEntity -> yfsUserModel.setUserName(username)
                .setUserType(yfsUserEntity.getUsertype().substring(0, 8))
                .setOrganizationKey(yfsUserEntity.getOrganizationKey())
                .setParentUserKey(yfsUserEntity.getParentUserKey())
                .setUserIdentity(yfsUserEntity.getLoginid()));
        return yfsUserModel;
    }
}
