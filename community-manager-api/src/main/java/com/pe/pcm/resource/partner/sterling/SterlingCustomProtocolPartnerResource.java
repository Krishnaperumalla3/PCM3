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

package com.pe.pcm.resource.partner.sterling;

import com.pe.pcm.common.CommunityManagerResponse;
import com.pe.pcm.partner.sterling.SterlingCustomProtocolPartnerService;
import com.pe.pcm.sterling.profile.SterlingProfilesModel;
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
@RequestMapping(value = "pcm/si/partner/custom-protocol", produces = APPLICATION_JSON_VALUE)
@Api(tags = {"Partner Profile With Sterling File GateWay Custom Protocol Partner"}, description = "Sterling File GateWay Custom Protocol Partner Resource")
public class SterlingCustomProtocolPartnerResource {

    private final SterlingCustomProtocolPartnerService sterlingCustomProtocolPartnerService;

    @Autowired
    public SterlingCustomProtocolPartnerResource(SterlingCustomProtocolPartnerService sterlingCustomProtocolPartnerService) {
        this.sterlingCustomProtocolPartnerService = sterlingCustomProtocolPartnerService;
    }

    @ApiOperation(value = "Create Custom-Protocol Partner Profile in Sterling", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PostMapping
    @PreAuthorize(SA_OB_BA)
    public ResponseEntity<CommunityManagerResponse> create(@Validated @RequestBody SterlingProfilesModel sterlingProfilesModel) {
        sterlingCustomProtocolPartnerService.create(sterlingProfilesModel);
        return ResponseEntity.ok(OK.apply(PARTNER_CREATE));
    }

    @ApiOperation(value = "Update Custom-Protocol Partner Profile in Sterling", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PutMapping
    @PreAuthorize(SA_OB_BA)
    public ResponseEntity<CommunityManagerResponse> update(@Validated @RequestBody SterlingProfilesModel sterlingProfilesModel) {
        sterlingCustomProtocolPartnerService.update(sterlingProfilesModel);
        return ResponseEntity.ok(OK.apply(PARTNER_UPDATE));
    }

    @ApiOperation(value = "Get Custom-Protocol Partner Profile in Sterling", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_NOT_FOUND, message = "no record found")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "{profileName}")
    public ResponseEntity<SterlingProfilesModel> get(@Validated @PathVariable String profileName) {
        return ResponseEntity.ok(sterlingCustomProtocolPartnerService.get(profileName));
    }

    @ApiOperation(value = "Delete Custom-Protocol Partner Profile in Sterling", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_NOT_FOUND, message = "no record found")
    })
    @DeleteMapping()
    @PreAuthorize(SA_OB_BA)
    public ResponseEntity<CommunityManagerResponse> delete(@Validated @RequestParam("profileName") String profileName, @RequestParam("deleteMailbox") Boolean deleteMailbox, @RequestParam("deleteUser") Boolean deleteUser) {
        sterlingCustomProtocolPartnerService.delete(profileName, deleteMailbox, deleteUser);
        return ResponseEntity.ok(OK.apply(PARTNER_DELETE));
    }

}
