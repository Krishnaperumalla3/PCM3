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
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PemAccountExpiryModelTemp implements Serializable {

    private List<String> profileName;
    private String startDate;
    private String endDate;

    public List<String> getProfileName() {
        return profileName;
    }

    public PemAccountExpiryModelTemp setProfileName(List<String> profileName) {
        this.profileName = profileName;
        return this;
    }

    public String getStartDate() {
        return startDate;
    }

    public PemAccountExpiryModelTemp setStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public String getEndDate() {
        return endDate;
    }

    public PemAccountExpiryModelTemp setEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }
}
