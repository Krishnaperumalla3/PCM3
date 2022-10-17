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

package com.pe.pcm.pem.codelist;

import com.pe.pcm.b2b.B2BApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Shameer.v, Kiran Reddy
 */
@Service
public class PemProxyCodeListService {

    private final B2BApiService b2BApiService;
    private static final Logger LOGGER = LoggerFactory.getLogger(PemProxyCodeListService.class);

    @Autowired
    public PemProxyCodeListService(B2BApiService b2BApiService) {
        this.b2BApiService = b2BApiService;
    }

    public void create(PemProxyCodeListModel pemProxyCodeListModel) {
        LOGGER.info("In ProxyCodeListService create() Method.");
        b2BApiService.createProxyCodeList(pemProxyCodeListModel);
    }

    public void update(PemProxyCodeListModel pemProxyCodeListModel) {
        LOGGER.info("In ProxyCodeListService update() Method.");
        b2BApiService.updateProxyCodeList(pemProxyCodeListModel);
    }

    public void delete(String codeListName) {
        LOGGER.info("In ProxyCodeListService delete() Method.");
        b2BApiService.deleteProxyCodeList(codeListName);
    }
}
