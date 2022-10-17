package com.pe.pcm.partner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pe.pcm.activity.ActivityHistoryService;
import com.pe.pcm.enums.Protocol;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.partner.entity.PartnerEntity;
import com.pe.pcm.protocol.AzureModel;
import com.pe.pcm.protocol.AzureService;
import com.pe.pcm.protocol.ManageProtocolService;
import com.pe.pcm.protocol.azure.entity.AzureEntity;
import com.pe.pcm.utils.KeyGeneratorUtil;
import com.pe.pcm.utils.PCMConstants;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import static com.pe.pcm.exception.GlobalExceptionHandler.conflict;
import static com.pe.pcm.exception.GlobalExceptionHandler.unknownProtocol;
import static com.pe.pcm.utils.CommonFunctions.isNotNull;
import static com.pe.pcm.utils.KeyGeneratorUtil.getChildPrimaryKey;

/**
 * @author Kiran Reddy.
 */
@Service
public class AzurePartnerService {

    private final AzureService azureService;
    private final PartnerService partnerService;
    private final ActivityHistoryService activityHistoryService;
    private final ManageProtocolService manageProtocolService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public AzurePartnerService(AzureService azureService, PartnerService partnerService, ActivityHistoryService activityHistoryService, ManageProtocolService manageProtocolService) {
        this.azureService = azureService;
        this.partnerService = partnerService;
        this.activityHistoryService = activityHistoryService;
        this.manageProtocolService = manageProtocolService;
    }

    @Transactional
    public String save(AzureModel awsS3Model) {
        if (!Optional.ofNullable(Protocol.findProtocol(awsS3Model.getProtocol())).isPresent()) {
            throw unknownProtocol();
        }
        duplicatePartner(awsS3Model.getProfileId());
        String parentPrimaryKey = KeyGeneratorUtil.getPrimaryKey.apply(PCMConstants.TP_PKEY_PRE_APPEND, PCMConstants.TP_PKEY_RANDOM_COUNT);
        String childPrimaryKey = KeyGeneratorUtil.getChildPrimaryKey(awsS3Model.getProtocol());
        saveProtocol(awsS3Model, parentPrimaryKey, childPrimaryKey);
        PartnerModel partnerModel = new PartnerModel();
        BeanUtils.copyProperties(awsS3Model, partnerModel);
        partnerService.save(partnerModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.savePartnerActivity(parentPrimaryKey, "Trading Partner created.");
        return parentPrimaryKey;
    }

    @Transactional
    public void update(AzureModel azureModel) {
        PartnerEntity oldPartnerEntity = SerializationUtils.clone(partnerService.get(azureModel.getPkId()));
        if (!oldPartnerEntity.getTpId().equalsIgnoreCase(azureModel.getProfileId())) {
            duplicatePartner(azureModel.getProfileId());
        }
        String parentPrimaryKey = oldPartnerEntity.getPkId();
        String childPrimaryKey = oldPartnerEntity.getPartnerProtocolRef();
        Protocol protocol = Optional.ofNullable(Protocol.findProtocol(oldPartnerEntity.getTpProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);

        Map<String, String> oldHttpEntityMap = new LinkedHashMap<>();
        boolean isUpdate = true;
        if (!protocol.getProtocol().equalsIgnoreCase(azureModel.getProtocol())) {
            manageProtocolService.deleteProtocol(oldPartnerEntity.getTpProtocol(), oldPartnerEntity.getPkId());
            childPrimaryKey = getChildPrimaryKey(azureModel.getProtocol());
            isUpdate = false;
        } else {
            oldHttpEntityMap = mapper.convertValue(azureService.get(parentPrimaryKey), new TypeReference<Map<String, String>>() {
            });
        }

        Map<String, String> newHttpEntityMap =
                mapper.convertValue(saveProtocol(azureModel, parentPrimaryKey, childPrimaryKey),
                        new TypeReference<Map<String, String>>() {
                        });
        azureModel.setPemIdentifier(isNotNull(azureModel.getPemIdentifier())
                ? azureModel.getPemIdentifier() : oldPartnerEntity.getPemIdentifier());
        PartnerModel partnerModel = new PartnerModel();
        BeanUtils.copyProperties(azureModel, partnerModel);
        PartnerEntity newPartnerEntity = partnerService.save(partnerModel, parentPrimaryKey, childPrimaryKey);

        activityHistoryService.updatePartnerActivity(oldPartnerEntity, newPartnerEntity, oldHttpEntityMap, newHttpEntityMap, isUpdate);
    }

    @Transactional
    public void delete(String pkId) {
    }

    public AzureModel get(String pkId) {

        return null;
    }

    private void duplicatePartner(String partnerId) {
        Optional<PartnerEntity> tradingPartnerEntityOptional = partnerService.find(partnerId);
        tradingPartnerEntityOptional.ifPresent(tradingPartnerEntity -> {
            throw conflict("Partner");
        });
    }

    public AzureEntity saveProtocol(AzureModel azureModel, String parentPrimaryKey, String childPrimaryKey) {
        return azureService.saveProtocol(azureModel, parentPrimaryKey, childPrimaryKey, "TP");
    }
}
