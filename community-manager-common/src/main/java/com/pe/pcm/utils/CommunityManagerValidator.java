/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc.
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

package com.pe.pcm.utils;

import com.pe.pcm.common.CommunityManagerNameModel;
import com.pe.pcm.enums.Protocol;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.protocol.As2Model;
import com.pe.pcm.protocol.MailboxModel;
import com.pe.pcm.protocol.RemoteCdModel;
import com.pe.pcm.protocol.RemoteProfileModel;

import java.util.Optional;

import static com.pe.pcm.enums.Protocol.SFG_SFTP;
import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;
import static com.pe.pcm.utils.CommonFunctions.isNullThrowError;
import static com.pe.pcm.utils.CommonFunctions.throwIfNullOrEmpty;
import static com.pe.pcm.utils.PCMConstants.PASSWORD;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * @author Chenchu Kiran Reddy.
 */
public class CommunityManagerValidator {

    private CommunityManagerValidator() {
    }

    public static void validate(MailboxModel mailboxModel, boolean isPartner) {
        if (isPartner) {
            isNullThrowError.apply(mailboxModel.getInMailBox(), "inMailBox");
        } else {
            isNullThrowError.apply(mailboxModel.getOutMailBox(), "outMailBox");
        }
    }

    public static void validate(As2Model as2Model) {
        isNullThrowError.apply(as2Model.getAs2Identifier(), "as2Identifier");
        if (as2Model.getHubInfo()) {
            isNullThrowError.apply(as2Model.getExchangeCertificate(), "exchangeCertificate");
            isNullThrowError.apply(as2Model.getSigningCertification(), "signingCertificate");
        } else {
            isNullThrowError.apply(as2Model.getEndPoint(), "endPoint");
            throwIfNullOrEmpty(as2Model.getResponseTimeout(), "responseTimeout");
            isNullThrowError.apply(as2Model.getHttpclientAdapter(), "httpClientAdapter");
            isNullThrowError.apply(as2Model.getCompressData(), "compressData");
            isNullThrowError.apply(as2Model.getPayloadType(), "payLoadType");
            isNullThrowError.apply(as2Model.getMimeType(), "mimeType");
            isNullThrowError.apply(as2Model.getMimeSubType(), "mimeSubType");
            isNullThrowError.apply(as2Model.getCipherStrength(), "cipherStrength");
            isNullThrowError.apply(as2Model.getSigningCertification(), "signingCertification");
            isNullThrowError.apply(as2Model.getSslType(), "sslType");
            if (as2Model.getSslType().equalsIgnoreCase("SSL_MUST")) {
                isNullThrowError.apply(as2Model.getCaCertificate(), "caCertificate");
            }
            isNullThrowError.apply(as2Model.getPayloadSecurity(), "payloadSecurity");
            isNullThrowError.apply(as2Model.getEncryptionAlgorithm(), "encryptionAlgorithm");
            isNullThrowError.apply(as2Model.getSignatureAlgorithm(), "signingAlgorithm");
            throwIfNullOrEmpty(as2Model.getMdn(), "mdn");
            isNullThrowError.apply(as2Model.getMdnType(), "mdnType");
            isNullThrowError.apply(as2Model.getMdnEncryption(), "mdnEncryption");
        }
    }

