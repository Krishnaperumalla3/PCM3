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

package com.pe.pcm.apiconnecct;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pe.pcm.apiconnect.*;
import com.pe.pcm.b2b.deserialize.B2bErrorResponseDeserializeModel;
import com.pe.pcm.constants.ApiAuthConstants;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.miscellaneous.CmCloseableHttpClient;
import org.apache.commons.io.IOUtils;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;
import static com.pe.pcm.utils.CommonFunctions.isNotNull;
import static com.pe.pcm.utils.PCMConstants.*;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author Kiran Reddy.
 */
@Service
public class ApiConnectClientService {


    private static final String APPLICATION_JSON = "application/json";

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiConnectClientService.class);
    private final CmCloseableHttpClient cmCloseableHttpClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public ApiConnectClientService(CmCloseableHttpClient cmCloseableHttpClient) {
        this.cmCloseableHttpClient = cmCloseableHttpClient;
    }

    public String postAPIConnect(APIProxyEndpointModel apiProxyEndpointModel, String payload, Map<String, String[]> params, Map<String, String> headers) {
        try (CloseableHttpClient client = cmCloseableHttpClient.getCloseableHttpClient(apiProxyEndpointModel.getServerUrl())) {
            HttpPost httpPost = new HttpPost(apiProxyEndpointModel.getServerUrl());
            AtomicBoolean acceptAdded = new AtomicBoolean(FALSE);
            AtomicBoolean contentTypeAdded = new AtomicBoolean(FALSE);
            LOGGER.info("URI From User {}", apiProxyEndpointModel.getServerUrl());
            apiProxyEndpointModel.getApiHeaderDataList().forEach(apihpDataModel -> {
                if (apihpDataModel.getRequired()) {
                    if (apihpDataModel.getDynamicValue()) {
                        httpPost.addHeader(apihpDataModel.getKey(), headers.get(apihpDataModel.getKey()));
                    } else if (isNotNull(apihpDataModel.getValue())) {
                        httpPost.addHeader(apihpDataModel.getKey(), apihpDataModel.getValue());
                    } else {
                        throw internalServerError("Please provide value for " + apihpDataModel.getKey() + " Header");
                    }
                    if (isNotNull(apihpDataModel.getKey())) {
                        if (apihpDataModel.getKey().equals(ACCEPT)) {
                            acceptAdded.set(TRUE);
                        }
                        if (apihpDataModel.getKey().equals(CONTENT_TYPE)) {
                            contentTypeAdded.set(TRUE);
                        }
                    }
                }
            });
            if (!acceptAdded.get()) {
                httpPost.setHeader(ACCEPT, APPLICATION_JSON);
            }
            if (!contentTypeAdded.get()) {
                httpPost.setHeader(CONTENT_TYPE, APPLICATION_JSON);
            }

            URIBuilder uriBuilder = new URIBuilder(httpPost.getURI());
            apiProxyEndpointModel.getApiParamDataList().forEach(apihpDataModel -> {
                if (apihpDataModel.getRequired()) {
                    if (apihpDataModel.getDynamicValue() && !params.isEmpty()) {
                        if (params.containsKey(apihpDataModel.getKey())) {
                            uriBuilder.addParameter(apihpDataModel.getKey(), params.get(apihpDataModel.getKey())[0]);
                        }
                    } else if (isNotNull(apihpDataModel.getValue())) {
                        uriBuilder.addParameter(apihpDataModel.getKey(), apihpDataModel.getValue());
                    } else {
                        throw internalServerError("Please provide value for " + apihpDataModel.getKey() + " Parameter");
                    }
                }
            });
            httpPost.setURI(uriBuilder.build());
            LOGGER.info("End URI {} ", httpPost.getURI());
            if (isNotNull(payload)) {
                httpPost.setEntity(new StringEntity(payload));
            }
            LOGGER.debug("Input PayLoad (POST) {} ", payload);
            if (apiProxyEndpointModel.getServerApiAuthData().getAuthType().equals(ApiAuthConstants.BASIC_AUTH)) {
                UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(
                        apiProxyEndpointModel.getServerApiAuthData().getBasicAuth().getUsername(),
                        apiProxyEndpointModel.getServerApiAuthData().getBasicAuth().getPassword()
                );
                httpPost.addHeader(new BasicScheme().authenticate(credentials, httpPost, null));
            }
            if (apiProxyEndpointModel.getServerApiAuthData().getAuthType().equals(ApiAuthConstants.TOKEN_AUTH)) {
                httpPost.addHeader(apiProxyEndpointModel.getServerApiAuthData().getTokenAuth().getTokenHeader(),
                        apiProxyEndpointModel.getServerApiAuthData().getTokenAuth().getTokenPrefix().trim() + " " + getSessionToken(apiProxyEndpointModel));
            }
            if (apiProxyEndpointModel.getServerApiAuthData().getAuthType().equals(ApiAuthConstants.OAUTH2_2_0)) {
                httpPost.addHeader(apiProxyEndpointModel.getServerApiAuthData().getoAuth2Auth().getTokenHeader(),
                        apiProxyEndpointModel.getServerApiAuthData().getoAuth2Auth().getTokenPrefix() + getOauth2Token(apiProxyEndpointModel.getServerApiAuthData().getoAuth2Auth()));
            }
            CloseableHttpResponse response = client.execute(httpPost);
            LOGGER.info("API response status Code : {} : ", response.getStatusLine().getStatusCode());
            String responseString = IOUtils.toString(response.getEntity().getContent(), UTF_8);
            LOGGER.info("status message : {} : ", responseString);
            return responseString;
        } catch (org.apache.http.conn.HttpHostConnectException ex) {
            LOGGER.error("Error : ", ex);
            throw internalServerError("Server API not working properly, please contact system admin, server url: " + apiProxyEndpointModel.getServerUrl());
        } catch (IOException | JSONException | AuthenticationException e) {
            LOGGER.error("Exception occurred while invokePostService : ", e);
            throw internalServerError(e.getMessage());
        } catch (NoSuchAlgorithmException | KeyManagementException | URISyntaxException ee) {
            throw internalServerError(ee.getMessage());
        }
    }

    public String putAPIConnect(APIProxyEndpointModel apiProxyEndpointModel, String payload, String id, Map<String, String[]> params, Map<String, String> headers) {
        try (CloseableHttpClient client = cmCloseableHttpClient.getCloseableHttpClient(apiProxyEndpointModel.getServerUrl())) {
            HttpPut httpPut = new HttpPut(apiProxyEndpointModel.getServerUrl() + (isNotNull(id) ? id : ""));
            AtomicBoolean acceptAdded = new AtomicBoolean(FALSE);
            AtomicBoolean contentTypeAdded = new AtomicBoolean(FALSE);
            apiProxyEndpointModel.getApiHeaderDataList().forEach(apihpDataModel -> {
                if (apihpDataModel.getRequired()) {
                    if (apihpDataModel.getDynamicValue()) {
                        httpPut.addHeader(apihpDataModel.getKey(), headers.get(apihpDataModel.getKey()));
                    } else if (isNotNull(apihpDataModel.getValue())) {
                        httpPut.addHeader(apihpDataModel.getKey(), apihpDataModel.getValue());
                    } else {
                        throw internalServerError("Please provide value for " + apihpDataModel.getKey() + " Header");
                    }
                    if (isNotNull(apihpDataModel.getKey())) {
                        if (apihpDataModel.getKey().equals(ACCEPT)) {
                            acceptAdded.set(TRUE);
                        }
                        if (apihpDataModel.getKey().equals(CONTENT_TYPE)) {
                            contentTypeAdded.set(TRUE);
                        }
                    }
                }
            });
            if (!acceptAdded.get()) {
                httpPut.setHeader(ACCEPT, APPLICATION_JSON);
            }
            if (!contentTypeAdded.get()) {
                httpPut.setHeader(CONTENT_TYPE, APPLICATION_JSON);
            }
            URIBuilder uriBuilder = new URIBuilder(httpPut.getURI());
            apiProxyEndpointModel.getApiParamDataList().forEach(apihpDataModel -> {
                if (apihpDataModel.getRequired()) {
                    if (apihpDataModel.getDynamicValue() && !params.isEmpty()) {
                        if (params.containsKey(apihpDataModel.getKey())) {
                            uriBuilder.addParameter(apihpDataModel.getKey(), params.get(apihpDataModel.getKey())[0]);
                        }
                    } else if (isNotNull(apihpDataModel.getValue())) {
                        uriBuilder.addParameter(apihpDataModel.getKey(), apihpDataModel.getValue());
                    } else {
                        throw internalServerError("Please provide value for " + apihpDataModel.getKey() + " Parameter");
                    }
                }
            });
            httpPut.setURI(uriBuilder.build());
            LOGGER.info("URI From User {}", apiProxyEndpointModel.getServerUrl());
            LOGGER.info("End URI {} ", httpPut.getURI());
            if (isNotNull(payload)) {
                httpPut.setEntity(new StringEntity(payload));
            }
            LOGGER.debug("Input PayLoad (POST) {} ", payload);
            if (apiProxyEndpointModel.getServerApiAuthData().getAuthType().equals(ApiAuthConstants.BASIC_AUTH)) {
                UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(
                        apiProxyEndpointModel.getServerApiAuthData().getBasicAuth().getUsername(),
                        apiProxyEndpointModel.getServerApiAuthData().getBasicAuth().getPassword()
                );
                httpPut.addHeader(new BasicScheme().authenticate(credentials, httpPut, null));
            }
            if (apiProxyEndpointModel.getServerApiAuthData().getAuthType().equals(ApiAuthConstants.TOKEN_AUTH)) {
                httpPut.addHeader(apiProxyEndpointModel.getServerApiAuthData().getTokenAuth().getTokenHeader(),
                        apiProxyEndpointModel.getServerApiAuthData().getTokenAuth().getTokenPrefix() + getSessionToken(apiProxyEndpointModel));
            }
            if (apiProxyEndpointModel.getServerApiAuthData().getAuthType().equals(ApiAuthConstants.OAUTH2_2_0)) {
                httpPut.addHeader(apiProxyEndpointModel.getServerApiAuthData().getoAuth2Auth().getTokenHeader(),
                        apiProxyEndpointModel.getServerApiAuthData().getoAuth2Auth().getTokenPrefix() + getOauth2Token(apiProxyEndpointModel.getServerApiAuthData().getoAuth2Auth()));
            }
            CloseableHttpResponse response = client.execute(httpPut);
            LOGGER.info("API response status Code : {} : ", response.getStatusLine().getStatusCode());
            String responseString = IOUtils.toString(response.getEntity().getContent(), UTF_8);
            LOGGER.info("status message : {} : ", responseString);
            return responseString;
        } catch (org.apache.http.conn.HttpHostConnectException ex) {
            LOGGER.error("Error : ", ex);
            throw internalServerError("Server API not working properly, please contact system admin, server url: " + apiProxyEndpointModel.getServerUrl());
        } catch (IOException | JSONException | AuthenticationException e) {
            LOGGER.error("Exception occurred while invokePostService : ", e);
            throw internalServerError(e.getMessage());
        } catch (NoSuchAlgorithmException | KeyManagementException | URISyntaxException ee) {
            throw internalServerError(ee.getMessage());
        }
    }

    public String getApiConnect(APIProxyEndpointModel apiProxyEndpointModel, Map<String, String[]> params, Map<String, String> headers) {
        try (CloseableHttpClient client = cmCloseableHttpClient.getCloseableHttpClient(apiProxyEndpointModel.getServerUrl())) {
            HttpGet httpGet = new HttpGet(apiProxyEndpointModel.getServerUrl());
            if (isNotNull(apiProxyEndpointModel.getApiHeaderDataList()) && !apiProxyEndpointModel.getApiHeaderDataList().isEmpty()) {
                apiProxyEndpointModel.getApiHeaderDataList().forEach(apihpDataModel -> {
                    if (apihpDataModel.getRequired()) {
                        if (apihpDataModel.getDynamicValue()) {
                            httpGet.addHeader(apihpDataModel.getKey(), headers.get(apihpDataModel.getKey()));
                        } else if (isNotNull(apihpDataModel.getValue())) {
                            httpGet.addHeader(apihpDataModel.getKey(), apihpDataModel.getValue());
                        } else {
                            throw internalServerError("Please provide value for " + apihpDataModel.getKey() + " Header");
                        }
                    }
                });
            }
            URIBuilder uriBuilder = new URIBuilder(httpGet.getURI());
            if (isNotNull(apiProxyEndpointModel.getApiParamDataList()) && !apiProxyEndpointModel.getApiParamDataList().isEmpty()) {
                apiProxyEndpointModel.getApiParamDataList().forEach(apihpDataModel -> {
                    if (apihpDataModel.getRequired()) {
                        if (apihpDataModel.getDynamicValue() && !params.isEmpty()) {
                            if (params.containsKey(apihpDataModel.getKey())) {
                                uriBuilder.addParameter(apihpDataModel.getKey(), params.get(apihpDataModel.getKey())[0]);
                            }
                        } else if (isNotNull(apihpDataModel.getValue())) {
                            uriBuilder.addParameter(apihpDataModel.getKey(), apihpDataModel.getValue());
                        } else {
                            throw internalServerError("Please provide value for " + apihpDataModel.getKey() + " Parameter");
                        }
                    }
                });
            }
            httpGet.setURI(uriBuilder.build());
            LOGGER.info("URI {} ", httpGet.getURI());
            if (apiProxyEndpointModel.getServerApiAuthData().getAuthType().equals(ApiAuthConstants.BASIC_AUTH)) {
                UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(
                        apiProxyEndpointModel.getServerApiAuthData().getBasicAuth().getUsername(),
                        apiProxyEndpointModel.getServerApiAuthData().getBasicAuth().getPassword()
                );
                httpGet.addHeader(new BasicScheme().authenticate(credentials, httpGet, null));
            }
            if (apiProxyEndpointModel.getServerApiAuthData().getAuthType().equals(ApiAuthConstants.TOKEN_AUTH)) {
                httpGet.addHeader(apiProxyEndpointModel.getServerApiAuthData().getTokenAuth().getTokenHeader(),
                        apiProxyEndpointModel.getServerApiAuthData().getTokenAuth().getTokenPrefix() + getSessionToken(apiProxyEndpointModel));
            }
            if (apiProxyEndpointModel.getServerApiAuthData().getAuthType().equals(ApiAuthConstants.OAUTH2_2_0)) {
                LOGGER.debug("HEADER LOCAL DEBUG [GET] {}", apiProxyEndpointModel.getServerApiAuthData().getoAuth2Auth().getTokenHeader());
                httpGet.addHeader(apiProxyEndpointModel.getServerApiAuthData().getoAuth2Auth().getTokenHeader(),
                        apiProxyEndpointModel.getServerApiAuthData().getoAuth2Auth().getTokenPrefix() + getOauth2Token(apiProxyEndpointModel.getServerApiAuthData().getoAuth2Auth()));
            }
            CloseableHttpResponse response = client.execute(httpGet);

            String jsonString = IOUtils.toString(response.getEntity().getContent(), UTF_8);
            LOGGER.debug("Server API Response [GET] {}", jsonString);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 300) {
                B2bErrorResponseDeserializeModel errorModel = objectMapper.readValue(jsonString, B2bErrorResponseDeserializeModel.class);
                throw internalServerError(isNotNull(errorModel.getErrorDescription()) ? errorModel.getErrorDescription() : jsonString);
            }
            return jsonString;
        } catch (CommunityManagerServiceException cme) {
            LOGGER.info("Error : ", cme);
            throw internalServerError(cme.getErrorMessage());
        } catch (org.apache.http.conn.HttpHostConnectException ex) {
            LOGGER.error("Error : ", ex);
            throw internalServerError(API_NOT_WORKING);
        } catch (IOException | JSONException | AuthenticationException e) {
            LOGGER.error("Exception occurred while getApiConnect : ", e);
            throw internalServerError(e.getMessage());
        } catch (NoSuchAlgorithmException | KeyManagementException | URISyntaxException ee) {
            throw internalServerError(ee.getMessage());
        }
    }

    public String deleteApiConnect(APIProxyEndpointModel apiProxyEndpointModel, Map<String, String[]> params, Map<String, String> headers) {
        try (CloseableHttpClient client = cmCloseableHttpClient.getCloseableHttpClient(apiProxyEndpointModel.getServerUrl())) {
            HttpDelete httpDelete = new HttpDelete(apiProxyEndpointModel.getServerUrl());
            apiProxyEndpointModel.getApiHeaderDataList().forEach(apihpDataModel -> {
                if (apihpDataModel.getRequired()) {
                    if (apihpDataModel.getDynamicValue()) {
                        httpDelete.addHeader(apihpDataModel.getKey(), headers.get(apihpDataModel.getKey()));
                    } else if (isNotNull(apihpDataModel.getValue())) {
                        httpDelete.addHeader(apihpDataModel.getKey(), apihpDataModel.getValue());
                    } else {
                        throw internalServerError("Please provide value for " + apihpDataModel.getKey() + " Header");
                    }
                }
            });
            URIBuilder uriBuilder = new URIBuilder(httpDelete.getURI());
            apiProxyEndpointModel.getApiParamDataList().forEach(apihpDataModel -> {
                if (apihpDataModel.getRequired()) {
                    if (apihpDataModel.getDynamicValue() && !params.isEmpty()) {
                        if (params.containsKey(apihpDataModel.getKey())) {
                            uriBuilder.addParameter(apihpDataModel.getKey(), params.get(apihpDataModel.getKey())[0]);
                        }
                    } else if (isNotNull(apihpDataModel.getValue())) {
                        uriBuilder.addParameter(apihpDataModel.getKey(), apihpDataModel.getValue());
                    } else {
                        throw internalServerError("Please provide value for " + apihpDataModel.getKey() + " Parameter");
                    }
                }
            });
            httpDelete.setURI(uriBuilder.build());
            LOGGER.info("URI {} ", httpDelete.getURI());
            if (apiProxyEndpointModel.getServerApiAuthData().getAuthType().equals(ApiAuthConstants.BASIC_AUTH)) {
                UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(
                        apiProxyEndpointModel.getServerApiAuthData().getBasicAuth().getUsername(),
                        apiProxyEndpointModel.getServerApiAuthData().getBasicAuth().getPassword()
                );
                httpDelete.addHeader(new BasicScheme().authenticate(credentials, httpDelete, null));
            }
            if (apiProxyEndpointModel.getServerApiAuthData().getAuthType().equals(ApiAuthConstants.TOKEN_AUTH)) {
                httpDelete.addHeader(apiProxyEndpointModel.getServerApiAuthData().getTokenAuth().getTokenHeader(),
                        apiProxyEndpointModel.getServerApiAuthData().getTokenAuth().getTokenPrefix() + getSessionToken(apiProxyEndpointModel));
            }
            if (apiProxyEndpointModel.getServerApiAuthData().getAuthType().equals(ApiAuthConstants.OAUTH2_2_0)) {
                httpDelete.addHeader(apiProxyEndpointModel.getServerApiAuthData().getoAuth2Auth().getTokenHeader(),
                        apiProxyEndpointModel.getServerApiAuthData().getoAuth2Auth().getTokenPrefix() + getOauth2Token(apiProxyEndpointModel.getServerApiAuthData().getoAuth2Auth()));
            }
            CloseableHttpResponse response = client.execute(httpDelete);
            String jsonString = IOUtils.toString(response.getEntity().getContent(), UTF_8);
            LOGGER.debug("Server API Response [GET] {}", jsonString);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 300) {
                B2bErrorResponseDeserializeModel errorModel = objectMapper.readValue(jsonString, B2bErrorResponseDeserializeModel.class);
                throw internalServerError(isNotNull(errorModel.getErrorDescription()) ? errorModel.getErrorDescription() : jsonString);
            }
            return jsonString;
        } catch (CommunityManagerServiceException cme) {
            LOGGER.info("Error : ", cme);
            throw internalServerError(cme.getErrorMessage());
        } catch (org.apache.http.conn.HttpHostConnectException ex) {
            LOGGER.error("Error : ", ex);
            throw internalServerError(API_NOT_WORKING);
        } catch (IOException | JSONException | AuthenticationException e) {
            LOGGER.error("Exception occurred while deleteApiConnect : ", e);
            throw internalServerError(e.getMessage());
        } catch (NoSuchAlgorithmException | KeyManagementException | URISyntaxException ee) {
            throw internalServerError(ee.getMessage());
        }
    }

    public String getOutlookResponse(OutLookApiModel outLookApiModel, Map<String, String[]> params, Map<String, String> headers) {
        try (CloseableHttpClient client = cmCloseableHttpClient.getCloseableHttpClient(outLookApiModel.getActualApi())) {
            HttpGet httpGet = new HttpGet(outLookApiModel.getActualApi());
            if (isNotNull(outLookApiModel.getApiHeaderDataList()) && !outLookApiModel.getApiHeaderDataList().isEmpty()) {
                outLookApiModel.getApiHeaderDataList().forEach(apihpDataModel -> {
                    if (apihpDataModel.getRequired()) {
                        if (apihpDataModel.getDynamicValue()) {
                            httpGet.addHeader(apihpDataModel.getKey(), headers.get(apihpDataModel.getKey()));
                        } else if (isNotNull(apihpDataModel.getValue())) {
                            httpGet.addHeader(apihpDataModel.getKey(), apihpDataModel.getValue());
                        } else {
                            throw internalServerError("Please provide value for " + apihpDataModel.getKey() + " Header");
                        }
                    }
                });
            }
            URIBuilder uriBuilder = new URIBuilder(httpGet.getURI());
            if (isNotNull(outLookApiModel.getApiParamDataList()) && !outLookApiModel.getApiParamDataList().isEmpty()) {
                outLookApiModel.getApiParamDataList().forEach(apihpDataModel -> {
                    if (apihpDataModel.getRequired()) {
                        if (apihpDataModel.getDynamicValue() && !params.isEmpty()) {
                            if (params.containsKey(apihpDataModel.getKey())) {
                                uriBuilder.addParameter(apihpDataModel.getKey(), params.get(apihpDataModel.getKey())[0]);
                            }
                        } else if (isNotNull(apihpDataModel.getValue())) {
                            uriBuilder.addParameter(apihpDataModel.getKey(), apihpDataModel.getValue());
                        } else {
                            throw internalServerError("Please provide value for " + apihpDataModel.getKey() + " Parameter");
                        }
                    }
                });
            }
            httpGet.setURI(uriBuilder.build());
            LOGGER.info("URI {} ", httpGet.getURI());
            String token = getOauth2Token(outLookApiModel.getOauth2Auth());
            httpGet.addHeader(outLookApiModel.getOauth2Auth().getTokenHeader(),
                    outLookApiModel.getOauth2Auth().getTokenPrefix() + token);
            CloseableHttpResponse response = client.execute(httpGet);
            String jsonString = IOUtils.toString(response.getEntity().getContent(), UTF_8);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 300) {
                B2bErrorResponseDeserializeModel errorModel = objectMapper.readValue(jsonString, B2bErrorResponseDeserializeModel.class);
                throw internalServerError(isNotNull(errorModel.getErrorDescription()) ? errorModel.getErrorDescription() : jsonString);
            }
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

    private String getSessionToken(APIProxyEndpointModel apiProxyEndpointModel) {
        try (CloseableHttpClient client = cmCloseableHttpClient.getCloseableHttpClient(apiProxyEndpointModel.getServerApiAuthData().getTokenAuth().getTokenApiUrl().trim())) {
            HttpPost httpPost = new HttpPost(apiProxyEndpointModel.getServerApiAuthData().getTokenAuth().getTokenApiUrl().trim());
            JSONObject obj = new JSONObject();
            obj.put("userName", apiProxyEndpointModel.getServerApiAuthData().getBasicAuth().getUsername());
            obj.put("password", apiProxyEndpointModel.getServerApiAuthData().getBasicAuth().getPassword());
            LOGGER.debug("JSON Request {}", obj);
            httpPost.setEntity(new StringEntity(obj.toString()));
            httpPost.setHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            httpPost.addHeader(new BasicScheme().authenticate(new UsernamePasswordCredentials(apiProxyEndpointModel
                            .getServerApiAuthData().getBasicAuth().getUsername(), apiProxyEndpointModel.getServerApiAuthData().getBasicAuth().getPassword()),
                    httpPost, null));
            CloseableHttpResponse response = client.execute(httpPost);
            String finalResponse = IOUtils.toString(response.getEntity().getContent(), UTF_8);
            return new JSONObject(finalResponse).getString(apiProxyEndpointModel.getServerApiAuthData().getTokenAuth().getTokenKey());
        } catch (Exception ex) {
            LOGGER.error("{} at Session Token : {}", EXCEPTION_OCCURRED, ex);
            throw internalServerError(ex.getMessage());
        }
    }

    private String getOauth2Token(Oauth2AuthModel oauth2AuthModel) {
        try (CloseableHttpClient client = cmCloseableHttpClient.getCloseableHttpClient(oauth2AuthModel.getTokenApiUrl())) {
            HttpPost httpPost = new HttpPost(oauth2AuthModel.getTokenApiUrl().trim());
            List<NameValuePair> urlParameters = new ArrayList<>();
            urlParameters.add(new BasicNameValuePair("grant_type", oauth2AuthModel.getGrantType()));
            urlParameters.add(new BasicNameValuePair("client_id", oauth2AuthModel.getClientID()));
            urlParameters.add(new BasicNameValuePair("client_secret", oauth2AuthModel.getClientSecret()));
            urlParameters.add(new BasicNameValuePair("scope", oauth2AuthModel.getScope()));
            urlParameters.add(new BasicNameValuePair("resource", oauth2AuthModel.getResource()));
            urlParameters.add(new BasicNameValuePair("username", oauth2AuthModel.getUsername()));
            urlParameters.add(new BasicNameValuePair("password", oauth2AuthModel.getPassword()));
            httpPost.setEntity(new UrlEncodedFormEntity(urlParameters));
            LOGGER.debug("Request {}", urlParameters);
            httpPost.setHeader(CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
            CloseableHttpResponse response = client.execute(httpPost);
            String finalResponse = IOUtils.toString(response.getEntity().getContent(), UTF_8);
            LOGGER.debug("Final Response from API {}", finalResponse);
            return new JSONObject(finalResponse).getString(oauth2AuthModel.getTokenKey());
        } catch (Exception ex) {
            LOGGER.error("{} at OAuth2 Token : {}", EXCEPTION_OCCURRED, ex);
            throw internalServerError(ex.getMessage());
        }
    }

    public String sendEmailToOutlook(OutLookEmailModel outLookEmailModel) {
        try (CloseableHttpClient client = cmCloseableHttpClient.getCloseableHttpClient(outLookEmailModel.getOutLookApiModel().getActualApi())) {
            HttpPost httpPost = new HttpPost(outLookEmailModel.getOutLookApiModel().getActualApi());
            String obj = objectMapper.writeValueAsString(mapperToMailJson.apply(outLookEmailModel));
            httpPost.setEntity(new StringEntity(obj));
            LOGGER.debug("JSON Request {}", obj);
            httpPost.setHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            httpPost.setHeader(ACCEPT, MediaType.APPLICATION_JSON_VALUE);
            String token = getOauth2Token(outLookEmailModel.getOutLookApiModel().getOauth2Auth());
            httpPost.addHeader(outLookEmailModel.getOutLookApiModel().getOauth2Auth().getTokenHeader(),
                    outLookEmailModel.getOutLookApiModel().getOauth2Auth().getTokenPrefix() + token);
            CloseableHttpResponse response = client.execute(httpPost);
            return IOUtils.toString(response.getEntity().getContent(), UTF_8);
        } catch (Exception ex) {
            LOGGER.error("{} at Session Token : {}", EXCEPTION_OCCURRED, ex);
            throw internalServerError(ex.getMessage());
        }
    }

    Function<OutLookEmailModel, SendMailToOutLookModel> mapperToMailJson = outLookEmailModel -> {
        List<ToRecipients> toRecipients = new ArrayList<>();
        outLookEmailModel.getToMails().forEach(communityManagerNameModel -> toRecipients.add(
                new ToRecipients().setEmailAddress(new EmailAddress().setAddress(communityManagerNameModel.getName()))
        ));
        return new SendMailToOutLookModel()
                .setMessage(new Message().setBody(new BodyModel()
                                .setContent(outLookEmailModel.getContent())
                                .setContentType(outLookEmailModel.getContentType()))
                        .setSubject(outLookEmailModel.getSubject())
                        .setToRecipients(toRecipients));
    };

}
