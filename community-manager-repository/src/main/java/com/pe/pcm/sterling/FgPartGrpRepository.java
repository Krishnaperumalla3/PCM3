package com.pe.pcm.sterling;

import com.pe.pcm.sterling.entity.FgPartGrpEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Shameer v.
 */

public interface FgPartGrpRepository extends CrudRepository<FgPartGrpEntity, String> {
    List<FgPartGrpEntity> findAllByOrderByNameAsc();

    List<FgPartGrpEntity> findAllByNameIn(List<String> groupNames);
}
