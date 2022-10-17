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

@Entity
@Table(name = "PETPE_APPLICATION_STAGING")
public class ApplicationStagingEntity extends Auditable {

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

    public String getPkId() {
        return pkId;
    }

    public ApplicationStagingEntity setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public ApplicationStagingEntity setApplicationName(String applicationName) {
        this.applicationName = applicationName;
        return this;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public ApplicationStagingEntity setApplicationId(String applicationId) {
        this.applicationId = applicationId;
        return this;
    }

    public String getEmailId() {
        return emailId;
    }

    public ApplicationStagingEntity setEmailId(String emailId) {
        this.emailId = emailId;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public ApplicationStagingEntity setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getAppIntegrationProtocol() {
        return appIntegrationProtocol;
    }

    public ApplicationStagingEntity setAppIntegrationProtocol(String appIntegrationProtocol) {
        this.appIntegrationProtocol = appIntegrationProtocol;
        return this;
    }

    public String getAppPickupFiles() {
        return appPickupFiles;
    }

    public ApplicationStagingEntity setAppPickupFiles(String appPickupFiles) {
        this.appPickupFiles = appPickupFiles;
        return this;
    }

    public String getAppDropFiles() {
        return appDropFiles;
    }

    public ApplicationStagingEntity setAppDropFiles(String appDropFiles) {
        this.appDropFiles = appDropFiles;
        return this;
    }

    public String getAppProtocolRef() {
        return appProtocolRef;
    }

    public ApplicationStagingEntity setAppProtocolRef(String appProtocolRef) {
        this.appProtocolRef = appProtocolRef;
        return this;
    }

    public String getAppIsActive() {
        return appIsActive;
    }

    public ApplicationStagingEntity setAppIsActive(String appIsActive) {
        this.appIsActive = appIsActive;
        return this;
    }

    @Override
    public String toString() {
        return "ApplicationEntity{" +
                "pkId='" + pkId + '\'' +
                ", applicationName='" + applicationName + '\'' +
                ", applicationId='" + applicationId + '\'' +
                ", emailId='" + emailId + '\'' +
                ", phone='" + phone + '\'' +
                ", appIntegrationProtocol='" + appIntegrationProtocol + '\'' +
                ", appPickupFiles='" + appPickupFiles + '\'' +
                ", appDropFiles='" + appDropFiles + '\'' +
                ", appProtocolRef='" + appProtocolRef + '\'' +
                ", appIsActive='" + appIsActive + '\'' +
                '}';
    }
}
