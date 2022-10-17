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
import com.pe.pcm.protocol.as2.si.certificate.SciTrpUserCertRepository;
import com.pe.pcm.protocol.as2.si.certificate.entity.SciTrpUserCertEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.BiFunction;

/**
 * @author Chenchu Kiran Reddy.
 */
@Service
public class SciTrpUserCertService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SciTrpUserCertService.class);

    private final SciTrpUserCertRepository sciTrpUserCertRepository;

    @Autowired
    public SciTrpUserCertService(SciTrpUserCertRepository sciTrpUserCertRepository) {
        this.sciTrpUserCertRepository = sciTrpUserCertRepository;
    }

    private final BiFunction<String, TrustedCertInfoEntity, SciTrpUserCertEntity> serialize = (transportObjectId, trustedCertInfo) -> {
        SciTrpUserCertEntity sciTrpUserCertEntity = new SciTrpUserCertEntity();
        sciTrpUserCertEntity.setTransportId(transportObjectId);
        sciTrpUserCertEntity.setUserCertId(trustedCertInfo.getObjectId());
//        sciTrpUserCertEntity.setCertificateKey()
        sciTrpUserCertEntity.setGoLiveDate(trustedCertInfo.getNotBefore());
        sciTrpUserCertEntity.setNotAfterDate(trustedCertInfo.getNotAfter());
        sciTrpUserCertEntity.setCertStatus(0);
        sciTrpUserCertEntity.setCertOrder(1);
        sciTrpUserCertEntity.setLockid(0);
        return sciTrpUserCertEntity;
    };

    public void save(String transportObjectId, TrustedCertInfoEntity trustedCertInfoEntity, String certKey) {
        sciTrpUserCertRepository.save(serialize.apply(transportObjectId, trustedCertInfoEntity).setCertificateKey(certKey));
    }

    public Optional<SciTrpUserCertEntity> findByTransportId(String transportObjectId) {
        return sciTrpUserCertRepository
                .findByTransportId(transportObjectId).map(sciTrpUserCerts -> !sciTrpUserCerts.isEmpty() ? sciTrpUserCerts.get(0) : new SciTrpUserCertEntity());
    }

    public void deleteAllByTransportId(String transportObjectId) {
        LOGGER.info("Delete SciTrpUserCertEntities.");
        sciTrpUserCertRepository.deleteAllByTransportId(transportObjectId);
    }
}
