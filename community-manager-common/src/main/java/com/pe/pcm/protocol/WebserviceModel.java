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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.pe.pcm.profile.ProfileModel;

@JacksonXmlRootElement(localName = "PARTNER_WEB_SERVICE")
@JsonPropertyOrder({"pkId", "profileName", "profileId", "emailId", "phone",
        "protocol", "addressLine1", "addressLine2", "status", "hubInfo", "name", "outBoundUrl", "inMailBox",
        "certificateId", "poolingInterval", "adapterName", "deleteAfterCollection"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class WebserviceModel extends ProfileModel {

    private String name;
    private String outBoundUrl;
    private String inMailBox;
    private String certificateId;
    private String poolingInterval;
    private String adapterName;
    private Boolean deleteAfterCollection;


    @JacksonXmlProperty(localName = "NAME")
    public String getName() {
        return name;
    }

    public WebserviceModel setName(String name) {
        this.name = name;
        return this;
    }

    @JacksonXmlProperty(localName = "OUTBOUND_URL")
    public String getOutBoundUrl() {
        return outBoundUrl;
    }

    public WebserviceModel setOutBoundUrl(String outBoundUrl) {
        this.outBoundUrl = outBoundUrl;
        return this;
    }

    @JacksonXmlProperty(localName = "IN_MAIL_BOX")
    public String getInMailBox() {
        return inMailBox;
    }

    public WebserviceModel setInMailBox(String inMailBox) {
        this.inMailBox = inMailBox;
        return this;
    }

    @JacksonXmlProperty(localName = "CERTIFICATE_ID")
    public String getCertificateId() {
        return certificateId;
    }

    public WebserviceModel setCertificateId(String certificateId) {
        this.certificateId = certificateId;
        return this;
    }

    @JacksonXmlProperty(localName = "POOLING_INTERVAL")
    public String getPoolingInterval() {
        return poolingInterval;
    }

    public WebserviceModel setPoolingInterval(String poolingInterval) {
        this.poolingInterval = poolingInterval;
        return this;
    }

    @JacksonXmlProperty(localName = "ADAPTER_NAME")
    public String getAdapterName() {
        return adapterName;
    }

    public WebserviceModel setAdapterName(String adapterName) {
        this.adapterName = adapterName;
        return this;
    }

    @JacksonXmlProperty(localName = "DELETE_AFTER_COLLECTION")
    public Boolean getDeleteAfterCollection() {
        return deleteAfterCollection;
    }

    public WebserviceModel setDeleteAfterCollection(Boolean deleteAfterCollection) {
        this.deleteAfterCollection = deleteAfterCollection;
        return this;
    }
}
