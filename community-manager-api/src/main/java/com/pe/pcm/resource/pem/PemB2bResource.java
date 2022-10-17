/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://pragmaedge.com/licenseagreement
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pe.pcm.resource.pem;

import com.pe.pcm.b2b.B2bCdNodeModel;
import com.pe.pcm.b2b.deserialize.B2bCdNodesDeserializeModel;
import com.pe.pcm.common.CommunityManagerNameModel;
import com.pe.pcm.common.CommunityManagerResponse;
import com.pe.pcm.common.CommunityMangerModel;
import com.pe.pcm.miscellaneous.CertificateUserUtilityService;
import com.pe.pcm.pem.*;
import com.pe.pcm.utils.CommonFunctions;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.pe.pcm.constants.AuthoritiesConstants.SA_OB_BA;
import static com.pe.pcm.constants.AuthoritiesConstants.SA_OB_BA_BU;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;


/**
 * @author Kiran Reddy.
 */
@RestController
@RequestMapping(value = "pem/b2b/si", produces = APPLICATION_JSON_VALUE)
@Api(tags = {"IBM Sterling B2B Integrator API"}, description = "IBM Sterling B2B Integrator API for PEM Resource")
public class PemB2bResource {

    private final PemB2bService pemB2bService;
    private final CertificateUserUtilityService certificateUserUtilityService;

    @Autowired
    public PemB2bResource(PemB2bService pemB2bService, CertificateUserUtilityService certificateUserUtilityService) {
        this.pemB2bService = pemB2bService;
        this.certificateUserUtilityService = certificateUserUtilityService;
    }

