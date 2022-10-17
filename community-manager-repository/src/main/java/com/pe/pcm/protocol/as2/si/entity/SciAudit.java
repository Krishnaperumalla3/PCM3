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

package com.pe.pcm.protocol.as2.si.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.sql.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class SciAudit {

    private Integer lockid;

    @CreationTimestamp
    @Column(name = "createts", updatable = false)
    private Date createts;

    @UpdateTimestamp
    private Date modifyts;

    private String createuserid;

    private String modifyuserid;

    private String createprogid;

    private String modifyprogid;

    public Integer getLockid() {
        return lockid;
    }

    public void setLockid(Integer lockid) {
        this.lockid = lockid;
    }

    public Date getCreatets() {
        return createts;
    }

    public void setCreatets(Date createts) {
        this.createts = createts;
    }

    public Date getModifyts() {
        return modifyts;
    }

    public void setModifyts(Date modifyts) {
        this.modifyts = modifyts;
    }

    public String getCreateuserid() {
        return createuserid;
    }

    public void setCreateuserid(String createuserid) {
        this.createuserid = createuserid;
    }

    public String getModifyuserid() {
        return modifyuserid;
    }

    public void setModifyuserid(String modifyuserid) {
        this.modifyuserid = modifyuserid;
    }

    public String getCreateprogid() {
        return createprogid;
    }

    public void setCreateprogid(String createprogid) {
        this.createprogid = createprogid;
    }

    public String getModifyprogid() {
        return modifyprogid;
    }

    public void setModifyprogid(String modifyprogid) {
        this.modifyprogid = modifyprogid;
    }

    @Override
    public String toString() {
        return "SciAudit [lockid=" + lockid + ", createts=" + createts + ", modifyts=" + modifyts + ", createuserid="
                + createuserid + ", modifyuserid=" + modifyuserid + ", createprogid=" + createprogid + ", modifyprogid="
                + modifyprogid + "]";
    }

}
