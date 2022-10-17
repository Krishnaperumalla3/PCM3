package com.pe.pcm.transactionnames;

import com.pe.pcm.poolinginterval.PoolingIntervalModel;
import com.pe.pcm.poolinginterval.entity.PoolingIntervalEntity;
import com.pe.pcm.transactionnames.entity.TransactionNamesEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author Shameer.v.
 */
@Service
public class TransactionNamesService {

    private final TransactionNamesRepository transactionNamesRepository;

    @Autowired
    public TransactionNamesService(TransactionNamesRepository transactionNamesRepository) {
        this.transactionNamesRepository = transactionNamesRepository;
    }

    @Transactional
    public void save(List<String> transactionNames) {
        transactionNamesRepository.deleteAll();
        List<TransactionNamesEntity> transactionNamesEntities = new ArrayList<>();
        IntStream.range(0, transactionNames.size()).forEach(index -> {
            TransactionNamesEntity transactionNamesEntity = new TransactionNamesEntity();
            transactionNamesEntity.setTransactionName(transactionNames.get(index));
            transactionNamesEntity.setSeq(index + 1);
            transactionNamesEntities.add(transactionNamesEntity);
        });
        transactionNamesRepository.saveAll(transactionNamesEntities);
    }

    public List<String> get() {
        return StreamSupport.stream(transactionNamesRepository.findAll().spliterator(),false)
                .map(TransactionNamesEntity::getTransactionName).collect(Collectors.toList());
    }
}
