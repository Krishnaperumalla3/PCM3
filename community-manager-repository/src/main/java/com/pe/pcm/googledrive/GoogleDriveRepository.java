package com.pe.pcm.googledrive;

import com.pe.pcm.googledrive.entity.GoogleDriveEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface GoogleDriveRepository extends JpaRepository<GoogleDriveEntity,String>, JpaSpecificationExecutor<GoogleDriveEntity>, PagingAndSortingRepository<GoogleDriveEntity, String>, CrudRepository<GoogleDriveEntity,String> {
    @Query("SELECT DISTINCT h FROM GoogleDriveEntity h WHERE h.pkId=?1")
    GoogleDriveEntity getClientDetails(String pkId);

    Optional<GoogleDriveEntity> findBySubscriberId(String subscriberId);

    Optional<List<GoogleDriveEntity>> findAllBySubscriberIdIn(List<String> subId);
}
