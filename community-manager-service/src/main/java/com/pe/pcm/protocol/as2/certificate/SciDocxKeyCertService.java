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
import com.pe.pcm.protocol.as2.si.certificate.SciDocxKeyCertRepository;
import com.pe.pcm.protocol.as2.si.certificate.entity.SciDocxKeyCertEntity;
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
public class SciDocxKeyCertService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SciDocxKeyCertService.class);

    private final SciDocxKeyCertRepository sciDocxKeyCertRepository;

    @Autowired
    public SciDocxKeyCertService(SciDocxKeyCertRepository sciDocxKeyCertRepository) {
        this.sciDocxKeyCertRepository = sciDocxKeyCertRepository;
    }

    private final BiFunction<String, CertsAndPriKeyEntity, SciDocxKeyCertEntity> serialize = (transportObjectId, certsAndPriKeyEntity) -> {
        SciDocxKeyCertEntity docxKeyCert = new SciDocxKeyCertEntity();
        docxKeyCert.setDocExchangeId(transportObjectId);
        docxKeyCert.setKeyCertId(certsAndPriKeyEntity.getObjectId());
        docxKeyCert.setGoLiveDate(certsAndPriKeyEntity.getNotBefore());
        docxKeyCert.setNotAfterDate(certsAndPriKeyEntity.getNotAfter());
        docxKeyCert.setCertStatus(certsAndPriKeyEntity.getStatus());
        docxKeyCert.setCertStatus(0);
        docxKeyCert.setCertOrder(1);
        docxKeyCert.setLockid(0);
        return docxKeyCert;
    };

    public void save(String transportObjectId, CertsAndPriKeyEntity certsAndPriKeyEntity, String certificateKey) {
        LOGGER.info("Create/Update SciDocxKeyCertEntity.");
        sciDocxKeyCertRepository.save(serialize.apply(transportObjectId, certsAndPriKeyEntity).setCertificateKey(certificateKey));
    }

    public Optional<SciDocxKeyCertEntity> findByDocExchangeId(String objectId) {
        return sciDocxKeyCertRepository.findByDocExchangeId(objectId).map(sciDocxKeyCertEntities -> !sciDocxKeyCertEntities.isEmpty() ? sciDocxKeyCertEntities.get(0) : new SciDocxKeyCertEntity());
    }

    public void deleteAllByDocExchangeId(String docExchangeId) {
        LOGGER.info("Delete SciDocxKeyCertEntity.");
        sciDocxKeyCertRepository.deleteAllByDocExchangeId(docExchangeId);
    }

}
