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

package com.pe.pcm.activity;

import com.pe.pcm.application.AppActivityHistoryRepository;
import com.pe.pcm.application.entity.AppActivityHistoryEntity;
import com.pe.pcm.application.entity.ApplicationEntity;
import com.pe.pcm.certificate.CaCertInfoService;
import com.pe.pcm.enums.DisplayFields;
import com.pe.pcm.envelope.EdiActivityHistoryRepository;
import com.pe.pcm.envelope.entity.EdiPropertiesActivityHistoryEntity;
import com.pe.pcm.envelope.entity.EdiPropertiesEntity;
import com.pe.pcm.partner.PartnerActivityHistoryRepository;
import com.pe.pcm.partner.PartnerService;
import com.pe.pcm.partner.entity.PartnerActivityHistoryEntity;
import com.pe.pcm.partner.entity.PartnerEntity;
import com.pe.pcm.utils.KeyGeneratorUtil;
import com.pe.pcm.utils.PCMConstants;
import com.pe.pcm.workflow.WorkFlowActivityHistoryRepository;
import com.pe.pcm.workflow.entity.WorkFlowActivityHistoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.pe.pcm.utils.CommonFunctions.*;
import static com.pe.pcm.utils.PCMConstants.*;

/**
 * @author Chenchu Kiran.
 */
@Service
public class ActivityHistoryService {

    private final PartnerActivityHistoryRepository partnerActivityHistoryRepository;
    private final AppActivityHistoryRepository appActivityHistoryRepository;
    private final EdiActivityHistoryRepository ediActivityHistoryRepository;
    private final WorkFlowActivityHistoryRepository workFlowActivityHistoryRepository;
    private final PartnerService partnerService;
    private final CaCertInfoService caCertInfoService;

    @Autowired
    public ActivityHistoryService(PartnerActivityHistoryRepository partnerActivityHistoryRepository, AppActivityHistoryRepository appActivityHistoryRepository,
                                  EdiActivityHistoryRepository ediActivityHistoryRepository, WorkFlowActivityHistoryRepository workFlowActivityHistoryRepository,
                                  PartnerService partnerService, CaCertInfoService caCertInfoService) {
        this.partnerActivityHistoryRepository = partnerActivityHistoryRepository;
        this.appActivityHistoryRepository = appActivityHistoryRepository;
        this.ediActivityHistoryRepository = ediActivityHistoryRepository;
        this.workFlowActivityHistoryRepository = workFlowActivityHistoryRepository;
        this.partnerService = partnerService;
        this.caCertInfoService = caCertInfoService;
    }

