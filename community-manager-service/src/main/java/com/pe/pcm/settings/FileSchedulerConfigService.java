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

package com.pe.pcm.settings;

import com.pe.pcm.config.FileArchiveProperties;
import com.pe.pcm.config.FileSchedulerModel;
import com.pe.pcm.config.s3.FilesPathRefModel;
import com.pe.pcm.config.s3.S3InfoModel;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.miscellaneous.PasswordUtilityService;
import com.pe.pcm.quartz.FilesSchedulerQuartzService;
import com.pe.pcm.settings.entity.FileScheCloudArcEntity;
import com.pe.pcm.settings.entity.FileSchedulerConfigEntity;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.pe.pcm.utils.CommonFunctions.*;
import static org.springframework.util.StringUtils.hasText;

/**
 * @author Kiran Reddy.
 */
@Service
public class FileSchedulerConfigService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileSchedulerConfigService.class);

    private final FileSchedulerConfigRepository fileSchedulerConfigRepository;
    private final PasswordUtilityService passwordUtilityService;
    private final FilesSchedulerQuartzService filesSchedulerQuartzService;
    private final FileScheCloudArcRepository fileScheCloudArcRepository;
    private final FileArchiveProperties fileArchiveProperties;

    @Autowired
    public FileSchedulerConfigService(FileSchedulerConfigRepository fileSchedulerConfigRepository,
                                      PasswordUtilityService passwordUtilityService,
                                      FilesSchedulerQuartzService filesSchedulerQuartzService,
                                      FileScheCloudArcRepository fileScheCloudArcRepository,
                                      FileArchiveProperties fileArchiveProperties) {
        this.fileSchedulerConfigRepository = fileSchedulerConfigRepository;
        this.passwordUtilityService = passwordUtilityService;
        this.filesSchedulerQuartzService = filesSchedulerQuartzService;
        this.fileScheCloudArcRepository = fileScheCloudArcRepository;
        this.fileArchiveProperties = fileArchiveProperties;
    }


    @Transactional
    public void save(FileSchedulerModel fileSchedulerModel) {
        validate(fileSchedulerModel);
        FileSchedulerConfigEntity fileSchedulerConfigEntity = serialize.apply(fileSchedulerModel);
        if (hasText(fileSchedulerConfigEntity.getSecretKeyId())) {
            try {
                passwordUtilityService.decrypt(fileSchedulerConfigEntity.getSecretKeyId());
                LOGGER.info("Secret key already encrypted.");
            } catch (Exception e) {
                LOGGER.info("Secret key encrypted.");
                fileSchedulerConfigEntity.setSecretKeyId(passwordUtilityService.encrypt(fileSchedulerConfigEntity.getSecretKeyId()));
            }
        }
        fileSchedulerConfigRepository.save(fileSchedulerConfigEntity);
        fileScheCloudArcRepository.deleteAllByTraSchRef("1");
        fileScheCloudArcRepository.saveAll(mapperToCloudArcEntities.apply(fileSchedulerModel));
        if (fileSchedulerModel.isActive()) {
            filesSchedulerQuartzService.createOrUpdateScheduler(fileArchiveProperties.getScheduler().getCron(), fileSchedulerModel.getFileAge());
        } else {
            filesSchedulerQuartzService.deleteJob();
        }
    }

    public FileSchedulerModel get() {
        FileSchedulerModel fileSchedulerModel = deSerialize.apply(fileSchedulerConfigRepository.findById("1").orElseThrow(() -> GlobalExceptionHandler.notFound("FileScheduler Config")));
        fileSchedulerModel.getS3SchedulerConfig().setFilesPathDetails(fileScheCloudArcRepository.findAllByTraSchRef("1")
                .stream()
                .map(fileScheCloudArcEntity -> new FilesPathRefModel().setSourcePath(fileScheCloudArcEntity.getSrcRootDir())
                        .setDestPath(fileScheCloudArcEntity.getDestRootDir())).collect(Collectors.toList()));
        return fileSchedulerModel;
    }

    public Optional<FileSchedulerConfigEntity> getOnlyFileSchConfEntity() {
        return fileSchedulerConfigRepository.findById("1");
    }

    @PostConstruct
    void doLoadScheduler() {
        fileSchedulerConfigRepository.findById("1").ifPresent(fileSchedulerConfigEntity -> {
            if (hasText(fileSchedulerConfigEntity.getIsActive())
                    && fileSchedulerConfigEntity.getIsActive().equalsIgnoreCase("Y")
                    && hasText(fileSchedulerConfigEntity.getFileAge())) {
                LOGGER.info("Starting File Scheduler.");
                filesSchedulerQuartzService.createOrUpdateScheduler(fileArchiveProperties.getScheduler().getCron(), fileSchedulerConfigEntity.getFileAge());
            }
        });
    }

    private static final Function<FileSchedulerModel, FileSchedulerConfigEntity> serialize = fileSchedulerModel -> {
        FileSchedulerConfigEntity fileSchedulerConfigEntity = new FileSchedulerConfigEntity().setPkId("1")
                .setFileAge(fileSchedulerModel.getFileAge())
                .setIsActive(convertBooleanToString(fileSchedulerModel.isActive()))
                .setCloudName(fileSchedulerModel.getCloudName());
        if (fileSchedulerModel.getCloudName().equalsIgnoreCase("AWS-S3")) {
            fileSchedulerConfigEntity.setRegion(fileSchedulerModel.getS3SchedulerConfig().getRegion())
                    .setBucketName(fileSchedulerModel.getS3SchedulerConfig().getBucketName())
                    .setAccessKeyId(fileSchedulerModel.getS3SchedulerConfig().getAccessKeyId())
                    .setSecretKeyId(fileSchedulerModel.getS3SchedulerConfig().getSecretKeyId());
        }
        return fileSchedulerConfigEntity;
    };

    private static final Function<FileSchedulerConfigEntity, FileSchedulerModel> deSerialize = fileSchedulerConfigEntity ->
            new FileSchedulerModel().setPkId(fileSchedulerConfigEntity.getPkId())
                    .setFileAge(fileSchedulerConfigEntity.getFileAge())
                    .setActive(convertStringToBoolean(fileSchedulerConfigEntity.getIsActive()))
                    .setCloudName(fileSchedulerConfigEntity.getCloudName())
                    .setS3SchedulerConfig(new S3InfoModel().setRegion(fileSchedulerConfigEntity.getRegion())
                            .setBucketName(fileSchedulerConfigEntity.getBucketName())
                            .setAccessKeyId(fileSchedulerConfigEntity.getAccessKeyId())
                            .setSecretKeyId(fileSchedulerConfigEntity.getSecretKeyId()));

    private static final Function<FileSchedulerModel, List<FileScheCloudArcEntity>> mapperToCloudArcEntities =
            fileSchedulerModel ->
                    fileSchedulerModel
                            .getS3SchedulerConfig()
                            .getFilesPathDetails()
                            .stream()
                            .map(filesPathRefModel ->
                                    new FileScheCloudArcEntity().setPkId(RandomStringUtils.randomAlphanumeric(18))
                                            .setTraSchRef("1")
                                            .setSrcRootDir(filesPathRefModel.getSourcePath())
                                            .setDestRootDir(filesPathRefModel.getDestPath()))
                            .collect(Collectors.toList());

    private void validate(FileSchedulerModel fileSchedulerModel) {
        isNullThrowError.apply(fileSchedulerModel.getFileAge(), "fileAge");
        isNullThrowError.apply(fileSchedulerModel.getCloudName(), "cloudName");
        if (fileSchedulerModel.getS3SchedulerConfig() != null) {
            isNullThrowError.apply(fileSchedulerModel.getS3SchedulerConfig().getRegion(), "region");
            isNullThrowError.apply(fileSchedulerModel.getS3SchedulerConfig().getBucketName(), "bucketName");
            isNullThrowError.apply(fileSchedulerModel.getS3SchedulerConfig().getAccessKeyId(), "accessKeyId");
            isNullThrowError.apply(fileSchedulerModel.getS3SchedulerConfig().getSecretKeyId(), "secretKeyId");
            if (fileSchedulerModel.getS3SchedulerConfig().getFilesPathDetails().isEmpty()) {
                throw GlobalExceptionHandler.internalServerError("Please configure at least one source and destination details.");
            }
        } else {
            throw GlobalExceptionHandler.internalServerError("Please provide the cloud details");
        }
    }

    @PreDestroy
    void deleteJob() {
        try {
            filesSchedulerQuartzService.deleteJob();
        } catch (Exception e) {
            //empty is fine here
        }
    }

}
