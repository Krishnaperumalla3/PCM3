/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc, Version 6.1 (the "License");
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

import com.pe.pcm.b2b.certificate.*;
import com.pe.pcm.b2b.deserialize.RemoteUserDeserializeModel;
import com.pe.pcm.b2b.other.CaCertGetModel;
import com.pe.pcm.b2b.other.CipherSuiteNameGetModel;
import com.pe.pcm.b2b.other.NodeGetModel;
import com.pe.pcm.b2b.protocol.*;
import com.pe.pcm.b2b.routing.rules.RemoteRoutingRules;
import com.pe.pcm.b2b.usermailbox.RemoteUserInfoModel;
import com.pe.pcm.b2b.usermailbox.RemoteUserModel;
import com.pe.pcm.common.CommunityManagerNameModel;
import com.pe.pcm.functions.TriFunction;
import com.pe.pcm.protocol.*;
import com.pe.pcm.utils.PCMConstants;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.pe.pcm.exception.GlobalExceptionHandler.conflict;
import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;
import static com.pe.pcm.utils.CommonFunctions.*;
import static com.pe.pcm.utils.PCMConstants.*;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;
import static org.springframework.util.StringUtils.hasText;


public class B2BFunctions {

    private B2BFunctions() {
    }

    static final BiFunction<RemoteProfileModel, String, RemoteProfileModel> mapperJsonToRemoteFtpModel = (remoteFtpModel, remoteFtpProfileJsonString) -> {

        JSONObject jsonObject = new JSONObject(remoteFtpProfileJsonString);
        remoteFtpModel.setEmailId(jsonObject.getString(EMAIL_ADDRESS));
        remoteFtpModel.setPhone(jsonObject.getString(PHONE));
        remoteFtpModel.setAddressLine1(remoteFtpProfileJsonString.contains(ADDRESS_LINE_1) ? jsonObject.getString(ADDRESS_LINE_1) : remoteFtpModel.getAddressLine1());
        remoteFtpModel.setAddressLine2(remoteFtpProfileJsonString.contains(ADDRESS_LINE_2) ? jsonObject.getString(ADDRESS_LINE_2) : remoteFtpModel.getAddressLine2());
        remoteFtpModel.setProfileName(jsonObject.getString("partnerName"));
        remoteFtpModel.setPassword(PRAGMA_EDGE_S);

        if (remoteFtpProfileJsonString.contains("consumerFtpConfiguration")) {
            JSONObject ftpJsonObject = new JSONObject(jsonObject.getJSONObject("consumerFtpConfiguration").toString());
            if (ftpJsonObject.toString().contains(BASE_DIRECTORY)) {
                if (remoteFtpModel.getSubscriberType().equals("TP")) {
                    remoteFtpModel.setInDirectory(ftpJsonObject.getString(BASE_DIRECTORY));
                } else {
                    remoteFtpModel.setOutDirectory(ftpJsonObject.getString(BASE_DIRECTORY));
                }
            }
            remoteFtpModel.setRemoteHost(ftpJsonObject.getString("hostname"))
                    .setRemotePort(String.valueOf(ftpJsonObject.getInt("listenPort")))
                    .setNoOfRetries(String.valueOf(ftpJsonObject.getInt("numberOfRetries")))
                    .setRetryInterval(String.valueOf(ftpJsonObject.getInt(PCMConstants.RETRY_INTERVAL)))
                    .setUserName(ftpJsonObject.getString("username"))
                    .setConnectionType(ftpJsonObject.getJSONObject("connectionType").getString(CODE));
        } else if (remoteFtpProfileJsonString.contains("consumerFtpsConfiguration")) {
            JSONObject ftpsJsonObject = new JSONObject(jsonObject.getJSONObject("consumerFtpsConfiguration").toString());
            if (ftpsJsonObject.toString().contains(BASE_DIRECTORY)) {
                if (remoteFtpModel.getSubscriberType().equals("TP")) {
                    remoteFtpModel.setInDirectory(ftpsJsonObject.getString(BASE_DIRECTORY));
                } else {
                    remoteFtpModel.setOutDirectory(ftpsJsonObject.getString(BASE_DIRECTORY));
                }
            }
            if (ftpsJsonObject.toString().contains("assignedCaCertificates")) {
                remoteFtpModel.setCertificateId(ftpsJsonObject.getString("assignedCaCertificates"));
            }
            remoteFtpModel.setUseCCC(ftpsJsonObject.getJSONObject("useCcc").getBoolean(CODE))
                    .setUseImplicitSSL(ftpsJsonObject.getJSONObject("useImplicitSsl").getBoolean(CODE))
                    .setNoOfRetries(String.valueOf(ftpsJsonObject.getInt("numberOfRetries")))
                    .setRetryInterval(String.valueOf(ftpsJsonObject.getInt(PCMConstants.RETRY_INTERVAL)))
                    .setUserName(ftpsJsonObject.getString("username"))
                    .setConnectionType(ftpsJsonObject.getJSONObject("connectionType").getString(CODE))
                    .setEncryptionStrength(ftpsJsonObject.getJSONObject("encryptionStrength").getString(CODE).toUpperCase())
                    .setRemoteHost(ftpsJsonObject.getString("hostname"))
                    .setRemotePort(String.valueOf(ftpsJsonObject.getInt("listenPort")))
                    .setDoesRequireEncryptedData(ftpsJsonObject.has("doesRequireEncryptedData") &&
                            !ftpsJsonObject.isNull("doesRequireEncryptedData") ?
                            ftpsJsonObject.getJSONObject("doesRequireEncryptedData").getBoolean(CODE) :
                            null)
                    .setDoesRequireSignedData(ftpsJsonObject.has("doesRequireSignedData") &&
                            !ftpsJsonObject.isNull("doesRequireSignedData") ?
                            ftpsJsonObject.getJSONObject("doesRequireSignedData").getBoolean(CODE) :
                            null)
                    .setAsciiArmor(ftpsJsonObject.has("asciiArmor") &&
                            !ftpsJsonObject.isNull("asciiArmor") ?
                            ftpsJsonObject.getJSONObject("asciiArmor").getBoolean(CODE) :
                            null)
                    .setTextMode(ftpsJsonObject.has("textMode") &&
                            !ftpsJsonObject.isNull("textMode") ?
                            ftpsJsonObject.getJSONObject("textMode").getBoolean(CODE) :
                            null);

        }
        remoteFtpModel.setSubscriberType("");
        return remoteFtpModel;
    };
    static final BiFunction<AwsS3Model, String, AwsS3Model> mapperJsonToAwsS3Model = (awsS3Model, remoteS3ProfileJsonString) -> {

        JSONObject jsonObject = new JSONObject(remoteS3ProfileJsonString);
        if (remoteS3ProfileJsonString.contains("consumerS3Configuration")) {
            JSONObject s3JsonObject = new JSONObject(jsonObject.getJSONObject("consumerS3Configuration").toString());
            ConsumerS3Configuration consumerS3Configuration = new ConsumerS3Configuration();
            consumerS3Configuration.setBucketName(s3JsonObject.getString("bucketName"))
                    .setConnectionTimeOut(s3JsonObject.getInt("connectionTimeOut"))
                    .setCredentialsRequired(s3JsonObject.getJSONObject("credentialsRequired").getBoolean(CODE))
                    .setDirectory(s3JsonObject.getString("directory"))
                    .setEndpoint(s3JsonObject.getString("endpoint"))
                    .setEndpointPort(s3JsonObject.getInt("endpointPort"))
                    .setMaxErrorRetryCount(s3JsonObject.getInt("maxErrorRetryCount"))
                    .setObscuredaccesskey(s3JsonObject.getString("obscuredaccesskey"))
                    .setObscuredsecretkey(s3JsonObject.getString("obscuredsecretkey"))
                    .setPassword(s3JsonObject.getString("password"))
                    .setProxyHost(s3JsonObject.getString("proxyHost"))
                    .setProxyPort(s3JsonObject.getInt("proxyPort"))
                    .setRegion(s3JsonObject.getString("region"))
                    .setRequiredProxy(s3JsonObject.getJSONObject("requiredProxy").getBoolean(CODE))
                    .setUserName(s3JsonObject.getString("userName"));
            awsS3Model.setConsumerS3Configuration(consumerS3Configuration);
        }
        if (remoteS3ProfileJsonString.contains("producerS3Configuration")) {
            JSONObject s3JsonObject = new JSONObject(jsonObject.getJSONObject("producerS3Configuration").toString());
            ProducerS3Configuration producerS3Configuration = new ProducerS3Configuration();
            producerS3Configuration.setConnectionTimeout(s3JsonObject.getInt("ConnectionTimeout"))
                    .setConnectionRetries(s3JsonObject.getInt("ConnectionRetries"))
                    .setCredentialsRequired(s3JsonObject.getJSONObject("credentialsRequired").getBoolean("code"))
                    .setEndPointUrl(s3JsonObject.getString("EndPointUrl"))
                    .setObscuredAccessKey(s3JsonObject.getString("ObscuredAccessKey"))
                    .setObscuredSecretKey(s3JsonObject.getString("ObscuredSecretKey"))
                    .setPassword(s3JsonObject.getString("password"))
                    .setProxyHost(s3JsonObject.getString("proxyHost"))
                    .setProxyPort(s3JsonObject.toString().contains("proxyPort") ? s3JsonObject.getInt("proxyPort") : null)
                    .setRegion(s3JsonObject.toString().contains("Region") ? s3JsonObject.getString("Region") : null)
                    .setProxyServer(s3JsonObject.getJSONObject("ProxyServer").getBoolean("code"))
                    .setProxyuserName(s3JsonObject.getString("proxyuserName"))
                    .setQueueName(s3JsonObject.getString("QueueName"));
            awsS3Model.setProducerS3Configuration(producerS3Configuration);
        }
        return awsS3Model;
    };
    static final TriFunction<Boolean, RemoteProfileModel, String, RemoteProfileModel> mapperJsonToRemoteSftpModel = (isPem, remoteFtpModel, remoteSftpProfileJsonString) -> {
        JSONObject jsonObject = new JSONObject(remoteSftpProfileJsonString);

        if (isPem) {
            remoteFtpModel.setProfileName(jsonObject.getString(PROFILE_NAME));
        }

        remoteFtpModel.setRemoteHost(jsonObject.getString("remoteHost"));
        remoteFtpModel.setRemotePort(String.valueOf(jsonObject.getInt("remotePort")));
        remoteFtpModel.setUserName(jsonObject.getString("remoteUser"));
        String prefAuthType = jsonObject.getJSONObject("preferredAuthenticationType").getString(CODE);
        if (!prefAuthType.equals("PUBLIC_KEY")) {
            remoteFtpModel.setPassword(PRAGMA_EDGE_S);
        }
        remoteFtpModel.setPreferredCipher(jsonObject.getJSONObject("preferredCipher").getString(CODE));
        remoteFtpModel.setPreferredMacAlgorithm(jsonObject.getJSONObject("preferredMacAlgorithm").getString(CODE));
        remoteFtpModel.setCompression(jsonObject.getJSONObject("compression").getString(CODE));
        remoteFtpModel.setPreferredAuthenticationType(prefAuthType);
        remoteFtpModel.setUserIdentityKey(remoteSftpProfileJsonString.contains("userIdentityKey") ? jsonObject.getString("userIdentityKey") : remoteFtpModel.getUserIdentityKey());
        remoteFtpModel.setRetryDelay(String.valueOf(remoteSftpProfileJsonString.contains("retryDelay") ? jsonObject.getInt("retryDelay") : ""));
        remoteFtpModel.setCharacterEncoding(remoteSftpProfileJsonString.contains("characterEncoding") ? jsonObject.getString("characterEncoding") : remoteFtpModel.getCharacterEncoding());
        remoteFtpModel.setConnectionRetryCount(String.valueOf(remoteSftpProfileJsonString.contains("connectionRetryCount") ? jsonObject.getInt("connectionRetryCount") : ""));
        remoteFtpModel.setResponseTimeOut(String.valueOf(remoteSftpProfileJsonString.contains("responseTimeOut") ? jsonObject.getInt("responseTimeOut") : ""));
        remoteFtpModel.setLocalPortRange(remoteSftpProfileJsonString.contains("localPortRange") ? jsonObject.getString("localPortRange") : remoteFtpModel.getLocalPortRange());

        if (remoteSftpProfileJsonString.contains("knownHostKeys")) {
            JSONArray arrKnownHK = jsonObject.getJSONArray("knownHostKeys");
            String knownHostKey = "";
            for (int index = 0; index < arrKnownHK.length(); index++) {
                knownHostKey = arrKnownHK.getJSONObject(index).getString("name");
            }
            remoteFtpModel.setKnownHostKey(knownHostKey.length() > 0 ? knownHostKey : remoteFtpModel.getKnownHostKey());
        }
        if (remoteSftpProfileJsonString.contains(PCMConstants.DIRECTORY)) {
            if (remoteFtpModel.getSubscriberType().equals("TP")) {
                remoteFtpModel.setInDirectory(jsonObject.getString(PCMConstants.DIRECTORY));
            } else {
                remoteFtpModel.setOutDirectory(jsonObject.getString(PCMConstants.DIRECTORY));
            }
        }
        remoteFtpModel.setSubscriberType("");
        return remoteFtpModel;
    };

