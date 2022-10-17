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

package com.pe.pcm.sterling.function;

import com.pe.pcm.functions.TriFunction;
import com.pe.pcm.sterling.mailbox.DeliveryChannelTemplateModel;
import com.pe.pcm.sterling.mailbox.RoutingChannelTempModel;
import com.pe.pcm.sterling.sfg.rct.entity.FgConsuFileStructureEntity;
import com.pe.pcm.sterling.sfg.rct.entity.FgDelivChanTmplEntity;
import com.pe.pcm.sterling.sfg.rct.entity.FgRoutChanTmplEntity;
import com.pe.pcm.utils.CommonFunctions;

import java.util.function.BiFunction;

/**
 * @author Chenchu Kiran.
 */
public class RctFunctions {
    private RctFunctions() {
        //should be private
    }

    public static final BiFunction<RoutingChannelTempModel, String, FgRoutChanTmplEntity> convertRctModelToFgRoutChanTmplEntity = (routingChannelTempModel, routingChanTempKey) -> {
        FgRoutChanTmplEntity fgRoutChanTmplEntity = new FgRoutChanTmplEntity()
                .setRoutchanTmplKey(routingChanTempKey)
                .setTmplName(routingChannelTempModel.getTemplateName())
                .setBpName(routingChannelTempModel.getBpName())
                .setPvMbxPattern(routingChannelTempModel.getProducerMailboxPath())
                .setConsidType(routingChannelTempModel.getConsumerIdentification())
                .setBpConsNameXpath(routingChannelTempModel.getProcessDataElementName())
                .setSubstMode("None");
        fgRoutChanTmplEntity.setLockid(0);
        return fgRoutChanTmplEntity;
    };

    public static final TriFunction<DeliveryChannelTemplateModel, String, String, FgDelivChanTmplEntity> convertDelChaTemModelToFgDelivChanTmplEntity =
            (deliveryChannelTemplateModel, templateName, routingChanTempKey) -> {
                FgDelivChanTmplEntity fgDelivChanTmplEntity = new FgDelivChanTmplEntity()
                        .setRoutchanTmplKey(routingChanTempKey)
                        .setTmplName(templateName)
                        .setPvMbxPattern(deliveryChannelTemplateModel.getConsumerMailboxPath())
                        .setLateCreateMbx(CommonFunctions.convertBooleanToString(deliveryChannelTemplateModel.isCreateMailboxAtRuntime()))
                        .setProtocol(deliveryChannelTemplateModel.isMailboxOrProtocol() ? 1 : 0);
                fgDelivChanTmplEntity.setLockid(0);
                return fgDelivChanTmplEntity;
            };
    public static final BiFunction<String, String, FgConsuFileStructureEntity> convertDelChaTemModelToFgConsuFileStructureEntity =
            (deliveryChanTempKey, consumerFstKey) -> {
                FgConsuFileStructureEntity fgConsuFileStructureEntity = new FgConsuFileStructureEntity()
                        .setcFstKey(consumerFstKey)
                        .setDelivChanTempKey(deliveryChanTempKey);
                fgConsuFileStructureEntity.setLockid(0);
                return fgConsuFileStructureEntity;
            };
}
