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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pe.pcm.quartz.jobs.FileSchedulerJob;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Trigger;

import java.util.*;

import static org.quartz.JobBuilder.newJob;

/**
 * @author Chenchu Kiran Reddy.
 */
public class FileSchedulerJobDescriptor {

    private String name;
    private String group;
    private String fileAge;
    private boolean bootRun;
    private Map<String, Object> data = new LinkedHashMap<>();
    private List<FileSchedulerTriggerDescriptor> fileSchedulerTriggerDescriptors = new ArrayList<>();

    /**
     * Convenience method for building Triggers of Job
     *
     * @return Triggers for this JobDetail
     */
    @JsonIgnore
    public Set<Trigger> buildTriggers() {
        Set<Trigger> triggers = new LinkedHashSet<>();
        for (FileSchedulerTriggerDescriptor fileSchedulerTriggerDescriptor : fileSchedulerTriggerDescriptors) {
            triggers.add(fileSchedulerTriggerDescriptor.buildTrigger());
        }
        return triggers;
    }

    /**
     * Convenience method that builds a JobDetail
     *
     * @return the JobDetail built from this descriptor
     */
    public JobDetail buildJobDetail() {
        JobDataMap jobDataMap = new JobDataMap(getData());
        jobDataMap.put("fileAge", getFileAge());
        jobDataMap.put("bootRun", isBootRun());
        return newJob(FileSchedulerJob.class)
                .withIdentity(getName(), getGroup())
                .setJobData(jobDataMap)
                .build();
    }

    /**
     * Convenience method that builds a descriptor from JobDetail and Trigger(s)
     *
     * @param jobDetail     the JobDetail instance
     * @param triggersOfJob the Trigger(s) to associate with the Job
     * @return the JobDescriptor
     */
//    @SuppressWarnings("unchecked")
    public static FileSchedulerJobDescriptor buildDescriptor(JobDetail jobDetail, List<? extends Trigger> triggersOfJob) {
        List<FileSchedulerTriggerDescriptor> fileSchedulerTriggerDescriptors = new ArrayList<>();

        for (Trigger trigger : triggersOfJob) {
            fileSchedulerTriggerDescriptors.add(FileSchedulerTriggerDescriptor.buildDescriptor(trigger));
        }

        return new FileSchedulerJobDescriptor()
                .setName(jobDetail.getKey().getName())
                .setGroup(jobDetail.getKey().getGroup())
                .setFileAge((String) jobDetail.getJobDataMap().get("fileAge"))
                .setBootRun((boolean) jobDetail.getJobDataMap().get("bootRun"))
                .setTriggerDescriptors(fileSchedulerTriggerDescriptors);
    }

    public String getName() {
        return name;
    }

    public FileSchedulerJobDescriptor setName(String name) {
        this.name = name;
        return this;
    }

    public String getGroup() {
        return group;
    }

    public FileSchedulerJobDescriptor setGroup(String group) {
        this.group = group;
        return this;
    }

    public List<FileSchedulerTriggerDescriptor> getTriggerDescriptors() {
        return fileSchedulerTriggerDescriptors;
    }

    public FileSchedulerJobDescriptor setTriggerDescriptors(final List<FileSchedulerTriggerDescriptor> fileSchedulerTriggerDescriptors) {
        this.fileSchedulerTriggerDescriptors = fileSchedulerTriggerDescriptors;
        return this;
    }

    public String getFileAge() {
        return fileAge;
    }

    public FileSchedulerJobDescriptor setFileAge(String fileAge) {
        this.fileAge = fileAge;
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public FileSchedulerJobDescriptor setData(Map<String, Object> data) {
        this.data = data;
        return this;
    }

    public boolean isBootRun() {
        return bootRun;
    }

    public FileSchedulerJobDescriptor setBootRun(boolean bootRun) {
        this.bootRun = bootRun;
        return this;
    }
}
