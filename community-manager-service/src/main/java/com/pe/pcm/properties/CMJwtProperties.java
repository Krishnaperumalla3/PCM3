package com.pe.pcm.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import static org.springframework.util.StringUtils.hasText;

/**
 * @author Kiran Reddy.
 */
@Component
@ConfigurationProperties(prefix = "jwt")
public class CMJwtProperties {
    private String secretkey;
    private Integer sessionExpire;
    private String schedulerCron;

    public String getSecretkey() {
        if (hasText(secretkey)) {
            return secretkey;
        }
        return "CACE9E5A149ED201C4033C1A1E02C9BE";
    }

    public CMJwtProperties setSecretkey(String secretkey) {
        this.secretkey = secretkey;
        return this;
    }

    public Integer getSessionExpire() {
        if (sessionExpire != null) {
            return sessionExpire;
        }
        return 15;
    }

    public CMJwtProperties setSessionExpire(Integer sessionExpire) {
        if (sessionExpire != null) {
            if (sessionExpire < 5) {
                this.sessionExpire = 15;
            } else if (sessionExpire > 240) {
                this.sessionExpire = 240;
            } else {
                this.sessionExpire = sessionExpire;
            }
        } else {
            this.sessionExpire = 15;
        }
        return this;
    }

    public String getSchedulerCron() {
        if (hasText(schedulerCron)) {
            return schedulerCron;
        }
        return "0 0 0 ? * *";
    }

    public CMJwtProperties setSchedulerCron(String schedulerCron) {
        this.schedulerCron = schedulerCron;
        return this;
    }
}
