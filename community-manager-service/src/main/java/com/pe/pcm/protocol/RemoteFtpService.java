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

package com.pe.pcm.protocol;

import com.pe.pcm.b2b.B2BRemoteFtpService;
import com.pe.pcm.certificate.CaCertInfoService;
import com.pe.pcm.certificate.SshKHostKeyService;
import com.pe.pcm.certificate.SshKeyPairService;
import com.pe.pcm.common.CommunityManagerNameModel;
import com.pe.pcm.enums.Protocol;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.miscellaneous.CertificateUserUtilityService;
import com.pe.pcm.miscellaneous.PasswordUtilityService;
import com.pe.pcm.protocol.remoteftp.RemoteFtpRepository;
import com.pe.pcm.protocol.remoteftp.entity.RemoteFtpEntity;
import com.pe.pcm.utils.CommonFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.pe.pcm.enums.Protocol.AS2;
import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;
import static com.pe.pcm.protocol.function.ProtocolFunctions.mapperToRemoteFtpEntity;
import static com.pe.pcm.utils.CommonFunctions.isNotNull;

/**
 * @author Chenchu Kiran.
 */

@Service
public class RemoteFtpService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RemoteFtpService.class);

    private final RemoteFtpRepository remoteFtpRepository;
    private final B2BRemoteFtpService b2BRemoteFtpService;
    private final CaCertInfoService caCertInfoService;
    private final SshKHostKeyService sshKHostKeyService;
    private final SshKeyPairService sshKeyPairService;
    private final CertificateUserUtilityService certificateUserUtilityService;
    private final PasswordUtilityService passwordUtilityService;


    @Autowired
    public RemoteFtpService(RemoteFtpRepository remoteFtpRepository, B2BRemoteFtpService b2BRemoteFtpService, CaCertInfoService caCertInfoService, SshKHostKeyService sshKHostKeyService, SshKeyPairService sshKeyPairService, @Lazy CertificateUserUtilityService certificateUserUtilityService, PasswordUtilityService passwordUtilityService) {
        this.remoteFtpRepository = remoteFtpRepository;
        this.b2BRemoteFtpService = b2BRemoteFtpService;
        this.caCertInfoService = caCertInfoService;
        this.sshKHostKeyService = sshKHostKeyService;
        this.sshKeyPairService = sshKeyPairService;
        this.certificateUserUtilityService = certificateUserUtilityService;
        this.passwordUtilityService = passwordUtilityService;
    }


    public RemoteFtpEntity saveProtocol(RemoteProfileModel remoteProfileModel, String parentPrimaryKey, String childPrimaryKey, Boolean isUpdate, String subscriberType, String oldProfileName) {
        //Check the default user update condition
        if (isNotNull(remoteProfileModel.getUserName())) {
            CommonFunctions.checkDefaultUsers(remoteProfileModel.getUserName());
        }

        if (remoteProfileModel.getHubInfo()) {
            remoteProfileModel.setPassword(passwordUtilityService.getDecryptedValue(remoteProfileModel.getPassword()));
        }

        RemoteFtpEntity remoteFtpEntity = mapperToRemoteFtpEntity.apply(remoteProfileModel);
        remoteFtpEntity.setPkId(childPrimaryKey)
                .setSubscriberType(subscriberType)
                .setSubscriberId(parentPrimaryKey)
                .setCertificateId(
                        (isNotNull(remoteProfileModel.getCaCertificateNames()) && !remoteProfileModel.getCaCertificateNames().isEmpty())
                                ? remoteProfileModel.getCaCertificateNames().stream().map(CommunityManagerNameModel::getName).collect(Collectors.joining(","))
                                : remoteProfileModel.getCertificateId())
                .setCaCertId(remoteProfileModel.getProtocol().equals("SFGFTPS") ? caCertInfoService.findByNameNotThrow(remoteProfileModel.getCertificateId()).getObjectId() : "")
                .setUserIdentityKeyId(remoteProfileModel.getProtocol().equals("SFGSFTP") ? sshKeyPairService.findByNameNoThrow(remoteProfileModel.getUserIdentityKey()).getObjectId() : "")
                .setKnownHostKeyId(remoteProfileModel.getProtocol().equals("SFGSFTP") ? sshKHostKeyService.findByNameNoThrow(remoteProfileModel.getKnownHostKey()).getObjectId() : "")
                .setAuthUserkeyId(remoteProfileModel.getProtocol().equals("SFGSFTP") ? remoteProfileModel.getHubInfo() ? !remoteProfileModel.getPreferredAuthenticationType().equalsIgnoreCase("Password") ? certificateUserUtilityService.getSshUserKeyListOfIds(remoteProfileModel.getAuthorizedUserKeys()).stream().map(CommunityManagerNameModel::getName).collect(Collectors.joining(",")) : "" : "" : "")
                .setProfileId(saveRemoteFtpProfile(remoteProfileModel, isUpdate, subscriberType, oldProfileName))
                .setPassword(passwordUtilityService.getEncryptedValue(remoteProfileModel.getPassword()));
        return save(remoteFtpEntity);
    }

    public String saveRemoteFtpProfile(RemoteProfileModel remoteProfileModel, Boolean isUpdate, String subscriberType, String oldProfileName) {
        return b2BRemoteFtpService.saveRemoteFtpProfile(remoteProfileModel, isUpdate, subscriberType, oldProfileName);
    }

    public void deleteProtocol(String pkId, String profileName, String profileProtocol, Boolean deleteInSI) {
        try {
            if (deleteInSI) {
                Protocol protocol = Optional.ofNullable(Protocol.findProtocol(profileProtocol)).orElse(AS2);
                switch (protocol) {
                    case SFG_FTP:
                    case SFG_FTPS:
                        b2BRemoteFtpService.deleteRemoteFtpProfile(profileName);
                        LOGGER.info("SFGFTP/SFGFTPS profile deleted successfully");
                        break;
                    case SFG_SFTP:
                        try {
                            b2BRemoteFtpService.deleteRemoteFtpProfile(profileName);
                        } catch (Exception e) {
                            //No need to Do any operations
                        }
                        b2BRemoteFtpService.deleteRemoteSftpProfile(profileName);
                        LOGGER.info("SFGSFTP profile deleted successfully");
                        break;
                    default:
                        // No Implementation Needed
                }
            } else {
                LOGGER.info("Delete Profile in SFG/SI is FALSE");
            }
        } catch (Exception e) {
            //We can Ignore this
        }
        delete(pkId);
    }

    public RemoteFtpEntity findFirstByInMailboxOrOutMailbox(String inMailbox, String outMailbox) {
        return remoteFtpRepository.findFirstByInDirectoryOrOutDirectory(inMailbox, outMailbox).orElse(new RemoteFtpEntity());
    }

    public RemoteProfileModel getRemoteFtp(RemoteProfileModel remoteProfileModel) {
        Protocol protocol = Optional.ofNullable(Protocol.findProtocol(remoteProfileModel.getProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);
        switch (protocol) {
            case SFG_FTP:
            case SFG_FTPS:
                return b2BRemoteFtpService.getRemoteFtpProfile(remoteProfileModel);
            case SFG_SFTP:
                return b2BRemoteFtpService.getRemoteSftpProfile(remoteProfileModel);
            default:
                // No Implementation Needed
        }
        return remoteProfileModel;
    }

    public RemoteFtpEntity save(RemoteFtpEntity remoteFtpEntity) {
        return remoteFtpRepository.save(remoteFtpEntity);
    }

    public RemoteFtpEntity get(String pkId) {
        return remoteFtpRepository.findBySubscriberId(pkId).orElseThrow(() -> notFound("Protocol"));
    }

    public void delete(String pkId) {
        remoteFtpRepository.findBySubscriberId(pkId).ifPresent(remoteFtpRepository::delete);
    }

    public void deleteUserMailboxCertificates(RemoteProfileModel remoteProfileModel, boolean deleteUser, boolean deleteMailboxes, boolean deleteCert) {


        if (deleteUser && isNotNull(remoteProfileModel.getUserName())) {
            b2BRemoteFtpService.deleteRemoteUser(remoteProfileModel.getUserName());
        }
        if (deleteMailboxes) {
            if (isNotNull(remoteProfileModel.getInDirectory())) {
                b2BRemoteFtpService.deleteRemoteMailBox(remoteProfileModel.getInDirectory());
            }
            if (isNotNull(remoteProfileModel.getOutDirectory())) {
                b2BRemoteFtpService.deleteRemoteMailBox(remoteProfileModel.getOutDirectory());
            }
        }
        if (deleteCert) {
            if (isNotNull(remoteProfileModel.getKnownHostKey())) {
                b2BRemoteFtpService.deleteSshKnowHostKeyInSI(remoteProfileModel.getKnownHostKey());
            }

            if (isNotNull(remoteProfileModel.getCertificateId())) {
                b2BRemoteFtpService.deleteCertificateId(remoteProfileModel.getCertificateId());
            }
        }


    }

    public void setB2bUserParams(RemoteProfileModel remoteProfileModel) {
        try {
            b2BRemoteFtpService.setB2bUserParams(remoteProfileModel);
        } catch (CommunityManagerServiceException e) {
            //We should ignore the Exception
        }
    }

    public List<RemoteFtpEntity> findAllByProtocolTypeAndIsHubInfo(String protocolType, String isHubInfo) {
        return remoteFtpRepository.findAllByProtocolTypeAndIsHubInfo(protocolType, isHubInfo).orElse(new ArrayList<>());
    }

    public Optional<List<RemoteFtpEntity>> findAllByIsHubInfoAndPrfAuthTypeLike(String isHubInfo, String prfAuthType) {
        return remoteFtpRepository.findAllByIsHubInfoAndPrfAuthTypeContainingIgnoreCase(isHubInfo, prfAuthType);
    }

    public List<RemoteFtpEntity> findAllByIsHubInfoAndPwdLastChangeDoneBefore(String hubInfo, Timestamp timeBefore) {
        return remoteFtpRepository.findAllByIsHubInfoAndPwdLastChangeDoneBefore(hubInfo, timeBefore).orElse(new ArrayList<>());
    }

    public List<RemoteFtpEntity> findAllBySubscriberId(List<String> subIds) {
        return remoteFtpRepository.findAllBySubscriberIdIn(subIds).orElse(new ArrayList<>());
    }
}
