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

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * @author Kiran Reddy.
 */
@JacksonXmlRootElement(localName = "ssoValidateTokenRequest")
public class SeasValidateTokenRequestModel {
    private String subject;
    @JacksonXmlCData
    private String token;
    private String refreshIfAboutToExpire;

    public String getSubject() {
        return subject;
    }

    public SeasValidateTokenRequestModel setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getToken() {
        return token;
    }

    public SeasValidateTokenRequestModel setToken(String token) {
        this.token = token;
        return this;
    }

    public String getRefreshIfAboutToExpire() {
        return refreshIfAboutToExpire;
    }

    public SeasValidateTokenRequestModel setRefreshIfAboutToExpire(String refreshIfAboutToExpire) {
        this.refreshIfAboutToExpire = refreshIfAboutToExpire;
        return this;
    }
}
