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

package com.pe.pcm.protocol.remotecd.entity;

import com.pe.pcm.audit.Auditable;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author Chenchu Kiran.
 */
@Entity
@Table(name = "PETPE_SFG_CD")
public class RemoteConnectDirectEntity extends Auditable {

    @Id
    private String pkId;
    @NotNull
    private String subscriberType;
    @NotNull
    private String subscriberId;
    private String localNodeName;
    private String remoteFileName;
    private String nodeName;
    private String snodeId;
    private String snodeIdPassword;
    private String operatingSystem;
    private String directory;
    private String hostName;
    private String port;
    private String copySisOpts;
    private String checkPoint;
    private String compressionLevel;
    private String disposition;
    private String submit;
    private String secure;
    private String runJob;
    private String runTask;
    private String caCertificateName;
    private String caCertificateId;
    private String cipherSuits;
    private String dcbParam;
    private String dnsName;
    private String unit;
    private String storageClass;
    private String space;
    private String adapterName;
    private String poolingIntervalMins;
    private String securityProtocol;
    @NotNull
    private String isActive;
    @NotNull
    private String isHubInfo;
    private String isSsp;
    private String outDirectory;
    private String inboundConnectionType;
    private String outboundConnectionType;

    public String getPkId() {
        return pkId;
    }

    public RemoteConnectDirectEntity setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getSubscriberType() {
        return subscriberType;
    }

    public RemoteConnectDirectEntity setSubscriberType(String subscriberType) {
        this.subscriberType = subscriberType;
        return this;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public RemoteConnectDirectEntity setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
        return this;
    }

    public String getLocalNodeName() {
        return localNodeName;
    }

    public RemoteConnectDirectEntity setLocalNodeName(String localNodeName) {
        this.localNodeName = localNodeName;
        return this;
    }

    public String getRemoteFileName() {
        return remoteFileName;
    }

    public RemoteConnectDirectEntity setRemoteFileName(String remoteFileName) {
        this.remoteFileName = remoteFileName;
        return this;
    }

    public String getNodeName() {
        return nodeName;
    }

    public RemoteConnectDirectEntity setNodeName(String nodeName) {
        this.nodeName = nodeName;
        return this;
    }

    public String getSnodeId() {
        return snodeId;
    }

    public RemoteConnectDirectEntity setSnodeId(String snodeId) {
        this.snodeId = snodeId;
        return this;
    }

    public String getSnodeIdPassword() {
        return snodeIdPassword;
    }

    public RemoteConnectDirectEntity setSnodeIdPassword(String snodeIdPassword) {
        this.snodeIdPassword = snodeIdPassword;
        return this;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public RemoteConnectDirectEntity setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
        return this;
    }

    public String getDirectory() {
        return directory;
    }

    public RemoteConnectDirectEntity setDirectory(String directory) {
        this.directory = directory;
        return this;
    }

    public String getHostName() {
        return hostName;
    }

    public RemoteConnectDirectEntity setHostName(String hostName) {
        this.hostName = hostName;
        return this;
    }

    public String getPort() {
        return port;
    }

    public RemoteConnectDirectEntity setPort(String port) {
        this.port = port;
        return this;
    }

    public String getCopySisOpts() {
        return copySisOpts;
    }

    public RemoteConnectDirectEntity setCopySisOpts(String copySisOpts) {
        this.copySisOpts = copySisOpts;
        return this;
    }

    public String getCheckPoint() {
        return checkPoint;
    }

    public RemoteConnectDirectEntity setCheckPoint(String checkPoint) {
        this.checkPoint = checkPoint;
        return this;
    }

    public String getCompressionLevel() {
        return compressionLevel;
    }

    public RemoteConnectDirectEntity setCompressionLevel(String compressionLevel) {
        this.compressionLevel = compressionLevel;
        return this;
    }

    public String getDisposition() {
        return disposition;
    }

    public RemoteConnectDirectEntity setDisposition(String disposition) {
        this.disposition = disposition;
        return this;
    }

    public String getSubmit() {
        return submit;
    }

    public RemoteConnectDirectEntity setSubmit(String submit) {
        this.submit = submit;
        return this;
    }

    public String getSecure() {
        return secure;
    }

    public RemoteConnectDirectEntity setSecure(String secure) {
        this.secure = secure;
        return this;
    }

    public String getRunJob() {
        return runJob;
    }

    public RemoteConnectDirectEntity setRunJob(String runJob) {
        this.runJob = runJob;
        return this;
    }

    public String getRunTask() {
        return runTask;
    }

    public RemoteConnectDirectEntity setRunTask(String runTask) {
        this.runTask = runTask;
        return this;
    }

    public String getCaCertificateName() {
        return caCertificateName;
    }

