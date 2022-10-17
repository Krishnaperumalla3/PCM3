/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc.
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

package com.pe.pcm.application.sterling;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pe.pcm.activity.ActivityHistoryService;
import com.pe.pcm.application.ApplicationModel;
import com.pe.pcm.application.ApplicationService;
import com.pe.pcm.application.entity.ApplicationEntity;
import com.pe.pcm.b2b.B2BApiService;
import com.pe.pcm.b2b.protocol.RemoteAs2Profile;
import com.pe.pcm.enums.Protocol;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.miscellaneous.UserUtilityService;
import com.pe.pcm.partner.PartnerModel;
import com.pe.pcm.protocol.As2Model;
import com.pe.pcm.protocol.ManageProtocolService;
import com.pe.pcm.protocol.as2.As2Service;
import com.pe.pcm.protocol.as2.entity.As2Entity;
import com.pe.pcm.sterling.partner.sci.SterlingPartnerProfileService;
import com.pe.pcm.utils.KeyGeneratorUtil;
import com.pe.pcm.utils.PCMConstants;
import com.pe.pcm.workflow.ProcessService;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import static com.pe.pcm.exception.GlobalExceptionHandler.*;
import static com.pe.pcm.protocol.function.ProtocolFunctions.applyAs2DefaultValues;
import static com.pe.pcm.protocol.function.ProtocolFunctions.convertAs2ValuesFromStringToNum;
import static com.pe.pcm.utils.CommonFunctions.*;
import static com.pe.pcm.utils.CommunityManagerValidator.validate;
import static com.pe.pcm.utils.KeyGeneratorUtil.getChildPrimaryKey;
import static com.pe.pcm.utils.KeyGeneratorUtil.getPrimaryKey;
import static com.pe.pcm.utils.PCMConstants.*;
import static java.lang.Boolean.FALSE;
import static org.springframework.beans.BeanUtils.copyProperties;

/**
 * @author Kiran Reddy.
 */
