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

package com.pe.pcm.rule;

import com.pe.pcm.rule.entity.RuleEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface RuleRepository extends CrudRepository<RuleEntity, String>, PagingAndSortingRepository<RuleEntity, String>, JpaSpecificationExecutor<RuleEntity> {

    Optional<RuleEntity> findByRuleName(String ruleName);

    Optional<List<RuleEntity>> findRulesEntitiesByOrderByRuleNameAsc();

    Optional<RuleEntity> findFirstByRuleName(String ruleName);

    Optional<List<RuleEntity>> findAllByOrderByRuleName();

    @Query("SELECT DISTINCT a.ruleName FROM RuleEntity a")
    Optional<List<String>> findDistinctRuleNames();

    Optional<List<RuleEntity>> findAllByRuleIdIn(List<String> ruleIds);
}
