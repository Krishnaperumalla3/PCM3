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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.pe.pcm.profile.ProfileModel;

@JacksonXmlRootElement(localName = "PARTNER_FILE_SYSTEM")
@JsonPropertyOrder({"pkId", "profileName", "profileId", "emailId", "phone",
        "protocol", "addressLine1", "addressLine2", "status", "userName", "password", "fileType", "inDirectory",
        "outDirectory", "deleteAfterCollection", "poolingInterval", "adapterName", "hubInfo"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class FileSystemModel extends ProfileModel {

    private String userName;
    private String password;
    private String fileType;
    private String inDirectory;
    private String outDirectory;
    private Boolean deleteAfterCollection;
    private String poolingInterval;
    private String adapterName;
    @JsonIgnore
    private String subscriberType;
    private String routingRuleName;

    @JacksonXmlProperty(localName = "USER_NAME")
    public String getUserName() {
        return userName;
    }

    public FileSystemModel setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    @JacksonXmlProperty(localName = "PASSWORD")
    public String getPassword() {
        return password;
    }

    public FileSystemModel setPassword(String password) {
        this.password = password;
        return this;
    }

    @JacksonXmlProperty(localName = "FILE_TYPE")
    public String getFileType() {
        return fileType;
    }

    public FileSystemModel setFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    @JacksonXmlProperty(localName = "IN_DIRECTORY")
    public String getInDirectory() {
        return inDirectory;
    }

    public FileSystemModel setInDirectory(String inDirectory) {
        this.inDirectory = inDirectory;
        return this;
    }

    @JacksonXmlProperty(localName = "OUT_DIRECTORY")
    public String getOutDirectory() {
        return outDirectory;
    }

    public FileSystemModel setOutDirectory(String outDirectory) {
        this.outDirectory = outDirectory;
        return this;
    }

    @JacksonXmlProperty(localName = "DELETE_AFTER_COLLECTION")
    public Boolean getDeleteAfterCollection() {
        return deleteAfterCollection;
    }

    public FileSystemModel setDeleteAfterCollection(Boolean deleteAfterCollection) {
        this.deleteAfterCollection = deleteAfterCollection;
        return this;
    }

    @JacksonXmlProperty(localName = "POOLING_INTERVAL")
    public String getPoolingInterval() {
        return poolingInterval;
    }

    public FileSystemModel setPoolingInterval(String poolingInterval) {
        this.poolingInterval = poolingInterval;
        return this;
    }

    @JacksonXmlProperty(localName = "ADAPTER_NAME")
    public String getAdapterName() {
        return adapterName;
    }

    public FileSystemModel setAdapterName(String adapterName) {
        this.adapterName = adapterName;
        return this;
    }

    public String getSubscriberType() {
        return subscriberType;
    }

    public FileSystemModel setSubscriberType(String subscriberType) {
        this.subscriberType = subscriberType;
        return this;
    }

    public String getRoutingRuleName() {
        return routingRuleName;
    }

    public void setRoutingRuleName(String routingRuleName) {
        this.routingRuleName = routingRuleName;
    }
}
