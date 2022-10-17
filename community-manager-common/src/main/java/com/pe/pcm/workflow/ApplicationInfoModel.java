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

package com.pe.pcm.workflow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.io.Serializable;

/**
 * @author Chenchu Kiran.
 */
@JacksonXmlRootElement(localName = "APPLICATION_INFO")
@JsonPropertyOrder(value = {"protocol", "application"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationInfoModel<T extends Serializable> implements Serializable {

    private String protocol;

    private T application;

    public ApplicationInfoModel(T application) {
        this.application = application;
    }

    public ApplicationInfoModel() {
    }

    @JacksonXmlProperty(localName = "PROTOCOL")
    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @JacksonXmlProperty(localName = "APPLICATION")
    public T getApplication() {
        return application;
    }

    public ApplicationInfoModel<T> setApplication(T application) {
        this.application = application;
        return this;
    }

}
