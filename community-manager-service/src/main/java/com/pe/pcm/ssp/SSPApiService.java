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

package com.pe.pcm.ssp;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.miscellaneous.AppShutDownService;
import com.pe.pcm.miscellaneous.PasswordUtilityService;
import com.pe.pcm.pem.PemAccountExpiryModel;
import org.apache.commons.io.IOUtils;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;
import static com.pe.pcm.utils.CommonFunctions.isNotNull;
import static com.pe.pcm.utils.CommonFunctions.removeENC;
import static com.pe.pcm.utils.PCMConstants.*;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author Shameer.v.
 * Kiran Reddy
 */
@Service
public class SSPApiService {

    private String baseUrl;
    private final String username;
    private String password;
    private final boolean isSSPActive;

    private static final String GET_SESSION = "session";
    private static final String ADD_NETMAP_NODE = "netmap/addNetmapNodes/";
    private static final String UPDATE_NETMAP_NODE = "netmap/modifyNetmapNodes/";
    private static final String GET_NETMAP = "netmap/getNetmap/";
    private static final String GET_ALL_NETMAP = "netmap/getAllNetmaps";
    private static final String GET_ALL_POLICIES = "policy/getAllPolicies";
    private static final String GET_ALL_KEYSTORES = "keyStore/getAllKeyStores";
    private static final String GET_KEYSTORE = "keyStore/getKeyStore/";
    private static final String DELETE_NET_MAP = "netmap/deleteNetmap/";
    private static final String DELETE_NET_MAP_NODE = "netmap/deleteNetmapNodes/";
    private static final String TRUST_KEYSTORE = "keyStore/createKeyDefEntries/";
    private static final String UPDATE_KEYSTORE_DEF = "keyStore/modifyKeyDefEntries/";
    private static final String NET_MAP_EXPORT = "netmap/export";

    private final PasswordUtilityService passwordUtilityService;
    private final AppShutDownService appShutDownService;


    private static final Logger LOGGER = LoggerFactory.getLogger(SSPApiService.class);

    public SSPApiService(@Value("${ssp.api.baseUrl}") String baseUrl, @Value("${ssp.api.username}") String username, @Value("${ssp.api.cmks}") String password, @Value("${ssp.active}") boolean isSSPActive, PasswordUtilityService passwordUtilityService, AppShutDownService appShutDownService) {
        this.baseUrl = baseUrl;
        this.username = username;
        this.password = password;
        this.isSSPActive = isSSPActive;
        this.passwordUtilityService = passwordUtilityService;
        this.appShutDownService = appShutDownService;
    }

    public XmlResponseModel addTrustKeystore(TrustKeyStoreModel trustKeyStoreModel, String keyStoreName) {

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(TrustKeyStoreModel.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(trustKeyStoreModel, sw);
            return invokePostSSPApi(sw.toString(), TRUST_KEYSTORE, keyStoreName);
        } catch (Exception ex) {
            LOGGER.error("{} at InvokePostSSP ", EXCEPTION_OCCURRED, ex);
            throw internalServerError(ex.getMessage());
        }
    }

    public XmlResponseModel updateTrustKeystore(TrustKeyStoreModel trustKeyStoreModel, String keyStoreName) {

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(TrustKeyStoreModel.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(trustKeyStoreModel, sw);
            return invokePutSSPApi(sw.toString(), UPDATE_KEYSTORE_DEF, keyStoreName);
        } catch (Exception ex) {
            LOGGER.error("{} at InvokePutSSP : {}", EXCEPTION_OCCURRED, ex);
            throw internalServerError(ex.getMessage());
        }
    }


    public XmlResponseModel addNodeToNetMap(InboundNodesModel inboundNodesModel, String netMapName) {

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(InboundNodesModel.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(inboundNodesModel, sw);
            return invokePostSSPApi(sw.toString(), ADD_NETMAP_NODE, netMapName);
        } catch (Exception ex) {
            LOGGER.error("{} at InvokePostSSP : {}", EXCEPTION_OCCURRED, ex);
            throw internalServerError(ex.getMessage());
        }
    }

