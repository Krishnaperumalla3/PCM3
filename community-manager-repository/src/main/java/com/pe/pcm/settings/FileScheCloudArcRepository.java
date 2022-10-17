package com.pe.pcm.settings;

import com.pe.pcm.settings.entity.FileScheCloudArcEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Kiran Reddy.
 */
public interface FileScheCloudArcRepository extends CrudRepository<FileScheCloudArcEntity, String> {
    List<FileScheCloudArcEntity> findAllByTraSchRef(String traSchRef);

    @Modifying
    @Query("delete from FileScheCloudArcEntity mg where mg.traSchRef = :traSchRef")
    void deleteAllByTraSchRef(@Param("traSchRef") String traSchRef);
}
