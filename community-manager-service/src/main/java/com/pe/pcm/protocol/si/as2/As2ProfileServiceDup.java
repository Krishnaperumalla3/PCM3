/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc.
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

package com.pe.pcm.protocol.si.as2;

import com.pe.pcm.protocol.as2.si.As2ProfileRepository;
import com.pe.pcm.protocol.as2.si.entity.As2Profile;
import com.pe.pcm.sterling.dto.As2ProfileDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * @author Chenchu Kiran.
 */
@Service
public class As2ProfileServiceDup {
    private static final Logger LOGGER = LoggerFactory.getLogger(As2ProfileServiceDup.class);

    private final As2ProfileRepository as2ProfileRepository;

    @Autowired
    public As2ProfileServiceDup(As2ProfileRepository as2ProfileRepository) {
        this.as2ProfileRepository = as2ProfileRepository;
    }

    private final Function<As2ProfileDTO, As2Profile> serialize = as2ProfileDTO ->
            new As2Profile().setProfileId(as2ProfileDTO.getProfileId())
                    .setIsOrg(as2ProfileDTO.getIsOrg())
                    .setHttpClientAdapter(as2ProfileDTO.getHttpClientAdapter());

    public void save(As2ProfileDTO as2ProfileDTO) {
        LOGGER.info("Create/Update As2ProfileEntity.");
        as2ProfileRepository.save(serialize.apply(as2ProfileDTO));
    }

    public void delete(String profileId) {
        LOGGER.info("Delete As2ProfileEntity.");
        as2ProfileRepository.deleteByProfileId(profileId);
    }

}
