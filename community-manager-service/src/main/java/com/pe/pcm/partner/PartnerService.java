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

package com.pe.pcm.partner;

import com.pe.pcm.common.CommunityManagerKeyValueModel;
import com.pe.pcm.constants.AuthoritiesConstants;
import com.pe.pcm.enums.Protocol;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.group.GroupService;
import com.pe.pcm.miscellaneous.UserUtilityService;
import com.pe.pcm.partner.entity.PartnerEntity;
import com.pe.pcm.profile.ProfileModel;
import com.pe.pcm.user.TpUserRepository;
import com.pe.pcm.user.entity.GroupEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.pe.pcm.constants.AuthoritiesConstants.SUPER_ADMIN;
import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;
import static com.pe.pcm.miscellaneous.CommonQueryPredicate.getPredicate;
import static com.pe.pcm.utils.CommonFunctions.*;
import static com.pe.pcm.utils.PCMConstants.PARTNER;
import static com.pe.pcm.utils.PCMConstants.TEMPLATE;
import static java.lang.Boolean.TRUE;

/**
 * @author Kiran Reddy.
 */
@Service
public class PartnerService {

    private final PartnerRepository partnerRepository;
    private final TpUserRepository tpUserRepository;
    private final GroupService groupService;
    private final UserUtilityService userUtilityService;

    @Autowired
    public PartnerService(PartnerRepository partnerRepository, TpUserRepository tpUserRepository, GroupService groupService, UserUtilityService userUtilityService) {
        this.partnerRepository = partnerRepository;
        this.tpUserRepository = tpUserRepository;
        this.groupService = groupService;
        this.userUtilityService = userUtilityService;
    }

    private final Function<ProfileModel, PartnerEntity> serialize = profileModel ->
            new PartnerEntity()
                    .setTpName(isNullThrowError.apply(profileModel.getProfileName(), "Partner Name").trim())
                    .setTpId(isNullThrowError.apply(profileModel.getProfileId(), "Partner Id").trim())
                    .setCustomTpName(profileModel.getCustomProfileName())
                    .setPgpInfo(profileModel.getPgpInfo())
                    .setIpWhitelist(profileModel.getIpWhiteList())
                    .setAddressLine1(profileModel.getAddressLine1())
                    .setAddressLine2(profileModel.getAddressLine2())
                    .setEmail(profileModel.getEmailId())
                    .setPhone(profileModel.getPhone())
                    .setTpProtocol(profileModel.getProtocol())
                    .setTpPickupFiles(convertBooleanToString(profileModel.getHubInfo()))
                    //On Alok Request we did this on 18-05-2021, CD and AS2 Protocols are bi-directional. It won't be Y or N. It has to be both.
                    .setFileTpServer(
                            profileModel.getProtocol().equals(Protocol.AS2.getProtocol()) || profileModel.getProtocol().equals(Protocol.SFG_CONNECT_DIRECT.getProtocol()) ?
                                    "B" : convertBooleanToString(!profileModel.getHubInfo()))
                    .setIsProtocolHubInfo(convertBooleanToString(profileModel.getHubInfo()))
                    .setStatus(convertBooleanToString(profileModel.getStatus()))
                    .setIsOnlyPcm(convertBooleanToString(profileModel.isOnlyPCM()))
                    .setPemIdentifier(profileModel.getPemIdentifier());

    public Optional<PartnerEntity> find(String partnerId) {
        return partnerRepository.findByTpId(partnerId);
    }

    @Transactional
    public PartnerEntity save(ProfileModel profileModel, String parentPrimaryKey, String childPrimaryKey) {
        PartnerEntity partnerEntity = serialize.apply(profileModel);
        partnerEntity.setPkId(parentPrimaryKey)
                .setPartnerProtocolRef(childPrimaryKey);
        return partnerRepository.save(partnerEntity);
    }

    public void save(PartnerEntity partnerEntity) {
        partnerRepository.save(partnerEntity);
    }

