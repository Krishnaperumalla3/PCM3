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

import com.pe.pcm.b2b.other.CaCertGetModel;
import com.pe.pcm.b2b.other.CipherSuiteNameGetModel;
import com.pe.pcm.b2b.protocol.RemoteAs2OrgProfile;
import com.pe.pcm.b2b.protocol.RemoteAs2PartnerProfile;
import com.pe.pcm.common.CommunityManagerNameModel;
import com.pe.pcm.googledrive.GoogleDriveModel;
import com.pe.pcm.googledrive.entity.GoogleDriveEntity;
import com.pe.pcm.protocol.*;
import com.pe.pcm.protocol.as2.entity.As2Entity;
import com.pe.pcm.protocol.awss3.entity.AwsS3Entity;
import com.pe.pcm.protocol.azure.entity.AzureEntity;
import com.pe.pcm.protocol.connectdirect.entity.ConnectDirectEntity;
import com.pe.pcm.protocol.customprotocol.entity.CustomProtocolEntity;
import com.pe.pcm.protocol.ec.entity.EcEntity;
import com.pe.pcm.protocol.filesystem.entity.FileSystemEntity;
import com.pe.pcm.protocol.ftp.entity.FtpEntity;
import com.pe.pcm.protocol.http.entity.HttpEntity;
import com.pe.pcm.protocol.mailbox.entity.MailboxEntity;
import com.pe.pcm.protocol.mq.entity.MqEntity;
import com.pe.pcm.protocol.oracleebs.entity.OracleEbsEntity;
import com.pe.pcm.protocol.remotecd.entity.RemoteConnectDirectEntity;
import com.pe.pcm.protocol.remoteftp.entity.RemoteFtpEntity;
import com.pe.pcm.protocol.sap.entity.SapEntity;
import com.pe.pcm.protocol.smtp.entity.SmtpEntity;
import com.pe.pcm.protocol.webservice.entity.WebserviceEntity;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;
import static com.pe.pcm.utils.CommonFunctions.*;
import static java.lang.Boolean.TRUE;

public class ProtocolFunctions {

    private static final String NONE = "NONE";
    private static final String MD5 = "MD5";
    private static final String SHA1 = "SHA1";
    private static final String SHA256 = "SHA256";
    private static final String SHA384 = "SHA384";
    private static final String SHA512 = "SHA512";
    private static final String SHA_1 = "SHA-1";
    private static final String SHA_256 = "SHA-256";
    private static final String SHA_384 = "SHA-384";
    private static final String SHA_512 = "SHA-512";

    private static final String TRIPLE_DES_168_CBC_WITH_PKCS5_PADDING = "Triple DES 168 CBC with PKCS5 padding";
    private static final String BIT_56_DES_CBC_WITH_PKCS5_PADDING = "56-bit DES CBC with PKCS5 padding";
    private static final String BIT_128_RC2_CBC_WITH_PKCS5_PADDING = "128-bit RC2 CBC with PKCS5 padding";
    private static final String BIT_40_RC2_CBC_WITH_PKCS5_PADDING = "40-bit RC2 CBC with PKCS5 padding";

    private static final String SYNCHRONOUS = "Synchronous";
    private static final String ASYNCHRONOUS_HTTP = "Asynchronous HTTP";
    private static final String ASYNCHRONOUS_HTTPS = "Asynchronous HTTPS";
    private static final String ASYNCHRONOUS_SMTP = "Asynchronous SMTP";

    private static final String PLAIN_TEXT = "Plain Text";
    private static final String SIGNED_DETACHED = "Signed Detached";
    private static final String ENCRYPTED = "Encrypted";
    private static final String SIGNED_DETACHED_ENCRYPTED = "Signed Detached Encrypted";

    private static final String ZERO = "0";
    private static final String ONE = "1";
    private static final String TWO = "2";
    private static final String THREE = "3";
    private static final String FOUR = "4";
    private static final String FIVE = "5";
    private static final String SIX = "6";
    private static final String SEVEN = "7";
    private static final String EIGHT = "8";
    private static final String NINE = "9";


    private ProtocolFunctions() {
    }

    public static final Function<FtpModel, FtpEntity> mapperToFtpEntity = ftpModel ->
            new FtpEntity()
                    .setProtocolType(ftpModel.getProtocol())
                    .setIsActive(convertBooleanToString(ftpModel.getStatus()))
                    .setHostName(ftpModel.getHostName())
                    .setPortNo(ftpModel.getPortNumber())
                    .setUserId(ftpModel.getUserName())
                    .setPassword(ftpModel.getPassword())
                    .setFileType(ftpModel.getFileType())
                    .setTransferType(ftpModel.getTransferType())
                    .setInDirectory(ftpModel.getInDirectory())
                    .setOutDirectory(ftpModel.getOutDirectory())
                    .setCertificateId(ftpModel.getProtocol().equals("FTPS") ? ftpModel.getCertificateId() : " ")
                    .setKnownHostKey(ftpModel.getProtocol().equals("SFTP") ? ftpModel.getKnownHostKey() : " ")
                    .setPoolingIntervalMins(ftpModel.getPoolingInterval())
                    .setAdapterName(ftpModel.getAdapterName())
                    .setDeleteAfterCollection(convertBooleanToString(ftpModel.getDeleteAfterCollection()))
                    .setIsHubInfo(convertBooleanToString(ftpModel.getHubInfo()))
                    .setCwdUp(ftpModel.getCwdUp())
                    .setQuote(ftpModel.getQuote())
                    .setSite(ftpModel.getSite())
                    .setConnectionType(ftpModel.getConnectionType())
                    .setMbDestination(ftpModel.getMbDestination())
                    .setMbDestinationLookup(ftpModel.getMbDestinationLookup())
                    .setSslSocket(ftpModel.getSslSocket())
                    .setSslCipher(ftpModel.getSslCipher())
                    .setSshAuthentication(ftpModel.getSshAuthentication())
                    .setSshCipher(ftpModel.getSshCipher())
                    .setSshCompression(ftpModel.getSshCompression())
                    .setSshMac(ftpModel.getSshMac())
                    .setSshIdentityKeyName(ftpModel.getSshIdentityKeyName());

