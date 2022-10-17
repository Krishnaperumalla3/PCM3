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

package com.pe.pcm.login;

import com.pe.pcm.login.entity.SoUsersEntity;
import com.pe.pcm.login.entity.YfsUserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Optional;

import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;


/**
 * @author Kiran Reddy.
 */
@Service
public class CommunityManagerLoginService {

    private static final Logger logger = LoggerFactory.getLogger(CommunityManagerLoginService.class);

    private final SoUsersRepository soUsersRepository;
    private final YfsUserRepository yfsUserRepository;


    @Autowired
    public CommunityManagerLoginService(SoUsersRepository soUsersRepository, YfsUserRepository yfsUserRepository) {
        this.soUsersRepository = soUsersRepository;
        this.yfsUserRepository = yfsUserRepository;
    }

    Optional<SoUsersEntity> getPcmUser(String username) {
        logger.debug("In CommunityManagerLoginService, getPcmUser Method");
        return Optional.of(soUsersRepository.findById(username)
                .filter(soUsersEntity -> soUsersEntity.getUserid().equals(username)).orElseThrow(() -> notFound(username + " : user")));
    }

    Optional<SoUsersEntity> getPcmUserNotThrow(String username) {
        logger.debug("In CommunityManagerLoginService, getPcmUserNotThrow Method");
        return soUsersRepository.findById(username);
    }

    Optional<CommunityManagerUserModel> minimal(String login, String role, boolean siUser) {
        logger.debug("In CommunityManagerLoginService, minimal Method");
        if (siUser) {
            Optional<YfsUserEntity> subscriber = getYfsUser(login);
            if (subscriber.isPresent()) {
                return Optional.of(new CommunityManagerUserModel(login, role));
            }
            throw notFound(login + " : user");
        }
        return getPcmUser(login).map(subscriber ->
                new CommunityManagerUserModel()
                        .setUserId(login)
                        .setUserRole(role)
                        .setRoles(Collections.singletonList(role))
                        .setEmail(subscriber.getEmail())
                        .setUserName(StringUtils.capitalize(subscriber.getFirstName()))
        );
    }

    Optional<YfsUserEntity> getYfsUser(String userName) {
        logger.debug("In CommunityManagerLoginService, getYfsUser Method");
        return yfsUserRepository.findFirstByUsername(userName);
    }

//    @Transactional
//    void updateLockAndAttempts(String userName, int lock, int unSuccessfulAttempts) {
//        soUsersRepository.findById(userName).ifPresent(soUsersEntity -> {
//            soUsersEntity.set
//        });
//    }


}