    public static final BiFunction<As2Model, String, As2Model> mapperJsonRemoteAs2OrgToAs2Model = (as2Model, remoteAs2OrgJsonString) -> {
        JSONObject jsonObject = new JSONObject(remoteAs2OrgJsonString);
        as2Model.setProfileName(jsonObject.getString(PROFILE_NAME));
        as2Model.setProfileId(jsonObject.getString(AS2_IDENTIFIER));
        //as2Model.setProfileId(jsonObject.getString("identityName"))
        //as2Model.setProfileId(jsonObject.getString("profileId"))
        as2Model.setEmailId(jsonObject.getString(EMAIL_ADDRESS));

        JSONArray arrExchangeCert = jsonObject.getJSONArray(EXCHANGE_CERT);
        String exchangeCert = "";
        for (int index = 0; index < arrExchangeCert.length(); index++) {
            exchangeCert = arrExchangeCert.getJSONObject(index).getString(CERT_NAME);
        }
        as2Model.setExchangeCertificate(exchangeCert.length() > 0 ? exchangeCert : as2Model.getExchangeCertificate());

        JSONArray arrSigningCert = jsonObject.getJSONArray(SIGNING_CERTIFICATE);
        String signingCert = "";
        for (int index = 0; index < arrSigningCert.length(); index++) {
            signingCert = arrSigningCert.getJSONObject(index).getString(CERT_NAME);
        }
        as2Model.setSigningCertification(signingCert.length() > 0 ? signingCert : as2Model.getSigningCertification());
        return as2Model;
    };

