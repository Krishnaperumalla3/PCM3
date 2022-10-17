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

package com.pe.pcm.pem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.functions.TriFunction;
import com.pe.pcm.miscellaneous.PasswordUtilityService;
import com.pe.pcm.pem.ws.*;
import org.apache.commons.io.IOUtils;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringWriter;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;
import static com.pe.pcm.utils.CommonFunctions.*;
import static com.pe.pcm.utils.PCMConstants.*;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * @author Kiran Reddy.
 */
@Service
public class PemWsService {

    @Value("${pem.api-ws.active}")
    private boolean isPemWsActive;
    @Value("${pem.api-ws.base-url}")
    private String baseUrl;
    @Value("${pem.api-ws.username}")
    private String username;
    @Value("${pem.api-ws.cmks}")
    private String password;

    private static final String APPLICATION_JSON = "application/json";
    public static final String APPLICATION_XML_VALUE = "application/xml";
    private static final String ROLL_OUT = "activitydefinitions/ADF/actions/rollout";

    private final PasswordUtilityService passwordUtilityService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(PemWsService.class);


    private static final TriFunction<PemRollOutModel, List<RollOutParticipants>, String, PemWsRollOutModel> mapperToWsRollOutModel = (pemRollOutModel, rollOutParticipants, userName) ->
            new PemWsRollOutModel().setAlertInterval(pemRollOutModel.getAlertInterval())
                    .setAlertStartDate(pemRollOutModel.getAlertStartDate())
                    .setDueDate(pemRollOutModel.getDueDate())
                    .setName(pemRollOutModel.getName() + "-" + userName)
                    .setContextData(pemRollOutModel.getContextData())
                    .setDescription(pemRollOutModel.getDescription())
                    .setRolloutInternally(pemRollOutModel.isRollOutInternally())
                    .setParticipants(rollOutParticipants);

    private static final TriFunction<PemRollOutModel, List<RollOutParticipants>, Integer, PemWsRollOutModel> mapperToWsRollOutModelWithProfile = (pemRollOutModel, rollOutParticipants, extn) ->
            new PemWsRollOutModel().setAlertInterval(pemRollOutModel.getAlertInterval())
                    .setAlertStartDate(pemRollOutModel.getAlertStartDate())
                    .setDueDate(pemRollOutModel.getDueDate())
                    .setName(pemRollOutModel.getName() + "#" + extn)
                    .setContextData(pemRollOutModel.getContextData())
                    .setDescription(pemRollOutModel.getDescription())
                    .setRolloutInternally(pemRollOutModel.isRollOutInternally())
                    .setParticipants(rollOutParticipants);

    private static final TriFunction<PemRollOutModelXml, RollOutParticipantsXml, Integer, PemWsRollOutXmlModel> mapperToWsRollOutModelWithProfileXml = (pemRollOutModel, rollOutParticipantxml, extn) ->
            new PemWsRollOutXmlModel().setAlertInterval(pemRollOutModel.getAlertInterval())
                    .setAlertStartDate(pemRollOutModel.getAlertStartDate())
                    .setDueDate(pemRollOutModel.getDueDate())
                    .setName(pemRollOutModel.getName() + "#" + extn)
                    .setContextData(pemRollOutModel.getContextData())
                    .setDescription(pemRollOutModel.getDescription())
                    .setRolloutInternally(pemRollOutModel.isRollOutInternally())
                    .setParticipants(rollOutParticipantxml);

    public PemWsService(PasswordUtilityService passwordUtilityService) {
        this.passwordUtilityService = passwordUtilityService;
    }

    public void doRollOut(PemRollOutModel pemRollOutModel) {
        if (isPemWsActive) {
            LOGGER.info("in doRollOut");
            pemRollOutModel.getExpiryList()
                    .stream()
                    .filter(distinctByKey(PemAccountExpiryModel::getUserName))
                    .forEach(pemAccountExpiryModel -> {
                        List<RollOutParticipants> rollOutParticipantsList = pemRollOutModel.getExpiryList().stream()
                                .filter(pm -> pm.getUserName().equals(pemAccountExpiryModel.getUserName()))
                                .map(pm -> new RollOutParticipants().setParticipantKey(pm.getPemIdentifier()))
                                .collect(Collectors.toList());
                        rollOut(mapperToWsRollOutModel.apply(pemRollOutModel, rollOutParticipantsList, pemAccountExpiryModel.getUserName()), pemRollOutModel.getActivityDefinition());
                    });
        }
    }

