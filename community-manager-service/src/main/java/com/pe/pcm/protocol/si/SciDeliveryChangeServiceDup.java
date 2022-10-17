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

package com.pe.pcm.protocol.si;

import com.pe.pcm.constants.AuthoritiesConstants;
import com.pe.pcm.enums.Protocol;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.protocol.as2.si.SciDeliveryChangeRepository;
import com.pe.pcm.protocol.as2.si.entity.SciDelivChanEntity;
import com.pe.pcm.sterling.dto.DeliveryChanDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static com.pe.pcm.protocol.function.ProtocolFunctions.convertBooleanToNumber;
import static com.pe.pcm.utils.PCMConstants.*;

/**
 * @author Chenchu Kiran.
 */
@Service
public class SciDeliveryChangeServiceDup {
    private static final Logger LOGGER = LoggerFactory.getLogger(SciDeliveryChangeServiceDup.class);

    private final SciDeliveryChangeRepository sciDeliveryChangeRepository;

    @Autowired
    public SciDeliveryChangeServiceDup(SciDeliveryChangeRepository sciDeliveryChangeRepository) {
        this.sciDeliveryChangeRepository = sciDeliveryChangeRepository;
    }

    private final Function<DeliveryChanDTO, SciDelivChanEntity> serialize = deliveryChanDTO -> {
        SciDelivChanEntity sciDelivChanEntity = new SciDelivChanEntity();

        sciDelivChanEntity.setReceiptType("0");
        sciDelivChanEntity.setRcptSigType("0");
        sciDelivChanEntity.setRcptDelivMode("0");
        sciDelivChanEntity.setSyncReplyMode("None");

        String objectName;
        if (deliveryChanDTO.isNormalProfile()) {
            if (deliveryChanDTO.getProtocol().getProtocol().equals(Protocol.AS2.getProtocol())) {
                objectName = DELIVERY + "_" + deliveryChanDTO.getProfileId() + "_" + deliveryChanDTO.getProfileName();
                if (!deliveryChanDTO.getHubInfo()) {
                    sciDelivChanEntity.setReceiptType(convertBooleanToNumber.apply(deliveryChanDTO.getMdn()));
                    sciDelivChanEntity.setRcptSigType(deliveryChanDTO.getMdnEncryption());
                    sciDelivChanEntity.setRcptDelivMode(deliveryChanDTO.getMdnType());
                    sciDelivChanEntity.setReceiptToAddress(deliveryChanDTO.getReceiptToAddress());
                }
            } else {
                objectName = deliveryChanDTO.getObjectName().startsWith(DELIVERY) ? deliveryChanDTO.getObjectName() : DELIVERY + "_" + deliveryChanDTO.getObjectName();
            }

        } else {
            objectName = deliveryChanDTO.getObjectName().startsWith(DELIVERY_CHAN) ? deliveryChanDTO.getObjectName() : DELIVERY_CHAN + "_" + deliveryChanDTO.getObjectName();
        }
        sciDelivChanEntity.setObjectName(objectName);
        sciDelivChanEntity.setObjectId(deliveryChanDTO.getObjectId());
        sciDelivChanEntity.setEntityId(deliveryChanDTO.getEntityId());
        sciDelivChanEntity.setTransportId(deliveryChanDTO.getTransportId());
        sciDelivChanEntity.setDocExchangeId(deliveryChanDTO.getDocExchangeId());
        sciDelivChanEntity.setDeliveryChannelKey(deliveryChanDTO.getDeliveryChannelKey());

        return sciDelivChanEntity;
    };

    private final UnaryOperator<SciDelivChanEntity> applyDefaultValuesToEntity = sciDelivChan -> {

        sciDelivChan.setLockid(1);
        sciDelivChan.setCreateuserid(INSTALL_PROCESS);
        sciDelivChan.setModifyuserid(AuthoritiesConstants.ADMIN);
        sciDelivChan.setCreateprogid(UI);
        sciDelivChan.setModifyprogid(UI);
        sciDelivChan.setObjectVersion("1");
        sciDelivChan.setObjectClass("DELIVERY_CHANNEL");
        sciDelivChan.setObjectState(STRING_TRUE);
        sciDelivChan.setExtObjectVersion(0);
        sciDelivChan.setSyncReplyMode(NONE);
        sciDelivChan.setNonrepudOfOrigin("no");
        sciDelivChan.setNonrepudOfRcpt("no");
        sciDelivChan.setSecureTransport("no");
        sciDelivChan.setConfidentiality("no");
        sciDelivChan.setAuthenticated("no");
        sciDelivChan.setAuthorized("no");

        sciDelivChan.setLastModifier(INSTALL_PROCESS);
        sciDelivChan.setSyncReplymodeInh(0);
        sciDelivChan.setNonrepudOrigInh(0);
        sciDelivChan.setNonrepudRcptInh(0);
        sciDelivChan.setSecureTranspInh(0);
        sciDelivChan.setConfidentialInh(0);
        sciDelivChan.setAuthenticatedInh(0);
        sciDelivChan.setAuthorizedInh(0);
        sciDelivChan.setRcptSigTypeInh(0);
        sciDelivChan.setRcptToAddrInh(0);
        sciDelivChan.setRcptDelvModeInh(0);
        sciDelivChan.setReceiptTypeInh(0);
        sciDelivChan.setRcptTimeoutInh(0);
        sciDelivChan.setAlwaysVerifyInh(0);
        sciDelivChan.setExtObjectVersion(0);

        return sciDelivChan;
    };

    public void save(DeliveryChanDTO deliveryChanDTO) {
        LOGGER.info("Create/Update SciDelivChanEntity.");
        sciDeliveryChangeRepository.save(applyDefaultValuesToEntity.apply(serialize.apply(deliveryChanDTO)));
    }

    public Optional<SciDelivChanEntity> findByObjectId(String objectId) {
        return sciDeliveryChangeRepository.findById(objectId);
    }

    public List<SciDelivChanEntity> findAllByObjectIds(List<String> objectIds) {
        return sciDeliveryChangeRepository.findAllByObjectIdIn(objectIds).orElseThrow(() -> GlobalExceptionHandler.internalServerError("Community Profile, Delivery Entities Not found."));
    }

    public void delete(String objectId) {
        sciDeliveryChangeRepository.deleteById(objectId);
    }

}
