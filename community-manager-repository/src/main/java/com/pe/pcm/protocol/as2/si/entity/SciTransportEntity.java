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
@Table(name = "SCI_TRANSPORT")
public class SciTransportEntity extends SciAudit implements Serializable {

    @Id
    private String transportKey;

    private String objectId;

    private String externalObjectId;

    private String objectVersion;

    private String objectName;

    private String entityId;

    private String sendingProtocol;

    private String receivingProtocol;

    private String endPoint;

    private String endPointType;

    private String endPointIpAddr;

    private String endPntListPort;

    private String directory;

    private String sslOption;

    private String cipherStrength;

    private String keyCertId;

    private String userCertId;

    private String securityProtocol;

    private String transferMode;

    private String responseTimeout;

    private String locDataPorts;

    private String locCtrlPorts;

    private String firewallProxy;

    private String fwConnTime;

    private String servSockTimeout;

    private String mailBox;

    private String mailFrom;

    private String mailId;

    private String cc;

    private String bcc;

    private String keyCertPassword;

    private String protocolMode;

    private String document;

    private String transpActUserId;

    private String transpActPwd;

    private String objectClass;

    @UpdateTimestamp
    private Date lastModification;

    private String lastModifier = "InstallProcess";

    private String objectState;

    private Integer sendProtoInh = 0;

    private Integer recvProtoInh = 0;

    private Integer endPointInh = 0;

    private Integer endPointTypeInh = 0;

    private Integer endPntIpInh = 0;

    private Integer endpntLstportInh = 0;

    private Integer directoryInh = 0;

    private Integer sslOptionInh = 0;

    private Integer cipherStgthInh = 0;

    private Integer keyCertIdInh = 0;

    private Integer userCertIdInh = 0;

    private Integer secProtoInh = 0;

    private Integer transferModeInh = 0;

    private Integer respTimeoutInh = 0;

    private Integer locDataPortsInh = 0;

    private Integer locCtrlPortsInh = 0;

    private Integer firewallProxyInh = 0;

    private Integer fwCntTimeInh = 0;

    private Integer servsockTmoutInh = 0;

    private Integer mailBoxInh = 0;

    private Integer mailFromInh = 0;

    private Integer mailIdInh = 0;

    private Integer ccInh = 0;

    private Integer bccInh = 0;

    private Integer keyCertPwdInh = 0;

    private Integer protocolModeInh = 0;

    private Integer documentInh = 0;

    private String extendsObjectId;

    private Integer extObjectVersion;

    private Integer transpActUidInh = 0;

    private Integer transpActPwdInh = 0;

    private Integer transpCaCertInh = 0;

    private String sslkyCertId;

    private Integer sslkyCertIdInh = 0;

    private String useCcc;

    private Integer useCccInh = 0;

