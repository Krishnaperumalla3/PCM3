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

package com.pe.pcm.user;

import com.pe.pcm.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @author Kiran Reddy.
 */
public interface UserRepository extends CrudRepository<UserEntity, String>,
        PagingAndSortingRepository<UserEntity, String>, JpaSpecificationExecutor<UserEntity> {

    Optional<UserEntity> findOneByActivationKey(String key);

    Optional<List<UserEntity>> findAllByEmail(String email);

    Optional<List<UserEntity>> findAllByUseridOrEmailOrExternalId(String userId, String email, String externalId);

    Optional<List<UserEntity>> findAllByExternalId(String externalId);

    Optional<UserEntity> findFirstByUseridAndExternalId(String userid, String externalId);

    @Modifying
    @Query("DELETE FROM UserEntity UE WHERE UE.userid = :userId")
    void deleteByIdModifying(@Param("userId") String userId);
}
