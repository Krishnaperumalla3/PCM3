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
import java.sql.Date;

import static com.pe.pcm.utils.PCMConstants.NONE;
import static com.pe.pcm.utils.PCMConstants.UI;

@Entity
@Table(name = "SCI_DOCX_KEY_CERT")
public class SciDocxKeyCertEntity extends SciAudit implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String certificateKey;
    private String docExchangeId;
    private String keyCertId;
    private Date goLiveDate;
    private Date notAfterDate;
    private Integer certStatus;
    private Integer certOrder;

    public SciDocxKeyCertEntity() {
        this.setKeyCertId("1");
        this.setLockid(1);
        this.setCreateuserid(NONE);
        this.setModifyuserid(NONE);
        this.setCreateprogid(UI);
        this.setModifyprogid(UI);
        this.setCertOrder(0);
        this.setCertOrder(1);
        this.setCertStatus(0);
    }

    public String getCertificateKey() {
        return certificateKey;
    }

    public SciDocxKeyCertEntity setCertificateKey(String certificateKey) {
        this.certificateKey = certificateKey;
        return this;
    }

    public String getDocExchangeId() {
        return docExchangeId;
    }

    public SciDocxKeyCertEntity setDocExchangeId(String docExchangeId) {
        this.docExchangeId = docExchangeId;
        return this;
    }

    public String getKeyCertId() {
        return keyCertId;
    }

    public SciDocxKeyCertEntity setKeyCertId(String keyCertId) {
        this.keyCertId = keyCertId;
        return this;
    }

    public Date getGoLiveDate() {
        return goLiveDate;
    }

    public SciDocxKeyCertEntity setGoLiveDate(Date goLiveDate) {
        this.goLiveDate = goLiveDate;
        return this;
    }

    public Date getNotAfterDate() {
        return notAfterDate;
    }

    public SciDocxKeyCertEntity setNotAfterDate(Date notAfterDate) {
        this.notAfterDate = notAfterDate;
        return this;
    }

    public Integer getCertStatus() {
        return certStatus;
    }

    public SciDocxKeyCertEntity setCertStatus(Integer certStatus) {
        this.certStatus = certStatus;
        return this;
    }

    public Integer getCertOrder() {
        return certOrder;
    }

    public SciDocxKeyCertEntity setCertOrder(Integer certOrder) {
        this.certOrder = certOrder;
        return this;
    }

    @Override
    public String toString() {
        return "SciDocxKeyCertEntity [docExchangeId=" + docExchangeId + ", keyCertId=" + keyCertId + ", certificateKey="
                + certificateKey + ", goLiveDate=" + goLiveDate + ", notAfterDate=" + notAfterDate + ", certStatus="
                + certStatus + ", certOrder=" + certOrder + "]";
    }

}
