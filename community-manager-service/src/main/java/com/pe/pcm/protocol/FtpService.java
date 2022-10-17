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

import com.pe.pcm.certificate.CaCertInfoService;
import com.pe.pcm.certificate.SshKHostKeyService;
import com.pe.pcm.certificate.SshKeyPairService;
import com.pe.pcm.miscellaneous.PasswordUtilityService;
import com.pe.pcm.protocol.ftp.FtpRepository;
import com.pe.pcm.protocol.ftp.entity.FtpEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;
import static com.pe.pcm.protocol.function.ProtocolFunctions.mapperToFtpEntity;

/**
 * @author Chenchu Kiran.
 */
@Service
public class FtpService {

    private final FtpRepository ftpRepository;
    private final CaCertInfoService caCertInfoService;
    private final SshKHostKeyService sshKHostKeyService;
    private final SshKeyPairService sshKeyPairService;
    private final PasswordUtilityService passwordUtilityService;

    @Autowired
    public FtpService(FtpRepository ftpRepository, CaCertInfoService caCertInfoService, SshKHostKeyService sshKHostKeyService, SshKeyPairService sshKeyPairService, PasswordUtilityService passwordUtilityService) {
        this.ftpRepository = ftpRepository;
        this.caCertInfoService = caCertInfoService;
        this.sshKHostKeyService = sshKHostKeyService;
        this.sshKeyPairService = sshKeyPairService;
        this.passwordUtilityService = passwordUtilityService;
    }

    public FtpEntity save(FtpEntity ftpEntity) {
        return ftpRepository.save(ftpEntity);
    }

    public FtpEntity get(String parentPkId) {
        return ftpRepository.findBySubscriberId(parentPkId).orElseThrow(() -> notFound("Protocol"));
    }

    public void delete(String parentPkId) {
        ftpRepository.findBySubscriberId(parentPkId).ifPresent(ftpRepository::delete);
    }

    @Transactional
    public FtpEntity saveProtocol(FtpModel ftpModel, String parentPrimaryKey, String childPrimaryKey, String subscriberType) {
        ftpModel.setPassword(passwordUtilityService.getDecryptedValue(ftpModel.getPassword()));
        FtpEntity ftpEntity = mapperToFtpEntity.apply(ftpModel);
        ftpEntity.setPkId(childPrimaryKey)
                .setSubscriberType(subscriberType)
                .setSubscriberId(parentPrimaryKey)
                .setCaCertName(ftpModel.getProtocol().equals("FTPS") ? caCertInfoService.findByIdNotThrow(ftpModel.getCertificateId()).getName() : "")
                .setSshIdentityKeyId(ftpModel.getProtocol().equals("SFTP") ? sshKeyPairService.findByNameNoThrow(ftpModel.getSshIdentityKeyName()).getObjectId() : "")
                .setKnownHostKeyId(ftpModel.getProtocol().equals("SFTP") ? sshKHostKeyService.findByNameNoThrow(ftpModel.getKnownHostKey()).getObjectId() : "")
                .setPassword(passwordUtilityService.getEncryptedValue(ftpModel.getPassword()));
        return save(ftpEntity);
    }

    public List<FtpEntity> findAllByProtocolTypeAndIsHubInfo(String protocolType, String isHubInfo) {
        return ftpRepository.findAllByProtocolTypeAndIsHubInfo(protocolType, isHubInfo).orElse(new ArrayList<>());
    }

    public List<FtpEntity> findAllBySubscriberId(List<String> subIds) {
        return ftpRepository.findAllBySubscriberIdIn(subIds).orElse(new ArrayList<>());
    }


}
