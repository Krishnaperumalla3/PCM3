/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://pragmaedge.com/licenseagreement
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pe.pcm.user;

import com.pe.pcm.user.entity.UserAttemptsEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Optional;

/**
 * @author Kiran Reddy.
 */
@Service
public class UserAttemptsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserAttemptsService.class);

    private final UserAttemptsRepository userAttemptsRepository;

    @Autowired
    public UserAttemptsService(UserAttemptsRepository userAttemptsRepository) {
        this.userAttemptsRepository = userAttemptsRepository;
    }

    public void save(UserAttemptsEntity userAttemptsEntity) {
        userAttemptsRepository.save(userAttemptsEntity);
    }

    @Transactional
    public void updateFailAttempts(String username) {
        Optional<UserAttemptsEntity> userAttemptsEntityOptional = userAttemptsRepository.findByUsername(username);
        if (userAttemptsEntityOptional.isPresent()) {
            userAttemptsEntityOptional.get().setAttempts(userAttemptsEntityOptional.get().getAttempts() + 1)
                    .setLastModified(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        } else {
            userAttemptsRepository.save(new UserAttemptsEntity()
                    .setUsername(username)
                    .setAttempts(1)
                    .setLastModified(new Timestamp(Calendar.getInstance().getTimeInMillis()))
            );
        }
    }

    public void resetFailAttempts(String username) {
        Optional<UserAttemptsEntity> userAttemptsEntityOptional = userAttemptsRepository.findByUsername(username);
        if (userAttemptsEntityOptional.isPresent()) {
            userAttemptsEntityOptional.get().setAttempts(0)
                    .setLastModified(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        } else {
            userAttemptsRepository.save(new UserAttemptsEntity()
                    .setUsername(username)
                    .setAttempts(0)
                    .setLastModified(new Timestamp(Calendar.getInstance().getTimeInMillis()))
            );
        }
    }

    public void resetFailAttempts(Optional<UserAttemptsEntity> userAttemptsEntityOptional, String username) {
        if (userAttemptsEntityOptional.isPresent()) {
            userAttemptsEntityOptional.get().setAttempts(0);
        } else {
            userAttemptsRepository.save(new UserAttemptsEntity()
                    .setUsername(username)
                    .setAttempts(0)
                    .setLastModified(new Timestamp(Calendar.getInstance().getTimeInMillis()))
            );
        }
    }

    @Transactional
    public boolean autoResetFailAttempts(UserAttemptsEntity userAttemptsEntity, int maxAgeToUnlock) {
        int maxAge = maxAgeToUnlock * 60 * 1000;
        Long currentTime = Calendar.getInstance().getTimeInMillis();
        Long lastAttemptAge = userAttemptsEntity.getLastModified().getTime();
        if ((currentTime - lastAttemptAge) >= maxAge) {
            userAttemptsRepository.save(userAttemptsEntity.setAttempts(0).setLastModified(new Timestamp(Calendar.getInstance().getTimeInMillis())));
            LOGGER.debug("Login attempts reset to 0");
            return true;
        } else {
            LOGGER.debug("Login attempts reset skipped");
        }
        return false;
    }

    public Optional<UserAttemptsEntity> getUserAttempts(String username) {
        return userAttemptsRepository.findByUsername(username);
    }

}