    public void doRollOutWithProfile(PemRollOutModel pemRollOutModel) {
        if (isPemWsActive) {
            LOGGER.info("in doRollOut");
            Map<String, Integer> duplicateProfileName = new ConcurrentHashMap<>();
            for (PemAccountExpiryModel pemAccountExpiryModel : pemRollOutModel.getExpiryList()) {
                if (duplicateProfileName.containsKey(pemAccountExpiryModel.getPemIdentifier())) {
                    duplicateProfileName.put(pemAccountExpiryModel.getPemIdentifier(), duplicateProfileName.get(pemAccountExpiryModel.getPemIdentifier()) + 1);
                } else {
                    duplicateProfileName.put(pemAccountExpiryModel.getPemIdentifier(), 1);
                }
            }
            AtomicInteger maxValue = new AtomicInteger(0);
            if (!duplicateProfileName.isEmpty()) {
                duplicateProfileName.entrySet().stream().max(Map.Entry.comparingByValue())
                        .ifPresent(stringIntegerEntry -> maxValue.set(stringIntegerEntry.getValue()));
            }
            int partitionSize = 80;
            List<List<PemAccountExpiryModel>> partitions = new LinkedList<>();
            for (int i = 0; i < pemRollOutModel.getExpiryList().size(); i += partitionSize) {
                partitions.add(pemRollOutModel.getExpiryList().subList(i,
                        Math.min(i + partitionSize, pemRollOutModel.getExpiryList().size())));
            }
            List<RollOutParticipants> rollOutParticipants = new ArrayList<>();
            AtomicReference<String> userName = new AtomicReference<>("");
            partitions.forEach(pemAccountExpiryModels -> {
                for (int i = 0; i < maxValue.get(); i++) {
                    if (!duplicateProfileName.isEmpty()) {
                        duplicateProfileName.forEach((s, j) -> {
                            if (j <= 0) {
                                duplicateProfileName.remove(s);
                            }
                            List<PemAccountExpiryModel> unique = pemAccountExpiryModels.stream()
                                    .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparing(PemAccountExpiryModel::getPemIdentifier))),
                                            ArrayList::new));
                            unique.forEach(pemAccountExpiryModel -> {
                                if (pemAccountExpiryModel.getPemIdentifier().equals(s)) {
                                    duplicateProfileName.put(s, j - 1);
                                }
                            });
                            List<PemAccountExpiryModel> unique2 = unique.stream().filter(pemAccountExpiryModel -> pemAccountExpiryModel.getPemIdentifier().equals(s)).collect(Collectors.toList());
                            unique2.forEach(pemAccountExpiryModel1 -> {
                                // System.out.println("insideLoop ProfileName   " +pemAccountExpiryModel1.getPemIdentifier());
                                rollOutParticipants.add(new RollOutParticipants().setParticipantKey(pemAccountExpiryModel1.getPemIdentifier()));
                                userName.set(pemAccountExpiryModel1.getUserName());
                            });
                        });
                        //System.out.println(duplicateProfileName.toString());
                        int extn = 1 + i;
                        rollOut(mapperToWsRollOutModelWithProfile.apply(pemRollOutModel, rollOutParticipants, extn), pemRollOutModel.getActivityDefinition());
                        rollOutParticipants.clear();
                        duplicateProfileName.forEach((s, j) -> {
                            if (j <= 0) {
                                //System.out.println("profileName  "+ s);
                                duplicateProfileName.remove(s);
                            }
                        });
                    }
                }
            });
        }
    }

    public void doRollOutWithProfileXmlDup(PemRollOutModelXml pemWsRollOutXmlModel) {
        if (isPemWsActive) {
            LOGGER.info("in doRollOutXml");
            Map<String, Integer> duplicateProfileName = new ConcurrentHashMap<>();
            for (PemAccountExpiryXmlModel pemAccountExpiryModel : pemWsRollOutXmlModel.getExpiryList().getExpiryAccount()) {
                if (duplicateProfileName.containsKey(pemAccountExpiryModel.getPemIdentifier())) {
                    duplicateProfileName.put(pemAccountExpiryModel.getPemIdentifier(), duplicateProfileName.get(pemAccountExpiryModel.getPemIdentifier()) + 1);
                } else {
                    duplicateProfileName.put(pemAccountExpiryModel.getPemIdentifier(), 1);
                }
            }
            AtomicInteger maxValue = new AtomicInteger(0);
            if (!duplicateProfileName.isEmpty()) {
                duplicateProfileName.entrySet().stream().max(Map.Entry.comparingByValue())
                        .ifPresent(stringIntegerEntry -> maxValue.set(stringIntegerEntry.getValue()));
            }
            int partitionSize = 80;
            List<List<PemAccountExpiryXmlModel>> partitions = new LinkedList<>();
            for (int i = 0; i < pemWsRollOutXmlModel.getExpiryList().getExpiryAccount().size(); i += partitionSize) {
                partitions.add(pemWsRollOutXmlModel.getExpiryList().getExpiryAccount().subList(i,
                        Math.min(i + partitionSize, pemWsRollOutXmlModel.getExpiryList().getExpiryAccount().size())));
            }

            AtomicReference<String> userName = new AtomicReference<>("");
            partitions.forEach(pemAccountExpiryModels -> {
                List<Participant> participants = new ArrayList<>();
                for (int i = 0; i < maxValue.get(); i++) {
                    if (!duplicateProfileName.isEmpty()) {
                        duplicateProfileName.forEach((s, j) -> {
                            if (j <= 0) {
                                duplicateProfileName.remove(s);
                            }
                            List<PemAccountExpiryXmlModel> unique = pemAccountExpiryModels.stream()
                                    .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparing(PemAccountExpiryXmlModel::getPemIdentifier))),
                                            ArrayList::new));
                            unique.forEach(pemAccountExpiryModel -> {
                                if (pemAccountExpiryModel.getPemIdentifier().equals(s)) {
                                    duplicateProfileName.put(s, j - 1);
                                }
                            });
                            List<PemAccountExpiryXmlModel> unique2 = unique.stream().filter(pemAccountExpiryModel -> pemAccountExpiryModel.getPemIdentifier().equals(s)).collect(Collectors.toList());
                            unique2.forEach(pemAccountExpiryModel1 -> {
                                List<NodeRefInput> nodeRefInputs = new ArrayList<>();
                                pemWsRollOutXmlModel.getContextDataNodes().getContextDataNode().forEach(nodeRef -> {
                                    if (isNotNull(nodeRef.getNodeValue()) && nodeRef.getNodeValue().equalsIgnoreCase("profileId")) {
                                        nodeRefInputs.add(new NodeRefInput().setNodeRef(nodeRef.getNodeRef())
                                                .setNodeValue(pemAccountExpiryModel1.getProfileId()));
                                    }
                                    if (isNotNull(nodeRef.getNodeValue()) && nodeRef.getNodeValue().equalsIgnoreCase("userName")) {
                                        nodeRefInputs.add(new NodeRefInput().setNodeRef(nodeRef.getNodeRef())
                                                .setNodeValue(pemAccountExpiryModel1.getUserName()));
                                    }
                                    if (isNotNull(nodeRef.getNodeValue()) && nodeRef.getNodeValue().equalsIgnoreCase("certName")) {
                                        nodeRefInputs.add(new NodeRefInput().setNodeRef(nodeRef.getNodeRef())
                                                .setNodeValue(pemAccountExpiryModel1.getCertName()));
                                    }
                                    if (isNotNull(nodeRef.getNodeValue()) && nodeRef.getNodeValue().equalsIgnoreCase("profileName")) {
                                        nodeRefInputs.add(new NodeRefInput().setNodeRef(nodeRef.getNodeRef())
                                                .setNodeValue(pemAccountExpiryModel1.getProfileName()));
                                    }
                                    if (isNotNull(nodeRef.getNodeValue()) && nodeRef.getNodeValue().equalsIgnoreCase("emailId")) {
                                        nodeRefInputs.add(new NodeRefInput().setNodeRef(nodeRef.getNodeRef())
                                                .setNodeValue(pemAccountExpiryModel1.getEmailId()));
                                    }
                                    if (isNotNull(nodeRef.getNodeValue()) && nodeRef.getNodeValue().equalsIgnoreCase("pemIdentifier")) {
                                        nodeRefInputs.add(new NodeRefInput().setNodeRef(nodeRef.getNodeRef())
                                                .setNodeValue(pemAccountExpiryModel1.getPemIdentifier()));
                                    }
                                    if (isNotNull(nodeRef.getNodeValue()) && nodeRef.getNodeValue().equalsIgnoreCase("certType")) {
                                        nodeRefInputs.add(new NodeRefInput().setNodeRef(nodeRef.getNodeRef())
                                                .setNodeValue(pemAccountExpiryModel1.getCertType()));
                                    }
                                    if (isNotNull(nodeRef.getNodeValue()) && nodeRef.getNodeValue().equalsIgnoreCase("pass")) {
                                        nodeRefInputs.add(new NodeRefInput().setNodeRef(nodeRef.getNodeRef())
                                                .setNodeValue(pemAccountExpiryModel1.getPass()));
                                    }
                                    if (isNotNull(nodeRef.getNodeValue()) && nodeRef.getNodeValue().equalsIgnoreCase("pwdLastUpdatedDate")) {
                                        nodeRefInputs.add(new NodeRefInput().setNodeRef(nodeRef.getNodeRef())
                                                .setNodeValue(pemAccountExpiryModel1.getPwdLastUpdatedDate()));
                                    }
                                    if (isNotNull(nodeRef.getNodeValue()) && nodeRef.getNodeValue().equalsIgnoreCase("protocol")) {
                                        nodeRefInputs.add(new NodeRefInput().setNodeRef(nodeRef.getNodeRef())
                                                .setNodeValue(pemAccountExpiryModel1.getProtocol()));
                                    }
                                    if (isNotNull(nodeRef.getNodeValue()) && nodeRef.getNodeValue().equalsIgnoreCase("notAfter")) {
                                        nodeRefInputs.add(new NodeRefInput().setNodeRef(nodeRef.getNodeRef())
                                                .setNodeValue(String.valueOf(pemAccountExpiryModel1.getNotAfter())));
                                    }
                                    if (isNotNull(nodeRef.getNodeValue()) && nodeRef.getNodeValue().equalsIgnoreCase("notBefore")) {
                                        nodeRefInputs.add(new NodeRefInput().setNodeRef(nodeRef.getNodeRef())
                                                .setNodeValue(String.valueOf(pemAccountExpiryModel1.getNotBefore())));
                                    }
                                    if (isNotNull(nodeRef.getNodeValue()) && nodeRef.getNodeValue().equalsIgnoreCase("expiresOn")) {
                                        nodeRefInputs.add(new NodeRefInput().setNodeRef(nodeRef.getNodeRef())
                                                .setNodeValue(String.valueOf(pemAccountExpiryModel1.getExpiresOn())));
                                    }
                                    if (isNotNull(nodeRef.getNodeValue()) && nodeRef.getNodeValue().equalsIgnoreCase("nonProd")) {
                                        nodeRefInputs.add(new NodeRefInput().setNodeRef(nodeRef.getNodeRef())
                                                .setNodeValue(String.valueOf(pemAccountExpiryModel1.getNonProd())));
                                    }
                                    if (isNotNull(nodeRef.getNodeValue()) && nodeRef.getNodeValue().equalsIgnoreCase("prod")) {
                                        nodeRefInputs.add(new NodeRefInput().setNodeRef(nodeRef.getNodeRef())
                                                .setNodeValue(String.valueOf(pemAccountExpiryModel1.getProd())));
                                    }
                                });
                                participants.add(new Participant().setParticipantKey(pemAccountExpiryModel1.getPemIdentifier())
                                        .setContextDataNodes(new ContextDataNode().setContextDataNode(nodeRefInputs)));
                                userName.set(pemAccountExpiryModel1.getUserName());
                            });
                        });
                        int extn = 1 + i;
                        // mapperToWsRollOutModelWithProfileXml.apply(pemWsRollOutXmlModel, participants, extn);
                        rollOutWithXml(mapperToWsRollOutModelWithProfileXml.apply(pemWsRollOutXmlModel, new RollOutParticipantsXml().setParticipant(participants), extn), pemWsRollOutXmlModel.getActivityDefinition());
                        partitions.clear();
                        duplicateProfileName.forEach((s, j) -> {
                            if (j <= 0) {
                                duplicateProfileName.remove(s);
                            }
                        });
                    }
                }
            });
        }
    }

    public void doRollOutWithProfileXml(PemRollOutModelXml pemWsRollOutXmlModel) {
        if (isPemWsActive) {
            LOGGER.info("in doRollOutXml");
            AtomicInteger maxValue = new AtomicInteger(0);
            AtomicReference<String> userName = new AtomicReference<>("");
            AtomicReference<List<PemAccountExpiryXmlModel>> unique = new AtomicReference<>();
            List<String> list = pemWsRollOutXmlModel.getExpiryList().getExpiryAccount()
                    .stream()
                    .map(PemAccountExpiryXmlModel::getPemIdentifier).collect(Collectors.toList());
            new HashSet<>(list).forEach(s -> maxValue.set(Math.max(maxValue.get(), Collections.frequency(list, s))));
            int partitionSize = 80;
            List<List<PemAccountExpiryXmlModel>> partitions = new LinkedList<>();
            for (int i = 0; i < pemWsRollOutXmlModel.getExpiryList().getExpiryAccount().size(); i += partitionSize) {
                partitions.add(pemWsRollOutXmlModel.getExpiryList().getExpiryAccount().subList(i,
                        Math.min(i + partitionSize, pemWsRollOutXmlModel.getExpiryList().getExpiryAccount().size())));
            }
            partitions.forEach(pemAccountExpiryXmlModels -> {
                for (int i = 1; i <= maxValue.get(); i++) {
                    List<Participant> participants = new ArrayList<>();
                    unique.set(pemAccountExpiryXmlModels.stream()
                            .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparing(PemAccountExpiryXmlModel::getPemIdentifier))),
                                    ArrayList::new)));
                    pemAccountExpiryXmlModels.removeAll(unique.get());
                    getParticipants(unique.get(), pemWsRollOutXmlModel, participants, userName);
                    rollOutWithXml(mapperToWsRollOutModelWithProfileXml.apply(pemWsRollOutXmlModel, new RollOutParticipantsXml().setParticipant(participants), i), pemWsRollOutXmlModel.getActivityDefinition());
                    partitions.clear();
                }
            });
        }
    }

    private void rollOutWithXml(PemWsRollOutXmlModel pemWsRollOutXmlModel, String activityDefinition) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(PemWsRollOutXmlModel.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(pemWsRollOutXmlModel, sw);
            invokePostSIService(sw.toString(), ROLL_OUT.replace("ADF", activityDefinition), APPLICATION_XML_VALUE);
        } catch (JAXBException e) {
            LOGGER.error("Xml Formatting Error {}", EXCEPTION_OCCURRED, e);
        }
    }

    private void rollOut(PemWsRollOutModel pemWsRollOutModel, String activityDefinition) {
        try {
            invokePostSIService(objectMapper.writeValueAsString(pemWsRollOutModel), ROLL_OUT.replace("ADF", activityDefinition), APPLICATION_JSON);
        } catch (JsonProcessingException e) {
            LOGGER.error(EXCEPTION_OCCURRED, e);
        } catch (CommunityManagerServiceException cme) {
            throw internalServerError(cme.getErrorMessage());
        }
    }

    private void invokePostSIService(String payload, String path, String contentType) {
        SSLContextBuilder builder = new SSLContextBuilder();
        try {
            builder.loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            });
        } catch (NoSuchAlgorithmException | KeyStoreException e) {
            throw internalServerError(e.getMessage());
        }
        try (CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory(new SSLConnectionSocketFactory(builder.build(), new String[]{"TLSv1.2"}, null, NoopHostnameVerifier.INSTANCE)).build()) {

            HttpPost httpPost = new HttpPost(baseUrl + path);

            LOGGER.info("URI {}{} ", baseUrl, path);
            LOGGER.info("Input PayLoad (POST) {} ", payload);

            httpPost.setEntity(new StringEntity(payload));
            httpPost.setHeader(CONTENT_TYPE, contentType);
            httpPost.setHeader(ACCEPT, contentType);

            httpPost.addHeader(new BasicScheme().authenticate(new UsernamePasswordCredentials(username, password), httpPost, null));
            CloseableHttpResponse response = client.execute(httpPost);
            if (response.getStatusLine().getStatusCode() >= 300) {
                String errorDetails = IOUtils.toString(response.getEntity().getContent(), UTF_8);
                LOGGER.error("SI Error Details: {}", errorDetails);
                throw internalServerError(errorDetails);
            }
        } catch (org.apache.http.conn.HttpHostConnectException ex) {
            throw internalServerError(ex.getMessage());
        } catch (IOException | NoSuchAlgorithmException | KeyManagementException | JSONException | AuthenticationException e) {
            LOGGER.error("Exception occurred while invokePostSIService : ", e);
            throw internalServerError(e.getMessage());
        }
    }


    @PostConstruct
    public void initB2bUrl() {
        this.baseUrl = this.baseUrl.endsWith("/") ? this.baseUrl : this.baseUrl + "/";
        if (isNotNull(password)) {
            if (password.startsWith("ENC")) {
                password = passwordUtilityService.decrypt(removeENC(password));
            }
        }
    }

    private void getParticipants(List<PemAccountExpiryXmlModel> pemAccountExpiryXmlModels,
                                 PemRollOutModelXml pemWsRollOutXmlModel,
                                 List<Participant> participants,
                                 AtomicReference<String> userName) {
        pemAccountExpiryXmlModels.forEach(pemAccountExpiryXmlModel -> {
            List<NodeRefInput> nodeRefInputs = new ArrayList<>();
            pemWsRollOutXmlModel.getContextDataNodes().getContextDataNode().forEach(nodeRef -> {
                if (isNotNull(nodeRef.getNodeValue()) && nodeRef.getNodeValue().equalsIgnoreCase("profileId")) {
                    nodeRefInputs.add(new NodeRefInput().setNodeRef(nodeRef.getNodeRef())
                            .setNodeValue(pemAccountExpiryXmlModel.getProfileId()));
                }
                if (isNotNull(nodeRef.getNodeValue()) && nodeRef.getNodeValue().equalsIgnoreCase("userName")) {
                    nodeRefInputs.add(new NodeRefInput().setNodeRef(nodeRef.getNodeRef())
                            .setNodeValue(pemAccountExpiryXmlModel.getUserName()));
                }
                if (isNotNull(nodeRef.getNodeValue()) && nodeRef.getNodeValue().equalsIgnoreCase("certName")) {
                    nodeRefInputs.add(new NodeRefInput().setNodeRef(nodeRef.getNodeRef())
                            .setNodeValue(pemAccountExpiryXmlModel.getCertName()));
                }
                if (isNotNull(nodeRef.getNodeValue()) && nodeRef.getNodeValue().equalsIgnoreCase("profileName")) {
                    nodeRefInputs.add(new NodeRefInput().setNodeRef(nodeRef.getNodeRef())
                            .setNodeValue(pemAccountExpiryXmlModel.getProfileName()));
                }
                if (isNotNull(nodeRef.getNodeValue()) && nodeRef.getNodeValue().equalsIgnoreCase("emailId")) {
                    nodeRefInputs.add(new NodeRefInput().setNodeRef(nodeRef.getNodeRef())
                            .setNodeValue(pemAccountExpiryXmlModel.getEmailId()));
                }
                if (isNotNull(nodeRef.getNodeValue()) && nodeRef.getNodeValue().equalsIgnoreCase("pemIdentifier")) {
                    nodeRefInputs.add(new NodeRefInput().setNodeRef(nodeRef.getNodeRef())
                            .setNodeValue(pemAccountExpiryXmlModel.getPemIdentifier()));
                }
                if (isNotNull(nodeRef.getNodeValue()) && nodeRef.getNodeValue().equalsIgnoreCase("certType")) {
                    nodeRefInputs.add(new NodeRefInput().setNodeRef(nodeRef.getNodeRef())
                            .setNodeValue(pemAccountExpiryXmlModel.getCertType()));
                }
                if (isNotNull(nodeRef.getNodeValue()) && nodeRef.getNodeValue().equalsIgnoreCase("pass")) {
                    nodeRefInputs.add(new NodeRefInput().setNodeRef(nodeRef.getNodeRef())
                            .setNodeValue(pemAccountExpiryXmlModel.getPass()));
                }
                if (isNotNull(nodeRef.getNodeValue()) && nodeRef.getNodeValue().equalsIgnoreCase("pwdLastUpdatedDate")) {
                    nodeRefInputs.add(new NodeRefInput().setNodeRef(nodeRef.getNodeRef())
                            .setNodeValue(pemAccountExpiryXmlModel.getPwdLastUpdatedDate()));
                }
                if (isNotNull(nodeRef.getNodeValue()) && nodeRef.getNodeValue().equalsIgnoreCase("protocol")) {
                    nodeRefInputs.add(new NodeRefInput().setNodeRef(nodeRef.getNodeRef())
                            .setNodeValue(pemAccountExpiryXmlModel.getProtocol()));
                }
                if (isNotNull(nodeRef.getNodeValue()) && nodeRef.getNodeValue().equalsIgnoreCase("notAfter")) {
                    nodeRefInputs.add(new NodeRefInput().setNodeRef(nodeRef.getNodeRef())
                            .setNodeValue(String.valueOf(pemAccountExpiryXmlModel.getNotAfter())));
                }
                if (isNotNull(nodeRef.getNodeValue()) && nodeRef.getNodeValue().equalsIgnoreCase("notBefore")) {
                    nodeRefInputs.add(new NodeRefInput().setNodeRef(nodeRef.getNodeRef())
                            .setNodeValue(String.valueOf(pemAccountExpiryXmlModel.getNotBefore())));
                }
                if (isNotNull(nodeRef.getNodeValue()) && nodeRef.getNodeValue().equalsIgnoreCase("expiresOn")) {
                    nodeRefInputs.add(new NodeRefInput().setNodeRef(nodeRef.getNodeRef())
                            .setNodeValue(String.valueOf(pemAccountExpiryXmlModel.getExpiresOn())));
                }
                if (isNotNull(nodeRef.getNodeValue()) && nodeRef.getNodeValue().equalsIgnoreCase("nonProd")) {
                    nodeRefInputs.add(new NodeRefInput().setNodeRef(nodeRef.getNodeRef())
                            .setNodeValue(String.valueOf(pemAccountExpiryXmlModel.getNonProd())));
                }
                if (isNotNull(nodeRef.getNodeValue()) && nodeRef.getNodeValue().equalsIgnoreCase("prod")) {
                    nodeRefInputs.add(new NodeRefInput().setNodeRef(nodeRef.getNodeRef())
                            .setNodeValue(String.valueOf(pemAccountExpiryXmlModel.getProd())));
                }
            });
            participants.add(new Participant().setParticipantKey(pemAccountExpiryXmlModel.getPemIdentifier())
                    .setContextDataNodes(new ContextDataNode().setContextDataNode(nodeRefInputs)));
            userName.set(pemAccountExpiryXmlModel.getUserName());
        });

    }

}
