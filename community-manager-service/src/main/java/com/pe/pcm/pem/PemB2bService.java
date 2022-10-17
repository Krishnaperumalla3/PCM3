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

package com.pe.pcm.pem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pe.pcm.b2b.B2BApiService;
import com.pe.pcm.b2b.B2BUserService;
import com.pe.pcm.b2b.B2bCdNodeModel;
import com.pe.pcm.b2b.deserialize.B2bCdNodesDeserializeModel;
import com.pe.pcm.common.CommunityManagerNameModel;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.login.entity.YfsUserEntity;
import com.pe.pcm.partner.PartnerRepository;
import com.pe.pcm.partner.entity.PartnerEntity;
import com.pe.pcm.protocol.as2.si.SciContractRepository;
import com.pe.pcm.protocol.as2.si.SciProfileRepository;
import com.pe.pcm.protocol.remoteftp.RemoteFtpRepository;
import com.pe.pcm.protocol.remoteftp.entity.RemoteFtpEntity;
import com.pe.pcm.protocol.si.SftpProfileService;
import com.pe.pcm.sterling.SterlingFGService;
import com.pe.pcm.sterling.partner.sfg.FgPartGrpMembService;
import com.pe.pcm.sterling.yfs.YfsPersonInfoService;
import com.pe.pcm.sterling.yfs.YfsUserService;
import com.pe.pcm.sterling.yfs.entity.YfsPersonInfoEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;
import static com.pe.pcm.generator.PasswordGenerator.generateValidPassword;
import static com.pe.pcm.utils.CommonFunctions.isNotNull;
import static com.pe.pcm.utils.SIUtility.getEncryptedPassword;

/**
 * @author Chenchu Kiran.
 */
