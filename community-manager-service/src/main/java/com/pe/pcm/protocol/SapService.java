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

import com.pe.pcm.protocol.sap.SapRepository;
import com.pe.pcm.protocol.sap.entity.SapEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;
import static com.pe.pcm.protocol.function.ProtocolFunctions.mapperToSapEntity;

/**
 * @author Chenchu Kiran
 */
@Service
public class SapService {

    private SapRepository sapRepository;

    @Autowired
    public SapService(SapRepository sapRepository) {
        this.sapRepository = sapRepository;
    }

    public SapEntity saveProtocol(SapModel sapModel, String parentPrimaryKey, String childPrimaryKey, String subscriberType) {
        SapEntity sapEntity = mapperToSapEntity.apply(sapModel)
                .setPkId(childPrimaryKey)
                .setSubscriberType(subscriberType)
                .setSubscriberId(parentPrimaryKey);

        return save(sapEntity);
    }

    public SapEntity save(SapEntity sapEntity) {
        return sapRepository.save(sapEntity);
    }

    public SapEntity get(String pkId) {
    return sapRepository.findBySubscriberId(pkId).orElseThrow(() -> notFound("Protocol"));
    }

    public void delete(String pkId) {
    sapRepository.findBySubscriberId(pkId).ifPresent(sapEntity -> sapRepository.delete(sapEntity));
    }
}
