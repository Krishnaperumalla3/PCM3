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

package com.pe.pcm.resource.miscellaneous;

import com.pe.pcm.adapter.AdapterNamesModel;
import com.pe.pcm.adapter.AdapterNamesService;
import com.pe.pcm.common.CommunityManagerKeyValueModel;
import com.pe.pcm.common.CommunityManagerNameModel;
import com.pe.pcm.common.CommunityManagerResponse;
import com.pe.pcm.common.CommunityMangerModel;
import com.pe.pcm.login.CommunityManagerLoginModel;
import com.pe.pcm.login.LogoModel;
import com.pe.pcm.login.SMLoginModel;
import com.pe.pcm.miscellaneous.CertificateUserUtilityService;
import com.pe.pcm.miscellaneous.OtherUtilityService;
import com.pe.pcm.miscellaneous.ProtocolUtilityService;
import com.pe.pcm.sterling.SterlingEntityService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.pe.pcm.constants.AuthoritiesConstants.SA_OB_BA_BU;
import static com.pe.pcm.utils.CommonFunctions.OK;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Kiran Reddy.
 */
@RestController
@RequestMapping(value = "pcm/utility", produces = APPLICATION_JSON_VALUE)
@Api(tags = {"Community Manager Utility Resource"})
public class UtilityResource {

    private final ProtocolUtilityService protocolUtilityService;
    private final CertificateUserUtilityService certificateUserUtilityService;
    private final SterlingEntityService sterlingEntityService;
    private final AdapterNamesService adapterNamesService;
    private final OtherUtilityService otherUtilityService;
    private final Environment environment;

    @Autowired
    public UtilityResource(ProtocolUtilityService protocolUtilityService,
                           CertificateUserUtilityService certificateUserUtilityService,
                           SterlingEntityService sterlingEntityService,
                           AdapterNamesService adapterNamesService,
                           OtherUtilityService otherUtilityService,
                           Environment environment) {
        this.protocolUtilityService = protocolUtilityService;
        this.certificateUserUtilityService = certificateUserUtilityService;
        this.sterlingEntityService = sterlingEntityService;
        this.adapterNamesService = adapterNamesService;
        this.otherUtilityService = otherUtilityService;
        this.environment = environment;
    }

