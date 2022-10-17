/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc.
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

package com.pe.pcm.resource.partner.sterling;

import com.pe.pcm.common.CommunityManagerResponse;
import com.pe.pcm.partner.sterling.SterlingAs2PartnerService;
import com.pe.pcm.protocol.As2Model;
import com.pe.pcm.protocol.As2RelationMapModel;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.pe.pcm.constants.AuthoritiesConstants.SA_OB_BA;
import static com.pe.pcm.constants.AuthoritiesConstants.SA_OB_BA_BU;
import static com.pe.pcm.utils.CommonFunctions.OK;
import static com.pe.pcm.utils.PCMConstants.*;
import static javax.servlet.http.HttpServletResponse.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Kiran Reddy.
 */
@RestController
@RequestMapping("pcm/si/partner/remote-as2")
@Api(tags = {"Partner Profile with IBM Sterling B2B Integrator AS2"}, description = "IBM Sterling B2B Integrator AS2 Partner Resource")
public class SterlingAs2PartnerResource {

    private final SterlingAs2PartnerService sterlingAs2PartnerService;

    @Autowired
    public SterlingAs2PartnerResource(SterlingAs2PartnerService sterlingAs2PartnerService) {
        this.sterlingAs2PartnerService = sterlingAs2PartnerService;
    }

    @ApiOperation(value = "Create AS2 Partner", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({@ApiResponse(code = SC_OK, message = "Success"),
            @ApiResponse(code = SC_UNAUTHORIZED, message = "UnAuthorized Request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exists")})
    @PostMapping()
    @PreAuthorize(SA_OB_BA)
    public ResponseEntity<CommunityManagerResponse> create(@Validated @RequestBody As2Model as2Model) {
        sterlingAs2PartnerService.create(as2Model, false);
        return ResponseEntity.ok(OK.apply(PARTNER_CREATE));
    }

    @ApiOperation(value = "Update AS2 Partner", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({@ApiResponse(code = SC_OK, message = "Success"),
            @ApiResponse(code = SC_UNAUTHORIZED, message = "UnAuthorized Request"),
            @ApiResponse(code = SC_NOT_FOUND, message = "No Record Found")})
    @PutMapping()
    @PreAuthorize(SA_OB_BA)
    public ResponseEntity<CommunityManagerResponse> update(@Validated @RequestBody As2Model as2Model) {
        sterlingAs2PartnerService.update(as2Model);
        return ResponseEntity.ok(OK.apply(PARTNER_UPDATE));
    }

    @ApiOperation(value = "Get AS2 Partner", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses(value = {@ApiResponse(code = SC_OK, message = "Success"),
            @ApiResponse(code = SC_UNAUTHORIZED, message = "UnAuthorized Request"),
            @ApiResponse(code = SC_NOT_FOUND, message = "No Record Found")})
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "{pkId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<As2Model> get(@PathVariable String pkId) {
        return ResponseEntity.ok(sterlingAs2PartnerService.get(pkId));
    }

    @ApiOperation(value = "Delete AS2 Partner", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses(value = {@ApiResponse(code = SC_OK, message = "Success"),
            @ApiResponse(code = SC_UNAUTHORIZED, message = "UnAuthorized Request"),
            @ApiResponse(code = SC_NOT_FOUND, message = "No Record Found")})
    @PreAuthorize(SA_OB_BA)
    @DeleteMapping(path = "{pkId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityManagerResponse> delete(@PathVariable String pkId) {
        sterlingAs2PartnerService.delete(pkId);
        return ResponseEntity.ok(OK.apply(PARTNER_DELETE));
    }

    @ApiOperation(value = "AS2 Organization Profile And Partner Profile Mapping", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({@ApiResponse(code = SC_OK, message = "Success"),
            @ApiResponse(code = SC_UNAUTHORIZED, message = "UnAuthorized Request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exists")})
    @PreAuthorize(SA_OB_BA)
    @PostMapping(path = "org-profile/mapping")
    public ResponseEntity<CommunityManagerResponse> mapping(@Validated @RequestBody As2RelationMapModel as2RelationMapModel) {
        sterlingAs2PartnerService.createMapping(as2RelationMapModel);
        return ResponseEntity.ok(OK.apply(PARTNER_CREATE));
    }

}
