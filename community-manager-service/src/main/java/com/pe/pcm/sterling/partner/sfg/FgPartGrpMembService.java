package com.pe.pcm.sterling.partner.sfg;

import com.pe.pcm.generator.PrimaryKeyGeneratorService;
import com.pe.pcm.pem.GroupAndIdentityModel;
import com.pe.pcm.pem.GroupModel;
import com.pe.pcm.protocol.as2.si.YFSOrganizationRepository;
import com.pe.pcm.protocol.as2.si.entity.YFSOrganizationEntity;
import com.pe.pcm.sterling.FgPartGrpRepository;
import com.pe.pcm.sterling.entity.FgPartGrpEntity;
import com.pe.pcm.sterling.partner.sfg.entity.FgPartGrpMembEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;
import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;
import static com.pe.pcm.utils.CommonFunctions.isNotNull;

/**
 * @author Chenchu Kiran.
 */
@Service
public class FgPartGrpMembService {

    private FgPartGrpMembRepository fgPartGrpMembRepository;
    private static PrimaryKeyGeneratorService primaryKeyGeneratorService;

    private FgPartGrpRepository fgPartGrpRepository;

    private YFSOrganizationRepository yfsOrganizationRepository;

    private final BiFunction<String, String, FgPartGrpMembEntity> serialize = FgPartGrpMembService::apply;

    @Autowired
    public FgPartGrpMembService(FgPartGrpMembRepository fgPartGrpMembRepository, PrimaryKeyGeneratorService primaryKeyGeneratorService, FgPartGrpRepository fgPartGrpRepository, YFSOrganizationRepository yfsOrganizationRepository) {
        this.fgPartGrpMembRepository = fgPartGrpMembRepository;
        this.primaryKeyGeneratorService = primaryKeyGeneratorService;
        this.fgPartGrpRepository = fgPartGrpRepository;
        this.yfsOrganizationRepository = yfsOrganizationRepository;
    }

    /*We can use this for both Create and Update*/
    public void save(String organizationKey, String partnerGrpName) {
        AtomicReference<String> partGrpMembKey = new AtomicReference<>();
        fgPartGrpMembRepository.findFirstByOrganizationKey(organizationKey).ifPresent(fgPartGrpMembEntity ->
                partGrpMembKey.set(fgPartGrpMembEntity.getPartGrpMembKey()));
        if (!isNotNull(partGrpMembKey.get())) {
            partGrpMembKey.set(primaryKeyGeneratorService.generatePrimaryKey("", 4));
        }
        FgPartGrpMembEntity fgPartGrpMembEntity = new FgPartGrpMembEntity().setPartGrpMembKey(partGrpMembKey.get())
                .setOrganizationKey(organizationKey)
                .setPartGrpKey(partnerGrpName);
        fgPartGrpMembEntity.setLockid(0);
        fgPartGrpMembEntity.setCreateuserid("pcm user");
        fgPartGrpMembEntity.setModifyuserid("pcm user");
        fgPartGrpMembEntity.setCreateprogid("filegateway");
        fgPartGrpMembEntity.setModifyprogid("filegateway");

        fgPartGrpMembRepository.save(fgPartGrpMembEntity);
    }

    public String[] get(String organizationKey) {
        FgPartGrpMembEntity fgPartGrpMembEntity = fgPartGrpMembRepository.findFirstByOrganizationKey(organizationKey)
                .orElseThrow(() -> internalServerError("FgPartGrpMembEntity entity not found."));
        return new String[]{fgPartGrpMembEntity.getPartGrpMembKey(), fgPartGrpMembEntity.getOrganizationKey()};
    }

    public void delete(String organizationKey) {
        fgPartGrpMembRepository.deleteAllByOrganizationKey(organizationKey);
    }

    @Transactional
    public void addIdentityToGroup(GroupAndIdentityModel groupAndIdentityModel) {
        String orgKey = yfsOrganizationRepository.findFirstByOrganizationName(groupAndIdentityModel.getIdentity()).map(YFSOrganizationEntity::getOrganizationKey)
                .orElseThrow(() -> notFound("Given identity is "));
        List<FgPartGrpMembEntity> fgPartGrpMembEntities1;
        List<FgPartGrpMembEntity> fgPartGrpMembEntities = fgPartGrpMembRepository.findAllByOrganizationKey(orgKey)
                .orElse(new ArrayList<>());
        if (!fgPartGrpMembEntities.isEmpty() && isNotNull(fgPartGrpMembEntities)) {
            List<String> uniqueGroup = groupAndIdentityModel.getGroups().stream().map(GroupModel::getGroup).
                    collect(Collectors.toList())
                    .stream().filter(s -> !fgPartGrpMembEntities.stream().
                            map(FgPartGrpMembEntity::getPartGrpKey).
                            collect(Collectors.toList()).contains(s)).
                    collect(Collectors.toList());
            List<String> grpPartKey = fgPartGrpRepository.findAllByNameIn(uniqueGroup).
                    stream().
                    map(FgPartGrpEntity::getPartGrpKey).
                    collect(Collectors.toList());
            fgPartGrpMembEntities1 = grpPartKey.stream().map(s -> serialize.apply(s, orgKey)).collect(Collectors.toList());
            fgPartGrpMembRepository.saveAll(fgPartGrpMembEntities1);
        } else {
            fgPartGrpMembEntities1 = groupAndIdentityModel.getGroups().stream().map(groupModel -> serialize.apply(groupModel.getGroup(), orgKey)).collect(Collectors.toList());
            fgPartGrpMembRepository.saveAll(fgPartGrpMembEntities1);
        }
    }

    private static FgPartGrpMembEntity apply(String partKey, String orgkey) {
        FgPartGrpMembEntity fgPartGrpMembEntity = new FgPartGrpMembEntity()
                .setPartGrpMembKey(primaryKeyGeneratorService.generatePrimaryKey("", 4))
                .setOrganizationKey(orgkey)
                .setPartGrpKey(partKey);
        fgPartGrpMembEntity.setLockid(0);
        fgPartGrpMembEntity.setCreateuserid("pcm user");
        fgPartGrpMembEntity.setModifyuserid("pcm user");
        fgPartGrpMembEntity.setCreateprogid("filegateway");
        fgPartGrpMembEntity.setModifyprogid("filegateway");
        return fgPartGrpMembEntity;
    }


}
