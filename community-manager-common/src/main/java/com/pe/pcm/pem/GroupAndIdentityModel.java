package com.pe.pcm.pem;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * @author Shameer.v.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupAndIdentityModel implements Serializable {


    private List<GroupModel> groups;
    private String identity;

    public List<GroupModel> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupModel> groups) {
        this.groups = groups;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }
}
