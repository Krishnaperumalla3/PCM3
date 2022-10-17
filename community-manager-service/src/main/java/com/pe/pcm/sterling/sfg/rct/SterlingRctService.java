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

import com.pe.pcm.sterling.function.RctFunctions;
import com.pe.pcm.sterling.mailbox.RoutingChannelTempModel;
import com.pe.pcm.sterling.sfg.rct.entity.FgRoutChanTmplEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.pe.pcm.exception.GlobalExceptionHandler.conflict;
import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;
import static com.pe.pcm.utils.KeyGeneratorUtil.getPrimaryKey;

/**
 * @author Chenchu Kiran.
 */
@Service
public class SterlingRctService {

    private final FgRoutChanTmplRepository fgRoutChanTmplRepository;
    private final FgRctConsumerFileService fgRctConsumerFileService;
    private final FgRctProducerFileService fgRctProducerFileService;
    private final FgRctProvisionAndGroupPermissionsService fgRctProvisionAndGroupPermissionsService;

    @Autowired
    public SterlingRctService(FgRoutChanTmplRepository fgRoutChanTmplRepository, FgRctConsumerFileService fgRctConsumerFileService, FgRctProducerFileService fgRctProducerFileService, FgRctProvisionAndGroupPermissionsService fgRctProvisionAndGroupPermissionsService) {
        this.fgRoutChanTmplRepository = fgRoutChanTmplRepository;
        this.fgRctConsumerFileService = fgRctConsumerFileService;
        this.fgRctProducerFileService = fgRctProducerFileService;
        this.fgRctProvisionAndGroupPermissionsService = fgRctProvisionAndGroupPermissionsService;
    }

    @Transactional
    public void create(RoutingChannelTempModel routingChannelTempModel) {
        save(routingChannelTempModel, false);
    }

    @Transactional
    public void update(RoutingChannelTempModel routingChannelTempModel) {
        save(routingChannelTempModel, true);
    }

    public RoutingChannelTempModel get(String templateName) {

        return null;
    }

    @Transactional
    public void delete(String templateNme) {
        fgRoutChanTmplRepository.findFirstByTmplName(templateNme).ifPresent(fgRoutChanTmplEntity -> {
            fgRctProvisionAndGroupPermissionsService.delete(fgRoutChanTmplEntity.getRoutchanTmplKey());
            fgRctProducerFileService.delete(fgRoutChanTmplEntity.getRoutchanTmplKey());
            fgRctConsumerFileService.delete(fgRoutChanTmplEntity.getRoutchanTmplKey());
            fgRoutChanTmplRepository.delete(fgRoutChanTmplEntity);
        });
    }

    private void save(RoutingChannelTempModel routingChannelTempModel, boolean isUpdate) {
        String routChanTmplKey;
        if (isUpdate) {
            //TODO : Here need to check with old name or PkId of template
            Optional<FgRoutChanTmplEntity> fgRoutChanTmplEntityOptional = fgRoutChanTmplRepository.findFirstByTmplName(routingChannelTempModel.getTemplateName());
            if (fgRoutChanTmplEntityOptional.isPresent()) {
                if (!routingChannelTempModel.getTemplateName().equalsIgnoreCase(fgRoutChanTmplEntityOptional.get().getTmplName().trim())) {
                    duplicateCheck(routingChannelTempModel.getTemplateName());
                }
                routChanTmplKey = fgRoutChanTmplEntityOptional.get().getRoutchanTmplKey().trim();
            } else {
                throw internalServerError("FgRoutChanTmplEntity entity not found");
            }
        } else {
            duplicateCheck(routingChannelTempModel.getTemplateName());
            routChanTmplKey = getPrimaryKey.apply("CM-", 20);
        }

        fgRoutChanTmplRepository.save(RctFunctions.convertRctModelToFgRoutChanTmplEntity.apply(routingChannelTempModel, routChanTmplKey));
        fgRctProvisionAndGroupPermissionsService.save(routingChannelTempModel, routChanTmplKey, isUpdate);
        fgRctProducerFileService.save(routingChannelTempModel, routChanTmplKey, isUpdate);
        fgRctConsumerFileService.save(routingChannelTempModel, routChanTmplKey, isUpdate);
    }

    private void duplicateCheck(String templateName) {
        fgRoutChanTmplRepository.findFirstByTmplName(templateName).ifPresent(fgRoutChanTmplEntity -> {
            throw conflict("FgRoutChanTmplEntity entity not found");
        });
    }
}
