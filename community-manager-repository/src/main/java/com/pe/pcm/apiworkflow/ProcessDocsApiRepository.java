package com.pe.pcm.apiworkflow;

import com.pe.pcm.apiworkflow.entity.ProcessDocsApiEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Kiran Reddy.
 */
public interface ProcessDocsApiRepository extends CrudRepository<ProcessDocsApiEntity, String> {

    Optional<List<ProcessDocsApiEntity>> findAllByProcessRef(String processRef);
}
