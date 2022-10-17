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

package com.pe.pcm.application.entity;

import com.pe.pcm.audit.Auditable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "PETPE_APPLICATION")
public class ApplicationEntity extends Auditable implements Serializable {

    @Id
    private String pkId;

    @NotNull
    private String applicationName;

    @NotNull
    private String applicationId;

    @NotNull
    private String emailId;

    @NotNull
    private String phone;

    @NotNull
    private String appIntegrationProtocol;

    @NotNull
    private String appPickupFiles;

    @NotNull
    private String appDropFiles;

    @NotNull
    private String appProtocolRef;

    @NotNull
    private String appIsActive;

    private String pemIdentifier;

    private String pgpInfo;

    private String ipWhitelist;

    private String fileAppServer;

    public String getPkId() {
        return pkId;
    }

    public ApplicationEntity setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public ApplicationEntity setApplicationName(String applicationName) {
        this.applicationName = applicationName;
        return this;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public ApplicationEntity setApplicationId(String applicationId) {
        this.applicationId = applicationId;
        return this;
    }

    public String getEmailId() {
        return emailId;
    }

    public ApplicationEntity setEmailId(String emailId) {
        this.emailId = emailId;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public ApplicationEntity setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getAppIntegrationProtocol() {
        return appIntegrationProtocol;
    }

    public ApplicationEntity setAppIntegrationProtocol(String appIntegrationProtocol) {
        this.appIntegrationProtocol = appIntegrationProtocol;
        return this;
    }

    public String getAppPickupFiles() {
        return appPickupFiles;
    }

    public ApplicationEntity setAppPickupFiles(String appPickupFiles) {
        this.appPickupFiles = appPickupFiles;
        return this;
    }

    public String getAppDropFiles() {
        return appDropFiles;
    }

    public ApplicationEntity setAppDropFiles(String appDropFiles) {
        this.appDropFiles = appDropFiles;
        return this;
    }

    public String getAppProtocolRef() {
        return appProtocolRef;
    }

    public ApplicationEntity setAppProtocolRef(String appProtocolRef) {
        this.appProtocolRef = appProtocolRef;
        return this;
    }

    public String getAppIsActive() {
        return appIsActive;
    }

    public ApplicationEntity setAppIsActive(String appIsActive) {
        this.appIsActive = appIsActive;
        return this;
    }

    public String getPemIdentifier() {
        return pemIdentifier;
    }

    public ApplicationEntity setPemIdentifier(String pemIdentifier) {
        this.pemIdentifier = pemIdentifier;
        return this;
    }

    public String getPgpInfo() {
        return pgpInfo;
    }

    public ApplicationEntity setPgpInfo(String pgpInfo) {
        this.pgpInfo = pgpInfo;
        return this;
    }

    public String getIpWhitelist() {
        return ipWhitelist;
    }

    public ApplicationEntity setIpWhitelist(String ipWhitelist) {
        this.ipWhitelist = ipWhitelist;
        return this;
    }

    public String getFileAppServer() {
        return fileAppServer;
    }

    public ApplicationEntity setFileAppServer(String fileAppServer) {
        this.fileAppServer = fileAppServer;
        return this;
    }
}