    public static final Function<AwsS3Model, AwsS3Entity> mapperToAwsS3Entity = awsS3Model ->
            new AwsS3Entity()
                    .setIsActive(convertBooleanToString(awsS3Model.getStatus()))
                    .setAccessKey(isNullThrowError.apply(awsS3Model.getAccessKey(), "Access Key").trim())
                    .setBucketName(isNullThrowError.apply(awsS3Model.getBucketName(), "Bucket Name").trim())
                    .setFileName(awsS3Model.getFileName())
                    .setEndpoint(awsS3Model.getEndpoint())
                    .setInMailbox(awsS3Model.getInMailbox())
                    .setSecretKey(isNullThrowError.apply(awsS3Model.getSecretKey(), "Secret Key").trim())
                    .setRegion(awsS3Model.getRegion())
                    .setPoolingIntervalMins(awsS3Model.getPoolingInterval())
                    .setAdapterName(awsS3Model.getAdapterName())
                    .setEndPointUrl(awsS3Model.getEndPointUrl())
                    .setFolderName(awsS3Model.getFolderName())
                    .setQueueName(awsS3Model.getQueueName())
                    .setIsHubInfo(isNotNull(awsS3Model.getInboundConnectionType()) &&
                            isNotNull(awsS3Model.getOutboundConnectionType()) ? awsS3Model.getInboundConnectionType() &&
                            awsS3Model.getOutboundConnectionType() ? "Y" : "N" : "N")
                    .setSourcePath(isNotNull(awsS3Model.getSourcePath()) ? awsS3Model.getSourcePath() : "default")
                    .setFileType(awsS3Model.getFileType())
                    .setInboundConnectionType(isNotNull(awsS3Model.getInboundConnectionType()) ? convertBooleanToString(awsS3Model.getInboundConnectionType()) : "N")
                    .setOutboundConnectionType(isNotNull(awsS3Model.getOutboundConnectionType()) ? convertBooleanToString(awsS3Model.getOutboundConnectionType()) : "N")
                    .setDeleteAfterCollection(convertBooleanToString(awsS3Model.getDeleteAfterCollection()));


    public static final Function<HttpModel, HttpEntity> mapperToHttpEntity = httpModel ->
            new HttpEntity()
                    .setProtocolType(httpModel.getProtocol())
                    .setIsActive(convertBooleanToString(httpModel.getStatus()))
                    .setInMailbox(httpModel.getInMailBox())
                    .setOutboundUrl(httpModel.getOutBoundUrl())
                    .setIsHubInfo(convertBooleanToString(httpModel.getHubInfo()))
                    .setAdapterName(httpModel.getAdapterName())
                    .setCertificate(isNotNull(httpModel.getCertificate()) ? httpModel.getCertificate() : " ")
                    .setPoolingIntervalMins(httpModel.getPoolingInterval());

    public static final Function<MqModel, MqEntity> mapperToMqEntity = mqModel ->
            new MqEntity()
                    .setQueueManager(mqModel.getQueueManager())
                    .setQueueName(mqModel.getQueueName())
                    .setFileType(mqModel.getFileType())
                    .setIsActive(convertBooleanToString(mqModel.getStatus()))
                    .setAdapterName(mqModel.getAdapterName())
                    .setHostName(mqModel.getHostName())
                    .setPort(mqModel.getPort())
                    .setPoolingIntervalMins(mqModel.getPoolingInterval())
                    .setIsHubInfo(convertBooleanToString(mqModel.getHubInfo()))
                    .setUserId(mqModel.getUserId())
                    .setPassword(mqModel.getPassword())
                    .setChannelName(mqModel.getChannelName());

    public static final Function<MailboxModel, MailboxEntity> mapperToMailboxEntity = mailboxModel ->
            new MailboxEntity()
                    .setProtocolType(mailboxModel.getProtocol())
                    .setIsActive(convertBooleanToString(mailboxModel.getStatus()))
                    .setInMailbox(mailboxModel.getInMailBox())
                    .setOutMailbox(mailboxModel.getOutMailBox())
                    .setPoolingIntervalMins(mailboxModel.getPoolingInterval())
                    .setIsHubInfo(convertBooleanToString(mailboxModel.getHubInfo()))
                    .setFilter(mailboxModel.getFilter());

    public static final Function<WebserviceModel, WebserviceEntity> mapperToWebserviceEntity = webserviceModel ->
            new WebserviceEntity()
                    .setInDirectory(webserviceModel.getInMailBox())
                    .setWebserviceName(webserviceModel.getName())
                    .setIsActive(convertBooleanToString(webserviceModel.getStatus()))
                    .setOutboundUrl(webserviceModel.getOutBoundUrl())
                    .setCertificate(webserviceModel.getCertificateId())
                    .setAdapterName(webserviceModel.getAdapterName())
                    .setIsHubInfo(convertBooleanToString(webserviceModel.getHubInfo()))
                    .setPoolingIntervalMins(webserviceModel.getPoolingInterval());

