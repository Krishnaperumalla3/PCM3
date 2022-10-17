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

package com.pe.pcm.protocol.function;

import com.pe.pcm.enums.Protocol;
import com.pe.pcm.protocol.RemoteProfileModel;
import com.pe.pcm.sterling.YfsUserModel;
import com.pe.pcm.sterling.dto.*;
import com.pe.pcm.sterling.profile.SterlingProfilesModel;
import com.pe.pcm.utils.PCMConstants;

import java.util.function.BiFunction;
import java.util.function.Function;

import static com.pe.pcm.utils.CommonFunctions.isNotNull;

/**
 * @author Chenchu Kiran.
 */
public class SiProtocolFunctions {

    private SiProtocolFunctions() {
    }

    public static final Function<RemoteProfileModel, TransportDTO> convertRemoteFtpModelToTransportDTO = remoteFtpModel ->
            new TransportDTO().setProfileName(remoteFtpModel.getCustomProfileName())
                    .setProfileId(remoteFtpModel.getProfileId())
                    .setEmailId(remoteFtpModel.getEmailId())
                    .setResponseTimeout(remoteFtpModel.getResponseTimeOut())
                    .setHubInfo(remoteFtpModel.getHubInfo())
                    .setDirectory(remoteFtpModel.getSubscriberType().equalsIgnoreCase("TP") ? remoteFtpModel.getInDirectory() : remoteFtpModel.getOutDirectory())
                    .setMailBox("/" + remoteFtpModel.getCustomProfileName())
                    .setUserId(remoteFtpModel.getUserName())
                    .setHostName(remoteFtpModel.getRemoteHost())
                    .setPortNumber(remoteFtpModel.getRemotePort())
                    .setProtocolMode("PUT")
                    .setTransferMode(isNotNull(remoteFtpModel.getConnectionType()) ? remoteFtpModel.getConnectionType() : "active")
                    .setResponseTimeout(isNotNull(remoteFtpModel.getResponseTimeOut()) ? remoteFtpModel.getResponseTimeOut() : "30")
                    .setNormalProfile(true)
                    .setCipherStrength(remoteFtpModel.getProtocol().equals(Protocol.AS2.getProtocol()) ? remoteFtpModel.getCipherStrength() : remoteFtpModel.getEncryptionStrength())
                    .setEndPoint(remoteFtpModel.getEndPoint())
                    .setSslOption(remoteFtpModel.getSslType());


    public static final Function<RemoteProfileModel, YfsOrganizationDTO> convertRemoteFtpModelToYfsOrganizationDTO = remoteFtpModel -> {
        String profileName;
        String orgKeyAndCode;
        if (remoteFtpModel.getProtocol().equals(Protocol.AS2.getProtocol())
                || remoteFtpModel.getProtocol().equals(Protocol.SFG_FTP.getProtocol())
                || remoteFtpModel.getProtocol().equals(Protocol.SFG_FTPS.getProtocol())
                || remoteFtpModel.getProtocol().equals(Protocol.SFG_SFTP.getProtocol())) {
            profileName = remoteFtpModel.getProfileId();
            orgKeyAndCode = remoteFtpModel.getProfileId();
        } else {
            profileName = remoteFtpModel.getProfileName();
            orgKeyAndCode = remoteFtpModel.getCustomProfileName();
        }
        return new YfsOrganizationDTO().setOrganizationName(profileName)
                .setOrganizationKeyAndCode(orgKeyAndCode)
                .setHubInfo(remoteFtpModel.getHubInfo())
                .setOrganizationIdentifier(remoteFtpModel.getOrgIdentifier());
    };


    public static final Function<RemoteProfileModel, YfsPersonInfoDTO> convertRemoteFtpModelToYfsPersonInfo = remoteFtpModel ->
            new YfsPersonInfoDTO().setAddressLine1(remoteFtpModel.getAddressLine1())
                    .setAddressLine2(remoteFtpModel.getAddressLine2())
                    .setCity(remoteFtpModel.getCity())
                    .setCompany(remoteFtpModel.getCustomProfileName())
                    .setCountry("US")
                    .setZipCode(remoteFtpModel.getZipCode())
                    .setCity(remoteFtpModel.getCity())
                    .setState(remoteFtpModel.getState())
                    .setEmailId(remoteFtpModel.getEmailId())
                    .setDayPhone(remoteFtpModel.getPhone())
                    .setFirstName(" ")
                    .setLastName(" ");

