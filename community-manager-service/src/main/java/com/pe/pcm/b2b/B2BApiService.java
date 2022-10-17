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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pe.pcm.b2b.certificate.*;
import com.pe.pcm.b2b.deserialize.B2bErrorResponseDeserializeModel;
import com.pe.pcm.b2b.fileprocess.RemoteProcessFlowModel;
import com.pe.pcm.b2b.protocol.*;
import com.pe.pcm.b2b.routing.rules.RemoteRoutingRules;
import com.pe.pcm.b2b.usermailbox.RemoteMailboxModel;
import com.pe.pcm.b2b.usermailbox.RemoteUserInfoModel;
import com.pe.pcm.b2b.usermailbox.RemoteUserModel;
import com.pe.pcm.b2b.usermailbox.RemoteVirtualRootModel;
import com.pe.pcm.common.CommunityManagerNameModel;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.miscellaneous.AppShutDownService;
import com.pe.pcm.miscellaneous.PasswordUtilityService;
import com.pe.pcm.pem.PemUtilityService;
import com.pe.pcm.pem.codelist.PemProxyCodeListModel;
import com.pe.pcm.protocol.AwsS3Model;
import com.pe.pcm.protocol.CustomProtocolModel;
import com.pe.pcm.sterling.SterlingAuthUserXrefSshService;
import org.apache.commons.io.IOUtils;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.pe.pcm.b2b.B2BFunctions.*;
import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;
import static com.pe.pcm.utils.CommonFunctions.isNotNull;
import static com.pe.pcm.utils.CommonFunctions.removeENC;
import static com.pe.pcm.utils.PCMConstants.*;
import static java.lang.Boolean.TRUE;
import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class B2BApiService {

    private final String username;

    private String password;

    private String baseUrl;

    private final boolean isB2bActive;

    private static final String APPLICATION_JSON = "application/json";

    private static final String APPLICATION_VND = "application/vnd.ibm.tenx.workflowmonitor.restartworkflowmonitor+json";

    private static final String WORKFLOW_MONITOR = "workflowmonitors/";

    private static final String MAIL_BOXES = "mailboxes/";

    private static final String USER_ACCOUNTS = "useraccounts/";

    private static final String VIRTUAL_ROOTS = "uservirtualroots/";

    private static final String SSH_REMOTE_PROFILE = "sshremoteprofiles/";

    private static final String FTP_REMOTE_PROFILE = "tradingpartners/";

    private static final String AS2_ORG_REMOTE_PROFILE = "as2organizations/";

    private static final String AS2_PARTNER_REMOTE_PROFILE = "as2tradingpartners/";

    private static final String SYSTEM_CERTIFICATE = "systemdigitalcertificates/";

    private static final String TRUSTED_CERTIFICATE = "trusteddigitalcertificates/";

    private static final String CA_CERTIFICATE = "cadigitalcertificates/";

    private static final String SSH_CERTIFICATE = "sshknownhostkeys/";

    private static final String SSH_UID_KEY = "sshuseridentitykeys/";

    private static final String CD_NODE = "sterlingconnectdirectnodes/";

    private static final String CD_MAP_XREF = "sterlingconnectdirectnetmapxrefs/";

    private static final String SSH_AUTH_USER_KEY = "sshauthorizeduserkeys/";

    private static final String ROUTING_RULES = "routingrules/";

    private static final String ROUTING_CHANNELS = "routingchannels/";

    private static final String CODE_LIST_CODES = "codelistcodes/";

    private static final String PARTNER_GROUPS = "partnergroups/";

    private final SterlingAuthUserXrefSshService sterlingAuthUserXrefSshService;
    private final PasswordUtilityService passwordUtilityService;
    private final AppShutDownService appShutDownService;
    private final PemUtilityService pemUtilityService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger LOGGER = LoggerFactory.getLogger(B2BApiService.class);

    public B2BApiService(@Value("${sterling-b2bi.b2bi-api.api.username}") String username, @Value("${sterling-b2bi.b2bi-api.api.cmks}") String password,
                         @Value("${sterling-b2bi.b2bi-api.api.baseUrl}") String baseUrl, @Value("${sterling-b2bi.b2bi-api.active}") boolean isB2bActive,
                         SterlingAuthUserXrefSshService sterlingAuthUserXrefSshService,
                         PasswordUtilityService passwordUtilityService, AppShutDownService appShutDownService, PemUtilityService pemUtilityService) {
        this.baseUrl = baseUrl;
        this.username = username;
        this.password = password;
        this.isB2bActive = isB2bActive;
        this.sterlingAuthUserXrefSshService = sterlingAuthUserXrefSshService;
        this.passwordUtilityService = passwordUtilityService;
        this.appShutDownService = appShutDownService;
        this.pemUtilityService = pemUtilityService;
    }

    public void restartWorkFlow(RemoteProcessFlowModel remoteProcessFlowModel) {
        try {
            invokePostSIService(objectMapper.writeValueAsString(remoteProcessFlowModel), WORKFLOW_MONITOR, APPLICATION_VND);
        } catch (JsonProcessingException e) {
            LOGGER.error("Exception {}", EXCEPTION_OCCURRED, e);
            throw internalServerError(e.getMessage());
        }
    }

    public void createMailBoxInSI(boolean flag, String inDirectory, String outDirectory) {
        if (isB2bActive && flag) {
            if (isNotNull(inDirectory)) {
                createMailbox(new RemoteMailboxModel(true, null, "InDirectory", null, "R", inDirectory));
            }
            if (isNotNull(outDirectory)) {
                createMailbox(new RemoteMailboxModel(true, null, "OutDirectory", null, "R", outDirectory));
            }
        }
    }

    void deleteMailBoxInSI(String id) {
        if (isB2bActive) {
            LOGGER.info("in deleteMailBoxInSI");
            try {
                invokeDeleteSIService(MAIL_BOXES + id);
            } catch (CommunityManagerServiceException e) {
                LOGGER.error(e.getErrorMessage());
            }
        }
    }

    private void createMailbox(RemoteMailboxModel remoteMailboxModel) {
        try {
            LOGGER.info("in createMailbox");
            invokePostSIService(objectMapper.writeValueAsString(remoteMailboxModel), MAIL_BOXES, APPLICATION_JSON);
        } catch (JsonProcessingException e) {
            LOGGER.error(EXCEPTION_OCCURRED, "at createMailbox", e);
            throw internalServerError(e.getMessage());
        }
    }

    public void createUserInSI(boolean flag, RemoteUserInfoModel remoteUserInfoModel) {
        if (isB2bActive && flag) {
            LOGGER.info("in createUserInSI");
            createUser(mapperToRemoteUserModel.apply(remoteUserInfoModel));
        }
    }

    public void updateUserInSI(boolean flag, RemoteUserInfoModel remoteUserInfoModel) {
        if (isB2bActive && flag) {
            LOGGER.info("in updateUserInSI");
            updateUser(mapperToRemoteUserModel.apply(remoteUserInfoModel));
        }
    }

    public void updateUserInSI(RemoteUserModel remoteUserModel) {
        if (isB2bActive) {
            LOGGER.info("in updateUserInSI");
            long start = System.currentTimeMillis();
            updateUser(remoteUserModel);
            long stop = System.currentTimeMillis();
            LOGGER.info("Time elapsed for b2bi Update User API : {}  milliseconds", stop - start);
        }
    }

    public void updateUserInSIWithPatch(String password, String userName) {
        if (isB2bActive) {
            LOGGER.info("in updateUserInSIWithPatch");
            long start = System.currentTimeMillis();
            String input = "{ \"password\" : " + password + "}";
            try {
                invokePatchSIService(input, USER_ACCOUNTS + userName.replace(SPACE, "+"));
            } catch (CommunityManagerServiceException cme) {
                LOGGER.error(EXCEPTION_OCCURRED, "at updateUser", cme);
                if (cme.getErrorMessage().equals(API_NOT_WORKING)) {
                    throw internalServerError(API_NOT_WORKING);
                } else if (cme.getErrorMessage().equals(SERVER_BUSY)) {
                    throw internalServerError(SERVER_BUSY);
                } else {
                    throw internalServerError(cme.getErrorMessage());
                }
            }
            long stop = System.currentTimeMillis();
            LOGGER.info("Time elapsed for b2bi Update User API With Patch: {}  milliseconds", stop - start);
        }
    }

    void deleteUserInSI(String user) {
        if (isB2bActive) {
            LOGGER.info("in deleteUserInSI");
            try {
                invokeDeleteSIService(USER_ACCOUNTS + user.replace(SPACE, "+"));
            } catch (CommunityManagerServiceException e) {
                LOGGER.error(e.getErrorMessage());
            }
        }
    }

    private RemoteUserModel managePassword(RemoteUserModel remoteUserModel) {
        if (isNotNull(remoteUserModel.getPassword()) && remoteUserModel.getPassword().equals(PRAGMA_EDGE_S)) {
            remoteUserModel.setPassword(null);
        }
        return remoteUserModel;
    }

    private void createUser(RemoteUserModel remoteUserModel) {
        try {
            invokePostSIService(objectMapper.writeValueAsString(managePassword(remoteUserModel)),
                    USER_ACCOUNTS, APPLICATION_JSON);
        } catch (CommunityManagerServiceException cme) {
            if (cme.getErrorMessage().contains("already exists")) {
                mergeUserAndUpdate(remoteUserModel);
            } else if (cme.getErrorMessage().contains("API000451: Invalid authorized user key")) {
                handleAuthUserKeyManually(remoteUserModel, TRUE);
            } else {
                throw internalServerError(cme.getErrorMessage());
            }
        } catch (JsonProcessingException je) {
            LOGGER.error(EXCEPTION_OCCURRED, "at createUser", je);
            throw internalServerError(je.getMessage());
        }
    }

    private void handleAuthUserKeyManually(RemoteUserModel remoteUserModel, Boolean isPost) {
        LOGGER.info("handleAuthUserKeyManually and storing in DB");
        try {
            List<String> newAuthUserKeys = new ArrayList<>();
            if (remoteUserModel.getAuthorizedUserKeys() != null && !remoteUserModel.getAuthorizedUserKeys().isEmpty()) {
                newAuthUserKeys.addAll(remoteUserModel.getAuthorizedUserKeys()
                        .stream()
                        .map(CommunityManagerNameModel::getName)
                        .distinct()
                        .collect(Collectors.toList()));
            }
            LOGGER.debug("New Auth Keys : {}", newAuthUserKeys);
            sterlingAuthUserXrefSshService.checkKeyAvailability(newAuthUserKeys);
            if (isPost) {
                LOGGER.info("Executing Post");
                invokePostSIService(objectMapper.writeValueAsString(managePassword(remoteUserModel.setAuthorizedUserKeys(null))), USER_ACCOUNTS, APPLICATION_JSON);
            } else {
                LOGGER.info("Executing Put");
                List<String> existingAuthUserKeys = sterlingAuthUserXrefSshService.findAllByUser(remoteUserModel.getUserId())
                        .stream()
                        .map(CommunityManagerNameModel::getName)
                        .collect(Collectors.toList());
                LOGGER.debug("Existing Auth keys : {}", existingAuthUserKeys);
                invokePutSIService(objectMapper.writeValueAsString(managePassword(remoteUserModel.setAuthorizedUserKeys(null))), USER_ACCOUNTS + remoteUserModel.getUserId().replace(SPACE, "+"));
                newAuthUserKeys.addAll(existingAuthUserKeys);
            }

            sterlingAuthUserXrefSshService.saveAll(remoteUserModel.getUserId(), newAuthUserKeys.stream().distinct().collect(Collectors.toList()));
        } catch (JsonProcessingException e) {
            LOGGER.error(EXCEPTION_OCCURRED, "at handleAuthUserKeyManually", e);
            throw internalServerError(e.getMessage());
        }
    }

    private void mergeUserAndUpdate(RemoteUserModel remoteUserModel) {
        updateUser(mergeRemoteUserModel.apply(getUserFromSI(remoteUserModel.getUserId()), remoteUserModel));
    }

    public void mergeUserAndUpdate(boolean flag, RemoteUserInfoModel remoteUserInfoModel) {
        if (isB2bActive && flag) {
            LOGGER.info("in mergeUserAndUpdate");
            RemoteUserModel remoteUserModel = mapperToRemoteUserModel.apply(remoteUserInfoModel);
            try {
                updateUser(mergeRemoteUserModel.apply(getUserFromSI(remoteUserInfoModel.getUserName()), remoteUserModel));
            } catch (CommunityManagerServiceException cme) {
                if (cme.getErrorMessage().contains("API000464")) {
                    createUser(remoteUserModel);
                }
                throw internalServerError(cme.getErrorMessage());
            }
        }
    }

    private void updateUser(RemoteUserModel remoteUserModel) {
        String userName = pemUtilityService.asciiToHexHH(remoteUserModel.getUserId());
        try {
            invokePutSIService(objectMapper.writeValueAsString(managePassword(remoteUserModel)), USER_ACCOUNTS + userName);
        } catch (CommunityManagerServiceException cme) {
            if (!cme.getErrorMessage().equals(API_NOT_WORKING) && !cme.getErrorMessage().equals(SERVER_BUSY) &&
                    cme.getErrorMessage().contains("API000464")) {
                createUser(remoteUserModel);
            } else if (cme.getErrorMessage().contains("API000451: Invalid authorized user key")) {
                LOGGER.error("Error from B2B api : {}", cme.getErrorMessage());
                handleAuthUserKeyManually(remoteUserModel, false);
            } else if (cme.getErrorMessage().equals(API_NOT_WORKING)) {
                throw internalServerError(API_NOT_WORKING);
            } else if (cme.getErrorMessage().equals(SERVER_BUSY)) {
                throw internalServerError(SERVER_BUSY);
            } else if (cme.getErrorMessage().contains("API000393")) {
                try {
                    remoteUserModel.setPassword(null);
                    invokePatchSIService(objectMapper.writeValueAsString(managePassword(remoteUserModel)), USER_ACCOUNTS + userName);
                } catch (JsonProcessingException e) {
                    LOGGER.error(EXCEPTION_OCCURRED, "at updateUser", e);
                }
            } else {
                throw internalServerError(cme.getErrorMessage());
            }
        } catch (JsonProcessingException e) {
            LOGGER.error(EXCEPTION_OCCURRED, "at updateUser", e);
        }
    }

    void createCdNode(CdNodeConfiguration cdNode) {
        try {
            invokePostSIService(objectMapper.writeValueAsString(cdNode), CD_NODE, APPLICATION_JSON);
        } catch (JsonProcessingException e) {
            LOGGER.error(EXCEPTION_OCCURRED, " at createCdNode", e);
            throw internalServerError(e.getMessage());
        }
    }

    void updateCdNode(CdNodeConfiguration cdNode, String oldNodeName) {
        try {
            invokePutSIService(objectMapper.writeValueAsString(cdNode), CD_NODE + oldNodeName);
        } catch (JsonProcessingException e) {
            LOGGER.error(EXCEPTION_OCCURRED, "at updateCdNode", e);
            throw internalServerError(e.getMessage());
        }
    }

    String getCdNode(String nodeName) {
        try {
            return invokeGetSIService(CD_NODE + nodeName);
        } catch (CommunityManagerServiceException e) {
            LOGGER.error(EXCEPTION_OCCURRED, "at getCdNodes", e);
            throw internalServerError(e.getErrorMessage());
        }
    }

    public String getCdNode(B2bCdNodeModel b2bCdNodeModel) {
        try {
            String path = CD_NODE;
            boolean isAdded = false;
            if (isNotNull(b2bCdNodeModel.getNodeName())) {
                path = path + "?searchByNodeName=" + b2bCdNodeModel.getNodeName().replace(SPACE, "+");
                isAdded = true;
            }

            if (isNotNull(b2bCdNodeModel.getNetMapName())) {
                path = path + (isAdded ? "&" : "?") + "?searchByNetMap=" + b2bCdNodeModel.getNetMapName().replace(SPACE, "+");
            }
            return invokeGetSIService(path);
        } catch (CommunityManagerServiceException e) {
            LOGGER.error(EXCEPTION_OCCURRED, " at getCdNode", e);
            throw internalServerError(e.getErrorMessage());
        }
    }

    void deleteNodeInSI(String nodeName) {
        if (isB2bActive) {
            LOGGER.info("in deleteNodeInSI");
            try {
                invokeDeleteSIService(CD_NODE + nodeName);
            } catch (CommunityManagerServiceException e) {
                LOGGER.error(e.getErrorMessage());
            }

        }
    }

    void createCdMapXref(CdNetMapXrefConfiguration cdNetMapXref) {
        LOGGER.info("in createCdMapXref()");
        try {
            invokePostSIService(objectMapper.writeValueAsString(cdNetMapXref), CD_MAP_XREF, APPLICATION_JSON);
        } catch (JsonProcessingException e) {
            LOGGER.error(EXCEPTION_OCCURRED, " at createCdMapXref", e);
            throw internalServerError(e.getMessage());
        }
    }

    void updateCdMapXref(CdNetMapXrefConfiguration cdNetMapXref) {
        LOGGER.info("in updateCdMapXref()");
        try {
            invokePutSIService(objectMapper.writeValueAsString(cdNetMapXref), CD_MAP_XREF + cdNetMapXref.getNetMapName());
        } catch (JsonProcessingException e) {
            LOGGER.error(EXCEPTION_OCCURRED, " at updateCdMapXref", e);
            throw internalServerError(e.getMessage());
        }
    }

    String getCdMapXref(String netMapName) {
        return invokeGetSIService(CD_MAP_XREF + netMapName);
    }

    public String getUserFromSI(String user) {
        try {
            long start = System.currentTimeMillis();
            String userName = pemUtilityService.asciiToHexHH(user);
            String res = invokeGetSIService(USER_ACCOUNTS + userName);
            long stop = System.currentTimeMillis();
            LOGGER.info("Time elapsed for b2bi Get User API : {} milliseconds", stop - start);
            return res;
        } catch (CommunityManagerServiceException e) {
            LOGGER.error(EXCEPTION_OCCURRED, " at getUserFromSI", e);
            throw internalServerError(e.getErrorMessage());
        }
    }

    public void createVirtualRoot(RemoteVirtualRootModel remoteVirtualRootModel) {

        try {
            invokePostSIService(objectMapper.writeValueAsString(remoteVirtualRootModel), VIRTUAL_ROOTS, APPLICATION_JSON);
        } catch (JsonProcessingException e) {
            LOGGER.error(EXCEPTION_OCCURRED, "at createVirtualRoot ", e);
            throw internalServerError(e.getMessage());
        }
    }

    void createRemoteSftpProfile(RemoteSftpProfile remoteSftpProfile) {
        try {
            invokePostSIService(objectMapper.writeValueAsString(remoteSftpProfile), SSH_REMOTE_PROFILE, APPLICATION_JSON);
        } catch (JsonProcessingException e) {
            LOGGER.error(EXCEPTION_OCCURRED, " at createRemoteSftpProfile : ", e);
            throw internalServerError(e.getMessage());
        }
    }

    void updateRemoteSftpProfile(RemoteSftpProfile remoteSftpProfile, String oldProfileName) {
        try {
            invokePutSIService(objectMapper.writeValueAsString(remoteSftpProfile), SSH_REMOTE_PROFILE + oldProfileName);
        } catch (JsonProcessingException e) {
            LOGGER.error(EXCEPTION_OCCURRED, " at updateRemoteSftpProfile : ", e);
            throw internalServerError(e.getMessage());
        }
    }

    String getRemoteSftpProfile(String profileName) {
        return invokeGetSIService(SSH_REMOTE_PROFILE + profileName);
    }

    void deleteRemoteSftpProfile(String profileName) {
        LOGGER.info("Deleting SFGSFTP Profile");
        invokeDeleteSIService(SSH_REMOTE_PROFILE + profileName.replace(" ", "+"));
    }


    void createRemoteFtpProfile(RemoteFtpProfile remoteFtpProfile) {
        try {
            invokePostSIService(objectMapper.writeValueAsString(remoteFtpProfile), FTP_REMOTE_PROFILE, APPLICATION_JSON);
        } catch (JsonProcessingException e) {
            LOGGER.error(EXCEPTION_OCCURRED, " at createRemoteFtpProfile : ", e);
            throw internalServerError(e.getMessage());
        }
    }

    public void createProxyProfile(CustomProtocolModel customProtocolModel) {
        try {
            invokePostSIService(objectMapper.writeValueAsString(mapperToCustomProtocol.apply(customProtocolModel)), FTP_REMOTE_PROFILE, APPLICATION_JSON);
        } catch (JsonProcessingException e) {
            LOGGER.error(EXCEPTION_OCCURRED, " at createProxyPartnerProfile : ", e);
            throw internalServerError(e.getMessage());
        }
    }


    public void updateProxyProfile(CustomProtocolModel customProtocolModel, String oldProfileName) {
        try {
            invokePutSIService(objectMapper.writeValueAsString(mapperToCustomProtocol.apply(customProtocolModel)), FTP_REMOTE_PROFILE + oldProfileName);
        } catch (JsonProcessingException e) {
            LOGGER.error(EXCEPTION_OCCURRED, " at updateRemoteProxyProfile : ", e);
            throw internalServerError(e.getMessage());
        }
    }

    public void deleteProxyProfile(String oldProfileName) {
        if (isB2bActive) {
            LOGGER.info("in deleteProxyPartnerProfile");
            try {
                invokeDeleteSIService(FTP_REMOTE_PROFILE + oldProfileName);
            } catch (CommunityManagerServiceException e) {
                LOGGER.error(e.getErrorMessage());
            }

        }
    }

    public void createProxyCodeList(PemProxyCodeListModel pemProxyCodeListModel) {
        try {
            invokePostSIService(objectMapper.writeValueAsString(pemProxyCodeListModel), CODE_LIST_CODES, APPLICATION_JSON);
        } catch (JsonProcessingException e) {
            LOGGER.error(EXCEPTION_OCCURRED, " at createProxyCodeList : ", e);
            throw internalServerError(e.getMessage());
        }
    }

    public void deleteProxyCodeList(String codeListName) {
        try {
            invokeDeleteSIService(CODE_LIST_CODES + codeListName.replace(SPACE, "+"));
        } catch (CommunityManagerServiceException e) {
            LOGGER.error(EXCEPTION_OCCURRED, " at Delete : ", e);
        }
    }

    public void updateProxyCodeList(PemProxyCodeListModel pemProxyCodeListModel) {
        try {
            invokePutSIService(objectMapper.writeValueAsString(pemProxyCodeListModel.getCodeSet().iterator().next()), CODE_LIST_CODES + pemProxyCodeListModel.getCodeList().replace(SPACE, "+"));
        } catch (JsonProcessingException e) {
            LOGGER.error(EXCEPTION_OCCURRED, " at createProxyCodeList : ", e);
            throw internalServerError(e.getMessage());
        }
    }

    void updateRemoteFtpProfile(RemoteFtpProfile remoteFtpProfile, String oldProfileName) {
        try {
            invokePutSIService(objectMapper.writeValueAsString(remoteFtpProfile), FTP_REMOTE_PROFILE + oldProfileName);
        } catch (JsonProcessingException e) {
            LOGGER.error(EXCEPTION_OCCURRED, " at updateRemoteFtpProfile : ", e);
            throw internalServerError(e.getMessage());
        }
    }

    public void createRemoteCDProfile(RemoteConnectDirectProfile remoteConnectDirectProfile) {
        try {
            invokePostSIService(objectMapper.writeValueAsString(remoteConnectDirectProfile), FTP_REMOTE_PROFILE, APPLICATION_JSON);
        } catch (JsonProcessingException e) {
            LOGGER.error(EXCEPTION_OCCURRED, " at createRemoteConnectDirectProfile : ", e);
            throw internalServerError(e.getMessage());
        }
    }

    public void createRemoteS3Profile(RemoteS3Profile remoteS3Profile) {
        try {
            invokePostSIService(objectMapper.writeValueAsString(remoteS3Profile), FTP_REMOTE_PROFILE, APPLICATION_JSON);
        } catch (JsonProcessingException e) {
            LOGGER.error(EXCEPTION_OCCURRED, " at createRemoteConnectDirectProfile : ", e);
            throw internalServerError(e.getMessage());
        }
    }

    public void updateRemoteCDProfile(RemoteConnectDirectProfile remoteConnectDirectProfile, String oldProfileName) {
        try {
            invokePutSIService(objectMapper.writeValueAsString(remoteConnectDirectProfile), FTP_REMOTE_PROFILE + oldProfileName);
        } catch (JsonProcessingException e) {
            LOGGER.error(EXCEPTION_OCCURRED, " at updateRemoteConnectDirectProfile : ", e);
            throw internalServerError(e.getMessage());
        }
    }

    public void updateRemoteS3Profile(RemoteS3Profile remoteS3Profile, String oldProfileName) {
        try {
            invokePutSIService(objectMapper.writeValueAsString(remoteS3Profile), FTP_REMOTE_PROFILE + oldProfileName);
        } catch (JsonProcessingException e) {
            LOGGER.error(EXCEPTION_OCCURRED, " at updateRemoteConnectDirectProfile : ", e);
            throw internalServerError(e.getMessage());
        }
    }


    public void createRemoteAs2Profile(RemoteAs2Profile remoteAs2Profile) {
        try {
            invokePostSIService(objectMapper.writeValueAsString(remoteAs2Profile), FTP_REMOTE_PROFILE, APPLICATION_JSON);
        } catch (JsonProcessingException e) {
            LOGGER.error(EXCEPTION_OCCURRED, " at createRemoteAs2Profile : ", e);
            throw internalServerError(e.getMessage());
        }
    }

    public void updateRemoteAs2Profile(RemoteAs2Profile remoteAs2Profile, String oldProfileName) {
        try {
            invokePutSIService(objectMapper.writeValueAsString(remoteAs2Profile), FTP_REMOTE_PROFILE + oldProfileName);
        } catch (JsonProcessingException e) {
            LOGGER.error(EXCEPTION_OCCURRED, " at createRemoteAs2Profile : ", e);
            throw internalServerError(e.getMessage());
        }
    }

    public void deleteRemoteCDProfile(String profileName) {
        invokeDeleteSIService(FTP_REMOTE_PROFILE + profileName);
    }

    public void deleteRemoteS3Profile(String profileName) {
        invokeDeleteSIService(FTP_REMOTE_PROFILE + profileName);
    }

    String getRemoteFtpProfile(String profileName) {
        return invokeGetSIService(FTP_REMOTE_PROFILE + profileName);
    }

   public AwsS3Model getRemoteS3Profile(AwsS3Model awsS3Model) {
        String profileName = isNotNull(awsS3Model.getCustomProfileName()) ? awsS3Model.getCustomProfileName() : awsS3Model.getProfileName();
        String s3JsonString = invokeGetSIService(FTP_REMOTE_PROFILE + profileName);
        return mapperJsonToAwsS3Model.apply(awsS3Model,s3JsonString);
    }

    void deleteRemoteFtpProfile(String profileName) {
        LOGGER.info("Deleting SFGFTP/SFGFTPS Profile");
        invokeDeleteSIService(FTP_REMOTE_PROFILE + profileName.replace(" ", "+"));
    }

    void createRemoteAs2OrgProfile(RemoteAs2OrgProfile remoteAs2OrgProfile) {
        try {
            invokePostSIService(objectMapper.writeValueAsString(remoteAs2OrgProfile), AS2_ORG_REMOTE_PROFILE, APPLICATION_JSON);
        } catch (JsonProcessingException e) {
            LOGGER.error(EXCEPTION_OCCURRED, " at createRemoteAs2OrgProfile : ", e);
            throw internalServerError(e.getMessage());
        }
    }

    void updateRemoteAs2OrgProfile(RemoteAs2OrgProfile remoteAs2OrgProfile, String pkId) {
        try {
            invokePutSIService(objectMapper.writeValueAsString(remoteAs2OrgProfile), AS2_ORG_REMOTE_PROFILE + pkId);
        } catch (JsonProcessingException e) {
            LOGGER.error(EXCEPTION_OCCURRED, " at updateRemoteAs2OrgProfile : ", e);
            throw internalServerError(e.getMessage());
        }
    }

    String getRemoteAs2OrgProfile(String objectId) {
        return invokeGetSIService(AS2_ORG_REMOTE_PROFILE + objectId);
    }

    void deleteRemoteAs2OrgProfile(String objectId) {
        invokeDeleteSIService(AS2_ORG_REMOTE_PROFILE + objectId);
    }

    void createRemoteAs2PartnerProfile(RemoteAs2PartnerProfile remoteAs2PartnerProfile) {
        try {
            invokePostSIService(objectMapper.writeValueAsString(remoteAs2PartnerProfile), AS2_PARTNER_REMOTE_PROFILE, APPLICATION_JSON);
        } catch (JsonProcessingException e) {
            LOGGER.error(EXCEPTION_OCCURRED, " at createRemoteAs2PartnerProfile : ", e);
            throw internalServerError(e.getMessage());
        }
    }

    void updateRemoteAs2PartnerProfile(RemoteAs2PartnerProfile remoteAs2PartnerProfile, String objectId) {
        try {
            invokePutSIService(objectMapper.writeValueAsString(remoteAs2PartnerProfile), AS2_PARTNER_REMOTE_PROFILE + objectId);
        } catch (JsonProcessingException e) {
            LOGGER.error(EXCEPTION_OCCURRED, " at updateRemoteAs2PartnerProfile : ", e);
            throw internalServerError(e.getMessage());
        }
    }

    String getRemoteAs2PartnerProfile(String objectId) {
        return invokeGetSIService(AS2_PARTNER_REMOTE_PROFILE + objectId);
    }

    void deleteRemoteAs2PartnerProfile(String objectId) {
        invokeDeleteSIService(AS2_PARTNER_REMOTE_PROFILE + objectId);
    }

    public void createCaCertInSI(CaCertModel caCertModel) {
        try {
            invokePostSIService(objectMapper.writeValueAsString(caCertModel), CA_CERTIFICATE, APPLICATION_JSON);
        } catch (JsonProcessingException e) {
            LOGGER.error(EXCEPTION_OCCURRED, " at createCaCertInSI : ", e);
            throw internalServerError(e.getMessage());
        }
    }

    public void createSystemCertInSI(SystemDigitalCertModel systemDigitalCertModel) {
        try {
            invokePostSIService(objectMapper.writeValueAsString(systemDigitalCertModel), SYSTEM_CERTIFICATE, APPLICATION_JSON);
        } catch (JsonProcessingException e) {
            LOGGER.error(EXCEPTION_OCCURRED, " at createSystemCertInSI : ", e);
            throw internalServerError(e.getMessage());
        }
    }

    public void createTrustedCertInSI(TrustedDigitalCertModel trustedDigitalCertModel) {
        try {
            invokePostSIService(objectMapper.writeValueAsString(trustedDigitalCertModel), TRUSTED_CERTIFICATE, APPLICATION_JSON);
        } catch (JsonProcessingException e) {
            LOGGER.error(EXCEPTION_OCCURRED, " at createTrustedCertInSI : ", e);
            throw internalServerError(e.getMessage());
        }
    }

    public void createSshKnowHostKeyInSI(SshKnownHostKeyModel sshKnownHostKeyModel) {
        try {
            invokePostSIService(objectMapper.writeValueAsString(sshKnownHostKeyModel), SSH_CERTIFICATE, APPLICATION_JSON);
        } catch (JsonProcessingException e) {
            LOGGER.error(EXCEPTION_OCCURRED, " at createSshKnowHostKeyInSI : ", e);
            throw internalServerError(e.getMessage());
        }
    }

    void deleteSshKnowHostKeyInSI(String sshKnownHostKey) {
        if (isB2bActive) {
            LOGGER.info("in delete SSHKnownHostKey : {}", sshKnownHostKey);
            try {
                invokeDeleteSIService(SSH_CERTIFICATE + sshKnownHostKey.replace(SPACE, "+"));
            } catch (CommunityManagerServiceException e) {
                LOGGER.error(e.getErrorMessage());
            }
        }
    }

    void deleteUserIdentityKey(String userIdentityKey) {
        if (isB2bActive) {
            LOGGER.info("in delete deleteUserIdentityKey : {}", userIdentityKey);
            try {
                invokeDeleteSIService(SSH_UID_KEY + userIdentityKey.replace(SPACE, "+"));
            } catch (CommunityManagerServiceException e) {
                LOGGER.error(e.getErrorMessage());
            }
        }
    }

    void deleteCaCertificate(String certificateId) {
        if (isB2bActive) {
            LOGGER.info("in delete deleteCertificateId : {}", certificateId);
            try {
                invokeDeleteSIService(CA_CERTIFICATE + certificateId.replace(SPACE, "+"));
            } catch (CommunityManagerServiceException e) {
                LOGGER.error(e.getErrorMessage());
            }
        }
    }

    public void createUIDKeyInSI(SSHUserIdKeyModel sshUserIdKeyModel) {
        try {
            invokePostSIService(objectMapper.writeValueAsString(sshUserIdKeyModel), SSH_UID_KEY, APPLICATION_JSON);
        } catch (JsonProcessingException e) {
            LOGGER.error(EXCEPTION_OCCURRED, " at createSSHUIDKeyInSI : ", e);
            throw internalServerError(e.getMessage());
        }
    }

    public void createSshAuthorizedUserKeyInSI(SshAuthorizedUserKeyModel sshAuthorizedUserKeyModel) {
        try {
            invokePostSIService(objectMapper.writeValueAsString(sshAuthorizedUserKeyModel), SSH_AUTH_USER_KEY, APPLICATION_JSON);
        } catch (JsonProcessingException e) {
            LOGGER.error(EXCEPTION_OCCURRED, " at create SSH AUTHORIZED USER Key In SI : ", e);
            throw internalServerError(e.getMessage());
        }
    }

    void createRoutingRuleInSI(RemoteRoutingRules remoteRoutingRules) {
        if (isB2bActive) {
            createRoutingRule(remoteRoutingRules.setRunRuleAs(username));
        }
    }

    void updateRoutingRuleInSI(RemoteRoutingRules remoteRoutingRules, String mailbox, Boolean isDirectApi, String oldName) {
        if (isB2bActive) {
            if (isDirectApi) {
                createMailBoxInSI(true, mailbox, null);
            }
            updateRoutingRule(remoteRoutingRules.setRunRuleAs(username), oldName);
        }
    }

    private void createRoutingRule(RemoteRoutingRules remoteRoutingRules) {
        try {
            invokePostSIService(objectMapper.writeValueAsString(remoteRoutingRules), ROUTING_RULES, APPLICATION_JSON);
        } catch (JsonProcessingException e) {
            LOGGER.error("Exception occurred at updateRoutingRules : ", e);
            throw internalServerError(e.getMessage());
        }
    }

    private void updateRoutingRule(RemoteRoutingRules remoteRoutingRules, String oldName) {
        try {
            invokePutSIService(objectMapper.writeValueAsString(remoteRoutingRules), ROUTING_RULES + oldName.replace(SPACE, "+"));
        } catch (JsonProcessingException e) {
            LOGGER.error("Exception occurred at updateRoutingRules : ", e);
            throw internalServerError(e.getMessage());
        }
    }

    String getRoutingRules(String ruleName) {
        if (isB2bActive) {
            return invokeGetSIService(ROUTING_RULES + ruleName.replace(SPACE, "+"));
        } else {
            throw internalServerError("Please reach the PCM Administration team, B2B Api not configured");
        }

    }

    String getPartnerGroupDetails() {
        return invokeGetB2bIService(PARTNER_GROUPS);
    }

    String getRoutingChannelsFromSI(String templateName, String consumerName, String producerName) {
        if (isB2bActive) {
            String queryParams = "";
            boolean isAdded = false;
            if (isNotNull(templateName)) {
                queryParams = "?searchByTemplate=" + templateName.replace(SPACE, "+");
                isAdded = true;
            }
            if (isNotNull(consumerName)) {
                queryParams = queryParams + (isAdded ? "&" : "?") + "searchByConsumer=" + consumerName.replace(SPACE, "+");
                isAdded = true;
            }
            if (isNotNull(producerName)) {
                queryParams = queryParams + (isAdded ? "&" : "?") + "searchByProducer=" + producerName.replace(SPACE, "+");
            }
            return invokeGetSIService(ROUTING_CHANNELS + queryParams);
        } else {
            throw internalServerError("Please reach the PCM Administration team, B2B Api not configured");
        }
    }


    private void invokePostSIService(String payload, String path, String acceptHeader) {

        try (CloseableHttpClient client = getCloseableHttpClient()) {
            HttpPost httpPost = new HttpPost(baseUrl + path);
            LOGGER.info("URI {} {}", baseUrl, path);
            LOGGER.debug("Input PayLoad (POST) {} ", payload);
            httpPost.setEntity(new StringEntity(payload));
            httpPost.setHeader(ACCEPT, acceptHeader);
            httpPost.setHeader(CONTENT_TYPE, APPLICATION_JSON);

            UsernamePasswordCredentials creds = new UsernamePasswordCredentials(username, password);
            httpPost.addHeader(new BasicScheme().authenticate(creds, httpPost, null));
            CloseableHttpResponse response = client.execute(httpPost);

            LOGGER.info("status Code : {} : ", response.getStatusLine().getStatusCode());

            if (response.getStatusLine().getStatusCode() >= 300) {
                String jsonString = IOUtils.toString(response.getEntity().getContent(), UTF_8);
                LOGGER.info("status message : {} : ", new JSONObject(jsonString).getString(ERROR_DESCRIPTION));
                if (MAIL_BOXES.equals(path) && response.getStatusLine().getStatusCode() == 400
                        && jsonString.contains("already exists")) {
                    LOGGER.info("MailBox already exist {}", jsonString);
                } else if (ROUTING_RULES.equals(path) && response.getStatusLine().getStatusCode() == 400
                        && jsonString.contains("Duplicate RoutingRule")) {
                    LOGGER.info("Routing Rule already exist {}", jsonString);
                } else {
                    throw internalServerError(new JSONObject(jsonString).getString(ERROR_DESCRIPTION));
                }
            }
        } catch (org.apache.http.conn.HttpHostConnectException ex) {
            LOGGER.error("Error :  ", ex);
            throw internalServerError(API_NOT_WORKING);
        } catch (IOException | JSONException | AuthenticationException e) {
            LOGGER.error("Exception occurred while invokePostSIService : ", e);
            throw internalServerError(SERVER_BUSY);
        } catch (NoSuchAlgorithmException | KeyManagementException ee) {
            throw internalServerError(ee.getMessage());
        }
    }

    private void invokePutSIService(String payload, String path) {
        try (CloseableHttpClient client = getCloseableHttpClient()) {
            HttpPut httpPut = new HttpPut(baseUrl + path);
            LOGGER.info("URI {} {} ", baseUrl, path);
            LOGGER.debug("Input Payload (PUT) {}", payload);
            httpPut.setEntity(new StringEntity(payload));
            httpPut.setHeader(ACCEPT, APPLICATION_JSON);
            httpPut.setHeader(CONTENT_TYPE, APPLICATION_JSON);

            UsernamePasswordCredentials cre = new UsernamePasswordCredentials(username, password);
            httpPut.addHeader(new BasicScheme().authenticate(cre, httpPut, null));
            CloseableHttpResponse response = client.execute(httpPut);
            String jsonString = IOUtils.toString(response.getEntity().getContent(), UTF_8);
            B2bErrorResponseDeserializeModel errorModel = objectMapper.readValue(jsonString, B2bErrorResponseDeserializeModel.class);
            LOGGER.info("status Code: {}", response.getStatusLine().getStatusCode());
            if (response.getStatusLine().getStatusCode() >= 300) {
                LOGGER.error("SI Error Details: {}", jsonString);
                if (jsonString.contains("API000393")) {
                    //return;
                }
                throw internalServerError(isNotNull(errorModel.getErrorDescription()) ? errorModel.getErrorDescription() : jsonString);
            }
        } catch (org.apache.http.conn.HttpHostConnectException ex) {
            LOGGER.error("Error    :   ", ex);
            throw internalServerError(API_NOT_WORKING);
        } catch (IOException | JSONException | AuthenticationException | NoSuchAlgorithmException | KeyManagementException e) {
            throw internalServerError(e.getMessage());
        }
    }

    private void invokePatchSIService(String payload, String path) {
        try (CloseableHttpClient client = getCloseableHttpClient()) {
            HttpPatch httpPatch = new HttpPatch(baseUrl + path);
            LOGGER.info("URI {} {} ", baseUrl, path);
            LOGGER.debug("Input Payload (PATCH) {}", payload);
            httpPatch.setEntity(new StringEntity(payload));
            httpPatch.setHeader(ACCEPT, APPLICATION_JSON);
            httpPatch.setHeader(CONTENT_TYPE, APPLICATION_JSON);

            UsernamePasswordCredentials cre = new UsernamePasswordCredentials(username, password);
            httpPatch.addHeader(new BasicScheme().authenticate(cre, httpPatch, null));
            CloseableHttpResponse response = client.execute(httpPatch);
            String jsonString = IOUtils.toString(response.getEntity().getContent(), UTF_8);
            B2bErrorResponseDeserializeModel errorModel = objectMapper.readValue(jsonString, B2bErrorResponseDeserializeModel.class);
            LOGGER.info("status Code: {}", response.getStatusLine().getStatusCode());
            if (response.getStatusLine().getStatusCode() >= 300) {
                LOGGER.error("SI Error Details: {}", jsonString);
                if (jsonString.contains("API000393")) {
                    return;
                }
                throw internalServerError(isNotNull(errorModel.getErrorDescription()) ? errorModel.getErrorDescription() : jsonString);
            }
        } catch (org.apache.http.conn.HttpHostConnectException ex) {
            LOGGER.error("Error    :   ", ex);
            throw internalServerError(API_NOT_WORKING);
        } catch (IOException | JSONException | AuthenticationException | NoSuchAlgorithmException | KeyManagementException e) {
            throw internalServerError(e.getMessage());
        }
    }

    private String invokeGetB2bIService(String path) {
        try (CloseableHttpClient client = getCloseableHttpClient()) {
            HttpGet httpGet = new HttpGet(baseUrl + path);
            LOGGER.info("URI {}{} ", baseUrl, path);
            httpGet.setHeader(ACCEPT, MediaType.APPLICATION_XML_VALUE);
            httpGet.setHeader(CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE);
            UsernamePasswordCredentials creds = new UsernamePasswordCredentials(username, password);
            httpGet.addHeader(new BasicScheme().authenticate(creds, httpGet, null));
            CloseableHttpResponse response = client.execute(httpGet);
            String xmlString = IOUtils.toString(response.getEntity().getContent(), UTF_8);
            LOGGER.debug("B2B API Response [GET] {}", xmlString);
            return xmlString;
        } catch (CommunityManagerServiceException cme) {
            LOGGER.info("Error    : ", cme);
            throw internalServerError(cme.getErrorMessage());
        } catch (org.apache.http.conn.HttpHostConnectException ex) {
            LOGGER.error("Error  :   ", ex);
            throw internalServerError(API_NOT_WORKING);
        } catch (IOException | JSONException | AuthenticationException e) {
            LOGGER.error("Exception occurred while invokeGetSIService : ", e);
            throw internalServerError(e.getMessage());
        } catch (NoSuchAlgorithmException | KeyManagementException ee) {
            throw internalServerError(ee.getMessage());
        }
    }

    private String invokeGetSIService(String path) {

        try (CloseableHttpClient client = getCloseableHttpClient()) {
            HttpGet httpGet = new HttpGet(baseUrl + path);
            LOGGER.info("URI {}{} ", baseUrl, path);
            httpGet.setHeader(ACCEPT, APPLICATION_JSON);
            httpGet.setHeader(CONTENT_TYPE, APPLICATION_JSON);
            UsernamePasswordCredentials creds = new UsernamePasswordCredentials(username, password);
            httpGet.addHeader(new BasicScheme().authenticate(creds, httpGet, null));
            CloseableHttpResponse response = client.execute(httpGet);

            String jsonString = IOUtils.toString(response.getEntity().getContent(), UTF_8);
            LOGGER.debug("B2B API Response [GET] {}", jsonString);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 300) {
                if (path.contains(ROUTING_CHANNELS) && statusCode == 404 || path.contains(CD_NODE) || path.contains(CD_MAP_XREF)) {
                    return "[]";
                } else {
                    B2bErrorResponseDeserializeModel errorModel = objectMapper.readValue(jsonString, B2bErrorResponseDeserializeModel.class);
                    throw internalServerError(isNotNull(errorModel.getErrorDescription()) ? errorModel.getErrorDescription() : jsonString);
                }
            }
            return jsonString;
        } catch (CommunityManagerServiceException cme) {
            LOGGER.info("Error    : ", cme);
            throw internalServerError(cme.getErrorMessage());
        } catch (org.apache.http.conn.HttpHostConnectException ex) {
            LOGGER.error("Error : ", ex);
            throw internalServerError(API_NOT_WORKING);
        } catch (IOException | JSONException | AuthenticationException e) {
            LOGGER.error("Exception occurred while invokeGetSIService : ", e);
            throw internalServerError(e.getMessage());
        } catch (NoSuchAlgorithmException | KeyManagementException ee) {
            throw internalServerError(ee.getMessage());
        }
    }

    private void invokeDeleteSIService(String path) {
        try (CloseableHttpClient client = getCloseableHttpClient()) {
            HttpDelete httpDelete = new HttpDelete(baseUrl + path);
            LOGGER.info("Delete URI : {}{}", baseUrl, path);
            httpDelete.setHeader(ACCEPT, APPLICATION_JSON);
            httpDelete.setHeader(CONTENT_TYPE, APPLICATION_JSON);
            UsernamePasswordCredentials creds = new UsernamePasswordCredentials(username, password);
            httpDelete.addHeader(new BasicScheme().authenticate(creds, httpDelete, null));
            CloseableHttpResponse response = client.execute(httpDelete);
            String jsonString = IOUtils.toString(response.getEntity().getContent(), UTF_8);
            if (response.getStatusLine().getStatusCode() == 400 && (!path.contains(FTP_REMOTE_PROFILE) || !path.contains(SSH_REMOTE_PROFILE))) {
                String errorDes = new JSONObject(jsonString).getString(ERROR_DESCRIPTION);
                LOGGER.info("Error from API: {}", errorDes);
                throw internalServerError(errorDes);
            }
            LOGGER.info("B2B Delete Response (DELETE) : {}", jsonString);
        } catch (org.apache.http.conn.HttpHostConnectException ex) {
            LOGGER.error("Error : ", ex);
            throw internalServerError(API_NOT_WORKING);
        } catch (IOException | JSONException | AuthenticationException e) {
            LOGGER.error("Exception occurred while invokeDeleteSIService : ", e);
            throw internalServerError(e.getMessage());
        } catch (NoSuchAlgorithmException | KeyManagementException ee) {
            LOGGER.error("Error :  ", ee);
            throw internalServerError(ee.getMessage());
        }
    }

    private CloseableHttpClient getCloseableHttpClient() throws NoSuchAlgorithmException, KeyManagementException {
        if (baseUrl.trim().startsWith("https")) {
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
            return HttpClients.custom().setSSLSocketFactory(
                    new SSLConnectionSocketFactory(builder.build(), new String[]{"TLSv1.2"}, null, NoopHostnameVerifier.INSTANCE)).build();
        }
        return HttpClients.createDefault();
    }

    @PostConstruct
    public void initB2bUrl() {
        this.baseUrl = this.baseUrl.endsWith("/") ? this.baseUrl.trim() : this.baseUrl + "/";
        if (isB2bActive) {
            if (isNotNull(password)) {
                if (password.startsWith("ENC")) {
                    String apiPwd = removeENC(password);
                    if (isNotNull(apiPwd)) {
                        try {
                            password = passwordUtilityService.decrypt(apiPwd);
                        } catch (CommunityManagerServiceException e) {
                            if (e.getErrorMessage().equals("IllegalBlockSizeException")) {
                                appShutDownService.initiateShutdown("B2Bi API Password is not properly Encrypted (sterling-b2bi.b2bi-api.api.cmks)");
                            }
                        }
                    } else {
                        appShutDownService.initiateShutdown("B2Bi API Password should not be Null/Empty (sterling-b2bi.b2bi-api.api.cmks)");
                    }
                }
            } else {
                appShutDownService.initiateShutdown("B2Bi API Password should not be Null/Empty (sterling-b2bi.b2bi-api.api.cmks), When sterling-b2bi.b2bi-api.active: true");
            }

        }
    }


}

