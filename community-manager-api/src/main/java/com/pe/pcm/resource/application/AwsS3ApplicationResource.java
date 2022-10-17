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

import com.pe.pcm.application.AwsS3ApplicationService;
import com.pe.pcm.common.CommunityManagerResponse;
import com.pe.pcm.protocol.AwsS3Model;
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
@RequestMapping(value = "pcm/application/aws-s3", produces = APPLICATION_JSON_VALUE)
@Api(tags = {"Application Profile with AWS-S3"}, description = "AWS-S3 Application Resource")
public class AwsS3ApplicationResource {

    private final AwsS3ApplicationService awsS3ApplicationService;

    @Autowired
    public AwsS3ApplicationResource(AwsS3ApplicationService awsS3ApplicationService) {
        this.awsS3ApplicationService = awsS3ApplicationService;
    }

    @ApiOperation(value = "Create AWS-S3 Application Profile", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PostMapping
    @PreAuthorize(SA_OB_BA)
    public ResponseEntity<CommunityManagerResponse> save(@Validated @RequestBody AwsS3Model awsS3Model) {
        awsS3ApplicationService.save(awsS3Model);
        return ResponseEntity.ok(OK.apply(APPLICATION_CREATE));
    }

    @ApiOperation(value = "Update AWS-S3 Application Profile", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PutMapping
    @PreAuthorize(SA_OB_BA)
    public ResponseEntity<CommunityManagerResponse> update(@Validated @RequestBody AwsS3Model awsS3Model) {
        awsS3ApplicationService.update(awsS3Model);
        return ResponseEntity.ok(OK.apply(APPLICATION_UPDATE));
    }

    @ApiOperation(value = "Get AWS-S3 Application Profile", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_NOT_FOUND, message = "no record found")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "{pkId}")
    public ResponseEntity<AwsS3Model> get(@PathVariable String pkId) {
        return ResponseEntity.ok(awsS3ApplicationService.get(pkId));
    }

    @ApiOperation(value = "Delete AWS-S3 Application Profile", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_NOT_FOUND, message = "no record found")
    })
    @PreAuthorize(SA_OB_BA)
    @DeleteMapping(path = "{pkId}")
    public ResponseEntity<CommunityManagerResponse> delete(@PathVariable String pkId) {
        awsS3ApplicationService.delete(pkId);
        return ResponseEntity.ok(OK.apply(APPLICATION_DELETE));
    }
}
