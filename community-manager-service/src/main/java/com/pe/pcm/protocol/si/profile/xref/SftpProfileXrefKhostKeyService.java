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

package com.pe.pcm.protocol.si.profile.xref;

import com.pe.pcm.common.CommunityManagerNameModel;
import com.pe.pcm.protocol.as2.si.SftpProfileXrefKhostKeyRepository;
import com.pe.pcm.protocol.as2.si.entity.SftpProfileXrefKhostKeyEntity;
import com.pe.pcm.protocol.as2.si.entity.identity.SftpProfileXrefKhostKeyIdentity;
import com.pe.pcm.utils.CommonFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * @author Chenchu Kiran.
 */
@Service
public class SftpProfileXrefKhostKeyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SftpProfileXrefKhostKeyService.class);

    private final SftpProfileXrefKhostKeyRepository sftpProfileXrefKhostKeyRepository;

    @Autowired
    public SftpProfileXrefKhostKeyService(SftpProfileXrefKhostKeyRepository sftpProfileXrefKhostKeyRepository) {
        this.sftpProfileXrefKhostKeyRepository = sftpProfileXrefKhostKeyRepository;
    }

    public void saveAll(String sftpProfileId, List<CommunityManagerNameModel> knownHostKeys) {
        LOGGER.info("Delete SftpProfileXrefKhostKeyEntities.");
        deleteAllByProfileId(sftpProfileId);
        LOGGER.info("Create All SftpProfileXrefKhostKeyEntity.");
        sftpProfileXrefKhostKeyRepository.saveAll(serialize.apply(sftpProfileId, knownHostKeys));
    }

    public List<SftpProfileXrefKhostKeyEntity> findAllByProfileId(String profileId) {
        return sftpProfileXrefKhostKeyRepository.findAllBySftpProfileXrefKhostKeyIdentityProfileId(profileId);
    }

    public void deleteAllByProfileId(String profileId) {
        LOGGER.info("Delete All SftpProfileXrefKhostKeyEntities.");
        sftpProfileXrefKhostKeyRepository.deleteAllByProfileId(profileId);
    }

    private static final BiFunction<String, List<CommunityManagerNameModel>, List<SftpProfileXrefKhostKeyEntity>> serialize = (profileId, knownHostKeysObject) ->
            knownHostKeysObject.stream()
                    .map(CommunityManagerNameModel::getName)
                    .filter(CommonFunctions::isNotNull)
                    .map(knownHostKey ->
                            new SftpProfileXrefKhostKeyEntity()
                                    .setSftpProfileXrefKhostKeyIdentity(new SftpProfileXrefKhostKeyIdentity().setProfileId(profileId)
                                            .setKhostKeyId(knownHostKey)))
                    .collect(Collectors.toList());

}