    public static final Function<RemoteProfileModel, DocExchangeDTO> convertRemoteFtpModelToDocExchangeDTO = remoteFtpModel ->
            new DocExchangeDTO().setNoOfRetries(remoteFtpModel.getNoOfRetries())
                    .setRetryInterval(remoteFtpModel.getRetryInterval())
                    .setProfileName(remoteFtpModel.getCustomProfileName())
                    .setProfileId(remoteFtpModel.getProfileId())
                    .setNormalProfile(true)
                    .setEnvEncryptAlg(remoteFtpModel.getEncryptionAlgorithm())
                    .setMsgSigningAlg(remoteFtpModel.getSignatureAlgorithm());

    public static final Function<RemoteProfileModel, DeliveryChanDTO> convertRemoteFtpModelToDeliveryChanDTO = remoteFtpModel ->
            new DeliveryChanDTO().setNormalProfile(true)
                    .setProfileName(remoteFtpModel.getCustomProfileName())
                    .setProfileId(remoteFtpModel.getProfileId())
                    .setHubInfo(remoteFtpModel.getHubInfo())
                    .setMdn(remoteFtpModel.getMdn())
                    .setMdnType(remoteFtpModel.getMdnType())
                    .setMdnEncryption(remoteFtpModel.getMdnEncryption())
                    .setReceiptToAddress(remoteFtpModel.getReceiptToAddress());

    public static final Function<RemoteProfileModel, PackagingDTO> convertRemoteFtpModelToPackagingDTO = remoteFtpModel ->
            new PackagingDTO().setProfileName(remoteFtpModel.getCustomProfileName())
                    .setProfileId(remoteFtpModel.getProfileId())
                    .setNormalProfile(true)
                    .setHubInfo(remoteFtpModel.getHubInfo())
                    .setPayloadType(remoteFtpModel.getPayloadType())
                    .setMimeType(remoteFtpModel.getMimeType())
                    .setMimeSubType(remoteFtpModel.getMimeSubType())
                    .setCompressData(remoteFtpModel.getCompressData());

    public static final Function<RemoteProfileModel, ProfileDTO> convertRemoteFtpModelToProfileDTO = remoteFtpModel ->
            new ProfileDTO().setProfileName(remoteFtpModel.getCustomProfileName())
                    .setNormalProfile(true);

    public static final Function<RemoteProfileModel, TransCertDTO> convertRemoteFtpModelToSciTrpSslCert = remoteFtpModel ->
            new TransCertDTO().setCertStatus(0);

    public static final Function<RemoteProfileModel, TransCertDTO> convertRemoteFtpModelToCaCert = remoteFtpModel ->
            new TransCertDTO().setCertStatus(0);

    public static final BiFunction<RemoteProfileModel, TransportDTO, RemoteProfileModel> convertTransportDTOToRemoteFtpModel = (remoteFtpModel, transportDTO) -> {
        remoteFtpModel.setEmailId(transportDTO.getEmailId());
        if (remoteFtpModel.getSubscriberType().equalsIgnoreCase("TP")) {
            remoteFtpModel.setInDirectory(transportDTO.getDirectory());
        } else {
            remoteFtpModel.setOutDirectory(transportDTO.getDirectory());
        }
        return remoteFtpModel.setUserName(transportDTO.getUserId())
                .setRemoteHost(transportDTO.getHostName())
                .setRemotePort(isNotNull(transportDTO.getPortNumber()) ? transportDTO.getPortNumber().trim() : transportDTO.getPortNumber())
                .setResponseTimeOut(transportDTO.getResponseTimeout())
                .setEndPoint(transportDTO.getEndPoint())
                .setSslType(transportDTO.getSslOption())
                .setCipherStrength(transportDTO.getCipherStrength());
    };

    public static final BiFunction<RemoteProfileModel, YfsPersonInfoDTO, RemoteProfileModel> convertYfsPersonInfoToRemoteFtpModel = (remoteFtpModel, yfsPersonInfoDTO) -> {
        remoteFtpModel.setAddressLine1(yfsPersonInfoDTO.getAddressLine1())
                .setAddressLine2(yfsPersonInfoDTO.getAddressLine2())
                .setEmailId(yfsPersonInfoDTO.getEmailId())
                .setPhone(isNotNull(yfsPersonInfoDTO.getDayPhone()) ? yfsPersonInfoDTO.getDayPhone().trim() : yfsPersonInfoDTO.getDayPhone());
        return remoteFtpModel;
    };

