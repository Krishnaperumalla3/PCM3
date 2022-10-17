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

package com.pe.pcm.certificate;

import com.pe.pcm.certificate.entity.SshUserKeyEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Chenchu Kiran.
 */
public interface SshUserKeyRepository extends CrudRepository<SshUserKeyEntity, String> {

    Optional<List<SshUserKeyEntity>> findAllByNameContainingIgnoreCaseOrderByName(String name);

    Optional<List<SshUserKeyEntity>> findAllByName(String name);

    Optional<List<SshUserKeyEntity>> findAllByOrderByNameAsc();

    Optional<List<SshUserKeyEntity>> findAllByNameInOrderByName(List<String> certNames);

    Optional<SshUserKeyEntity> findFirstByName(String name);

}