    public RemoteConnectDirectEntity setCaCertificateName(String caCertificateName) {
        this.caCertificateName = caCertificateName;
        return this;
    }

    public String getCaCertificateId() {
        return caCertificateId;
    }

    public RemoteConnectDirectEntity setCaCertificateId(String caCertificateId) {
        this.caCertificateId = caCertificateId;
        return this;
    }

    public String getCipherSuits() {
        return cipherSuits;
    }

    public RemoteConnectDirectEntity setCipherSuits(String cipherSuits) {
        this.cipherSuits = cipherSuits;
        return this;
    }


    public String getAdapterName() {
        return adapterName;
    }

    public RemoteConnectDirectEntity setAdapterName(String adapterName) {
        this.adapterName = adapterName;
        return this;
    }

    public String getPoolingIntervalMins() {
        return poolingIntervalMins;
    }

    public RemoteConnectDirectEntity setPoolingIntervalMins(String poolingIntervalMins) {
        this.poolingIntervalMins = poolingIntervalMins;
        return this;
    }

    public String getIsActive() {
        return isActive;
    }

    public RemoteConnectDirectEntity setIsActive(String isActive) {
        this.isActive = isActive;
        return this;
    }

    public String getDcbParam() {
        return dcbParam;
    }

    public RemoteConnectDirectEntity setDcbParam(String dcbParam) {
        this.dcbParam = dcbParam;
        return this;
    }

    public String getDnsName() {
        return dnsName;
    }

    public RemoteConnectDirectEntity setDnsName(String dnsName) {
        this.dnsName = dnsName;
        return this;
    }

    public String getUnit() {
        return unit;
    }

    public RemoteConnectDirectEntity setUnit(String unit) {
        this.unit = unit;
        return this;
    }

    public String getStorageClass() {
        return storageClass;
    }

    public RemoteConnectDirectEntity setStorageClass(String storageClass) {
        this.storageClass = storageClass;
        return this;
    }

    public String getSpace() {
        return space;
    }

    public RemoteConnectDirectEntity setSpace(String space) {
        this.space = space;
        return this;
    }

    public String getIsHubInfo() {
        return isHubInfo;
    }

    public RemoteConnectDirectEntity setIsHubInfo(String isHubInfo) {
        this.isHubInfo = isHubInfo;
        return this;
    }

    public String getIsSsp() {
        return isSsp;
    }

    public RemoteConnectDirectEntity setIsSsp(String isSsp) {
        this.isSsp = isSsp;
        return this;
    }

    public String getSecurityProtocol() {
        return securityProtocol;
    }

    public RemoteConnectDirectEntity setSecurityProtocol(String securityProtocol) {
        this.securityProtocol = securityProtocol;
        return this;
    }

    public String getOutDirectory() {
        return outDirectory;
    }

    public RemoteConnectDirectEntity setOutDirectory(String outDirectory) {
        this.outDirectory = outDirectory;
        return this;
    }

    public String getInboundConnectionType() {
        return inboundConnectionType;
    }

    public RemoteConnectDirectEntity setInboundConnectionType(String inboundConnectionType) {
        this.inboundConnectionType = inboundConnectionType;
        return this;
    }

    public String getOutboundConnectionType() {
        return outboundConnectionType;
    }

    public RemoteConnectDirectEntity setOutboundConnectionType(String outboundConnectionType) {
        this.outboundConnectionType = outboundConnectionType;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("pkId", pkId)
                .append("subscriberType", subscriberType)
                .append("subscriberId", subscriberId)
                .append("localNodeName", localNodeName)
                .append("remoteFileName", remoteFileName)
                .append("nodeName", nodeName)
                .append("snodeId", snodeId)
                .append("snodeIdPassword", snodeIdPassword)
                .append("operatingSystem", operatingSystem)
                .append("directory", directory)
                .append("hostName", hostName)
                .append("port", port)
                .append("copySisOpts", copySisOpts)
                .append("checkPoint", checkPoint)
                .append("compressionLevel", compressionLevel)
                .append("disposition", disposition)
                .append("submit", submit)
                .append("secure", secure)
                .append("runJob", runJob)
                .append("runTask", runTask)
                .append("caCertificateName", caCertificateName)
                .append("caCertificateId", caCertificateId)
                .append("cipherSuits", cipherSuits)
                .append("dcbParam", dcbParam)
                .append("dnsName", dnsName)
                .append("unit", unit)
                .append("storageClass", storageClass)
                .append("space", space)
                .append("adapterName", adapterName)
                .append("poolingIntervalMins", poolingIntervalMins)
                .append("securityProtocol", securityProtocol)
                .append("isActive", isActive)
                .append("isHubInfo", isHubInfo)
                .toString();
    }
}
