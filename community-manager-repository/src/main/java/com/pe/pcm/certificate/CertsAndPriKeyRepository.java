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

import com.pe.pcm.certificate.entity.CertsAndPriKeyEntity;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Chenchu Kiran.
 */
public interface CertsAndPriKeyRepository extends CrudRepository<CertsAndPriKeyEntity, String> {

    Optional<List<CertsAndPriKeyEntity>> findAllByOrderByNameAsc();

    Optional<CertsAndPriKeyEntity> findFirstByName(String name);

    Optional<List<CertsAndPriKeyEntity>> findAllByNameContainingIgnoreCaseOrderByName(String name);

    Optional<List<CertsAndPriKeyEntity>> findAllByName(String name);

    Optional<List<CertsAndPriKeyEntity>> findAllByNotAfterBefore(Date notAfter);

}
