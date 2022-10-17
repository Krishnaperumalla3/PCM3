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

package com.pe.pcm.protocol;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.pe.pcm.annotations.constraint.Required;
import com.pe.pcm.profile.ProfileModel;

@JacksonXmlRootElement(localName = "PARTNER_MAILBOX")
@JsonPropertyOrder({"pkId", "profileName", "profileId", "emailId", "phone",
        "protocol", "addressLine1", "addressLine2", "status", "inMailBox",
        "outMailBox", "createMailBoxInSi", "filter", "poolingInterval", "hubInfo", "deleteAfterCollection"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class MailboxModel extends ProfileModel {

    private String inMailBox;
    private String outMailBox;
    private Boolean createMailBoxInSI = false;
    private String filter;
    @Required(customMessage = "poolingInterval")
    private String poolingInterval;
    //private Boolean hubInfo
    private Boolean deleteAfterCollection;
    @JsonIgnore
    private String subscriberType;

    private String routingRuleName;

    @JacksonXmlProperty(localName = "IN_MAIL_BOX")
    public String getInMailBox() {
        return inMailBox;
    }

    public MailboxModel setInMailBox(String inMailBox) {
        this.inMailBox = inMailBox;
        return this;
    }

    @JacksonXmlProperty(localName = "OUT_MAIL_BOX")
    public String getOutMailBox() {
        return outMailBox;
    }

    public MailboxModel setOutMailBox(String outMailBox) {
        this.outMailBox = outMailBox;
        return this;
    }

    @JacksonXmlProperty(localName = "CREATE_MAIL_BOX_IN_SI")
    public Boolean getCreateMailBoxInSI() {
        return createMailBoxInSI;
    }

    public MailboxModel setCreateMailBoxInSI(Boolean createMailBoxInSI) {
        this.createMailBoxInSI = createMailBoxInSI;
        return this;
    }

    @JacksonXmlProperty(localName = "FILTER")
    public String getFilter() {
        return filter;
    }

    public MailboxModel setFilter(String filter) {
        this.filter = filter;
        return this;
    }

    @JacksonXmlProperty(localName = "POOLING_INTERVAL")
    public String getPoolingInterval() {
        return poolingInterval;
    }

    public MailboxModel setPoolingInterval(String poolingInterval) {
        this.poolingInterval = poolingInterval;
        return this;
    }

    @JacksonXmlProperty(localName = "DELETE_AFTER_COLLECTION")
    public Boolean getDeleteAfterCollection() {
        return deleteAfterCollection;
    }

    public MailboxModel setDeleteAfterCollection(Boolean deleteAfterCollection) {
        this.deleteAfterCollection = deleteAfterCollection;
        return this;
    }

    public String getSubscriberType() {
        return subscriberType;
    }

    public MailboxModel setSubscriberType(String subscriberType) {
        this.subscriberType = subscriberType;
        return this;
    }

    public String getRoutingRuleName() {
        return routingRuleName;
    }

    public void setRoutingRuleName(String routingRuleName) {
        this.routingRuleName = routingRuleName;
    }
}
