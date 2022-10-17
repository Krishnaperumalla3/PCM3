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

package com.pe.pcm.workflow.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "PETPE_PROCESS")
public class ProcessEntity implements Serializable {

    @Id
    private String seqId;

    private String partnerProfile;

    private String applicationProfile;

    private String seqType;

    private String flow;

    public String getSeqId() {
        return seqId;
    }

    public ProcessEntity setSeqId(String seqId) {
        this.seqId = seqId;
        return this;
    }

    public String getPartnerProfile() {
        return partnerProfile;
    }

    public ProcessEntity setPartnerProfile(String partnerProfile) {
        this.partnerProfile = partnerProfile;
        return this;
    }

    public String getApplicationProfile() {
        return applicationProfile;
    }

    public ProcessEntity setApplicationProfile(String applicationProfile) {
        this.applicationProfile = applicationProfile;
        return this;
    }

    public String getSeqType() {
        return seqType;
    }

    public ProcessEntity setSeqType(String seqType) {
        this.seqType = seqType;
        return this;
    }

    public String getFlow() {
        return flow;
    }

    public ProcessEntity setFlow(String flow) {
        this.flow = flow;
        return this;
    }

    @Override
    public String toString() {
        return "ProcessEntity{" +
                "seqId='" + seqId + '\'' +
                ", partnerProfile='" + partnerProfile + '\'' +
                ", applicationProfile='" + applicationProfile + '\'' +
                ", seqType='" + seqType + '\'' +
                ", flow='" + flow + '\'' +
                '}';
    }
}
