//package com.pe.pcm.partner.sterling;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.pe.pcm.activity.ActivityHistoryService;
//import com.pe.pcm.certificate.CaCertInfoService;
//import com.pe.pcm.certificate.CertsAndPriKeyService;
//import com.pe.pcm.certificate.entity.CaCertInfoEntity;
//import com.pe.pcm.certificate.entity.CertsAndPriKeyEntity;
//import com.pe.pcm.common.CommunityManagerNameModel;
//import com.pe.pcm.enums.Protocol;
//import com.pe.pcm.exception.GlobalExceptionHandler;
//import com.pe.pcm.miscellaneous.UserUtilityService;
//import com.pe.pcm.partner.PartnerModel;
//import com.pe.pcm.partner.PartnerService;
//import com.pe.pcm.partner.entity.PartnerEntity;
//import com.pe.pcm.protocol.ManageProtocolService;
//import com.pe.pcm.protocol.RemoteProfileModel;
//import com.pe.pcm.protocol.as2.certificate.SciTranspCaCertServiceDup;
//import com.pe.pcm.protocol.as2.certificate.SciTrpSslCertService;
//import com.pe.pcm.protocol.as2.si.entity.*;
//import com.pe.pcm.protocol.remoteftp.entity.RemoteFtpEntity;
//import com.pe.pcm.protocol.si.*;
//import com.pe.pcm.protocol.si.profile.xref.SftpProfileXrefKhostKeyService;
//import com.pe.pcm.sterling.security.SterlingDBPasswordService;
//import com.pe.pcm.sterling.dto.TransportDTO;
//import com.pe.pcm.sterling.mailbox.MbxMailboxService;
//import com.pe.pcm.sterling.partner.sfg.ManageSfgProfileService;
//import com.pe.pcm.sterling.virtualroot.MbxVirtualRootService;
//import com.pe.pcm.sterling.yfs.YfsPersonInfoService;
//import com.pe.pcm.sterling.yfs.YfsUserService;
//import com.pe.pcm.utils.CommonFunctions;
//import com.pe.pcm.utils.PCMConstants;
//import com.pe.pcm.workflow.ProcessService;
//import org.apache.commons.lang3.SerializationUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//import java.util.*;
//import java.util.concurrent.atomic.AtomicInteger;
//import java.util.concurrent.atomic.AtomicReference;
//import java.util.function.BiFunction;
//import java.util.stream.Collectors;
//
//import static com.pe.pcm.exception.GlobalExceptionHandler.*;
//import static com.pe.pcm.protocol.function.SiProtocolFunctions.*;
//import static com.pe.pcm.protocol.function.SterlingFunctions.*;
//import static com.pe.pcm.utils.CommonFunctions.*;
//import static com.pe.pcm.utils.KeyGeneratorUtil.getChildPrimaryKey;
//import static com.pe.pcm.utils.KeyGeneratorUtil.getPrimaryKey;
//import static com.pe.pcm.utils.PCMConstants.*;
//import static org.springframework.util.StringUtils.hasLength;
//
///**
// * @author Chenchu Kiran.
// */
//@Service
//public class RemotePartnerService {
//    private static final Logger LOGGER = LoggerFactory.getLogger(RemotePartnerService.class);
//
//    private PartnerService partnerService;
//    private ActivityHistoryService activityHistoryService;
//    private UserUtilityService userUtilityService;
//    private ManageProtocolService manageProtocolService;
//    private ProcessService processService;
//
//    private SciTransportServiceDup sciTransportServiceDup;
//    private YfsOrganizationServiceDup yfsOrganizationServiceDup;
//    private YfsPersonInfoService yfsPersonInfoService;
//    private RemoteFtpServiceDup remoteFtpServiceDup;
//    private SciDocExchangeServiceDup sciDocExchangeServiceDup;
//    private SciDeliveryChangeServiceDup sciDeliveryChangeServiceDup;
//    private SciPackagingServiceDup sciPackagingServiceDup;
//    private SciProfileServiceDup sciProfileServiceDup;
//
//    private SciTrpSslCertService sciTrpSslCertService;
//    private CertsAndPriKeyService certsAndPriKeyService;
//    private SciTranspCaCertServiceDup sciTranspCaCertServiceDup;
//    private CaCertInfoService caCertInfoService;
//
//    private SftpProfileService sftpProfileService;
//    private SftpProfileXrefKhostKeyService sftpProfileXrefKhostKeyService;
//
//    private YfsUserService yfsUserService;
//    private MbxMailboxService mbxMailboxService;
//    private MbxVirtualRootService mbxVirtualRootService;
//    private String community;
//
//    private ManageSfgProfileService manageSfgProfileService;
//    private SterlingDBPasswordService sterlingDBPasswordService;
//
//    private ObjectMapper mapper = new ObjectMapper();
//    private BiFunction<PartnerEntity, RemoteFtpEntity, RemoteProfileModel> serialize = RemotePartnerService::apply;
//
//    @Autowired
//    public RemotePartnerService(PartnerService partnerService, ActivityHistoryService activityHistoryService, UserUtilityService userUtilityService, ManageProtocolService manageProtocolService, ProcessService processService, SciTransportServiceDup sciTransportServiceDup, YfsOrganizationServiceDup yfsOrganizationServiceDup, YfsPersonInfoService yfsPersonInfoService, RemoteFtpServiceDup remoteFtpServiceDup, SciDocExchangeServiceDup sciDocExchangeServiceDup, SciDeliveryChangeServiceDup sciDeliveryChangeServiceDup, SciPackagingServiceDup sciPackagingServiceDup, SciProfileServiceDup sciProfileServiceDup, SciTrpSslCertService sciTrpSslCertService, SciTranspCaCertServiceDup sciTranspCaCertServiceDup, CertsAndPriKeyService certsAndPriKeyService, CaCertInfoService caCertInfoService, SftpProfileService sftpProfileService, SftpProfileXrefKhostKeyService sftpProfileXrefKhostKeyService, MbxMailboxService mbxMailboxService, YfsUserService yfsUserService, MbxVirtualRootService mbxVirtualRootService, @Value("${b2b.profile.community}") String community, ManageSfgProfileService manageSfgProfileService, SterlingDBPasswordService sterlingDBPasswordService) {
//        this.partnerService = partnerService;
//        this.activityHistoryService = activityHistoryService;
//        this.userUtilityService = userUtilityService;
//        this.manageProtocolService = manageProtocolService;
//        this.processService = processService;
//        this.sciTransportServiceDup = sciTransportServiceDup;
//        this.yfsOrganizationServiceDup = yfsOrganizationServiceDup;
//        this.yfsPersonInfoService = yfsPersonInfoService;
//        this.remoteFtpServiceDup = remoteFtpServiceDup;
//        this.sciDocExchangeServiceDup = sciDocExchangeServiceDup;
//        this.sciDeliveryChangeServiceDup = sciDeliveryChangeServiceDup;
//        this.sciPackagingServiceDup = sciPackagingServiceDup;
//        this.sciProfileServiceDup = sciProfileServiceDup;
//        this.sciTrpSslCertService = sciTrpSslCertService;
//        this.sciTranspCaCertServiceDup = sciTranspCaCertServiceDup;
//        this.certsAndPriKeyService = certsAndPriKeyService;
//        this.caCertInfoService = caCertInfoService;
//        this.sftpProfileService = sftpProfileService;
//        this.sftpProfileXrefKhostKeyService = sftpProfileXrefKhostKeyService;
//        this.mbxMailboxService = mbxMailboxService;
//        this.yfsUserService = yfsUserService;
//        this.mbxVirtualRootService = mbxVirtualRootService;
//        this.community = community;
//        this.manageSfgProfileService = manageSfgProfileService;
//        this.sterlingDBPasswordService = sterlingDBPasswordService;
//    }
//
//    @Transactional
//    public String save(RemoteProfileModel remoteProfileModel) {
//        LOGGER.info("In PCM-SI Profile save() Method.");
//        if (!Optional.ofNullable(Protocol.findProtocol(remoteProfileModel.getProtocol())).isPresent()) {
//            throw unknownProtocol();
//        }
//        duplicatePartner(remoteProfileModel.getProfileId());
//        String parentPrimaryKey = getPrimaryKey.apply(PCMConstants.TP_PKEY_PRE_APPEND,
//                PCMConstants.TP_PKEY_RANDOM_COUNT);
//        String childPrimaryKey = getChildPrimaryKey(remoteProfileModel.getProtocol());
//        saveProtocol(remoteProfileModel, parentPrimaryKey, childPrimaryKey, null, false);
//
//        PartnerModel partnerModel = new PartnerModel();
//        BeanUtils.copyProperties(remoteProfileModel, partnerModel);
//        partnerService.save(partnerModel, parentPrimaryKey, childPrimaryKey);
//        activityHistoryService.savePartnerActivity(parentPrimaryKey, "Trading Partner created.");
//        userUtilityService.addPartnerToCurrentUser(parentPrimaryKey, TP);
//        LOGGER.info("Completed SFG Profile save() Method.");
//        return parentPrimaryKey;
//    }
//
//    @Transactional
//    public void update(RemoteProfileModel remoteProfileModel) {
//        PartnerEntity oldPartnerEntity = SerializationUtils.clone(partnerService.get(remoteProfileModel.getPkId()));
//        if (!oldPartnerEntity.getTpId().equalsIgnoreCase(remoteProfileModel.getProfileId())) {
//            duplicatePartner(remoteProfileModel.getProfileId());
//        }
//        String parentPrimaryKey = oldPartnerEntity.getPkId();
//        String childPrimaryKey = oldPartnerEntity.getPartnerProtocolRef();
//        Protocol protocol = Optional.ofNullable(Protocol.findProtocol(remoteProfileModel.getProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);
//        Map<String, String> oldFtpEntityMap = new LinkedHashMap<>();
//        boolean isUpdate;
//        if (!protocol.getProtocol().equalsIgnoreCase(oldPartnerEntity.getTpProtocol())) {
//            manageProtocolService.deleteProtocol(oldPartnerEntity.getTpProtocol(), oldPartnerEntity.getPkId());
//            childPrimaryKey = getChildPrimaryKey(remoteProfileModel.getProtocol());
//            isUpdate = false;
//        } else {
//            oldFtpEntityMap = mapper.convertValue(remoteFtpServiceDup.get(parentPrimaryKey), new TypeReference<Map<String, String>>() {
//            });
//            isUpdate = true;
//        }
//        Map<String, String> newFtpEntityMap = mapper.convertValue(saveProtocol(remoteProfileModel, parentPrimaryKey, childPrimaryKey, oldPartnerEntity.getTpName(), isUpdate), new TypeReference<Map<String, String>>() {
//        });
//        PartnerModel partnerModel = new PartnerModel();
//        BeanUtils.copyProperties(remoteProfileModel, partnerModel);
//        PartnerEntity newPartnerEntity = partnerService.save(partnerModel, parentPrimaryKey, childPrimaryKey);
//        activityHistoryService.updatePartnerActivity(oldPartnerEntity, newPartnerEntity, oldFtpEntityMap, newFtpEntityMap, isUpdate);
//
//    }
//
//    @Transactional
//    public void delete(String pkId) {
//        if (!processService.findByPartnerProfile(pkId).isEmpty()) {
//            throw internalServerError("Unable to delete this partner, Because it has workflow...");
//        }
//        PartnerEntity partnerEntity = partnerService.findPartnerById(pkId);
//
//        remoteFtpServiceDup.delete(pkId);
//        deleteSiProfiles(partnerEntity.getTpName(), Optional.ofNullable(Protocol.findProtocol(partnerEntity.getTpProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol));
//
//        partnerService.delete(partnerEntity);
//    }
//
//    public RemoteProfileModel get(String pkId) {
//        return getFromSI(serialize.apply(partnerService.get(pkId), remoteFtpServiceDup.get(pkId)));
//    }
//
//    private void deleteSiProfiles(String profileName, Protocol protocol) {
//        AtomicReference<String> userId = new AtomicReference<>("");
//        switch (protocol) {
//            case SFG_FTP:
//            case SFG_FTPS:
//                sciTransportServiceDup.get(profileName + "_" + CONSUMER).ifPresent(sciTransportEntity -> {
//                    userId.set(sciTransportEntity.getTranspActUserId());
//                    yfsOrganizationServiceDup.get(sciTransportEntity.getEntityId()).ifPresent(yfsOrganizationEntity -> {
//                        yfsPersonInfoService.delete(yfsOrganizationEntity.getCorporateAddressKey());
//                        yfsOrganizationServiceDup.deleteByObjectId(sciTransportEntity.getEntityId());
//                    });
//                    sciProfileServiceDup.findByEntityId(sciTransportEntity.getEntityId()).ifPresent(sciProfileEntity -> {
//                        sciPackagingServiceDup.delete(sciProfileEntity.getPackagingId());
//                        sciDeliveryChangeServiceDup.findByObjectId(sciProfileEntity.getDelivChannelId()).ifPresent(sciDelivChan -> {
//                            sciDocExchangeServiceDup.delete(sciDelivChan.getDocExchangeId());
//                            sciDeliveryChangeServiceDup.delete(sciProfileEntity.getDelivChannelId());
//                        });
//                        sciProfileServiceDup.deleteByEntityId(sciTransportEntity.getEntityId());
//                    });
//                    sciTrpSslCertService.deleteAllByTransportId(sciTransportEntity.getObjectId());
//                    sciTranspCaCertServiceDup.deleteAllByTransportId(sciTransportEntity.getObjectId());
//
//                    sciTransportServiceDup.delete(sciTransportEntity.getTransportKey());
//                });
//                break;
//            case SFG_SFTP:
//                //TODO
//                break;
//            default:
//                //We can ignore this
//                break;
//        }
//        if (isNotNull(userId)) {
//            yfsUserService.delete(userId.get());
//            mbxVirtualRootService.delete(userId.get());
//        }
//
//    }
//
//    private RemoteFtpEntity saveProtocol(RemoteProfileModel remoteProfileModel, String parentPrimaryKey, String childPrimaryKey, String oldProfileName, Boolean isUpdate) {
//        remoteProfileModel.setSubscriberType("TP");
//        Protocol protocol = Optional.ofNullable(Protocol.findProtocol(remoteProfileModel.getProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);
//        String objectKey = getPrimaryKey.apply(SCI_TR_OBJ_PRE_APPEND, SCI_RANDOM_COUNT);
//
//        switch (protocol) {
//            case SFG_FTP:
//            case SFG_FTPS:
//                /*Save into SI Tables*/
//                String transportObjectId;
//                String transportEntityId;
//                String transportKey;
//                String sciDocExchangeObj;
//                String sciDocExchangeKey;
//                String corporateAddressKey;
//                String sciDeliveryChanObjectId;
//                String sciDeliveryChanKey;
//                String sciDelChanObjectName;
//                String sciProfileObjectId;
//                String sciProfileKey;
//                String sciPackagingObjectId;
//                String sciPackagingKey;
//
//                if (isUpdate) {
//                    //TODO - Need to find the exact key to retrieve the data
//                    Optional<SciTransportEntity> sciTransportEntityOptional = sciTransportServiceDup.get(oldProfileName + "_" + CONSUMER);
//
//                    if (sciTransportEntityOptional.isPresent()) {
//                        transportObjectId = sciTransportEntityOptional.get().getObjectId();
//                        transportEntityId = sciTransportEntityOptional.get().getEntityId();
//                        transportKey = sciTransportEntityOptional.get().getTransportKey();
//
//                        Optional<YFSOrganizationEntity> yfsOrganizationEntityOptional = yfsOrganizationServiceDup.get(transportEntityId);
//                        if (yfsOrganizationEntityOptional.isPresent()) {
//                            corporateAddressKey = yfsOrganizationEntityOptional.get().getCorporateAddressKey();
//                        } else {
//                            throw internalServerError("YfsOrganization Record notfound");
//                        }
//
//                        Optional<SciProfileEntity> sciProfileOptional = sciProfileServiceDup.findByEntityId(transportEntityId);
//                        if (sciProfileOptional.isPresent()) {
//                            sciProfileObjectId = sciProfileOptional.get().getObjectId();
//                            sciProfileKey = sciProfileOptional.get().getProfileKey();
//                            sciPackagingObjectId = sciProfileOptional.get().getPackagingId().trim();
//                            sciDeliveryChanObjectId = sciProfileOptional.get().getDelivChannelId();
//                        } else {
//                            throw internalServerError("SciProfileEntity Record notfound");
//                        }
//
//                        Optional<SciDelivChanEntity> sciDelivChanOptional = sciDeliveryChangeServiceDup.findByObjectId(sciDeliveryChanObjectId);
//                        if (sciDelivChanOptional.isPresent()) {
//                            sciDelChanObjectName = sciDelivChanOptional.get().getObjectName();
//                            sciDeliveryChanKey = sciDelivChanOptional.get().getDeliveryChannelKey();
//                            sciDocExchangeObj = sciDelivChanOptional.get().getDocExchangeId();
//                        } else {
//                            throw internalServerError("SciDeliveryChain Record notfound");
//                        }
//
//                        Optional<SciDocExchangeEntity> sciDocExchangeOptional = sciDocExchangeServiceDup.findById(transportEntityId);
//                        if (sciDocExchangeOptional.isPresent()) {
//                            sciDocExchangeKey = sciDocExchangeOptional.get().getDocExchangeKey();
//                        } else {
//                            throw internalServerError("DocExchange Record notfound");
//                        }
//
//                        Optional<SciPackagingEntity> sciPackagingOptional = sciPackagingServiceDup.get(sciPackagingObjectId);
//                        if (sciPackagingOptional.isPresent()) {
//                            sciPackagingKey = sciPackagingOptional.get().getPackagingKey();
//                        } else {
//                            throw internalServerError("SciPackagingEntity Record notfound");
//                        }
//
//                    } else {
//                        throw internalServerError("Transport Record notfound");
//                    }
//                } else {
//                    transportObjectId = objectKey + ":-3afb";
//                    transportEntityId = objectKey + ":-3b0a";
//                    transportKey = objectKey + "-4trk";
//
//                    sciDocExchangeObj = objectKey + ":-3afc";
//                    sciDocExchangeKey = objectKey + "-4dek";
//
//                    corporateAddressKey = getPrimaryKey.apply("", 23);
//
//                    sciProfileObjectId = objectKey + ":-3afe";
//                    sciProfileKey = getPrimaryKey.apply("", 23);
//
//                    sciDeliveryChanObjectId = objectKey + ":-3afa";
//                    sciDeliveryChanKey = objectKey + "-4dck";
//                    sciDelChanObjectName = DELIVERY + "_" + getPrimaryKey.apply("", 15);
//
//                    sciPackagingObjectId = objectKey + ":-3afd";
//                    sciPackagingKey = getPrimaryKey.apply("", 23);
//
//                }
//
//                sciTransportServiceDup.save(convertRemoteFtpModelToTransportDTO.apply(remoteProfileModel)
//                        .setTransportKey(transportKey)
//                        .setObjectId(transportObjectId)
//                        .setEntityId(transportEntityId));
//                yfsOrganizationServiceDup.save(convertRemoteFtpModelToYfsOrganizationDTO.apply(remoteProfileModel)
//                        .setObjectId(transportEntityId)
//                        .setCorporateAddressKey(corporateAddressKey));
//                sciProfileServiceDup.save(convertRemoteFtpModelToProfileDTO.apply(remoteProfileModel)
//                        .setObjectId(sciProfileObjectId)
//                        .setEntityId(transportEntityId)
//                        .setPackagingId(sciPackagingObjectId)
//                        .setDelChannelId(sciDeliveryChanObjectId)
//                        .setProfileKey(sciProfileKey));
//                sciPackagingServiceDup.save(convertRemoteFtpModelToPackagingDTO.apply(remoteProfileModel)
//                        .setObjectId(sciPackagingObjectId)
//                        .setEntityId(transportEntityId)
//                        .setPackagingKey(sciPackagingKey));
//                sciDeliveryChangeServiceDup.save(convertRemoteFtpModelToDeliveryChanDTO.apply(remoteProfileModel)
//                        .setObjectId(sciDeliveryChanObjectId)
//                        .setObjectName(sciDelChanObjectName)
//                        .setEntityId(transportEntityId)
//                        .setTransportId(transportObjectId)
//                        .setDocExchangeId(sciDocExchangeObj)
//                        .setDeliveryChannelKey(sciDeliveryChanKey));
//                sciDocExchangeServiceDup.save(convertRemoteFtpModelToDocExchangeDTO.apply(remoteProfileModel)
//                        .setObjectId(sciDocExchangeObj)
//                        .setEntityId(transportEntityId)
//                        .setDocExchangeKey(sciDocExchangeKey));
//
//                if (isNotNull(corporateAddressKey)) {
//                    yfsPersonInfoService.save(convertRemoteFtpModelToYfsPersonInfo.apply(remoteProfileModel)
//                            .setPersonInfoKey(corporateAddressKey));
//                }
//
//                if (remoteProfileModel.getProtocol().equals("SFGFTPS")) {
//                    if (isUpdate) {
//                        sciTrpSslCertService.deleteAllByTransportId(transportObjectId);
//                        sciTranspCaCertServiceDup.deleteAllByTransportId(transportObjectId);
//                    }
//                    AtomicInteger atomicInteger = new AtomicInteger(0);
//                    /*Saving Key Certificates*/
//                    remoteProfileModel.getKeyCertificateIds().forEach(certObject -> {
//                        if (hasLength(certObject.getName())) {
//                            CertsAndPriKeyEntity certsAndPriKeyEntity = certsAndPriKeyService.findById(certObject.getName())
//                                    .orElseThrow(() -> internalServerError("Provided Key Cert is not found, CertName : " + certObject.getName()));
//                            sciTrpSslCertService.save(convertRemoteFtpModelToSciTrpSslCert.apply(remoteProfileModel)
//                                    .setTransportId(transportObjectId)
//                                    .setKeyCertId(certsAndPriKeyEntity.getObjectId())
//                                    .setCertificateKey(getPrimaryKey.apply("", 23))
//                                    .setGoLiveDate(certsAndPriKeyEntity.getNotBefore())
//                                    .setNotAfterDate(certsAndPriKeyEntity.getNotAfter())
//                                    .setCertOrder(atomicInteger.getAndIncrement()));
//                        }
//                    });
//
//                    atomicInteger.set(0);
//                    /*Saving CA Certificates*/
//                    remoteProfileModel.getCaCertificateIds().forEach(certObject -> {
//                        if (hasLength(certObject.getName())) {
//                            //Saving CaCertificate
//                            CaCertInfoEntity caCertInfoEntity = caCertInfoService.getCaCertById(certObject.getName())
//                                    .orElseThrow(() -> internalServerError("Provided CA Cert is not found, CertName : " + certObject.getName()));
//                            sciTranspCaCertServiceDup.save(convertRemoteFtpModelToCaCert.apply(remoteProfileModel)
//                                    .setTransportId(transportObjectId)
//                                    .setKeyCertId(caCertInfoEntity.getObjectId())
//                                    .setCertificateKey(getPrimaryKey.apply("", 23))
//                                    .setGoLiveDate(caCertInfoEntity.getNotBefore())
//                                    .setNotAfterDate(caCertInfoEntity.getNotAfter())
//                                    .setCertOrder(atomicInteger.getAndIncrement()));
//                        }
//                    });
//                }
//
//                if (remoteProfileModel.isCreateSfgProfile()) {
//                    manageSfgProfileService.save(
//                            convertRemoteFtpModelToSfgProfileDetailsDTO.apply(remoteProfileModel)
//                                    .setOrganizationKey(remoteProfileModel.getProfileId().substring(0, Math.min(remoteProfileModel.getProfileId().length(), 24)))
//                                    .setTransportEntityKey(transportEntityId)
//                                    .setSciProfileObjectId(sciProfileObjectId)
//                                    .setCommunityName(community)
//                                    .setCommunityProfileId(getCommunityProfile("FTP"))
//
//                    );
//                }
//                break;
//            case SFG_SFTP:
//                String sftpProfileId;
//                String authType = isNullThrowError.apply(remoteProfileModel.getPreferredAuthenticationType(), "PreferredAuthenticationType");
//                if (authType.equalsIgnoreCase("password")) {
//                    isNullThrowError.apply(remoteProfileModel.getPassword(), "password, because password field is mandatory when Authentication type is password.");
//                } else if (authType.equalsIgnoreCase("PUBLIC_KEY") || authType.equalsIgnoreCase("PUBLIC KEY")) {
//                    isNullThrowError.apply(remoteProfileModel.getUserIdentityKey(), "UserIdentityKey, because UserIdentityKey field is mandatory when AuthenticationType is Public Key");
//                    if (remoteProfileModel.getKnownHostKeyNames().stream()
//                            .map(CommunityManagerNameModel::getName)
//                            .filter(CommonFunctions::isNotNull)
//                            .collect(Collectors.toList()).isEmpty()
//                    ) {
//                        throw internalServerError("Provide at least one KnownHostKey when AuthenticationType is Public Key");
//                    }
//                    remoteProfileModel.setPreferredAuthenticationType("publickey");
//                }
//                if (isUpdate) {
//                    sftpProfileId = sftpProfileService.getByName(oldProfileName).orElseThrow(() -> internalServerError("SftpProfile Record is not found")).getProfileId();
//                } else {
//                    sftpProfileId = objectKey + ":node1";
//                }
//                sftpProfileService.save(remoteProfileModel, sftpProfileId);
//                sftpProfileXrefKhostKeyService.saveAll(sftpProfileId, remoteProfileModel.getKnownHostKeyNames());
//                break;
//            default:
//                throw internalServerError("The given Protocol should match with SFGFTP/SFGFTPS/SFGFTPS");
//        }
//
//        /*Create or Update MailBoxes into SI*/
//        mbxMailboxService.createAll(convertRemoteFtpModelToMbxMailBoxModels.apply(remoteProfileModel));
//
//        /*Create or Update User into SI*/
//        yfsUserService.save(convertRemoteFtpModelToYfsUserModel.apply(remoteProfileModel), remoteProfileModel.isMergeUser());
//
//        /*Create or Update Virtual Root into SI*/
//        mbxVirtualRootService.save(
//                remoteProfileModel.getUserName(),
//                remoteProfileModel.getSubscriberType().equals("TP") ? remoteProfileModel.getInDirectory() : remoteProfileModel.getOutDirectory(),
//                remoteProfileModel.isMergeUser()
//        );
//
//        /*Save into PCM Table*/
//        return remoteFtpServiceDup.saveProtocol(remoteProfileModel, parentPrimaryKey, childPrimaryKey, "");
//
//    }
//
//    private void duplicatePartner(String partnerId) {
//        Optional<PartnerEntity> tradingPartnerEntityOptional = partnerService.find(partnerId);
//        tradingPartnerEntityOptional.ifPresent(tradingPartnerEntity -> {
//            throw conflict("Partner");
//        });
//    }
//
//    private RemoteProfileModel getFromSI(RemoteProfileModel remoteProfileModel) {
//        String userId = "";
//        remoteProfileModel.setSubscriberType("TP");
//        Protocol protocol = Optional.ofNullable(Protocol.findProtocol(remoteProfileModel.getProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);
//        switch (protocol) {
//            case SFG_FTP:
//            case SFG_FTPS:
//                //TODO - Need to find the exact key to retrieve the data
//                TransportDTO transportDTO = sciTransportServiceDup.getTransportDTO(remoteProfileModel.getProfileName() + "_" + CONSUMER);
//                convertTransportDTOToRemoteFtpModel.apply(remoteProfileModel, transportDTO);
//                userId = transportDTO.getUserId();
//                Optional<YFSOrganizationEntity> yfsOrganizationEntityOptional = yfsOrganizationServiceDup.get(transportDTO.getEntityId());
//                if (yfsOrganizationEntityOptional.isPresent()) {
//                    /*Need to get the HubInfo here*/
//                    if (isNotNull(yfsOrganizationEntityOptional.get().getCorporateAddressKey())) {
//                        convertYfsPersonInfoToRemoteFtpModel.apply(remoteProfileModel, yfsPersonInfoService.getYfsPersonInfoDTO(yfsOrganizationEntityOptional.get().getCorporateAddressKey()));
//                    }
//                } else {
//                    throw internalServerError("YfsOrganization Record notfound");
//                }
//
//                Optional<SciProfileEntity> sciProfileOptional = sciProfileServiceDup.findByEntityId(transportDTO.getEntityId());
//                if (sciProfileOptional.isPresent()) {
//                    Optional<SciDelivChanEntity> sciDelivChanOptional = sciDeliveryChangeServiceDup.findByObjectId(sciProfileOptional.get().getDelivChannelId());
//                    if (sciDelivChanOptional.isPresent()) {
//                        convertDocExchangeDTOToRemoteFtpModel.apply(remoteProfileModel, sciDocExchangeServiceDup.getDocExchangeDTO(sciDelivChanOptional.get().getDocExchangeId()));
//                    } else {
//                        throw internalServerError("SciDeliveryChain Record notfound");
//                    }
//                } else {
//                    throw internalServerError("SciProfileEntity Record notfound");
//                }
//                break;
//            case SFG_SFTP:
//                sftpProfileService.getByName(remoteProfileModel.getProfileName()).ifPresent(sftpProfileEntity -> {
//                    remoteProfileModel.setKnownHostKeyNames(sftpProfileXrefKhostKeyService.findAllByProfileId(sftpProfileEntity.getProfileId() + ":node1")
//                            .stream()
//                            .map(sftpProfileXrefKhostKeyEntity -> new CommunityManagerNameModel(sftpProfileXrefKhostKeyEntity.getSftpProfileXrefKhostKeyIdentity().getKhostKeyId()))
//                            .collect(Collectors.toList()));
//                    remoteProfileModel.setUserIdentityKey(sftpProfileEntity.getUserIdentityKeyName());
//                    remoteProfileModel.setPassword(sterlingDBPasswordService.decrypt(sftpProfileEntity.getRemotePassword()));
//                });
//                break;
//            default:
//                throw internalServerError("Protocol was not matched with any Remote Protocol");
//        }
//
//        if (isNotNull(userId)) {
//            addYfsUserModelToRemoteFtpModel.apply(remoteProfileModel, yfsUserService.get(userId));
//        }
//
//        return remoteProfileModel;
//    }
//
//    private static RemoteProfileModel apply(PartnerEntity tradingPartner, RemoteFtpEntity remoteFtpEntity) {
//        RemoteProfileModel remoteProfileModel = new RemoteProfileModel();
//        remoteProfileModel.setPkId(tradingPartner.getPkId())
//                .setProfileName(tradingPartner.getTpName())
//                .setCustomProfileName(tradingPartner.getCustomTpName())
//                .setProfileId(tradingPartner.getTpId())
//                .setAddressLine1(tradingPartner.getAddressLine1())
//                .setPhone(tradingPartner.getPhone())
//                .setAddressLine2(tradingPartner.getAddressLine2())
//                .setProtocol(tradingPartner.getTpProtocol())
//                .setEmailId(tradingPartner.getEmail())
//                .setStatus(convertStringToBoolean(tradingPartner.getStatus()))
//                .setHubInfo(convertStringToBoolean(remoteFtpEntity.getIsHubInfo()))
//                .setPgpInfo(tradingPartner.getPgpInfo())
//                .setPemIdentifier(tradingPartner.getPemIdentifier())
//                .setOnlyPCM(isNotNull(tradingPartner.getIsOnlyPcm()) ? convertStringToBoolean(tradingPartner.getIsOnlyPcm()) : Boolean.FALSE);
//
//        if (remoteProfileModel.getHubInfo()) {
//            remoteProfileModel.setPreferredAuthenticationType(isNotNull(remoteFtpEntity.getPrfAuthType()) ? remoteFtpEntity.getPrfAuthType() : "PASSWORD");
//        }
//
//        return remoteProfileModel.setUserName(remoteFtpEntity.getUserId())
//                .setPassword(remoteFtpEntity.getPassword())
//                .setTransferType(remoteFtpEntity.getTransferType())
//                //Start : Extra adding for Bulk uploads
//                .setRemoteHost(remoteFtpEntity.getHostName())
//                .setRemotePort(remoteFtpEntity.getPortNo())
//                .setCertificateId(remoteFtpEntity.getCertificateId())
//                .setUserIdentityKey(remoteFtpEntity.getUserIdentityKey())
//                .setKnownHostKey(remoteFtpEntity.getKnownHostKey())
//                .setConnectionType(remoteFtpEntity.getConnectionType())
//                .setEncryptionStrength(remoteFtpEntity.getEncryptionStrength())
//                .setRetryInterval(remoteFtpEntity.getRetryInterval())
//                .setNoOfRetries(remoteFtpEntity.getNoOfRetries())
//                .setUseCCC(convertStringToBoolean(remoteFtpEntity.getUseCcc()))
//                .setUseImplicitSSL(convertStringToBoolean(remoteFtpEntity.getUseImplicitSsl()))
//                //END : Extra adding for Bulk uploads
//                .setInDirectory(remoteFtpEntity.getInDirectory())
//                .setOutDirectory(remoteFtpEntity.getOutDirectory())
//                .setFileType(remoteFtpEntity.getFileType())
//                .setDeleteAfterCollection(convertStringToBoolean(remoteFtpEntity.getDeleteAfterCollection()))
//                .setAdapterName(remoteFtpEntity.getAdapterName())
//                .setPoolingInterval(remoteFtpEntity.getPoolingIntervalMins())
//                .setSubscriberType("TP")
//                .setAuthorizedUserKeys(isNotNull(remoteFtpEntity.getAuthUserKeys()) ?
//                        Arrays.stream(remoteFtpEntity.getAuthUserKeys().split(","))
//                                .map(CommunityManagerNameModel::new)
//                                .collect(Collectors.toList())
//                        : new ArrayList<>());
//    }
//
//    private String getCommunityProfile(String protocol) {
//        AtomicReference<String> sciCommunityProfileId = new AtomicReference<>("");
//        Optional<YFSOrganizationEntity> yfsOrganizationEntityOptional = yfsOrganizationServiceDup.findFirstByOrganizationName(community + "_AFTCommunity");
//        if (yfsOrganizationEntityOptional.isPresent()) {
//            Map<String, String> sciProfileIdsMap = sciProfileServiceDup.findAllByEntityId(yfsOrganizationEntityOptional.get().getObjectId())
//                    .stream()
//                    .collect(Collectors.toMap(SciProfileEntity::getDelivChannelId, SciProfileEntity::getObjectId));
//            Map<String, String> transportIdsMap = sciDeliveryChangeServiceDup.findAllByObjectIds(new ArrayList<>(sciProfileIdsMap.keySet()))
//                    .stream()
//                    .collect(Collectors.toMap(SciDelivChanEntity::getTransportId, SciDelivChanEntity::getObjectId));
//            List<SciTransportEntity> sciTransportEntities = sciTransportServiceDup.findAllByObjectIds(new ArrayList<>(transportIdsMap.keySet()));
//            sciTransportEntities.forEach(sciTransportEntity -> {
//                if (sciTransportEntity.getObjectName().trim().equalsIgnoreCase(protocol)) {
//                    String sciDeliveryChanObjectId = transportIdsMap.get(sciTransportEntity.getObjectId());
//                    sciCommunityProfileId.set(sciProfileIdsMap.get(sciDeliveryChanObjectId));
//                }
//            });
//        } else {
//            throw internalServerError("Community Partner Configuration not available.");
//        }
//        return sciCommunityProfileId.get();
//    }
//}