    public static void validate(RemoteProfileModel remoteProfileModel, boolean isPartner) {
        isNullThrowError.apply(remoteProfileModel.getProtocol(), "protocol");
        Protocol protocol = Optional.ofNullable(Protocol.findProtocol(remoteProfileModel.getProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);
        if (isPartner) {
            isNullThrowError.apply(remoteProfileModel.getInDirectory(), "inDirectory");
        } else {
            isNullThrowError.apply(remoteProfileModel.getOutDirectory(), "outDirectory");
        }
        if (remoteProfileModel.getHubInfo()) {
            isNullThrowError.apply(remoteProfileModel.getTransferType(), "transferType");
            isNullThrowError.apply(remoteProfileModel.getUserName(), "userName");
            isNullThrowError.apply(remoteProfileModel.getPassword(), PASSWORD);
            if (protocol == SFG_SFTP) {
                String authType = isNullThrowError.apply(remoteProfileModel.getPreferredAuthenticationType(), "preferredAuthenticationType");
                if (authType.equalsIgnoreCase("PUBLIC_KEY") || authType.equalsIgnoreCase("PUBLIC KEY") || authType.equalsIgnoreCase("PUBLICKEY")) {
                    if (remoteProfileModel.getAuthorizedUserKeys().stream().map(CommunityManagerNameModel::getName).count() == 0) {
                        throw new CommunityManagerServiceException(BAD_REQUEST.value(), "authorizedUserKeys should not be Null/Empty.");
                    }
                } else if (!authType.equals(PASSWORD)) {
                    throw internalServerError("preferredAuthenticationType should match with (CaseInsensitive) : ['PASSWORD', 'PUBLIC_KEY', 'PUBLIC KEY'");
                }
            }
        } else {
            isNullThrowError.apply(remoteProfileModel.getRemoteHost(), "remoteHost");
            isNullThrowError.apply(remoteProfileModel.getRemotePort(), "remotePort");
            isNullThrowError.apply(remoteProfileModel.getUserName(), "userName");
            switch (protocol) {
                case SFG_FTP:
                    isNullThrowError.apply(remoteProfileModel.getPassword(), PASSWORD);
                    isNullThrowError.apply(remoteProfileModel.getConnectionType(), "connectionType");
                    isNullThrowError.apply(remoteProfileModel.getNoOfRetries(), "noOfRetries");
                    isNullThrowError.apply(remoteProfileModel.getRetryInterval(), "retryInterval");
                    break;
                case SFG_FTPS:
                    isNullThrowError.apply(remoteProfileModel.getPassword(), PASSWORD);
                    isNullThrowError.apply(remoteProfileModel.getConnectionType(), "connectionType");
                    isNullThrowError.apply(remoteProfileModel.getEncryptionStrength(), "encryptionStrength");
                    isNullThrowError.apply(remoteProfileModel.getNoOfRetries(), "noOfRetries");
                    isNullThrowError.apply(remoteProfileModel.getRetryInterval(), "retryInterval");
                    if (remoteProfileModel.getCaCertificateNames().stream().map(CommunityManagerNameModel::getName).count() == 0) {
                        throw new CommunityManagerServiceException(BAD_REQUEST.value(), " caCertificateNames should not be Null/Empty.");
                    }
                    break;
                case SFG_SFTP:
                    String authType = isNullThrowError.apply(remoteProfileModel.getPreferredAuthenticationType(), "preferredAuthenticationType");
                    if (authType.equalsIgnoreCase(PASSWORD)) {
                        isNullThrowError.apply(remoteProfileModel.getPassword(), PASSWORD);
                    } else if (authType.equalsIgnoreCase("PUBLIC_KEY") || authType.equalsIgnoreCase("PUBLIC KEY") || authType.equalsIgnoreCase("PUBLICKEY")) {
                        isNullThrowError.apply(remoteProfileModel.getUserIdentityKey(), "userIdentityKey");
                        if (remoteProfileModel.getKnownHostKeyNames().stream().map(CommunityManagerNameModel::getName).count() == 0) {
                            throw new CommunityManagerServiceException(BAD_REQUEST.value(), " knownHostKeyNames should not be Null/Empty.");
                        }
                    } else {
                        throw internalServerError("preferredAuthenticationType should match with (CaseInsensitive) : ['PASSWORD', 'PUBLIC_KEY', 'PUBLIC KEY'");
                    }
                    break;
                default:
                    break;
            }
        }
        isNullThrowError.apply(remoteProfileModel.getPoolingInterval(), "poolingIntervalName");
        isNullThrowError.apply(remoteProfileModel.getAdapterName(), "adapterName");
    }

    public static void validate(RemoteCdModel remoteCdModel) {
        //TODO
        if (remoteCdModel.getCaCertName().isEmpty()) {
            throw new CommunityManagerServiceException(BAD_REQUEST.value(), "caCertName should not be Null/Empty.");
        } else {
            isNullThrowError.apply(remoteCdModel.getCaCertName().get(0).getCaCertName(), "caCertName");
        }

        if (remoteCdModel.getCipherSuits().isEmpty()) {
            throw new CommunityManagerServiceException(BAD_REQUEST.value(), "cipherSuits should not be Null/Empty.");
        } else {
            isNullThrowError.apply(remoteCdModel.getCipherSuits().get(0).getCipherSuiteName(), "cipherSuits");
        }
    }
}
