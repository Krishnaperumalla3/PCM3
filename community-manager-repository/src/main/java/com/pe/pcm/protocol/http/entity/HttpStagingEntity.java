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

package com.pe.pcm.protocol.http.entity;

import com.pe.pcm.audit.Auditable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.StringJoiner;

/**
 * @author Chenchu Kiran.
 */
@Entity
@Table(name = "PETPE_HTTP_STAGING")
public class HttpStagingEntity extends Auditable {

    @Id
    private String pkId;

    @NotNull
    private String subscriberType;

    @NotNull
    private String subscriberId;

    @NotNull
    private String protocolType;

    @NotNull
    private String inMailbox;

    private String outboundUrl;

    private String certificate;

    @NotNull
    private String isActive;

    @NotNull
    private String isHubInfo;

    private String adapterName;

    private String poolingIntervalMins;

    public String getPkId() {
        return pkId;
    }

    public HttpStagingEntity setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getSubscriberType() {
        return subscriberType;
    }

    public HttpStagingEntity setSubscriberType(String subscriberType) {
        this.subscriberType = subscriberType;
        return this;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public HttpStagingEntity setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
        return this;
    }

    public String getProtocolType() {
        return protocolType;
    }

    public HttpStagingEntity setProtocolType(String protocolType) {
        this.protocolType = protocolType;
        return this;
    }

    public String getInMailbox() {
        return inMailbox;
    }

    public HttpStagingEntity setInMailbox(String inMailbox) {
        this.inMailbox = inMailbox;
        return this;
    }

    public String getOutboundUrl() {
        return outboundUrl;
    }

    public HttpStagingEntity setOutboundUrl(String outboundUrl) {
        this.outboundUrl = outboundUrl;
        return this;
    }

    public String getCertificate() {
        return certificate;
    }

    public HttpStagingEntity setCertificate(String certificate) {
        this.certificate = certificate;
        return this;
    }

    public String getIsActive() {
        return isActive;
    }

    public HttpStagingEntity setIsActive(String isActive) {
        this.isActive = isActive;
        return this;
    }

    public String getIsHubInfo() {
        return isHubInfo;
    }

    public HttpStagingEntity setIsHubInfo(String isHubInfo) {
        this.isHubInfo = isHubInfo;
        return this;
    }

    public String getAdapterName() {
        return adapterName;
    }

    public HttpStagingEntity setAdapterName(String adapterName) {
        this.adapterName = adapterName;
        return this;
    }

    public String getPoolingIntervalMins() {
        return poolingIntervalMins;
    }

    public HttpStagingEntity setPoolingIntervalMins(String poolingIntervalMins) {
        this.poolingIntervalMins = poolingIntervalMins;
        return this;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */

    @Override
    public String toString() {
        return new StringJoiner(", ", HttpStagingEntity.class.getSimpleName() + "[", "]")
                .add("pkId='" + pkId + "'")
                .add("subscriberType='" + subscriberType + "'")
                .add("subscriberId='" + subscriberId + "'")
                .add("protocolType='" + protocolType + "'")
                .add("inMailbox='" + inMailbox + "'")
                .add("outboundUrl='" + outboundUrl + "'")
                .add("certificate='" + certificate + "'")
                .add("isActive='" + isActive + "'")
                .add("isHubInfo='" + isHubInfo + "'")
                .add("adapterName='" + adapterName + "'")
                .add("poolingIntervalMins='" + poolingIntervalMins + "'")
                .add("createdBy='" + createdBy + "'")
                .add("lastUpdatedBy='" + lastUpdatedBy + "'")
                .add("lastUpdatedDt=" + lastUpdatedDt)
                .toString();
    }
}