    //TODO : Need to modify
    public void updateEnvelopeActivity(EdiPropertiesEntity oldEntity, EdiPropertiesEntity newEntity) {
        List<String> activities = new ArrayList<>();
        if (notEqual(oldEntity.getPartnername(), newEntity.getPartnername())) {
            activities.add(activityBuilder(oldEntity.getPartnername(), newEntity.getPartnername(), "Partner Profile"));
        }
        if (notEqual(oldEntity.getDirection(), newEntity.getDirection())) {
            activities.add(activityBuilder(oldEntity.getDirection(), newEntity.getDirection(), "Direction"));
        }
        if (notEqual(oldEntity.getValidateinput(), newEntity.getValidateinput())) {
            activities.add(activityBuilder(oldEntity.getValidateinput(), newEntity.getValidateinput(), "Validate Input"));
        }
        if (notEqual(oldEntity.getValidateoutput(), newEntity.getValidateoutput())) {
            activities.add(activityBuilder(oldEntity.getValidateoutput(), newEntity.getValidateoutput(), "Validate Output"));
        }
        if (notEqual(oldEntity.getUseindicator(), newEntity.getUseindicator())) {
            activities.add(activityBuilder(oldEntity.getUseindicator(), newEntity.getUseindicator(), "Test Indicator"));
        }
        if (notEqual(oldEntity.getIsasenderidqal(), newEntity.getIsasenderidqal())) {
            activities.add(activityBuilder(oldEntity.getIsasenderidqal(), newEntity.getIsasenderidqal(), "Sender ID Qualifier(ISA05)"));
        }
        if (notEqual(oldEntity.getIsareceiveridqal(), newEntity.getIsareceiveridqal())) {
            activities.add(activityBuilder(oldEntity.getIsareceiveridqal(), newEntity.getIsareceiveridqal(), "Receiver ID Qualifier(ISA07)"));
        }
        if (notEqual(oldEntity.getIsasenderid(), newEntity.getIsasenderid())) {
            activities.add(activityBuilder(oldEntity.getIsasenderid(), newEntity.getIsasenderid(), "Sender ID(ISA06)"));
        }
        if (notEqual(oldEntity.getIsareceiverid(), newEntity.getIsareceiverid())) {
            activities.add(activityBuilder(oldEntity.getIsareceiverid(), newEntity.getIsareceiverid(), "Receiver ID(ISA08)"));
        }
        if (notEqual(oldEntity.getInterversion(), newEntity.getInterversion())) {
            activities.add(activityBuilder(oldEntity.getInterversion(), newEntity.getInterversion(), "Interchange Version(ISA12)"));
        }
        if (notEqual(oldEntity.getGlobalcontno(), newEntity.getGlobalcontno())) {
            activities.add(activityBuilder(oldEntity.getGlobalcontno(), newEntity.getGlobalcontno(), "Global Control Number"));
        }
        if (notEqual(oldEntity.getInvokebpforisa(), newEntity.getInvokebpforisa())) {
            activities.add(activityBuilder(oldEntity.getInvokebpforisa(), newEntity.getInvokebpforisa(), "Invoke BP For ISA"));
        }
        if (notEqual(oldEntity.getBusinessprocess(), newEntity.getBusinessprocess())) {
            activities.add(activityBuilder(oldEntity.getBusinessprocess(), newEntity.getBusinessprocess(), "Business Process"));
        }
        if (notEqual(oldEntity.getPercontnumcheck(), newEntity.getPercontnumcheck())) {
            activities.add(activityBuilder(oldEntity.getPercontnumcheck(), newEntity.getPercontnumcheck(), "Perform Control Number Sequence Check"));
        }
        if (notEqual(oldEntity.getPerdupnumcheck(), newEntity.getPerdupnumcheck())) {
            activities.add(activityBuilder(oldEntity.getPerdupnumcheck(), newEntity.getPerdupnumcheck(), "Perform Duplicate Control Number Check"));
        }
        if (notEqual(oldEntity.getIsaacceptlookalias(), newEntity.getIsaacceptlookalias())) {
            activities.add(activityBuilder(oldEntity.getIsaacceptlookalias(), newEntity.getIsaacceptlookalias(), "ISA Accepter Lookup Alias"));
        }
        if (notEqual(oldEntity.getGssenderid(), newEntity.getGssenderid())) {
            activities.add(activityBuilder(oldEntity.getGssenderid(), newEntity.getGssenderid(), "GS Sender ID(GS02)"));
        }
        if (notEqual(oldEntity.getGsreceiverid(), newEntity.getGsreceiverid())) {
            activities.add(activityBuilder(oldEntity.getGsreceiverid(), newEntity.getGsreceiverid(), "GS Receiver ID(GS03)"));
        }
        if (notEqual(oldEntity.getFuncationalidcode(), newEntity.getFuncationalidcode())) {
            activities.add(activityBuilder(oldEntity.getFuncationalidcode(), newEntity.getFuncationalidcode(), "Functional ID Code(GS01)"));
        }
        if (notEqual(oldEntity.getRespagencycode(), newEntity.getRespagencycode())) {
            activities.add(activityBuilder(oldEntity.getRespagencycode(), newEntity.getRespagencycode(), "Responsible Agency Code(GS07)"));
        }
        if (notEqual(oldEntity.getGroupversion(), newEntity.getGroupversion())) {
            activities.add(activityBuilder(oldEntity.getGroupversion(), newEntity.getGroupversion(), "Group Version(GS08)"));
        }
        if (notEqual(oldEntity.getComplcheck(), newEntity.getComplcheck())) {
            activities.add(activityBuilder(oldEntity.getComplcheck(), newEntity.getComplcheck(), "Compliance Check"));
        }
        if (notEqual(oldEntity.getComplcheckmap(), newEntity.getComplcheckmap())) {
            activities.add(activityBuilder(oldEntity.getComplcheckmap(), newEntity.getComplcheckmap(), "Compliance Check Map"));
        }
        if (notEqual(oldEntity.getRetainenv(), newEntity.getRetainenv())) {
            activities.add(activityBuilder(oldEntity.getRetainenv(), newEntity.getRetainenv(), "Retain Enclosing Envelope"));
        }
        if (notEqual(oldEntity.getGeninack(), newEntity.getGeninack())) {
            activities.add(activityBuilder(oldEntity.getGeninack(), newEntity.getGeninack(), "Generate an Acknowledgement"));
        }
        if (notEqual(oldEntity.getAckdetlevel(), newEntity.getAckdetlevel())) {
            activities.add(activityBuilder(oldEntity.getAckdetlevel(), newEntity.getAckdetlevel(), "Acknowledgement Detail Level"));
        }
        if (notEqual(oldEntity.getStsenderid(), newEntity.getStsenderid())) {
            activities.add(activityBuilder(oldEntity.getStsenderid(), newEntity.getStsenderid(), " ST Sender ID"));
        }
        if (notEqual(oldEntity.getStreceiverid(), newEntity.getStreceiverid())) {
            activities.add(activityBuilder(oldEntity.getStreceiverid(), newEntity.getStreceiverid(), "ST Receiver ID"));
        }
        if (notEqual(oldEntity.getTrnsetidcode(), newEntity.getTrnsetidcode())) {
            activities.add(activityBuilder(oldEntity.getTrnsetidcode(), newEntity.getTrnsetidcode(), "Transaction Set Id Code"));
        }
        if (notEqual(oldEntity.getAcceptlookalias(), newEntity.getAcceptlookalias())) {
            activities.add(activityBuilder(oldEntity.getAcceptlookalias(), newEntity.getAcceptlookalias(), "ST Accepter Lookup Alias"));
        }
        if (notEqual(oldEntity.getSegterm(), newEntity.getSegterm())) {
            activities.add(activityBuilder(oldEntity.getSegterm(), newEntity.getSegterm(), "Segment Terminator"));
        }
        if (notEqual(oldEntity.getEleterm(), newEntity.getEleterm())) {
            activities.add(activityBuilder(oldEntity.getEleterm(), newEntity.getEleterm(), "Data Element Terminator"));
        }
        if (notEqual(oldEntity.getReleasechar(), newEntity.getReleasechar())) {
            activities.add(activityBuilder(oldEntity.getReleasechar(), newEntity.getReleasechar(), "Release Character"));
        }
        if (notEqual(oldEntity.getUsecorrelation(), newEntity.getUsecorrelation())) {
            activities.add(activityBuilder(oldEntity.getUsecorrelation(), newEntity.getUsecorrelation(), "Use Correlation Overrides"));
        }
        if (notEqual(oldEntity.getExtractionmailbox(), newEntity.getExtractionmailbox())) {
            activities.add(activityBuilder(oldEntity.getExtractionmailbox(), newEntity.getExtractionmailbox(), "Extraction RemoteMailboxModel"));
        }
        if (notEqual(oldEntity.getExtractionmailboxbp(), newEntity.getExtractionmailboxbp())) {
            activities.add(activityBuilder(oldEntity.getExtractionmailboxbp(), newEntity.getExtractionmailboxbp(), "Extraction RemoteMailboxModel BP"));
        }
        if (notEqual(oldEntity.getIntackreq(), newEntity.getIntackreq())) {
            activities.add(activityBuilder(oldEntity.getIntackreq(), newEntity.getIntackreq(), "Interchange Acknowledgement Requested"));
        }
        if (notEqual(oldEntity.getAckoverduehr(), newEntity.getAckoverduehr())) {
            activities.add(activityBuilder(oldEntity.getAckoverduehr(), newEntity.getAckoverduehr(), "Acknowledgement Overdue Time (Hours)"));
        }
        if (notEqual(oldEntity.getSubeleterm(), newEntity.getSubeleterm())) {
            activities.add(activityBuilder(oldEntity.getSubeleterm(), newEntity.getSubeleterm(), "Component Element Terminator"));
        }
        if (notEqual(oldEntity.getDataextraction(), newEntity.getDataextraction())) {
            activities.add(activityBuilder(oldEntity.getDataextraction(), newEntity.getDataextraction(), "Data Extraction"));
        }
        if (notEqual(oldEntity.getExpectack(), newEntity.getExpectack())) {
            activities.add(activityBuilder(oldEntity.getExpectack(), newEntity.getExpectack(), "Expect Acknowledgement"));
        }
        if (notEqual(oldEntity.getAckoverduemin(), newEntity.getAckoverduemin())) {
            activities.add(activityBuilder(oldEntity.getAckoverduemin(), newEntity.getAckoverduemin(), "Acknowledgement Overdue Time (Minutes)"));
        }

        saveEnvelopeActivity(oldEntity.getPkId(), String.join(",", activities));
    }

