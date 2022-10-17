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

import com.pe.pcm.common.CommunityManagerKeyValueModel;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.generator.PrimaryKeyGeneratorService;
import com.pe.pcm.sterling.yfs.entity.YfsResourceEntity;
import com.pe.pcm.sterling.yfs.entity.YfsResourcePermissionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Kiran Reddy.
 */
@Service
public class YfsResourcePermissionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YfsResourcePermissionService.class);

    private final YfsResourceRepository yfsResourceRepository;
    private final YfsResourcePermissionRepository yfsResourcePermissionRepository;
    private final PrimaryKeyGeneratorService primaryKeyGeneratorService;

    @Autowired
    public YfsResourcePermissionService(YfsResourceRepository yfsResourceRepository, YfsResourcePermissionRepository yfsResourcePermissionRepository, PrimaryKeyGeneratorService primaryKeyGeneratorService) {
        this.yfsResourceRepository = yfsResourceRepository;
        this.yfsResourcePermissionRepository = yfsResourcePermissionRepository;
        this.primaryKeyGeneratorService = primaryKeyGeneratorService;
    }

    public void save(String userKey, List<String> permissions, boolean mergeUser) {

        validations(permissions);
        if (!mergeUser) {
            deleteAllByUserKey(userKey);
        }
        Map<String, String> yfsResourceKeys = checkAndGetThePermissions(permissions);
        permissions.forEach(permission -> {
            String resourceKey = yfsResourceKeys.get(permission);
            Optional<YfsResourcePermissionEntity> resourcePermissionEntityOptional = yfsResourcePermissionRepository.findFirstByResourceKeyAndUserKey(resourceKey, userKey);
            if (resourcePermissionEntityOptional.isPresent()) {
                LOGGER.info("Permission exist.");
            } else {
                YfsResourcePermissionEntity yfsResourcePermissionEntity =
                        new YfsResourcePermissionEntity().setResourcePermissionKey(primaryKeyGeneratorService.generatePrimaryKey("P", 4))
                                .setUserKey(userKey)
                                .setUserGroupKey(" ")
                                .setResourceKey(resourceKey)
                                .setActivateFlag(" ")
                                .setReadOnlyFlag("N")
                                .setRights("2047");
                yfsResourcePermissionEntity.setLockid(0);
                yfsResourcePermissionEntity.setCreateuserid(" ");
                yfsResourcePermissionEntity.setCreateprogid(" ");
                yfsResourcePermissionEntity.setModifyuserid(" ");
                yfsResourcePermissionEntity.setModifyprogid(" ");
                yfsResourcePermissionRepository.save(yfsResourcePermissionEntity);
            }
        });
    }

    private Map<String, String> checkAndGetThePermissions(List<String> permissions) {
        return permissions
                .stream()
                .map(permission -> {
                    Optional<YfsResourceEntity> yfsResourceEntityOptional = yfsResourceRepository.findFirstByResourceDesc(permission);
                    if (yfsResourceEntityOptional.isPresent()) {
                        return new CommunityManagerKeyValueModel().setKey(permission).setValue(yfsResourceEntityOptional.get().getResourceKey());
                    } else {
                        throw GlobalExceptionHandler.internalServerError("The Given Permission is not Available, Permission Name : " + permission);
                    }
                }).collect(Collectors.toMap(CommunityManagerKeyValueModel::getKey, CommunityManagerKeyValueModel::getValue));
    }

    void deleteAllByUserKey(String userKey) {
        yfsResourcePermissionRepository.deleteAllByUserKey(userKey);
    }

    List<String> findAllPermissionsDesByUserKey(String userKey) {
        return yfsResourceRepository.findAllByResourceKeyIn(
                        yfsResourcePermissionRepository.findAllByUserKey(userKey).orElse(new ArrayList<>())
                                .stream()
                                .map(YfsResourcePermissionEntity::getResourceKey)
                                .collect(Collectors.toList()))
                .stream()
                .map(YfsResourceEntity::getResourceDesc)
                .collect(Collectors.toList());
    }

    List<YfsResourcePermissionEntity> findAllByUserKeyAndResourceKeyIn(String userKey, List<String> resourcePermissionKeys) {
        return yfsResourcePermissionRepository.findAllByUserKeyAndResourceKeyIn(userKey, resourcePermissionKeys);
    }

    void deleteById(String resourcePermissionKey) {
        yfsResourcePermissionRepository.deleteById(resourcePermissionKey);
    }

    private void validations(List<String> permissions) {
        List<String> reOrgPer = new ArrayList<>();
        permissions.forEach(per -> reOrgPer.add(per.endsWith(" Mailbox") ? per : per + " Mailbox"));
        permissions.clear();
        permissions.addAll(reOrgPer);
    }
}
