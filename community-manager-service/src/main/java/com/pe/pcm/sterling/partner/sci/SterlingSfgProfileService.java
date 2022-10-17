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
import com.pe.pcm.common.CommunityManagerKeyValueModel;
import com.pe.pcm.enums.Protocol;
import com.pe.pcm.protocol.as2.certificate.SciTranspCaCertServiceDup;
import com.pe.pcm.protocol.as2.certificate.SciTrpSslCertService;
import com.pe.pcm.protocol.as2.si.entity.*;
import com.pe.pcm.protocol.function.SterlingFunctions;
import com.pe.pcm.protocol.si.*;
import com.pe.pcm.protocol.si.profile.xref.SftpProfileXrefKhostKeyService;
import com.pe.pcm.sterling.community.SciCommunityRepository;
import com.pe.pcm.sterling.dto.SfgProfileDetailsDTO;
import com.pe.pcm.sterling.dto.TransportDTO;
import com.pe.pcm.sterling.mailbox.SterlingMailboxService;
import com.pe.pcm.sterling.partner.sfg.ManageSfgProfileService;
import com.pe.pcm.sterling.profile.SterlingProfilesModel;
import com.pe.pcm.sterling.virtualroot.MbxVirtualRootService;
import com.pe.pcm.sterling.yfs.YfsPersonInfoService;
import com.pe.pcm.sterling.yfs.YfsUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;
import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;
import static com.pe.pcm.protocol.function.SiProtocolFunctions.*;
import static com.pe.pcm.protocol.function.SterlingFunctions.*;
import static com.pe.pcm.utils.CommonFunctions.isNotNull;
import static com.pe.pcm.utils.KeyGeneratorUtil.getPrimaryKey;
import static com.pe.pcm.utils.PCMConstants.*;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * @author Kiran Reddy.
 */
