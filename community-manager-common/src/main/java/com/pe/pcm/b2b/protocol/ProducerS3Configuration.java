package com.pe.pcm.b2b.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pe.pcm.protocol.AwsS3Model;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProducerS3Configuration implements Serializable {

    private Integer connectionRetries;
    private Integer connectionTimeout;
    private String endPointUrl;
    private String obscuredAccessKey;
    private String obscuredSecretKey;
    private Boolean proxyServer;
    private String queueName;
    private String region;
    private Boolean credentialsRequired;
    private String password;
    private String proxyHost;
    private Integer proxyPort;
    private String proxyuserName;

    public ProducerS3Configuration(AwsS3Model awsS3Model) {
        this.connectionRetries = awsS3Model.getProducerS3Configuration().getConnectionRetries();
        this.connectionTimeout = awsS3Model.getProducerS3Configuration().getConnectionTimeout();
        this.endPointUrl = awsS3Model.getProducerS3Configuration().getEndPointUrl();
        this.obscuredAccessKey = awsS3Model.getProducerS3Configuration().getObscuredAccessKey();
        this.obscuredSecretKey = awsS3Model.getProducerS3Configuration().getObscuredSecretKey();
        this.proxyServer = awsS3Model.getProducerS3Configuration().getProxyServer();
        this.queueName = awsS3Model.getProducerS3Configuration().getQueueName();
        this.region = awsS3Model.getProducerS3Configuration().getRegion();
        this.credentialsRequired = awsS3Model.getProducerS3Configuration().getCredentialsRequired();
        this.password = awsS3Model.getProducerS3Configuration().getPassword();
        this.proxyHost = awsS3Model.getProducerS3Configuration().getProxyHost();
        this.proxyPort = awsS3Model.getProducerS3Configuration().getProxyPort();
        this.proxyuserName = awsS3Model.getProducerS3Configuration().getProxyuserName();
    }

    public ProducerS3Configuration() {

    }

    public Integer getConnectionRetries() {
        return connectionRetries;
    }

    @JsonProperty("ConnectionRetries")
    public ProducerS3Configuration setConnectionRetries(Integer connectionRetries) {
        this.connectionRetries = connectionRetries;
        return this;
    }

    public Integer getConnectionTimeout() {
        return connectionTimeout;
    }

    @JsonProperty("ConnectionTimeout")
    public ProducerS3Configuration setConnectionTimeout(Integer connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
        return this;
    }

    public String getEndPointUrl() {
        return endPointUrl;
    }

    @JsonProperty("EndPointUrl")
    public ProducerS3Configuration setEndPointUrl(String endPointUrl) {
        this.endPointUrl = endPointUrl;
        return this;
    }

    public String getObscuredAccessKey() {
        return obscuredAccessKey;
    }

    @JsonProperty("ObscuredAccessKey")
    public ProducerS3Configuration setObscuredAccessKey(String obscuredAccessKey) {
        this.obscuredAccessKey = obscuredAccessKey;
        return this;
    }

    public String getObscuredSecretKey() {
        return obscuredSecretKey;
    }

    @JsonProperty("ObscuredSecretKey")
    public ProducerS3Configuration setObscuredSecretKey(String obscuredSecretKey) {
        this.obscuredSecretKey = obscuredSecretKey;
        return this;
    }

    public Boolean getProxyServer() {
        return proxyServer;
    }

    @JsonProperty("ProxyServer")
    public ProducerS3Configuration setProxyServer(Boolean proxyServer) {
        this.proxyServer = proxyServer;
        return this;
    }

    public String getQueueName() {
        return queueName;
    }

    @JsonProperty("QueueName")
    public ProducerS3Configuration setQueueName(String queueName) {
        this.queueName = queueName;
        return this;
    }

    public String getRegion() {
        return region;
    }

    @JsonProperty("Region")
    public ProducerS3Configuration setRegion(String region) {
        this.region = region;
        return this;
    }

    public Boolean getCredentialsRequired() {
        return credentialsRequired;
    }

    public ProducerS3Configuration setCredentialsRequired(Boolean credentialsRequired) {
        this.credentialsRequired = credentialsRequired;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public ProducerS3Configuration setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public ProducerS3Configuration setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
        return this;
    }

    public Integer getProxyPort() {
        return proxyPort;
    }

    public ProducerS3Configuration setProxyPort(Integer proxyPort) {
        this.proxyPort = proxyPort;
        return this;
    }

    public String getProxyuserName() {
        return proxyuserName;
    }

    public ProducerS3Configuration setProxyuserName(String proxyuserName) {
        this.proxyuserName = proxyuserName;
        return this;
    }
}
