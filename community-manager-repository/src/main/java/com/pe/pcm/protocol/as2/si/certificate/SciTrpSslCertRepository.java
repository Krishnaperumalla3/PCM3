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

package com.pe.pcm.protocol.as2.si.certificate;

import com.pe.pcm.protocol.as2.si.certificate.entity.SciTrpSslCertEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author Chenchu Kiran.
 */
public interface SciTrpSslCertRepository extends CrudRepository<SciTrpSslCertEntity, String> {


    Optional<SciTrpSslCertEntity> findFirstByTransportIdAndKeyCertId(String transportId, String keyCertId);

    Optional<List<SciTrpSslCertEntity>> findAllByTransportIdAndKeyCertIdIn(String transportId, List<String> keyCertIds);

    @Modifying
    @Query("delete from SciTrpSslCertEntity trp where trp.transportId = :transportId")
    void deleteAllByTransportId(@Param("transportId") String transportId);

    Optional<List<SciTrpSslCertEntity>> findAllByTransportId(String transportId);
}