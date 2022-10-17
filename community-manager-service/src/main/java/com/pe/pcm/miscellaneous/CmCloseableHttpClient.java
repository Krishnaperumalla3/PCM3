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

package com.pe.pcm.miscellaneous;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.stereotype.Service;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;

/**
 * @author Kiran Reddy.
 */
@Service
public class CmCloseableHttpClient {

    public CloseableHttpClient getCloseableHttpClient(String baseUrl) throws NoSuchAlgorithmException, KeyManagementException {
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
}
