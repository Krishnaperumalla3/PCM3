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

package com.pe.pcm.sterling.mailbox;

import com.pe.pcm.sterling.mailbox.entity.MbxMailBoxEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MbxMailBoxRepository extends CrudRepository<MbxMailBoxEntity, Integer> {

    Optional<List<MbxMailBoxEntity>> findAllByPath(String path);

    Optional<MbxMailBoxEntity> findFirstByPathUp(String pathUp);

    List<MbxMailBoxEntity> findAllByPathIn(List<String> paths);

    @Query("SELECT coalesce(max(me.mailboxId), 0) FROM MbxMailBoxEntity me")
    Long getMaxMailboxId();


    List<MbxMailBoxEntity> findAllByOrderByPath();
}
