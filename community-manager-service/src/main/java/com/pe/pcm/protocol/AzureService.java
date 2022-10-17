package com.pe.pcm.protocol;

import com.pe.pcm.protocol.azure.AzureRepository;
import com.pe.pcm.protocol.azure.entity.AzureEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;
import static com.pe.pcm.protocol.function.ProtocolFunctions.mapperToAzureEntity;

/**
 * @author Kiran Reddy.
 */
@Service
public class AzureService {

    private final AzureRepository azureRepository;

    @Autowired
    public AzureService(AzureRepository azureRepository) {
        this.azureRepository = azureRepository;
    }

    public AzureEntity save(AzureEntity azureEntity) {
        return azureRepository.save(azureEntity);
    }

    public AzureEntity get(String pkId) {
        return azureRepository.findBySubscriberId(pkId).orElseThrow(() -> notFound("Protocol"));
    }

    public void delete(String pkId) {
        azureRepository.findBySubscriberId(pkId).ifPresent(azureRepository::delete);
    }

    public AzureEntity saveProtocol(AzureModel azureModel, String parentPrimaryKey, String childPrimaryKey, String subscriberType) {
        AzureEntity azureEntity = mapperToAzureEntity.apply(azureModel);
        azureEntity.setPkId(childPrimaryKey)
                .setSubscriberType(subscriberType)
                .setSubscriberId(parentPrimaryKey);
        return save(azureEntity);
    }

}
