package com.pe.pcm.file;

import com.pe.pcm.common.CommunityManagerKeyValueModel;
import com.pe.pcm.enums.Protocol;
import com.pe.pcm.miscellaneous.UserUtilityService;
import com.pe.pcm.partner.FilesModel;
import com.pe.pcm.partner.PartnerService;
import com.pe.pcm.partner.entity.PartnerEntity;
import com.pe.pcm.properties.DemoSiProperties;
import com.pe.pcm.protocol.*;
import com.pe.pcm.reports.FileTransferService;
import com.pe.pcm.reports.entity.TransferInfoEntity;
import com.pe.pcm.sterling.mailbox.SterlingMailboxService;
import com.pe.pcm.utils.FileInfoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static com.pe.pcm.utils.PCMConstants.DOWNLOAD;
import static com.pe.pcm.utils.PCMConstants.SUCCESS;
import static java.lang.Boolean.TRUE;

/**
 * @author Shameer.v.
 */
@Service
public class PartnerMailBoxService {

    private static final String RESOURCE_MB_PER_SQL = "SELECT RESOURCE_ID FROM YFS_RESOURCE WHERE RESOURCE_TYPE = '1' AND " +
            "RESOURCE_KEY IN  (SELECT RESOURCE_KEY  FROM  YFS_RESOURCE_PERMISSION " +
            "WHERE USER_KEY = (SELECT USER_KEY FROM YFS_USER WHERE USERNAME  = ? ))";

    private static final String MB_MSG_AVI_SQL = "SELECT MESSAGE_NAME, MESSAGE_SIZE FROM MBX_MESSAGE MM, MBX_EXTRACT_COUNT MC" +
            " WHERE MM.MESSAGE_ID = MC.MESSAGE_ID AND MC.EXTRACTABLE_COUNT = 1 AND MM.MAILBOX_ID = (SELECT MAILBOX_ID  FROM MBX_MAILBOX WHERE PATH = ? )";

    private final FtpService ftpService;
    private final HttpService httpService;
    private final AwsS3Service awsS3Service;
    private final JdbcTemplate jdbcTemplate;
    private final MailboxService mailboxService;
    private final PartnerService partnerService;
    private final DemoSiProperties demoSiProperties;
    private final RemoteFtpService remoteFtpService;
    private final ManageFileService manageFileService;
    private final UserUtilityService userUtilityService;
    private final FileTransferService fileTransferService;
    private final SterlingMailboxService sterlingMailboxService;
    private final RemoteConnectDirectService remoteConnectDirectService;


    @Autowired
    public PartnerMailBoxService(UserUtilityService userUtilityService,
                                 PartnerService partnerService,
                                 FtpService ftpService,
                                 HttpService httpService,
                                 MailboxService mailboxService,
                                 RemoteFtpService remoteFtpService,
                                 RemoteConnectDirectService remoteConnectDirectService,
                                 AwsS3Service awsS3Service,
                                 JdbcTemplate jdbcTemplate,
                                 ManageFileService manageFileService,
                                 DemoSiProperties demoSiProperties,
                                 FileTransferService fileTransferService, SterlingMailboxService sterlingMailboxService) {
        this.ftpService = ftpService;
        this.userUtilityService = userUtilityService;
        this.partnerService = partnerService;
        this.httpService = httpService;
        this.mailboxService = mailboxService;
        this.remoteFtpService = remoteFtpService;
        this.remoteConnectDirectService = remoteConnectDirectService;
        this.awsS3Service = awsS3Service;
        this.jdbcTemplate = jdbcTemplate;
        this.manageFileService = manageFileService;
        this.demoSiProperties = demoSiProperties;
        this.fileTransferService = fileTransferService;
        this.sterlingMailboxService = sterlingMailboxService;
    }

