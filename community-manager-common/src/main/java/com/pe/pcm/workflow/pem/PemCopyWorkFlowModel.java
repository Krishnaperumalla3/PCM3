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

package com.pe.pcm.workflow.pem;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * @author Chenchu Kiran.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PemCopyWorkFlowModel implements Serializable {

    //@NotNull
    private String sourcePartner;
    private String sourceApplication;
    //@NotNull
    private String destinationPartner;
    private String destinationApplication;

    private String inboundSenderId;
    private String inboundReceiverId;

    private String outboundSenderId;
    private String outboundReceiverId;

    private String inboundRegex;
    private String outboundRegex;

    private List<PemProcessDocsModel> processDocList = new ArrayList<>();


    public String getSourcePartner() {
        return sourcePartner;
    }

    public PemCopyWorkFlowModel setSourcePartner(String sourcePartner) {
        this.sourcePartner = sourcePartner;
        return this;
    }

    public String getSourceApplication() {
        return sourceApplication;
    }

    public PemCopyWorkFlowModel setSourceApplication(String sourceApplication) {
        this.sourceApplication = sourceApplication;
        return this;
    }

    public String getDestinationPartner() {
        return destinationPartner;
    }

    public PemCopyWorkFlowModel setDestinationPartner(String destinationPartner) {
        this.destinationPartner = destinationPartner;
        return this;
    }

    public String getDestinationApplication() {
        return destinationApplication;
    }

    public PemCopyWorkFlowModel setDestinationApplication(String destinationApplication) {
        this.destinationApplication = destinationApplication;
        return this;
    }

    public String getInboundSenderId() {
        return inboundSenderId;
    }

    public PemCopyWorkFlowModel setInboundSenderId(String inboundSenderId) {
        this.inboundSenderId = inboundSenderId;
        return this;
    }

    public String getInboundReceiverId() {
        return inboundReceiverId;
    }

    public PemCopyWorkFlowModel setInboundReceiverId(String inboundReceiverId) {
        this.inboundReceiverId = inboundReceiverId;
        return this;
    }

    public String getOutboundSenderId() {
        return outboundSenderId;
    }

    public PemCopyWorkFlowModel setOutboundSenderId(String outboundSenderId) {
        this.outboundSenderId = outboundSenderId;
        return this;
    }

    public String getOutboundReceiverId() {
        return outboundReceiverId;
    }

    public PemCopyWorkFlowModel setOutboundReceiverId(String outboundReceiverId) {
        this.outboundReceiverId = outboundReceiverId;
        return this;
    }

    public String getInboundRegex() {
        return inboundRegex;
    }

    public PemCopyWorkFlowModel setInboundRegex(String inboundRegex) {
        this.inboundRegex = inboundRegex;
        return this;
    }

    public String getOutboundRegex() {
        return outboundRegex;
    }

    public PemCopyWorkFlowModel setOutboundRegex(String outboundRegex) {
        this.outboundRegex = outboundRegex;
        return this;
    }

    public List<PemProcessDocsModel> getProcessDocList() {
        return processDocList;
    }

    public PemCopyWorkFlowModel setProcessDocList(List<PemProcessDocsModel> processDocList) {
        this.processDocList = processDocList;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PemCopyWorkFlowModel.class.getSimpleName() + "[", "]")
                .add("sourcePartner='" + sourcePartner + "'")
                .add("sourceApplication='" + sourceApplication + "'")
                .add("destinationPartner='" + destinationPartner + "'")
                .add("destinationApplication='" + destinationApplication + "'")
                .add("inboundSenderId='" + inboundSenderId + "'")
                .add("inboundReceiverId='" + inboundReceiverId + "'")
                .add("outboundSenderId='" + outboundSenderId + "'")
                .add("outboundReceiverId='" + outboundReceiverId + "'")
                .add("inboundRegex='" + inboundRegex + "'")
                .add("outboundRegex='" + outboundRegex + "'")
                .add("processDocList=" + processDocList)
                .toString();
    }
}