    public PartnerEntity get(String pkId) {
        return partnerRepository.findById(pkId).orElseThrow(() -> notFound(PARTNER));
    }

    public PartnerEntity getNoThrow(String pkId) {
        return partnerRepository.findById(pkId).orElse(new PartnerEntity());
    }

    public PartnerEntity findPartnerById(String pkId) {
        return partnerRepository.findById(pkId).orElseThrow(() -> notFound(PARTNER));
    }

    public PartnerEntity getUniquePartner(String partnerName) {
        return partnerRepository.findByTpName(partnerName).map(partnerEntities -> {
            if (partnerEntities.size() > 1) {
                throw new CommunityManagerServiceException(400, "Given Partner name has Multiple Records");
            }
            return !partnerEntities.isEmpty() ? partnerEntities.get(0) : new PartnerEntity();
        }).orElseThrow(() -> notFound(partnerName + " " + PARTNER));
    }

    public void delete(PartnerEntity partnerEntity) {
        partnerRepository.delete(partnerEntity);
    }

    public Page<PartnerEntity> search(ProfileModel profileModel, Pageable pageable) {
        Page<PartnerEntity> partnerList = partnerRepository
                .findAll(getSpecification(profileModel, false, null), pageable);
        // return mapEntityPageIntoDTOPage(pageable, partnerList)
        return new PageImpl<>(new ArrayList<>(partnerList.getContent()), pageable, partnerList.getTotalElements());
    }

    //Used for pem
    public List<PartnerEntity> search(ProfileModel profileModel, Boolean isOr, String fileTpServer) {
        isOr = isNotNull(isOr) ? isOr : false;
        return partnerRepository.findAll(getSpecification(profileModel, isOr, fileTpServer))
                .stream()
                .sorted(Comparator.comparing(PartnerEntity::getTpName))
                .collect(Collectors.toList());
    }

    private Specification<PartnerEntity> getSpecification(ProfileModel profileModel, boolean isOr, String fileTpServer) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            getPredicate(root, cb, predicates, profileModel.getProfileName(), "tpName", profileModel.getLike() != null ? profileModel.getLike() : TRUE);
            getPredicate(root, cb, predicates, profileModel.getProfileId(), "tpId", true);
            getPredicate(root, cb, predicates, profileModel.getProtocol(), "tpProtocol", false);
            getPredicate(root, cb, predicates, profileModel.getStatus() != null ? convertBooleanToString(profileModel.getStatus()) : "", "status", false);
            if (isNotNull(profileModel.getPgpInfo())) {
                if (profileModel.getPgpInfo().equalsIgnoreCase("true")) {
                    predicates.add(cb.and(cb.isNotNull(cb.lower(root.get("pgpInfo"))), cb.notEqual(root.get("pgpInfo"), "")));
                } else if (profileModel.getPgpInfo().equalsIgnoreCase("false")) {
                    predicates.add(cb.or(cb.isNull(cb.lower(root.get("pgpInfo"))), cb.equal(root.get("pgpInfo"), "")));
                }
            }
            if (isNotNull(fileTpServer) && isOr) {
                //getPredicate(root, cb, predicates, fileTpName, "fileTpServer", false);
                Predicate predicate = cb.equal(root.get("fileTpServer"), fileTpServer);
                Predicate predicate1 = cb.equal(root.get("isProtocolHubInfo"), profileModel.getHubInfo() != null ? convertBooleanToString(profileModel.getHubInfo()) : "");
                predicates.add(cb.or(predicate, predicate1));
            } else {
                getPredicate(root, cb, predicates, profileModel.getHubInfo() != null ? convertBooleanToString(profileModel.getHubInfo()) : "", "isProtocolHubInfo", false);
            }

            //TODO : KIRAN
//            if (isNotNull(profileModel.getHubInfo())) {
//                getPredicate(root, cb, predicates, convert, "tpId", true);
//            }

