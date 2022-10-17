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

package com.pe.pcm.resource.partner;

import com.pe.pcm.common.CommunityManagerKeyValueModel;
import com.pe.pcm.common.CommunityManagerNameModel;
import com.pe.pcm.common.CommunityManagerResponse;
import com.pe.pcm.partner.ManagePartnerService;
import com.pe.pcm.partner.entity.PartnerActivityHistoryEntity;
import com.pe.pcm.partner.entity.PartnerEntity;
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

/**
 * @author Kiran Reddy.
 */
@RestController
@RequestMapping(path = "pcm/partner")
@Api(tags = {"Manage Partner"}, description = "Manage Partner Resource")
public class PartnerResource {

    private final ManagePartnerService managePartnerService;

    @Autowired
    public PartnerResource(ManagePartnerService managePartnerService) {
        this.managePartnerService = managePartnerService;
    }

    @ApiOperation(value = "Get Partner Profiles", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @PostMapping(path = "search", produces = APPLICATION_JSON_VALUE)
    public Page<PartnerEntity> search(@RequestBody ProfileModel profileModel,
                                      @PageableDefault(size = 10, page = 0, sort = {"tpName"}, direction = Direction.ASC) Pageable pageable) {
        return managePartnerService.search(profileModel, pageable);
    }

    @ApiOperation(value = "Get Partner Activity History", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @PostMapping(path = "activity/{pkId}", produces = APPLICATION_JSON_VALUE)
    public Page<PartnerActivityHistoryEntity> getHistory(@PathVariable String pkId,
                                                         @PageableDefault(size = 10, page = 0, sort = {"activityDt"}, direction = Direction.DESC) Pageable pageable) {
        return managePartnerService.getHistory(pkId, pageable);
    }

    @ApiOperation(value = "Get Partners List", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @GetMapping(path = "partners-list")
    public ResponseEntity<List<CommunityManagerNameModel>> getPartnersList() {
        return ResponseEntity.ok(managePartnerService.getPartnersList());
    }

    @ApiOperation(value = "Get Partners Map", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @GetMapping(path = "partners-map")
    public ResponseEntity<List<CommunityManagerKeyValueModel>> getPartnersMap() {
        return ResponseEntity.ok(managePartnerService.getPartnersMap());
    }

    @ApiOperation(value = "Get AS2 Organization Profiles", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @GetMapping(path = "org-profile/partners-list")
    public ResponseEntity<List<CommunityManagerNameModel>> getAs2OrgProfilesList() {
        return ResponseEntity.ok(managePartnerService.getAs2Profiles("Y"));
    }

    @ApiOperation(value = "Get AS2 Partner Profiles", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @GetMapping(path = "partner-profile/partners-list")
    public ResponseEntity<List<CommunityManagerNameModel>> getAs2PartnersProfilesList() {
        return ResponseEntity.ok(managePartnerService.getAs2Profiles("N"));
    }

    @ApiOperation(value = "Update Profile status", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "status")
    @PreAuthorize(SA_OB_BA)
    public ResponseEntity<CommunityManagerResponse> statusChange(@RequestParam("pkId") String pkId,
                                                                 @RequestParam("status") Boolean status) {
        managePartnerService.statusChange(pkId, status, false);
        return ResponseEntity.ok(OK.apply(STATUS_UPDATE));
    }

    @ApiOperation(value = "Get Partner Profiles by Protocol", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "partners-by-protocol")
    public ResponseEntity<List<CommunityManagerKeyValueModel>> getProfilesByProtocol(@RequestParam("protocol") String protocol, @RequestParam("isHubInfo") boolean isHubInfo) {
        return ResponseEntity.ok(managePartnerService.getProfilesByProtocolAndHubInfo(protocol, isHubInfo));
    }

    @ApiOperation(value = "Get Partner Profiles by Protocol", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "find-by-protocol")
    public ResponseEntity<List<CommunityManagerKeyValueModel>> getProfilesByProtocol(@RequestParam("protocol") String protocol) {
        return ResponseEntity.ok(managePartnerService.getProfilesByProtocol(protocol));
    }

}
