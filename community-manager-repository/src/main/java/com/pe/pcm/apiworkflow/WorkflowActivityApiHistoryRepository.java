package com.pe.pcm.apiworkflow;

import com.pe.pcm.apiworkflow.entity.WorkflowActivityApiHistoryEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Kiran Reddy.
 */
public interface WorkflowActivityApiHistoryRepository extends CrudRepository<WorkflowActivityApiHistoryEntity, String> {
}
