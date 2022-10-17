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

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;

/**
 * @author Kiran Reddy.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
public class APIHPDataModel implements Serializable {

    private String key;
    private String value;
    private String description;
    private Boolean required;
    private Boolean dynamicValue;

    public String getKey() {
        return key;
    }

    public APIHPDataModel setKey(String key) {
        this.key = key;
        return this;
    }

    public String getValue() {
        return value;
    }

    public APIHPDataModel setValue(String value) {
        this.value = value;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public APIHPDataModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public Boolean getRequired() {
        return required;
    }

    public APIHPDataModel setRequired(Boolean required) {
        this.required = required;
        return this;
    }

    public Boolean getDynamicValue() {
        return dynamicValue;
    }

    public APIHPDataModel setDynamicValue(Boolean dynamicValue) {
        this.dynamicValue = dynamicValue;
        return this;
    }

    @Override
    public String toString() {
        return "APIHPDataModel{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", description='" + description + '\'' +
                ", required=" + required +
                ", dynamicValue=" + dynamicValue +
                '}';
    }
}
