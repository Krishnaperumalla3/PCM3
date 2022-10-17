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

import com.pe.pcm.apiconnecct.APIProxyEndpointService;
import com.pe.pcm.apiconnecct.entity.APIProxyEndpointEntity;
import com.pe.pcm.apiconnect.APIProxyEndpointModel;
import com.pe.pcm.common.CommunityManagerNameModel;
import com.pe.pcm.common.CommunityManagerResponse;
import com.pe.pcm.properties.OAuth2Properties;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.pe.pcm.utils.CommonFunctions.OK;
import static javax.servlet.http.HttpServletResponse.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Kiran Reddy.
 */
@RestController
@RequestMapping(path = "pcm/api/proxy-endpoint")
@Api(tags = {"API Proxy Endpoint Resource"})
@ConditionalOnProperty(
        value = "cm.api-connect-enabled",
        havingValue = "true")
public class APIProxyEndpointResource {

    private final APIProxyEndpointService apiProxyEndpointService;

    @Autowired
    public APIProxyEndpointResource(APIProxyEndpointService apiProxyEndpointService) {
        this.apiProxyEndpointService = apiProxyEndpointService;
    }

    @ApiOperation(value = "Create Proxy Endpoint", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_NOT_ACCEPTABLE, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "proxy endpoint already exist")
    })
    @PostMapping
    public ResponseEntity<CommunityManagerResponse> create(@Validated @RequestBody APIProxyEndpointModel apiProxyEndpointModel) {
        apiProxyEndpointService.create(apiProxyEndpointModel);
        return ResponseEntity.ok(OK.apply("Proxy endpoint created successfully"));
    }

    @ApiOperation(value = "Update Proxy Endpoint", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_NOT_ACCEPTABLE, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "proxy endpoint already exist")
    })
    @PutMapping
    public ResponseEntity<CommunityManagerResponse> update(@Validated @RequestBody APIProxyEndpointModel apiProxyEndpointModel) {
        apiProxyEndpointService.update(apiProxyEndpointModel);
        return ResponseEntity.ok(OK.apply("Proxy endpoint updated successfully"));
    }

    @ApiOperation(value = "Get Proxy Endpoint", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_NOT_ACCEPTABLE, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "proxy endpoint already exist")
    })
    @GetMapping(path = "{pkId}")
    public ResponseEntity<APIProxyEndpointModel> get(@PathVariable String pkId) {
        return ResponseEntity.ok(apiProxyEndpointService.get(pkId));
    }

    @ApiOperation(value = "Delete Proxy Endpoint", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_NOT_ACCEPTABLE, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "proxy endpoint already exist")
    })
    @DeleteMapping(path = "{pkId}")
    public ResponseEntity<CommunityManagerResponse> delete(@PathVariable String pkId) {
        apiProxyEndpointService.delete(pkId);
        return ResponseEntity.ok(OK.apply("Proxy endpoint deleted successfully"));
    }

    @ApiOperation(value = "Search Endpoints", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "search", produces = APPLICATION_JSON_VALUE)
    public Page<APIProxyEndpointEntity> search(@RequestBody APIProxyEndpointModel apiProxyEndpointModel,
                                               @PageableDefault(sort = {"apiName"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return apiProxyEndpointService.search(apiProxyEndpointModel, pageable);
    }

    @ApiOperation(value = "Get Endpoints Map", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @GetMapping(path = "endpoints")
    public ResponseEntity<List<CommunityManagerNameModel>> getUniqueApiNames() {
        return ResponseEntity.ok(apiProxyEndpointService.getUniqueApiNames());
    }

    @ApiOperation(value = "Get OAuth2.0 Details", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @GetMapping(path = "get-oauth2-details")
    public ResponseEntity<OAuth2Properties> getOAuth2Details() {
        return ResponseEntity.ok(apiProxyEndpointService.getOAuth2Details());
    }

    @ApiOperation(value = "Get Method details of an API", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @GetMapping(path = "get-methods-by-apiName/{apiName}")
    public ResponseEntity<List<CommunityManagerNameModel>> getMethodsByAPIName(@PathVariable String apiName) {
        return ResponseEntity.ok(apiProxyEndpointService.getMethodsByAPIName(apiName));
    }

}