            checkRoleAndPredicateWithPartner(root, cb, predicates, "pkId", true);

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

//  NOTE : Don't Delete it we have to use this when SFG protocols have to show on same search from SI
//    private Page<ProfileModel> mapEntityPageIntoDTOPage(Pageable pageRequest, Page<PartnerEntity> source)
//        List<ProfileModel> profileModels = new ArrayList<>()
//        source.getContent().forEach(partnerEntity -> profileModels.add(mapperToPartnerProfileModel.apply(partnerEntity)))
//        return new PageImpl<>(profileModels, pageRequest, source.getTotalElements())
//

    List<PartnerEntity> findAllPartnerProfiles() {
        return partnerRepository.findAllByOrderByTpNameAsc().orElse(new ArrayList<>());
    }

    List<PartnerEntity> findAllAs2Profiles(String isHubInfo) {
        return partnerRepository.findAllByIsProtocolHubInfoAndTpProtocol(isHubInfo, "AS2").orElse(new ArrayList<>());
    }

    public List<PartnerEntity> getAllTemplateProfiles() {
        return partnerRepository.findAllByTpNameContainingIgnoreCaseOrderByTpName(TEMPLATE).orElse(new ArrayList<>());
    }

    public List<PartnerEntity> findByPartnerName(String tpName) {
        return partnerRepository.findByTpName(tpName.trim()).orElseThrow(() -> notFound(PARTNER));
    }

    List<PartnerEntity> findByPartnerNameAndPartnerId(String partnerName, String partnerId) {
        return partnerRepository.findAllByTpNameAndTpId(partnerName, partnerId).orElseThrow(() -> notFound(PARTNER));
    }

    List<PartnerEntity> findAllByTpProtocolAndIsProtocolHubInfoLike(String protocol, String isHubInfo) {
        return partnerRepository.findAllByTpProtocolAndIsProtocolHubInfoOrderByTpName(protocol, isHubInfo).orElse(new ArrayList<>());
    }

    public List<PartnerEntity> findAllByTpProtocol(String protocol) {
        return partnerRepository.findAllByTpProtocolOrderByTpName(protocol).orElse(new ArrayList<>());
    }

    public List<PartnerEntity> findAllByPkIdIn(List<String> partnersNamesList) {
        return partnerRepository.findAllByPkIdIn(partnersNamesList).orElse(new ArrayList<>());
    }

    public List<CommunityManagerKeyValueModel> getPartnersListAssignedToUserMap(String userId) {
        List<CommunityManagerKeyValueModel> communityManagerMapPartnersModelList = new ArrayList<>();
        tpUserRepository.findById(userId.trim()).ifPresent(tpUserEntity -> {

            List<String> pkIdList = new ArrayList<>(isNotNull(tpUserEntity.getPartnerList()) ? Arrays.asList(tpUserEntity.getPartnerList().split(",")) : new ArrayList<>());
            List<String> groupPkIdList = new ArrayList<>();
            AtomicInteger atomicInteger = new AtomicInteger(0);

            addPartners(communityManagerMapPartnersModelList, pkIdList, atomicInteger);

            pkIdList.addAll(isNotNull(tpUserEntity.getGroupList()) ? Arrays.asList(tpUserEntity.getGroupList().split(",")) : new ArrayList<>());
            pkIdList.forEach(groupPkId -> {
                atomicInteger.getAndIncrement();
                groupPkIdList.add(groupPkId);
                if (atomicInteger.get() % 998 == 0 || atomicInteger.get() >= pkIdList.size()) {
                    AtomicInteger atomicInteger1 = new AtomicInteger(0);
                    List<String> tempList2 = new ArrayList<>();
                    groupService.findAllIdsIn(groupPkIdList)
                            .stream()
                            .map(GroupEntity::getPartnerList)
                            .collect(Collectors.toList())
                            .forEach(partnerPkIds -> {
                                tempList2.addAll(isNotNull(partnerPkIds) ? Arrays.asList(partnerPkIds.split(",")) : new ArrayList<>());
                                addPartners(communityManagerMapPartnersModelList, tempList2, atomicInteger1);
                            });
                    groupPkIdList.clear();
                }
            });
            pkIdList.clear();
        });
        return communityManagerMapPartnersModelList.stream().sorted(Comparator.comparing(CommunityManagerKeyValueModel::getValue)).collect(Collectors.toList());

    }

