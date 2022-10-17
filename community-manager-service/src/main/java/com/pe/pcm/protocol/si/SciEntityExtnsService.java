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

import com.pe.pcm.generator.PrimaryKeyGeneratorService;
import com.pe.pcm.sterling.dto.SfgProfileDetailsDTO;
import com.pe.pcm.sterling.partner.sci.SciEntityExtnsRepository;
import com.pe.pcm.sterling.partner.sci.entity.SciEntityExtnsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.pe.pcm.utils.PCMConstants.STRING_FALSE;
import static com.pe.pcm.utils.PCMConstants.STRING_TRUE;

/**
 * @author Chenchu Kiran.
 */
@Service
public class SciEntityExtnsService {

    private SciEntityExtnsRepository sciEntityExtnsRepository;
    private PrimaryKeyGeneratorService primaryKeyGeneratorService;

    private Map<String, String> extKeys = new LinkedHashMap<>();

    @Autowired
    public SciEntityExtnsService(SciEntityExtnsRepository sciEntityExtnsRepository, PrimaryKeyGeneratorService primaryKeyGeneratorService) {
        this.sciEntityExtnsRepository = sciEntityExtnsRepository;
        this.primaryKeyGeneratorService = primaryKeyGeneratorService;
    }

    public void save(SfgProfileDetailsDTO sfgProfileDetailsDTO) {

        deleteAllByEntityId(sfgProfileDetailsDTO.getTransportEntityKey());

        sfgProfileDetailsDTO.getExtensionKeysMap().putAll(extKeys);
        sfgProfileDetailsDTO.getExtensionKeysMap().forEach((key, value) -> {
                    String val = value;
                    if (sfgProfileDetailsDTO.getCreateProducerProfile() && key.equalsIgnoreCase("DMIROUTE_WILLPRODUCE")) {
                        val = STRING_TRUE.toUpperCase();
                    }
                    if (sfgProfileDetailsDTO.getOnlyProducer() && key.equalsIgnoreCase("DMIROUTE_WILLCONSUME")) {
                        val = STRING_FALSE.toUpperCase();
                    }
                    sciEntityExtnsRepository.save(
                            new SciEntityExtnsEntity().setObjectId(primaryKeyGeneratorService.generatePrimaryKey("", 4))
                                    .setEntityId(sfgProfileDetailsDTO.getTransportEntityKey())
                                    .setExtensionKey(key)
                                    .setExtensionValue(val)
                                    .setObjectVersion("1")
                                    .setObjectState("true")

                    );
                }
        );
    }

    public void deleteAllByEntityId(String entityId) {
        sciEntityExtnsRepository.deleteAllByEntityId(entityId);
    }

    public Map<String, String> getExtKeys(String transportEntityId) {
        return sciEntityExtnsRepository.findAllByEntityId(transportEntityId)
                .stream()
                .collect(Collectors.toMap(SciEntityExtnsEntity::getExtensionKey, SciEntityExtnsEntity::getExtensionValue));
    }

    @PostConstruct
    private void loadKeys() {
        extKeys.put("SFTPSCPENABLED", STRING_FALSE.toUpperCase());
        extKeys.put("PGP_C_ENCRYPT", "no");
        extKeys.put("DMIROUTE_WILLCONSUME", STRING_TRUE.toUpperCase());
        extKeys.put("DMIROUTE_WILLPRODUCE", STRING_FALSE.toUpperCase());
        extKeys.put("ENCRYPTIONMETHOD", "PGPPK");
        extKeys.put("AUTHORIZEDUSERKEYENABLED", STRING_FALSE.toUpperCase());
        extKeys.put("CONSUMERCONNECTION", "LISTENCONNECTION");
        extKeys.put("PGP_C_COMP", "no");
        extKeys.put("PGP_C_SIGN", "no");
    }
}
