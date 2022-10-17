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

import com.jcraft.jsch.*;
import com.pe.pcm.config.FileArchiveProperties;
import com.pe.pcm.config.FileSchedulerModel;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.miscellaneous.AmazonS3FileService;
import com.pe.pcm.miscellaneous.PasswordUtilityService;
import com.pe.pcm.pgp.PGPManagerService;
import com.pe.pcm.reports.TransferInfoRepository;
import com.pe.pcm.reports.TransferInfoStagingRepository;
import com.pe.pcm.reports.entity.TransferInfoEntity;
import com.pe.pcm.reports.entity.TransferInfoStagingEntity;
import com.pe.pcm.settings.FileSchedulerConfigService;
import com.pe.pcm.utils.FileInfoModel;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.openpgp.PGPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import javax.crypto.CipherInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchProviderException;
import java.util.Arrays;
import java.util.Optional;
import java.util.Properties;

import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;
import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;
import static com.pe.pcm.utils.CommonFunctions.isNotNull;
import static com.pe.pcm.utils.CommonFunctions.isNullThrowCustomError;
import static java.lang.String.format;

/**
 * @author Kiran Reddy.
 */
@Service
public class ManageFileService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManageFileService.class);

    private final PGPManagerService pgpManagerService;
    private final AmazonS3FileService amazonS3FileService;
    private final FileArchiveProperties fileArchiveProperties;
    private final PasswordUtilityService passwordUtilityService;
    private final MultipartFileSenderService multipartFileSenderService;
    private final FileSchedulerConfigService fileSchedulerConfigService;
    private final TransferInfoRepository transferInfoRepository;
    private final TransferInfoStagingRepository transferInfoStagingRepository;

    @Autowired
    public ManageFileService(PasswordUtilityService passwordUtilityService,
                             PGPManagerService pgpManagerService,
                             AmazonS3FileService amazonS3FileService,
                             FileArchiveProperties fileArchiveProperties,
                             MultipartFileSenderService multipartFileSenderService,
                             FileSchedulerConfigService fileSchedulerConfigService,
                             TransferInfoRepository transferInfoRepository,
                             TransferInfoStagingRepository transferInfoStagingRepository) {
        this.passwordUtilityService = passwordUtilityService;
        this.pgpManagerService = pgpManagerService;
        this.amazonS3FileService = amazonS3FileService;
        this.fileArchiveProperties = fileArchiveProperties;
        this.multipartFileSenderService = multipartFileSenderService;
        this.fileSchedulerConfigService = fileSchedulerConfigService;
        this.transferInfoRepository = transferInfoRepository;
        this.transferInfoStagingRepository = transferInfoStagingRepository;
    }

    public FileSystemResource fileDownloadWithSFTP(FileInfoModel fileInfoModel, String remotePath) {
        Session session = null;
        Channel channel = null;
        ChannelSftp sftpChannel = null;

        try {
            JSch jsch = new JSch();
            session = jsch.getSession(fileInfoModel.getUserName(), fileInfoModel.getHost(), fileInfoModel.getPort());
            session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
            session.setPassword(fileInfoModel.getPassword());
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();

            channel = session.openChannel("sftp");
            channel.connect();

            sftpChannel = (ChannelSftp) channel;
            LOGGER.info("Downloading file from Mailbox");
            File tempFile = File.createTempFile("tempfile", ""); //This needs to be changes to normal file creation
            sftpChannel.get(remotePath, tempFile.getAbsolutePath());

            LOGGER.info("Successfully Downloaded the file from Mailbox.");
            session.disconnect();
            tempFile.deleteOnExit(); //This will delete the file when thread close
            return new FileSystemResource(tempFile);
        } catch (JSchException | SftpException | IOException e) {
            e.printStackTrace();
            throw internalServerError("Error: while reading data from Mailbox: " + e.getMessage());
        } finally {
            if (sftpChannel != null) {
                sftpChannel.exit();
            }
            if (channel != null) {
                channel.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }
    }

    public void fileDropWithSFTP(FileInfoModel fileInfoModel, InputStream stream) {
        Session session = null;
        Channel channel = null;
        ChannelSftp sftpChannel = null;
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(fileInfoModel.getUserName(), fileInfoModel.getHost(), fileInfoModel.getPort());
            session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
            session.setPassword(fileInfoModel.getPassword());
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();

            channel = session.openChannel("sftp");
            channel.connect();

            sftpChannel = (ChannelSftp) channel;

            String fullPath;
            if (isNotNull(fileInfoModel.getFilePath())) {
                fullPath = fileInfoModel.getFilePath() + (fileInfoModel.getFilePath().endsWith("/") ? "" : "/") + fileInfoModel.getFileName();
                LOGGER.info("Mailbox path and FileName is : {}", fullPath);
            } else {
                throw internalServerError("Please provide the suffix path for the filename.");
            }

            createDirs(fileInfoModel.getFilePath(), sftpChannel);

            LOGGER.info("Dropping file into {}", fullPath);
            sftpChannel.put(stream, fullPath);
            LOGGER.info("Successfully dropped the file.");
        } catch (JSchException | SftpException e) {
            throw internalServerError("Error: while uploading data to Mailbox:- " + e.getMessage());
        } finally {
            if (sftpChannel != null) {
                sftpChannel.exit();
            }
            if (channel != null) {
                channel.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
                LOGGER.error("Unable to close the file stream.");
            }

        }
    }

    private void createDirs(String dirPath, ChannelSftp sftp) {
        if (dirPath != null && !dirPath.isEmpty() && sftp != null) {
            String[] dirs = Arrays.stream(dirPath.split("/"))
                    .filter(StringUtils::isNotBlank)
                    .toArray(String[]::new);

            for (String dir : dirs) {
                try {
                    sftp.cd(dir);
                    LOGGER.info("Change directory {}", dir);
                } catch (Exception e) {
                    try {
                        sftp.mkdir(dir);
                        LOGGER.info("Create directory {}", dir);
                    } catch (SftpException e1) {
                        LOGGER.error("Create directory failure, directory:{}", dir, e1);
                    }
                    try {
                        sftp.cd(dir);
                        LOGGER.info("Change directory {}", dir);
                    } catch (SftpException e1) {
                        LOGGER.error("Change directory failure, directory:{}", dir, e1);
                    }
                }
            }
        }
    }

    public FileSystemResource loadFileAsFsResourceForDownload(String filePath) {
        if (org.springframework.util.StringUtils.hasText(filePath)) {
            Path path = Paths.get(filePath);
            if (Files.exists(path)) {
                return new FileSystemResource(path.toFile());
            } else {
                throw internalServerError("File Not Found In the Given Path: " + filePath);
            }
        } else {
            throw internalServerError("File path is null");
        }
    }

    private FileSystemResource decryptFile(File tempFile, String encType, String filePath, String type) {
        if (isNotNull(encType)) {
            if (encType.equalsIgnoreCase("PGP")) {
                try (FileInputStream fis = new FileInputStream(filePath)) {
                    pgpManagerService.decryptFile(fis,
                            new FileInputStream(getPgpPrivateKeyPath(fileArchiveProperties.getPgp().getPrivateKey())),
                            fileArchiveProperties.getPgp().getCmks(),
                            tempFile
                    );
                } catch (NoSuchProviderException | PGPException | IOException e) {
                    LOGGER.error(e.getMessage());
                    LOGGER.debug("", e);
                    throw internalServerError("Unable to process the request, please contact sysadmin");
                }
            } else if (encType.equalsIgnoreCase("AES")) {
                try (InputStream inputStream = FileUtils.openInputStream(FileUtils.getFile(filePath));
                     CipherInputStream cipherInputStream = passwordUtilityService.decryptFileWithJava8(inputStream)) {
                    try (FileOutputStream os = new FileOutputStream(tempFile.getAbsolutePath(), false)) {
                        int i;
                        byte[] block = new byte[2048];
                        while ((i = cipherInputStream.read(block)) != -1) {
                            os.write(block, 0, i);
                        }
                    }
                } catch (IOException e) {
                    throw internalServerError("unable to process the request, please try after sometime.");
                }
            } else {
                throw internalServerError(format("Encryption type is not available, type: %s. please contact Community Manger Team.", type));
            }
            tempFile.deleteOnExit(); // delete temp file on exit
            return new FileSystemResource(tempFile);
        } else {
            throw internalServerError("Encryption type is Null/Empty, please contact Community Manger Team.");
        }
    }

    public FileSystemResource loadFileAsFsResourceForDownload(Long seqId, String type) {
        Optional<TransferInfoEntity> optionalTransferInfoEntity = transferInfoRepository.findById(seqId);
        if (optionalTransferInfoEntity.isPresent()) {
            return loadFileAsFsResourceForDownload(optionalTransferInfoEntity.get(), type);
        } else {
            TransferInfoEntity transferInfoEntity = new TransferInfoEntity();
            TransferInfoStagingEntity transferInfoStagingEntity =
                    transferInfoStagingRepository.findById(seqId).orElseThrow(() -> notFound("Transfer info Staging"));
            BeanUtils.copyProperties(transferInfoStagingEntity, transferInfoEntity);
            return loadFileAsFsResourceForDownload(transferInfoEntity, type);
        }
    }

    private FileSystemResource loadFileAsFsResourceForDownload(TransferInfoEntity transferInfoEntity, String type) {
        String fileName;
        String filePath;
        if (type.equals("source")) {
            filePath = transferInfoEntity.getSrcarcfileloc();
            fileName = transferInfoEntity.getSrcfilename();
        } else if (type.equals("destination")) {
            filePath = transferInfoEntity.getDestarcfileloc();
            fileName = transferInfoEntity.getDestfilename();
        } else {
            throw internalServerError("Please provide the required type as source/destination");
        }
        isNullThrowCustomError.apply(filePath, org.springframework.util.StringUtils.capitalize(type) + " file path is Null/Empty, please check with Community Manager Team.");
        Path path = Paths.get(filePath);
        String encType = transferInfoEntity.getEncType();
        String isEncrypted = transferInfoEntity.getIsEncrypted();
        LOGGER.info("File info: encType, isEncrypted - {}, {}", encType, isEncrypted);
        if (Files.exists(path)) {
            if (isNotNull(isEncrypted) && isEncrypted.equalsIgnoreCase("Y")) {
                File tempFile;
                try {
                    tempFile = File.createTempFile(fileName, "");
                    LOGGER.info("Created a temp file to store the data, file path: {}", tempFile.getAbsolutePath());
                } catch (IOException e) {
                    throw internalServerError("Unable to download the file, please reach sysadmin.");
                }
                return decryptFile(tempFile, encType, filePath, type);
            } else {
                return new FileSystemResource(path.toFile());
            }
        } else {
            LOGGER.info("File not available in given path: {}", path);
            try {
                FileSchedulerModel fileSchedulerModel = fileSchedulerConfigService.get();
                File s3TempFile = File.createTempFile(fileName, "");
                amazonS3FileService.loadFileFromS3(s3TempFile, filePath, fileSchedulerModel);
                s3TempFile.deleteOnExit();
                if (isNotNull(isEncrypted) && isEncrypted.equalsIgnoreCase("Y")) {
                    File tempFile = File.createTempFile(fileName, "");
                    LOGGER.info("Created a temp file to store the data, file path: {}", tempFile.getAbsolutePath());
                    return decryptFile(tempFile, encType, s3TempFile.getAbsolutePath(), type);
                } else {
                    return new FileSystemResource(s3TempFile);
                }
            } catch (IOException e) {
                LOGGER.error("", e);
                throw internalServerError("Unable to download the file, please reach sysadmin.");
            } catch (CommunityManagerServiceException cme) {
                if (cme.getStatusCode() == 401) {
                    throw internalServerError("File is not available in given path , Path: " + path);
                }
                throw internalServerError(cme.getErrorMessage() + "; file is not available in filesystem:" + path);
            }
        }
    }

    public String loadFileAsStringForView(Long seqId, String type) {
        FileSystemResource fileSystemResource;
        Optional<TransferInfoEntity> optionalTransferInfoEntity = transferInfoRepository.findById(seqId);
        if (optionalTransferInfoEntity.isPresent()) {
            fileSystemResource = loadFileAsFsResourceForDownload(optionalTransferInfoEntity.get(), type);
        } else {
            TransferInfoEntity transferInfoEntity = new TransferInfoEntity();
            TransferInfoStagingEntity transferInfoStagingEntity = transferInfoStagingRepository.findById(seqId).orElseThrow(() -> notFound("Transfer info Staging"));
            BeanUtils.copyProperties(transferInfoStagingEntity, transferInfoEntity);
            fileSystemResource = loadFileAsFsResourceForDownload(transferInfoEntity, type);
        }

        final long fileLength = fileSystemResource.getFile().length();
        if (fileLength > 10485760) {
            LOGGER.info("File size is {}", fileLength);
            return "The file size is too large to view, please download the file to view.";
        }
        try (InputStream in = fileSystemResource.getInputStream()) {
            byte[] bytes = new byte[(int) fileLength];
            int offset = 0;
            while (offset < bytes.length) {
                int result = in.read(bytes, offset, bytes.length - offset);
                if (result == -1) {
                    break;
                }
                offset += result;
            }
            return new String(bytes, StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            LOGGER.debug("", e);
            throw internalServerError("Unable to process the request, please contact sysadmin");
        }
    }

    /**
     * This is for Dev Testing
     */
//    public void encryptFileUsingPGP() {
//        try (FileOutputStream oFile = new FileOutputStream("D:\\test\\sspdata-enc.txt", false)) {
//            pgpManagerService.encryptFile(
//                    "C:\\test\\sample-data.txt",
//                    new FileInputStream(getPgpPrivateKeyPath(fileArchiveProperties.getPgp().getPrivateKey())),
//                    oFile, false, false);
//        } catch (PGPException | NoSuchProviderException | IOException e) {
//            e.printStackTrace();
//        }
//    }
    public void stream(TransferInfoEntity transferInfoEntity, String type, HttpServletRequest request,
                       HttpServletResponse response) {
        String filePath;
        if (type.equals("source")) {
            filePath = transferInfoEntity.getSrcarcfileloc();
        } else if (type.equals("destination")) {
            filePath = transferInfoEntity.getDestarcfileloc();
        } else {
            throw internalServerError("Please provide the required type as source/destination");
        }
        try {
            multipartFileSenderService.serveResource(Paths.get(filePath), request, response);
        } catch (Exception e) {
            throw internalServerError("unable to download the file. Please contact sysadmin");
        }
    }

    private String getPgpPrivateKeyPath(String keyFileName) {
        String currPath = new File(".").getAbsolutePath();
        return currPath.substring(0, currPath.length() - 1) + "pgpkeys" + File.separator + keyFileName;
    }

}
