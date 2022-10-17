package com.pe.pcm.resource.accessmanagement;


import com.pe.pcm.chargeback.ChargeBackSlabsModel;
import com.pe.pcm.chargeback.ChargebackService;
import com.pe.pcm.common.CommunityManagerResponse;
import com.pe.pcm.reports.entity.PetpeChargebackSlabs;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

@RestController
@RequestMapping(path = "pcm/chargeback")
@Api(tags = {"Charge Back"})
public class ChargebackResource {

    private final ChargebackService chargebackService;

   @Autowired
    public ChargebackResource(ChargebackService chargebackService) {
        this.chargebackService = chargebackService;
    }

    @ApiOperation(value = "Get Chargeback Data", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @GetMapping(path = "chargeback-data")
    public ResponseEntity<PetpeChargebackSlabs> getChargebackData() {
        return ResponseEntity.ok(chargebackService.getChargebackData());
    }

    @ApiOperation(value = "update the Chargeback", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PutMapping(path = "update-chargeback")
    public ResponseEntity<CommunityManagerResponse> update(@Validated @RequestBody ChargeBackSlabsModel chargeBackSlabsModel) {
        chargebackService.update(chargeBackSlabsModel);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Chargeback Will Be Updated in 20 Minutes"));
    }

}
