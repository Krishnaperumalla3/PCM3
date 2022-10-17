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

import com.pe.pcm.application.ManageApplicationService;
import com.pe.pcm.application.entity.AppActivityHistoryEntity;
import com.pe.pcm.application.entity.ApplicationEntity;
import com.pe.pcm.common.CommunityManagerKeyValueModel;
import com.pe.pcm.common.CommunityManagerNameModel;
import com.pe.pcm.common.CommunityManagerResponse;
import com.pe.pcm.profile.ProfileModel;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.pe.pcm.constants.AuthoritiesConstants.SA_OB_BA;
import static com.pe.pcm.constants.AuthoritiesConstants.SA_OB_BA_BU;
import static com.pe.pcm.utils.CommonFunctions.OK;
import static com.pe.pcm.utils.PCMConstants.STATUS_UPDATE;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

/**
 * @author Kiran Reddy.
 */
@RestController
@RequestMapping(path = "pcm/application")
@Api(tags = {"Application Resource"})
public class ApplicationResource {

    private final ManageApplicationService manageApplicationService;

    @Autowired
    public ApplicationResource(ManageApplicationService manageApplicationService) {
        this.manageApplicationService = manageApplicationService;
    }

    @ApiOperation(value = "Get Application Profiles", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @PostMapping(path = "search", produces = APPLICATION_JSON_VALUE)
    public Page<ApplicationEntity> search(@RequestBody ProfileModel profileModel,
                                          @PageableDefault(size = 10, page = 0, sort = {"applicationName"}, direction = Direction.ASC) Pageable pageable) {
        return manageApplicationService.search(profileModel, pageable);
    }

    @ApiOperation(value = "Get Application Profiles in XML", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @PostMapping(path = "search-xml", produces = APPLICATION_XML_VALUE, consumes = APPLICATION_XML_VALUE)
    public Page<ApplicationEntity> searchWithXML(@RequestBody ProfileModel profileModel,
                                                 @PageableDefault(size = 10, page = 0, sort = {"applicationName"}, direction = Direction.ASC) Pageable pageable) {
        return manageApplicationService.search(profileModel, pageable);
    }

    @ApiOperation(value = "Get Application Activity History", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @PostMapping(path = "activity/{pkId}", produces = APPLICATION_JSON_VALUE)
    public Page<AppActivityHistoryEntity> getHistory(@PathVariable String pkId,
                                                     @PageableDefault(size = 10, page = 0, sort = {"activityDt"}, direction = Direction.DESC) Pageable pageable) {
        return manageApplicationService.getHistory(pkId, pageable);
    }

    @ApiOperation(value = "Update Profile status", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA)
    @PostMapping(path = "status")
    public ResponseEntity<CommunityManagerResponse> statusChange(@RequestParam("pkId") String pkId,
                                                                 @RequestParam("status") Boolean status) {
        manageApplicationService.statusChange(pkId, status, false);
        return ResponseEntity.ok(OK.apply(STATUS_UPDATE));
    }

    @ApiOperation(value = "Get Applications as Map", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @GetMapping(path = "application-map")
    public ResponseEntity<List<CommunityManagerKeyValueModel>> getApplicationMap() {
        return ResponseEntity.ok(manageApplicationService.getApplicationMap());
    }

    @ApiOperation(value = "Get Applications as List", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @GetMapping(path = "application-list")
    public ResponseEntity<List<CommunityManagerNameModel>> getApplicationList() {
        return ResponseEntity.ok(manageApplicationService.getApplicationList());
    }

    @ApiOperation(value = "Get Applications Profiles by Protocol", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "applications-by-protocol")
    public ResponseEntity<List<CommunityManagerKeyValueModel>> getProfilesByProtocol(@RequestParam("protocol") String protocol) {
        return ResponseEntity.ok(manageApplicationService.getProfilesByProtocol(protocol));
    }

}
