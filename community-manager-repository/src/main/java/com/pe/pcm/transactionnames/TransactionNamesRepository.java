package com.pe.pcm.transactionnames;

import com.pe.pcm.poolinginterval.entity.PoolingIntervalEntity;
import com.pe.pcm.transactionnames.entity.TransactionNamesEntity;
import org.springframework.data.repository.CrudRepository;

public interface TransactionNamesRepository extends CrudRepository<TransactionNamesEntity, String> {
}
