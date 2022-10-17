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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.pe.pcm.activity.ActivityHistoryService;
import com.pe.pcm.common.CommunityManagerKeyValueModel;
import com.pe.pcm.common.CommunityManagerNameModel;
import com.pe.pcm.enums.Protocol;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.googledrive.GoogleDriveModel;
import com.pe.pcm.miscellaneous.UserUtilityService;
import com.pe.pcm.partner.entity.PartnerActivityHistoryEntity;
import com.pe.pcm.partner.entity.PartnerEntity;
import com.pe.pcm.partner.sterling.SterlingAs2PartnerService;
import com.pe.pcm.pem.codelist.PemCodeListService;
import com.pe.pcm.pem.codelist.entity.PemCodeListEntity;
import com.pe.pcm.profile.ProfileModel;
import com.pe.pcm.protocol.*;
import com.pe.pcm.utils.CommonFunctions;
import com.pe.pcm.workflow.PartnerInfoModel;
import com.pe.pcm.workflow.pem.PemFileTypeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.pe.pcm.constants.AuthoritiesConstants.ADMIN;
import static com.pe.pcm.constants.AuthoritiesConstants.SUPER_ADMIN;
import static com.pe.pcm.enums.Protocol.findProtocol;
import static com.pe.pcm.utils.CommonFunctions.convertStringToBoolean;
import static com.pe.pcm.utils.CommonFunctions.distinctByKey;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * @author Kiran Reddy.
 */
@Service
public class ManagePartnerService {

    private final PartnerService partnerService;

    private final ActivityHistoryService activityHistoryService;

    private final HttpPartnerService httpPartnerService;

    private final FtpPartnerService ftpPartnerService;

    private final RemoteConnectDirectPartnerService remoteConnectDirectPartnerService;

    private final EcPartnerService ecPartnerService;

    private final FileSystemPartnerService fileSystemPartnerService;

    private final MailboxPartnerService mailboxPartnerService;

    private final MqPartnerService mqPartnerService;

    private final RemoteFtpPartnerService remoteFtpPartnerService;

    private final SapPartnerService sapPartnerService;

    private final WsPartnerService wsPartnerService;

    private final AwsS3PartnerService awsS3PartnerService;

    private final ConnectDirectPartnerService connectDirectPartnerService;

    private final PemCodeListService pemCodeListService;

    private final UserUtilityService userUtilityService;

    private final SterlingAs2PartnerService sterlingAs2PartnerService;

    private final GoogleDrivePartnerService googleDrivePartnerService;

    private final ObjectMapper mapper = new XmlMapper();

    @Autowired
    public ManagePartnerService(PartnerService partnerService,
                                ActivityHistoryService activityHistoryService,
                                HttpPartnerService httpPartnerService,
                                FtpPartnerService ftpPartnerService,
                                RemoteConnectDirectPartnerService remoteConnectDirectPartnerService,
                                EcPartnerService ecPartnerService,
                                FileSystemPartnerService fileSystemPartnerService,
                                MailboxPartnerService mailboxPartnerService,
                                MqPartnerService mqPartnerService,
                                RemoteFtpPartnerService remoteFtpPartnerService,
                                SapPartnerService sapPartnerService,
                                WsPartnerService wsPartnerService,
                                AwsS3PartnerService awsS3PartnerService,
                                ConnectDirectPartnerService connectDirectPartnerService,
                                PemCodeListService pemCodeListService,
                                UserUtilityService userUtilityService,
                                SterlingAs2PartnerService sterlingAs2PartnerService, GoogleDrivePartnerService googleDrivePartnerService) {
        this.partnerService = partnerService;
        this.activityHistoryService = activityHistoryService;
        this.httpPartnerService = httpPartnerService;
        this.ftpPartnerService = ftpPartnerService;
        this.remoteConnectDirectPartnerService = remoteConnectDirectPartnerService;
        this.ecPartnerService = ecPartnerService;
        this.fileSystemPartnerService = fileSystemPartnerService;
        this.mailboxPartnerService = mailboxPartnerService;
        this.mqPartnerService = mqPartnerService;
        this.remoteFtpPartnerService = remoteFtpPartnerService;
        this.sapPartnerService = sapPartnerService;
        this.wsPartnerService = wsPartnerService;
        this.awsS3PartnerService = awsS3PartnerService;
        this.connectDirectPartnerService = connectDirectPartnerService;
        this.pemCodeListService = pemCodeListService;
        this.userUtilityService = userUtilityService;
        this.sterlingAs2PartnerService = sterlingAs2PartnerService;
        this.googleDrivePartnerService = googleDrivePartnerService;
    }

