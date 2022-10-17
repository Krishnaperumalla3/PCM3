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

package com.pe.pcm.resource.miscellaneous;

import com.pe.pcm.miscellaneous.ManageAdminActionsService;
import com.pe.pcm.common.CommunityManagerResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.pe.pcm.constants.AuthoritiesConstants.ONLY_SA;
import static com.pe.pcm.constants.AuthoritiesConstants.SA_OB_BA;
import static com.pe.pcm.utils.CommonFunctions.OK;
import static com.pe.pcm.utils.PCMConstants.REQUEST_OK;
import static javax.servlet.http.HttpServletResponse.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Kiran Reddy.
 */
@RestController
@RequestMapping(value = "pcm/admin/", produces = APPLICATION_JSON_VALUE)
@Api(tags = {"Admin Actions Resource"})
public class AdminActionsResource {

    private final ManageAdminActionsService manageAdminActionsService;

    @Autowired
    public AdminActionsResource(ManageAdminActionsService manageAdminActionsService) {
        this.manageAdminActionsService = manageAdminActionsService;
    }

    @ApiOperation(value = "To update the relations between the workflow entities. this is onetime run when we migrate from 6.1 below version to any higher version of PCM",
            authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PreAuthorize(ONLY_SA)
    @PutMapping(path = "workflow")
    public ResponseEntity<CommunityManagerResponse> manageWorkFlowColumns() {
        manageAdminActionsService.manageWorkFlow();
        return ResponseEntity.ok(OK.apply(REQUEST_OK));
    }

    @ApiOperation(value = "To delete the Transaction from both TransferInfo and TransInfoD(Transaction Activities)",
            authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PreAuthorize(ONLY_SA)
    @GetMapping(path = "purge/transactions")
    public ResponseEntity<CommunityManagerResponse> purgeTransactions(@Validated @RequestParam("days") Long days) {
        return ResponseEntity.ok(OK.apply(manageAdminActionsService.purgeTransactions(days)));
    }
}
