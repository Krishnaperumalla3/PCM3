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

import com.pe.pcm.protocol.as2.si.entity.SciAudit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Chenchu Kiran.
 */
@Entity
@Table(name = "YFS_USER_GROUP_LIST")
public class YfsUserGroupListEntity extends SciAudit {

    @Id
    private String userGroupListKey;
    private String userKey;
    @Column(name = "usergroup_key")
    private String userGroupKey;

    public String getUserGroupListKey() {
        return userGroupListKey;
    }

    public YfsUserGroupListEntity setUserGroupListKey(String userGroupListKey) {
        this.userGroupListKey = userGroupListKey;
        return this;
    }

    public String getUserKey() {
        return userKey;
    }

    public YfsUserGroupListEntity setUserKey(String userKey) {
        this.userKey = userKey;
        return this;
    }

    public String getUserGroupKey() {
        return userGroupKey;
    }

    public YfsUserGroupListEntity setUserGroupKey(String userGroupKey) {
        this.userGroupKey = userGroupKey;
        return this;
    }
}
