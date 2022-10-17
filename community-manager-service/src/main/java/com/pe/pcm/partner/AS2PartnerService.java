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

package com.pe.pcm.partner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pe.pcm.activity.ActivityHistoryService;
import com.pe.pcm.b2b.B2BApiService;
import com.pe.pcm.certificate.CaCertInfoService;
import com.pe.pcm.certificate.CertsAndPriKeyService;
import com.pe.pcm.certificate.TrustedCertInfoService;
import com.pe.pcm.miscellaneous.UserUtilityService;
import com.pe.pcm.protocol.As2RelationMapModel;
import com.pe.pcm.protocol.ManageProtocolService;
import com.pe.pcm.protocol.as2.*;
import com.pe.pcm.protocol.as2.certificate.*;
import com.pe.pcm.protocol.as2.si.entity.SciProfileEntity;
import com.pe.pcm.protocol.as2.si.entity.YFSOrganizationEntity;
import com.pe.pcm.utils.KeyGeneratorUtil;
import com.pe.pcm.utils.PCMConstants;
import com.pe.pcm.workflow.ProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.pe.pcm.exception.GlobalExceptionHandler.conflict;
import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;
import static com.pe.pcm.utils.PCMConstants.*;

@Service
public class AS2PartnerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AS2PartnerService.class);

    private final DeliveryChangeService deliveryChangeService;

    private final SciDocxKeyCertService sciDocxKeyCertService;

    private final DocExchangeService docExchangeService;

    private final PackagingService packagingService;


    private final ProfileService profileService;

    private final TransportService transportService;

    private final AS2ProfileService as2ProfileService;

    private YfsOrganizationService yfsOrganizationService;

    private TrustedCertInfoService trustedCertInfoService;

    private AS2EmailInfoService as2EmailInfoService;

    private CaCertInfoService caCertInfoService;

    private SciDocxUserCertService sciDocxUserCertService;

    private SciTrpUserCertService sciTrpUserCertService;

    private SciTranspCaCertService sciTranspCaCertService;

    private PartnerService partnerService;

    private ObjectMapper mapper = new ObjectMapper();

    private ActivityHistoryService activityHistoryService;

    private SciContractService sciContractService;

    private ProcessService processService;

    private Boolean isAs2B2bApiActive;

    private Boolean isB2bApiActive;

    private CertsAndPriKeyService certsAndPriKeyService;

    private SciTrpKeyCertService sciTrpKeyCertService;

    private UserUtilityService userUtilityService;

    private ManageProtocolService manageProtocolService;

    private B2BApiService b2BApiService;

    private String community;


    @Autowired
    public AS2PartnerService(DeliveryChangeService deliveryChangeService,
                             SciDocxKeyCertService sciDocxKeyCertService, DocExchangeService docExchangeService,
                             PackagingService packagingService, ProfileService profileService,
                             TransportService transportService, AS2ProfileService as2ProfileService,
                             YfsOrganizationService yfsOrganizationService, TrustedCertInfoService trustedCertInfoService,
                             SciDocxUserCertService sciDocxUserCertService, SciTrpUserCertService sciTrpUserCertService,
                             CaCertInfoService caCertInfoService, SciTranspCaCertService sciTranspCaCertService,
                             AS2EmailInfoService as2EmailInfoService, PartnerService partnerService, ActivityHistoryService activityHistoryService,
                             SciContractService sciContractService, ProcessService processService, @Value("${sterling-b2bi.b2bi-api.as2.active}") Boolean isAs2B2bApiActive,
                             @Value("${sterling-b2bi.b2bi-api.active}") Boolean isB2bApiActive, CertsAndPriKeyService certsAndPriKeyService, SciTrpKeyCertService sciTrpKeyCertService,
                             UserUtilityService userUtilityService, ManageProtocolService manageProtocolService, B2BApiService b2BApiService,
                             @Value("${sterling-b2bi.b2bi-api.b2bi-sfg-api.community-name}") String community) {

        this.deliveryChangeService = deliveryChangeService;
        this.sciDocxKeyCertService = sciDocxKeyCertService;
        this.docExchangeService = docExchangeService;
        this.packagingService = packagingService;
        this.profileService = profileService;
        this.transportService = transportService;
        this.as2ProfileService = as2ProfileService;
        this.yfsOrganizationService = yfsOrganizationService;
        this.trustedCertInfoService = trustedCertInfoService;
        this.sciDocxUserCertService = sciDocxUserCertService;
        this.sciTrpUserCertService = sciTrpUserCertService;
        this.caCertInfoService = caCertInfoService;
        this.sciTranspCaCertService = sciTranspCaCertService;
        this.as2EmailInfoService = as2EmailInfoService;
        this.partnerService = partnerService;
        this.activityHistoryService = activityHistoryService;
        this.sciContractService = sciContractService;
        this.processService = processService;
        this.isAs2B2bApiActive = isAs2B2bApiActive;
        this.isB2bApiActive = isB2bApiActive;
        this.certsAndPriKeyService = certsAndPriKeyService;
        this.sciTrpKeyCertService = sciTrpKeyCertService;
        this.userUtilityService = userUtilityService;
        this.manageProtocolService = manageProtocolService;
        this.b2BApiService = b2BApiService;
        this.community = community;
    }

    //
