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

package com.pe.pcm.group;

import com.pe.pcm.common.CommunityManagerKeyValueModel;
import com.pe.pcm.miscellaneous.UserUtilityService;
import com.pe.pcm.user.GroupRepository;
import com.pe.pcm.user.TpUserRepository;
import com.pe.pcm.user.entity.GroupEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.pe.pcm.constants.AuthoritiesConstants.ADMIN;
import static com.pe.pcm.constants.AuthoritiesConstants.SUPER_ADMIN;
import static com.pe.pcm.exception.GlobalExceptionHandler.*;
import static com.pe.pcm.utils.CommonFunctions.isNotNull;
import static com.pe.pcm.utils.KeyGeneratorUtil.getPrimaryKey;
import static com.pe.pcm.utils.PCMConstants.PARTNER_GROUP_PKEY_PRE_APPEND;
import static com.pe.pcm.utils.PCMConstants.PARTNER_GROUP_PKEY_RANDOM_COUNT;
import static java.lang.Boolean.FALSE;

/**
 * @author Shameer.
 */
@Service
public class GroupService {

    private final GroupRepository grouprepository;
    private final UserUtilityService userUtilityService;
    private final TpUserRepository tpUserRepository;


    private final Function<GroupEntity, GroupModel> serialize = GroupService::apply;

    @Autowired
    public GroupService(GroupRepository grouprepository, UserUtilityService userUtilityService, TpUserRepository tpUserRepository) {
        this.grouprepository = grouprepository;
        this.userUtilityService = userUtilityService;
        this.tpUserRepository = tpUserRepository;
    }

    private static GroupModel apply(GroupEntity groupEntity) {
        return new GroupModel()
                .setPkId(groupEntity.getPkId())
                .setGroupName(groupEntity.getGroupname())
                .setPartnerList(isNotNull(groupEntity.getPartnerList()) ? Arrays.asList(groupEntity.getPartnerList().split(",")) : new ArrayList<>());
    }

    private GroupEntity createGroup(GroupModel groupModel) {
        GroupEntity groupentity = new GroupEntity()
                .setPkId(groupModel.getPkId())
                .setGroupname(groupModel.getGroupName())
                .setPartnerList(String.join(",", groupModel.getPartnerList()));
        return grouprepository.save(groupentity);
    }

    @Transactional
    public GroupEntity create(GroupModel groupModel) {
        validate(groupModel);
        grouprepository.findByGroupname(groupModel.getGroupName()).ifPresent(groupEntity -> {
            throw conflict("Group");
        });
        String pkId = getPrimaryKey.apply(PARTNER_GROUP_PKEY_PRE_APPEND, PARTNER_GROUP_PKEY_RANDOM_COUNT);
        groupModel.setPkId(pkId);
        userUtilityService.addPartnerToCurrentUser(pkId, "GP");
        return createGroup(groupModel);
    }

    @Transactional
    public GroupEntity update(GroupModel groupModel) {
        validate(groupModel);
        return createGroup(groupModel);
    }

    @Transactional
    public void delete(String pkId) {
        grouprepository.findById(pkId).ifPresent(grouprepository::delete);
    }

    public Page<GroupEntity> search(GroupModel groupModel, Pageable pageable) {
        Page<GroupEntity> groupList = grouprepository.findAll((Specification<GroupEntity>) (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            getPredicate(root, cb, predicates, groupModel.getGroupName());
            return cb.and(predicates.toArray(new Predicate[0]));
        }, pageable);
        return new PageImpl<>(new ArrayList<>(groupList.getContent()), pageable, groupList.getTotalElements());
    }

    private void getPredicate(Root<GroupEntity> root, CriteriaBuilder cb, List<Predicate> predicates, String searchParam) {
        if (!StringUtils.isEmpty(searchParam)) {
            predicates.add(cb.like(cb.lower(root.get("groupname")), "%" + searchParam.toLowerCase() + "%"));
        }
    }

    public GroupModel get(String pkId) {
        return serialize.apply(grouprepository.findById(pkId).orElseThrow(() -> notFound("Group")));
    }

    public List<CommunityManagerKeyValueModel> getGroupsMap() {
        String role = userUtilityService.getUserOrRole(FALSE);
        if (!role.equals(SUPER_ADMIN) && !role.equals(ADMIN)) {
            return getAppliedGroupsMaps(userUtilityService.getUserOrRole(Boolean.TRUE));
        }
        return grouprepository.findAllByOrderByGroupnameAsc().stream().map(groupEntity ->
                new CommunityManagerKeyValueModel(groupEntity.getPkId(), groupEntity.getGroupname())
        ).collect(Collectors.toList());
    }

    public List<GroupEntity> findAllIdsIn(List<String> groupNamesList) {
        return grouprepository.findAllByPkIdIn(groupNamesList).orElse(new ArrayList<>());
    }

    private List<CommunityManagerKeyValueModel> getAppliedGroupsMaps(String userId) {
        List<CommunityManagerKeyValueModel> communityManagerMapPartnersModelList = new ArrayList<>();
        tpUserRepository.findById(userId).ifPresent(tpUserEntity -> {
            AtomicInteger atomicInteger = new AtomicInteger(0);
            List<String> tempList0 = new ArrayList<>(isNotNull(tpUserEntity.getGroupList()) ? Arrays.asList(tpUserEntity.getGroupList().split(",")) : new ArrayList<>());
            List<String> tempList1 = new ArrayList<>();
            tempList0.forEach(group -> {
                atomicInteger.getAndIncrement();
                tempList1.add(group);
                if (atomicInteger.get() % 998 == 0 || atomicInteger.get() >= tempList0.size()) {
                    communityManagerMapPartnersModelList.addAll(findAllIdsIn(tempList1)
                            .stream()
                            .map(groupEntity -> new CommunityManagerKeyValueModel(groupEntity.getPkId(), groupEntity.getGroupname()))
                            .collect(Collectors.toList()));
                }
            });
        });
        return communityManagerMapPartnersModelList.stream().sorted(Comparator.comparing(CommunityManagerKeyValueModel::getValue)).collect(Collectors.toList());
    }

    private void validate(GroupModel groupModel) {
        if (groupModel.getPartnerList().isEmpty()) {
            throw internalServerError("Please provide at least one Partner to create/update the group.");
        }
    }

}
