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
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kiran Reddy.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
public class APIConnectionDetailsModel implements Serializable {

    private String url;
    private String method;
    private APIAuthDataModel apiAuthData;
    private List<APIHPDataModel> apiHeaderDataList;
    private List<APIHPDataModel> apiParamDataList;

    public String getUrl() {
        return url;
    }

    public APIConnectionDetailsModel setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getMethod() {
        return method;
    }

    public APIConnectionDetailsModel setMethod(String method) {
        this.method = method;
        return this;
    }

    public APIAuthDataModel getApiAuthData() {
        if (apiAuthData == null) {
            apiAuthData = new APIAuthDataModel();
        }
        return apiAuthData;
    }

    public APIConnectionDetailsModel setApiAuthData(APIAuthDataModel apiAuthData) {
        this.apiAuthData = apiAuthData;
        return this;
    }

    public List<APIHPDataModel> getApiHeaderDataList() {
        if (apiHeaderDataList == null) {
            apiHeaderDataList = new ArrayList<>();
        }
        return apiHeaderDataList;
    }

    public APIConnectionDetailsModel setApiHeaderDataList(List<APIHPDataModel> apiHeaderDataList) {
        this.apiHeaderDataList = apiHeaderDataList;
        return this;
    }

    public List<APIHPDataModel> getApiParamDataList() {
        if (apiParamDataList == null) {
            apiParamDataList = new ArrayList<>();
        }
        return apiParamDataList;
    }

    public APIConnectionDetailsModel setApiParamDataList(List<APIHPDataModel> apiParamDataList) {
        this.apiParamDataList = apiParamDataList;
        return this;
    }
}
