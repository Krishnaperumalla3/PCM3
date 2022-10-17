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

package com.pe.pcm.workflow;

import com.pe.pcm.workflow.entity.ProcessEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProcessRepository extends CrudRepository<ProcessEntity, String> {

    Optional<List<ProcessEntity>> findAllByPartnerProfile(String partnerProfile);

    Optional<List<ProcessEntity>> findAllByPartnerProfileAndApplicationProfile(String partnerProfile, String applicationProfile);

    Optional<List<ProcessEntity>> findByPartnerProfileIn(List<String> partnerProfiles);

    Optional<List<ProcessEntity>> findByPartnerProfile(String partnerProfile);

    Optional<List<ProcessEntity>> findByApplicationProfile(String applicationProfile);

    @Modifying
    @Query("DELETE FROM ProcessEntity PE WHERE PE.seqId IN :seqIds")
    void deleteAllBySeqIdIn(@Param("seqIds") List<String> seqIds);

    Optional<List<ProcessEntity>> findByApplicationProfileIn(List<String> applicationProfile);

}
