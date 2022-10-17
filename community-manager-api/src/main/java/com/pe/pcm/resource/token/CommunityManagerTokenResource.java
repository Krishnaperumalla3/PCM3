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

package com.pe.pcm.resource.token;

import com.pe.pcm.common.CommunityManagerResponse;
import com.pe.pcm.exception.CommunityManagerTokenException;
import com.pe.pcm.login.CommunityManagerLoginModel;
import com.pe.pcm.login.CommunityManagerTokenService;
import com.pe.pcm.login.CommunityManagerUserModel;
import com.pe.pcm.logout.LogoutService;
import com.pe.pcm.miscellaneous.OtherUtilityService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.pe.pcm.constants.ProfilesConstants.CM;
import static com.pe.pcm.constants.ProfilesConstants.PCM;
import static com.pe.pcm.utils.CommonFunctions.isNotNull;
import static javax.servlet.http.HttpServletResponse.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Kiran Reddy.
 */
@RestController
@RequestMapping(path = "pcm")
@Profile({PCM, CM})
@Api(tags = {"Access Token Generator"}, hidden = true)
public class CommunityManagerTokenResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommunityManagerTokenResource.class);

    private final LogoutService logoutService;
    private final OtherUtilityService otherUtilityService;
    private final CommunityManagerTokenService communityManagerTokenService;

    @Autowired
    public CommunityManagerTokenResource(CommunityManagerTokenService communityManagerTokenService, LogoutService logoutService, OtherUtilityService otherUtilityService) {
        this.communityManagerTokenService = communityManagerTokenService;
        this.logoutService = logoutService;
        this.otherUtilityService = otherUtilityService;
    }

    @ApiOperation(value = "Generate JWT token - to access all REST resources", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({@ApiResponse(code = SC_UNAUTHORIZED, message = "UnAuthorized Request"),
            @ApiResponse(code = SC_OK, message = "Success"),
            @ApiResponse(code = SC_BAD_REQUEST, message = "Bad Request")})
    @PostMapping(path = "generate-token", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(org.springframework.http.HttpStatus.OK)
    public ResponseEntity<CommunityManagerUserModel> generateToken(HttpServletRequest httpServletRequest, @RequestBody CommunityManagerLoginModel cmProfile) {

        LOGGER.debug("In PragmaCMTokenResource, generateToken Method");

        boolean isSmLogin = otherUtilityService.getIsSMLogin();
        if (isSmLogin) {
            String username = httpServletRequest.getHeader(otherUtilityService.paramName());
            if (isNotNull(username)) {
                cmProfile.setUserName(username);
                LOGGER.debug("User Name : {}", username);
            }
        }

        return ResponseEntity.ok(communityManagerTokenService.authenticate(cmProfile, isSmLogin)
                .map(user -> user.setToken(communityManagerTokenService.tokenFor(user)))
                .orElseThrow(() -> new CommunityManagerTokenException("Exception while creating access token for" + cmProfile.getUserName())));
    }

    @ApiOperation(value = "Invalidate JWT token", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({@ApiResponse(code = SC_UNAUTHORIZED, message = "UnAuthorized Request"),
            @ApiResponse(code = SC_OK, message = "Success"),
            @ApiResponse(code = SC_BAD_REQUEST, message = "Bad Request")})
    @GetMapping(path = "logout", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityManagerResponse> invalidateToken(HttpServletRequest httpServletRequest) {
        logoutService.invalidateToken(httpServletRequest);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Token invalidated successfully!!!"));
    }

}
