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

import com.pe.pcm.common.CommunityManagerKeyValueModel;
import com.pe.pcm.common.CommunityManagerNameModel;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.protocol.*;
import com.pe.pcm.si.SterlingMailboxModel;
import com.pe.pcm.sterling.YfsUserModel;
import com.pe.pcm.sterling.dto.*;
import com.pe.pcm.sterling.profile.SterlingProfilesModel;
import com.pe.pcm.user.entity.UserEntity;
import com.pe.pcm.utils.CommonFunctions;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import static com.pe.pcm.utils.CommonFunctions.*;
import static com.pe.pcm.utils.PCMConstants.*;

/**
 * @author Kiran Reddy.
 */
public class SterlingFunctions {

    private static final String IN_DIRECTORY = "In Directory";
    private static final String OUT_DIRECTORY = "Out Directory";

    private SterlingFunctions() {
        //Not allow to create Object from outside
    }

    public static final Function<SfgProfileDetailsDTO, SciContractDTO> convertSfgProfileDetailsDTOToSciContractDTO = sfgProfileDetailsDTO ->
            new SciContractDTO().setProfileName(sfgProfileDetailsDTO.getProfileName())
                    .setSciProfileObjectId(sfgProfileDetailsDTO.getSciProfileObjectId())
                    .setCommunityProfileId(sfgProfileDetailsDTO.getCommunityProfileId())
                    .setCommunityName(sfgProfileDetailsDTO.getCommunityName())
                    .setTransportEntityId(sfgProfileDetailsDTO.getTransportEntityKey())
                    .setCreateProducerProfile(sfgProfileDetailsDTO.getCreateProducerProfile())
                    .setProtocol(sfgProfileDetailsDTO.getReceivingProtocol())
                    .setExtensionKeysMap(sfgProfileDetailsDTO.getExtensionKeysMap())
                    .setProfileType(sfgProfileDetailsDTO.getProfileType())
                    .setCreateProducerProfile(sfgProfileDetailsDTO.getCreateProducerProfile());

    public static final Function<SterlingProfilesModel, SfgProfileDetailsDTO> convertSterlingProfilesModelToSfgProfileDetailsDTO = sterlingProfilesModel ->
            new SfgProfileDetailsDTO().setProfileName(sterlingProfilesModel.getProfileName())
                    .setUserName(sterlingProfilesModel.getUserName())
                    .setCreateProducerProfile(sterlingProfilesModel.isCreateProducerProfile())
                    .setConsumerCreated(sterlingProfilesModel.isConsumerCreated())
                    .setReceivingProtocol(sterlingProfilesModel.getReceivingProtocol())
                    .setExtensionKeysMap(sterlingProfilesModel.getCustomProtocolExtensions()
                            .stream()
                            .collect(Collectors.toMap(CommunityManagerKeyValueModel::getKey, CommunityManagerKeyValueModel::getValue)))
                    .setProfileType(sterlingProfilesModel.getProfileType())
                    .setConsumerProfile(sterlingProfilesModel.getConsumerProfile())
                    .setSfgSubDetailsLoaded(sterlingProfilesModel.getSfgSubDetailsLoaded());

    public static final Function<SterlingProfilesModel, List<SterlingMailboxModel>> convertSterlingProfilesModelToMbxMailBoxModels = sterlingProfilesModel -> {
        List<SterlingMailboxModel> sterlingMailboxModels = new ArrayList<>();
        if (isNotNull(sterlingProfilesModel.getInDirectory())) {
            sterlingMailboxModels.add(new SterlingMailboxModel().setPath(sterlingProfilesModel.getInDirectory())
                    .setDescription(IN_DIRECTORY));

        }
        if (isNotNull(sterlingProfilesModel.getOutDirectory())) {
            sterlingMailboxModels.add(new SterlingMailboxModel().setPath(sterlingProfilesModel.getOutDirectory())
                    .setDescription(OUT_DIRECTORY));
        }
        if (isNotNull(sterlingProfilesModel.getSfgMailbox())) {
            sterlingMailboxModels.add(new SterlingMailboxModel().setPath(sterlingProfilesModel.getSfgMailbox())
                    .setDescription("SFG Mailbox"));
        }
        return sterlingMailboxModels;
    };

