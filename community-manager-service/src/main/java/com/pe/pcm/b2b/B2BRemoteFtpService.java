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

package com.pe.pcm.b2b;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pe.pcm.b2b.protocol.RemoteFtpConfiguration;
import com.pe.pcm.b2b.protocol.RemoteFtpProfile;
import com.pe.pcm.b2b.protocol.RemoteFtpsConfiguration;
import com.pe.pcm.b2b.protocol.RemoteSftpProfile;
import com.pe.pcm.b2b.usermailbox.RemoteUserInfoModel;
import com.pe.pcm.common.CommunityManagerNameModel;
import com.pe.pcm.enums.Protocol;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.login.YfsUserRepository;
import com.pe.pcm.login.entity.YfsUserEntity;
import com.pe.pcm.pem.b2bproxy.consumerconfiguration.ConsumerSshConfiguration;
import com.pe.pcm.pem.b2bproxy.producerconfiguration.ProducerFtpConfiguration;
import com.pe.pcm.pem.b2bproxy.producerconfiguration.ProducerFtpsConfiguration;
import com.pe.pcm.pem.b2bproxy.producerconfiguration.ProducerSshConfiguration;
import com.pe.pcm.protocol.RemoteProfileModel;
import com.pe.pcm.protocol.as2.si.SciProfileRepository;
import com.pe.pcm.protocol.as2.si.SftpProfileRepository;
import com.pe.pcm.protocol.as2.si.entity.YfsOrganizationDupEntity;
import com.pe.pcm.protocol.si.SciTransportServiceDup;
import com.pe.pcm.protocol.si.SftpProfileService;
import com.pe.pcm.protocol.si.YfsOrganizationServiceDup;
import com.pe.pcm.sterling.RemoteMailBoxService;
import com.pe.pcm.sterling.SterlingAuthUserXrefSshService;
import com.pe.pcm.sterling.partner.SciCodeUserXrefService;
import com.pe.pcm.sterling.security.SterlingDBPasswordService;
import com.pe.pcm.sterling.yfs.YfsPersonInfoRepository;
import com.pe.pcm.sterling.yfs.YfsResourcePermissionRepository;
import com.pe.pcm.sterling.yfs.YfsResourcePermissionService;
import com.pe.pcm.sterling.yfs.entity.YfsResourcePermissionEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.pe.pcm.b2b.B2BFunctions.*;
import static com.pe.pcm.enums.Protocol.SFG_FTP;
import static com.pe.pcm.utils.CommonFunctions.*;
import static com.pe.pcm.utils.PCMConstants.PRAGMA_EDGE_S;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * @author Chenchu Kiran.
 */
@Service
public class B2BRemoteFtpService {

    private final SftpProfileRepository sftpProfileRepository;
    private final SciProfileRepository sciProfileRepository;
    private final B2BApiService b2BApiService;
    private final String community;
    private final RemoteMailBoxService remoteMailBoxService;
    private final YfsPersonInfoRepository yfsPersonInfoRepository;
    private final YfsUserRepository yfsUserRepository;
    private final SterlingAuthUserXrefSshService sterlingAuthUserXrefSshService;
    private final YfsResourcePermissionRepository yfsResourcePermissionRepository;
    private final B2BRoutingRuleService b2BRoutingRuleService;
    private final SterlingDBPasswordService sterlingDBPasswordService;
    private final SciTransportServiceDup sciTransportServiceDup;
    private final SftpProfileService sftpProfileService;
    private final YfsResourcePermissionService yfsResourcePermissionService;
    private final SciCodeUserXrefService sciCodeUserXrefService;
    private final YfsOrganizationServiceDup yfsOrganizationServiceDup;

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public B2BRemoteFtpService(SftpProfileRepository sftpProfileRepository, SciProfileRepository sciProfileRepository,
                               B2BApiService b2BApiService, @Value("${sterling-b2bi.b2bi-api.b2bi-sfg-api.community-name}") String community,
                               RemoteMailBoxService remoteMailBoxService, YfsPersonInfoRepository yfsPersonInfoRepository, YfsUserRepository yfsUserRepository,
                               SterlingAuthUserXrefSshService sterlingAuthUserXrefSshService, YfsResourcePermissionRepository yfsResourcePermissionRepository,
                               B2BRoutingRuleService b2BRoutingRuleService, SterlingDBPasswordService sterlingDBPasswordService, SciTransportServiceDup sciTransportServiceDup,
                               SftpProfileService sftpProfileService, YfsResourcePermissionService yfsResourcePermissionService, SciCodeUserXrefService sciCodeUserXrefService,
                               YfsOrganizationServiceDup yfsOrganizationServiceDup) {
        this.sftpProfileRepository = sftpProfileRepository;
        this.sciProfileRepository = sciProfileRepository;
        this.b2BApiService = b2BApiService;
        this.community = community;
        this.remoteMailBoxService = remoteMailBoxService;
        this.yfsPersonInfoRepository = yfsPersonInfoRepository;
        this.yfsUserRepository = yfsUserRepository;
        this.sterlingAuthUserXrefSshService = sterlingAuthUserXrefSshService;
        this.yfsResourcePermissionRepository = yfsResourcePermissionRepository;
        this.b2BRoutingRuleService = b2BRoutingRuleService;
        this.sterlingDBPasswordService = sterlingDBPasswordService;
        this.sciTransportServiceDup = sciTransportServiceDup;
        this.sftpProfileService = sftpProfileService;
        this.yfsResourcePermissionService = yfsResourcePermissionService;
        this.sciCodeUserXrefService = sciCodeUserXrefService;
        this.yfsOrganizationServiceDup = yfsOrganizationServiceDup;
    }

