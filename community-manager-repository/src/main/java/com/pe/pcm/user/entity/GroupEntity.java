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

package com.pe.pcm.user.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Entity
@Table(name = "PETPE_SO_TPGROUPS")
public class GroupEntity {

    @Id
    @JsonProperty(value = "pk_Id")
    private String pkId;

    @NotNull
    @JsonProperty(value = "groupname")
    private String groupname;

    private String partnerList;

    @Column(updatable = false)
    @CreationTimestamp
    private Timestamp createdDate;

    private String lastUpdatedBy;

    @UpdateTimestamp
    private Timestamp lastUpdatedDt;


    /**
     * @return the pkId
     */
    public String getPkId() {
        return pkId;
    }

    /**
     * @param pkId the pkId to set
     */
    public GroupEntity setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    /**
     * @return the groupname
     */
    public String getGroupname() {
        return groupname;
    }

    /**
     * @param groupname the groupname to set
     */
    public GroupEntity setGroupname(String groupname) {
        this.groupname = groupname;
        return this;
    }

    /**
     * @return the partnerList
     */
    public String getPartnerList() {
        return partnerList;
    }

    /**
     * @param partnerList the partnerList to set
     */
    public GroupEntity setPartnerList(String partnerList) {
        this.partnerList = partnerList;
        return this;
    }

    /**
     * @return the createdDate
     */
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public GroupEntity setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    /**
     * @return the lastUpdatedBy
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * @param lastUpdatedBy the lastUpdatedBy to set
     */
    public GroupEntity setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
        return this;
    }

    /**
     * @return the lastUpdatedDt
     */
    public Timestamp getLastUpdatedDt() {
        return lastUpdatedDt;
    }

    /**
     * @param lastUpdatedDt the lastUpdatedDt to set
     */
    public GroupEntity setLastUpdatedDt(Timestamp lastUpdatedDt) {
        this.lastUpdatedDt = lastUpdatedDt;
        return this;
    }

}