    public SciTransportEntity() {
        this.setLockid(1);
        this.setCreateuserid(INSTALL_PROCESS);
        this.setModifyuserid(AuthoritiesConstants.ADMIN);
        this.setCreateprogid(UI);
        this.setModifyprogid(UI);
        this.setObjectVersion("1");
        this.setSendingProtocol("HTTP");
        this.setReceivingProtocol("HTTP");
        this.setObjectClass("TRANSPORT");
        this.setObjectState(STRING_TRUE);
        this.setExtObjectVersion(0);
        this.setEndPointType("allPurpose");
        this.setSslOption("SSL_MUST");
        this.setCipherStrength("ALL");
        this.setTransferMode("ACTIVE");
        this.setResponseTimeout("300");
        this.setLocCtrlPorts("0");
        this.setLocDataPorts("0");
        this.setFwConnTime("0");
        this.setServSockTimeout("0");
        this.setUseCcc(STRING_FALSE);
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

    public String getSendingProtocol() {
        return sendingProtocol;
    }

    public void setSendingProtocol(String sendingProtocol) {
        this.sendingProtocol = sendingProtocol;
    }

    public String getReceivingProtocol() {
        return receivingProtocol;
    }

    public void setReceivingProtocol(String receivingProtocol) {
        this.receivingProtocol = receivingProtocol;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getEndPointType() {
        return endPointType;
    }

    public void setEndPointType(String endPointType) {
        this.endPointType = endPointType;
    }

    public String getEndPointIpAddr() {
        return endPointIpAddr;
    }

    public void setEndPointIpAddr(String endPointIpAddr) {
        this.endPointIpAddr = endPointIpAddr;
    }

    public String getEndPntListPort() {
        return endPntListPort;
    }

    public void setEndPntListPort(String endPntListPort) {
        this.endPntListPort = endPntListPort;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getSslOption() {
        return sslOption;
    }

    public void setSslOption(String sslOption) {
        this.sslOption = sslOption;
    }

    public String getCipherStrength() {
        return cipherStrength;
    }

    public void setCipherStrength(String cipherStrength) {
        this.cipherStrength = cipherStrength;
    }

    public String getKeyCertId() {
        return keyCertId;
    }

    public void setKeyCertId(String keyCertId) {
        this.keyCertId = keyCertId;
    }

    public String getUserCertId() {
        return userCertId;
    }

    public void setUserCertId(String userCertId) {
        this.userCertId = userCertId;
    }

    public String getSecurityProtocol() {
        return securityProtocol;
    }

    public void setSecurityProtocol(String securityProtocol) {
        this.securityProtocol = securityProtocol;
    }

    public String getTransferMode() {
        return transferMode;
    }

    public void setTransferMode(String transferMode) {
        this.transferMode = transferMode;
    }

    public String getResponseTimeout() {
        return responseTimeout;
    }

    public void setResponseTimeout(String responseTimeout) {
        this.responseTimeout = responseTimeout;
    }

    public String getLocDataPorts() {
        return locDataPorts;
    }

    public void setLocDataPorts(String locDataPorts) {
        this.locDataPorts = locDataPorts;
    }

    public String getLocCtrlPorts() {
        return locCtrlPorts;
    }

    public void setLocCtrlPorts(String locCtrlPorts) {
        this.locCtrlPorts = locCtrlPorts;
    }

    public String getFirewallProxy() {
        return firewallProxy;
    }

    public void setFirewallProxy(String firewallProxy) {
        this.firewallProxy = firewallProxy;
    }

    public String getFwConnTime() {
        return fwConnTime;
    }

    public void setFwConnTime(String fwConnTime) {
        this.fwConnTime = fwConnTime;
    }

    public String getServSockTimeout() {
        return servSockTimeout;
    }

    public void setServSockTimeout(String servSockTimeout) {
        this.servSockTimeout = servSockTimeout;
    }

    public String getMailBox() {
        return mailBox;
    }

    public void setMailBox(String mailBox) {
        this.mailBox = mailBox;
    }

    public String getMailFrom() {
        return mailFrom;
    }

    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getKeyCertPassword() {
        return keyCertPassword;
    }

    public void setKeyCertPassword(String keyCertPassword) {
        this.keyCertPassword = keyCertPassword;
    }

    public String getProtocolMode() {
        return protocolMode;
    }

    public void setProtocolMode(String protocolMode) {
        this.protocolMode = protocolMode;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getTranspActUserId() {
        return transpActUserId;
    }

    public void setTranspActUserId(String transpActUserId) {
        this.transpActUserId = transpActUserId;
    }

    public String getTranspActPwd() {
        return transpActPwd;
    }

    public void setTranspActPwd(String transpActPwd) {
        this.transpActPwd = transpActPwd;
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

    public Integer getSendProtoInh() {
        return sendProtoInh;
    }

    public void setSendProtoInh(Integer sendProtoInh) {
        this.sendProtoInh = sendProtoInh;
    }

    public Integer getRecvProtoInh() {
        return recvProtoInh;
    }

    public void setRecvProtoInh(Integer recvProtoInh) {
        this.recvProtoInh = recvProtoInh;
    }

    public Integer getEndPointInh() {
        return endPointInh;
    }

    public void setEndPointInh(Integer endPointInh) {
        this.endPointInh = endPointInh;
    }

    public Integer getEndPointTypeInh() {
        return endPointTypeInh;
    }

    public void setEndPointTypeInh(Integer endPointTypeInh) {
        this.endPointTypeInh = endPointTypeInh;
    }

    public Integer getEndPntIpInh() {
        return endPntIpInh;
    }

    public void setEndPntIpInh(Integer endPntIpInh) {
        this.endPntIpInh = endPntIpInh;
    }

    public Integer getEndpntLstportInh() {
        return endpntLstportInh;
    }

    public void setEndpntLstportInh(Integer endpntLstportInh) {
        this.endpntLstportInh = endpntLstportInh;
    }

    public Integer getDirectoryInh() {
        return directoryInh;
    }

    public void setDirectoryInh(Integer directoryInh) {
        this.directoryInh = directoryInh;
    }

    public Integer getSslOptionInh() {
        return sslOptionInh;
    }

    public void setSslOptionInh(Integer sslOptionInh) {
        this.sslOptionInh = sslOptionInh;
    }

    public Integer getCipherStgthInh() {
        return cipherStgthInh;
    }

    public void setCipherStgthInh(Integer cipherStgthInh) {
        this.cipherStgthInh = cipherStgthInh;
    }

    public Integer getKeyCertIdInh() {
        return keyCertIdInh;
    }

    public void setKeyCertIdInh(Integer keyCertIdInh) {
        this.keyCertIdInh = keyCertIdInh;
    }

    public Integer getUserCertIdInh() {
        return userCertIdInh;
    }

    public void setUserCertIdInh(Integer userCertIdInh) {
        this.userCertIdInh = userCertIdInh;
    }

    public Integer getSecProtoInh() {
        return secProtoInh;
    }

    public void setSecProtoInh(Integer secProtoInh) {
        this.secProtoInh = secProtoInh;
    }

    public Integer getTransferModeInh() {
        return transferModeInh;
    }

    public void setTransferModeInh(Integer transferModeInh) {
        this.transferModeInh = transferModeInh;
    }

    public Integer getRespTimeoutInh() {
        return respTimeoutInh;
    }

    public void setRespTimeoutInh(Integer respTimeoutInh) {
        this.respTimeoutInh = respTimeoutInh;
    }

    public Integer getLocDataPortsInh() {
        return locDataPortsInh;
    }

    public void setLocDataPortsInh(Integer locDataPortsInh) {
        this.locDataPortsInh = locDataPortsInh;
    }

    public Integer getLocCtrlPortsInh() {
        return locCtrlPortsInh;
    }

    public void setLocCtrlPortsInh(Integer locCtrlPortsInh) {
        this.locCtrlPortsInh = locCtrlPortsInh;
    }

    public Integer getFirewallProxyInh() {
        return firewallProxyInh;
    }

    public void setFirewallProxyInh(Integer firewallProxyInh) {
        this.firewallProxyInh = firewallProxyInh;
    }

    public Integer getFwCntTimeInh() {
        return fwCntTimeInh;
    }

    public void setFwCntTimeInh(Integer fwCntTimeInh) {
        this.fwCntTimeInh = fwCntTimeInh;
    }

    public Integer getServsockTmoutInh() {
        return servsockTmoutInh;
    }

    public void setServsockTmoutInh(Integer servsockTmoutInh) {
        this.servsockTmoutInh = servsockTmoutInh;
    }

    public Integer getMailBoxInh() {
        return mailBoxInh;
    }

    public void setMailBoxInh(Integer mailBoxInh) {
        this.mailBoxInh = mailBoxInh;
    }

    public Integer getMailFromInh() {
        return mailFromInh;
    }

    public void setMailFromInh(Integer mailFromInh) {
        this.mailFromInh = mailFromInh;
    }

    public Integer getMailIdInh() {
        return mailIdInh;
    }

    public void setMailIdInh(Integer mailIdInh) {
        this.mailIdInh = mailIdInh;
    }

    public Integer getCcInh() {
        return ccInh;
    }

    public void setCcInh(Integer ccInh) {
        this.ccInh = ccInh;
    }

    public Integer getBccInh() {
        return bccInh;
    }

    public void setBccInh(Integer bccInh) {
        this.bccInh = bccInh;
    }

    public Integer getKeyCertPwdInh() {
        return keyCertPwdInh;
    }

    public void setKeyCertPwdInh(Integer keyCertPwdInh) {
        this.keyCertPwdInh = keyCertPwdInh;
    }

    public Integer getProtocolModeInh() {
        return protocolModeInh;
    }

    public void setProtocolModeInh(Integer protocolModeInh) {
        this.protocolModeInh = protocolModeInh;
    }

    public Integer getDocumentInh() {
        return documentInh;
    }

    public void setDocumentInh(Integer documentInh) {
        this.documentInh = documentInh;
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

    public Integer getTranspActUidInh() {
        return transpActUidInh;
    }

    public void setTranspActUidInh(Integer transpActUidInh) {
        this.transpActUidInh = transpActUidInh;
    }

    public Integer getTranspActPwdInh() {
        return transpActPwdInh;
    }

    public void setTranspActPwdInh(Integer transpActPwdInh) {
        this.transpActPwdInh = transpActPwdInh;
    }

    public Integer getTranspCaCertInh() {
        return transpCaCertInh;
    }

    public void setTranspCaCertInh(Integer transpCaCertInh) {
        this.transpCaCertInh = transpCaCertInh;
    }

    public String getSslkyCertId() {
        return sslkyCertId;
    }

    public void setSslkyCertId(String sslkyCertId) {
        this.sslkyCertId = sslkyCertId;
    }

    public Integer getSslkyCertIdInh() {
        return sslkyCertIdInh;
    }

    public void setSslkyCertIdInh(Integer sslkyCertIdInh) {
        this.sslkyCertIdInh = sslkyCertIdInh;
    }

    public String getUseCcc() {
        return useCcc;
    }

    public void setUseCcc(String useCcc) {
        this.useCcc = useCcc;
    }

    public Integer getUseCccInh() {
        return useCccInh;
    }

    public void setUseCccInh(Integer useCccInh) {
        this.useCccInh = useCccInh;
    }

    public String getTransportKey() {
        return transportKey;
    }

    public void setTransportKey(String transportKey) {
        this.transportKey = transportKey;
    }

    @Override
    public String toString() {
        return "SciTransportEntity [objectId=" + objectId + ", externalObjectId=" + externalObjectId + ", objectVersion="
                + objectVersion + ", objectName=" + objectName + ", entityId=" + entityId + ", sendingProtocol="
                + sendingProtocol + ", receivingProtocol=" + receivingProtocol + ", endPoint=" + endPoint
                + ", endPointType=" + endPointType + ", endPointIpAddr=" + endPointIpAddr + ", endPntListPort="
                + endPntListPort + ", directory=" + directory + ", sslOption=" + sslOption + ", cipherStrength="
                + cipherStrength + ", keyCertId=" + keyCertId + ", userCertId=" + userCertId + ", securityProtocol="
                + securityProtocol + ", transferMode=" + transferMode + ", responseTimeout=" + responseTimeout
                + ", locDataPorts=" + locDataPorts + ", locCtrlPorts=" + locCtrlPorts + ", firewallProxy="
                + firewallProxy + ", fwConnTime=" + fwConnTime + ", servSockTimeout=" + servSockTimeout + ", mailBox="
                + mailBox + ", mailFrom=" + mailFrom + ", mailId=" + mailId + ", cc=" + cc + ", bcc=" + bcc
                + ", keyCertPassword=" + keyCertPassword + ", protocolMode=" + protocolMode + ", document=" + document
                + ", transpActUserId=" + transpActUserId + ", transpActPwd=" + transpActPwd + ", objectClass="
                + objectClass + ", lastModification=" + lastModification + ", lastModifier=" + lastModifier
                + ", objectState=" + objectState + ", sendProtoInh=" + sendProtoInh + ", recvProtoInh=" + recvProtoInh
                + ", endPointInh=" + endPointInh + ", endPointTypeInh=" + endPointTypeInh + ", endPntIpInh="
                + endPntIpInh + ", endpntLstportInh=" + endpntLstportInh + ", directoryInh=" + directoryInh
                + ", sslOptionInh=" + sslOptionInh + ", cipherStgthInh=" + cipherStgthInh + ", keyCertIdInh="
                + keyCertIdInh + ", userCertIdInh=" + userCertIdInh + ", secProtoInh=" + secProtoInh
                + ", transferModeInh=" + transferModeInh + ", respTimeoutInh=" + respTimeoutInh + ", locDataPortsInh="
                + locDataPortsInh + ", locCtrlPortsInh=" + locCtrlPortsInh + ", firewallProxyInh=" + firewallProxyInh
                + ", fwCntTimeInh=" + fwCntTimeInh + ", servsockTmoutInh=" + servsockTmoutInh + ", mailBoxInh="
                + mailBoxInh + ", mailFromInh=" + mailFromInh + ", mailIdInh=" + mailIdInh + ", ccInh=" + ccInh
                + ", bccInh=" + bccInh + ", keyCertPwdInh=" + keyCertPwdInh + ", protocolModeInh=" + protocolModeInh
                + ", documentInh=" + documentInh + ", extendsObjectId=" + extendsObjectId + ", extObjectVersion="
                + extObjectVersion + ", transpActUidInh=" + transpActUidInh + ", transpActPwdInh=" + transpActPwdInh
                + ", transpCaCertInh=" + transpCaCertInh + ", sslkyCertId=" + sslkyCertId + ", sslkyCertIdInh="
                + sslkyCertIdInh + ", useCcc=" + useCcc + ", useCccInh=" + useCccInh + ", transportKey=" + transportKey
                + "]";
    }

}
