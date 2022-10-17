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

package com.pe.pcm.protocol.as2.si.certificate.entity;

import com.pe.pcm.protocol.as2.si.entity.SciAudit;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

import static com.pe.pcm.utils.PCMConstants.NONE;
import static com.pe.pcm.utils.PCMConstants.UI;

/**
 * @author Chenchu Kiran.
 */
@Entity
@Table(name = "SCI_TRP_SSL_CERT")
public class SciTrpSslCertEntity extends SciAudit implements Serializable {

    @Id
    private String certificateKey;

    private String transportId;

    private String keyCertId;

    private Date goLiveDate;

    private Date notAfterDate;

    private Integer certStatus;

    private Integer certOrder;

    public SciTrpSslCertEntity() {
        this.setLockid(0);
        this.setCreateuserid(NONE);
        this.setModifyuserid(NONE);
        this.setCreateprogid(UI);
        this.setModifyprogid(UI);

    }

    public String getCertificateKey() {
        return certificateKey;
    }

    public SciTrpSslCertEntity setCertificateKey(String certificateKey) {
        this.certificateKey = certificateKey;
        return this;
    }

    public String getTransportId() {
        return transportId;
    }

    public SciTrpSslCertEntity setTransportId(String transportId) {
        this.transportId = transportId;
        return this;
    }

    public String getKeyCertId() {
        return keyCertId;
    }

    public SciTrpSslCertEntity setKeyCertId(String keyCertId) {
        this.keyCertId = keyCertId;
        return this;
    }

    public Date getGoLiveDate() {
        return goLiveDate;
    }

    public SciTrpSslCertEntity setGoLiveDate(Date goLiveDate) {
        this.goLiveDate = goLiveDate;
        return this;
    }

    public Date getNotAfterDate() {
        return notAfterDate;
    }

    public SciTrpSslCertEntity setNotAfterDate(Date notAfterDate) {
        this.notAfterDate = notAfterDate;
        return this;
    }

    public Integer getCertStatus() {
        return certStatus;
    }

    public SciTrpSslCertEntity setCertStatus(Integer certStatus) {
        this.certStatus = certStatus;
        return this;
    }

    public Integer getCertOrder() {
        return certOrder;
    }

    public SciTrpSslCertEntity setCertOrder(Integer certOrder) {
        this.certOrder = certOrder;
        return this;
    }
}
