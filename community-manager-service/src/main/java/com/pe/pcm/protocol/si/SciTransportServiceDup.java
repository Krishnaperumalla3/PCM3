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

import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.protocol.as2.si.SciTransportRepository;
import com.pe.pcm.protocol.as2.si.entity.SciTransportEntity;
import com.pe.pcm.sterling.dto.TransportDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static com.pe.pcm.enums.Protocol.*;
import static com.pe.pcm.utils.CommonFunctions.isNotNull;
import static com.pe.pcm.utils.PCMConstants.*;
import static org.springframework.util.StringUtils.hasText;

/**
 * @author Kiran Reddy.
 */
@Service
public class SciTransportServiceDup {

    private static final Logger LOGGER = LoggerFactory.getLogger(SciTransportServiceDup.class);

    private final SciTransportRepository sciTransportRepository;

    @Autowired
    public SciTransportServiceDup(SciTransportRepository sciTransportRepository) {
        this.sciTransportRepository = sciTransportRepository;
    }

    private final Function<TransportDTO, SciTransportEntity> serialize = transportDTO -> {

        SciTransportEntity sciTransportEntity = new SciTransportEntity();
        sciTransportEntity.setSendingProtocol(null);
        sciTransportEntity.setReceivingProtocol(transportDTO.getReceivingProtocol());
        sciTransportEntity.setDirectory(transportDTO.getDirectory());
        sciTransportEntity.setMailBox(transportDTO.getMailBox());
        sciTransportEntity.setSslOption(transportDTO.getSslOption());
        sciTransportEntity.setProtocolMode(transportDTO.getProtocolMode());
        sciTransportEntity.setTransferMode(transportDTO.getTransferMode());
        String objectName;
        if (transportDTO.isNormalProfile()) {
            if (transportDTO.getProtocol().getProtocol().equals(AS2.getProtocol())) {
                objectName = TRANSPORT + transportDTO.getProfileId() + "_" + transportDTO.getProfileName();
                sciTransportEntity.setSendingProtocol("HTTP");
                sciTransportEntity.setReceivingProtocol("HTTP");
                sciTransportEntity.setDirectory(null);
                sciTransportEntity.setMailBox(null);
                sciTransportEntity.setCipherStrength(transportDTO.getCipherStrength());
                sciTransportEntity.setSslOption(transportDTO.getSslOption());
                sciTransportEntity.setTransferMode("");
                sciTransportEntity.setProtocolMode("");
            } else {
                objectName = TRANSPORT + transportDTO.getProfileName();
                sciTransportEntity.setSslOption(transportDTO.getProtocol().getProtocol().equals(SFG_FTPS.getProtocol()) ? "SSL_MUST" : "SSL_NONE");
                sciTransportEntity.setSecurityProtocol(transportDTO.getProtocol().getProtocol().equals(SFG_FTP.getProtocol()) ? "" : "SSL");
                sciTransportEntity.setReceivingProtocol("FTP");
                if (isNotNull(transportDTO.getCipherStrength())) {
                    sciTransportEntity.setCipherStrength(transportDTO.getCipherStrength());
                }
            }
        } else {
            objectName = transportDTO.getProfileName() + "_" + transportDTO.getProfileType();
        }
        sciTransportEntity.setObjectName(objectName);
        sciTransportEntity.setObjectId(transportDTO.getObjectId());
        sciTransportEntity.setEntityId(transportDTO.getEntityId());
        sciTransportEntity.setTransportKey(transportDTO.getTransportKey());

        sciTransportEntity.setLocDataPorts(null);
        sciTransportEntity.setLocCtrlPorts(null);
        sciTransportEntity.setFwConnTime(null);
        sciTransportEntity.setServSockTimeout(null);
        sciTransportEntity.setKeyCertPassword(transportDTO.getKeyCertificatePassphrase());
        sciTransportEntity.setTranspActUserId(transportDTO.getUserId());
        sciTransportEntity.setTranspActPwd(transportDTO.getPassword());
        sciTransportEntity.setEndPointIpAddr(transportDTO.getHostName());
        sciTransportEntity.setEndPntListPort(transportDTO.getPortNumber());
        sciTransportEntity.setSecurityProtocol(transportDTO.getSecurityProtocol());
        sciTransportEntity.setEndPoint(transportDTO.getEndPoint());
        sciTransportEntity.setKeyCertId(transportDTO.getKeyCertification());
        sciTransportEntity.setUserCertId(null);

        sciTransportEntity.setCipherStrength(
                (hasText(transportDTO.getCipherStrength()) ? transportDTO.getCipherStrength().toUpperCase() : sciTransportEntity.getCipherStrength()));
        sciTransportEntity.setResponseTimeout(transportDTO.getResponseTimeout());

        //This is for AS2
        if (transportDTO.getHubInfo()) {
            sciTransportEntity.setSslOption("SSL_NONE");
        }

        return sciTransportEntity;
    };

