package com.pe.pcm.apiconnecct;

import com.pe.pcm.apiconnecct.entity.APIAuthDataEntity;
import com.pe.pcm.apiconnecct.entity.APIHPDataEntity;
import com.pe.pcm.apiconnecct.entity.APIProxyEndpointEntity;
import com.pe.pcm.apiconnect.*;
import com.pe.pcm.functions.TriFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import static com.pe.pcm.constants.ApiAuthConstants.*;
import static com.pe.pcm.utils.CommonFunctions.*;
import static com.pe.pcm.utils.KeyGeneratorUtil.generatePrimaryKey;
import static org.springframework.util.StringUtils.hasLength;

public class ApiConnectFunctions {

    public static final String SERVER_AUTH = "SERVER_AUTH";
    public static final String PROXY_AUTH = "PROXY_AUTH";

    private ApiConnectFunctions() {
    }

    static final BiFunction<APIProxyEndpointModel, String, APIProxyEndpointEntity> apiProxyEndpointSerialize = (apiProxyEndpointModel, apiPrimaryKey) -> {
        APIProxyEndpointEntity apiProxyEndpointEntity = new APIProxyEndpointEntity();
        apiProxyEndpointEntity.setPkId(apiPrimaryKey)
                .setApiName(apiProxyEndpointModel.getApiName())
                .setProxyUrl(apiProxyEndpointModel.getProxyUrl())
                .setServerUrl(apiProxyEndpointModel.getServerUrl())
                .setMethodName(apiProxyEndpointModel.getServerWebMethod())
                .setReqPayloadSpa(apiProxyEndpointModel.getBody())
                .setApiType(apiProxyEndpointModel.getApiType())
                .setPoolingIntervalMins(apiProxyEndpointModel.getServerWebMethod().equalsIgnoreCase("GET") ?
                        apiProxyEndpointModel.getPoolingInterval() : ""
                );
        return apiProxyEndpointEntity;
    };

    static final TriFunction<APIAuthDataModel, String, String, APIAuthDataEntity> apiAuthDataEntitySerialize = (apiAuthDataModel, apiPrimaryKey, type) -> {
        APIAuthDataEntity apiAuthDataEntity = new APIAuthDataEntity();
        apiAuthDataEntity.setPkId(generatePrimaryKey("APIAS", 20))
                .setApiId(apiPrimaryKey)
                .setApiConfigType(type)
                .setAuthType(apiAuthDataModel.getAuthType());
        if (isNotNull(apiAuthDataModel.getBasicAuth())) {
            apiAuthDataEntity.setUsername(apiAuthDataModel.getBasicAuth().getUsername());
            apiAuthDataEntity.setPassword(apiAuthDataModel.getBasicAuth().getPassword());
        }
        if (isNotNull(apiAuthDataModel.getTokenAuth())) {
            apiAuthDataEntity.setTokenApiUrl(apiAuthDataModel.getTokenAuth().getTokenApiUrl())
                    .setTokenKey(apiAuthDataModel.getTokenAuth().getTokenKey())
                    .setTokenHeader(apiAuthDataModel.getTokenAuth().getTokenHeader())
                    .setTokenPrefix(apiAuthDataModel.getTokenAuth().getTokenPrefix())
                    .setUsername(apiAuthDataModel.getTokenAuth().getUsername())
                    .setPassword(apiAuthDataModel.getTokenAuth().getPassword());
        }
        if (isNotNull(apiAuthDataModel.getoAuth2Auth())) {
            apiAuthDataEntity.setClientId(apiAuthDataModel.getoAuth2Auth().getClientID())
                    .setTokenHeader(apiAuthDataModel.getoAuth2Auth().getTokenHeader())
                    .setTokenPrefix(apiAuthDataModel.getoAuth2Auth().getTokenPrefix())
                    .setTokenApiUrl(apiAuthDataModel.getoAuth2Auth().getTokenApiUrl())
                    .setTokenKey(apiAuthDataModel.getoAuth2Auth().getTokenKey())
                    .setClientSecret(apiAuthDataModel.getoAuth2Auth().getClientSecret())
                    .setGrantType(apiAuthDataModel.getoAuth2Auth().getGrantType())
                    .setResourceOauth(apiAuthDataModel.getoAuth2Auth().getResource())
                    .setScopeOauth(apiAuthDataModel.getoAuth2Auth().getScope())
                    .setUsername(apiAuthDataModel.getoAuth2Auth().getUsername())
                    .setPassword(apiAuthDataModel.getoAuth2Auth().getPassword());
        }
        return apiAuthDataEntity;
    };

