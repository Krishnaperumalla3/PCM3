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

import com.pe.pcm.certificate.ManageCertificateService;
import com.pe.pcm.common.CommunityManagerResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static com.pe.pcm.constants.AuthoritiesConstants.SA_OB_BA;
import static javax.servlet.http.HttpServletResponse.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Kiran Reddy.
 */
@RestController
@RequestMapping(value = "pcm/certificate", produces = APPLICATION_JSON_VALUE)
@Api(tags = {"Certificate Upload [CA/SSH/System/Trusted]"})
@PreAuthorize(SA_OB_BA)
public class CertificateResource {

    private final ManageCertificateService manageCertificateService;

    @Autowired
    public CertificateResource(ManageCertificateService manageCertificateService) {
        this.manageCertificateService = manageCertificateService;
    }

    @ApiOperation(value = "Upload CA Certificate", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PostMapping(path = "upload-ca-cert", consumes = "multipart/form-data")
    public ResponseEntity<CommunityManagerResponse> uploadCACert(@Validated @RequestParam("file") MultipartFile file, @RequestParam("certName") String certName) {
        manageCertificateService.uploadCACert(file, certName);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "CA Certificate uploaded successfully!!"));
    }

    @ApiOperation(value = "Upload Trusted Certificate", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PostMapping(path = "upload-trusted-cert", consumes = "multipart/form-data")
    public ResponseEntity<CommunityManagerResponse> uploadTrustedCert(@Validated @RequestParam("file") MultipartFile file, @RequestParam("certName") String certName) {
        manageCertificateService.uploadTrustedCert(file, certName);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Trusted Certificate uploaded successfully."));
    }

    @ApiOperation(value = "Upload System Certificate", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PostMapping(path = "upload-system-cert", consumes = "multipart/form-data")
    public ResponseEntity<CommunityManagerResponse> uploadSystemCert(@Validated @RequestParam("file") MultipartFile file, @RequestParam("certName") String certName,
                                                                     @RequestParam("certType") String certType, @RequestParam("privateKeyPassword") String privateKeyPassword) {
        manageCertificateService.uploadSystemCert(file, certName, certType, privateKeyPassword);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "System Certificate uploaded successfully."));
    }

    @ApiOperation(value = "Upload SSH KnownHost Key", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PostMapping(path = "upload-ssh-known-host-key", consumes = "multipart/form-data")
    public ResponseEntity<CommunityManagerResponse> uploadSshKnownHostKey(@Validated @RequestParam("file") MultipartFile file, @RequestParam("keyName") String keyName) {
        manageCertificateService.uploadSshKnownHostKey(file, keyName);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "SSH key uploaded successfully."));
    }

    @ApiOperation(value = "Upload SSH User Identity Key", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PostMapping(path = "upload-SSH-UID-key", consumes = "multipart/form-data")
    public ResponseEntity<CommunityManagerResponse> uploadUIDKey(@Validated @RequestParam("file") MultipartFile file, @RequestParam("keyName") String keyName) {
        manageCertificateService.uploadSSHUIDKey(file, keyName);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "SSH UID key uploaded successfully."));
    }

    @ApiOperation(value = "Upload SSH Authorised User Key", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PostMapping(path = "upload-SSH-authorized-user-key", consumes = "multipart/form-data")
    public ResponseEntity<CommunityManagerResponse> uploadSshAuthorizedUserKey(@Validated @RequestParam("file") MultipartFile file, @RequestParam("keyName") String keyName) {
        manageCertificateService.uploadSshAuthorizedUserKey(file, keyName);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "SSH Authorized User key uploaded successfully."));
    }
}
