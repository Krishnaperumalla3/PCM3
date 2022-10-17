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

package com.pe.pcm.resource.envelop;

import com.pe.pcm.envelope.EnvelopeModel;
import com.pe.pcm.envelope.EnvelopeService;
import com.pe.pcm.envelope.entity.EdiPropertiesActivityHistoryEntity;
import com.pe.pcm.envelope.entity.EdiPropertiesEntity;
import com.pe.pcm.common.CommunityManagerResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.pe.pcm.constants.AuthoritiesConstants.SA_OB_BA;
import static com.pe.pcm.constants.AuthoritiesConstants.SA_OB_BA_BU;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Shameer.
 */
@RestController
@RequestMapping(path = "pcm/envelope")
@Api(tags = {"Envelope Resource"})
public class EnvelopeResource {

    private final EnvelopeService envelopeservice;

    @Autowired
    public EnvelopeResource(EnvelopeService envelopeservice) {
        this.envelopeservice = envelopeservice;
    }

    @ApiOperation(value = "Create Envelop Profile", authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA)
    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityManagerResponse> create(@Validated @RequestBody EnvelopeModel ediModel) {
        envelopeservice.create(ediModel);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Envelop created successfully"));
    }

    @ApiOperation(value = "Update Envelop Profile", authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA)
    @PutMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityManagerResponse> update(@Validated @RequestBody EnvelopeModel ediModel) {
        envelopeservice.update(ediModel);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Envelope updated successfully"));
    }

    @ApiOperation(value = "Get Envelop Profile", authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "{pkId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<EnvelopeModel> get(@Validated @PathVariable String pkId) {
        return ResponseEntity.ok(envelopeservice.get(pkId));
    }

    @ApiOperation(value = "Get Envelop Activity", authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @PostMapping(path = "activity/{ediRefId}", produces = APPLICATION_JSON_VALUE)
    public Page<EdiPropertiesActivityHistoryEntity> getActivity(@PathVariable String ediRefId, @PageableDefault(size = 10, page = 0, sort = {"activityDt"}, direction = Direction.DESC) Pageable pageable) {
        return envelopeservice.getHistory(ediRefId,pageable);
    }

    @ApiOperation(value = "Delete Envelop Profile", authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA)
    @DeleteMapping(path = "{pkId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityManagerResponse> delete(@Validated @PathVariable String pkId) {
        envelopeservice.delete(pkId);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Envelop deleted successfully"));
    }

    @ApiOperation(value = "Search Envelop Profile", authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @PostMapping(path = "search", produces = APPLICATION_JSON_VALUE)
    public Page<EdiPropertiesEntity> search(@RequestBody EnvelopeModel ediModel, @PageableDefault(size = 10, page = 0, sort = {"partnername"}, direction = Direction.DESC) Pageable pageable) {
        return envelopeservice.search(ediModel, pageable);
    }


}
