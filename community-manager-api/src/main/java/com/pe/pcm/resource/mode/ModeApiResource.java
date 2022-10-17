package com.pe.pcm.resource.mode;

import com.pe.pcm.mode.CommonModel;
import com.pe.pcm.mode.ModeApiService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static javax.servlet.http.HttpServletResponse.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "pem/mode", produces = APPLICATION_JSON_VALUE)
@Api(tags = {"Proxy API For Mode LightWell APIs"}, description = "Mode Document Search Resource")
public class ModeApiResource {

    private final ModeApiService modeApiService;

    @Autowired
    public ModeApiResource(ModeApiService modeApiService) {
        this.modeApiService = modeApiService;
    }

    @ApiOperation(value = "Proxy API For Document Search light well API.", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PostMapping(path = "/document-search")
    public ResponseEntity<String> documentSearch(@Validated @RequestBody CommonModel commonModel) {
        return ResponseEntity.ok(modeApiService.documentSearch(commonModel));
    }

    @ApiOperation(value = "Proxy API For Add An Identity light well API.", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PostMapping(path = "/add-identity")
    public ResponseEntity<String> addIdentity(@Validated @RequestBody CommonModel commonModel) {
        return ResponseEntity.ok(modeApiService.modeActualPostAPIForIdentity(commonModel));
    }

    @ApiOperation(value = "Proxy API For update An Identity light well API.", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PutMapping(path = "/update-identity")
    public ResponseEntity<String> updateIdentity(@Validated @RequestBody CommonModel commonModel, @RequestParam String identId) {
        return ResponseEntity.ok(modeApiService.modeActualPutAPIForIdentity(commonModel, identId));
    }


    @ApiOperation(value = "Proxy API For Search identities convenience method light well API.", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PostMapping(path = "/search-identity")
    public ResponseEntity<String> searchIdentity(@RequestBody CommonModel commonModel, @RequestParam String search) {
        return ResponseEntity.ok(modeApiService.identitySearch(commonModel, search));
    }


    @ApiOperation(value = "Proxy API For Send Rule Search convenience method light well API.", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PostMapping(path = "/search-sendRule")
    public ResponseEntity<String> sendRuleSearch(@RequestBody CommonModel commonModel) {
        return ResponseEntity.ok(modeApiService.sendRuleSearch(commonModel));
    }

    @ApiOperation(value = "Proxy API For Create send Rule convenience method light well API.", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PostMapping(path = "/create-sendRule")
    public ResponseEntity<String> addRule(@RequestBody CommonModel commonModel) {
        return ResponseEntity.ok(modeApiService.addRule(commonModel));
    }

    @ApiOperation(value = "Proxy API For  update An Send Rule light well API.", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })

    @PutMapping(path = "/update-sendRule")
    public ResponseEntity<String> updateAddRule(@Validated @RequestBody CommonModel commonModel, @RequestParam String id) {
        return ResponseEntity.ok(modeApiService.modeActualPutAPIForAddRule(commonModel, id));
    }

}
