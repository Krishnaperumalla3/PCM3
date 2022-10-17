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

package com.pe.pcm.protocol;

import com.pe.pcm.enums.Protocol;
import com.pe.pcm.protocol.as2.As2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.pe.pcm.enums.Protocol.findProtocol;

/**
 * @author Chenchu Kiran.
 */
@Service
public class ManageProtocolService {

    private FtpService ftpService;
    private As2Service as2Service;
    private HttpService httpService;
    private MqService mqService;
    private MailboxService mailboxService;
    private SapService sapService;
    private WebserviceService webserviceService;
    private FileSystemService fileSystemService;
    private RemoteFtpService remoteFtpService;
    private EcService ecService;
    private ConnectDirectService connectDirectService;
    private RemoteConnectDirectService remoteConnectDirectService;
    private AwsS3Service awsS3Service;
    private CustomProtocolService customProtocolService;

    @Autowired
    public ManageProtocolService(FtpService ftpService, As2Service as2Service, HttpService httpService, MqService mqService, MailboxService mailboxService,
                                 SapService sapService, WebserviceService webserviceService, FileSystemService fileSystemService, RemoteFtpService remoteFtpService,
                                 EcService ecService, ConnectDirectService connectDirectService, RemoteConnectDirectService remoteConnectDirectService, AwsS3Service awsS3Service) {
        this.ftpService = ftpService;
        this.as2Service = as2Service;
        this.httpService = httpService;
        this.mqService = mqService;
        this.mailboxService = mailboxService;
        this.sapService = sapService;
        this.webserviceService = webserviceService;
        this.fileSystemService = fileSystemService;
        this.remoteFtpService = remoteFtpService;
        this.ecService = ecService;
        this.connectDirectService = connectDirectService;
        this.remoteConnectDirectService = remoteConnectDirectService;
        this.awsS3Service = awsS3Service;
    }


    public String getAs2Identifier(String protocolPkId) {
        return as2Service.get(protocolPkId).getAs2Identifier();
    }

    //Delete Protocol
    public void deleteProtocol(String profileProtocol, String protocolPkId) {
        Protocol protocol = findProtocol(profileProtocol.trim());
        switch (protocol) {
            case FTP:
            case FTPS:
            case SFTP:
                ftpService.delete(protocolPkId);
                break;
            case AS2:
                as2Service.delete(protocolPkId);
                break;
            case HTTP:
            case HTTPS:
                httpService.delete(protocolPkId);
                break;
            case MQ:
                mqService.delete(protocolPkId);
                break;
            case MAILBOX:
                mailboxService.delete(protocolPkId);
                break;
            case SAP:
                sapService.delete(protocolPkId);
                break;
            case WEB_SERVICE:
                webserviceService.delete(protocolPkId);
                break;
            case FILE_SYSTEM:
                fileSystemService.delete(protocolPkId);
                break;
            case SFG_FTP:
            case SFG_FTPS:
            case SFG_SFTP:
                remoteFtpService.delete(protocolPkId);
                break;
            case EXISTING_CONNECTION:
                ecService.delete(protocolPkId);
                break;
            case SFG_CONNECT_DIRECT:
                remoteConnectDirectService.delete(protocolPkId);
                break;
            case CONNECT_DIRECT:
                connectDirectService.delete(protocolPkId);
                break;
            case AWS_S3:
                awsS3Service.delete(protocolPkId);
                break;
            case CUSTOM_PROTOCOL:
                customProtocolService.delete(protocolPkId);
                break;
            default:
                // No Implementation Needed
        }

    }
}
