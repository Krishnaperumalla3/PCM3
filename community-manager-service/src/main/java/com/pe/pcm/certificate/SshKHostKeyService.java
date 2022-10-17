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

import com.pe.pcm.certificate.entity.SshKHostKeyEntity;
import com.pe.pcm.utils.CommonFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Chenchu Kiran.
 */
@Service
public class SshKHostKeyService {
    private final SshKHostKeyRepository sshKHostKeyRepository;

    @Autowired
    public SshKHostKeyService(SshKHostKeyRepository sshKHostKeyRepository) {
        this.sshKHostKeyRepository = sshKHostKeyRepository;
    }

    public Optional<List<SshKHostKeyEntity>> getSshKHostKeyList() {
        return sshKHostKeyRepository.findAllByOrderByNameAsc();
    }

    public Optional<List<SshKHostKeyEntity>> getSshKHostKeyListByName(String name, Boolean isLike) {
        return isLike ? sshKHostKeyRepository.findAllByNameContainingIgnoreCaseOrderByNameAsc(name) : sshKHostKeyRepository.findAllByOrderByNameAsc();
    }

    public SshKHostKeyEntity findByNameNoThrow(String name){
        return CommonFunctions.isNotNull(name) ? sshKHostKeyRepository.findByName(name).orElse(new SshKHostKeyEntity()) : new SshKHostKeyEntity();
    }
}