    public static final BiFunction<As2Model, String, As2Model> mapperJsonRemoteAs2PartnerToAs2Model = (as2Model, remoteAs2OrgJsonString) -> {

        JSONObject jsonObject = new JSONObject(remoteAs2OrgJsonString);
        as2Model.setProfileName(jsonObject.getString(PROFILE_NAME));
        as2Model.setProfileId(jsonObject.getString("aS2Identifier"));
        as2Model.setEmailId(jsonObject.getString(EMAIL_ADDRESS));
        as2Model.setHttpclientAdapter(jsonObject.getJSONObject("httpClientAdapter").getString("name"));
        as2Model.setCipherStrength(jsonObject.getJSONObject("cipherStrength").getString(CODE));
        as2Model.setEndPoint(jsonObject.getString("endPoint"));

        JSONArray arrSigningCert = jsonObject.getJSONArray(SIGNING_CERTIFICATE);
        String signingCert = "";
        for (int index = 0; index < arrSigningCert.length(); index++) {
            signingCert = arrSigningCert.getJSONObject(index).getString(CERT_NAME);
        }
        as2Model.setSigningCertification(signingCert.length() > 0 ? signingCert : as2Model.getSigningCertification());

        if (remoteAs2OrgJsonString.contains(EXCHANGE_CERT)) {
            JSONArray arrExchangeCert = jsonObject.getJSONArray(EXCHANGE_CERT);
            String exchangeCert = "";
            for (int index = 0; index < arrExchangeCert.length(); index++) {
                exchangeCert = arrExchangeCert.getJSONObject(index).getString(CERT_NAME);
            }
            as2Model.setExchangeCertificate(exchangeCert.length() > 0 ? exchangeCert : as2Model.getExchangeCertificate());
        }

        if (remoteAs2OrgJsonString.contains(CA_CERTIFICATE)) {
            JSONArray arrExchangeCert = jsonObject.getJSONArray(CA_CERTIFICATE);
            String exchangeCert = "";
            for (int index = 0; index < arrExchangeCert.length(); index++) {
                exchangeCert = arrExchangeCert.getJSONObject(index).getString(CERT_NAME);
            }
            as2Model.setCaCertificate(exchangeCert.length() > 0 ? exchangeCert : as2Model.getExchangeCertificate());
        }

        if (remoteAs2OrgJsonString.contains(KEY_CERTIFICATE)) {
            JSONArray arrExchangeCert = jsonObject.getJSONArray(KEY_CERTIFICATE);
            String exchangeCert = "";
            for (int index = 0; index < arrExchangeCert.length(); index++) {
                exchangeCert = arrExchangeCert.getJSONObject(index).getString(CERT_NAME);
            }
            as2Model.setKeyCertification(exchangeCert.length() > 0 ? exchangeCert : as2Model.getExchangeCertificate());
        }

        if (remoteAs2OrgJsonString.contains("compressData")) {
            as2Model.setCompressData(jsonObject.getJSONObject("compressData").getString(CODE));
        }
        if (remoteAs2OrgJsonString.contains("encryptionAlgorithm")) {
            as2Model.setEncryptionAlgorithm(jsonObject.getJSONObject("encryptionAlgorithm").getString(CODE));
        }
        if (remoteAs2OrgJsonString.contains("mdnReceipt")) {
            as2Model.setMdn(jsonObject.getJSONObject("mdnReceipt").getBoolean(CODE));
        }

        if (remoteAs2OrgJsonString.contains("mimeSubType")) {
            as2Model.setMimeSubType(jsonObject.getJSONObject("mimeSubType").getString(CODE));
        }

        if (remoteAs2OrgJsonString.contains("mimeType")) {
            as2Model.setMimeType(jsonObject.getJSONObject("mimeType").getString(CODE));
        }

        if (remoteAs2OrgJsonString.contains("payloadType")) {
            as2Model.setPayloadType(jsonObject.getJSONObject("payloadType").getString(CODE));
        }

        if (remoteAs2OrgJsonString.contains("receiptToAddress")) {
            as2Model.setReceiptToAddress(jsonObject.getJSONObject("receiptToAddress").getString(CODE));
        }

        if (remoteAs2OrgJsonString.contains("responseTimeout")) {
            as2Model.setResponseTimeout(jsonObject.getJSONObject("receiptSignatureType").getInt(CODE));
        }
        if (remoteAs2OrgJsonString.contains("ssl")) {
            as2Model.setSslType(jsonObject.getJSONObject("ssl").getString(CODE));
        }

        if (remoteAs2OrgJsonString.contains("signingAlgorithm")) {
            as2Model.setSignatureAlgorithm(jsonObject.getJSONObject("signingAlgorithm").getString(CODE));
        }
        return as2Model;
    };

