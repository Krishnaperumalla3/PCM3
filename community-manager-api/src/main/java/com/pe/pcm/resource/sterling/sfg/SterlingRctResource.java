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

package com.pe.pcm.resource.sterling.sfg;

import com.pe.pcm.common.CommunityManagerResponse;
import com.pe.pcm.sterling.mailbox.RoutingChannelTempModel;
import com.pe.pcm.sterling.sfg.rct.SterlingRctService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.pe.pcm.constants.AuthoritiesConstants.SA_OB_BA;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Kiran Reddy.
 */
@RestController
@RequestMapping(path = "pcm/sfg/rct", produces = APPLICATION_JSON_VALUE)
@Api(tags = {"Sterling File GateWay Routing Channel Template"}, description = "Routing Channel Template Resource")
@PreAuthorize(SA_OB_BA)
public class SterlingRctResource {

    private final SterlingRctService sterlingRctService;

    @Autowired
    public SterlingRctResource(SterlingRctService sterlingRctService) {
        this.sterlingRctService = sterlingRctService;
    }

    @ApiOperation(value = "Create Routing Channel Template", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping
    public ResponseEntity<CommunityManagerResponse> create(@RequestBody RoutingChannelTempModel routingChannelTempModel) {
        sterlingRctService.create(routingChannelTempModel);
        return ResponseEntity.ok(new CommunityManagerResponse(OK.value(), "Routing Channel Template created successfully"));
    }

    @ApiOperation(value = "Update Routing Channel Template", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PutMapping
    public ResponseEntity<CommunityManagerResponse> update(@Validated RoutingChannelTempModel routingChannelTempModel) {
        sterlingRctService.update(routingChannelTempModel);
        return ResponseEntity.ok(new CommunityManagerResponse(OK.value(), "Routing Channel Template updated successfully"));
    }

    @ApiOperation(value = "Get Routing Channel Template", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @GetMapping(path = "{templateName}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<RoutingChannelTempModel> getUser(@Validated @PathVariable("templateName") String templateName) {
        return ResponseEntity.ok(sterlingRctService.get(templateName));
    }

//    @ApiOperation(value = "Search User", authorizations = {@Authorization(value = "apiKey")})
//    @ApiResponses({
//            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
//    })
//    @PostMapping(path = "search", produces = APPLICATION_JSON_VALUE)
//    public Page<UserEntity> search(@RequestBody UserModel userModel,
//                                   @PageableDefault(size = 10, page = 0, sort = {"userid"}, direction = Sort.Direction.ASC) Pageable pageable) {
//        return userService.search(userModel, pageable);
//    }

    @ApiOperation(value = "Delete Routing Channel Template", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @DeleteMapping(path = "{templateName}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityManagerResponse> delete(@Validated @PathVariable("templateName") String templateName) {
        sterlingRctService.delete(templateName);
        return ResponseEntity.ok(new CommunityManagerResponse(OK.value(), "Routing Channel Template deleted successfully."));
    }
}
