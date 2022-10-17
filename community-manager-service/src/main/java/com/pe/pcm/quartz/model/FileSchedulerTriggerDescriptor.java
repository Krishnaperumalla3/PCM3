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

package com.pe.pcm.quartz.model;

import org.quartz.JobDataMap;
import org.quartz.Trigger;
import org.springframework.util.StringUtils;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.TimeZone;

import static java.time.ZoneId.systemDefault;
import static java.util.UUID.randomUUID;
import static org.quartz.CronExpression.isValidExpression;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.springframework.util.StringUtils.*;

/**
 * @author Chenchu Kiran Reddy.
 */

public class FileSchedulerTriggerDescriptor {

    private String name;
    private String group;
    private LocalDateTime fireTime;
    private String cron;

    public FileSchedulerTriggerDescriptor setName(final String name) {
        this.name = name;
        return this;
    }

    public FileSchedulerTriggerDescriptor setGroup(final String group) {
        this.group = group;
        return this;
    }

    public FileSchedulerTriggerDescriptor setFireTime(final LocalDateTime fireTime) {
        this.fireTime = fireTime;
        return this;
    }

    public FileSchedulerTriggerDescriptor setCron(final String cron) {
        this.cron = cron;
        return this;
    }

    private String buildName() {
        return hasLength(name) ? name : randomUUID().toString();
    }

    /**
     * Convenience method for building a Trigger
     *
     * @return the Trigger associated with this descriptor
     */
    public Trigger buildTrigger() {
        // @formatter:off
        if (!StringUtils.isEmpty(cron)) {
            if (!isValidExpression(cron))
                throw new IllegalArgumentException("Provided expression " + cron + " is not a valid cron expression");
            return newTrigger()
                    .withIdentity(buildName(), group)
                    .withSchedule(cronSchedule(cron)
                            .withMisfireHandlingInstructionFireAndProceed()
                            .inTimeZone(TimeZone.getTimeZone(systemDefault())))
                    .usingJobData("cron", cron)
                    .build();
        } else if (!isEmpty(fireTime)) {
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("fireTime", fireTime);
            return newTrigger()
                    .withIdentity(buildName(), group)
                    .withSchedule(simpleSchedule()
                            .withMisfireHandlingInstructionNextWithExistingCount())
                    .startAt(Date.from(fireTime.atZone(systemDefault()).toInstant()))
                    .usingJobData(jobDataMap)
                    .build();
        }
        // @formatter:on
        throw new IllegalStateException("unsupported trigger descriptor " + this);
    }

    /**
     * @param trigger the Trigger used to build this descriptor
     * @return the TriggerDescriptor
     */
    public static FileSchedulerTriggerDescriptor buildDescriptor(Trigger trigger) {
        // @formatter:off
        return new FileSchedulerTriggerDescriptor()
                .setName(trigger.getKey().getName())
                .setGroup(trigger.getKey().getGroup())
                .setFireTime((LocalDateTime) trigger.getJobDataMap().get("fireTime"))
                .setCron(trigger.getJobDataMap().getString("cron"));
        // @formatter:on
    }


    public LocalDateTime getFireTime() {
        return fireTime;
    }

    public String getCron() {
        return cron;
    }
}
