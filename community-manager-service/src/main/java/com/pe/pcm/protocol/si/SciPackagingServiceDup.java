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

import com.pe.pcm.enums.Protocol;
import com.pe.pcm.protocol.as2.si.SciPackagingRepository;
import com.pe.pcm.protocol.as2.si.entity.SciPackagingEntity;
import com.pe.pcm.sterling.dto.PackagingDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

import static com.pe.pcm.utils.PCMConstants.PACKAGING;

/**
 * @author Chenchu Kiran Reddy.
 */
@Service
public class SciPackagingServiceDup {

    private static final Logger LOGGER = LoggerFactory.getLogger(SciPackagingServiceDup.class);

    private final SciPackagingRepository sciPackagingRepository;

    @Autowired
    public SciPackagingServiceDup(SciPackagingRepository sciPackagingRepository) {
        this.sciPackagingRepository = sciPackagingRepository;
    }

    private final Function<PackagingDTO, SciPackagingEntity> serialize = packagingDTO -> {
        SciPackagingEntity sciPackagingEntity = new SciPackagingEntity();
        sciPackagingEntity.setEntityId(packagingDTO.getEntityId());
        sciPackagingEntity.setPayloadType("13");
        String objectName;
        if (packagingDTO.isNormalProfile()) {
            if (packagingDTO.getProtocol().getProtocol().equals(Protocol.AS2.getProtocol())) {
                objectName = PACKAGING + packagingDTO.getProfileId() + "_" + packagingDTO.getProfileName();
                sciPackagingEntity.setEntityId("");
                if (packagingDTO.getHubInfo()) {
                    sciPackagingEntity.setPayloadType("0");
                } else {
                    sciPackagingEntity.setPayloadType(packagingDTO.getPayloadType());
                    sciPackagingEntity.setCompressData(packagingDTO.getCompressData());
                    sciPackagingEntity.setDefaultMimeType(packagingDTO.getMimeType());
                    sciPackagingEntity.setDefMimeSubtype(packagingDTO.getMimeSubType());
                }
            } else {
                objectName = packagingDTO.getProfileName().startsWith(PACKAGING) ? packagingDTO.getProfileName() : PACKAGING + packagingDTO.getProfileName();
            }
        } else {
            objectName = packagingDTO.getProfileName() + "_" + packagingDTO.getProfileType();
        }
        sciPackagingEntity.setObjectName(objectName);
        sciPackagingEntity.setObjectId(packagingDTO.getObjectId());
        sciPackagingEntity.setPackagingKey(packagingDTO.getPackagingKey());

        return sciPackagingEntity;
    };

    public void save(PackagingDTO packagingDTO) {
        LOGGER.info("Create/Update SciPackagingEntity.");
        sciPackagingRepository.save(serialize.apply(packagingDTO));
    }

    public Optional<SciPackagingEntity> get(String objectId) {
        return sciPackagingRepository.findFirstByObjectId(objectId);
    }

    public void delete(String objectId) {
        LOGGER.info("Delete SciPackagingEntity.");
        sciPackagingRepository.deleteById(objectId);
    }
}
