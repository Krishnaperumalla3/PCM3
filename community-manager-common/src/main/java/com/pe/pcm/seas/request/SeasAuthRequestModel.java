/*
 *
 *  * Copyright (c) 2020 Pragma Edge Inc
 *  *
 *  * Licensed under the Pragma Edge Inc
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  * https://pragmaedge.com/licenseagreement
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.pe.pcm.seas.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * @author Kiran Reddy.
 */
@JacksonXmlRootElement(localName = "authRequest")
public class SeasAuthRequestModel {

    private String profile;
    private String userId;
    private String password;

    public String getProfile() {
        return profile;
    }

    public SeasAuthRequestModel setProfile(String profile) {
        this.profile = profile;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public SeasAuthRequestModel setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public SeasAuthRequestModel setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        return "SeasAuthRequestModel{" +
                "profile='" + profile + '\'' +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
