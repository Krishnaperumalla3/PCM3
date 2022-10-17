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

package com.pe.pcm.user;

import com.pe.pcm.common.CommunityManagerKeyValueModel;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.mail.MailService;
import com.pe.pcm.miscellaneous.IndependentService;
import com.pe.pcm.miscellaneous.PasswordUtilityService;
import com.pe.pcm.miscellaneous.UserUtilityService;
import com.pe.pcm.user.entity.TpUserEntity;
import com.pe.pcm.user.entity.UserEntity;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.pe.pcm.constants.AuthoritiesConstants.*;
import static com.pe.pcm.constants.ProfilesConstants.SSO_SSP_SEAS;
import static com.pe.pcm.exception.GlobalExceptionHandler.*;
import static com.pe.pcm.miscellaneous.CommonQueryPredicate.getPredicate;
import static com.pe.pcm.utils.CommonFunctions.*;
import static com.pe.pcm.utils.PCMConstants.*;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.springframework.util.StringUtils.hasLength;
import static org.springframework.util.StringUtils.hasText;

/**
 * @author Kiran Reddy.
 */
@Service
public class UserService {

    private static final String USER_TYPE = "userType";

    private final UserRepository userRepository;
    private final TpUserRepository tpUserRepository;
    private final MailService mailService;
    private PasswordUtilityService passwordUtilityService;
    private UserUtilityService userUtilityService;
    private final IndependentService independentService;

    private final RoleRepository roleRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository,
                       TpUserRepository tpUserRepository,
                       MailService mailService,
                       PasswordUtilityService passwordUtilityService,
                       UserUtilityService userUtilityService,
                       IndependentService independentService,
                       RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.tpUserRepository = tpUserRepository;
        this.mailService = mailService;
        this.passwordUtilityService = passwordUtilityService;
        this.userUtilityService = userUtilityService;
        this.independentService = independentService;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public void create(UserModel userModel, String baseUrl) {
        LOGGER.info("createUser()");
        validation(userModel);
        userRepository.findAllByUseridOrEmailOrExternalId(userModel.getUserId(), userModel.getEmail(), userModel.getExternalId()).ifPresent(userEntities ->
                userEntities.forEach(userEntity -> {
                    if (userModel.getUserId().equalsIgnoreCase(userEntity.getUserid())) {
                        throw conflict("User");
                    }
                    if (userModel.getEmail().equalsIgnoreCase(userEntity.getEmail())) {
                        throw conflict("Email");
                    }
                    if (hasLength(userModel.getExternalId()) && userModel.getExternalId().equalsIgnoreCase(userEntity.getExternalId())) {
                        throw conflict("ExternalId");
                    }
                })
        );
        if (userModel.getUserType().equals(INTERNAL)) {
            LOGGER.info("Sending activation mail.");
            mailService.sendActivationEmail(createUser(userModel, FALSE), baseUrl);
        } else {
            LOGGER.info("Not sending activation mail.");
            createUser(userModel, FALSE);
        }


    }

    @Transactional
    public void update(UserModel userModel) {
        LOGGER.info("Update user.");
        validation(userModel);
        userRepository.findAllByUseridOrEmailOrExternalId(null, userModel.getEmail(), userModel.getExternalId()).ifPresent(userEntities ->
                userEntities.forEach(userEntity -> {
                    if (userEntity.getEmail().equalsIgnoreCase(userModel.getEmail()) && !userEntity.getUserid().equalsIgnoreCase(userModel.getUserId())) {
                        throw conflict("Email");
                    }

                    if (hasLength(userModel.getExternalId()) && userModel.getExternalId().equalsIgnoreCase(userEntity.getExternalId()) && !userEntity.getUserid().equalsIgnoreCase(userModel.getUserId())) {
                        throw conflict("ExternalId");
                    }

                }));
        createUser(userModel, TRUE);
    }

    @Transactional
    public void statusChange(String pkId, Boolean status) {
        update(get(pkId).setStatus(status));
    }

    @Transactional
    public void changeLang(UserLangModel userLangModel) {
        try {
            if (isNotNull(userLangModel.getLang())) {
                update(get(userLangModel.getUserId()).setLang(userLangModel.getLang()));
            }
        } catch (CommunityManagerServiceException cme) {
            if (cme.getStatusCode() != HttpStatus.NOT_FOUND.value()) {
                throw internalServerError(cme.getErrorMessage());
            }
        }

    }

