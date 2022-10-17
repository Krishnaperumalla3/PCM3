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

package com.pe.pcm.audit;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PETPE_GLOBAL_AUDIT")
public class GlobalAuditEntity extends Auditable {

    @Id
    private String pkId;

    private String serviceName;

    private String serviceRefId;

    private String activityType;

    private String activity;

    public String getPkId() {
        return pkId;
    }

    public GlobalAuditEntity setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getServiceName() {
        return serviceName;
    }

    public GlobalAuditEntity setServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public String getServiceRefId() {
        return serviceRefId;
    }

    public GlobalAuditEntity setServiceRefId(String serviceRefId) {
        this.serviceRefId = serviceRefId;
        return this;
    }

    public String getActivityType() {
        return activityType;
    }

    public GlobalAuditEntity setActivityType(String activityType) {
        this.activityType = activityType;
        return this;
    }

    public String getActivity() {
        return activity;
    }

    public GlobalAuditEntity setActivity(String activity) {
        this.activity = activity;
        return this;
    }
}
