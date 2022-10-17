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

import com.pe.pcm.certificate.entity.TrustedCertInfoEntity;
import com.pe.pcm.protocol.as2.si.certificate.SciDocxUserCertRepository;
import com.pe.pcm.protocol.as2.si.certificate.entity.SciDocxUserCertEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

@Service
public class SciDocxUserCertService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SciDocxUserCertService.class);

    private final SciDocxUserCertRepository sciDocxUserCertRepository;

    @Autowired
    public SciDocxUserCertService(SciDocxUserCertRepository sciDocxUserCertRepository) {
        this.sciDocxUserCertRepository = sciDocxUserCertRepository;
    }

    private final BiFunction<String, TrustedCertInfoEntity, SciDocxUserCertEntity> serialize = (transportObjectId, trustedCertInfo) -> {
        SciDocxUserCertEntity sciDocxUserCertEntity = new SciDocxUserCertEntity();
        sciDocxUserCertEntity.setDocExchangeId(transportObjectId);
        sciDocxUserCertEntity.setUserCertId(trustedCertInfo.getObjectId());
        sciDocxUserCertEntity.setGoLiveDate(trustedCertInfo.getNotBefore());
        sciDocxUserCertEntity.setNotAfterDate(trustedCertInfo.getNotAfter());
        sciDocxUserCertEntity.setCertStatus(trustedCertInfo.getStatus());
        sciDocxUserCertEntity.setCertOrder(1);
        sciDocxUserCertEntity.setLockid(0);
        return sciDocxUserCertEntity;
    };

    private final Function<TrustedCertInfoEntity, SciDocxUserCertEntity> serializeToUpdate = trustedCertInfo -> {
        SciDocxUserCertEntity sciDocxUserCertEntity = new SciDocxUserCertEntity();
        sciDocxUserCertEntity.setUserCertId(trustedCertInfo.getObjectId());
        sciDocxUserCertEntity.setGoLiveDate(trustedCertInfo.getNotBefore());
        sciDocxUserCertEntity.setNotAfterDate(trustedCertInfo.getNotAfter());
        sciDocxUserCertEntity.setCertStatus(trustedCertInfo.getStatus());
        sciDocxUserCertEntity.setCertOrder(1);
        sciDocxUserCertEntity.setLockid(0);
        return sciDocxUserCertEntity;
    };

    public void save(String transportObjectId, TrustedCertInfoEntity trustedCertInfoEntity, String signingKey) {
        sciDocxUserCertRepository.save(serialize.apply(transportObjectId, trustedCertInfoEntity).setCertificateKey(signingKey));
    }

    public void update(String transportObjectId, TrustedCertInfoEntity trustedCertInfoEntity) {

        Optional<SciDocxUserCertEntity> sciDocxUserCertOptional = findByDocExchangeId(transportObjectId);
        if (sciDocxUserCertOptional.isPresent()) {
            SciDocxUserCertEntity sciDocxUserCertEntityOrg = sciDocxUserCertOptional.get();
            SciDocxUserCertEntity sciDocxUserCertEntity = serializeToUpdate.apply(trustedCertInfoEntity);
            sciDocxUserCertEntity.setCertificateKey(sciDocxUserCertEntityOrg.getCertificateKey());
            sciDocxUserCertEntity.setDocExchangeId(sciDocxUserCertEntityOrg.getDocExchangeId());
            sciDocxUserCertRepository.save(sciDocxUserCertEntity);
        }
    }

    public Optional<SciDocxUserCertEntity> get(String transportObjectId) {
        Optional<List<SciDocxUserCertEntity>> sciDocExchangeUserCertOptionalList = sciDocxUserCertRepository
                .findByDocExchangeId(transportObjectId);
        return sciDocExchangeUserCertOptionalList.map(sciDocxUserCerts -> sciDocxUserCerts.get(0));
//        return sciDocExchangeUserCertOptionalList.map(sciDocxUserCerts -> Optional.ofNullable(sciDocxUserCerts.get(0))).orElseGet(() -> Optional.ofNullable(null))
    }

    public Optional<SciDocxUserCertEntity> findByDocExchangeId(String objectId) {
        Optional<List<SciDocxUserCertEntity>> sciDocExchangeUserCertOptionalList = sciDocxUserCertRepository
                .findByDocExchangeId(objectId);
        return sciDocExchangeUserCertOptionalList.map(sciDocxUserCerts -> sciDocxUserCerts.get(0));
    }

    public void delete(String transportObjectId) {
        Optional<SciDocxUserCertEntity> sciDocxUserCertOptional = get(transportObjectId);
        sciDocxUserCertOptional.ifPresent(sciDocxUserCertRepository::delete);
    }

    public void deleteAllByDocExchangeId(String docExchangeId) {
        LOGGER.info("Delete SciDocxUserCertEntities.");
        sciDocxUserCertRepository.deleteAllByDocExchangeId(docExchangeId);
    }

}