@Service
public class SterlingAs2ApplicationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SterlingAs2ApplicationService.class);

    private final As2Service as2Service;
    private final ApplicationService applicationService;
    private final ProcessService processService;
    private final UserUtilityService userUtilityService;
    private final ManageProtocolService manageProtocolService;
    private final ActivityHistoryService activityHistoryService;
    private final SterlingPartnerProfileService sterlingPartnerProfileService;

    private final Boolean isB2bApiActive;
    private final Boolean isAs2B2bApiActive;

    private final B2BApiService b2BApiService;
    private final String community;


    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public SterlingAs2ApplicationService(As2Service as2Service, ApplicationService applicationService,
                                         ProcessService processService, UserUtilityService userUtilityService,
                                         ManageProtocolService manageProtocolService, ActivityHistoryService activityHistoryService,
                                         SterlingPartnerProfileService sterlingPartnerProfileService,
                                         @Value("${sterling-b2bi.b2bi-api.active}") Boolean isB2bApiActive,
                                         @Value("${sterling-b2bi.b2bi-api.as2.active}") Boolean isAs2B2bApiActive,
                                         B2BApiService b2BApiService,
                                         @Value("${sterling-b2bi.b2bi-api.b2bi-sfg-api.community-name}") String community) {
        this.as2Service = as2Service;
        this.applicationService = applicationService;
        this.processService = processService;
        this.userUtilityService = userUtilityService;
        this.manageProtocolService = manageProtocolService;
        this.activityHistoryService = activityHistoryService;
        this.sterlingPartnerProfileService = sterlingPartnerProfileService;
        this.isB2bApiActive = isB2bApiActive;
        this.isAs2B2bApiActive = isAs2B2bApiActive;
        this.b2BApiService = b2BApiService;
        this.community = community;
    }

    @Transactional
    public String create(As2Model as2Model, boolean isBulkUpload) {
        validate(as2Model);
        String pkId;
        if (!Optional.ofNullable(Protocol.findProtocol(as2Model.getProtocol())).isPresent()) {
            throw unknownProtocol();
        }
        applyAs2DefaultValues.apply(as2Model);
        if (isBulkUpload) {
            sterlingPartnerProfileService.checkSciProfileDup(isNotNull(as2Model.getCustomProfileName()) ? as2Model.getCustomProfileName() : as2Model.getProfileName());
        } else {
            applicationService.find(as2Model.getProfileId()).ifPresent(tradingPartnerEntity -> {
                throw conflict(APPLICATION);
            });
        }

        if (isB2bApiActive) {
            if (isAs2B2bApiActive) {
                pkId = saveUsingB2bApi(as2Model);
            } else {
                pkId = createUsingPcm(as2Model, isBulkUpload);
            }
        } else {
            return createUsingPcm(as2Model, isBulkUpload);
        }
        userUtilityService.addPartnerToCurrentUser(pkId, APP);
        return pkId;
    }

    @Transactional
    public void update(As2Model as2Model) {
        validate(as2Model);
        applyAs2DefaultValues.apply(as2Model);
        if (isB2bApiActive) {
            if (isAs2B2bApiActive) {
                updateUsingB2bApi(as2Model);
            } else {
                updateUsingPcm(as2Model);
            }
        } else {
            updateUsingPcm(as2Model);
        }
    }

    @Transactional
    public void delete(String pkId) {
        if (!processService.findByApplicationProfile(pkId).isEmpty()) {
            throw internalServerError("Unable to delete this Application, Because it has workflow..");
        }
        ApplicationEntity applicationEntity = applicationService.get(pkId);
        As2Entity as2Entity = as2Service.get(pkId);
        if (isB2bApiActive) {
            if (isAs2B2bApiActive) {
                deleteThroughB2bApi(applicationEntity, as2Entity.getIsHubInfo());
            } else {
                deleteThroughPcm(applicationEntity, as2Entity.getAs2Identifier());
            }
        }

        as2Service.delete(pkId);
        applicationService.delete(applicationEntity);
    }

    private void deleteThroughPcm(ApplicationEntity applicationEntity, String as2Identifier) {
        sterlingPartnerProfileService.delete(applicationEntity.getApplicationId() + "_" + applicationEntity.getApplicationName(),
                as2Identifier,
                Optional.ofNullable(Protocol.findProtocol(applicationEntity.getAppIntegrationProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol),
                false,
                false,
                null,
                true,
                "/" + applicationEntity.getApplicationName() + "/Inbound",
                "/" + applicationEntity.getApplicationName() + "/Outbound"
        );
    }

    public As2Model get(String pkId) {
        As2Model as2Model = new As2Model();
        ApplicationEntity applicationEntity = applicationService.get(pkId);
        as2Model.setProfileName(applicationEntity.getApplicationName());
        as2Model.setPhone(applicationEntity.getPhone());
        as2Model.setEmailId(applicationEntity.getEmailId());
        as2Model.setProtocol(applicationEntity.getAppIntegrationProtocol());
        as2Model.setStatus(convertStringToBoolean(applicationEntity.getAppIsActive()));
        as2Model.setPkId(applicationEntity.getPkId());
        as2Model.setPemIdentifier(applicationEntity.getPemIdentifier());
        as2Model.setCustomProfileName(applicationEntity.getApplicationName());
        as2Model.setPgpInfo(applicationEntity.getPgpInfo());
        getAs2(as2Model, pkId);
        if (isB2bApiActive) {
            if (isAs2B2bApiActive) {
                getUsingB2bApi(as2Model);
            } else {
                getUsingPcm(as2Model);
            }
        } else {
            getUsingPcm(as2Model);
        }
        return as2Model;
    }

    private void getUsingPcm(As2Model as2Model) {
        sterlingPartnerProfileService.get(as2Model);
    }

    private void duplicatePartner(String partnerId) {
        Optional<ApplicationEntity> applicationEntityOptional = applicationService.find(partnerId);
        applicationEntityOptional.ifPresent(applicationEntity -> {
            throw conflict(APPLICATION);
        });
    }

    private String createUsingPcm(As2Model as2Model, boolean isBulkUpload) {
        LOGGER.info("Sterling Profile create() Method.");
        if (!Optional.ofNullable(Protocol.findProtocol(as2Model.getProtocol())).isPresent()) {
            throw unknownProtocol();
        }
        duplicatePartner(as2Model.getProfileId());
        //Converting text values to number
        convertAs2ValuesFromStringToNum.apply(as2Model);
        as2Model.setCustomProfileName(as2Model.getProfileName());
        as2Model.setAs2EmailAddress(isNotNull(as2Model.getAs2EmailAddress()) ? as2Model.getAs2EmailAddress() : as2Model.getEmailId());
        String parentPrimaryKey = getPrimaryKey.apply(PCMConstants.APP_PKEY_PRE_APPEND, PCMConstants.APP_PKEY_RANDOM_COUNT);
        String childPrimaryKey = getChildPrimaryKey(as2Model.getProtocol());
        Protocol protocol = Optional.ofNullable(Protocol.findProtocol(as2Model.getProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);
        saveProtocol(as2Model, protocol, parentPrimaryKey, childPrimaryKey, as2Model.getProfileId() + "_" + as2Model.getCustomProfileName(), as2Model.getCustomProfileName(), false, isBulkUpload);

        if (!isBulkUpload) {
            ApplicationModel applicationModel = new ApplicationModel();
            copyProperties(as2Model, applicationModel);
            applicationService.save(applicationModel, parentPrimaryKey, childPrimaryKey);
            activityHistoryService.saveApplicationActivity(parentPrimaryKey, "Application created.");
        }

        userUtilityService.addPartnerToCurrentUser(parentPrimaryKey, TP);
        LOGGER.info("Completed Sterling Profile create() Method.");
        return parentPrimaryKey;
    }

    private void updateUsingPcm(As2Model as2Model) {

        //Converting text values to number
        convertAs2ValuesFromStringToNum.apply(as2Model);

        ApplicationEntity oldApplicationEntity = SerializationUtils.clone(applicationService.get(as2Model.getPkId()));
        if (!oldApplicationEntity.getApplicationId().equalsIgnoreCase(as2Model.getProfileId())) {
            duplicatePartner(as2Model.getProfileId());
        }

        as2Model.setCustomProfileName(as2Model.getProfileName());
        as2Model.setAs2EmailAddress(isNotNull(as2Model.getAs2EmailAddress()) ? as2Model.getAs2EmailAddress() : as2Model.getEmailId());
        String parentPrimaryKey = oldApplicationEntity.getPkId();
        String childPrimaryKey = oldApplicationEntity.getAppProtocolRef();
        Protocol protocol = Optional.ofNullable(Protocol.findProtocol(as2Model.getProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);
        Map<String, String> oldAs2EntityMap = new LinkedHashMap<>();
        boolean isUpdate;
        if (!protocol.getProtocol().equalsIgnoreCase(oldApplicationEntity.getAppIntegrationProtocol())) {
            manageProtocolService.deleteProtocol(oldApplicationEntity.getAppIntegrationProtocol(), oldApplicationEntity.getPkId());
            childPrimaryKey = getChildPrimaryKey(as2Model.getProtocol());
            isUpdate = false;
        } else {
            oldAs2EntityMap = mapper.convertValue(as2Service.get(parentPrimaryKey), new TypeReference<Map<String, String>>() {
            });
            isUpdate = true;
        }
        String oldCustomProfileName = oldApplicationEntity.getApplicationName();
        Map<String, String> newFtpEntityMap = mapper.convertValue(
                saveProtocol(as2Model, protocol, parentPrimaryKey, childPrimaryKey,
                        oldApplicationEntity.getApplicationId() + "_" + oldCustomProfileName, oldCustomProfileName, isUpdate, false), new TypeReference<Map<String, String>>() {
                });
        PartnerModel partnerModel = new PartnerModel();
        BeanUtils.copyProperties(as2Model, partnerModel);
        ApplicationEntity newApplicationEntity = applicationService.save(partnerModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.updateApplicationActivity(oldApplicationEntity, newApplicationEntity, oldAs2EntityMap, newFtpEntityMap, isUpdate);
    }

    private As2Entity saveProtocol(As2Model as2Model, Protocol protocol, String parentPrimaryKey, String childPrimaryKey, String customProfileName, String oldAs2ProfileName, boolean isUpdate, boolean isBulkUpload) {
        String sciProfileId = sterlingPartnerProfileService.save(as2Model.setSubscriberType(APP), protocol, customProfileName, oldAs2ProfileName, isUpdate);
        //TODO : Need to remove this soon
        if (isUpdate) {
            try {
                if (isNotNull(as2Model.getIsSIProfile()) && as2Model.getIsSIProfile()) {
                    b2BApiService.updateRemoteAs2Profile(new RemoteAs2Profile(as2Model, true, community), oldAs2ProfileName + "-ref");
                }
            } catch (CommunityManagerServiceException e) {
                if (e.getErrorMessage() != null && e.getErrorMessage().equals("{\n" +
                        "  \"errorCode\": 500,\n" +
                        "  \"errorDescription\": null\n" +
                        "}") && !as2Model.isOnlyPCM() && isNotNull(as2Model.getIsSIProfile()) && as2Model.getIsSIProfile()) {
                    b2BApiService.createRemoteAs2Profile(new RemoteAs2Profile(as2Model, false, community));
                } else {
                    throw internalServerError(e.getErrorMessage());
                }
            }
        } else {
            if (!as2Model.isOnlyPCM() && isNotNull(as2Model.getIsSIProfile()) && as2Model.getIsSIProfile()) {
                b2BApiService.createRemoteAs2Profile(new RemoteAs2Profile(as2Model, false, community));
            }
        }
        if (!isBulkUpload) {
            return as2Service.save(parentPrimaryKey, childPrimaryKey, TP, as2Model, sciProfileId);
        }
        return new As2Entity();
    }

    private void getAs2(As2Model as2Model, String subscriberPkId) {
        As2Entity as2Entity = as2Service.get(subscriberPkId);
        as2Model.setAs2Identifier(as2Entity.getAs2Identifier());
        as2Model.setProfileId(as2Entity.getIdentityName());
        as2Model.setAs2EmailAddress(as2Entity.getEmailAddress());
        as2Model.setProfileName(as2Entity.getProfileName());
        as2Model.setStatus(convertStringToBoolean(as2Entity.getIsActive()));
        as2Model.setHubInfo(convertStringToBoolean(as2Entity.getIsHubInfo()));
        as2Model.setHttpclientAdapter(as2Entity.getHttpClientAdapter());
        if (as2Entity.getIsHubInfo().equalsIgnoreCase("N")) {
            as2Model.setSenderId(as2Entity.getSenderId());
            as2Model.setSenderQualifier(as2Entity.getSenderQualifier());
            as2Model.setResponseTimeout(isNotNull(as2Entity.getResponseTimeout()) ? Integer.parseInt(as2Entity.getResponseTimeout()) : 5);
            as2Model.setPayloadType(as2Entity.getPayloadType());
            as2Model.setMimeType(isNotNull(as2Entity.getMimeType()) ? as2Entity.getMimeType() : "Application");
            as2Model.setMimeSubType(as2Entity.getMimeSubType());
            as2Model.setSslType(as2Entity.getSslType());
            as2Model.setCipherStrength(as2Entity.getCipherStrength());
            as2Model.setCompressData(as2Entity.getCompressData());
            as2Model.setPayloadEncryptionAlgorithm(as2Entity.getPayloadEncryptionAlgorithm());
            as2Model.setPayloadSecurity(as2Entity.getPayloadSecurity());
            as2Model.setEncryptionAlgorithm(as2Entity.getEncryptionAlgorithm());
            as2Model.setSignatureAlgorithm(as2Entity.getSignatureAlgorithm());
            as2Model.setMdn(convertStringToBoolean(as2Entity.getIsMdnRequired()));
            as2Model.setMdnType(as2Entity.getMdnType());
            as2Model.setMdnEncryption(as2Entity.getMdnEncryption());
            as2Model.setPassword(as2Entity.getPassword());
            as2Model.setReceiptToAddress(as2Entity.getReceiptToAddress());
            as2Model.setUsername(as2Entity.getUsername());
            as2Model.setCaCertificate(as2Entity.getCaCert());
        }
        as2Model.setExchangeCertificate(as2Entity.getExchgCert());
        as2Model.setSigningCertification(as2Entity.getSigningCert());
        as2Model.setKeyCertification(as2Entity.getKeyCert());
        as2Model.setKeyCertificatePassphrase(as2Entity.getKeyCertPassphrase());
    }

    private void deleteThroughB2bApi(ApplicationEntity applicationEntity, String hubInfo) {
        as2Service.deleteThroughB2bApi(convertStringToBoolean(hubInfo), applicationEntity.getApplicationId());
        as2Service.delete(applicationEntity.getPkId());
    }

    private void updateUsingB2bApi(As2Model as2Model) {
        ApplicationEntity oldApplicationEntity = SerializationUtils.clone(applicationService.get(as2Model.getPkId()));
        String parentPrimaryKey = oldApplicationEntity.getPkId();
        String childPrimaryKey = oldApplicationEntity.getAppProtocolRef();
        Protocol protocol = Optional.ofNullable(Protocol.findProtocol(oldApplicationEntity.getAppIntegrationProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);
        Map<String, String> oldAs2EntityMap = mapper.convertValue(as2Service.get(parentPrimaryKey),
                new TypeReference<Map<String, String>>() {
                });
        boolean isUpdate = true;
        if (!protocol.getProtocol().equalsIgnoreCase(as2Model.getProtocol())) {
            deleteThroughB2bApi(oldApplicationEntity, oldAs2EntityMap.get("isHubInfo"));
            isUpdate = false;
        }
        Map<String, String> newRemoteAs2EntityMap = mapper.convertValue(as2Service.saveFromB2bApi(as2Model, parentPrimaryKey, childPrimaryKey,
                isUpdate, TP), new TypeReference<Map<String, String>>() {
        });
        ApplicationModel applicationModel = new ApplicationModel();
        copyProperties(as2Model, applicationModel);
        ApplicationEntity newApplicationEntity = applicationService.save(applicationModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.updateApplicationActivity(oldApplicationEntity,
                newApplicationEntity, oldAs2EntityMap, newRemoteAs2EntityMap, isUpdate);
    }

    private String saveUsingB2bApi(As2Model as2Model) {
        applyAs2DefaultValues.apply(as2Model);
        String parentPrimaryKey = KeyGeneratorUtil.getPrimaryKey.apply(APP_PKEY_PRE_APPEND,
                APP_PKEY_RANDOM_COUNT);
        String childPrimaryKey = KeyGeneratorUtil.getChildPrimaryKey(as2Model.getProtocol());
        as2Service.saveFromB2bApi(as2Model, parentPrimaryKey, childPrimaryKey, FALSE, TP);
        ApplicationModel applicationModel = new ApplicationModel();
        copyProperties(as2Model, applicationModel);
        applicationService.save(applicationModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.savePartnerActivity(parentPrimaryKey, PARTNER_CREATED);
        return parentPrimaryKey;
    }

    private void getUsingB2bApi(As2Model as2Model) {
        as2Service.getUsingB2bApi(as2Model);
    }

    @Transactional
    public void changeStatus(String pkId, Boolean status) {
        ApplicationEntity newApplicationEntity = applicationService.get(pkId);
        ApplicationEntity oldApplicationEntity = SerializationUtils.clone(newApplicationEntity);
        newApplicationEntity.setAppIsActive(convertBooleanToString(status));
        As2Entity as2Entity = as2Service.get(pkId);
        Map<String, String> oldEntityMap = getEntityMap(as2Entity);
        as2Entity.setIsActive(convertBooleanToString(status));
        Map<String, String> newEntityMap = getEntityMap(as2Entity);
        activityHistoryService.updateApplicationActivity(oldApplicationEntity,
                newApplicationEntity, oldEntityMap, newEntityMap, true);
    }
}
