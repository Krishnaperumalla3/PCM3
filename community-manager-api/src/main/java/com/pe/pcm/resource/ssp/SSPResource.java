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

package com.pe.pcm.resource.ssp;

import com.pe.pcm.common.CommunityManagerNameModel;
import com.pe.pcm.common.CommunityMangerModel;
import com.pe.pcm.pem.PemAccountExpiryModel;
import com.pe.pcm.ssp.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.pe.pcm.constants.AuthoritiesConstants.SA_OB_BA;
import static javax.servlet.http.HttpServletResponse.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

/**
 * @author Kiran Reddy.
 */
@RestController
@RequestMapping(value = "pem/ssp", produces = APPLICATION_XML_VALUE)
@Api(tags = {"SSP-GET-PUT-POST-DELETE Operations For FTP/SFTP/HTTP/CD"})
@PreAuthorize(SA_OB_BA)
public class SSPResource {

    private final SSPApiService sspApiService;

    @Autowired
    public SSPResource(SSPApiService sspApiService) {
        this.sspApiService = sspApiService;
    }

    @ApiOperation(value = "SSP-Node-Creation For FTP/SFTP/HTTP/CD", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PostMapping(path = "/ssp-node", consumes = APPLICATION_XML_VALUE, produces = APPLICATION_XML_VALUE)
    public XmlResponseModel save(@RequestBody InboundNodesModel inboundNodesModel, @RequestParam("netMapName") String netMapName) {
        return sspApiService.addNodeToNetMap(inboundNodesModel, netMapName);
    }

    @ApiOperation(value = "SSP-Node-Update For FTP/SFTP/HTTP/CD", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PutMapping(path = "/ssp-node-update", consumes = APPLICATION_XML_VALUE, produces = APPLICATION_XML_VALUE)
    public XmlResponseModel update(@RequestBody InboundNodesModel inboundNodesModel, @RequestParam("netMapName") String netMapName) {
        return sspApiService.updateNetMapNode(inboundNodesModel, netMapName);
    }

    @ApiOperation(value = "SSP-GET-NETMAP", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @GetMapping(path = "/ssp-get-netmap", produces = {APPLICATION_XML_VALUE, APPLICATION_JSON_VALUE})
    public XmlResponseModel getSSPNetMap(@RequestParam("netMapName") String netMapName) {
        return sspApiService.getSSPNetMap(netMapName);
    }

    @ApiOperation(value = "SSP-Check-Node-Availability", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PostMapping(path = "/ssp-check-node-availability", consumes = APPLICATION_XML_VALUE, produces = APPLICATION_XML_VALUE)
    public ResponseEntity<CommunityManagerNameModel> getSSPNodeAvailability(@RequestBody NodeAvailabilityModel nodeAvailabilityModel) {
        return ResponseEntity.ok(new CommunityManagerNameModel(sspApiService.getSSPNodeAvailability(nodeAvailabilityModel)));
    }

    @ApiOperation(value = "SSP-GET-ALL-NETMAPS", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @GetMapping(path = "/ssp-get-all-netmaps", produces = APPLICATION_XML_VALUE)
    public XmlResponseModel getSSPAllNetMaps() {
        return sspApiService.getSSPAllNetMaps();
    }

    @ApiOperation(value = "SSP-GET-ALL-POLICIES", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @GetMapping(path = "/ssp-get-all-policies", produces = APPLICATION_XML_VALUE)
    public XmlResponseGetModel getAllPolicies() {
        return sspApiService.getAllPolicies();
    }

    @ApiOperation(value = "SSP-GET-ALL-KEYSTORES", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @GetMapping(path = "/ssp-get-all-keystores", produces = APPLICATION_XML_VALUE)
    public XmlResponseGetModel getAllKeyStores() {
        return sspApiService.getAllKeyStores();
    }

    @ApiOperation(value = "SSP-GET-KEYSTORE", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @GetMapping(path = "/ssp-get-keystore", produces = APPLICATION_XML_VALUE)
    public XmlResponseModel getKeyStore(@RequestParam("keyStoreName") String keyStoreName) {
        return sspApiService.getKeyStore(keyStoreName);
    }

    @ApiOperation(value = "SSP-keyDef-addTo-keyStore", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PostMapping(path = "/ssp-trust-keystore", consumes = APPLICATION_XML_VALUE, produces = APPLICATION_XML_VALUE)
    public XmlResponseModel addKeyDef(@RequestBody TrustKeyStoreModel trustKeyStoreModel, @RequestParam("keyStoreName") String keyStoreName) {
        return sspApiService.addTrustKeystore(trustKeyStoreModel, keyStoreName);
    }

    @ApiOperation(value = "SSP-keyDef-update-keyStore", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PutMapping(path = "/ssp-trust-keystore-update", consumes = APPLICATION_XML_VALUE, produces = APPLICATION_XML_VALUE)
    public XmlResponseModel updateKeyDef(@RequestBody TrustKeyStoreModel trustKeyStoreModel, @RequestParam("keyStoreName") String keyStoreName) {
        return sspApiService.updateTrustKeystore(trustKeyStoreModel, keyStoreName);
    }


    @ApiOperation(value = "SSP-ENCODE-BASE64", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PostMapping(path = "/ssp-encode-base64", consumes = APPLICATION_XML_VALUE, produces = APPLICATION_XML_VALUE)
    public InputModel encodeBase64(@RequestBody InputModel inputModel) {
        String str = inputModel.getData().stream()
                .map(valueModel ->
                        new ValueModel(java.util.Base64.getEncoder().encodeToString(valueModel.getValue().getBytes())))
                .collect(Collectors.toList()).stream().map(ValueModel::getValue).collect(Collectors.joining("|"));

        return new InputModel().setData(Stream.of(new ValueModel(str)).collect(Collectors.toList()));
    }

    @ApiOperation(value = "SSP-DECODE-BASE64", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PostMapping(path = "/ssp-decode-base64", consumes = APPLICATION_XML_VALUE, produces = APPLICATION_XML_VALUE)
    public InputModel decodeBase64(@RequestBody InputModel inputModel) {
        AtomicReference<List<String>> str = new AtomicReference<>(new ArrayList<>());
        inputModel.getData().stream()
                .map(ValueModel::getValue)
                .forEach(s -> str.set(Stream.of(s.split("\\|")).collect(Collectors.toList())));
        return new InputModel().setData(
                str.get()
                        .stream()
                        .map(value -> new ValueModel(new String(Base64.getDecoder().decode(value.getBytes()))))
                        .collect(Collectors.toList())
        );
    }

    @ApiOperation(value = "SSP Certificate Expiry Info", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "get-ssp-expiry-cert-info", produces = APPLICATION_XML_VALUE, consumes = APPLICATION_XML_VALUE)
    public ResponseEntity<CommunityMangerModel<PemAccountExpiryModel>> getSSPExpiryCertInfo(@RequestParam Boolean isPartner, @RequestBody SspExpiryCertInfoModel sspExpiryCertInfoModel) {
        return ResponseEntity.ok(new CommunityMangerModel<>(sspApiService.getSspExpiryCert(isPartner, sspExpiryCertInfoModel.getScriptInput())));
    }

    @ApiOperation(value = "SSP-Check-KeyStore", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PostMapping(path = "/ssp-check-keystore", consumes = APPLICATION_XML_VALUE, produces = APPLICATION_XML_VALUE)
    public ResponseEntity<List<String>> getSSPKeyStoreAvailability(@RequestBody KeyStoreAvailabilityModel keyStoreAvailabilityModel) {
        return ResponseEntity.ok(sspApiService.getSSPKeyStoreAvailability(keyStoreAvailabilityModel));
    }

}
