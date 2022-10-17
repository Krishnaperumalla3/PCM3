package com.pe.pcm.resource.miscellaneous;

import com.pe.pcm.common.CommunityManagerKeyValueModel;
import com.pe.pcm.miscellaneous.UserActivityService;
import com.pe.pcm.user.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static javax.servlet.http.HttpServletResponse.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

/**
 * @author Shameer.v.
 */
@RestController
@RequestMapping(value = "pcm/user-activity", produces = APPLICATION_JSON_VALUE)
@Api(tags = {"Fetch User Activity"})
public class UserActivityResource {

    private final UserActivityService userActivityService;


    @Autowired
    public UserActivityResource(UserActivityService userActivityService) {

        this.userActivityService = userActivityService;
    }

    @ApiOperation(value = "API To Fetch User Activity", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
    })
    @GetMapping(produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public ResponseEntity<CommunityManagerKeyValueModel> getUserActivity(@RequestParam String userId) {
        return ResponseEntity.ok(userActivityService.getUserActivity(userId));
    }

}
