package com.pe.pcm.protocol.azure;

import com.pe.pcm.protocol.azure.entity.AzureEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author Kiran Reddy.
 */
public interface AzureRepository extends CrudRepository<AzureEntity, String> {

    Optional<AzureEntity> findBySubscriberId(String subscriberId);
}