    static final Function<String, RemoteRoutingRules> mapperStringToRemoteRoutingRules = routingRuleJsonString -> {

        RemoteRoutingRules remoteRoutingRules = new RemoteRoutingRules();
        JSONObject jsonObject = new JSONObject(routingRuleJsonString);
        remoteRoutingRules.setAction(jsonObject.getString("action"))
                .setActionType(jsonObject.getJSONObject("actionType").getString(CODE))
                .setEvaluationMode(jsonObject.getJSONObject(PCMConstants.EVALUATION_MODE).getString(CODE))
                .setName(jsonObject.getString("name"))
                .setRunRuleAs(jsonObject.getString("runRuleAs"));
        if (routingRuleJsonString.contains("mailboxes")) {
            JSONArray jsonArray = jsonObject.getJSONArray("mailboxes");
            List<String> mailBoxes = new ArrayList<>();
            for (int index = 0; index < jsonArray.length(); index++) {
                String href = jsonArray.getJSONObject(index).getString("href");
                mailBoxes.add(href.substring(href.lastIndexOf('/') + 1));
            }
            remoteRoutingRules.setMailboxes(mailBoxes);
        }
        if (routingRuleJsonString.contains(PCMConstants.EVALUATION_MODE)) {
            remoteRoutingRules.setMessageNamePattern(jsonObject.getJSONObject(PCMConstants.EVALUATION_MODE).getString(CODE));
        }
        return remoteRoutingRules;
    };

