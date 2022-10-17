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

package com.pe.pcm.protocol.as2.si.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

import static com.pe.pcm.utils.PCMConstants.STRING_FALSE;
import static com.pe.pcm.utils.PCMConstants.STRING_TRUE;

@Entity
@Table(name = "AS2_TRADEPART_INFO")
public class As2TradePartInfo implements Serializable {

    @Id
    private String orgProfileId;

    @Column(name = "ORG_AS2_ID")
    private String orgAs2Id;

    private String partnerProfileId;

    private String inContractId;

    private String outContractId;

    @Column(name = "PART_AS2_ID")
    private String partAs2Id;

    private String receiptTimeout = "300";

    private String retryInterval = "60";

    private String ruleId;

    private String maxRetries = "1";

    private String notiFinFl = STRING_TRUE;

    private String notiInFl = STRING_FALSE;

    private String waitForMdn = STRING_FALSE;

    private String parentMbx;

    private String collectionType = "2";

    public String getOrgProfileId() {
        return orgProfileId;
    }

    public As2TradePartInfo setOrgProfileId(String orgProfileId) {
        this.orgProfileId = orgProfileId;
        return this;
    }

    public String getOrgAs2Id() {
        return orgAs2Id;
    }

    public As2TradePartInfo setOrgAs2Id(String orgAs2Id) {
        this.orgAs2Id = orgAs2Id;
        return this;
    }

    public String getPartnerProfileId() {
        return partnerProfileId;
    }

    public As2TradePartInfo setPartnerProfileId(String partnerProfileId) {
        this.partnerProfileId = partnerProfileId;
        return this;
    }

    public String getInContractId() {
        return inContractId;
    }

    public As2TradePartInfo setInContractId(String inContractId) {
        this.inContractId = inContractId;
        return this;
    }

    public String getOutContractId() {
        return outContractId;
    }

    public As2TradePartInfo setOutContractId(String outContractId) {
        this.outContractId = outContractId;
        return this;
    }

    public String getPartAs2Id() {
        return partAs2Id;
    }

    public As2TradePartInfo setPartAs2Id(String partAs2Id) {
        this.partAs2Id = partAs2Id;
        return this;
    }

    public String getReceiptTimeout() {
        return receiptTimeout;
    }

    public As2TradePartInfo setReceiptTimeout(String receiptTimeout) {
        this.receiptTimeout = receiptTimeout;
        return this;
    }

    public String getRetryInterval() {
        return retryInterval;
    }

    public As2TradePartInfo setRetryInterval(String retryInterval) {
        this.retryInterval = retryInterval;
        return this;
    }

    public String getRuleId() {
        return ruleId;
    }

    public As2TradePartInfo setRuleId(String ruleId) {
        this.ruleId = ruleId;
        return this;
    }

    public String getMaxRetries() {
        return maxRetries;
    }

    public As2TradePartInfo setMaxRetries(String maxRetries) {
        this.maxRetries = maxRetries;
        return this;
    }

    public String getNotiFinFl() {
        return notiFinFl;
    }

    public As2TradePartInfo setNotiFinFl(String notiFinFl) {
        this.notiFinFl = notiFinFl;
        return this;
    }

    public String getNotiInFl() {
        return notiInFl;
    }

    public As2TradePartInfo setNotiInFl(String notiInFl) {
        this.notiInFl = notiInFl;
        return this;
    }

    public String getWaitForMdn() {
        return waitForMdn;
    }

    public As2TradePartInfo setWaitForMdn(String waitForMdn) {
        this.waitForMdn = waitForMdn;
        return this;
    }

    public String getParentMbx() {
        return parentMbx;
    }

    public As2TradePartInfo setParentMbx(String parentMbx) {
        this.parentMbx = parentMbx;
        return this;
    }

    public String getCollectionType() {
        return collectionType;
    }

    public As2TradePartInfo setCollectionType(String collectionType) {
        this.collectionType = collectionType;
        return this;
    }
}
