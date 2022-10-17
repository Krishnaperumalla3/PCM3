package com.pe.pcm.protocol;


import com.pe.pcm.protocol.customprotocol.CustomProtocolRepository;
import com.pe.pcm.protocol.customprotocol.entity.CustomProtocolEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;
import static com.pe.pcm.protocol.function.ProtocolFunctions.mapperToCustomProtocolEntity;

/**
 * @author Shameer.
 */

@Service
public class CustomProtocolService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RemoteFtpService.class);

    private final CustomProtocolRepository customProtocolRepository;


    @Autowired
    public CustomProtocolService(CustomProtocolRepository customProtocolRepository) {
        this.customProtocolRepository = customProtocolRepository;
    }

    public CustomProtocolEntity saveProtocol(CustomProtocolModel customProtocolModel, String parentPrimaryKey, String childPrimaryKey, String subscriberType) {

        CustomProtocolEntity customProtocolEntity = mapperToCustomProtocolEntity.apply(customProtocolModel);
        customProtocolEntity.setPkId(childPrimaryKey)
                .setSubscriberType(subscriberType)
                .setSubscriberId(parentPrimaryKey);
        return save(customProtocolEntity);
    }

    public CustomProtocolEntity get(String pkId) {
        return customProtocolRepository.findBySubscriberId(pkId).orElseThrow(() -> notFound("Protocol"));
    }

    public void delete(String pkId) {
        customProtocolRepository.findBySubscriberId(pkId).ifPresent(customProtocolRepository::delete);
    }

    private CustomProtocolEntity save(CustomProtocolEntity customProtocolEntity) {
        return customProtocolRepository.save(customProtocolEntity);
    }
}