    static final Function<RemoteUserInfoModel, RemoteUserModel> mapperToRemoteUserModel = userInfoModel -> {

        RemoteUserModel remoteUserModel = new RemoteUserModel()
                .setAuthenticationType(isNotNull(userInfoModel.getAuthenticationType()) ? userInfoModel.getAuthenticationType() : "Local")
                .setAuthenticationHost(isNotNull(userInfoModel.getAuthenticationHost()) ? userInfoModel.getAuthenticationHost() : "")
                .setGivenName(isNotNull(userInfoModel.getGivenName()) ? userInfoModel.getGivenName() : userInfoModel.getUserName())
                .setSurname(isNotNull(userInfoModel.getSurname()) ? userInfoModel.getSurname() : userInfoModel.getUserName())
                .setUserId(userInfoModel.getUserName())
                .setUserIdentity(isNotNull(userInfoModel.getUserIdentity()) ? userInfoModel.getUserIdentity() : "")
                .setPassword(isNullThrowError.apply(userInfoModel.getPassword(), "Password is mandatory"))
                .setPolicy(userInfoModel.getPolicy())
                .setSessionTimeout(isNotNull(userInfoModel.getSessionTimeout()) ? userInfoModel.getSessionTimeout() : "")
                .setEmail(userInfoModel.getEmail());

        if (userInfoModel.getGroups() != null && !userInfoModel.getGroups().isEmpty()) {
            remoteUserModel.setGroups(userInfoModel.getGroups());
        } else {
            List<CommunityManagerNameModel> groupsListModels = new ArrayList<>();
            groupsListModels.add(new CommunityManagerNameModel("MAILBOX"));
            //groupsListModels.add(new CommunityManagerNameModel("Dashboard Users"))
            groupsListModels.add(new CommunityManagerNameModel("Mailbox Administrators"));
            groupsListModels.add(new CommunityManagerNameModel("Mailbox Browser Interface Users"));

            remoteUserModel.setGroups(groupsListModels);
        }

        if (userInfoModel.getProtocol().equals("SFGSFTP") && isNullThrowError.apply(userInfoModel.getPreferredAuthenticationType(), "Please Provide Authentication Type [PASSWORD, PUBLIC_KEY]").equals("PUBLIC_KEY")) {
            if (userInfoModel.getAuthorizedUserKeys() != null && !userInfoModel.getAuthorizedUserKeys().isEmpty()) {
                remoteUserModel.setAuthorizedUserKeys(userInfoModel.getAuthorizedUserKeys());
            } else {
                throw internalServerError("Authorized User key is mandatory");
            }
        }

        if (userInfoModel.getCreateDirectoryInSI()) {
            List<CommunityManagerNameModel> permissionsList = new ArrayList<>();
            Set<String> rootList = new LinkedHashSet<>();
            AtomicReference<String> atomicReference = new AtomicReference<>("");
            if (isNotNull(userInfoModel.getInDirectory())) {
                getRootDirectories(userInfoModel.getInDirectory(), rootList, atomicReference, true);
            }
            if (isNotNull(userInfoModel.getOutDirectory())) {
                atomicReference.set("");
                getRootDirectories(userInfoModel.getOutDirectory(), rootList, atomicReference, true);
            }

            if (!rootList.isEmpty()) {
                permissionsList.addAll(rootList
                        .stream()
                        .map(CommunityManagerNameModel::new)
                        .collect(Collectors.toList())
                );
                permissionsList.add(new CommunityManagerNameModel("MyAccount"));
                // permissionsList.add(new CommunityManagerNameModel("Dashboard UI"))
                //permissionsList.add(new CommunityManagerNameModel("Admin Web App Permission"))
            }
            remoteUserModel.setPermissions(permissionsList);
        }

        return remoteUserModel;
    };

    static final Function<RemoteCdModel, CdNodeConfiguration> mapperToCdNode = cdModel ->
            new CdNodeConfiguration(cdModel)
                    .setCaCertificates(cdModel.getCaCertName())
                    .setSecurePlusOption(convertBooleanToStringForCD(cdModel.isSecure()))
                    .setServerHost(cdModel.getHostName())
                    .setServerPort(cdModel.getPort())
                    .setCipherSuites(cdModel.getCipherSuits())
                    .setSecurityProtocol(isNotNull(cdModel.getSecurityProtocol()) ? convertSecurityProtocolToInteger(cdModel.getSecurityProtocol()) : 0)
                    .setSystemCertificate(cdModel.getSystemCertificate())
                    .setMaxLocallyInitiatedPnodeSessionsAllowed(cdModel.getMaxLocallyInitiatedPnodeSessionsAllowed())
                    .setMaxRemotelyInitiatedSnodeSessionsAllowed(cdModel.getMaxRemotelyInitiatedSnodeSessionsAllowed());