    public static final Function<FileSystemModel, FileSystemEntity> mapperToFileSystemEntity = filesystemModel ->
            new FileSystemEntity()
                    .setUserName(filesystemModel.getUserName())
                    .setPassword(filesystemModel.getPassword())
                    .setInDirectory(filesystemModel.getInDirectory())
                    .setOutDirectory(filesystemModel.getOutDirectory())
                    .setFileType(filesystemModel.getFileType())
                    .setDeleteAfterCollection(convertBooleanToString(filesystemModel.getDeleteAfterCollection()))
                    .setIsActive(convertBooleanToString(filesystemModel.getStatus()))
                    .setIsHubInfo(convertBooleanToString(filesystemModel.getHubInfo()))
                    .setPoolingIntervalMins(filesystemModel.getPoolingInterval())
                    .setFsaAdapter(filesystemModel.getAdapterName());


    public static final Function<RemoteProfileModel, RemoteFtpEntity> mapperToRemoteFtpEntity = remoteFtpModel ->
            new RemoteFtpEntity()
                    .setProtocolType(remoteFtpModel.getProtocol())
                    .setIsActive(convertBooleanToString(remoteFtpModel.getStatus()))
                    .setConnectionType(remoteFtpModel.getConnectionType())
                    .setTransferType(remoteFtpModel.getTransferType())
                    .setFileType(remoteFtpModel.getFileType())
                    .setPortNo(remoteFtpModel.getRemotePort())
                    .setHostName(remoteFtpModel.getRemoteHost())
                    .setInDirectory(remoteFtpModel.getInDirectory())
                    .setOutDirectory(remoteFtpModel.getOutDirectory())
                    .setCertificateId(
                            (isNotNull(remoteFtpModel.getCaCertificateNames()) && !remoteFtpModel.getCaCertificateNames().isEmpty())
                                    ? remoteFtpModel.getCaCertificateNames().stream().map(CommunityManagerNameModel::getName).collect(Collectors.joining(","))
                                    : remoteFtpModel.getCertificateId())
                    .setUserIdentityKey(remoteFtpModel.getUserIdentityKey())
                    .setKnownHostKey(remoteFtpModel.getKnownHostKey())
                    .setNoOfRetries(remoteFtpModel.getNoOfRetries())
                    .setRetryInterval(remoteFtpModel.getRetryInterval())
                    .setPoolingIntervalMins(remoteFtpModel.getPoolingInterval())
                    .setAdapterName(remoteFtpModel.getAdapterName())
                    .setIsHubInfo(convertBooleanToString(remoteFtpModel.getHubInfo()))
                    .setDeleteAfterCollection(convertBooleanToString(remoteFtpModel.getDeleteAfterCollection()))
                    .setUserId(remoteFtpModel.getUserName())
                    .setPassword(remoteFtpModel.getHubInfo() ? remoteFtpModel.getPassword() : "")
                    .setPrfAuthType(remoteFtpModel.getPreferredAuthenticationType())
                    .setAuthUserKeys(isNotNull(remoteFtpModel.getAuthorizedUserKeys()) ? remoteFtpModel.getAuthorizedUserKeys().stream().map(CommunityManagerNameModel::getName).collect(Collectors.joining(",")) : "")
                    .setPwdLastChangeDone(remoteFtpModel.getPwdLastChangeDone())
                    .setMergeUser(convertBooleanToString(remoteFtpModel.isMergeUser()))
                    .setEncryptionStrength(remoteFtpModel.getEncryptionStrength())
                    .setUseImplicitSsl(convertBoolToIMPSSLStr.apply(remoteFtpModel.getUseImplicitSSL()));

