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
import com.pe.pcm.protocol.as2.si.SciDocExchangeRepository;
import com.pe.pcm.protocol.as2.si.entity.SciDocExchangeEntity;
import com.pe.pcm.utils.KeyGeneratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.pe.pcm.utils.PCMConstants.*;
import static java.util.Optional.empty;
import static java.util.Optional.of;

@Service
public class DocExchangeService {

    private SciDocExchangeRepository sciDocExchangeRepository;

    @Autowired
    public DocExchangeService(SciDocExchangeRepository sciDocExchangeRepository) {
        this.sciDocExchangeRepository = sciDocExchangeRepository;
    }

    private BiFunction<String, As2Model, SciDocExchangeEntity> serialize = (transportObjectId,
                                                                            as2TradingPartner) -> {
        SciDocExchangeEntity sciDocExchangeEntity = new SciDocExchangeEntity();
        String docExchangeObjectId = KeyGeneratorUtil.getPrimaryKey.apply(SCI_DE_OBJ_PRE_APPEND, SCI_RANDOM_COUNT);

        sciDocExchangeEntity.setObjectId(transportObjectId + ":-3afc");
        sciDocExchangeEntity.setEntityId(transportObjectId + ":-3b0a");
        sciDocExchangeEntity.setDocExchangeKey(docExchangeObjectId + "-as2");
        sciDocExchangeEntity.setSigUserCertId(as2TradingPartner.getExchangeCertificate());
        sciDocExchangeEntity.setSigKeyCertId(as2TradingPartner.getSigningCertification());
        sciDocExchangeEntity.setObjectName(DOC_EXCHANGE + as2TradingPartner.getProfileId() + "_" + as2TradingPartner.getProfileName());
        if (!as2TradingPartner.getHubInfo()) {
            sciDocExchangeEntity.setObjectName(DOC_EXCHANGE + as2TradingPartner.getProfileId() + "_" + as2TradingPartner.getProfileName());
            // sciDocExchangeEntity.setObjectName(DOC_EXCHANGE + as2TradingPartner.getProfileId());
            sciDocExchangeEntity.setMsgSigningAlg(as2TradingPartner.getSignatureAlgorithm());
            sciDocExchangeEntity.setEnvEncryptAlg(as2TradingPartner.getEncryptionAlgorithm());
        }
        return sciDocExchangeEntity;
    };

    private Function<As2Model, SciDocExchangeEntity> serializeToUpdate = as2TradingPartner -> {
        SciDocExchangeEntity sciDocExchangeEntity = new SciDocExchangeEntity();

        sciDocExchangeEntity.setSigUserCertId(as2TradingPartner.getExchangeCertificate());
        sciDocExchangeEntity.setSigKeyCertId(as2TradingPartner.getSigningCertification());
        sciDocExchangeEntity.setObjectName(DOC_EXCHANGE + as2TradingPartner.getProfileId() + "_" + as2TradingPartner.getProfileName());
        if (!as2TradingPartner.getHubInfo()) {
            sciDocExchangeEntity.setObjectName(DOC_EXCHANGE + as2TradingPartner.getProfileId() + "_" + as2TradingPartner.getProfileName());
           // sciDocExchangeEntity.setObjectName(DOC_EXCHANGE + as2TradingPartner.getProfileId());
            sciDocExchangeEntity.setMsgSigningAlg(as2TradingPartner.getSignatureAlgorithm());
            sciDocExchangeEntity.setEnvEncryptAlg(as2TradingPartner.getEncryptionAlgorithm());
        }
        return sciDocExchangeEntity;
    };

    public void save(String transportObjectId, As2Model as2TradingPartner) {
        sciDocExchangeRepository.save(serialize.apply(transportObjectId, as2TradingPartner));
    }

    public Optional<SciDocExchangeEntity> update(String transportEntityId, As2Model as2TradingPartner) {

        Optional<SciDocExchangeEntity> sciDocExchangeOptional = findByEntityId(transportEntityId);
        if (sciDocExchangeOptional.isPresent()) {
            SciDocExchangeEntity sciDocExchangeEntityOrg = sciDocExchangeOptional.get();
            SciDocExchangeEntity sciDocExchangeEntity = serializeToUpdate.apply(as2TradingPartner);
            sciDocExchangeEntity.setObjectId(sciDocExchangeEntityOrg.getObjectId());
            sciDocExchangeEntity.setEntityId(sciDocExchangeEntityOrg.getEntityId());
            sciDocExchangeEntity.setDocExchangeKey(sciDocExchangeEntityOrg.getDocExchangeKey());
            return of(sciDocExchangeRepository.save(sciDocExchangeEntity));
        }
        return empty();
    }


    public Optional<SciDocExchangeEntity> get(boolean isHubInfo, String objectName) {
        String searchString = (isHubInfo) ? objectName + "_" + objectName : objectName;
        Optional<List<SciDocExchangeEntity>> sciDocExchangeOptionalList = sciDocExchangeRepository
                .findByObjectName(DOC_EXCHANGE + searchString);
        return sciDocExchangeOptionalList.map(sciDocExchanges -> sciDocExchanges.get(0));
    }

    public Optional<SciDocExchangeEntity> findByEntityId(String entityId) {
        Optional<List<SciDocExchangeEntity>> sciDocExchangeOptionalList = sciDocExchangeRepository
                .findByEntityId(entityId);
        return sciDocExchangeOptionalList.map(sciDocExchanges -> sciDocExchanges.get(0));
    }

    public void delete(boolean isHubInfo, String entityId) {
        findByEntityId(entityId).ifPresent(sciDocExchange -> sciDocExchangeRepository.delete(sciDocExchange));
    }

}
