package com.pe.pcm.apiworkflow;

import com.pe.pcm.apiworkflow.entity.ProcessApiEntity;
import com.pe.pcm.exception.GlobalExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @author Kiran Reddy.
 */
@Service
public class ProcessApiService {

    private final ProcessApiRepository processApiRepository;

    @Autowired
    public ProcessApiService(ProcessApiRepository processApiRepository) {
        this.processApiRepository = processApiRepository;
    }

    @Transactional
    public void create(ProcessApiEntity processApiEntity) {
        processApiRepository.save(processApiEntity);
    }

    @Transactional
    public ProcessApiEntity getBySeqId(String pkId) {
        return processApiRepository.findById(pkId).orElse(new ProcessApiEntity());
    }

    public ProcessApiEntity get(String profileId) {
        return processApiRepository.findByProfileId(profileId).orElse(new ProcessApiEntity());
    }

    public Optional<ProcessApiEntity> getByProfileOptional(String profileId) {
        return processApiRepository.findByProfileId(profileId);
    }

    @Transactional
    public void delete(String seqId) {
        processApiRepository.findById(seqId).ifPresent(processApiRepository::delete);
    }

    public void updateApiName(String oldApiName, String newAPiName) {
        processApiRepository.save(processApiRepository.findByProfileId(oldApiName).orElseThrow(() -> GlobalExceptionHandler.notFound("Process API")).setProfileId(newAPiName));
    }
}
