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

package com.pe.pcm.sterling;

import com.pe.pcm.certificate.SshUserKeyService;
import com.pe.pcm.common.CommunityManagerNameModel;
import com.pe.pcm.reports.JdbcTemplateComponent;
import com.pe.pcm.sterling.entity.AuthUserXrefSshEntity;
import com.pe.pcm.sterling.entity.identity.AuthUserXrefSshIdentity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;

/**
 * @author Kiran Reddy.
 */
@Service
public class SterlingAuthUserXrefSshService {

    private final AuthUserXrefSshRepository authUserXrefSshRepository;
    private final SshUserKeyService sshUserKeyService;
    private final JdbcTemplateComponent jdbcTemplateComponent;

    private static final Logger LOGGER = LoggerFactory.getLogger(SterlingAuthUserXrefSshService.class);

    @Autowired
    public SterlingAuthUserXrefSshService(AuthUserXrefSshRepository authUserXrefSshRepository, SshUserKeyService sshUserKeyService, JdbcTemplateComponent jdbcTemplateComponent) {
        this.authUserXrefSshRepository = authUserXrefSshRepository;
        this.sshUserKeyService = sshUserKeyService;
        this.jdbcTemplateComponent = jdbcTemplateComponent;
    }

    public void checkKeyAvailability(List<String> authKeyList) {
        LOGGER.info("Keys validation in DB...");
        authKeyList.forEach(this::findKey);
        LOGGER.info("All keys are available in DB");
    }

    private void findKey(String keyName) {
        sshUserKeyService.findFirstByName(keyName).orElseThrow(() -> internalServerError("Authorised User Key NotFound, Key : " + keyName));
    }

    public List<CommunityManagerNameModel> findAllByUser(String userId) {
        LOGGER.info("Retrieving the Existing Keys");
        return findAllByUserId(userId)
                .stream()
                .map(authUserXrefSshEntity -> new CommunityManagerNameModel(authUserXrefSshEntity.getAuthUserXrefSshIdentity().getUserkey()))
                .collect(Collectors.toList());
    }

    private List<AuthUserXrefSshEntity> findAllByUserId(String userId) {
        return authUserXrefSshRepository.findAllByAuthUserXrefSshIdentityUserId(userId).orElse(new ArrayList<>());
    }

    //This is old one we need to clear this testing team confirm if below worked
    public void saveAll(String userId, List<String> authKeysList) {
        if (authKeysList != null && !authKeysList.isEmpty()) {
            LOGGER.info("Final Keys to store : {}", authKeysList);
            LOGGER.info("Deleting Keys, rows effected : {}", deleteByUserId(userId));
            LOGGER.info("Creating Keys, rows effected  : {}", jdbcTemplateComponent.saveAuthUserXrefSsh(userId, authKeysList).length);
            LOGGER.info("Keys saved successfully.");
        } else {
            LOGGER.info("No keys are available to store..!!!");
        }
    }

    public int deleteByUserId(String userId) {
        return jdbcTemplateComponent.deleteAuthUserXrefSsh(userId);
    }

    public void saveAll(String userId, List<String> authKeysIds, boolean mergeUser) {
        if (!authKeysIds.isEmpty() && !authKeysIds.get(0).equals("null")) {
            checkKeyAvailability(authKeysIds);
            if (!mergeUser) {
                jdbcTemplateComponent.deleteAuthUserXrefSsh(userId);
            }
            authKeysIds.forEach(authKey -> authUserXrefSshRepository.save(new AuthUserXrefSshEntity().setAuthUserXrefSshIdentity(new AuthUserXrefSshIdentity().setUserId(userId).setUserkey(authKey))));
        }
    }

}
