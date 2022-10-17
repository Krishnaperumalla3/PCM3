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

package com.pe.pcm.envelope;

import com.pe.pcm.activity.ActivityHistoryService;
import com.pe.pcm.envelope.entity.EdiPropertiesActivityHistoryEntity;
import com.pe.pcm.envelope.entity.EdiPropertiesEntity;
import com.pe.pcm.utils.PCMConstants;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;
import static com.pe.pcm.miscellaneous.CommonQueryPredicate.getPredicate;
import static com.pe.pcm.utils.DataProvider.generateEnvelopeId;
import static com.pe.pcm.utils.DataProvider.generateEnvelopeName;
import static com.pe.pcm.utils.KeyGeneratorUtil.getPrimaryKey;
import static java.lang.Boolean.TRUE;

/**
 * @author Shameer.
 */

@Service
public class EnvelopeService {

    private final EdiPropertiesRepository edipropertiesrepository;
    private final ActivityHistoryService activityHistoryService;

    @Autowired
    public EnvelopeService(EdiPropertiesRepository edipropertiesrepository,
                           ActivityHistoryService activityHistoryService) {
        this.edipropertiesrepository = edipropertiesrepository;
        this.activityHistoryService = activityHistoryService;
    }

    @Transactional
    public void create(EnvelopeModel envelopeModel) {
        String parentPrimaryKey = getPrimaryKey.apply(PCMConstants.TP_ENV_PKEY_PRE_APPEND, PCMConstants.TP_ENV_PKEY_RANDOM_COUNT);
        save(envelopeModel.setPkId(parentPrimaryKey));
        activityHistoryService.saveEnvelopeActivity(parentPrimaryKey, "Envelope Created");
    }

    @Transactional
    public void update(EnvelopeModel envelopeModel) {
        EdiPropertiesEntity oldEdiPropertiesEntity = SerializationUtils.clone(getEnvelop(envelopeModel.getPkId()));
        EdiPropertiesEntity newEdiPropertiesEntity = save(envelopeModel);
        activityHistoryService.updateEnvelopeActivity(oldEdiPropertiesEntity, newEdiPropertiesEntity);
    }

    @Transactional
    public void delete(String pkId) {
        edipropertiesrepository.findById(pkId).ifPresent(ediPropertiesEntity -> edipropertiesrepository.delete(ediPropertiesEntity));
    }

    public EnvelopeModel get(String pkId) {
        return apply(getEnvelop(pkId));
    }

    private EdiPropertiesEntity getEnvelop(String pkId) {
        return edipropertiesrepository.findById(pkId).orElseThrow(() -> notFound("Envelope"));
    }

    public Page<EdiPropertiesEntity> search(EnvelopeModel ediModel, Pageable pageable) {
        Page<EdiPropertiesEntity> ediPropertiesEntities = edipropertiesrepository.findAll((Specification<EdiPropertiesEntity>) (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            getPredicate(root, cb, predicates, ediModel.getEdiProperties().getPartnerName(), "partnername", TRUE);
            getPredicate(root, cb, predicates, ediModel.getEdiProperties().getDirection(), "direction", TRUE);
            getPredicate(root, cb, predicates, ediModel.getIsaSegment().getInterVersion(), "interversion", TRUE);
            getPredicate(root, cb, predicates, ediModel.getStSegment().getAcceptLookAlias(), "acceptlookalias", TRUE);
            getPredicate(root, cb, predicates, ediModel.getIsaSegment().getIsaSenderId(), "isasenderid", TRUE);
            getPredicate(root, cb, predicates, ediModel.getIsaSegment().getIsaReceiverId(), "isareceiverid", TRUE);
            getPredicate(root, cb, predicates, ediModel.getGsSegment().getGsSenderId(), "gssenderid", TRUE);
            getPredicate(root, cb, predicates, ediModel.getGsSegment().getGsReceiverId(), "gsreceiverid", TRUE);
            getPredicate(root, cb, predicates, ediModel.getStSegment().getStSenderId(), "stsenderid", TRUE);
            getPredicate(root, cb, predicates, ediModel.getStSegment().getStReceiverId(), "streceiverid", TRUE);
            return cb.and(predicates.toArray(new Predicate[0]));
        }, pageable);
        List<EdiPropertiesEntity> searchResults = new LinkedList<>();
        for (EdiPropertiesEntity index : ediPropertiesEntities) {
            searchResults.add(index);
        }
        return new PageImpl<>(searchResults, pageable, ediPropertiesEntities.getTotalElements());
    }