    @ApiOperation(value = "Get Protocols List", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @GetMapping(path = "protocols-list")
    public ResponseEntity<List<String>> getProtocolList() {
        List<String> protocolList = protocolUtilityService.getProtocolList();
        protocolList.removeIf(s -> s.equals("SMTP") || s.equals("ORACLE_EBS") || s.equals("FTP") || s.equals("FTPS") || s.equals("SFTP") || s.equals("AWS_S3"));
        return ResponseEntity.ok(protocolList);
    }

    @ApiOperation(value = "Get Protocols Map", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @GetMapping(path = "protocols-map")
    public ResponseEntity<List<CommunityManagerKeyValueModel>> getProtocolMap() {
        return ResponseEntity.ok(protocolUtilityService.getProtocolMap());
    }

    @ApiOperation(value = "Get Trusted Certs List", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "trusted-cert-list")
    public ResponseEntity<List<CommunityManagerNameModel>> getTrustedCertInfoList() {
        return ResponseEntity.ok(certificateUserUtilityService.getTrustedCertInfoList());
    }

    @ApiOperation(value = "Get Auth XrefSSH List", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "auth-xref-ssh-list")
    public ResponseEntity<List<CommunityManagerKeyValueModel>> getAuthXrefSshList() {
        return ResponseEntity.ok(certificateUserUtilityService.getAuthXrefSshList());
    }

    @ApiOperation(value = "Get Trusted Certs Map", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "trusted-cert-map")
    public ResponseEntity<List<CommunityManagerKeyValueModel>> getTrustedCertInfoMap() {
        return ResponseEntity.ok(certificateUserUtilityService.getTrustedCertInfoMap());
    }

    @ApiOperation(value = "Get CA Certs Map", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "ca-cert-map")
    public ResponseEntity<List<CommunityManagerKeyValueModel>> getCaCertInfoMap() {
        return ResponseEntity.ok(certificateUserUtilityService.getCaCertInfoMap());
    }

    @ApiOperation(value = "Get CA Certs List", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "ca-cert-list")
    public ResponseEntity<List<CommunityManagerNameModel>> getCaCertInfoList() {
        return ResponseEntity.ok(certificateUserUtilityService.getCaCertInfoList());
    }

    @ApiOperation(value = "Get KnownHostKey List", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "ssh-khost-key-cert-list")
    public ResponseEntity<List<CommunityManagerNameModel>> getSshKHostKeyList() {
        return ResponseEntity.ok(certificateUserUtilityService.getSshKHostKeyList());
    }

    @ApiOperation(value = "Get SSH Key Pair List", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "ssh-key-pair")
    public ResponseEntity<List<CommunityManagerNameModel>> getSshKeyPairList() {
        return ResponseEntity.ok(certificateUserUtilityService.getSshKeyPairList());
    }

    @ApiOperation(value = "Get System Cert Map", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "system-cert-map")
    public ResponseEntity<List<CommunityManagerKeyValueModel>> getSystemCertMap() {
        return ResponseEntity.ok(certificateUserUtilityService.getCertsAndPriKeyMap());
    }

    @ApiOperation(value = "Get SI BPs List", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "si-bp-list")
    public ResponseEntity<List<CommunityManagerNameModel>> getBpList() {
        return ResponseEntity.ok(sterlingEntityService.getBpList());
    }

    @ApiOperation(value = "Get Adapters List", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "adapter-names")
    public ResponseEntity<AdapterNamesModel> getAdapterNames() {
        return ResponseEntity.ok(adapterNamesService.getAdapterNames());
    }

    @ApiOperation(value = "Get Logo data", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @GetMapping(path = "get-logo")
    public ResponseEntity<LogoModel> getLogoData() {
        return ResponseEntity.ok(otherUtilityService.getLogoData());
    }

    @ApiOperation(value = "Get Active Profile", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @GetMapping(path = "active-profile")
    public ResponseEntity<SMLoginModel> getActiveProfile() {
        return ResponseEntity.ok(new SMLoginModel(otherUtilityService.getIsSMLogin(), environment.getActiveProfiles()[0]));
    }

    @ApiOperation(value = "Get SI Maps List", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "si-map-list")
    public ResponseEntity<List<CommunityManagerNameModel>> getMapList() {
        return ResponseEntity.ok(sterlingEntityService.getMapList());
    }

    @ApiOperation(value = "Get Envelop terminators map", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "envelop-terminators-map-list")
    public ResponseEntity<List<CommunityManagerKeyValueModel>> getTerminatorsMapList() {
        return ResponseEntity.ok(otherUtilityService.getTerminatorsMap());
    }

    @ApiOperation(value = "Validation for User Activities ", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "is-valid")
    public ResponseEntity<CommunityManagerResponse> isValidResource(@RequestBody CommunityManagerLoginModel cmProfile) {
        otherUtilityService.isValidResource(cmProfile);
        return ResponseEntity.ok(OK.apply("Valid Resource"));
    }

    @ApiOperation(value = "Get isB2B Active", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @GetMapping(path = "is-b2b-active")
    public ResponseEntity<Boolean> getB2bStatus() {
        return ResponseEntity.ok(otherUtilityService.getIsB2bActive());
    }

    @ApiOperation(value = "Allow MFT duplicates or not (true/false)", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "is-mft-duplicate")
    public ResponseEntity<Boolean> getIsMFTDuplicate() {
        return ResponseEntity.ok(otherUtilityService.getIsMftDuplicate());
    }

    @ApiOperation(value = "Get date range(which should be used in transferInfo search screen)", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @GetMapping(path = "search-date-range")
    public ResponseEntity<Integer> getTransferInfoDateRange() {
        return ResponseEntity.ok(otherUtilityService.getFileTransferTimeRange());
    }

    @ApiOperation(value = "Get SSH User Key List", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "get-ssh-user-key-list")
    public ResponseEntity<CommunityMangerModel<CommunityManagerNameModel>> getSshUserKeyList() {
        return ResponseEntity.ok(new CommunityMangerModel<>(certificateUserUtilityService.getSshUserKeyList()));
    }

    @ApiOperation(value = "Get CD Cipher Suits", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "cd/get-cipher-suites")
    public ResponseEntity<CommunityMangerModel<CommunityManagerKeyValueModel>> getCipherSuites() {
        return ResponseEntity.ok(new CommunityMangerModel<>(otherUtilityService.getCipherSuites()));
    }

    //TODO = Remove(Already in PEMB2B Resource)
    @ApiOperation(value = "Get Trusted Cert ID using Name", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "get-trusted-cert-id")
    public ResponseEntity<CommunityManagerNameModel> getTrustedCertInfoId(@RequestParam("certName") String certName) {
        return ResponseEntity.ok(certificateUserUtilityService.getTrustedCertInfoId(certName));
    }

    //TODO = Remove(Already in PEMB2B Resource)
    @ApiOperation(value = "Get System Cert Id using Name", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "get-system-cert-id")
    public ResponseEntity<CommunityManagerNameModel> getSystemCertId(@RequestParam("certName") String certName) {
        return ResponseEntity.ok(certificateUserUtilityService.getCertsAndPriKeyId(certName));
    }

    //TODO = Remove(Already in PEMB2B Resource)
    @ApiOperation(value = "Get SSH User using Name", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "get-ssh-user-key")
    public ResponseEntity<CommunityMangerModel<CommunityManagerNameModel>> getSshUserKey(@RequestParam("certName") String certName) {
        return ResponseEntity.ok(new CommunityMangerModel<>(certificateUserUtilityService.getSshUserKeyCertList(certName, true)));
    }

    @ApiOperation(value = "Get Clouds List(AWS-S3,....)", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "get-cloud-list")
    public ResponseEntity<CommunityMangerModel<CommunityManagerNameModel>> getCloudList() {
        return ResponseEntity.ok(new CommunityMangerModel<>(otherUtilityService.getCloudList()));
    }

    @ApiOperation(value = "Get AWS Regions", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "get-cloud-aws-regions")
    public ResponseEntity<CommunityMangerModel<CommunityManagerKeyValueModel>> getAwsRegions() {
        return ResponseEntity.ok(new CommunityMangerModel<>(otherUtilityService.getAwsRegions()));
    }

}
