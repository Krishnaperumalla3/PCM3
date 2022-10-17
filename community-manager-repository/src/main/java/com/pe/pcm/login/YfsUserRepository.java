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

package com.pe.pcm.login;

import com.pe.pcm.login.entity.YfsUserEntity;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * @author Chenchu Kiran.
 */
public interface YfsUserRepository extends CrudRepository<YfsUserEntity, String> {

    Optional<YfsUserEntity> findFirstByUsername(String userName);

    Optional<List<YfsUserEntity>> findAllByUsernameIn(List<String> userNamesList);

    Optional<List<YfsUserEntity>> findAllByUsernameContainingIgnoreCaseOrderByUsername(String userName);

    Optional<List<YfsUserEntity>> findAllByUsernameOrderByUsername(String userName);

    List<YfsUserEntity> findAllByOrganizationKey(String organizationKey);

    List<YfsUserEntity> findAllByOrderByLoginid();

    Optional<List<YfsUserEntity>> findAllByUsernameInAndPwdlastchangedonBefore(List<String> userNamesList, Timestamp timestamp);

    void deleteAllByUsername(String userName);

    Optional<YfsUserEntity> findFirstByOrganizationKey(String organizationKey);


}
