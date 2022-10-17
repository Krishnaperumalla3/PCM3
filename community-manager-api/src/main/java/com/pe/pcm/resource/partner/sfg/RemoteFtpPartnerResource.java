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

package com.pe.pcm.resource.partner.sfg;

import com.pe.pcm.common.CommunityManagerResponse;
import com.pe.pcm.partner.RemoteFtpPartnerService;
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
@RequestMapping(value = "pcm/partner/remoteftp", produces = APPLICATION_JSON_VALUE)
@Api(tags = {"Partner Profile With SFGFTP/SFGFTPS/SFGSFTP"}, description = "Remote SFGFTP/SFGFTPS/SFGSFTP Partner Resource")
public class RemoteFtpPartnerResource {

    private final RemoteFtpPartnerService remoteFtpPartnerService;

    @Autowired
    public RemoteFtpPartnerResource(RemoteFtpPartnerService remoteFtpPartnerService) {
        this.remoteFtpPartnerService = remoteFtpPartnerService;
    }

    @ApiOperation(value = "Create SFGFTP/SFGFTPS/SFGSFTP Partner Profile", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PostMapping
    @PreAuthorize(SA_OB_BA)
    public ResponseEntity<CommunityManagerResponse> save(@Validated @RequestBody RemoteProfileModel remoteProfileModel) {
        remoteFtpPartnerService.save(remoteProfileModel);
        return ResponseEntity.ok(OK.apply(PARTNER_CREATE));
    }

    @ApiOperation(value = "Update SFGFTP/SFGFTPS/SFGSFTP Partner Profile", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PutMapping
    @PreAuthorize(SA_OB_BA)
    public ResponseEntity<CommunityManagerResponse> update(@Validated @RequestBody RemoteProfileModel remoteProfileModel) {
        remoteFtpPartnerService.update(remoteProfileModel);
        return ResponseEntity.ok(OK.apply(PARTNER_UPDATE));
    }

    @ApiOperation(value = "Get SFGFTP/SFGFTPS/SFGSFTP Partner Profile", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_NOT_FOUND, message = "no record found")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "{pkId}")
    public ResponseEntity<RemoteProfileModel> get(@Validated @PathVariable String pkId) {
        return ResponseEntity.ok(remoteFtpPartnerService.get(pkId));
    }

    @ApiOperation(value = "Delete SFGFTP/SFGFTPS/SFGSFTP Partner Profile", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_NOT_FOUND, message = "no record found")
    })
    @PreAuthorize(SA_OB_BA)
    @DeleteMapping()
    public ResponseEntity<CommunityManagerResponse> delete(@Validated @RequestParam("pkId") String pkId,
                                                           @Validated @RequestParam("isDeleteInSI") Boolean isDeleteInSI,
                                                           @RequestParam("deleteArtifacts") Boolean deleteArtifacts) {
        remoteFtpPartnerService.delete(pkId, isDeleteInSI, deleteArtifacts, deleteArtifacts, deleteArtifacts);
        return ResponseEntity.ok(OK.apply(PARTNER_DELETE));
    }


    @ApiOperation(value = "Custom Delete SFGFTP/SFGFTPS/SFGSFTP Partner Profile", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_NOT_FOUND, message = "no record found")
    })
    @PreAuthorize(SA_OB_BA)
    @DeleteMapping(path = "custom-delete")
    public ResponseEntity<CommunityManagerResponse> customDelete(@Validated @RequestParam("pkId") String pkId,
                                                                 @Validated @RequestParam("deleteRemoteProfile") Boolean deleteRemoteProfile,
                                                                 @RequestParam("deleteUser") Boolean deleteUser,
                                                                 @RequestParam("deleteMailboxes") Boolean deleteMailboxes,
                                                                 @RequestParam("deleteKnownHostKeys") Boolean deleteKnownHostKeys) {
        remoteFtpPartnerService.delete(pkId, deleteRemoteProfile, deleteUser, deleteMailboxes, deleteKnownHostKeys);
        return ResponseEntity.ok(OK.apply(PARTNER_DELETE));
    }
}
