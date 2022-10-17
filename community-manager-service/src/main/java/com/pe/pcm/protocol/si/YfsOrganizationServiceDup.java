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

package com.pe.pcm.protocol.si;

import com.pe.pcm.enums.Protocol;
import com.pe.pcm.protocol.as2.si.YfsOrganizationRepositoryDup;
import com.pe.pcm.protocol.as2.si.entity.YfsOrganizationDupEntity;
import com.pe.pcm.sterling.dto.YfsOrganizationDTO;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

import static com.pe.pcm.utils.CommonFunctions.convertBooleanToString;
import static com.pe.pcm.utils.PCMConstants.SPACE;

/**
 * @author Chenchu Kiran Reddy.
 */
@Service
public class YfsOrganizationServiceDup {

    private static final Logger LOGGER = LoggerFactory.getLogger(YfsOrganizationServiceDup.class);

    private final YfsOrganizationRepositoryDup yfsOrganizationRepositoryDup;

    @Autowired
    public YfsOrganizationServiceDup(YfsOrganizationRepositoryDup yfsOrganizationRepositoryDup) {
        this.yfsOrganizationRepositoryDup = yfsOrganizationRepositoryDup;
    }

    private final Function<YfsOrganizationDTO, YfsOrganizationDupEntity> serialize = yfsOrganizationDTO -> {

        YfsOrganizationDupEntity yfsOrganizationEntity = new YfsOrganizationDupEntity();
        String orgKeyOrCode = yfsOrganizationDTO.getOrganizationKeyAndCode().substring(0, Math.min(yfsOrganizationDTO.getOrganizationKeyAndCode().length(), 21));
        List<String> organizationKeys = findAllByOrganizationKey(orgKeyOrCode);
        List<Integer> numbersFromOrgNames = new ArrayList<>();

        if (organizationKeys.isEmpty()) {
            orgKeyOrCode = yfsOrganizationDTO.getOrganizationKeyAndCode().substring(0, Math.min(yfsOrganizationDTO.getOrganizationKeyAndCode().length(), 24));
        } else {
            organizationKeys.forEach(organizationKey -> {
                String num = organizationKey.substring(21).trim();
                if (NumberUtils.isDigits(num)) {
                    numbersFromOrgNames.add(Integer.valueOf(num));
                }
                numbersFromOrgNames.forEach(e -> System.out.println("final elements " + e));
            });
            AtomicReference<Integer> max = new AtomicReference<>(0);
            numbersFromOrgNames.stream().mapToInt(Integer::intValue).max().ifPresent(max::set);
            orgKeyOrCode = yfsOrganizationDTO.getOrganizationKeyAndCode().substring(0, Math.min(yfsOrganizationDTO.getOrganizationKeyAndCode().length(), 21)) + (max.get() + 1);
        }
        yfsOrganizationEntity.setOrganizationKey(orgKeyOrCode)
                .setObjectId(yfsOrganizationDTO.getObjectId())
                .setOrganizationCode(orgKeyOrCode)
                .setCorporateAddressKey(yfsOrganizationDTO.getCorporateAddressKey())
                .setOrganizationName(yfsOrganizationDTO.getOrganizationName()) //Identity Name in SI
                .setIsHubOrganization(convertBooleanToString(yfsOrganizationDTO.getHubInfo()))
                .setIdentifier(yfsOrganizationDTO.getOrganizationIdentifier()) //AS2 Identifier In SI
                .setIsEnterprise("Y")
                //Hardcode value
                .setLocaleCode("en_US_EST")
                .setActivateFlag("Y");
        if (yfsOrganizationDTO.getProtocol().equals(Protocol.AS2)) {
            LOGGER.info("In Organization Service AS2 Profile.");
            yfsOrganizationEntity.setIsEnterprise(SPACE)
                    .setIsHubOrganization(yfsOrganizationDTO.getHubInfo() ? "N" : SPACE)
            .setLocaleCode(SPACE)
            .setExternalObjectId("")
            .setExtendsObjectId("");

        }
        yfsOrganizationEntity.setLockid(yfsOrganizationDTO.getHubInfo() ? 1 : 0);
        return yfsOrganizationEntity;
    };

    public void save(YfsOrganizationDTO yfsOrganizationDTO) {
        LOGGER.info("Create/Update YfsOrganizationEntity");
        yfsOrganizationRepositoryDup.save(serialize.apply(yfsOrganizationDTO));
    }

    public Optional<YfsOrganizationDupEntity> get(String objectId) {
        return yfsOrganizationRepositoryDup.findById(objectId);
    }

    public Optional<YfsOrganizationDupEntity> findFirstByOrganizationName(String organizationName) {
        return yfsOrganizationRepositoryDup.findFirstByOrganizationName(organizationName);
    }

    public List<YfsOrganizationDupEntity> findAllByOrganizationName(String organizationName) {
        return yfsOrganizationRepositoryDup.findAllByOrganizationName(organizationName);
    }

    public void deleteByObjectId(String objectId) {
        LOGGER.info("Delete YfsOrganizationEntity.");
        yfsOrganizationRepositoryDup.deleteAllByObjectId(objectId);
    }

    private List<String> findAllByOrganizationKey(String orgKey) {
        return yfsOrganizationRepositoryDup.findAllByOrganizationKeyContaining(orgKey);
    }

}
