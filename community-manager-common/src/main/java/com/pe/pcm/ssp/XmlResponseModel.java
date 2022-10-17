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

package com.pe.pcm.ssp;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "XmlResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlResponseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private int httpCode;

    @XmlElement
    private String httpStatus;

    @XmlElement
    private String action;

    @XmlElement
    private String message;

    @XmlElement
    private String messageLevel;

    @XmlElement
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Object> objectsList;

    @XmlElement
    @JacksonXmlElementWrapper(useWrapping = false)
    private Entry results;

    public XmlResponseModel() {
    }

    public XmlResponseModel(int httpCode, String httpStatus, String action, String message, String messageLevel, List<Object> objectsList, Entry results) {
        this.httpCode = httpCode;
        this.httpStatus = httpStatus;
        this.action = action;
        this.message = message;
        this.messageLevel = messageLevel;
        this.objectsList = objectsList;
        this.results = results;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public XmlResponseModel setHttpCode(int httpCode) {
        this.httpCode = httpCode;
        return this;
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public XmlResponseModel setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }

    public String getAction() {
        return action;
    }

    public XmlResponseModel setAction(String action) {
        this.action = action;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public XmlResponseModel setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getMessageLevel() {
        return messageLevel;
    }

    public XmlResponseModel setMessageLevel(String messageLevel) {
        this.messageLevel = messageLevel;
        return this;
    }

    public Entry getResults() {
        return results;
    }

    public XmlResponseModel setResults(Entry results) {
        this.results = results;
        return this;
    }

    public List<Object> getObjectsList() {
        return objectsList;
    }

    public XmlResponseModel setObjectsList(List<Object> objectsList) {
        this.objectsList = objectsList;
        return this;
    }

    @Override
    public String toString() {
        return "XmlResponseModel{" +
                "httpCode=" + httpCode +
                ", httpStatus='" + httpStatus + '\'' +
                ", action='" + action + '\'' +
                ", message='" + message + '\'' +
                ", messageLevel='" + messageLevel + '\'' +
                ", objectsList=" + objectsList +
                ", results=" + results +
                '}';
    }
}
