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

package com.pe.pcm.seas.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kiran Reddy.
 */
@JacksonXmlRootElement(localName = "ssoAuthResponse")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SeasSsoAuthResponseModel {

    private Boolean authenticated;
    private String detailResponseCode;

    @JacksonXmlCData
    private String token;

    @JacksonXmlElementWrapper(localName = "logs")
    @JacksonXmlProperty(localName = "log")
    private List<String> logs;

    @JacksonXmlElementWrapper(localName = "applicationOutputs")
    @JacksonXmlProperty(localName = "property")
    private List<SeasPropertyModel> seasPropertyModels;

    public Boolean getAuthenticated() {
        return authenticated;
    }

    public SeasSsoAuthResponseModel setAuthenticated(Boolean authenticated) {
        this.authenticated = authenticated;
        return this;
    }

    public String getDetailResponseCode() {
        return detailResponseCode;
    }

    public SeasSsoAuthResponseModel setDetailResponseCode(String detailResponseCode) {
        this.detailResponseCode = detailResponseCode;
        return this;
    }

    public String getToken() {
        return token;
    }

    public SeasSsoAuthResponseModel setToken(String token) {
        this.token = token;
        return this;
    }

    public List<String> getLogs() {
        return logs;
    }

    public SeasSsoAuthResponseModel setLogs(List<String> logs) {
        this.logs = logs;
        return this;
    }

    public List<SeasPropertyModel> getSeasPropertyModels() {
        if (seasPropertyModels == null) {
            seasPropertyModels = new ArrayList<>();
        }
        return seasPropertyModels;
    }

    public SeasSsoAuthResponseModel setSeasPropertyModels(List<SeasPropertyModel> seasPropertyModels) {
        this.seasPropertyModels = seasPropertyModels;
        return this;
    }

    @Override
    public String toString() {
        return "SeasSsoAuthResponseModel{" +
                "authenticated=" + authenticated +
                ", detailResponseCode='" + detailResponseCode + '\'' +
                ", token='" + token + '\'' +
                ", logs=" + logs +
                '}';
    }
}
