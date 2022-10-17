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

import com.pe.pcm.protocol.as2.si.As2TradepartInfoRepository;
import com.pe.pcm.protocol.as2.si.SciContractExtnRepository;
import com.pe.pcm.protocol.as2.si.SciContractRepository;
import com.pe.pcm.protocol.as2.si.entity.As2TradePartInfo;
import com.pe.pcm.protocol.as2.si.entity.SciContractEntity;
import com.pe.pcm.protocol.as2.si.entity.SciContractExtn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Kiran Reddy.
 */
@Service
public class SciContractService {

    private final SciContractRepository sciContractRepository;
    private final SciContractExtnRepository sciContractExtnRepository;
    private final As2TradepartInfoRepository as2TradepartInfoRepository;

    @Autowired
    public SciContractService(SciContractRepository sciContractRepository,
                              SciContractExtnRepository sciContractExtnRepository,
                              As2TradepartInfoRepository as2TradepartInfoRepository) {
        this.sciContractRepository = sciContractRepository;
        this.sciContractExtnRepository = sciContractExtnRepository;
        this.as2TradepartInfoRepository = as2TradepartInfoRepository;
    }

    public void save(String sciCon, String objectId, String workflowName, String objectName, String partnerProfile, String orgProfile, String contractKey) {
        SciContractEntity sciContractEntity = new SciContractEntity();
        sciContractEntity.setObjectId(sciCon + objectId);
        sciContractEntity.setWorkflowName(workflowName);
        sciContractEntity.setObjectName(objectName);
        sciContractEntity.setProdProfileId(partnerProfile);
        sciContractEntity.setConsumeProfileId(orgProfile);
        sciContractEntity.setContractKey(sciCon + contractKey);
        sciContractRepository.save(sciContractEntity);
    }

    void saveExtn(String sciConExtn, String sciCon, String objectId, String objectName, String value, String contractId) {
        SciContractExtn sciContractExtn = new SciContractExtn();
        sciContractExtn.setObjectId(sciConExtn + objectId);
        sciContractExtn.setObjectName(objectName);
        sciContractExtn.setValue(value);
        sciContractExtn.setContractId(sciCon + contractId);
        sciContractExtnRepository.save(sciContractExtn);
    }

    void saveTradePartInfo(String orgProfile, String organizationName, String partnerProfile, String sciCon, String partnerName, String as2Tpart) {
        As2TradePartInfo as2TradePartInfo = new As2TradePartInfo();
        as2TradePartInfo.setOrgProfileId(orgProfile);
        as2TradePartInfo.setOrgAs2Id(organizationName);
        as2TradePartInfo.setPartnerProfileId(partnerProfile);
        as2TradePartInfo.setInContractId(sciCon + ":-7070");
        as2TradePartInfo.setOutContractId(sciCon + ":-7076");
        as2TradePartInfo.setPartAs2Id(partnerName);
        as2TradePartInfo.setRuleId(as2Tpart + ":-701e");
        as2TradePartInfo.setParentMbx("/AS2/" + organizationName + "/" + partnerName);
        as2TradepartInfoRepository.save(as2TradePartInfo);
    }

    Optional<As2TradePartInfo> getAs2Relation(String orgProfile, String partnerProfile) {
        return as2TradepartInfoRepository.findFirstByOrgAs2IdAndPartAs2Id(orgProfile.trim(), partnerProfile.trim());
    }

}