    public static final Function<CustomProtocolModel, CustomProtocolEntity> mapperToCustomProtocolEntity = customProtocol ->
            new CustomProtocolEntity()
                    .setProtocolType(customProtocol.getProtocol())
                    .setIsActive(convertBooleanToString(customProtocol.getStatus()))
                    .setAppendSuffixToUsername(isNotNull(customProtocol.getAppendSuffixToUsername()) ? convertBooleanToString(customProtocol.getAppendSuffixToUsername()) : "N")
                    .setAsciiArmor(isNotNull(customProtocol.getAsciiArmor()) ? convertBooleanToString(customProtocol.getAsciiArmor()) : "N")
                    .setAuthenticationHost(customProtocol.getAuthenticationHost())
                    .setAuthenticationType(customProtocol.getAuthenticationType())
                    .setAuthorizedUserKeyName(customProtocol.getAuthorizedUserKeyName())
                    .setCity(customProtocol.getCity())
                    .setCode(customProtocol.getCode())
                    .setCommunity(customProtocol.getCommunity())
                    .setCountryOrRegion(customProtocol.getCountryOrRegion())
                    .setCustomProtocolExtensions(customProtocol.getCustomProtocolExtensions())
                    .setCustomProtocolName(customProtocol.getCustomProtocolName())
                    .setDoesRequireCompressedData((isNotNull(customProtocol.getDoesRequireCompressedData()) ? convertBooleanToString(customProtocol.getDoesRequireCompressedData()) : "N"))
                    .setDoesRequireEncryptedData(isNotNull(customProtocol.getDoesRequireEncryptedData()) ? convertBooleanToString(customProtocol.getDoesRequireEncryptedData()) : "N")
                    .setDoesRequireSignedData(isNotNull(customProtocol.getDoesRequireSignedData()) ? convertBooleanToString(customProtocol.getDoesRequireSignedData()) : "N")
                    .setDoesUseSsh(isNotNull(customProtocol.getDoesUseSSH()) ? convertBooleanToString(customProtocol.getDoesUseSSH()) : "N")
                    .setGivenName(isNotNull(customProtocol.getGivenName()) ? customProtocol.getGivenName() : customProtocol.getProfileName())
                    .setIsInitiatingConsumer(isNotNull(customProtocol.getIsInitiatingConsumer()) ? convertBooleanToString(customProtocol.getIsInitiatingConsumer()) : "N")
                    .setIsInitiatingProducer(isNotNull(customProtocol.getIsInitiatingProducer()) ? convertBooleanToString(customProtocol.getIsInitiatingProducer()) : "N")
                    .setIsListeningConsumer(isNotNull(customProtocol.getIsListeningConsumer()) ? convertBooleanToString(customProtocol.getIsListeningConsumer()) : "N")
                    .setIsListeningProducer(isNotNull(customProtocol.getIsListeningProducer()) ? convertBooleanToString(customProtocol.getIsListeningProducer()) : "N")
                    .setKeyEnabled(isNotNull(customProtocol.getKeyEnabled()) ? convertBooleanToString(customProtocol.getKeyEnabled()) : "N")
                    .setMailbox(customProtocol.getMailbox())
                    .setPassword(customProtocol.getPassword())
                    .setPasswordPolicy(customProtocol.getPasswordPolicy())
                    .setPollingInterval(customProtocol.getPollingInterval())
                    .setPostalCode(customProtocol.getPostalCode())
                    .setPublicKeyId(customProtocol.getPublicKeyID())
                    .setRemoteFilePattern(customProtocol.getRemoteFilePattern())
                    .setSessionTimeout(customProtocol.getSessionTimeout())
                    .setStateOrProvince(customProtocol.getStateOrProvince())
                    .setSurname(isNotNull(customProtocol.getSurname()) ? customProtocol.getSurname() : customProtocol.getProfileName())
                    .setTextMode(isNotNull(customProtocol.getTextMode()) ? convertBooleanToString(customProtocol.getTextMode()) : "N")
                    .setTimeZone(customProtocol.getTimeZone())
                    .setUseGlobalMailbox(isNotNull(customProtocol.getUseGlobalMailbox()) ? convertBooleanToString(customProtocol.getUseGlobalMailbox()) : "N")
                    .setUsername(customProtocol.getUsername());

    public static final Function<RemoteCdModel, RemoteConnectDirectEntity> mapperToRemoteCdEntity = cdModel ->
            new RemoteConnectDirectEntity()
                    .setAdapterName(cdModel.getAdapterName())
                    .setDirectory(cdModel.getDirectory())
                    .setIsActive(convertBooleanToString(cdModel.getStatus()))
                    .setIsHubInfo(convertBooleanToString(cdModel.getHubInfo()))
                    .setIsSsp(convertBooleanToString(cdModel.isSSP()))
                    .setLocalNodeName(cdModel.getLocalNodeName())
                    .setNodeName(cdModel.getNodeName())
                    .setPoolingIntervalMins(cdModel.getPoolingInterval())
                    .setRemoteFileName(cdModel.getRemoteFileName())
                    .setSnodeId(cdModel.getsNodeId())
                    .setSnodeIdPassword(cdModel.getsNodeIdPassword())
                    .setOperatingSystem(cdModel.getOperatingSystem())
                    .setHostName(cdModel.getHostName())
                    .setPort(Integer.toString(cdModel.getPort()))
                    .setCopySisOpts(cdModel.getCopySisOpts())
                    .setCheckPoint(cdModel.getCheckPoint())
                    .setCompressionLevel(cdModel.getCompressionLevel())
                    .setDisposition(cdModel.getDisposition())
                    .setSubmit(cdModel.getSubmit())
                    .setSecure(convertBooleanToString(cdModel.isSecure()))
                    .setRunJob(cdModel.getRunJob())
                    .setRunTask(cdModel.getRunTask())
                    .setSecurityProtocol(cdModel.getSecurityProtocol())
                    .setCaCertificateName(isNotNull(cdModel.getCaCertName()) ? cdModel.getCaCertName().stream().map(CaCertGetModel::getCaCertName).collect(Collectors.joining(",")) : "")
                    .setCipherSuits(isNotNull(cdModel.getCipherSuits()) ? cdModel.getCipherSuits().stream().map(CipherSuiteNameGetModel::getCipherSuiteName).collect(Collectors.joining(",")) : "")
                    .setDcbParam(ProtocolFunctions.getCdMainFrameInfoParam.apply(cdModel, "dcbParam"))
                    .setDnsName(ProtocolFunctions.getCdMainFrameInfoParam.apply(cdModel, "dnsName"))
                    .setUnit(ProtocolFunctions.getCdMainFrameInfoParam.apply(cdModel, "unit"))
                    .setStorageClass(ProtocolFunctions.getCdMainFrameInfoParam.apply(cdModel, "storageClass"))
                    .setSpace(ProtocolFunctions.getCdMainFrameInfoParam.apply(cdModel, "space"))
                    .setOutDirectory(cdModel.getOutDirectory())
                    .setInboundConnectionType((cdModel.getInboundConnectionType() != null) ? convertBooleanToString(cdModel.getInboundConnectionType()) : "N")
                    .setOutboundConnectionType((cdModel.getOutboundConnectionType() != null) ? convertBooleanToString(cdModel.getOutboundConnectionType()) : "N");

