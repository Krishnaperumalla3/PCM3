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

package com.pe.pcm.protocol.filesystem.entity;

import com.pe.pcm.audit.Auditable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.StringJoiner;

/**
 * @author Chenchu Kiran.
 */
@Entity
@Table(name = "PETPE_FILESYSTEM")
public class FileSystemEntity extends Auditable {
    @Id
    private String pkId;

    @NotNull
    private String subscriberType;

    @NotNull
    private String subscriberId;
    @NotNull
    private String fsaAdapter;

    private String inDirectory;

    private String outDirectory;

    private String fileType;
    @NotNull
    private String deleteAfterCollection;
    @NotNull
    private String poolingIntervalMins;
    @NotNull
    private String isActive;

    @NotNull
    private String isHubInfo;

    private String userName;

    private String password;


    public String getPkId() {
        return pkId;
    }

    public FileSystemEntity setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getSubscriberType() {
        return subscriberType;
    }

    public FileSystemEntity setSubscriberType(String subscriberType) {
        this.subscriberType = subscriberType;
        return this;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public FileSystemEntity setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
        return this;
    }

    public String getFsaAdapter() {
        return fsaAdapter;
    }

    public FileSystemEntity setFsaAdapter(String fsaAdapter) {
        this.fsaAdapter = fsaAdapter;
        return this;
    }

    public String getInDirectory() {
        return inDirectory;
    }

    public FileSystemEntity setInDirectory(String inDirectory) {
        this.inDirectory = inDirectory;
        return this;
    }

    public String getOutDirectory() {
        return outDirectory;
    }

    public FileSystemEntity setOutDirectory(String outDirectory) {
        this.outDirectory = outDirectory;
        return this;
    }

    public String getFileType() {
        return fileType;
    }

    public FileSystemEntity setFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public String getDeleteAfterCollection() {
        return deleteAfterCollection;
    }

    public FileSystemEntity setDeleteAfterCollection(String deleteAfterCollection) {
        this.deleteAfterCollection = deleteAfterCollection;
        return this;
    }

    public String getPoolingIntervalMins() {
        return poolingIntervalMins;
    }

    public FileSystemEntity setPoolingIntervalMins(String poolingIntervalMins) {
        this.poolingIntervalMins = poolingIntervalMins;
        return this;
    }

    public String getIsActive() {
        return isActive;
    }

    public FileSystemEntity setIsActive(String isActive) {
        this.isActive = isActive;
        return this;
    }

    public String getIsHubInfo() {
        return isHubInfo;
    }

    public FileSystemEntity setIsHubInfo(String isHubInfo) {
        this.isHubInfo = isHubInfo;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public FileSystemEntity setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public FileSystemEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", FileSystemEntity.class.getSimpleName() + "[", "]")
                .add("pkId='" + pkId + "'")
                .add("subscriberType='" + subscriberType + "'")
                .add("subscriberId='" + subscriberId + "'")
                .add("fsaAdapter='" + fsaAdapter + "'")
                .add("inDirectory='" + inDirectory + "'")
                .add("outDirectory='" + outDirectory + "'")
                .add("fileType='" + fileType + "'")
                .add("deleteAfterCollection='" + deleteAfterCollection + "'")
                .add("poolingIntervalMins='" + poolingIntervalMins + "'")
                .add("isActive='" + isActive + "'")
                .add("isHubInfo='" + isHubInfo + "'")
                .add("userName='" + userName + "'")
                .add("password='" + password + "'")
                .add("createdBy='" + createdBy + "'")
                .add("lastUpdatedBy='" + lastUpdatedBy + "'")
                .add("lastUpdatedDt=" + lastUpdatedDt)
                .toString();
    }
}
