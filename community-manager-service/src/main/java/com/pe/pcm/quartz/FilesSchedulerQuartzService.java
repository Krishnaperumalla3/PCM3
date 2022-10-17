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

package com.pe.pcm.quartz;

import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.quartz.model.FileSchedulerJobDescriptor;
import com.pe.pcm.quartz.model.FileSchedulerTriggerDescriptor;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static org.quartz.JobKey.jobKey;

/**
 * @author Kiran Reddy.
 */
@Service
@Transactional
public class FilesSchedulerQuartzService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FilesSchedulerQuartzService.class);

    private static final String NAME = "file-scheduler";
    private static final String GROUP = "file-archive-group";

    private final Scheduler scheduler;

    @Autowired
    public FilesSchedulerQuartzService(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void createOrUpdateScheduler(String cron, String fileAge) {
        deleteJob();
        createJob(getJobDescriptor(cron).setFileAge(fileAge));
    }

    private void createJob(FileSchedulerJobDescriptor descriptor) {
        JobDetail jobDetail = descriptor.buildJobDetail();
        Set<Trigger> triggersForJob = descriptor.buildTriggers();
        LOGGER.info("About to save job with key - {}", jobDetail.getKey());
        try {
            scheduler.scheduleJob(jobDetail, triggersForJob, false);
            LOGGER.info("Job with key - {}  saved successfully.", jobDetail.getKey());
            LOGGER.info("Successfully created the Scheduler");
        } catch (SchedulerException e) {
            throw GlobalExceptionHandler.internalServerError(e.getLocalizedMessage());
        }
    }

    public void deleteJob() {
        try {
            scheduler.deleteJob(jobKey(NAME, GROUP));
            LOGGER.info("Deleted job with key - {} {}", NAME, GROUP);
        } catch (SchedulerException e) {
            LOGGER.info("Could not delete job with key {} {} due to error - {}", NAME, GROUP, e.getLocalizedMessage());
        }
    }

    public void pauseJob(String group, String name) {
        try {
            scheduler.pauseJob(jobKey(name, group));
            LOGGER.info("Paused job with key - {}.{}", group, name);
        } catch (SchedulerException e) {
            LOGGER.info("Could not pause job with key - {}.{} due to error - {}", group, name, e.getLocalizedMessage());
        }
    }

    public void resumeJob(String group, String name) {
        try {
            scheduler.resumeJob(jobKey(name, group));
            LOGGER.info("Resumed job with key - {}.{}", group, name);
        } catch (SchedulerException e) {
            LOGGER.info("Could not resume job with key - {}.{} due to error - {}", group, name, e.getLocalizedMessage());
        }
    }

    @Transactional(readOnly = true)
    public Optional<FileSchedulerJobDescriptor> findJob(String group, String name) {
        try {
            JobDetail jobDetail = scheduler.getJobDetail(jobKey(name, group));
            if (Objects.nonNull(jobDetail))
                return Optional.of(
                        FileSchedulerJobDescriptor.buildDescriptor(jobDetail,
                                scheduler.getTriggersOfJob(jobKey(name, group))));
        } catch (SchedulerException e) {
            LOGGER.info("Could not find job with key - {}.{} due to error - {}", group, name, e.getLocalizedMessage());
        }
        LOGGER.info("Could not find job with key - {}.{}", group, name);
        return Optional.empty();
    }

    private FileSchedulerJobDescriptor getJobDescriptor(String cron) {
        return new FileSchedulerJobDescriptor().setName(NAME)
                .setGroup(GROUP)
                .setTriggerDescriptors(Collections.singletonList(new FileSchedulerTriggerDescriptor().setGroup(GROUP)
                        .setName(NAME)
                        .setCron(cron)));
    }

}
