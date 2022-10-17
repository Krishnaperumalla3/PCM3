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

import com.pe.pcm.apiconnecct.entity.APIAuthDataEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Kiran Reddy.
 */
@Service
public class APIAuthDataService {
    private static final Logger LOGGER = LoggerFactory.getLogger(APIAuthDataService.class);

    private final APIAuthDataRepository apiAuthDataRepository;

    @Autowired
    public APIAuthDataService(APIAuthDataRepository apiAuthDataRepository) {
        this.apiAuthDataRepository = apiAuthDataRepository;
    }

    public void create() {
        save();
    }

    public void update() {
        save();
    }

    private void save() {
        apiAuthDataRepository.save(new APIAuthDataEntity());
    }

}
