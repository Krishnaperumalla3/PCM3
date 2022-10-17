package com.pe.pcm.pem;

import java.io.Serializable;

/**
 * @author Kiran Reddy.
 */
public class ComplianceInputModel implements Serializable {

    private String url;
    private String method;
    private String username;
    private String password;
    private String input;

    public String getUrl() {
        return url;
    }

    public ComplianceInputModel setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getMethod() {
        return method;
    }

    public ComplianceInputModel setMethod(String method) {
        this.method = method;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public ComplianceInputModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public ComplianceInputModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getInput() {
        return input;
    }

    public ComplianceInputModel setInput(String input) {
        this.input = input;
        return this;
    }
}
