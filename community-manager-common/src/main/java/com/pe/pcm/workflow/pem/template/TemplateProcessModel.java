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

package com.pe.pcm.workflow.pem.template;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * @author Chenchu Kiran.
 */
public class TemplateProcessModel {
    private String sourcePartner;
    private String sourceApplication;
    private String destinationPartner;
    private String destinationApplication;

    private String inboundSenderId;
    private String inboundReceiverId;

    private String outboundSenderId;
    private String outboundReceiverId;

    private String inboundRegex;
    private String outboundRegex;

    private List<TemplateProcessDocModel> templateProcessDocModelList = new ArrayList<>();

    public String getSourcePartner() {
        return sourcePartner;
    }

    public TemplateProcessModel setSourcePartner(String sourcePartner) {
        this.sourcePartner = sourcePartner;
        return this;
    }

    public String getSourceApplication() {
        return sourceApplication;
    }

    public TemplateProcessModel setSourceApplication(String sourceApplication) {
        this.sourceApplication = sourceApplication;
        return this;
    }

    public String getDestinationPartner() {
        return destinationPartner;
    }

    public TemplateProcessModel setDestinationPartner(String destinationPartner) {
        this.destinationPartner = destinationPartner;
        return this;
    }

    public String getDestinationApplication() {
        return destinationApplication;
    }

    public TemplateProcessModel setDestinationApplication(String destinationApplication) {
        this.destinationApplication = destinationApplication;
        return this;
    }

    public String getInboundSenderId() {
        return inboundSenderId;
    }

    public TemplateProcessModel setInboundSenderId(String inboundSenderId) {
        this.inboundSenderId = inboundSenderId;
        return this;
    }

    public String getInboundReceiverId() {
        return inboundReceiverId;
    }

    public TemplateProcessModel setInboundReceiverId(String inboundReceiverId) {
        this.inboundReceiverId = inboundReceiverId;
        return this;
    }

    public String getOutboundSenderId() {
        return outboundSenderId;
    }

    public TemplateProcessModel setOutboundSenderId(String outboundSenderId) {
        this.outboundSenderId = outboundSenderId;
        return this;
    }

    public String getOutboundReceiverId() {
        return outboundReceiverId;
    }

    public TemplateProcessModel setOutboundReceiverId(String outboundReceiverId) {
        this.outboundReceiverId = outboundReceiverId;
        return this;
    }

    public String getInboundRegex() {
        return inboundRegex;
    }

    public TemplateProcessModel setInboundRegex(String inboundRegex) {
        this.inboundRegex = inboundRegex;
        return this;
    }

    public String getOutboundRegex() {
        return outboundRegex;
    }

    public TemplateProcessModel setOutboundRegex(String outboundRegex) {
        this.outboundRegex = outboundRegex;
        return this;
    }

    public List<TemplateProcessDocModel> getTemplateProcessDocModelList() {
        return templateProcessDocModelList;
    }

    public TemplateProcessModel setTemplateProcessDocModelList(List<TemplateProcessDocModel> templateProcessDocModelList) {
        this.templateProcessDocModelList = templateProcessDocModelList;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TemplateProcessModel.class.getSimpleName() + "[", "]")
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
                .add("templateProcessDocModelList=" + templateProcessDocModelList)
                .toString();
    }
}
