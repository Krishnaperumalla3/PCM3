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

package com.pe.pcm.pem;

import com.jcraft.jsch.*;
import com.pe.pcm.certificate.SshKeyPairRepository;
import com.pe.pcm.certificate.entity.SshKeyPairEntity;
import com.pe.pcm.common.CommunityManagerKeyValueModel;
import com.pe.pcm.common.CommunityManagerNameModel;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.login.PwdPolicyRepository;
import com.pe.pcm.login.YfsUserRepository;
import com.pe.pcm.login.entity.PwdPolicyEntity;
import com.pe.pcm.miscellaneous.APIConsumerUtilityService;
import com.pe.pcm.partner.PartnerRepository;
import com.pe.pcm.pem.entity.PemPartnerCodeEntity;
import com.pe.pcm.protocol.as2.si.SftpProfileRepository;
import com.pe.pcm.protocol.as2.si.entity.SftpProfileEntity;
import com.pe.pcm.protocol.as2.si.pem.RoutchanTemplateRepository;
import com.pe.pcm.protocol.remoteftp.RemoteFtpRepository;
import com.pe.pcm.protocol.remoteftp.entity.RemoteFtpEntity;
import com.pe.pcm.ssp.InputModel;
import com.pe.pcm.ssp.ValueModel;
import com.pe.pcm.utils.CommonFunctions;
import com.pe.pcm.utils.FileInfoModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.passay.CharacterData;
import org.passay.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;
import static com.pe.pcm.utils.CommonFunctions.apacheDecodeBase64;
import static com.pe.pcm.utils.CommonFunctions.isNotNull;
import static com.pe.pcm.utils.PCMConstants.HEX;
import static java.lang.System.out;
import static java.time.LocalDate.parse;

/**
 * @author Kiran Reddy.
 */
@Service
public class PemUtilityService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PemUtilityService.class);

    private final PemPartnerCodeRepository pemPartnerCodeRepository;
    private final RoutchanTemplateRepository routchanTemplateRepository;
    private final PwdPolicyRepository pwdPolicyRepository;
    private final YfsUserRepository yfsUserRepository;
    private final RemoteFtpRepository remoteFtpRepository;
    private final PartnerRepository partnerRepository;
    @Value("${pem.remote.server.base-directory.path}")
    private String baseDirectory;
    @Value("${pem.remote.server.pem-key}")
    private String privateKey;
    @Value("${pem.remote.server.session-timeout}")
    private Integer sessionTimeout;
    private final APIConsumerUtilityService apiConsumerUtilityService;
    private final SshKeyPairRepository sshKeyPairRepository;
    private final SftpProfileRepository sftpProfileRepository;

    @Autowired
    public PemUtilityService(PemPartnerCodeRepository pemPartnerCodeRepository,
                             RoutchanTemplateRepository routchanTemplateRepository,
                             PwdPolicyRepository pwdPolicyRepository,
                             YfsUserRepository yfsUserRepository,
                             RemoteFtpRepository remoteFtpRepository,
                             PartnerRepository partnerRepository,
                             APIConsumerUtilityService apiConsumerUtilityService, SshKeyPairRepository sshKeyPairRepository, SftpProfileRepository sftpProfileRepository) {
        this.pemPartnerCodeRepository = pemPartnerCodeRepository;
        this.routchanTemplateRepository = routchanTemplateRepository;
        this.pwdPolicyRepository = pwdPolicyRepository;
        this.yfsUserRepository = yfsUserRepository;
        this.remoteFtpRepository = remoteFtpRepository;
        this.partnerRepository = partnerRepository;
        this.apiConsumerUtilityService = apiConsumerUtilityService;
        this.sshKeyPairRepository = sshKeyPairRepository;
        this.sftpProfileRepository = sftpProfileRepository;
    }

    /**
     * Save Partner Codes
     */
    public void savePartnerCode(List<CommunityManagerKeyValueModel> communityManagerKeyValueModelList) {

        communityManagerKeyValueModelList.forEach(communityManagerKeyValueModel ->
                pemPartnerCodeRepository.save(new PemPartnerCodeEntity(communityManagerKeyValueModel.getKey(), communityManagerKeyValueModel.getValue()))
        );
    }

    /**
     * Delete Partner Codes
     */
    public void deletePartnerCode(List<CommunityManagerKeyValueModel> communityManagerKeyValueModelList) {
        communityManagerKeyValueModelList.forEach(communityManagerKeyValueModel ->
                pemPartnerCodeRepository.delete(new PemPartnerCodeEntity(communityManagerKeyValueModel.getKey(), communityManagerKeyValueModel.getValue()))
        );
    }

    /**
     * Get Partner Codes
     */
    public CommunityManagerKeyValueModel getPartnerCodeByName(String partnerName) {

        CommunityManagerKeyValueModel communityManagerKeyValueModel = new CommunityManagerKeyValueModel();
        pemPartnerCodeRepository.findById(partnerName).ifPresent(pemPartnerCodeEntity -> {
            communityManagerKeyValueModel.setKey(pemPartnerCodeEntity.getPartnerName());
            communityManagerKeyValueModel.setValue(pemPartnerCodeEntity.getPartnerCode());
        });
        return communityManagerKeyValueModel;
    }

    /**
     * Fetch Template Names
     */
    public List<CommunityManagerNameModel> getTemplateNames() {
        return routchanTemplateRepository.findAllByOrderByTmplNameAsc()
                .orElse(new ArrayList<>())
                .stream()
                .map(routchanTemplateEntity -> new CommunityManagerNameModel(routchanTemplateEntity.getTmplName()))
                .collect(Collectors.toList());
    }

    public void fileDrop(FileInfoModel fileInfoModel) {
        FTPClient ftp;
        try {

            ftp = new FTPClient();
            ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(out)));
            ftp.connect(fileInfoModel.getHost(), fileInfoModel.getPort());

            if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
                ftp.disconnect();
                throw new IOException("Exception in connecting to FTP Server");
            }
            ftp.login(fileInfoModel.getUserName(), fileInfoModel.getPassword());