    public static final Function<SterlingProfilesModel, YfsUserModel> convertSterlingProfilesModelToYfsUserModel = sterlingProfilesModel -> {
        YfsUserModel yfsUserModel = new YfsUserModel().setUserName(sterlingProfilesModel.getUserName())
                .setGivenName(isNotNull(sterlingProfilesModel.getGivenName()) ? sterlingProfilesModel.getGivenName() : sterlingProfilesModel.getUserName())
                .setEmailId(sterlingProfilesModel.getEmailId())
                .setPassword(sterlingProfilesModel.getPassword())
                .setSurname(isNotNull(sterlingProfilesModel.getSurname()) ? sterlingProfilesModel.getSurname() : sterlingProfilesModel.getUserName())
                .setPwdPolicyId(sterlingProfilesModel.getPasswordPolicy())
                .setMergeUser(sterlingProfilesModel.isMergeUser())
                .setOrganizationKey(isNotNull(sterlingProfilesModel.getPartnerCode()) ? sterlingProfilesModel.getPartnerCode() : "EXT_" + sterlingProfilesModel.getProfileId());
        Set<String> permissions = new LinkedHashSet<>();
        AtomicReference<String> atomicReference = new AtomicReference<>("");
        if (isNotNull(sterlingProfilesModel.getInDirectory())) {
            getRootDirectories(sterlingProfilesModel.getInDirectory(), permissions, atomicReference, true);
        }
        if (isNotNull(sterlingProfilesModel.getInDirectory())) {
            atomicReference.set("");
            getRootDirectories(sterlingProfilesModel.getInDirectory(), permissions, atomicReference, true);
        }
        if (!permissions.isEmpty()) {
            yfsUserModel.setPermissions(new ArrayList<>(permissions));
        }

        yfsUserModel.setAuthorizedUserKeys(
                sterlingProfilesModel.getAuthorizedUserKeys()
                        .stream()
                        .map(CommunityManagerNameModel::getName)
                        .filter(CommonFunctions::isNotNull)
                        .collect(Collectors.toList()));
        yfsUserModel.setGroups(
                sterlingProfilesModel.getGroups()
                        .stream()
                        .map(CommunityManagerNameModel::getName)
                        .filter(CommonFunctions::isNotNull)
                        .collect(Collectors.toList()));

        return yfsUserModel;
    };

    public static final BiFunction<SterlingProfilesModel, TransportDTO, SterlingProfilesModel> addTransportDTOToSterlingProfilesModel = (sterlingProfilesModel, transportDTO) -> {
        sterlingProfilesModel.setEmailId(transportDTO.getEmailId());
        if (sterlingProfilesModel.getSubscriberType().equalsIgnoreCase("TP")) {
            sterlingProfilesModel.setInDirectory(transportDTO.getDirectory());
        } else {
            sterlingProfilesModel.setOutDirectory(transportDTO.getDirectory());
        }

        return sterlingProfilesModel.setSfgMailbox(transportDTO.getMailBox());
    };

    public static final BiFunction<SterlingProfilesModel, YfsPersonInfoDTO, SterlingProfilesModel> addYfsPersonInfoToSterlingProfilesModel = (sterlingProfilesModel, yfsPersonInfoDTO) -> {
        sterlingProfilesModel.setAddressLine1(yfsPersonInfoDTO.getAddressLine1())
                .setAddressLine2(yfsPersonInfoDTO.getAddressLine2())
                .setEmailId(yfsPersonInfoDTO.getEmailId())
                .setPhone(yfsPersonInfoDTO.getDayPhone().trim());
        return sterlingProfilesModel;
    };

    public static final BiFunction<SterlingProfilesModel, YfsUserModel, SterlingProfilesModel> addYfsUserModelToSterlingProfilesModel = (sterlingProfilesModel, yfsUserModel) ->
            sterlingProfilesModel.setUserName(yfsUserModel.getUserName())
                    .setSurname(yfsUserModel.getSurname())
                    .setGivenName(yfsUserModel.getGivenName())
                    .setPasswordPolicy(yfsUserModel.getPolicy())
                    .setGroups(yfsUserModel.getGroups()
                            .stream()
                            .map(CommunityManagerNameModel::new)
                            .collect(Collectors.toList()))
                    .setAuthorizedUserKeys(yfsUserModel.getAuthorizedUserKeys()
                            .stream()
                            .map(CommunityManagerNameModel::new)
                            .collect(Collectors.toList()));


