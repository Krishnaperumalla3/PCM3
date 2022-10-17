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

package com.pe.pcm.protocol.as2.si;

import com.pe.pcm.protocol.as2.si.entity.SftpProfileEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SftpProfileRepository extends CrudRepository<SftpProfileEntity, String>, JpaSpecificationExecutor<SftpProfileEntity> {

    Optional<SftpProfileEntity> findFirstByName(String name);

    Optional<List<SftpProfileEntity>> findAllByNameContainingIgnoreCase(String name);

    Optional<List<SftpProfileEntity>> findAllByName(String name);
    Optional<List<SftpProfileEntity>> findAllByUserIdentityKeyName(String name);

    @Modifying
    @Query("UPDATE SftpProfileEntity sftp SET sftp.localPortRange = NULL, sftp.characterEncoding = NULL WHERE sftp.profileId = :profileId")
    void updateLocalPortRangeAndCharacterEncodingToNull(@Param("profileId") String profileId);

    @Modifying
    @Query("UPDATE SftpProfileEntity sftp SET sftp.localPortRange = NULL WHERE sftp.profileId = :profileId")
    void updateLocalPortRangeToNull(@Param("profileId") String profileId);

    @Modifying
    @Query("UPDATE SftpProfileEntity sftp SET sftp.characterEncoding = NULL WHERE sftp.profileId = :profileId")
    void updateCharacterEncoding(@Param("profileId") String profileId);

}
