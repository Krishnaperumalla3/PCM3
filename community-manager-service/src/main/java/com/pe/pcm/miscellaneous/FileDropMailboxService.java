package com.pe.pcm.miscellaneous;

import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.file.ManageFileService;
import com.pe.pcm.partner.ManagePartnerService;
import com.pe.pcm.properties.DemoSiProperties;
import com.pe.pcm.protocol.MailboxModel;
import com.pe.pcm.protocol.RemoteProfileModel;
import com.pe.pcm.reports.TransInfoDService;
import com.pe.pcm.reports.entity.TransferInfoEntity;
import com.pe.pcm.utils.FileInfoModel;
import com.pe.pcm.workflow.PartnerInfoModel;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static com.pe.pcm.enums.Protocol.findProtocol;
import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;

/**
 * @author Kiran Reddy.
 */
@Service
public class FileDropMailboxService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileDropMailboxService.class);

    private final DemoSiProperties demoSiProperties;
    private final ManageFileService manageFileService;
    private final ManagePartnerService managePartnerService;

    private final TransInfoDService transInfoDService;

    @Autowired
    public FileDropMailboxService(DemoSiProperties demoSiProperties, ManageFileService manageFileService, ManagePartnerService managePartnerService, TransInfoDService transInfoDService) {
        this.demoSiProperties = demoSiProperties;
        this.manageFileService = manageFileService;
        this.managePartnerService = managePartnerService;
        this.transInfoDService = transInfoDService;
    }

    @Async
    public void uploadFiles(String partnerPkId, File[] files, String[] fileNames) {

        if (files != null) {
            for (int index = 0; index < files.length; index++) {
                File file = files[index];
                String mailbox = "";
                PartnerInfoModel<?> partnerModel = managePartnerService.getPartner(partnerPkId);
                switch (findProtocol(partnerModel.getProtocol())) {
                    case FTP:
                    case FTPS:
                    case SFTP:
                        //TODO
                        break;
                    case SFG_FTP:
                    case SFG_FTPS:
                    case SFG_SFTP:
                        LOGGER.info("B2Bi Profile");
                        mailbox = ((RemoteProfileModel) partnerModel.getPartner()).getInDirectory();
                        break;
                    case MAILBOX:
                        LOGGER.info("Mailbox Profile");
                        MailboxModel mailboxModel = (MailboxModel) partnerModel.getPartner();
                        mailbox = mailboxModel.getInMailBox();
                        break;
                    default:
                        // it can be empty
                        break;
                }
                try {
                    if (StringUtils.hasText(mailbox)) {
                        manageFileService.fileDropWithSFTP(
                                new FileInfoModel()
                                        .setFilePath(mailbox)
                                        .setHost(demoSiProperties.getHost())
                                        .setPort(demoSiProperties.getPort())
                                        .setUserName(demoSiProperties.getUsername())
                                        .setPassword(demoSiProperties.getCmks())
                                        .setFileName(fileNames[index]),
                                FileUtils.openInputStream(file));
                        Files.delete(file.toPath());
                        LOGGER.info("Temp file deleted");
                        //TODO FIle activity should be loaded into DB, not regular activity , this should be user level activity
                    } else {
                        LOGGER.info("Partner's mailbox is empty.");
                    }
                } catch (CommunityManagerServiceException e) {
                    //TODO delete files needs to handle
                    //TODO need to send a mail to customer
                    throw internalServerError(e.getErrorMessage());
                } catch (IOException io) {
                    //TODO delete files needs to handle
                    //TODO need to send a mail to customer
                    throw internalServerError(io.getMessage());
                }
            }
            //TODO Need to send a mail to customer
        }
    }

    //TODO: Here we r only handling Partner upload , needs to implement application file upload, also Asynce implementation
    @Async
    @Transactional
    public void fileUpload(TransferInfoEntity transferInfoEntity, String type, File file, String fileName) {
        String filePath;
        if (type.equals("source")) {
            // Partner name
        } else if (type.equals("destination")) {
            // Application Name
        } else {
            throw internalServerError("Please provide the required type as source/destination");
        }
        String mailbox = "";
        PartnerInfoModel<?> partnerModel = managePartnerService.getPartner(transferInfoEntity.getPartner());
        switch (findProtocol(partnerModel.getProtocol())) {
            case FTP:
            case FTPS:
            case SFTP:
                //todo
                break;
            case SFG_FTP:
            case SFG_FTPS:
            case SFG_SFTP:
                //TODO First we need to check is mailbox is available in SI or not
                // , if available then we need to process this
                LOGGER.info("B2Bi Profile");
                mailbox = ((RemoteProfileModel) partnerModel.getPartner()).getInDirectory();
                break;
            case MAILBOX:
                LOGGER.info("Mailbox Profile");
                MailboxModel mailboxModel = (MailboxModel) partnerModel.getPartner();
                mailbox = mailboxModel.getInMailBox();
                break;
            default:
                // it can be empty
                break;
        }

        try {
            if (StringUtils.hasText(mailbox)) {
                manageFileService.fileDropWithSFTP(
                        new FileInfoModel()
                                .setFilePath(mailbox)
                                .setHost(demoSiProperties.getHost())
                                .setPort(demoSiProperties.getPort())
                                .setUserName(demoSiProperties.getUsername())
                                .setPassword(demoSiProperties.getCmks())
                                .setFileName(fileName),
                        FileUtils.openInputStream(file));
                FileUtils.forceDelete(file);
                LOGGER.info("Temp file deleted");
                transInfoDService.save(transferInfoEntity.getCorebpid(), String.valueOf(transferInfoEntity.getSeqid()), "File " + fileName + " dropped into " + mailbox);
            } else {
                LOGGER.info("Partner's mailbox is empty.");
            }
        } catch (CommunityManagerServiceException e) {
            throw internalServerError(e.getErrorMessage());
        } catch (IOException io) {
            throw internalServerError(io.getMessage());
        }

    }

}
