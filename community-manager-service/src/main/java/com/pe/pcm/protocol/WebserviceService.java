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
import com.pe.pcm.protocol.webservice.WebserviceRepository;
import com.pe.pcm.protocol.webservice.entity.WebserviceEntity;
import com.pe.pcm.utils.CommonFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;
import static com.pe.pcm.protocol.function.ProtocolFunctions.mapperToWebserviceEntity;

/**
 * @author Chenchu Kiran.
 */
@Service
public class WebserviceService {

    private WebserviceRepository webserviceRepository;
    private CaCertInfoService caCertInfoService;

    @Autowired
    public WebserviceService(WebserviceRepository webserviceRepository, CaCertInfoService caCertInfoService) {
        this.webserviceRepository = webserviceRepository;
        this.caCertInfoService = caCertInfoService;
    }

    public WebserviceEntity save(WebserviceEntity webserviceEntity) {
        return webserviceRepository.save(webserviceEntity);
    }

    public WebserviceEntity saveProtocol(WebserviceModel webserviceModel, String parentPrimaryKey, String childPrimaryKey,
                                         String subscriberType) {
        WebserviceEntity webserviceEntity = mapperToWebserviceEntity.apply(webserviceModel);
        webserviceEntity.setPkId(childPrimaryKey)
                .setSubscriberType(subscriberType)
                .setSubscriberId(parentPrimaryKey)
                .setCaCertName(CommonFunctions.isNotNull(webserviceModel.getCertificateId()) ? caCertInfoService.findByIdNotThrow(webserviceModel.getCertificateId()).getName() : "");
        return save(webserviceEntity);
    }

    public WebserviceEntity get(String pkId) {
        return webserviceRepository.findBySubscriberId(pkId).orElseThrow(() -> notFound("Protocol"));
    }

    public void delete(String pkId) {
        webserviceRepository.findBySubscriberId(pkId).ifPresent(webserviceEntity -> webserviceRepository.delete(webserviceEntity));
    }
}
