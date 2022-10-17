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

package com.pe.pcm.certificate.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Shameer.
 */
@Entity
@Table(name = "AUTHO_XREF_SSH")
public class AuthXrefSshEntity implements Serializable {

    private static final long serialVersionUID = -4470610126561044958L;

    @NotNull
    private String userkey;
    @NotNull
    @Id
    private String userId;

    public String getUserkey() {
        return userkey;
    }

    public AuthXrefSshEntity setUserkey(String userkey) {
        this.userkey = userkey;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public AuthXrefSshEntity setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("userkey", userkey)
                .append("userId", userId)
                .toString();
    }
}
