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

package com.pe.pcm.login;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SMLoginModel {

    private Boolean smLogin;
    private String activeProfile;

    public SMLoginModel(Boolean smLogin, String activeProfile) {
        this.smLogin = smLogin;
        this.activeProfile = activeProfile;
    }

    public String getActiveProfile() {
        return activeProfile;
    }

    public SMLoginModel setActiveProfile(String activeProfile) {
        this.activeProfile = activeProfile;
        return this;
    }

    public Boolean getSMLogin() {
        return smLogin;
    }

    public SMLoginModel setSMLogin(Boolean smLogin) {
        this.smLogin = smLogin;
        return this;
    }

}
