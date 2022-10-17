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
import static com.pe.pcm.utils.PCMConstants.CM_API;


@Entity
@Table(name = "SCI_DOCX_USER_CERT")
public class SciDocxUserCertEntity extends SciAudit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String certificateKey;

    private String docExchangeId;

    private String userCertId;

    private Date goLiveDate;

    private Date notAfterDate;

    private Integer certStatus;

    private Integer certOrder;

    public SciDocxUserCertEntity() {
        this.setLockid(1);
        this.setUserCertId("");
        this.setCreateuserid(NONE);
        this.setModifyuserid(NONE);
        this.setCreateprogid(CM_API);
        this.setModifyprogid(CM_API);
    }

    public String getCertificateKey() {
        return certificateKey;
    }

    public SciDocxUserCertEntity setCertificateKey(String certificateKey) {
        this.certificateKey = certificateKey;
        return this;
    }

    public String getDocExchangeId() {
        return docExchangeId;
    }

    public SciDocxUserCertEntity setDocExchangeId(String docExchangeId) {
        this.docExchangeId = docExchangeId;
        return this;
    }

    public String getUserCertId() {
        return userCertId;
    }

    public SciDocxUserCertEntity setUserCertId(String userCertId) {
        this.userCertId = userCertId;
        return this;
    }

    public Date getGoLiveDate() {
        return goLiveDate;
    }

    public SciDocxUserCertEntity setGoLiveDate(Date goLiveDate) {
        this.goLiveDate = goLiveDate;
        return this;
    }

    public Date getNotAfterDate() {
        return notAfterDate;
    }

    public SciDocxUserCertEntity setNotAfterDate(Date notAfterDate) {
        this.notAfterDate = notAfterDate;
        return this;
    }

    public Integer getCertStatus() {
        return certStatus;
    }

    public SciDocxUserCertEntity setCertStatus(Integer certStatus) {
        this.certStatus = certStatus;
        return this;
    }

    public Integer getCertOrder() {
        return certOrder;
    }

    public SciDocxUserCertEntity setCertOrder(Integer certOrder) {
        this.certOrder = certOrder;
        return this;
    }

    @Override
    public String toString() {
        return "SciDocxUserCertEntity [certificateKey=" + certificateKey + ", docExchangeId=" + docExchangeId
                + ", userCertId=" + userCertId + ", goLiveDate=" + goLiveDate + ", notAfterDate=" + notAfterDate
                + ", certStatus=" + certStatus + ", certOrder=" + certOrder + "]";
    }

}