//            String directory = baseDirectory + (isNotNull(fileInfoModel.getFilePath()) ? fileInfoModel.getFilePath() : "")
            String directory = fileInfoModel.getFilePath();
            LOGGER.info("Creating Directory if not available: Directory = {}", directory);
            ftp.makeDirectory(directory);
            InputStream stream = new ByteArrayInputStream(isNotNull(fileInfoModel.getContent()) ? fileInfoModel.getContent().getBytes() : "".getBytes());
            ftp.storeFile(directory + "/" + fileInfoModel.getFileName(), stream);

            /*To Store the file in local filesystem*/
//                Files.createDirectories(Paths.get(directory))
//                LOGGER.info("Writing file into the given location")
//                Files.write(Paths.get(directory + "/" + fileInfoModel.getFileName()), isNotNull(fileInfoModel.getContent()) ? fileInfoModel.getContent().getBytes() : "".getBytes())


        } catch (IOException e) {
            throw GlobalExceptionHandler.internalServerError(e.getMessage());
        }
    }

    public void dropFile(FileInfoModel fileInfoModel) {
        if (fileInfoModel.isLocalDirectory()) {
            fileDopInLocalDirectory(fileInfoModel);
        } else {
            fileDropWithSFTP(fileInfoModel);
        }
    }

    private void fileDropWithSFTP(FileInfoModel fileInfoModel) {
        Session session;
        try {
            JSch jsch = new JSch();
            if (fileInfoModel.getIsPemKey()) {
                jsch.addIdentity(privateKey);
                LOGGER.info("PEM Key has been loaded to Secure channel");
            }
            session = jsch.getSession(fileInfoModel.getUserName(), fileInfoModel.getHost(), fileInfoModel.getPort());
            session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
            session.setPassword(fileInfoModel.getPassword());
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setTimeout(sessionTimeout);
            session.setConfig(config);
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();

            ChannelSftp sftpChannel = (ChannelSftp) channel;

            String fullPath;
            if (isNotNull(fileInfoModel.getFilePath())) {
                fullPath = fileInfoModel.getFilePath() + (fileInfoModel.getFilePath().endsWith("/") ? "" : "/") + fileInfoModel.getFileName();
                LOGGER.info("Absolute path is : {}", fullPath);
            } else {
                throw internalServerError("Please provide the suffix path for the filename.");
            }

            createDirs(fileInfoModel.getFilePath(), sftpChannel);

            InputStream stream = new ByteArrayInputStream(isNotNull(fileInfoModel.getContent()) ? fileInfoModel.getContent().getBytes() : "".getBytes());
            LOGGER.info("Dropping file into/as {}", fullPath);
            sftpChannel.put(stream, fullPath);
            LOGGER.info("Successfully dropped the file.");
            sftpChannel.exit();
            LOGGER.info("Before session disconnecting.");
            session.disconnect();
        } catch (Exception e) {
            LOGGER.error("{}", "Error : ", e);
            throw internalServerError(e.getMessage());
        }
    }


    private void fileDopInLocalDirectory(FileInfoModel fileInfoModel) {
        if (!isNotNull(fileInfoModel.getFilePath())) {
            throw internalServerError("Please provide the filePath");
        }
        if (!isNotNull(fileInfoModel.getFileName())) {
            throw internalServerError("Please provide the fileName");
        }
        try {
            Files.write(Paths.get(fileInfoModel.getFilePath() + "/" + fileInfoModel.getFileName()), isNotNull(fileInfoModel.getContent()) ? fileInfoModel.getContent().getBytes() : "Test".getBytes());
        } catch (Exception e) {
            LOGGER.error("{}", "Error : ", e);
            throw internalServerError(e.getMessage());
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

    public String getFirstStringValue(StringConcatenationModel stringConcatenationModel) {
        if (isNotNull(stringConcatenationModel.getString1())) {
            return stringConcatenationModel.getString1();
        }
        if (isNotNull(stringConcatenationModel.getString2())) {
            return stringConcatenationModel.getString2();
        }
        if (isNotNull(stringConcatenationModel.getString3())) {
            return stringConcatenationModel.getString3();
        }
        if (isNotNull(stringConcatenationModel.getString4())) {
            return stringConcatenationModel.getString4();
        }
        if (isNotNull(stringConcatenationModel.getString5())) {
            return stringConcatenationModel.getString5();
        }
        if (isNotNull(stringConcatenationModel.getString6())) {
            return stringConcatenationModel.getString6();
        }
        if (isNotNull(stringConcatenationModel.getString7())) {
            return stringConcatenationModel.getString7();
        }
        if (isNotNull(stringConcatenationModel.getString8())) {
            return stringConcatenationModel.getString8();
        }
        if (isNotNull(stringConcatenationModel.getString9())) {
            return stringConcatenationModel.getString9();
        }
        if (isNotNull(stringConcatenationModel.getString10())) {
            return stringConcatenationModel.getString10();
        }
        if (isNotNull(stringConcatenationModel.getString11())) {
            return stringConcatenationModel.getString11();
        }
        if (isNotNull(stringConcatenationModel.getString12())) {
            return stringConcatenationModel.getString12();
        }
        if (isNotNull(stringConcatenationModel.getString13())) {
            return stringConcatenationModel.getString13();
        }
        if (isNotNull(stringConcatenationModel.getString14())) {
            return stringConcatenationModel.getString14();
        }
        if (isNotNull(stringConcatenationModel.getString15())) {
            return stringConcatenationModel.getString15();
        }
        if (isNotNull(stringConcatenationModel.getString16())) {
            return stringConcatenationModel.getString16();
        }
        if (isNotNull(stringConcatenationModel.getString17())) {
            return stringConcatenationModel.getString17();
        }
        if (isNotNull(stringConcatenationModel.getString18())) {
            return stringConcatenationModel.getString18();
        }
        if (isNotNull(stringConcatenationModel.getString19())) {
            return stringConcatenationModel.getString19();
        }
        if (isNotNull(stringConcatenationModel.getString20())) {
            return stringConcatenationModel.getString20();
        }
        return "";
    }

    @PostConstruct
    public void initB2bUrl() {
        if (isNotNull(this.baseDirectory))
            this.baseDirectory = (this.baseDirectory.endsWith("/") || this.baseDirectory.endsWith("\\")) ? this.baseDirectory : (this.baseDirectory + "/");
        else this.baseDirectory = "/";

        if (sessionTimeout == null)
            sessionTimeout = 5000;
        else if (sessionTimeout < 3000)
            sessionTimeout = 3000;
    }

    public String getCurrentDate(String format) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public List<PemAccountExpiryModel> getExpiryUsersWithMailIds(int remainDays) {
        List<PemAccountExpiryModel> pemAccountExpiryModels = new ArrayList<>();
        List<PwdPolicyEntity> pwdPolicyEntities = pwdPolicyRepository.findAllByOrderByPolicyId().orElse(new ArrayList<>());
        List<RemoteFtpEntity> remoteFtpEntities = remoteFtpRepository.findAllByIsHubInfo("Y").orElse(new ArrayList<>());
        List<String> uniqueUserList = remoteFtpEntities.stream().map(RemoteFtpEntity::getUserId).distinct().collect(Collectors.toList());

        int partitionSize = 999;
        List<List<String>> partitions = new LinkedList<>();
        for (int i = 0; i < uniqueUserList.size(); i += partitionSize) {
            partitions.add(uniqueUserList.subList(i,
                    Math.min(i + partitionSize, uniqueUserList.size())));
        }
        partitions.forEach(userList -> pwdPolicyEntities.forEach(pwdPolicyEntity -> yfsUserRepository.findAllByUsernameIn(userList)
                .orElse(new ArrayList<>())
                .stream()
                .filter(yfsUserEntity -> yfsUserEntity.getPasswordPolicyId() != null && yfsUserEntity.getPasswordPolicyId().equals(pwdPolicyEntity.getPolicyId()))
                .collect(Collectors.toList())
                .forEach(yfsUserEntity -> {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String pwdLastChangedOn = simpleDateFormat.format(yfsUserEntity.getPwdlastchangedon());
                    String currentDate = simpleDateFormat.format(new Timestamp(System.currentTimeMillis()));
                    long noOfDays = parse(pwdLastChangedOn).until(parse(currentDate), ChronoUnit.DAYS);
                    if (!pwdPolicyEntity.getNumDaysValid().equalsIgnoreCase("0")) {
                        long noOfDaysRemain = Long.parseLong(pwdPolicyEntity.getNumDaysValid()) - noOfDays;
                        if (noOfDaysRemain <= remainDays) {
                            partnerRepository.findAllByPkIdIn(remoteFtpEntities.stream()
                                    .filter(remoteFtpEntity -> isNotNull(remoteFtpEntity.getUserId()) && remoteFtpEntity.getUserId().equals(yfsUserEntity.getUsername()))
                                    .map(RemoteFtpEntity::getSubscriberId)
                                    .collect(Collectors.toList())
                            ).orElse(new ArrayList<>()).forEach(partnerEntity ->
                                    pemAccountExpiryModels.add(new PemAccountExpiryModel().setUserName(yfsUserEntity.getUsername())
                                            .setProfileName(partnerEntity.getTpName())
                                            .setProfileId(partnerEntity.getTpId())
                                            .setPemIdentifier(partnerEntity.getPemIdentifier())
                                            .setEmailId(partnerEntity.getEmail()))
                            );
                        }
                    }
                }))
        );
        return pemAccountExpiryModels;
    }

    public List<PemAccountExpiryModel> getExpiryUserIdentityKeys(Integer noOfDays, String uidKeyName, Boolean isAuthKey) {
        List<PemAccountExpiryModel> pemAccountExpiryModels = new ArrayList<>();
        Map<String, Timestamp> selectedKeys = new HashMap<>();
        List<SshKeyPairEntity> sshKeyPairEntities = new ArrayList<>();
        if (isNotNull(noOfDays)) {
            sshKeyPairEntities = sshKeyPairRepository.findAllByModifiedBy("U").orElse(new ArrayList<>());
        } else if (isNotNull(uidKeyName)) {
            selectedKeys.put(uidKeyName, new Timestamp(0L));
        }
        int partitionSize = 999;
        List<List<SshKeyPairEntity>> partitions = new LinkedList<>();
        for (int i = 0; i < sshKeyPairEntities.size(); i += partitionSize) {
            partitions.add(sshKeyPairEntities.subList(i,
                    Math.min(i + partitionSize, sshKeyPairEntities.size())));
        }
        partitions.forEach(sshKeyPairList -> sshKeyPairList.forEach(sshKeyPairEntity -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String createdDate = simpleDateFormat.format(sshKeyPairEntity.getCreateDate());
            String currentDate = simpleDateFormat.format(new Timestamp(System.currentTimeMillis()));
            long noOfDaysUntilCurrentDate = parse(createdDate).until(parse(currentDate), ChronoUnit.DAYS);
            if (noOfDaysUntilCurrentDate >= noOfDays) {
                selectedKeys.put(sshKeyPairEntity.getName(), sshKeyPairEntity.getCreateDate());
            }
        }));
        selectedKeys.forEach((keyName, createDate) -> sftpProfileRepository.findAllByUserIdentityKeyName(keyName)
                .ifPresent(sftpProfileEntities -> {
                            //Info: On Alok Request isAuthKey added to filter with publicKey on 30-06-2022.
                            List<String> pkIds;
                            if (isNotNull(isAuthKey) ? isAuthKey : false) {
                                pkIds = remoteFtpRepository.findAllByProfileIdIn(sftpProfileEntities
                                                .stream()
                                                .filter(sftpProfileEntity -> sftpProfileEntity.getPreferredAuth().equals("publickey"))
                                                .map(SftpProfileEntity::getProfileId)
                                                .collect(Collectors.toList())).orElse(new ArrayList<>()).stream()
                                        .filter(remoteFtpEntity -> remoteFtpEntity.getPrfAuthType().equals("publickey"))
                                        .map(RemoteFtpEntity::getSubscriberId)
                                        .collect(Collectors.toList());
                            } else {
                                pkIds = remoteFtpRepository.findAllByProfileIdIn(sftpProfileEntities
                                                .stream()
                                                .map(SftpProfileEntity::getProfileId)
                                                .collect(Collectors.toList())).orElse(new ArrayList<>()).stream()
                                        .map(RemoteFtpEntity::getSubscriberId)
                                        .collect(Collectors.toList());
                            }
                            partnerRepository.findAllByPkIdIn(pkIds)
                                    .orElse(new ArrayList<>()).forEach(partnerEntity ->
                                            pemAccountExpiryModels.add(new PemAccountExpiryModel()
                                                    .setKeyName(keyName)
                                                    .setCreateDate(createDate)
                                                    .setProfileName(partnerEntity.getTpName())
                                                    .setProfileId(partnerEntity.getTpId())
                                                    .setPemIdentifier(partnerEntity.getPemIdentifier())
                                                    .setEmailId(partnerEntity.getEmail()))
                                    );
                        }
                ));
        return pemAccountExpiryModels;
    }

    public List<PemAccountExpiryModel> getExpiryUsersWithMailIdsByLastUpdated(int days) {
        Timestamp timestamp = CommonFunctions.minusDays(new Date(System.currentTimeMillis()), days);
        List<PemAccountExpiryModel> pemAccountExpiryModels = new ArrayList<>();
        List<RemoteFtpEntity> remoteFtpEntities = remoteFtpRepository.findAllByIsHubInfo("Y").orElse(new ArrayList<>());
        List<String> uniqueUserList = remoteFtpEntities.stream().map(RemoteFtpEntity::getUserId).distinct().collect(Collectors.toList());

        int partitionSize = 999;
        List<List<String>> partitions = new LinkedList<>();
        for (int i = 0; i < uniqueUserList.size(); i += partitionSize) {
            partitions.add(uniqueUserList.subList(i,
                    Math.min(i + partitionSize, uniqueUserList.size())));
        }
        partitions.forEach(userList ->
                yfsUserRepository.findAllByUsernameInAndPwdlastchangedonBefore(userList, timestamp)
                        .orElse(new ArrayList<>())
                        .forEach(yfsUserEntity ->
                                partnerRepository.findAllByPkIdIn(remoteFtpEntities.stream()
                                        .filter(remoteFtpEntity -> remoteFtpEntity.getUserId().equals(yfsUserEntity.getUsername()))
                                        .map(RemoteFtpEntity::getSubscriberId)
                                        .collect(Collectors.toList())
                                ).orElse(new ArrayList<>()).forEach(partnerEntity -> pemAccountExpiryModels.add(new PemAccountExpiryModel().setUserName(yfsUserEntity.getUsername())
                                        .setProfileName(partnerEntity.getTpName())
                                        .setProfileId(partnerEntity.getTpId())
                                        .setPemIdentifier(isNotNull(partnerEntity.getPemIdentifier()) ? partnerEntity.getPemIdentifier() : "")
                                        .setEmailId(partnerEntity.getEmail())
                                        .setPwdLastUpdatedDate(yfsUserEntity.getPwdlastchangedon()))
                                )
                        )
        );
        return pemAccountExpiryModels.stream()
                .sorted(Comparator.comparing(PemAccountExpiryModel::getPemIdentifier))
                .collect(Collectors.toList());
    }

    public List<CommunityManagerNameModel> getValuesFromCSV(PemConvertCSVModel pemConvertCSVModel) {
        byte[] decodedBytes = Base64.getDecoder().decode(pemConvertCSVModel.getBase64OfCSV());
        String data = new String(decodedBytes, StandardCharsets.UTF_8);
        String[] arr = data.contains(",") ? data.split(",") : data.contains(";") ? data.split(";") : data.split("\n");
        if (isNotNull(pemConvertCSVModel.getRow())) {
            if (pemConvertCSVModel.getRow() < arr.length) {
                return Collections.singletonList(new CommunityManagerNameModel(arr[pemConvertCSVModel.getRow()].trim()));
            } else {
                throw internalServerError("Provided index is out of range actual range is " + arr.length);
            }
        } else {
            return Arrays
                    .stream(arr)
                    .map(s -> new CommunityManagerNameModel(s.trim()))
                    .collect(Collectors.toList());
        }
    }

    public String getCustomPassword(Integer upperCaseLettersCnt, Integer lowerCaseLettersCnt, Integer numberCnt, Integer specialCharCnt) {
        upperCaseLettersCnt = isNotNull(upperCaseLettersCnt) ? upperCaseLettersCnt : 2;
        lowerCaseLettersCnt = isNotNull(lowerCaseLettersCnt) ? lowerCaseLettersCnt : 2;
        numberCnt = isNotNull(numberCnt) ? numberCnt : 2;
        specialCharCnt = isNotNull(specialCharCnt) ? specialCharCnt : 2;
        int passwordLength = Stream.of(upperCaseLettersCnt, lowerCaseLettersCnt, numberCnt, specialCharCnt).reduce(0, Integer::sum);
        if (passwordLength < 8) {
            throw internalServerError("Password length should not be less than 8.");
        }
        CharacterRule lowerCaseRule = new CharacterRule(EnglishCharacterData.LowerCase);
        lowerCaseRule.setNumberOfCharacters(lowerCaseLettersCnt);
        CharacterRule upperCaseRule = new CharacterRule(EnglishCharacterData.UpperCase);
        upperCaseRule.setNumberOfCharacters(upperCaseLettersCnt);
        CharacterRule digitRule = new CharacterRule(EnglishCharacterData.Digit);
        digitRule.setNumberOfCharacters(numberCnt);
        CharacterData specialChars = new CharacterData() {
            public String getErrorCode() {
                return AllowedCharacterRule.ERROR_CODE;
            }

            public String getCharacters() {
                return "!@#$%^*_-/.";
            }
        };
        CharacterRule splCharRule = new CharacterRule(specialChars);
        splCharRule.setNumberOfCharacters(specialCharCnt);
        return new PasswordGenerator().generatePassword(passwordLength, splCharRule, lowerCaseRule, upperCaseRule, digitRule);
    }

    public CustomProtocolResponseModel getCustomProtocolNames(String filePath) {
        List<ValueModel> namesList = new ArrayList<>();
        try {
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(Files.newInputStream(Paths.get(filePath)));
            while (xmlEventReader.hasNext()) {
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();
                    if (startElement.getName().getLocalPart().equals("AFTExtension")) {
                        Attribute nameAttr = startElement.getAttributeByName(new QName("name"));
                        namesList.add(new ValueModel(nameAttr.getValue()));
                    }
                }
            }
        } catch (Exception ex) {
            throw internalServerError("Error occurred while getting custom protocol names" + ex.getMessage());
        }
        return new CustomProtocolResponseModel().setData(namesList);
    }

    public CustomProtocolResponseModel getVarNames(String filePath, String customProtocolName) {
        CustomProtocolResponseModel customProtocolResponseModel = new CustomProtocolResponseModel();
        List<ValueModel> valueModels = new ArrayList<>();
        try {
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new FileInputStream(filePath));
            Attribute nameAttr = null;
            Attribute bpAttr;
            while (xmlEventReader.hasNext()) {
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();
                    if (startElement.getName().getLocalPart().equals("AFTExtension")) {
                        nameAttr = startElement.getAttributeByName(new QName("name"));
                    }
                    if (isNotNull(nameAttr) && nameAttr.getValue().equalsIgnoreCase(customProtocolName)) {
                        bpAttr = startElement.getAttributeByName(new QName("bp"));
                        if (isNotNull(bpAttr)) {
                            customProtocolResponseModel.setBp(bpAttr.getValue());
                            customProtocolResponseModel.setCustomProtocol(customProtocolName);
                        }
                        if (startElement.getName().getLocalPart().equals("VARDEF")) {
                            Attribute varName = startElement.getAttributeByName(new QName("varname"));
                            valueModels.add(new ValueModel(varName.getValue()));
                        }
                        customProtocolResponseModel.setData(valueModels);
                    }
                }
            }
        } catch (Exception ex) {
            throw internalServerError("Error occurred while getting custom protocol var names" + ex.getMessage());
        }
        return customProtocolResponseModel;
    }

    public String hexToAscii(String hexStr) {
        int n = hexStr.length();

        // Iterate over string
        for (int i = 0; i < n; i++) {

            char ch = hexStr.charAt(i);
            out.println();
            // Check if the character
            // is invalid
            if ((ch < '0' || ch > '9')
                    && (ch < 'A' || ch > 'F')) {

                System.out.println("No");
            }
        }

        return Arrays
                .stream(hexStr.split("(?<=\\G..)"))
                .map(s -> Character.toString((char) Integer.parseInt(s, 16)))
                .collect(Collectors.joining());
    }

    public String asciiToHex(String asciiStr) {
        char[] chars = asciiStr.toCharArray();
        StringBuilder hex = new StringBuilder();
        for (char ch : chars) {
            if (!Character.isLetter(ch) && !Character.isDigit(ch)) {
                hex.append(Integer.toHexString(ch));
            } else {
                hex.append(ch);
            }
        }
        return hex.toString();
    }

    public String asciiToHexHH(String s) {
        StringBuilder sb = new StringBuilder();
        int len = s.length();
        for (int i = 0; i < len; i++) {
            int ch = s.charAt(i);
            if ('A' <= ch && ch <= 'Z') {        // 'A'..'Z'
                sb.append((char) ch);
            } else if ('a' <= ch && ch <= 'z') {    // 'a'..'z'
                sb.append((char) ch);
            } else if ('0' <= ch && ch <= '9') {    // '0'..'9'
                sb.append((char) ch);
            } else if (ch == ' ') {            // space
                sb.append('+');
            } else if (ch == '-' || ch == '_'        // unreserved
                    || ch == '.' || ch == '!'
                    || ch == '~' || ch == '*'
                    || ch == '\'' || ch == '('
                    || ch == ')') {
                sb.append((char) ch);
            } else if (ch <= 0x007f) {        // other ASCII
                sb.append(HEX[ch]);
            } else if (ch <= 0x07FF) {        // non-ASCII <= 0x7FF
                sb.append(HEX[0xc0 | (ch >> 6)]);
                sb.append(HEX[0x80 | (ch & 0x3F)]);
            } else {                            // 0x7FF < ch <= 0xFFFF
                sb.append(HEX[0xe0 | (ch >> 12)]);
                sb.append(HEX[0x80 | ((ch >> 6) & 0x3F)]);
                sb.append(HEX[0x80 | (ch & 0x3F)]);
            }
        }
        return sb.toString();
    }

    public PemRegexOutPutModel regexFinder(InputModel inputModel) {
        try {
            Pattern pattern = Pattern.compile(inputModel.getPattern());
            List<ValueModel> notFound = inputModel.getData().stream().filter(valueModel -> isNotNull(valueModel.getValue()) && !pattern.matcher(valueModel.getValue())
                    .matches()).collect(Collectors.toList());
            if (notFound.isEmpty()) {
                return new PemRegexOutPutModel("Success", notFound);
            } else {
                return new PemRegexOutPutModel("Failure", notFound);
            }
        } catch (Exception ex) {
            throw internalServerError("Exception occurred while regex finding  " + ex.getMessage());
        }
    }

    public String convertComplianceTextReportToXML(ComplianceInputModel complianceInputModel) {
        String complianceReport = consumeComplianceAPI(complianceInputModel
                .setInput("<input><File>"
                        + apacheDecodeBase64.apply(complianceInputModel.getInput(), Boolean.TRUE)
                        + "</File></input>"
                )
        );
        LOGGER.debug("Compliance SI API response: {}", complianceReport);
        if (complianceReport.contains("Report Entry") && complianceReport.contains("Report Entry:")) {
            return complianceTextToXML1(complianceReport);
        } else {
            return complianceTextToXML2(complianceReport);
        }
    }

    private String complianceTextToXML2(String complianceReport) {
        StringBuilder outputSb = new StringBuilder("<status_report>");
        try {
            AtomicBoolean isErrorInfoLoaded = new AtomicBoolean(false);
            Arrays.asList(complianceReport.split("\n"))
                    .forEach(line -> {
                        if (line != null && line.length() > 0) {
                            if (line.startsWith("<?xml version")) {
                                outputSb.append(line.substring(line.indexOf("?><"), line.indexOf("<![")).replace("?>", ""));
                            } else {
                                if (!isErrorInfoLoaded.get()) {
                                    outputSb.append("<error>")
                                            .append(line)
                                            .append("</error>");
                                    isErrorInfoLoaded.set(true);
                                } else if (line.contains("]]></")) {
                                    String[] dt = line.split("]]>");
                                    outputSb.append(splitAndConvertToXml(":", dt[0]))
                                            .append(dt[1]);
                                } else {
                                    outputSb.append(splitAndConvertToXml(":", line));
                                }
                            }
                        }
                    });
            outputSb.append("</status_report>");
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return outputSb.toString();
    }

    private String complianceTextToXML1(String complianceReport) {

        StringBuilder outputSb = new StringBuilder("<status_report>");
        AtomicReference<String> subTag = new AtomicReference<>();

        AtomicBoolean isInfoReportEntity = new AtomicBoolean(false);
        AtomicBoolean isErrorReportEntity = new AtomicBoolean(false);
        AtomicBoolean reportEntityOpen = new AtomicBoolean(false);
        AtomicBoolean isCodeAdded = new AtomicBoolean(false);
        AtomicBoolean isChildInfoReportEntity = new AtomicBoolean(false);
        AtomicBoolean isCodeDescJustAdded = new AtomicBoolean(false);
        try {
            Arrays.stream(complianceReport.split("\n"))
                    .forEach(line -> {
                        if (line != null && line.trim().length() > 0 && !(line.contains("<status_report>") || line.startsWith("============="))) {
                            if (line.contains("![CDATA[")) {
                                subTag.set(line.substring(line.indexOf("?><"), line.indexOf("<![CDATA")).trim().replace("?><", "<"));
                                outputSb.append(subTag.get());
                            } else if (line.endsWith("warning(s)")) {
                                outputSb.append("<warning>")
                                        .append(line.split(" ")[0])
                                        .append("</warning>");
                            } else if (line.endsWith("error(s)")) {
                                outputSb.append("<error>")
                                        .append(line.split(" ")[0])
                                        .append("</error>");
                            } else if (line.startsWith("Information:")) {
                                if (isChildInfoReportEntity.get()) {
                                    outputSb.append("</info>");
                                    isChildInfoReportEntity.set(false);
                                }
                                if (reportEntityOpen.get()) {
                                    outputSb.append("</report_entity>");
                                    reportEntityOpen.set(false);
                                }
                                if (isInfoReportEntity.get()) {
                                    outputSb.append("</information>");
                                    isInfoReportEntity.set(false);
                                }
                                outputSb.append("<information>");
                                isInfoReportEntity.set(true);
                            } else if (line.startsWith("Error(s):")) {
                                if (isChildInfoReportEntity.get()) {
                                    outputSb.append("</info>");
                                    isChildInfoReportEntity.set(false);
                                }
                                if (reportEntityOpen.get()) {
                                    outputSb.append("</report_entity>");
                                    reportEntityOpen.set(false);
                                }
                                if (isInfoReportEntity.get()) {
                                    outputSb.append("</information>");
                                    isInfoReportEntity.set(false);
                                }
                                outputSb.append("<errors>");
                                isErrorReportEntity.set(true);
                            } else if (line.startsWith("Report Entry:")) {
                                reportEntityOpen.set(true);
                                outputSb.append("<report_entity>");
                            } else if (reportEntityOpen.get() && line.contains("Code:")) {
                                isCodeAdded.set(true);
                                outputSb.append("<code_description>")
                                        .append(line.split(":")[1])
                                        .append("</code_description>");
                            } else if (reportEntityOpen.get() && isCodeAdded.get() && line.contains("Info:")) {
                                outputSb.append("<info>");
                                isCodeAdded.set(false);
                                isChildInfoReportEntity.set(true);
                            } else if (reportEntityOpen.get() && isChildInfoReportEntity.get() && !isCodeDescJustAdded.get() && !line.contains("]]></") && !line.contains("</status_report>")) {
                                String[] data = line.split(":");
                                outputSb.append("<entity>")
                                        .append("<code>")
                                        .append(data[0].trim())
                                        .append("</code>")
                                        .append("<description>")
                                        .append(data[1])
                                        .append("</description>");
                                isCodeDescJustAdded.set(true);
                            } else if (reportEntityOpen.get() && isChildInfoReportEntity.get() && isCodeDescJustAdded.get() && !line.contains("]]></") && !line.contains("</status_report>")) {
                                outputSb.append("<value>")
                                        .append(line.trim())
                                        .append("</value>")
                                        .append("</entity>");
                                isCodeDescJustAdded.set(false);
                            } else if ((isInfoReportEntity.get() || isErrorReportEntity.get()) && line.startsWith("]]>")) {
                                if (isChildInfoReportEntity.get()) {
                                    outputSb.append("</info>");
                                    isChildInfoReportEntity.set(false);
                                }
                                if (reportEntityOpen.get()) {
                                    outputSb.append("</report_entity>");
                                    reportEntityOpen.set(false);
                                }
                                if (isInfoReportEntity.get()) {
                                    outputSb.append("</information>");
                                    isInfoReportEntity.set(false);
                                }
                                if (isErrorReportEntity.get()) {
                                    outputSb.append("</errors>");
                                    isErrorReportEntity.set(false);
                                }
                            }
                        }
                    });
            outputSb.append(subTag.get().replace("<", "</"))
                    .append("</status_report>");
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return outputSb.toString();
    }

    private String consumeComplianceAPI(ComplianceInputModel complianceInputModel) {

        if (complianceInputModel.getMethod().equalsIgnoreCase("POST")) {
            return apiConsumerUtilityService.invokePostService(
                    complianceInputModel.getUrl(),
                    complianceInputModel.getUsername(),
                    complianceInputModel.getPassword(),
                    complianceInputModel.getInput()
            );
        } else {
            throw internalServerError("This Service will only support POST Operation.");
        }
    }

    private String splitAndConvertToXml(String regex, String line) {
        String[] data = line.split(regex);
        String tag = data[0].replace(" ", "");
        return "<" + tag + ">" + data[1] + "</" + tag + ">";
    }

}