    public static final BiFunction<RemoteProfileModel, DocExchangeDTO, RemoteProfileModel> convertDocExchangeDTOToRemoteFtpModel = (remoteFtpModel, docExchangeDTO) ->
            remoteFtpModel.setNoOfRetries(docExchangeDTO.getNoOfRetries())
                    .setRetryInterval(docExchangeDTO.getRetryInterval())
                    .setEncryptionAlgorithm(docExchangeDTO.getEnvEncryptAlg())
                    .setSignatureAlgorithm(docExchangeDTO.getMsgSigningAlg());

    public static final Function<YfsUserModel, YfsPersonInfoDTO> convertYfsUserModelToYfsPersonInfoDTO = yfsUserModel ->
            new YfsPersonInfoDTO().setAddressLine1(" ")
                    .setAddressLine2(" ")
                    .setCity(" ")
                    .setCompany(" ")
                    .setCountry(" ")
                    .setZipCode(" ")
                    .setCity(" ")
                    .setState(" ")
                    .setEmailId(yfsUserModel.getEmailId())
                    .setDayPhone(" ")
                    .setFirstName(yfsUserModel.getGivenName())
                    .setLastName(yfsUserModel.getSurname())
                    .setBirthDate(yfsUserModel.getPwdLastChangeDon());

    public static final BiFunction<SterlingProfilesModel, Protocol, TransportDTO> convertSterlingProfilesModelToTransportDTO = (sterlingProfilesModel, protocol) -> {
        TransportDTO transportDTO = new TransportDTO().setProfileName(sterlingProfilesModel.getProfileName())
                .setProfileId(sterlingProfilesModel.getProfileId())
                .setEmailId(sterlingProfilesModel.getEmailId())
                .setResponseTimeout("")
                .setHubInfo(sterlingProfilesModel.getHubInfo())
                .setMailBox(isNotNull(sterlingProfilesModel.getSfgMailbox()) ? sterlingProfilesModel.getSfgMailbox() : "/" + sterlingProfilesModel.getProfileName())
                .setSslOption("SSL_NONE")
                .setReceivingProtocol(sterlingProfilesModel.getReceivingProtocol())
                .setProfileType(sterlingProfilesModel.getProfileType());

        if (!isNotNull(sterlingProfilesModel.getCustomProtocolName())) {
            transportDTO.setProtocolMode("PUT")
                    .setTransferMode("active")
                    .setResponseTimeout("300")
                    .setDirectory(sterlingProfilesModel.getSubscriberType().equalsIgnoreCase("TP") ? sterlingProfilesModel.getInDirectory() : sterlingProfilesModel.getOutDirectory());
        } else {
            if (!sterlingProfilesModel.getProfileType().equalsIgnoreCase(PCMConstants.CONSUMER)) {
                transportDTO.setMailBox("");
            }
        }

        switch (protocol) {
            case SFG_FTP:
                transportDTO.setUserId(sterlingProfilesModel.getConsumerProfile() ? sterlingProfilesModel.getConsumerFtpConfiguration().getUserName() : sterlingProfilesModel.getProducerFtpConfiguration().getUserName())
                        .setPassword(sterlingProfilesModel.getConsumerProfile() ? sterlingProfilesModel.getConsumerFtpConfiguration().getPassword() : sterlingProfilesModel.getProducerFtpConfiguration().getPassword())
                        .setHostName(sterlingProfilesModel.getConsumerProfile() ? sterlingProfilesModel.getConsumerFtpConfiguration().getHostName() : sterlingProfilesModel.getProducerFtpConfiguration().getHostName())
                        .setPortNumber(sterlingProfilesModel.getConsumerProfile() ? sterlingProfilesModel.getConsumerFtpConfiguration().getListenPort() : sterlingProfilesModel.getProducerFtpConfiguration().getListenPort());
                break;
            case SFG_FTPS:
                transportDTO.setUserId(sterlingProfilesModel.getConsumerProfile() ? sterlingProfilesModel.getConsumerFtpsConfiguration().getUserName() : sterlingProfilesModel.getProducerFtpsConfiguration().getUserName())
                        .setPassword(sterlingProfilesModel.getConsumerProfile() ? sterlingProfilesModel.getConsumerFtpsConfiguration().getPassword() : sterlingProfilesModel.getProducerFtpsConfiguration().getPassword())
                        .setHostName(sterlingProfilesModel.getConsumerProfile() ? sterlingProfilesModel.getConsumerFtpsConfiguration().getHostName() : sterlingProfilesModel.getProducerFtpsConfiguration().getHostName())
                        .setPortNumber(sterlingProfilesModel.getConsumerProfile() ? sterlingProfilesModel.getConsumerFtpsConfiguration().getListenPort() : sterlingProfilesModel.getProducerFtpsConfiguration().getListenPort())
                        .setSslOption("SSL_MUST")
                        .setSecurityProtocol("SSL");

                break;
            case SFG_SFTP:
                /*TODO*/
                break;
            default:
                transportDTO.setUserId("")
                        .setPassword(sterlingProfilesModel.getPassword())
                        .setHostName("");
                break;
        }
        return transportDTO;
    };

