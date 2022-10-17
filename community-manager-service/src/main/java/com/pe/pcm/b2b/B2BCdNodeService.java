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

package com.pe.pcm.b2b;

import com.pe.pcm.b2b.other.CaCertGetModel;
import com.pe.pcm.b2b.other.CipherSuiteNameGetModel;
import com.pe.pcm.b2b.protocol.CdNodeConfiguration;
import com.pe.pcm.protocol.RemoteCdModel;
import com.pe.pcm.sterling.CdNodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

import static com.pe.pcm.b2b.B2BFunctions.*;
import static com.pe.pcm.utils.CommonFunctions.convertSecurityProtocolToInteger;
import static com.pe.pcm.utils.CommonFunctions.isNotNullWithZeroCheck;


/**
 * @author Shameer.
 */
@Service
public class B2BCdNodeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(B2BCdNodeService.class);

    private final B2BApiService b2BApiService;
    private final CdNodeRepository cdNodeRepository;
    private final String netMapName;

    private String internalServerHost;
    private String internalServerPort;
    private String internalSecurePlusOption;
    private String internalCaCert;
    private String internalSystemCertificate;
    private String internalSecurityProtocol;
    private String internalCipherSuites;

    private String externalServerHost;
    private String externalServerPort;
    private String externalSecurePlusOption;
    private String externalCaCert;
    private String externalSystemCertificate;
    private String externalSecurityProtocol;
    private String externalCipherSuites;

    @Autowired
    public B2BCdNodeService(B2BApiService b2BApiService, CdNodeRepository cdNodeRepository,
                            @Value("${sterling-b2bi.b2bi-api.cd.net-map-name}") String netMapName,
                            @Value("${sterling-b2bi.b2bi-api.cd.proxy.internal.server-host}") String internalServerHost,
                            @Value("${sterling-b2bi.b2bi-api.cd.proxy.internal.server-port}") String internalServerPort,
                            @Value("${sterling-b2bi.b2bi-api.cd.proxy.internal.secure-plus-option}") String internalSecurePlusOption,
                            @Value("${sterling-b2bi.b2bi-api.cd.proxy.internal.ca-cert}") String internalCaCert,
                            @Value("${sterling-b2bi.b2bi-api.cd.proxy.internal.system-certificate}") String internalSystemCertificate,
                            @Value("${sterling-b2bi.b2bi-api.cd.proxy.internal.security-protocol}") String internalSecurityProtocol,
                            @Value("${sterling-b2bi.b2bi-api.cd.proxy.internal.cipher-suites}") String internalCipherSuites,
                            @Value("${sterling-b2bi.b2bi-api.cd.proxy.external.server-host}") String externalServerHost,
                            @Value("${sterling-b2bi.b2bi-api.cd.proxy.external.server-port}") String externalServerPort,
                            @Value("${sterling-b2bi.b2bi-api.cd.proxy.external.secure-plus-option}") String externalSecurePlusOption,
                            @Value("${sterling-b2bi.b2bi-api.cd.proxy.external.ca-cert}") String externalCaCert,
                            @Value("${sterling-b2bi.b2bi-api.cd.proxy.external.system-certificate}") String externalSystemCertificate,
                            @Value("${sterling-b2bi.b2bi-api.cd.proxy.external.security-protocol}") String externalSecurityProtocol,
                            @Value("${sterling-b2bi.b2bi-api.cd.proxy.external.cipher-suites}") String externalCipherSuites) {
        this.b2BApiService = b2BApiService;
        this.cdNodeRepository = cdNodeRepository;
        this.netMapName = netMapName;
        this.internalServerHost = internalServerHost;
        this.internalServerPort = internalServerPort;
        this.internalSecurePlusOption = internalSecurePlusOption;
        this.internalCaCert = internalCaCert;
        this.internalSystemCertificate = internalSystemCertificate;
        this.internalSecurityProtocol = internalSecurityProtocol;
        this.internalCipherSuites = internalCipherSuites;
        this.externalServerHost = externalServerHost;
        this.externalServerPort = externalServerPort;
        this.externalSecurePlusOption = externalSecurePlusOption;
        this.externalCaCert = externalCaCert;
        this.externalSystemCertificate = externalSystemCertificate;
        this.externalSecurityProtocol = externalSecurityProtocol;
        this.externalCipherSuites = externalCipherSuites;
    }

    public void createNodeInSI(RemoteCdModel remoteCdModel) {
        if (remoteCdModel.isSSP()) {
            if (remoteCdModel.isInternal()) {
                b2BApiService.createCdNode(new CdNodeConfiguration(remoteCdModel)
                        .setCipherSuites(Arrays.stream(internalCipherSuites.split(",")).map(CipherSuiteNameGetModel::new).collect(Collectors.toList()))
                        .setSecurityProtocol(convertSecurityProtocolToInteger(internalSecurityProtocol))
                        .setServerHost(internalServerHost)
                        .setSecurePlusOption(internalSecurePlusOption)
                        .setServerPort(Integer.parseInt(internalServerPort))
                        .setCaCertificates(Arrays.stream(internalCaCert.split(",")).map(CaCertGetModel::new).collect(Collectors.toList()))
                        .setSystemCertificate(internalSystemCertificate));
            } else {
                b2BApiService.createCdNode(new CdNodeConfiguration(remoteCdModel)
                        .setCipherSuites(Arrays.stream(externalCipherSuites.split(",")).map(CipherSuiteNameGetModel::new).collect(Collectors.toList()))
                        .setSecurityProtocol(convertSecurityProtocolToInteger(externalSecurityProtocol))
                        .setServerPort(Integer.parseInt(externalServerPort))
                        .setServerHost(externalServerHost)
                        .setSecurePlusOption(externalSecurePlusOption)
                        .setCaCertificates(Arrays.stream(externalCaCert.split(",")).map(CaCertGetModel::new).collect(Collectors.toList()))
                        .setSystemCertificate(externalSystemCertificate));
            }
            if (isNotNullWithZeroCheck(remoteCdModel.getMaxRemotelyInitiatedSnodeSessionsAllowed()) || isNotNullWithZeroCheck(remoteCdModel.getMaxLocallyInitiatedPnodeSessionsAllowed())) {
                cdNodeRepository.updatePnodeandSnode(Integer.valueOf(remoteCdModel.getMaxLocallyInitiatedPnodeSessionsAllowed()), Integer.valueOf(remoteCdModel.getMaxRemotelyInitiatedSnodeSessionsAllowed()), remoteCdModel.getNodeName());
            }

        } else {
            if (remoteCdModel.isInternal()) {
                remoteCdModel.setSystemCertificate(internalSystemCertificate);
            } else {
                remoteCdModel.setSystemCertificate(externalSystemCertificate);
            }
            if (isNotNullWithZeroCheck(remoteCdModel.getMaxRemotelyInitiatedSnodeSessionsAllowed()) || isNotNullWithZeroCheck(remoteCdModel.getMaxLocallyInitiatedPnodeSessionsAllowed())) {
                cdNodeRepository.updatePnodeandSnode(Integer.valueOf(remoteCdModel.getMaxLocallyInitiatedPnodeSessionsAllowed()), Integer.valueOf(remoteCdModel.getMaxRemotelyInitiatedSnodeSessionsAllowed()), remoteCdModel.getNodeName());
            }
            b2BApiService.createCdNode(mapperToCdNode.apply(remoteCdModel));
        }
        updateOrCreateCdMapXref(remoteCdModel, Boolean.TRUE);
    }

    //TODO
    private void updateOrCreateCdMapXref(RemoteCdModel remoteCdModel, Boolean isNew) {
        //String cdXref = b2BApiService.getCdMapXref(netMapName);
       // LOGGER.info("cdXref GET Json: {}", cdXref);
        if (/*cdXref.equals("[]")*/ isNew) {
            b2BApiService.createCdMapXref(mapperToNetMapXerf.apply(remoteCdModel, netMapName));
        } else {
            //b2BApiService.updateCdMapXref(mergeJsonStringAndMapXref.apply(remoteCdModel, cdXref, isNew));
        }
    }


    public void updateNodeInSI(RemoteCdModel remoteCdModel, String oldNodeName) {
        if (remoteCdModel.isSSP()) {
            CdNodeConfiguration cdNodeConfiguration = new CdNodeConfiguration(remoteCdModel);
            if (remoteCdModel.isInternal()) {
                cdNodeConfiguration.setCipherSuites(Arrays.stream(internalCipherSuites.split(",")).map(CipherSuiteNameGetModel::new).collect(Collectors.toList()));
                cdNodeConfiguration.setSecurityProtocol(convertSecurityProtocolToInteger(internalSecurityProtocol));
                cdNodeConfiguration.setServerPort(Integer.parseInt(internalServerPort));
                cdNodeConfiguration.setServerHost(internalServerHost);
                cdNodeConfiguration.setSecurePlusOption(internalSecurePlusOption);
                cdNodeConfiguration.setCaCertificates(Collections.singletonList(new CaCertGetModel(internalCaCert)));
                cdNodeConfiguration.setSystemCertificate(internalSystemCertificate);
            } else {
                cdNodeConfiguration.setCipherSuites(Arrays.stream(externalCipherSuites.split(",")).map(CipherSuiteNameGetModel::new).collect(Collectors.toList()));
                cdNodeConfiguration.setSecurityProtocol(convertSecurityProtocolToInteger(externalSecurityProtocol));
                cdNodeConfiguration.setServerPort(Integer.parseInt(externalServerPort));
                cdNodeConfiguration.setServerHost(externalServerHost);
                cdNodeConfiguration.setSecurePlusOption(externalSecurePlusOption);
                cdNodeConfiguration.setCaCertificates(Collections.singletonList(new CaCertGetModel(externalCaCert)));
                cdNodeConfiguration.setSystemCertificate(externalSystemCertificate);
            }
            if (isNotNullWithZeroCheck(remoteCdModel.getMaxRemotelyInitiatedSnodeSessionsAllowed()) || isNotNullWithZeroCheck(remoteCdModel.getMaxLocallyInitiatedPnodeSessionsAllowed())) {
                cdNodeRepository.updatePnodeandSnode(Integer.valueOf(remoteCdModel.getMaxLocallyInitiatedPnodeSessionsAllowed()), Integer.valueOf(remoteCdModel.getMaxRemotelyInitiatedSnodeSessionsAllowed()), oldNodeName);
            }
            b2BApiService.updateCdNode(cdNodeConfiguration, oldNodeName);
        } else {
            if (remoteCdModel.isInternal()) {
                remoteCdModel.setSystemCertificate(internalSystemCertificate);
            } else {
                remoteCdModel.setSystemCertificate(externalSystemCertificate);
            }
            if (isNotNullWithZeroCheck(remoteCdModel.getMaxRemotelyInitiatedSnodeSessionsAllowed()) || isNotNullWithZeroCheck(remoteCdModel.getMaxLocallyInitiatedPnodeSessionsAllowed())) {
                cdNodeRepository.updatePnodeandSnode(Integer.valueOf(remoteCdModel.getMaxLocallyInitiatedPnodeSessionsAllowed()), Integer.valueOf(remoteCdModel.getMaxRemotelyInitiatedSnodeSessionsAllowed()), oldNodeName);
            }
            b2BApiService.updateCdNode(mapperToCdNode.apply(remoteCdModel), oldNodeName);
        }
        if (!oldNodeName.equals(remoteCdModel.getNodeName())) {
            updateOrCreateCdMapXref(remoteCdModel, Boolean.TRUE);
        }else {
            updateOrCreateCdMapXref(remoteCdModel, Boolean.FALSE);
        }
    }

    public RemoteCdModel getNodeInSI(RemoteCdModel remoteCdModel) {
        return assignNodeJsonStringToCdModel.apply(remoteCdModel, b2BApiService.getCdNode(remoteCdModel.getNodeName()));
    }

    private void deleteNodeInNetMapName(String nodeName, String netMapName) {
        String netMapJsonString = b2BApiService.getCdMapXref(netMapName);
        if (!netMapJsonString.equals("[]")) {
            b2BApiService.updateCdMapXref(deleteNodeInNetMap.apply(nodeName, netMapJsonString));
        }

    }

    public void deleteNodeInSI(String nodeName) {
        try {
            deleteNodeInNetMapName(nodeName, netMapName);
        } catch (Exception e) {
            //Do nothing
        }
        try {
            b2BApiService.deleteNodeInSI(nodeName);
        } catch (Exception e) {
            //Do nothing
        }
    }
}
