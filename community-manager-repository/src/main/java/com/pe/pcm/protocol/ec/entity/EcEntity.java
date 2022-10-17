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

package com.pe.pcm.protocol.ec.entity;

import com.pe.pcm.audit.Auditable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "PETPE_EC")
public class EcEntity extends Auditable {

    @Id
    private String pkId;

    @NotNull
    private String subscriberType;

    @NotNull
    private String subscriberId;

    @NotNull
    private String isActive;

    @NotNull
    private String isHubInfo;

    private String ecProtocol;

    private String ecProtocolRef;


    public String getPkId() {
        return pkId;
    }

    public EcEntity setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getSubscriberType() {
        return subscriberType;
    }

    public EcEntity setSubscriberType(String subscriberType) {
        this.subscriberType = subscriberType;
        return this;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public EcEntity setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
        return this;
    }

    public String getIsActive() {
        return isActive;
    }

    public EcEntity setIsActive(String isActive) {
        this.isActive = isActive;
        return this;
    }

    public String getIsHubInfo() {
        return isHubInfo;
    }

    public EcEntity setIsHubInfo(String isHubInfo) {
        this.isHubInfo = isHubInfo;
        return this;
    }

    public String getEcProtocol() {
        return ecProtocol;
    }

    public EcEntity setEcProtocol(String ecProtocol) {
        this.ecProtocol = ecProtocol;
        return this;
    }

    public String getEcProtocolRef() {
        return ecProtocolRef;
    }

    public EcEntity setEcProtocolRef(String ecProtocolRef) {
        this.ecProtocolRef = ecProtocolRef;
        return this;
    }

    @Override
    public String toString() {
        return "EcEntity{" +
                "pkId='" + pkId + '\'' +
                ", subscriberType='" + subscriberType + '\'' +
                ", subscriberId='" + subscriberId + '\'' +
                ", isActive='" + isActive + '\'' +
                ", isHubInfo='" + isHubInfo + '\'' +
                ", ecProtocol='" + ecProtocol + '\'' +
                ", ecProtocolRef='" + ecProtocolRef + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", lastUpdatedBy='" + lastUpdatedBy + '\'' +
                ", lastUpdatedDt=" + lastUpdatedDt +
                '}';
    }
}