    public PartnerInfoModel<?> getPartner(String pkIdOrName) {
        String pkId;
        PartnerEntity partnerEntity;
        try {
            partnerEntity = partnerService.findPartnerById(pkIdOrName);
            pkId = pkIdOrName;
        } catch (CommunityManagerServiceException ntf) {
            partnerEntity = partnerService.getUniquePartner(pkIdOrName);
            pkId = partnerEntity.getPkId();
        }

        Protocol protocol = findProtocol(partnerEntity.getTpProtocol());
        PartnerInfoModel<?> partnerInfoModel;
        switch (protocol) {
            case FTP:
            case FTPS:
            case SFTP:
                ProfileModel ftpModel = ftpPartnerService.get(pkId);
                ftpModel.setPkId(null);
                partnerInfoModel = new PartnerInfoModel<>(ftpModel);
                partnerInfoModel.setProtocol(protocol.getProtocol());
                break;
            case AS2:
                ProfileModel as2Model = sterlingAs2PartnerService.get(pkId);
                as2Model.setPkId(null);
                partnerInfoModel = new PartnerInfoModel<>(as2Model);
                partnerInfoModel.setProtocol(protocol.getProtocol());
                break;
            case HTTP:
            case HTTPS:
                ProfileModel httpModel = httpPartnerService.get(pkId);
                httpModel.setPkId(null);
                partnerInfoModel = new PartnerInfoModel<>(httpModel);
                partnerInfoModel.setProtocol(protocol.getProtocol());
                break;
            case MQ:
                ProfileModel mqModel = mqPartnerService.get(pkId);
                mqModel.setPkId(null);
                partnerInfoModel = new PartnerInfoModel<>(mqModel);
                partnerInfoModel.setProtocol(protocol.getProtocol());
                break;
            case MAILBOX:
                ProfileModel mailboxModel = mailboxPartnerService.get(pkId);
                mailboxModel.setPkId(null);
                partnerInfoModel = new PartnerInfoModel<>(mailboxModel);
                partnerInfoModel.setProtocol(protocol.getProtocol());
                break;
            case SAP:
                ProfileModel sapModel = sapPartnerService.get(pkId);
                sapModel.setPkId(null);
                partnerInfoModel = new PartnerInfoModel<>(sapModel);
                partnerInfoModel.setProtocol(protocol.getProtocol());
                break;
            case WEB_SERVICE:
                ProfileModel webserviceModel = wsPartnerService.get(pkId);
                webserviceModel.setPkId(null);
                partnerInfoModel = new PartnerInfoModel<>(webserviceModel);
                partnerInfoModel.setProtocol(protocol.getProtocol());
                break;
            case FILE_SYSTEM:
                ProfileModel fileSystemModel = fileSystemPartnerService.get(pkId);
                fileSystemModel.setPkId(null);
                partnerInfoModel = new PartnerInfoModel<>(fileSystemModel);
                partnerInfoModel.setProtocol(protocol.getProtocol());
                break;
            case SFG_FTP:
            case SFG_FTPS:
            case SFG_SFTP:
                ProfileModel remoteFtpModel = remoteFtpPartnerService.get(pkId);
                remoteFtpModel.setPkId(null);
                partnerInfoModel = new PartnerInfoModel<>(remoteFtpModel);
                partnerInfoModel.setProtocol(protocol.getProtocol());
                break;
            case EXISTING_CONNECTION:
                ProfileModel ecModel = ecPartnerService.get(pkId);
                ecModel.setPkId(null);
                partnerInfoModel = new PartnerInfoModel<>(ecModel);
                partnerInfoModel.setProtocol(protocol.getProtocol());
                break;
            case SFG_CONNECT_DIRECT:
                ProfileModel cdModel = remoteConnectDirectPartnerService.get(pkId);
                cdModel.setPkId(null);
                partnerInfoModel = new PartnerInfoModel<>(cdModel);
                partnerInfoModel.setProtocol(protocol.getProtocol());
                break;
            case CONNECT_DIRECT:
                ProfileModel connectDirectModel = connectDirectPartnerService.get(pkId);
                connectDirectModel.setPkId(null);
                partnerInfoModel = new PartnerInfoModel<>(connectDirectModel);
                partnerInfoModel.setProtocol(protocol.getProtocol());
                break;
            default:
                partnerInfoModel = new PartnerInfoModel<ProfileModel>();
        }
        return partnerInfoModel;
    }

    public Page<PartnerEntity> search(ProfileModel profileModel, Pageable pageable) {
        return partnerService.search(profileModel, pageable);
    }

