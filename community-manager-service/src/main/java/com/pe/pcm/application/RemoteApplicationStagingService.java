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

import com.pe.pcm.application.entity.RemoteApplicationStagingEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Chenchu Kiran.
 */
@Service
public class RemoteApplicationStagingService {

    private RemoteApplicationStagingRepository remoteApplicationStagingRepository;

    @Autowired
    public RemoteApplicationStagingService(RemoteApplicationStagingRepository remoteApplicationStagingRepository) {
        this.remoteApplicationStagingRepository = remoteApplicationStagingRepository;
    }

    public List<RemoteApplicationStagingEntity> findAllRemoteApplicationProfiles() {
        return remoteApplicationStagingRepository.findAllByOrderByApplicationNameAsc().orElse(new ArrayList<>());
    }

    @Transactional
    public void delete(RemoteApplicationStagingEntity remoteApplicationStagingEntity) {
        remoteApplicationStagingRepository.delete(remoteApplicationStagingEntity);
    }
}

