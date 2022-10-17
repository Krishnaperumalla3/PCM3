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

package com.pe.pcm.certificate;

import com.pe.pcm.certificate.entity.CertsAndPriKeyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Chenchu Kiran.
 */
@Service
public class CertsAndPriKeyService {

    private final CertsAndPriKeyRepository certsAndPriKeyRepository;

    @Autowired
    public CertsAndPriKeyService(CertsAndPriKeyRepository certsAndPriKeyRepository) {
        this.certsAndPriKeyRepository = certsAndPriKeyRepository;
    }

    public Optional<CertsAndPriKeyEntity> findById(String certId) {
        return certsAndPriKeyRepository.findById(certId);
    }

    public CertsAndPriKeyEntity findByIdNoThrow(String certId) {
        return certsAndPriKeyRepository.findById(certId).orElse(new CertsAndPriKeyEntity());
    }

    public Optional<List<CertsAndPriKeyEntity>> getCertsAndPriKeyList() {
        return certsAndPriKeyRepository.findAllByOrderByNameAsc();
    }

    public Optional<CertsAndPriKeyEntity> findByName(String certName) {
        return certsAndPriKeyRepository.findFirstByName(certName);
    }

    public List<CertsAndPriKeyEntity> getCertsAndPriKeyIdList(String certName, Boolean isLike) {
        return (isLike ? certsAndPriKeyRepository.findAllByNameContainingIgnoreCaseOrderByName(certName) : certsAndPriKeyRepository.findAllByName(certName)).orElse(new ArrayList<>());
    }

    public Optional<List<CertsAndPriKeyEntity>> findAllByNotAfterBefore(Date notAfterDate) {
        return certsAndPriKeyRepository.findAllByNotAfterBefore(notAfterDate);
    }

}
