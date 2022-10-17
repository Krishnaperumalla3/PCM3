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

import com.pe.pcm.workflow.entity.ProcessRulesEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProcessRulesRepository extends CrudRepository<ProcessRulesEntity, String> {

    Optional<List<ProcessRulesEntity>> findAllByPkIdIn(List<String> pkIds);

    Integer deleteAllByPkIdIn(List<String> pkIds);

    Optional<List<ProcessRulesEntity>> findAllByOrderByPkId();

    Optional<ProcessRulesEntity> findFirstByRuleId(String ruleId);

}
