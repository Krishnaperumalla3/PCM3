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

import com.pe.pcm.b2b.other.CaCertGetModel;
import com.pe.pcm.miscellaneous.CertificateUserUtilityService;
import com.pe.pcm.protocol.connectdirect.ConnectDirectRepository;
import com.pe.pcm.protocol.connectdirect.entity.ConnectDirectEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;
import static com.pe.pcm.protocol.function.ProtocolFunctions.mapperToCdEntity;

/**
 * @author Kiran Reddy.
 */
@Service
public class ConnectDirectService {

    private final ConnectDirectRepository connectDirectRepository;
    private final CertificateUserUtilityService certificateUserUtilityService;

    @Autowired
    public ConnectDirectService(ConnectDirectRepository connectDirectRepository, CertificateUserUtilityService certificateUserUtilityService) {
        this.connectDirectRepository = connectDirectRepository;
        this.certificateUserUtilityService = certificateUserUtilityService;
    }

    public ConnectDirectEntity save(ConnectDirectEntity connectDirectEntity) {
        return connectDirectRepository.save(connectDirectEntity);
    }

    public ConnectDirectEntity get(String parentPkId) {
        return connectDirectRepository.findBySubscriberId(parentPkId).orElseThrow(() -> notFound("Protocol"));
    }

    public void delete(String parentPkId) {
        connectDirectRepository.findBySubscriberId(parentPkId).ifPresent(connectDirectRepository::delete);
    }

    public ConnectDirectEntity saveProtocol(ConnectDirectModel connectDirectModel, String parentPrimaryKey, String childPrimaryKey, String subscriberType) {
        ConnectDirectEntity connectDirectEntity = mapperToCdEntity.apply(connectDirectModel);
        connectDirectEntity.setPkId(childPrimaryKey)
                .setSubscriberType(subscriberType)
                .setSubscriberId(parentPrimaryKey)
                .setCaCertificateId(certificateUserUtilityService.findByNameInOrderByName(connectDirectModel.getCaCertificate()).stream().map(CaCertGetModel::getCaCertName).collect(Collectors.joining(",")));
        return save(connectDirectEntity);
    }
}