    public static final Function<ConnectDirectModel, ConnectDirectEntity> mapperToCdEntity = connectDirectModel ->
            new ConnectDirectEntity()
                    .setAdapterName(connectDirectModel.getAdapterName())
                    .setIsActive(convertBooleanToString(connectDirectModel.getStatus()))
                    .setIsHubInfo(convertBooleanToString(connectDirectModel.getHubInfo()))
                    .setLocalNodeName(connectDirectModel.getLocalNodeName())
                    .setSecurityProtocol(connectDirectModel.getSecurityProtocol())
                    .setCaCertificate(isNotNull(connectDirectModel.getCaCertificate()) ? connectDirectModel.getCaCertificate().stream().map(CaCertGetModel::getCaCertName).collect(Collectors.joining(",")) : "")
                    .setCipherSuits(isNotNull(connectDirectModel.getCipherSuits()) ? connectDirectModel.getCipherSuits().stream().map(CipherSuiteNameGetModel::getCipherSuiteName).collect(Collectors.joining(",")) : "")
                    .setDcb(connectDirectModel.getDcb())
                    .setCodePageFrom(connectDirectModel.getCodePageFrom())
                    .setCodePageTo(connectDirectModel.getCodePageTo())
                    .setLocalXlate(connectDirectModel.getLocalXLate())
                    .setPoolingInterval(connectDirectModel.getPoolingInterval())
                    .setRemoteHost(connectDirectModel.getRemoteHost())
                    .setRemoteNodeName(connectDirectModel.getRemoteNodeName())
                    .setRemoteUser(connectDirectModel.getRemoteUser())
                    .setRemotePassword(connectDirectModel.getRemotePassword())
                    .setRemotePort(connectDirectModel.getRemotePort())
                    .setSecurePlus(isNotNull(connectDirectModel.getSecurePlus()) ? convertBooleanToString(connectDirectModel.getSecurePlus()) : null)
                    .setSecurityProtocol(connectDirectModel.getSecurityProtocol())
                    .setSysOpts(connectDirectModel.getSysOpts())
                    .setTransferType(connectDirectModel.getTransferType());

    private static final BiFunction<RemoteCdModel, String, String> getCdMainFrameInfoParam = (cdModel, param) -> {
        if (cdModel.getOperatingSystem().equalsIgnoreCase("Mainframe(Z/OS)")) {
            switch (param) {
                case "dcbParam":
                    return cdModel.getCdMainFrameModel().getDcbParam();
                case "dnsName":
                    return cdModel.getCdMainFrameModel().getDnsName();
                case "unit":
                    return cdModel.getCdMainFrameModel().getUnit();
                case "storageClass":
                    return cdModel.getCdMainFrameModel().getStorageClass();
                case "space":
                    return cdModel.getCdMainFrameModel().getSpace();
                default:
                    return "";
            }
        }
        return "";
    };

    public static final Function<SapModel, SapEntity> mapperToSapEntity = sapModel ->
            new SapEntity()
                    .setIsActive(convertBooleanToString(sapModel.getStatus()))
                    .setSapRoute(sapModel.getSapRoute())
                    .setIsHubInfo(convertBooleanToString(sapModel.getHubInfo()))
                    .setSapAdapterName(sapModel.getAdapterName());

    public static final Function<EcModel, EcEntity> mapperToEcEntity = ecModel ->
            new EcEntity()
                    .setIsActive(convertBooleanToString(ecModel.getStatus()))
                    .setIsHubInfo(convertBooleanToString(ecModel.getHubInfo()))
                    .setEcProtocol(ecModel.getEcProtocol())
                    .setEcProtocolRef(ecModel.getEcProtocolReference());

    public static final Function<SmtpModel, SmtpEntity> mapperToSmtpEntity = smtpModel ->
            new SmtpEntity()
                    .setName(smtpModel.getName())
                    .setAccessProtocol(smtpModel.getAccessProtocol())
                    .setMailServer(smtpModel.getMailServer())
                    .setMailServerPort(smtpModel.getMailServerPort())
                    .setUserName(smtpModel.getUserName())
                    .setPassword(smtpModel.getPassword())
                    .setConnectionRetries(smtpModel.getConnectionRetries())
                    .setRetryInterval(smtpModel.getRetryInterval())
                    .setMaxMsgsSession(smtpModel.getMaxMsgsSession())
                    .setRemoveInboxMailMsgs(smtpModel.getRemoveMailMsgs())
                    .setSsl(smtpModel.getSsl())
                    .setkeyCertPassPhrase(smtpModel.getKeyCertPassPhrase())
                    .setCipherStrength(smtpModel.getCipherStrength())
                    .setKeyCert(smtpModel.getKeyCert())
                    .setCaCertificates(smtpModel.getCaCertificates())
                    .setUriName(smtpModel.getUriName())
                    .setPoolingInterval(smtpModel.getPoolingInterval());

    public static final Function<OracleEbsModel, OracleEbsEntity> mapperToOracleEBSEntity = oracleEBSModel ->
            new OracleEbsEntity()
                    .setName(oracleEBSModel.getName())
                    .setProtocol(oracleEBSModel.getOrProtocol())
                    .setAutoSendBodRecMsgs(oracleEBSModel.getAutoSendBodRecMsgs())
                    .setBpRecMsgs(oracleEBSModel.getBpRecMsgs())
                    .setUserName(oracleEBSModel.getUserName())
                    .setPassword(oracleEBSModel.getPassword())
                    .setBpUnknownBods(oracleEBSModel.getBpUnknownBods())
                    .setDateTimeOag(oracleEBSModel.getDateTimeOag())
                    .setDirectoryBod(oracleEBSModel.getDirectoryBod())
                    .setHttpEndpoint(oracleEBSModel.getHttpEndpoint())
                    .setNameBod(oracleEBSModel.getNameBod())
                    .setRequestType(oracleEBSModel.getRequestType())
                    .setTpContractSend(oracleEBSModel.getTpContractSend())
                    .setTimeoutBod(oracleEBSModel.getTimeoutBod());

