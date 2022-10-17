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

package com.pe.pcm.protocol;

import com.pe.pcm.certificate.CaCertInfoService;
import com.pe.pcm.protocol.http.HttpRepository;
import com.pe.pcm.protocol.http.entity.HttpEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;
import static com.pe.pcm.protocol.function.ProtocolFunctions.mapperToHttpEntity;

/**
 * @author Chenchu Kiran.
 */
@Service
public class HttpService {

    private final HttpRepository httpRepository;
    private final CaCertInfoService caCertInfoService;

    @Autowired
    public HttpService(HttpRepository httpRepository, CaCertInfoService caCertInfoService) {
        this.httpRepository = httpRepository;
        this.caCertInfoService = caCertInfoService;
    }

    public HttpEntity save(HttpEntity httpEntity) {
        return httpRepository.save(httpEntity);
    }

    public HttpEntity get(String pkId) {
        return httpRepository.findBySubscriberId(pkId).orElseThrow(() -> notFound("Protocol"));
    }

    public void delete(String pkId) {
        httpRepository.findBySubscriberId(pkId).ifPresent(httpRepository::delete);
    }

    public HttpEntity saveProtocol(HttpModel httpModel, String parentPrimaryKey, String childPrimaryKey, String subscriberType) {
        HttpEntity httpEntity = mapperToHttpEntity.apply(httpModel);
        httpEntity.setPkId(childPrimaryKey)
                .setSubscriberType(subscriberType)
                .setSubscriberId(parentPrimaryKey)
                .setCertificateId(caCertInfoService.findByNameNotThrow(httpModel.getCertificate()).getObjectId());
        return save(httpEntity);
    }

    public void delete(HttpEntity httpEntity) {
        httpRepository.delete(httpEntity);
    }

    public List<HttpEntity> findAllByProtocolType(String protocolType) {
        return httpRepository.findAllByProtocolType(protocolType).orElse(new ArrayList<>());
    }

    public List<HttpEntity> findAllBySubscriberId(List<String> subIds) {
        return httpRepository.findAllBySubscriberIdIn(subIds).orElse(new ArrayList<>());
    }
}