    public void updatePartnerActivity(PartnerEntity oldPartner, PartnerEntity newPartner, Map<String, String> oldProtocol, Map<String, String> newProtocol, boolean isUpdate) {

        List<String> activities = new ArrayList<>();

        if (notEqual(oldPartner.getTpName(), newPartner.getTpName())) {
            activities.add(activityBuilder(oldPartner.getTpName(), newPartner.getTpName(), "Trading Partner Name"));
        }
        if (notEqual(oldPartner.getTpId(), newPartner.getTpId())) {
            activities.add(activityBuilder(oldPartner.getTpId(), newPartner.getTpId(), "Trading Partner Id"));
        }
        if (notEqual(oldPartner.getAddressLine1(), newPartner.getAddressLine1())) {
            activities.add(activityBuilder(oldPartner.getAddressLine1(), newPartner.getAddressLine1(), "Address Line1"));
        }
        if (notEqual(oldPartner.getAddressLine2(), newPartner.getAddressLine2())) {
            activities.add(activityBuilder(oldPartner.getAddressLine2(), newPartner.getAddressLine2(), "Address Line2"));
        }
        if (notEqual(oldPartner.getEmail(), newPartner.getEmail())) {
            activities.add(activityBuilder(oldPartner.getEmail(), newPartner.getEmail(), "Email Id"));
        }
        if (notEqual(oldPartner.getPhone(), newPartner.getPhone())) {
            activities.add(activityBuilder(oldPartner.getPhone(), newPartner.getPhone(), "Phone No"));
        }
        if (notEqual(oldPartner.getStatus(), newPartner.getStatus())) {
            activities.add(activityBuilder(convertStringToReqString(oldPartner.getStatus()), convertStringToReqString(newPartner.getStatus()), "Status"));
        }
        if (notEqual(oldPartner.getTpProtocol(), newPartner.getTpProtocol())) {
            activities.add(activityBuilder(oldPartner.getTpProtocol(), newPartner.getTpProtocol(), "Protocol"));
        }

        if (!isUpdate) {
            savePartnerActivity(oldPartner.getPkId(), String.join(",", activities));
            return;
        }

        activities.addAll(buildActivity(newProtocol, oldProtocol, newPartner.getTpProtocol()));
        savePartnerActivity(oldPartner.getPkId(), String.join(",", activities));
    }