    public Page<PartnerActivityHistoryEntity> getHistory(String pkId, Pageable pageable) {
        return activityHistoryService.getTradingPartnerHistory(pkId, pageable);
    }

    public List<CommunityManagerNameModel> getAllTemplateProfilesList() {
        return partnerService.getAllTemplateProfiles()
                .parallelStream()
                .map(partnerEntity -> new CommunityManagerNameModel(partnerEntity.getTpName()))
                .collect(Collectors.toList());
    }

    public List<PartnerEntity> getPartnerByPartnerNameAndPartnerId(PemFileTypeModel pemFileTypeModel) {
        return partnerService.findByPartnerNameAndPartnerId(pemFileTypeModel.getPartner(), pemFileTypeModel.getApplication());
    }

    public List<CommunityManagerNameModel> getPartnersList() {
        String role = userUtilityService.getUserOrRole(FALSE);
        if (!role.equals(SUPER_ADMIN) && !role.equals(ADMIN)) {
            return partnerService.getPartnersListAssignedToUserMap(userUtilityService.getUserOrRole(TRUE))
                    .stream()
                    .map(communityManagerKeyValueModel -> new CommunityManagerNameModel(communityManagerKeyValueModel.getValue()))
                    .collect(Collectors.toList());
        }
        return partnerService.findAllPartnerProfiles()
                .stream()
                .map(tradingPartnerEntity -> new CommunityManagerNameModel(tradingPartnerEntity.getTpName()))
                .collect(Collectors.toList())
                .stream()
                .filter(distinctByKey(CommunityManagerNameModel::getName))
                .collect(Collectors.toList());
    }

    public List<CommunityManagerNameModel> getAs2Profiles(String isHubInfo) {
        return partnerService.findAllAs2Profiles(isHubInfo)
                .stream()
                .map(tradingPartnerEntity -> new CommunityManagerNameModel(tradingPartnerEntity.getTpName()))
                .collect(Collectors.toList());
    }

    public List<PemCodeListEntity> finaAllPartnersAndCodeList() {
        return pemCodeListService.findAllByProfilesNotIn(partnerService.findAllPartnerProfiles()
                .stream()
                .map(PartnerEntity::getTpName)
                .collect(Collectors.toList())
        );
    }

    public List<CommunityManagerKeyValueModel> getPartnersMap() {
        String role = userUtilityService.getUserOrRole(FALSE);
        if (!role.equals(SUPER_ADMIN) && !role.equals(ADMIN)) {
            return partnerService.getPartnersListAssignedToUserMap(userUtilityService.getUserOrRole(TRUE));
        }
        return partnerService.findAllPartnerProfiles()
                .stream()
                .map(tradingPartnerEntity -> new CommunityManagerKeyValueModel(tradingPartnerEntity.getPkId(), tradingPartnerEntity.getTpName()))
                .collect(Collectors.toList())
                .stream()
                .filter(distinctByKey(CommunityManagerKeyValueModel::getValue))
                .collect(Collectors.toList());
    }

