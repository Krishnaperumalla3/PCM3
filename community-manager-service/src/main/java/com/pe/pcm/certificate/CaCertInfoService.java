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

import com.pe.pcm.certificate.entity.CaCertInfoEntity;
import com.pe.pcm.utils.CommonFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Chenchu Kiran.
 */
@Service
public class CaCertInfoService {

    private final CaCertInfoRepository caCertInfoRepository;

    @Autowired
    public CaCertInfoService(CaCertInfoRepository caCertInfoRepository) {
        this.caCertInfoRepository = caCertInfoRepository;
    }

    public Optional<CaCertInfoEntity> getCaCertInfoByName(String name) {
        return caCertInfoRepository.findFirstByName(name);
    }

    public Optional<CaCertInfoEntity> getCaCertById(String objectId) {
        return caCertInfoRepository.findById(objectId);
    }


    public CaCertInfoEntity findByIdNotThrow(String objectId) {
        return CommonFunctions.isNotNull(objectId) ? caCertInfoRepository.findById(objectId).orElse(new CaCertInfoEntity()) : new CaCertInfoEntity();
    }

    public Optional<List<CaCertInfoEntity>> getCaCertInfoList() {
        return caCertInfoRepository.findAllByOrderByNameAsc();
    }

    public Optional<List<CaCertInfoEntity>> getCaCertInfoList(String certName, Boolean isLike) {
        return (isLike ? caCertInfoRepository.findAllByNameContainingIgnoreCaseOrderByName(certName) : caCertInfoRepository.findAllByName(certName));
    }

    public Optional<List<CaCertInfoEntity>> findAllByNameInAndNotAfterBefore(List<String> certNames, Date notAfterDate) {
        return caCertInfoRepository.findAllByNameInAndNotAfterBefore(certNames, notAfterDate);
    }

    public Optional<List<CaCertInfoEntity>> findAllByNameInOrderByName(List<String> certNames) {
        return caCertInfoRepository.findAllByNameInOrderByName(certNames);
    }

    public Optional<List<CaCertInfoEntity>> findAllByIdInAndNotAfterBefore(List<String> objectIds, Date notAfterDate) {
        return caCertInfoRepository.findAllByObjectIdInAndNotAfterBefore(objectIds, notAfterDate);
    }

    public  CaCertInfoEntity findByNameNotThrow(String name){
        return CommonFunctions.isNotNull(name) ? caCertInfoRepository.findFirstByName(name).orElse(new CaCertInfoEntity()) : new CaCertInfoEntity();
    }
}
