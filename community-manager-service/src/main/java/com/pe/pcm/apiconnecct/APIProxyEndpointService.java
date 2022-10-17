/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://pragmaedge.com/licenseagreement
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pe.pcm.apiconnecct;

import com.pe.pcm.apiconnecct.entity.APIAuthDataEntity;
import com.pe.pcm.apiconnecct.entity.APIHPDataEntity;
import com.pe.pcm.apiconnecct.entity.APIProxyEndpointEntity;
import com.pe.pcm.apiconnect.APIProxyEndpointModel;
import com.pe.pcm.apiworkflow.ManageApiWorkflowService;
import com.pe.pcm.common.CommunityManagerNameModel;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.properties.OAuth2Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.pe.pcm.apiconnecct.ApiConnectFunctions.*;
import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;
import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;
import static com.pe.pcm.miscellaneous.CommonQueryPredicate.getPredicate;
import static com.pe.pcm.utils.CommonFunctions.isNotNull;
import static com.pe.pcm.utils.KeyGeneratorUtil.generatePrimaryKey;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.util.StringUtils.hasLength;

/**
 * @author Kiran Reddy.
 */
@Service
public class APIProxyEndpointService {
    private static final Logger LOGGER = LoggerFactory.getLogger(APIProxyEndpointService.class);

    private final APIProxyEndpointRepository apiProxyEndpointRepository;
    private final APIAuthDataRepository apiAuthDataRepository;
    private final APIHPDataRepository apihpDataRepository;
    private final OAuth2Properties oAuth2Properties;
    private final ManageApiWorkflowService manageApiWorkflowService;

    @Autowired
    public APIProxyEndpointService(APIProxyEndpointRepository apiProxyEndpointRepository,
                                   APIAuthDataRepository apiAuthDataRepository,
                                   APIHPDataRepository apihpDataRepository,
                                   OAuth2Properties oAuth2Properties,
                                   ManageApiWorkflowService manageApiWorkflowService) {
        this.apiProxyEndpointRepository = apiProxyEndpointRepository;
        this.apiAuthDataRepository = apiAuthDataRepository;
        this.apihpDataRepository = apihpDataRepository;
        this.oAuth2Properties = oAuth2Properties;
        this.manageApiWorkflowService = manageApiWorkflowService;
    }

    @Transactional
    public void create(APIProxyEndpointModel apiProxyEndpointModel) {
        LOGGER.info("API Proxy Endpoint creating");
        findByApiNameAndWebMethod(apiProxyEndpointModel.getApiName(), apiProxyEndpointModel.getProxyWebMethod()).ifPresent(apiProxyEndpointEntity -> {
            throw GlobalExceptionHandler.conflict("Proxy Endpoint");
        });
        save(apiProxyEndpointModel);
        LOGGER.info("API Proxy Endpoint created.");
    }

    @Transactional
    public void update(APIProxyEndpointModel apiProxyEndpointModel) {
        findByApiNameAndWebMethod(apiProxyEndpointModel.getApiName(), apiProxyEndpointModel.getProxyWebMethod()).ifPresent(apiProxyEndpointEntity -> {
            if (!apiProxyEndpointModel.getPkId().equals(apiProxyEndpointEntity.getPkId())) {
                throw internalServerError("ApiName is already exist.");
            }
        });
        String oldApiName;
        Optional<APIProxyEndpointEntity> apiProxyEndpointEntityOptional = apiProxyEndpointRepository.findById(apiProxyEndpointModel.getPkId());
        if (apiProxyEndpointEntityOptional.isPresent()) {
            oldApiName = apiProxyEndpointEntityOptional.get().getApiName();
            delete(apiProxyEndpointEntityOptional.get());
        } else {
            throw notFound("API Proxy Endpoint");
        }

        if (!oldApiName.equals(apiProxyEndpointModel.getApiName())) {
            manageApiWorkflowService.updateApiName(oldApiName, apiProxyEndpointModel.getApiName());
        }
        save(apiProxyEndpointModel);
    }

    private void save(APIProxyEndpointModel apiProxyEndpointModel) {
        final String pkId = getPrimaryKey(apiProxyEndpointModel.getPkId());
        apiProxyEndpointRepository.save(apiProxyEndpointSerialize.apply(apiProxyEndpointModel, pkId));
        if (isNotNull(apiProxyEndpointModel.getProxyApiAuthData())) {
            apiAuthDataRepository.save(apiAuthDataEntitySerialize.apply(apiProxyEndpointModel.getProxyApiAuthData(), pkId, PROXY_AUTH));
        }
        if (isNotNull(apiProxyEndpointModel.getServerApiAuthData())) {
            apiAuthDataRepository.save(apiAuthDataEntitySerialize.apply(apiProxyEndpointModel.getServerApiAuthData(), pkId, SERVER_AUTH));
        }
        apihpDataRepository.saveAll(apiHpDataEntitySerialize.apply(apiProxyEndpointModel, pkId));
    }

