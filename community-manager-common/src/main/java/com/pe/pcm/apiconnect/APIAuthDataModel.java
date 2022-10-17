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

package com.pe.pcm.apiconnect;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;

/**
 * @author Kiran Reddy.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class APIAuthDataModel implements Serializable {

    private String authType;
    private BasicAuthModel basicAuth;
    private TokenAuthModel tokenAuth;
    private Oauth2AuthModel oAuth2Auth;

    public String getAuthType() {
        return authType;
    }

    public APIAuthDataModel setAuthType(String authType) {
        this.authType = authType;
        return this;
    }


    public TokenAuthModel getTokenAuth() {
        return tokenAuth;
    }

    public APIAuthDataModel setTokenAuth(TokenAuthModel tokenAuth) {
        this.tokenAuth = tokenAuth;
        return this;
    }

    public BasicAuthModel getBasicAuth() {
        return basicAuth;
    }

    public APIAuthDataModel setBasicAuth(BasicAuthModel basicAuth) {
        this.basicAuth = basicAuth;
        return this;
    }

    public Oauth2AuthModel getoAuth2Auth() {
        return oAuth2Auth;
    }

    public APIAuthDataModel setoAuth2Auth(Oauth2AuthModel oAuth2Auth) {
        this.oAuth2Auth = oAuth2Auth;
        return this;
    }
}