    public void statusChange(String partnerNameOrPkId, boolean status, boolean isPem) {

        if (isPem) {
            partnerNameOrPkId = partnerService.getUniquePartner(partnerNameOrPkId).getPkId();
        }

        Protocol protocol = findProtocol(partnerService.findPartnerById(partnerNameOrPkId).getTpProtocol());
        switch (protocol) {
            case FTP:
            case FTPS:
            case SFTP:
                FtpModel ftpModel = ftpPartnerService.get(partnerNameOrPkId);
                ftpModel.setStatus(status);
                ftpPartnerService.update(ftpModel);
                break;
            case AS2:
                sterlingAs2PartnerService.changeStatus(partnerNameOrPkId, status);
                break;
            case HTTP:
            case HTTPS:
                HttpModel httpModel = httpPartnerService.get(partnerNameOrPkId);
                httpModel.setStatus(status);
                httpPartnerService.update(httpModel);
                break;
            case MQ:
                MqModel mqModel = mqPartnerService.get(partnerNameOrPkId);
                mqModel.setStatus(status);
                mqPartnerService.update(mqModel);
                break;
            case MAILBOX:
                MailboxModel mailboxModel = mailboxPartnerService.get(partnerNameOrPkId);
                mailboxModel.setStatus(status);
                mailboxPartnerService.update(mailboxModel);
                break;
            case SAP:
                SapModel sapModel = sapPartnerService.get(partnerNameOrPkId);
                sapModel.setStatus(status);
                sapPartnerService.update(sapModel);
                break;
            case WEB_SERVICE:
                WebserviceModel webserviceModel = wsPartnerService.get(partnerNameOrPkId);
                webserviceModel.setStatus(status);
                wsPartnerService.update(webserviceModel);
                break;
            case FILE_SYSTEM:
                FileSystemModel fileSystemModel = fileSystemPartnerService.get(partnerNameOrPkId);
                fileSystemModel.setStatus(status);
                fileSystemPartnerService.update(fileSystemModel);
                break;
            case SFG_FTP:
            case SFG_FTPS:
            case SFG_SFTP:
//                RemoteProfileModel remoteProfileModel = remoteFtpPartnerService.get(partnerNameOrPkId)
//                remoteProfileModel.setStatus(status)
//                remoteFtpPartnerService.update(remoteProfileModel)
                remoteFtpPartnerService.changeStatus(partnerNameOrPkId, status);
                break;
            case EXISTING_CONNECTION:
                EcModel ecModel = ecPartnerService.get(partnerNameOrPkId);
                ecModel.setStatus(status);
                ecPartnerService.update(ecModel);
                break;
            case SFG_CONNECT_DIRECT:
                RemoteCdModel remoteCdModel = remoteConnectDirectPartnerService.get(partnerNameOrPkId);
                remoteCdModel.setStatus(status);
                remoteConnectDirectPartnerService.update(remoteCdModel);
                break;
            case CONNECT_DIRECT:
                ConnectDirectModel connectDirectModel = connectDirectPartnerService.get(partnerNameOrPkId);
                connectDirectModel.setStatus(status);
                connectDirectPartnerService.update(connectDirectModel);
                break;
            case AWS_S3:
                AwsS3Model awsS3Model = awsS3PartnerService.get(partnerNameOrPkId, false);
                awsS3Model.setStatus(status);
                awsS3PartnerService.update(awsS3Model);
                break;
            case GOOGLE_DRIVE:
                GoogleDriveModel googleDriveModel = googleDrivePartnerService.get(partnerNameOrPkId);
                googleDriveModel.setStatus(status);
                googleDrivePartnerService.update(null,googleDriveModel);
                break;
            default:
                // No Implementation Needed
        }
    }

    public String savePartner(PartnerInfoModel partnerInfoModel) {
        Protocol protocol = findProtocol(partnerInfoModel.getProtocol());
        String profileId = null;
        switch (protocol) {
            case FTP:
            case FTPS:
            case SFTP:
                FtpModel ftpModel = mapper.convertValue(partnerInfoModel.getPartner(), FtpModel.class);
                profileId = ftpPartnerService.save(ftpModel);
                break;
            case AS2:
                As2Model as2Model = mapper.convertValue(partnerInfoModel.getPartner(), As2Model.class);
                profileId = sterlingAs2PartnerService.create(as2Model, false);
                break;
            case HTTP:
            case HTTPS:
                HttpModel httpModel = mapper.convertValue(partnerInfoModel.getPartner(), HttpModel.class);
                profileId = httpPartnerService.save(httpModel);
                break;
            case MQ:
                MqModel mqModel = mapper.convertValue(partnerInfoModel.getPartner(), MqModel.class);
                profileId = mqPartnerService.save(mqModel);
                break;
            case MAILBOX:
                MailboxModel mailboxModel = mapper.convertValue(partnerInfoModel.getPartner(), MailboxModel.class);
                profileId = mailboxPartnerService.save(mailboxModel);
                break;
            case SAP:
                SapModel sapModel = mapper.convertValue(partnerInfoModel.getPartner(), SapModel.class);
                profileId = sapPartnerService.save(sapModel);
                break;
            case WEB_SERVICE:
                WebserviceModel webserviceModel = mapper.convertValue(partnerInfoModel.getPartner(), WebserviceModel.class);
                profileId = wsPartnerService.save(webserviceModel);
                break;
            case FILE_SYSTEM:
                FileSystemModel fileSystemModel = mapper.convertValue(partnerInfoModel.getPartner(), FileSystemModel.class);
                profileId = fileSystemPartnerService.save(fileSystemModel);
                break;
            case SFG_FTP:
            case SFG_FTPS:
            case SFG_SFTP:
                RemoteProfileModel remoteProfileModel = mapper.convertValue(partnerInfoModel.getPartner(), RemoteProfileModel.class);
                profileId = remoteFtpPartnerService.save(remoteProfileModel);
                break;
            case EXISTING_CONNECTION:
                EcModel ecModel = mapper.convertValue(partnerInfoModel.getPartner(), EcModel.class);
                profileId = ecPartnerService.save(ecModel);
                break;
            case SFG_CONNECT_DIRECT:
                RemoteCdModel remoteCdModel = mapper.convertValue(partnerInfoModel.getPartner(), RemoteCdModel.class);
                profileId = remoteConnectDirectPartnerService.save(remoteCdModel);
                break;
            case CONNECT_DIRECT:
                ConnectDirectModel connectDirectModel = mapper.convertValue(partnerInfoModel.getPartner(), ConnectDirectModel.class);
                profileId = connectDirectPartnerService.save(connectDirectModel);
                break;
            default:
                // No Implementation Needed
        }
        return profileId;
    }

