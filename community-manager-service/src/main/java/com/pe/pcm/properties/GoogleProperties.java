package com.pe.pcm.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "google")
public class GoogleProperties {
    private String applicationName;

    public String getApplicationName() {
        return applicationName;
    }

    public GoogleProperties setApplicationName(String applicationName) {
        this.applicationName = applicationName;
        return this;
    }
}


