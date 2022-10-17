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

import com.pe.pcm.protocol.as2.si.SciProfileRepository;
import com.pe.pcm.protocol.as2.si.entity.SciProfileEntity;
import com.pe.pcm.sterling.dto.ProfileDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static com.pe.pcm.enums.Protocol.AS2;
import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;
import static com.pe.pcm.utils.PCMConstants.PROFILE;
import static com.pe.pcm.utils.PCMConstants.SPACE;

/**
 * @author Chenchu Kiran.
 */
@Service
public class SciProfileServiceDup {

    private static final Logger LOGGER = LoggerFactory.getLogger(SciProfileServiceDup.class);

    private final SciProfileRepository sciProfileRepository;

    @Autowired
    public SciProfileServiceDup(SciProfileRepository sciProfileRepository) {
        this.sciProfileRepository = sciProfileRepository;
    }

    private final Function<ProfileDTO, SciProfileEntity> serialize = profileDTO -> {
        SciProfileEntity sciProfileEntity = new SciProfileEntity();
        sciProfileEntity.setProfileType("EDI");
        String objectName;
        if (profileDTO.isNormalProfile()) {

            if (profileDTO.getProtocol().getProtocol().equals(AS2.getProtocol())) {
                objectName = profileDTO.getProfileName();
                sciProfileEntity.setProfileType("AS2");
                sciProfileEntity.setSvcProviderId(SPACE);
            } else {
                objectName = PROFILE + profileDTO.getProfileName();
            }
        } else {
            objectName = profileDTO.getProfileName() + "_" + profileDTO.getProfileType();
        }
        sciProfileEntity.setObjectId(profileDTO.getObjectId());
        sciProfileEntity.setObjectName(objectName);
        sciProfileEntity.setEntityId(profileDTO.getEntityId());
        sciProfileEntity.setDelivChannelId(profileDTO.getDelChannelId());
        sciProfileEntity.setPackagingId(profileDTO.getPackagingId());
        sciProfileEntity.setProfileKey(profileDTO.getProfileKey());
        return sciProfileEntity;
    };

    public void save(ProfileDTO profileDTO) {
        LOGGER.info("Create/Update SciProfileEntity");
        sciProfileRepository.save(serialize.apply(profileDTO));
    }


    public Optional<SciProfileEntity> findByEntityId(String entityId) {
        return sciProfileRepository.findFirstByEntityId(entityId);
    }

    public Optional<SciProfileEntity> findFirstByObjectNameAndEntityId(String objectName, String entityId) {
        return sciProfileRepository.findFirstByObjectNameAndEntityId(objectName, entityId);
    }

    public List<SciProfileEntity> findAllByEntityId(String entityId) {
        return sciProfileRepository.findAllByEntityId(entityId).orElseThrow(() -> internalServerError("Community Profile, Sci Profile Entity not found"));
    }

    public Optional<SciProfileEntity> findFirstByObjectName(String objectName) {
        return sciProfileRepository.findFirstByObjectName(objectName);
    }

    public void deleteByEntityId(String entityId) {
        LOGGER.info("Delete SciProfileEntity.");
        sciProfileRepository.deleteAllByEntityId(entityId);
    }

    public Optional<SciProfileEntity> findById(String objectId) {
        return sciProfileRepository.findById(objectId);
    }

}
