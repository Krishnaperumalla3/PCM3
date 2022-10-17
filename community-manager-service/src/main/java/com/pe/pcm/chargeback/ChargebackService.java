package com.pe.pcm.chargeback;

import com.pe.pcm.reports.JdbcTemplateComponent;
import com.pe.pcm.reports.TransferInfoChargebackRepository;
import com.pe.pcm.reports.entity.PetpeChargebackSlabs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;

@Service
public class ChargebackService {

    private final TransferInfoChargebackRepository transferInfoChargebackRepository;

    private final Logger log = LoggerFactory.getLogger(JdbcTemplateComponent.class);

    @Autowired
    public ChargebackService(TransferInfoChargebackRepository transferInfoChargebackRepository) {
        this.transferInfoChargebackRepository = transferInfoChargebackRepository;
    }

    public PetpeChargebackSlabs getChargebackData() {
        log.info(" ChargeBack Data ");
       return transferInfoChargebackRepository.findFirstByActiveOrderByLastupdateDateDesc((double) 1).orElseThrow(() -> notFound(" TABLE ACTIVE DATA "));
    }

    @Transactional
    public String update(ChargeBackSlabsModel chargeBackSlabsModel) {
        transferInfoChargebackRepository.updateSlabs((double) 0);
        PetpeChargebackSlabs chargebackSlabsData = modelToEntity(chargeBackSlabsModel);
        log.info(" ChargeBack New Values Updated ");
        transferInfoChargebackRepository.save(chargebackSlabsData);
        return null;
    }

    public PetpeChargebackSlabs modelToEntity(ChargeBackSlabsModel chargeBackSlabsModel) {
        PetpeChargebackSlabs chargebackSlabs = new PetpeChargebackSlabs();
        chargebackSlabs.setMinCharge(chargeBackSlabsModel.getMinCharge());
        chargebackSlabs.setFlat1(chargeBackSlabsModel.getFlat1());
        chargebackSlabs.setRate110(chargeBackSlabsModel.getRate110());
        chargebackSlabs.setFlat10(chargeBackSlabsModel.getFlat10());
        chargebackSlabs.setRate1025(chargeBackSlabsModel.getRate1025());
        chargebackSlabs.setFlat25(chargeBackSlabsModel.getFlat25());
        chargebackSlabs.setRateAbove25(chargeBackSlabsModel.getRateAbove25());
        chargebackSlabs.setFlatOther(chargeBackSlabsModel.getFlatOther());
        chargebackSlabs.setActive((double) 1);
        chargebackSlabs.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
        chargebackSlabs.setLastupdateDate(Timestamp.valueOf(LocalDateTime.now()));
        return chargebackSlabs;
    }

}
