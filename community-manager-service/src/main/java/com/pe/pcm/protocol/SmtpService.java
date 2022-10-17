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

import com.pe.pcm.protocol.smtp.SmtpRepository;
import com.pe.pcm.protocol.smtp.entity.SmtpEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;
import static com.pe.pcm.protocol.function.ProtocolFunctions.mapperToSmtpEntity;

/**
 * @author Shameer.
 */
@Service
public class SmtpService {

    private SmtpRepository smtpRepository;


    @Autowired
    public SmtpService(SmtpRepository smtpRepository) {
        this.smtpRepository = smtpRepository;
    }

    /**
     * Saves a given SMTP entity. and
     * Returns SMTP Entity
     */
    public SmtpEntity save(SmtpEntity smtpEntity) {
        return smtpRepository.save(smtpEntity);
    }

    public SmtpEntity get(String pkId) {
        return smtpRepository.findBySubscriberId(pkId).orElseThrow(() -> notFound("Protocol"));
    }

    public void delete(String pkId) {
        smtpRepository.findBySubscriberId(pkId).ifPresent(smtpEntity -> smtpRepository.delete(smtpEntity));
    }

    @Transactional
    public SmtpEntity saveProtocol(SmtpModel smtpModel, String parentPrimaryKey, String childPrimaryKey, String subscriberType) {
        SmtpEntity smtpEntity = mapperToSmtpEntity.apply(smtpModel);
        smtpEntity.setPkId(childPrimaryKey)
                .setSubscriberType(subscriberType)
                .setSubscriberId(parentPrimaryKey);
        return save(smtpEntity);
    }

}