    private static EnvelopeModel apply(EdiPropertiesEntity ediPropertiesEntity) {
        EnvelopeModel envelopeModel = new EnvelopeModel();
        envelopeModel.setPkId(ediPropertiesEntity.getPkId());
        envelopeModel.setEdiProperties(new EdiProperties()
                .setPartnerPkId(ediPropertiesEntity.getPartnerid())
                .setPartnerName(ediPropertiesEntity.getPartnername())
                .setDirection(ediPropertiesEntity.getDirection())
                .setValidateInput(ediPropertiesEntity.getValidateinput())
                .setValidateOutput(ediPropertiesEntity.getValidateoutput())
                .setUseIndicator(ediPropertiesEntity.getUseindicator()));
        envelopeModel.setIsaSegment(new IsaSegment().setIsaReceiverIdQal(ediPropertiesEntity.getIsareceiveridqal())
                .setIsaSenderId(ediPropertiesEntity.getIsasenderid())
                .setIsaReceiverIdQal(ediPropertiesEntity.getIsareceiveridqal())
                .setIsaReceiverId(ediPropertiesEntity.getIsareceiverid())
                .setInterVersion(ediPropertiesEntity.getInterversion())
                .setIsaAcceptLookAlias(ediPropertiesEntity.getIsaacceptlookalias())
                .setGlobalContNo(ediPropertiesEntity.getGlobalcontno())
                .setPerContNumCheck(ediPropertiesEntity.getPercontnumcheck())
                .setBusinessProcess(ediPropertiesEntity.getBusinessprocess())
                .setInvokeBPForIsa(ediPropertiesEntity.getInvokebpforisa())
                .setPerDupNumCheck(ediPropertiesEntity.getPerdupnumcheck()));
        envelopeModel.setGsSegment(new GsSegment().setGsSenderId(ediPropertiesEntity.getGssenderid())
                .setGsReceiverId(ediPropertiesEntity.getGsreceiverid())
                .setFunctionalIdCode(ediPropertiesEntity.getFuncationalidcode())
                .setRespAgencyCode(ediPropertiesEntity.getRespagencycode())
                .setGroupVersion(ediPropertiesEntity.getGroupversion()));
        envelopeModel.setInBound(new InBound().setGenInboundAck(ediPropertiesEntity.getGeninack())
                .setAckDetailLevel(ediPropertiesEntity.getAckdetlevel())
                .setRetainEnv(ediPropertiesEntity.getRetainenv())
                .setComplianceCheck(ediPropertiesEntity.getComplcheck())
                .setComplianceCheckMap(ediPropertiesEntity.getComplcheckmap()));
        envelopeModel.setOutBound(new OutBound().setSegTerm(ediPropertiesEntity.getSegterm())
                .setSubEleTerm(ediPropertiesEntity.getSubeleterm())
                .setEleTerm(ediPropertiesEntity.getEleterm())
                .setReleaseCharacter(ediPropertiesEntity.getReleasechar())
                .setDataExtraction(ediPropertiesEntity.getDataextraction())
                .setExtractionMailBox(ediPropertiesEntity.getExtractionmailbox())
                .setExtractionMailBoxBp(ediPropertiesEntity.getExtractionmailboxbp())
                .setExpectAck(ediPropertiesEntity.getExpectack())
                .setIntAckReq(ediPropertiesEntity.getIntackreq())
                .setUseCorrelation(ediPropertiesEntity.getUsecorrelation())
                .setAckOverDueHr(ediPropertiesEntity.getAckoverduehr())
                .setAckOverDueMin(ediPropertiesEntity.getAckoverduemin()));
        envelopeModel.setStSegment(new StSegment().setStSenderId(ediPropertiesEntity.getStsenderid())
                .setStReceiverId(ediPropertiesEntity.getGsreceiverid())
                .setTrnSetIdCode(ediPropertiesEntity.getTrnsetidcode())
                .setAcceptLookAlias(ediPropertiesEntity.getAcceptlookalias()));
        return envelopeModel;

    }

