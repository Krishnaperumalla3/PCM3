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

package com.pe.pcm.seas;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.pe.pcm.seas.request.SeasAuthRequestModel;
import com.pe.pcm.seas.request.SeasSsoAuthRequestModel;
import com.pe.pcm.seas.request.SeasValidateTokenRequestModel;
import com.pe.pcm.seas.response.SeasAuthResponseModel;
import com.pe.pcm.seas.response.SeasSsoAuthResponseModel;
import com.pe.pcm.seas.response.SeasValidateTokenResponseModel;

/**
 * @author Kiran Reddy.
 */
@JacksonXmlRootElement(localName = "idmbDoc")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SeasRequestResponseModel {

    @JacksonXmlProperty(isAttribute = true)
    private String version;

    @JacksonXmlProperty(localName = "ssoAuthRequest")
    private SeasSsoAuthRequestModel seasSsoAuthRequestModel;

    @JacksonXmlProperty(localName = "ssoAuthResponse")
    private SeasSsoAuthResponseModel seasSsoAuthResponseModel;

    @JacksonXmlProperty(localName = "ssoValidateTokenRequest")
    private SeasValidateTokenRequestModel seasValidateTokenRequestModel;

    @JacksonXmlProperty(localName = "ssoValidateTokenResponse")
    private SeasValidateTokenResponseModel seasValidateTokenResponseModel;

    @JacksonXmlProperty(localName = "authRequest")
    private SeasAuthRequestModel seasAuthRequestModel;

    @JacksonXmlProperty(localName = "authResponse")
    private SeasAuthResponseModel seasAuthResponseModel;


    public String getVersion() {
        return version;
    }

    public SeasRequestResponseModel setVersion(String version) {
        this.version = version;
        return this;
    }

    public SeasSsoAuthRequestModel getSeasSsoAuthRequestModel() {
        return seasSsoAuthRequestModel;
    }

    public SeasRequestResponseModel setSeasSsoAuthRequestModel(SeasSsoAuthRequestModel seasSsoAuthRequestModel) {
        this.seasSsoAuthRequestModel = seasSsoAuthRequestModel;
        return this;
    }

    public SeasSsoAuthResponseModel getSeasSsoAuthResponseModel() {
        return seasSsoAuthResponseModel;
    }

    public SeasRequestResponseModel setSeasSsoAuthResponseModel(SeasSsoAuthResponseModel seasSsoAuthResponseModel) {
        this.seasSsoAuthResponseModel = seasSsoAuthResponseModel;
        return this;
    }

    public SeasValidateTokenRequestModel getSeasValidateTokenRequestModel() {
        return seasValidateTokenRequestModel;
    }

    public SeasRequestResponseModel setSeasValidateTokenRequestModel(SeasValidateTokenRequestModel seasValidateTokenRequestModel) {
        this.seasValidateTokenRequestModel = seasValidateTokenRequestModel;
        return this;
    }

    public SeasValidateTokenResponseModel getSeasValidateTokenResponseModel() {
        return seasValidateTokenResponseModel;
    }

    public SeasRequestResponseModel setSeasValidateTokenResponseModel(SeasValidateTokenResponseModel seasValidateTokenResponseModel) {
        this.seasValidateTokenResponseModel = seasValidateTokenResponseModel;
        return this;
    }

    public SeasAuthRequestModel getSeasAuthRequestModel() {
        return seasAuthRequestModel;
    }

    public SeasRequestResponseModel setSeasAuthRequestModel(SeasAuthRequestModel seasAuthRequestModel) {
        this.seasAuthRequestModel = seasAuthRequestModel;
        return this;
    }

    public SeasAuthResponseModel getSeasAuthResponseModel() {
        return seasAuthResponseModel;
    }

    public SeasRequestResponseModel setSeasAuthResponseModel(SeasAuthResponseModel seasAuthResponseModel) {
        this.seasAuthResponseModel = seasAuthResponseModel;
        return this;
    }

    @Override
    public String toString() {
        return "SeasRequestResponseModel{" +
                "version='" + version + '\'' +
                ", seasSsoAuthRequestModel=" + seasSsoAuthRequestModel +
                ", seasSsoAuthResponseModel=" + seasSsoAuthResponseModel +
                ", seasValidateTokenRequestModel=" + seasValidateTokenRequestModel +
                ", seasValidateTokenResponseModel=" + seasValidateTokenResponseModel +
                ", seasAuthRequestModel=" + seasAuthRequestModel +
                ", seasAuthResponseModel=" + seasAuthResponseModel +
                '}';
    }
}
