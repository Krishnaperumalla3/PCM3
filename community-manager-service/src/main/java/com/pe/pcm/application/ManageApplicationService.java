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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.pe.pcm.activity.ActivityHistoryService;
import com.pe.pcm.application.entity.AppActivityHistoryEntity;
import com.pe.pcm.application.entity.ApplicationEntity;
import com.pe.pcm.application.sfg.RemoteConnectDirectApplicationService;
import com.pe.pcm.application.sfg.RemoteFtpApplicationService;
import com.pe.pcm.application.sterling.SterlingAs2ApplicationService;
import com.pe.pcm.common.CommunityManagerKeyValueModel;
import com.pe.pcm.common.CommunityManagerNameModel;
import com.pe.pcm.enums.Protocol;
import com.pe.pcm.googledrive.GoogleDriveModel;
import com.pe.pcm.profile.ProfileModel;
import com.pe.pcm.protocol.*;
import com.pe.pcm.workflow.ApplicationInfoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;
import static com.pe.pcm.utils.CommonFunctions.convertStringToBoolean;
import static com.pe.pcm.utils.PCMConstants.APPLICATION;

/**
 * @author Chenchu Kiran Reddy.
 */
@Service
public class ManageApplicationService {

    private final ApplicationService applicationService;
    private final FtpApplicationService ftpApplicationService;
    private final MailboxApplicationService mailboxApplicationService;
    private final HttpApplicationService httpApplicationService;
    private final SapApplicationService sapApplicationService;
    private final RemoteFtpApplicationService remoteFtpApplicationService;
    private final RemoteConnectDirectApplicationService remoteConnectDirectApplicationService;
    private final FileSystemApplicationService fileSystemApplicationService;
    private final MqApplicationService mqApplicationService;
    private final WsApplicationService wsApplicationService;
    private final AwsS3ApplicationService awsS3ApplicationService;
    private final ActivityHistoryService activityHistoryService;
    private final ConnectDirectApplicationService connectDirectApplicationService;

    private final GoogleDriveApplicationService googleDriveApplicationService;
    private final SterlingAs2ApplicationService sterlingAs2ApplicationService;


    private final ObjectMapper mapper = new XmlMapper();

    @Autowired
    public ManageApplicationService(ApplicationService applicationService, FtpApplicationService ftpApplicationService,
                                    MailboxApplicationService mailboxApplicationService, HttpApplicationService httpApplicationService,
                                    SapApplicationService sapApplicationService, RemoteFtpApplicationService remoteFtpApplicationService,
                                    RemoteConnectDirectApplicationService remoteConnectDirectApplicationService, FileSystemApplicationService fileSystemApplicationService,
                                    MqApplicationService mqApplicationService, WsApplicationService wsApplicationService, AwsS3ApplicationService awsS3ApplicationService, ActivityHistoryService activityHistoryService, ConnectDirectApplicationService connectDirectApplicationService, GoogleDriveApplicationService googleDriveApplicationService, SterlingAs2ApplicationService sterlingAs2ApplicationService) {
        this.applicationService = applicationService;
        this.ftpApplicationService = ftpApplicationService;
        this.mailboxApplicationService = mailboxApplicationService;
        this.httpApplicationService = httpApplicationService;
        this.sapApplicationService = sapApplicationService;
        this.remoteFtpApplicationService = remoteFtpApplicationService;
        this.remoteConnectDirectApplicationService = remoteConnectDirectApplicationService;
        this.fileSystemApplicationService = fileSystemApplicationService;
        this.mqApplicationService = mqApplicationService;
        this.wsApplicationService = wsApplicationService;
        this.awsS3ApplicationService = awsS3ApplicationService;
        this.activityHistoryService = activityHistoryService;
        this.connectDirectApplicationService = connectDirectApplicationService;
        this.googleDriveApplicationService = googleDriveApplicationService;
        this.sterlingAs2ApplicationService = sterlingAs2ApplicationService;
    }

    public Page<ApplicationEntity> search(ProfileModel profileModel, Pageable pageable) {
        return applicationService.search(profileModel, pageable);
    }

    public Page<AppActivityHistoryEntity> getHistory(String pkId, Pageable pageable) {
        return activityHistoryService.getApplicationHistory(pkId, pageable);
    }

