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

import com.pe.pcm.sterling.partner.sci.SciCodeUserXrefRepository;
import com.pe.pcm.sterling.partner.sci.entity.SciCodeUserXrefEntity;
import com.pe.pcm.sterling.partner.sci.entity.identity.SciCodeUserXrefIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Chenchu Kiran Reddy.
 */
@Service
public class SciCodeUserXrefService {

    private final SciCodeUserXrefRepository sciCodeUserXrefRepository;

    @Autowired
    public SciCodeUserXrefService(SciCodeUserXrefRepository sciCodeUserXrefRepository) {
        this.sciCodeUserXrefRepository = sciCodeUserXrefRepository;
    }

    public void save(String userId, String sciTransportEntityId) {
        delete(userId);
        sciCodeUserXrefRepository.save(new SciCodeUserXrefEntity().setStatus(1)
                .setTpObjectId(sciTransportEntityId)
                .setSciCodeUserXrefIdentity(new SciCodeUserXrefIdentity().setUserId(userId)
                        .setCommcodeId("1234-5678")));
    }

    public void delete(String sciTransportEntityId) {
        sciCodeUserXrefRepository.deleteAllByTpObjectId(sciTransportEntityId);
        //sciCodeUserXrefRepository.deleteById(new SciCodeUserXrefIdentity().setUserId(userId).setCommcodeId("1234-5678"))
    }

    public List<SciCodeUserXrefEntity> findAllByTpObjectIdIn(List<String> sciTransportEntityIdsList) {
        return sciCodeUserXrefRepository.findAllByTpObjectIdIn(sciTransportEntityIdsList);
    }


}
