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
//import com.pe.pcm.partner.OracleEBSPartnerService;
//import com.pe.pcm.protocol.OracleEbsModel;
//import com.pe.pcm.common.CommunityManagerResponse;
//import io.swagger.annotations.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import static com.pe.pcm.utils.CommonFunctions.OK;
//import static com.pe.pcm.utils.PCMConstants.*;
//import static javax.servlet.http.HttpServletResponse.*;
//import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
//import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
//
//
//@RestController
//@RequestMapping(value = "pcm/partner/oracle-ebs", produces = APPLICATION_JSON_VALUE)
//@Api(tags = {"Partner Profile With ORACLE_EBS Resource"})
//public class OracleEBSPartnerResource {
//
//    private OracleEBSPartnerService oracleEBSPartnerService;
//
//    @Autowired
//    public OracleEBSPartnerResource(OracleEBSPartnerService oracleEBSPartnerService) {
//        this.oracleEBSPartnerService = oracleEBSPartnerService;
//    }
//
//    @ApiOperation(value = "Create ORACLE_EBS Profile", authorizations = {@Authorization(value = "apiKey")})
//    @ApiResponses({
//            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
//            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
//            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
//    })
//    @PostMapping
//    public ResponseEntity<CommunityManagerResponse> save(@Validated @RequestBody OracleEbsModel oracleEBSModel) {
//        oracleEBSPartnerService.save(oracleEBSModel);
//        return ResponseEntity.ok(OK.apply(PARTNER_CREATE));
//    }
//
//    @ApiOperation(value = "Update ORACLE_EBS Profile", authorizations = {@Authorization(value = "apiKey")})
//    @ApiResponses({
//            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
//            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
//            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
//    })
//    @PutMapping
//    public ResponseEntity<CommunityManagerResponse> update(@Validated @RequestBody OracleEbsModel oracleEBSModel) {
//        oracleEBSPartnerService.update(oracleEBSModel);
//        return ResponseEntity.ok(OK.apply(PARTNER_UPDATE));
//    }
//
//
//    @ApiOperation(value = "Get ORACLE_EBS Profile", authorizations = {@Authorization(value = "apiKey")})
//    @ApiResponses({
//            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
//            @ApiResponse(code = SC_NOT_FOUND, message = "no record found")
//    })
//    @GetMapping(path = "{pkId}")
//    public ResponseEntity<OracleEbsModel> get(@Validated @PathVariable String pkId) {
//        return ResponseEntity.ok(oracleEBSPartnerService.get(pkId));
//    }
//
//    @ApiOperation(value = "Delete ORACLE_EBS Profile", authorizations = {@Authorization(value = "apiKey")})
//    @ApiResponses({
//            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
//            @ApiResponse(code = SC_NOT_FOUND, message = "no record found")
//    })
//    @DeleteMapping(path = "{pkId}")
//    public ResponseEntity<CommunityManagerResponse> delete(@Validated @PathVariable String pkId) {
//        oracleEBSPartnerService.delete(pkId);
//        return ResponseEntity.ok(OK.apply(PARTNER_DELETE));
//    }
//}
