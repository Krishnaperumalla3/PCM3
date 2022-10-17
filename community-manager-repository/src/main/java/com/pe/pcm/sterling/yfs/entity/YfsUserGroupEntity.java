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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Chenchu Kiran.
 */
@Entity
@Table(name = "YFS_USER_GROUP")
public class YfsUserGroupEntity {

    @Id
    @Column(name = "usergroup_key")
    private String userGroupKey;
    @Column(name = "usergroup_id")
    private String userGroupId;
    @Column(name = "usergroup_name")
    private String userGroupName;

    public String getUserGroupKey() {
        return userGroupKey;
    }

    public YfsUserGroupEntity setUserGroupKey(String userGroupKey) {
        this.userGroupKey = userGroupKey;
        return this;
    }

    public String getUserGroupId() {
        return userGroupId;
    }

    public YfsUserGroupEntity setUserGroupId(String userGroupId) {
        this.userGroupId = userGroupId;
        return this;
    }

    public String getUserGroupName() {
        return userGroupName;
    }

    public YfsUserGroupEntity setUserGroupName(String userGroupName) {
        this.userGroupName = userGroupName;
        return this;
    }
}
