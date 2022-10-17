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

import com.pe.pcm.certificate.entity.CaCertInfoEntity;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Chenchu Kiran.
 */
public interface CaCertInfoRepository extends CrudRepository<CaCertInfoEntity, String> {

    Optional<CaCertInfoEntity> findFirstByName(String certificateName);

    Optional<List<CaCertInfoEntity>> findAllByOrderByNameAsc();

    Optional<List<CaCertInfoEntity>> findAllByName(String certName);

    Optional<List<CaCertInfoEntity>> findAllByNameContainingIgnoreCaseOrderByName(String cartName);

    Optional<List<CaCertInfoEntity>> findAllByNameInAndNotAfterBefore(List<String> certNames, Date notAfter);

    Optional<List<CaCertInfoEntity>> findAllByNameInOrderByName(List<String> certNames);

    Optional<List<CaCertInfoEntity>> findAllByObjectIdInAndNotAfterBefore(List<String> objectNames, Date notAfter);

}
