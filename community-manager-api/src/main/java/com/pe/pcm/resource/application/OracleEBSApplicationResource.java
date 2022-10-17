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
//package com.pe.pcm.resource.application;
//
//import com.pe.pcm.application.OracleEBSApplicationService;
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
///**
// * @author Shameer.
// */
//
//@RestController
//@RequestMapping(value = "pcm/application/oracleEBS", produces = APPLICATION_JSON_VALUE)
//@Api(tags = {"Application With oracleEBS"})
//public class OracleEBSApplicationResource {
//
//        private final OracleEBSApplicationService oracleEBSApplicationService;
//
//        @Autowired
//        public OracleEBSApplicationResource(OracleEBSApplicationService oracleEBSApplicationService) {
//            this.oracleEBSApplicationService = oracleEBSApplicationService;
//        }
//
//        @ApiOperation(value = "Create oracleEBS Application Profile", authorizations = {@Authorization(value = "apiKey")})
//        @ApiResponses({
//                @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
//                @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
//                @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
//        })
//        @PostMapping
//        public ResponseEntity<CommunityManagerResponse> save(@Validated @RequestBody OracleEbsModel oracleEBSModel) {
//            oracleEBSApplicationService.save(oracleEBSModel);
//            return ResponseEntity.ok(OK.apply(APPLICATION_CREATE));
//        }
//
//        @ApiOperation(value = "Update oracleEBS Application Profile", authorizations = {@Authorization(value = "apiKey")})
//        @ApiResponses({
//                @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
//                @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
//                @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
//        })
//        @PutMapping
//        public ResponseEntity<CommunityManagerResponse> update(@Validated @RequestBody OracleEbsModel oracleEBSModel) {
//            oracleEBSApplicationService.update(oracleEBSModel);
//            return ResponseEntity.ok(OK.apply(APPLICATION_UPDATE));
//        }
//
//        @ApiOperation(value = "Get oracleEBS Application Profile", authorizations = {@Authorization(value = "apiKey")})
//        @ApiResponses({
//                @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
//                @ApiResponse(code = SC_NOT_FOUND, message = "no record found")
//        })
//        @GetMapping(path = "{pkId}")
//        public ResponseEntity<OracleEbsModel> get(@PathVariable String pkId) {
//            return ResponseEntity.ok(oracleEBSApplicationService.get(pkId));
//        }
//
//        @ApiOperation(value = "Delete oracleEBS Application Profile", authorizations = {@Authorization(value = "apiKey")})
//        @ApiResponses({
//                @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
//                @ApiResponse(code = SC_NOT_FOUND, message = "no record found")
//        })
//
//        @DeleteMapping(path = "{pkId}")
//        public ResponseEntity<CommunityManagerResponse> delete(@PathVariable String pkId) {
//            oracleEBSApplicationService.delete(pkId);
//            return ResponseEntity.ok(OK.apply(APPLICATION_DELETE));
//        }
//}
