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

package com.pe.pcm.sterling.dto;

import java.io.Serializable;

/**
 * @author Chenchu Kiran.
 */
public class As2ProfileDTO implements Serializable {
    private String profileId;
    private String isOrg;
    private String httpClientAdapter;

    public String getProfileId() {
        return profileId;
    }

    public As2ProfileDTO setProfileId(String profileId) {
        this.profileId = profileId;
        return this;
    }

    public String getIsOrg() {
        return isOrg;
    }

    public As2ProfileDTO setIsOrg(String isOrg) {
        this.isOrg = isOrg;
        return this;
    }

    public String getHttpClientAdapter() {
        return httpClientAdapter;
    }

    public As2ProfileDTO setHttpClientAdapter(String httpClientAdapter) {
        this.httpClientAdapter = httpClientAdapter;
        return this;
    }
}
