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

package com.pe.pcm.resource.application.sterling;

import com.pe.pcm.application.sterling.SterlingAs2ApplicationService;
import com.pe.pcm.common.CommunityManagerResponse;
import com.pe.pcm.protocol.As2Model;
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
@RequestMapping("pcm/si/application/remote-as2")
@Api(tags = {"Application Profile with IBM Sterling B2B Integrator AS2"}, description = "IBM Sterling B2B Integrator AS2 Application Resource")
public class SterlingAs2ApplicationResource {

    private final SterlingAs2ApplicationService sterlingAs2ApplicationService;

    @Autowired
    public SterlingAs2ApplicationResource(SterlingAs2ApplicationService sterlingAs2ApplicationService) {
        this.sterlingAs2ApplicationService = sterlingAs2ApplicationService;
    }

    @ApiOperation(value = "Create AS2 Application", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({@ApiResponse(code = SC_OK, message = "Success"),
            @ApiResponse(code = SC_UNAUTHORIZED, message = "UnAuthorized Request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exists")})
    @PostMapping()
    @PreAuthorize(SA_OB_BA)
    public ResponseEntity<CommunityManagerResponse> create(@Validated @RequestBody As2Model as2Model) {
        sterlingAs2ApplicationService.create(as2Model, false);
        return ResponseEntity.ok(OK.apply(APPLICATION_CREATE));
    }

    @ApiOperation(value = "Update AS2 Application", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({@ApiResponse(code = SC_OK, message = "Success"),
            @ApiResponse(code = SC_UNAUTHORIZED, message = "UnAuthorized Request"),
            @ApiResponse(code = SC_NOT_FOUND, message = "No Record Found")})
    @PutMapping()
    @PreAuthorize(SA_OB_BA)
    public ResponseEntity<CommunityManagerResponse> update(@Validated @RequestBody As2Model as2Model) {
        sterlingAs2ApplicationService.update(as2Model);
        return ResponseEntity.ok(OK.apply(APPLICATION_UPDATE));
    }

    @ApiOperation(value = "Get AS2 Application", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses(value = {@ApiResponse(code = SC_OK, message = "Success"),
            @ApiResponse(code = SC_UNAUTHORIZED, message = "UnAuthorized Request"),
            @ApiResponse(code = SC_NOT_FOUND, message = "No Record Found")})
    @GetMapping(path = "{pkId}", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize(SA_OB_BA_BU)
    public ResponseEntity<As2Model> get(@PathVariable String pkId) {
        return ResponseEntity.ok(sterlingAs2ApplicationService.get(pkId));
    }

    @ApiOperation(value = "Delete AS2 Application", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses(value = {@ApiResponse(code = SC_OK, message = "Success"),
            @ApiResponse(code = SC_UNAUTHORIZED, message = "UnAuthorized Request"),
            @ApiResponse(code = SC_NOT_FOUND, message = "No Record Found")})
    @DeleteMapping(path = "{pkId}", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize(SA_OB_BA)
    public ResponseEntity<CommunityManagerResponse> delete(@PathVariable String pkId) {
        sterlingAs2ApplicationService.delete(pkId);
        return ResponseEntity.ok(OK.apply(APPLICATION_DELETE));
    }

}
