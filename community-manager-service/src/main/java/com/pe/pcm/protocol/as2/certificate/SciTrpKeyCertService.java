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

import com.pe.pcm.certificate.entity.CertsAndPriKeyEntity;
import com.pe.pcm.protocol.as2.si.certificate.SciTrpKeyCertRepository;
import com.pe.pcm.protocol.as2.si.certificate.entity.SciTrpKeyCertEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author Chenchu Kiran Reddy.
 */
@Service
public class SciTrpKeyCertService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SciTrpKeyCertService.class);

    private final SciTrpKeyCertRepository sciTrpKeyCertRepository;

    @Autowired
    public SciTrpKeyCertService(SciTrpKeyCertRepository sciTrpKeyCertRepository) {
        this.sciTrpKeyCertRepository = sciTrpKeyCertRepository;
    }

    private final BiFunction<String, CertsAndPriKeyEntity, SciTrpKeyCertEntity> serialize = (transportObjectId, certsAndPriKeyEntity) -> {
        SciTrpKeyCertEntity sciTrpKeyCertEntity = new SciTrpKeyCertEntity();
        sciTrpKeyCertEntity.setTransportId(transportObjectId);
        sciTrpKeyCertEntity.setKeyCertId(certsAndPriKeyEntity.getObjectId());
        sciTrpKeyCertEntity.setGoLiveDate(certsAndPriKeyEntity.getNotBefore());
        sciTrpKeyCertEntity.setNotAfterDate(certsAndPriKeyEntity.getNotAfter());
        sciTrpKeyCertEntity.setCertStatus(0);
        sciTrpKeyCertEntity.setCertOrder(1);
        sciTrpKeyCertEntity.setLockid(0);
        return sciTrpKeyCertEntity;
    };

    private final Function<CertsAndPriKeyEntity, SciTrpKeyCertEntity> serializeToUpdate = trustedCertInfo -> {
        SciTrpKeyCertEntity sciTrpKeyCertEntity = new SciTrpKeyCertEntity();
        sciTrpKeyCertEntity.setKeyCertId(trustedCertInfo.getObjectId());
        sciTrpKeyCertEntity.setGoLiveDate(trustedCertInfo.getNotBefore());
        sciTrpKeyCertEntity.setNotAfterDate(trustedCertInfo.getNotAfter());
        sciTrpKeyCertEntity.setCertStatus(0);
        sciTrpKeyCertEntity.setCertOrder(1);
        sciTrpKeyCertEntity.setLockid(0);
        return sciTrpKeyCertEntity;
    };


    public void save(String transportObjectId, CertsAndPriKeyEntity certsAndPriKeyEntity, String certificateKey) {
        LOGGER.info("Create/Update SciTrpKeyCertEntity.");
        sciTrpKeyCertRepository.save(serialize.apply(transportObjectId, certsAndPriKeyEntity).setCertificateKey(certificateKey));
    }

    public void update(String transportObjectId, CertsAndPriKeyEntity certsAndPriKeyEntity) {
        Optional<SciTrpKeyCertEntity> sciTrpKeyCertEntityOptional = findByTransportId(transportObjectId);
        if (sciTrpKeyCertEntityOptional.isPresent()) {
            SciTrpKeyCertEntity sciTrpKeyCertEntityOrg = sciTrpKeyCertEntityOptional.get();
            SciTrpKeyCertEntity sciTrpUserCertEntity = serializeToUpdate.apply(certsAndPriKeyEntity);
            sciTrpUserCertEntity.setTransportId(sciTrpKeyCertEntityOrg.getTransportId());
            sciTrpUserCertEntity.setCertificateKey(sciTrpKeyCertEntityOrg.getCertificateKey());
            sciTrpKeyCertRepository.save(sciTrpUserCertEntity);

        }
    }

    public Optional<SciTrpKeyCertEntity> get(String transportObjectId) {
        return sciTrpKeyCertRepository.findFirstByTransportId(transportObjectId);
    }

    public Optional<SciTrpKeyCertEntity> findByTransportId(String transportId) {
        return sciTrpKeyCertRepository.findFirstByTransportId(transportId);
    }

    public void delteAllByTransportId(String transportId) {
        LOGGER.info("Delete SciTrpKeyCertEntities.");
        sciTrpKeyCertRepository.deleteAllByTransportId(transportId);
    }

}
