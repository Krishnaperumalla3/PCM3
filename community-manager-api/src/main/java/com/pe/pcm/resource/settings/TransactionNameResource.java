package com.pe.pcm.resource.settings;

import com.pe.pcm.common.CommunityManagerResponse;
import com.pe.pcm.transactionnames.TransactionNamesService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.pe.pcm.constants.AuthoritiesConstants.SA_OB_BA;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Shameer.v.
 */
@RestController
@RequestMapping(value = "pcm/transaction-names", produces = APPLICATION_JSON_VALUE)
@Api(tags = {"Transaction Names"}, description = "Transaction Names Resource")
@PreAuthorize(SA_OB_BA)
public class TransactionNameResource {


    private final TransactionNamesService transactionNamesService;

    @Autowired
    public TransactionNameResource(TransactionNamesService transactionNamesService) {
        this.transactionNamesService = transactionNamesService;
    }


    @ApiOperation(value = "Create/Update Transaction Names", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PutMapping
    public ResponseEntity<CommunityManagerResponse> save(@Validated @RequestBody List<String> transactionNames) {
        transactionNamesService.save(transactionNames);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Transaction Names saved successfully"));
    }

    @ApiOperation(value = "Get Transaction Names", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_NOT_FOUND, message = "Transaction Names not found")
    })
    @GetMapping
    public ResponseEntity<List<String>> get() {
        return ResponseEntity.ok(transactionNamesService.get());
    }

}
