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

package com.pe.pcm.resource.pem;

import com.pe.pcm.common.GenericModel;
import com.pe.pcm.sql.DatabaseSetupService;
import com.pe.pcm.sql.SingleSqlModel;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.pe.pcm.constants.AuthoritiesConstants.SA_OB_BA;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Kiran Reddy.
 */
@RestController
@RequestMapping(value = "pem/framework-integration", produces = APPLICATION_JSON_VALUE)
@Api(tags = {"PEM - Framework Integration Resource"})
@PreAuthorize(SA_OB_BA)
public class FrameworkIntegrationResource {

    private final DatabaseSetupService databaseSetupService;

    @Autowired
    public FrameworkIntegrationResource(DatabaseSetupService databaseSetupService) {
        this.databaseSetupService = databaseSetupService;
    }

    @ApiOperation(value = "Integration (CRUD Operations)", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericModel> integrate(@RequestBody SingleSqlModel singleSqlModel) {
        return ResponseEntity.ok(databaseSetupService.sqlOperations(singleSqlModel));
    }

}
