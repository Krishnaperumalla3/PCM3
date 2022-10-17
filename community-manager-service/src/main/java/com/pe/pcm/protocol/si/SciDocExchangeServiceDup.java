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

package com.pe.pcm.protocol.si;

import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.protocol.as2.si.SciDocExchangeRepository;
import com.pe.pcm.protocol.as2.si.entity.SciDocExchangeEntity;
import com.pe.pcm.sterling.dto.DocExchangeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

import static com.pe.pcm.enums.Protocol.AS2;
import static com.pe.pcm.utils.CommonFunctions.isNotNull;
import static com.pe.pcm.utils.PCMConstants.DOC_EXCHANGE;

/**
 * @author Chenchu Kiran Reddy.
 */
@Service
public class SciDocExchangeServiceDup {
    private static final Logger LOGGER = LoggerFactory.getLogger(SciDocExchangeServiceDup.class);

    private final SciDocExchangeRepository sciDocExchangeRepository;

    @Autowired
    public SciDocExchangeServiceDup(SciDocExchangeRepository sciDocExchangeRepository) {
        this.sciDocExchangeRepository = sciDocExchangeRepository;
    }

    private final Function<DocExchangeDTO, SciDocExchangeEntity> serialize = docExchangeDTO -> {
        SciDocExchangeEntity sciDocExchangeEntity = new SciDocExchangeEntity();
        String objectName;
        if (docExchangeDTO.isNormalProfile()) {

            if (docExchangeDTO.getProtocol().getProtocol().equals(AS2.getProtocol())) {
                objectName = DOC_EXCHANGE + docExchangeDTO.getProfileId() + "_" + docExchangeDTO.getProfileName();
            } else {
                objectName = DOC_EXCHANGE + docExchangeDTO.getProfileName();
            }
        } else {
            objectName = docExchangeDTO.getProfileName() + "_" + docExchangeDTO.getProfileType();
        }
        sciDocExchangeEntity.setObjectName(objectName);
        sciDocExchangeEntity.setObjectId(docExchangeDTO.getObjectId());
        sciDocExchangeEntity.setEntityId(docExchangeDTO.getEntityId());
        sciDocExchangeEntity.setDocExchangeKey(docExchangeDTO.getDocExchangeKey());

        sciDocExchangeEntity.setNoOfRetries(isNotNull(docExchangeDTO.getNoOfRetries()) ? docExchangeDTO.getNoOfRetries() : "3");
        sciDocExchangeEntity.setRetryInterval(isNotNull(docExchangeDTO.getRetryInterval()) ? docExchangeDTO.getRetryInterval() : "30");
        sciDocExchangeEntity.setEnvEncryptAlg(docExchangeDTO.getEnvEncryptAlg());
        sciDocExchangeEntity.setMsgSigningAlg(docExchangeDTO.getMsgSigningAlg());

        return sciDocExchangeEntity;
    };

    public void save(DocExchangeDTO docExchangeDTO) {
        LOGGER.info("Create/Update SciDocExchangeEntity");
        sciDocExchangeRepository.save(serialize.apply(docExchangeDTO));
    }

    public Optional<SciDocExchangeEntity> findById(String objectId) {
        return sciDocExchangeRepository.findById(objectId);
    }

    public DocExchangeDTO getDocExchangeDTO(String objectId) {
        return deSerialize.apply(findById(objectId).orElseThrow(() -> GlobalExceptionHandler.internalServerError("SciDocExchangeEntity entity notfound")));
    }

    public void delete(String objectId) {
        LOGGER.info("Delete SciDocExchangeEntity.");
        sciDocExchangeRepository.deleteById(objectId);
    }

    private static final Function<SciDocExchangeEntity, DocExchangeDTO> deSerialize = sciDocExchange ->
            new DocExchangeDTO().setRetryInterval(sciDocExchange.getRetryInterval())
                    .setNoOfRetries(sciDocExchange.getNoOfRetries())
                    .setObjectId(sciDocExchange.getObjectId())
                    .setEnvEncryptAlg(sciDocExchange.getEnvEncryptAlg())
                    .setMsgSigningAlg(sciDocExchange.getMsgSigningAlg());

}