    @Transactional
    public void delete(String pkId) {

        apiProxyEndpointRepository.deleteById(pkId);
        apihpDataRepository.deleteAllByApiId(pkId);
        apiAuthDataRepository.deleteByApiId(pkId);
    }

    private void delete(APIProxyEndpointEntity apiProxyEndpointEntity) {
        apiProxyEndpointRepository.delete(apiProxyEndpointEntity);
        apihpDataRepository.deleteAllByApiId(apiProxyEndpointEntity.getPkId());
        apiAuthDataRepository.deleteByApiId(apiProxyEndpointEntity.getPkId());
    }

    public APIProxyEndpointModel get(String pkId) {
        Optional<APIProxyEndpointEntity> apiProxyEndpointEntity = apiProxyEndpointRepository.findById(pkId);
        Optional<List<APIAuthDataEntity>> apiAuthDataEntities = apiAuthDataRepository.findAllByApiId(pkId);
        Optional<List<APIHPDataEntity>> apiHpDataEntities = apihpDataRepository.findAllByApiId(pkId);
        if (apiProxyEndpointEntity.isPresent()) {
            return triDeSerialize.apply(apiProxyEndpointEntity.get(), apiHpDataEntities.orElseGet(ArrayList::new)
                    , apiAuthDataEntities.orElseGet(ArrayList::new));
        } else {
            throw GlobalExceptionHandler.notFound("API Proxy Endpoint");
        }
    }

    public APIProxyEndpointModel findByApiNameAndMethodName(String apiName, String methodName) {
        Optional<APIProxyEndpointEntity> apiProxyEndpointEntity = apiProxyEndpointRepository.findFirstByApiNameAndMethodName(apiName, methodName);
        Optional<List<APIHPDataEntity>> apiHpDataEntities;
        Optional<List<APIAuthDataEntity>> apiAuthDataEntities;
        if (apiProxyEndpointEntity.isPresent()) {
            apiAuthDataEntities = apiAuthDataRepository.findAllByApiId(apiProxyEndpointEntity.get().getPkId());
            apiHpDataEntities = apihpDataRepository.findAllByApiId(apiProxyEndpointEntity.get().getPkId());
        } else {
            throw GlobalExceptionHandler.customError(NOT_FOUND.value(), "API endpoint is not available, please configure the endpoint. endpoint: /restapi/ " + apiName);
        }
        return triDeSerialize.apply(apiProxyEndpointEntity.get(), apiHpDataEntities.orElseGet(ArrayList::new)
                , apiAuthDataEntities.orElseGet(ArrayList::new));
    }

    public Optional<APIProxyEndpointEntity> findByApiNameAndWebMethod(String apiName, String methodName) {
        return apiProxyEndpointRepository.findFirstByApiNameAndMethodName(apiName, methodName);
    }

    public Page<APIProxyEndpointEntity> search(APIProxyEndpointModel apiProxyEndpointModel, Pageable pageable) {
        Page<APIProxyEndpointEntity> endPointList = apiProxyEndpointRepository
                .findAll((Specification<APIProxyEndpointEntity>) (root, query, cb) -> {
                    List<Predicate> predicates = new ArrayList<>();
                    getPredicate(root, cb, predicates, apiProxyEndpointModel.getApiName(), "apiName", true);
                    getPredicate(root, cb, predicates, apiProxyEndpointModel.getServerWebMethod(), "methodName", true);
                    getPredicate(root, cb, predicates, apiProxyEndpointModel.getServerUrl(), "serverUrl", false);
                    return cb.and(predicates.toArray(new Predicate[0]));
                }, pageable);
        return new PageImpl<>(new ArrayList<>(endPointList.getContent()), pageable, endPointList.getTotalElements());
    }

    public List<CommunityManagerNameModel> getUniqueApiNames() {
        return apiProxyEndpointRepository.findAllByApiNameUnique()
                .stream()
                .map(CommunityManagerNameModel::new)
                .collect(Collectors.toList());
    }

    private String getPrimaryKey(String pkId) {
        if (hasLength(pkId)) {
            return pkId;
        } else {
            return generatePrimaryKey("API", 20);
        }
    }

    public OAuth2Properties getOAuth2Details() {
        return oAuth2Properties;
    }

    public List<CommunityManagerNameModel> getMethodsByAPIName(String apiName) {
        return apiProxyEndpointRepository.findAllMethodNamesByApiName(apiName).stream()
                .map(CommunityManagerNameModel::new)
                .collect(Collectors.toList());
    }

}
