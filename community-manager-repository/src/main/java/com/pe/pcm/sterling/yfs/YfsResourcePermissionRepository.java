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

package com.pe.pcm.sterling.yfs;

import com.pe.pcm.sterling.yfs.entity.YfsResourcePermissionEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author Chenchu Kiran.
 */
public interface YfsResourcePermissionRepository extends CrudRepository<YfsResourcePermissionEntity, String> {

    Optional<List<YfsResourcePermissionEntity>> findAllByUserKey(String userKey);

    @Modifying
    @Query("delete from YfsResourcePermissionEntity rp where rp.userKey= :userKey")
    void deleteAllByUserKey(@Param("userKey") String userKey);

    List<YfsResourcePermissionEntity> findAllByUserKeyAndResourceKeyIn(String userKey, List<String> resourcePermissionKeys);

    Optional<YfsResourcePermissionEntity> findFirstByResourceKeyAndUserKey(String resourceKey, String userKey);

}
