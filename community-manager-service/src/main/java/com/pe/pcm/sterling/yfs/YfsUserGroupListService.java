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

package com.pe.pcm.sterling.yfs;

import com.pe.pcm.functions.TriFunction;
import com.pe.pcm.generator.PrimaryKeyGeneratorService;
import com.pe.pcm.sterling.yfs.entity.YfsUserGroupEntity;
import com.pe.pcm.sterling.yfs.entity.YfsUserGroupListEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;

/**
 * @author Kiran Reddy.
 */
@Service
public class YfsUserGroupListService {

    private final YfsUserGroupRepository yfsUserGroupRepository;
    private final YfsUserGroupListRepository yfsUserGroupListRepository;
    private final PrimaryKeyGeneratorService primaryKeyGeneratorService;

    @Autowired
    public YfsUserGroupListService(YfsUserGroupRepository yfsUserGroupRepository, YfsUserGroupListRepository yfsUserGroupListRepository, PrimaryKeyGeneratorService primaryKeyGeneratorService) {
        this.yfsUserGroupRepository = yfsUserGroupRepository;
        this.yfsUserGroupListRepository = yfsUserGroupListRepository;
        this.primaryKeyGeneratorService = primaryKeyGeneratorService;
    }


    public void save(String userKey, List<String> userGroupNames, boolean mergeUser) {

        //This needs to be improved
        Map<String, String> groupsMap = yfsUserGroupRepository.findAllByOrderByUserGroupName()
                .stream()
                .collect(Collectors.toMap(YfsUserGroupEntity::getUserGroupName, YfsUserGroupEntity::getUserGroupKey));

        checkGroupsAvailability(groupsMap, userGroupNames);

        if (!mergeUser) {
            yfsUserGroupListRepository.deleteAllByUserKey(userKey);
        }

        userGroupNames.forEach(userGroupName -> {
            String userGroupListKey;
            String userGroupKey = groupsMap.get(userGroupName);
            if (mergeUser) {
                Optional<YfsUserGroupListEntity> yfsUserGroupListEntityOptional = yfsUserGroupListRepository.findFirstByUserKeyAndUserGroupKey(userKey, userGroupKey);
                if (yfsUserGroupListEntityOptional.isPresent()) {
                    userGroupListKey = yfsUserGroupListEntityOptional.get().getUserGroupListKey();
                } else {
                    userGroupListKey = primaryKeyGeneratorService.generatePrimaryKey("GR", 4);
                }
            } else {
                userGroupListKey = primaryKeyGeneratorService.generatePrimaryKey("GR", 4);
            }
            yfsUserGroupListRepository.save(serialize.apply(userGroupListKey, userKey, userGroupKey));
        });

    }

    private void checkGroupsAvailability(Map<String, String> groupsMap, List<String> userGroupNames) {
        List<String> groupNames = new ArrayList<>(groupsMap.keySet());
        userGroupNames.forEach(userGroupName -> {
            if (!groupNames.contains(userGroupName.trim())) {
                throw internalServerError("Provided Group is not available, Group Name: " + userGroupName);
            }
        });
    }

    public List<String> findAllGroupsByUserKey(String userKey) {
        return yfsUserGroupRepository
                .findAllByUserGroupKeyIn(yfsUserGroupListRepository.findAllByUserKey(userKey)
                        .stream()
                        .map(YfsUserGroupListEntity::getUserGroupKey)
                        .collect(Collectors.toList()))
                .stream()
                .map(YfsUserGroupEntity::getUserGroupName)
                .collect(Collectors.toList());
    }

    public void delete(String userKey) {
        yfsUserGroupListRepository.deleteAllByUserKey(userKey);
    }

    private static final TriFunction<String, String, String, YfsUserGroupListEntity> serialize = (userGroupListKey, userKey, userGroupKey) -> {
        YfsUserGroupListEntity yfsUserGroupListEntity = new YfsUserGroupListEntity().setUserGroupListKey(userGroupListKey)
                .setUserKey(userKey)
                .setUserGroupKey(userGroupKey);
        yfsUserGroupListEntity.setLockid(0);
        yfsUserGroupListEntity.setCreateuserid(" ");
        yfsUserGroupListEntity.setCreateprogid(" ");
        yfsUserGroupListEntity.setModifyuserid(" ");
        yfsUserGroupListEntity.setModifyprogid(" ");
        return yfsUserGroupListEntity;
    };

}
