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

package com.pe.pcm.sterling.mailbox;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Chenchu Kiran.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoutingChannelTempModel implements Serializable {

    private String pkId;
    private String templateName;
    private String consumerIdentification;
    private String bpName;
    private String processDataElementName;
    private String specialCharacterHandling;
    private String producerMailboxPath;
    private GroupPermissionsModel groupPermissions;
    private List<ProvisioningFactModel> provisioningFactList = new ArrayList<>();
    private List<ProducerFileStructureModel> producerFileStructureList = new ArrayList<>();
    private List<DeliveryChannelTemplateModel> deliveryChannelTemplateList = new ArrayList<>();

    public String getPkId() {
        return pkId;
    }

    public RoutingChannelTempModel setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getTemplateName() {
        return templateName;
    }

    public RoutingChannelTempModel setTemplateName(String templateName) {
        this.templateName = templateName;
        return this;
    }

    public String getConsumerIdentification() {
        return consumerIdentification;
    }

    public RoutingChannelTempModel setConsumerIdentification(String consumerIdentification) {
        this.consumerIdentification = consumerIdentification;
        return this;
    }

    public String getBpName() {
        return bpName;
    }

    public RoutingChannelTempModel setBpName(String bpName) {
        this.bpName = bpName;
        return this;
    }

    public String getProcessDataElementName() {
        return processDataElementName;
    }

    public RoutingChannelTempModel setProcessDataElementName(String processDataElementName) {
        this.processDataElementName = processDataElementName;
        return this;
    }

    public String getSpecialCharacterHandling() {
        return specialCharacterHandling;
    }

    public RoutingChannelTempModel setSpecialCharacterHandling(String specialCharacterHandling) {
        this.specialCharacterHandling = specialCharacterHandling;
        return this;
    }

    public String getProducerMailboxPath() {
        return producerMailboxPath;
    }

    public RoutingChannelTempModel setProducerMailboxPath(String producerMailboxPath) {
        this.producerMailboxPath = producerMailboxPath;
        return this;
    }

    public GroupPermissionsModel getGroupPermissions() {
        return groupPermissions;
    }

    public RoutingChannelTempModel setGroupPermissions(GroupPermissionsModel groupPermissions) {
        this.groupPermissions = groupPermissions;
        return this;
    }

    public List<ProvisioningFactModel> getProvisioningFactList() {
        return provisioningFactList;
    }

    public RoutingChannelTempModel setProvisioningFactList(List<ProvisioningFactModel> provisioningFactList) {
        this.provisioningFactList = provisioningFactList;
        return this;
    }

    public List<ProducerFileStructureModel> getProducerFileStructureList() {
        return producerFileStructureList;
    }

    public RoutingChannelTempModel setProducerFileStructureList(List<ProducerFileStructureModel> producerFileStructureList) {
        this.producerFileStructureList = producerFileStructureList;
        return this;
    }

    public List<DeliveryChannelTemplateModel> getDeliveryChannelTemplateList() {
        return deliveryChannelTemplateList;
    }

    public RoutingChannelTempModel setDeliveryChannelTemplateList(List<DeliveryChannelTemplateModel> deliveryChannelTemplateList) {
        this.deliveryChannelTemplateList = deliveryChannelTemplateList;
        return this;
    }
}
