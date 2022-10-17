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

package com.pe.pcm.sterling.profile;

import com.pe.pcm.common.CommunityManagerNameModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Chenchu Kiran.
 */
public class SterlingConsumerFtpsConfigModel implements Serializable {

    private List<CommunityManagerNameModel> assignedCaCertificateNames = new ArrayList<>();
    private String connectionType;
    private Integer controlPortRange;
    private String encryptionStrength;
    private String hostName;
    private String listenPort;
    private Integer localPortRange;
    private String numberOfRetries;
    private String password;
    private String retryInterval;
    private Boolean uploadFileUnderTemporaryNameFirst = false;
    private Boolean useCCC = false;
    private Boolean useImplicitSsl = false;
    private String userName;

    public List<CommunityManagerNameModel> getAssignedCaCertificateNames() {
        return assignedCaCertificateNames;
    }

    public SterlingConsumerFtpsConfigModel setAssignedCaCertificateNames(List<CommunityManagerNameModel> assignedCaCertificateNames) {
        this.assignedCaCertificateNames = assignedCaCertificateNames;
        return this;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public SterlingConsumerFtpsConfigModel setConnectionType(String connectionType) {
        this.connectionType = connectionType;
        return this;
    }

    public Integer getControlPortRange() {
        return controlPortRange;
    }

    public SterlingConsumerFtpsConfigModel setControlPortRange(Integer controlPortRange) {
        this.controlPortRange = controlPortRange;
        return this;
    }

    public String getEncryptionStrength() {
        return encryptionStrength;
    }

    public SterlingConsumerFtpsConfigModel setEncryptionStrength(String encryptionStrength) {
        this.encryptionStrength = encryptionStrength;
        return this;
    }

    public String getHostName() {
        return hostName;
    }

    public SterlingConsumerFtpsConfigModel setHostName(String hostName) {
        this.hostName = hostName;
        return this;
    }

    public String getListenPort() {
        return listenPort;
    }

    public SterlingConsumerFtpsConfigModel setListenPort(String listenPort) {
        this.listenPort = listenPort;
        return this;
    }

    public Integer getLocalPortRange() {
        return localPortRange;
    }

    public SterlingConsumerFtpsConfigModel setLocalPortRange(Integer localPortRange) {
        this.localPortRange = localPortRange;
        return this;
    }

    public String getNumberOfRetries() {
        return numberOfRetries;
    }

    public SterlingConsumerFtpsConfigModel setNumberOfRetries(String numberOfRetries) {
        this.numberOfRetries = numberOfRetries;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public SterlingConsumerFtpsConfigModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getRetryInterval() {
        return retryInterval;
    }

    public SterlingConsumerFtpsConfigModel setRetryInterval(String retryInterval) {
        this.retryInterval = retryInterval;
        return this;
    }

    public Boolean getUploadFileUnderTemporaryNameFirst() {
        return uploadFileUnderTemporaryNameFirst;
    }

    public SterlingConsumerFtpsConfigModel setUploadFileUnderTemporaryNameFirst(Boolean uploadFileUnderTemporaryNameFirst) {
        this.uploadFileUnderTemporaryNameFirst = uploadFileUnderTemporaryNameFirst;
        return this;
    }

    public Boolean getUseCCC() {
        return useCCC;
    }

    public SterlingConsumerFtpsConfigModel setUseCCC(Boolean useCCC) {
        this.useCCC = useCCC;
        return this;
    }

    public Boolean getUseImplicitSsl() {
        return useImplicitSsl;
    }

    public SterlingConsumerFtpsConfigModel setUseImplicitSsl(Boolean useImplicitSsl) {
        this.useImplicitSsl = useImplicitSsl;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public SterlingConsumerFtpsConfigModel setUserName(String userName) {
        this.userName = userName;
        return this;
    }
}