//    @Transactional
//    public String saveAs2(As2Model as2Model, Boolean isBulkUpload) {
//        String pkId;
//        if (!Optional.ofNullable(Protocol.findProtocol(as2Model.getProtocol())).isPresent()) {
//            throw unknownProtocol();
//        }
//        if (isBulkUpload) {
//            packagingService.get(true, as2Model.getProfileId()).ifPresent(sciPackagingEntity -> {
//                throw conflict("Partner ( In Sterling )");
//            });
//        } else {
//            partnerService.find(as2Model.getProfileId()).ifPresent(tradingPartnerEntity -> {
//                throw conflict(PARTNER);
//            });
//        }
//
//        if (isB2bApiActive) {
//            if (isAs2B2bApiActive) {
//                pkId = saveUsingB2bApi(as2Model);
//            } else {
//                pkId = saveUsingPcm(as2Model, isBulkUpload);
//            }
//        } else {
//            return saveUsingPcm(as2Model, isBulkUpload);
//        }
//        b2BApiService.createMailBoxInSI(true, "/AS2/" + as2Model.getProfileName().trim() + "/Inbound", "/AS2/" + as2Model.getProfileName().trim() + "/Outbound");
//        userUtilityService.addPartnerToCurrentUser(pkId, TP);
//        return pkId;
//    }
//
//    @Transactional
//    public void update(As2Model as2Model) {
//        applyAs2DefaultValues.apply(as2Model);
//        if (isB2bApiActive) {
//            if (isAs2B2bApiActive) {
//                updateUsingB2bApi(as2Model);
//            } else {
//                updateUsingPcm(as2Model);
//            }
//        } else {
//            updateUsingPcm(as2Model);
//        }
//        b2BApiService.createMailBoxInSI(true, "/AS2/" + as2Model.getProfileName().trim() + "/Inbound", "/AS2/" + as2Model.getProfileName().trim() + "/Outbound");
//    }
//
//    public As2Model get(String pkId) {
//        As2Model as2Model = new As2Model();
//        PartnerEntity partnerEntity = partnerService.get(pkId);
//        as2Model.setAddressLine1(partnerEntity.getAddressLine1());
//        as2Model.setAddressLine2(partnerEntity.getAddressLine2());
//        as2Model.setProfileName(partnerEntity.getTpName());
//        as2Model.setPhone(partnerEntity.getPhone());
//        as2Model.setEmailId(partnerEntity.getEmail());
//        as2Model.setProtocol(partnerEntity.getTpProtocol());
//        as2Model.setStatus(convertStringToBoolean(partnerEntity.getStatus()));
//        as2Model.setPkId(partnerEntity.getPkId());
//        as2Model.setPgpInfo(partnerEntity.getPgpInfo());
//        as2Model.setPemIdentifier(partnerEntity.getPemIdentifier());
//        getAs2(as2Model, pkId);
//        if (isB2bApiActive) {
//            if (isAs2B2bApiActive) {
//                getUsingB2bApi(as2Model);
//            } else {
//                getUsingPcm(as2Model);
//            }
//        } else {
//            getUsingPcm(as2Model);
//        }
//        return as2Model;
//    }
//
//    @Transactional
//    public void delete(String pkId) {
//        if (!processService.findByPartnerProfile(pkId).isEmpty()) {
//            throw internalServerError(TP_WORKFLOW_EXIST);
//        }
//        PartnerEntity partnerEntity = partnerService.get(pkId);
//        if (isB2bApiActive) {
//            if (isAs2B2bApiActive) {
//                deleteThroughB2bApi(partnerEntity);
//            } else {
//                deleteThroughPcm(partnerEntity);
//            }
//        } else {
//            deleteThroughPcm(partnerEntity);
//        }
//        partnerService.delete(partnerEntity);
//    }
//
//
//
////    private String saveUsingB2bApi(As2Model as2Model) {
////        applyAs2DefaultValues.apply(as2Model);
////        String parentPrimaryKey = KeyGeneratorUtil.getPrimaryKey.apply(TP_PKEY_PRE_APPEND,
////                TP_PKEY_RANDOM_COUNT);
////        String childPrimaryKey = KeyGeneratorUtil.getChildPrimaryKey(as2Model.getProtocol());
////        as2Service.saveFromB2bApi(as2Model, parentPrimaryKey, childPrimaryKey, FALSE, TP);
////        PartnerModel partnerModel = new PartnerModel();
////        copyProperties(as2Model, partnerModel);
////        partnerService.save(partnerModel, parentPrimaryKey, childPrimaryKey);
////        activityHistoryService.savePartnerActivity(parentPrimaryKey, PARTNER_CREATED);
////        return parentPrimaryKey;
////    }
//
//    private String saveUsingPcm(As2Model as2Model, Boolean isBulkUpload) {
//        String parentPrimaryKey = KeyGeneratorUtil.getPrimaryKey.apply(TP_PKEY_PRE_APPEND, TP_PKEY_RANDOM_COUNT);
//        String childPrimaryKey = KeyGeneratorUtil.getPrimaryKey.apply(PROTOCOL_AS2_PKEY_PRE_APPEND, PROTOCOL_PKEY_RANDOM_COUNT);
//        String transportObjectId = KeyGeneratorUtil.getPrimaryKey.apply(SCI_TR_OBJ_PRE_APPEND, SCI_RANDOM_COUNT);
//        saveAs2(applyAs2DefaultValues.apply(as2Model), parentPrimaryKey, childPrimaryKey, transportObjectId, isBulkUpload);
//        return parentPrimaryKey;
//    }
//
//    private void saveAs2(As2Model as2Model, String parentPrimaryKey, String childPrimaryKey, String transportObjectId, Boolean isBulkUpload) {
//        //Converting text values to number
//        convertAs2ValuesFromStringToNum.apply(as2Model);
//
//        transportService.save(transportObjectId, as2Model);
//        docExchangeService.save(transportObjectId, as2Model);
//        deliveryChangeService.save(transportObjectId, as2Model);
//        packagingService.save(transportObjectId, as2Model); // profile service - packaging_id
//        profileService.save(transportObjectId, as2Model);
//        yfsOrganizationService.save(transportObjectId, as2Model);
//        as2ProfileService.save(transportObjectId, as2Model);
//        as2EmailInfoService.save(transportObjectId, as2Model);
//
//
//        // Saving Exchange Certificate
//        saveExchangeCertificate(transportObjectId, as2Model.getExchangeCertificate().trim(), as2Model.getHubInfo());
//        // Saving Signing Certificate
//       // saveSigningCertificates(transportObjectId, as2Model.getSigningCertification().trim(), as2Model.getHubInfo());
//
//        //Saving CaCertificate
//        if (hasLength(as2Model.getCaCertificate())) {
//            caCertInfoService.getCaCertInfoByName(as2Model.getCaCertificate())
//                    .ifPresent(caCertInfoEntity -> sciTranspCaCertService.save(transportObjectId, caCertInfoEntity));
//        }
//        if (!as2Model.isOnlyPCM()) {
//            if (isNotNull(as2Model.getSIProfile())) {
//                RemoteAs2Profile remoteAs2Profile = new RemoteAs2Profile(as2Model,false,community);
//                b2BApiService.createRemoteAs2Profile(remoteAs2Profile);
//
//            }
//        }
//        if (!isBulkUpload) {
//            as2Service.save(parentPrimaryKey, childPrimaryKey, "TP", as2Model, transportObjectId);
//            PartnerModel partnerModel = new PartnerModel();
//            copyProperties(as2Model, partnerModel);
//            partnerService.save(partnerModel, parentPrimaryKey, childPrimaryKey);
//            activityHistoryService.savePartnerActivity(parentPrimaryKey, "Trading Partner created.");
//        }
//    }
//
//   /* private void createSfgProfile() {
//
//        logger.info("Creating SFG Profile");
//        String orgKey = isNotNull(sterlingProfilesModel.getPartnerCode()) ? sterlingProfilesModel.getPartnerCode() : sterlingProfilesModel.getProfileName();
//        manageSfgProfileService.save(
//                SterlingFunctions.convertSterlingProfilesModelToSfgProfileDetailsDTO.apply(sterlingProfilesModel)
//                        .setOrganizationKey(orgKey.substring(0, Math.min(orgKey.length(), 24)))
//                        .setTransportEntityKey(transportEntityId)
//                        .setSciProfileObjectId(sciProfileObjectId)
//                        .setCommunityName(community)
//                        .setCommunityId(getCommunityId(isNotNull(sterlingProfilesModel.getCommunityName()) ? sterlingProfilesModel.getCommunityName() : community))
//                        .setCommunityProfileId(getCommunityProfile(sterlingProfilesModel.getReceivingProtocol()))
//                        .setOnlyProducer(FALSE)
//        );
//
//    }*/
//
//    private void updateUsingB2bApi(As2Model as2Model) {
//        PartnerEntity oldPartnerEntity = SerializationUtils.clone(partnerService.get(as2Model.getPkId()));
//        String parentPrimaryKey = oldPartnerEntity.getPkId();
//        String childPrimaryKey = oldPartnerEntity.getPartnerProtocolRef();
//        Protocol protocol = Optional.ofNullable(Protocol.findProtocol(oldPartnerEntity.getTpProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);
//        Map<String, String> oldHttpEntityMap = mapper.convertValue(as2Service.get(parentPrimaryKey),
//                new TypeReference<Map<String, String>>() {
//                });
//        boolean isUpdate = true;
//        if (!protocol.getProtocol().equalsIgnoreCase(as2Model.getProtocol())) {
//            deleteThroughB2bApi(oldPartnerEntity);
//            isUpdate = false;
//        }
//        Map<String, String> newRemoteFtpEntityMap = mapper.convertValue(as2Service.saveFromB2bApi(as2Model, parentPrimaryKey, childPrimaryKey,
//                isUpdate, TP), new TypeReference<Map<String, String>>() {
//        });
//        PartnerModel partnerModel = new PartnerModel();
//        copyProperties(as2Model, partnerModel);
//        PartnerEntity newPartnerEntity = partnerService.save(partnerModel, parentPrimaryKey, childPrimaryKey);
//        activityHistoryService.updatePartnerActivity(oldPartnerEntity,
//                newPartnerEntity, oldHttpEntityMap, newRemoteFtpEntityMap, isUpdate);
//    }
//
//    private void updateUsingPcm(As2Model as2Model) {
//        String objectName = TRANSPORT + as2Model.getProfileId() + "_" + as2Model.getProfileName();
//        //Converting text values to number
//        convertAs2ValuesFromStringToNum.apply(as2Model);
//        PartnerEntity oldTradingPartnerEntity = SerializationUtils.clone(partnerService.get(as2Model.getPkId()));
//        String parentPrimaryKey = oldTradingPartnerEntity.getPkId();
//        String childPrimaryKey = oldTradingPartnerEntity.getPartnerProtocolRef();
//        Protocol protocol = Optional.ofNullable(Protocol.findProtocol(oldTradingPartnerEntity.getTpProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);
//        boolean isUpdate = Boolean.TRUE;
//        Map<String, String> oldAs2EntityMap = new LinkedHashMap<>();
//        if (!protocol.getProtocol().equalsIgnoreCase(as2Model.getProtocol())) {
//            manageProtocolService.deleteProtocol(oldTradingPartnerEntity.getTpProtocol(), oldTradingPartnerEntity.getPkId());
//            childPrimaryKey = getChildPrimaryKey(as2Model.getProtocol());
//            as2Service.delete(parentPrimaryKey);
//            isUpdate = Boolean.FALSE;
//        } else {
//            oldAs2EntityMap = mapper.convertValue(as2Service.get(parentPrimaryKey),
//                    new TypeReference<Map<String, String>>() {
//                    });
//        }
//        String sciProfileId = isNotNull(oldAs2EntityMap.get("sciProfileId")) ? oldAs2EntityMap.get("sciProfileId").split(":")[0] : "";
//        Map<String, String> newAs2EntityMap = mapper.convertValue(as2Service.save(parentPrimaryKey, childPrimaryKey, "TP", as2Model, sciProfileId),
//                new TypeReference<Map<String, String>>() {
//                });
//        Optional<SciTransportEntity> sciTransportOptional = transportService.get(objectName);
//        boolean finalIsUpdate = isUpdate;
//        sciTransportOptional.ifPresent(sciTransportEntity -> {
//
//            String transportObjectId = sciTransportEntity.getObjectId().split(":")[0];
//            transportService.update(sciTransportEntity, as2Model);
//            docExchangeService.update(sciTransportEntity.getEntityId().trim(), as2Model).ifPresent(sciDocExchange ->
//                    // Update Signing Certificate
//                    updateSigningCertificates(sciDocExchange.getObjectId().trim(), as2Model.getSigningCertification().trim(), as2Model.getHubInfo())
//            );
//
//            // Update Exchange Certificate
//            updateExchangeCertificate(sciTransportEntity.getObjectId().trim(), as2Model.getExchangeCertificate().trim(), as2Model.getHubInfo());
//
//            deliveryChangeService.update(sciTransportEntity.getObjectId().trim(), as2Model);
//
//            profileService.findByEntityId(sciTransportEntity.getEntityId().trim()).ifPresent(sciProfile ->
//                    packagingService.update(sciProfile.getPackagingId(), as2Model)
//            );
//
//            as2ProfileService.update(sciTransportEntity.getObjectId(), as2Model);
//
//
//            as2EmailInfoService.update(transportObjectId, as2Model);
//            yfsOrganizationService.update(sciTransportEntity.getEntityId().trim(), as2Model);
//
//
//            //Update CaCertificate
//            if (hasLength(as2Model.getCaCertificate())) {
//                caCertInfoService.getCaCertInfoByName(as2Model.getCaCertificate())
//                        .ifPresent(caCertInfoEntity -> sciTranspCaCertService.update(sciTransportEntity.getObjectId().trim(), caCertInfoEntity));
//            }
//        });
//        PartnerModel partnerModel = new PartnerModel();
//        copyProperties(as2Model, partnerModel);
//        PartnerEntity newPartnerEntity = partnerService.save(partnerModel, parentPrimaryKey, childPrimaryKey);
//        activityHistoryService.updatePartnerActivity(oldTradingPartnerEntity, newPartnerEntity, oldAs2EntityMap, newAs2EntityMap, finalIsUpdate);
//    }
//
//    private void getUsingPcm(As2Model as2Model) {
//        String objectName = TRANSPORT + as2Model.getProfileId() + "_" + as2Model.getProfileName();
//        Optional<SciTransportEntity> sciTransportOptional = transportService.get(objectName);
//        if (sciTransportOptional.isPresent()) {
//            SciTransportEntity sciTransportEntity = sciTransportOptional.get();
//            as2Model.setEndPoint(sciTransportEntity.getEndPoint());
//            as2Model.setSslType(sciTransportEntity.getSslOption());
//            as2Model.setCipherStrength(sciTransportEntity.getCipherStrength());
//            as2Model.setResponseTimeout(isNotNull(sciTransportEntity.getResponseTimeout()) ? Integer.valueOf(sciTransportEntity.getResponseTimeout()) : 0);
//
//            docExchangeService.findByEntityId(sciTransportEntity.getEntityId().trim()).ifPresent(sciDocExchange -> {
//                // Get Signing Certificate and Add to Model
//                if (as2Model.getHubInfo()) {
//                    sciDocxKeyCertService.findByDocExchangeId(sciDocExchange.getObjectId())
//                            .ifPresent(sciDocxKeyCertEntity -> as2Model.setSigningCertification(sciDocxKeyCertEntity.getKeyCertId()));
//                } else {
//                    sciDocxUserCertService.findByDocExchangeId(sciDocExchange.getObjectId())
//                            .ifPresent(sciDocxUserCertEntity -> as2Model.setSigningCertification(sciDocxUserCertEntity.getUserCertId()));
//                }
//                as2Model.setEncryptionAlgorithm(sciDocExchange.getEnvEncryptAlg());
//                as2Model.setSignatureAlgorithm(sciDocExchange.getMsgSigningAlg());
//            });
//
//            deliveryChangeService.findByTransportId(sciTransportEntity.getObjectId().trim()).ifPresent(sciDelivChan -> {
//                as2Model.setMdn(convertNumberToBoolean.test(isNotNull(sciDelivChan.getReceiptType()) ? sciDelivChan.getReceiptType() : "0"));
//                as2Model.setMdnEncryption(sciDelivChan.getRcptSigType());
//                as2Model.setMdnType(sciDelivChan.getRcptDelivMode());
//                as2Model.setReceiptToAddress(sciDelivChan.getReceiptToAddress());
//            });
//            profileService.findByEntityId(sciTransportEntity.getEntityId()).ifPresent(sciProfile ->
//                    packagingService.findByObjectId(sciProfile.getPackagingId()).ifPresent(sciPackaging -> {
//                        as2Model.setPayloadType(sciPackaging.getPayloadType());
//                        as2Model.setCompressData(sciPackaging.getCompressData());
//                        as2Model.setMimeType(sciPackaging.getDefaultMimeType());
//                        as2Model.setMimeSubType(sciPackaging.getDefMimeSubtype());
//                    })
//            );
//
//            // Get Exchange Certificate and Add to Model
//            if (as2Model.getHubInfo()) {
//                sciTrpKeyCertService.findByTransportId(sciTransportEntity.getObjectId().trim())
//                        .ifPresent(sciTrpKeyCertEntity -> as2Model.setExchangeCertificate(sciTrpKeyCertEntity.getKeyCertId()));
//            } else {
//                sciTrpUserCertService.findByTransportId(sciTransportEntity.getObjectId().trim())
//                        .ifPresent(trustedCertInfoEntity -> as2Model.setExchangeCertificate(trustedCertInfoEntity.getUserCertId()));
//            }
//
//            //Get CA Certificate and Add to Model
//            sciTranspCaCertService.findByTransportId(sciTransportEntity.getObjectId().trim()).ifPresent(sciTranspCaCertEntity ->
//                    caCertInfoService.getCaCertById(sciTranspCaCertEntity.getCaCertId()).ifPresent(caCertInfoEntity -> as2Model.setCaCertificate(caCertInfoEntity.getName()))
//            );
//        }
//
//        convertAs2ValuesFromNumToString.apply(as2Model);
//
//    }
//
//    private void getUsingB2bApi(As2Model as2Model) {
//        as2Service.getUsingB2bApi(as2Model);
//    }
//
//    private void getAs2(As2Model as2Model, String subscriberPkId) {
//        As2Entity as2Entity = as2Service.get(subscriberPkId);
//        as2Model.setAs2Identifier(as2Entity.getAs2Identifier());
//        as2Model.setProfileId(as2Entity.getIdentityName());
//        as2Model.setAs2EmailAddress(as2Entity.getEmailAddress());
//        as2Model.setProfileName(as2Entity.getProfileName());
//        as2Model.setStatus(convertStringToBoolean(as2Entity.getIsActive()));
//        as2Model.setHubInfo(convertStringToBoolean(as2Entity.getIsHubInfo()));
//        as2Model.setHttpclientAdapter(as2Entity.getHttpClientAdapter());
//        if (as2Entity.getIsHubInfo().equalsIgnoreCase("N")) {
//            as2Model.setSenderId(as2Entity.getSenderId());
//            as2Model.setSenderQualifier(as2Entity.getSenderQualifier());
//            as2Model.setResponseTimeout(isNotNull(as2Entity.getResponseTimeout()) ? Integer.valueOf(as2Entity.getResponseTimeout()) : 5);
//            as2Model.setPayloadType(as2Entity.getPayloadType());
//            as2Model.setMimeType(isNotNull(as2Entity.getMimeType()) ? as2Entity.getMimeType() : "Application");
//            as2Model.setMimeSubType(as2Entity.getMimeSubType());
//            as2Model.setSslType(as2Entity.getSslType());
//            as2Model.setCipherStrength(as2Entity.getCipherStrength());
//            as2Model.setCompressData(as2Entity.getCompressData());
//            as2Model.setPayloadEncryptionAlgorithm(as2Entity.getPayloadEncryptionAlgorithm());
//            as2Model.setPayloadSecurity(as2Entity.getPayloadSecurity());
//            as2Model.setEncryptionAlgorithm(as2Entity.getEncryptionAlgorithm());
//            as2Model.setSignatureAlgorithm(as2Entity.getSignatureAlgorithm());
//            as2Model.setMdn(convertStringToBoolean(as2Entity.getIsMdnRequired()));
//            as2Model.setMdnType(as2Entity.getMdnType());
//            as2Model.setMdnEncryption(as2Entity.getMdnEncryption());
//            as2Model.setPassword(as2Entity.getPassword());
//            as2Model.setReceiptToAddress(as2Entity.getReceiptToAddress());
//            as2Model.setUsername(as2Entity.getUsername());
//            as2Model.setCaCertificate(as2Entity.getCaCert());
//        }
//        as2Model.setExchangeCertificate(as2Entity.getExchgCertName());
//        as2Model.setSigningCertification(as2Entity.getSigningCertName());
//        as2Model.setKeyCertification(as2Entity.getKeyCert());
//        as2Model.setKeyCertificatePassphrase(as2Entity.getKeyCertPassphrase());
//    }
//
//    //TODO PENDING
//    private void deleteThroughPcm(PartnerEntity partnerEntity) {
//        String objectName = TRANSPORT + partnerEntity.getTpId() + "_" + partnerEntity.getTpName();
//        Boolean isHubInfo = convertStringToBoolean(partnerEntity.getIsProtocolHubInfo());
//        transportService.get(objectName).ifPresent(sciTransportEntity -> {
//            String transportObjectId = sciTransportEntity.getObjectId().split(":")[0];
//            transportService.delete(objectName);
//            docExchangeService.delete(isHubInfo, sciTransportEntity.getEntityId().trim());
//            deliveryChangeService.delete(isHubInfo, sciTransportEntity.getObjectId().trim());
//            profileService.findByEntityId(sciTransportEntity.getEntityId()).ifPresent(sciProfile -> {
//                profileService.delete(sciTransportEntity.getEntityId());
//                packagingService.delete(isHubInfo, sciProfile.getPackagingId());
//            });
//            as2ProfileService.delete(transportObjectId);
//            sciDocxKeyCertService.delete(transportObjectId);
//            as2EmailInfoService.delete(transportObjectId);
//            yfsOrganizationService.delete(transportObjectId);
//            sciDocxUserCertService.delete(transportObjectId);
//            sciTrpUserCertService.delete(transportObjectId);
//            sciTranspCaCertService.delete(transportObjectId);
//        });
//        as2Service.delete(partnerEntity.getTpId());
//    }
//
//    private void deleteThroughB2bApi(PartnerEntity partnerEntity) {
//        as2Service.deleteThroughB2bApi(convertStringToBoolean(partnerEntity.getIsProtocolHubInfo()), partnerEntity.getTpId());
//        as2Service.delete(partnerEntity.getTpId());
//    }
//
//    private void saveExchangeCertificate(String transportObjectId, String certId, boolean isOrg) {
//        if (hasLength(certId)) {
//            if (isOrg) {
//                certsAndPriKeyService.findById(certId)
//                        .ifPresent(certsAndPriKeyEntity -> sciTrpKeyCertService.save(transportObjectId, certsAndPriKeyEntity, getPrimaryKey.apply(SCI_EX_CERT_PRE_APPEND, TP_PKEY_RANDOM_COUNT)));
//            } else {
//                trustedCertInfoService.findById(certId)
//                        .ifPresent(trustedCertInfoEntity -> sciTrpUserCertService.save(transportObjectId, trustedCertInfoEntity, null));
//            }
//        }
//    }
//
//    private void saveSigningCertificates(String transportObjectId, String certId, boolean isOrg) {
//        if (hasLength(certId)) {
//            if (isOrg) {
//                certsAndPriKeyService.findById(certId)
//                        .ifPresent(certsAndPriKeyEntity -> sciDocxKeyCertService.save(transportObjectId, certsAndPriKeyEntity, null));
//            } else {
//                trustedCertInfoService.findById(certId.trim())
//                        .ifPresent(trustedCertInfoEntity -> sciDocxUserCertService.save(transportObjectId, trustedCertInfoEntity, null));
//            }
//        }
//    }
//
//    private void updateExchangeCertificate(String transportObjectId, String certId, boolean isOrg) {
//        if (hasLength(certId)) {
//            if (isOrg) {
//                certsAndPriKeyService.findById(certId)
//                        .ifPresent(certsAndPriKeyEntity -> sciTrpKeyCertService.update(transportObjectId, certsAndPriKeyEntity));
//            } else {
//                trustedCertInfoService.findById(certId)
//                        .ifPresent(trustedCertInfoEntity -> sciTrpUserCertService.update(transportObjectId, trustedCertInfoEntity));
//            }
//        }
//    }
//
//    private void updateSigningCertificates(String transportObjectId, String certId, boolean isOrg) {
//        if (hasLength(certId)) {
//            if (isOrg) {
//                certsAndPriKeyService.findById(certId)
//                        .ifPresent(certsAndPriKeyEntity -> sciDocxKeyCertService.update(transportObjectId, certsAndPriKeyEntity));
//            } else {
//                trustedCertInfoService.findById(certId.trim())
//                        .ifPresent(trustedCertInfoEntity -> sciDocxUserCertService.update(transportObjectId, trustedCertInfoEntity));
//            }
//        }
//    }
    @Transactional
    public void createAs2Mapping(As2RelationMapModel as2RelationMapModel) {

        sciContractService.getAs2Relation(as2RelationMapModel.getOrganizationName(), as2RelationMapModel.getPartnerName()).ifPresent(as2TradePartInfo -> {
            throw conflict("This mapping");
        });

        String sciContractObjectId = KeyGeneratorUtil.getPrimaryKey.apply(PCMConstants.SCI_CONTRACT_PRE_APPEND, SCI_RANDOM_COUNT);
        String sciConExtnObjectId = KeyGeneratorUtil.getPrimaryKey.apply(PCMConstants.SCI_CONTRACT_EXT_PRE_APPEND, SCI_RANDOM_COUNT);
        String as2PartnerContractId = KeyGeneratorUtil.getPrimaryKey.apply(PCMConstants.SCI_AS2_TRADE_PART_PRE_APPEND, SCI_RANDOM_COUNT);

        String orgProfileId = profileService.get(as2RelationMapModel.getOrganizationName()).map(SciProfileEntity::getObjectId).orElseThrow(() -> internalServerError("Organization Profile not available in Sterling."));
        String partnerProfileId = profileService.get(as2RelationMapModel.getPartnerName()).map(SciProfileEntity::getObjectId).orElseThrow(() -> internalServerError("Partner Profile not available in Sterling."));
        String orgIdentifier = yfsOrganizationService.findById(as2RelationMapModel.getOrganizationName()).map(YFSOrganizationEntity::getIdentifier).orElse("");
        String objectName1 = as2RelationMapModel.getPartnerName() + "_TO_" + as2RelationMapModel.getOrganizationName() + "_CONTRACT";
        String objectName2 = as2RelationMapModel.getPartnerName() + "_FROM_" + as2RelationMapModel.getOrganizationName() + "_CONTRACT";

        sciContractService.save(sciContractObjectId, AS2_7070, "MailboxAS2Add", objectName1, partnerProfileId, orgProfileId, "1");
        sciContractService.save(sciContractObjectId, AS2_7076, "MailboxAS2SendSyncMDNSpawner", objectName2, orgProfileId, partnerProfileId, "2");
        sciContractService.saveExtn(sciConExtnObjectId, sciContractObjectId, ":-7072", "IdentityIdentifier", orgIdentifier, AS2_7076);
        sciContractService.saveExtn(sciConExtnObjectId, sciContractObjectId, ":-7073", "PartnerIdentifier", as2RelationMapModel.getPartnerName(), AS2_7076);
        sciContractService.saveExtn(sciConExtnObjectId, sciContractObjectId, ":-7062", "PartnerIdentifier", as2RelationMapModel.getPartnerName(), AS2_7070);
        sciContractService.saveExtn(sciConExtnObjectId, sciContractObjectId, ":-7063", "IdentityIdentifier", orgIdentifier, AS2_7070);
        sciContractService.saveTradePartInfo(orgProfileId, as2RelationMapModel.getOrganizationName(), partnerProfileId, sciContractObjectId, as2RelationMapModel.getPartnerName(), as2PartnerContractId);
    }

}
