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

package com.pe.pcm.protocol.as2.certificate;

import com.pe.pcm.certificate.entity.CaCertInfoEntity;
import com.pe.pcm.protocol.as2.si.certificate.SciTranspCaCertRepository;
import com.pe.pcm.protocol.as2.si.certificate.entity.SciTranspCaCertEntity;
import com.pe.pcm.utils.PCMConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.pe.pcm.utils.KeyGeneratorUtil.getPrimaryKey;
import static com.pe.pcm.utils.PCMConstants.TP_PKEY_RANDOM_COUNT;

@Service
public class SciTranspCaCertService {

    private final SciTranspCaCertRepository sciTranspCaCertRepository;

    @Autowired
    public SciTranspCaCertService(SciTranspCaCertRepository sciTranspCaCertRepository) {
        this.sciTranspCaCertRepository = sciTranspCaCertRepository;
    }

    private final BiFunction<String, CaCertInfoEntity, SciTranspCaCertEntity> serialize = (transportObjectId, caCertInfoEntity) -> {
        SciTranspCaCertEntity sciTranspCaCertEntity = new SciTranspCaCertEntity();
        sciTranspCaCertEntity.setTransportId(transportObjectId);
        sciTranspCaCertEntity.setCaCertId(caCertInfoEntity.getObjectId());
        sciTranspCaCertEntity.setTransportCertificateKey(getPrimaryKey.apply(PCMConstants.SCI_CA_CERT_PRE_APPEND, TP_PKEY_RANDOM_COUNT) + ":--as2");
        sciTranspCaCertEntity.setGoLiveDate(caCertInfoEntity.getNotBefore());
        sciTranspCaCertEntity.setNotAfterDate(caCertInfoEntity.getNotAfter());
        sciTranspCaCertEntity.setCertStatus(1);
        sciTranspCaCertEntity.setCertOrder(0);
        sciTranspCaCertEntity.setLockid(0);
        return sciTranspCaCertEntity;
    };

    private final Function<CaCertInfoEntity, SciTranspCaCertEntity> serializeToUpdate = caCertInfoEntity -> {
        SciTranspCaCertEntity sciTranspCaCertEntity = new SciTranspCaCertEntity();
        sciTranspCaCertEntity.setCaCertId(caCertInfoEntity.getObjectId());
        sciTranspCaCertEntity.setGoLiveDate(caCertInfoEntity.getNotBefore());
        sciTranspCaCertEntity.setNotAfterDate(caCertInfoEntity.getNotAfter());
        sciTranspCaCertEntity.setCertStatus(1);
        sciTranspCaCertEntity.setCertOrder(0);
        sciTranspCaCertEntity.setLockid(0);
        return sciTranspCaCertEntity;
    };

    public void save(String transportObjectId, CaCertInfoEntity caCertInfoEntity) {
        SciTranspCaCertEntity sciTranspCaCertEntity = serialize.apply(transportObjectId, caCertInfoEntity);
        sciTranspCaCertRepository.save(sciTranspCaCertEntity);
    }

    public void update(String transportObjectId, CaCertInfoEntity caCertInfoEntity) {
        Optional<SciTranspCaCertEntity> sciTranspCaCertOptional = findByTransportId(transportObjectId);
        if (sciTranspCaCertOptional.isPresent()) {
            SciTranspCaCertEntity sciTranspCaCertEntityOrg = sciTranspCaCertOptional.get();
            SciTranspCaCertEntity sciTranspCaCertEntity = serializeToUpdate.apply(caCertInfoEntity);
            sciTranspCaCertEntity.setTransportId(sciTranspCaCertEntityOrg.getTransportId());
            sciTranspCaCertEntity.setTransportCertificateKey(sciTranspCaCertEntityOrg.getTransportCertificateKey());
            sciTranspCaCertRepository.save(sciTranspCaCertEntity);
        }

    }

    public Optional<SciTranspCaCertEntity> get(String transportObjectId) {
        Optional<List<SciTranspCaCertEntity>> sciTranspCaCertOptionalList = sciTranspCaCertRepository
                .findByTransportId(transportObjectId + ":-3afb");
        return sciTranspCaCertOptionalList.map(sciTranspCaCertEntities -> sciTranspCaCertEntities.get(0));
    }

    public Optional<SciTranspCaCertEntity> findByTransportId(String transportObjectId) {
        Optional<List<SciTranspCaCertEntity>> sciTranspCaCertOptionalList = sciTranspCaCertRepository
                .findByTransportId(transportObjectId);
        return sciTranspCaCertOptionalList.map(sciTranspCaCerts -> sciTranspCaCerts.get(0));
    }

    public void delete(String transportObjectId) {
        Optional<SciTranspCaCertEntity> sciTranspCaCertOptional = get(transportObjectId);
        sciTranspCaCertOptional.ifPresent(sciTranspCaCertRepository::delete);
    }


}
