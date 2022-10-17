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
import com.pe.pcm.protocol.remotecd.RemoteConnectDirectRepository;
import com.pe.pcm.protocol.remotecd.entity.RemoteConnectDirectEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;
import static com.pe.pcm.protocol.function.ProtocolFunctions.mapperToRemoteCdEntity;

/**
 * @author Chenchu Kiran.
 */
@Service
public class RemoteConnectDirectService {

    private final RemoteConnectDirectRepository remoteConnectDirectRepository;
    private final CertificateUserUtilityService certificateUserUtilityService;

    @Autowired
    public RemoteConnectDirectService(RemoteConnectDirectRepository remoteConnectDirectRepository, @Lazy CertificateUserUtilityService certificateUserUtilityService) {
        this.remoteConnectDirectRepository = remoteConnectDirectRepository;
        this.certificateUserUtilityService = certificateUserUtilityService;
    }

    public RemoteConnectDirectEntity save(RemoteConnectDirectEntity remoteConnectDirectEntity) {
        return remoteConnectDirectRepository.save(remoteConnectDirectEntity);
    }

    public RemoteConnectDirectEntity saveProtocol(RemoteCdModel remoteCdModel, String parentPrimaryKey, String childPrimaryKey, String subscriberType) {
        RemoteConnectDirectEntity remoteConnectDirectEntity = mapperToRemoteCdEntity.apply(remoteCdModel);
        remoteConnectDirectEntity.setPkId(childPrimaryKey)
                .setSubscriberType(subscriberType)
                .setSubscriberId(parentPrimaryKey)
                .setCaCertificateId(certificateUserUtilityService.findByNameInOrderByName(remoteCdModel.getCaCertName()).stream().map(CaCertGetModel::getCaCertName).collect(Collectors.joining(",")));
        return save(remoteConnectDirectEntity);
    }

    public RemoteConnectDirectEntity get(String pkId) {
        return remoteConnectDirectRepository.findBySubscriberId(pkId).orElseThrow(() -> notFound("Protocol"));
    }

    public void delete(String pkId) {
        remoteConnectDirectRepository.findBySubscriberId(pkId).ifPresent(remoteConnectDirectRepository::delete);
    }

    public List<RemoteConnectDirectEntity> findAllByCaCertificateNotNull() {
        return remoteConnectDirectRepository.findAllByCaCertificateNameNotNull().orElse(new ArrayList<>());
    }

    public List<RemoteConnectDirectEntity> findAllBySubscriberId(List<String> subIds) {
        return remoteConnectDirectRepository.findAllBySubscriberIdIn(subIds).orElse(new ArrayList<>());
    }
}
