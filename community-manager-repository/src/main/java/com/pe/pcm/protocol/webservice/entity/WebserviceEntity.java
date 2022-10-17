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

package com.pe.pcm.protocol.webservice.entity;

import com.pe.pcm.audit.Auditable;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author Chenchu Kiran.
 */
@Entity
@Table(name = "PETPE_WEBSERVICE")
public class WebserviceEntity extends Auditable {

    @Id
    private String pkId;

    @NotNull
    private String subscriberType;

    @NotNull
    private String subscriberId;

    @NotNull
    private String webserviceName;

    @NotNull
    private String isActive;

    @NotNull
    private String isHubInfo;

    private String outboundUrl;

    private String inDirectory;

    private String certificate;

    private String caCertName;

    private String adapterName;

    private String poolingIntervalMins;

    public String getPkId() {
        return pkId;
    }

    public WebserviceEntity setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getSubscriberType() {
        return subscriberType;
    }

    public WebserviceEntity setSubscriberType(String subscriberType) {
        this.subscriberType = subscriberType;
        return this;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public WebserviceEntity setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
        return this;
    }

    public String getWebserviceName() {
        return webserviceName;
    }

    public WebserviceEntity setWebserviceName(String webserviceName) {
        this.webserviceName = webserviceName;
        return this;
    }

    public String getIsActive() {
        return isActive;
    }

    public WebserviceEntity setIsActive(String isActive) {
        this.isActive = isActive;
        return this;
    }

    public String getIsHubInfo() {
        return isHubInfo;
    }

    public WebserviceEntity setIsHubInfo(String isHubInfo) {
        this.isHubInfo = isHubInfo;
        return this;
    }

    public String getOutboundUrl() {
        return outboundUrl;
    }

    public WebserviceEntity setOutboundUrl(String outboundUrl) {
        this.outboundUrl = outboundUrl;
        return this;
    }

    public String getInDirectory() {
        return inDirectory;
    }

    public WebserviceEntity setInDirectory(String inDirectory) {
        this.inDirectory = inDirectory;
        return this;
    }

    public String getCertificate() {
        return certificate;
    }

    public WebserviceEntity setCertificate(String certificate) {
        this.certificate = certificate;
        return this;
    }

    public String getCaCertName() {
        return caCertName;
    }

    public WebserviceEntity setCaCertName(String caCertName) {
        this.caCertName = caCertName;
        return this;
    }

    public String getAdapterName() {
        return adapterName;
    }

    public WebserviceEntity setAdapterName(String adapterName) {
        this.adapterName = adapterName;
        return this;
    }

    public String getPoolingIntervalMins() {
        return poolingIntervalMins;
    }

    public WebserviceEntity setPoolingIntervalMins(String poolingIntervalMins) {
        this.poolingIntervalMins = poolingIntervalMins;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("pkId", pkId)
                .append("subscriberType", subscriberType)
                .append("subscriberId", subscriberId)
                .append("webserviceName", webserviceName)
                .append("isActive", isActive)
                .append("isHubInfo", isHubInfo)
                .append("outboundUrl", outboundUrl)
                .append("inDirectory", inDirectory)
                .append("certificate", certificate)
                .append("caCertName", caCertName)
                .append("adapterName", adapterName)
                .append("poolingIntervalMins", poolingIntervalMins)
                .toString();
    }
}
