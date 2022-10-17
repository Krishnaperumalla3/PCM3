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

package com.pe.pcm.protocol.remoteftp;

import com.pe.pcm.protocol.remoteftp.entity.RemoteFtpEntity;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * @author Chenchu Kiran.
 */
public interface RemoteFtpRepository extends CrudRepository<RemoteFtpEntity, String> {

    Optional<RemoteFtpEntity> findBySubscriberId(String subscriberId);

    Optional<List<RemoteFtpEntity>> findAllByIsHubInfo(String isHubInfo);

    Optional<List<RemoteFtpEntity>> findAllByProtocolTypeAndIsHubInfo(String protocolType, String isHubInfo);

    Optional<List<RemoteFtpEntity>> findAllByIsHubInfoAndPrfAuthTypeContainingIgnoreCase(String isHubInfo, String prfAuthType);

    Optional<List<RemoteFtpEntity>> findBySubscriberIdIn(List<String> subscriberId);

    Optional<List<RemoteFtpEntity>> findAllBySubscriberIdIn(List<String> subscriberIds);

    Optional<List<RemoteFtpEntity>> findAllByIsHubInfoAndPwdLastChangeDoneBefore(String isHubInfo, Timestamp pwdLastChangesDone);
    Optional<List<RemoteFtpEntity>> findAllByProfileIdIn(List<String> profileIds);

    Optional<RemoteFtpEntity> findFirstByInDirectoryOrOutDirectory(String inDirectory, String outDirectory);

}
