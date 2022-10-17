package com.pe.pcm.reports;

import com.pe.pcm.reports.entity.PetpeChargebackSlabs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface TransferInfoChargebackRepository extends JpaRepository<PetpeChargebackSlabs, String>, JpaSpecificationExecutor<PetpeChargebackSlabs>, PagingAndSortingRepository<PetpeChargebackSlabs, String> {

    @Transactional
    @Modifying
    @Query("UPDATE PetpeChargebackSlabs p SET p.active = :active,p.lastupdateDate = CURRENT_TIMESTAMP  WHERE p.active = 1 ")
    void updateSlabs(@Param("active") Double active);

    Optional<PetpeChargebackSlabs> findFirstByActiveOrderByLastupdateDateDesc(Double i);


}
