package com.pe.pcm.sfg;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pe.pcm.common.CommunityManagerNameModel;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.miscellaneous.AppShutDownService;
import com.pe.pcm.miscellaneous.PasswordUtilityService;
import com.pe.pcm.workflow.pem.PemImportPGPModel;
import org.apache.commons.io.IOUtils;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import static com.pe.pcm.exception.GlobalExceptionHandler.customError;
import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;
import static com.pe.pcm.utils.CommonFunctions.isNotNull;
import static com.pe.pcm.utils.CommonFunctions.removeENC;
import static com.pe.pcm.utils.PCMConstants.*;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author Chenchu Kiran Reddy.
 */
@Service
public class SFGApiService {

    private final String username;
    private String password;
    private String baseUrl;
    private final boolean isSFGActive;
    private static final String PGP_PUBLIC_KEYS = "pgppublickeys/";
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final AppShutDownService appShutDownService;
    private final PasswordUtilityService passwordUtilityService;

    private static final Logger LOGGER = LoggerFactory.getLogger(SFGApiService.class);

    public SFGApiService(@Value("${sterling-b2bi.b2bi-api.sfg-api.api.username}") String username,
                         @Value("${sterling-b2bi.b2bi-api.sfg-api.api.cmks}") String password,
                         @Value("${sterling-b2bi.b2bi-api.sfg-api.api.baseUrl}") String baseUrl,
                         @Value("${sterling-b2bi.b2bi-api.sfg-api.active}") boolean isSFGActive, AppShutDownService appShutDownService, PasswordUtilityService passwordUtilityService) {
        this.username = username;
        this.password = password;
        this.baseUrl = baseUrl;
        this.isSFGActive = isSFGActive;
        this.appShutDownService = appShutDownService;
        this.passwordUtilityService = passwordUtilityService;
    }

    private static final String APPLICATION_JSON = "application/json";
    private static final String APPLICATION_XML = "application/xml";
    private static final String ROUTING_CHANNEL_TMPL = "routingchanneltemplates/";

    public List<CommunityManagerNameModel> getRoutingChannelTmpl(String createUserId, String partnerGroupKey, String templateName) {
        List<CommunityManagerNameModel> templateNameList = new ArrayList<>();
        if (isSFGActive) {
            String queryParams = "";
            boolean isAdded = false;
            if (isNotNull(createUserId)) {
                queryParams = "?createUserId=" + createUserId.replace(SPACE, "+");
                isAdded = true;
            }
            if (isNotNull(partnerGroupKey)) {
                queryParams = queryParams + (isAdded ? "&" : "?") + "partnerGroupKey=" + partnerGroupKey.replace(SPACE, "+");
                isAdded = true;
            }
            if (isNotNull(templateName)) {
                queryParams = queryParams + (isAdded ? "&" : "?") + "templateName=" + templateName.replace(SPACE, "+");
            }
            if (isNotNull(createUserId) && isNotNull(partnerGroupKey) && isNotNull(templateName)) {
                queryParams = queryParams + (isAdded ? "&" : "?") + "templateName";
            }
            try {
                String jsonString = invokeGetService(ROUTING_CHANNEL_TMPL + queryParams, APPLICATION_JSON);
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray authorizedUserKeys = jsonObject.getJSONArray("fgRoutingChannelTemplateXSDTypes");
                for (int index = 0; index < authorizedUserKeys.length(); index++) {
                    templateNameList.add(new CommunityManagerNameModel(authorizedUserKeys.getJSONObject(index).getString("templateName")));
                }
                return templateNameList;
            } catch (CommunityManagerServiceException ce) {
                if (ce.getErrorMessage().equals("400")) {
                    templateNameList.add(new CommunityManagerNameModel(""));
                    return templateNameList;
                } else {
                    throw internalServerError(ce.getErrorMessage());
                }
            }

        } else {
            throw internalServerError("Please reach the CM Administration team, SFG Api not configured");
        }
    }

