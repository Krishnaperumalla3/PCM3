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

import com.pe.pcm.certificate.entity.TrustedCertInfoEntity;
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
public class TrustedCertInfoService {

    private TrustedCertInfoRepository trustedCertInfoRepository;

    @Autowired
    public TrustedCertInfoService(TrustedCertInfoRepository trustedCertInfoRepository) {
        this.trustedCertInfoRepository = trustedCertInfoRepository;
    }

    public Optional<TrustedCertInfoEntity> findById(String certId) {
        return trustedCertInfoRepository.findById(certId);
    }

    public TrustedCertInfoEntity findByIdNoThrow(String certId) {
        return trustedCertInfoRepository.findById(certId).orElse(new TrustedCertInfoEntity());
    }

    public Optional<List<TrustedCertInfoEntity>> getTrustedCetInfoList() {
        return trustedCertInfoRepository.findAllByOrderByNameAsc();
    }

    public Optional<TrustedCertInfoEntity> findByName(String certName) {
        return trustedCertInfoRepository.findFirstByName(certName);
    }

    public List<TrustedCertInfoEntity> findAllByName(String certName, Boolean isLike) {
        return (isLike ?
                trustedCertInfoRepository.findAllByNameContainingIgnoreCaseOrderByName(certName) : trustedCertInfoRepository.findAllByName(certName)
        ).orElse(new ArrayList<>());
    }

    public Optional<List<TrustedCertInfoEntity>> findAllByNameInAndNotAfterBefore(List<String> certNames, Date notAfterDate) {
        return trustedCertInfoRepository.findAllByNameInAndNotAfterBefore(certNames, notAfterDate);
    }
}