    public static final Function<RemoteProfileModel, List<SterlingMailboxModel>> convertRemoteProfileModelToMbxMailBoxModels = remoteProfileModel -> {
        List<SterlingMailboxModel> sterlingMailboxModels = new ArrayList<>();
        if (isNotNull(remoteProfileModel.getInDirectory())) {
            sterlingMailboxModels.add(new SterlingMailboxModel().setPath(remoteProfileModel.getInDirectory())
                    .setDescription(remoteProfileModel.getSubscriberType().equals(TP) ? IN_DIRECTORY : OUT_DIRECTORY));

        }
        if (isNotNull(remoteProfileModel.getOutDirectory())) {
            sterlingMailboxModels.add(new SterlingMailboxModel().setPath(remoteProfileModel.getOutDirectory())
                    .setDescription(remoteProfileModel.getSubscriberType().equals(TP) ? OUT_DIRECTORY : IN_DIRECTORY));
        }
        return sterlingMailboxModels;
    };

    public static final BiFunction<YfsUserModel, UserEntity, YfsUserModel> validateAndApplyDefaultValues = (yfsUserModel, userEntity) -> {
        yfsUserModel.setUserName(userEntity.getUserid())
                .setGivenName(userEntity.getFirstName())
                .setEmailId(userEntity.getEmail())
                .setSurname(userEntity.getLastName())
                .setMergeUser(Boolean.TRUE)
                .setUserType(StringUtils.hasText(yfsUserModel.getUserType()) ? yfsUserModel.getUserType() : INTERNAL);
        if (yfsUserModel.getGroups() == null || yfsUserModel.getGroups().isEmpty()) {
            yfsUserModel.setGroups(Arrays.asList("MAILBOX", "Mailbox Administrators", "Mailbox Browser Interface Users"));
        }
        if (yfsUserModel.getPermissions() != null && yfsUserModel.getPermissions().isEmpty()) {
            throw GlobalExceptionHandler.internalServerError("Permissions should not be empty, at least assign one permission.");
        }
        return yfsUserModel;
    };

