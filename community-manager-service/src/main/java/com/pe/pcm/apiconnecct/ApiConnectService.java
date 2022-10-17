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

package com.pe.pcm.apiconnecct;

import com.pe.pcm.apiconnect.APIProxyEndpointModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Kiran Reddy.
 */
@Service
public class ApiConnectService {

    private final APIProxyEndpointService apiProxyEndpointService;
    private final ApiConnectClientService apiConnectClientService;

    @Autowired
    public ApiConnectService(APIProxyEndpointService apiProxyEndpointService, ApiConnectClientService apiConnectClientService) {
        this.apiProxyEndpointService = apiProxyEndpointService;
        this.apiConnectClientService = apiConnectClientService;
    }

    public String postAPIConnect(String apiName, String payload, HttpServletRequest httpServletRequest) {
        APIProxyEndpointModel apiProxyEndpointModel = apiProxyEndpointService.findByApiNameAndMethodName(apiName, "POST");
        Map<String, String> headers = Collections.list(httpServletRequest.getHeaderNames())
                .stream()
                .collect(Collectors.toMap(h -> h, httpServletRequest::getHeader));
        Map<String, String[]> params = httpServletRequest.getParameterMap();
        return apiConnectClientService.postAPIConnect(apiProxyEndpointModel, payload, params, headers);

    }

    public String putAPIConnect(String apiName, String payload, String id, HttpServletRequest httpServletRequest) {
        APIProxyEndpointModel apiProxyEndpointModel = apiProxyEndpointService.findByApiNameAndMethodName(apiName, "PUT");
        Map<String, String> headers = Collections.list(httpServletRequest.getHeaderNames())
                .stream()
                .collect(Collectors.toMap(h -> h, httpServletRequest::getHeader));
        Map<String, String[]> params = httpServletRequest.getParameterMap();
        return apiConnectClientService.putAPIConnect(apiProxyEndpointModel, payload, id, params, headers);
    }

    public String getAPIConnect(String apiName, HttpServletRequest httpServletRequest) {
        APIProxyEndpointModel apiProxyEndpointModel = apiProxyEndpointService.findByApiNameAndMethodName(apiName, "GET");
        Map<String, String> headers = Collections.list(httpServletRequest.getHeaderNames())
                .stream()
                .collect(Collectors.toMap(h -> h, httpServletRequest::getHeader));
        Map<String, String[]> params = httpServletRequest.getParameterMap();
        return apiConnectClientService.getApiConnect(apiProxyEndpointModel, params, headers);

    }

    public String deleteAPIConnect(String apiName, HttpServletRequest httpServletRequest) {
        APIProxyEndpointModel apiProxyEndpointModel = apiProxyEndpointService.findByApiNameAndMethodName(apiName, "DELETE");
        Map<String, String> headers = Collections.list(httpServletRequest.getHeaderNames())
                .stream()
                .collect(Collectors.toMap(h -> h, httpServletRequest::getHeader));
        Map<String, String[]> params = httpServletRequest.getParameterMap();
        return apiConnectClientService.deleteApiConnect(apiProxyEndpointModel, params, headers);
    }

}
