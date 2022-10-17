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
public class SterlingProducerFtpsConfigModel implements Serializable {

    private List<CommunityManagerNameModel> assignedCaCertificateNames = new ArrayList<>();
    private String connectionType;
    private String encryptionStrength;
    private String hostName;
    private String listenPort;
    private String numberOfRetries;
    private String retryInterval;
    private String password;
    private String siteCommand;
    private Boolean useCCC = false;
    private Boolean useImplicitSsl = false;
    private String userName;

    public List<CommunityManagerNameModel> getAssignedCaCertificateNames() {
        return assignedCaCertificateNames;
    }

    public SterlingProducerFtpsConfigModel setAssignedCaCertificateNames(List<CommunityManagerNameModel> assignedCaCertificateNames) {
        this.assignedCaCertificateNames = assignedCaCertificateNames;
        return this;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public SterlingProducerFtpsConfigModel setConnectionType(String connectionType) {
        this.connectionType = connectionType;
        return this;
    }

    public String getEncryptionStrength() {
        return encryptionStrength;
    }

    public SterlingProducerFtpsConfigModel setEncryptionStrength(String encryptionStrength) {
        this.encryptionStrength = encryptionStrength;
        return this;
    }


    public String getListenPort() {
        return listenPort;
    }

    public SterlingProducerFtpsConfigModel setListenPort(String listenPort) {
        this.listenPort = listenPort;
        return this;
    }

    public String getNumberOfRetries() {
        return numberOfRetries;
    }

    public SterlingProducerFtpsConfigModel setNumberOfRetries(String numberOfRetries) {
        this.numberOfRetries = numberOfRetries;
        return this;
    }

    public String getRetryInterval() {
        return retryInterval;
    }

    public SterlingProducerFtpsConfigModel setRetryInterval(String retryInterval) {
        this.retryInterval = retryInterval;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public SterlingProducerFtpsConfigModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getSiteCommand() {
        return siteCommand;
    }

    public SterlingProducerFtpsConfigModel setSiteCommand(String siteCommand) {
        this.siteCommand = siteCommand;
        return this;
    }

    public Boolean getUseCCC() {
        return useCCC;
    }

    public SterlingProducerFtpsConfigModel setUseCCC(Boolean useCCC) {
        this.useCCC = useCCC;
        return this;
    }

    public Boolean getUseImplicitSsl() {
        return useImplicitSsl;
    }

    public SterlingProducerFtpsConfigModel setUseImplicitSsl(Boolean useImplicitSsl) {
        this.useImplicitSsl = useImplicitSsl;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public SterlingProducerFtpsConfigModel setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getHostName() {
        return hostName;
    }

    public SterlingProducerFtpsConfigModel setHostName(String hostName) {
        this.hostName = hostName;
        return this;
    }
}
