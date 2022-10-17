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

package com.pe.pcm.application;

import com.pe.pcm.application.entity.ApplicationEntity;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.profile.ProfileModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;
import static com.pe.pcm.miscellaneous.CommonQueryPredicate.getPredicate;
import static com.pe.pcm.utils.CommonFunctions.*;
import static com.pe.pcm.utils.PCMConstants.APPLICATION;

/**
 * @author Chenchu Kiran.
 */
@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    @Autowired
    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    private final Function<ProfileModel, ApplicationEntity> serialize = profileModel ->
            new ApplicationEntity()
                    .setApplicationName(isNullThrowError.apply(profileModel.getProfileName(), "Application Name").trim())
                    .setApplicationId(isNullThrowError.apply(profileModel.getProfileId(), "Application Id").trim())
                    .setEmailId(profileModel.getEmailId())
                    .setPhone(profileModel.getPhone())
                    .setFileAppServer(
                            profileModel.getProtocol().equals("AS2") || profileModel.getProtocol().equals("SFG_CONNECT_DIRECT") ?
                                    "B" : convertBooleanToString(!profileModel.getHubInfo()))
                    .setAppIntegrationProtocol(profileModel.getProtocol())
                    .setAppIsActive(convertBooleanToString(profileModel.getStatus()))
                    .setAppPickupFiles(convertBooleanToString(profileModel.getHubInfo()))
                    .setAppDropFiles(convertBooleanToString(!profileModel.getHubInfo()))
                    .setIpWhitelist(profileModel.getIpWhiteList())
                    .setPgpInfo(profileModel.getPgpInfo())
                    .setPemIdentifier(profileModel.getPemIdentifier());

    public Optional<ApplicationEntity> find(String applicationId) {
        return applicationRepository.findByApplicationId(applicationId);
    }

    @Transactional
    public ApplicationEntity save(ProfileModel profileModel, String parentPrimaryKey, String childPrimaryKey) {
        ApplicationEntity applicationEntity = serialize.apply(profileModel);
        applicationEntity.setPkId(parentPrimaryKey)
                .setAppProtocolRef(childPrimaryKey);
        return applicationRepository.save(applicationEntity);
    }

    public void save(ApplicationEntity applicationEntity) {
        applicationRepository.save(applicationEntity);
    }

    public ApplicationEntity get(String pkId) {
        return applicationRepository.findById(pkId).orElseThrow(() -> notFound(APPLICATION));
    }

    public ApplicationEntity getNoThrow(String pkId) {
        return applicationRepository.findById(pkId).orElse(new ApplicationEntity());
    }

    //Used for pem
    public List<ApplicationEntity> search(ProfileModel profileModel, Boolean isOr, String fileAppServer) {
        return applicationRepository.findAll(getSpecification(profileModel, isOr, fileAppServer))
                .stream()
                .sorted(Comparator.comparing(ApplicationEntity::getApplicationName))
                .collect(Collectors.toList());
    }


    public void delete(ApplicationEntity applicationEntity) {
        applicationRepository.delete(applicationEntity);
    }

    public Page<ApplicationEntity> search(ProfileModel profileModel, Pageable pageable) {
        Page<ApplicationEntity> applicationList = applicationRepository
                .findAll((Specification<ApplicationEntity>) (root, query, cb) -> {
                    List<Predicate> predicates = new ArrayList<>();
                    getPredicate(root, cb, predicates, profileModel.getProfileName(), "applicationName", true);
                    getPredicate(root, cb, predicates, profileModel.getProfileId(), "applicationId", true);
                    getPredicate(root, cb, predicates, profileModel.getProtocol(), "appIntegrationProtocol", false);
                    getPredicate(root, cb, predicates, profileModel.getStatus() != null ? convertBooleanToString(profileModel.getStatus()) : "", "appIsActive", false);
                    return cb.and(predicates.toArray(new Predicate[0]));
                }, pageable);
        return new PageImpl<>(new ArrayList<>(applicationList.getContent()), pageable, applicationList.getTotalElements());
    }

    public List<ApplicationEntity> getAllTemplateApplicationProfiles() {
        return applicationRepository.findAllByApplicationNameContainingIgnoreCaseOrderByApplicationName("template").orElse(new ArrayList<>());
    }

    public List<ApplicationEntity> searchByApplicationName(String application) {
        return applicationRepository.findByApplicationName(application).orElseThrow(() -> notFound("Application"));
    }

    public ApplicationEntity getUniqueApplication(String applicationName) {
        return applicationRepository.findByApplicationName(applicationName).map(applicationEntities -> {
            if (applicationEntities.size() > 1) {
                throw new CommunityManagerServiceException(400, "Given ApplicationName has Multiple Records");
            }
            if (applicationEntities.isEmpty()) {
                throw notFound(applicationName + " " + APPLICATION);
            } else {
                return applicationEntities.get(0);
            }
        }).orElseThrow(() -> notFound(applicationName + " " + APPLICATION));
    }

    List<ApplicationEntity> findAllApplicationProfiles() {
        return applicationRepository.findAllByOrderByApplicationNameAsc().orElse(new ArrayList<>());
    }

    public List<ApplicationEntity> finaAllByProtocol(String protocol) {
        return applicationRepository.findAllByAppIntegrationProtocolOrderByApplicationName(protocol).orElse(new ArrayList<>());
    }

    private Specification<ApplicationEntity> getSpecification(ProfileModel profileModel, Boolean isOr, String fileAppServer) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            getPredicate(root, cb, predicates, profileModel.getProfileName(), "applicationName", true);
            getPredicate(root, cb, predicates, profileModel.getProfileId(), "applicationId", true);
            getPredicate(root, cb, predicates, profileModel.getProtocol(), "appIntegrationProtocol", false);
            getPredicate(root, cb, predicates, profileModel.getStatus() != null ? convertBooleanToString(profileModel.getStatus()) : "", "appIsActive", false);
            if (isNotNull(profileModel.getPgpInfo())) {
                if (profileModel.getPgpInfo().equalsIgnoreCase("true")) {
                    //predicates.add(cb.isNotNull(cb.lower(root.get("pgpInfo"))));
                    predicates.add(cb.and(cb.isNotNull(cb.lower(root.get("pgpInfo"))), cb.notEqual(root.get("pgpInfo"), "")));
                } else if (profileModel.getPgpInfo().equalsIgnoreCase("false")) {
                    //predicates.add(cb.isNull(cb.lower(root.get("pgpInfo"))));
                    predicates.add(cb.or(cb.isNull(cb.lower(root.get("pgpInfo"))), cb.equal(root.get("pgpInfo"), "")));
                }
            }
            if (isNotNull(fileAppServer) && isOr) {
                //getPredicate(root, cb, predicates, fileTpName, "fileTpServer", false);
                Predicate predicate = cb.equal(root.get("fileAppServer"), fileAppServer);
                Predicate predicate1 = cb.equal(root.get("appPickupFiles"), profileModel.getHubInfo() != null ? convertBooleanToString(profileModel.getHubInfo()) : "");
                predicates.add(cb.or(predicate, predicate1));
            } else {
                getPredicate(root, cb, predicates, profileModel.getHubInfo() != null ? convertBooleanToString(profileModel.getHubInfo()) : "", "appPickupFiles", false);
            }

            //checkRoleAndPredicateWithPartner(root, cb, predicates, "pkId", true);

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
