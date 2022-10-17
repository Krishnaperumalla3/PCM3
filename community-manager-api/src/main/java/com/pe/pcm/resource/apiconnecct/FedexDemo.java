package com.pe.pcm.resource.apiconnecct;

import io.swagger.annotations.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.pe.pcm.utils.PCMConstants.REQUEST_PROCESSED_SUCCESSFULLY;
import static javax.servlet.http.HttpServletResponse.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Kiran Reddy.
 */
@RestController
@RequestMapping(path = "customer")
@Api(tags = {"Fedex Demo customer endpoints"})
@ConditionalOnProperty(
        value = "cm.api-connect-enabled",
        havingValue = "true")
public class FedexDemo {


    @ApiOperation(value = "ACME Partner endpoint", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "method not implemented")
    })
    @PostMapping(path = "acme", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> acmeEndpoint(@RequestBody String payload,
                                               HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(REQUEST_PROCESSED_SUCCESSFULLY);
    }

    @ApiOperation(value = "ABC Corp Partner endpoint", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "method not implemented")
    })
    @PostMapping(path = "abc-corp", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> abcCorpEndpoint(@RequestBody String payload,
                                                  HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(REQUEST_PROCESSED_SUCCESSFULLY);
    }

    @ApiOperation(value = "API for POST", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "method not implemented")
    })
    @PostMapping(path = "{customerName}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postAPI(@PathVariable String customerName, @RequestBody String payload,
                                          HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(REQUEST_PROCESSED_SUCCESSFULLY);
    }

}
