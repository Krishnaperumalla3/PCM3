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

package com.pe.pcm.sterling.partner.sci.entity;

import com.pe.pcm.sterling.partner.sci.entity.identity.SciCodeUserXrefIdentity;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;

/**
 * @author Chenchu Kiran.
 */
@Entity
@Table(name = "SCI_CODE_USR_XREF")
public class SciCodeUserXrefEntity implements Serializable {

    @EmbeddedId
    private SciCodeUserXrefIdentity sciCodeUserXrefIdentity;

    private String tpObjectId;
    @CreationTimestamp
    private Date dateInvited;
    private int status;

    public SciCodeUserXrefIdentity getSciCodeUserXrefIdentity() {
        return sciCodeUserXrefIdentity;
    }

    public SciCodeUserXrefEntity setSciCodeUserXrefIdentity(SciCodeUserXrefIdentity sciCodeUserXrefIdentity) {
        this.sciCodeUserXrefIdentity = sciCodeUserXrefIdentity;
        return this;
    }

    public String getTpObjectId() {
        return tpObjectId;
    }

    public SciCodeUserXrefEntity setTpObjectId(String tpObjectId) {
        this.tpObjectId = tpObjectId;
        return this;
    }

    public Date getDateInvited() {
        return dateInvited;
    }

    public SciCodeUserXrefEntity setDateInvited(Date dateInvited) {
        this.dateInvited = dateInvited;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public SciCodeUserXrefEntity setStatus(int status) {
        this.status = status;
        return this;
    }
}
