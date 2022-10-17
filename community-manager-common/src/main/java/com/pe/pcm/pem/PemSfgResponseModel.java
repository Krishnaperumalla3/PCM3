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

package com.pe.pcm.pem;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * @author Chenchu Kiran.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PemSfgResponseModel implements Serializable {

    private String profileName;
    private String profileCode;
    private String userAccount;

    public String getProfileName() {
        return profileName;
    }

    public PemSfgResponseModel setProfileName(String profileName) {
        this.profileName = profileName;
        return this;
    }

    public String getProfileCode() {
        return profileCode;
    }

    public PemSfgResponseModel setProfileCode(String profileCode) {
        this.profileCode = profileCode;
        return this;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public PemSfgResponseModel setUserAccount(String userAccount) {
        this.userAccount = userAccount;
        return this;
    }
}