    static final Function<CustomProtocolModel, RemoteCustomProtocol> mapperToCustomProtocol = cpModel ->
            new RemoteCustomProtocol()
                    .setAddressLine1(cpModel.getAddressLine1())
                    .setAddressLine2(cpModel.getAddressLine2())
                    .setAppendSuffixToUsername(cpModel.getAppendSuffixToUsername())
                    .setAsciiArmor(cpModel.getAsciiArmor())
                    .setAuthenticationHost(cpModel.getAuthenticationHost())
                    .setAuthenticationType(cpModel.getAuthenticationType())
                    .setAuthorizedUserKeyName(cpModel.getAuthorizedUserKeyName())
                    .setCity(cpModel.getCity())
                    .setCode(cpModel.getCode())
                    .setCommunity(cpModel.getCommunity())
                    .setCountryOrRegion(cpModel.getCountryOrRegion())
                    .setCustomProtocolExtensions(cpModel.getCustomProtocolExtensions())
                    .setCustomProtocolName(cpModel.getCustomProtocolName())
                    .setDoesRequireCompressedData(cpModel.getDoesRequireCompressedData())
                    .setDoesRequireEncryptedData(cpModel.getDoesRequireEncryptedData())
                    .setDoesRequireSignedData(cpModel.getDoesRequireSignedData())
                    .setDoesUseSSH(isNotNull(cpModel.getDoesUseSSH()) ? cpModel.getDoesUseSSH() : false)
                    .setEmailAddress(cpModel.getEmailId())
                    .setGivenName(isNotNull(cpModel.getGivenName()) ? cpModel.getGivenName() : cpModel.getProfileName())
                    .setIsInitiatingConsumer(isNotNull(cpModel.getIsInitiatingConsumer()) ? cpModel.getIsInitiatingConsumer() : false)
                    .setIsInitiatingProducer(isNotNull(cpModel.getIsInitiatingProducer()) ? cpModel.getIsInitiatingProducer() : true)
                    .setIsListeningConsumer(isNotNull(cpModel.getIsListeningConsumer()) ? cpModel.getIsListeningConsumer() : true)
                    .setIsListeningProducer(isNotNull(cpModel.getIsListeningProducer()) ? cpModel.getIsListeningProducer() : false)
                    .setKeyEnabled(cpModel.getKeyEnabled())
                    .setMailbox(cpModel.getMailbox())
                    .setPartnerName(cpModel.getProfileName())
                    .setPassword(cpModel.getPassword())
                    .setPhone(cpModel.getPhone())
                    .setPasswordPolicy(cpModel.getPasswordPolicy())
                    .setPollingInterval(cpModel.getPollingInterval())
                    .setPostalCode(cpModel.getPostalCode())
                    .setPublicKeyID(cpModel.getPublicKeyID())
                    .setRemoteFilePattern(cpModel.getRemoteFilePattern())
                    .setSessionTimeout(cpModel.getSessionTimeout())
                    .setStateOrProvince(cpModel.getStateOrProvince())
                    .setSurname(isNotNull(cpModel.getSurname()) ? cpModel.getSurname() : cpModel.getProfileName())
                    .setTextMode(cpModel.getTextMode())
                    .setTimeZone(cpModel.getTimeZone())
                    .setUseGlobalMailbox(cpModel.getUseGlobalMailbox())
                    .setUsername(cpModel.getUsername());

    public static String getFileNameFromFile(MultipartFile file, String certName) {
        if (isNotNull(certName)) {
            return certName;
        } else if (hasText(file.getOriginalFilename() )) {
            if (StringUtils.cleanPath(file.getOriginalFilename()).contains("..")) {
                throw internalServerError("Sorry! Filename contains invalid path sequence " + file);
            }
            return file.getOriginalFilename().substring(0, file.getOriginalFilename().indexOf('.'));
        } else {
            throw internalServerError(SERVER_BUSY);
        }
    }

    public static final BiFunction<String, String, CaCertModel> mapperToCaCertModel = (certName, certData) ->
            new CaCertModel().setCertName(certName)
                    .setCertData(certData)
                    .setVerifyAuthChain(false)
                    .setVerifyValidity(false);

    public static final BiFunction<String, String, SystemDigitalCertModel> mapperToSystemDigitalCert = (certName, certType) ->
            new SystemDigitalCertModel()
                    .setCertName(certName)
                    .setCertType(certType)
                    .setVerifyAuthChain(false)
                    .setVerifyValidity(false)
                    .setCrlCache(false);

    public static final BiFunction<String, String, TrustedDigitalCertModel> mapperToTrustedDigitalModel = (certName, certData) ->
            new TrustedDigitalCertModel().setCertName(certName)
                    .setCertData(certData)
                    .setVerifyAuthChain(false)
                    .setVerifyValidity(false);

    public static final BiFunction<String, String, SshKnownHostKeyModel> mapperToSshKnownHostKeyModel = (keyName, keyData) ->
            new SshKnownHostKeyModel().setKeyName(keyName)
                    .setKeyData(keyData)
                    .setKeyStatusEnabled(true);

    public static final BiFunction<String, String, SSHUserIdKeyModel> mapperToSshUIDKeyModel = (keyName, keyData) ->
            new SSHUserIdKeyModel().setKeyName(keyName)
                    .setPrivateKeyData(keyData)
                    .setKeyStatusEnabled(false);

    public static final BiFunction<String, String, SshAuthorizedUserKeyModel> mapperToSshAuthorizedUserKeyModel = (keyName, keyData) ->
            new SshAuthorizedUserKeyModel().setKeyName(keyName)
                    .setKeyData(keyData)
                    .setKeyStatusEnabled(true);

