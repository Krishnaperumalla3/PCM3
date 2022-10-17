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

package com.pe.pcm.protocol.si;

import com.pe.pcm.pem.PemProfileSearchModel;
import com.pe.pcm.protocol.RemoteProfileModel;
import com.pe.pcm.protocol.as2.si.SftpProfileRepository;
import com.pe.pcm.protocol.as2.si.entity.SftpProfileEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;
import static com.pe.pcm.miscellaneous.CommonQueryPredicate.getPredicate;
import static com.pe.pcm.utils.CommonFunctions.*;
import static com.pe.pcm.utils.PCMConstants.PRAGMA_EDGE_S;
import static java.lang.Boolean.TRUE;

/**
 * @author Chenchu Kiran Reddy.
 */
@Service
public class SftpProfileService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SftpProfileService.class);

    private final SftpProfileRepository sftpProfileRepository;

    @Autowired
    public SftpProfileService(SftpProfileRepository sftpProfileRepository) {
        this.sftpProfileRepository = sftpProfileRepository;
    }

    public void save(RemoteProfileModel remoteProfileModel, String profileId) {
        LOGGER.info("Create/Update SftpProfileEntity");
        LOGGER.info("Profile Id : {}", profileId);
        sftpProfileRepository.save(serialize.apply(remoteProfileModel).setProfileId(profileId));
        //This is for DB2 and SQL server , where below two columns are being added as empty
        if (!isNotNull(remoteProfileModel.getLocalPortRange()) || !isNotNull(remoteProfileModel.getCharacterEncoding())) {
            if (!isNotNull(remoteProfileModel.getLocalPortRange()) && !isNotNull(remoteProfileModel.getCharacterEncoding())) {
                sftpProfileRepository.updateLocalPortRangeAndCharacterEncodingToNull(profileId);
            } else if (!isNotNull(remoteProfileModel.getLocalPortRange())) {
                sftpProfileRepository.updateLocalPortRangeToNull(profileId);
            } else if (!isNotNull(remoteProfileModel.getCharacterEncoding())) {
                sftpProfileRepository.updateCharacterEncoding(profileId);
            }
        }
    }

    public Optional<SftpProfileEntity> getByName(String profileName) {
        return sftpProfileRepository.findFirstByName(profileName);
    }

    public void getByName(RemoteProfileModel remoteProfileModel) {
        deserialize.apply(findFirstByName(remoteProfileModel.getCustomProfileName()), remoteProfileModel);
    }

    private SftpProfileEntity findFirstByName(String profileName) {
        LOGGER.info("SFTP Profile Name : {}", profileName);
        return getByName(profileName).orElseThrow(() -> internalServerError("SftpProfile not found"));
    }

    public void delete(SftpProfileEntity sftpProfileEntity) {
        LOGGER.info("Delete SftpProfileEntity.");
        sftpProfileRepository.delete(sftpProfileEntity);
    }

    public Optional<List<SftpProfileEntity>> findAllByNameContainingIgnoreCase(String profileName) {
        return sftpProfileRepository.findAllByNameContainingIgnoreCase(profileName);
    }

    public Optional<List<SftpProfileEntity>> findAllByName(String profileName) {
        return sftpProfileRepository.findAllByName(profileName);
    }

    public List<SftpProfileEntity> findAllRemoteSftpProfileByNames(List<PemProfileSearchModel> profileModels) {
        return sftpProfileRepository.findAll(getSpecification(profileModels))
                .stream()
                .sorted(Comparator.comparing(SftpProfileEntity::getName))
                .collect(Collectors.toList());
    }

    private Specification<SftpProfileEntity> getSpecification(List<PemProfileSearchModel> profileModels) {

        if (profileModels.isEmpty() || !isNotNull(profileModels.get(0).getProfileName())) {
            throw internalServerError("Please provide at least one value to fetch the SSH Profiles.");
        }
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            profileModels.forEach(profile ->
                    getPredicate(root, cb, predicates, profile.getProfileName(), "name", profile.getLike() != null ? profile.getLike() : TRUE)
            );
            return cb.or(predicates.toArray(new Predicate[0]));
        };
    }


    private final BiFunction<SftpProfileEntity, RemoteProfileModel, RemoteProfileModel> deserialize = (sftpProfileEntity, remoteProfileModel) ->
            remoteProfileModel.setSiProfileId(sftpProfileEntity.getProfileId())
                    .setConnectionRetryCount(String.valueOf(sftpProfileEntity.getConnectionRetries()))
                    .setResponseTimeOut(String.valueOf(sftpProfileEntity.getResponseTimeout()))
                    .setRemoteHost(sftpProfileEntity.getRemoteHost())
                    .setRemotePort(String.valueOf(sftpProfileEntity.getRemotePort()))
                    .setUserIdentityKey(sftpProfileEntity.getUserIdentityKeyName())
                    .setUserName(sftpProfileEntity.getRemoteUser())
                    .setPassword(PRAGMA_EDGE_S)
                    .setRetryDelay(String.valueOf(sftpProfileEntity.getRetryDelay()))
                    .setPreferredCipher(sftpProfileEntity.getPreferredCipher())
                    .setPreferredMacAlgorithm(sftpProfileEntity.getPreferredMac())
                    .setPreferredAuthenticationType(sftpProfileEntity.getPreferredAuth())
                    .setCompression(sftpProfileEntity.getCompressBool())
                    .setLocalPortRange(sftpProfileEntity.getLocalPortRange())
                    .setCharacterEncoding(sftpProfileEntity.getCharacterEncoding());


    private final Function<RemoteProfileModel, SftpProfileEntity> serialize = remoteFtpModel ->
            new SftpProfileEntity().setName(remoteFtpModel.getCustomProfileName())
                    .setConnectionRetries(convertStringToIntegerThrowError.apply(remoteFtpModel.getConnectionRetryCount(), "connectionRetryCount"))
                    .setResponseTimeout(convertStringToIntegerThrowError.apply(remoteFtpModel.getResponseTimeOut(), "responseTimeOut"))
                    .setRemoteHost(isNullThrowError.apply(remoteFtpModel.getRemoteHost(), "remoteHost"))
                    .setRemotePort(convertStringToIntegerThrowError.apply(isNullThrowError.apply(remoteFtpModel.getRemotePort(), "remotePort"), "remotePort"))
                    .setRemoteUser(isNullThrowError.apply(remoteFtpModel.getUserName(), "userName"))
                    .setRemotePassword(remoteFtpModel.getPassword())
                    .setUserIdentityKeyName(remoteFtpModel.getUserIdentityKey())
                    .setRetryDelay(convertStringToIntegerThrowError.apply(remoteFtpModel.getRetryDelay(), "RetryDelay"))
                    .setPreferredCipher(isNotNull(remoteFtpModel.getPreferredCipher()) ? remoteFtpModel.getPreferredCipher().toLowerCase() : "aes128-cbc")
                    .setPreferredMac(isNotNull(remoteFtpModel.getPreferredMacAlgorithm()) ? remoteFtpModel.getPreferredMacAlgorithm().toLowerCase() : "hmac-md5")
                    .setPreferredAuth(remoteFtpModel.getPreferredAuthenticationType().toLowerCase())
                    .setCompressBool(isNotNull(remoteFtpModel.getCompression()) ? remoteFtpModel.getCompression().toLowerCase() : "none")
                    .setLocalPortRange(remoteFtpModel.getLocalPortRange())
                    .setChangeDirectory(remoteFtpModel.getSubscriberType().equals("TP") ? remoteFtpModel.getInDirectory() : remoteFtpModel.getOutDirectory())
                    .setCharacterEncoding(remoteFtpModel.getCharacterEncoding());

}
