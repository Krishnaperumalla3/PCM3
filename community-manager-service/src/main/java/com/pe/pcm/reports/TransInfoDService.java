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

package com.pe.pcm.reports;

import com.pe.pcm.miscellaneous.UserUtilityService;
import com.pe.pcm.reports.entity.TransInfoDEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.util.StringUtils.hasText;

/**
 * @author Kiran Reddy.
 */
@Service
public class TransInfoDService {

    private final TransInfoDRepository transInfoDRepository;
    private final UserUtilityService userUtilityService;

    @Autowired
    public TransInfoDService(TransInfoDRepository transInfoDRepository,
                             UserUtilityService userUtilityService) {
        this.transInfoDRepository = transInfoDRepository;
        this.userUtilityService = userUtilityService;
    }

    public void save(String processId, String seqId, String fileActivity) {
        Integer maxVal;
        if (hasText(processId)) {
            maxVal = transInfoDRepository.findMaxSequenceByBpidOrBpid(processId, seqId);
        } else {
            maxVal = transInfoDRepository.findMaxSequenceByBpid(seqId);
        }

        transInfoDRepository.save(new TransInfoDEntity()
                .setRulename(userUtilityService.getUserOrRole(true))
                .setBpid(String.valueOf(seqId))
                .setBpname("-")
                .setSequence(maxVal == null ? 1 : ++maxVal)
                .setDetails(fileActivity));
    }

    public void save(String mailbox, Long mailboxId, String activityType, String fileName, Long fileLength, String fileActivity) {
        Integer maxSeq = transInfoDRepository.findMaxSequenceByBpid(String.valueOf(mailboxId));
        transInfoDRepository.save(new TransInfoDEntity()
                .setRulename(mailbox)
                .setActName(activityType)
                .setBpid(String.valueOf(mailboxId))
                .setBpname(fileName)
                .setSequence(maxSeq == null ? 1 : maxSeq + 1)
                .setDetails(fileActivity + ", File length: " + fileLength + " Bytes"));
    }

    public List<TransInfoDEntity> findAllByRuleNameAndActivityName(String mailbox, String activityName) {
        return transInfoDRepository.findAllByRulenameAndActNameIgnoreCaseOrderByActivityDtDesc(mailbox, activityName);
    }
}
