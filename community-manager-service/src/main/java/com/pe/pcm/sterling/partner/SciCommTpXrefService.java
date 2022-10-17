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

package com.pe.pcm.sterling.partner;

import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.sterling.partner.sci.SciCommTpXrefRepository;
import com.pe.pcm.sterling.partner.sci.entity.SciCommTpXrefEntity;
import com.pe.pcm.sterling.partner.sci.entity.identity.SciCommTpXrefIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Chenchu Kiran.
 */
@Service
public class SciCommTpXrefService {

    private SciCommTpXrefRepository sciCommTpXrefRepository;

    @Autowired
    public SciCommTpXrefService(SciCommTpXrefRepository sciCommTpXrefRepository) {
        this.sciCommTpXrefRepository = sciCommTpXrefRepository;
    }

    /*We can user this for both Create and Update*/
    public void save(String communityId, String tpProfileId) {
        Optional<SciCommTpXrefEntity> sciCommTpXrefEntityOptional =
                sciCommTpXrefRepository.findById(
                        new SciCommTpXrefIdentity()
                                .setCommunityId(communityId)
                                .setTpProfileId(tpProfileId));
        if (!sciCommTpXrefEntityOptional.isPresent()) {
            sciCommTpXrefRepository.save(
                    new SciCommTpXrefEntity()
                            .setSciCommTpXrefIdentity(
                                    new SciCommTpXrefIdentity()
                                            .setCommunityId(communityId)
                                            .setTpProfileId(tpProfileId)));
        }
    }

    public void delete(String tpProfileId) {
        sciCommTpXrefRepository.deleteAllByTpProfileId(tpProfileId);
    }


    public String getCommunityName(String sciProfileId) {
        return sciCommTpXrefRepository.findFirstBySciCommTpXrefIdentityTpProfileId(sciProfileId).orElseThrow(() -> GlobalExceptionHandler.internalServerError("SciCommTpXref Entity not found")).getSciCommTpXrefIdentity().getCommunityId();
    }

}
