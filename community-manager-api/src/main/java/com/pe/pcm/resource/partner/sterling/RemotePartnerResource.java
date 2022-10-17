//package com.pe.pcm.resource.partner.sterling;
//
//import com.pe.pcm.common.CommunityManagerResponse;
//import com.pe.pcm.partner.sterling.RemotePartnerService;
//import com.pe.pcm.protocol.RemoteProfileModel;
//import io.swagger.annotations.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import static com.pe.pcm.utils.CommonFunctions.OK;
//import static com.pe.pcm.utils.PCMConstants.*;
//import static javax.servlet.http.HttpServletResponse.*;
//import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
//
///**
// * @author Chenchu Kiran.
// */
//
//@RestController
//@RequestMapping(value = "pcm/si/partner/remote-ftp", produces = APPLICATION_JSON_VALUE)
//@Api(tags = {"Sterling FTP/FTPS/SFTP Partner on-boarding Resource"})
//public class RemotePartnerResource {
//
//    private RemotePartnerService remotePartnerService;
//
//    @Autowired
//    public RemotePartnerResource(RemotePartnerService remotePartnerService) {
//        this.remotePartnerService = remotePartnerService;
//    }
//
//    @ApiOperation(value = "Create PCM-SI Partner Profile", authorizations = {@Authorization(value = "apiKey")})
//    @ApiResponses({
//            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
//            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
//            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
//    })
//    @PostMapping
//    public ResponseEntity<CommunityManagerResponse> save(@Validated @RequestBody RemoteProfileModel remoteProfileModel) {
//        //TODO
//        remotePartnerService.save(remoteProfileModel);
//        return ResponseEntity.ok(OK.apply(PARTNER_CREATE));
//    }
//
//
//    @ApiOperation(value = "Update PCM-SI Partner Profile", authorizations = {@Authorization(value = "apiKey")})
//    @ApiResponses({
//            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
//            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
//            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
//    })
//    @PutMapping
//    public ResponseEntity<CommunityManagerResponse> update(@Validated @RequestBody RemoteProfileModel remoteProfileModel) {
//        //TODO
//        remotePartnerService.update(remoteProfileModel);
//        return ResponseEntity.ok(OK.apply(PARTNER_UPDATE));
//    }
//
//    @ApiOperation(value = "Get PCM-SI Partner Profile", authorizations = {@Authorization(value = "apiKey")})
//    @ApiResponses({
//            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
//            @ApiResponse(code = SC_NOT_FOUND, message = "no record found")
//    })
//    @GetMapping(path = "{pkId}")
//    public ResponseEntity<RemoteProfileModel> get(@Validated @PathVariable String pkId) {
//        return ResponseEntity.ok(remotePartnerService.get(pkId));
//    }
//
//    @ApiOperation(value = "Delete PCM-SI Partner Profile", authorizations = {@Authorization(value = "apiKey")})
//    @ApiResponses({
//            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
//            @ApiResponse(code = SC_NOT_FOUND, message = "no record found")
//    })
//    @DeleteMapping()
//    public ResponseEntity<CommunityManagerResponse> delete(@Validated @RequestParam("pkId") String pkId, @RequestParam("deleteArtifacts") Boolean deleteArtifacts) {
//        //TODO
//        remotePartnerService.delete(pkId);
//        return ResponseEntity.ok(OK.apply(PARTNER_DELETE));
//    }
//
//}