    public void save(TransportDTO transportDTO) {
        LOGGER.info("Saving SciTransportEntity");
        sciTransportRepository.save(serialize.apply(transportDTO));
    }

    public Optional<SciTransportEntity> get(String objectName) {
        LOGGER.info("Object Name : {}", objectName);
        Optional<List<SciTransportEntity>> sciTransportOptionalList = sciTransportRepository.findByObjectName(objectName);
        if (sciTransportOptionalList.isPresent() && sciTransportOptionalList.get().size() == 0) {
            sciTransportOptionalList = sciTransportRepository.findByObjectName(objectName.replace(TRANSPORT, "") + _CONSUMER);
            LOGGER.info("{}, {}", sciTransportOptionalList.isPresent(), objectName.replace(TRANSPORT, "") + _CONSUMER);
        }
        return sciTransportOptionalList.map(sciTransports -> !sciTransports.isEmpty() ? sciTransports.get(0) : null);
    }


    public Optional<SciTransportEntity> getTransport(String objectName) {
        return sciTransportRepository.findByObjectName(objectName).map(sciTransportEntities -> !sciTransportEntities.isEmpty() ? sciTransportEntities.get(0) : null);
    }

    public void delete(String transportKey) {
        LOGGER.info("Delete SciTransportEntity.");
        sciTransportRepository.deleteById(transportKey);
    }

    public Optional<TransportDTO> getTransportDTO(String objectName) {
        return deSerialize.apply(get(objectName));
    }

    public Optional<TransportDTO> getTransportDTOByEntityId(String entityId) {
        return deSerialize.apply(findFirstByEntityId(entityId));
    }

    public List<SciTransportEntity> findAllByObjectIds(List<String> objectIds) {
        return sciTransportRepository.findAllByObjectIdIn(objectIds).orElseThrow(() -> GlobalExceptionHandler.internalServerError("Community Profile, SciTransport Entity not found."));
    }

    public Optional<SciTransportEntity> findFirstByEntityId(String entityId) {
        return sciTransportRepository.findFirstByEntityId(entityId);
    }

    public void updateObjectName(String transportKey, String objectName) {
        sciTransportRepository.updateObjectName(transportKey, objectName);
    }

    private static final Function<Optional<SciTransportEntity>, Optional<TransportDTO>> deSerialize = sciTransportEntityOptional ->
            sciTransportEntityOptional.map(sciTransportEntity -> new TransportDTO().setEntityId(sciTransportEntity.getEntityId())
                    .setObjectId(sciTransportEntity.getObjectId())
                    .setDirectory(sciTransportEntity.getDirectory())
                    .setMailBox(sciTransportEntity.getMailBox())
                    .setUserId(sciTransportEntity.getTranspActUserId())
                    .setPassword(PRAGMA_EDGE_S)
                    .setHostName(sciTransportEntity.getEndPointIpAddr())
                    .setSecurityProtocol(sciTransportEntity.getSecurityProtocol())
                    .setProtocolMode(sciTransportEntity.getProtocolMode())
                    .setKeyCertification(sciTransportEntity.getKeyCertId())
                    .setEndPoint(sciTransportEntity.getEndPoint())
                    .setSslOption(sciTransportEntity.getSslOption())
                    .setCipherStrength(sciTransportEntity.getCipherStrength())
                    .setResponseTimeout(sciTransportEntity.getResponseTimeout())
                    .setSslOption(sciTransportEntity.getSslOption())
                    .setEntityId(sciTransportEntity.getEntityId())
                    .setPortNumber(sciTransportEntity.getEndPntListPort()));


}
