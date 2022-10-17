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
import com.pe.pcm.pem.codelist.PemProxyCodeListModel;
import com.pe.pcm.pem.codelist.PemProxyCodeListService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.pe.pcm.constants.AuthoritiesConstants.SA_OB_BA;
import static com.pe.pcm.utils.CommonFunctions.OK;
import static javax.servlet.http.HttpServletResponse.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Shameer.v.
 */
@RestController
@RequestMapping(value = "pem/proxy/codeList", produces = APPLICATION_JSON_VALUE)
@Api(tags = {"IBM Sterling B2B Integrator Code List Proxy"}, description = "IBM Sterling B2B Integrator Code List Proxy Resource")
public class PemProxyCodeListResource {

    private final PemProxyCodeListService pemProxyCodeListService;

    @Autowired
    public PemProxyCodeListResource(PemProxyCodeListService pemProxyCodeListService) {
        this.pemProxyCodeListService = pemProxyCodeListService;
    }

    @ApiOperation(value = "Create Proxy CodeList", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PostMapping
    @PreAuthorize(SA_OB_BA)
    public ResponseEntity<CommunityManagerResponse> create(@Validated @RequestBody PemProxyCodeListModel pemProxyCodeListModel) {
        pemProxyCodeListService.create(pemProxyCodeListModel);
        return ResponseEntity.ok(OK.apply("Code List Added Successfully"));
    }

    @ApiOperation(value = "Update Proxy CodeList", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PutMapping
    @PreAuthorize(SA_OB_BA)
    public ResponseEntity<CommunityManagerResponse> update(@Validated @RequestBody PemProxyCodeListModel pemProxyCodeListModel) {
        pemProxyCodeListService.update(pemProxyCodeListModel);
        return ResponseEntity.ok(OK.apply("Code List updated Successfully"));
    }

    @ApiOperation(value = "Delete Proxy CodeList", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @DeleteMapping
    @PreAuthorize(SA_OB_BA)
    public ResponseEntity<CommunityManagerResponse> delete(String codeListName) {
        pemProxyCodeListService.delete(codeListName);
        return ResponseEntity.ok(OK.apply("Code List updated Successfully"));
    }
}
