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

package com.pe.pcm.protocol.mq.entity;

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
@Table(name = "PETPE_MQ")
public class MqEntity extends Auditable {

    @Id
    private String pkId;

    @NotNull
    private String subscriberType;

    @NotNull
    private String subscriberId;

    @NotNull
    private String queueManager;

    @NotNull
    private String queueName;

    private String poolingIntervalMins;

    private String fileType;

    @NotNull
    private String isActive;

    @NotNull
    private String isHubInfo;

    private String hostName;

    private String port;

    private String userId;

    private String password;

    private String adapterName;

    private String channelName;

    public String getPkId() {
        return pkId;
    }

    public MqEntity setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getSubscriberType() {
        return subscriberType;
    }

    public MqEntity setSubscriberType(String subscriberType) {
        this.subscriberType = subscriberType;
        return this;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public MqEntity setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
        return this;
    }

    public String getQueueManager() {
        return queueManager;
    }

    public MqEntity setQueueManager(String queueManager) {
        this.queueManager = queueManager;
        return this;
    }

    public String getQueueName() {
        return queueName;
    }

    public MqEntity setQueueName(String queueName) {
        this.queueName = queueName;
        return this;
    }

    public String getPoolingIntervalMins() {
        return poolingIntervalMins;
    }

    public MqEntity setPoolingIntervalMins(String poolingIntervalMins) {
        this.poolingIntervalMins = poolingIntervalMins;
        return this;
    }

    public String getFileType() {
        return fileType;
    }

    public MqEntity setFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public String getIsActive() {
        return isActive;
    }

    public MqEntity setIsActive(String isActive) {
        this.isActive = isActive;
        return this;
    }

    public String getIsHubInfo() {
        return isHubInfo;
    }

    public MqEntity setIsHubInfo(String isHubInfo) {
        this.isHubInfo = isHubInfo;
        return this;
    }

    public String getHostName() {
        return hostName;
    }

    public MqEntity setHostName(String hostName) {
        this.hostName = hostName;
        return this;
    }

    public String getPort() {
        return port;
    }

    public MqEntity setPort(String port) {
        this.port = port;
        return this;
    }

    public String getAdapterName() {
        return adapterName;
    }

    public MqEntity setAdapterName(String adapterName) {
        this.adapterName = adapterName;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public MqEntity setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public MqEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getChannelName() {
        return channelName;
    }

    public MqEntity setChannelName(String channelName) {
        this.channelName = channelName;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("pkId", pkId)
                .append("subscriberType", subscriberType)
                .append("subscriberId", subscriberId)
                .append("queueManager", queueManager)
                .append("queueName", queueName)
                .append("poolingIntervalMins", poolingIntervalMins)
                .append("fileType", fileType)
                .append("isActive", isActive)
                .append("isHubInfo", isHubInfo)
                .append("hostName", hostName)
                .append("port", port)
                .append("userId", userId)
                .append("password", password)
                .append("adapterName", adapterName)
                .toString();
    }
}
