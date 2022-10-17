package com.pe.pcm.logout;

import com.pe.pcm.logout.entity.UserTokenExpEntity;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * @author Kiran Reddy.
 */
public interface UserTokenExpRepository extends CrudRepository<UserTokenExpEntity, Long> {

    Optional<List<UserTokenExpEntity>> findAllByUserid(String userid);

    Optional<List<UserTokenExpEntity>> findAllByCreatedDateBefore(Timestamp createdDate);
}