    public void updateApplicationActivity(ApplicationEntity oldApplication, ApplicationEntity newApplication, Map<String, String> oldProtocol, Map<String, String> newProtocol, boolean isUpdate) {

        List<String> activities = new ArrayList<>();

        if (notEqual(oldApplication.getApplicationName(), newApplication.getApplicationName())) {
            activities.add(activityBuilder(oldApplication.getApplicationName(), newApplication.getApplicationName(), "Application Name"));
        }
        if (notEqual(oldApplication.getApplicationId(), newApplication.getApplicationId())) {
            activities.add(activityBuilder(oldApplication.getApplicationId(), newApplication.getApplicationId(), "Application Id"));
        }
        if (notEqual(oldApplication.getEmailId(), newApplication.getEmailId())) {
            activities.add(activityBuilder(oldApplication.getEmailId(), newApplication.getEmailId(), "Email Id"));
        }
        if (notEqual(oldApplication.getPhone(), newApplication.getPhone())) {
            activities.add(activityBuilder(oldApplication.getPhone(), newApplication.getPhone(), "Phone No"));
        }
        if (notEqual(oldApplication.getAppIsActive(), newApplication.getAppIsActive())) {
            activities.add(activityBuilder(convertStringToReqString(oldApplication.getAppIsActive()), convertStringToReqString(newApplication.getAppIsActive()), "Status"));
        }
        if (notEqual(oldApplication.getAppIntegrationProtocol(), newApplication.getAppIntegrationProtocol())) {
            activities.add(activityBuilder(oldApplication.getAppIntegrationProtocol(), newApplication.getAppIntegrationProtocol(), "Protocol"));
        }

        if (!isUpdate) {
            saveApplicationActivity(oldApplication.getPkId(), String.join(",", activities));
            return;
        }
        activities.addAll(buildActivity(newProtocol, oldProtocol, newApplication.getAppIntegrationProtocol()));
        saveApplicationActivity(oldApplication.getPkId(), String.join(",", activities));
    }

