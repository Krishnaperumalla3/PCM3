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

package com.pe.pcm.miscellaneous;

import com.pe.pcm.b2b.other.CaCertGetModel;
import com.pe.pcm.certificate.*;
import com.pe.pcm.certificate.entity.CertsAndPriKeyEntity;
import com.pe.pcm.certificate.entity.TrustedCertInfoEntity;
import com.pe.pcm.common.CommunityManagerKeyValueModel;
import com.pe.pcm.common.CommunityManagerNameModel;
import com.pe.pcm.partner.PartnerService;
import com.pe.pcm.partner.entity.PartnerEntity;
import com.pe.pcm.pem.PemAccountExpiryModel;
import com.pe.pcm.protocol.FtpService;
import com.pe.pcm.protocol.HttpService;
import com.pe.pcm.protocol.RemoteConnectDirectService;
import com.pe.pcm.protocol.RemoteFtpService;
import com.pe.pcm.protocol.as2.As2Service;
import com.pe.pcm.protocol.remoteftp.entity.RemoteFtpEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.pe.pcm.utils.CommonFunctions.*;

/**
 * @author Chenchu Kiran Reddy.
 */
@Service
public class CertificateUserUtilityService {

    private final TrustedCertInfoService trustedCertInfoService;
    private final CaCertInfoService caCertInfoService;
    private final SshKHostKeyService sshKHostKeyService;
    private final SshKeyPairService sshKeyPairService;
    private final CertsAndPriKeyService certsAndPriKeyService;
    private final SshUserKeyService sshUserKeyService;
    private final AuthXrefSshService authXrefSshService;
    private final As2Service as2Service;
    private final FtpService ftpService;
    private final RemoteFtpService remoteFtpService;
    private final HttpService httpService;
    private final RemoteConnectDirectService remoteConnectDirectService;
    private final PartnerService partnerService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CertificateUserUtilityService.class);

    @Autowired
    public CertificateUserUtilityService(TrustedCertInfoService trustedCertInfoService, CaCertInfoService caCertInfoService, SshKHostKeyService sshKHostKeyService, SshKeyPairService sshKeyPairService, CertsAndPriKeyService certsAndPriKeyService, SshUserKeyService sshUserKeyService, AuthXrefSshService authXrefSshService, As2Service as2Service, FtpService ftpService, RemoteFtpService remoteFtpService, HttpService httpService, RemoteConnectDirectService remoteConnectDirectService, PartnerService partnerService) {
        this.trustedCertInfoService = trustedCertInfoService;
        this.caCertInfoService = caCertInfoService;
        this.sshKHostKeyService = sshKHostKeyService;
        this.sshKeyPairService = sshKeyPairService;
        this.certsAndPriKeyService = certsAndPriKeyService;
        this.sshUserKeyService = sshUserKeyService;
        this.authXrefSshService = authXrefSshService;
        this.as2Service = as2Service;
        this.ftpService = ftpService;
        this.remoteFtpService = remoteFtpService;
        this.httpService = httpService;
        this.remoteConnectDirectService = remoteConnectDirectService;
        this.partnerService = partnerService;
    }

    public List<CommunityManagerNameModel> getTrustedCertInfoList() {
        return trustedCertInfoService.getTrustedCetInfoList().orElse(Collections.emptyList())
                .stream()
                .map(trustedCertInfoEntity -> new CommunityManagerNameModel(trustedCertInfoEntity.getName())).collect(Collectors.toList());
    }

    public List<CommunityManagerKeyValueModel> getAuthXrefSshList() {
        return authXrefSshService.findByUserkey().orElse(Collections.emptyList())
                .stream()
                .map(authXrefSshEntity -> new CommunityManagerKeyValueModel(authXrefSshEntity.getUserId(), authXrefSshEntity.getUserkey())).collect(Collectors.toList());
    }

    public List<CommunityManagerKeyValueModel> getTrustedCertInfoMap() {
        return trustedCertInfoService.getTrustedCetInfoList().orElse(Collections.emptyList())
                .stream()
                .map(trustedCertInfoEntity -> new CommunityManagerKeyValueModel(trustedCertInfoEntity.getObjectId(), trustedCertInfoEntity.getName()))
                .collect(Collectors.toList());
    }

    public List<CommunityManagerNameModel> getCaCertInfoList() {
        return caCertInfoService.getCaCertInfoList().orElse(Collections.emptyList())
                .stream()
                .map(caCertInfoEntity -> new CommunityManagerNameModel(caCertInfoEntity.getName()))
                .collect(Collectors.toList());
    }

    public List<CommunityManagerNameModel> getSshKHostKeyList() {
        return sshKHostKeyService.getSshKHostKeyList().orElse(Collections.emptyList())
                .stream()
                .map(sshKHostKeyEntity -> new CommunityManagerNameModel(sshKHostKeyEntity.getName()))
                .collect(Collectors.toList());
    }

    public List<CommunityManagerNameModel> getSshKHostKeyListByName(String name, Boolean isLike) {
        return sshKHostKeyService.getSshKHostKeyListByName(name, isLike).orElse(Collections.emptyList())
                .stream()
                .map(sshKHostKeyEntity -> new CommunityManagerNameModel(sshKHostKeyEntity.getName()))
                .collect(Collectors.toList());
    }

    public List<CommunityManagerNameModel> getSshKeyPairList() {
        return sshKeyPairService.findAll()
                .stream()
                .map(sshKeyPair -> new CommunityManagerNameModel(sshKeyPair.getName()))
                .collect(Collectors.toList());
    }

    public List<CommunityManagerKeyValueModel> getCaCertInfoMap() {
        return caCertInfoService.getCaCertInfoList().orElse(Collections.emptyList())
                .stream()
                .map(caCertInfoEntity -> new CommunityManagerKeyValueModel(caCertInfoEntity.getObjectId(), caCertInfoEntity.getName()))
                .collect(Collectors.toList());
    }

    public List<CommunityManagerNameModel> getCaCertInfoList(String certName, Boolean isLike) {
        return caCertInfoService.getCaCertInfoList(certName, isLike).orElse(Collections.emptyList())
                .stream()
                .map(caCertInfoEntity -> new CommunityManagerNameModel(caCertInfoEntity.getName()))
                .collect(Collectors.toList());
    }

    //System Certificates
    public List<CommunityManagerKeyValueModel> getCertsAndPriKeyMap() {
        return certsAndPriKeyService.getCertsAndPriKeyList().orElse(Collections.emptyList())
                .stream()
                .map(certsAndPriKeyEntity -> new CommunityManagerKeyValueModel(certsAndPriKeyEntity.getObjectId(), certsAndPriKeyEntity.getName()))
                .collect(Collectors.toList());
    }

    public CommunityManagerNameModel getTrustedCertInfoId(String certName) {
        return new CommunityManagerNameModel(trustedCertInfoService.findByName(certName).orElse(new TrustedCertInfoEntity()).getObjectId());
    }

    public List<CommunityManagerNameModel> getTrustedCertInfoIdList(String certName, Boolean isLike) {
        return trustedCertInfoService.findAllByName(certName, isLike)
                .stream()
                .map(trustedCertInfoEntity -> new CommunityManagerNameModel(trustedCertInfoEntity.getObjectId()))
                .collect(Collectors.toList());
    }

    public CommunityManagerNameModel getCertsAndPriKeyId(String certName) {
        return new CommunityManagerNameModel(certsAndPriKeyService.findByName(certName).orElse(new CertsAndPriKeyEntity()).getObjectId());
    }

    public List<CommunityManagerNameModel> getCertsAndPriKeyIdList(String certName, Boolean isLike) {
        return certsAndPriKeyService.getCertsAndPriKeyIdList(certName, isLike)
                .stream()
                .map(certsAndPriKeyEntity -> new CommunityManagerNameModel(certsAndPriKeyEntity.getObjectId()))
                .collect(Collectors.toList());
    }

    public List<CommunityManagerNameModel> getSshUserKeyCertList(String name, Boolean isLike) {
        return sshUserKeyService.findAllByName(name, isLike)
                .stream()
                .map(sshUserKeyEntity -> new CommunityManagerNameModel(sshUserKeyEntity.getName()))
                .collect(Collectors.toList());
    }

    public List<CommunityManagerNameModel> getSshUserKeyList() {
        return sshUserKeyService.findAllByNameAsc()
                .stream()
                .map(sshUserKeyEntity -> new CommunityManagerNameModel(sshUserKeyEntity.getName()))
                .collect(Collectors.toList());
    }

    public List<CommunityManagerNameModel> getSshUserKeyListOfIds(List<CommunityManagerNameModel> certNames) {
        List<CommunityManagerNameModel> communityManagerNameModels = new ArrayList<>();
        List<List<String>> partitionList = getPartitions(999, certNames.stream().map(CommunityManagerNameModel::getName).collect(Collectors.toList()));
        partitionList.forEach(certNamesList -> sshUserKeyService.findAllByNameInOrderByName(certNamesList).forEach(sshUserKeyEntity -> communityManagerNameModels.add(new CommunityManagerNameModel().setName(sshUserKeyEntity.getObjectId()))));
        return communityManagerNameModels;
    }

    public List<PemAccountExpiryModel> getExpiryCertList(int days) {

        Date dateBefore = addDays(new Date(System.currentTimeMillis()), days);
        LOGGER.info("Date Before: {}", dateBefore);
        List<PemAccountExpiryModel> pemAccountExpiryModels = new ArrayList<>();
        pemAccountExpiryModels.addAll(getCaCertInfoExpiryList(dateBefore));
        pemAccountExpiryModels.addAll(getTrustedExpiryList(dateBefore));
        return pemAccountExpiryModels;
    }

    public List<PemAccountExpiryModel> getExpiryPriCertList(int days) {

        Date dateBefore = addDays(new Date(System.currentTimeMillis()), days);
        LOGGER.info("Date Before: {}", dateBefore);
        List<PemAccountExpiryModel> pemAccountExpiryModels = new ArrayList<>();
        pemAccountExpiryModels.addAll(getPriCertExpiryList(dateBefore));
        return pemAccountExpiryModels;
    }

    public List<CaCertGetModel> findByNameInOrderByName(List<CaCertGetModel> certNames) {
        List<CaCertGetModel> caCertGetModels = new ArrayList<>();
        List<List<String>> partitionsList = getPartitions(998, certNames.stream()
                .map(CaCertGetModel::getCaCertName)
                .distinct()
                .collect(Collectors.toList()));
        partitionsList.forEach(certNameList ->
                caCertInfoService.findAllByNameInOrderByName(certNameList).orElse(new ArrayList<>()).forEach(caCertInfoEntity -> caCertGetModels.add(new CaCertGetModel().setCaCertName(caCertInfoEntity.getObjectId()))));
        return caCertGetModels;
    }

    private List<PemAccountExpiryModel> getTrustedExpiryList(Date dateBefore) {
        List<CommunityManagerKeyValueModel> trustedCertNameList = new ArrayList<>();
        List<PemAccountExpiryModel> pemAccountExpiryModels = new ArrayList<>();
        trustedCertNameList.addAll(as2Service.findAllByExchgCertNameNotNull()
                .stream()
                .filter(as2Entity -> isNotNull(as2Entity.getSubscriberId()))
                .map(as2Entity -> new CommunityManagerKeyValueModel(as2Entity.getSubscriberId(), as2Entity.getExchgCertName()))
                .collect(Collectors.toList()));
        trustedCertNameList.addAll(as2Service.findAllBySigningCertNameNotNull()
                .stream()
                .filter(as2Entity -> isNotNull(as2Entity.getSubscriberId()))
                .map(as2Entity -> new CommunityManagerKeyValueModel(as2Entity.getSubscriberId(), as2Entity.getSigningCertName()))
                .collect(Collectors.toList()));
        List<List<String>> partitionsListTrusted = getPartitions(998, trustedCertNameList.stream()
                .map(CommunityManagerKeyValueModel::getValue)
                .distinct()
                .collect(Collectors.toList()));
        partitionsListTrusted.forEach(certNameList -> trustedCertInfoService.findAllByNameInAndNotAfterBefore(certNameList, dateBefore).orElse(new ArrayList<>())
                .forEach(trustedCertInfoEntity -> {
                    List<String> matchedProfilePkIds = trustedCertNameList.stream()
                            .filter(e -> isNotNull(e.getValue()) && isNotNull(trustedCertInfoEntity.getName()) && e.getValue().equalsIgnoreCase(trustedCertInfoEntity.getName()))
                            .map(CommunityManagerKeyValueModel::getKey)
                            .collect(Collectors.toList());
                    if (!matchedProfilePkIds.isEmpty()) {
                        List<PartnerEntity> matchedPartnerEntities = new ArrayList<>();
                        if (matchedProfilePkIds.size() > 998) {
                            getPartitions(901, matchedProfilePkIds).forEach(pkIdList -> matchedPartnerEntities.addAll(partnerService.findAllByIdIn(pkIdList)));
                        } else {
                            matchedPartnerEntities.addAll(partnerService.findAllByIdIn(matchedProfilePkIds));
                        }
                        matchedPartnerEntities.forEach(partnerEntity -> pemAccountExpiryModels.add(new PemAccountExpiryModel()
                                .setCertName(trustedCertInfoEntity.getName())
                                .setCertType("Trusted Cert")
                                .setProfileName(partnerEntity.getTpName())
                                .setProfileId(partnerEntity.getTpId())
                                .setEmailId(partnerEntity.getEmail())
                                .setPemIdentifier(partnerEntity.getPemIdentifier())
                                .setProtocol(partnerEntity.getTpProtocol())
                                .setNotBefore(trustedCertInfoEntity.getNotBefore())
                                .setNotAfter(trustedCertInfoEntity.getNotAfter()))
                        );
                    }
                })
        );
        return pemAccountExpiryModels;
    }

    private List<PemAccountExpiryModel> getPriCertExpiryList(Date dateBefore) {
        List<PemAccountExpiryModel> pemAccountExpiryModels = new ArrayList<>();
        certsAndPriKeyService.findAllByNotAfterBefore(dateBefore).orElse(new ArrayList<>())
                .forEach(signingCertInfoEntity -> pemAccountExpiryModels.add(new PemAccountExpiryModel()
                                .setCertName(signingCertInfoEntity.getName())
                                .setCertType("Signing Cert")
                                .setNotBefore(signingCertInfoEntity.getNotBefore())
                                .setNotAfter(signingCertInfoEntity.getNotAfter()))
                        );
        return pemAccountExpiryModels;
    }

    private List<PemAccountExpiryModel> getCaCertInfoExpiryList(Date dateBefore) {
        List<PemAccountExpiryModel> pemAccountExpiryModels = new ArrayList<>();

        //Reading certs from using Cert Names
        List<CommunityManagerKeyValueModel> caNameList = new ArrayList<>();
        caNameList.addAll(remoteFtpService.findAllByProtocolTypeAndIsHubInfo("SFGFTPS", "N")
                .stream()
                .filter(remoteFtpEntity -> isNotNull(remoteFtpEntity.getSubscriberId()))
                .map(remoteFtpEntity -> new CommunityManagerKeyValueModel(remoteFtpEntity.getSubscriberId(), remoteFtpEntity.getCertificateId()))
                .collect(Collectors.toList()));
        caNameList.addAll(httpService.findAllByProtocolType("HTTPS")
                .stream()
                .filter(httpEntity -> isNotNull(httpEntity.getSubscriberId()))
                .map(httpEntity -> new CommunityManagerKeyValueModel(httpEntity.getSubscriberId(), httpEntity.getCertificate()))
                .collect(Collectors.toList())
        );
        caNameList.addAll(remoteConnectDirectService.findAllByCaCertificateNotNull()
                .stream()
                .filter(remoteConnectDirectEntity -> isNotNull(remoteConnectDirectEntity.getSubscriberId()))
                .map(rcd -> new CommunityManagerKeyValueModel(rcd.getSubscriberId(), rcd.getCaCertificateName()))
                .collect(Collectors.toList())
        );
        caNameList.addAll(as2Service.findAllByCaCertNotNull()
                .stream()
                .filter(as2Entity -> isNotNull(as2Entity.getSubscriberId()))
                .map(as2Entity -> new CommunityManagerKeyValueModel(as2Entity.getSubscriberId(), as2Entity.getCaCert()))
                .collect(Collectors.toList()));

        List<List<String>> partitionsList = getPartitions(998, caNameList.stream()
                .map(CommunityManagerKeyValueModel::getValue)
                .distinct()
                .collect(Collectors.toList()));
        partitionsList.forEach(certNameList -> caCertInfoService.findAllByNameInAndNotAfterBefore(certNameList, dateBefore).orElse(new ArrayList<>())
                .forEach(caCertInfoEntity -> {
                    List<String> matchedProfilePkIds = caNameList.stream()
                            .filter(e -> isNotNull(e.getValue()) && isNotNull(caCertInfoEntity.getName()) && e.getValue().equalsIgnoreCase(caCertInfoEntity.getName()))
                            .map(CommunityManagerKeyValueModel::getKey)
                            .collect(Collectors.toList());
                    if (!matchedProfilePkIds.isEmpty()) {
                        List<PartnerEntity> matchedPartnerEntities = new ArrayList<>();
                        if (matchedProfilePkIds.size() > 998) {
                            getPartitions(901, matchedProfilePkIds).forEach(pkIdList -> matchedPartnerEntities.addAll(partnerService.findAllByIdIn(pkIdList)));
                        } else {
                            matchedPartnerEntities.addAll(partnerService.findAllByIdIn(matchedProfilePkIds));
                        }
                        matchedPartnerEntities.forEach(partnerEntity -> pemAccountExpiryModels.add(new PemAccountExpiryModel()
                                .setCertName(caCertInfoEntity.getName())
                                .setCertType("CA Cert")
                                .setProfileName(partnerEntity.getTpName())
                                .setProfileId(partnerEntity.getTpId())
                                .setEmailId(partnerEntity.getEmail())
                                .setPemIdentifier(partnerEntity.getPemIdentifier())
                                .setProtocol(partnerEntity.getTpProtocol())
                                .setNotBefore(caCertInfoEntity.getNotBefore())
                                .setNotAfter(caCertInfoEntity.getNotAfter()))
                        );
                    }
                })
        );


        //Reading the certificated using Cert Ids
        List<CommunityManagerKeyValueModel> caIdList = ftpService.findAllByProtocolTypeAndIsHubInfo("FTPS", "N")
                .stream()
                .filter(ftpEntity -> isNotNull(ftpEntity.getSubscriberId()))
                .map(ftpEntity -> new CommunityManagerKeyValueModel(ftpEntity.getSubscriberId(), ftpEntity.getCertificateId())).collect(Collectors.toList());

        List<List<String>> partitionsList1 = getPartitions(998, caIdList.stream()
                .map(CommunityManagerKeyValueModel::getValue)
                .distinct()
                .collect(Collectors.toList()));
        partitionsList1.forEach(caIdsList -> caCertInfoService.findAllByIdInAndNotAfterBefore(caIdsList, dateBefore).orElse(new ArrayList<>())
                .forEach(caCertInfoEntity -> {
                            List<String> matchedProfilePkIds = caIdList
                                    .stream()
                                    .filter(e -> e.getValue().equalsIgnoreCase(caCertInfoEntity.getObjectId()))
                                    .map(CommunityManagerKeyValueModel::getKey)
                                    .collect(Collectors.toList());
                            if (!matchedProfilePkIds.isEmpty()) {
                                List<PartnerEntity> matchedPartnerEntities = new ArrayList<>();
                                if (matchedProfilePkIds.size() > 998) {
                                    getPartitions(901, matchedProfilePkIds).forEach(pkIdList -> matchedPartnerEntities.addAll(partnerService.findAllByIdIn(pkIdList)));
                                } else {
                                    matchedPartnerEntities.addAll(partnerService.findAllByIdIn(matchedProfilePkIds));
                                }
                                matchedPartnerEntities.forEach(partnerEntity -> pemAccountExpiryModels.add(new PemAccountExpiryModel()
                                        .setCertName(caCertInfoEntity.getName())
                                        .setCertType("CA Cert")
                                        .setProfileName(partnerEntity.getTpName())
                                        .setProfileId(partnerEntity.getTpId())
                                        .setEmailId(partnerEntity.getEmail())
                                        .setPemIdentifier(partnerEntity.getPemIdentifier())
                                        .setProtocol(partnerEntity.getTpProtocol())
                                        .setNotBefore(caCertInfoEntity.getNotBefore())
                                        .setNotAfter(caCertInfoEntity.getNotAfter()))
                                );
                            }
                        }
                )
        );

        return pemAccountExpiryModels;
    }

    public List<PemAccountExpiryModel> getPartnerProfilesUsersExpiryList(int days) {
        List<PemAccountExpiryModel> pemAccountExpiryModels = new ArrayList<>();

        Timestamp dateBefore = minusDays(new Date(System.currentTimeMillis()), days);
        LOGGER.info("Date Before: {}", dateBefore);

        Map<String, RemoteFtpEntity> remoteFtpEntitiesMap = remoteFtpService.findAllByIsHubInfoAndPwdLastChangeDoneBefore("N", dateBefore)
                .stream()
                .filter(remoteFtpEntity -> isNotNull(remoteFtpEntity.getUserId()))
                .collect(Collectors.toMap(RemoteFtpEntity::getSubscriberId, o -> o));

        getPartitions(998, new ArrayList<>(remoteFtpEntitiesMap.keySet()))
                .forEach(pkIdsList -> partnerService.findAllByIdIn(pkIdsList).forEach(partnerEntity ->
                        pemAccountExpiryModels.add(
                                new PemAccountExpiryModel().setProfileName(partnerEntity.getTpName())
                                        .setUserName(remoteFtpEntitiesMap.get(partnerEntity.getPkId()).getUserId())
                                        .setPwdLastUpdatedDate(remoteFtpEntitiesMap.get(partnerEntity.getPkId()).getPwdLastChangeDone())
                        )));
        return pemAccountExpiryModels;
    }

}