    static final BiFunction<APIProxyEndpointModel, String, List<APIHPDataEntity>> apiHpDataEntitySerialize = (apiProxyEndpointModel, apiPrimaryKey) -> {
        List<APIHPDataEntity> apiHpDataEntities = new ArrayList<>();

        apiProxyEndpointModel.getApiHeaderDataList().forEach(apiHpDataModel -> {
            if (hasLength(apiHpDataModel.getKey())) {
                apiHpDataEntities.add(new APIHPDataEntity().setPkId(generatePrimaryKey("APIHS", 20))
                        .setApiId(apiPrimaryKey)
                        .setApiConfigType(SERVER_AUTH)
                        .setHpType("HEADER")
                        .setHpKey(apiHpDataModel.getKey())
                        .setHpValue(apiHpDataModel.getValue())
                        .setDynamicValue(convertBooleanToString(apiHpDataModel.getDynamicValue()))
                        .setRequired(convertBooleanToString(apiHpDataModel.getRequired()))
                        .setHpDescription(apiHpDataModel.getDescription()));
            }
        });
        apiProxyEndpointModel.getApiParamDataList().forEach(apiHpDataModel -> {
            if (hasLength(apiHpDataModel.getKey())) {
                apiHpDataEntities.add(new APIHPDataEntity().setPkId(generatePrimaryKey("APIPS", 20))
                        .setApiId(apiPrimaryKey)
                        .setApiConfigType(SERVER_AUTH)
                        .setHpType("QP")
                        .setHpKey(apiHpDataModel.getKey())
                        .setHpValue(apiHpDataModel.getValue())
                        .setDynamicValue(convertBooleanToString(apiHpDataModel.getDynamicValue()))
                        .setRequired(convertBooleanToString(apiHpDataModel.getRequired()))
                        .setHpDescription(apiHpDataModel.getDescription()));
            }
        });
        return apiHpDataEntities;
    };