    static final BiFunction<RemoteProfileModel, String, RemoteProfileModel> assignUserJsonStringToRemoteFtpModel = (remoteFtpModel, remoteUserString) -> {
        JSONObject jsonObject = new JSONObject(remoteUserString);

        if (remoteUserString.contains("email")) {
            remoteFtpModel.setSecondaryMail(jsonObject.getString("email"));
        }

        List<CommunityManagerNameModel> authorizedUserKeysList = new ArrayList<>();
        if (remoteUserString.contains(AUTHORIZED_USER_KEYS)) {
            JSONArray authorizedUserKeys = jsonObject.getJSONArray(AUTHORIZED_USER_KEYS);
            for (int index = 0; index < authorizedUserKeys.length(); index++) {
                authorizedUserKeysList.add(new CommunityManagerNameModel(authorizedUserKeys.getJSONObject(index).getString("name")));
            }
        }
        remoteFtpModel.setAuthorizedUserKeys(authorizedUserKeysList);

        List<CommunityManagerNameModel> groupsList = new ArrayList<>();
        if (remoteUserString.contains("groups")) {
            JSONArray authorizedUserKeys = jsonObject.getJSONArray(GROUPS);
            for (int index = 0; index < authorizedUserKeys.length(); index++) {
                groupsList.add(new CommunityManagerNameModel(authorizedUserKeys.getJSONObject(index).getString("name")));
            }
        }
        remoteFtpModel.setGroups(groupsList);

        if (remoteUserString.contains(USER_IDENTITY)) {
            remoteFtpModel.setUserIdentity(jsonObject.getString(USER_IDENTITY));
        }

        return remoteFtpModel;
    };


    static final BiFunction<RemoteCdModel, String, RemoteCdModel> assignNodeJsonStringToCdModel = (cdModel, remoteNodeString) -> {
        if (remoteNodeString.equalsIgnoreCase("[]")) {
            return cdModel;
        }
        JSONObject jsonObject = new JSONObject(remoteNodeString);
        List<CaCertGetModel> caCertList = new ArrayList<>();
        List<CipherSuiteNameGetModel> cipherSuiteList = new ArrayList<>();
        // cdModel.setSecurityProtocol(jsonObject.getString("securityProtocol"))
        cdModel.setHostName(jsonObject.getString("serverHost"));
        cdModel.setPort(jsonObject.getInt("serverPort"));
        cdModel.setSecure(convertStringToBooleanForCD(jsonObject.getJSONObject("securePlusOption").getString(CODE)));
        cdModel.setNodeName(jsonObject.getString("serverNodeName"));
        //cdModel.setSystemCertificate(jsonObject.getString("systemCertificate"))
        if (remoteNodeString.contains("caCertificates")) {
            JSONArray caCertificates = jsonObject.getJSONArray("caCertificates");
            for (int index = 0; index < caCertificates.length(); index++) {
                caCertList.add(new CaCertGetModel(caCertificates.getJSONObject(index).getString("caCertName")));
            }
        }
        cdModel.setCaCertName(caCertList);
        if (remoteNodeString.contains("cipherSuites")) {
            JSONArray cipherSuites = jsonObject.getJSONArray("cipherSuites");
            for (int index = 0; index < cipherSuites.length(); index++) {
                cipherSuiteList.add(new CipherSuiteNameGetModel(cipherSuites.getJSONObject(index).getString("cipherSuiteName")));
            }
        }
        cdModel.setCipherSuits(cipherSuiteList);
        return cdModel;
    };

    static final TriFunction<RemoteCdModel, String, Boolean, CdNetMapXrefConfiguration> mergeJsonStringAndMapXref = (cdModel, netMapJsonString, isNew) -> {
        JSONObject jsonObject = new JSONObject(netMapJsonString);
        List<NodeGetModel> nodes = new ArrayList<>();

        if (netMapJsonString.contains(NODES)) {
            JSONArray netMapNodes = jsonObject.getJSONArray(NODES);
            for (int index = 0; index < netMapNodes.length(); index++) {
                nodes.add(new NodeGetModel(netMapNodes.getJSONObject(index).getString(NODE_NAME)));
            }
        }
        if (!isNew) {
            AtomicBoolean atomicBoolean = new AtomicBoolean(false);
            IntStream.rangeClosed(0, nodes.size()).forEach(index -> {
                if (nodes.get(index).getNodeName().equals(cdModel.getNodeName())) {
                    atomicBoolean.set(true);
                }
            });
            if (!atomicBoolean.get()) {
                throw conflict("Node name already Updated to MAP");
            }
        }

        nodes.add(new NodeGetModel(cdModel.getNodeName()));
        return new CdNetMapXrefConfiguration(jsonObject.getString("netMapName"), nodes);
    };

    static final BiFunction<String, String, CdNetMapXrefConfiguration> deleteNodeInNetMap = (nodeName, netMapJsonString) -> {

        JSONObject jsonObject = new JSONObject(netMapJsonString);
        List<NodeGetModel> nodes = new ArrayList<>();
        if (netMapJsonString.contains(NODES)) {
            JSONArray netMapNodes = jsonObject.getJSONArray(NODES);
            for (int index = 0; index < netMapNodes.length(); index++) {
                if (netMapNodes.getJSONObject(index).getString(NODE_NAME).equals(nodeName)) {
                    nodes.remove(new NodeGetModel(netMapNodes.getJSONObject(index).getString(NODE_NAME)));
                } else {
                    nodes.add(new NodeGetModel(netMapNodes.getJSONObject(index).getString(NODE_NAME)));
                }
            }
        }
        return new CdNetMapXrefConfiguration(jsonObject.getString("netMapName"), nodes);
    };