    private List<String> buildActivity(Map<String, String> incoming, Map<String, String> outgoing, String protocol) {
        List<String> ignorableFields = Arrays.asList("pkId", "subscriberType", "subscriberId", "protocolType", "isActive", "profileId",
                "createdBy", "lastUpdatedBy", "lastUpdatedDt", "identityName", "profileName", "remotePassword", "pwdLastChangeDone");
        List<String> activities = new ArrayList<>();
        incoming.forEach((field, fieldValue) -> {
            if (notEqual(outgoing.get(field), fieldValue) && !ignorableFields.contains(field)) {
                if (protocol.equals("ExistingConnection") && field.equals("ecProtocolRef")) {
                    activities.add(activityBuilder(partnerService.get(outgoing.get(field)).getTpName(), partnerService.get(fieldValue).getTpName(), DisplayFields.valueOf(field.toUpperCase()).getDisplayField()));
                } else if (field.contains("poolingInterval")) {
                    activities.add(activityBuilder(convertPoolingIntervalToDisplayValue(outgoing.get(field)),
                            convertPoolingIntervalToDisplayValue(fieldValue), DisplayFields.valueOf(field.toUpperCase()).getDisplayField()));
                } else if (protocol.equals("Webservice") && field.equals("certificate")) {
                    activities.add(activityBuilder(
                            caCertInfoService.findByIdNotThrow(outgoing.get(field)).getName(),
                            caCertInfoService.findByIdNotThrow(fieldValue).getName(),
                            DisplayFields.valueOf(field.toUpperCase()).getDisplayField())
                    );
                } else if (field.equals("password")) {
                    activities.add("Password changed");
                } else {
                    activities.add(activityBuilder(outgoing.get(field), fieldValue, DisplayFields.valueOf(field.toUpperCase()).getDisplayField()));
                }
            }
        });
        return activities;
    }

