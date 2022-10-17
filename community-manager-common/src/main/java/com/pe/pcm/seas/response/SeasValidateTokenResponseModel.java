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
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kiran Reddy.
 */
@JacksonXmlRootElement(localName = "ssoValidateTokenResponse")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SeasValidateTokenResponseModel {

    private String detailResponseCode;
    private Boolean expired;
    private String subject;
    private Boolean validated;

    @JacksonXmlElementWrapper(localName = "logs")
    @JacksonXmlProperty(localName = "log")
    private List<String> logs;

    @JacksonXmlElementWrapper(localName = "applicationOutputs")
    @JacksonXmlProperty(localName = "property")
    private List<SeasPropertyModel> seasPropertyModels;

    public String getDetailResponseCode() {
        return detailResponseCode;
    }

    public SeasValidateTokenResponseModel setDetailResponseCode(String detailResponseCode) {
        this.detailResponseCode = detailResponseCode;
        return this;
    }

    public Boolean getExpired() {
        return expired;
    }

    public SeasValidateTokenResponseModel setExpired(Boolean expired) {
        this.expired = expired;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public SeasValidateTokenResponseModel setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public Boolean getValidated() {
        return validated;
    }

    public SeasValidateTokenResponseModel setValidated(Boolean validated) {
        this.validated = validated;
        return this;
    }

    public List<String> getLogs() {
        return logs;
    }

    public SeasValidateTokenResponseModel setLogs(List<String> logs) {
        this.logs = logs;
        return this;
    }

    public List<SeasPropertyModel> getSeasPropertyModels() {
        if (seasPropertyModels == null) {
            seasPropertyModels = new ArrayList<>();
        }
        return seasPropertyModels;
    }

    public SeasValidateTokenResponseModel setSeasPropertyModels(List<SeasPropertyModel> seasPropertyModels) {
        this.seasPropertyModels = seasPropertyModels;
        return this;
    }

    @Override
    public String toString() {
        return "SeasValidateTokenResponseModel{" +
                "detailResponseCode='" + detailResponseCode + '\'' +
                ", expired=" + expired +
                ", subject='" + subject + '\'' +
                ", validated=" + validated +
                ", logs=" + logs +
                ", seasPropertyModels=" + seasPropertyModels +
                '}';
    }
}
