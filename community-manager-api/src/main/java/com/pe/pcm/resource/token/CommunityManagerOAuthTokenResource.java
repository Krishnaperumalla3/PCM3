package com.pe.pcm.resource.token;

import com.pe.pcm.common.CommunityManagerResponse;
import com.pe.pcm.exception.CommunityManagerTokenException;
import com.pe.pcm.login.CommunityManagerUserModel;
import com.pe.pcm.login.CommunityMangerOAuthTokenService;
import com.pe.pcm.logout.LogoutService;
import com.pe.pcm.oauth2.OAuthInputModel;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static javax.servlet.http.HttpServletResponse.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Kiran Reddy.
 */
@RestController
@RequestMapping(path = "pcm/oauth")
@Api(tags = {"Access Token Generator (OAuth)"}, hidden = true)
public class CommunityManagerOAuthTokenResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommunityManagerOAuthTokenResource.class);

    private final LogoutService logoutService;
    private final CommunityMangerOAuthTokenService communityMangerOAuthTokenService;

    @Autowired
    public CommunityManagerOAuthTokenResource(LogoutService logoutService,
                                              CommunityMangerOAuthTokenService communityMangerOAuthTokenService) {
        this.logoutService = logoutService;
        this.communityMangerOAuthTokenService = communityMangerOAuthTokenService;
    }


    @ApiOperation(value = "Generate Access token - to access all REST resources", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({@ApiResponse(code = SC_UNAUTHORIZED, message = "UnAuthorized Request"),
            @ApiResponse(code = SC_OK, message = "Success"),
            @ApiResponse(code = SC_BAD_REQUEST, message = "Bad Request")})
    @PostMapping(path = "generate-token",
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(org.springframework.http.HttpStatus.OK)
    public ResponseEntity<CommunityManagerUserModel> generateToken(HttpServletRequest httpServletRequest, OAuthInputModel oAuthInputModel) {
        LOGGER.debug("In CommunityManagerOAuthTokenResource, generateToken Method");
        return ResponseEntity.ok(communityMangerOAuthTokenService.authenticate(oAuthInputModel, httpServletRequest)
                .map(user -> user.setToken(communityMangerOAuthTokenService.tokenFor(user)))
                .orElseThrow(() -> new CommunityManagerTokenException("Exception while creating access token for" + oAuthInputModel.getUsername())));
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