    static final TriFunction<APIProxyEndpointEntity, List<APIHPDataEntity>, List<APIAuthDataEntity>, APIProxyEndpointModel> triDeSerialize = ((apiProxyEndpointEntity, apihpDataEntities, apiAuthDataEntities) -> {
        List<APIHPDataModel> apiHpDataModelList = new ArrayList<>();
        List<APIHPDataModel> apiQpDataModelList = new ArrayList<>();
        APIProxyEndpointModel proxyEndpointModel = new APIProxyEndpointModel();
        proxyEndpointModel.setPkId(apiProxyEndpointEntity.getPkId())
                .setApiName(apiProxyEndpointEntity.getApiName())
                .setServerUrl(apiProxyEndpointEntity.getServerUrl())
                .setServerWebMethod(apiProxyEndpointEntity.getMethodName())
                .setProxyUrl(apiProxyEndpointEntity.getProxyUrl())
                .setProxyWebMethod(apiProxyEndpointEntity.getMethodName())
                .setBody(apiProxyEndpointEntity.getReqPayloadSpa())
                .setApiType(apiProxyEndpointEntity.getApiType())
                .setPoolingInterval(apiProxyEndpointEntity.getPoolingIntervalMins());

        apiAuthDataEntities.forEach(apiAuthDataEntity -> {
            if (apiAuthDataEntity.getApiConfigType().equals(SERVER_AUTH)) {
                APIAuthDataModel apiAuthDataModel = new APIAuthDataModel();
                if (apiAuthDataEntity.getAuthType().equals(BASIC_AUTH)) {
                    apiAuthDataModel.setBasicAuth(new BasicAuthModel()
                            .setUsername(apiAuthDataEntity.getUsername())
                            .setPassword(apiAuthDataEntity.getPassword()));
                }
                if (apiAuthDataEntity.getAuthType().equals(TOKEN_AUTH)) {
                    apiAuthDataModel.setTokenAuth(new TokenAuthModel().setTokenApiUrl(apiAuthDataEntity.getTokenApiUrl())
                            .setUsername(apiAuthDataEntity.getUsername())
                            .setPassword(apiAuthDataEntity.getPassword())
                            .setTokenKey(apiAuthDataEntity.getTokenKey())
                            .setTokenHeader(apiAuthDataEntity.getTokenHeader())
                            .setTokenPrefix(apiAuthDataEntity.getTokenPrefix()));
                }
                if (apiAuthDataEntity.getAuthType().equals(OAUTH2_2_0)) {
                    apiAuthDataModel.setoAuth2Auth(new Oauth2AuthModel().setClientID(apiAuthDataEntity.getClientId())
                            .setClientSecret(apiAuthDataEntity.getClientSecret())
                            .setGrantType(apiAuthDataEntity.getGrantType())
                            .setResource(apiAuthDataEntity.getResourceOauth())
                            .setScope(apiAuthDataEntity.getScopeOauth())
                            .setUsername(apiAuthDataEntity.getUsername())
                            .setPassword(apiAuthDataEntity.getPassword())
                            .setTokenApiUrl(apiAuthDataEntity.getTokenApiUrl())
                            .setTokenHeader(apiAuthDataEntity.getTokenHeader())
                            .setTokenKey(apiAuthDataEntity.getTokenKey())
                            .setTokenPrefix(apiAuthDataEntity.getTokenPrefix())
                    );
                }
                apiAuthDataModel.setAuthType(apiAuthDataEntity.getAuthType());
                proxyEndpointModel.setServerApiAuthData(apiAuthDataModel);
            } else if (apiAuthDataEntity.getApiConfigType().equals(PROXY_AUTH)) {
                APIAuthDataModel apiAuthDataModel = new APIAuthDataModel();
                if (apiAuthDataEntity.getAuthType().equals(BASIC_AUTH)) {
                    apiAuthDataModel.setBasicAuth(new BasicAuthModel()
                            .setUsername(apiAuthDataEntity.getUsername())
                            .setPassword(apiAuthDataEntity.getPassword()));
                }
                if (apiAuthDataEntity.getAuthType().equals(TOKEN_AUTH)) {
                    apiAuthDataModel.setTokenAuth(new TokenAuthModel().setTokenApiUrl(apiAuthDataEntity.getTokenApiUrl())
                            .setUsername(apiAuthDataEntity.getUsername())
                            .setPassword(apiAuthDataEntity.getPassword())
                            .setTokenKey(apiAuthDataEntity.getTokenKey())
                            .setTokenHeader(apiAuthDataEntity.getTokenHeader())
                            .setTokenPrefix(apiAuthDataEntity.getTokenPrefix()));
                }
                if (apiAuthDataEntity.getAuthType().equals(OAUTH2_2_0)) {
                    apiAuthDataModel.setoAuth2Auth(new Oauth2AuthModel().setClientID(apiAuthDataEntity.getClientId())
                            .setClientSecret(apiAuthDataEntity.getClientSecret())
                            .setGrantType(apiAuthDataEntity.getGrantType())
                            .setResource(apiAuthDataEntity.getResourceOauth())
                            .setScope(apiAuthDataEntity.getScopeOauth())
                            .setUsername(apiAuthDataEntity.getUsername())
                            .setPassword(apiAuthDataEntity.getPassword())
                            .setTokenApiUrl(apiAuthDataEntity.getTokenApiUrl())
                            .setTokenHeader(apiAuthDataEntity.getTokenHeader())
                            .setTokenKey(apiAuthDataEntity.getTokenKey())
                            .setTokenPrefix(apiAuthDataEntity.getTokenPrefix())
                    );
                }
                apiAuthDataModel.setAuthType(apiAuthDataEntity.getAuthType());
                proxyEndpointModel.setProxyApiAuthData(apiAuthDataModel);
            }
        });
        apihpDataEntities.forEach(apiHpDataEntity -> {
            if (apiHpDataEntity.getApiConfigType().equals(SERVER_AUTH)) {
                if (apiHpDataEntity.getHpType().equals("HEADER")) {
                    apiHpDataModelList.add(
                            new APIHPDataModel().setKey(apiHpDataEntity.getHpKey())
                                    .setValue(apiHpDataEntity.getHpValue())
                                    .setDynamicValue(convertStringToBoolean(apiHpDataEntity.getDynamicValue()))
                                    .setRequired(convertStringToBoolean(apiHpDataEntity.getRequired()))
                                    .setDescription(apiHpDataEntity.getHpDescription())
                    );
                    proxyEndpointModel.setApiHeaderDataList(apiHpDataModelList);
                } else if (apiHpDataEntity.getHpType().equals("QP")) {
                    apiQpDataModelList.add(
                            new APIHPDataModel().setKey(apiHpDataEntity.getHpKey())
                                    .setValue(apiHpDataEntity.getHpValue())
                                    .setDynamicValue(convertStringToBoolean(apiHpDataEntity.getDynamicValue()))
                                    .setRequired(convertStringToBoolean(apiHpDataEntity.getRequired()))
                                    .setDescription(apiHpDataEntity.getHpDescription())
                    );
                    proxyEndpointModel.setApiParamDataList(apiQpDataModelList);
                }
            }
        });
        return proxyEndpointModel;
    });
}