    @Transactional
    public void delete(String userId) {
        userRepository.findById(userId).ifPresent(userRepository::delete);
        tpUserRepository.findById(userId).ifPresent(tpUserRepository::delete);
    }

    @Transactional
    public void deleteModifying(String userId) {
        userRepository.findById(userId).ifPresent(userEntity -> userRepository.deleteByIdModifying(userId));
        tpUserRepository.findById(userId).ifPresent(tpUserEntity -> tpUserRepository.deleteByIdModifying(userId));
    }

    public UserModel get(String userId) {
        return mapperToUserModel.apply(getUser(userId));
    }

    //This method is public, is only for AssignGroup Permissions in YfsUserService
    public UserEntity createUser(UserModel userModel, Boolean isUpdate) {
        tpUserRepository.save(mapperToTpUserEntity.apply(userModel));
        return userRepository.save(mapperToUserEntity.apply(userModel, isUpdate));
    }

    private void createUser(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    public UserEntity getUser(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> notFound("User")).setTpUserEntity(tpUserRepository.findById(userId).orElse(new TpUserEntity()));
    }

    public UserEntity getOnlyUser(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> notFound("User"));
    }

    public Optional<UserEntity> getOptional(String userId) {
        return userRepository.findById(userId);
    }

    @Transactional
    public void unLockAndActivateUser(String userId) {
        getOptional(userId).ifPresent(userEntity -> userRepository.save(userEntity.setAccountNonLocked("Y")
                .setStatus("Y")));
    }

    public Page<UserEntity> search(UserModel userModel, Pageable pageable) {
        Page<UserEntity> userEntityList = userRepository
                .findAll((Specification<UserEntity>) (root, query, cb) -> {
                    List<Predicate> predicates = new ArrayList<>();
                    getPredicate(root, cb, predicates, userModel.getUserId(), "userid", true);
                    getPredicate(root, cb, predicates, userModel.getUserRole(), "role", false);
                    getPredicate(root, cb, predicates, userModel.getFirstName(), "firstName", true);
                    getPredicate(root, cb, predicates, userModel.getLastName(), "lastName", true);
                    getPredicate(root, cb, predicates, userModel.getEmail(), "email", true);
                    getPredicate(root, cb, predicates, userModel.getStatus() != null ? convertBooleanToString(userModel.getStatus()) : "", "status", true);

                    if (userUtilityService.getUserOrRole(false).equals(ADMIN)) {
                        predicates.add(cb.notEqual(root.get("role"), SUPER_ADMIN));
                    }
                    if (independentService.getActiveProfile().equals(SSO_SSP_SEAS)) {
                        getPredicate(root, cb, predicates, EXTERNAL, USER_TYPE, false);
                    } else {
                        getPredicate(root, cb, predicates, INTERNAL, USER_TYPE, false);
                    }
                    getPredicate(root, cb, predicates, userModel.getUserType(), USER_TYPE, false);
                    return cb.and(predicates.toArray(new Predicate[0]));
                }, pageable);
        return new PageImpl<>(new ArrayList<>(userEntityList.getContent()), pageable, userEntityList.getTotalElements());
    }

    @Transactional
    public Optional<UserEntity> activateRegistration(String key) {
        Optional<UserEntity> userEntityOptional = userRepository.findOneByActivationKey(key)
                .map(user -> {
                    user.setStatus("Y");
                    user.setActivationKey(null);
                    return user;
                });
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userRepository.save(userEntityOptional.get());
            mailService.sendPasswordMail(SerializationUtils.clone(userEntity).setPassword(passwordUtilityService.decrypt(userEntity.getPassword())));
        }
        return userEntityOptional;
    }

    private UserEntity getUserByEmail(String email) {
        List<UserEntity> userEntities = userRepository.findAllByEmail(email).orElse(new ArrayList<>());
        if (userEntities.isEmpty()) {
            throw internalServerError("No User available with provided email");
        } else if (userEntities.size() > 1) {
            throw internalServerError("More than 1 user available with provided email, Please Contact Admin");
        } else {
            return userEntities.get(0);
        }
    }

    @Transactional
    public String updatePasswordFromForgot(String userId, String password, String otp) {
        if (!userId.isEmpty() && !password.isEmpty() && !otp.isEmpty()) {
            passwordValidation(password);
            UserEntity userEntity = getUser(userId);
            if (userEntity.getOtp().equalsIgnoreCase(otp)) {
                createUser(userEntity.setPassword(passwordUtilityService.encrypt(password)));
                return "Password Updated Successfully";
            } else {
                throw internalServerError("OTP not matches");
            }
        } else {
            throw internalServerError("Please Provide Password / OTP");
        }
    }

    @Transactional
    public void changePassword(String pkId, String oldPassword, String newPassword) {
        if (independentService.getActiveProfile().equals(SSO_SSP_SEAS)) {
            throw internalServerError("Can't update the password of external user account.");
        }
        UserEntity userEntity = getUser(pkId);
        if (!userEntity.getUserid().equals(userUtilityService.getUserOrRole(TRUE))) {
            throw internalServerError("You can't update others user account password");
        }
        try {
            passwordValidation(newPassword);
            if (oldPassword.equals(passwordUtilityService.decrypt(userEntity.getPassword()))) {
                userEntity.setPassword(passwordUtilityService.encrypt(newPassword));
                createUser(userEntity);
            } else {
                throw internalServerError("Old Password Didn't Match");
            }
        } catch (Exception e) {
            throw internalServerError(e.getMessage());
        }
    }

    @Transactional
    public String sendOTP(String email) {
        if (isNotNull(email)) {
            UserEntity userEntity = getUserByEmail(email);
            String otp = RandomStringUtils.randomNumeric(6);
            createUser(userEntity.setOtp(otp));
            mailService.sendOtpMail(userEntity, otp);
            return userEntity.getUserid();
        } else {
            throw internalServerError("Please Provide Valid Email");
        }
    }

    @Transactional
    public void save(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    public List<CommunityManagerKeyValueModel> getRoles() {
//        List<CommunityManagerKeyValueModel> list = new ArrayList<>();
//        list.add(new CommunityManagerKeyValueModel("super_admin", "Super Admin"));
//        list.add(new CommunityManagerKeyValueModel("admin", "Admin"));
//        list.add(new CommunityManagerKeyValueModel("on_boarder", "On Boarder"));
//        list.add(new CommunityManagerKeyValueModel("business_admin", "Business Admin"));
//        list.add(new CommunityManagerKeyValueModel("business_user", "Business User"));
//        list.add(new CommunityManagerKeyValueModel("data_processor", "Data Processor"));
//        list.add(new CommunityManagerKeyValueModel("data_processor_restricted", "Data Processor Restricted"));
//        return list;
        return roleRepository.findAllByOrderById()
                .stream()
                .map(roleEntity -> new CommunityManagerKeyValueModel(roleEntity.getName(), roleEntity.getNameDes()))
                .collect(Collectors.toList());
    }

    public Optional<List<UserEntity>> findAllByExternalId(String externalId) {
        return userRepository.findAllByExternalId(externalId);
    }

    public Optional<TpUserEntity> getTpUser(String userId) {
        return tpUserRepository.findById(userId);
    }

    private final BiFunction<UserModel, Boolean, UserEntity> mapperToUserEntity = (userModel, isUpdate) -> {

        String password;
        String activationKey;

        if (isUpdate) {
            UserEntity userEntity = getUser(userModel.getUserId());
            password = userEntity.getPassword();
            activationKey = userEntity.getActivationKey();
        } else {
            if (userModel.getUserType().equals(INTERNAL)) {
                password = passwordUtilityService.encrypt(RandomStringUtils.randomAlphanumeric(10));
                activationKey = RandomStringUtils.randomNumeric(20);
            } else {
                password = " ";
                activationKey = null;
            }
        }
        return new UserEntity()
                .setUserid(userModel.getUserId())
                .setPassword(password)
                .setActivationKey(activationKey)
                .setFirstName(userModel.getFirstName())
                .setLastName(userModel.getLastName())
                .setMiddleName(userModel.getMiddleName())
                .setRole(userModel.getUserRole())
                .setStatus(convertBooleanToString(userModel.getStatus()))
                .setEmail(userModel.getEmail())
                .setPhone(userModel.getPhone())
                .setLang(userModel.getLang())
                .setUserType(userModel.getUserType())
                .setExternalId(userModel.getExternalId())
                .setLastUpdatedBy(hasText(userModel.getUpdateBy()) ? userModel.getUpdateBy() : userUtilityService.getUserOrRole(TRUE))
                .setAccountNonLocked(isNotNull(userModel.getAccountNonLocked()) ? convertBooleanToString(userModel.getAccountNonLocked()) : "Y")
                .setIsB2bUser("N");
    };


    private static final Function<UserEntity, UserModel> mapperToUserModel = userEntity ->
            new UserModel()
                    .setUserId(userEntity.getUserid().trim())
                    .setEmail(userEntity.getEmail())
                    .setFirstName(userEntity.getFirstName())
                    .setMiddleName(userEntity.getMiddleName())
                    .setLastName(userEntity.getLastName())
                    .setPhone(userEntity.getPhone())
                    .setUserRole(userEntity.getRole())
                    .setStatus(hasText(userEntity.getStatus()) && convertStringToBoolean(userEntity.getStatus()))
                    .setGroupsList(isNotNull(userEntity.getTpUserEntity().getGroupList()) ? Arrays.asList(userEntity.getTpUserEntity().getGroupList().split(",")) : new ArrayList<>())
                    .setPartnersList(isNotNull(userEntity.getTpUserEntity().getPartnerList()) ? Arrays.asList(userEntity.getTpUserEntity().getPartnerList().split(",")) : new ArrayList<>())
                    .setLang(hasText(userEntity.getLang()) ? userEntity.getLang() : "en")
                    .setUserType(hasText(userEntity.getUserType()) ? userEntity.getUserType() : INTERNAL)
                    .setExternalId(userEntity.getExternalId());

    private static final Function<UserModel, TpUserEntity> mapperToTpUserEntity = userModel ->
            new TpUserEntity()
                    .setUserid(userModel.getUserId())
                    .setGroupList(String.join(",", userModel.getGroupsList()))
                    .setPartnerList(String.join(",", userModel.getPartnersList()));

    private void validation(UserModel userModel) {

        if (!ROLES.contains(userModel.getUserRole())) {
            throw customError(400, "user Role should be one of " + ROLES);
        }

        if (!hasText(userModel.getLang())) {
            userModel.setLang("en");
        }

        List<String> languages = Arrays.asList("en", "fr", "pt", "de", "it", "es");
        if (!languages.contains(userModel.getLang())) {
            throw customError(400, "Please pride the language in the given list" + languages);
        }

        String profile = independentService.getActiveProfile();
        if (profile.equals(SSO_SSP_SEAS)) {
            if (userModel.getUserRole().equals(SUPER_ADMIN)) {
                throw internalServerError("Super Admin user can't be created, please try with other roles.");
            }
            if (!hasText(userModel.getUserType())) {
                throw GlobalExceptionHandler.badRequest(USER_TYPE);
            } else if (!userModel.getUserType().equals(EXTERNAL)) {
                //TODO: soon we should allow
                throw badRequestCustom("userType value should be " + EXTERNAL);
            }
            if (!hasText(userModel.getExternalId())) {
                throw badRequest("externalId");
            }
        } else if (!hasText(userModel.getUserType())) { /*This is for backward compatibility*/
            userModel.setUserType(INTERNAL);
        } else if (!(userModel.getUserType().equals(INTERNAL) || userModel.getUserType().equals(EXTERNAL_SI))) {
            throw badRequestCustom("userType should be " + INTERNAL + " or " + EXTERNAL_SI);
        }
    }

    private void passwordValidation(String password) {

        final String INVALID_FORMAT = "Invalid password format.";
        if (notMatch("((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*\\d))|((?=.*[A-Z])(?=.*[!@#.&$%_\\-\\s]))|((?=.*[a-z])(?=.*\\d))|((?=.*[a-z])(?=.*[!@#.&$%_\\-\\s]))|((?=.*[!@#.&$%_\\-\\s])(?=.*\\d))", password)) {
            throw GlobalExceptionHandler.internalServerError(INVALID_FORMAT);
        }

        if (notMatch("^[^\\s]+(\\s+[^\\s]+)*$", password)) {
            throw GlobalExceptionHandler.internalServerError(INVALID_FORMAT);
        }

        if (password.length() < 15 || password.length() > 50) {
            throw GlobalExceptionHandler.internalServerError(INVALID_FORMAT);
        }

    }

    private boolean notMatch(String regex, String data) {
        return !Pattern.compile(regex).matcher(data).find();
    }
}
