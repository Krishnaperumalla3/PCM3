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
import com.pe.pcm.protocol.as2.si.entity.SciProfileEntity;
import com.pe.pcm.protocol.as2.si.SciProfileRepository;
import com.pe.pcm.utils.KeyGeneratorUtil;
import com.pe.pcm.utils.PCMConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

@Service
public class ProfileService {

    private final SciProfileRepository sciProfileRepository;

    @Autowired
    public ProfileService(SciProfileRepository sciProfileRepository) {
        this.sciProfileRepository = sciProfileRepository;
    }

    private final BiFunction<String, As2Model, SciProfileEntity> serialize = (transportObjectId, as2TradingPartner) -> {
        SciProfileEntity sciProfileEntity = new SciProfileEntity();
        String profileObjectId = KeyGeneratorUtil.getPrimaryKey.apply(PCMConstants.SCI_PR_OBJ_PRE_APPEND,
                PCMConstants.SCI_RANDOM_COUNT);
        sciProfileEntity.setObjectId(transportObjectId + ":-3afe");
        sciProfileEntity.setObjectName(as2TradingPartner.getProfileName()); //Profile Name In SI
        sciProfileEntity.setEntityId(transportObjectId + ":-3b0a");
        sciProfileEntity.setDelivChannelId(transportObjectId + ":-3afa");
        sciProfileEntity.setPackagingId(transportObjectId + ":-3afd");
        sciProfileEntity.setProfileKey(profileObjectId + "-as2");
        return sciProfileEntity;
    };

    public void save(String transportObjectId, As2Model as2TradingPartner) {
        SciProfileEntity sciProfileEntity = serialize.apply(transportObjectId, as2TradingPartner);
        sciProfileRepository.save(sciProfileEntity);
    }

    public void update(As2Model as2TradingPartner) {

        Optional<SciProfileEntity> sciProfileOptional = get(as2TradingPartner.getProfileId());
        if (sciProfileOptional.isPresent()) {
            SciProfileEntity sciProfileEntity = sciProfileOptional.get();
            sciProfileRepository.save(sciProfileEntity);
        }

    }

    public Optional<SciProfileEntity> get(String objectName) {
        Optional<List<SciProfileEntity>> sciProfileOptionalList = sciProfileRepository.findByObjectName(objectName);
        return sciProfileOptionalList.map(sciProfiles -> sciProfiles.get(0));
    }

    public Optional<SciProfileEntity> findByEntityId(String entityId) {
        return sciProfileRepository.findFirstByEntityId(entityId);
    }

    public void delete(String entityId) {
        findByEntityId(entityId).ifPresent(sciProfileRepository::delete);
    }

}
