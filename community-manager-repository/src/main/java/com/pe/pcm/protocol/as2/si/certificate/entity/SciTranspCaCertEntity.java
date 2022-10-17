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

@Entity
@Table(name = "SCI_TRANSP_CA_CERT")
public class SciTranspCaCertEntity extends SciAudit implements Serializable {

    private static final long serialVersionUID = 5688116024581776595L;

    @Id
    private String transportCertificateKey;

    private String transportId;

    private String caCertId;

    private String objectState;

    private Date goLiveDate;

    private Date notAfterDate;

    private Integer certStatus;

    private Integer certOrder;

    public SciTranspCaCertEntity() {
        this.setLockid(1);
        this.setCreateuserid(NONE);
        this.setModifyuserid(NONE);
        this.setCreateprogid(UI);
        this.setModifyprogid(UI);
    }

    public String getTransportCertificateKey() {
        return transportCertificateKey;
    }

    public void setTransportCertificateKey(String transportCertificateKey) {
        this.transportCertificateKey = transportCertificateKey;
    }

    public String getTransportId() {
        return transportId;
    }

    public void setTransportId(String transportId) {
        this.transportId = transportId;
    }

    public String getCaCertId() {
        return caCertId;
    }

    public void setCaCertId(String caCertId) {
        this.caCertId = caCertId;
    }

    public String getObjectState() {
        return objectState;
    }

    public void setObjectState(String objectState) {
        this.objectState = objectState;
    }

    public Date getGoLiveDate() {
        return goLiveDate;
    }

    public void setGoLiveDate(Date goLiveDate) {
        this.goLiveDate = goLiveDate;
    }

    public Date getNotAfterDate() {
        return notAfterDate;
    }

    public void setNotAfterDate(Date date) {
        this.notAfterDate = date;
    }

    public Integer getCertStatus() {
        return certStatus;
    }

    public void setCertStatus(Integer certStatus) {
        this.certStatus = certStatus;
    }

    public Integer getCertOrder() {
        return certOrder;
    }

    public void setCertOrder(Integer certOrder) {
        this.certOrder = certOrder;
    }

    @Override
    public String toString() {
        return "SciTranspCaCertEntity [transportCertificateKey=" + transportCertificateKey + ", transportId=" + transportId
                + ", caCertId=" + caCertId + ", objectState=" + objectState + ", goLiveDate=" + goLiveDate
                + ", notAfterDate=" + notAfterDate + ", certStatus=" + certStatus + ", certOrder=" + certOrder + "]";
    }

}