    public void savePartnerActivity(String parentPrimaryKey, String activity) {
        if (isNotNull(activity)) {
            partnerActivityHistoryRepository.save(new PartnerActivityHistoryEntity()
                    .setPkId(KeyGeneratorUtil.getPrimaryKey.apply(TP_ACT_HIST_PKEY_PRE_APPEND, TP_ACT_HIST_PKEY_RANDOM_COUNT))
                    .setActivity(activity)
                    .setTpRefId(parentPrimaryKey));
        }
    }

    public void saveApplicationActivity(String parentPrimaryKey, String activity) {

        if (isNotNull(activity)) {
            appActivityHistoryRepository.save(new AppActivityHistoryEntity()
                    .setPkId(KeyGeneratorUtil.getPrimaryKey.apply(APP_ACT_HIST_PKEY_PRE_APPEND, APP_ACT_HIST_PKEY_RANDOM_COUNT))
                    .setActivity(activity)
                    .setAppRefId(parentPrimaryKey));
        }
    }

    public void saveEnvelopeActivity(String parentPrimaryKey, String activity) {
        ediActivityHistoryRepository.save(new EdiPropertiesActivityHistoryEntity()
                .setPkId(KeyGeneratorUtil.getPrimaryKey.apply(PCMConstants.EDI_ACT_HIST_PKEY_PRE_APPEND, PCMConstants.EDI_ACT_HIST_PKEY_RANDOM_COUNT))
                .setActivity(activity)
                .setTpRefId(parentPrimaryKey));
    }

    private String activityBuilder(String oldValue, String newValue, String fieldName) {
        return fieldName + " changed from " + oldValue + " to " + newValue;
    }

    public Page<PartnerActivityHistoryEntity> getTradingPartnerHistory(String pkId, Pageable pageable) {
        return partnerActivityHistoryRepository.findAllByTpRefId(pkId, pageable);
    }

    public Page<AppActivityHistoryEntity> getApplicationHistory(String pkId, Pageable pageable) {
        return appActivityHistoryRepository.findAllByAppRefId(pkId, pageable);
    }

    public Page<EdiPropertiesActivityHistoryEntity> getEnvelopeHistory(String ediRefId, Pageable pageable) {
        return ediActivityHistoryRepository.findAllByEdiRefId(ediRefId, pageable);
    }

    public Page<WorkFlowActivityHistoryEntity> getWorkFlowHistory(String processRefId, Pageable pageable) {
        Page<WorkFlowActivityHistoryEntity> workFlowActivityHistoryEntities = workFlowActivityHistoryRepository.findAllByProcessRefId(processRefId, pageable);
        return new PageImpl<>(new ArrayList<>(workFlowActivityHistoryEntities.getContent()), pageable, workFlowActivityHistoryEntities.getTotalElements());
    }

    public void saveWorkflowActivity(String profileId, String applicationId, String activity) {
        workFlowActivityHistoryRepository.save(new WorkFlowActivityHistoryEntity()
                .setPkId(KeyGeneratorUtil.getPrimaryKey.apply(PCMConstants.B2B_ACT_HIST_PKEY_PRE_APPEND, B2B_ACT_HIST_PKEY_RANDOM_COUNT))
                .setProcessRefId(profileId + "_" + applicationId)
                .setActivity(activity)
                .setActivityDt(new Timestamp(System.currentTimeMillis()))
        );
    }

    public void saveApiWorkflowActivity(String profileId, String activity) {
        workFlowActivityHistoryRepository.save(new WorkFlowActivityHistoryEntity()
                .setPkId(KeyGeneratorUtil.getPrimaryKey.apply(PCMConstants.B2B_ACT_HIST_PKEY_PRE_APPEND, B2B_ACT_HIST_PKEY_RANDOM_COUNT))
                .setProcessRefId(profileId)
                .setActivity(activity)
                .setActivityDt(new Timestamp(System.currentTimeMillis()))
        );
    }

}
