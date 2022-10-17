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

package com.pe.pcm.sterling.sfg.rct;

import com.pe.pcm.sterling.mailbox.RoutingChannelTempModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Chenchu Kiran.
 */
@Service
public class FgRctProducerFileService {
    private final FgProdFileStructureRepository fgProdFileStructureRepository;
    private final FgProdFileLayerTypeRepository fgProdFileLayerTypeRepository;
    private final FgProdFileLayerRepository fgProdFileLayerRepository;
    private final FgProdFileLayerPrmTypeRepository fgProdFileLayerPrmTypeRepository;
    private final FgProdFileLayerPrmRepository fgProdFileLayerPrmRepository;

    @Autowired
    public FgRctProducerFileService(FgProdFileStructureRepository fgProdFileStructureRepository, FgProdFileLayerTypeRepository fgProdFileLayerTypeRepository, FgProdFileLayerRepository fgProdFileLayerRepository, FgProdFileLayerPrmTypeRepository fgProdFileLayerPrmTypeRepository, FgProdFileLayerPrmRepository fgProdFileLayerPrmRepository) {
        this.fgProdFileStructureRepository = fgProdFileStructureRepository;
        this.fgProdFileLayerTypeRepository = fgProdFileLayerTypeRepository;
        this.fgProdFileLayerRepository = fgProdFileLayerRepository;
        this.fgProdFileLayerPrmTypeRepository = fgProdFileLayerPrmTypeRepository;
        this.fgProdFileLayerPrmRepository = fgProdFileLayerPrmRepository;
    }

    public void save(RoutingChannelTempModel routingChannelTempModel, String routingChanTempKey, boolean isUpdate){

    }

    public void delete(String routingChanTempKey){

    }
}
