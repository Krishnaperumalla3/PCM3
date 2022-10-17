package com.pe.pcm.schedulers;

import com.pe.pcm.logout.UserTokenExpRepository;
import com.pe.pcm.properties.CMJwtProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

/**
 * @author Kiran Reddy.
 */
@Component
public class TokenRemoveScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenRemoveScheduler.class);
    private final CMJwtProperties cmJwtProperties;
    private final UserTokenExpRepository userTokenExpRepository;

    @Autowired
    public TokenRemoveScheduler(CMJwtProperties cmJwtProperties,
                                UserTokenExpRepository userTokenExpRepository) {
        this.cmJwtProperties = cmJwtProperties;
        this.userTokenExpRepository = userTokenExpRepository;
    }

    @Transactional
    @Scheduled(cron = "${jwt.cron.expression}")
    public void cleanTokens() {
        LOGGER.info("START: Cleaning Expired tokens from Database");
        userTokenExpRepository.findAllByCreatedDateBefore(new java.sql.Timestamp(System.currentTimeMillis() - (cmJwtProperties.getSessionExpire() * 60 * 1000) - 60000))
                .ifPresent(userTokenExpRepository::deleteAll);
        LOGGER.info("END: Cleaning Expired tokens from Database");
    }
}
