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

import com.pe.pcm.protocol.mq.MqRepository;
import com.pe.pcm.protocol.mq.entity.MqEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;
import static com.pe.pcm.protocol.function.ProtocolFunctions.mapperToMqEntity;

/**
 * @author Chenchu Kiran.
 */
@Service
public class MqService {

    private final MqRepository mqRepository;

    @Autowired
    public MqService(MqRepository mqRepository) {
        this.mqRepository = mqRepository;
    }

    public MqEntity save(MqEntity mqEntity) {
        return mqRepository.save(mqEntity);
    }

    public MqEntity saveProtocol(MqModel mqModel, String parentPrimaryKey, String childPrimaryKey,
                                 String subscriberType) {
        MqEntity mqEntity = mapperToMqEntity.apply(mqModel);
        mqEntity.setPkId(childPrimaryKey)
                .setSubscriberId(parentPrimaryKey)
                .setSubscriberType(subscriberType);
        return mqRepository.save(mqEntity);
    }

    public MqEntity get(String pkId) {
        return mqRepository.findBySubscriberId(pkId).orElseThrow(() -> notFound("Protocol"));
    }

    public void delete(String pkId) {
        mqRepository.findBySubscriberId(pkId).ifPresent(mqRepository::delete);
    }
}