    public static final UnaryOperator<As2Model> applyAs2DefaultValues = as2Model -> {

        as2Model.setResponseTimeout(as2Model.getResponseTimeout() != null ? as2Model.getResponseTimeout() : 5);
        as2Model.setCompressData(isNotNull(as2Model.getCompressData()) ? as2Model.getCompressData().toLowerCase() : "default");
        as2Model.setPayloadType(isNotNull(as2Model.getPayloadType()) ? as2Model.getPayloadType() : "0");
        as2Model.setMimeType(isNotNull(as2Model.getMimeType()) ? as2Model.getMimeType() : "Application");
        as2Model.setMimeSubType(isNotNull(as2Model.getMimeSubType()) ? as2Model.getMimeSubType() : "plain");
        as2Model.setCipherStrength(isNotNull(as2Model.getCipherStrength()) ? as2Model.getCipherStrength() : "ALL");

        as2Model.setSslType(isNotNull(as2Model.getSslType()) ? as2Model.getSslType() : "SSL_NONE");
        as2Model.setPayloadEncryptionAlgorithm(isNotNull(as2Model.getPayloadEncryptionAlgorithm()) ? as2Model.getPayloadEncryptionAlgorithm() : TRIPLE_DES_168_CBC_WITH_PKCS5_PADDING);
        as2Model.setPayloadSecurity(isNotNull(as2Model.getPayloadSecurity()) ? as2Model.getPayloadSecurity() : PLAIN_TEXT);
        as2Model.setEncryptionAlgorithm(isNotNull(as2Model.getEncryptionAlgorithm()) ? as2Model.getEncryptionAlgorithm() : TRIPLE_DES_168_CBC_WITH_PKCS5_PADDING);
        as2Model.setSignatureAlgorithm(isNotNull(as2Model.getSignatureAlgorithm()) ? as2Model.getSignatureAlgorithm() : MD5);

        as2Model.setMdn(as2Model.getMdn() != null ? as2Model.getMdn() : TRUE);
        as2Model.setMdnType(isNotNull(as2Model.getMdnType()) ? as2Model.getMdnType() : "0");
        as2Model.setMdnEncryption(isNotNull(as2Model.getMdnEncryption()) ? as2Model.getMdnEncryption() : "0");

        return as2Model;
    };