    public String saveRemoteFtpProfile(RemoteProfileModel remoteProfileModel, Boolean isUpdate, String subscriberType, String oldProfileName) {
        isNullThrowError.apply(remoteProfileModel.getPoolingInterval(), "Polling Interval");
        if (remoteProfileModel.getPoolingInterval().equals("ON") && remoteProfileModel.getHubInfo()) {
            remoteProfileModel.setCreateDirectoryInSI(TRUE);
        }
        StringBuilder profileId = new StringBuilder();
        Protocol protocol = Optional.ofNullable(Protocol.findProtocol(remoteProfileModel.getProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);
        remoteProfileModel.setSubscriberType(subscriberType);
        if (!remoteProfileModel.isOnlyPCM()) {
            switch (protocol) {
                case SFG_FTP:
                case SFG_FTPS:
                    if (!remoteProfileModel.getHubInfo()) {
                        RemoteFtpProfile remoteFtpProfile = new RemoteFtpProfile(remoteProfileModel, isUpdate, community);
                        if (protocol.equals(SFG_FTP)) {
                            if (remoteFtpProfile.getIsInitiatingConsumer() || remoteFtpProfile.getIsListeningConsumer()) {
                                remoteFtpProfile.setConsumerFtpConfiguration(new RemoteFtpConfiguration(remoteProfileModel, isUpdate));
                            }
                            if (remoteFtpProfile.getIsInitiatingProducer() || remoteFtpProfile.getIsListeningProducer()) {
                                remoteFtpProfile.setProducerFtpConfiguration(new ProducerFtpConfiguration(remoteProfileModel, isUpdate));
                            }
                        } else {
                            if (remoteFtpProfile.getIsInitiatingConsumer() || remoteFtpProfile.getIsListeningConsumer()) {
                                remoteFtpProfile.setConsumerFtpsConfiguration(new RemoteFtpsConfiguration(remoteProfileModel, isUpdate));
                            }
                            if (remoteFtpProfile.getIsInitiatingProducer() || remoteFtpProfile.getIsListeningProducer()) {
                                remoteFtpProfile.setProducerFtpsConfiguration(new ProducerFtpsConfiguration(remoteProfileModel, isUpdate));
                            }
                        }
                        if (isUpdate) {
                            b2BApiService.updateRemoteFtpProfile(remoteFtpProfile, oldProfileName);
                        } else {
                            b2BApiService.createRemoteFtpProfile(remoteFtpProfile);
                        }
                    } else {
                        remoteProfileModel.setCreateUserInSI(remoteProfileModel.getCreateUserInSI() ? remoteProfileModel.getCreateUserInSI() : isAutUserKeysEmpty(remoteProfileModel.getAuthorizedUserKeys()));
                        remoteProfileModel.setCreateDirectoryInSI(remoteProfileModel.getCreateUserInSI() ? remoteProfileModel.getCreateUserInSI() : remoteProfileModel.getCreateDirectoryInSI());
                    }
                    break;
                case SFG_SFTP:
                    if (!remoteProfileModel.getHubInfo()) {
                        RemoteSftpProfile remoteSftpProfile = new RemoteSftpProfile(remoteProfileModel, isUpdate);
                        if (isUpdate) {
                            b2BApiService.updateRemoteSftpProfile(remoteSftpProfile, oldProfileName);
                        } else {
                            b2BApiService.createRemoteSftpProfile(remoteSftpProfile);
                        }
                        //Profile creation in SI Requested By ALOK.
                        if (isNotNull(remoteProfileModel.getIsSIProfile())) {
                            RemoteFtpProfile remoteFtpProfile = new RemoteFtpProfile(remoteProfileModel, isUpdate, community);
                            if (remoteFtpProfile.getIsInitiatingConsumer() || remoteFtpProfile.getIsListeningConsumer()) {
                                remoteFtpProfile.setConsumerSshConfiguration(new ConsumerSshConfiguration(remoteProfileModel.getCustomProfileName()));
                            }
                            if (remoteFtpProfile.getIsInitiatingProducer() || remoteFtpProfile.getIsListeningProducer()) {
                                remoteFtpProfile.setProducerSshConfiguration(new ProducerSshConfiguration(remoteProfileModel.getCustomProfileName()));
                            }
                            if (isUpdate) {
                                b2BApiService.updateRemoteFtpProfile(remoteFtpProfile,remoteFtpProfile.getPartnerName());
                            } else {
                                b2BApiService.createRemoteFtpProfile(remoteFtpProfile);
                            }
                        }
                        //END
                    } else {
                        remoteProfileModel.setCreateUserInSI(remoteProfileModel.getCreateUserInSI() ? remoteProfileModel.getCreateUserInSI() : isAutUserKeysEmpty(remoteProfileModel.getAuthorizedUserKeys()));
                        remoteProfileModel.setCreateDirectoryInSI(remoteProfileModel.getCreateUserInSI() ? remoteProfileModel.getCreateUserInSI() : remoteProfileModel.getCreateDirectoryInSI());
                    }
                    break;
                default:
                    // No Implementation Needed
            }
            //onBoardProfileToSI(remoteProfileModel, isUpdate, oldProfileName, profileId)
            b2BApiService.createMailBoxInSI(remoteProfileModel.getHubInfo() ? remoteProfileModel.getCreateDirectoryInSI() : FALSE, remoteProfileModel.getInDirectory(), remoteProfileModel.getOutDirectory());
            if (remoteProfileModel.getHubInfo() && remoteProfileModel.getPoolingInterval().equals("ON")) {
                b2BRoutingRuleService.save(remoteProfileModel.getProfileName(), remoteProfileModel.getInDirectory(), oldProfileName);
            }

            String emailIds = isNotNull(remoteProfileModel.getSecondaryMail()) ? remoteProfileModel.getSecondaryMail() : remoteProfileModel.getEmailId();

            /*We are getting and storing here to restore the child user user account permissions after we update/create the parent account*/
            Map<String, List<YfsResourcePermissionEntity>> yfsResourcePermissionEntityMap = new LinkedHashMap<>();
            if (remoteProfileModel.getHubInfo() && remoteProfileModel.isResetPermissions() && isNotNull(remoteProfileModel.getUserIdentity())) {
                final List<String> masterAccounts;
                List<String> orgProfileObjectIds = yfsOrganizationServiceDup.findAllByOrganizationName(remoteProfileModel.getUserIdentity())
                        .stream()
                        .map(YfsOrganizationDupEntity::getObjectId)
                        .collect(Collectors.toList());

                if (!orgProfileObjectIds.isEmpty()) {
                    masterAccounts = sciCodeUserXrefService.findAllByTpObjectIdIn(orgProfileObjectIds)
                            .stream()
                            .map(sciCodeUserXrefEntity -> sciCodeUserXrefEntity.getSciCodeUserXrefIdentity().getUserId())
                            .collect(Collectors.toList());
                } else {
                    masterAccounts = new ArrayList<>();
                }

                yfsUserRepository.findAllByOrganizationKey(remoteProfileModel.getUserIdentity())
                        .forEach(yfsUserEntity -> {
                                    if (!masterAccounts.contains(yfsUserEntity.getUsername().trim())) {
                                        yfsResourcePermissionEntityMap.put(
                                                yfsUserEntity.getUsername().trim(),
                                                yfsResourcePermissionRepository.findAllByUserKey(yfsUserEntity.getUserKey()).orElse(new ArrayList<>())
                                                        .stream()
                                                        .map(yfsResourcePermissionEntity -> {
                                                            YfsResourcePermissionEntity ysfEntity = new YfsResourcePermissionEntity();
                                                            BeanUtils.copyProperties(yfsResourcePermissionEntity, ysfEntity);
                                                            return ysfEntity.setResourcePermissionKey(yfsResourcePermissionEntity.getResourcePermissionKey());
                                                        }).collect(Collectors.toList())
                                        );
                                    }
                                }
                        );
            }
            String profileName = remoteProfileModel.getProfileName().length() > 60 ? remoteProfileModel.getProfileName().substring(0, 60) : remoteProfileModel.getProfileName();
            RemoteUserInfoModel remoteUserInfoModel =
                    mapper.convertValue(remoteProfileModel, RemoteUserInfoModel.class)
                            .setPolicy(remoteProfileModel.getPwdPolicy())
                            .setEmail(emailIds.contains(",") ? "" : emailIds)
                            .setGivenName(isNotNull(remoteProfileModel.getGivenName()) ? remoteProfileModel.getGivenName() : profileName)
                            .setSurname(isNotNull(remoteProfileModel.getSurname()) ? remoteProfileModel.getSurname() : profileName)
                            .setUserName(isNotNull(remoteProfileModel.getProfileUserName()) ? remoteProfileModel.getProfileUserName() : remoteProfileModel.getUserName())
                            .setPassword(isNotNull(remoteProfileModel.getProfileUserPassword()) ? remoteProfileModel.getProfileUserPassword() : remoteProfileModel.getPassword());
            if (isUpdate) {
                if (remoteProfileModel.isMergeUser()) {
                    b2BApiService.mergeUserAndUpdate(remoteProfileModel.getHubInfo() ? TRUE : FALSE, remoteUserInfoModel);
                } else {
                    b2BApiService.updateUserInSI(remoteProfileModel.getHubInfo() ? TRUE : FALSE, remoteUserInfoModel);
                }
                //TODO
            } else {
                if (remoteProfileModel.isMergeUser()) {
                    b2BApiService.mergeUserAndUpdate(remoteProfileModel.getHubInfo() ? TRUE : FALSE, remoteUserInfoModel);
                    //TODO
                } else {
                    b2BApiService.createUserInSI(remoteProfileModel.getHubInfo() ? TRUE : FALSE, remoteUserInfoModel);
                }
            }

            if (remoteProfileModel.getHubInfo()) {
                YfsUserEntity yfsUserEntity1 = yfsUserRepository.findFirstByUsername(remoteProfileModel.getUserName())
                        .orElseThrow(() -> GlobalExceptionHandler.internalServerError("User is not available in SI table , User : " + remoteProfileModel.getUserName()));
                if (emailIds.contains(",")) {
                    yfsPersonInfoRepository.updateEmailIdById(yfsUserEntity1.getBillingaddressKey(), emailIds);
                }

                /*Saving the old Permissions for child accounts after we create/update the user parent account*/
                if (remoteProfileModel.isResetPermissions() && isNotNull(remoteProfileModel.getUserIdentity())) {

                    List<YfsUserEntity> yfsUserEntities = yfsUserRepository.findAllByUsernameIn(new ArrayList<>(yfsResourcePermissionEntityMap.keySet())).orElse(new ArrayList<>());
                    yfsUserEntities.forEach(yfsUserEntity -> {
                        List<YfsResourcePermissionEntity> yfsResourcePermissionEntities = new ArrayList<>();
                        yfsResourcePermissionEntityMap.get(yfsUserEntity.getUsername()).forEach(yfsResourcePermissionEntity ->
                                yfsResourcePermissionEntities.add(yfsResourcePermissionEntity.setUserKey(yfsUserEntity.getUserKey()))
                        );
                        if (!yfsResourcePermissionEntities.isEmpty()) {
                            //TODO yfsResourcePermissionRepository.deleteAllByUserKey(yfsUserEntity.getUserKey()); //This is newly Added , this should be enabled for EFX
                            yfsResourcePermissionRepository.saveAll(yfsResourcePermissionEntities);
                        }
                    });
                    //createUpdatePermissions(remoteProfileModel, yfsUserEntity1.getUserKey(), remoteProfileModel.isMergeUser()); // TODO his should be enabled for EFX
                }
            }

        }
        switch (protocol) {
            case SFG_FTP:
            case SFG_FTPS:
                String profileName = (isNotNull(remoteProfileModel.getCustomProfileName()) ? remoteProfileModel.getCustomProfileName() : remoteProfileModel.getProfileName()) + "_CONSUMER";
                if (isNotNull(remoteProfileModel.getPassword())) {
                    if (isUpdate) {
                        if (!remoteProfileModel.getPassword().equalsIgnoreCase(PRAGMA_EDGE_S)) {
                            sciTransportServiceDup.get(profileName).ifPresent(sciTransportEntity -> sciTransportEntity.setTranspActPwd(sterlingDBPasswordService.encrypt(remoteProfileModel.getPassword())));
                        }
                    }else {
                        sciTransportServiceDup.get(profileName).ifPresent(sciTransportEntity -> sciTransportEntity.setTranspActPwd(sterlingDBPasswordService.encrypt(remoteProfileModel.getPassword())));
                    }
                }
                sciProfileRepository.findFirstByObjectName(profileName)
                        .ifPresent(sciProfile -> profileId.append(sciProfile.getObjectId()));
                break;
            case SFG_SFTP:
                sftpProfileRepository.findFirstByName(isNotNull(remoteProfileModel.getCustomProfileName()) ? remoteProfileModel.getCustomProfileName() : remoteProfileModel.getProfileName())
                        .ifPresent(siRemoteSftpProfileEntity -> {
                            //TODO
                            profileId.append(siRemoteSftpProfileEntity.getProfileId());
                        });
                break;
            default:
                // No Implementation Needed
        }

        return profileId.toString();
    }

    private void createUpdatePermissions(RemoteProfileModel remoteProfileModel, String userKey, boolean mergeUser) {

        if (remoteProfileModel.getCreateDirectoryInSI() /*|| remoteProfileModel.getPoolingInterval().equals("ON")*/) {

            Set<String> permissionsSet = new LinkedHashSet<>();
            AtomicReference<String> atomicReference = new AtomicReference<>("");
            if (isNotNull(remoteProfileModel.getInDirectory())) {
                getRootDirectories(remoteProfileModel.getInDirectory(), permissionsSet, atomicReference, true);
            }
            if (isNotNull(remoteProfileModel.getOutDirectory())) {
                atomicReference.set("");
                getRootDirectories(remoteProfileModel.getOutDirectory(), permissionsSet, atomicReference, true);
            }
            if (!permissionsSet.isEmpty()) {
                yfsResourcePermissionRepository.deleteAllByUserKey(userKey);
                permissionsSet.add("MyAccount");
                yfsResourcePermissionService.save(userKey, new ArrayList<>(permissionsSet), mergeUser);
            }
        }
    }

    public void deleteRemoteFtpProfile(String profileName) {
        b2BApiService.deleteRemoteFtpProfile(profileName);
    }

    public void deleteRemoteSftpProfile(String profileName) {
        b2BApiService.deleteRemoteSftpProfile(profileName);
    }

    public RemoteProfileModel getRemoteFtpProfile(RemoteProfileModel remoteProfileModel) {
        return mapperJsonToRemoteFtpModel
                .apply(remoteProfileModel, b2BApiService.getRemoteFtpProfile(isNotNull(remoteProfileModel.getCustomProfileName()) ?
                        remoteProfileModel.getCustomProfileName() : remoteProfileModel.getProfileName()));
    }

    public RemoteProfileModel getRemoteSftpProfile(RemoteProfileModel remoteProfileModel) {
        return mapperJsonToRemoteSftpModel.apply(false, remoteProfileModel, b2BApiService.getRemoteSftpProfile(isNotNull(remoteProfileModel.getCustomProfileName()) ?
                remoteProfileModel.getCustomProfileName() : remoteProfileModel.getProfileName()));
    }

    public void setB2bUserParams(RemoteProfileModel remoteProfileModel) {
        assignUserJsonStringToRemoteFtpModel.apply(remoteProfileModel, b2BApiService.getUserFromSI(remoteProfileModel.getUserName()));
        if (remoteProfileModel.getAuthorizedUserKeys().isEmpty()) {
            remoteProfileModel.setAuthorizedUserKeys(sterlingAuthUserXrefSshService.findAllByUser(remoteProfileModel.getUserName()));
        }
    }

    public void deleteRemoteUser(String user) {
        b2BApiService.deleteUserInSI(user);
    }

    public void deleteRemoteMailBox(String path) {
        remoteMailBoxService.findByMailboxPath(path).ifPresent(mbxMailBoxEntity -> b2BApiService.deleteMailBoxInSI(String.valueOf(mbxMailBoxEntity.getMailboxId())));
    }

    public void deleteSshKnowHostKeyInSI(String knownHostKey) {
        b2BApiService.deleteSshKnowHostKeyInSI(knownHostKey);
    }

    public void deleteUserIdentityKey(String userIdentityKey) {
        b2BApiService.deleteUserIdentityKey(userIdentityKey);
    }

    public void deleteCertificateId(String certificateId) {
        b2BApiService.deleteCaCertificate(certificateId);
    }

    private boolean isAutUserKeysEmpty(List<CommunityManagerNameModel> authKeys) {
        return authKeys.isEmpty() ? TRUE : FALSE;
    }

}
