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

import com.pe.pcm.certificate.ManageCertificateService;
import com.pe.pcm.common.CommunityManagerResponse;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.workflow.pem.PemImportCertModel;
import com.pe.pcm.workflow.pem.PemImportPGPModel;
import com.pe.pcm.workflow.pem.PgpPublicKeyModel;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.pe.pcm.constants.AuthoritiesConstants.SA_OB_BA;
import static com.pe.pcm.constants.AuthoritiesConstants.SA_OB_BA_BU;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

/**
 * @author Kiran Reddy.
 */
@RestController
@RequestMapping(value = "pem/certificate", produces = APPLICATION_JSON_VALUE)
@Api(tags = {"PEM Certificate [CA/SSH/USER-KEY/SSH-UID/TRUSTED/PGP] Resource"})
public class PemCertificateResource {

    private final ManageCertificateService manageCertificateService;

    @Autowired
    public PemCertificateResource(ManageCertificateService manageCertificateService) {
        this.manageCertificateService = manageCertificateService;
    }

    @ApiOperation(value = "Upload CA Cert", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_NOT_FOUND, message = "no record found")
    })
    @PreAuthorize(SA_OB_BA)
    @PostMapping(path = "upload-ca-cert")
    public ResponseEntity<CommunityManagerResponse> uploadCACert(@Validated @RequestBody PemImportCertModel pemImportCertModel) {
        try {
            manageCertificateService.uploadCACert(pemImportCertModel);
        } catch (CommunityManagerServiceException e) {
            return ResponseEntity.ok(new CommunityManagerResponse(200, e.getErrorMessage()));
        }
        return ResponseEntity.ok(new CommunityManagerResponse(200, "CA Certificate uploaded successfully!!"));
    }

    @ApiOperation(value = "Upload Trusted Cert", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_NOT_FOUND, message = "no record found")
    })
    @PreAuthorize(SA_OB_BA)
    @PostMapping(path = "upload-trusted-cert")
    public ResponseEntity<CommunityManagerResponse> uploadTrustedCert(@Validated @RequestBody PemImportCertModel pemImportCertModel) {
        try {
            manageCertificateService.uploadTrustedCert(pemImportCertModel);
        } catch (CommunityManagerServiceException e) {
            return ResponseEntity.ok(new CommunityManagerResponse(200, e.getErrorMessage()));
        }
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Trusted Certificate uploaded successfully."));
    }

    @ApiOperation(value = "Upload SSH KnownHostKey Cert", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_NOT_FOUND, message = "no record found")
    })
    @PreAuthorize(SA_OB_BA)
    @PostMapping(path = "upload-ssh-known-host-key")
    public ResponseEntity<CommunityManagerResponse> uploadSshKnownHostKey(@Validated @RequestBody PemImportCertModel pemImportCertModel) {
        try {
            manageCertificateService.uploadSshKnownHostKey(pemImportCertModel);
        } catch (CommunityManagerServiceException e) {
            return ResponseEntity.ok(new CommunityManagerResponse(200, e.getErrorMessage()));
        }
        return ResponseEntity.ok(new CommunityManagerResponse(200, "SSH key uploaded successfully."));
    }

    @ApiOperation(value = "Upload SSH-UID-Key Cert", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_NOT_FOUND, message = "no record found")
    })
    @PreAuthorize(SA_OB_BA)
    @PostMapping(path = "upload-SSH-UID-key")
    public ResponseEntity<CommunityManagerResponse> uploadUIDKey(@Validated @RequestBody PemImportCertModel pemImportCertModel) {
        try {
            manageCertificateService.uploadSSHUIDKey(pemImportCertModel);
        } catch (CommunityManagerServiceException e) {
            return ResponseEntity.ok(new CommunityManagerResponse(200, e.getErrorMessage()));
        }
        return ResponseEntity.ok(new CommunityManagerResponse(200, "SSH UID key uploaded successfully."));
    }

    @ApiOperation(value = "Upload SSH Authorised User Key Cert", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_NOT_FOUND, message = "no record found")
    })
    @PreAuthorize(SA_OB_BA)
    @PostMapping(path = "upload-SSH-authorized-user-key")
    public ResponseEntity<CommunityManagerResponse> uploadSshAuthorizedUserKey(@Validated @RequestBody PemImportCertModel pemImportCertModel) {
        try {
            manageCertificateService.uploadSshAuthorizedUserKey(pemImportCertModel);
        } catch (CommunityManagerServiceException e) {
            return ResponseEntity.ok(new CommunityManagerResponse(200, e.getErrorMessage()));
        }
        return ResponseEntity.ok(new CommunityManagerResponse(200, "SSH Authorized User key uploaded successfully."));
    }

    @ApiOperation(value = "Upload PGP PublicKeys", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_NOT_FOUND, message = "no record found")
    })
    @PreAuthorize(SA_OB_BA)
    @PostMapping(path = "upload-pgp-publickey", consumes = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE}, produces = {APPLICATION_XML_VALUE, APPLICATION_JSON_VALUE})
    public ResponseEntity<CommunityManagerResponse> uploadPGPCert(@Validated @RequestBody PemImportPGPModel pemImportPGPModel) {
        try {
            manageCertificateService.uploadPGPCert(pemImportPGPModel);
        } catch (CommunityManagerServiceException e) {
            return ResponseEntity.ok(new CommunityManagerResponse(200, e.getErrorMessage()));
        }
        return ResponseEntity.ok(new CommunityManagerResponse(200, "PGP PublicKey uploaded successfully!!"));
    }

    //Temporarily disabled on alok request.
   /* @ApiOperation(value = "Get PGP PublicKey", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_NOT_FOUND, message = "no record found")
    })
    @GetMapping(path = "get-pgp-publickey",consumes = {APPLICATION_JSON_VALUE,APPLICATION_XML_VALUE},produces = {APPLICATION_XML_VALUE,APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> getPGPCert(@RequestParam String certName) {
        String resp = "";
        try {
            resp = manageCertificateService.getPGPCert(certName);
        } catch (CommunityManagerServiceException e) {
            return ResponseEntity.ok(new CommunityManagerResponse(200, e.getErrorMessage()));
        }
        return ResponseEntity.ok(resp);
    }*/

    @ApiOperation(value = "Get PGP PublicKey DB", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_NOT_FOUND, message = "no record found")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "get-pgp-publickey-local", produces = {APPLICATION_XML_VALUE, APPLICATION_JSON_VALUE})
    public ResponseEntity<List<PgpPublicKeyModel>> getPGPCertLocal(@RequestParam String certName) {
        return ResponseEntity.ok(manageCertificateService.getPGPCertLocal(certName));
    }

    @ApiOperation(value = "Delete PGP PublicKey", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_NOT_FOUND, message = "no record found")
    })
    @PreAuthorize(SA_OB_BA)
    @DeleteMapping(path = "delete-pgp-publickey", produces = {APPLICATION_XML_VALUE, APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> deletePGPCert(@RequestParam String certName) {
        try {
            manageCertificateService.deletePGPCert(certName);
        } catch (CommunityManagerServiceException e) {
            return ResponseEntity.ok(new CommunityManagerResponse(200, e.getErrorMessage()));
        }
        return ResponseEntity.ok(new CommunityManagerResponse(200, "PGP PublicKey Deleted successfully!!"));
    }

}
