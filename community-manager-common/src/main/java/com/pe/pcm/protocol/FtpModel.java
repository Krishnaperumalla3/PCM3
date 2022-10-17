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

package com.pe.pcm.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.pe.pcm.profile.ProfileModel;

import java.io.Serializable;

@JsonPropertyOrder({"pkId", "profileName", "profileId", "emailId", "phone",
        "protocol", "addressLine1", "addressLine2", "status", "hostName",
        "portNumber", "userName", "password", "transferType", "fileType",
        "inDirectory", "outDirectory", "certificateId", "knownHostKey",
        "hubInfo", "createUserInSI", "createDirectoryInSI", "deleteAfterCollection",
        "poolingInterval", "adapterName" , "cwdUp", "quote", "site", "connectionType",
        "mbDestination", "mbDestinationLookup" , "sslSocket", "sslCipher", "sshAuthentication",
        "sshCipher", "sshCompression", "sshMac" , "sshIdentityKeyName"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class FtpModel extends ProfileModel implements Serializable {

    //Protocol Data
    private String hostName;
    private String portNumber;
    private String userName;
    private String password;
    private String transferType;
    private String fileType;
    private String inDirectory;
    private String outDirectory;
    private String certificateId;
    private String knownHostKey;
    private Boolean createUserInSI = false;
    private Boolean createDirectoryInSI = false;
    private Boolean deleteAfterCollection;
    private String poolingInterval;
    private String adapterName;

    private String cwdUp;
    private String quote;
    private String site;
    private String connectionType;
    private String mbDestination;
    private String mbDestinationLookup;
    private String sslSocket;
    private String sslCipher;
    private String sshAuthentication;
    private String sshCipher;
    private String sshCompression;
    private String sshMac;
    private String sshIdentityKeyName;

    @JacksonXmlProperty(localName = "HOST_NAME")
    public String getHostName() {
        return hostName;
    }

    public FtpModel setHostName(String hostName) {
        this.hostName = hostName;
        return this;
    }

    @JacksonXmlProperty(localName = "PORT_NUMBER")
    public String getPortNumber() {
        return portNumber;
    }

    public FtpModel setPortNumber(String portNumber) {
        this.portNumber = portNumber;
        return this;
    }

    @JacksonXmlProperty(localName = "USER_ID")
    public String getUserName() {
        return userName;
    }

    public FtpModel setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    @JacksonXmlProperty(localName = "PASSWORD")
    public String getPassword() {
        return password;
    }

    public FtpModel setPassword(String password) {
        this.password = password;
        return this;
    }

    @JacksonXmlProperty(localName = "TRANSFER_TYPE")
    public String getTransferType() {
        return transferType;
    }

    public FtpModel setTransferType(String transferType) {
        this.transferType = transferType;
        return this;
    }

    @JacksonXmlProperty(localName = "FILE_TYPE")
    public String getFileType() {
        return fileType;
    }

    public FtpModel setFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    @JacksonXmlProperty(localName = "IN_DIRECTORY")
    public String getInDirectory() {
        return inDirectory;
    }

    public FtpModel setInDirectory(String inDirectory) {
        this.inDirectory = inDirectory;
        return this;
    }

    @JacksonXmlProperty(localName = "OUT_DIRECTORY")
    public String getOutDirectory() {
        return outDirectory;
    }

    public FtpModel setOutDirectory(String outDirectory) {
        this.outDirectory = outDirectory;
        return this;
    }

    @JacksonXmlProperty(localName = "CERTIFICATE_ID")
    public String getCertificateId() {
        return certificateId;
    }

    public FtpModel setCertificateId(String certificateId) {
        this.certificateId = certificateId;
        return this;
    }

    @JacksonXmlProperty(localName = "KNOWN_HOST_KEY")
    public String getKnownHostKey() {
        return knownHostKey;
    }

    public FtpModel setKnownHostKey(String knownHostKey) {
        this.knownHostKey = knownHostKey;
        return this;
    }

    @JacksonXmlProperty(localName = "CREATE_USER_IN_SI")
    public Boolean getCreateUserInSI() {
        return createUserInSI;
    }

    public FtpModel setCreateUserInSI(Boolean createUserInSI) {
        this.createUserInSI = createUserInSI;
        return this;
    }

    @JacksonXmlProperty(localName = "CREATE_DIRECTORY_IN_SI")
    public Boolean getCreateDirectoryInSI() {
        return createDirectoryInSI;
    }

    public FtpModel setCreateDirectoryInSI(Boolean createDirectoryInSI) {
        this.createDirectoryInSI = createDirectoryInSI;
        return this;
    }

    @JacksonXmlProperty(localName = "DELETE_AFTER_COLLECTION")
    public Boolean getDeleteAfterCollection() {
        return deleteAfterCollection;
    }

    public FtpModel setDeleteAfterCollection(Boolean deleteAfterCollection) {
        this.deleteAfterCollection = deleteAfterCollection;
        return this;
    }

    @JacksonXmlProperty(localName = "POOLING_INTERVAL")
    public String getPoolingInterval() {
        return poolingInterval;
    }

    public FtpModel setPoolingInterval(String poolingInterval) {
        this.poolingInterval = poolingInterval;
        return this;
    }

    @JacksonXmlProperty(localName = "ADAPTER_NAME")
    public String getAdapterName() {
        return adapterName;
    }

    public FtpModel setAdapterName(String adapterName) {
        this.adapterName = adapterName;
        return this;
    }

    @JacksonXmlProperty(localName = "CWD_UP")
    public String getCwdUp() {
        return cwdUp;
    }

    public FtpModel setCwdUp(String cwdUp) {
        this.cwdUp = cwdUp;
        return this;
    }
    @JacksonXmlProperty(localName = "QUOTE")
    public String getQuote() {
        return quote;
    }

    public FtpModel setQuote(String quote) {
        this.quote = quote;
        return this;
    }
    @JacksonXmlProperty(localName = "SITE")
    public String getSite() {
        return site;
    }

    public FtpModel setSite(String site) {
        this.site = site;
        return this;
    }
    @JacksonXmlProperty(localName = "CONNECTION_TYPE")
    public String getConnectionType() {
        return connectionType;
    }

    public FtpModel setConnectionType(String connectionType) {
        this.connectionType = connectionType;
        return this;
    }
    @JacksonXmlProperty(localName = "MB_DESTINATION")
    public String getMbDestination() {
        return mbDestination;
    }

    public FtpModel setMbDestination(String mbDestination) {
        this.mbDestination = mbDestination;
        return this;
    }
    @JacksonXmlProperty(localName = "MB_DESTINATION_LOOKUP")
    public String getMbDestinationLookup() {
        return mbDestinationLookup;
    }

    public FtpModel setMbDestinationLookup(String mbDestinationLookup) {
        this.mbDestinationLookup = mbDestinationLookup;
        return this;
    }
    @JacksonXmlProperty(localName = "SSL_SOCKET")
    public String getSslSocket() {
        return sslSocket;
    }

    public FtpModel setSslSocket(String sslSocket) {
        this.sslSocket = sslSocket;
        return this;
    }
    @JacksonXmlProperty(localName = "SSL_CIPHER")
    public String getSslCipher() {
        return sslCipher;
    }

    public FtpModel setSslCipher(String sslCipher) {
        this.sslCipher = sslCipher;
        return this;
    }
    @JacksonXmlProperty(localName = "SSH_AUTHENTICATION")
    public String getSshAuthentication() {
        return sshAuthentication;
    }

    public FtpModel setSshAuthentication(String sshAuthentication) {
        this.sshAuthentication = sshAuthentication;
        return this;
    }
    @JacksonXmlProperty(localName = "SSH_CIPHER")
    public String getSshCipher() {
        return sshCipher;
    }

    public FtpModel setSshCipher(String sshCipher) {
        this.sshCipher = sshCipher;
        return this;
    }
    @JacksonXmlProperty(localName = "SSH_COMPRESSION")
    public String getSshCompression() {
        return sshCompression;
    }

    public FtpModel setSshCompression(String sshCompression) {
        this.sshCompression = sshCompression;
        return this;
    }
    @JacksonXmlProperty(localName = "SSH_MAC")
    public String getSshMac() {
        return sshMac;
    }

    public FtpModel setSshMac(String sshMac) {
        this.sshMac = sshMac;
        return this;
    }
    @JacksonXmlProperty(localName = "SSH_IDENTITY_KEY_NAME")
    public String getSshIdentityKeyName() {
        return sshIdentityKeyName;
    }

    public FtpModel setSshIdentityKeyName(String sshIdentityKeyName) {
        this.sshIdentityKeyName = sshIdentityKeyName;
        return this;
    }
}
