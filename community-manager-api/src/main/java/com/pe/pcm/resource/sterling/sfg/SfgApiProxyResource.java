package com.pe.pcm.resource.sterling.sfg;

import com.pe.pcm.common.CommunityManagerNameModel;
import com.pe.pcm.sfg.SFGApiService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.pe.pcm.constants.AuthoritiesConstants.SA_OB_BA;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Kiran Reddy.
 */
@RestController
@RequestMapping(path = "pcm/proxy/sfgapis", produces = APPLICATION_JSON_VALUE)
@Api(tags = {"SFG APIs Proxy"}, description = "SFG APIs Proxy Resource")
@PreAuthorize(SA_OB_BA)
public class SfgApiProxyResource {

    private final SFGApiService sfgApiService;

    @Autowired
    public SfgApiProxyResource(SFGApiService sfgApiService) {
        this.sfgApiService = sfgApiService;
    }

    @ApiOperation(value = "Find Routing Channel Template Availability", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @GetMapping
    public ResponseEntity<List<CommunityManagerNameModel>> getRoutingChannelTemplateList(String createUserId, String partnerGroupKey, String templateName) {
        return ResponseEntity.ok(sfgApiService.getRoutingChannelTmpl(createUserId, partnerGroupKey, templateName));
    }

}
