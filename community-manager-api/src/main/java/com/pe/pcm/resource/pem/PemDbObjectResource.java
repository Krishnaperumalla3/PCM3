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
import com.pe.pcm.common.CommunityMangerModel;
import com.pe.pcm.sql.PemDbObjectDataTypeModel;
import com.pe.pcm.sql.PemDbObjectModel;
import com.pe.pcm.sql.PemDbObjectsService;
import com.pe.pcm.utils.CommonFunctions;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.pe.pcm.constants.AuthoritiesConstants.SA_OB_BA;
import static com.pe.pcm.constants.AuthoritiesConstants.SA_OB_BA_BU;
import static com.pe.pcm.utils.PCMConstants.REQUEST_OK;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Kiran Reddy.
 */
@RestController
@RequestMapping(value = "pem/sql-service/config", produces = APPLICATION_JSON_VALUE)
@Api(tags = {"PEM SQL Service Config Resource"})
public class PemDbObjectResource {

    private final PemDbObjectsService pemDbObjectsService;

    @Autowired
    public PemDbObjectResource(PemDbObjectsService pemDbObjectsService) {
        this.pemDbObjectsService = pemDbObjectsService;
    }

    @ApiOperation(value = "Create Db Object", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA)
    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityManagerResponse> create(@RequestBody PemDbObjectModel pemDbObjectModel) {
        pemDbObjectsService.create(pemDbObjectModel);
        return ResponseEntity.ok(CommonFunctions.OK.apply(REQUEST_OK));
    }

    @ApiOperation(value = "Create Db Object Data Types", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA)
    @PostMapping(path = "create-object-dataypes", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityManagerResponse> createDataTypes(@RequestBody PemDbObjectDataTypeModel pemDbObjectDataTypeModel) {
        pemDbObjectsService.createDataType(pemDbObjectDataTypeModel);
        return ResponseEntity.ok(CommonFunctions.OK.apply(REQUEST_OK));
    }

    @ApiOperation(value = "Update Db Object", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA)
    @PutMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityManagerResponse> update(@RequestBody PemDbObjectModel pemDbObjectModel) {
        pemDbObjectsService.update(pemDbObjectModel);
        return ResponseEntity.ok(CommonFunctions.OK.apply(REQUEST_OK));
    }

    @ApiOperation(value = "Get Db Object", authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "{seqId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PemDbObjectModel> getUser(@Validated @PathVariable("seqId") String seqId) {
        return ResponseEntity.ok(pemDbObjectsService.get(seqId));
    }

    @ApiOperation(value = "Search Db Objects", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "search", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityMangerModel> findAll() {
        return ResponseEntity.ok(new CommunityMangerModel<>(pemDbObjectsService.findAll()));
    }

    @ApiOperation(value = "Delete Db Object", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA)
    @DeleteMapping(path = "{seqId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityManagerResponse> delete(@Validated @PathVariable("seqId") String seqId) {
        pemDbObjectsService.delete(seqId);
        return ResponseEntity.ok(new CommunityManagerResponse(OK.value(), "record deleted successfully"));
    }

}
