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

package com.pe.pcm.resource.apiconnecct;

import com.pe.pcm.apiconnecct.ApiConnectService;
import com.pe.pcm.security.apiconnectauth.ApiConnectAuthFilter;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static javax.servlet.http.HttpServletResponse.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Kiran Reddy.
 */
@RestController
@RequestMapping(path = "restapi")
@Api(tags = {"API Connect Resource"})
@ConditionalOnProperty(
        value = "cm.api-connect-enabled",
        havingValue = "true")
public class ApiConnectResource {

    private final ApiConnectService apiConnectService;
    private final ApiConnectAuthFilter apiConnectAuthFilter;

    @Autowired
    public ApiConnectResource(ApiConnectService apiConnectService,
                              ApiConnectAuthFilter apiConnectAuthFilter) {
        this.apiConnectService = apiConnectService;
        this.apiConnectAuthFilter = apiConnectAuthFilter;
    }

    @ApiOperation(
            value = "API Connect for POST",
            authorizations = {
                    @Authorization(value = "apiKey"),
                    @Authorization(value = "basicAuth")
            }
    )
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "method not implemented")
    })
    @PostMapping(path = "{apiName}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postAPIConnect(@PathVariable String apiName, @RequestBody String payload,
                                                 HttpServletRequest httpServletRequest) {
        apiConnectAuthFilter.authenticate(httpServletRequest, apiName, "POST");
        return ResponseEntity.ok(apiConnectService.postAPIConnect(apiName, payload, httpServletRequest));
    }

    @ApiOperation(value = "API Connect for PUT", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "API already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "method not implemented")
    })
    @PutMapping(path = "{apiName}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> putAPIConnect(@PathVariable String apiName, @RequestBody String payload, String id, HttpServletRequest httpServletRequest) {
        apiConnectAuthFilter.authenticate(httpServletRequest, apiName, "PUT");
        return ResponseEntity.ok(apiConnectService.putAPIConnect(apiName, payload, id, httpServletRequest));
    }

    @ApiOperation(value = "API Connect for GET", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "method not implemented")
    })
    @GetMapping(path = "{apiName}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAPIConnect(@PathVariable String apiName, HttpServletRequest httpServletRequest) {
        apiConnectAuthFilter.authenticate(httpServletRequest, apiName, "GET");
        return ResponseEntity.ok(apiConnectService.getAPIConnect(apiName, httpServletRequest));
    }

    @ApiOperation(value = "API Connect for DELETE", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "method not implemented")
    })
    @DeleteMapping(path = "{apiName}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteAPIConnect(@PathVariable String apiName, HttpServletRequest httpServletRequest) {
        apiConnectAuthFilter.authenticate(httpServletRequest, apiName, "DELETE");
        return ResponseEntity.ok(apiConnectService.deleteAPIConnect(apiName, httpServletRequest));
    }

}