    public XmlResponseModel updateNetMapNode(InboundNodesModel inboundNodesModel, String netMapName) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(InboundNodesModel.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(inboundNodesModel, sw);
            return invokePutSSPApi(sw.toString(), UPDATE_NETMAP_NODE, netMapName);
        } catch (Exception ex) {
            LOGGER.error("{} at UpdateNetMapNode {}", EXCEPTION_OCCURRED, ex);
            throw internalServerError(ex.getMessage());
        }
    }

    public List<PemAccountExpiryModel> getSspExpiryCert(Boolean isPartner, String scriptInput) {
        List<PemAccountExpiryModel> pemAccountExpiryModel = new ArrayList<>();
        XmlResponseModel xmlResponseModel = invokeGetSSP(baseUrl + NET_MAP_EXPORT);
        String[] certInfo = scriptInput.split("========================================================================");
        Arrays.stream(certInfo).forEach(s -> {
            if (s.contains("Certificate name")) {
                String expiryOn;
                String emailId;
                String certName = s.substring(s.indexOf("Certificate name:") + 18, s.indexOf("Certificate store:"));
                if (s.contains("Signature algorithm:")) {
                    expiryOn = s.substring(s.indexOf("Expires on:") + 12, s.indexOf("Signature algorithm:"));
                } else {
                    expiryOn = s.substring(s.indexOf("Expires on:") + 12, s.lastIndexOf(""));
                }
                if (s.contains("EMAILADDRESS=")) {
                    emailId = s.substring(s.indexOf("EMAILADDRESS=") + 13, s.indexOf(", CN="));
                } else {
                    emailId = "";
                }
                if (isPartner) {
                    if (isNotNull(xmlResponseModel.getResults().getEntry().getValue().getSspCMConfigTag().getNetmapsTag()
                            .getCdNetmapDef())) {
                        xmlResponseModel.getResults().getEntry().getValue().getSspCMConfigTag().getNetmapsTag()
                                .getCdNetmapDef().forEach(netMapDefModelFields -> netMapDefModelFields.getInboundNodes()
                                        .forEach(inboundNodesModel -> inboundNodesModel.getInboundNodeDef()
                                                .forEach(sspNodeModel -> {
                                                    if (isNotNull(sspNodeModel.getSslInfo())) {
                                                        if (isNotNull(sspNodeModel.getSslInfo().getTrustedCertNames())) {
                                                            sspNodeModel.getSslInfo().getTrustedCertNames().forEach(trustedCertNameModel -> {
                                                                if (trustedCertNameModel.getTrustedCertName().equals(certName)) {
                                                                    pemAccountExpiryModel.add(new PemAccountExpiryModel().setCertName(certName)
                                                                            .setProfileName(sspNodeModel.getName())
                                                                            .setExpiresOn(expiryOn)
                                                                            .setEmailId(emailId));
                                                                }
                                                            });
                                                        }
                                                    }
                                                })));
                    }
                    xmlResponseModel.getResults().getEntry().getValue().getSspCMConfigTag().getNetmapsTag()
                            .getFtpNetmapDef().forEach(netMapDefModelFields -> netMapDefModelFields.getInboundNodes()
                                    .forEach(inboundNodesModel -> inboundNodesModel.getInboundNodeDef()
                                            .forEach(sspNodeModel -> {
                                                if (isNotNull(sspNodeModel.getSslInfo())) {
                                                    if (isNotNull(sspNodeModel.getSslInfo().getTrustedCertNames())) {
                                                        sspNodeModel.getSslInfo().getTrustedCertNames().forEach(trustedCertNameModel -> {
                                                            if (trustedCertNameModel.getTrustedCertName().equals(certName)) {
                                                                pemAccountExpiryModel.add(new PemAccountExpiryModel().setCertName(certName)
                                                                        .setProfileName(sspNodeModel.getName())
                                                                        .setExpiresOn(expiryOn)
                                                                        .setEmailId(emailId));
                                                            }
                                                        });
                                                    }
                                                }
                                            })));
                    xmlResponseModel.getResults().getEntry().getValue().getSspCMConfigTag().getNetmapsTag()
                            .getHttpNetmapDef().forEach(netMapDefModelFields -> netMapDefModelFields.getInboundNodes()
                                    .forEach(inboundNodesModel -> inboundNodesModel.getInboundNodeDef()
                                            .forEach(sspNodeModel -> {
                                                if (isNotNull(sspNodeModel.getSslInfo())) {
                                                    if (isNotNull(sspNodeModel.getSslInfo().getTrustedCertNames())) {
                                                        sspNodeModel.getSslInfo().getTrustedCertNames().forEach(trustedCertNameModel -> {
                                                            if (trustedCertNameModel.getTrustedCertName().equals(certName)) {
                                                                pemAccountExpiryModel.add(new PemAccountExpiryModel().setCertName(certName)
                                                                        .setProfileName(sspNodeModel.getName())
                                                                        .setExpiresOn(expiryOn)
                                                                        .setEmailId(emailId));
                                                            }
                                                        });
                                                    }
                                                }
                                            })));
                    xmlResponseModel.getResults().getEntry().getValue().getSspCMConfigTag().getNetmapsTag()
                            .getSftpNetmapDef().forEach(netMapDefModelFields -> netMapDefModelFields.getInboundNodes()
                                    .forEach(inboundNodesModel -> inboundNodesModel.getInboundNodeDef()
                                            .forEach(sspNodeModel -> {
                                                if (isNotNull(sspNodeModel.getSslInfo())) {
                                                    if (isNotNull(sspNodeModel.getSslInfo().getTrustedCertNames())) {
                                                        sspNodeModel.getSslInfo().getTrustedCertNames().forEach(trustedCertNameModel -> {
                                                            if (trustedCertNameModel.getTrustedCertName().equals(certName)) {
                                                                pemAccountExpiryModel.add(new PemAccountExpiryModel().setCertName(certName)
                                                                        .setProfileName(sspNodeModel.getName())
                                                                        .setExpiresOn(expiryOn)
                                                                        .setEmailId(emailId));
                                                            }
                                                        });
                                                    }
                                                }
                                            })));
                } else {
                    if (isNotNull(xmlResponseModel.getResults().getEntry().getValue().getSspCMConfigTag().getNetmapsTag()
                            .getCdNetmapDef())) {
                        xmlResponseModel.getResults().getEntry().getValue().getSspCMConfigTag().getNetmapsTag()
                                .getCdNetmapDef().forEach(netMapDefModelFields -> netMapDefModelFields.getInboundNodes()
                                        .forEach(inboundNodesModel -> inboundNodesModel.getInboundNodeDef()
                                                .forEach(sspNodeModel -> {
                                                    if (isNotNull(sspNodeModel.getSslInfo())) {
                                                        if (isNotNull(sspNodeModel.getSslInfo().getKeyCertName())) {
                                                            if (sspNodeModel.getSslInfo().getKeyCertName().equals(certName)) {
                                                                pemAccountExpiryModel.add(new PemAccountExpiryModel().setCertName(certName)
                                                                        .setProfileName(sspNodeModel.getName())
                                                                        .setExpiresOn(expiryOn)
                                                                        .setEmailId(emailId));
                                                            }
                                                        }
                                                    }
                                                })));
                    }
                    xmlResponseModel.getResults().getEntry().getValue().getSspCMConfigTag().getNetmapsTag()
                            .getFtpNetmapDef().forEach(netMapDefModelFields -> netMapDefModelFields.getInboundNodes()
                                    .forEach(inboundNodesModel -> inboundNodesModel.getInboundNodeDef()
                                            .forEach(sspNodeModel -> {
                                                if (isNotNull(sspNodeModel.getSslInfo())) {
                                                    if (isNotNull(sspNodeModel.getSslInfo().getKeyCertName())) {
                                                        if (sspNodeModel.getSslInfo().getKeyCertName().equals(certName)) {
                                                            pemAccountExpiryModel.add(new PemAccountExpiryModel().setCertName(certName)
                                                                    .setProfileName(sspNodeModel.getName())
                                                                    .setExpiresOn(expiryOn)
                                                                    .setEmailId(emailId));
                                                        }
                                                    }
                                                }
                                            })));
                    xmlResponseModel.getResults().getEntry().getValue().getSspCMConfigTag().getNetmapsTag()
                            .getHttpNetmapDef().forEach(netMapDefModelFields -> netMapDefModelFields.getInboundNodes()
                                    .forEach(inboundNodesModel -> inboundNodesModel.getInboundNodeDef()
                                            .forEach(sspNodeModel -> {
                                                if (isNotNull(sspNodeModel.getSslInfo())) {
                                                    if (isNotNull(sspNodeModel.getSslInfo().getKeyCertName())) {
                                                        if (sspNodeModel.getSslInfo().getKeyCertName().equals(certName)) {
                                                            pemAccountExpiryModel.add(new PemAccountExpiryModel().setCertName(certName)
                                                                    .setProfileName(sspNodeModel.getName())
                                                                    .setExpiresOn(expiryOn)
                                                                    .setEmailId(emailId));
                                                        }
                                                    }
                                                }
                                            })));
                    xmlResponseModel.getResults().getEntry().getValue().getSspCMConfigTag().getNetmapsTag()
                            .getSftpNetmapDef().forEach(netMapDefModelFields -> netMapDefModelFields.getInboundNodes()
                                    .forEach(inboundNodesModel -> inboundNodesModel.getInboundNodeDef()
                                            .forEach(sspNodeModel -> {
                                                if (isNotNull(sspNodeModel.getSslInfo())) {
                                                    if (isNotNull(sspNodeModel.getSslInfo().getKeyCertName())) {
                                                        if (sspNodeModel.getSslInfo().getKeyCertName().equals(certName)) {
                                                            pemAccountExpiryModel.add(new PemAccountExpiryModel().setCertName(certName)
                                                                    .setProfileName(sspNodeModel.getName())
                                                                    .setExpiresOn(expiryOn)
                                                                    .setEmailId(emailId));
                                                        }
                                                    }
                                                }
                                            })));
                    if (isNotNull(xmlResponseModel.getResults().getEntry().getValue().getSspCMConfigTag().getNetmapsTag()
                            .getCdNetmapDef())) {
                        xmlResponseModel.getResults().getEntry().getValue().getSspCMConfigTag().getNetmapsTag()
                                .getCdNetmapDef().forEach(netMapDefModelFields -> {
                                    if (isNotNull(netMapDefModelFields.getOutboundNodes())) {
                                        netMapDefModelFields.getOutboundNodes()
                                                .forEach(outBoundNodesModel -> {
                                                    if (isNotNull(outBoundNodesModel.getOutboundNodeDef())) {
                                                        outBoundNodesModel.getOutboundNodeDef()
                                                                .forEach(sspNodeModel -> {
                                                                    if (isNotNull(sspNodeModel.getSslInfo())) {
                                                                        if (isNotNull(sspNodeModel.getSslInfo().getTrustedCertNames())) {
                                                                            sspNodeModel.getSslInfo().getTrustedCertNames().forEach(trustedCertNameModel -> {
                                                                                if (trustedCertNameModel.getTrustedCertName().equals(certName)) {
                                                                                    pemAccountExpiryModel.add(new PemAccountExpiryModel().setCertName(certName)
                                                                                            .setProfileName(sspNodeModel.getName())
                                                                                            .setExpiresOn(expiryOn)
                                                                                            .setEmailId(emailId));
                                                                                }
                                                                            });
                                                                        }
                                                                        if (isNotNull(sspNodeModel.getSslInfo().getKeyCertName())) {
                                                                            if (sspNodeModel.getSslInfo().getKeyCertName().equals(certName)) {
                                                                                pemAccountExpiryModel.add(new PemAccountExpiryModel().setCertName(certName)
                                                                                        .setProfileName(sspNodeModel.getName())
                                                                                        .setExpiresOn(expiryOn)
                                                                                        .setEmailId(emailId));
                                                                            }
                                                                        }
                                                                    }
                                                                });
                                                    }
                                                });
                                    }
                                });
                    }
                    xmlResponseModel.getResults().getEntry().getValue().getSspCMConfigTag().getNetmapsTag()
                            .getFtpNetmapDef().forEach(netMapDefModelFields -> netMapDefModelFields.getOutboundNodes()
                                    .forEach(outBoundNodesModel -> outBoundNodesModel.getOutboundNodeDef()
                                            .forEach(sspNodeModel -> {
                                                if (isNotNull(sspNodeModel.getSslInfo())) {
                                                    if (isNotNull(sspNodeModel.getSslInfo().getTrustedCertNames())) {
                                                        sspNodeModel.getSslInfo().getTrustedCertNames().forEach(trustedCertNameModel -> {
                                                            if (trustedCertNameModel.getTrustedCertName().equals(certName)) {
                                                                pemAccountExpiryModel.add(new PemAccountExpiryModel().setCertName(certName)
                                                                        .setProfileName(sspNodeModel.getName())
                                                                        .setExpiresOn(expiryOn)
                                                                        .setEmailId(emailId));
                                                            }
                                                        });
                                                    }
                                                    if (isNotNull(sspNodeModel.getSslInfo().getKeyCertName())) {
                                                        if (sspNodeModel.getSslInfo().getKeyCertName().equals(certName)) {
                                                            pemAccountExpiryModel.add(new PemAccountExpiryModel().setCertName(certName)
                                                                    .setProfileName(sspNodeModel.getName())
                                                                    .setExpiresOn(expiryOn)
                                                                    .setEmailId(emailId));
                                                        }
                                                    }
                                                }
                                            })));
                    xmlResponseModel.getResults().getEntry().getValue().getSspCMConfigTag().getNetmapsTag()
                            .getHttpNetmapDef().forEach(netMapDefModelFields -> netMapDefModelFields.getOutboundNodes()
                                    .forEach(outBoundNodesModel -> outBoundNodesModel.getOutboundNodeDef()
                                            .forEach(sspNodeModel -> {
                                                if (isNotNull(sspNodeModel.getSslInfo())) {
                                                    if (isNotNull(sspNodeModel.getSslInfo().getTrustedCertNames())) {
                                                        sspNodeModel.getSslInfo().getTrustedCertNames().forEach(trustedCertNameModel -> {
                                                            if (trustedCertNameModel.getTrustedCertName().equals(certName)) {
                                                                pemAccountExpiryModel.add(new PemAccountExpiryModel().setCertName(certName)
                                                                        .setProfileName(sspNodeModel.getName())
                                                                        .setExpiresOn(expiryOn)
                                                                        .setEmailId(emailId));
                                                            }
                                                        });
                                                    }
                                                    if (isNotNull(sspNodeModel.getSslInfo().getKeyCertName())) {
                                                        if (sspNodeModel.getSslInfo().getKeyCertName().equals(certName)) {
                                                            pemAccountExpiryModel.add(new PemAccountExpiryModel().setCertName(certName)
                                                                    .setProfileName(sspNodeModel.getName())
                                                                    .setExpiresOn(expiryOn)
                                                                    .setEmailId(emailId));
                                                        }
                                                    }
                                                }
                                            })));
                    xmlResponseModel.getResults().getEntry().getValue().getSspCMConfigTag().getNetmapsTag()
                            .getSftpNetmapDef().forEach(netMapDefModelFields -> netMapDefModelFields.getOutboundNodes()
                                    .forEach(outBoundNodesModel -> outBoundNodesModel.getSftpOutboundNodeDef()
                                            .forEach(sspNodeModel -> {
                                                if (isNotNull(sspNodeModel.getSslInfo())) {
                                                    if (isNotNull(sspNodeModel.getSslInfo().getTrustedCertNames())) {
                                                        sspNodeModel.getSslInfo().getTrustedCertNames().forEach(trustedCertNameModel -> {
                                                            if (trustedCertNameModel.getTrustedCertName().equals(certName)) {
                                                                pemAccountExpiryModel.add(new PemAccountExpiryModel().setCertName(certName)
                                                                        .setProfileName(sspNodeModel.getName())
                                                                        .setExpiresOn(expiryOn)
                                                                        .setEmailId(emailId));
                                                            }
                                                        });
                                                    }
                                                    if (isNotNull(sspNodeModel.getSslInfo().getKeyCertName())) {
                                                        if (sspNodeModel.getSslInfo().getKeyCertName().equals(certName)) {
                                                            pemAccountExpiryModel.add(new PemAccountExpiryModel().setCertName(certName)
                                                                    .setProfileName(sspNodeModel.getName())
                                                                    .setExpiresOn(expiryOn)
                                                                    .setEmailId(emailId));
                                                        }
                                                    }
                                                }
                                            })));
                }
            }
        });
        return pemAccountExpiryModel;
    }

    public XmlResponseModel getSSPNetMap(String netMapName) {
        return invokeGetSSP(baseUrl + GET_NETMAP + netMapName);
    }

    public String getSSPNodeAvailability(NodeAvailabilityModel nodeAvailabilityModel) {
        AtomicReference<String> nodeName = new AtomicReference<>("");
        XmlResponseModel xmlResponseModel = invokeGetSSP(baseUrl + GET_NETMAP + nodeAvailabilityModel.getNetMap());
        xmlResponseModel.getResults().getEntry().getValue().getNetmapDef().getInboundNodes().forEach(
                inboundNodesModel -> inboundNodesModel.getInboundNodeDef()
                        .forEach(sspNodeModel -> {
                            if (sspNodeModel.getName().equalsIgnoreCase(nodeAvailabilityModel.getNodeName())) {
                                nodeName.set(sspNodeModel.getName());
                            }
                        })
        );
        return nodeName.get();
    }

    public List<String> getSSPKeyStoreAvailability(KeyStoreAvailabilityModel keyStoreAvailabilityModel) {
        List<String> certName = new ArrayList<>();
        XmlResponseModel xmlResponseModel = invokeGetSSP(baseUrl + GET_KEYSTORE + keyStoreAvailabilityModel.getCertificateStore());
        if (isNotNull(keyStoreAvailabilityModel.getCertName())) {
            xmlResponseModel.getResults().getEntry().getValue().getKeyStoreDef().getElements().getKeyDef().forEach(keyDefModel -> {
                if (isNotNull(keyStoreAvailabilityModel.getCertName()) && keyDefModel.getName().equalsIgnoreCase(keyStoreAvailabilityModel.getCertName())) {
                    certName.add(keyDefModel.getName());
                }
            });
        } else {
            xmlResponseModel.getResults().getEntry().getValue().getKeyStoreDef().getElements().getKeyDef().forEach(keyDefModel -> certName.add(keyDefModel.getName()));
        }
        return certName;
    }

    public XmlResponseModel getSSPAllNetMaps() {
        return invokeGetSSP(baseUrl + GET_ALL_NETMAP);
    }

    public XmlResponseGetModel getAllPolicies() {
        return mapperToGetResponse.apply(invokeGetSSP(baseUrl + GET_ALL_POLICIES), GET_ALL_POLICIES);
    }

    public XmlResponseGetModel getAllKeyStores() {
        return mapperToGetResponse.apply(invokeGetSSP(baseUrl + GET_ALL_KEYSTORES), GET_ALL_KEYSTORES);
    }

    public XmlResponseModel getKeyStore(String keyStore) {
        return invokeGetSSP(baseUrl + GET_KEYSTORE + keyStore);
    }

    private XmlResponseModel invokeGetSSP(String path) {
        String token = getSessionToken();
        try {
            CloseableHttpClient client = getHttpClient();
            HttpGet httpGet = new HttpGet(path);
            LOGGER.info("End URL {}", path);
            httpGet.setHeader(CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE);
            httpGet.setHeader("X-Authentication", token);
            httpGet.setHeader("X-Passphrase", "Pragma@05");
            httpGet.addHeader(new BasicScheme().authenticate(new UsernamePasswordCredentials(username, password), httpGet, null));
            CloseableHttpResponse response = client.execute(httpGet);
            String finalResponse = IOUtils.toString(response.getEntity().getContent(), UTF_8);
            finalResponse = finalResponse.contains("&lt;") ? finalResponse.replaceAll("&lt;", "<") : finalResponse;
            finalResponse = finalResponse.contains("&gt;") ? finalResponse.replaceAll("&gt;", ">") : finalResponse;
            //finalResponse =  StringEscapeUtils.escapeXml(finalResponse);
            //LOGGER.info("API Response {} ", finalResponse);
            return new XmlMapper().readValue(finalResponse, XmlResponseModel.class);
        } catch (Exception ex) {
            LOGGER.error("{} at InvokeGetSSP : {}", EXCEPTION_OCCURRED, ex);
            throw internalServerError(ex.getMessage());
        } finally {
            invalidateSession(token);
        }
    }

    private XmlResponseModel invokeDeleteSSP(String path) {
        try {
            CloseableHttpClient client = getHttpClient();
            HttpDelete httpDelete = new HttpDelete(path);
            LOGGER.info("End URL {} ", path);
            httpDelete.setHeader(CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE);
            httpDelete.setHeader(ACCEPT, MediaType.APPLICATION_XML_VALUE);
            httpDelete.setHeader("X-Authentication", getSessionToken());
            httpDelete.setHeader("X-Passphrase", "Pragma@05");
            httpDelete.addHeader(new BasicScheme().authenticate(new UsernamePasswordCredentials(username, password), httpDelete, null));
            CloseableHttpResponse response = client.execute(httpDelete);
            String finalResponse = IOUtils.toString(response.getEntity().getContent(), UTF_8);
            LOGGER.info("API Response {} ", finalResponse);
            return new XmlMapper().readValue(finalResponse, XmlResponseModel.class);
        } catch (Exception ex) {
            LOGGER.error("{} at InvokeDeleteSSP : {}", EXCEPTION_OCCURRED, ex);
            throw internalServerError(ex.getMessage());
        }
    }

    public XmlResponseModel invokePostSSPApi(String request, String url, String name) {
        String token = getSessionToken();
        try {
            HttpPost httpPost = new HttpPost(baseUrl + url + name);
            LOGGER.info("End URL {} {} {} ", baseUrl, url, name);
            LOGGER.info("Request Entity : {} ", request);
            httpPost.setEntity(new StringEntity(request));
            httpPost.setHeader(CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE);
            httpPost.setHeader(ACCEPT, MediaType.APPLICATION_XML_VALUE);
            httpPost.setHeader("X-Authentication", token);
            httpPost.setHeader("X-Passphrase", "Pragma@05");
            httpPost.addHeader(new BasicScheme().authenticate(new UsernamePasswordCredentials(username, password), httpPost, null));
            CloseableHttpResponse response = getHttpClient().execute(httpPost);
            String finalResponse = IOUtils.toString(response.getEntity().getContent(), UTF_8);
            LOGGER.info("API Response {} ", finalResponse);
            return new XmlMapper().readValue(finalResponse, XmlResponseModel.class);
        } catch (Exception ex) {
            LOGGER.error("{} at InvokePostSSP : {}", EXCEPTION_OCCURRED, ex);
            throw internalServerError(ex.getMessage());
        } finally {
            invalidateSession(token);
        }
    }

    public XmlResponseModel invokePutSSPApi(String request, String url, String name) {
        String token = getSessionToken();
        try {
            HttpPut httpPut = new HttpPut(baseUrl + url + name);
            LOGGER.info("End URL {} {} {} ", baseUrl, url, name);
            LOGGER.debug("Request Entity : {} ", request);
            httpPut.setEntity(new StringEntity(request));
            httpPut.setHeader(CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE);
            httpPut.setHeader(ACCEPT, MediaType.APPLICATION_XML_VALUE);
            httpPut.setHeader("X-Authentication", token);
            httpPut.setHeader("X-Passphrase", "Pragma@05");
            httpPut.addHeader(new BasicScheme().authenticate(new UsernamePasswordCredentials(username, password), httpPut, null));
            CloseableHttpResponse response = getHttpClient().execute(httpPut);
            String finalResponse = IOUtils.toString(response.getEntity().getContent(), UTF_8);
            finalResponse = finalResponse.contains("&lt;") ? finalResponse.replaceAll("&lt;", "<") : finalResponse;
            finalResponse = finalResponse.contains("&gt;") ? finalResponse.replaceAll("&gt;", ">") : finalResponse;
            String target = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
            StringBuilder stringBuilder = new StringBuilder(finalResponse);
            if (finalResponse.contains(target)) {
                int startIndx = finalResponse.indexOf(target);
                int stopIndx = startIndx + target.length();
                stringBuilder.delete(startIndx, stopIndx);
            }
            LOGGER.info("API Response {} ", stringBuilder.toString());
            return new XmlMapper().readValue(stringBuilder.toString(), XmlResponseModel.class);
        } catch (Exception ex) {
            LOGGER.error("{} at InvokePutSSP : {}", EXCEPTION_OCCURRED, ex);
            throw internalServerError(ex.getMessage());
        } finally {
            invalidateSession(token);
        }
    }

    private void invalidateSession(String token) {
        try (CloseableHttpClient client = getHttpClient()) {
            String endUrl = baseUrl + GET_SESSION;
            HttpDelete httpDelete = new HttpDelete(endUrl.trim());
            LOGGER.info("End Url {}", endUrl.trim());
            httpDelete.setHeader("X-Authentication", token);
            CloseableHttpResponse response = client.execute(httpDelete);
            String finalResponse = IOUtils.toString(response.getEntity().getContent(), UTF_8);
            Document doc = Jsoup.parse(finalResponse, "", Parser.xmlParser());
            LOGGER.info("API Response {}", doc.select("message").text());
        } catch (Exception ex) {
            LOGGER.error("{} at invalidateSession : {}", EXCEPTION_OCCURRED, ex);
            throw internalServerError(ex.getMessage());
        }
    }


    private String getSessionToken() {
        try (CloseableHttpClient client = getHttpClient()) {
            String endUrl = baseUrl + GET_SESSION;
            HttpPost httpPost = new HttpPost(endUrl.trim());
            LOGGER.info("End Url {}", endUrl.trim());
            JSONObject obj = new JSONObject();
            obj.put("userId", username);
            obj.put("password", password);
            LOGGER.info("JSON Request {}", obj);
            httpPost.setEntity(new StringEntity(obj.toString()));
            httpPost.setHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            httpPost.addHeader(new BasicScheme().authenticate(new UsernamePasswordCredentials(username, password), httpPost, null));
            CloseableHttpResponse response = client.execute(httpPost);
            String finalResponse = IOUtils.toString(response.getEntity().getContent(), UTF_8);
            Document doc = Jsoup.parse(finalResponse, "", Parser.xmlParser());
            LOGGER.info("API Response {}", finalResponse);
            return new JSONObject(doc.select("objectsList").text()).getString("sessionToken");
        } catch (Exception ex) {
            LOGGER.error("{} at Session Token : {}", EXCEPTION_OCCURRED, ex);
            throw internalServerError(ex.getMessage());
        }
    }

    private CloseableHttpClient getHttpClient() throws
            KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        TrustStrategy acceptingTrustStrategy = (x509Certificates, s) -> true;
        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
        return HttpClients.custom().setSSLSocketFactory(csf).build();
    }

    @PostConstruct
    public void initB2bUrl() {
        if (isSSPActive) {
            if (isNotNull(baseUrl)) {
                this.baseUrl = this.baseUrl.endsWith("/") ? this.baseUrl : this.baseUrl + "/";
            } else {
                appShutDownService.initiateShutdown("SSP API base URL should not be Empty/Null (ssp.api.baseUrl), when ssp.active: true");
            }
            if (!isNotNull(username)) {
                appShutDownService.initiateShutdown("SSP API username should not be Empty/Null (ssp.api.username), when ssp.active: true");
            }
            if (isNotNull(password)) {
                if (password.startsWith("ENC")) {
                    try {
                        password = passwordUtilityService.decrypt(removeENC(password));
                    } catch (CommunityManagerServiceException e) {
                        if (e.getErrorMessage().equals("IllegalBlockSizeException")) {
                            appShutDownService.initiateShutdown("SSP API Password is not properly Encrypted (ssp.api.cmks)");
                        }
                    }
                }
            } else {
                appShutDownService.initiateShutdown("SSP API Password should not be Empty/Null (ssp.api.cmks), when ssp.active: true");
            }
        }

    }

    private static final BiFunction<XmlResponseModel, String, XmlResponseGetModel> mapperToGetResponse = (xmlResponseModel, url) -> {
        XmlResponseGetModel xmlResponseGetModel = new XmlResponseGetModel();
        BeanUtils.copyProperties(xmlResponseModel, xmlResponseGetModel);
        String str = xmlResponseModel.getObjectsList().get(0).toString();
        str = str.substring(1, str.length() - 1);
        List<String> list = Stream.of(str.split(","))
                .map(str1 -> str1.substring(1, str1.length() - 1))
                .collect(Collectors.toList());
        if (url.equalsIgnoreCase(GET_ALL_POLICIES)) {
            xmlResponseGetModel.setObjectsList(new SspCommonModel(list, null));
        }
        if (url.equalsIgnoreCase(GET_ALL_KEYSTORES)) {
            xmlResponseGetModel.setObjectsList(new SspCommonModel(null, list));
        }
        return xmlResponseGetModel;
    };

}
