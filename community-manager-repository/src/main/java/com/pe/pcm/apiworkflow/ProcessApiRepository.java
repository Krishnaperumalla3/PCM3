package com.pe.pcm.apiworkflow;

import com.pe.pcm.apiworkflow.entity.ProcessApiEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Kiran Reddy.
 */
public interface ProcessApiRepository extends CrudRepository<ProcessApiEntity, String> {

    Optional<ProcessApiEntity> findByProfileId(String s);

    Optional<List<ProcessApiEntity>> findAllByProfileId(String profileId);
}
