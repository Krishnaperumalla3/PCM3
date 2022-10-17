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

import com.pe.pcm.protocol.as2.si.certificate.SciTrpSslCertRepository;
import com.pe.pcm.protocol.as2.si.certificate.entity.SciTrpSslCertEntity;
import com.pe.pcm.sterling.dto.TransCertDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author Chenchu Kiran Reddy.
 */
@Service
public class SciTrpSslCertService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SciTrpSslCertService.class);

    private final SciTrpSslCertRepository sciTrpSslCertRepository;

    @Autowired
    public SciTrpSslCertService(SciTrpSslCertRepository sciTrpSslCertRepository) {
        this.sciTrpSslCertRepository = sciTrpSslCertRepository;
    }

    private final Function<TransCertDTO, SciTrpSslCertEntity> serialize = transCertDTO ->
            new SciTrpSslCertEntity().setCertificateKey(transCertDTO.getCertificateKey())
                    .setTransportId(transCertDTO.getTransportId())
                    .setKeyCertId(transCertDTO.getKeyCertId())
                    .setGoLiveDate(transCertDTO.getGoLiveDate())
                    .setNotAfterDate(transCertDTO.getNotAfterDate())
                    .setCertOrder(transCertDTO.getCertOrder())
                    .setCertStatus(transCertDTO.getCertStatus());

    public void save(TransCertDTO transCertDTO) {
        LOGGER.info("Create/Update SciTrpSslCertEntity");
        sciTrpSslCertRepository.save(serialize.apply(transCertDTO));
    }

    public Optional<SciTrpSslCertEntity> get(String transportObjectId, String keyCertId) {
        return sciTrpSslCertRepository.findFirstByTransportIdAndKeyCertId(transportObjectId, keyCertId);
    }

    public void deleteAllByTransportId(String transportId) {
        LOGGER.info("Delete SciTrpSslCertEntity.");
        sciTrpSslCertRepository.deleteAllByTransportId(transportId);
    }

    public void deleteById(String certificateKey) {
        sciTrpSslCertRepository.deleteById(certificateKey);
    }

    public List<SciTrpSslCertEntity> finaAllByTransportIdAndKeyCertIds(String transportId, List<String> keyCertIds) {
        return sciTrpSslCertRepository.findAllByTransportIdAndKeyCertIdIn(transportId, keyCertIds).orElse(new ArrayList<>());
    }

    public List<SciTrpSslCertEntity> findAllByTransportId(String transportId) {
        return sciTrpSslCertRepository.findAllByTransportId(transportId).orElse(new ArrayList<>());
    }

}
