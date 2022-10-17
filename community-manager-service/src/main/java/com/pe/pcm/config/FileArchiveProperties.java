package com.pe.pcm.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.StringJoiner;

/**
 * @author Kiran Reddy.
 */
@Component
@ConfigurationProperties(prefix = "file.archive")
public class FileArchiveProperties {

    private Scheduler scheduler;
    private Pgp pgp;
    private Aes aes;

    public Scheduler getScheduler() {
        return scheduler;
    }

    public FileArchiveProperties setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
        return this;
    }

    public Pgp getPgp() {
        return pgp;
    }

    public FileArchiveProperties setPgp(Pgp pgp) {
        this.pgp = pgp;
        return this;
    }

    public Aes getAes() {
        return aes;
    }

    public FileArchiveProperties setAes(Aes aes) {
        this.aes = aes;
        return this;
    }

    public static class Scheduler {
        private String cron;
        private FileJob deleteFilesJob;

        public String getCron() {
            if (StringUtils.hasText(cron)) {
                return cron;
            }
            return "0 0 0 ? * * *";
        }

        public Scheduler setCron(String cron) {
            this.cron = cron;
            return this;
        }

        public FileJob getDeleteFilesJob() {
            return deleteFilesJob;
        }

        public Scheduler setDeleteFilesJob(FileJob deleteFilesJob) {
            this.deleteFilesJob = deleteFilesJob;
            return this;
        }

        public static class FileJob {
            private boolean active;
            private String scriptFileLoc;

            public boolean isActive() {
                return active;
            }

            public FileJob setActive(boolean active) {
                this.active = active;
                return this;
            }

            public String getScriptFileLoc() {
                if (StringUtils.hasText(scriptFileLoc)) {
                    return scriptFileLoc;
                }
                return "classpath:CMArchiveDelete.sh";
            }

            public FileJob setScriptFileLoc(String scriptFileLoc) {
                this.scriptFileLoc = scriptFileLoc;
                return this;
            }

            @Override
            public String toString() {
                return new StringJoiner(", ", FileJob.class.getSimpleName() + "[", "]")
                        .add("active=" + active)
                        .add("scriptFileLoc='" + scriptFileLoc + "'")
                        .toString();
            }
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Scheduler.class.getSimpleName() + "[", "]")
                    .add("cron='" + cron + "'")
                    .add("deleteFilesJob=" + deleteFilesJob)
                    .toString();
        }
    }

    public static class Pgp {
        private String privateKey;
        private String cmks;

        public String getPrivateKey() {
            return privateKey;
        }

        public Pgp setPrivateKey(String privateKey) {
            this.privateKey = privateKey;
            return this;
        }

        public String getCmks() {
            return cmks;
        }

        public Pgp setCmks(String cmks) {
            this.cmks = cmks;
            return this;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Pgp.class.getSimpleName() + "[", "]")
                    .add("privateKey='" + privateKey + "'")
                    .add("cmks='" + cmks + "'")
                    .toString();
        }
    }

    public static class Aes {
        private String secretKey;
        private String salt;

        public String getSecretKey() {
            return secretKey;
        }

        public Aes setSecretKey(String secretKey) {
            this.secretKey = secretKey;
            return this;
        }

        public String getSalt() {
            return salt;
        }

        public Aes setSalt(String salt) {
            this.salt = salt;
            return this;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Aes.class.getSimpleName() + "[", "]")
                    .add("secretKey='" + secretKey + "'")
                    .add("salt='" + salt + "'")
                    .toString();
        }
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", FileArchiveProperties.class.getSimpleName() + "[", "]")
                .add("scheduler=" + scheduler)
                .add("pgp=" + pgp)
                .add("aes=" + aes)
                .toString();
    }
}
