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

package com.pe.pcm.sterling.partner.sci;

import com.pe.pcm.certificate.CaCertInfoService;
import com.pe.pcm.certificate.CertsAndPriKeyService;
import com.pe.pcm.certificate.TrustedCertInfoService;
import com.pe.pcm.certificate.entity.CaCertInfoEntity;
import com.pe.pcm.certificate.entity.CertsAndPriKeyEntity;
import com.pe.pcm.certificate.entity.TrustedCertInfoEntity;
import com.pe.pcm.common.CommunityManagerNameModel;
import com.pe.pcm.enums.Protocol;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.miscellaneous.PasswordUtilityService;
import com.pe.pcm.protocol.As2Model;
import com.pe.pcm.protocol.RemoteProfileModel;
import com.pe.pcm.protocol.as2.certificate.*;
import com.pe.pcm.protocol.as2.si.certificate.entity.*;
import com.pe.pcm.protocol.as2.si.entity.*;
import com.pe.pcm.protocol.si.*;
import com.pe.pcm.protocol.si.as2.AS2EmailInfoServiceDup;
import com.pe.pcm.protocol.si.as2.As2ProfileServiceDup;
import com.pe.pcm.protocol.si.profile.xref.SftpProfileXrefKhostKeyService;
import com.pe.pcm.sterling.dto.DocExchangeDTO;
import com.pe.pcm.sterling.dto.TransportDTO;
import com.pe.pcm.sterling.mailbox.SterlingMailboxService;
import com.pe.pcm.sterling.mailbox.routingrule.SterlingRoutingRuleService;
import com.pe.pcm.sterling.security.SterlingDBPasswordService;
import com.pe.pcm.sterling.virtualroot.MbxVirtualRootService;
import com.pe.pcm.sterling.yfs.YfsPersonInfoService;
import com.pe.pcm.sterling.yfs.YfsUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.pe.pcm.enums.Protocol.*;
import static com.pe.pcm.exception.GlobalExceptionHandler.*;
import static com.pe.pcm.generator.PasswordGenerator.generateValidPassword;
import static com.pe.pcm.protocol.function.ProtocolFunctions.convertAs2ValuesFromNumToString;
import static com.pe.pcm.protocol.function.ProtocolFunctions.convertNumberToBoolean;
import static com.pe.pcm.protocol.function.SiProtocolFunctions.*;
import static com.pe.pcm.protocol.function.SterlingFunctions.*;
import static com.pe.pcm.utils.CommonFunctions.*;
import static com.pe.pcm.utils.KeyGeneratorUtil.getPrimaryKey;
import static com.pe.pcm.utils.PCMConstants.*;
import static java.lang.Boolean.TRUE;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.util.StringUtils.hasLength;

/**
 * @author Kiran Reddy.
 */
