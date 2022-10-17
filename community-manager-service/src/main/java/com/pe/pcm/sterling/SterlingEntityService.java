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

package com.pe.pcm.sterling;

import com.pe.pcm.common.CommunityManagerNameModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Kiran Reddy.
 */
@Service
public class SterlingEntityService {

    private final WfdRepository wfdRepository;
    private final MapRepository mapRepository;

    @Autowired
    public SterlingEntityService(WfdRepository wfdRepository, MapRepository mapRepository) {
        this.wfdRepository = wfdRepository;
        this.mapRepository = mapRepository;
    }

    public List<CommunityManagerNameModel> getBpList() {
        return getAllBPs().stream().map(CommunityManagerNameModel::new).collect(Collectors.toList());
    }

    public List<CommunityManagerNameModel> getMapList() {
        return getAllMaps().stream().map(CommunityManagerNameModel::new).collect(Collectors.toList());
    }

    private List<String> getAllBPs() {
        return wfdRepository.findDistinctOrderByName().orElse(new ArrayList<>());
    }

    private List<String> getAllMaps() {
        return mapRepository.findAllByOrderByMapIdentity().orElse(new ArrayList<>());
    }

}
