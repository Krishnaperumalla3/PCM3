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

import com.pe.pcm.sterling.partner.sci.entity.identity.SciCommTpXrefIdentity;
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
@Table(name = "SCI_COMM_TP_XREF")
public class SciCommTpXrefEntity implements Serializable {

    @EmbeddedId
    private SciCommTpXrefIdentity sciCommTpXrefIdentity;
    @CreationTimestamp
    private Date dateJoined;

    public SciCommTpXrefIdentity getSciCommTpXrefIdentity() {
        return sciCommTpXrefIdentity;
    }

    public SciCommTpXrefEntity setSciCommTpXrefIdentity(SciCommTpXrefIdentity sciCommTpXrefIdentity) {
        this.sciCommTpXrefIdentity = sciCommTpXrefIdentity;
        return this;
    }

    public Date getDateJoined() {
        return dateJoined;
    }

    public SciCommTpXrefEntity setDateJoined(Date dateJoined) {
        this.dateJoined = dateJoined;
        return this;
    }
}
