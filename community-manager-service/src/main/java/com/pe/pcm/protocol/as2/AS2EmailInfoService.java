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
import com.pe.pcm.protocol.as2.si.entity.As2EmailInfoEntity;
import com.pe.pcm.protocol.as2.si.AS2EmailInfoRepository;
import com.pe.pcm.protocol.as2.si.entity.identity.As2EmailInfoIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.BiFunction;

@Service
public class AS2EmailInfoService {

    private final AS2EmailInfoRepository as2EmailInfoRepository;

    @Autowired
    public AS2EmailInfoService(AS2EmailInfoRepository as2EmailInfoRepository) {
        this.as2EmailInfoRepository = as2EmailInfoRepository;
    }

    private BiFunction<String, As2Model, As2EmailInfoEntity> serialize = (transportObjectId, as2TradingPartner) ->
            new As2EmailInfoEntity().setAs2EmailInfoIdentity(
                    new As2EmailInfoIdentity().setEntityId(transportObjectId + ":-3b0a")
                            .setProfileId(transportObjectId))
                    .setEmailAddress(as2TradingPartner.getEmailId());

    public void save(String transportObjectId, As2Model as2TradingPartner) {
        as2EmailInfoRepository.save(serialize.apply(transportObjectId, as2TradingPartner));
    }

    public void update(String transportObjectId, As2Model as2TradingPartner) {
        get(transportObjectId).ifPresent(as2EmailInfoEntityOptional -> as2EmailInfoRepository.save(as2EmailInfoEntityOptional.setEmailAddress(as2TradingPartner.getEmailId())));
    }

    public Optional<As2EmailInfoEntity> get(String transportObjectId) {
        return as2EmailInfoRepository.findById(new As2EmailInfoIdentity().setProfileId(transportObjectId + ":-3b0a"));
    }

    public void delete(String transportObjectId) {
        get(transportObjectId).ifPresent(as2EmailInfoEntity -> as2EmailInfoRepository.delete(as2EmailInfoEntity));
    }

}
