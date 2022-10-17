package com.pe.pcm.apiworkflow;

import com.pe.pcm.apiworkflow.entity.ProcessApiRuleEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Kiran Reddy.
 */
public interface ProcessRulesApiRepository extends CrudRepository<ProcessApiRuleEntity, String> {

    Optional<List<ProcessApiRuleEntity>> findAllByPkIdIn(List<String> pkIds);

    Integer deleteAllByPkIdIn(List<String> pkIds);
}
