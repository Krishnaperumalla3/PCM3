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

import com.pe.pcm.sterling.entity.CdNodeEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CdNodeRepository extends CrudRepository<CdNodeEntity, String> {

    @Modifying
    @Query("UPDATE CdNodeEntity cd SET cd.maxPnodeSessions =:pnode, cd.maxSnodeSessions = :snode WHERE cd.nodeName =:nodeName")
    void updatePnodeandSnode(@Param("pnode") Integer pnode, @Param("snode") Integer snode, @Param("nodeName") String nodeName);
}