    public void uploadPGPCert(PemImportPGPModel pemImportPGPModel) {
        try {
            invokePostSIService(objectMapper.writeValueAsString(pemImportPGPModel), PGP_PUBLIC_KEYS, APPLICATION_JSON);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public String getPGPCert(String certName) {
        String path = PGP_PUBLIC_KEYS + "?name=" + certName;
        try {
             path = URLEncoder.encode(PGP_PUBLIC_KEYS + "?name=" + certName,"UTF-8");
        } catch (Exception ex) {

        }
        return invokeGetService(path, APPLICATION_XML);
    }

    public void deletePGPCert(String objectId) {
        String path = PGP_PUBLIC_KEYS + objectId;
       /* try {
            //path = URLEncoder.encode(PGP_PUBLIC_KEYS + objectId,"UTF-8");
        } catch (Exception ex) {

        }*/
        invokeDeleteSIService(path,APPLICATION_XML);
    }

    private String invokeGetService(String path, String contentType) {

        try (CloseableHttpClient client = getCloseableHttpClient()) {
            HttpGet httpGet = new HttpGet(baseUrl + path);
            LOGGER.info("URI {}{} ", baseUrl, path);
            httpGet.setHeader(ACCEPT, contentType);
            httpGet.setHeader(CONTENT_TYPE, contentType);
            UsernamePasswordCredentials creds = new UsernamePasswordCredentials(username, password);
            httpGet.addHeader(new BasicScheme().authenticate(creds, httpGet, null));
            CloseableHttpResponse response = client.execute(httpGet);

            String responseString = IOUtils.toString(response.getEntity().getContent(), UTF_8);
            LOGGER.info("SFG API Response [GET] {}", responseString);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 300) {
                if (path.contains(ROUTING_CHANNEL_TMPL) && statusCode == 404) {
                    throw internalServerError("400");
                }
                if (contentType.equals(APPLICATION_JSON)) {
                    throw internalServerError(new JSONObject(responseString).getString(ERROR_DESCRIPTION));
                } else if (contentType.equals(APPLICATION_XML)) {
                    Document doc = Jsoup.parse(responseString, "", Parser.xmlParser());
                    throw internalServerError(doc.select(ERROR_DESCRIPTION).text());
                }
            }
            return responseString;
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
                throw internalServerError(new JSONObject(jsonString).getString(ERROR_DESCRIPTION));
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

    private void invokeDeleteSIService(String path , String contentType) {
        try (CloseableHttpClient client = getCloseableHttpClient()) {
            HttpDelete httpDelete = new HttpDelete(baseUrl + path);
            LOGGER.info("Delete URI : {}{}", baseUrl, path);
            httpDelete.setHeader(ACCEPT, contentType);
            httpDelete.setHeader(CONTENT_TYPE, contentType);
            UsernamePasswordCredentials creds = new UsernamePasswordCredentials(username, password);
            httpDelete.addHeader(new BasicScheme().authenticate(creds, httpDelete, null));
            CloseableHttpResponse response = client.execute(httpDelete);
            String jsonString = IOUtils.toString(response.getEntity().getContent(), UTF_8);
            LOGGER.info("SFG Delete Response (DELETE) : {}", jsonString);
            if (response.getStatusLine().getStatusCode() >= 300) {
                Document doc = Jsoup.parse(jsonString, "", Parser.xmlParser());
                if (!doc.select(ERROR_DESCRIPTION).text().equals("Could not rollback with auto-commit set on")) {
                    throw customError(Integer.parseInt(doc.select(ERROR_CODE).text()), doc.select(ERROR_DESCRIPTION).text());
                }
                //throw internalServerError(new JSONObject(jsonString).getString(ERROR_DESCRIPTION));
            }
        } catch (CommunityManagerServiceException cme) {
            LOGGER.info("Error    : ", cme);
            throw internalServerError(cme.getErrorMessage());
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
    public void initSFGUrl() {
        this.baseUrl = this.baseUrl.endsWith("/") ? this.baseUrl.trim() : this.baseUrl + "/";
        if (isSFGActive) {
            if (isNotNull(password)) {
                if (password.startsWith("ENC")) {
                    String apiPwd = removeENC(password);
                    if (isNotNull(apiPwd)) {
                        try {
                            password = passwordUtilityService.decrypt(apiPwd);
                        } catch (CommunityManagerServiceException e) {
                            if (e.getErrorMessage().equals("IllegalBlockSizeException")) {
                                appShutDownService.initiateShutdown("SFG API Password is not properly Encrypted (sterling-b2bi.sfg-api.api.cmks)");
                            }
                        }
                    } else {
                        appShutDownService.initiateShutdown("SFG API Password should not be Null/Empty (sterling-b2bi.sfg-api.api.cmks)");
                    }
                }

            } else {
                appShutDownService.initiateShutdown("SFG API Password should not be Null/Empty (sterling-b2bi.sfg-api.api.cmks), When sterling-b2bi.sfg-api.active: true");
            }

        }
    }
}