    public SortedSet<String> getAllMailboxesByCurrentUser() {
        SortedSet<String> mailboxes = new TreeSet<>();
        List<String> parentList = partnerService.getPartnersListAssignedToUserMap(userUtilityService.getUserOrRole(TRUE))
                .stream()
                .map(CommunityManagerKeyValueModel::getKey)
                .collect(Collectors.toList());
        partnerService.findAllByPkIdIn(parentList).stream()
                .map(PartnerEntity::getTpProtocol).distinct()
                .collect(Collectors.toList()).
                forEach(protocolStr -> {
                    Protocol protocol = Protocol.findProtocol(protocolStr);
                    switch (protocol) {
                        case FTP:
                        case FTPS:
                        case SFTP:
                            ftpService.findAllBySubscriberId(parentList)
                                    .forEach(ftpEntity -> mailboxes.add(ftpEntity.getInDirectory()));
                            break;
                        case HTTP:
                        case HTTPS:
                            httpService.findAllBySubscriberId(parentList)
                                    .forEach(httpEntity -> mailboxes.add(httpEntity.getInMailbox()));
                            break;
                        case MAILBOX:
                            mailboxService.findAllBySubscriberId(parentList)
                                    .forEach(mailboxEntity -> mailboxes.add(mailboxEntity.getInMailbox()));
                            break;
                        case SFG_FTP:
                        case SFG_FTPS:
                        case SFG_SFTP:
                            remoteFtpService.findAllBySubscriberId(parentList)
                                    .forEach(remoteFtpEntity -> mailboxes.add(remoteFtpEntity.getInDirectory()));
                            break;
                        case SFG_CONNECT_DIRECT:
                            remoteConnectDirectService.findAllBySubscriberId(parentList)
                                    .forEach(remoteConnectDirectEntity -> mailboxes.add(remoteConnectDirectEntity.getDirectory()));
                            break;
                        case AWS_S3:
                            awsS3Service.findAllBySubscriberId(parentList)
                                    .forEach(awsS3Entity -> mailboxes.add(awsS3Entity.getInMailbox()));
                            break;
                        default:
                            //no need to anything
                    }
                });
        List<String> siUserMailboxes = addResourceMailboxesByUser(userUtilityService.getUserOrRole(TRUE));
        if (siUserMailboxes != null && !siUserMailboxes.isEmpty()) {
            mailboxes.addAll(siUserMailboxes);
        }
        return mailboxes;
    }

    public List<String> addResourceMailboxesByUser(String userName) {
        return jdbcTemplate.query(RESOURCE_MB_PER_SQL,
                ps -> ps.setString(1, userName),
                (rs, rowNum) -> StringUtils.hasText(rs.getString("RESOURCE_ID")) ? rs.getString("RESOURCE_ID").replace(".mbx", "") : "");
    }

    public List<FilesModel> getAvailableFilesInMailbox(String mailBoxId) {
        return jdbcTemplate.query(MB_MSG_AVI_SQL, ps -> ps.setString(1, mailBoxId),
                (rs, rowNum) ->
                        new FilesModel(
                                rs.getString("MESSAGE_NAME"),
                                rs.getString("MESSAGE_SIZE")
                        ));
    }

    public FileSystemResource loadFileAsFsResourceForDownload(String mailbox, String fileName) throws IOException {
        FileSystemResource file = manageFileService.fileDownloadWithSFTP(new FileInfoModel()
                        .setHost(demoSiProperties.getHost())
                        .setPort(demoSiProperties.getPort())
                        .setUserName(demoSiProperties.getUsername())
                        .setPassword(demoSiProperties.getCmks()),
                mailbox + "/" + fileName);
        String partnerName = getMailboxPartnerName(mailbox);
        Long mailboxId = sterlingMailboxService.getMailboxId(mailbox);
        String user = userUtilityService.getUserOrRole(TRUE);
        fileTransferService.save(new TransferInfoEntity()
                .setPartner(partnerName)
                .setFlowinout(DOWNLOAD)
                .setSrcfilename(fileName)
                .setXrefName(mailbox)
                .setSenderid(user)
                .setCorebpid(String.valueOf(mailboxId))
                .setSrcFileSize(file.contentLength())
                .setStatus(SUCCESS));
        fileTransferService.saveTransferInfoD(
                mailbox,
                mailboxId,
                DOWNLOAD,
                fileName,
                file.contentLength(),
                "File " + fileName + " Downloaded from " + mailbox + " By user: " + user
        );
        return file;
    }

    // Currently, I am only checking in
    private String getMailboxPartnerName(String mailbox) {
        String subscriberId = mailboxService.findFirstByInMailboxOrOutMailbox(mailbox, mailbox).getSubscriberId();
        if (StringUtils.hasText(subscriberId)) {
            return partnerService.findPartnerById(subscriberId).getTpName();
        }
        subscriberId = remoteFtpService.findFirstByInMailboxOrOutMailbox(mailbox, mailbox).getInDirectory();
        if (StringUtils.hasText(subscriberId)) {
            return partnerService.findPartnerById(subscriberId).getTpName();
        }
        return null;
    }


}