    public List<CommunityManagerNameModel> getAllTemplateApplicationList() {
        return applicationService.getAllTemplateApplicationProfiles()
                .stream()
                .map(applicationEntity -> new CommunityManagerNameModel(applicationEntity.getApplicationName()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void statusChange(String applicationNameOrPkId, Boolean status, Boolean isPem) {
        if (isPem) {
            applicationNameOrPkId = applicationService.getUniqueApplication(applicationNameOrPkId).getPkId();
        }
        Protocol protocol = Protocol.findProtocol(applicationService.get(applicationNameOrPkId).getAppIntegrationProtocol());
        switch (protocol) {
            case AS2:
                sterlingAs2ApplicationService.changeStatus(applicationNameOrPkId, status);
                break;
            case FTP:
            case FTPS:
            case SFTP:
                FtpModel ftpModel = ftpApplicationService.get(applicationNameOrPkId);
                ftpModel.setStatus(status);
                ftpApplicationService.update(ftpModel);
                break;
            case HTTP:
            case HTTPS:
                HttpModel httpModel = httpApplicationService.get(applicationNameOrPkId);
                httpModel.setStatus(status);
                httpApplicationService.update(httpModel);
                break;
            case MQ:
                MqModel mqModel = mqApplicationService.get(applicationNameOrPkId);
                mqModel.setStatus(status);
                mqApplicationService.update(mqModel);
                break;
            case MAILBOX:
                MailboxModel mailboxModel = mailboxApplicationService.get(applicationNameOrPkId);
                mailboxModel.setStatus(status);
                mailboxApplicationService.update(mailboxModel);
                break;
            case SAP:
                SapModel sapModel = sapApplicationService.get(applicationNameOrPkId);
                sapModel.setStatus(status);
                sapApplicationService.update(sapModel);
                break;
            case WEB_SERVICE:
                WebserviceModel webserviceModel = wsApplicationService.get(applicationNameOrPkId);
                webserviceModel.setStatus(status);
                wsApplicationService.update(webserviceModel);
                break;
            case FILE_SYSTEM:
                FileSystemModel fileSystemModel = fileSystemApplicationService.get(applicationNameOrPkId);
                fileSystemModel.setStatus(status);
                fileSystemApplicationService.update(fileSystemModel);
                break;
            case SFG_FTP:
            case SFG_FTPS:
            case SFG_SFTP:
//                RemoteProfileModel remoteProfileModel = remoteFtpApplicationService.get(applicationNameOrPkId)
//                remoteProfileModel.setStatus(status)
//                remoteFtpApplicationService.update(remoteProfileModel)
                remoteFtpApplicationService.changeStatus(applicationNameOrPkId, status);
                break;
            case SFG_CONNECT_DIRECT:
                RemoteCdModel remoteCdModel = remoteConnectDirectApplicationService.get(applicationNameOrPkId);
                remoteCdModel.setStatus(status);
                remoteConnectDirectApplicationService.update(remoteCdModel);
                break;
            case CONNECT_DIRECT:
                ConnectDirectModel connectDirectModel = connectDirectApplicationService.get(applicationNameOrPkId);
                connectDirectModel.setStatus(status);
                connectDirectApplicationService.update(connectDirectModel);
                break;
            case AWS_S3:
                AwsS3Model awsS3Model = awsS3ApplicationService.get(applicationNameOrPkId);
                awsS3Model.setStatus(status);
                awsS3ApplicationService.update(awsS3Model);
                break;
            case GOOGLE_DRIVE:
                GoogleDriveModel googleDriveModel = googleDriveApplicationService.get(applicationNameOrPkId);
                googleDriveModel.setStatus(status);
                googleDriveApplicationService.update(null, googleDriveModel);
                break;
            default:
                // No Implementation Needed
                break;
        }
    }

    public String saveApplication(ApplicationInfoModel applicationInfoModel) {
        Protocol protocol = Protocol.findProtocol(applicationInfoModel.getProtocol());
        String applicationId = null;
        switch (protocol) {
            case FTP:
            case FTPS:
            case SFTP:
                FtpModel ftpModel = mapper.convertValue(applicationInfoModel.getApplication(), FtpModel.class);
                applicationId = ftpApplicationService.save(ftpModel);
                break;
            case HTTP:
            case HTTPS:
                HttpModel httpModel = mapper.convertValue(applicationInfoModel.getApplication(), HttpModel.class);
                applicationId = httpApplicationService.save(httpModel);
                break;
            case MQ:
                MqModel mqModel = mapper.convertValue(applicationInfoModel.getApplication(), MqModel.class);
                applicationId = mqApplicationService.save(mqModel);
                break;
            case MAILBOX:
                MailboxModel mailboxModel = mapper.convertValue(applicationInfoModel.getApplication(), MailboxModel.class);
                applicationId = mailboxApplicationService.save(mailboxModel);
                break;
            case SAP:
                SapModel sapModel = mapper.convertValue(applicationInfoModel.getApplication(), SapModel.class);
                applicationId = sapApplicationService.save(sapModel);
                break;
            case WEB_SERVICE:
                WebserviceModel webserviceModel = mapper.convertValue(applicationInfoModel.getApplication(), WebserviceModel.class);
                applicationId = wsApplicationService.save(webserviceModel);
                break;
            case FILE_SYSTEM:
                FileSystemModel fileSystemModel = mapper.convertValue(applicationInfoModel.getApplication(), FileSystemModel.class);
                applicationId = fileSystemApplicationService.save(fileSystemModel);
                break;
            case SFG_FTP:
            case SFG_FTPS:
            case SFG_SFTP:
                RemoteProfileModel remoteProfileModel = mapper.convertValue(applicationInfoModel.getApplication(), RemoteProfileModel.class);
                applicationId = remoteFtpApplicationService.save(remoteProfileModel);
                break;
            case SFG_CONNECT_DIRECT:
                RemoteCdModel remoteCdModel = mapper.convertValue(applicationInfoModel.getApplication(), RemoteCdModel.class);
                applicationId = remoteConnectDirectApplicationService.save(remoteCdModel);
                break;
            case CONNECT_DIRECT:
                ConnectDirectModel connectDirectModel = mapper.convertValue(applicationInfoModel.getApplication(), ConnectDirectModel.class);
                applicationId = connectDirectApplicationService.save(connectDirectModel);
                break;

            default:
                // No Implementation Needed
        }
        return applicationId;
    }

    public String getApplication(ApplicationInfoModel applicationInfoModel) {
        Protocol protocol = Protocol.findProtocol(applicationInfoModel.getProtocol());
        String applicationId = null;
        switch (protocol) {
            case FTP:
            case FTPS:
            case SFTP:
                FtpModel ftpModel = mapper.convertValue(applicationInfoModel.getApplication(), FtpModel.class);
                applicationId = applicationService.find(ftpModel.getProfileId()).map(ApplicationEntity::getPkId).orElseThrow(() -> notFound(APPLICATION));
                break;
            case HTTP:
            case HTTPS:
                HttpModel httpModel = mapper.convertValue(applicationInfoModel.getApplication(), HttpModel.class);
                applicationId = applicationService.find(httpModel.getProfileId()).map(ApplicationEntity::getPkId).orElseThrow(() -> notFound(APPLICATION));
                break;
            case MQ:
                MqModel mqModel = mapper.convertValue(applicationInfoModel.getApplication(), MqModel.class);
                applicationId = applicationService.find(mqModel.getProfileId()).map(ApplicationEntity::getPkId).orElseThrow(() -> notFound(APPLICATION));
                break;
            case MAILBOX:
                MailboxModel mailboxModel = mapper.convertValue(applicationInfoModel.getApplication(), MailboxModel.class);
                applicationId = applicationService.find(mailboxModel.getProfileId()).map(ApplicationEntity::getPkId).orElseThrow(() -> notFound(APPLICATION));
                break;
            case SAP:
                SapModel sapModel = mapper.convertValue(applicationInfoModel.getApplication(), SapModel.class);
                applicationId = applicationService.find(sapModel.getProfileId()).map(ApplicationEntity::getPkId).orElseThrow(() -> notFound(APPLICATION));
                break;
            case WEB_SERVICE:
                WebserviceModel webserviceModel = mapper.convertValue(applicationInfoModel.getApplication(), WebserviceModel.class);
                applicationId = applicationService.find(webserviceModel.getProfileId()).map(ApplicationEntity::getPkId).orElseThrow(() -> notFound(APPLICATION));
                break;
            case FILE_SYSTEM:
                FileSystemModel fileSystemModel = mapper.convertValue(applicationInfoModel.getApplication(), FileSystemModel.class);
                applicationId = applicationService.find(fileSystemModel.getProfileId()).map(ApplicationEntity::getPkId).orElseThrow(() -> notFound(APPLICATION));
                break;
            case SFG_FTP:
            case SFG_FTPS:
            case SFG_SFTP:
                RemoteProfileModel remoteProfileModel = mapper.convertValue(applicationInfoModel.getApplication(), RemoteProfileModel.class);
                applicationId = applicationService.find(remoteProfileModel.getProfileId()).map(ApplicationEntity::getPkId).orElseThrow(() -> notFound(APPLICATION));
                break;
            case SFG_CONNECT_DIRECT:
                RemoteCdModel remoteCdModel = mapper.convertValue(applicationInfoModel.getApplication(), RemoteCdModel.class);
                applicationId = applicationService.find(remoteCdModel.getProfileId()).map(ApplicationEntity::getPkId).orElseThrow(() -> notFound(APPLICATION));
                break;
            case CONNECT_DIRECT:
                ConnectDirectModel connectDirectModel = mapper.convertValue(applicationInfoModel.getApplication(), ConnectDirectModel.class);
                applicationId = applicationService.find(connectDirectModel.getProfileId()).map(ApplicationEntity::getPkId).orElseThrow(() -> notFound(APPLICATION));
                break;

            default:
                // No Implementation Needed
        }
        return applicationId;
    }

    /*  Dont delete or comment this method. */
    public ApplicationInfoModel getApplication(String pkId) {

        ApplicationEntity applicationEntity = applicationService.get(pkId);
        Protocol protocol = Protocol.findProtocol(applicationEntity.getAppIntegrationProtocol());
        ApplicationInfoModel applicationInfoModel;
        switch (protocol) {
            case FTP:
            case FTPS:
            case SFTP:
                ProfileModel ftpModel = ftpApplicationService.get(pkId);
                ftpModel.setPkId(null);
                //applicationInfoModel.setApplication(ftpModel)
                applicationInfoModel = new ApplicationInfoModel<>(ftpModel);
                applicationInfoModel.setProtocol(protocol.getProtocol());
                break;
            case AS2:
                //ToDO - YET To Implement
                applicationInfoModel = new ApplicationInfoModel<>();
                break;
            case HTTP:
            case HTTPS:
                ProfileModel httpModel = httpApplicationService.get(pkId);
                httpModel.setPkId(null);
                //applicationInfoModel.setApplication(httpModel)
                applicationInfoModel = new ApplicationInfoModel<>(httpModel);
                applicationInfoModel.setProtocol(protocol.getProtocol());
                break;
            case MQ:
                ProfileModel mqModel = mqApplicationService.get(pkId);
                mqModel.setPkId(null);
                //applicationInfoModel.setApplication(mqModel)
                applicationInfoModel = new ApplicationInfoModel<>(mqModel);
                applicationInfoModel.setProtocol(protocol.getProtocol());
                break;
            case MAILBOX:
                ProfileModel mailboxModel = mailboxApplicationService.get(pkId);
                mailboxModel.setPkId(null);
                //applicationInfoModel.setApplication(mailboxModel)
                applicationInfoModel = new ApplicationInfoModel<>(mailboxModel);
                applicationInfoModel.setProtocol(protocol.getProtocol());
                break;
            case SAP:
                ProfileModel sapModel = sapApplicationService.get(pkId);
                sapModel.setPkId(null);
                //applicationInfoModel.setApplication(sapModel)
                applicationInfoModel = new ApplicationInfoModel<>(sapModel);
                applicationInfoModel.setProtocol(protocol.getProtocol());
                break;
            case WEB_SERVICE:
                ProfileModel webserviceModel = wsApplicationService.get(pkId);
                webserviceModel.setPkId(null);
                //applicationInfoModel.setApplication(webserviceModel)
                applicationInfoModel = new ApplicationInfoModel<>(webserviceModel);
                applicationInfoModel.setProtocol(protocol.getProtocol());
                break;
            case FILE_SYSTEM:
                ProfileModel fileSystemModel = fileSystemApplicationService.get(pkId);
                fileSystemModel.setPkId(null);
                //applicationInfoModel.setApplication(fileSystemModel)
                applicationInfoModel = new ApplicationInfoModel<>(fileSystemModel);
                applicationInfoModel.setProtocol(protocol.getProtocol());
                break;
            case SFG_FTP:
            case SFG_FTPS:
            case SFG_SFTP:
                ProfileModel remoteFtpModel = remoteFtpApplicationService.get(pkId);
                remoteFtpModel.setPkId(null);
                //applicationInfoModel.setApplication(remoteFtpModel)
                applicationInfoModel = new ApplicationInfoModel<>(remoteFtpModel);
                applicationInfoModel.setProtocol(protocol.getProtocol());
                break;
            case SFG_CONNECT_DIRECT:
                ProfileModel remoteCdModel = remoteConnectDirectApplicationService.get(pkId);
                remoteCdModel.setPkId(null);
                //applicationInfoModel.setApplication(cdModel)
                applicationInfoModel = new ApplicationInfoModel<>(remoteCdModel);
                applicationInfoModel.setProtocol(protocol.getProtocol());
                break;
            case CONNECT_DIRECT:
                ProfileModel cdModel = connectDirectApplicationService.get(pkId);
                cdModel.setPkId(null);
                //applicationInfoModel.setApplication(cdModel)
                applicationInfoModel = new ApplicationInfoModel<>(cdModel);
                applicationInfoModel.setProtocol(protocol.getProtocol());
                break;
            default:
                applicationInfoModel = new ApplicationInfoModel<>();
        }
        return applicationInfoModel;
    }

    public void delete(String applicationNameOrPkId, Boolean isPem, Boolean deleteInSI) {
        if (isPem) {
            applicationNameOrPkId = applicationService.getUniqueApplication(applicationNameOrPkId).getPkId();
        }
        Protocol protocol = Protocol.findProtocol(applicationService.get(applicationNameOrPkId).getAppIntegrationProtocol());
        switch (protocol) {
            case FTP:
            case FTPS:
            case SFTP:
                ftpApplicationService.delete(applicationNameOrPkId);
                break;
            case HTTP:
            case HTTPS:
                httpApplicationService.delete(applicationNameOrPkId);
                break;
            case MQ:
                mqApplicationService.delete(applicationNameOrPkId);
                break;
            case MAILBOX:
                mailboxApplicationService.delete(applicationNameOrPkId);
                break;
            case SAP:
                sapApplicationService.delete(applicationNameOrPkId);
                break;
            case WEB_SERVICE:
                wsApplicationService.delete(applicationNameOrPkId);
                break;
            case FILE_SYSTEM:
                fileSystemApplicationService.delete(applicationNameOrPkId);
                break;
            case SFG_FTP:
            case SFG_FTPS:
            case SFG_SFTP:
                remoteFtpApplicationService.delete(applicationNameOrPkId, deleteInSI);
                break;
            case SFG_CONNECT_DIRECT:
                remoteConnectDirectApplicationService.delete(applicationNameOrPkId, deleteInSI);
                break;
            case CONNECT_DIRECT:
                connectDirectApplicationService.delete(applicationNameOrPkId);
                break;

            default:
                // No Implementation Needed
        }

    }

    public List<CommunityManagerKeyValueModel> getApplicationMap() {
        return applicationService.findAllApplicationProfiles()
                .stream()
                .map(applicationEntity -> new CommunityManagerKeyValueModel(applicationEntity.getPkId(), applicationEntity.getApplicationName()))
                .collect(Collectors.toList());
    }

    public List<CommunityManagerNameModel> getApplicationList() {
        return applicationService.findAllApplicationProfiles()
                .parallelStream()
                .map(applicationEntity -> new CommunityManagerNameModel(applicationEntity.getApplicationName()))
                .collect(Collectors.toList());
    }

    public List<CommunityManagerKeyValueModel> getProfilesByProtocol(String protocol) {
        return applicationService.finaAllByProtocol(protocol)
                .stream()
                .map(partnerEntity -> new CommunityManagerKeyValueModel(partnerEntity.getPkId(), partnerEntity.getApplicationName()))
                .collect(Collectors.toList());
    }

    //Pem Service: this method is using to get All profiles contact information
    public List<ProfileModel> getProfiles(ProfileModel profileModel, Boolean isOr, String fileAppServer) {
        return applicationService.search(profileModel, isOr, fileAppServer)
                .stream()
                .map(applicationEntity ->
                        new ProfileModel()
                                .setHubInfo(convertStringToBoolean(applicationEntity.getAppPickupFiles()))
                                .setProfileName(applicationEntity.getApplicationName())
                                .setProfileId(applicationEntity.getApplicationId())
                                .setEmailId(applicationEntity.getEmailId())
                                .setPhone(applicationEntity.getPhone())
                                .setProtocol(applicationEntity.getAppIntegrationProtocol())
                                .setStatus(convertStringToBoolean(applicationEntity.getAppIsActive()))
                                .setPemIdentifier(applicationEntity.getPemIdentifier()))
                .collect(Collectors.toList());
    }

}
