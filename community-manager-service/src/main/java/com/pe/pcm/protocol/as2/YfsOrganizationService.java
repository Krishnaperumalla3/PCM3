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
import com.pe.pcm.protocol.as2.si.YFSOrganizationRepository;
import com.pe.pcm.protocol.as2.si.entity.YFSOrganizationEntity;
import com.pe.pcm.utils.CommonFunctions;
import com.pe.pcm.utils.KeyGeneratorUtil;
import com.pe.pcm.utils.PCMConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.BiFunction;

@Service
public class YfsOrganizationService {

    private final YFSOrganizationRepository yfsOrganizationRepository;

    @Autowired
    public YfsOrganizationService(YFSOrganizationRepository yfsOrganizationRepository) {
        this.yfsOrganizationRepository = yfsOrganizationRepository;
    }

    private final BiFunction<String, As2Model, YFSOrganizationEntity> serialize = (transportObjectId,
                                                                             as2TradingPartner) -> {

        YFSOrganizationEntity yfsOrganizationEntity = new YFSOrganizationEntity();
        String ysfCorpAddrId = KeyGeneratorUtil.getPrimaryKey.apply(PCMConstants.SCI_TR_OBJ_PRE_APPEND,
                PCMConstants.TP_PKEY_RANDOM_COUNT);
        String ysfCorpAddrId1 = KeyGeneratorUtil.getPrimaryKey.apply(PCMConstants.SCI_YFS_CORP_ADDR_PRE_APPEND,
                PCMConstants.TP_PKEY_RANDOM_COUNT);
        yfsOrganizationEntity.setOrganizationKey(as2TradingPartner.getProfileId().substring(0,Math.min(as2TradingPartner.getProfileId().length(),24)));
        yfsOrganizationEntity.setOrganizationCode(as2TradingPartner.getProfileId().substring(0,Math.min(as2TradingPartner.getProfileId().length(),24)));
        yfsOrganizationEntity.setCorporateAddressKey(ysfCorpAddrId + "-" + ysfCorpAddrId1);
        yfsOrganizationEntity.setOrganizationName(CommonFunctions.isNotNull(as2TradingPartner.getIdentityName()) ? as2TradingPartner.getIdentityName() : as2TradingPartner.getProfileId()); //Identity Name in SI
        yfsOrganizationEntity.setObjectId(transportObjectId + ":-3b0a");
        yfsOrganizationEntity.setIsHubOrganization(CommonFunctions.convertBooleanToString(as2TradingPartner.getHubInfo()));
        yfsOrganizationEntity.setIdentifier(as2TradingPartner.getAs2Identifier()); //AS2 Identifier In SI
        yfsOrganizationEntity.setLockid(as2TradingPartner.getHubInfo() ? 1 : 0);

        return yfsOrganizationEntity;
    };

    public void save(String transportObjectId, As2Model as2TradingPartner) {
        YFSOrganizationEntity yfsOrganizationEntity = serialize.apply(transportObjectId, as2TradingPartner);
        yfsOrganizationRepository.save(yfsOrganizationEntity);
    }

    public void update(String transportObjectId, As2Model as2TradingPartner) {
        Optional<YFSOrganizationEntity> yfsOrganizationOptional = findByObjectId(transportObjectId);
        if (yfsOrganizationOptional.isPresent()) {
            YFSOrganizationEntity yfsOrganizationEntity = yfsOrganizationOptional.get();
            yfsOrganizationEntity.setIsHubOrganization(CommonFunctions.convertBooleanToString(as2TradingPartner.getHubInfo()));
            yfsOrganizationEntity.setLockid(as2TradingPartner.getHubInfo() ? 1 : 0);
            yfsOrganizationRepository.save(yfsOrganizationEntity);

        }
    }

    public Optional<YFSOrganizationEntity> get(String transportObjectId) {
        return yfsOrganizationRepository.findByObjectId(transportObjectId + ":-3b0a").map(yfsOrganizations -> yfsOrganizations.get(0));
    }

    public Optional<YFSOrganizationEntity> findById(String organizationKey) {
        return yfsOrganizationRepository.findById(organizationKey);
    }

    private Optional<YFSOrganizationEntity> findByObjectId(String organizationKey) {
        return yfsOrganizationRepository.findByObjectId(organizationKey).map(yfsOrganizationEntities -> yfsOrganizationEntities.get(0));
    }

    public void delete(String transportObjectId) {
        Optional<YFSOrganizationEntity> yfsOrganizationOptional = get(transportObjectId);
        yfsOrganizationOptional.ifPresent(yfsOrganizationEntity -> yfsOrganizationRepository.delete(yfsOrganizationEntity));
    }

}
