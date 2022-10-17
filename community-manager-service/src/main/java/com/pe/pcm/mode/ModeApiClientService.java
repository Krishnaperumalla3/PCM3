package com.pe.pcm.mode;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.miscellaneous.CmCloseableHttpClient;
import org.apache.commons.io.IOUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;
import static com.pe.pcm.utils.CommonFunctions.isNotNull;
import static com.pe.pcm.utils.PCMConstants.*;
import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class ModeApiClientService {


    private static final Logger LOGGER = LoggerFactory.getLogger(ModeApiClientService.class);

    private static final String APPLICATION_JSON = "application/json";

    private final CmCloseableHttpClient cmCloseableHttpClient;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public ModeApiClientService(CmCloseableHttpClient cmCloseableHttpClient) {
        this.cmCloseableHttpClient = cmCloseableHttpClient;
    }

    public String modeDocumentSearchApi(CommonModel commonModel) {
        return modeActualGetCall(commonModel.getModeDocumentSearchModel(), commonModel.getActualLightWellUrl(), commonModel.getOauth2AuthModelForMode());
    }

    public String modeActualGetCallForIdentitySearch(CommonModel commonModel, String search) {
        String actualApi = getPath(commonModel.getActualLightWellUrl()) + (isNotNull(search) ? search : "");
        return modeActualGetCall(commonModel.getIdentitySearchModel(), actualApi, commonModel.getOauth2AuthModelForMode());
    }

    public String modeActualGetCallForSendRuleSearch(CommonModel commonModel) {
        return modeActualGetCall(commonModel.getSendRuleSearchModel(), getPath(commonModel.getActualLightWellUrl()), commonModel.getOauth2AuthModelForMode());
    }

    public String modeActualPostAPIForIdentity(CommonModel commonModel) {
        return modeActualPostAPICall(commonModel.getIdentityModel(), commonModel.getActualLightWellUrl(), commonModel.getOauth2AuthModelForMode());
    }

    public String modeActualPostApiForAddRule(CommonModel commonModel) {
        return modeActualPostAPICall(commonModel.getAddRuleModel(), commonModel.getActualLightWellUrl(), commonModel.getOauth2AuthModelForMode());
    }

    public String modeActualPutAPIForIdentity(CommonModel commonModel, String id) {
        return modeActualPutAPICall(commonModel.getIdentityModel(), commonModel.getActualLightWellUrl(), commonModel.getOauth2AuthModelForMode(), id);
    }

    public String modeActualPutAPIForAddRule(CommonModel commonModel, String id) {
        return modeActualPutAPICall(commonModel.getAddRuleModel(), commonModel.getActualLightWellUrl(), commonModel.getOauth2AuthModelForMode(), id);
    }


    private String modeActualPostAPICall(Object payLoad, String actualLightWellApi, Oauth2AuthModelForMode oauth2AuthModelForMode) {
        try (CloseableHttpClient client = cmCloseableHttpClient.getCloseableHttpClient(actualLightWellApi)) {
            HttpPost httpPost = new HttpPost(actualLightWellApi);
            LOGGER.info("URI From User {}", actualLightWellApi);
            httpPost.setHeader(ACCEPT, APPLICATION_JSON);
            httpPost.setHeader(CONTENT_TYPE, APPLICATION_JSON);
            if (isNotNull(payLoad)) {
                httpPost.setEntity(new StringEntity(objectMapper.writeValueAsString(payLoad)));
            }
            LOGGER.debug("Input PayLoad (POST) {} ", objectMapper.writeValueAsString(payLoad));
            httpPost.addHeader(oauth2AuthModelForMode.getTokenHeader(),
                    oauth2AuthModelForMode.getTokenPrefix() + getOauth2Token(oauth2AuthModelForMode));
            CloseableHttpResponse response = client.execute(httpPost);
            LOGGER.info("API response status Code : {} : ", response.getStatusLine().getStatusCode());
            String responseString = IOUtils.toString(response.getEntity().getContent(), UTF_8);
            LOGGER.info("status message : {} : ", responseString);
            return responseString;
        } catch (org.apache.http.conn.HttpHostConnectException ex) {
            LOGGER.error("Error : ", ex);
            throw internalServerError("Server API not working properly, please contact system admin, server url: " + actualLightWellApi);
        } catch (IOException | JSONException e) {
            LOGGER.error("Exception occurred while invokePostService : ", e);
            throw internalServerError(e.getMessage());
        } catch (NoSuchAlgorithmException | KeyManagementException ee) {
            throw internalServerError(ee.getMessage());
        }
    }

    private String modeActualPutAPICall(Object payLoad, String actualLightWellApi, Oauth2AuthModelForMode oauth2AuthModelForMode, String id) {
        try (CloseableHttpClient client = cmCloseableHttpClient.getCloseableHttpClient(actualLightWellApi)) {
            HttpPut httpPut = new HttpPut(getPath(actualLightWellApi) + (isNotNull(id) ? id : ""));
            httpPut.setHeader(ACCEPT, APPLICATION_JSON);
            httpPut.setHeader(CONTENT_TYPE, APPLICATION_JSON);
            URIBuilder uriBuilder = new URIBuilder(httpPut.getURI());
            httpPut.setURI(uriBuilder.build());
            LOGGER.info("URI From User {}", actualLightWellApi);
            LOGGER.info("End URI {} ", httpPut.getURI());
            if (isNotNull(payLoad)) {
                httpPut.setEntity(new StringEntity(objectMapper.writeValueAsString(payLoad)));
            }
            LOGGER.debug("Input PayLoad (POST) {} ", objectMapper.writeValueAsString(payLoad));
            httpPut.addHeader(oauth2AuthModelForMode.getTokenHeader(),
                    oauth2AuthModelForMode.getTokenPrefix() + getOauth2Token(oauth2AuthModelForMode));
            CloseableHttpResponse response = client.execute(httpPut);
            LOGGER.info("API response status Code : {} : ", response.getStatusLine().getStatusCode());
            String responseString = IOUtils.toString(response.getEntity().getContent(), UTF_8);
            LOGGER.info("status message : {} : ", responseString);
            return responseString;
        } catch (org.apache.http.conn.HttpHostConnectException ex) {
            LOGGER.error("Error : ", ex);
            throw internalServerError("Server API not working properly, please contact system admin, server url: " + actualLightWellApi);
        } catch (IOException | JSONException e) {
            LOGGER.error("Exception occurred while invokePostService : ", e);
            throw internalServerError(e.getMessage());
        } catch (NoSuchAlgorithmException | KeyManagementException | URISyntaxException ee) {
            throw internalServerError(ee.getMessage());
        }
    }

    private String modeActualGetCall(Object payLoad, String actualLightWellApi, Oauth2AuthModelForMode oauth2AuthModelForMode) {
        try (CloseableHttpClient client = cmCloseableHttpClient.getCloseableHttpClient(actualLightWellApi)) {
            HttpGet httpGet = new HttpGet(actualLightWellApi);
            URIBuilder uriBuilder = new URIBuilder(httpGet.getURI());
            Map<String, Object> keyValue = objectMapper.convertValue(payLoad, new TypeReference<Map<String, Object>>() {
            });
            System.out.println(" " + keyValue);
            keyValue.forEach((key, value) -> {
                if (isNotNull(key) && isNotNull(value)) {
                    if (value.toString().startsWith("[")) {
                        Arrays.asList(value.toString().replaceAll("[\\[\\]]", "").split(",")).forEach(s1 -> uriBuilder.addParameter(key, s1));
                    } else if (key.equals("authorities")) {
                        uriBuilder.addParameter("authorities[0].authority", String.valueOf(value));
                    } else {
                        uriBuilder.addParameter(key, String.valueOf(value));
                    }
                }
            });
            LOGGER.info(uriBuilder.toString());
            httpGet.setURI(uriBuilder.build());
            LOGGER.info("URI {} ", httpGet.getURI());
            httpGet.addHeader(ACCEPT, APPLICATION_JSON);
            httpGet.addHeader(oauth2AuthModelForMode.getTokenHeader(),
                    oauth2AuthModelForMode.getTokenPrefix() + getOauth2Token(oauth2AuthModelForMode));
            CloseableHttpResponse response = client.execute(httpGet);
            String jsonString = IOUtils.toString(response.getEntity().getContent(), UTF_8);
            LOGGER.debug("Server API Response [GET] {}", jsonString);
            int statusCode = response.getStatusLine().getStatusCode();
            LOGGER.debug("Status Code: {}", statusCode);
            return jsonString;
        } catch (CommunityManagerServiceException cme) {
            LOGGER.info("Error : ", cme);
            throw internalServerError(cme.getErrorMessage());
        } catch (org.apache.http.conn.HttpHostConnectException ex) {
            LOGGER.error("Error : ", ex);
            throw internalServerError(API_NOT_WORKING);
        } catch (IOException | JSONException e) {
            LOGGER.error("Exception occurred while getApiConnect : ", e);
            throw internalServerError(e.getMessage());
        } catch (NoSuchAlgorithmException | KeyManagementException | URISyntaxException ee) {
            throw internalServerError(ee.getMessage());
        }
    }

    private String getOauth2Token(Oauth2AuthModelForMode oauth2AuthModel) {
        try (CloseableHttpClient client = cmCloseableHttpClient.getCloseableHttpClient(oauth2AuthModel.getTokenApiUrl())) {
            HttpPost httpPost = new HttpPost(oauth2AuthModel.getTokenApiUrl().trim());
            List<NameValuePair> urlParameters = new ArrayList<>();
            urlParameters.add(new BasicNameValuePair("grant_type", oauth2AuthModel.getGrantType()));
            urlParameters.add(new BasicNameValuePair("username", oauth2AuthModel.getUsername()));
            urlParameters.add(new BasicNameValuePair("password", oauth2AuthModel.getPassword()));
            httpPost.setEntity(new UrlEncodedFormEntity(urlParameters));
            LOGGER.debug("Request {}", urlParameters);
            httpPost.setHeader("Authorization", "Basic " +
                    Base64.encodeBase64String((oauth2AuthModel.getClientID() + ":" + oauth2AuthModel.getClientSecret()).getBytes()));
            CloseableHttpResponse response = client.execute(httpPost);
            String finalResponse = IOUtils.toString(response.getEntity().getContent(), UTF_8);
            LOGGER.debug("Final Response from API {}", finalResponse);
            return new JSONObject(finalResponse).getString(oauth2AuthModel.getTokenKey());
        } catch (Exception ex) {
            LOGGER.error("{} at OAuth2 Token : {}", EXCEPTION_OCCURRED, ex);
            throw internalServerError(ex.getMessage());
        }
    }

    private String getPath(String path) {
        return path.endsWith("/") ? path : path + "/";
    }

}