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

package com.pe.pcm.apiconnecct.entity;

import com.pe.pcm.audit.Auditable;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Kiran Reddy.
 */
@Entity
@Table(name = "PETPE_API_HP_DATA")
public class APIHPDataEntity extends Auditable implements Serializable {
    @Id
    private String pkId;
    @Column(name = "api_id")
    private String apiId;
    private String apiConfigType;
    private String hpType;
    private String hpKey;
    private String hpValue;
    private String hpDescription;
    private String required;
    private String dynamicValue;

    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "api_id", insertable = false, updatable = false)
    private APIProxyEndpointEntity apiProxyEndpointEntity;*/

    public String getPkId() {
        return pkId;
    }

    public APIHPDataEntity setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getApiId() {
        return apiId;
    }

    public APIHPDataEntity setApiId(String apiId) {
        this.apiId = apiId;
        return this;
    }

    public String getApiConfigType() {
        return apiConfigType;
    }

    public APIHPDataEntity setApiConfigType(String apiConfigType) {
        this.apiConfigType = apiConfigType;
        return this;
    }

    public String getHpType() {
        return hpType;
    }

    public APIHPDataEntity setHpType(String hpType) {
        this.hpType = hpType;
        return this;
    }

    public String getHpKey() {
        return hpKey;
    }

    public APIHPDataEntity setHpKey(String hpKey) {
        this.hpKey = hpKey;
        return this;
    }

    public String getHpValue() {
        return hpValue;
    }

    public APIHPDataEntity setHpValue(String hpValue) {
        this.hpValue = hpValue;
        return this;
    }

    public String getHpDescription() {
        return hpDescription;
    }

    public APIHPDataEntity setHpDescription(String hpDescription) {
        this.hpDescription = hpDescription;
        return this;
    }

    /*public APIProxyEndpointEntity getApiProxyEndpointEntity() {
        return apiProxyEndpointEntity;
    }

    public APIHPDataEntity setApiProxyEndpointEntity(APIProxyEndpointEntity apiProxyEndpointEntity) {
        this.apiProxyEndpointEntity = apiProxyEndpointEntity;
        return this;
    }*/

    public String getRequired() {
        return required;
    }

    public APIHPDataEntity setRequired(String required) {
        this.required = required;
        return this;
    }

    public String getDynamicValue() {
        return dynamicValue;
    }

    public APIHPDataEntity setDynamicValue(String dynamicValue) {
        this.dynamicValue = dynamicValue;
        return this;
    }

    @Override
    public String toString() {
        return "APIHPDataEntity{" +
                "pkId='" + pkId + '\'' +
                ", apiId='" + apiId + '\'' +
                ", apiConfigType='" + apiConfigType + '\'' +
                ", hpType='" + hpType + '\'' +
                ", hpKey='" + hpKey + '\'' +
                ", hpValue='" + hpValue + '\'' +
                ", hpDescription='" + hpDescription + '\'' +
                ", required='" + required + '\'' +
                ", dynamicValue='" + dynamicValue + '\'' +
                '}';
    }
}
