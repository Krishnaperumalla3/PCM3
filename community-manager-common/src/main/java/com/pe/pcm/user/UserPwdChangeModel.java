/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc.
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

package com.pe.pcm.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Chenchu Kiran.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserPwdChangeModel implements Serializable {
    @NotNull
    private String pkId;
    @NotNull
    private String oldPassword;
    @NotNull
    private String newPassword;

    public String getPkId() {
        return pkId;
    }

    public UserPwdChangeModel setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public UserPwdChangeModel setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
        return this;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public UserPwdChangeModel setNewPassword(String newPassword) {
        this.newPassword = newPassword;
        return this;
    }
}
