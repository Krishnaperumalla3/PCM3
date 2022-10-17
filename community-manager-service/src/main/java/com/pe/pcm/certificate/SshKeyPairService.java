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

import com.pe.pcm.certificate.entity.SshKeyPairEntity;
import com.pe.pcm.utils.CommonFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Chenchu Kiran.
 */
@Service
public class SshKeyPairService {

    private SshKeyPairRepository sshKeyPairRepository;

    @Autowired
    public SshKeyPairService(SshKeyPairRepository sshKeyPairRepository) {
        this.sshKeyPairRepository = sshKeyPairRepository;
    }

    public List<SshKeyPairEntity> findAll() {
        return sshKeyPairRepository.findAllByOrderByNameAsc().orElse(new ArrayList<>());
    }

    public SshKeyPairEntity findByNameNoThrow(String name){
        return CommonFunctions.isNotNull(name) ? sshKeyPairRepository.findFirstByName(name).orElse(new SshKeyPairEntity()) : new SshKeyPairEntity();
    }
}
