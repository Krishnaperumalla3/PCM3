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

import com.pe.pcm.constants.AuthoritiesConstants;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

import static com.pe.pcm.utils.PCMConstants.*;

@Entity
@Table(name = "SCI_DOC_EXCHANGE")
public class SciDocExchangeEntity extends SciAudit implements Serializable {

    @Id
    private String objectId;

    private String externalObjectId;

    private String objectVersion;

    private String objectName;

    private String entityId;

    private String noOfRetries;

    private String retryInterval;

    private String persistDuration;

    private String envelopeProtocol;

    private String envEncryptAlg;

    private String keyModel;

    private String sigKeyCertId;

    private String sigKeyCertPwd;

    private String sigUserCertId;

    private String msgSigningAlg;

    private String msgSymmAlgStgth;

    private String idempotency;

    private String objectClass;

    @UpdateTimestamp
    private Date lastModification;

    private String lastModifier = INSTALL_PROCESS;

    private String objectState;

    private Integer noOfRetriesInh = 0;

    private Integer retryIntervalInh = 0;

    private Integer persistDuraInh = 0;

    private Integer envProtoInh = 0;

    private Integer envEncrypAlgInh = 0;

    private Integer keyModelInh = 0;

    private Integer sigKeyCertidInh = 0;

    private Integer sigkeyCrtPwdInh = 0;

    private Integer sigUsrCertidInh = 0;

    private Integer msgSigAlgInh = 0;

    private Integer msgSymAlgStInh = 0;

    private Integer idempotencyInh = 0;

    private String extendsObjectId;

    private Integer extObjectVersion;

    private String pgpPkEncrypt;

    private String pgpPkVerify;

    private Integer pgpPkEncryptInh = 0;

    private Integer pgpPkVerifyInh = 0;

    private String docExchangeKey;