    public static final Function<SterlingProfilesModel, YfsOrganizationDTO> convertSterlingProfilesModelToYfsOrganizationDTO = sterlingProfilesModel ->
            new YfsOrganizationDTO().setOrganizationKeyAndCode(isNotNull(sterlingProfilesModel.getPartnerCode()) ? sterlingProfilesModel.getPartnerCode() : "EXT_" + sterlingProfilesModel.getProfileId())
                    .setOrganizationName(sterlingProfilesModel.getProfileId())
                    .setHubInfo(sterlingProfilesModel.getHubInfo());

    public static final Function<SterlingProfilesModel, ProfileDTO> convertSterlingProfilesModelToProfileDTO = sterlingProfilesModel ->
            new ProfileDTO().setProfileName(sterlingProfilesModel.getProfileName())
                    .setProfileType(sterlingProfilesModel.getProfileType());

    public static final Function<SterlingProfilesModel, DeliveryChanDTO> convertSterlingProfilesModelToDeliveryChanDTO = sterlingProfilesModel ->
            new DeliveryChanDTO();

    public static final Function<SterlingProfilesModel, PackagingDTO> convertSterlingProfilesModelToPackagingDTO = sterlingProfilesModel ->
            new PackagingDTO().setProfileName(sterlingProfilesModel.getProfileName())
                    .setProfileType(sterlingProfilesModel.getProfileType());

    public static final BiFunction<SterlingProfilesModel, Protocol, DocExchangeDTO> convertSterlingProfilesModelToDocExchangeDTO = (sterlingProfilesModel, protocol) -> {
        DocExchangeDTO docExchangeDTO = new DocExchangeDTO().setProfileName(sterlingProfilesModel.getProfileName())
                .setProfileType(sterlingProfilesModel.getProfileType());
        switch (protocol) {
            case SFG_FTP:
                docExchangeDTO.setNoOfRetries(sterlingProfilesModel.getConsumerProfile() ? sterlingProfilesModel.getConsumerFtpConfiguration().getNumberOfRetries() : sterlingProfilesModel.getProducerFtpConfiguration().getNumberOfRetries())
                        .setRetryInterval(sterlingProfilesModel.getConsumerProfile() ? sterlingProfilesModel.getConsumerFtpConfiguration().getRetryInterval() : sterlingProfilesModel.getProducerFtpConfiguration().getRetryInterval());
                break;
            case SFG_FTPS:
                docExchangeDTO.setNoOfRetries(sterlingProfilesModel.getConsumerProfile() ? sterlingProfilesModel.getConsumerFtpsConfiguration().getNumberOfRetries() : sterlingProfilesModel.getProducerFtpsConfiguration().getNumberOfRetries())
                        .setRetryInterval(sterlingProfilesModel.getConsumerProfile() ? sterlingProfilesModel.getConsumerFtpsConfiguration().getRetryInterval() : sterlingProfilesModel.getProducerFtpsConfiguration().getRetryInterval());
                break;
            case SFG_SFTP:
                /*TODO*/
                break;
            default:
                //No need to do any operations here
                break;
        }
        return docExchangeDTO;
    };

    public static final Function<SterlingProfilesModel, YfsPersonInfoDTO> convertSterlingProfilesModelToYfsPersonInfo = sterlingProfilesModel ->
            new YfsPersonInfoDTO().setAddressLine1(sterlingProfilesModel.getAddressLine1())
                    .setAddressLine2(sterlingProfilesModel.getAddressLine2())
                    .setCity(sterlingProfilesModel.getCity())
                    .setCompany(sterlingProfilesModel.getProfileName())
                    .setCountry("US")
                    .setZipCode(sterlingProfilesModel.getZipCode())
                    .setCity(sterlingProfilesModel.getCity())
                    .setState(sterlingProfilesModel.getState())
                    .setEmailId(sterlingProfilesModel.getEmailId())
                    .setDayPhone(sterlingProfilesModel.getPhone())
                    .setFirstName(" ")
                    .setLastName(" ");
}
