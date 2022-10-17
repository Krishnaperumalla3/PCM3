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

package com.pe.pcm.aws.s3;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.pe.pcm.config.FileSchedulerModel;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.miscellaneous.PasswordUtilityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author Kiran Reddy.
 */
@Service
public class AmazonS3ClientService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AmazonS3ClientService.class);

    private String bucketName;
    private final PasswordUtilityService passwordUtilityService;

    @Autowired
    public AmazonS3ClientService(PasswordUtilityService passwordUtilityService) {
        this.passwordUtilityService = passwordUtilityService;
    }

    public String getBucketName() {
        return bucketName;
    }


    public AmazonS3 loadAmazonS3Client(FileSchedulerModel fileSchedulerModel) {
        LOGGER.info("Loading Amazon S3 Client");
        if (StringUtils.hasText(fileSchedulerModel.getCloudName()) && fileSchedulerModel.getCloudName().equalsIgnoreCase("AWS-S3")) {
            final BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(fileSchedulerModel.getS3SchedulerConfig().getAccessKeyId(),
                    passwordUtilityService.decrypt(fileSchedulerModel.getS3SchedulerConfig().getSecretKeyId())
            );
            bucketName = fileSchedulerModel.getS3SchedulerConfig().getBucketName();
            return AmazonS3ClientBuilder
                    .standard()
                    .withRegion(fileSchedulerModel.getS3SchedulerConfig().getRegion())
                    .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                    .build();
        } else {
            throw GlobalExceptionHandler.internalServerError("Unable to create Amazon S3 Client, Please the configuration.");
        }
    }

}
