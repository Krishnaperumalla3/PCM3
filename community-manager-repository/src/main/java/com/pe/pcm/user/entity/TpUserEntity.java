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

import com.pe.pcm.constants.AuthoritiesConstants;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "PETPE_SO_TPUSERS")
public class TpUserEntity implements Serializable {

    @Id
    private String userid;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdDate;

    @ColumnDefault(value = AuthoritiesConstants.SUPER_ADMIN)
    private String lastUpdatedBy;

    @UpdateTimestamp
    private Timestamp lastUpdatedDt;

    private String partnerList;

    private String groupList;

    public String getUserid() {
        return userid;
    }

    public TpUserEntity setUserid(String userid) {
        this.userid = userid;
        return this;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public TpUserEntity setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public TpUserEntity setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
        return this;
    }

    public Timestamp getLastUpdatedDt() {
        return lastUpdatedDt;
    }

    public TpUserEntity setLastUpdatedDt(Timestamp lastUpdatedDt) {
        this.lastUpdatedDt = lastUpdatedDt;
        return this;
    }

    public String getPartnerList() {
        return partnerList;
    }

    public TpUserEntity setPartnerList(String partnerList) {
        this.partnerList = partnerList;
        return this;
    }

    public String getGroupList() {
        return groupList;
    }

    public TpUserEntity setGroupList(String groupList) {
        this.groupList = groupList;
        return this;
    }

}
