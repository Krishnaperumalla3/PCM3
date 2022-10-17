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

package com.pe.pcm.resource.sterling;

import com.pe.pcm.common.CommunityManagerResponse;
import com.pe.pcm.login.entity.YfsUserEntity;
import com.pe.pcm.sterling.YfsUserModel;
import com.pe.pcm.sterling.yfs.YfsUserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.pe.pcm.constants.AuthoritiesConstants.SA_OB_BA;
import static com.pe.pcm.constants.AuthoritiesConstants.SA_OB_BA_BU;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Kiran Reddy.
 */
@RestController
@RequestMapping(path = "pcm/si/user", produces = APPLICATION_JSON_VALUE)
@Api(tags = {"IBM Sterling B2B Integrator User Account"}, description = "IBM Sterling B2B Integrator User Account Resource")
public class YfsUserResource {

    private final YfsUserService yfsUserService;


    @Autowired
    public YfsUserResource(YfsUserService yfsUserService) {
        this.yfsUserService = yfsUserService;
    }

    @ApiOperation(value = "Create User Account in IBM Sterling B2B Integrator", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping
    @PreAuthorize(SA_OB_BA)
    public ResponseEntity<CommunityManagerResponse> createMailbox(@RequestBody YfsUserModel yfsUserModel) {
        yfsUserService.create(yfsUserModel);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "User Account Created successfully."));
    }

    @ApiOperation(value = "Load ORG User", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @GetMapping
    @PreAuthorize(SA_OB_BA_BU)
    public ResponseEntity<List<YfsUserEntity>> loadOrgUsers(String orgId) {
        return ResponseEntity.ok(yfsUserService.loadOrgUsers(orgId));
    }

    @ApiOperation(value = "Assign Groups and Permissions to a user", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping("assign-gp-per")
    @PreAuthorize(SA_OB_BA_BU)
    public ResponseEntity<CommunityManagerResponse> assignGroupsAndPermissions(@RequestBody YfsUserModel yfsUserModel) {
        yfsUserService.assignGroupsAndPermissions(yfsUserModel);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Groups and Permission added successfully!"));
    }

    @ApiOperation(value = "Get User Permissions and Groups", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @GetMapping("get-gp-per")
    @PreAuthorize(SA_OB_BA_BU)
    public ResponseEntity<YfsUserModel> getGroupsAndPermissions(String userName) {
        return ResponseEntity.ok(yfsUserService.getGroupsAndPermissions(userName));
    }

    @ApiOperation(value = "Get Identities from IBM Sterling B2B Integrator", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @GetMapping("get-identities-list")
    public ResponseEntity<List<String>> getIdentities() {
        return ResponseEntity.ok(yfsUserService.getOrganisationNames());
    }

    @ApiOperation(value = "Get Manager Ids from IBM Sterling B2B Integrator", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @GetMapping("get-Manager-list")
    public ResponseEntity<List<String>> getManagerIds() {
        return ResponseEntity.ok(yfsUserService.getManagerIds());
    }

    @ApiOperation(value = "Get policies from IBM Sterling B2B Integrator", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @GetMapping("get-Policy-list")
    public ResponseEntity<List<String>> getPolicies() {
        return ResponseEntity.ok(yfsUserService.getPolicyId());
    }

    @ApiOperation(value = "Get Auth Host from IBM Sterling B2B Integrator", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @GetMapping("get-Auth-Host-list")
    public ResponseEntity<List<String>> getAuthHost() {
        return ResponseEntity.ok(yfsUserService.getAuthHost());
    }

    @ApiOperation(value = "Get Yfs User from IBM Sterling B2B Integrator", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @GetMapping("get-Yfs-User")
    public ResponseEntity<YfsUserModel> getYfsUser(String username) {
        return ResponseEntity.ok(yfsUserService.getYfsUser(username));
    }

}
