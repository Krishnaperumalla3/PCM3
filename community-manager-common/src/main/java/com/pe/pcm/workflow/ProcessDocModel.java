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

package com.pe.pcm.workflow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JacksonXmlRootElement(localName = "PROCESS_DOC")
@JsonPropertyOrder({"fileNamePattern", "docType", "versionNo", "partnerId", "receiverId",
        "docTrans", "processRuleSeq", "processRulesList"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProcessDocModel implements Serializable {

    private static final long serialVersionUID = -1679317111364856927L;
    private String fileNamePattern;
    private String docType;
    private String versionNo;
    private String partnerId;
    private String receiverId;
    private String docTrans;
    private String processRuleSeq;
    private int index;
    private List<ProcessRuleModel> processRulesList = new ArrayList<>();

    @JacksonXmlProperty(localName = "FILENAME_PATTERN")
    public String getFileNamePattern() {
        return fileNamePattern;
    }

    public void setFileNamePattern(String fileNamePattern) {
        this.fileNamePattern = fileNamePattern;
    }

    @JacksonXmlProperty(localName = "DOCTYPE")
    public String getDocType() {
        return docType;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

    @JacksonXmlProperty(localName = "VERSION")
    public String getVersionNo() {
        return versionNo;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    @JacksonXmlProperty(localName = "PARTNERID")
    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    @JacksonXmlProperty(localName = "RECEIVERID")
    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    @JacksonXmlProperty(localName = "DOCTRANS")
    public String getDocTrans() {
        return docTrans;
    }

    public void setDocTrans(String docTrans) {
        this.docTrans = docTrans;
    }

    @JacksonXmlProperty(localName = "PROCESS_RULESEQ")
    public String getProcessRuleSeq() {
        return processRuleSeq;
    }

    public void setProcessRuleSeq(String processRuleSeq) {
        this.processRuleSeq = processRuleSeq;
    }

    @JacksonXmlElementWrapper(localName = "PROCESS_RULES")
    @JacksonXmlProperty(localName = "PROCESS_RULE")
    public List<ProcessRuleModel> getProcessRulesList() {
        return processRulesList;
    }

    public void setProcessRulesList(
            List<ProcessRuleModel> processRulesList) {
        this.processRulesList = processRulesList;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "ProcessDocModel{" +
                "fileNamePattern='" + fileNamePattern + '\'' +
                ", docType='" + docType + '\'' +
                ", versionNo='" + versionNo + '\'' +
                ", partnerId='" + partnerId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", docTrans='" + docTrans + '\'' +
                ", processRuleSeq='" + processRuleSeq + '\'' +
                ", index=" + index +
                ", processRulesList=" + processRulesList +
                '}';
    }
}
