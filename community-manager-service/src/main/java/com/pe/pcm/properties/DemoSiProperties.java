package com.pe.pcm.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Kiran Reddy.
 */
@Component
@ConfigurationProperties(prefix = "file-upload.si")
public class DemoSiProperties {
    private String host;
    private Integer port;
    private String username;
    private String cmks;

    public String getHost() {
        return host;
    }

    public DemoSiProperties setHost(String host) {
        this.host = host;
        return this;
    }

    public Integer getPort() {
        return port;
    }

    public DemoSiProperties setPort(Integer port) {
        this.port = port;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public DemoSiProperties setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getCmks() {
        return cmks;
    }

    public DemoSiProperties setCmks(String cmks) {
        this.cmks = cmks;
        return this;
    }
}
