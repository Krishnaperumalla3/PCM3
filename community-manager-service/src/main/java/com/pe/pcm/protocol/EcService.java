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

import com.pe.pcm.protocol.ec.EcRepository;
import com.pe.pcm.protocol.ec.entity.EcEntity;
import com.pe.pcm.protocol.function.ProtocolFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;

/**
 * @author Chenchu Kiran.
 */
@Service
public class EcService {

    private EcRepository ecRepository;

    @Autowired
    public EcService(EcRepository ecRepository) {
        this.ecRepository = ecRepository;
    }

    public EcEntity saveProtocol(EcModel ecModel, String parentPrimaryKey, String childPrimaryKey, String subscriberType) {
        EcEntity ecEntity = ProtocolFunctions.mapperToEcEntity.apply(ecModel)
                .setPkId(childPrimaryKey)
                .setSubscriberType(subscriberType)
                .setSubscriberId(parentPrimaryKey);
        return save(ecEntity);
    }

    public EcEntity save(EcEntity ecEntity) {
        return ecRepository.save(ecEntity);
    }

    public EcEntity get(String pkId) {
        return ecRepository.findBySubscriberId(pkId).orElseThrow(() -> notFound("Protocol"));
    }

    public void delete(String pkId) {
        ecRepository.findBySubscriberId(pkId).ifPresent(ecEntity -> ecRepository.delete(ecEntity));
    }
}
