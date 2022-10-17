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

package com.pe.pcm.protocol.as2;

import com.pe.pcm.protocol.As2Model;
import com.pe.pcm.protocol.as2.si.entity.SciDelivChanEntity;
import com.pe.pcm.protocol.as2.si.SciDeliveryChangeRepository;
import com.pe.pcm.constants.AuthoritiesConstants;
import com.pe.pcm.utils.KeyGeneratorUtil;
import com.pe.pcm.utils.PCMConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static com.pe.pcm.protocol.function.ProtocolFunctions.convertBooleanToNumber;
import static com.pe.pcm.utils.PCMConstants.*;

@Service
public class DeliveryChangeService {

    private final SciDeliveryChangeRepository sciDeliveryChangeRepository;

    @Autowired
    public DeliveryChangeService(SciDeliveryChangeRepository sciDeliveryChangeRepository) {
        this.sciDeliveryChangeRepository = sciDeliveryChangeRepository;
    }

    private final BiFunction<String, As2Model, SciDelivChanEntity> serialize = (transportObjectId, as2TradingPartner) -> {
        SciDelivChanEntity sciDelivChanEntity = new SciDelivChanEntity();

        String deliChaObjectId = KeyGeneratorUtil.getPrimaryKey.apply(PCMConstants.SCI_DC_OBJ_PRE_APPEND,
                PCMConstants.SCI_RANDOM_COUNT);
        sciDelivChanEntity.setObjectId(transportObjectId + ":-3afa");
        sciDelivChanEntity.setEntityId(transportObjectId + ":-3b0a");
        sciDelivChanEntity.setTransportId(transportObjectId + ":-3afb");
        sciDelivChanEntity.setDocExchangeId(transportObjectId + ":-3afc");
        sciDelivChanEntity.setDeliveryChannelKey(deliChaObjectId + "-as2");

        sciDelivChanEntity.setObjectName(DELIVERY+ "_" + as2TradingPartner.getProfileId() + "_" + as2TradingPartner.getProfileName());
        if (!as2TradingPartner.getHubInfo()) {
            sciDelivChanEntity.setObjectName(DELIVERY+ "_" + as2TradingPartner.getProfileId() + "_" + as2TradingPartner.getProfileName());
            //sciDelivChanEntity.setObjectName(DELIVERY + "_" + as2TradingPartner.getProfileId())
            sciDelivChanEntity.setReceiptType(convertBooleanToNumber.apply(as2TradingPartner.getMdn()));//New
            sciDelivChanEntity.setRcptSigType(as2TradingPartner.getMdnEncryption());
            sciDelivChanEntity.setRcptDelivMode(as2TradingPartner.getMdnType());
            sciDelivChanEntity.setReceiptToAddress(as2TradingPartner.getReceiptToAddress()); //New
        }
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
        sciDelivChan.setRcptDelivMode("0");

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

    private Function<As2Model, SciDelivChanEntity> serializeToUpdate = as2TradingPartner -> {
        SciDelivChanEntity sciDelivChanEntity = new SciDelivChanEntity();
        sciDelivChanEntity.setObjectName(DELIVERY + "_" + as2TradingPartner.getProfileId() + "_" + as2TradingPartner.getProfileName());
        if (!as2TradingPartner.getHubInfo()) {
            sciDelivChanEntity.setObjectName(DELIVERY + "_" + as2TradingPartner.getProfileId() + "_" + as2TradingPartner.getProfileName());
            //sciDelivChanEntity.setObjectName(DELIVERY + "_" + as2TradingPartner.getProfileId());
            sciDelivChanEntity.setRcptSigType(as2TradingPartner.getMdnEncryption());
            sciDelivChanEntity.setRcptDelivMode(as2TradingPartner.getMdnType());
            sciDelivChanEntity.setReceiptType(convertBooleanToNumber.apply(as2TradingPartner.getMdn()));//New
            sciDelivChanEntity.setReceiptToAddress(as2TradingPartner.getReceiptToAddress()); //New
        }
        return sciDelivChanEntity;
    };

    public void save(String transportObjectId, As2Model as2TradingPartner) {
        SciDelivChanEntity sciDelivChanEntity = applyDefaultValuesToEntity.apply(serialize.apply(transportObjectId, as2TradingPartner));
        sciDeliveryChangeRepository.save(sciDelivChanEntity);
    }

    public void update(String sciTransportObjId, As2Model as2TradingPartner) {
        Optional<SciDelivChanEntity> sciDelivChanOptional = findByTransportId(sciTransportObjId);
        if (sciDelivChanOptional.isPresent()) {
            SciDelivChanEntity sciDelivChanEntityOrg = sciDelivChanOptional.get();
            SciDelivChanEntity sciDelivChanEntity = applyDefaultValuesToEntity.apply(serializeToUpdate.apply(as2TradingPartner));
            sciDelivChanEntity.setObjectId(sciDelivChanEntityOrg.getObjectId());
            sciDelivChanEntity.setEntityId(sciDelivChanEntityOrg.getEntityId());
            sciDelivChanEntity.setTransportId(sciDelivChanEntityOrg.getTransportId());
            sciDelivChanEntity.setDocExchangeId(sciDelivChanEntityOrg.getDocExchangeId());
            sciDelivChanEntity.setDeliveryChannelKey(sciDelivChanEntityOrg.getDeliveryChannelKey());
            sciDeliveryChangeRepository.save(sciDelivChanEntity);
        }
    }


    public Optional<SciDelivChanEntity> get(boolean isHubInfo, String objectName) {
        String searchString = (isHubInfo) ? objectName + "_" + objectName : objectName;
        Optional<List<SciDelivChanEntity>> sciDelivChanOptionalList = sciDeliveryChangeRepository
                .findByObjectName(DELIVERY + "_" + searchString);
        return sciDelivChanOptionalList.map(sciDelivChans -> sciDelivChans.get(0));
    }

    public Optional<SciDelivChanEntity> findByTransportId(String transportId) {
        return sciDeliveryChangeRepository
                .findByTransportId(transportId).map(sciDelivChans -> sciDelivChans.get(0));
    }

    public void delete(boolean isHubInfo, String transportId) {
        findByTransportId(transportId).ifPresent(sciDelivChan -> sciDeliveryChangeRepository.delete(sciDelivChan));
    }

}
