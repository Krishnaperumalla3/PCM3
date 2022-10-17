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

package com.pe.pcm.apiconnecct;

import com.pe.pcm.apiconnecct.entity.APIProxyEndpointEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author Kiran Reddy.
 */
public interface APIProxyEndpointRepository extends CrudRepository<APIProxyEndpointEntity, String>,
        PagingAndSortingRepository<APIProxyEndpointEntity, String>, JpaSpecificationExecutor<APIProxyEndpointEntity> {

    Optional<APIProxyEndpointEntity> findFirstByApiNameAndMethodName(String apiName, String methodName);

    @Query("SELECT DISTINCT apiName FROM APIProxyEndpointEntity")
    List<String> findAllByApiNameUnique();

    @Query("SELECT DISTINCT pe.methodName FROM APIProxyEndpointEntity pe where pe.apiName = :apiName")
    List<String> findAllMethodNamesByApiName(@Param("apiName") String apiName);
}
