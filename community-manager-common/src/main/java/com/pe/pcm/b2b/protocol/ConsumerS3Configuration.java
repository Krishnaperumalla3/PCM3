package com.pe.pcm.b2b.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pe.pcm.protocol.AwsS3Model;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsumerS3Configuration implements Serializable {

    private String bucketName;
    private Integer connectionTimeOut;
    private Boolean credentialsRequired;
    private String directory;
    private String endpoint;
    private Integer endpointPort;
    private Integer maxErrorRetryCount;
    private String obscuredaccesskey;
    private String obscuredsecretkey;
    private String password;
    private String proxyHost;
    private Integer proxyPort;
    private String region;
    private Boolean requiredProxy;
    private String userName;

    public ConsumerS3Configuration(AwsS3Model awsS3Model) {
        this.bucketName = awsS3Model.getConsumerS3Configuration().getBucketName();
        this.connectionTimeOut = awsS3Model.getConsumerS3Configuration().getConnectionTimeOut();
        this.credentialsRequired = awsS3Model.getConsumerS3Configuration().getCredentialsRequired();
        this.directory = awsS3Model.getConsumerS3Configuration().getDirectory();
        this.endpoint = awsS3Model.getConsumerS3Configuration().getEndpoint();
        this.endpointPort = awsS3Model.getConsumerS3Configuration().getEndpointPort();
        this.maxErrorRetryCount = awsS3Model.getConsumerS3Configuration().getMaxErrorRetryCount();
        this.obscuredaccesskey = awsS3Model.getConsumerS3Configuration().getObscuredaccesskey();
        this.obscuredsecretkey = awsS3Model.getConsumerS3Configuration().getObscuredsecretkey();
        this.password = awsS3Model.getConsumerS3Configuration().getPassword();
        this.proxyHost = awsS3Model.getConsumerS3Configuration().getProxyHost();
        this.proxyPort = awsS3Model.getConsumerS3Configuration().getProxyPort();
        this.region = awsS3Model.getConsumerS3Configuration().getRegion();
        this.requiredProxy = awsS3Model.getConsumerS3Configuration().getRequiredProxy();
        this.userName = awsS3Model.getConsumerS3Configuration().getUserName();
    }

    public ConsumerS3Configuration() {
    }

    public String getBucketName() {
        return bucketName;
    }

    public ConsumerS3Configuration setBucketName(String bucketName) {
        this.bucketName = bucketName;
        return this;
    }

    public Integer getConnectionTimeOut() {
        return connectionTimeOut;
    }

    public ConsumerS3Configuration setConnectionTimeOut(Integer connectionTimeOut) {
        this.connectionTimeOut = connectionTimeOut;
        return this;
    }

    public Boolean getCredentialsRequired() {
        return credentialsRequired;
    }

    public ConsumerS3Configuration setCredentialsRequired(Boolean credentialsRequired) {
        this.credentialsRequired = credentialsRequired;
        return this;
    }

    public String getDirectory() {
        return directory;
    }

    public ConsumerS3Configuration setDirectory(String directory) {
        this.directory = directory;
        return this;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public ConsumerS3Configuration setEndpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public Integer getEndpointPort() {
        return endpointPort;
    }

    public ConsumerS3Configuration setEndpointPort(Integer endpointPort) {
        this.endpointPort = endpointPort;
        return this;
    }

    public Integer getMaxErrorRetryCount() {
        return maxErrorRetryCount;
    }

    public ConsumerS3Configuration setMaxErrorRetryCount(Integer maxErrorRetryCount) {
        this.maxErrorRetryCount = maxErrorRetryCount;
        return this;
    }

    public String getObscuredaccesskey() {
        return obscuredaccesskey;
    }

    public ConsumerS3Configuration setObscuredaccesskey(String obscuredaccesskey) {
        this.obscuredaccesskey = obscuredaccesskey;
        return this;
    }

    public String getObscuredsecretkey() {
        return obscuredsecretkey;
    }

    public ConsumerS3Configuration setObscuredsecretkey(String obscuredsecretkey) {
        this.obscuredsecretkey = obscuredsecretkey;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public ConsumerS3Configuration setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public ConsumerS3Configuration setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
        return this;
    }

    public Integer getProxyPort() {
        return proxyPort;
    }

    public ConsumerS3Configuration setProxyPort(Integer proxyPort) {
        this.proxyPort = proxyPort;
        return this;
    }

    public String getRegion() {
        return region;
    }

    public ConsumerS3Configuration setRegion(String region) {
        this.region = region;
        return this;
    }

    public Boolean getRequiredProxy() {
        return requiredProxy;
    }

    public ConsumerS3Configuration setRequiredProxy(Boolean requiredProxy) {
        this.requiredProxy = requiredProxy;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public ConsumerS3Configuration setUserName(String userName) {
        this.userName = userName;
        return this;
    }
}
