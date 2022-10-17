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

package com.pe.pcm.protocol.sap.entity;

import com.pe.pcm.audit.Auditable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author Chenchu Kiran.
 */
@Entity
@Table(name = "PETPE_SAP_STAGING")
public class SapStagingEntity extends Auditable {

    @Id
    private String pkId;

    @NotNull
    private String subscriberType;

    @NotNull
    private String subscriberId;

    @NotNull
    private String sapRoute;

    @NotNull
    private String isActive;

    @NotNull
    private String isHubInfo;

    @NotNull
    private String sapAdapterName;

    public String getPkId() {
        return pkId;
    }

    public SapStagingEntity setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getSubscriberType() {
        return subscriberType;
    }

    public SapStagingEntity setSubscriberType(String subscriberType) {
        this.subscriberType = subscriberType;
        return this;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public SapStagingEntity setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
        return this;
    }

    public String getSapRoute() {
        return sapRoute;
    }

    public SapStagingEntity setSapRoute(String sapRoute) {
        this.sapRoute = sapRoute;
        return this;
    }

    public String getIsActive() {
        return isActive;
    }

    public SapStagingEntity setIsActive(String isActive) {
        this.isActive = isActive;
        return this;
    }

    public String getIsHubInfo() {
        return isHubInfo;
    }

    public SapStagingEntity setIsHubInfo(String isHubInfo) {
        this.isHubInfo = isHubInfo;
        return this;
    }

    public String getSapAdapterName() {
        return sapAdapterName;
    }

    public SapStagingEntity setSapAdapterName(String sapAdapterName) {
        this.sapAdapterName = sapAdapterName;
        return this;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "SapEntity [pkId=" + pkId + ", subscriberType=" + subscriberType + ", subscriberId=" + subscriberId
                + ", sapRoute=" + sapRoute + ", isActive=" + isActive + ", isHubInfo=" + isHubInfo + ", sapAdapterName="
                + sapAdapterName + "]";
    }


}
