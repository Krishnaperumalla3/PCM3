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


import com.pe.pcm.certificate.entity.AuthXrefSshEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Shameer.
 */
@Service
public class AuthXrefSshService {

    private final AuthXrefSshRepository authXrefSshRepository;

    @Autowired
    public AuthXrefSshService(AuthXrefSshRepository authXrefSshRepository) {
        this.authXrefSshRepository = authXrefSshRepository;
    }

    public Optional<List<AuthXrefSshEntity>> findByUserkey() {
        return authXrefSshRepository.findAllByOrderByUserIdAsc();
    }
}