    private void addPartners(List<CommunityManagerKeyValueModel> partnerListFinal, List<String> partnerPkIdList, AtomicInteger atomicInteger) {
        List<String> tempPartnerPkIdList = new ArrayList<>();
        partnerPkIdList.forEach(partnerPkId -> {
            atomicInteger.getAndIncrement();
            tempPartnerPkIdList.add(partnerPkId);
            if (atomicInteger.get() % 998 == 0 || atomicInteger.get() >= partnerPkIdList.size()) {
                partnerListFinal.addAll(findAllByPkIdIn(tempPartnerPkIdList)
                        .stream()
                        .map(partnerEntity -> new CommunityManagerKeyValueModel(partnerEntity.getPkId(), partnerEntity.getTpName()))
                        .collect(Collectors.toList()));
                tempPartnerPkIdList.clear();
            }
        });
        partnerPkIdList.clear();
        atomicInteger.set(0);
    }

    public void checkRoleAndPredicateWithPartner(Root<?> root, CriteriaBuilder cb, List<Predicate> predicates, String field, boolean isKey) {

        String authenticatedUser = userUtilityService.getUserOrRole(Boolean.FALSE);

        if (!authenticatedUser.equalsIgnoreCase(SUPER_ADMIN) && !authenticatedUser.equalsIgnoreCase(AuthoritiesConstants.ADMIN)) {
            List<String> parentList;
            if (isKey) {
                parentList = getPartnersListAssignedToUserMap(userUtilityService.getUserOrRole(TRUE))
                        .stream()
                        .map(CommunityManagerKeyValueModel::getKey)
                        .collect(Collectors.toList());
            } else {
                parentList = getPartnersListAssignedToUserMap(userUtilityService.getUserOrRole(TRUE))
                        .stream()
                        .map(CommunityManagerKeyValueModel::getValue)
                        .collect(Collectors.toList());
            }

            if (!parentList.isEmpty()) {
                AtomicInteger atomicInteger = new AtomicInteger(0);
                List<String> parentListDup = new ArrayList<>();
                List<CriteriaBuilder.In<String>> inList = new ArrayList<>();
                parentList.forEach(partner -> {
                    atomicInteger.getAndIncrement();
                    parentListDup.add(partner);
                    if (atomicInteger.get() % 998 == 0 || atomicInteger.get() == parentList.size()) {
                        CriteriaBuilder.In<String> in = cb.in(root.get(field));
                        parentListDup.forEach(in::value);
                        inList.add(in);
                        parentListDup.clear();
                    }
                });
                parentListDup.clear();
                predicates.add(cb.or(inList.toArray(new Predicate[0])));
            }
        }
    }

    public List<String> getPartnersByCurrentUser(boolean isPartnerPkId) {
        String authenticatedUser = userUtilityService.getUserOrRole(Boolean.FALSE);

        if (!authenticatedUser.equalsIgnoreCase(SUPER_ADMIN) && !authenticatedUser.equalsIgnoreCase(AuthoritiesConstants.ADMIN)) {

            if (isPartnerPkId) {
                return getPartnersListAssignedToUserMap(userUtilityService.getUserOrRole(TRUE))
                        .stream()
                        .map(CommunityManagerKeyValueModel::getKey)
                        .collect(Collectors.toList());
            } else {
                return getPartnersListAssignedToUserMap(userUtilityService.getUserOrRole(TRUE))
                        .stream()
                        .map(CommunityManagerKeyValueModel::getValue)
                        .collect(Collectors.toList());
            }
        }
        return new ArrayList<>();
    }

    public List<PartnerEntity> findAllByIdIn(List<String> ids) {
        return partnerRepository.findAllByPkIdIn(ids).orElse(new ArrayList<>());
    }

}
