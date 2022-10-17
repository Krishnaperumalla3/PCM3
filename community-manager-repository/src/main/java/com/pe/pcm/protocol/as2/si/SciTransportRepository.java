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

import com.pe.pcm.protocol.as2.si.entity.SciTransportEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author Kiran Reddy.
 */
public interface SciTransportRepository extends CrudRepository<SciTransportEntity, String> {

    Optional<List<SciTransportEntity>> findByObjectName(String objectName);

    Optional<List<SciTransportEntity>> findAllByObjectIdIn(List<String> objectNames);

    Optional<SciTransportEntity> findFirstByEntityId(String entityId);

    @Modifying
    @Query("update SciTransportEntity sce set sce.objectName = :objectName where sce.transportKey = :transportKey")
    void updateObjectName(@Param("transportKey") String transportKey, @Param("objectName") String objectName);

}
