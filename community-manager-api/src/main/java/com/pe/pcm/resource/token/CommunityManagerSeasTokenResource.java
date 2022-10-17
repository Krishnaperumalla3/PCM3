/*
 *
 *  * Copyright (c) 2020 Pragma Edge Inc
 *  *
 *  * Licensed under the Pragma Edge Inc
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  * https://pragmaedge.com/licenseagreement
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.pe.pcm.resource.token;

import com.pe.pcm.common.CommunityManagerResponse;
import com.pe.pcm.common.DataModel;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.exception.CommunityManagerTokenException;
import com.pe.pcm.login.CommunityManagerLoginModel;
import com.pe.pcm.login.CommunityManagerSeasTokenService;
import com.pe.pcm.login.CommunityManagerUserModel;
import com.pe.pcm.logout.LogoutService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.pe.pcm.constants.ProfilesConstants.SSO_SSP_SEAS;
import static javax.servlet.http.HttpServletResponse.*;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Kiran Reddy.
 */
@RestController
@RequestMapping(path = "pcm")
@Profile(SSO_SSP_SEAS)
@Api(tags = {"Access Token Generator (SSP)"}, hidden = true)
public class CommunityManagerSeasTokenResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommunityManagerSeasTokenResource.class);

    private final LogoutService logoutService;
    private final CommunityManagerSeasTokenService communityManagerSeasTokenService;

    @Autowired
    public CommunityManagerSeasTokenResource(LogoutService logoutService, CommunityManagerSeasTokenService communityManagerSeasTokenService) {
        this.logoutService = logoutService;
        this.communityManagerSeasTokenService = communityManagerSeasTokenService;
    }

    @ApiOperation(value = "Get SEAS token info")
    @ApiResponses({@ApiResponse(code = SC_UNAUTHORIZED, message = "UnAuthorized Request"),
            @ApiResponse(code = SC_OK, message = "Success"),
            @ApiResponse(code = SC_BAD_REQUEST, message = "Bad Request")})
    @GetMapping(path = "seas/get-token-info", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public ResponseEntity<CommunityManagerUserModel> getTokenInfo(HttpServletRequest request, @RequestParam String userName, @RequestParam String token) {
        LOGGER.debug("In PragmaCMTokenResource, generateSeasToken Method");
        return ResponseEntity.ok(communityManagerSeasTokenService.getTokenDetails(userName, token, request.getCookies())
                .map(user -> user.setToken(communityManagerSeasTokenService.tokenFor(user)))
                .orElseThrow(() -> new CommunityManagerServiceException(HttpStatus.NOT_ACCEPTABLE.value(), "Exception while creating access token for" + userName)));

    }

    @ApiOperation(value = "Get SSP Logout URL")
    @ApiResponses({@ApiResponse(code = SC_UNAUTHORIZED, message = "UnAuthorized Request"),
            @ApiResponse(code = SC_OK, message = "Success"),
            @ApiResponse(code = SC_BAD_REQUEST, message = "Bad Request")})
    @GetMapping(path = "seas/get-ssp-logout-url", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public ResponseEntity<DataModel> getSspLogoutURI() {
        return ResponseEntity.ok(new DataModel().setData(communityManagerSeasTokenService.getSspLogoutURL()));
    }

    @ApiOperation(value = "Generate JWT token - to access all REST resources")
    @ApiResponses({@ApiResponse(code = SC_UNAUTHORIZED, message = "UnAuthorized Request"),
            @ApiResponse(code = SC_OK, message = "Success"),
            @ApiResponse(code = SC_BAD_REQUEST, message = "Bad Request")})
    @PostMapping(path = "generate-token", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(org.springframework.http.HttpStatus.OK)
    public ResponseEntity<CommunityManagerUserModel> generateToken(@RequestBody CommunityManagerLoginModel cmProfile) {

        LOGGER.debug("In PragmaCMTokenResource, generateToken Method");
        return ResponseEntity.ok(communityManagerSeasTokenService.authenticate(cmProfile)
                .map(user -> user.setToken(communityManagerSeasTokenService.tokenFor(user)))
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
