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

package com.pe.pcm.file;

import com.pe.pcm.application.ApplicationService;
import com.pe.pcm.application.entity.ApplicationEntity;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.login.CommunityManagerUserModel;
import com.pe.pcm.miscellaneous.AppShutDownService;
import com.pe.pcm.miscellaneous.FileDropMailboxService;
import com.pe.pcm.miscellaneous.UserUtilityService;
import com.pe.pcm.partner.PartnerService;
import com.pe.pcm.properties.CMProperties;
import com.pe.pcm.properties.DemoSiProperties;
import com.pe.pcm.protocol.MailboxService;
import com.pe.pcm.protocol.RemoteFtpService;
import com.pe.pcm.reports.FileTransferService;
import com.pe.pcm.reports.TransInfoDService;
import com.pe.pcm.reports.entity.TransInfoDEntity;
import com.pe.pcm.reports.entity.TransferInfoEntity;
import com.pe.pcm.seas.user.UserDetailsImpl;
import com.pe.pcm.sterling.mailbox.SterlingMailboxService;
import com.pe.pcm.utils.CommonFunctions;
import com.pe.pcm.utils.FileInfoModel;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;
import static com.pe.pcm.utils.CommonFunctions.deleteIfExists;
import static com.pe.pcm.utils.PCMConstants.*;
import static java.nio.file.StandardOpenOption.*;

/**
 * @author Kiran Reddy.
 */

