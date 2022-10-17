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

package com.pe.pcm.resource.application;


import com.pe.pcm.application.SmtpApplicationService;
import com.pe.pcm.common.CommunityManagerResponse;
import com.pe.pcm.protocol.SmtpModel;
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
 * @author Shameer.
 */

@RestController
@RequestMapping(value = "pcm/application/smtp", produces = APPLICATION_JSON_VALUE)
@Api(tags = {"Application Profile With SMTP"}, description = "SMTP Application Resource")
public class SmtpApplicationResource {

    private final SmtpApplicationService smtpApplicationService;

    @Autowired
    public SmtpApplicationResource(SmtpApplicationService smtpApplicationService) {
        this.smtpApplicationService = smtpApplicationService;
    }

    @ApiOperation(value = "Create SMTP Application Profile", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PostMapping
    @PreAuthorize(SA_OB_BA)
    public ResponseEntity<CommunityManagerResponse> save(@Validated @RequestBody SmtpModel smtpModel) {
        smtpApplicationService.save(smtpModel);
        return ResponseEntity.ok(OK.apply(APPLICATION_CREATE));
    }

    @ApiOperation(value = "Update SMTP Application Profile", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PutMapping
    @PreAuthorize(SA_OB_BA)
    public ResponseEntity<CommunityManagerResponse> update(@Validated @RequestBody SmtpModel ftpModel) {
        smtpApplicationService.update(ftpModel);
        return ResponseEntity.ok(OK.apply(APPLICATION_UPDATE));
    }

    @ApiOperation(value = "Get SMTP Application Profile", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_NOT_FOUND, message = "no record found")
    })
    @GetMapping(path = "{pkId}")
    @PreAuthorize(SA_OB_BA_BU)
    public ResponseEntity<SmtpModel> get(@PathVariable String pkId) {
        return ResponseEntity.ok(smtpApplicationService.get(pkId));
    }

    @ApiOperation(value = "Delete SMTP Application Profile", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_NOT_FOUND, message = "no record found")
    })
    @PreAuthorize(SA_OB_BA)
    @DeleteMapping(path = "{pkId}")
    public ResponseEntity<CommunityManagerResponse> delete(@PathVariable String pkId) {
        smtpApplicationService.delete(pkId);
        return ResponseEntity.ok(OK.apply(APPLICATION_DELETE));
    }
}


