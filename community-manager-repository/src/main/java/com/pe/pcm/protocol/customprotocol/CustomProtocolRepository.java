package com.pe.pcm.protocol.customprotocol;

import com.pe.pcm.protocol.customprotocol.entity.CustomProtocolEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author Shameer.
 */
public interface CustomProtocolRepository extends CrudRepository<CustomProtocolEntity, String> {

    Optional<CustomProtocolEntity> findBySubscriberId(String subscriberId);
}
