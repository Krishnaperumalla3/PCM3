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

package com.pe.pcm.certificate.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.pe.pcm.protocol.as2.si.entity.SciAudit;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * @author Chenchu Kiran.
 */
@Entity
@Table(name = "TRUSTED_CERT_INFO")
public class TrustedCertInfoEntity extends SciAudit implements Serializable {

    private static final long serialVersionUID = -4470610126561044958L;

    @Id
    private String objectId;

    @NotNull
    private String name;

    private String username;

    @UpdateTimestamp
    private Date createDate;

    private String certIssuerRdn;

    private String certIssuerSerial;

    private String certSubjectRdn;

    private String keyUsageBits;

    private Date notBefore;

    private Date notAfter;

    @NotNull
    private Integer status;

    @NotNull
    @Column(name = "sha1_hash")
    private String sha1Hash;

    private String rawCrtLocTabnam;

    @NotNull
    private String rawCertObjectId;

    private String normIssuerRdn;

    private String normSubjRdn;

    @UpdateTimestamp
    private Date modifiedDate;

    @NotNull
    private String modifiedBy;

    private String verifyOnUse;

    private String ocspInfo;

    @NotNull
    private String trustedCertificateKey;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCertIssuerRdn() {
        return certIssuerRdn;
    }

    public void setCertIssuerRdn(String certIssuerRdn) {
        this.certIssuerRdn = certIssuerRdn;
    }

    public String getCertIssuerSerial() {
        return certIssuerSerial;
    }

    public void setCertIssuerSerial(String certIssuerSerial) {
        this.certIssuerSerial = certIssuerSerial;
    }

    public String getCertSubjectRdn() {
        return certSubjectRdn;
    }

    public void setCertSubjectRdn(String certSubjectRdn) {
        this.certSubjectRdn = certSubjectRdn;
    }

    public String getKeyUsageBits() {
        return keyUsageBits;
    }

    public void setKeyUsageBits(String keyUsageBits) {
        this.keyUsageBits = keyUsageBits;
    }

    public Date getNotBefore() {
        return notBefore;
    }

    public void setNotBefore(Date notBefore) {
        this.notBefore = notBefore;
    }

    public Date getNotAfter() {
        return notAfter;
    }

    public void setNotAfter(Date notAfter) {
        this.notAfter = notAfter;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSha1Hash() {
        return sha1Hash;
    }

    public void setSha1Hash(String sha1Hash) {
        this.sha1Hash = sha1Hash;
    }

    public String getRawCrtLocTabnam() {
        return rawCrtLocTabnam;
    }

    public void setRawCrtLocTabnam(String rawCrtLocTabnam) {
        this.rawCrtLocTabnam = rawCrtLocTabnam;
    }

    public String getRawCertObjectId() {
        return rawCertObjectId;
    }

    public void setRawCertObjectId(String rawCertObjectId) {
        this.rawCertObjectId = rawCertObjectId;
    }

    public String getNormIssuerRdn() {
        return normIssuerRdn;
    }

    public void setNormIssuerRdn(String normIssuerRdn) {
        this.normIssuerRdn = normIssuerRdn;
    }

    public String getNormSubjRdn() {
        return normSubjRdn;
    }

    public void setNormSubjRdn(String normSubjRdn) {
        this.normSubjRdn = normSubjRdn;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getVerifyOnUse() {
        return verifyOnUse;
    }

    public void setVerifyOnUse(String verifyOnUse) {
        this.verifyOnUse = verifyOnUse;
    }

    public String getOcspInfo() {
        return ocspInfo;
    }

    public void setOcspInfo(String ocspInfo) {
        this.ocspInfo = ocspInfo;
    }

    public String getTrustedCertificateKey() {
        return trustedCertificateKey;
    }

    public void setTrustedCertificateKey(String trustedCertificateKey) {
        this.trustedCertificateKey = trustedCertificateKey;
    }

    @Override
    public String toString() {
        return "TrustedCertInfoEntity [objectId=" + objectId + ", name=" + name + ", username=" + username + ", createDate="
                + createDate + ", certIssuerRdn=" + certIssuerRdn + ", certIssuerSerial=" + certIssuerSerial
                + ", certSubjectRdn=" + certSubjectRdn + ", keyUsageBits=" + keyUsageBits + ", notBefore=" + notBefore
                + ", notAfter=" + notAfter + ", status=" + status + ", sha1Hash=" + sha1Hash + ", rawCrtLocTabnam="
                + rawCrtLocTabnam + ", rawCertObjectId=" + rawCertObjectId + ", normIssuerRdn=" + normIssuerRdn
                + ", normSubjRdn=" + normSubjRdn + ", modifiedDate=" + modifiedDate + ", modifiedBy=" + modifiedBy
                + ", verifyOnUse=" + verifyOnUse + ", ocspInfo=" + ocspInfo + ", trustedCertificateKey="
                + trustedCertificateKey + "]";
    }

}
