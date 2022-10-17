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
import java.util.StringJoiner;

import static com.pe.pcm.utils.PCMConstants.NONE;
import static com.pe.pcm.utils.PCMConstants.UI;

/**
 * @author Chenchu Kiran.
 */
@Entity
@Table(name = "SCI_TRP_KEY_CERT")
public class SciTrpKeyCertEntity extends SciAudit implements Serializable {

    @Id
    private String certificateKey;

    private String transportId;

    private String keyCertId;

    private Date goLiveDate;

    private Date notAfterDate;

    private Integer certStatus;

    private Integer certOrder;

    public SciTrpKeyCertEntity() {
        this.setLockid(1);
        this.setCreateuserid(NONE);
        this.setModifyuserid(NONE);
        this.setCreateprogid(UI);
        this.setModifyprogid(UI);

    }

    public String getCertificateKey() {
        return certificateKey;
    }

    public SciTrpKeyCertEntity setCertificateKey(String certificateKey) {
        this.certificateKey = certificateKey;
        return this;
    }

    public String getTransportId() {
        return transportId;
    }

    public SciTrpKeyCertEntity setTransportId(String transportId) {
        this.transportId = transportId;
        return this;
    }

    public String getKeyCertId() {
        return keyCertId;
    }

    public SciTrpKeyCertEntity setKeyCertId(String keyCertId) {
        this.keyCertId = keyCertId;
        return this;
    }

    public Date getGoLiveDate() {
        return goLiveDate;
    }

    public SciTrpKeyCertEntity setGoLiveDate(Date goLiveDate) {
        this.goLiveDate = goLiveDate;
        return this;
    }

    public Date getNotAfterDate() {
        return notAfterDate;
    }

    public SciTrpKeyCertEntity setNotAfterDate(Date notAfterDate) {
        this.notAfterDate = notAfterDate;
        return this;
    }

    public Integer getCertStatus() {
        return certStatus;
    }

    public SciTrpKeyCertEntity setCertStatus(Integer certStatus) {
        this.certStatus = certStatus;
        return this;
    }

    public Integer getCertOrder() {
        return certOrder;
    }

    public SciTrpKeyCertEntity setCertOrder(Integer certOrder) {
        this.certOrder = certOrder;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SciTrpKeyCertEntity.class.getSimpleName() + "[", "]")
                .add("certificateKey='" + certificateKey + "'")
                .add("transportId='" + transportId + "'")
                .add("keyCertId='" + keyCertId + "'")
                .add("goLiveDate=" + goLiveDate)
                .add("notAfterDate=" + notAfterDate)
                .add("certStatus=" + certStatus)
                .add("certOrder=" + certOrder)
                .toString();
    }

}