    public static final Function<RemoteProfileModel, YfsUserModel> convertRemoteProfileModelToYfsUserModel = remoteProfileModel -> {
        String userName = isNotNull(remoteProfileModel.getProfileUserName()) ? remoteProfileModel.getProfileUserName() : remoteProfileModel.getUserName();
        YfsUserModel yfsUserModel = new YfsUserModel().setUserName(userName)
                //GivenName and surname allows only max of 60characters
                .setGivenName(isNotNull(remoteProfileModel.getGivenName()) ? remoteProfileModel.getGivenName() :
                        remoteProfileModel.getProfileName().substring(0, Math.min(remoteProfileModel.getProfileName().length(), 60)))
                .setEmailId(remoteProfileModel.getEmailId())
                .setPassword(isNotNull(remoteProfileModel.getProfileUserPassword()) ? remoteProfileModel.getProfileUserPassword() : remoteProfileModel.getPassword())
                .setSurname(isNotNull(remoteProfileModel.getSurname()) ? remoteProfileModel.getSurname() :
                        remoteProfileModel.getProfileName().substring(0, Math.min(remoteProfileModel.getProfileName().length(), 60)))
                .setPwdPolicyId(remoteProfileModel.getPwdPolicy())
                .setMergeUser(remoteProfileModel.isMergeUser())
                //User Identity and Organization both are same, need to remove one soon.
                .setUserIdentity(remoteProfileModel.getUserIdentity())
                .setOrganizationKey(remoteProfileModel.getUserIdentity())
                .setResetPermissions(remoteProfileModel.isResetPermissions())
                .setUserType(remoteProfileModel.getAuthenticationType())
                .setAuthenticationHost(remoteProfileModel.getAuthenticationHost());

        if (isNotNull(remoteProfileModel.getSessionTimeout())) {
            yfsUserModel.setSessionTimeOut(Integer.parseInt(remoteProfileModel.getSessionTimeout()) * 60);
        }

        if (remoteProfileModel.getCreateDirectoryInSI() || remoteProfileModel.getPoolingInterval().equals("ON")) {
            //On Alok request added permissions from request and adding those to yfsuser permissions if not empty.
            if (isNotNull(remoteProfileModel.getPermissions()) && !remoteProfileModel.getPermissions().isEmpty()) {
                Set<String> permissionsSet = new LinkedHashSet<>();
                AtomicReference<String> atomicReference = new AtomicReference<>("");
                remoteProfileModel.getPermissions().forEach(communityManagerNameModel ->
                        getRootDirectories(communityManagerNameModel.getName(), permissionsSet, atomicReference, true)
                );
                if (!permissionsSet.isEmpty()) {
                    yfsUserModel.getPermissions().addAll(permissionsSet);
                }
            } else {
                Set<String> permissionsSet = new LinkedHashSet<>();
                AtomicReference<String> atomicReference = new AtomicReference<>("");
                if (isNotNull(remoteProfileModel.getInDirectory())) {
                    getRootDirectories(remoteProfileModel.getInDirectory(), permissionsSet, atomicReference, true);
                }
                if (isNotNull(remoteProfileModel.getOutDirectory())) {
                    atomicReference.set("");
                    getRootDirectories(remoteProfileModel.getOutDirectory(), permissionsSet, atomicReference, true);
                }
                if (!permissionsSet.isEmpty()) {
                    yfsUserModel.getPermissions().addAll(permissionsSet);
                }
            }
        }

        Set<String> permissionSetToDelete;
        if (!yfsUserModel.getPermissions().isEmpty()) {
            permissionSetToDelete = new HashSet<>(yfsUserModel.getPermissions());
            if (!remoteProfileModel.getPermissions().isEmpty() && isNotNull(remoteProfileModel.getPermissions())) {
                remoteProfileModel.getPermissions().forEach(communityManagerNameModel ->
                        permissionSetToDelete.remove(findRootDirectory(communityManagerNameModel.getName(), Boolean.TRUE) + " " + MAILBOX)
                );
            } else {
                if (isNotNull(remoteProfileModel.getInDirectory())) {
                    permissionSetToDelete.remove(findRootDirectory(remoteProfileModel.getInDirectory(), Boolean.TRUE) + " " + MAILBOX);
                }
                if (isNotNull(remoteProfileModel.getOutDirectory())) {
                    permissionSetToDelete.remove(findRootDirectory(remoteProfileModel.getOutDirectory(), Boolean.TRUE) + " " + MAILBOX);
                }
            }
        } else {
            permissionSetToDelete = new HashSet<>();
            AtomicReference<String> atomicReference = new AtomicReference<>("");
            if (!remoteProfileModel.getPermissions().isEmpty() && isNotNull(remoteProfileModel.getPermissions())) {
                remoteProfileModel.getPermissions().forEach(communityManagerNameModel -> getRootDirectories(communityManagerNameModel.getName(), permissionSetToDelete, atomicReference, true));
                remoteProfileModel.getPermissions().forEach(communityManagerNameModel -> permissionSetToDelete.remove(findRootDirectory(communityManagerNameModel.getName(), Boolean.TRUE) + " " + MAILBOX));
            } else {
                if (isNotNull(remoteProfileModel.getInDirectory())) {
                    getRootDirectories(remoteProfileModel.getInDirectory(), permissionSetToDelete, atomicReference, true);
                    permissionSetToDelete.remove(findRootDirectory(remoteProfileModel.getInDirectory(), Boolean.TRUE) + " " + MAILBOX);
                }
                if (isNotNull(remoteProfileModel.getOutDirectory())) {
                    getRootDirectories(remoteProfileModel.getOutDirectory(), permissionSetToDelete, atomicReference, true);
                    permissionSetToDelete.remove(findRootDirectory(remoteProfileModel.getOutDirectory(), Boolean.TRUE) + " " + MAILBOX);
                }
            }
        }
        yfsUserModel.setPermissionsToDelete(new ArrayList<>(permissionSetToDelete));

        yfsUserModel.setAuthorizedUserKeys(
                remoteProfileModel.getAuthorizedUserKeys()
                        .stream()
                        .map(CommunityManagerNameModel::getName)
                        .filter(CommonFunctions::isNotNull)
                        .collect(Collectors.toList()));

        List<String> groupList = remoteProfileModel.getGroups()
                .stream()
                .map(CommunityManagerNameModel::getName)
                .filter(CommonFunctions::isNotNull)
                .collect(Collectors.toList());
        if (groupList.isEmpty()) {
            yfsUserModel.setGroups(Arrays.asList("MAILBOX", "Mailbox Administrators", "Mailbox Browser Interface Users"));
        } else {
            yfsUserModel.setGroups(groupList);
        }
        return yfsUserModel;
    };