@Service
public class SterlingPartnerProfileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SterlingPartnerProfileService.class);

    private final SciTransportServiceDup sciTransportServiceDup;
    private final YfsOrganizationServiceDup yfsOrganizationServiceDup;
    private final YfsPersonInfoService yfsPersonInfoService;
    private final SciDocExchangeServiceDup sciDocExchangeServiceDup;
    private final SciDeliveryChangeServiceDup sciDeliveryChangeServiceDup;
    private final SciPackagingServiceDup sciPackagingServiceDup;
    private final SciProfileServiceDup sciProfileServiceDup;

    private final SciTrpSslCertService sciTrpSslCertService;
    private final CertsAndPriKeyService certsAndPriKeyService;
    private final SciTranspCaCertServiceDup sciTranspCaCertServiceDup;
    private final CaCertInfoService caCertInfoService;
    private final TrustedCertInfoService trustedCertInfoService;
    private final SciTrpKeyCertService sciTrpKeyCertService;
    private final SciTrpUserCertService sciTrpUserCertService;
    private final SciDocxUserCertService sciDocxUserCertService;
    private final SciDocxKeyCertService sciDocxKeyCertService;

    private final SftpProfileService sftpProfileService;
    private final SftpProfileXrefKhostKeyService sftpProfileXrefKhostKeyService;

    private final YfsUserService yfsUserService;
    private final SterlingMailboxService sterlingMailboxService;
    private final MbxVirtualRootService mbxVirtualRootService;

    private final As2ProfileServiceDup as2ProfileServiceDup;
    private final AS2EmailInfoServiceDup as2EmailInfoServiceDup;

    private final SterlingDBPasswordService sterlingDBPasswordService;
    private final SterlingRoutingRuleService sterlingRoutingRuleService;

    private final PasswordUtilityService passwordUtilityService;

    private final Boolean passphraseValidation;
    private final String passphraseValidationProfile;
    private final ApplicationContext appContext;

    private final boolean sfgV6updatable;


    @Autowired
    public SterlingPartnerProfileService(SciTransportServiceDup sciTransportServiceDup,
                                         YfsOrganizationServiceDup yfsOrganizationServiceDup,
                                         YfsPersonInfoService yfsPersonInfoService,
                                         SciDocExchangeServiceDup sciDocExchangeServiceDup,
                                         SciDeliveryChangeServiceDup sciDeliveryChangeServiceDup,
                                         SciPackagingServiceDup sciPackagingServiceDup,
                                         SciProfileServiceDup sciProfileServiceDup,
                                         SciTrpSslCertService sciTrpSslCertService,
                                         CertsAndPriKeyService certsAndPriKeyService,
                                         SciTranspCaCertServiceDup sciTranspCaCertServiceDup,
                                         CaCertInfoService caCertInfoService,
                                         TrustedCertInfoService trustedCertInfoService,
                                         SciTrpKeyCertService sciTrpKeyCertService,
                                         SciTrpUserCertService sciTrpUserCertService,
                                         SciDocxUserCertService sciDocxUserCertService,
                                         SciDocxKeyCertService sciDocxKeyCertService,
                                         SftpProfileService sftpProfileService,
                                         SftpProfileXrefKhostKeyService sftpProfileXrefKhostKeyService,
                                         YfsUserService yfsUserService,
                                         SterlingMailboxService sterlingMailboxService,
                                         MbxVirtualRootService mbxVirtualRootService,
                                         As2ProfileServiceDup as2ProfileServiceDup,
                                         AS2EmailInfoServiceDup as2EmailInfoServiceDup,
                                         SterlingDBPasswordService sterlingDBPasswordService,
                                         SterlingRoutingRuleService sterlingRoutingRuleService,
                                         PasswordUtilityService passwordUtilityService,
                                         @Value("${sterling-b2bi.user.cmks-validation}") Boolean passphraseValidation,
                                         @Value("${sterling-b2bi.user.cmks-validation-profile}") String passphraseValidationProfile,
                                         ApplicationContext appContext,
                                         @Value("${sterling-b2bi.sfg-v6-update}") boolean sfgV6updatable) {
        this.sciTransportServiceDup = sciTransportServiceDup;
        this.yfsOrganizationServiceDup = yfsOrganizationServiceDup;
        this.yfsPersonInfoService = yfsPersonInfoService;
        this.sciDocExchangeServiceDup = sciDocExchangeServiceDup;
        this.sciDeliveryChangeServiceDup = sciDeliveryChangeServiceDup;
        this.sciPackagingServiceDup = sciPackagingServiceDup;
        this.sciProfileServiceDup = sciProfileServiceDup;
        this.sciTrpSslCertService = sciTrpSslCertService;
        this.certsAndPriKeyService = certsAndPriKeyService;
        this.sciTranspCaCertServiceDup = sciTranspCaCertServiceDup;
        this.caCertInfoService = caCertInfoService;
        this.trustedCertInfoService = trustedCertInfoService;
        this.sciTrpKeyCertService = sciTrpKeyCertService;
        this.sciTrpUserCertService = sciTrpUserCertService;
        this.sciDocxUserCertService = sciDocxUserCertService;
        this.sciDocxKeyCertService = sciDocxKeyCertService;
        this.sftpProfileService = sftpProfileService;
        this.sftpProfileXrefKhostKeyService = sftpProfileXrefKhostKeyService;
        this.yfsUserService = yfsUserService;
        this.sterlingMailboxService = sterlingMailboxService;
        this.mbxVirtualRootService = mbxVirtualRootService;
        this.as2ProfileServiceDup = as2ProfileServiceDup;
        this.as2EmailInfoServiceDup = as2EmailInfoServiceDup;
        this.sterlingDBPasswordService = sterlingDBPasswordService;
        this.sterlingRoutingRuleService = sterlingRoutingRuleService;
        this.passwordUtilityService = passwordUtilityService;
        this.passphraseValidation = passphraseValidation;
        this.passphraseValidationProfile = passphraseValidationProfile;
        this.appContext = appContext;
        this.sfgV6updatable = sfgV6updatable;
    }

    public String save(As2Model as2Model, Protocol protocol, String oldProfileName, String oldAs2ProfileName, boolean isUpdate) {
        return saveSterlingProfile(
                convertAs2ModelToRemoteProfileModel.apply(as2Model),
                protocol,
                oldProfileName,
                oldAs2ProfileName,
                isUpdate);
    }

    public String save(RemoteProfileModel remoteProfileModel,
                       Protocol protocol,
                       String oldProfileName,
                       boolean isUpdate) {

        if (remoteProfileModel.getPoolingInterval().equals("ON") && remoteProfileModel.getHubInfo()) {
            remoteProfileModel.setCreateDirectoryInSI(TRUE);
        }
        if (remoteProfileModel.getHubInfo()) {
            /*Create or Update MailBoxes into SI*/
            if (remoteProfileModel.getCreateDirectoryInSI() || remoteProfileModel.getPoolingInterval().equals("ON")) {
                sterlingMailboxService.createAll(convertRemoteProfileModelToMbxMailBoxModels.apply(remoteProfileModel));
            }
            /*Create or Update User into SI*/
            if (remoteProfileModel.getCreateUserInSI()) {
                boolean isPatch = isNotNull(remoteProfileModel.isPatch()) && remoteProfileModel.isPatch();
                yfsUserService.save(convertRemoteProfileModelToYfsUserModel.apply(remoteProfileModel), isUpdate, isPatch);

                /*Create or Update Virtual Root into SI*/
                if (remoteProfileModel.getCreateDirectoryInSI() || remoteProfileModel.getPoolingInterval().equals("ON")) {
                    mbxVirtualRootService.save(
                            isNotNull(remoteProfileModel.getProfileUserName()) ? remoteProfileModel.getProfileUserName() : remoteProfileModel.getUserName(),
                            isNotNull(remoteProfileModel.getVirtualRoot()) ? remoteProfileModel.getVirtualRoot() : findRootDirectory(remoteProfileModel.getSubscriberType().equals(TP) ?
                                            remoteProfileModel.getInDirectory() : remoteProfileModel.getOutDirectory(),
                                    remoteProfileModel.isUseBaseDirectoryForVirtualRoot()),
                            remoteProfileModel.isMergeUser()
                    );
                }
            }
            if (remoteProfileModel.getPoolingInterval().equals("ON")) {
                sterlingRoutingRuleService.save(
                        remoteProfileModel.getSubscriberType().equals(TP) ? remoteProfileModel.getInDirectory() : remoteProfileModel.getOutDirectory(),
                        remoteProfileModel.getCustomProfileName(),
                        isNotNull(oldProfileName) ? oldProfileName : remoteProfileModel.getProfileName(),
                        remoteProfileModel.getSubscriberType(),
                        null,
                        isNotNull(remoteProfileModel.getRoutingRuleName()) ? remoteProfileModel.getRoutingRuleName() : null);
            } else {
                sterlingRoutingRuleService.delete(oldProfileName);
            }
            return null;
        } else {
            return saveSterlingProfile(remoteProfileModel, protocol, oldProfileName, null, isUpdate);
        }
    }

    private String saveSterlingProfile(RemoteProfileModel remoteProfileModel, Protocol protocol, String oldProfileName, String oldAs2ProfileName, boolean isUpdate) {
        LOGGER.info("Save Sterling Profile");

        if ((!isUpdate && protocol.equals(SFG_SFTP) && remoteProfileModel.getHubInfo()) || (!isUpdate && (protocol.equals(SFG_FTP) || protocol.equals(SFG_FTPS) || protocol.equals(AS2)))) {
            duplicateCheck(oldProfileName);
        }

        String objectKey = getPrimaryKey.apply(SCI_TR_OBJ_PRE_APPEND, SCI_RANDOM_COUNT);
        String objectId;
        switch (protocol) {
            case AS2:
            case SFG_FTP:
            case SFG_FTPS:
                /*Declare the main keys which will be used in all Tables*/
                String transportKey;
                String transportEntityId;
                String transportObjectId;
                String sciDocExchangeObj;
                String sciDocExchangeKey;
                String corporateAddressKey;
                String sciDeliveryChanKey;
                String sciDeliveryChanObjectId;
                String sciDelChanObjectName;
                String sciProfileKey;
                String sciProfileObjectId;
                String sciPackagingKey;
                String sciPackagingObjectId;

                String dbPassword;
                String dbKeyPassphrase;

                if (isUpdate) {
                    //=====================================
                    Optional<SciProfileEntity> sciProfileOptional;
                    if (protocol.getProtocol().equals(AS2.getProtocol())) {
                        sciProfileOptional =
                                sciProfileServiceDup.findFirstByObjectName(isNotNull(oldAs2ProfileName) ? oldAs2ProfileName : PROFILE + oldProfileName);
                    } else {
                        sciProfileOptional =
                                sciProfileServiceDup.findById(remoteProfileModel.getSciProfileObjectId());
                    }

                    //START: SFG Support
                    if (sfgV6updatable && !sciProfileOptional.isPresent()) {
                        sciProfileOptional =
                                sciProfileServiceDup.findFirstByObjectName(isNotNull(oldAs2ProfileName) ? oldAs2ProfileName : oldProfileName + _CONSUMER);
                    }
                    //END: SFG Support

                    if (sciProfileOptional.isPresent()) {
                        transportEntityId = sciProfileOptional.get().getEntityId();
                        sciProfileObjectId = sciProfileOptional.get().getObjectId();
                        sciProfileKey = sciProfileOptional.get().getProfileKey();
                        sciPackagingObjectId = sciProfileOptional.get().getPackagingId().trim();
                        sciDeliveryChanObjectId = sciProfileOptional.get().getDelivChannelId();
                    } else {
                        throw internalServerError("SciProfile Record not found in Sterling Integrator");
                    }

                    //==================


                    Optional<SciTransportEntity> sciTransportEntityOptional = sciTransportServiceDup.findFirstByEntityId(transportEntityId);

                    //START: SFG Support, TODO This block of code can be removed, need to test and confirm
                    LOGGER.info("SFG to V6 Updatable: {}", sfgV6updatable);
                    if (sfgV6updatable && !sciTransportEntityOptional.isPresent()) {
                        sciTransportEntityOptional = sciTransportServiceDup.get(oldProfileName + _CONSUMER);
                    } else if (!sfgV6updatable && !sciTransportEntityOptional.isPresent()) {
                        if (sciTransportServiceDup.get(oldProfileName + _CONSUMER).isPresent()) {
                            throw internalServerError("SFG Profile should not be updated through B2Bi, Please contact System Admin. ");
                        } else {
                            throw customError(NOT_FOUND.value(), "Transport profile not found in Sterling Integrator.");
                        }
                    }
                    //END: SFG Support

                    if (sciTransportEntityOptional.isPresent()) {
                        transportObjectId = sciTransportEntityOptional.get().getObjectId();
//                        transportEntityId = sciTransportEntityOptional.get().getEntityId();
                        transportKey = sciTransportEntityOptional.get().getTransportKey();
                        dbPassword = sciTransportEntityOptional.get().getTranspActPwd();
                        dbKeyPassphrase = sciTransportEntityOptional.get().getKeyCertPassword();
                        if (protocol.getProtocol().equals(AS2.getProtocol()) && !isNotNull(dbKeyPassphrase) && !isNotNull(remoteProfileModel.getKeyCertificatePassphrase())) {
                            remoteProfileModel.setKeyCertificatePassphrase(generateValidPassword());
                        }

                        String orgName;
                        if (remoteProfileModel.getProtocol().equals(Protocol.AS2.getProtocol())
                                || remoteProfileModel.getProtocol().equals(Protocol.SFG_FTP.getProtocol())
                                || remoteProfileModel.getProtocol().equals(Protocol.SFG_FTPS.getProtocol())
                                || remoteProfileModel.getProtocol().equals(Protocol.SFG_SFTP.getProtocol())) {
                            orgName = remoteProfileModel.getProfileId();
                        } else {
                            orgName = remoteProfileModel.getProfileName();
                        }

                        //By default, SI will take first 24 chars from partner name for ORG Name, so we are validating here
                        if (yfsOrganizationServiceDup.findAllByOrganizationName(orgName.substring(0, Math.min(orgName.length(), 24))).size() > 1) {
                            throw internalServerError("Trying to create duplicate organization profile, orgName : " + orgName);
                        }

                        Optional<YfsOrganizationDupEntity> yfsOrganizationEntityOptional = yfsOrganizationServiceDup.get(transportEntityId);
                        if (yfsOrganizationEntityOptional.isPresent()) {
                            corporateAddressKey = yfsOrganizationEntityOptional.get().getCorporateAddressKey();
                        } else {
                            throw internalServerError("YfsOrganization Record not found in Sterling Integrator");
                        }


                        Optional<SciDelivChanEntity> sciDelivChanOptional = sciDeliveryChangeServiceDup.findByObjectId(sciDeliveryChanObjectId);
                        if (sciDelivChanOptional.isPresent()) {
                            sciDelChanObjectName = sciDelivChanOptional.get().getObjectName();
                            sciDeliveryChanKey = sciDelivChanOptional.get().getDeliveryChannelKey();
                            sciDocExchangeObj = sciDelivChanOptional.get().getDocExchangeId();
                        } else {
                            throw internalServerError("SciDeliveryChain Record not found in Sterling Integrator");
                        }

                        Optional<SciDocExchangeEntity> sciDocExchangeOptional = sciDocExchangeServiceDup.findById(sciDocExchangeObj);
                        if (sciDocExchangeOptional.isPresent()) {
                            sciDocExchangeKey = sciDocExchangeOptional.get().getDocExchangeKey();
                        } else {
                            throw internalServerError("DocExchange Record not found in Sterling Integrator");
                        }

                        Optional<SciPackagingEntity> sciPackagingOptional = sciPackagingServiceDup.get(sciPackagingObjectId);
                        if (sciPackagingOptional.isPresent()) {
                            sciPackagingKey = sciPackagingOptional.get().getPackagingKey();
                        } else {
                            throw internalServerError("Packaging Record not found in Sterling Integrator");
                        }

                    } else {
                        throw internalServerError("Transport profile not found in Sterling Integrator");
                    }

                } else {
                    duplicateCheck(remoteProfileModel);
                    transportObjectId = objectKey + ":-3afb";
                    transportKey = objectKey + "-4trk";
                    transportEntityId = objectKey + ":-3b0a";

                    sciDocExchangeObj = objectKey + ":-3afc";
                    sciDocExchangeKey = objectKey + "-4dek";

                    corporateAddressKey = getPrimaryKey.apply("", 23);

                    sciProfileObjectId = objectKey + ":-3afe";
                    sciProfileKey = getPrimaryKey.apply("", 23);

                    sciDeliveryChanObjectId = objectKey + ":-3afa";
                    sciDeliveryChanKey = objectKey + "-4dck";
                    sciDelChanObjectName = DELIVERY + "_" + getPrimaryKey.apply("", 15);

                    sciPackagingObjectId = objectKey + ":-3afd";
                    sciPackagingKey = getPrimaryKey.apply("", 23);
                    dbPassword = null;
                    dbKeyPassphrase = null;
                    if (protocol.getProtocol().equals(AS2.getProtocol()) && !isNotNull(remoteProfileModel.getKeyCertificatePassphrase())) {
                        remoteProfileModel.setKeyCertificatePassphrase(generateValidPassword());
                    }
                }

                objectId = sciProfileObjectId;
                sciTransportServiceDup.save(convertRemoteFtpModelToTransportDTO.apply(remoteProfileModel)
                        .setTransportKey(transportKey)
                        .setObjectId(transportObjectId)
                        .setEntityId(transportEntityId)
                        .setPassword(getEncPassword(dbPassword, remoteProfileModel.getPassword(), isUpdate))
                        .setKeyCertificatePassphrase(getEncPassword(dbKeyPassphrase, remoteProfileModel.getKeyCertificatePassphrase(), isUpdate))
                        .setProtocol(protocol));

                yfsOrganizationServiceDup.save(convertRemoteFtpModelToYfsOrganizationDTO.apply(remoteProfileModel)
                        .setObjectId(transportEntityId)
                        .setCorporateAddressKey(corporateAddressKey)
                        .setProtocol(protocol));

                if (isNotNull(corporateAddressKey)) {
                    yfsPersonInfoService.save(convertRemoteFtpModelToYfsPersonInfo.apply(remoteProfileModel)
                            .setPersonInfoKey(corporateAddressKey));
                }

                sciProfileServiceDup.save(convertRemoteFtpModelToProfileDTO.apply(remoteProfileModel)
                        .setObjectId(sciProfileObjectId)
                        .setEntityId(transportEntityId)
                        .setPackagingId(sciPackagingObjectId)
                        .setDelChannelId(sciDeliveryChanObjectId)
                        .setProfileKey(sciProfileKey)
                        .setProtocol(protocol));
                sciPackagingServiceDup.save(convertRemoteFtpModelToPackagingDTO.apply(remoteProfileModel)
                        .setObjectId(sciPackagingObjectId)
                        .setEntityId(transportEntityId)
                        .setPackagingKey(sciPackagingKey)
                        .setProtocol(protocol));
                sciDeliveryChangeServiceDup.save(convertRemoteFtpModelToDeliveryChanDTO.apply(remoteProfileModel)
                        .setObjectId(sciDeliveryChanObjectId)
                        .setObjectName(sciDelChanObjectName)
                        .setEntityId(transportEntityId)
                        .setTransportId(transportObjectId)
                        .setDocExchangeId(sciDocExchangeObj)
                        .setDeliveryChannelKey(sciDeliveryChanKey)
                        .setProtocol(protocol));
                sciDocExchangeServiceDup.save(convertRemoteFtpModelToDocExchangeDTO.apply(remoteProfileModel)
                        .setObjectId(sciDocExchangeObj)
                        .setEntityId(transportEntityId)
                        .setDocExchangeKey(sciDocExchangeKey)
                        .setProtocol(protocol));
                if (protocol.equals(SFG_FTPS)) {
                    if (isUpdate) {
                        sciTrpSslCertService.deleteAllByTransportId(transportObjectId);
                        sciTranspCaCertServiceDup.deleteAllByTransportId(transportObjectId);
                    }
                    AtomicInteger atomicInteger = new AtomicInteger(0);
                    /*Saving Key Certificates*/
                    remoteProfileModel.getKeyCertificateNames().forEach(certObject -> {
                        if (hasLength(certObject.getName())) {
                            CertsAndPriKeyEntity certsAndPriKeyEntity = certsAndPriKeyService.findByName(certObject.getName())
                                    .orElseThrow(() -> internalServerError("Provided Key Cert is not found, CertName : " + certObject.getName()));
                            sciTrpSslCertService.save(convertRemoteFtpModelToSciTrpSslCert.apply(remoteProfileModel)
                                    .setTransportId(transportObjectId)
                                    .setKeyCertId(certsAndPriKeyEntity.getObjectId())
                                    .setCertificateKey(getPrimaryKey.apply("", 23))
                                    .setGoLiveDate(certsAndPriKeyEntity.getNotBefore())
                                    .setNotAfterDate(certsAndPriKeyEntity.getNotAfter())
                                    .setCertOrder(atomicInteger.getAndIncrement()));
                        }
                    });
                    atomicInteger.set(0);
                    /*Saving CA Certificates*/
                    remoteProfileModel.getCaCertificateNames().forEach(certObject -> {
                        if (hasLength(certObject.getName())) {
                            //Saving CaCertificate
                            CaCertInfoEntity caCertInfoEntity = caCertInfoService.getCaCertInfoByName(certObject.getName())
                                    .orElseThrow(() -> internalServerError("Provided CA Cert is not found, CertName : " + certObject.getName()));
                            sciTranspCaCertServiceDup.save(convertRemoteFtpModelToCaCert.apply(remoteProfileModel)
                                    .setTransportId(transportObjectId)
                                    .setKeyCertId(caCertInfoEntity.getObjectId())
                                    .setCertificateKey(getPrimaryKey.apply("", 23))
                                    .setGoLiveDate(caCertInfoEntity.getNotBefore())
                                    .setNotAfterDate(caCertInfoEntity.getNotAfter())
                                    .setCertOrder(atomicInteger.getAndIncrement()));
                        }
                    });
                } else if (protocol.equals(AS2)) {
                    as2ProfileServiceDup.save(convertRemoteProfileModelToAs2ProfileDTO.apply(remoteProfileModel).setProfileId(sciProfileObjectId));
                    if (remoteProfileModel.getHubInfo()) {
                        as2EmailInfoServiceDup.save(
                                convertRemoteProfileModelToAs2EmailInfoDTO.apply(remoteProfileModel)
                                        .setEntityId(transportEntityId)
                                        .setProfileId(sciProfileObjectId)
                        );
                    }
                    saveAs2Certificates(remoteProfileModel, transportObjectId, sciDocExchangeObj, isUpdate);
                }
                break;
            case SFG_SFTP:
                String sftpProfileId;
                String authType = isNullThrowError.apply(remoteProfileModel.getPreferredAuthenticationType(), "PreferredAuthenticationType");

                /*Condition Checking*/
                if (authType.equalsIgnoreCase(PASSWORD)) {
                    isNullThrowError.apply(remoteProfileModel.getPassword(), "password, because password field is mandatory when Authentication type is password.");
                } else if (authType.equalsIgnoreCase("PUBLIC_KEY") || authType.equalsIgnoreCase("PUBLIC KEY")
                        || authType.equalsIgnoreCase("PUBLICKEY")) {
                    isNullThrowError.apply(remoteProfileModel.getUserIdentityKey(), "UserIdentityKey, because UserIdentityKey field is mandatory when AuthenticationType is :" + authType);
                    if (remoteProfileModel.getKnownHostKeyNames()
                            .stream().noneMatch(communityManagerNameModel -> isNotNull(communityManagerNameModel.getName()))) {
                        throw internalServerError("Provide at least one KnownHostKey when AuthenticationType is " + authType);
                    }
                    remoteProfileModel.setPreferredAuthenticationType("publickey");
                } else {
                    throw internalServerError("Authentication type should match with (CaseInsensitive) : ['PASSWORD', 'PUBLIC_KEY', 'PUBLIC KEY'");
                }

                if (isUpdate) {
                    SftpProfileEntity sftpProfileEntity = sftpProfileService.getByName(oldProfileName).orElseThrow(() -> internalServerError("SftpProfile Record is not found"));
                    sftpProfileId = sftpProfileEntity.getProfileId();
                    remoteProfileModel.setPassword(getEncPassword(isNotNull(sftpProfileEntity.getRemotePassword()) ? sftpProfileEntity.getRemotePassword() : "", remoteProfileModel.getPassword(), isUpdate));
//                    if (authType.equalsIgnoreCase(PASSWORD)) {
//                        remoteProfileModel.setPassword(getEncPassword(sftpProfileEntity.getRemotePassword().trim(), remoteProfileModel.getPassword()));
//                    } else {
//                        remoteProfileModel.setPassword("");
//                    }
                } else {
                    sftpProfileId = objectKey + ":node1";
                    if (authType.equalsIgnoreCase(PASSWORD)) {
                        remoteProfileModel.setPassword(getEncPassword(null, remoteProfileModel.getPassword(), isUpdate));
                    } else {
                        remoteProfileModel.setPassword("");
                    }
                }
                objectId = sftpProfileId;

                sftpProfileService.save(remoteProfileModel, sftpProfileId);
                //TODO: Need to validate the KHost key available or not
                sftpProfileXrefKhostKeyService.saveAll(sftpProfileId, remoteProfileModel.getKnownHostKeyNames());
                break;
            default:
                throw internalServerError("The given Protocol should match with SFGFTP/SFGFTPS/SFGSFTP");
        }
        return objectId;
    }

    private void saveAs2Certificates(RemoteProfileModel remoteProfileModel, String transportObjectId, String sciDocExchangeObj, boolean isUpdate) {
        String exchangeKey;
        String signingKey;
        if (remoteProfileModel.getHubInfo()) {
            if (isUpdate) {
//                sciTrpKeyCertService.delteAllByTransportId(transportObjectId)
//                sciDocxKeyCertService.deleteAllByDocExchangeId(sciDocExchangeObj)
                Optional<SciTrpKeyCertEntity> sciTrpKeyCertEntityOptional = sciTrpKeyCertService.findByTransportId(transportObjectId);
                if (sciTrpKeyCertEntityOptional.isPresent() && isNotNull(sciTrpKeyCertEntityOptional.get().getCertificateKey())) {
                    exchangeKey = sciTrpKeyCertEntityOptional.get().getCertificateKey();
                } else {
                    exchangeKey = getPrimaryKey.apply(SCI_EX_CERT_PRE_APPEND, TP_PKEY_RANDOM_COUNT) + __AS2;
                }

                Optional<SciDocxKeyCertEntity> sciDocxKeyCertEntityOptional = sciDocxKeyCertService.findByDocExchangeId(sciDocExchangeObj);
                if (sciDocxKeyCertEntityOptional.isPresent() && isNotNull(sciDocxKeyCertEntityOptional.get().getCertificateKey())) {
                    signingKey = sciDocxKeyCertEntityOptional.get().getCertificateKey();
                } else {
                    signingKey = getPrimaryKey.apply(SCI_CERT_KEY_PRE_APPEND, TP_PKEY_RANDOM_COUNT) + __AS2;
                }
            } else {
                exchangeKey = getPrimaryKey.apply(SCI_EX_CERT_PRE_APPEND, TP_PKEY_RANDOM_COUNT) + __AS2;
                signingKey = getPrimaryKey.apply(SCI_CERT_KEY_PRE_APPEND, TP_PKEY_RANDOM_COUNT) + __AS2;
            }
            Optional<CertsAndPriKeyEntity> certsAndPriKeyEntityOptional = certsAndPriKeyService.findById(remoteProfileModel.getExchangeCertificateId().trim());
            if (certsAndPriKeyEntityOptional.isPresent()) {
                sciTrpKeyCertService.save(transportObjectId, certsAndPriKeyEntityOptional.get(), exchangeKey);
            } else {
                throw internalServerError("Provided Exchange Key is not available, name : " + remoteProfileModel.getExchangeCertificateId());
            }
            Optional<CertsAndPriKeyEntity> certsAndPriKeyEntityOptional1 = certsAndPriKeyService.findById(remoteProfileModel.getSigningCertificationId().trim());
            if (certsAndPriKeyEntityOptional1.isPresent()) {
                sciDocxKeyCertService.save(sciDocExchangeObj, certsAndPriKeyEntityOptional1.get(), signingKey);
            } else {
                throw internalServerError("Provided Signing Key is not available, name : " + remoteProfileModel.getSigningCertificationId());
            }
        } else {
            String caCertKey = "";
            if (isUpdate) {
                sciTranspCaCertServiceDup.deleteAllByTransportId(transportObjectId);
//                            sciDocxUserCertService.deleteAllByDocExchangeId(sciDocExchangeObj)
//                            sciTranspCaCertServiceDup.deleteAllByTransportId(transportObjectId)
                Optional<SciTrpUserCertEntity> sciTrpUserCertEntityOptional = sciTrpUserCertService.findByTransportId(transportObjectId);
                if (sciTrpUserCertEntityOptional.isPresent() && isNotNull(sciTrpUserCertEntityOptional.get().getCertificateKey())) {
                    exchangeKey = sciTrpUserCertEntityOptional.get().getCertificateKey();
                } else {
                    exchangeKey = getPrimaryKey.apply(SCI_EX_CERT_PRE_APPEND, TP_PKEY_RANDOM_COUNT) + __AS2;
                }
                Optional<SciDocxUserCertEntity> sciDocxUserCertEntityOptional = sciDocxUserCertService.findByDocExchangeId(sciDocExchangeObj);
                if (sciDocxUserCertEntityOptional.isPresent() && isNotNull(sciDocxUserCertEntityOptional.get().getCertificateKey())) {
                    signingKey = sciDocxUserCertEntityOptional.get().getCertificateKey();
                } else {
                    signingKey = getPrimaryKey.apply(SCI_SI_CERT_PRE_APPEND, TP_PKEY_RANDOM_COUNT) + __AS2;
                }
                if (isNotNull(remoteProfileModel.getCaCertificateName())) {
                    Optional<SciTranspCaCertEntity> sciTranspCaCertEntity = sciTranspCaCertServiceDup.findByTransportId(transportObjectId);
                    if (sciTranspCaCertEntity.isPresent()) {
                        caCertKey = sciTranspCaCertEntity.get().getTransportCertificateKey();
                    } else {
                        caCertKey = getPrimaryKey.apply("", 23);
                    }
                }

            } else {
                caCertKey = getPrimaryKey.apply("", 23);
                exchangeKey = getPrimaryKey.apply(SCI_EX_CERT_PRE_APPEND, TP_PKEY_RANDOM_COUNT) + __AS2;
                signingKey = getPrimaryKey.apply(SCI_SI_CERT_PRE_APPEND, TP_PKEY_RANDOM_COUNT) + __AS2;
            }
            if (isNotNull(remoteProfileModel.getExchangeCertificateId())) {
                Optional<TrustedCertInfoEntity> trustedCertInfoEntityOptional =
                        trustedCertInfoService.findById(remoteProfileModel.getExchangeCertificateId().trim());
                if (trustedCertInfoEntityOptional.isPresent()) {
                    sciTrpUserCertService.save(transportObjectId, trustedCertInfoEntityOptional.get(), exchangeKey);
                } else {
                    throw internalServerError("Provided Exchange Key is not available, name : " + remoteProfileModel.getExchangeCertificateId());
                }
            }

            Optional<TrustedCertInfoEntity> trustedCertInfoEntityOptional1 =
                    trustedCertInfoService.findById(remoteProfileModel.getSigningCertificationId().trim());
            if (trustedCertInfoEntityOptional1.isPresent()) {
                sciDocxUserCertService.save(sciDocExchangeObj, trustedCertInfoEntityOptional1.get(), signingKey);
            } else {
                throw internalServerError("Provided Signing Key is not available, name : " + remoteProfileModel.getSigningCertificationId());
            }

            //Saving CaCertificate
            if (isNotNull(remoteProfileModel.getCaCertificateName())) {
                CaCertInfoEntity caCertInfoEntity = caCertInfoService.getCaCertInfoByName(remoteProfileModel.getCaCertificateName().trim())
                        .orElseThrow(() -> internalServerError("Provided CA Cert is not found, CertName : " + remoteProfileModel.getCaCertificateName().trim()));
                sciTranspCaCertServiceDup.save(convertRemoteFtpModelToCaCert.apply(remoteProfileModel)
                        .setTransportId(transportObjectId)
                        .setKeyCertId(caCertInfoEntity.getObjectId())
                        .setCertificateKey(caCertKey)
                        .setGoLiveDate(caCertInfoEntity.getNotBefore())
                        .setNotAfterDate(caCertInfoEntity.getNotAfter())
                        .setCertOrder(1));
            }
        }
    }

    private String getEncPassword(String dbPassword, String password, boolean isUpdate) {
        if (isNotNull(dbPassword)) {
            if (isNotNull(password)) {
                if (password.equalsIgnoreCase(PRAGMA_EDGE_S)) {
                    return dbPassword;
                } else {
                    return getEncPasswordFromAPI(password.trim());
                }
            } else {
                return dbPassword;
            }
        } else {
            if (isNotNull(password)) {
                if (!isUpdate && password.equalsIgnoreCase(PRAGMA_EDGE_S)) {
                    throw internalServerError("Provided password is not allowed, Please try again with a different password.");
                } else if (isUpdate && password.equalsIgnoreCase(PRAGMA_EDGE_S)) {
                    return "";
                }
                return getEncPasswordFromAPI(password.trim());
            }
            return "";
        }
    }

    public void deleteSterlingProfiles(String profileName, String as2IdentifierName, Protocol protocol) {
        switch (protocol) {
            case AS2:
            case SFG_FTP:
            case SFG_FTPS:
                LOGGER.info("Delete {}", protocol.getProtocol());
                sciTransportServiceDup.get(TRANSPORT + profileName).ifPresent(sciTransportEntity -> {
                    yfsOrganizationServiceDup.get(sciTransportEntity.getEntityId()).ifPresent(yfsOrganizationEntity -> {
                        yfsPersonInfoService.delete(yfsOrganizationEntity.getCorporateAddressKey());
                        yfsOrganizationServiceDup.deleteByObjectId(sciTransportEntity.getEntityId());
                    });

                    sciProfileServiceDup.findFirstByObjectNameAndEntityId(isNotNull(as2IdentifierName) ? as2IdentifierName : PROFILE + profileName, sciTransportEntity.getEntityId()).ifPresent(sciProfileEntity -> {
                        sciPackagingServiceDup.delete(sciProfileEntity.getPackagingId());
                        sciDeliveryChangeServiceDup.findByObjectId(sciProfileEntity.getDelivChannelId()).ifPresent(sciDelivChan -> {
                            sciDocExchangeServiceDup.delete(sciDelivChan.getDocExchangeId());
                            sciDeliveryChangeServiceDup.delete(sciProfileEntity.getDelivChannelId());
                            if (protocol.getProtocol().equals(AS2.getProtocol())) {
                                sciTrpKeyCertService.delteAllByTransportId(sciTransportEntity.getObjectId());
                                sciDocxKeyCertService.deleteAllByDocExchangeId(sciDelivChan.getDocExchangeId());
                                sciTrpUserCertService.deleteAllByTransportId(sciTransportEntity.getObjectId());
                                sciDocxUserCertService.deleteAllByDocExchangeId(sciDelivChan.getDocExchangeId());
                                sciTranspCaCertServiceDup.deleteAllByTransportId(sciTransportEntity.getObjectId());
                            }
                        });
                        if (protocol.getProtocol().equals(AS2.getProtocol())) {
                            as2EmailInfoServiceDup.delete(sciProfileEntity.getObjectId(), sciTransportEntity.getEntityId());
                            as2ProfileServiceDup.delete(sciProfileEntity.getObjectId());
                        }
                        sciProfileServiceDup.deleteByEntityId(sciTransportEntity.getEntityId());
                    });

                    sciTrpSslCertService.deleteAllByTransportId(sciTransportEntity.getObjectId());
                    sciTranspCaCertServiceDup.deleteAllByTransportId(sciTransportEntity.getObjectId());

                    sciTransportServiceDup.delete(sciTransportEntity.getTransportKey());

                });
                break;
            case SFG_SFTP:
                sftpProfileService.getByName(profileName).ifPresent(sftpProfileEntity -> {
                    sftpProfileXrefKhostKeyService.deleteAllByProfileId(sftpProfileEntity.getProfileId());
                    sftpProfileService.delete(sftpProfileEntity);
                    LOGGER.info("Deleting SFTP profile");
                });
                break;
            default:
                //No need to do any operation
                break;
        }
    }

    public void delete(String profileName,
                       String as2Identifier,
                       Protocol protocol,
                       boolean hubInfo,
                       boolean deleteUser,
                       String userName,
                       boolean deleteMailbox,
                       String mailbox1,
                       String mailbox2) {

        if (deleteMailbox) {
            if (isNotNull(mailbox1)) {
                deleteMailbox(mailbox1);
            }
            if (isNotNull(mailbox2)) {
                deleteMailbox(mailbox2);
            }
        }
        if (hubInfo) {
            if (deleteUser && isNotNull(userName)) {
                deleteUser(userName);
            }
        } else {
            deleteSterlingProfiles(profileName, as2Identifier, protocol);
        }

    }

    public void deleteMailbox(String mailbox) {
        sterlingMailboxService.delete(mailbox);
    }

    public void deleteUser(String userName) {
        yfsUserService.delete(userName);
        mbxVirtualRootService.delete(userName);
    }

    private void duplicateCheck(String profileName) {
        sciTransportServiceDup.get(TRANSPORT + profileName).ifPresent(sciTransportEntity -> {
            throw internalServerError("Sterling Profile Already Exist.");
        });
    }

    private void duplicateCheck(RemoteProfileModel remoteProfileModel) {
        duplicateCheck(remoteProfileModel.getCustomProfileName());
        String orgName;
        if (remoteProfileModel.getProtocol().equals(Protocol.AS2.getProtocol())
                || remoteProfileModel.getProtocol().equals(Protocol.SFG_FTP.getProtocol())
                || remoteProfileModel.getProtocol().equals(Protocol.SFG_FTPS.getProtocol())
                || remoteProfileModel.getProtocol().equals(Protocol.SFG_SFTP.getProtocol())) {
            orgName = remoteProfileModel.getProfileId();
        } else {
            orgName = remoteProfileModel.getProfileName();
        }
        yfsOrganizationServiceDup.findFirstByOrganizationName(orgName.substring(0, Math.min(orgName.length(), 24))).ifPresent(yfsOrganizationDupEntity -> {
            throw internalServerError("Organization name is already available, name : " + orgName);
        });

    }

    public RemoteProfileModel get(RemoteProfileModel remoteProfileModel) {
        if (!remoteProfileModel.getHubInfo()) {
            getSterlingProfile(remoteProfileModel);
        }
        return remoteProfileModel;
    }

    public void get(As2Model as2Model) {
        RemoteProfileModel remoteProfileModel = new RemoteProfileModel();
        String customProfileName = as2Model.getCustomProfileName();
        remoteProfileModel.setCustomProfileName(as2Model.getProfileId() + "_" + as2Model.getCustomProfileName());
        remoteProfileModel.setProfileName(as2Model.getProfileName());
        remoteProfileModel.setHubInfo(as2Model.getHubInfo());
        remoteProfileModel.setProtocol(AS2.getProtocol());
        remoteProfileModel.setSubscriberType("TP");
        getSterlingProfile(remoteProfileModel);
        convertRemoteProfileModelToAs2Model.apply(remoteProfileModel, as2Model);
        remoteProfileModel.setCustomProfileName(customProfileName);
        convertAs2ValuesFromNumToString.apply(as2Model);
    }

    private void getSterlingProfile(RemoteProfileModel remoteProfileModel) {
        remoteProfileModel.setCustomProfileName(isNotNull(remoteProfileModel.getCustomProfileName()) ? remoteProfileModel.getCustomProfileName() : remoteProfileModel.getProfileName());
        Protocol protocol = Optional.ofNullable(Protocol.findProtocol(remoteProfileModel.getProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);
        switch (protocol) {
            case AS2:
            case SFG_FTP:
            case SFG_FTPS:
                Optional<TransportDTO> transportDTOOptional;
                Optional<SciProfileEntity> sciProfileOptional;
                if (protocol.getProtocol().equals(AS2.getProtocol())) {
                    //We handled CONSUMER inside the method
                    transportDTOOptional = sciTransportServiceDup.getTransportDTO(TRANSPORT + remoteProfileModel.getCustomProfileName());
                    if (transportDTOOptional.isPresent()) {
                        sciProfileOptional = sciProfileServiceDup.findByEntityId(transportDTOOptional.get().getEntityId());
                    } else {
                        throw notFound("Transport Entity not found.");
                    }
                } else {
                    sciProfileOptional = sciProfileServiceDup.findById(remoteProfileModel.getSciProfileObjectId());
                    if (sciProfileOptional.isPresent()) {
                        transportDTOOptional = sciTransportServiceDup.getTransportDTOByEntityId(sciProfileOptional.get().getEntityId());
                    } else {
                        throw internalServerError("SciProfileEntity Record notfound");
                    }
                }

                if (transportDTOOptional.isPresent()) {
                    convertTransportDTOToRemoteFtpModel.apply(remoteProfileModel, transportDTOOptional.get());
                    Optional<YfsOrganizationDupEntity> yfsOrganizationEntityOptional = yfsOrganizationServiceDup.get(transportDTOOptional.get().getEntityId());
                    if (yfsOrganizationEntityOptional.isPresent()) {
                        /*Need to get the HubInfo here*/
                        if (isNotNull(yfsOrganizationEntityOptional.get().getCorporateAddressKey())) {
                            convertYfsPersonInfoToRemoteFtpModel.apply(remoteProfileModel, yfsPersonInfoService.getYfsPersonInfoDTO(yfsOrganizationEntityOptional.get().getCorporateAddressKey()));
                        }
                        remoteProfileModel.setOrgIdentifier(yfsOrganizationEntityOptional.get().getIdentifier());
                    } else {
                        throw internalServerError("YfsOrganization Record notfound");
                    }
                    String sciDockExchangeObjectId;
                    if (sciProfileOptional.isPresent()) {

                        sciPackagingServiceDup.get(sciProfileOptional.get().getPackagingId()).ifPresent(sciPackaging -> {
                            remoteProfileModel.setPayloadType(sciPackaging.getPayloadType());
                            remoteProfileModel.setCompressData(sciPackaging.getCompressData());
                            remoteProfileModel.setMimeType(sciPackaging.getDefaultMimeType());
                            remoteProfileModel.setMimeSubType(sciPackaging.getDefMimeSubtype());
                        });

                        Optional<SciDelivChanEntity> sciDelivChanOptional = sciDeliveryChangeServiceDup.findByObjectId(sciProfileOptional.get().getDelivChannelId());
                        if (sciDelivChanOptional.isPresent()) {
                            remoteProfileModel.setMdn(convertNumberToBoolean.test(isNotNull(sciDelivChanOptional.get().getReceiptType()) ? sciDelivChanOptional.get().getReceiptType() : "0"));
                            remoteProfileModel.setMdnEncryption(sciDelivChanOptional.get().getRcptSigType());
                            remoteProfileModel.setMdnType(sciDelivChanOptional.get().getRcptDelivMode());
                            remoteProfileModel.setReceiptToAddress(sciDelivChanOptional.get().getReceiptToAddress());

                            DocExchangeDTO docExchangeDTO = sciDocExchangeServiceDup.getDocExchangeDTO(sciDelivChanOptional.get().getDocExchangeId());
                            convertDocExchangeDTOToRemoteFtpModel.apply(remoteProfileModel, docExchangeDTO);
                            sciDockExchangeObjectId = docExchangeDTO.getObjectId();
                        } else {
                            throw internalServerError("SciDeliveryChain Record notfound");
                        }
                    } else {
                        throw internalServerError("SciProfileEntity Record notfound");
                    }
                    if (protocol.getProtocol().equals(SFG_FTPS.getProtocol())) {
                        remoteProfileModel.setEncryptionStrength(transportDTOOptional.get().getCipherStrength());
                        sciTrpSslCertService.findAllByTransportId(transportDTOOptional.get().getObjectId())
                                .forEach(sciTrpSslCertEntity ->
                                        certsAndPriKeyService.findById(sciTrpSslCertEntity.getKeyCertId())
                                                .ifPresent(certsAndPriKeyEntity -> remoteProfileModel.getKeyCertificateNames().add(new CommunityManagerNameModel(certsAndPriKeyEntity.getName()))));
                        sciTranspCaCertServiceDup.findAllByTransportId(transportDTOOptional.get().getObjectId())
                                .forEach(sciTranspCaCertEntity ->
                                        caCertInfoService.getCaCertById(sciTranspCaCertEntity.getCaCertId())
                                                .ifPresent(caCertInfoEntity -> remoteProfileModel.getCaCertificateNames().add(new CommunityManagerNameModel(caCertInfoEntity.getName()))));
                    } else if (protocol.getProtocol().equals(AS2.getProtocol())) {
                        if (remoteProfileModel.getHubInfo()) {
                            sciTrpKeyCertService.get(transportDTOOptional.get().getObjectId())
                                    .ifPresent(sciTrpKeyCertEntity -> remoteProfileModel.setExchangeCertificateId(sciTrpKeyCertEntity.getKeyCertId()));
                            sciDocxKeyCertService.findByDocExchangeId(sciDockExchangeObjectId)
                                    .ifPresent(sciDocxKeyCertEntity -> remoteProfileModel.setSigningCertificationId(sciDocxKeyCertEntity.getKeyCertId()));
                        } else {
                            sciTrpUserCertService.findByTransportId(transportDTOOptional.get().getObjectId())
                                    .ifPresent(sciTrpUserCertEntity -> remoteProfileModel.setExchangeCertificateId(sciTrpUserCertEntity.getUserCertId()));
                            sciDocxUserCertService.get(sciDockExchangeObjectId)
                                    .ifPresent(sciDocxUserCertEntity -> remoteProfileModel.setSigningCertificationId(sciDocxUserCertEntity.getUserCertId()));
                            sciTranspCaCertServiceDup.findByTransportId(transportDTOOptional.get().getObjectId())
                                    .ifPresent(sciTranspCaCertEntity -> remoteProfileModel.setCaCertificateName(sciTranspCaCertEntity.getCaCertId()));
                        }
                    }
                } else {
                    throw notFound("Transport Entity not found.");
                }
                break;
            case SFG_SFTP:
                sftpProfileService.getByName(remoteProfileModel);
                remoteProfileModel.setKnownHostKeyNames(sftpProfileXrefKhostKeyService.findAllByProfileId(remoteProfileModel.getSiProfileId())
                        .stream()
                        .map(sftpProfileXrefKhostKeyEntity -> new CommunityManagerNameModel(sftpProfileXrefKhostKeyEntity.getSftpProfileXrefKhostKeyIdentity().getKhostKeyId()))
                        .collect(Collectors.toList()));
                if (remoteProfileModel.getKnownHostKeyNames() == null) {
                    remoteProfileModel.setKnownHostKeyNames(new ArrayList<>());
                    remoteProfileModel.getKnownHostKeyNames().add(new CommunityManagerNameModel(null));
                } else if (remoteProfileModel.getKnownHostKeyNames() != null && remoteProfileModel.getKnownHostKeyNames().isEmpty()) {
                    remoteProfileModel.getKnownHostKeyNames().add(new CommunityManagerNameModel());
                }
                break;
            default:
                throw internalServerError("Protocol was not matched with any Remote Protocol");
        }
        remoteProfileModel.setPassword(PRAGMA_EDGE_S);
    }

    public void checkSciProfileDup(String profileName) {
        sciProfileServiceDup.findFirstByObjectName(profileName).ifPresent(sciProfileEntity -> {
            throw conflict("Partner ( In Sterling )");
        });
    }

    private String getEncPasswordFromAPI(String plainText) {
        return sterlingDBPasswordService.encrypt(plainText);
    }


    @PostConstruct
    private void passphraseValidation() {

        if (passphraseValidation != null && passphraseValidation) {
            Optional<SftpProfileEntity> sftpProfileEntityOptional = sftpProfileService.getByName(passphraseValidationProfile);
            if (sftpProfileEntityOptional.isPresent()) {
                String pwd = sftpProfileEntityOptional.get().getRemotePassword();
                if (isNotNull(pwd)) {
                    try {
                        pwd = sterlingDBPasswordService.decrypt(pwd).trim();
                    } catch (Exception e) {
                        LOGGER.info("Unable to decrypt, Please check user passphrase in yml file");
                        initiateShutdown();
                    }
                    if (pwd.equals(passwordUtilityService.decrypt("bAKAbfGlmNV0AvvQN3hmgQ=="))) {
                        LOGGER.info("Passphrase matched with sterling config passphrase.");
                    } else {
                        LOGGER.info("passphrase not matched");
                        System.out.println("Passphrase not matched, please configure the right passphrase in YML file.");
                        initiateShutdown();
                    }
                } else {
                    System.out.println("provided profile have empty password");
                    LOGGER.info("provided profile have empty password");
                    initiateShutdown();
                }
            } else {
                System.out.println("Organization profile notfound");
                LOGGER.info("Organization profile notfound");
                initiateShutdown();
            }

        } else {
            LOGGER.info("skipped Passphrase checking");
            System.out.println("Skipped passphrase checking");
        }
    }

    private void initiateShutdown() {
        LOGGER.error("*************************************");
        LOGGER.error("     APPLICATION GETTING SHUTDOWN     ");
        LOGGER.error("*************************************");
        System.exit(SpringApplication.exit(appContext, (ExitCodeGenerator) () -> 0));
    }

}