    public SciDocExchangeEntity() {
        this.setLockid(1);
        this.setCreateuserid(INSTALL_PROCESS);
        this.setModifyuserid(AuthoritiesConstants.ADMIN);
        this.setCreateprogid(UI);
        this.setModifyprogid(UI);
        this.setObjectVersion("1");
        this.setObjectClass("DOC_EXCHANGE");
        this.setObjectState(STRING_TRUE);
        this.setExtObjectVersion(0);
        this.setPersistDuration("300");
        this.setEnvelopeProtocol("2");
        this.setNoOfRetries("3");
        this.setRetryInterval("30");
        this.setPersistDuration("300");
        this.setEnvelopeProtocol("2");

    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getExternalObjectId() {
        return externalObjectId;
    }

    public void setExternalObjectId(String externalObjectId) {
        this.externalObjectId = externalObjectId;
    }

    public String getObjectVersion() {
        return objectVersion;
    }

    public void setObjectVersion(String objectVersion) {
        this.objectVersion = objectVersion;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getNoOfRetries() {
        return noOfRetries;
    }

    public void setNoOfRetries(String noOfRetries) {
        this.noOfRetries = noOfRetries;
    }

    public String getRetryInterval() {
        return retryInterval;
    }

    public void setRetryInterval(String retryInterval) {
        this.retryInterval = retryInterval;
    }

    public String getPersistDuration() {
        return persistDuration;
    }

    public void setPersistDuration(String persistDuration) {
        this.persistDuration = persistDuration;
    }

    public String getEnvelopeProtocol() {
        return envelopeProtocol;
    }

    public void setEnvelopeProtocol(String envelopeProtocol) {
        this.envelopeProtocol = envelopeProtocol;
    }

    public String getEnvEncryptAlg() {
        return envEncryptAlg;
    }

    public void setEnvEncryptAlg(String envEncryptAlg) {
        this.envEncryptAlg = envEncryptAlg;
    }

    public String getKeyModel() {
        return keyModel;
    }

    public void setKeyModel(String keyModel) {
        this.keyModel = keyModel;
    }

    public String getSigKeyCertId() {
        return sigKeyCertId;
    }

    public void setSigKeyCertId(String sigKeyCertId) {
        this.sigKeyCertId = sigKeyCertId;
    }

    public String getSigKeyCertPwd() {
        return sigKeyCertPwd;
    }

    public void setSigKeyCertPwd(String sigKeyCertPwd) {
        this.sigKeyCertPwd = sigKeyCertPwd;
    }

    public String getSigUserCertId() {
        return sigUserCertId;
    }

    public void setSigUserCertId(String sigUserCertId) {
        this.sigUserCertId = sigUserCertId;
    }

    public String getMsgSigningAlg() {
        return msgSigningAlg;
    }

    public void setMsgSigningAlg(String msgSigningAlg) {
        this.msgSigningAlg = msgSigningAlg;
    }

    public String getMsgSymmAlgStgth() {
        return msgSymmAlgStgth;
    }

    public void setMsgSymmAlgStgth(String msgSymmAlgStgth) {
        this.msgSymmAlgStgth = msgSymmAlgStgth;
    }

    public String getIdempotency() {
        return idempotency;
    }

    public void setIdempotency(String idempotency) {
        this.idempotency = idempotency;
    }

    public String getObjectClass() {
        return objectClass;
    }

    public void setObjectClass(String objectClass) {
        this.objectClass = objectClass;
    }

    public Date getLastModification() {
        return lastModification;
    }

    public void setLastModification(Date lastModification) {
        this.lastModification = lastModification;
    }

    public String getLastModifier() {
        return lastModifier;
    }

    public void setLastModifier(String lastModifier) {
        this.lastModifier = lastModifier;
    }

    public String getObjectState() {
        return objectState;
    }

    public void setObjectState(String objectState) {
        this.objectState = objectState;
    }

    public Integer getNoOfRetriesInh() {
        return noOfRetriesInh;
    }

    public void setNoOfRetriesInh(Integer noOfRetriesInh) {
        this.noOfRetriesInh = noOfRetriesInh;
    }

    public Integer getRetryIntervalInh() {
        return retryIntervalInh;
    }

    public void setRetryIntervalInh(Integer retryIntervalInh) {
        this.retryIntervalInh = retryIntervalInh;
    }

    public Integer getPersistDuraInh() {
        return persistDuraInh;
    }

    public void setPersistDuraInh(Integer persistDuraInh) {
        this.persistDuraInh = persistDuraInh;
    }

    public Integer getEnvProtoInh() {
        return envProtoInh;
    }

    public void setEnvProtoInh(Integer envProtoInh) {
        this.envProtoInh = envProtoInh;
    }

    public Integer getEnvEncrypAlgInh() {
        return envEncrypAlgInh;
    }

    public void setEnvEncrypAlgInh(Integer envEncrypAlgInh) {
        this.envEncrypAlgInh = envEncrypAlgInh;
    }

    public Integer getKeyModelInh() {
        return keyModelInh;
    }

    public void setKeyModelInh(Integer keyModelInh) {
        this.keyModelInh = keyModelInh;
    }

    public Integer getSigKeyCertidInh() {
        return sigKeyCertidInh;
    }

    public void setSigKeyCertidInh(Integer sigKeyCertidInh) {
        this.sigKeyCertidInh = sigKeyCertidInh;
    }

    public Integer getSigkeyCrtPwdInh() {
        return sigkeyCrtPwdInh;
    }

    public void setSigkeyCrtPwdInh(Integer sigkeyCrtPwdInh) {
        this.sigkeyCrtPwdInh = sigkeyCrtPwdInh;
    }

    public Integer getSigUsrCertidInh() {
        return sigUsrCertidInh;
    }

    public void setSigUsrCertidInh(Integer sigUsrCertidInh) {
        this.sigUsrCertidInh = sigUsrCertidInh;
    }

    public Integer getMsgSigAlgInh() {
        return msgSigAlgInh;
    }

    public void setMsgSigAlgInh(Integer msgSigAlgInh) {
        this.msgSigAlgInh = msgSigAlgInh;
    }

    public Integer getMsgSymAlgStInh() {
        return msgSymAlgStInh;
    }

    public void setMsgSymAlgStInh(Integer msgSymAlgStInh) {
        this.msgSymAlgStInh = msgSymAlgStInh;
    }

    public Integer getIdempotencyInh() {
        return idempotencyInh;
    }

    public void setIdempotencyInh(Integer idempotencyInh) {
        this.idempotencyInh = idempotencyInh;
    }

    public String getExtendsObjectId() {
        return extendsObjectId;
    }

    public void setExtendsObjectId(String extendsObjectId) {
        this.extendsObjectId = extendsObjectId;
    }

    public Integer getExtObjectVersion() {
        return extObjectVersion;
    }

    public void setExtObjectVersion(Integer extObjectVersion) {
        this.extObjectVersion = extObjectVersion;
    }

    public String getPgpPkEncrypt() {
        return pgpPkEncrypt;
    }

    public void setPgpPkEncrypt(String pgpPkEncrypt) {
        this.pgpPkEncrypt = pgpPkEncrypt;
    }

    public String getPgpPkVerify() {
        return pgpPkVerify;
    }

    public void setPgpPkVerify(String pgpPkVerify) {
        this.pgpPkVerify = pgpPkVerify;
    }

    public Integer getPgpPkEncryptInh() {
        return pgpPkEncryptInh;
    }

    public void setPgpPkEncryptInh(Integer pgpPkEncryptInh) {
        this.pgpPkEncryptInh = pgpPkEncryptInh;
    }

    public Integer getPgpPkVerifyInh() {
        return pgpPkVerifyInh;
    }

    public void setPgpPkVerifyInh(Integer pgpPkVerifyInh) {
        this.pgpPkVerifyInh = pgpPkVerifyInh;
    }

    public String getDocExchangeKey() {
        return docExchangeKey;
    }

    public void setDocExchangeKey(String docExchangeKey) {
        this.docExchangeKey = docExchangeKey;
    }

    @Override
    public String toString() {
        return "SciDocExchangeEntity [objectId=" + objectId + ", externalObjectId=" + externalObjectId + ", objectVersion="
                + objectVersion + ", objectName=" + objectName + ", entityId=" + entityId + ", noOfRetries="
                + noOfRetries + ", retryInterval=" + retryInterval + ", persistDuration=" + persistDuration
                + ", envelopeProtocol=" + envelopeProtocol + ", envEncryptAlg=" + envEncryptAlg + ", keyModel="
                + keyModel + ", sigKeyCertId=" + sigKeyCertId + ", sigKeyCertPwd=" + sigKeyCertPwd + ", sigUserCertId="
                + sigUserCertId + ", msgSigningAlg=" + msgSigningAlg + ", msgSymmAlgStgth=" + msgSymmAlgStgth
                + ", idempotency=" + idempotency + ", objectClass=" + objectClass + ", lastModification="
                + lastModification + ", lastModifier=" + lastModifier + ", objectState=" + objectState
                + ", noOfRetriesInh=" + noOfRetriesInh + ", retryIntervalInh=" + retryIntervalInh + ", persistDuraInh="
                + persistDuraInh + ", envProtoInh=" + envProtoInh + ", envEncrypAlgInh=" + envEncrypAlgInh
                + ", keyModelInh=" + keyModelInh + ", sigKeyCertidInh=" + sigKeyCertidInh + ", sigkeyCrtPwdInh="
                + sigkeyCrtPwdInh + ", sigUsrCertidInh=" + sigUsrCertidInh + ", msgSigAlgInh=" + msgSigAlgInh
                + ", msgSymAlgStInh=" + msgSymAlgStInh + ", idempotencyInh=" + idempotencyInh + ", extendsObjectId="
                + extendsObjectId + ", extObjectVersion=" + extObjectVersion + ", pgpPkEncrypt=" + pgpPkEncrypt
                + ", pgpPkVerify=" + pgpPkVerify + ", pgpPkEncryptInh=" + pgpPkEncryptInh + ", pgpPkVerifyInh="
                + pgpPkVerifyInh + ", docExchangeKey=" + docExchangeKey + "]";
    }

}
