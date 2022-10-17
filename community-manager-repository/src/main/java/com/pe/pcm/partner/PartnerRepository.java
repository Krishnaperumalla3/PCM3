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

package com.pe.pcm.partner;

import com.pe.pcm.partner.entity.PartnerEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Chenchu Kiran.
 */
public interface PartnerRepository extends CrudRepository<PartnerEntity, String>,
        PagingAndSortingRepository<PartnerEntity, String>, JpaSpecificationExecutor<PartnerEntity> {

    Optional<List<PartnerEntity>> findByTpName(String tpName);

    Optional<PartnerEntity> findByTpId(String partnerId);

    Optional<List<PartnerEntity>> findAllByOrderByTpNameAsc();

    Optional<List<PartnerEntity>> findAllByIsProtocolHubInfoAndTpProtocol(String isProtocolHubInfo, String tpProtocol);

    Optional<List<PartnerEntity>> findAllByTpNameContainingIgnoreCaseOrderByTpName(String tpName);

    Optional<List<PartnerEntity>> findAllByTpNameAndTpId(String tpName, String tpId);

    Optional<List<PartnerEntity>> findAllByTpProtocolOrderByTpName(String protocol);

    Optional<List<PartnerEntity>> findAllByTpProtocolAndIsProtocolHubInfoOrderByTpName(String protocol, String isHubInfo);

    Optional<List<PartnerEntity>> findAllByPkIdIn(List<String> tpNamesList);

}
