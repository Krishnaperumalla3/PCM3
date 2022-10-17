package com.pe.pcm.pem;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PemUpdateContractModel {

    private String contractName;
    private String bpName;

    public String getContactName() {
        return contractName;
    }

    public PemUpdateContractModel setContactName(String contactName) {
        this.contractName = contactName;
        return this;
    }

    public String getBpName() {
        return bpName;
    }

    public PemUpdateContractModel setBpName(String bpName) {
        this.bpName = bpName;
        return this;
    }


}
