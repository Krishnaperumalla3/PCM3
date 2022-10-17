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

import com.pe.pcm.reports.entity.TransInfoDEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Kiran Reddy.
 */
@Repository
public interface TransInfoDRepository extends CrudRepository<TransInfoDEntity, Long> {

    Optional<List<TransInfoDEntity>> findByBpidOrderBySequenceAsc(String bpId);

    Optional<List<TransInfoDEntity>> findByBpidAndSequenceNotNullOrderBySequenceAsc(String bpId);

    List<TransInfoDEntity> findAllByBpidOrderBySequence(String bpId);

    @Query("SELECT MAX(sequence) FROM TransInfoDEntity WHERE bpid = :bpId OR bpid = :seqId")
    Integer findMaxSequenceByBpidOrBpid(@Param("bpId") String bpId, @Param("seqId") String seqId);

    @Query("SELECT MAX(sequence) FROM TransInfoDEntity WHERE bpid = :seqId")
    Integer findMaxSequenceByBpid(@Param("seqId") String seqId);

    List<TransInfoDEntity> findAllByRulenameAndActNameIgnoreCaseOrderByActivityDtDesc(String ruleName, String actName);

}
