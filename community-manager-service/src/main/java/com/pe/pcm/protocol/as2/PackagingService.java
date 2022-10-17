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

package com.pe.pcm.protocol.as2;

import com.pe.pcm.protocol.As2Model;
import com.pe.pcm.protocol.as2.si.entity.SciPackagingEntity;
import com.pe.pcm.protocol.as2.si.SciPackagingRepository;
import com.pe.pcm.utils.KeyGeneratorUtil;
import com.pe.pcm.utils.PCMConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.pe.pcm.utils.PCMConstants.PACKAGING;

@Service
public class PackagingService {

    private SciPackagingRepository sciPackagingRepository;

    @Autowired
    public PackagingService(SciPackagingRepository sciPackagingRepository) {
        this.sciPackagingRepository = sciPackagingRepository;
    }

    private BiFunction<String, As2Model, SciPackagingEntity> serialize = (transportObjectId, as2TradingPartner) -> {
        SciPackagingEntity sciPackagingEntity = new SciPackagingEntity();
        String packageObjectId = KeyGeneratorUtil.getPrimaryKey.apply(PCMConstants.SCI_PC_OBJ_PRE_APPEND,
                PCMConstants.SCI_RANDOM_COUNT);

        sciPackagingEntity.setObjectId(transportObjectId + ":-3afd");
        sciPackagingEntity.setEntityId(transportObjectId + ":-3b0a");
        sciPackagingEntity.setPackagingKey(packageObjectId + "-as2");
        sciPackagingEntity.setObjectName(PACKAGING + as2TradingPartner.getProfileId() + "_" + as2TradingPartner.getProfileName());
        if (!as2TradingPartner.getHubInfo()) {
            sciPackagingEntity.setObjectName(PACKAGING + as2TradingPartner.getProfileId() + "_" + as2TradingPartner.getProfileName());
            //sciPackagingEntity.setObjectName(PACKAGING + as2TradingPartner.getProfileId());
            sciPackagingEntity.setPayloadType(as2TradingPartner.getPayloadType());
            sciPackagingEntity.setCompressData(as2TradingPartner.getCompressData());
            sciPackagingEntity.setDefaultMimeType(as2TradingPartner.getMimeType());
            sciPackagingEntity.setDefMimeSubtype(as2TradingPartner.getMimeSubType());

        }
        return sciPackagingEntity;
    };

    private Function<As2Model, SciPackagingEntity> serializeToUpdate = as2TradingPartner -> {
        SciPackagingEntity sciPackagingEntity = new SciPackagingEntity();
        sciPackagingEntity.setObjectName(PACKAGING + as2TradingPartner.getProfileId() + "_" + as2TradingPartner.getProfileName());
        if (!as2TradingPartner.getHubInfo()) {
            sciPackagingEntity.setObjectName(PACKAGING + as2TradingPartner.getProfileId() + "_" + as2TradingPartner.getProfileName());
            sciPackagingEntity.setObjectName(PACKAGING + as2TradingPartner.getProfileId());
            sciPackagingEntity.setPayloadType(as2TradingPartner.getPayloadType());
            sciPackagingEntity.setCompressData(as2TradingPartner.getCompressData());
            sciPackagingEntity.setDefaultMimeType(as2TradingPartner.getMimeType());
            sciPackagingEntity.setDefMimeSubtype(as2TradingPartner.getMimeSubType());

        }
        return sciPackagingEntity;
    };

    public void save(String transportObjectId, As2Model as2TradingPartner) {
        SciPackagingEntity sciPackagingEntity = serialize.apply(transportObjectId, as2TradingPartner);
        sciPackagingRepository.save(sciPackagingEntity);
    }

    public void update(String packagingId, As2Model as2TradingPartner) {

        Optional<SciPackagingEntity> sciPackagingOptional = findByObjectId(packagingId);
        if (sciPackagingOptional.isPresent()) {
            SciPackagingEntity sciPackagingEntityOrg = sciPackagingOptional.get();
            SciPackagingEntity sciPackagingEntity = serializeToUpdate.apply(as2TradingPartner);
            sciPackagingEntity.setObjectId(sciPackagingEntityOrg.getObjectId());
            sciPackagingEntity.setEntityId(sciPackagingEntityOrg.getEntityId());
            sciPackagingEntity.setPackagingKey(sciPackagingEntityOrg.getPackagingKey());
            sciPackagingRepository.save(sciPackagingEntity);
        }
    }

    public Optional<SciPackagingEntity> get(boolean isHubInfo, String objectName) {
        String searchString = (isHubInfo) ? objectName + "_" + objectName : objectName;
        Optional<List<SciPackagingEntity>> sciPackagingOptionalList = sciPackagingRepository
                .findByObjectName(PACKAGING + searchString);
        return sciPackagingOptionalList.map(sciPackagings -> sciPackagings.get(0));
    }

    public Optional<SciPackagingEntity> findByObjectId(String objectId) {
        return sciPackagingRepository.findFirstByObjectId(objectId);
    }

    public void delete(boolean isHubInfo, String objectId) {
        findByObjectId(objectId).ifPresent(sciPackaging -> sciPackagingRepository.delete(sciPackaging));
    }
}