@Service
public class SterlingSfgProfileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SterlingSfgProfileService.class);

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

    private final SftpProfileService sftpProfileService;
    private final SftpProfileXrefKhostKeyService sftpProfileXrefKhostKeyService;

    private final YfsUserService yfsUserService;
    private final SterlingMailboxService sterlingMailboxService;
    private final MbxVirtualRootService mbxVirtualRootService;
    private final String community;

    private final ManageSfgProfileService manageSfgProfileService;
    private final SciCommunityRepository sciCommunityRepository;

    @Autowired
    public SterlingSfgProfileService(SciTransportServiceDup sciTransportServiceDup, YfsOrganizationServiceDup yfsOrganizationServiceDup,
                                     YfsPersonInfoService yfsPersonInfoService, SciDocExchangeServiceDup sciDocExchangeServiceDup,
                                     SciDeliveryChangeServiceDup sciDeliveryChangeServiceDup, SciPackagingServiceDup sciPackagingServiceDup,
                                     SciProfileServiceDup sciProfileServiceDup, SciTrpSslCertService sciTrpSslCertService,
                                     CertsAndPriKeyService certsAndPriKeyService, SciTranspCaCertServiceDup sciTranspCaCertServiceDup,
                                     CaCertInfoService caCertInfoService, SftpProfileService sftpProfileService,
                                     SftpProfileXrefKhostKeyService sftpProfileXrefKhostKeyService, YfsUserService yfsUserService,
                                     SterlingMailboxService sterlingMailboxService, MbxVirtualRootService mbxVirtualRootService,
                                     @Value("${sterling-b2bi.b2bi-api.b2bi-sfg-api.community-name}") String community, ManageSfgProfileService manageSfgProfileService,
                                     SciCommunityRepository sciCommunityRepository) {
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
        this.sftpProfileService = sftpProfileService;
        this.sftpProfileXrefKhostKeyService = sftpProfileXrefKhostKeyService;
        this.yfsUserService = yfsUserService;
        this.sterlingMailboxService = sterlingMailboxService;
        this.mbxVirtualRootService = mbxVirtualRootService;
        this.community = community;
        this.manageSfgProfileService = manageSfgProfileService;
        this.sciCommunityRepository = sciCommunityRepository;
    }

    private void deleteSiProfiles(String profileName, Protocol protocol) {
        AtomicReference<String> userId = new AtomicReference<>("");
        switch (protocol) {
            case SFG_FTP:
            case SFG_FTPS:
                sciTransportServiceDup.get(profileName + "_" + CONSUMER).ifPresent(sciTransportEntity -> {
                    userId.set(sciTransportEntity.getTranspActUserId());
                    yfsOrganizationServiceDup.get(sciTransportEntity.getEntityId()).ifPresent(yfsOrganizationEntity -> {
                        yfsPersonInfoService.delete(yfsOrganizationEntity.getCorporateAddressKey());
                        yfsOrganizationServiceDup.deleteByObjectId(sciTransportEntity.getEntityId());
                    });
                    sciProfileServiceDup.findByEntityId(sciTransportEntity.getEntityId()).ifPresent(sciProfileEntity -> {
                        sciPackagingServiceDup.delete(sciProfileEntity.getPackagingId());
                        sciDeliveryChangeServiceDup.findByObjectId(sciProfileEntity.getDelivChannelId()).ifPresent(sciDelivChan -> {
                            sciDocExchangeServiceDup.delete(sciDelivChan.getDocExchangeId());
                            sciDeliveryChangeServiceDup.delete(sciProfileEntity.getDelivChannelId());
                        });
                        sciProfileServiceDup.deleteByEntityId(sciTransportEntity.getEntityId());
                    });
                    sciTrpSslCertService.deleteAllByTransportId(sciTransportEntity.getObjectId());
                    sciTranspCaCertServiceDup.deleteAllByTransportId(sciTransportEntity.getObjectId());

                    sciTransportServiceDup.delete(sciTransportEntity.getTransportKey());
                });
                break;
            case SFG_SFTP:
                //TODO
                break;
            default:
                //We can ignore this
                break;
        }
        if (isNotNull(userId)) {
            yfsUserService.delete(userId.get());
            mbxVirtualRootService.delete(userId.get());
        }

    }

    public void save(SterlingProfilesModel sterlingProfilesModel, Protocol protocol, String oldProfileName, Boolean isUpdate) {

        sterlingProfilesModel.setHubInfo(FALSE);
        sterlingProfilesModel.setCreateProducerProfile(TRUE);
        /*Create or Save the Consumer Record*/
        sterlingProfilesModel.setConsumerProfile(TRUE);
        sterlingProfilesModel.setConsumerCreated(FALSE);
        sterlingProfilesModel.setProfileType(CONSUMER);
        sterlingProfilesModel.setSfgSubDetailsLoaded(FALSE);
        save1(sterlingProfilesModel.setReceivingProtocol("CUSTOM"), protocol, oldProfileName, false, isUpdate);

        /*Create or Update the Producer Record*/
        sterlingProfilesModel.setConsumerProfile(FALSE);
        sterlingProfilesModel.setConsumerCreated(TRUE);
        sterlingProfilesModel.setProfileType(PRODUCER);
        sterlingProfilesModel.setSfgSubDetailsLoaded(TRUE);
        save1(sterlingProfilesModel.setReceivingProtocol("MAILBOX"), protocol, oldProfileName, true, isUpdate);

        /*Create or Update MailBoxes into SI*/
        sterlingMailboxService.createAll(convertSterlingProfilesModelToMbxMailBoxModels.apply(sterlingProfilesModel));

        /*Create or Update User into SI*/
        yfsUserService.save(convertSterlingProfilesModelToYfsUserModel.apply(sterlingProfilesModel), sterlingProfilesModel.isMergeUser(), FALSE);

        /*Create or Update Virtual Root into SI*/
        mbxVirtualRootService.save(
                sterlingProfilesModel.getUserName(),
                sterlingProfilesModel.getSubscriberType().equals("TP") ? sterlingProfilesModel.getInDirectory() : sterlingProfilesModel.getOutDirectory(),
                sterlingProfilesModel.isMergeUser()
        );

    }

    private void save1(SterlingProfilesModel sterlingProfilesModel, Protocol protocol, String oldProfileName, boolean isConsumerCreated, boolean isUpdate) {

        String objectKey = getPrimaryKey.apply(SCI_TR_OBJ_PRE_APPEND, SCI_RANDOM_COUNT);
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

        if (isUpdate) {
            Optional<SciTransportEntity> sciTransportEntityOptional = sciTransportServiceDup.get(oldProfileName + "_" + sterlingProfilesModel.getProfileType());

            if (sciTransportEntityOptional.isPresent()) {
                transportObjectId = sciTransportEntityOptional.get().getObjectId();
                transportEntityId = sciTransportEntityOptional.get().getEntityId();
                transportKey = sciTransportEntityOptional.get().getTransportKey();

                if (!isConsumerCreated) {
                    Optional<YfsOrganizationDupEntity> yfsOrganizationEntityOptional = yfsOrganizationServiceDup.get(transportEntityId);
                    if (yfsOrganizationEntityOptional.isPresent()) {
                        corporateAddressKey = yfsOrganizationEntityOptional.get().getCorporateAddressKey();
                    } else {
                        throw internalServerError("YfsOrganization Record notfound");
                    }
                } else {
                    corporateAddressKey = "";
                }

                Optional<SciProfileEntity> sciProfileOptional = sciProfileServiceDup.findFirstByObjectNameAndEntityId(oldProfileName + "_" + sterlingProfilesModel.getProfileType(), transportEntityId);
                if (sciProfileOptional.isPresent()) {
                    sciProfileObjectId = sciProfileOptional.get().getObjectId();
                    sciProfileKey = sciProfileOptional.get().getProfileKey();
                    sciPackagingObjectId = sciProfileOptional.get().getPackagingId().trim();
                    sciDeliveryChanObjectId = sciProfileOptional.get().getDelivChannelId();
                } else {
                    throw internalServerError("SciProfileEntity Record notfound");
                }

                Optional<SciDelivChanEntity> sciDelivChanOptional = sciDeliveryChangeServiceDup.findByObjectId(sciDeliveryChanObjectId);
                if (sciDelivChanOptional.isPresent()) {
                    sciDelChanObjectName = sciDelivChanOptional.get().getObjectName();
                    sciDeliveryChanKey = sciDelivChanOptional.get().getDeliveryChannelKey();
                    sciDocExchangeObj = sciDelivChanOptional.get().getDocExchangeId();
                } else {
                    throw internalServerError("SciDeliveryChain Record notfound");
                }

                Optional<SciDocExchangeEntity> sciDocExchangeOptional = sciDocExchangeServiceDup.findById(transportEntityId);
                if (sciDocExchangeOptional.isPresent()) {
                    sciDocExchangeKey = sciDocExchangeOptional.get().getDocExchangeKey();
                } else {
                    throw internalServerError("DocExchange Record notfound");
                }

                Optional<SciPackagingEntity> sciPackagingOptional = sciPackagingServiceDup.get(sciPackagingObjectId);
                if (sciPackagingOptional.isPresent()) {
                    sciPackagingKey = sciPackagingOptional.get().getPackagingKey();
                } else {
                    throw internalServerError("SciPackagingEntity Record notfound");
                }

            } else {
                throw internalServerError("Transport Record notfound");
            }
        } else {
            transportObjectId = objectKey + ":-3afb";
            transportKey = objectKey + "-4trk";
            if (isConsumerCreated) {
                Optional<SciTransportEntity> sciTransportEntityOptional = sciTransportServiceDup.get(oldProfileName + "_" + CONSUMER);
                if (sciTransportEntityOptional.isPresent()) {
                    transportEntityId = sciTransportEntityOptional.get().getEntityId();
                } else {
                    throw internalServerError("SciTransportEntity Record notfound, while persisting Producer Account");
                }
            } else {
                transportEntityId = objectKey + ":-3b0a";
            }
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

        }

        sciTransportServiceDup.save(convertSterlingProfilesModelToTransportDTO.apply(sterlingProfilesModel, protocol)
                .setTransportKey(transportKey)
                .setObjectId(transportObjectId)
                .setEntityId(transportEntityId));
        if (!isConsumerCreated) {
            yfsOrganizationServiceDup.save(convertSterlingProfilesModelToYfsOrganizationDTO.apply(sterlingProfilesModel)
                    .setObjectId(transportEntityId)
                    .setCorporateAddressKey(corporateAddressKey)
                    .setProtocol(protocol));
        }

        sciProfileServiceDup.save(convertSterlingProfilesModelToProfileDTO.apply(sterlingProfilesModel)
                .setObjectId(sciProfileObjectId)
                .setEntityId(transportEntityId)
                .setPackagingId(sciPackagingObjectId)
                .setDelChannelId(sciDeliveryChanObjectId)
                .setProfileKey(sciProfileKey));
        sciPackagingServiceDup.save(convertSterlingProfilesModelToPackagingDTO.apply(sterlingProfilesModel)
                .setObjectId(sciPackagingObjectId)
                .setEntityId(transportEntityId)
                .setPackagingKey(sciPackagingKey));
        sciDeliveryChangeServiceDup.save(convertSterlingProfilesModelToDeliveryChanDTO.apply(sterlingProfilesModel)
                .setObjectId(sciDeliveryChanObjectId)
                .setObjectName(sciDelChanObjectName)
                .setEntityId(transportEntityId)
                .setTransportId(transportObjectId)
                .setDocExchangeId(sciDocExchangeObj)
                .setDeliveryChannelKey(sciDeliveryChanKey));
        sciDocExchangeServiceDup.save(convertSterlingProfilesModelToDocExchangeDTO.apply(sterlingProfilesModel, protocol)
                .setObjectId(sciDocExchangeObj)
                .setEntityId(transportEntityId)
                .setDocExchangeKey(sciDocExchangeKey));

        if (!isConsumerCreated && isNotNull(corporateAddressKey)) {
            yfsPersonInfoService.save(convertSterlingProfilesModelToYfsPersonInfo.apply(sterlingProfilesModel)
                    .setPersonInfoKey(corporateAddressKey));
        }

        if (sterlingProfilesModel.isCreateSfgProfile()) {
            LOGGER.info("Creating SFG Profile");
            String orgKey = isNotNull(sterlingProfilesModel.getPartnerCode()) ? sterlingProfilesModel.getPartnerCode() : sterlingProfilesModel.getProfileName();
            manageSfgProfileService.save(
                    SterlingFunctions.convertSterlingProfilesModelToSfgProfileDetailsDTO.apply(sterlingProfilesModel)
                            .setOrganizationKey(orgKey.substring(0, Math.min(orgKey.length(), 24)))
                            .setTransportEntityKey(transportEntityId)
                            .setSciProfileObjectId(sciProfileObjectId)
                            .setCommunityName(community)
                            .setCommunityId(getCommunityId(isNotNull(sterlingProfilesModel.getCommunityName()) ? sterlingProfilesModel.getCommunityName() : community))
                            .setCommunityProfileId(getCommunityProfile(sterlingProfilesModel.getReceivingProtocol()))
                            .setOnlyProducer(FALSE)
            );
        }
    }

    //TODO
    private void duplicatePartner(String partnerId) {
        /*Need to Check */
    }

    public SterlingProfilesModel get(SterlingProfilesModel sterlingProfilesModel, Protocol protocol, String profileType, boolean isSfgProfile) {

        Optional<TransportDTO> transportDTOOptional = sciTransportServiceDup.getTransportDTO(sterlingProfilesModel.getProfileName() + "_" + profileType);

        if (transportDTOOptional.isPresent()) {
            addTransportDTOToSterlingProfilesModel.apply(sterlingProfilesModel, transportDTOOptional.get());

            Optional<YfsOrganizationDupEntity> yfsOrganizationEntityOptional = yfsOrganizationServiceDup.get(transportDTOOptional.get().getEntityId());
            if (yfsOrganizationEntityOptional.isPresent()) {
                /*Need to get the HubInfo here*/
                if (isNotNull(yfsOrganizationEntityOptional.get().getCorporateAddressKey())) {
                    addYfsPersonInfoToSterlingProfilesModel.apply(sterlingProfilesModel, yfsPersonInfoService.getYfsPersonInfoDTO(yfsOrganizationEntityOptional.get().getCorporateAddressKey()));
                }
                addYfsUserModelToSterlingProfilesModel.apply(sterlingProfilesModel, yfsUserService.findByOrganizationKey(yfsOrganizationEntityOptional.get().getOrganizationKey()));
            } else {
                throw internalServerError("YfsOrganization Record notfound");
            }

            Optional<SciProfileEntity> sciProfileOptional = sciProfileServiceDup.findByEntityId(transportDTOOptional.get().getEntityId());
            if (sciProfileOptional.isPresent()) {
                if (isSfgProfile) {
                    SfgProfileDetailsDTO sfgProfileDetailsDTO = manageSfgProfileService.get(transportDTOOptional.get().getEntityId(), sciProfileOptional.get().getObjectId());
                    sterlingProfilesModel.setCustomProtocolExtensions(sfgProfileDetailsDTO.getExtensionKeysMap()
                            .entrySet()
                            .stream()
                            .map(entrySet -> new CommunityManagerKeyValueModel(entrySet.getKey(), entrySet.getValue()))
                            .collect(Collectors.toList()))
                            .setCommunityName(getCommunityName(sfgProfileDetailsDTO.getCommunityId()));
                }
            } else {
                throw internalServerError("SciProfileEntity Record notfound");
            }
        } else {
            throw notFound("Transport entity not found.");
        }

//        switch (protocol) {
//            case SFG_FTP:
//            case SFG_FTPS:
//                //TODO - Need to find the exact key to retrieve the data
//
//                break;
//            case SFG_SFTP:
//                //TODO
//                break;
//            default:
//                //No need to do any operations here
//                break;
//        }

        return sterlingProfilesModel;
    }


    private String getCommunityProfile(String protocol) {
        AtomicReference<String> sciCommunityProfileId = new AtomicReference<>("");
        Optional<YfsOrganizationDupEntity> yfsOrganizationEntityOptional = yfsOrganizationServiceDup.findFirstByOrganizationName(community + "_AFTCommunity");
        if (yfsOrganizationEntityOptional.isPresent()) {
            Map<String, String> sciProfileIdsMap = sciProfileServiceDup.findAllByEntityId(yfsOrganizationEntityOptional.get().getObjectId())
                    .stream()
                    .collect(Collectors.toMap(SciProfileEntity::getDelivChannelId, SciProfileEntity::getObjectId));
            Map<String, String> transportIdsMap = sciDeliveryChangeServiceDup.findAllByObjectIds(new ArrayList<>(sciProfileIdsMap.keySet()))
                    .stream()
                    .collect(Collectors.toMap(SciDelivChanEntity::getTransportId, SciDelivChanEntity::getObjectId));
            List<SciTransportEntity> sciTransportEntities = sciTransportServiceDup.findAllByObjectIds(new ArrayList<>(transportIdsMap.keySet()));
            sciTransportEntities.forEach(sciTransportEntity -> {

                if (protocol.equals("CUSTOM")) {
                    if (sciTransportEntity.getObjectName().trim().equalsIgnoreCase("sftp-protocol") ||
                            sciTransportEntity.getObjectName().trim().equalsIgnoreCase("EFX-Standard-Protocol")) {
                        String sciDeliveryChanObjectId = transportIdsMap.get(sciTransportEntity.getObjectId());
                        sciCommunityProfileId.set(sciProfileIdsMap.get(sciDeliveryChanObjectId));
                    }
                } else {
                    if (sciTransportEntity.getObjectName().trim().equalsIgnoreCase(protocol)) {
                        String sciDeliveryChanObjectId = transportIdsMap.get(sciTransportEntity.getObjectId());
                        sciCommunityProfileId.set(sciProfileIdsMap.get(sciDeliveryChanObjectId));
                    }
                }

            });
        } else {
            throw internalServerError("Community Partner Configuration not available.");
        }
        if (!isNotNull(sciCommunityProfileId.get())) {
            throw internalServerError("Community Profile not found, Community Name : " + community);
        }
        return sciCommunityProfileId.get();
    }

    private String getCommunityId(String communityName) {
        return sciCommunityRepository.findFirstByObjectName(communityName).orElseThrow(() -> internalServerError("Provided Community is not Available, Community Name : " + communityName)).getObjectId();
    }

    private String getCommunityName(String communityId) {
        return sciCommunityRepository.findById(communityId).orElseThrow(() -> internalServerError("Community Entity not found")).getObjectName();
    }


    public void delete(String profileName, Protocol protocol, boolean deleteConsumer, boolean deleteProducer, boolean deleteMailbox, boolean deleteUser, boolean deleteSfgProfile, boolean onlyProducer) {

        /*Delete Consumer and User Details*/
        if (deleteConsumer) {
            deleteProfile(profileName, protocol, CONSUMER, deleteUser, onlyProducer, deleteSfgProfile);
        }

        /*Delete Producer and User Details*/
        if (deleteProducer) {
            deleteProfile(profileName, protocol, PRODUCER, deleteUser, onlyProducer, deleteSfgProfile);
        }

        /*Delete Mailbox Details*/
        if (deleteMailbox) {
            //TODO Need to find the way for assigned Mailboxes
            sterlingMailboxService.delete("");
        }


    }

    private void deleteProfile(String profileName, Protocol protocol, String profileType, boolean deleteUser, boolean onlyProducer, boolean deleteSfgProfile) {

        sciTransportServiceDup.get(profileName + "_" + profileType).ifPresent(sciTransportEntity -> {
            AtomicReference<String> orgKey = new AtomicReference<>("");
            if (profileType.equals(CONSUMER) || onlyProducer) {
                yfsOrganizationServiceDup.get(sciTransportEntity.getEntityId()).ifPresent(yfsOrganizationEntity -> {
                    orgKey.set(yfsOrganizationEntity.getOrganizationKey());
                    if (deleteUser) {
                        yfsUserService.deleteByOrganizationKey(yfsOrganizationEntity.getOrganizationKey());
                        mbxVirtualRootService.delete(yfsOrganizationEntity.getOrganizationKey());
                    }
                    yfsPersonInfoService.delete(yfsOrganizationEntity.getCorporateAddressKey());
                    yfsOrganizationServiceDup.deleteByObjectId(sciTransportEntity.getEntityId());
                });
            }

            sciProfileServiceDup.findFirstByObjectNameAndEntityId(profileName + "_" + profileType, sciTransportEntity.getEntityId()).ifPresent(sciProfileEntity -> {
                sciPackagingServiceDup.delete(sciProfileEntity.getPackagingId());
                sciDeliveryChangeServiceDup.findByObjectId(sciProfileEntity.getDelivChannelId()).ifPresent(sciDelivChan -> {
                    sciDocExchangeServiceDup.delete(sciDelivChan.getDocExchangeId());
                    sciDeliveryChangeServiceDup.delete(sciProfileEntity.getDelivChannelId());
                });
                if (deleteSfgProfile) {
                    manageSfgProfileService.delete(
                            new SfgProfileDetailsDTO()
                                    .setOrganizationKey(isNotNull(orgKey.get()) ? orgKey.get() : profileName.substring(0, Math.min(profileName.length(), 24)))
                                    .setTransportEntityKey(sciTransportEntity.getEntityId())
                                    .setSciProfileObjectId(sciProfileEntity.getObjectId())
                                    .setOnlyProducer(FALSE)
                    );
                }
                sciProfileServiceDup.deleteByEntityId(sciTransportEntity.getEntityId());
            });

            switch (protocol) {
                case SFG_FTP:
                case SFG_FTPS:
                    sciTrpSslCertService.deleteAllByTransportId(sciTransportEntity.getObjectId());
                    sciTranspCaCertServiceDup.deleteAllByTransportId(sciTransportEntity.getObjectId());
                    break;
                case SFG_SFTP:
                    //TODO
                default:
                    //No need to do any operation
                    break;
            }

            sciTransportServiceDup.delete(sciTransportEntity.getTransportKey());
        });

    }

}
