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

package com.pe.pcm.partner.staging;

import com.pe.pcm.partner.PartnerStagingRepository;
import com.pe.pcm.partner.entity.PartnerStagingEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;

/**
 * @author Kiran Reddy.
 */
@Service
public class PartnerStagingService {

    private final PartnerStagingRepository partnerStagingRepository;

    @Autowired
    public PartnerStagingService(PartnerStagingRepository partnerStagingRepository) {
        this.partnerStagingRepository = partnerStagingRepository;
    }

    public void save(PartnerStagingEntity partnerStagingEntity) {
        partnerStagingRepository.save(partnerStagingEntity);
    }

    public PartnerStagingEntity get(String pkId) {
        return partnerStagingRepository.findById(pkId).orElseThrow(() -> notFound("Staging Partner"));
    }

    public void delete(String pkId) {
        partnerStagingRepository.findById(pkId)
                .ifPresent(partnerStagingRepository::delete);
    }
}
