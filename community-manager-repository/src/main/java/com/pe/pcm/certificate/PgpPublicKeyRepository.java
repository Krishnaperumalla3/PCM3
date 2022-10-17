package com.pe.pcm.certificate;

import com.pe.pcm.certificate.entity.PgpPublicKeyEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PgpPublicKeyRepository extends CrudRepository<PgpPublicKeyEntity, String> {

    Optional<List<PgpPublicKeyEntity>> findAllByNameContainingAndKeyType(String name, String keyType);
}
