package com.pe.pcm.resource.miscellaneous;

import com.pe.pcm.apiconnect.OutLookApiModel;
import com.pe.pcm.apiconnect.OutLookApiResponse;
import com.pe.pcm.apiconnect.OutLookEmailModel;
import com.pe.pcm.common.CommunityMangerModel;
import com.pe.pcm.miscellaneous.OutLookApiService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static javax.servlet.http.HttpServletResponse.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

/**
 * @author Shameer.v.
 */
@RestController
@RequestMapping(path = "outlook-apis")
@Api(tags = {"Out Look API Resource"})
public class OutLookApiResource {

    private final OutLookApiService outLookApiService;

    @Autowired
    public OutLookApiResource(OutLookApiService outLookApiService) {
        this.outLookApiService = outLookApiService;
    }

    @ApiOperation(value = "API For Outlook Undelivered Mails Response", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "method not implemented")
    })
    @PostMapping(path = "/getOutlookApi", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public ResponseEntity<CommunityMangerModel<OutLookApiResponse>> getOutLookAPI(@RequestBody OutLookApiModel outLookApiModel,
                                                                                  HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(new CommunityMangerModel<>(outLookApiService.getOutLookAPI(outLookApiModel, httpServletRequest)));
    }

    @ApiOperation(value = "API For sending mail to Outlook", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "method not implemented")
    })
    @PostMapping(path = "/sendMailOutlookApi", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public ResponseEntity<String> sendMailToOutlook(@RequestBody OutLookEmailModel outLookEmailModel) {
        return ResponseEntity.ok(outLookApiService.sendMailToOutlook(outLookEmailModel));
    }
}
