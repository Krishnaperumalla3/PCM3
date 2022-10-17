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

package com.pe.pcm.sterling.yfs.entity;

import com.pe.pcm.login.entity.PcmAudit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Chenchu Kiran.
 */
@Entity
@Table(name = "YFS_RESOURCE_PERMISSION")
public class YfsResourcePermissionEntity extends PcmAudit implements Serializable {

    @Id
    private String resourcePermissionKey;
    private String resourceKey;
    @Column(name = "USERGROUP_KEY")
    private String userGroupKey;
    private String userKey;
    private String activateFlag;
    private String readOnlyFlag;
    private String rights;


    public String getResourcePermissionKey() {
        return resourcePermissionKey;
    }

    public YfsResourcePermissionEntity setResourcePermissionKey(String resourcePermissionKey) {
        this.resourcePermissionKey = resourcePermissionKey;
        return this;
    }

    public String getResourceKey() {
        return resourceKey;
    }

    public YfsResourcePermissionEntity setResourceKey(String resourceKey) {
        this.resourceKey = resourceKey;
        return this;
    }

    public String getUserGroupKey() {
        return userGroupKey;
    }

    public YfsResourcePermissionEntity setUserGroupKey(String userGroupKey) {
        this.userGroupKey = userGroupKey;
        return this;
    }

    public String getUserKey() {
        return userKey;
    }

    public YfsResourcePermissionEntity setUserKey(String userKey) {
        this.userKey = userKey;
        return this;
    }

    public String getActivateFlag() {
        return activateFlag;
    }

    public YfsResourcePermissionEntity setActivateFlag(String activateFlag) {
        this.activateFlag = activateFlag;
        return this;
    }

    public String getReadOnlyFlag() {
        return readOnlyFlag;
    }

    public YfsResourcePermissionEntity setReadOnlyFlag(String readOnlyFlag) {
        this.readOnlyFlag = readOnlyFlag;
        return this;
    }

    public String getRights() {
        return rights;
    }

    public YfsResourcePermissionEntity setRights(String rights) {
        this.rights = rights;
        return this;
    }

    @Override
    public String toString() {
        return "YfsResourcePermissionEntity{" +
                "resourcePermissionKey='" + resourcePermissionKey + '\'' +
                ", resourceKey='" + resourceKey + '\'' +
                ", userGroupKey='" + userGroupKey + '\'' +
                ", userKey='" + userKey + '\'' +
                ", activateFlag='" + activateFlag + '\'' +
                ", readOnlyFlag='" + readOnlyFlag + '\'' +
                ", rights='" + rights + '\'' +
                '}';
    }
}
