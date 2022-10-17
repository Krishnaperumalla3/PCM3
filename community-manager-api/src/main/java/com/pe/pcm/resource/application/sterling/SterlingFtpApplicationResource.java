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

import com.pe.pcm.application.sterling.SterlingFtpApplicationService;
import com.pe.pcm.common.CommunityManagerResponse;
import com.pe.pcm.protocol.RemoteProfileModel;
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
@RequestMapping(value = "pcm/si/application/remote-ftp", produces = APPLICATION_JSON_VALUE)
@Api(tags = {"Application Profile with IBM Sterling B2B Integrator FTP/FTPS/SFTP"}, description = "IBM Sterling B2B Integrator FTP/FTPS/SFTP Application Resource")
public class SterlingFtpApplicationResource {

    private final SterlingFtpApplicationService sterlingFtpApplicationService;

    @Autowired
    public SterlingFtpApplicationResource(SterlingFtpApplicationService sterlingFtpApplicationService) {
        this.sterlingFtpApplicationService = sterlingFtpApplicationService;
    }

    @ApiOperation(value = "Create IBM Sterling B2B Integrator FTP/FTPS/SFTP Application Profile", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PostMapping
    @PreAuthorize(SA_OB_BA)
    public ResponseEntity<CommunityManagerResponse> create(@Validated @RequestBody RemoteProfileModel remoteProfileModel) {
        sterlingFtpApplicationService.create(remoteProfileModel);
        return ResponseEntity.ok(OK.apply(APPLICATION_CREATE));
    }

    @ApiOperation(value = "Update IBM Sterling B2B Integrator FTP/FTPS/SFTP Application Profile", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PutMapping
    @PreAuthorize(SA_OB_BA)
    public ResponseEntity<CommunityManagerResponse> update(@Validated @RequestBody RemoteProfileModel remoteProfileModel) {
        sterlingFtpApplicationService.update(remoteProfileModel);
        return ResponseEntity.ok(OK.apply(APPLICATION_UPDATE));
    }

    @ApiOperation(value = "Get IBM Sterling B2B Integrator FTP/FTPS/SFTP Application Profile", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_NOT_FOUND, message = "no record found")
    })
    @GetMapping(path = "{pkId}")
    @PreAuthorize(SA_OB_BA_BU)
    public ResponseEntity<RemoteProfileModel> get(@Validated @PathVariable String pkId) {
        return ResponseEntity.ok(sterlingFtpApplicationService.get(pkId));
    }

    @ApiOperation(value = "Delete IBM Sterling B2B Integrator FTP/FTPS/SFTP Application Profile", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_NOT_FOUND, message = "no record found")
    })
    @DeleteMapping()
    @PreAuthorize(SA_OB_BA)
    public ResponseEntity<CommunityManagerResponse> delete(@Validated @RequestParam("pkId") String pkId, @RequestParam("deleteUser") Boolean deleteUser, @RequestParam("deleteMailboxes") Boolean deleteMailboxes) {
        sterlingFtpApplicationService.delete(pkId, deleteUser, deleteMailboxes);
        return ResponseEntity.ok(OK.apply(APPLICATION_DELETE));
    }

}
