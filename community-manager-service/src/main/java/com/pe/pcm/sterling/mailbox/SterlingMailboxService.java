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

package com.pe.pcm.sterling.mailbox;

import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.miscellaneous.IndependentService;
import com.pe.pcm.si.SterlingMailboxModel;
import com.pe.pcm.sterling.mailbox.entity.MbxMailBoxEntity;
import com.pe.pcm.sterling.mailbox.entity.MbxMailboxGuidEntity;
import com.pe.pcm.sterling.yfs.YfsResourceRepository;
import com.pe.pcm.sterling.yfs.entity.YfsResourceEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.pe.pcm.utils.CommonFunctions.getRootDirectories;
import static com.pe.pcm.utils.CommonFunctions.isNotNull;
import static com.pe.pcm.utils.KeyGeneratorUtil.getPrimaryKey;
import static com.pe.pcm.utils.PCMConstants.ORACLE;

/**
 * @author Chenchu Kiran.
 */
@Service
public class SterlingMailboxService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SterlingMailboxService.class);

    private final MbxMailBoxRepository mbxMailBoxRepository;
    private final MbxMailboxGuidRepository mbxMailboxGuidRepository;
    private final YfsResourceRepository yfsResourceRepository;
    private final IndependentService independentService;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SterlingMailboxService(MbxMailBoxRepository mbxMailBoxRepository, MbxMailboxGuidRepository mbxMailboxGuidRepository, YfsResourceRepository yfsResourceRepository, IndependentService independentService, JdbcTemplate jdbcTemplate) {
        this.mbxMailBoxRepository = mbxMailBoxRepository;
        this.mbxMailboxGuidRepository = mbxMailboxGuidRepository;
        this.yfsResourceRepository = yfsResourceRepository;
        this.independentService = independentService;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void create(SterlingMailboxModel sterlingMailboxModel) {
        create1(sterlingMailboxModel);
    }

    public void createAll(List<SterlingMailboxModel> sterlingMailboxModels) {
        sterlingMailboxModels.forEach(this::create1);
    }

    @Transactional
    public void update(String mailbox) {
        //Design Pending
    }

    public List<String> getMailboxNamesList() {
        return mbxMailBoxRepository.findAllByOrderByPath().stream()
                .map(MbxMailBoxEntity::getPath)
                .collect(Collectors.toList());
    }

    private void create1(SterlingMailboxModel sterlingMailboxModel) {
        String dbType = independentService.getDbType();
        if (isNotNull(sterlingMailboxModel.getPath())) {
            String path = sterlingMailboxModel.getPath().trim();
            if (!path.startsWith("/")) {
                throw GlobalExceptionHandler.internalServerError("Mailbox should starts with /");
            }
            Optional<MbxMailBoxEntity> mbxMailBoxEntityOptional = mbxMailBoxRepository.findFirstByPathUp(path.toUpperCase());
            if (!mbxMailBoxEntityOptional.isPresent()) {
                AtomicLong parentId = new AtomicLong(1);
                getRootDirectories(path, new LinkedHashSet<>(), new AtomicReference<>(""), false).forEach(directory -> {
                    if (directory.length() == path.length()) {
                        parentId.set(save(sterlingMailboxModel, directory.trim(), true, parentId.get(), dbType).getMailboxId());
                    } else {
                        parentId.set(save(sterlingMailboxModel, directory.trim(), false, parentId.get(), dbType).getMailboxId());
                    }
                });
            }
        }
    }

    private MbxMailBoxEntity save(SterlingMailboxModel sterlingMailboxModel, String directory, Boolean isFinalDirectory, Long parentId, String dbType) {

        Optional<MbxMailBoxEntity> mbxMailBoxEntityOptional = mbxMailBoxRepository.findFirstByPathUp(directory.toUpperCase());
        if (mbxMailBoxEntityOptional.isPresent()) {
            return mbxMailBoxEntityOptional.get();
        } else {
            Long seqNum;
            if (dbType.equals(ORACLE)) {
                seqNum = getOracleNextValue();
            } else {
                Long mbxMax = mbxMailBoxRepository.getMaxMailboxId();
                Long mbxGuidMax = mbxMailboxGuidRepository.getMaxNextMailboxId();
                seqNum = mbxMax >= mbxGuidMax ? mbxMax : mbxGuidMax;
            }

            String description;
            if (isFinalDirectory) {
                description = sterlingMailboxModel.getDescription();
            } else {
                description = directory.substring(directory.lastIndexOf('/') + 1) + " Auto Generated";
            }
            LOGGER.info("Mailbox info : directory {}, mailboxId {}, parentId {}", directory, seqNum, parentId);
            MbxMailBoxEntity mbxMailBoxEntity =
                    mbxMailBoxRepository.save(
                            new MbxMailBoxEntity().setMailboxId(seqNum)
                                    .setParentId(parentId)
                                    .setPath(directory)
                                    .setDescription(description)
                                    .setPathUp(directory.toUpperCase()));
            mbxMailboxGuidRepository.save(
                    new MbxMailboxGuidEntity()
                            .setNextMailboxGuid(getPrimaryKey.apply("-5", 23))
                            .setNextMailboxId(mbxMailBoxEntity.getMailboxId()));
            Optional<YfsResourceEntity> yfsResourceEntityOptional = yfsResourceRepository.findFirstByResourceId(directory + ".mbx");
            if (!yfsResourceEntityOptional.isPresent()) {
                yfsResourceRepository.save(resourceEntitySerialization.apply(directory).setResourceKey(getPrimaryKey.apply("MBP", 20)));
            }

            return mbxMailBoxEntity;
        }
    }

    @Transactional
    public void delete(String mailbox) {
        if (isNotNull(mailbox)) {
            String path = mailbox.trim();
            List<String> directoriesDescription = getRootDirectories(path, new LinkedHashSet<>(), new AtomicReference<>(""), false)
                    .stream()
                    .map(String::trim)
                    .collect(Collectors.toList());
            mbxMailBoxRepository.findAllByPathIn(directoriesDescription).forEach(mbxMailBoxEntity -> {

                mbxMailboxGuidRepository.deleteAllByNextMailboxId(mbxMailBoxEntity.getMailboxId());
                yfsResourceRepository.deleteAllByResourceId(mbxMailBoxEntity.getPath().trim() + ".mbx");
                mbxMailBoxRepository.delete(mbxMailBoxEntity);
            });
        }

    }

    public Long getMailboxId(String mailbox) {
        return mbxMailBoxRepository.findFirstByPathUp(mailbox.toUpperCase()).orElseThrow(() -> GlobalExceptionHandler.internalServerError("Mailbox is not available, Mailbox : " + mailbox)).getMailboxId();
    }

    private static final Function<String, YfsResourceEntity> resourceEntitySerialization = mailbox -> {
        YfsResourceEntity yfsResourceEntity = new YfsResourceEntity().setResourceId(mailbox + ".mbx")
                .setResourceDesc(mailbox + " Mailbox")
                .setOrigResourceId(" ")
                .setParentResourceId(" ")
                .setUrl(" ")
                .setResourceType("1")
                .setResourceCreateType(" ")
                .setResourceSeq("0")
                .setCanAddToMenu(" ")
                .setIsPermissionControlled(" ")
                .setShowDetail(" ")
                .setApplicationName(" ")
                .setServiceKey(" ")
                .setOutputXmlTemplateFileName(" ")
                .setOutputXslTemplateFileName(" ")
                .setFormClassName(" ")
                .setOverrideFormClassName(" ")
                .setBehaviorClassName(" ")
                .setEventComponent(" ")
                .setJsp(" ")
                .setJavascript(" ")
                .setBinding(" ")
                .setDisplayBinding(" ")
                .setAltImage(" ")
                .setAltImageBinding(" ")
                .setPopup(" ")
                .setDocumentType(" ")
                .setOutputNamespace(" ")
                .setInputNamespace(" ")
                .setResourceSubType(" ")
                .setImage(" ")
                .setToolTip(" ")
                .setViewId(" ")
                .setOverrideEntityId(" ")
                .setSelectionKeyName(" ")
                .setOverrideEntityKeyName(" ")
                .setEntityKeyName(" ")
                .setCloseWindowOnComplete(" ")
                .setIgnoreException(" ")
                .setIgnoreDefaultApi(" ")
                .setHeight("0")
                .setWidth("0")
                .setInput(" ")
                .setTemplate(" ")
                .setApiName(" ")
                .setFlowName(" ")
                .setSkipAutoExecute("N")
                .setHideNavigationPanel("N")
                .setHideMaxRecords("N")
                .setSystemKey(" ")
                .setAdapterKey(" ")
                .setProtocolKey(" ")
                .setParameter1(" ")
                .setParameter2(" ")
                .setParameter3(" ")
                .setParameter4(" ")
                .setParameter5(" ")
                .setSuppressDecoration("N")
                .setReDirector("N")
                .setViewGroupId(" ")
                .setVersion(" ")
                .setApplicationCode(" ")
                .setSupportsSearchToDetail("N")
                .setHelpApplicationCode(" ")
                .setSuppressHelp("N")
                .setRollbackOnlyMode("N")
                .setIsReport("N")
                .setStatus("1");

        yfsResourceEntity.setLockid(0);
        yfsResourceEntity.setCreateuserid(" ");
        yfsResourceEntity.setModifyuserid(" ");
        yfsResourceEntity.setCreateprogid(" ");
        yfsResourceEntity.setModifyprogid(" ");

        return yfsResourceEntity;
    };

    private Long getOracleNextValue() {
        return jdbcTemplate.queryForObject("SELECT MBX_MAILBOX_GUID_SEQUENCE.NEXTVAL FROM DUAL", new Object[]{}, Long.class);

    }

}
