///*
// * Copyright (c) 2020 Pragma Edge Inc
// *
// * Licensed under the Pragma Edge Inc
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// * https://pragmaedge.com/licenseagreement
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package com.pe.pcm.resource.partner;
//
//import com.pe.pcm.common.CommunityManagerResponse;
//import com.pe.pcm.partner.AS2PartnerService;
//import com.pe.pcm.protocol.As2Model;
//import com.pe.pcm.protocol.As2RelationMapModel;
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
//@RestController
//@RequestMapping("pcm/partner/as2")
//@Api(tags = {"Partner Profile With AS2 Resource"})
//public class AS2PartnerResource {
//
//    private final AS2PartnerService as2PartnerService;
//
//    @Autowired
//    public AS2PartnerResource(AS2PartnerService as2PartnerService) {
//        this.as2PartnerService = as2PartnerService;
//    }
//
////    @ApiOperation(value = "Save AS2 Trading Partner", authorizations = {@Authorization(value = "apiKey")})
////    @ApiResponses({@ApiResponse(code = SC_OK, message = "Success"),
////            @ApiResponse(code = SC_UNAUTHORIZED, message = "UnAuthorized Request"),
////            @ApiResponse(code = SC_CONFLICT, message = "resource already exists")})
////    @PostMapping()
////    public ResponseEntity<CommunityManagerResponse> saveAS2TradingPartner(@Validated @RequestBody As2Model as2Model) {
////        as2PartnerService.saveAs2(as2Model, false);
////        return ResponseEntity.ok(OK.apply(PARTNER_CREATE));
////    }
////
////    @ApiOperation(value = "Update AS2 Trading Partner", authorizations = {@Authorization(value = "apiKey")})
////    @ApiResponses({@ApiResponse(code = SC_OK, message = "Success"),
////            @ApiResponse(code = SC_UNAUTHORIZED, message = "UnAuthorized Request"),
////            @ApiResponse(code = SC_NOT_FOUND, message = "No Record Found")})
////    @PutMapping()
////    public ResponseEntity<CommunityManagerResponse> updateAS2TradingPartner(@Validated @RequestBody As2Model as2Model) {
////        as2PartnerService.update(as2Model);
////        return ResponseEntity.ok(OK.apply(PARTNER_UPDATE));
////    }
////
////    @ApiOperation(value = "Get AS2 Trading Partner", authorizations = {@Authorization(value = "apiKey")})
////    @ApiResponses(value = {@ApiResponse(code = SC_OK, message = "Success"),
////            @ApiResponse(code = SC_UNAUTHORIZED, message = "UnAuthorized Request"),
////            @ApiResponse(code = SC_NOT_FOUND, message = "No Record Found")})
////    @GetMapping(path = "{pkId}", produces = APPLICATION_JSON_VALUE)
////    public ResponseEntity<As2Model> getAS2TradingPartner(@PathVariable String pkId) {
////        return ResponseEntity.ok(as2PartnerService.get(pkId));
////    }
////
////    @ApiOperation(value = "Delete AS2 Trading Partner", authorizations = {@Authorization(value = "apiKey")})
////    @ApiResponses(value = {@ApiResponse(code = SC_OK, message = "Success"),
////            @ApiResponse(code = SC_UNAUTHORIZED, message = "UnAuthorized Request"),
////            @ApiResponse(code = SC_NOT_FOUND, message = "No Record Found")})
////    @DeleteMapping(path = "{pkId}", produces = APPLICATION_JSON_VALUE)
////    public ResponseEntity<CommunityManagerResponse> deleteAS2TradingPartner(@PathVariable String pkId) {
////        as2PartnerService.delete(pkId);
////        return ResponseEntity.ok(OK.apply(PARTNER_DELETE));
////    }
//
//    @ApiOperation(value = "As2 Org Profile And Partner Profile Mapping", authorizations = {@Authorization(value = "apiKey")})
//    @ApiResponses({@ApiResponse(code = SC_OK, message = "Success"),
//            @ApiResponse(code = SC_UNAUTHORIZED, message = "UnAuthorized Request"),
//            @ApiResponse(code = SC_CONFLICT, message = "resource already exists")})
//    @PostMapping(path = "org-profile/mapping")
//    public ResponseEntity<CommunityManagerResponse> as2OrgAndPartnerProfilesMapping(@Validated @RequestBody As2RelationMapModel as2RelationMapModel) {
//        as2PartnerService.createAs2Mapping(as2RelationMapModel);
//        return ResponseEntity.ok(OK.apply(PARTNER_CREATE));
//    }
//
//}
