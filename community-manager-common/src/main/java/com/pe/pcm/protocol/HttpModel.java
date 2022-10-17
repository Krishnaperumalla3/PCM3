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

@JacksonXmlRootElement(localName = "PARTNER_HTTP")
@JsonPropertyOrder({"pkId", "profileName", "profileId", "emailId", "phone",
        "protocol", "addressLine1", "addressLine2", "status", "inMailBox",
        "outBoundUrl", "poolingInterval", "adapterName", "hubInfo", "certificate"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class HttpModel extends ProfileModel {

    private String inMailBox;

    private String outBoundUrl;

    private String poolingInterval;

    private String adapterName;

    private String certificate;

    private String routingRuleName;


    @JacksonXmlProperty(localName = "IN_MAIL_BOX")
    public String getInMailBox() {
        return inMailBox;
    }

    public HttpModel setInMailBox(String inMailBox) {
        this.inMailBox = inMailBox;
        return this;
    }

    @JacksonXmlProperty(localName = "OUT_BOUND_URL")
    public String getOutBoundUrl() {
        return outBoundUrl;
    }

    public HttpModel setOutBoundUrl(String outBoundUrl) {
        this.outBoundUrl = outBoundUrl;
        return this;
    }

    @JacksonXmlProperty(localName = "POOLING_INTERVAL")
    public String getPoolingInterval() {
        return poolingInterval;
    }

    public HttpModel setPoolingInterval(String poolingInterval) {
        this.poolingInterval = poolingInterval;
        return this;
    }

    @JacksonXmlProperty(localName = "ADAPTER_NAME")
    public String getAdapterName() {
        return adapterName;
    }

    public HttpModel setAdapterName(String adapterName) {
        this.adapterName = adapterName;
        return this;
    }

    @JacksonXmlProperty(localName = "CERTIFICATE")
    public String getCertificate() {
        return certificate;
    }

    public HttpModel setCertificate(String certificate) {
        this.certificate = certificate;
        return this;
    }

    public String getRoutingRuleName() {
        return routingRuleName;
    }

    public void setRoutingRuleName(String routingRuleName) {
        this.routingRuleName = routingRuleName;
    }
}
