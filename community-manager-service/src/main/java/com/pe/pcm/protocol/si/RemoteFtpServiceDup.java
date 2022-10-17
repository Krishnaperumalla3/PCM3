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

package com.pe.pcm.protocol.si;

import com.pe.pcm.certificate.CaCertInfoService;
import com.pe.pcm.certificate.SshKHostKeyService;
import com.pe.pcm.certificate.SshKeyPairService;
import com.pe.pcm.certificate.entity.CaCertInfoEntity;
import com.pe.pcm.common.CommunityManagerNameModel;
import com.pe.pcm.miscellaneous.CertificateUserUtilityService;
import com.pe.pcm.miscellaneous.PasswordUtilityService;
import com.pe.pcm.protocol.RemoteProfileModel;
import com.pe.pcm.protocol.remoteftp.RemoteFtpRepository;
import com.pe.pcm.protocol.remoteftp.entity.RemoteFtpEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;
import static com.pe.pcm.protocol.function.ProtocolFunctions.mapperToRemoteFtpEntity;

/**
 * @author Kiran Reddy.
 */
@Service
public class RemoteFtpServiceDup {

    private final RemoteFtpRepository remoteFtpRepository;
    private final PasswordUtilityService passwordUtilityService;
    private final CaCertInfoService caCertInfoService;
    private final SshKHostKeyService sshKHostKeyService;
    private final SshKeyPairService sshKeyPairService;
    private final CertificateUserUtilityService certificateUserUtilityService;

    @Autowired
    public RemoteFtpServiceDup(RemoteFtpRepository remoteFtpRepository, PasswordUtilityService passwordUtilityService, CaCertInfoService caCertInfoService, SshKHostKeyService sshKHostKeyService, SshKeyPairService sshKeyPairService, CertificateUserUtilityService certificateUserUtilityService) {
        this.remoteFtpRepository = remoteFtpRepository;
        this.passwordUtilityService = passwordUtilityService;
        this.caCertInfoService = caCertInfoService;
        this.sshKHostKeyService = sshKHostKeyService;
        this.sshKeyPairService = sshKeyPairService;
        this.certificateUserUtilityService = certificateUserUtilityService;
    }

    public RemoteFtpEntity saveProtocol(RemoteProfileModel remoteProfileModel,
                                        String parentPrimaryKey,
                                        String childPrimaryKey,
                                        String siProfileId) {
        if (remoteProfileModel.getHubInfo()) {
            remoteProfileModel.setPassword(passwordUtilityService.getDecryptedValue(remoteProfileModel.getPassword()));
        }
        RemoteFtpEntity remoteFtpEntity = mapperToRemoteFtpEntity.apply(remoteProfileModel);
        remoteFtpEntity.setPkId(childPrimaryKey)
                .setSubscriberType(remoteProfileModel.getSubscriberType())
                .setSubscriberId(parentPrimaryKey)
                .setCaCertId(remoteProfileModel.getProtocol().equals("SFGFTPS") && !remoteProfileModel.getHubInfo() ?
                        caCertInfoService.findAllByNameInOrderByName(Arrays.asList(remoteFtpEntity.getCertificateId().split(",")))
                                .orElse(new ArrayList<>())
                                .stream()
                                .map(CaCertInfoEntity::getObjectId)
                                .collect(Collectors.joining(",")) : "")
                .setUserIdentityKeyId(remoteProfileModel.getProtocol().equals("SFGSFTP") ? sshKeyPairService.findByNameNoThrow(remoteProfileModel.getUserIdentityKey()).getObjectId() : "")
                .setKnownHostKeyId(remoteProfileModel.getProtocol().equals("SFGSFTP") ? sshKHostKeyService.findByNameNoThrow(remoteProfileModel.getKnownHostKey()).getObjectId() : "")
                .setAuthUserkeyId(remoteProfileModel.getProtocol().equals("SFGSFTP") ? remoteProfileModel.getHubInfo() ? !remoteProfileModel.getPreferredAuthenticationType().equalsIgnoreCase("Password") ? certificateUserUtilityService.getSshUserKeyListOfIds(remoteProfileModel.getAuthorizedUserKeys()).stream().map(CommunityManagerNameModel::getName).collect(Collectors.joining(",")) : "" : "" : "")
//                .setUserIdentityKeyId(remoteProfileModel.getUserIdentityKey())
//                .setKnownHostKeyId()
//                .setKnownHostKey(remoteProfileModel.getKnownHostKeyNames().stream().map(CommunityManagerNameModel::getName).collect(Collectors.joining(",")))
//                .setAuthUserkeyId(remoteProfileModel.getAuthorizedUserKeys().stream().map(CommunityManagerNameModel::getName).collect(Collectors.joining(",")))
                .setProfileId(siProfileId)
                .setPassword(passwordUtilityService.getEncryptedValue(remoteProfileModel.getPassword()));
        return save(remoteFtpEntity);
    }

    public void deleteProtocol(String pkId) {
        delete(pkId);
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

}
