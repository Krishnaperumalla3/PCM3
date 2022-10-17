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

package com.pe.pcm.utills.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * @author Chenchu Kiran.
 */
@Entity
@Table(name = "PETPE_SERVICE_RUN")
public class PcmServiceRunEntity {

    @Id
    private String pkId;
    private String serviceName;
    private String serviceLock;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdDate;

    @UpdateTimestamp
    private Timestamp lastUpdatedDt;

    public String getPkId() {
        return pkId;
    }

    public PcmServiceRunEntity setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getServiceName() {
        return serviceName;
    }

    public PcmServiceRunEntity setServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public String getServiceLock() {
        return serviceLock;
    }

    public PcmServiceRunEntity setServiceLock(String serviceLock) {
        this.serviceLock = serviceLock;
        return this;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public PcmServiceRunEntity setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public Timestamp getLastUpdatedDt() {
        return lastUpdatedDt;
    }

    public PcmServiceRunEntity setLastUpdatedDt(Timestamp lastUpdatedDt) {
        this.lastUpdatedDt = lastUpdatedDt;
        return this;
    }
}
