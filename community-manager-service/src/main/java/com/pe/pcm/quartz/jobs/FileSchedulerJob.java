/*
 *
 *  * Copyright (c) 2020 Pragma Edge Inc
 *  *
 *  * Licensed under the Pragma Edge Inc
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  * https://pragmaedge.com/licenseagreement
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 *
 */

package com.pe.pcm.quartz.jobs;

import com.pe.pcm.config.FileArchiveProperties;
import com.pe.pcm.reports.TransInfoDRepository;
import com.pe.pcm.reports.TransInfoDStagingRepository;
import com.pe.pcm.reports.TransferInfoRepository;
import com.pe.pcm.reports.TransferInfoStagingRepository;
import com.pe.pcm.reports.entity.TransInfoDStagingEntity;
import com.pe.pcm.reports.entity.TransferInfoStagingEntity;
import com.pe.pcm.settings.FileScheCloudArcRepository;
import com.pe.pcm.settings.FileSchedulerConfigRepository;
import com.pe.pcm.settings.entity.FileScheCloudArcEntity;
import com.pe.pcm.settings.entity.FileSchedulerConfigEntity;
import com.pe.pcm.utils.CommonFunctions;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.springframework.util.StringUtils.hasText;

/**
 * @author Kiran Reddy.
 */
@Component
public class FileSchedulerJob implements Job {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileSchedulerJob.class);

    private final FileScheCloudArcRepository fileScheCloudArcRepository;
    private final FileSchedulerConfigRepository fileSchedulerConfigRepository;
    private final TransferInfoRepository transferInfoRepository;
    private final TransInfoDRepository transInfoDRepository;
    private final TransferInfoStagingRepository transferInfoStagingRepository;
    private final TransInfoDStagingRepository transInfoDStagingRepository;
    private final FileArchiveProperties fileArchiveProperties;

    private boolean jobRunning = FALSE;
    private String scriptFilePath;


    @Autowired
    public FileSchedulerJob(FileScheCloudArcRepository fileScheCloudArcRepository,
                            FileSchedulerConfigRepository fileSchedulerConfigRepository,
                            TransferInfoRepository transferInfoRepository,
                            TransInfoDRepository transInfoDRepository,
                            TransferInfoStagingRepository transferInfoStagingRepository,
                            TransInfoDStagingRepository transInfoDStagingRepository,
                            FileArchiveProperties fileArchiveProperties) {
        this.fileScheCloudArcRepository = fileScheCloudArcRepository;
        this.fileSchedulerConfigRepository = fileSchedulerConfigRepository;
        this.transferInfoRepository = transferInfoRepository;
        this.transInfoDRepository = transInfoDRepository;
        this.transferInfoStagingRepository = transferInfoStagingRepository;
        this.transInfoDStagingRepository = transInfoDStagingRepository;
        this.fileArchiveProperties = fileArchiveProperties;
    }

    @Override
    public void execute(JobExecutionContext context) {
        Optional<FileSchedulerConfigEntity> schedulerConfigEntityOptional = fileSchedulerConfigRepository.findById("1");
        if (schedulerConfigEntityOptional.isPresent()) {
            if (!jobRunning) {
                jobRunning = TRUE;
                doMyTask(
                        schedulerConfigEntityOptional.get().getFileAge(),
                        fileScheCloudArcRepository.findAllByTraSchRef("1")
                                .stream()
                                .map(FileScheCloudArcEntity::getSrcRootDir)
                                .collect(Collectors.toList())
                );
            } else {
                LOGGER.info("Job Skipped due to last job is already in-progress.");
            }
        } else {
            LOGGER.info("File Scheduler config data not available, skipping scheduler job");
        }
    }

    @Transactional
    void doMyTask(String fileAge, List<String> deleteFilesDirectory) {
        LOGGER.info("Script execution job triggered. FileAge is: {}, delete Arc location is: {}", fileAge, deleteFilesDirectory);
        try {
            if (hasText(fileAge)) {
                Timestamp timestamp = CommonFunctions.minusDays(new Date(System.currentTimeMillis()), Integer.parseInt(fileAge));
                LOGGER.info("File Arrived before {}, Entities will be moved to staging tables.", timestamp);
                transferInfoRepository.findAllByFilearrivedBefore(timestamp)
                        .ifPresent(transferInfoEntities -> {
                                    transferInfoEntities.forEach(transferInfoEntity -> {
                                        String refId = hasText(transferInfoEntity.getCorebpid()) ?
                                                transferInfoEntity.getCorebpid() : String.valueOf(transferInfoEntity.getSeqid());
                                        transInfoDRepository.findByBpidOrderBySequenceAsc(refId.trim()).ifPresent(transInfoDEntities ->
                                                transInfoDEntities.forEach(transInfoDEntity -> {
                                                    TransInfoDStagingEntity transInfoDStagingEntity = new TransInfoDStagingEntity();
                                                    BeanUtils.copyProperties(transInfoDEntity, transInfoDStagingEntity);
                                                    transInfoDStagingRepository.save(transInfoDStagingEntity.setPkId(transInfoDEntity.getPkId()));
                                                    transInfoDRepository.deleteById(transInfoDEntity.getPkId());
                                                }));

                                        TransferInfoStagingEntity transferInfoStagingEntity = new TransferInfoStagingEntity();
                                        BeanUtils.copyProperties(transferInfoEntity, transferInfoStagingEntity);
                                        transferInfoStagingRepository.save(transferInfoStagingEntity.setSeqid(transferInfoEntity.getSeqid()));
                                        transferInfoRepository.deleteById(transferInfoEntity.getSeqid());
                                    });
                                    LOGGER.info("No of Transfer file entities moved to staging tables: {} Entities", transferInfoEntities.size());
                                }
                        );
                //Delete the files from filesystem
                if (fileArchiveProperties.getScheduler().getDeleteFilesJob().isActive()) {
                    deleteFilesDirectory.forEach(directory -> {
                        try {
                            new ProcessBuilder(Arrays.asList("sh", scriptFilePath, directory, fileAge)).start();
                        } catch (IOException e) {
                            LOGGER.error("Unable to delete the files.", e);
                        }
                    });
                } else {
                    LOGGER.info("Execution of delete files skipped...., due to \"file.archive.scheduler.delete-files-job.active\" was set to false in YML file.");
                }
            } else {
                LOGGER.info("Skipped Files deletion and Entities staging, because of file age is NULL/EMPTY");
            }
        } catch (Exception e) {
            LOGGER.error("Error : ", e);
        }
        jobRunning = FALSE;
    }

    @PostConstruct
    void loadScriptFilePath() {
        if (hasText(fileArchiveProperties.getScheduler().getDeleteFilesJob().getScriptFileLoc())) {
            scriptFilePath = fileArchiveProperties.getScheduler().getDeleteFilesJob().getScriptFileLoc().trim();
        } else {
            final ClassLoader classLoader = getClass().getClassLoader();
            final File file = new File(Objects.requireNonNull(classLoader.getResource("CMArchiveDelete.sh")).getFile());
            scriptFilePath = file.getPath();
        }
    }

}