@Service
public class FileUploadService {
    private String rootTempDirectory;
    //    private File rootTempDirFile;
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadService.class);
    private final CMProperties cmProperties;
    private final DemoSiProperties demoSiProperties;
    private final AppShutDownService appShutDownService;

    private final TransInfoDService transInfoDService;
    private final ManageFileService manageFileService;
    private final FileDropMailboxService fileDropMailboxService;
    private final FileTransferService fileTransferService;
    private final MailboxService mailboxService;
    private final PartnerService partnerService;
    private final ApplicationService applicationService;
    private final RemoteFtpService remoteFtpService;
    private final UserUtilityService userUtilityService;
    private final SterlingMailboxService sterlingMailboxService;


    @Autowired
    public FileUploadService(CMProperties cmProperties,
                             DemoSiProperties demoSiProperties,
                             AppShutDownService appShutDownService,
                             TransInfoDService transInfoDService,
                             ManageFileService manageFileService,
                             FileDropMailboxService fileDropMailboxService,
                             FileTransferService fileTransferService,
                             MailboxService mailboxService,
                             PartnerService partnerService,
                             ApplicationService applicationService,
                             RemoteFtpService remoteFtpService,
                             UserUtilityService userUtilityService,
                             SterlingMailboxService sterlingMailboxService) {
        this.cmProperties = cmProperties;
        this.demoSiProperties = demoSiProperties;
        this.appShutDownService = appShutDownService;
        this.transInfoDService = transInfoDService;
        this.manageFileService = manageFileService;
        this.fileDropMailboxService = fileDropMailboxService;
        this.fileTransferService = fileTransferService;
        this.mailboxService = mailboxService;
        this.partnerService = partnerService;
        this.applicationService = applicationService;
        this.remoteFtpService = remoteFtpService;
        this.userUtilityService = userUtilityService;
        this.sterlingMailboxService = sterlingMailboxService;
    }

    public String createTempDirectory() {
        return createTempDirectoryWithPermissions();
    }

    public void saveChunk(String tmpDirectoryName, int fileIndex, MultipartFile multipartFile) {
        Path tempDirPath = Paths.get(rootTempDirectory, tmpDirectoryName);
        if (Files.exists(tempDirPath)) {
            try {
                multipartFile.transferTo(tempDirPath.resolve(Paths.get(fileIndex < 10 ? ("0" + fileIndex) : fileIndex + "_" + multipartFile.getOriginalFilename())));
            } catch (IOException e) {
                throw internalServerError("Exception while moving the file into temp location, please upload the file again.");
            }
        } else {
            throw internalServerError("Directory is not available, please upload the file again.");
        }
        LOGGER.debug("Chunk saved successfully!");
    }

    @Transactional
    public void uploadFile(String mailbox, File file, String fileName) {
        uploadFileToMailbox(mailbox, file, fileName, userUtilityService.getUserOrRole(Boolean.TRUE));
        deleteIfExists(file);
    }

    @Async
    @Transactional
    public void mergeChunksAndDropIntoMailbox(String tmpDirectoryName, String fileName, String mailbox, String userName) {

        Path filePath = mergeChunks(tmpDirectoryName, fileName);
        if (filePath != null) {
            addUserDetailsToContext(userName);
            uploadFileToMailbox(mailbox, filePath.toFile(), fileName, userName);
            deleteDirectory(filePath.getParent().toFile());
        }
    }

    public Path mergeChunks(String tmpDirectoryName, String fileName) {
        Path tempDirPath = Paths.get(rootTempDirectory, tmpDirectoryName);
        if (tempDirPath.toFile().exists()) {
            Path outFile = Paths.get(rootTempDirectory, createTempDirectoryWithPermissions(), fileName);
            try (FileChannel out = FileChannel.open(outFile, CREATE, WRITE)) {
                try (Stream<Path> stream = Files.list(tempDirPath)) {
                    stream.forEach(inFile -> {
                        try (FileChannel inFileChannel = FileChannel.open(inFile, READ)) {
                            for (long position = 0, length = inFileChannel.size(); position < length; )
                                position += inFileChannel.transferTo(position, length - position, out);
                        } catch (IOException e) {
                            LOGGER.error("Error while Merging file: ", e);
                            deleteDirectory(tempDirPath.toFile());
                            throw internalServerError("Exception while merging files into single file, please upload the file again.");
                        }
                    });
                }
                deleteDirectory(tempDirPath.toFile());
                return outFile;
            } catch (IOException e) {
                deleteDirectory(tempDirPath.toFile());
                LOGGER.error("Exception while merging files into single file, please upload the file again.");
            }
        } else {
            LOGGER.error("Chunks Directory is not available, please upload the file again.");
        }
        return null;
    }

    public void stopTheChunkUpload(String chunkDirName) {
        deleteDirectory(new File(rootTempDirectory, chunkDirName));
    }

    private void deleteDirectory(File childDirectory) {
        LOGGER.info("Directory to be deleted {}", childDirectory.getAbsolutePath());
        try {
            if (FileUtils.directoryContains(new File(rootTempDirectory), childDirectory)) {
                FileUtils.forceDelete(childDirectory);
                LOGGER.info("Chunks Directory Deleted");
            }
        } catch (IOException e) {
            LOGGER.error("Unable to delete the Chunks Directory");
        }
    }

    /**
     * This will generate the random folderName with the help of
     * fileName then create the directory in root temp directory: jarLocation/<cm_temp_directory>/<folderName>
     * (If it is linux then it will apply READ, WRITE permissions)
     */
    private String createTempDirectoryWithPermissions(String... tempDir) {
        LOGGER.info("TempDirectory is getting created ");
        String tempDirectory;
        if (tempDir != null && tempDir.length > 0) {
            tempDirectory = tempDir[0];
        } else {
            tempDirectory = CommonFunctions.getUniqueString(16);
        }

        Path tempPath = Paths.get(rootTempDirectory, tempDirectory);
        LOGGER.info("Trying to create Temp Directory: {}", tempPath);
        if (tempPath.toFile().exists()) {
            LOGGER.info("File already exist for new file Upload, Creating new Directory.");
            return createTempDirectoryWithPermissions();
        } else {
            if (rootTempDirectory.startsWith("/")) {
                Set<PosixFilePermission> fullPermission = new HashSet<>();
                fullPermission.add(PosixFilePermission.OWNER_EXECUTE);
                fullPermission.add(PosixFilePermission.OWNER_READ);
                fullPermission.add(PosixFilePermission.OWNER_WRITE);
                try {
                    Files.createDirectory(tempPath, PosixFilePermissions.asFileAttribute(fullPermission));
                    Files.setPosixFilePermissions(tempPath, fullPermission);
                } catch (IOException e) {
                    throw internalServerError("Unable to create(or permissions) the temp directory. Please contact the sysAdmin.");
                }
            } else {
                try {
                    Files.createDirectory(tempPath);
                } catch (IOException e) {
                    throw internalServerError("Unable to create(or permissions) the temp directory. Please contact the sysAdmin.");
                }
            }
        }
        LOGGER.debug("Temp Directory created: {}", tempDirectory);
        return tempDirectory;
    }

    public List<TransInfoDEntity> getFileActivity(String mailbox, String activityName) {
        return transInfoDService.findAllByRuleNameAndActivityName(mailbox, activityName);
    }

    //This is duplicate code
    private String getMailboxProfileName(String mailbox) {
        String subscriberId = mailboxService.findFirstByInMailboxOrOutMailbox(mailbox, mailbox).getSubscriberId();
        if (StringUtils.hasText(subscriberId)) {
            try {
                return partnerService.findPartnerById(subscriberId).getTpName();
            } catch (CommunityManagerServiceException e) {
                return applicationService.find(subscriberId).orElse(new ApplicationEntity()).getApplicationName();
            }
        }
        subscriberId = remoteFtpService.findFirstByInMailboxOrOutMailbox(mailbox, mailbox).getInDirectory();
        if (StringUtils.hasText(subscriberId)) {
            try {
                return partnerService.findPartnerById(subscriberId).getTpName();
            } catch (CommunityManagerServiceException e) {
                return applicationService.find(subscriberId).orElse(new ApplicationEntity()).getApplicationName();
            }
        }

        return null;
    }

    private void saveFileActivity(String mailbox, String fileName, Long fileLength, String status, String userName) {
        String partnerName = getMailboxProfileName(mailbox);
        Long mailboxId = sterlingMailboxService.getMailboxId(mailbox);

        fileTransferService.save(new TransferInfoEntity()
                .setFlowinout(UPLOAD)
                .setPartner(partnerName)
                .setSenderid(userName)
                .setSrcprotocol(FILE_MANAGER)
                .setSrcfilename(fileName)
                .setSrcFileSize(fileLength)
                .setCorebpid(String.valueOf(mailboxId))
                .setXrefName(mailbox)
                .setStatus(status));

        transInfoDService.save(mailbox,
                mailboxId,
                UPLOAD,
                fileName,
                fileLength,
                status.equals(SUCCESS) ? ("File " + fileName + " uploaded into " + mailbox) : ("Unable to upload the file: " + fileName + " into " + mailbox + ", by " + userName)
        );
    }

    private void uploadFileToMailbox(String mailbox, File file, String fileName, String userName) {
        try {
            manageFileService.fileDropWithSFTP(new FileInfoModel()
                            .setFilePath(mailbox)
                            .setHost(demoSiProperties.getHost())
                            .setPort(demoSiProperties.getPort())
                            .setUserName(demoSiProperties.getUsername())
                            .setPassword(demoSiProperties.getCmks())
                            .setFileName(fileName),
                    FileUtils.openInputStream(file));
            saveFileActivity(mailbox, fileName, file.length(), SUCCESS, userName);
        } catch (CommunityManagerServiceException | IOException cme) {
            saveFileActivity(mailbox, fileName, file.length(), FAILED, userName);
            LOGGER.error("Unable to upload the file to mailbox, Mail sent to the User with error information.", cme);
        }
    }

    private void addUserDetailsToContext(String userName) {
        UserDetails userDetails = UserDetailsImpl.build(new CommunityManagerUserModel()
                .setUserId(userName));
        UsernamePasswordAuthenticationToken authentication
                = new UsernamePasswordAuthenticationToken(userDetails,
                null,
                userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


    @PostConstruct
    public void validateFileTempLocation() {
        rootTempDirectory = CommonFunctions.getJarPath() + "/" + cmProperties.getTempDirectory();
        LOGGER.info("Root Temp Dir: {}", rootTempDirectory);
//        rootTempDirectory = CommonFunctions.getJarPath();
//        rootTempDirFile = new File(CommonFunctions.getJarPath(), cmProperties.getTempDirectory());
//        if (rootTempDirFile.exists()) {
//            LOGGER.info("Community Manager Temp Directory is available");
//        } else {
//            LOGGER.info("Creating Community Manager TempDirectory");
//            createTempDirectoryWithPermissions(cmProperties.getTempDirectory());
//        }
//        try {
//            rootTempDirectory = rootTempDirFile.getAbsolutePath();
//        } catch (CommunityManagerServiceException cme) {
//            appShutDownService.initiateShutdown("Unable to create Community Manager temp directory: " + rootTempDirectory);
//        }

    }

}
