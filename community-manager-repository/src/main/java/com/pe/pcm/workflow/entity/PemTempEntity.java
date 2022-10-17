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

package com.pe.pcm.workflow.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "PETPE_PEM_TEMP")
public class PemTempEntity implements Serializable {
    @Id
    private String pkId;
    private String processDocPkId;
    private String seqId;
    private String partnerProfile;
    private String applicationProfile;
    private String seqType;
    private String fileName;
    private String flowType;
    private String docType;
    private String senderId;
    private String receiverId;
    private String trans;

    public String getPkId() {
        return pkId;
    }

    public PemTempEntity setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getProcessDocPkId() {
        return processDocPkId;
    }

    public PemTempEntity setProcessDocPkId(String processDocPkId) {
        this.processDocPkId = processDocPkId;
        return this;
    }

    public String getSeqId() {
        return seqId;
    }

    public PemTempEntity setSeqId(String seqId) {
        this.seqId = seqId;
        return this;
    }

    public String getPartnerProfile() {
        return partnerProfile;
    }

    public PemTempEntity setPartnerProfile(String partnerProfile) {
        this.partnerProfile = partnerProfile;
        return this;
    }

    public String getApplicationProfile() {
        return applicationProfile;
    }

    public PemTempEntity setApplicationProfile(String applicationProfile) {
        this.applicationProfile = applicationProfile;
        return this;
    }

    public String getSeqType() {
        return seqType;
    }

    public PemTempEntity setSeqType(String seqType) {
        this.seqType = seqType;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public PemTempEntity setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getFlowType() {
        return flowType;
    }

    public PemTempEntity setFlowType(String flowType) {
        this.flowType = flowType;
        return this;
    }

    public String getDocType() {
        return docType;
    }

    public PemTempEntity setDocType(String docType) {
        this.docType = docType;
        return this;
    }

    public String getSenderId() {
        return senderId;
    }

    public PemTempEntity setSenderId(String senderId) {
        this.senderId = senderId;
        return this;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public PemTempEntity setReceiverId(String receiverId) {
        this.receiverId = receiverId;
        return this;
    }

    public String getTrans() {
        return trans;
    }

    public PemTempEntity setTrans(String trans) {
        this.trans = trans;
        return this;
    }

    @Override
    public String toString() {
        return "PemTempEntity{" +
                "ProcessDocPkId='" + processDocPkId + '\'' +
                ", seqId='" + seqId + '\'' +
                ", partnerProfile='" + partnerProfile + '\'' +
                ", applicationProfile='" + applicationProfile + '\'' +
                ", seqType='" + seqType + '\'' +
                ", fileName='" + fileName + '\'' +
                ", flowType='" + flowType + '\'' +
                ", docType='" + docType + '\'' +
                ", senderId='" + senderId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", transaction='" + trans + '\'' +
                '}';
    }
}
