package com.pe.pcm.apiworkflow;

import com.pe.pcm.apiworkflow.entity.ProcessApiEntity;

import java.util.function.BiFunction;

public class ApiWorkFlowFunctions {

    public ApiWorkFlowFunctions() {
    }

    public static BiFunction<String, String, ProcessApiEntity> serializeToProcessApiEntity = (profileName, pkid) ->
            new ProcessApiEntity()
                    .setSeqId(pkid)
                    .setProfileId(profileName);
}
