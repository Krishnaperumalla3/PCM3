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

package com.pe.pcm.resource.pem;

import com.pe.pcm.common.CommunityManagerResponse;
import com.pe.pcm.pem.PemRollOutModel;
import com.pe.pcm.pem.PemRollOutModelXml;
import com.pe.pcm.pem.PemWsService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.pe.pcm.constants.AuthoritiesConstants.SA_OB_BA;
import static com.pe.pcm.utils.CommonFunctions.OK;
import static com.pe.pcm.utils.PCMConstants.REQUEST_OK;
import static javax.servlet.http.HttpServletResponse.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

/**
 * @author Kiran Reddy.
 */
@RestController
@RequestMapping(value = "pem/ws", produces = APPLICATION_JSON_VALUE)
@Api(tags = {"PEM Web Service Resource"})
@PreAuthorize(SA_OB_BA)
public class PemWsResource {

    private final PemWsService pemWsService;

    @Autowired
    public PemWsResource(PemWsService pemWsService) {
        this.pemWsService = pemWsService;
    }

    @ApiOperation(value = "PEM WebService Rollout", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PostMapping(path = "roll-out")
    public ResponseEntity<CommunityManagerResponse> pemWsRollOut(@RequestBody PemRollOutModel pemRollOutModel) {
        pemWsService.doRollOut(pemRollOutModel);
        return ResponseEntity.ok(OK.apply(REQUEST_OK));
    }

    @ApiOperation(value = "PEM Webservice Rollout with Profile", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PostMapping(path = "roll-out-profile")
    public ResponseEntity<CommunityManagerResponse> pemWsRollOutProfile(@RequestBody PemRollOutModel pemRollOutModel) {
        pemWsService.doRollOutWithProfile(pemRollOutModel);
        return ResponseEntity.ok(OK.apply(REQUEST_OK));
    }

    @ApiOperation(value = "PEM Webservice Rollout with Profile Xml", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PostMapping(path = "roll-out-profile-xml",consumes = APPLICATION_XML_VALUE,produces = APPLICATION_XML_VALUE)
    public ResponseEntity<CommunityManagerResponse> pemWsRollOutProfileXml(@RequestBody PemRollOutModelXml pemRollOutModelXml) {
        pemWsService.doRollOutWithProfileXml(pemRollOutModelXml);
        return ResponseEntity.ok(OK.apply(REQUEST_OK));
    }
}
