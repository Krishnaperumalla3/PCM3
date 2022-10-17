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

package com.pe.pcm.application;

import com.pe.pcm.application.entity.ApplicationEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends CrudRepository<ApplicationEntity, String>,
        PagingAndSortingRepository<ApplicationEntity, String>, JpaSpecificationExecutor<ApplicationEntity> {

    Optional<ApplicationEntity> findByApplicationId(String applicationId);

    Optional<List<ApplicationEntity>> findAllByOrderByApplicationNameAsc();

    Optional<List<ApplicationEntity>> findByApplicationName(String applicationName);

    Optional<List<ApplicationEntity>> findAllByApplicationNameContainingIgnoreCaseOrderByApplicationName(String applicationName);

    Optional<List<ApplicationEntity>> findAllByAppIntegrationProtocolOrderByApplicationName(String protocol);

}
