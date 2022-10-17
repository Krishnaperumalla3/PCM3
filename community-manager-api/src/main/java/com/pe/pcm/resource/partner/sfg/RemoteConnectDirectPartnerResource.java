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
import com.pe.pcm.partner.RemoteConnectDirectPartnerService;
import com.pe.pcm.protocol.RemoteCdModel;
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
@RequestMapping(value = "pcm/partner/remote-connect-direct", produces = APPLICATION_JSON_VALUE)
@Api(tags = {"Partner Profile With SFG ConnectDirect"}, description = "SFG ConnectDirect Partner Resource")
public class RemoteConnectDirectPartnerResource {

    private final RemoteConnectDirectPartnerService remoteConnectDirectPartnerService;

    @Autowired
    public RemoteConnectDirectPartnerResource(RemoteConnectDirectPartnerService remoteConnectDirectPartnerService) {
        this.remoteConnectDirectPartnerService = remoteConnectDirectPartnerService;
    }

    @ApiOperation(value = "Create ConnectDirect Profile", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PostMapping
    @PreAuthorize(SA_OB_BA)
    public ResponseEntity<CommunityManagerResponse> save(@Validated @RequestBody RemoteCdModel remoteCdModel) {
        remoteConnectDirectPartnerService.save(remoteCdModel);
        return ResponseEntity.ok(OK.apply(PARTNER_CREATE));
    }

    @ApiOperation(value = "Update ConnectDirect Profile", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PutMapping
    @PreAuthorize(SA_OB_BA)
    public ResponseEntity<CommunityManagerResponse> update(@Validated @RequestBody RemoteCdModel remoteCdModel) {
        remoteConnectDirectPartnerService.update(remoteCdModel);
        return ResponseEntity.ok(OK.apply(PARTNER_UPDATE));
    }

    @ApiOperation(value = "Get ConnectDirect Profile", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_NOT_FOUND, message = "no record found")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "{pkId}")
    public ResponseEntity<RemoteCdModel> get(@Validated @PathVariable String pkId) {
        return ResponseEntity.ok(remoteConnectDirectPartnerService.get(pkId));
    }

    @ApiOperation(value = "Delete ConnectDirect Profile", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_NOT_FOUND, message = "no record found")
    })
    @DeleteMapping()
    @PreAuthorize(SA_OB_BA)
    public ResponseEntity<CommunityManagerResponse> delete(@RequestParam String pkId, @RequestParam Boolean isDeleteInSI) {
        remoteConnectDirectPartnerService.delete(pkId, isDeleteInSI);
        return ResponseEntity.ok(OK.apply(PARTNER_DELETE));
    }
}