    public static final Function<As2Model, RemoteProfileModel> convertAs2ModelToRemoteProfileModel = as2Model -> {

        RemoteProfileModel remoteProfileModel = new RemoteProfileModel();

        remoteProfileModel.setProfileName(as2Model.getProfileName())
                .setProfileId(as2Model.getProfileId())
                .setProtocol(as2Model.getProtocol())
                .setAddressLine1(as2Model.getAddressLine1())
                .setAddressLine2(as2Model.getAddressLine2())
                .setPhone(as2Model.getPhone())
                .setEmailId(as2Model.getEmailId())
                .setCustomProfileName(as2Model.getCustomProfileName())
                .setPgpInfo(as2Model.getPgpInfo())
                .setStatus(as2Model.getStatus())
                .setHubInfo(as2Model.getHubInfo())
                .setPemIdentifier(as2Model.getPemIdentifier())
                .setIpWhiteList(as2Model.getIpWhiteList());
        return remoteProfileModel.setAs2EmailAddress(isNotNull(as2Model.getAs2EmailAddress()) ? as2Model.getAs2EmailAddress() : as2Model.getEmailId())
                .setOrgIdentifier(as2Model.getAs2Identifier())
                .setEndPoint(as2Model.getEndPoint())
                .setResponseTimeOut(isNotNull(as2Model.getResponseTimeout()) ? String.valueOf(as2Model.getResponseTimeout()) : "")
                .setAdapterName(as2Model.getHttpclientAdapter())
                .setCompressData(as2Model.getCompressData())
                .setPayloadType(as2Model.getPayloadType())
                .setMimeType(as2Model.getMimeType())
                .setMimeSubType(as2Model.getMimeSubType())
                .setCipherStrength(as2Model.getCipherStrength())
                .setExchangeCertificateId(as2Model.getExchangeCertificate())
                .setSigningCertificationId(as2Model.getSigningCertification())
                .setCaCertificateName(as2Model.getCaCertificate())
                .setKeyCertificatePassphrase(as2Model.getKeyCertificatePassphrase())
                .setSslType(as2Model.getSslType())
                .setPayloadSecurity(as2Model.getPayloadSecurity())
                .setEncryptionAlgorithm(as2Model.getEncryptionAlgorithm())
                .setSignatureAlgorithm(as2Model.getSignatureAlgorithm())
                .setMdn(as2Model.getMdn())
                .setMdnType(as2Model.getMdnType())
                .setMdnEncryption(as2Model.getMdnEncryption())
                .setReceiptToAddress(as2Model.getReceiptToAddress())
                .setSubscriberType(as2Model.getSubscriberType());
    };

    public static final Function<RemoteProfileModel, As2ProfileDTO> convertRemoteProfileModelToAs2ProfileDTO = remoteProfileModel ->
            new As2ProfileDTO().setIsOrg(convertBooleanToString(remoteProfileModel.getHubInfo()))
                    .setHttpClientAdapter(remoteProfileModel.getAdapterName());

    public static final Function<RemoteProfileModel, As2EmailInfoDTO> convertRemoteProfileModelToAs2EmailInfoDTO = remoteProfileModel ->
            new As2EmailInfoDTO().setEmailAddress(remoteProfileModel.getEmailId());

