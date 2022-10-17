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
import com.pe.pcm.protocol.as2.si.entity.SciTransportEntity;
import com.pe.pcm.protocol.as2.si.SciTransportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.pe.pcm.utils.PCMConstants.TRANSPORT;
import static org.springframework.util.StringUtils.isEmpty;

@Service
public class TransportService {

    private final SciTransportRepository sciTransportRepository;

    @Autowired
    public TransportService(SciTransportRepository sciTransportRepository) {
        this.sciTransportRepository = sciTransportRepository;
    }

    private final BiFunction<String, As2Model, SciTransportEntity> serialize = (transportObjectId, as2TradingPartner) -> {

        SciTransportEntity sciTransportEntity = new SciTransportEntity();
        sciTransportEntity.setObjectId(transportObjectId + ":-3afb");
        sciTransportEntity.setEntityId(transportObjectId + ":-3b0a");
        sciTransportEntity.setTransportKey(transportObjectId + "-as2");
        sciTransportEntity.setMailId(as2TradingPartner.getEmailId());
        sciTransportEntity.setObjectName(TRANSPORT + as2TradingPartner.getProfileId() + "_" + as2TradingPartner.getProfileName());

        if (!as2TradingPartner.getHubInfo()) {
            //We should fix this
//            sciTransportEntity.setObjectName("transport_" + as2TradingPartner.getProfileId() + "_" + as2TradingPartner.getProfileName())
            sciTransportEntity.setKeyCertId(as2TradingPartner.getKeyCertification());
            sciTransportEntity.setUserCertId(as2TradingPartner.getKeyCertificatePassphrase()); // As2CACertificateName?
            sciTransportEntity.setEndPoint(as2TradingPartner.getEndPoint());
            sciTransportEntity.setSslOption((isEmpty(as2TradingPartner.getSslType()) ? sciTransportEntity.getSslOption()
                    : as2TradingPartner.getSslType()));
            sciTransportEntity.setCipherStrength(
                    (isEmpty(as2TradingPartner.getCipherStrength()) ? sciTransportEntity.getCipherStrength()
                            : as2TradingPartner.getCipherStrength()));
            sciTransportEntity.setResponseTimeout(String.valueOf(as2TradingPartner.getResponseTimeout()));

        }
        return sciTransportEntity;
    };

    private final Function<As2Model, SciTransportEntity> serializeToUpdate = as2TradingPartner -> {

        SciTransportEntity sciTransportEntity = new SciTransportEntity();
        sciTransportEntity.setMailId(as2TradingPartner.getEmailId());
        sciTransportEntity.setObjectName(TRANSPORT + as2TradingPartner.getProfileId() + "_" + as2TradingPartner.getProfileName());

        if (!as2TradingPartner.getHubInfo()) {
//            sciTransportEntity.setObjectName("transport_" + as2TradingPartner.getProfileId() + "_" + as2TradingPartner.getProfileName())
            sciTransportEntity.setKeyCertId(as2TradingPartner.getKeyCertification());
            sciTransportEntity.setUserCertId(as2TradingPartner.getKeyCertificatePassphrase());
            sciTransportEntity.setEndPoint(as2TradingPartner.getEndPoint());
            sciTransportEntity.setSslOption(isEmpty(as2TradingPartner.getSslType()) ? sciTransportEntity.getSslOption() : as2TradingPartner.getSslType());
            sciTransportEntity.setCipherStrength(isEmpty(as2TradingPartner.getCipherStrength()) ? sciTransportEntity.getCipherStrength() : as2TradingPartner.getCipherStrength());
            sciTransportEntity.setResponseTimeout(String.valueOf(as2TradingPartner.getResponseTimeout()));

        }
        return sciTransportEntity;
    };

    public void save(String transportObjectId, As2Model as2TradingPartner) {
        sciTransportRepository.save(serialize.apply(transportObjectId, as2TradingPartner));
    }

    public void update(SciTransportEntity sciTransportEntityOrg, As2Model as2TradingPartner) {

        SciTransportEntity sciTransportEntity = serializeToUpdate.apply(as2TradingPartner);
        sciTransportEntity.setObjectId(sciTransportEntityOrg.getObjectId());
        sciTransportEntity.setEntityId(sciTransportEntityOrg.getEntityId());
        sciTransportEntity.setTransportKey(sciTransportEntityOrg.getTransportKey());
        sciTransportRepository.save(sciTransportEntity);
    }

    public Optional<SciTransportEntity> get(String objectName) {
        Optional<List<SciTransportEntity>> sciTransportOptionalList = sciTransportRepository.findByObjectName(objectName);
        return sciTransportOptionalList.map(sciTransports -> sciTransports.get(0));
    }

    public void delete(String as2Identifier) {
        Optional<SciTransportEntity> sciTransportOptional = get(as2Identifier);
        sciTransportOptional.ifPresent(sciTransportRepository::delete);
    }

}
