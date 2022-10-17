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
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.pe.pcm.profile.ProfileModel;

import javax.validation.constraints.NotNull;

@JacksonXmlRootElement(localName = "PARTNER_MQ")
@JsonPropertyOrder({"pkId", "profileName", "profileId", "emailId", "phone",
        "protocol", "addressLine1", "addressLine2", "status", "hostName", "port", "fileType", "queueManager",
        "queueName", "poolingInterval", "adapterName", "hubInfo", "deleteAfterCollection"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class MqModel extends ProfileModel {

    private String hostName;
    private String port;
    private String fileType;
    private String queueManager;
    private String queueName;
    private String userId;
    private String password;
    //Common
    @NotNull
    private String poolingInterval;
    private String adapterName;
    private Boolean deleteAfterCollection;
    private String channelName;

    @JacksonXmlProperty(localName = "USER_ID")
    public String getUserId() {
        return userId;
    }

    public MqModel setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    @JacksonXmlProperty(localName = "PASSWORD")
    public String getPassword() {
        return password;
    }

    public MqModel setPassword(String password) {
        this.password = password;
        return this;
    }

    @JacksonXmlProperty(localName = "HOST_NAME")
    public String getHostName() {
        return hostName;
    }

    public MqModel setHostName(String hostName) {
        this.hostName = hostName;
        return this;
    }

    @JacksonXmlProperty(localName = "PORT")
    public String getPort() {
        return port;
    }

    public MqModel setPort(String port) {
        this.port = port;
        return this;
    }

    @JacksonXmlProperty(localName = "FILE_TYPE")
    public String getFileType() {
        return fileType;
    }

    public MqModel setFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    @JacksonXmlProperty(localName = "QUEUE_MANAGER")
    public String getQueueManager() {
        return queueManager;
    }

    public MqModel setQueueManager(String queueManager) {
        this.queueManager = queueManager;
        return this;
    }

    @JacksonXmlProperty(localName = "QUEUE_NAME")
    public String getQueueName() {
        return queueName;
    }

    public MqModel setQueueName(String queueName) {
        this.queueName = queueName;
        return this;
    }

    @JacksonXmlProperty(localName = "POOLING_INTERVAL")
    public String getPoolingInterval() {
        return poolingInterval;
    }

    public MqModel setPoolingInterval(String poolingInterval) {
        this.poolingInterval = poolingInterval;
        return this;
    }

    @JacksonXmlProperty(localName = "ADAPTER_NAME")
    public String getAdapterName() {
        return adapterName;
    }

    public MqModel setAdapterName(String adapterName) {
        this.adapterName = adapterName;
        return this;
    }

    @JacksonXmlProperty(localName = "DELETE_AFTER_COLLECTION")
    public Boolean getDeleteAfterCollection() {
        return deleteAfterCollection;
    }

    public MqModel setDeleteAfterCollection(Boolean deleteAfterCollection) {
        this.deleteAfterCollection = deleteAfterCollection;
        return this;
    }

    public String getChannelName() {
        return channelName;
    }

    public MqModel setChannelName(String channelName) {
        this.channelName = channelName;
        return this;
    }
}
