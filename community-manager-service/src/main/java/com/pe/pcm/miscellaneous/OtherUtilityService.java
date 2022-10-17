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

package com.pe.pcm.miscellaneous;

import com.amazonaws.regions.Regions;
import com.pe.pcm.application.RemoteApplicationStagingService;
import com.pe.pcm.application.sfg.RemoteFtpApplicationService;
import com.pe.pcm.common.CommunityManagerKeyValueModel;
import com.pe.pcm.common.CommunityManagerNameModel;
import com.pe.pcm.enums.Protocol;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.login.CommunityManagerLoginModel;
import com.pe.pcm.login.CommunityManagerTokenService;
import com.pe.pcm.login.LogoModel;
import com.pe.pcm.partner.RemoteFtpPartnerService;
import com.pe.pcm.partner.staging.RemotePartnerStagingService;
import com.pe.pcm.protocol.RemoteFtpService;
import com.pe.pcm.protocol.remoteftp.entity.RemoteFtpEntity;
import com.pe.pcm.utils.DataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;
import static com.pe.pcm.utils.CommonFunctions.isNotNull;

/**
 * @author Kiran Reddy.
 */

@Service
public class OtherUtilityService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OtherUtilityService.class);

    private final RemoteFtpPartnerService remoteFtpPartnerService;
    private final RemoteFtpApplicationService remoteFtpApplicationService;
    private final RemotePartnerStagingService remotePartnerStagingService;
    private final RemoteApplicationStagingService remoteApplicationStagingService;
    private final CommunityManagerTokenService communityManagerTokenService;
    private final RemoteFtpService remoteFtpService;
    private final Boolean isSMLogin;
    private final Boolean isB2bActive;
    private final String paramName;
    private final Boolean isMFTDuplicate;
    private final Integer dateRangeInHours;
    private final UserUtilityService userUtilityService;
    private final Environment environment;

    private boolean isCm = true;

    @Autowired
    public OtherUtilityService(RemoteFtpPartnerService remoteFtpPartnerService,
                               RemoteFtpApplicationService remoteFtpApplicationService,
                               RemotePartnerStagingService remotePartnerStagingService,
                               RemoteApplicationStagingService remoteApplicationStagingService,
                               CommunityManagerTokenService communityManagerTokenService,
                               RemoteFtpService remoteFtpService,
                               @Value("${login.sm.enable}") Boolean isSMLogin,
                               @Value("${sterling-b2bi.b2bi-api.active}") Boolean isB2bActive,
                               @Value("${login.sm.param-name}") String paramName,
                               @Value("${workFlow.duplicate.mft}") Boolean isMFTDuplicate,
                               @Value("${file-transfer.search.time-range}") Integer dateRangeInHours,
                               UserUtilityService userUtilityService, Environment environment) {
        this.remoteFtpPartnerService = remoteFtpPartnerService;
        this.remoteFtpApplicationService = remoteFtpApplicationService;
        this.remotePartnerStagingService = remotePartnerStagingService;
        this.remoteApplicationStagingService = remoteApplicationStagingService;
        this.communityManagerTokenService = communityManagerTokenService;
        this.remoteFtpService = remoteFtpService;
        this.isSMLogin = isSMLogin;
        this.isB2bActive = isB2bActive;
        this.paramName = paramName;
        this.isMFTDuplicate = isMFTDuplicate;
        this.dateRangeInHours = dateRangeInHours;
        this.userUtilityService = userUtilityService;
        this.environment = environment;
    }

    //PEM: Move the Partner  from Staging tables to Partner & Protocol tables
    public void activateRemoteProfiles() {

        remotePartnerStagingService.findAllRemotePartnerProfiles().forEach(remotePartnerStagingEntity -> {
            Protocol protocol = Optional.ofNullable(Protocol.findProtocol(remotePartnerStagingEntity.getTpProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);
            switch (protocol) {
                case SFG_FTP:
                case SFG_FTPS:
                case SFG_SFTP:
                    RemoteFtpEntity remoteFtpEntity = remoteFtpService.get(remotePartnerStagingEntity.getPkId());
                    remoteFtpPartnerService.saveRemoteStagingProfile(remotePartnerStagingEntity, remoteFtpEntity);
                    break;
                case AS2:
                    //Need t implement
                    break;
                default:
                    // No Implementation Needed
            }
            remotePartnerStagingService.delete(remotePartnerStagingEntity);
        });

        remoteApplicationStagingService.findAllRemoteApplicationProfiles().forEach(remoteApplicationStagingEntity -> {
            Protocol protocol = Optional.ofNullable(Protocol.findProtocol(remoteApplicationStagingEntity.getAppIntegrationProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);
            switch (protocol) {
                case SFG_FTP:
                case SFG_FTPS:
                case SFG_SFTP:
                    RemoteFtpEntity remoteFtpEntity = remoteFtpService.get(remoteApplicationStagingEntity.getPkId());
                    remoteFtpApplicationService.saveRemoteStagingProfile(remoteApplicationStagingEntity, remoteFtpEntity);
                    break;
                default:
                    // No Implementation Needed
            }
            remoteApplicationStagingService.delete(remoteApplicationStagingEntity);
        });

    }

    public Boolean getIsSMLogin() {
        return isSMLogin;
    }

    public String paramName() {
        return paramName;
    }

    public Boolean getIsB2bActive() {
        return this.isB2bActive;
    }

    public List<CommunityManagerKeyValueModel> getTerminatorsMap() {
        return DataProvider.getTerminatorsMap();
    }

    public void isValidResource(CommunityManagerLoginModel cmProfile) {
        if (userUtilityService.getUserOrRole(Boolean.TRUE).equals(cmProfile.getUserName())) {
            try {
                communityManagerTokenService.authenticate(cmProfile, isSMLogin);
            } catch (CommunityManagerServiceException e) {
                if (e.getStatusCode() == 401) {
                    throw internalServerError("Invalid Password.");
                }
                throw internalServerError(e.getErrorMessage());
            }
        } else {
            throw internalServerError("Please use the parent user details to see password.");
        }
    }

    public Boolean getIsMftDuplicate() {
        return isMFTDuplicate;
    }

    public Integer getFileTransferTimeRange() {
        return dateRangeInHours;
    }

    public List<CommunityManagerKeyValueModel> getCipherSuites() {

        List<CommunityManagerKeyValueModel> communityManagerKeyValueModels = new ArrayList<>();
        communityManagerKeyValueModels.add(new CommunityManagerKeyValueModel("55", "ECDHE_RSA_WITH_AES_128_CBC_SHA"));
        communityManagerKeyValueModels.add(new CommunityManagerKeyValueModel("5", "RSA_WITH_AES_128_CBC_SHA"));
        communityManagerKeyValueModels.add(new CommunityManagerKeyValueModel("6", "RSA_WITH_AES_256_CBC_SHA"));
        communityManagerKeyValueModels.add(new CommunityManagerKeyValueModel("13", "DHE_DSS_WITH_DES_CBC_SHA"));
        communityManagerKeyValueModels.add(new CommunityManagerKeyValueModel("51", "ECDHE_ECDSA_WITH_3DES_EDE_CBC_SHA"));
        communityManagerKeyValueModels.add(new CommunityManagerKeyValueModel("49", "ECDHE_ECDSA_WITH_AES_256_CBC_SHA*"));
        communityManagerKeyValueModels.add(new CommunityManagerKeyValueModel("7", "RSA_WITH_3DES_EDE_CBC_SHA"));
        communityManagerKeyValueModels.add(new CommunityManagerKeyValueModel("52", "ECDHE_ECDSA_WITH_AES_128_CBC_SHA"));
        communityManagerKeyValueModels.add(new CommunityManagerKeyValueModel("14", "DHE_RSA_WITH_DES_CBC_SHA"));
        communityManagerKeyValueModels.add(new CommunityManagerKeyValueModel("9", "DHE_RSA_WITH_3DES_EDE_CBC_SHA"));
        communityManagerKeyValueModels.add(new CommunityManagerKeyValueModel("12", "RSA_WITH_DES_CBC_SHA"));
        communityManagerKeyValueModels.add(new CommunityManagerKeyValueModel("54", "ECDHE_RSA_WITH_3DES_EDE_CBC_SHA"));
        communityManagerKeyValueModels.add(new CommunityManagerKeyValueModel("50", "ECDHE_RSA_WITH_AES_256_CBC_SHA*"));
        return communityManagerKeyValueModels;
    }


    public LogoModel getLogoData() {
        try {
            File file = new File(".");
            String currPath = file.getAbsolutePath();
            String logoDir = currPath.substring(0, currPath.length() - 1) + "logo";
            Path logoDirPath = Paths.get(logoDir);
            String logoName = "";

            if (logoDirPath.toFile().exists()) {
                List<Path> logoPaths;
                try (Stream<Path> files = Files.list(logoDirPath)) {
                    logoPaths = files.filter(Files::isRegularFile)
                            .map(Path::getFileName)
                            .collect(Collectors.toList());
                }

                for (Path path : logoPaths) {
                    if (path.toString().startsWith("logo.") || path.toString().startsWith("Logo.")) {
                        logoName = path.toString();
                        break;
                    }
                }

                if (logoName.length() > 0) {
                    LOGGER.info("Logo Loading from folder, Logo Name : {}", logoName);
                    return new LogoModel().setLogoData(
                            "data:image/" +
                                    logoName +
                                    ";base64," +
                                    Base64.getEncoder().encodeToString(Files.readAllBytes(Paths.get(logoDir, logoName))));
                }
            }

            LOGGER.info("Logo Loading from Application, isCm : {}", isCm);
            return new LogoModel().setLogoData(
                    "data:image/" +
                            (isCm ? "IBMLogo.png" : "PragmaLogo.png") +
                            ";base64," +
                            Base64.getEncoder().encodeToString(Files.readAllBytes(new ClassPathResource(isCm ? "/logo/IBMLogo.png" : "/logo/PragmaLogo.png").getFile().toPath())));

        } catch (Exception ex) {
            throw GlobalExceptionHandler.internalServerError("Unable to load the custom logo");
        }
    }

    public List<CommunityManagerNameModel> getCloudList() {
        List<CommunityManagerNameModel> list = new ArrayList<>();
        list.add(new CommunityManagerNameModel("AWS-S3"));
        return list;
    }

    public List<CommunityManagerKeyValueModel> getAwsRegions() {
        return Arrays.stream(Regions.values()).map(region -> new CommunityManagerKeyValueModel(region.getName(), region.getDescription())).collect(Collectors.toList());
    }

    @PostConstruct
    public void loadValues() {
        String dplType = environment.getProperty("cm.cm-deployment");
        isCm = !isNotNull(dplType) || !dplType.equalsIgnoreCase("FALSE");
    }

}