    public static final UnaryOperator<As2Model> convertAs2ValuesFromStringToNum = as2Model -> {
        if (!as2Model.getHubInfo()) {
            switch (as2Model.getSignatureAlgorithm()) {
                case MD5:
                    as2Model.setSignatureAlgorithm("0");
                    break;
                case SHA1:
                    as2Model.setSignatureAlgorithm("1");
                    break;
                case SHA256:
                    as2Model.setSignatureAlgorithm("2");
                    break;
                case SHA384:
                    as2Model.setSignatureAlgorithm("3");
                    break;
                case SHA512:
                    as2Model.setSignatureAlgorithm("4");
                    break;
                case SHA_1:
                    as2Model.setSignatureAlgorithm("5");
                    break;
                case SHA_256:
                    as2Model.setSignatureAlgorithm("6");
                    break;
                case SHA_384:
                    as2Model.setSignatureAlgorithm("7");
                    break;
                case SHA_512:
                    as2Model.setSignatureAlgorithm("8");
                    break;
                default:
                    //No Implementation needed.
            }
            switch (as2Model.getMdnEncryption()) {
                case NONE:
                    as2Model.setMdnEncryption("0");
                    break;
                case MD5:
                    as2Model.setMdnEncryption("1");
                    break;
                case SHA1:
                    as2Model.setMdnEncryption("2");
                    break;
                case SHA256:
                    as2Model.setMdnEncryption("3");
                    break;
                case SHA384:
                    as2Model.setMdnEncryption("4");
                    break;
                case SHA512:
                    as2Model.setMdnEncryption("5");
                    break;
                case SHA_1:
                    as2Model.setMdnEncryption("6");
                    break;
                case SHA_256:
                    as2Model.setMdnEncryption("7");
                    break;
                case SHA_384:
                    as2Model.setMdnEncryption("8");
                    break;
                case SHA_512:
                    as2Model.setMdnEncryption("9");
                    break;
                default:
                    //No Implementation needed.
            }
            switch (as2Model.getEncryptionAlgorithm()) {
                case TRIPLE_DES_168_CBC_WITH_PKCS5_PADDING:
                    as2Model.setEncryptionAlgorithm("0");
                    break;
                case BIT_56_DES_CBC_WITH_PKCS5_PADDING:
                    as2Model.setEncryptionAlgorithm("1");
                    break;
                case BIT_128_RC2_CBC_WITH_PKCS5_PADDING:
                    as2Model.setEncryptionAlgorithm("2");
                    break;
                case BIT_40_RC2_CBC_WITH_PKCS5_PADDING:
                    as2Model.setEncryptionAlgorithm("4");
                    break;
                default:
                    //No Implementation needed.
            }

            switch (as2Model.getMdnType()) {
                case SYNCHRONOUS:
                    as2Model.setMdnType("0");
                    break;
                case ASYNCHRONOUS_HTTP:
                    as2Model.setMdnType("1");
                    break;
                case ASYNCHRONOUS_HTTPS:
                    as2Model.setMdnType("3");
                    break;
                case ASYNCHRONOUS_SMTP:
                    as2Model.setMdnType("2");
                    break;
                default:
                    throw internalServerError("mdnType Should match with : Synchronous, Asynchronous HTTP, Asynchronous HTTPS, Asynchronous SMTP");
            }

            switch (as2Model.getPayloadType()) {
                case PLAIN_TEXT:
                    as2Model.setPayloadType("0");
                    break;
                case SIGNED_DETACHED:
                    as2Model.setPayloadType("1");
                    break;
                case ENCRYPTED:
                    as2Model.setPayloadType("3");
                    break;
                case SIGNED_DETACHED_ENCRYPTED:
                    as2Model.setPayloadType("4");
                    break;
                default:
                    //No Implementation needed.
            }
            as2Model.setCompressData(as2Model.getCompressData().toLowerCase());
        }

        return as2Model;
    };
    public static final UnaryOperator<As2Model> convertAs2ValuesFromNumToString = as2Model -> {
        if (!as2Model.getHubInfo()) {
            switch (as2Model.getSignatureAlgorithm()) {
                case ZERO:
                    as2Model.setSignatureAlgorithm(MD5);
                    break;
                case ONE:
                    as2Model.setSignatureAlgorithm(SHA1);
                    break;
                case TWO:
                    as2Model.setSignatureAlgorithm(SHA256);
                    break;
                case THREE:
                    as2Model.setSignatureAlgorithm(SHA384);
                    break;
                case FOUR:
                    as2Model.setSignatureAlgorithm(SHA512);
                    break;
                case FIVE:
                    as2Model.setSignatureAlgorithm(SHA_1);
                    break;
                case SIX:
                    as2Model.setSignatureAlgorithm(SHA_256);
                    break;
                case SEVEN:
                    as2Model.setSignatureAlgorithm(SHA_384);
                    break;
                case EIGHT:
                    as2Model.setSignatureAlgorithm(SHA_512);
                    break;
                default:
                    //No Implementation needed.
            }
            switch (as2Model.getMdnEncryption()) {
                case ZERO:
                    as2Model.setMdnEncryption(NONE);
                    break;
                case ONE:
                    as2Model.setMdnEncryption(MD5);
                    break;
                case TWO:
                    as2Model.setMdnEncryption(SHA1);
                    break;
                case THREE:
                    as2Model.setMdnEncryption(SHA256);
                    break;
                case FOUR:
                    as2Model.setMdnEncryption(SHA384);
                    break;
                case FIVE:
                    as2Model.setMdnEncryption(SHA512);
                    break;
                case SIX:
                    as2Model.setMdnEncryption(SHA_1);
                    break;
                case SEVEN:
                    as2Model.setMdnEncryption(SHA_256);
                    break;
                case EIGHT:
                    as2Model.setMdnEncryption(SHA_384);
                    break;
                case NINE:
                    as2Model.setMdnEncryption(SHA_512);
                    break;
                default:
                    //No Implementation needed.
            }
            switch (as2Model.getEncryptionAlgorithm()) {
                case ZERO:
                    as2Model.setEncryptionAlgorithm(TRIPLE_DES_168_CBC_WITH_PKCS5_PADDING);
                    break;
                case ONE:
                    as2Model.setEncryptionAlgorithm(BIT_56_DES_CBC_WITH_PKCS5_PADDING);
                    break;
                case TWO:
                    as2Model.setEncryptionAlgorithm(BIT_128_RC2_CBC_WITH_PKCS5_PADDING);
                    break;
                case FOUR:
                    as2Model.setEncryptionAlgorithm(BIT_40_RC2_CBC_WITH_PKCS5_PADDING);
                    break;
                default:
                    //No Implementation needed.
            }

            switch (as2Model.getMdnType()) {
                case ZERO:
                    as2Model.setMdnType(SYNCHRONOUS);
                    break;
                case ONE:
                    as2Model.setMdnType(ASYNCHRONOUS_HTTP);
                    break;
                case THREE:
                    as2Model.setMdnType(ASYNCHRONOUS_HTTPS);
                    break;
                case TWO:
                    as2Model.setMdnType(ASYNCHRONOUS_SMTP);
                    break;
                default:
                    //No Implementation needed.
            }

            switch (as2Model.getPayloadType()) {
                case ZERO:
                    as2Model.setPayloadType(PLAIN_TEXT);
                    break;
                case ONE:
                    as2Model.setPayloadType(SIGNED_DETACHED);
                    break;
                case THREE:
                    as2Model.setPayloadType(ENCRYPTED);
                    break;
                case FOUR:
                    as2Model.setPayloadType(SIGNED_DETACHED_ENCRYPTED);
                    break;
                default:
                    //No Implementation needed.
            }
        }

        return as2Model;
    };

