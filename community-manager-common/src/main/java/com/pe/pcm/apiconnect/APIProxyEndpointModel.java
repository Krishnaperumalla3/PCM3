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
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kiran Reddy.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class APIProxyEndpointModel implements Serializable {

    private String pkId;
    private String apiName;

    private String proxyUrl;
    private String proxyWebMethod;
    private String poolingInterval;
    private APIAuthDataModel proxyApiAuthData;

    private String serverUrl;
    private String serverWebMethod;
    private APIAuthDataModel serverApiAuthData;

    private List<APIHPDataModel> apiHeaderDataList;
    private List<APIHPDataModel> apiParamDataList;

    private String body;

    private String apiType;

    public String getPkId() {
        return pkId;
    }

    public APIProxyEndpointModel setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getApiName() {
        return apiName;
    }

    public APIProxyEndpointModel setApiName(String apiName) {
        this.apiName = apiName;
        return this;
    }

    public String getProxyUrl() {
        return proxyUrl;
    }

    public APIProxyEndpointModel setProxyUrl(String proxyUrl) {
        this.proxyUrl = proxyUrl;
        return this;
    }

    public String getProxyWebMethod() {
        return proxyWebMethod;
    }

    public APIProxyEndpointModel setProxyWebMethod(String proxyWebMethod) {
        this.proxyWebMethod = proxyWebMethod;
        return this;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public APIProxyEndpointModel setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
        return this;
    }

    public String getServerWebMethod() {
        return serverWebMethod;
    }

    public APIProxyEndpointModel setServerWebMethod(String serverWebMethod) {
        this.serverWebMethod = serverWebMethod;
        return this;
    }

    public APIAuthDataModel getProxyApiAuthData() {
        return proxyApiAuthData;
    }

    public APIProxyEndpointModel setProxyApiAuthData(APIAuthDataModel proxyApiAuthData) {
        this.proxyApiAuthData = proxyApiAuthData;
        return this;
    }

    public APIAuthDataModel getServerApiAuthData() {
        return serverApiAuthData;
    }

    public APIProxyEndpointModel setServerApiAuthData(APIAuthDataModel serverApiAuthData) {
        this.serverApiAuthData = serverApiAuthData;
        return this;
    }

    public List<APIHPDataModel> getApiHeaderDataList() {
        if (apiHeaderDataList == null) {
            return new ArrayList<>();
        }
        return apiHeaderDataList;
    }

    public APIProxyEndpointModel setApiHeaderDataList(List<APIHPDataModel> apiHeaderDataList) {
        this.apiHeaderDataList = apiHeaderDataList;
        return this;
    }

    public List<APIHPDataModel> getApiParamDataList() {
        if (apiParamDataList == null) {
            return new ArrayList<>();
        }
        return apiParamDataList;
    }

    public APIProxyEndpointModel setApiParamDataList(List<APIHPDataModel> apiParamDataList) {
        this.apiParamDataList = apiParamDataList;
        return this;
    }

    public String getBody() {
        return body;
    }

    public APIProxyEndpointModel setBody(String body) {
        this.body = body;
        return this;
    }

    public String getApiType() {
        return apiType;
    }

    public APIProxyEndpointModel setApiType(String apiType) {
        this.apiType = apiType;
        return this;
    }

    public String getPoolingInterval() {
        return poolingInterval;
    }

    public APIProxyEndpointModel setPoolingInterval(String poolingInterval) {
        this.poolingInterval = poolingInterval;
        return this;
    }
}
