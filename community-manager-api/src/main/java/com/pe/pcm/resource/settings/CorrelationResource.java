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

import com.pe.pcm.correlation.CorrelationModel;
import com.pe.pcm.settings.CorrelationService;
import com.pe.pcm.settings.entity.CorrelationEntity;
import com.pe.pcm.common.CommunityManagerResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.pe.pcm.constants.AuthoritiesConstants.*;
import static javax.servlet.http.HttpServletResponse.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Kiran Reddy.
 */
@RestController
@RequestMapping(path = "pcm/correlation")
@Api(tags = {"Correlation Resource"})
public class CorrelationResource {

    private final CorrelationService correlationService;

    @Autowired
    public CorrelationResource(CorrelationService correlationService) {
        this.correlationService = correlationService;
    }

    @ApiOperation(value = "Get Correlations", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_NOT_FOUND, message = "no record found")
    })
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CorrelationEntity> getCorrelations() {
        return ResponseEntity.ok(correlationService.getCorrelations());
    }

    @ApiOperation(value = "Correlations Create/Update", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_AD)
    @PutMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityManagerResponse> create(@RequestBody CorrelationModel crlnamesmodel) {
        correlationService.update(crlnamesmodel);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Correlation create/update successfully"));
    }

}
