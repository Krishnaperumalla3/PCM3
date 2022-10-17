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

import com.pe.pcm.common.GenericModel;
import com.pe.pcm.sql.DatabaseSetupService;
import com.pe.pcm.sql.SqlRequestModel;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.pe.pcm.constants.AuthoritiesConstants.SA_OB_BA;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Chenchu Kiran.
 */
@RestController
@RequestMapping(value = "pcm/sql-service", produces = APPLICATION_JSON_VALUE)
@Api(tags = {"PEM SQL Operations Resource"})
@PreAuthorize(SA_OB_BA)
public class SqlManageResource {

    private final DatabaseSetupService databaseSetupService;

    @Autowired
    public SqlManageResource(DatabaseSetupService databaseSetupService) {
        this.databaseSetupService = databaseSetupService;
    }

    @ApiOperation(value = "SQL Insert Operation", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "create", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericModel> create(@RequestBody SqlRequestModel sqlRequestModel) {
        return ResponseEntity.ok(databaseSetupService.sqlOperations(sqlRequestModel, "INSERT"));
    }

    @ApiOperation(value = "SQL Insert Operation With DataType", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "create-datatype", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericModel> createDataType(@RequestBody SqlRequestModel sqlRequestModel) {
        return ResponseEntity.ok(databaseSetupService.sqlOperationsWithDataType(sqlRequestModel, "INSERT"));
    }


    @ApiOperation(value = "SQL Update Operation", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PutMapping(path = "update", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericModel> update(@RequestBody SqlRequestModel sqlRequestModel) {
        return ResponseEntity.ok(databaseSetupService.sqlOperations(sqlRequestModel, "UPDATE"));
    }

    @ApiOperation(value = "SQL Update Operation With DataTypes", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PutMapping(path = "update-dataType", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericModel> updateWithDataType(@RequestBody SqlRequestModel sqlRequestModel) {
        return ResponseEntity.ok(databaseSetupService.sqlOperationsWithDataType(sqlRequestModel, "UPDATE"));
    }

    @ApiOperation(value = "Delete Db Object", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @DeleteMapping(path = "delete", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericModel> delete(@RequestBody SqlRequestModel sqlRequestModel) {
        return ResponseEntity.ok(databaseSetupService.sqlOperations(sqlRequestModel, "DELETE"));
    }

    @ApiOperation(value = "SQL Select Operation", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "search", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericModel> search(@RequestBody SqlRequestModel sqlRequestModel) {
        return ResponseEntity.ok(databaseSetupService.sqlOperations(sqlRequestModel, "SELECT"));
    }


}