    static final BiFunction<RemoteCdModel, String, CdNetMapXrefConfiguration> mapperToNetMapXerf = (cdModel, netMapName) ->
            new CdNetMapXrefConfiguration(netMapName, Collections.singletonList(new NodeGetModel(cdModel.getNodeName())));


    static final BiFunction<String, RemoteUserModel, RemoteUserModel> mergeRemoteUserModel = (remoteUserString, remoteUserModel) -> {

        JSONObject jsonObject = new JSONObject(remoteUserString);
        List<CommunityManagerNameModel> authorizedUserKeysList = new ArrayList<>();
        List<CommunityManagerNameModel> groupsList = new ArrayList<>();
        List<CommunityManagerNameModel> permissionsList = new ArrayList<>();

        if (remoteUserString.contains(AUTHORIZED_USER_KEYS)) {
            JSONArray authorizedUserKeys = jsonObject.getJSONArray(AUTHORIZED_USER_KEYS);
            for (int index = 0; index < authorizedUserKeys.length(); index++) {
                authorizedUserKeysList.add(new CommunityManagerNameModel(authorizedUserKeys.getJSONObject(index).getString("name")));
            }
        }

        if (remoteUserModel.getAuthorizedUserKeys() != null) {
            remoteUserModel.getAuthorizedUserKeys().addAll(authorizedUserKeysList);
            remoteUserModel.setAuthorizedUserKeys(removeDuplicates(remoteUserModel.getAuthorizedUserKeys()));
        } else {
            if (!authorizedUserKeysList.isEmpty()) {
                remoteUserModel.setAuthorizedUserKeys(authorizedUserKeysList);
            }
        }

        if (remoteUserString.contains(GROUPS)) {
            JSONArray groups = jsonObject.getJSONArray(GROUPS);
            for (int index = 0; index < groups.length(); index++) {
                groupsList.add(new CommunityManagerNameModel(groups.getJSONObject(index).getString("name")));
            }
        }
        if (remoteUserModel.getGroups() != null) {
            remoteUserModel.getGroups().addAll(groupsList);
            remoteUserModel.setGroups(removeDuplicates(remoteUserModel.getGroups()));
        } else {
            if (!groupsList.isEmpty()) {
                remoteUserModel.setGroups(groupsList);
            }
        }

        if (remoteUserString.contains("permissions")) {
            JSONArray permissions = jsonObject.getJSONArray("permissions");
            for (int index = 0; index < permissions.length(); index++) {
                permissionsList.add(new CommunityManagerNameModel(permissions.getJSONObject(index).getString("name")));
            }
        }
        if (remoteUserModel.getPermissions() != null) {
            remoteUserModel.getPermissions().addAll(permissionsList);
            remoteUserModel.setPermissions(removeDuplicates(remoteUserModel.getPermissions()));
        } else {
            if (!permissionsList.isEmpty()) {
                remoteUserModel.setPermissions(permissionsList);
            }
        }

        if (!isNotNull(remoteUserModel.getUserIdentity())) {
            remoteUserModel.setUserIdentity(jsonObject.getString(USER_IDENTITY));
        }

        if (!hasText(remoteUserModel.getPolicy())) {
            if (remoteUserString.contains("policy")) {
                remoteUserModel.setPolicy(jsonObject.getString("policy"));
            }
        }

        if (!hasText(remoteUserModel.getSessionTimeout())) {
            if (remoteUserString.contains("sessionTimeout")) {
                remoteUserModel.setSessionTimeout(String.valueOf(jsonObject.getInt("sessionTimeout")));
            }
        }

        return remoteUserModel;
    };

    private static List<CommunityManagerNameModel> removeDuplicates(List<CommunityManagerNameModel> communityManagerNameModels) {
        return communityManagerNameModels
                .stream()
                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(CommunityManagerNameModel::getName))), ArrayList::new));
    }

    public static final Function<RemoteUserDeserializeModel, RemoteUserModel> userDsModelToUserMod = dsModel ->
            new RemoteUserModel().setUserId(dsModel.getUserId())
                    .setGivenName(dsModel.getGivenName())
                    .setSurname(dsModel.getSurname())
                    .setPassword(dsModel.getPassword())
                    .setUserIdentity(dsModel.getUserIdentity())
                    .setEmail(dsModel.getEmail())
                    .setPolicy(dsModel.getPolicy())
                    .setAuthenticationHost(dsModel.getAuthenticationHost())
                    .setAuthenticationType(dsModel.getAuthenticationType().getCode())
                    .setSessionTimeout(dsModel.getSessionTimeout())
                    .setAuthorizedUserKeys(dsModel.getAuthorizedUserKeys())
                    .setPermissions(dsModel.getPermissions())
                    .setGroups(dsModel.getGroups());

    static final BiFunction<String, String, RemoteRoutingRules> mapperToRemoteRoutingRules = (profileName, directory) ->
            new RemoteRoutingRules().setName(profileName)
                    .setActionType("bp")
                    .setEvaluationMode("automatic")
                    .setMailboxes(Collections.singletonList(directory));

}
