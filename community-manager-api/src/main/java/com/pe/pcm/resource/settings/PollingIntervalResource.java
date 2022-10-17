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

package com.pe.pcm.resource.settings;

import com.pe.pcm.common.CommunityManagerKeyValueModel;
import com.pe.pcm.common.CommunityManagerResponse;
import com.pe.pcm.poolinginterval.PollingIntervalService;
import com.pe.pcm.poolinginterval.PoolingIntervalModel;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.pe.pcm.constants.AuthoritiesConstants.SA_AD;
import static com.pe.pcm.constants.AuthoritiesConstants.SA_AD_OB_BA_BU;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Kiran Reddy.
 */
@RestController
@RequestMapping(value = "pcm/pooling-interval", produces = APPLICATION_JSON_VALUE)
@Api(tags = {"PollingInterval"}, description = "PollingInterval Resource")
public class PollingIntervalResource {

    private final PollingIntervalService pollingIntervalService;

    @Autowired
    public PollingIntervalResource(PollingIntervalService pollingIntervalService) {
        this.pollingIntervalService = pollingIntervalService;
    }

    @ApiOperation(value = "Create/Update PollingIntervals", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PutMapping
    @PreAuthorize(SA_AD)
    public ResponseEntity<CommunityManagerResponse> save(@Validated @RequestBody List<PoolingIntervalModel> poolingIntervalModelList) {
        pollingIntervalService.save(poolingIntervalModelList);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Polling Intervals saved successfully"));
    }

    @ApiOperation(value = "Get PollingIntervals", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_NOT_FOUND, message = "poolingIntervals not found")
    })
    @GetMapping
    @PreAuthorize(SA_AD_OB_BA_BU)
    public ResponseEntity<List<PoolingIntervalModel>> get() {
        return ResponseEntity.ok(pollingIntervalService.get());
    }

    @ApiOperation(value = "Get PollingIntervals list", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_NOT_FOUND, message = "pollingIntervals not found")
    })
    @PreAuthorize(SA_AD_OB_BA_BU)
    @GetMapping(path = "list")
    public ResponseEntity<List<CommunityManagerKeyValueModel>> getList() {
        return ResponseEntity.ok(pollingIntervalService.getList());
    }

}