    public void delete(String partnerNameOrPkId, boolean isPem, boolean deleteInSI, boolean deleteArtifacts) {

        if (isPem) {
            partnerNameOrPkId = partnerService.getUniquePartner(partnerNameOrPkId).getPkId();
        }

        Protocol protocol = findProtocol(partnerService.findPartnerById(partnerNameOrPkId).getTpProtocol());
        switch (protocol) {
            case FTP:
            case FTPS:
            case SFTP:
                ftpPartnerService.delete(partnerNameOrPkId);
                break;
            case AS2:
                sterlingAs2PartnerService.delete(partnerNameOrPkId);
                break;
            case HTTP:
            case HTTPS:
                httpPartnerService.delete(partnerNameOrPkId);
                break;
            case MQ:
                mqPartnerService.delete(partnerNameOrPkId);
                break;
            case MAILBOX:
                mailboxPartnerService.delete(partnerNameOrPkId);
                break;
            case SAP:
                sapPartnerService.delete(partnerNameOrPkId);
                break;
            case WEB_SERVICE:
                wsPartnerService.delete(partnerNameOrPkId);
                break;
            case FILE_SYSTEM:
                fileSystemPartnerService.delete(partnerNameOrPkId);
                break;
            case SFG_FTP:
            case SFG_FTPS:
            case SFG_SFTP:
                remoteFtpPartnerService.delete(partnerNameOrPkId, deleteInSI, deleteArtifacts, deleteArtifacts, deleteArtifacts);
                break;
            case EXISTING_CONNECTION:
                ecPartnerService.delete(partnerNameOrPkId);
                break;
            case SFG_CONNECT_DIRECT:
                remoteConnectDirectPartnerService.delete(partnerNameOrPkId, deleteInSI);
                break;
            case CONNECT_DIRECT:
                connectDirectPartnerService.delete(partnerNameOrPkId);
                break;
            default:
                // No Implementation Needed
        }
    }

    public List<CommunityManagerKeyValueModel> getProfilesByProtocolAndHubInfo(String protocol, boolean isHubInfo) {
        return partnerService.findAllByTpProtocolAndIsProtocolHubInfoLike(protocol, CommonFunctions.convertBooleanToString(isHubInfo))
                .stream()
                .map(partnerEntity -> new CommunityManagerKeyValueModel(partnerEntity.getPkId(), partnerEntity.getTpName()))
                .collect(Collectors.toList());
    }

    public List<CommunityManagerKeyValueModel> getProfilesByProtocol(String protocol) {
        return partnerService.findAllByTpProtocol(protocol)
                .stream()
                .map(partnerEntity -> new CommunityManagerKeyValueModel(partnerEntity.getPkId(), partnerEntity.getTpName()))
                .collect(Collectors.toList());
    }

    //Pem Service: this method is using to get All profiles contact information
    public List<ProfileModel> getProfiles(ProfileModel profileModel, Boolean isOr, String fileTpServer) {
        return partnerService.search(profileModel, isOr, fileTpServer)
                .stream()
                .map(partnerEntity ->
                        new ProfileModel()
                                .setProfileName(partnerEntity.getTpName())
                                .setPgpInfo(partnerEntity.getPgpInfo())
                                .setIpWhiteList(partnerEntity.getIpWhitelist())
                                .setProfileId(partnerEntity.getTpId()).setEmailId(partnerEntity.getEmail()).setPhone(partnerEntity.getPhone())
                                .setProtocol(partnerEntity.getTpProtocol()).setAddressLine1(partnerEntity.getAddressLine1()).setAddressLine2(partnerEntity.getAddressLine2())
                                .setStatus(convertStringToBoolean(partnerEntity.getStatus())).setCustomProfileName(partnerEntity.getCustomTpName())
                                .setPemIdentifier(partnerEntity.getPemIdentifier()))
                .collect(Collectors.toList());
    }

    //Pem Service: this method is using to get All Remote profiles Based on HubInfo and AuthType
    public List<RemoteProfileModel> getRemoteProfiles(Boolean isHubInfo, String prfAuthType) {
        return remoteFtpPartnerService.findAllByIsHubInfoAndPrfAuthTypeLike(isHubInfo, prfAuthType);
    }

}
