package com.pe.pcm.user;

import com.pe.pcm.user.entity.RoleEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Kiran Reddy.
 */
public interface RoleRepository extends CrudRepository<RoleEntity, Long> {
    List<RoleEntity> findAllByOrderById();
}