@Service
public class PemB2bService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PemB2bService.class);

    private final SterlingFGService sterlingFGService;
    private final SciProfileRepository sciProfileRepository;
    private final B2BApiService b2BApiService;
    private final YfsUserService yfsUserService;
    private final PartnerRepository partnerRepository;
    private final RemoteFtpRepository remoteFtpRepository;
    private final YfsPersonInfoService yfsPersonInfoService;
    private final SftpProfileService sftpProfileService;
    private final B2BUserService b2BUserService;
    private final SciContractRepository sciContractRepository;
    private final FgPartGrpMembService fgPartGrpMembService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public PemB2bService(SterlingFGService sterlingFGService, SciProfileRepository sciProfileRepository, B2BApiService b2BApiService, YfsUserService yfsUserService, PartnerRepository partnerRepository, RemoteFtpRepository remoteFtpRepository, YfsPersonInfoService yfsPersonInfoService, SftpProfileService sftpProfileService, B2BUserService b2BUserService, SciContractRepository sciContractRepository, FgPartGrpMembService fgPartGrpMembService) {
        this.sterlingFGService = sterlingFGService;
        this.sciProfileRepository = sciProfileRepository;
        this.b2BApiService = b2BApiService;
        this.yfsUserService = yfsUserService;
        this.partnerRepository = partnerRepository;
        this.remoteFtpRepository = remoteFtpRepository;
        this.yfsPersonInfoService = yfsPersonInfoService;
        this.sftpProfileService = sftpProfileService;
        this.b2BUserService = b2BUserService;
        this.sciContractRepository = sciContractRepository;
        this.fgPartGrpMembService = fgPartGrpMembService;
    }

    public List<CommunityManagerNameModel> findUserAccounts(String userName, Boolean isLike) {
        return yfsUserService.findAllByUserName(userName, isLike)
                .stream().map(yfsUserEntity -> new CommunityManagerNameModel(yfsUserEntity.getUsername().trim()))
                .collect(Collectors.toList());
    }

    public List<PemSfgResponseModel> findAllRemoteFtpProfiles(String profileName, Boolean isLike) {
        if (isNotNull(profileName)) {
            List<PemSfgResponseModel> pemSfgResponseModels = findAllRemoteSftpProfileByName(profileName, isLike);
            pemSfgResponseModels.addAll(findAllRemoteFtpAndFtpsProfileByName(profileName, isLike));
            return pemSfgResponseModels;
        } else {
            throw internalServerError("please provide valid data for ProfileName");
        }
    }

    public List<PemSfgResponseModel> findAllRemoteSftpProfileByName(String profileName, Boolean isLike) {

        return isLike ?
                sftpProfileService.findAllByNameContainingIgnoreCase(profileName.trim()).orElse(new ArrayList<>())
                        .stream()
                        .map(sftpProfileEntity -> new PemSfgResponseModel()
                                .setProfileName(sftpProfileEntity.getName())
                                .setUserAccount(sftpProfileEntity.getRemoteUser()))
                        .collect(Collectors.toList()) :
                sftpProfileService.findAllByName(profileName.trim()).orElse(new ArrayList<>())
                        .stream()
                        .map(sftpProfileEntity -> new PemSfgResponseModel()
                                .setProfileName(sftpProfileEntity.getName())
                                .setUserAccount(sftpProfileEntity.getRemoteUser()))
                        .collect(Collectors.toList());
    }

    public List<PemSfgResponseModel> findAllRemoteSftpProfileByNames(List<PemProfileSearchModel> profileModels) {

        return sftpProfileService.findAllRemoteSftpProfileByNames(profileModels)
                .stream()
                .map(sftpProfileEntity -> new PemSfgResponseModel()
                        .setProfileName(sftpProfileEntity.getName())
                        .setUserAccount(sftpProfileEntity.getRemoteUser()))
                .collect(Collectors.toList());
    }

    private List<PemSfgResponseModel> findAllRemoteFtpAndFtpsProfileByName(String profileName, Boolean isLike) {

        return isLike ?
                sciProfileRepository.findAllByObjectNameContainingIgnoreCase(profileName.trim()).orElse(new ArrayList<>())
                        .stream()
                        .map(sciProfile -> new PemSfgResponseModel()
                                .setProfileName(sciProfile.getObjectName().replace("_CONSUMER", "")))
                        .collect(Collectors.toList()) :
                sciProfileRepository.findAllByObjectName(profileName.trim()).orElse(new ArrayList<>())
                        .stream()
                        .map(sciProfile -> new PemSfgResponseModel()
                                .setProfileName(sciProfile.getObjectName().replace("_CONSUMER", "")))
                        .collect(Collectors.toList());
    }

    public CommunityManagerNameModel getRCTbyProvFacts(String provFacts) {
        return new CommunityManagerNameModel(sterlingFGService.getRCTbyProvFacts(provFacts));
    }


    @Transactional
    public void restUserAccountsByB2BiApi(PemAccountExpiryModel pemAccountExpiryModel) {
        b2BUserService.restUserAccountsByB2BiApi(pemAccountExpiryModel, Boolean.TRUE);
    }

    @Transactional
    public void addIdentityToGroup(GroupAndIdentityModel groupAndIdentityModel) {
        fgPartGrpMembService.addIdentityToGroup(groupAndIdentityModel);
    }

    @Transactional
    public List<UserAcctPwdUpdatedModel> restUserAccountsByB2BiApi(List<PemAccountExpiryModel> pemAccountModels) {
        List<UserAcctPwdUpdatedModel> userAcctPwdUpdatedModel = new ArrayList<>();
        UserAcctPwdUpdatedModel userAcctPwdUpdatedModel1 = new UserAcctPwdUpdatedModel();
        List<CommunityManagerNameModel> updatedUserNames = new ArrayList<>();
        List<CommunityManagerNameModel> notFoundUserNames = new ArrayList<>();
        if (!pemAccountModels.isEmpty()) {
            pemAccountModels
                    .stream()
                    .filter(pemAccountExpiryModel -> isNotNull(pemAccountExpiryModel.getUserName()))
                    .forEach(pemAccountExpiryModel -> {
                        try {
                            b2BUserService.restUserAccountsByB2BiApi(pemAccountExpiryModel, Boolean.TRUE);
                            updatedUserNames.add(new CommunityManagerNameModel(pemAccountExpiryModel.getUserName().trim()));
                        } catch (CommunityManagerServiceException e) {
                            if (e.getErrorMessage().contains("API000464")) {
                                notFoundUserNames.add(new CommunityManagerNameModel(pemAccountExpiryModel.getUserName().trim()));
                            } else {
                                throw internalServerError(e.getErrorMessage());
                            }

                        }
                    });
            userAcctPwdUpdatedModel1.setPasswordUpdatedUsers(updatedUserNames);
            userAcctPwdUpdatedModel1.setUsersNotFound(notFoundUserNames);
            userAcctPwdUpdatedModel.add(userAcctPwdUpdatedModel1);
        }
        return userAcctPwdUpdatedModel;
    }


    @Transactional
    public List<UserAcctPwdUpdatedModel> restUserAccounts(List<PemAccountExpiryModel> pemAccountModels) {
        List<UserAcctPwdUpdatedModel> userAcctPwdUpdatedModel = new ArrayList<>();
        UserAcctPwdUpdatedModel userAcctPwdUpdatedModel1 = new UserAcctPwdUpdatedModel();
        List<CommunityManagerNameModel> updatedUserNames = new ArrayList<>();
        List<CommunityManagerNameModel> notFoundUserNames = new ArrayList<>();
        //Checking and Collecting All the use accounts from SI
        if (!pemAccountModels.isEmpty()) {
            pemAccountModels
                    .stream()
                    .filter(pemAccountExpiryModel -> isNotNull(pemAccountExpiryModel.getUserName()))
                    .forEach(pemAccountExpiryModel -> {
                        AtomicBoolean userNotfound = new AtomicBoolean(true);
                        yfsUserService.getOnlyUser(pemAccountExpiryModel.getUserName().trim()).ifPresent(yfsUserEntity -> {
                            YfsUserEntity yfsUserEntity1 = new YfsUserEntity();
                            BeanUtils.copyProperties(yfsUserEntity, yfsUserEntity1);
                            String password;
                            if (isNotNull(pemAccountExpiryModel.getPass())) {
                                password = pemAccountExpiryModel.getPass();
                            } else {
                                password = generateValidPassword();
                            }

                            yfsUserEntity1.setPwdlastchangedon(new Timestamp(new Date().getTime()))
                                    .setPassword(getEncryptedPassword(password, yfsUserEntity.getSalt()))
                                    .setUserKey(yfsUserEntity.getUserKey());
                            yfsUserService.save(yfsUserEntity1);
                            updatedUserNames.add(new CommunityManagerNameModel(pemAccountExpiryModel.getUserName().trim()));

                            //Update Yfs Person Info Entity
                            if (isNotNull(yfsUserEntity.getBillingaddressKey())) {
                                yfsPersonInfoService.findById(yfsUserEntity.getBillingaddressKey()).ifPresent(yfsPersonInfoEntity -> {
                                    YfsPersonInfoEntity yfsPersonInfoEntity1 = new YfsPersonInfoEntity();
                                    BeanUtils.copyProperties(yfsPersonInfoEntity, yfsPersonInfoEntity1);
                                    yfsPersonInfoEntity1.setModifyts(new java.sql.Date(new Date().getTime()));
                                    yfsPersonInfoEntity1.setModifyuserid("CM-API");
                                    yfsPersonInfoService.save(yfsPersonInfoEntity1);
                                    LOGGER.info("YfsPersonInfoEntity updated successfully!");
                                });
                            } else {
                                LOGGER.info("YfsPersonInfoEntity not mapped, so we are not updating YfsPersonInfoEntity");
                            }
                            LOGGER.info("yfsUserEntity updated successfully!");
                            userNotfound.set(false);
                        });
                        if (userNotfound.get()) {
                            notFoundUserNames.add(new CommunityManagerNameModel(pemAccountExpiryModel.getUserName().trim()));
                        }

                    });
            userAcctPwdUpdatedModel1.setPasswordUpdatedUsers(updatedUserNames);
            userAcctPwdUpdatedModel1.setUsersNotFound(notFoundUserNames);
            userAcctPwdUpdatedModel.add(userAcctPwdUpdatedModel1);
        } else {
            throw internalServerError("At least provide one user for Password Reset");
        }
        return userAcctPwdUpdatedModel;
    }

    @Transactional
    public List<PemAccountExpiryModel> restUserAccountsDup(List<PemAccountExpiryModelTemp> pemAccountExpiryModelTemp) {
        List<PemAccountExpiryModel> pemAccountExpiryModels = new ArrayList<>();
        List<PartnerEntity> partnerEntities = new ArrayList<>();
        pemAccountExpiryModelTemp.forEach(pemAccountExpiryModelTemp1 ->
                pemAccountExpiryModelTemp1.getProfileName().forEach(profileName -> partnerEntities.addAll(partnerRepository.findAllByTpNameContainingIgnoreCaseOrderByTpName(profileName).orElse(new ArrayList<>())))
        );
        List<String> uniqueUsersList = remoteFtpRepository.findBySubscriberIdIn(partnerEntities.stream()
                        .map(PartnerEntity::getPkId).collect(Collectors.toList()))
                .orElse(new ArrayList<>()).stream().map(RemoteFtpEntity::getUserId)
                .filter(Objects::nonNull).distinct().collect(Collectors.toList());
        int partitionSize = 999;
        List<List<String>> partitions = new LinkedList<>();
        for (int i = 0; i < uniqueUsersList.size(); i += partitionSize) {
            partitions.add(uniqueUsersList.subList(i,
                    Math.min(i + partitionSize, uniqueUsersList.size())));
        }
        //TODO This logic should be reviewed by Kiran
        partitions.forEach(usersList -> yfsUserService.findAllByUsernameIn(usersList).orElse(new ArrayList<>()).forEach(yfsUserEntity ->
                usersList.forEach(s -> pemAccountExpiryModelTemp.forEach(pemAccountExpiryModelTemp1 -> {
                    if (s.equalsIgnoreCase(yfsUserEntity.getUsername())) {
                        try {
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            simpleDateFormat.setLenient(false);
                            Date pwdLastChangedOn = simpleDateFormat.parse(yfsUserEntity.getPwdlastchangedon().toString());
                            Date startDate = simpleDateFormat.parse(pemAccountExpiryModelTemp1.getStartDate());
                            Date endDate = simpleDateFormat.parse(pemAccountExpiryModelTemp1.getEndDate());
                            if (!(startDate.compareTo(pwdLastChangedOn) * pwdLastChangedOn.compareTo(endDate) >= 0)) {
                                String password = generateValidPassword();
                                yfsUserService.getOnlyUser(yfsUserEntity.getUsername().trim()).ifPresent(yfsUserEntity1 -> {
                                    YfsUserEntity yfsUserEntityDummy = new YfsUserEntity();
                                    BeanUtils.copyProperties(yfsUserEntity1, yfsUserEntityDummy);
                                    yfsUserEntityDummy.setPwdlastchangedon(new Timestamp(new Date().getTime()))
                                            .setPassword(getEncryptedPassword(password, yfsUserEntity1.getSalt()))
                                            .setUserKey(yfsUserEntity1.getUserKey());
                                    yfsUserService.save(yfsUserEntityDummy);
                                    pemAccountExpiryModels.add(new PemAccountExpiryModel()
                                            .setUserName(yfsUserEntity.getUsername()));
                                });
                            }
                        } catch (Exception ex) {
                            throw internalServerError(ex.getMessage());
                        }
                    }
                }))));
        return pemAccountExpiryModels;
    }


    public List<B2bCdNodesDeserializeModel> getCdNodes(B2bCdNodeModel b2bCdNodeModel) {
        String cdNodesString = b2BApiService.getCdNode(b2bCdNodeModel);
        try {
            return Arrays.asList(objectMapper.readValue(cdNodesString, B2bCdNodesDeserializeModel[].class));
        } catch (Exception e) {
            throw internalServerError(e.getMessage());
        }

    }

    @Transactional
    public void updateContract(PemUpdateContractModel pemUpdateContractModel) {
        sciContractRepository.findByObjectName(pemUpdateContractModel.getContactName()).ifPresent(SciContractEntity -> sciContractRepository.save(SciContractEntity.setWorkflowName(pemUpdateContractModel.getBpName())));
    }

}
