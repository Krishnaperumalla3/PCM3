/*
 *
 *  * Copyright (c) 2020 Pragma Edge Inc
 *  *
 *  * Licensed under the Pragma Edge Inc
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  * https://pragmaedge.com/licenseagreement
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 *
 */

package com.pe.pcm.settings.entity;

import com.pe.pcm.audit.Auditable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Kiran Reddy.
 */
@Entity
@Table(name = "PETPE_TRANSFERINFO_SCH_CONF")
public class FileSchedulerConfigEntity extends Auditable {
    @Id
    private String pkId;
    private String isActive;
    private String fileAge;
    private String cloudName;
    private String region;
    private String bucketName;
    private String accessKeyId;
    private String secretKeyId;


    public String getPkId() {
        return pkId;
    }

    public FileSchedulerConfigEntity setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getCloudName() {
        return cloudName;
    }

    public FileSchedulerConfigEntity setCloudName(String cloudName) {
        this.cloudName = cloudName;
        return this;
    }

    public String getRegion() {
        return region;
    }

    public FileSchedulerConfigEntity setRegion(String region) {
        this.region = region;
        return this;
    }

    public String getBucketName() {
        return bucketName;
    }

    public FileSchedulerConfigEntity setBucketName(String bucketName) {
        this.bucketName = bucketName;
        return this;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public FileSchedulerConfigEntity setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
        return this;
    }

    public String getSecretKeyId() {
        return secretKeyId;
    }

    public FileSchedulerConfigEntity setSecretKeyId(String secretKeyId) {
        this.secretKeyId = secretKeyId;
        return this;
    }

    public String getIsActive() {
        return isActive;
    }

    public FileSchedulerConfigEntity setIsActive(String isActive) {
        this.isActive = isActive;
        return this;
    }

    public String getFileAge() {
        return fileAge;
    }

    public FileSchedulerConfigEntity setFileAge(String fileAge) {
        this.fileAge = fileAge;
        return this;
    }

}