    public static final BiFunction<RemoteProfileModel, As2Model, As2Model> convertRemoteProfileModelToAs2Model = (remoteProfileModel, as2Model) ->
            as2Model.setAs2Identifier(remoteProfileModel.getOrgIdentifier())
//                    .setCaCertificate(remoteProfileModel.getCaCertificateName())
//                    .setExchangeCertificate(remoteProfileModel.getExchangeCertificateId())
//                    .setSigningCertification(remoteProfileModel.getSigningCertificationId())
                    .setResponseTimeout(remoteProfileModel.getResponseTimeOut() != null ? Integer.parseInt(remoteProfileModel.getResponseTimeOut()) : 0)
                    .setEncryptionAlgorithm(remoteProfileModel.getEncryptionAlgorithm())
                    .setSignatureAlgorithm(remoteProfileModel.getSignatureAlgorithm())
                    .setEndPoint(remoteProfileModel.getEndPoint())
                    //.setCompressData(remoteProfileModel.getCompressData())
                    .setPayloadType(remoteProfileModel.getPayloadType())
                    //.setMimeType(remoteProfileModel.getMimeType())
                    //.setMimeSubType(remoteProfileModel.getMimeSubType())
                    .setCipherStrength(remoteProfileModel.getCipherStrength())
                    .setSslType(remoteProfileModel.getSslType())
                    //.setPayloadSecurity(remoteProfileModel.getPayloadSecurity())
                    .setEncryptionAlgorithm(remoteProfileModel.getEncryptionAlgorithm())
                    .setMdn(remoteProfileModel.getMdn())
                    //.setMdnType(remoteProfileModel.getMdnType())
                    .setMdnEncryption(remoteProfileModel.getMdnEncryption());

    public static final Function<MailboxModel, List<SterlingMailboxModel>> convertMailboxModelToSterlingMailBoxModels = mailboxModel -> {
        List<SterlingMailboxModel> sterlingMailboxModels = new ArrayList<>();
        if (isNotNull(mailboxModel.getInMailBox())) {
            sterlingMailboxModels.add(new SterlingMailboxModel().setPath(mailboxModel.getInMailBox())
                    .setDescription(mailboxModel.getSubscriberType().equals("TP") ? IN_DIRECTORY : OUT_DIRECTORY));

        }
        if (isNotNull(mailboxModel.getOutMailBox())) {
            sterlingMailboxModels.add(new SterlingMailboxModel().setPath(mailboxModel.getOutMailBox())
                    .setDescription(mailboxModel.getSubscriberType().equals("TP") ? OUT_DIRECTORY : IN_DIRECTORY));
        }
        return sterlingMailboxModels;
    };

    public static final Function<FileSystemModel, List<SterlingMailboxModel>> convertFileSystemModelToSterlingMailBoxModels = fileSystemModel -> {
        List<SterlingMailboxModel> sterlingMailboxModels = new ArrayList<>();
        if (isNotNull(fileSystemModel.getInDirectory()) && fileSystemModel.getInDirectory().startsWith("/")) {
            sterlingMailboxModels.add(new SterlingMailboxModel().setPath(fileSystemModel.getInDirectory())
                    .setDescription(fileSystemModel.getSubscriberType().equals("TP") ? IN_DIRECTORY : OUT_DIRECTORY));

        }
        if (isNotNull(fileSystemModel.getOutDirectory()) && fileSystemModel.getOutDirectory().startsWith("/")) {
            sterlingMailboxModels.add(new SterlingMailboxModel().setPath(fileSystemModel.getOutDirectory())
                    .setDescription(fileSystemModel.getSubscriberType().equals("TP") ? OUT_DIRECTORY : IN_DIRECTORY));
        }
        return sterlingMailboxModels;
    };
    public static final Function<HttpModel, List<SterlingMailboxModel>> convertHttpModelToSterlingMailBoxModels = httpModel -> {
        List<SterlingMailboxModel> sterlingMailboxModels = new ArrayList<>();
        if (isNotNull(httpModel.getInMailBox())) {
            sterlingMailboxModels.add(new SterlingMailboxModel().setPath(httpModel.getInMailBox())
                    .setDescription(IN_DIRECTORY));

        }
        return sterlingMailboxModels;
    };

    public static final UnaryOperator<RemoteProfileModel> applyRemoteProfileModelDefaultValues = remoteProfileModel ->
            remoteProfileModel.setTransferType(isNotNull(remoteProfileModel.getTransferType()) ? remoteProfileModel.getTransferType() : "Binary")
                    .setConnectionType(isNotNull(remoteProfileModel.getConnectionType()) ? remoteProfileModel.getConnectionType() : "passive");
}