    public static final Function<As2Model, As2Entity> mapperToAs2Entity = as2Model -> {

        As2Entity as2Entity = new As2Entity();
        as2Entity.setIsActive(convertBooleanToString(as2Model.getStatus()));
        as2Entity.setAs2Identifier(as2Model.getAs2Identifier());
        as2Entity.setIdentityName(as2Model.getProfileId());
        as2Entity.setProfileName(as2Model.getProfileName());
        as2Entity.setEmailAddress(as2Model.getAs2EmailAddress());
        as2Entity.setExchgCert(as2Model.getExchangeCertificate());
        as2Entity.setSigningCert(as2Model.getSigningCertification());
        as2Entity.setIsHubInfo(convertBooleanToString(as2Model.getHubInfo()));

        if (!as2Model.getHubInfo()) {
            as2Entity.setUrl(as2Model.getEndPoint());
            as2Entity.setSenderId(as2Model.getSenderId());
            as2Entity.setSenderQualifier(as2Model.getSenderQualifier());
            as2Entity.setKeyCertPassphrase(as2Model.getKeyCertificatePassphrase());
            as2Entity.setPayloadSecurity(as2Model.getPayloadSecurity());
            as2Entity.setPayloadEncryptionAlgorithm(as2Model.getPayloadEncryptionAlgorithm());
            as2Entity.setUsername(as2Model.getUsername());
            as2Entity.setPassword(as2Model.getPassword());
            as2Entity.setSslType(as2Model.getSslType());
            as2Entity.setEncryptionAlgorithm(as2Model.getEncryptionAlgorithm());
            as2Entity.setSignatureAlgorithm(as2Model.getSignatureAlgorithm());
            as2Entity.setIsMdnRequired(convertBooleanToString(as2Model.getMdn()));
            as2Entity.setMdnType(as2Model.getMdnType());
            as2Entity.setMdnEncryption(as2Model.getMdnEncryption());
            as2Entity.setReceiptToAddress(as2Model.getReceiptToAddress());
            as2Entity.setResponseTimeout(String.valueOf(as2Model.getResponseTimeout()));
            as2Entity.setPayloadType(String.valueOf(as2Model.getPayloadType()));
            as2Entity.setMimeType(as2Model.getMimeType());
            as2Entity.setMimeSubType(as2Model.getMimeSubType());
            as2Entity.setCipherStrength(as2Model.getCipherStrength());
            as2Entity.setKeyCert(as2Model.getKeyCertification());
            as2Entity.setEncryptionAlgorithm(as2Model.getEncryptionAlgorithm());
            as2Entity.setSignatureAlgorithm(as2Model.getSignatureAlgorithm());
            as2Entity.setCompressData(as2Model.getCompressData());
            as2Entity.setHttpClientAdapter(as2Model.getHttpclientAdapter());
            as2Entity.setCaCert(as2Model.getCaCertificate());
        }
        return as2Entity;
    };

    public static final Function<As2Model, RemoteAs2OrgProfile> mapperToRemoteAs2OrgProfile = as2Model ->
            new RemoteAs2OrgProfile()
                    .setAS2Identifier(as2Model.getProfileId())
                    .setIdentityName(as2Model.getProfileName())
                    .setProfileName(as2Model.getProfileName())
                    .setEmailAddress(as2Model.getEmailId())
                    .setExchangeCert(as2Model.getExchangeCertificate())
                    .setSigningCert(as2Model.getSigningCertification());

    public static final Function<As2Model, RemoteAs2PartnerProfile> mapperToRemoteAs2PartnerProfile = as2Model ->
            //For temp we did this as like below , we should do complete the fun asap
            new RemoteAs2PartnerProfile();

    public static final Function<Boolean, String> convertBooleanToNumber = value -> value ? "1" : "0";

    public static final Predicate<String> convertNumberToBoolean = value -> value.equals("1");

    public static final Function<AzureModel, AzureEntity> mapperToAzureEntity = azureModel ->
            new AzureEntity()
                    .setIsActive(convertBooleanToString(azureModel.getStatus()))
                    .setAuthType(azureModel.getAuthType())
                    .setAccountName(azureModel.getAccountName())
                    .setAccountKey(azureModel.getAccountKey())
                    .setEndpointSuffix(azureModel.getEndpointSuffix())
                    .setEndpoint(azureModel.getEndpoint())
                    .setBucketName(azureModel.getBucketName())
                    .setFolderName(azureModel.getFolderName())
                    .setAdapterName(azureModel.getAdapterName())
                    .setFileName(azureModel.getFileName())
                    .setPoolingIntervalMins(azureModel.getPoolingInterval())
                    .setIsHubInfo(isNotNull(azureModel.getInboundPush()) &&
                            isNotNull(azureModel.getOutboundPull()) ? azureModel.getInboundPush() &&
                            azureModel.getOutboundPull() ? "Y" : "N" : "N")
                    .setInboundPush(isNotNull(azureModel.getInboundPush()) ? convertBooleanToString(azureModel.getInboundPush()) : "N")
                    .setOutboundPull(isNotNull(azureModel.getOutboundPull()) ? convertBooleanToString(azureModel.getOutboundPull()) : "N")
                    .setDeleteAfterCollection(convertBooleanToString(azureModel.getDeleteAfterCollection()));

    public static final Function<GoogleDriveModel, GoogleDriveEntity> mapperToGDriveEntity = googleDriveModel -> new GoogleDriveEntity()
            .setProjectId(googleDriveModel.getProjectId())
            .setInDirectory(googleDriveModel.getInDirectory())
            .setOutDirectory(googleDriveModel.getOutDirectory())
            .setFileType(googleDriveModel.getFileType())
            .setPoolingIntervalMins(googleDriveModel.getPoolingInterval())
            .setIsActive(convertBooleanToString(googleDriveModel.getStatus()))
            .setDeleteAfterCollection(convertBooleanToString(googleDriveModel.getDeleteAfterCollection()));
}
