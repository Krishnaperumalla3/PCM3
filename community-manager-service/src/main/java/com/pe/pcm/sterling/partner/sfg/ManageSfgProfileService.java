package com.pe.pcm.sterling.partner.sfg;

import com.pe.pcm.protocol.si.SciContractServiceDup;
import com.pe.pcm.protocol.si.SciEntityExtnsService;
import com.pe.pcm.sterling.dto.SfgProfileDetailsDTO;
import com.pe.pcm.sterling.partner.SciCodeUserXrefService;
import com.pe.pcm.sterling.partner.SciCommTpXrefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.pe.pcm.protocol.function.SterlingFunctions.convertSfgProfileDetailsDTOToSciContractDTO;

/**
 * @author Chenchu Kiran.
 */
@Service
public class ManageSfgProfileService {

    private final FgPartGrpMembService fgPartGrpMembService;
    private final SciCommTpXrefService sciCommTpXrefService;
    private final SciCodeUserXrefService sciCodeUserXrefService;
    private final SciContractServiceDup sciContractServiceDup;
    private final SciEntityExtnsService sciEntityExtnsService;

    @Autowired
    public ManageSfgProfileService(FgPartGrpMembService fgPartGrpMembService, SciCommTpXrefService sciCommTpXrefService, SciCodeUserXrefService sciCodeUserXrefService, SciContractServiceDup sciContractServiceDup, SciEntityExtnsService sciEntityExtnsService) {
        this.fgPartGrpMembService = fgPartGrpMembService;
        this.sciCommTpXrefService = sciCommTpXrefService;
        this.sciCodeUserXrefService = sciCodeUserXrefService;
        this.sciContractServiceDup = sciContractServiceDup;
        this.sciEntityExtnsService = sciEntityExtnsService;
    }

    public void save(SfgProfileDetailsDTO sfgProfileDetailsDTO) {

        if (!sfgProfileDetailsDTO.getSfgSubDetailsLoaded()) {
            fgPartGrpMembService.save(sfgProfileDetailsDTO.getOrganizationKey(),"All Partners");
            sciCodeUserXrefService.save(sfgProfileDetailsDTO.getUserName(), sfgProfileDetailsDTO.getTransportEntityKey());
            sciEntityExtnsService.save(sfgProfileDetailsDTO);
        }

        sciCommTpXrefService.save(sfgProfileDetailsDTO.getCommunityId(), sfgProfileDetailsDTO.getSciProfileObjectId());
        sciContractServiceDup.save(convertSfgProfileDetailsDTOToSciContractDTO.apply(sfgProfileDetailsDTO));
    }


    public void delete(SfgProfileDetailsDTO sfgProfileDetailsDTO) {
        //TODO : START:- Need to handle the Both cases Cosumer and Producer
        fgPartGrpMembService.delete(sfgProfileDetailsDTO.getOrganizationKey());
        sciCodeUserXrefService.delete(sfgProfileDetailsDTO.getTransportEntityKey());
        sciEntityExtnsService.deleteAllByEntityId(sfgProfileDetailsDTO.getTransportEntityKey());
        //TODO : END:- Need to handle the Both cases Cosumer and Producer

        sciCommTpXrefService.delete(sfgProfileDetailsDTO.getSciProfileObjectId());
        sciContractServiceDup.delete(sfgProfileDetailsDTO.getSciProfileObjectId());

    }

    public SfgProfileDetailsDTO get(String transportEntityKey, String sciProfileObjectId) {
        return new SfgProfileDetailsDTO().setExtensionKeysMap(sciEntityExtnsService.getExtKeys(transportEntityKey))
                .setCommunityId(sciCommTpXrefService.getCommunityName(sciProfileObjectId));
    }

}