    private Function<EnvelopeModel, EdiPropertiesEntity> serialize = envelopeModel ->
            new EdiPropertiesEntity()
                    .setPkId(envelopeModel.getPkId())
                    .setPartnerid(envelopeModel.getEdiProperties().getPartnerPkId())
                    .setPartnername(envelopeModel.getEdiProperties().getPartnerName())
                    .setDirection(envelopeModel.getEdiProperties().getDirection())
                    .setValidateinput(envelopeModel.getEdiProperties().getValidateInput())
                    .setValidateoutput(envelopeModel.getEdiProperties().getValidateOutput())
                    .setIsasenderidqal(envelopeModel.getIsaSegment().getIsaSenderIdQal())
                    .setIsasenderid(envelopeModel.getIsaSegment().getIsaSenderId())
                    .setIsareceiveridqal(envelopeModel.getIsaSegment().getIsaReceiverIdQal())
                    .setIsareceiverid(envelopeModel.getIsaSegment().getIsaReceiverId())
                    .setInterversion(envelopeModel.getIsaSegment().getInterVersion())
                    .setUseindicator(envelopeModel.getEdiProperties().getUseIndicator())
                    .setSegterm(envelopeModel.getOutBound().getSegTerm())
                    .setSubeleterm(envelopeModel.getOutBound().getSubEleTerm())
                    .setEleterm(envelopeModel.getOutBound().getEleTerm())
                    .setReleasechar(envelopeModel.getOutBound().getReleaseCharacter())
                    .setRetainenv(envelopeModel.getInBound().getRetainEnv())
                    .setGssenderid(envelopeModel.getGsSegment().getGsSenderId())
                    .setGsreceiverid(envelopeModel.getGsSegment().getGsReceiverId())
                    .setFuncationalidcode(envelopeModel.getGsSegment().getFunctionalIdCode())
                    .setRespagencycode(envelopeModel.getGsSegment().getRespAgencyCode())
                    .setGroupversion(envelopeModel.getGsSegment().getGroupVersion())
                    .setStsenderid(envelopeModel.getStSegment().getStSenderId())
                    .setStreceiverid(envelopeModel.getStSegment().getStReceiverId())
                    .setGeninack(envelopeModel.getInBound().getGenInboundAck())
                    .setAckdetlevel(envelopeModel.getInBound().getAckDetailLevel())
                    .setTrnsetidcode(envelopeModel.getStSegment().getTrnSetIdCode())
                    .setDataextraction(envelopeModel.getOutBound().getDataExtraction())
                    .setExtractionmailbox(envelopeModel.getOutBound().getExtractionMailBox())
                    .setComplcheck(envelopeModel.getInBound().getComplianceCheck())
                    .setComplcheckmap(envelopeModel.getInBound().getComplianceCheckMap())
                    .setBusinessprocess(envelopeModel.getIsaSegment().getBusinessProcess())
                    .setInvokebpforisa(envelopeModel.getIsaSegment().getInvokeBPForIsa())
                    .setExtractionmailboxbp(envelopeModel.getOutBound().getExtractionMailBoxBp())
                    .setExpectack(envelopeModel.getOutBound().getExpectAck())
                    .setIntackreq(envelopeModel.getOutBound().getIntAckReq())
                    .setUsecorrelation(envelopeModel.getOutBound().getUseCorrelation())
                    .setAcceptlookalias(envelopeModel.getStSegment().getAcceptLookAlias())
                    .setIsaacceptlookalias(envelopeModel.getIsaSegment().getIsaAcceptLookAlias())
                    .setGlobalcontno(envelopeModel.getIsaSegment().getGlobalContNo())
                    .setPercontnumcheck(envelopeModel.getIsaSegment().getPerContNumCheck())
                    .setPerdupnumcheck(envelopeModel.getIsaSegment().getPerDupNumCheck())
                    .setAckoverduehr(envelopeModel.getOutBound().getAckOverDueHr())
                    .setAckoverduemin(envelopeModel.getOutBound().getAckOverDueMin())
                    .setIsaenvelopeid(generateEnvelopeId(envelopeModel, "ISA"))
                    .setIsaenvelopename(generateEnvelopeName(envelopeModel, "ISA"))
                    .setGsenvelopeid(generateEnvelopeId(envelopeModel, "GS"))
                    .setGsenvelopename(generateEnvelopeName(envelopeModel, "GS"))
                    .setStenvelopeid(generateEnvelopeId(envelopeModel, "ST"))
                    .setStenvelopename(generateEnvelopeName(envelopeModel, "ST"))
                    .setIsacontstd("")
                    .setIsaauthinfo("1234567890")
                    .setIsaauthinfoqual("00")
                    .setIsaauthsecinfo("1234567890")
                    .setIsaauthsecqual("00")
                    .setLimitintersize("NO")
                    .setBatchtransaction("")
                    .setHippacompliance("NO")
                    .setHippavallevel("")
                    .setErrorbp("")
                    .setAcceptnoninter("NO")
                    .setSpoutboundencode("")
                    .setTa1alaform("")
                    .setAla999form("")
                    .setAccnongroup("")
                    .setAckdetails("")
                    .setEdipostmode("")
                    .setErrorbpmode("Specify")
                    .setInvokebpmode("SpecifyBP")
                    .setEncodedoc("NO")
                    .setBpinvokesetinpd("Invoke")
                    .setStreamseg("YES")
                    .setLastupdatedby("admin");

    public EdiPropertiesEntity save(EnvelopeModel envelopeModel) {
        return edipropertiesrepository.save(serialize.apply(envelopeModel));
    }

    public Page<EdiPropertiesActivityHistoryEntity> getHistory(String ediRefId, Pageable pageable) {
        return activityHistoryService.getEnvelopeHistory(ediRefId, pageable);
    }

}
