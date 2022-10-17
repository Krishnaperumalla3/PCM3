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
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kiran Reddy.
 */
@Entity
@Table(name = "PETPE_API_PROXY_ENDPOINT")
public class APIProxyEndpointEntity extends Auditable  implements Serializable {

    @Id
    @Column(name = "pk_id")
    private String pkId;
    private String apiName;
    private String methodName;
    private String proxyUrl;
    private String serverUrl;
    private String reqPayloadSpa;
    private String apiType;
    private String poolingIntervalMins;

    /*@Fetch(value = FetchMode.SUBSELECT)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "apiProxyEndpointEntity")
    private List<APIAuthDataEntity> apiAuthDataEntities;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "apiProxyEndpointEntity")
    private List<APIHPDataEntity> apiHpDataEntities = new ArrayList<>();*/


    public String getPkId() {
        return pkId;
    }

    public APIProxyEndpointEntity setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getApiName() {
        return apiName;
    }

    public APIProxyEndpointEntity setApiName(String apiName) {
        this.apiName = apiName;
        return this;
    }

    public String getMethodName() {
        return methodName;
    }

    public APIProxyEndpointEntity setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public String getProxyUrl() {
        return proxyUrl;
    }

    public APIProxyEndpointEntity setProxyUrl(String proxyUrl) {
        this.proxyUrl = proxyUrl;
        return this;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public APIProxyEndpointEntity setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
        return this;
    }

    public String getReqPayloadSpa() {
        return reqPayloadSpa;
    }

    public APIProxyEndpointEntity setReqPayloadSpa(String reqPayloadSpa) {
        this.reqPayloadSpa = reqPayloadSpa;
        return this;
    }

    public String getApiType() {
        return apiType;
    }

    public APIProxyEndpointEntity setApiType(String apiType) {
        this.apiType = apiType;
        return this;
    }

    public String getPoolingIntervalMins() {
        return poolingIntervalMins;
    }

    public APIProxyEndpointEntity setPoolingIntervalMins(String poolingIntervalMins) {
        this.poolingIntervalMins = poolingIntervalMins;
        return this;
    }
}
