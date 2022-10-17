/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc.
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

package com.pe.pcm.protocol.si.as2;

import com.pe.pcm.protocol.as2.si.AS2EmailInfoRepository;
import com.pe.pcm.protocol.as2.si.entity.As2EmailInfoEntity;
import com.pe.pcm.protocol.as2.si.entity.identity.As2EmailInfoIdentity;
import com.pe.pcm.sterling.dto.As2EmailInfoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * @author Chenchu Kiran.
 */
@Service
public class AS2EmailInfoServiceDup {
    private static final Logger LOGGER = LoggerFactory.getLogger(AS2EmailInfoServiceDup.class);

    private final AS2EmailInfoRepository as2EmailInfoRepository;

    @Autowired
    public AS2EmailInfoServiceDup(AS2EmailInfoRepository as2EmailInfoRepository) {
        this.as2EmailInfoRepository = as2EmailInfoRepository;
    }

    private final Function<As2EmailInfoDTO, As2EmailInfoEntity> serialize = as2EmailInfoDTO ->
            new As2EmailInfoEntity().setAs2EmailInfoIdentity(
                    new As2EmailInfoIdentity()
                            .setEntityId(as2EmailInfoDTO.getEntityId())
                            .setProfileId(as2EmailInfoDTO.getProfileId()))
                    .setEmailAddress(as2EmailInfoDTO.getEmailAddress());

    public void save(As2EmailInfoDTO as2EmailInfoDTO) {
        LOGGER.info("Create/Update As2EmailInfoEntity.");
        as2EmailInfoRepository.save(serialize.apply(as2EmailInfoDTO));
    }

    public As2EmailInfoDTO get(String profileId, String entityId) {
        return deSerialize.apply(as2EmailInfoRepository.findById(new As2EmailInfoIdentity().setEntityId(entityId).setProfileId(profileId)).orElse(new As2EmailInfoEntity()));
    }

    public void delete(String profileId, String entityId) {
        LOGGER.info("Delete As2EmailInfoEntity.");
        as2EmailInfoRepository.findById(new As2EmailInfoIdentity().setEntityId(entityId).setProfileId(profileId)).ifPresent(as2EmailInfoRepository::delete);
    }

    private final Function<As2EmailInfoEntity, As2EmailInfoDTO> deSerialize = as2EmailInfoEntity ->
            new As2EmailInfoDTO().setEmailAddress(as2EmailInfoEntity.getEmailAddress());

}
