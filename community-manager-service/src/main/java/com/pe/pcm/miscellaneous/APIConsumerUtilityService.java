package com.pe.pcm.miscellaneous;

import com.pe.pcm.exception.CommunityManagerServiceException;
import org.apache.commons.io.IOUtils;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.CloseableHttpResponse;
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
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;
import static com.pe.pcm.utils.PCMConstants.API_NOT_WORKING;
import static com.pe.pcm.utils.PCMConstants.SERVER_BUSY;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author Kiran Reddy.
 */
@Service
public class APIConsumerUtilityService {

    private static final Logger LOGGER = LoggerFactory.getLogger(APIConsumerUtilityService.class);

    public String invokePostService(String url, String username, String password, String payload) {

        try (CloseableHttpClient client = getCloseableHttpClient(url)) {
            HttpPost httpPost = new HttpPost(url);
            LOGGER.debug("Input PayLoad (POST) {} ", payload);
            httpPost.setEntity(new StringEntity(payload));

            UsernamePasswordCredentials creds = new UsernamePasswordCredentials(username, password);
            httpPost.addHeader(new BasicScheme().authenticate(creds, httpPost, null));
            CloseableHttpResponse response = client.execute(httpPost);

            LOGGER.info("status Code : {} : ", response.getStatusLine().getStatusCode());

            if (response.getStatusLine().getStatusCode() >= 300) {
                String jsonString = IOUtils.toString(response.getEntity().getContent(), UTF_8);
                LOGGER.info("status message : {} : ", jsonString);
                throw internalServerError(jsonString);
            }
            return IOUtils.toString(response.getEntity().getContent(), UTF_8);
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

    public String invokeGetSIService(String url, String username, String password) {

        try (CloseableHttpClient client = getCloseableHttpClient(url)) {
            HttpGet httpGet = new HttpGet(url);

            UsernamePasswordCredentials creds = new UsernamePasswordCredentials(username, password);
            httpGet.addHeader(new BasicScheme().authenticate(creds, httpGet, null));
            CloseableHttpResponse response = client.execute(httpGet);

            String jsonString = IOUtils.toString(response.getEntity().getContent(), UTF_8);
            LOGGER.debug("B2B API Response [GET] {}", jsonString);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 300) {
                LOGGER.info("status message : {} : ", jsonString);
                throw internalServerError(jsonString);
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

    private CloseableHttpClient getCloseableHttpClient(String url) throws NoSuchAlgorithmException, KeyManagementException {
        if (url.startsWith("https")) {
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
}
