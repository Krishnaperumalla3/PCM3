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
import com.pe.pcm.sterling.sfg.rct.entity.FgConsuFileLayerPrmTypeEntity;
import com.pe.pcm.sterling.sfg.rct.entity.FgConsuFileLayerTypeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.pe.pcm.sterling.function.RctFunctions.convertDelChaTemModelToFgConsuFileStructureEntity;
import static com.pe.pcm.sterling.function.RctFunctions.convertDelChaTemModelToFgDelivChanTmplEntity;
import static com.pe.pcm.utils.KeyGeneratorUtil.getPrimaryKey;

/**
 * @author Chenchu Kiran.
 */
@Service
public class FgRctConsumerFileService {

    private final FgDelivChanTmplRepository fgDelivChanTmplRepository;
    private final FgConsuFileStructureRepository fgConsuFileStructureRepository;
    private final FgConsuFileLayerTypeRepository fgConsuFileLayerTypeRepository;
    private final FgConsuFileLayerRepository fgConsuFileLayerRepository;
    private final FgConsuFileLayerPrmTypeRepository fgConsuFileLayerPrmTypeRepository;
    private final FgConsuFileLayerPrmRepository fgConsuFileLayerPrmRepository;

    @Autowired
    public FgRctConsumerFileService(FgDelivChanTmplRepository fgDelivChanTmplRepository, FgConsuFileStructureRepository fgConsuFileStructureRepository, FgConsuFileLayerTypeRepository fgConsuFileLayerTypeRepository, FgConsuFileLayerRepository fgConsuFileLayerRepository, FgConsuFileLayerPrmTypeRepository fgConsuFileLayerPrmTypeRepository, FgConsuFileLayerPrmRepository fgConsuFileLayerPrmRepository) {
        this.fgDelivChanTmplRepository = fgDelivChanTmplRepository;
        this.fgConsuFileStructureRepository = fgConsuFileStructureRepository;
        this.fgConsuFileLayerTypeRepository = fgConsuFileLayerTypeRepository;
        this.fgConsuFileLayerRepository = fgConsuFileLayerRepository;
        this.fgConsuFileLayerPrmTypeRepository = fgConsuFileLayerPrmTypeRepository;
        this.fgConsuFileLayerPrmRepository = fgConsuFileLayerPrmRepository;
    }


    public void save(RoutingChannelTempModel routingChannelTempModel, String routingChanTempKey, boolean isUpdate) {
        if (isUpdate) {
            delete(routingChanTempKey);
        }
        Map<String, FgConsuFileLayerTypeEntity> fgConsuFileLayerTypeEntityMap =
                fgConsuFileLayerTypeRepository.findAllByOrderByCreateuserid()
                        .stream()
                        .collect(Collectors.toMap(FgConsuFileLayerTypeEntity::getLayerType, o -> o));
        List<FgConsuFileLayerPrmTypeEntity> fgConsuFileLayerPrmTypeEntityList = fgConsuFileLayerPrmTypeRepository.findAllByOrderByConsuFlrTypeKeyAscOrdinalAsc();

        routingChannelTempModel.getDeliveryChannelTemplateList().forEach(deliveryChannelTemplateModel -> {
            String delivChanTmplKey = getPrimaryKey.apply("CM-", 20);
            String consumerFstKey = getPrimaryKey.apply("CM-", 20);
            fgDelivChanTmplRepository.save(convertDelChaTemModelToFgDelivChanTmplEntity.apply(
                    deliveryChannelTemplateModel,
                    routingChannelTempModel.getTemplateName(),
                    routingChanTempKey)
                    .setDelivChanTKey(delivChanTmplKey)
            );
            fgConsuFileStructureRepository.save(convertDelChaTemModelToFgConsuFileStructureEntity.apply(delivChanTmplKey, consumerFstKey));
            AtomicInteger atomicInteger = new AtomicInteger(0);
            deliveryChannelTemplateModel.getConsumerFileStructureModelList().forEach(consumerFileStructureModel -> {
                String consumerFlrKey = getPrimaryKey.apply("CM-", 20);
            });
        });
    }

    public void delete(String routingChanTempKey) {
        fgDelivChanTmplRepository.findAllByRoutchanTmplKey(routingChanTempKey).ifPresent(fgDelivChanTmplEntities ->
                fgDelivChanTmplEntities.forEach(fgDelivChanTmplEntity -> {
                    fgConsuFileStructureRepository.findAllByDelivChanTempKey(fgDelivChanTmplEntity.getDelivChanTKey())
                            .ifPresent(fgConsuFileStructureEntities ->
                                    fgConsuFileStructureEntities.forEach(fgConsuFileStructureEntity -> {
                                        fgConsuFileLayerRepository.findAllByConsuFstKey(fgConsuFileStructureEntity.getcFstKey())
                                                .ifPresent(fgConsuFileLayerEntities ->
                                                        fgConsuFileLayerEntities.forEach(fgConsuFileLayerEntity -> {
                                                            fgConsuFileLayerPrmRepository.deleteAllByConsuFlrKey(fgConsuFileLayerEntity.getcFlrKey());
                                                            fgConsuFileLayerRepository.delete(fgConsuFileLayerEntity);
                                                        })
                                                );
                                        fgConsuFileStructureRepository.delete(fgConsuFileStructureEntity);
                                    })
                            );
                    fgDelivChanTmplRepository.delete(fgDelivChanTmplEntity);
                })
        );
    }
}
