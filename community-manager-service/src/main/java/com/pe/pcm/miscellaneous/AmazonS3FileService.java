/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc.
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

package com.pe.pcm.miscellaneous;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.pe.pcm.aws.s3.AmazonS3ClientService;
import com.pe.pcm.config.FileSchedulerModel;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;

/**
 * @author Kiran Reddy.
 */
@Service
public class AmazonS3FileService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AmazonS3FileService.class);

    private final AmazonS3ClientService amazonS3ClientService;

    @Autowired
    public AmazonS3FileService(AmazonS3ClientService amazonS3ClientService) {
        this.amazonS3ClientService = amazonS3ClientService;
    }

    public void loadFileFromS3(File tempFile, String originalFilePath, FileSchedulerModel fileSchedulerModel) {
        List<String> finalPaths = new ArrayList<>();
        fileSchedulerModel.getS3SchedulerConfig().getFilesPathDetails().forEach(filesPathRefModel -> {
            if (originalFilePath.contains(filesPathRefModel.getSourcePath())) {
                finalPaths.add(originalFilePath.replace(filesPathRefModel.getSourcePath(), filesPathRefModel.getDestPath()).replace("\\", "/"));
            }
        });
        LOGGER.info("Loading the file from S3");
        if (finalPaths.isEmpty()) {
            throw internalServerError("Cloud destination folder not configured, please check with admin");
        }
        AtomicBoolean fileFound = new AtomicBoolean(Boolean.FALSE);
        for (String finalPath : finalPaths) {
            LOGGER.info("Loading from Path: {}", finalPath);
            if (!fileFound.get()) {
                try (S3Object s3Object = amazonS3ClientService.loadAmazonS3Client(fileSchedulerModel).getObject(new GetObjectRequest(amazonS3ClientService.getBucketName(), finalPath))) {
                    FileUtils.copyInputStreamToFile(s3Object.getObjectContent(), tempFile);
                    fileFound.set(Boolean.TRUE);
                    return;
                } catch (AmazonS3Exception ae) {
                    LOGGER.error("{}", ae.getErrorMessage());
                    if (!ae.getErrorCode().equalsIgnoreCase("NoSuchKey")) {
                        throw internalServerError(ae.getErrorMessage());
                    }
                } catch (IOException io) {
                    throw internalServerError("Unable to download the file, Please try after sometime.");
                }
            }
        }
        if (!fileFound.get()) {
            throw internalServerError("File not available in given AWS S3 path: " + finalPaths);
        }
    }

}
