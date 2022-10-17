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

import com.pe.pcm.protocol.as2.si.certificate.SciTranspCaCertRepository;
import com.pe.pcm.protocol.as2.si.certificate.entity.SciTranspCaCertEntity;
import com.pe.pcm.sterling.dto.TransCertDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author Chenchu Kiran Reddy.
 */
@Service
public class SciTranspCaCertServiceDup {
    private static final Logger LOGGER = LoggerFactory.getLogger(SciTranspCaCertServiceDup.class);

    private final SciTranspCaCertRepository sciTranspCaCertRepository;

    @Autowired
    public SciTranspCaCertServiceDup(SciTranspCaCertRepository sciTranspCaCertRepository) {
        this.sciTranspCaCertRepository = sciTranspCaCertRepository;
    }

    private final Function<TransCertDTO, SciTranspCaCertEntity> serialize = transCertDTO -> {
        SciTranspCaCertEntity sciTranspCaCertEntity = new SciTranspCaCertEntity();
        sciTranspCaCertEntity.setTransportId(transCertDTO.getTransportId());
        sciTranspCaCertEntity.setCaCertId(transCertDTO.getKeyCertId());
        sciTranspCaCertEntity.setTransportCertificateKey(transCertDTO.getCertificateKey());
        sciTranspCaCertEntity.setGoLiveDate(transCertDTO.getGoLiveDate());
        sciTranspCaCertEntity.setNotAfterDate(transCertDTO.getNotAfterDate());
        sciTranspCaCertEntity.setCertStatus(0);
        sciTranspCaCertEntity.setCertOrder(transCertDTO.getCertOrder());
        sciTranspCaCertEntity.setLockid(0);
        return sciTranspCaCertEntity;
    };

    public void save(TransCertDTO transCertDTO) {
        LOGGER.info("Create/Update SciTranspCaCertEntity");
        sciTranspCaCertRepository.save(serialize.apply(transCertDTO));
    }

    public Optional<SciTranspCaCertEntity> findByTransportId(String transportObjectId) {
        return sciTranspCaCertRepository.findByTransportId(transportObjectId).map(sciTranspCaCerts -> !sciTranspCaCerts.isEmpty() ? sciTranspCaCerts.get(0) : null);
    }

    public List<SciTranspCaCertEntity> findAllByTransportId(String transportObjectId) {
        return sciTranspCaCertRepository.findAllByTransportId(transportObjectId);
    }

    public void deleteAllByTransportId(String transportId) {
        LOGGER.info("Delete SciTranspCaCertEntities.");
        sciTranspCaCertRepository.deleteAllByTransportId(transportId);
    }

}