    @ApiOperation(value = "Get IBM Sterling B2B Integrator User Accounts ", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "get-user-accounts", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public ResponseEntity<CommunityMangerModel<CommunityManagerNameModel>> getUserAccounts(String userName, Boolean isLike) {
        return ResponseEntity.ok(new CommunityMangerModel<>(pemB2bService.findUserAccounts(userName, isLike)));
    }

    @ApiOperation(value = "Search RCT using provisional facts", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "get-rct-by-facts/by-value", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityManagerNameModel> getRCTbyProvFacts(String factValue) {
        return ResponseEntity.ok(pemB2bService.getRCTbyProvFacts(factValue));
    }

    @ApiOperation(value = "Search Trusted Cert by name", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "get-trusted-cert-id/by-name")
    public ResponseEntity<CommunityMangerModel<CommunityManagerNameModel>> getTrustedCertInfoId(String certName, Boolean isLike) {
        return ResponseEntity.ok(new CommunityMangerModel<>(certificateUserUtilityService.getTrustedCertInfoIdList(certName, isLike)));
    }

    @ApiOperation(value = "Search System Cert by namee", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "get-system-cert-id/by-name", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityMangerModel<CommunityManagerNameModel>> getSystemCertId(String certName, Boolean isLike) {
        return ResponseEntity.ok(new CommunityMangerModel<>(certificateUserUtilityService.getCertsAndPriKeyIdList(certName, isLike)));
    }

    @ApiOperation(value = "Search SSH User Key by name", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "get-ssh-user-key/by-name", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityMangerModel<CommunityManagerNameModel>> getSshUserKey(String certName, Boolean isLike) {
        return ResponseEntity.ok(new CommunityMangerModel<>(certificateUserUtilityService.getSshUserKeyCertList(certName, isLike)));
    }

    @ApiOperation(value = "Search SSH KnownHost Key by name", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "get-ssh-khost-key/by-name", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityMangerModel<CommunityManagerNameModel>> getSshKHostKeyList(String certName, Boolean isLike) {
        return ResponseEntity.ok(new CommunityMangerModel<>(certificateUserUtilityService.getSshKHostKeyListByName(certName, isLike)));
    }

    @ApiOperation(value = "Search Remote FTP/FTPS/SFTP Profiles", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "get-sfg-profiles/by-name", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityMangerModel<PemSfgResponseModel>> getSfgFtpFtpsAndSftpProfiles(String profileName, Boolean isLike) {
        return ResponseEntity.ok(new CommunityMangerModel<>(pemB2bService.findAllRemoteFtpProfiles(profileName, isLike)));
    }

    @ApiOperation(value = "Search Remote SFTP Profiles", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "/pem/b2b/si/get-sfg-sftp-profiles/by-name", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public ResponseEntity<CommunityMangerModel<PemSfgResponseModel>> getSfgSftpProfiles(String profileName, Boolean isLike) {
        return ResponseEntity.ok(new CommunityMangerModel<>(pemB2bService.findAllRemoteSftpProfileByName(profileName, isLike)));
    }

    @ApiOperation(value = "Search Remote SFTP Profiles", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @PostMapping(path = "/pem/b2b/si/get-sfg-sftp-profiles/by-name", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public ResponseEntity<CommunityMangerModel<PemSfgResponseModel>> getSfgSftpProfiles1(@RequestBody CommunityMangerModel<PemProfileSearchModel> profileModelCommunityMangerModel) {
        return ResponseEntity.ok(new CommunityMangerModel<>(pemB2bService.findAllRemoteSftpProfileByNames(profileModelCommunityMangerModel.getContent())));
    }

    @ApiOperation(value = "Search CA Cert by name", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "get-ca-cert-id/by-name", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityMangerModel<CommunityManagerNameModel>> getCACertInfoList(String certName, Boolean isLike) {
        return ResponseEntity.ok(new CommunityMangerModel<>(certificateUserUtilityService.getCaCertInfoList(certName, isLike)));
    }

    @ApiOperation(value = "Search expired cert list with Xml", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "get-expiry-cert-list-xml", produces = APPLICATION_XML_VALUE)
    public ResponseEntity<CommunityMangerModel<PemAccountExpiryModel>> getExpiryCertListWithXml(int noOfDays) {
        return ResponseEntity.ok(new CommunityMangerModel<>(certificateUserUtilityService.getExpiryCertList(noOfDays)));
    }

    @ApiOperation(value = "Search expired cert list", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "get-expiry-cert-list", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityMangerModel<PemAccountExpiryModel>> getExpiryCertList(int noOfDays) {
        return ResponseEntity.ok(new CommunityMangerModel<>(certificateUserUtilityService.getExpiryCertList(noOfDays)));
    }

    @ApiOperation(value = "Search expired pri cert list with Xml", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "get-expiry-pri-cert-list-xml", produces = APPLICATION_XML_VALUE)
    public ResponseEntity<CommunityMangerModel<PemAccountExpiryModel>> getExpiryPriCertListWithXml(int noOfDays) {
        return ResponseEntity.ok(new CommunityMangerModel<>(certificateUserUtilityService.getExpiryPriCertList(noOfDays)));
    }

    @ApiOperation(value = "Reset user accounts by name", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA)
    @PostMapping(path = "reset-users", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityMangerModel<UserAcctPwdUpdatedModel>> resetUsers(@RequestBody CommunityMangerModel<PemAccountExpiryModel> pemAccountExpiryModels) {
        return ResponseEntity.ok(new CommunityMangerModel<>(pemB2bService.restUserAccounts(pemAccountExpiryModels.getContent())));
    }

    @ApiOperation(value = "Reset user accounts by name(Using B2BI)", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA)
    @PostMapping(path = "reset-users-by-b2bi", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityMangerModel<UserAcctPwdUpdatedModel>> resetUsersByB2Bi(@RequestBody CommunityMangerModel<PemAccountExpiryModel> pemAccountExpiryModels) {
        return ResponseEntity.ok(new CommunityMangerModel<>(pemB2bService.restUserAccountsByB2BiApi(pemAccountExpiryModels.getContent())));
    }

    @ApiOperation(value = "Reset user accounts by partner name", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA)
    @PostMapping(path = "reset-users-updated", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityMangerModel<PemAccountExpiryModel>> resetUsersUpdated(@RequestBody CommunityMangerModel<PemAccountExpiryModelTemp> pemAccountExpiryModelTemp) {
        return ResponseEntity.ok(new CommunityMangerModel<>(pemB2bService.restUserAccountsDup(pemAccountExpiryModelTemp.getContent())));
    }

    @ApiOperation(value = "Search Connect Direct Nodes", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @PostMapping(path = "get-cd-nodes", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityMangerModel<B2bCdNodesDeserializeModel>> getCdNodes(@RequestBody B2bCdNodeModel b2bCdNodeModel) {
        return ResponseEntity.ok(new CommunityMangerModel<>(pemB2bService.getCdNodes(b2bCdNodeModel)));
    }

    @ApiOperation(value = "Search Partner Profiles expired Users list", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "get-partner-profiles-expired-users-list", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityMangerModel<PemAccountExpiryModel>> getPartnerProfilesUsersExpiryList(int noOfDays) {
        return ResponseEntity.ok(new CommunityMangerModel<>(certificateUserUtilityService.getPartnerProfilesUsersExpiryList(noOfDays)));
    }

    @ApiOperation(value = "Reset user accounts (B2Bi-API)", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA)
    @PostMapping(path = "reset-user", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityManagerResponse> resetUsersByB2bApi(@RequestBody PemAccountExpiryModel pemAccountExpiryModel) {
        pemB2bService.restUserAccountsByB2BiApi(pemAccountExpiryModel);
        return ResponseEntity.ok(CommonFunctions.OK.apply("Password updated successfully.!"));
    }


    @ApiOperation(value = "Update work flow name with contract", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA)
    @PutMapping (path = "update-contract", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityManagerResponse> contractUpdate(@RequestBody PemUpdateContractModel pemUpdateContractModel) {
        pemB2bService.updateContract(pemUpdateContractModel);
        return ResponseEntity.ok(CommonFunctions.OK.apply("contract updated successfully.!"));
    }

    @ApiOperation(value = "Add Identity To Group", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA)
    @PostMapping(path = "add-identity-to-group", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityManagerResponse> addIdentityToGroup(@RequestBody GroupAndIdentityModel groupAndIdentityModel) {
        pemB2bService.addIdentityToGroup(groupAndIdentityModel);
        return ResponseEntity.ok(CommonFunctions.OK.apply("Identity Added successfully to the Group..!"));
    }

}
