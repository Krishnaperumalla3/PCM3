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

package com.pe.pcm.certificate;

import com.pe.pcm.b2b.B2BApiService;
import com.pe.pcm.certificate.entity.PgpPublicKeyEntity;
import com.pe.pcm.common.CommunityManagerNameModel;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.sfg.SFGApiService;
import com.pe.pcm.workflow.pem.PemImportCertModel;
import com.pe.pcm.workflow.pem.PemImportPGPModel;
import com.pe.pcm.workflow.pem.PgpPublicKeyModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.pe.pcm.b2b.B2BFunctions.*;
import static com.pe.pcm.utils.CommonFunctions.convertFileToString;
import static com.pe.pcm.utils.CommonFunctions.convertToBase64;

/**
 * @author Chenchu Kiran.
 */
@Service
public class ManageCertificateService {

    private final B2BApiService b2BApiService;
    private final SFGApiService sfgApiService;
    private final PgpPublicKeyRepository pgpPublicKeyRepository;

    private final boolean isB2bActive;
    private final boolean isSFGActive;

    @Autowired
    public ManageCertificateService(B2BApiService b2BApiService, SFGApiService sfgApiService, PgpPublicKeyRepository pgpPublicKeyRepository, @Value("${sterling-b2bi.b2bi-api.active}") boolean isB2bActive, @Value("${sterling-b2bi.b2bi-api.sfg-api.active}") boolean isSFGActive) {
        this.b2BApiService = b2BApiService;
        this.sfgApiService = sfgApiService;
        this.pgpPublicKeyRepository = pgpPublicKeyRepository;
        this.isB2bActive = isB2bActive;
        this.isSFGActive = isSFGActive;
    }

    public void uploadCACert(MultipartFile file, String certName) {
        isB2bEnabled();
        b2BApiService.createCaCertInSI(mapperToCaCertModel.apply(getFileNameFromFile(file, certName), convertToBase64.apply(convertFileToString.apply(file))));
    }

    //For PEM
    public void uploadCACert(PemImportCertModel pemImportCertModel) {
        isB2bEnabled();
        b2BApiService.createCaCertInSI(mapperToCaCertModel.apply(pemImportCertModel.getCertOrKeyName(), pemImportCertModel.getCertOrKeyData()));
    }

    public void uploadSystemCert(MultipartFile file, String certName, String certType, String privateKeyPassword) {
        isB2bEnabled();
        b2BApiService.createSystemCertInSI(mapperToSystemDigitalCert.apply(getFileNameFromFile(file, certName), certType)
                .setPrivateKeyPassword(privateKeyPassword)
                .setCertData(convertToBase64.apply(convertFileToString.apply(file))));
    }

    public void uploadTrustedCert(MultipartFile file, String certName) {
        isB2bEnabled();
        b2BApiService.createTrustedCertInSI(mapperToTrustedDigitalModel.apply(getFileNameFromFile(file, certName), convertToBase64.apply(convertFileToString.apply(file))));
    }

    //For PEM
    public void uploadTrustedCert(PemImportCertModel pemImportCertModel) {
        isB2bEnabled();
        b2BApiService.createTrustedCertInSI(mapperToTrustedDigitalModel.apply(pemImportCertModel.getCertOrKeyName(), pemImportCertModel.getCertOrKeyData()));
    }

    public void uploadSshKnownHostKey(MultipartFile file, String keyName) {
        isB2bEnabled();
        b2BApiService.createSshKnowHostKeyInSI(mapperToSshKnownHostKeyModel.apply(getFileNameFromFile(file, keyName), convertToBase64.apply(convertFileToString.apply(file))));
    }

    //For PEM
    public void uploadSshKnownHostKey(PemImportCertModel pemImportCertModel) {
        isB2bEnabled();
        b2BApiService.createSshKnowHostKeyInSI(mapperToSshKnownHostKeyModel.apply(pemImportCertModel.getCertOrKeyName(), pemImportCertModel.getCertOrKeyData()));
    }

    public void uploadSSHUIDKey(MultipartFile file, String keyName) {
        isB2bEnabled();
        b2BApiService.createUIDKeyInSI(mapperToSshUIDKeyModel.apply(getFileNameFromFile(file, keyName), convertToBase64.apply(convertFileToString.apply(file))));
    }

    //For PEM
    public void uploadSSHUIDKey(PemImportCertModel pemImportCertModel) {
        isB2bEnabled();
        b2BApiService.createUIDKeyInSI(mapperToSshUIDKeyModel.apply(pemImportCertModel.getCertOrKeyName(), pemImportCertModel.getCertOrKeyData()));
    }

    public void uploadSshAuthorizedUserKey(MultipartFile file, String keyName) {
        isB2bEnabled();
        b2BApiService.createSshAuthorizedUserKeyInSI(mapperToSshAuthorizedUserKeyModel.apply(getFileNameFromFile(file, keyName), convertToBase64.apply(convertFileToString.apply(file))));
    }

    //For PEM
    public void uploadSshAuthorizedUserKey(PemImportCertModel pemImportCertModel) {
        isB2bEnabled();
        b2BApiService.createSshAuthorizedUserKeyInSI(mapperToSshAuthorizedUserKeyModel.apply(pemImportCertModel.getCertOrKeyName(), pemImportCertModel.getCertOrKeyData()));
    }

    //For PEM
    public void uploadPGPCert(PemImportPGPModel pemImportPGPModel) {
        isSFGEnabled();
        sfgApiService.uploadPGPCert(pemImportPGPModel);
    }

    //For PEM
    public String getPGPCert(String certName) {
        isSFGEnabled();
        return sfgApiService.getPGPCert(certName);
    }

    //For PEM
    public List<PgpPublicKeyModel> getPGPCertLocal(String certName) {
        /*PgpPublicKeyEntity pgpPublicKeyEntity = pgpPublicKeyRepository.findAllByNameLikeAndAndKeyType(certName,"PARTNER").orElse(new PgpPublicKeyEntity());
        PgpPublicKeyModel pgpPublicKeyModel = new PgpPublicKeyModel();
        BeanUtils.copyProperties(pgpPublicKeyEntity,pgpPublicKeyModel);*/
        return pgpPublicKeyRepository.findAllByNameContainingAndKeyType(certName,"PARTNER").orElse(Collections.emptyList())
                .stream()
                .map(pgpPublicKeyEntity -> new PgpPublicKeyModel()
                .setName(pgpPublicKeyEntity.getName())
                .setObjectId(pgpPublicKeyEntity.getObjectId())
                .setUsages(pgpPublicKeyEntity.getUsages()))
                .collect(Collectors.toList());
    }

    //For PEM
    public void deletePGPCert(String objectId) {
        isSFGEnabled();
         sfgApiService.deletePGPCert(objectId);
    }


    void isB2bEnabled() {
        if (!isB2bActive) {
            throw GlobalExceptionHandler.internalServerError("B2B API doesn't configured, Please contact B2B Admin Team to setup B2B API.");
        }
    }

    void isSFGEnabled() {
        if (!isSFGActive) {
            throw GlobalExceptionHandler.internalServerError("SFG API doesn't configured, Please contact B2B Admin Team to setup SFG API.");
        }
    }
}
