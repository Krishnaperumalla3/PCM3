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

package com.pe.pcm.protocol.si;

import com.pe.pcm.generator.PrimaryKeyGeneratorService;
import com.pe.pcm.protocol.as2.si.SciContractExtnRepository;
import com.pe.pcm.protocol.as2.si.SciContractRepository;
import com.pe.pcm.protocol.as2.si.entity.SciContractEntity;
import com.pe.pcm.sterling.dto.SciContractDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;

/**
 * @author Chenchu Kiran.
 */
@Service
public class SciContractServiceDup {

    private SciContractRepository sciContractRepository;
    private SciContractExtnRepository sciContractExtnRepository;
    private PrimaryKeyGeneratorService primaryKeyGeneratorService;

    @Autowired
    public SciContractServiceDup(SciContractRepository sciContractRepository, SciContractExtnRepository sciContractExtnRepository, PrimaryKeyGeneratorService primaryKeyGeneratorService) {
        this.sciContractRepository = sciContractRepository;
        this.sciContractExtnRepository = sciContractExtnRepository;
        this.primaryKeyGeneratorService = primaryKeyGeneratorService;
    }

    //TODO
    public void save(SciContractDTO sciContractDTO) {

        save1(sciContractDTO, sciContractDTO.getCommunityProfileId(), sciContractDTO.getSciProfileObjectId(), "FROM");
        save1(sciContractDTO, sciContractDTO.getSciProfileObjectId(), sciContractDTO.getCommunityProfileId(), "TO");

    }

    private void save1(SciContractDTO sciContractDTO, String producer, String consumer, String flowType) {
        Optional<SciContractEntity> sciContractEntityOptional =
                sciContractRepository.findFirstByProdProfileIdAndConsumeProfileId(producer, consumer);
        if (sciContractEntityOptional.isPresent()) {
            sciContractDTO.setContractKey(sciContractEntityOptional.get().getContractKey())
                    .setStartDate(sciContractEntityOptional.get().getStartDate())
                    .setEndDate(sciContractEntityOptional.get().getEndDate())
                    .setObjectId(sciContractEntityOptional.get().getObjectId());
        } else {

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            sciContractDTO.setContractKey(primaryKeyGeneratorService.generatePrimaryKey("", 4))
                    .setObjectId(primaryKeyGeneratorService.generatePrimaryKey("", 5))
                    .setStartDate(formatter.format(cal.getTime()));
            cal.add(Calendar.DATE, 7);
            sciContractDTO.setEndDate(formatter.format(cal.getTime()));
        }

        sciContractRepository.save(new SciContractEntity().setObjectId(sciContractDTO.getObjectId())
                .setWorkflowName(sciContractDTO.getWorkFlowName())
                .setObjectName(sciContractDTO.getProfileName() + "_" + flowType + sciContractDTO.getCommunityName() + "_AFTCommunity_" + sciContractDTO.getProtocol() + "_" + sciContractDTO.getProfileType() + "_CONTRACT")
                .setProdProfileId(producer)
                .setConsumeProfileId(consumer)
                .setContractKey(sciContractDTO.getContractKey())
                .setStartDate(sciContractDTO.getStartDate())
                .setEndDate(sciContractDTO.getEndDate()));
    }

    public void delete(String sciProfileObjectId) {
        sciContractRepository.deleteAllByProdProfileId(sciProfileObjectId);
        sciContractRepository.deleteAllByConsumeProfileId(sciProfileObjectId);
    }

}
