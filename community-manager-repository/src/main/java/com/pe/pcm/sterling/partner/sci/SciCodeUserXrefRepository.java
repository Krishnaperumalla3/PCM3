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

package com.pe.pcm.sterling.partner.sci;

import com.pe.pcm.sterling.partner.sci.entity.SciCodeUserXrefEntity;
import com.pe.pcm.sterling.partner.sci.entity.identity.SciCodeUserXrefIdentity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Chenchu Kiran.
 */
public interface SciCodeUserXrefRepository extends CrudRepository<SciCodeUserXrefEntity, SciCodeUserXrefIdentity> {

    @Modifying
    @Query("delete from SciCodeUserXrefEntity cu where cu.tpObjectId = :tpObjectId")
    void deleteAllByTpObjectId(@Param("tpObjectId") String tpObjectId);

    List<SciCodeUserXrefEntity> findAllByTpObjectIdIn(List<String> tpObjectIdsList);
}
