package com.pe.pcm.application;


import com.pe.pcm.activity.ActivityHistoryService;
import com.pe.pcm.application.entity.AppActivityHistoryEntity;
import com.pe.pcm.application.entity.ApplicationEntity;
import com.pe.pcm.application.sfg.RemoteConnectDirectApplicationService;
import com.pe.pcm.application.sfg.RemoteFtpApplicationService;
import com.pe.pcm.application.sterling.SterlingAs2ApplicationService;
import com.pe.pcm.b2b.other.CaCertGetModel;
import com.pe.pcm.b2b.other.CipherSuiteNameGetModel;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.protocol.*;
import com.pe.pcm.workflow.ApplicationInfoModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;
import static com.pe.pcm.utils.CommonFunctions.convertStringToBoolean;
import static com.pe.pcm.utils.CommonFunctions.isNotNull;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ManageApplicationServiceTest {

    @Mock
    private ApplicationService applicationService;
    @Mock
    private FtpApplicationService ftpApplicationService;
    @Mock
    private MailboxApplicationService mailboxApplicationService;
    @Mock
    private GoogleDriveApplicationService googleDriveApplicationService;
    @Mock
    private HttpApplicationService httpApplicationService;
    @Mock
    private SapApplicationService sapApplicationService;
    @Mock
    private RemoteFtpApplicationService remoteFtpApplicationService;
    @Mock
    private RemoteConnectDirectApplicationService remoteConnectDirectApplicationService;
    @Mock
    private FileSystemApplicationService fileSystemApplicationService;
    @Mock
    private MqApplicationService mqApplicationService;
    @Mock
    private WsApplicationService wsApplicationService;
    @Mock
    private ActivityHistoryService activityHistoryService;
    @Mock
    private AwsS3ApplicationService awsS3ApplicationService;
    @Mock
    private ConnectDirectApplicationService connectDirectApplicationService;
    @Mock
    private SterlingAs2ApplicationService sterlingAs2ApplicationService;
    //@InjectMocks
    private ManageApplicationService manageApplicationService;

    @BeforeEach
    void inIt() {
        manageApplicationService = new ManageApplicationService(applicationService, ftpApplicationService, mailboxApplicationService,
                httpApplicationService, sapApplicationService, remoteFtpApplicationService, remoteConnectDirectApplicationService,
                fileSystemApplicationService, mqApplicationService, wsApplicationService, awsS3ApplicationService, activityHistoryService
                , connectDirectApplicationService, googleDriveApplicationService, sterlingAs2ApplicationService);
    }

    @Test
    @DisplayName("Save Application")
    void testSaveApplication() {
        manageApplicationService.saveApplication(getApplicationInfoModelForFtp());
        Mockito.verify(ftpApplicationService, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Get App History")
    void testGetHistory() {
        Mockito.when(activityHistoryService.getApplicationHistory(Mockito.anyString(), Mockito.any())).thenReturn(generateList1());
        manageApplicationService.getHistory("123456", Pageable.unpaged());
        Mockito.verify(activityHistoryService, Mockito.times(1)).getApplicationHistory(Mockito.anyString(), Mockito.any());
    }

    @Test
    @DisplayName("Create a Manage Application by passing a known HTTP protocol")
    void testCreate1() {
        manageApplicationService.saveApplication(getApplicationInfoModelForHttp());
        Mockito.verify(httpApplicationService, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Create a Manage Application by passing a known MQ protocol")
    void testCreate2() {
        manageApplicationService.saveApplication(getApplicationInfoModelForMq());
        Mockito.verify(mqApplicationService, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Create a Manage Application by passing a known Mailbox protocol")
    void testCreate3() {
        manageApplicationService.saveApplication(getApplicationInfoModelForMailbox());
        Mockito.verify(mailboxApplicationService, Mockito.times(1)).save(Mockito.any());
    }


    @Test
    @DisplayName("Create a Manage Application by passing a known SAP protocol")
    void testCreate4() {
        manageApplicationService.saveApplication(getApplicationInfoModelForSAP());
        Mockito.verify(sapApplicationService, Mockito.times(1)).save(Mockito.any());
    }


    @Test
    @DisplayName("Create a Manage Application by passing a known Webservice protocol")
    void testCreate5() {
        manageApplicationService.saveApplication(getApplicationInfoModelForWebService());
        Mockito.verify(wsApplicationService, Mockito.times(1)).save(Mockito.any());
    }


    @Test
    @DisplayName("Create a Manage Application by passing a known FileSystem protocol")
    void testCreate6() {
        manageApplicationService.saveApplication(getApplicationInfoModelForFileSystem());
        Mockito.verify(fileSystemApplicationService, Mockito.times(1)).save(Mockito.any());
    }


    @Test
    @DisplayName("Create a Manage Application by passing a known SFGFTP protocol")
    void testCreate7() {
        manageApplicationService.saveApplication(getApplicationInfoModelForSfgFtp());
        Mockito.verify(remoteFtpApplicationService, Mockito.times(1)).save(Mockito.any());
    }


    @Test
    @DisplayName("Create a Manage Application by passing a known ConnectDirect protocol")
    void testCreate8() {
        manageApplicationService.saveApplication(getApplicationInfoModelForConnectDirect());
        Mockito.verify(remoteConnectDirectApplicationService, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Get ftp Application")
    void testGet() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(applicationService.get(Mockito.anyString())).thenThrow(notFound("Application"));
            String pkId = "123456";
            manageApplicationService.getApplication(pkId);
        });
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(ftpApplicationService, Mockito.never()).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Get http Application ")
    void testGet2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(applicationService.get(Mockito.anyString())).thenThrow(notFound("Application"));
            String pkId = "123456";
            manageApplicationService.getApplication(pkId);
        });
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(httpApplicationService, Mockito.never()).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Get FTP Application with passing an valid data")
    void testGetFtpApplication() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity("FTP"));
        Mockito.when(ftpApplicationService.get(Mockito.anyString())).thenReturn(getFtpModel("FTP"));
        String pkId = "123456";
        manageApplicationService.getApplication(pkId);
        Mockito.verify(ftpApplicationService, Mockito.times(1)).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Get Http Application with passing an valid data")
    void testGetHttpApplication() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity("HTTP"));
        Mockito.when(httpApplicationService.get(Mockito.anyString())).thenReturn(getHttpModel());
        String pkId = "123456";
        manageApplicationService.getApplication(pkId);
        Mockito.verify(httpApplicationService, Mockito.times(1)).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Get MQ Application with passing an valid data")
    void testGetMqApplication() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity("MQ"));
        Mockito.when(mqApplicationService.get(Mockito.anyString())).thenReturn(generateMqModel());
        String pkId = "123456";
        manageApplicationService.getApplication(pkId);
        Mockito.verify(mqApplicationService, Mockito.times(1)).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Get Mailbox Application with passing an valid data")
    void testGetMailboxApplication() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity("Mailbox"));
        Mockito.when(mailboxApplicationService.get(Mockito.anyString())).thenReturn(generateMailBoxModel());
        String pkId = "123456";
        manageApplicationService.getApplication(pkId);
        Mockito.verify(mailboxApplicationService, Mockito.times(1)).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Get SAP Application with passing an valid data")
    void testGetSAPApplication() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity("SAP"));
        Mockito.when(sapApplicationService.get(Mockito.anyString())).thenReturn(generateSapModel());
        String pkId = "123456";
        manageApplicationService.getApplication(pkId);
        Mockito.verify(sapApplicationService, Mockito.times(1)).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Get WebService Application with passing an valid data")
    void testGetWebServiceApplication() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity("Webservice"));
        Mockito.when(wsApplicationService.get(Mockito.anyString())).thenReturn(generateWebserviceModel());
        String pkId = "123456";
        manageApplicationService.getApplication(pkId);
        Mockito.verify(wsApplicationService, Mockito.times(1)).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Get FileSystem Application with passing an valid data")
    void testGetFileSystemApplication() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity("FileSystem"));
        Mockito.when(fileSystemApplicationService.get(Mockito.anyString())).thenReturn(generateFileSystemModel());
        String pkId = "123456";
        manageApplicationService.getApplication(pkId);
        Mockito.verify(fileSystemApplicationService, Mockito.times(1)).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Get SFG Application with passing an valid data")
    void testGetSFGApplication() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity("SFGFTP"));
        Mockito.when(remoteFtpApplicationService.get(Mockito.anyString())).thenReturn(generateRemoteFtpModel());
        String pkId = "123456";
        manageApplicationService.getApplication(pkId);
        Mockito.verify(remoteFtpApplicationService, Mockito.times(1)).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Get Connect:Direct Application with passing an valid data")
    void testGetConnectDirectApplication() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity("SFG_CONNECT_DIRECT"));
        Mockito.when(remoteConnectDirectApplicationService.get(Mockito.anyString())).thenReturn(generateCdModel());
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity("SFG_CONNECT_DIRECT"));
        Mockito.when(remoteConnectDirectApplicationService.get(Mockito.anyString())).thenReturn(generateCdModel());
        String pkId = "123456";
        manageApplicationService.getApplication(pkId);
        Mockito.verify(remoteConnectDirectApplicationService, Mockito.times(1)).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Get AS2 Application with passing an valid data")
    void testGetAs2Application() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity("AS2"));
        String pkId = "123456";
        manageApplicationService.getApplication(pkId);
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
    }

    /*@Test
    @DisplayName("Get Default Application with passing an valid data")
    void testGetDefaultApplication() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity("Default"));
        String pkId = "123456";
        manageApplicationService.getApplication(pkId);
        Mockito.verify(applicationService,Mockito.times(1)).get(Mockito.anyString());
    }*/

    @Test
    @DisplayName("Get a Mq Application")
    void testGet4() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(applicationService.get(Mockito.anyString())).thenThrow(notFound("Application"));
            String pkId = "123456";
            manageApplicationService.getApplication(pkId);
        });
        Mockito.verify(mqApplicationService, Mockito.never()).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Get a Ftp Application")
    void testGet5() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(applicationService.get(Mockito.anyString())).thenThrow(notFound("Application"));
            String pkId = "123456";
            manageApplicationService.getApplication(pkId);
        });
        Mockito.verify(ftpApplicationService, Mockito.never()).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Get a Cd Application ")
    void testGet6() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(applicationService.get(Mockito.anyString())).thenThrow(notFound("Application"));
            String pkId = "123456";
            manageApplicationService.getApplication(pkId);
        });
        Mockito.verify(remoteConnectDirectApplicationService, Mockito.never()).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Get a Remote Ftp Application")
    void testGet7() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(applicationService.get(Mockito.anyString())).thenThrow(notFound("Application"));
            String pkId = "123456";
            manageApplicationService.getApplication(pkId);
        });
        Mockito.verify(remoteFtpApplicationService, Mockito.never()).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Get a FileSystem Application")
    void testGet8() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(applicationService.get(Mockito.anyString())).thenThrow(notFound("Application"));
            String pkId = "123456";
            manageApplicationService.getApplication(pkId);
        });
        Mockito.verify(fileSystemApplicationService, Mockito.never()).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Get a Ws Application")
    void testGet9() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(applicationService.get(Mockito.anyString())).thenThrow(notFound("Application"));
            String pkId = "123456";
            manageApplicationService.getApplication(pkId);
        });
        Mockito.verify(wsApplicationService, Mockito.never()).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Get a Sap Application")
    void testGet10() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(applicationService.get(Mockito.anyString())).thenThrow(notFound("Application"));
            String pkId = "123456";
            manageApplicationService.getApplication(pkId);
        });
        Mockito.verify(sapApplicationService, Mockito.never()).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Get a Mailbox Application")
    void testGet11() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(applicationService.get(Mockito.anyString())).thenThrow(notFound("Application"));
            String pkId = "123456";
            manageApplicationService.getApplication(pkId);
        });
        Mockito.verify(mailboxApplicationService, Mockito.never()).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Delete Application With isPem True")
    void testDelete() {
        Mockito.when(applicationService.getUniqueApplication(Mockito.anyString())).thenReturn(getApplicationEntity("HTTPS"));
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity("HTTPS"));
        String pkId = "123456";
        manageApplicationService.delete(pkId, true, true);
        Mockito.verify(applicationService, Mockito.times(1)).getUniqueApplication(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(httpApplicationService, Mockito.times(1)).delete(Mockito.any());
    }

    @Test
    @DisplayName("Delete Application with isPem False")
    void test_Delete() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity("HTTP"));
        String pkId = "123456";
        manageApplicationService.delete(pkId, false, true);
        Mockito.verify(applicationService, Mockito.never()).getUniqueApplication(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(httpApplicationService, Mockito.times(1)).delete(Mockito.any());
    }

    @Test
    @DisplayName("Delete a Ftp Application ")
    void testDelete1() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity("FTP"));      //delete ftp protocol
        String pkId = "123456";
        manageApplicationService.delete(pkId, false, false);
        Mockito.verify(ftpApplicationService, Mockito.never()).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Delete a Mq Application ")
    void testDelete2() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity("MQ"));      //delete mq protocol
        String pkId = "123456";
        manageApplicationService.delete(pkId, false, false);
        Mockito.verify(mqApplicationService, Mockito.never()).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Delete a Mailbox Application ")
    void testDelete3() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity("Mailbox"));      //delete mailbox protocol
        String pkId = "123456";
        manageApplicationService.delete(pkId, false, false);
        Mockito.verify(mailboxApplicationService, Mockito.never()).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Delete a Sap Application")
    void testDelete4() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity("SAP"));      //delete sap protocol
        String pkId = "123456";
        manageApplicationService.delete(pkId, false, false);
        Mockito.verify(sapApplicationService, Mockito.never()).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Delete a Web Service Application")
    void testDelete5() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity("Webservice"));      //delete Webservice protocol
        String pkId = "123456";
        manageApplicationService.delete(pkId, false, false);
        Mockito.verify(wsApplicationService, Mockito.never()).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Delete a File System Application")
    void testDelete6() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity("FileSystem"));      //delete Filesystem protocol
        String pkId = "123456";
        manageApplicationService.delete(pkId, false, false);
        Mockito.verify(fileSystemApplicationService, Mockito.never()).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Delete a Remote Ftp Application")
    void testDelete7() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity("SFGFTP"));//delete remoteApplicationService protocol
        String pkId = "123456";
        manageApplicationService.delete(pkId, false, false);
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(remoteFtpApplicationService, Mockito.times(1)).delete(Mockito.anyString(), Mockito.anyBoolean());
    }

    @Test
    @DisplayName("Delete a Cd Application")
    void testDelete8() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity("SFG_CONNECT_DIRECT"));      //delete ConnectDirect protocol
        String pkId = "123456";
        manageApplicationService.delete(pkId, false, false);
        Mockito.verify(remoteConnectDirectApplicationService, Mockito.never()).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Status Change with isPem false")
    void testStatusUpdate() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity("HTTPS"));
        Mockito.when(httpApplicationService.get(Mockito.anyString())).thenReturn(getHttpModel());
        String pkId = "123456";
        manageApplicationService.statusChange(pkId, false, false);
        Mockito.verify(applicationService, Mockito.never()).getUniqueApplication(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(httpApplicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(httpApplicationService, Mockito.times(1)).update(Mockito.any());
    }

    @Test
    @DisplayName("Status Change with isPem false")
    void testStatusUpdateHttp() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity("HTTP"));
        Mockito.when(httpApplicationService.get(Mockito.anyString())).thenReturn(getHttpModel());
        String pkId = "123456";
        manageApplicationService.statusChange(pkId, false, false);
        Mockito.verify(applicationService, Mockito.never()).getUniqueApplication(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(httpApplicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(httpApplicationService, Mockito.times(1)).update(Mockito.any());
    }

    @Test
    @DisplayName("Status Change with isPem true")
    void testStatusUpdate1() {
        Mockito.when(applicationService.getUniqueApplication(Mockito.anyString())).thenReturn(getApplicationEntity("SFTP"));
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity("SFTP"));
        Mockito.when(ftpApplicationService.get(Mockito.anyString())).thenReturn(getFtpModel("SFTP"));
        String pkId = "123456";
        manageApplicationService.statusChange(pkId, false, true);
        Mockito.verify(applicationService, Mockito.times(1)).getUniqueApplication(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(ftpApplicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(ftpApplicationService, Mockito.times(1)).update(Mockito.any());
    }

    @Test
    @DisplayName("Status Change with for FTP Protocol")
    void testStatusUpdate2() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity("FTP"));
        Mockito.when(ftpApplicationService.get(Mockito.anyString())).thenReturn(getFtpModel("FTP"));
        String pkId = "123456";
        manageApplicationService.statusChange(pkId, false, false);
        Mockito.verify(applicationService, Mockito.never()).getUniqueApplication(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(ftpApplicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(ftpApplicationService, Mockito.times(1)).update(Mockito.any());
    }

    @Test
    @DisplayName("Status Change with for FTPS Protocol")
    void testStatusUpdate3() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity("FTPS"));
        Mockito.when(ftpApplicationService.get(Mockito.anyString())).thenReturn(getFtpModel("FTPS"));
        String pkId = "123456";
        manageApplicationService.statusChange(pkId, false, false);
        Mockito.verify(applicationService, Mockito.never()).getUniqueApplication(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(ftpApplicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(ftpApplicationService, Mockito.times(1)).update(Mockito.any());
    }

    @Test
    @DisplayName("Status Change with for MQ Protocol")
    void testStatusUpdate4() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity("MQ"));
        Mockito.when(mqApplicationService.get(Mockito.anyString())).thenReturn(generateMqModel());
        String pkId = "123456";
        manageApplicationService.statusChange(pkId, false, false);
        Mockito.verify(applicationService, Mockito.never()).getUniqueApplication(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(mqApplicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(mqApplicationService, Mockito.times(1)).update(Mockito.any());
    }

    @Test
    @DisplayName("Status Change with for MailBox Protocol")
    void testStatusUpdate5() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity("Mailbox"));
        Mockito.when(mailboxApplicationService.get(Mockito.anyString())).thenReturn(generateMailBoxModel());
        String pkId = "123456";
        manageApplicationService.statusChange(pkId, false, false);
        Mockito.verify(applicationService, Mockito.never()).getUniqueApplication(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(mailboxApplicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(mailboxApplicationService, Mockito.times(1)).update(Mockito.any());
    }

    @Test
    @DisplayName("Status Change with for SAP Protocol")
    void testStatusUpdate6() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity("SAP"));
        Mockito.when(sapApplicationService.get(Mockito.anyString())).thenReturn(generateSapModel());
        String pkId = "123456";
        manageApplicationService.statusChange(pkId, false, false);
        Mockito.verify(applicationService, Mockito.never()).getUniqueApplication(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(sapApplicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(sapApplicationService, Mockito.times(1)).update(Mockito.any());
    }

    @Test
    @DisplayName("Status Change with for Webservice Protocol")
    void testStatusUpdate7() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity("Webservice"));
        Mockito.when(wsApplicationService.get(Mockito.anyString())).thenReturn(generateWebserviceModel());
        String pkId = "123456";
        manageApplicationService.statusChange(pkId, false, false);
        Mockito.verify(applicationService, Mockito.never()).getUniqueApplication(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(wsApplicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(wsApplicationService, Mockito.times(1)).update(Mockito.any());
    }

    @Test
    @DisplayName("Status Change with for FileSystem Protocol")
    void testStatusUpdate8() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity("FileSystem"));
        Mockito.when(fileSystemApplicationService.get(Mockito.anyString())).thenReturn(generateFileSystemModel());
        String pkId = "123456";
        manageApplicationService.statusChange(pkId, false, false);
        Mockito.verify(applicationService, Mockito.never()).getUniqueApplication(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(fileSystemApplicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(fileSystemApplicationService, Mockito.times(1)).update(Mockito.any());
    }

    @Test
    @DisplayName("Status Change with for SFGFTPS Protocol")
    void testStatusUpdate9() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity("SFGFTPS"));
        String pkId = "123456";
        manageApplicationService.statusChange(pkId, false, false);
        Mockito.verify(applicationService, Mockito.never()).getUniqueApplication(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(remoteFtpApplicationService, Mockito.never()).get(Mockito.anyString());
        Mockito.verify(remoteFtpApplicationService, Mockito.never()).update(Mockito.any());
    }

    @Test
    @DisplayName("Status Change with for Connect:Direct Protocol")
    void testStatusUpdate10() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity("SFG_CONNECT_DIRECT"));
        Mockito.when(remoteConnectDirectApplicationService.get(Mockito.anyString())).thenReturn(generateCdModel());
        String pkId = "123456";
        manageApplicationService.statusChange(pkId, false, false);
        Mockito.verify(applicationService, Mockito.never()).getUniqueApplication(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(remoteConnectDirectApplicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(remoteConnectDirectApplicationService, Mockito.times(1)).update(Mockito.any());
    }

    @Test
    @DisplayName("Status Change with for AWS-S3 Protocol")
    void testStatusUpdate11() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity("AWS_S3"));
        Mockito.when(awsS3ApplicationService.get(Mockito.anyString())).thenReturn(generateAwsS3Model());
        String pkId = "123456";
        manageApplicationService.statusChange(pkId, false, false);
        Mockito.verify(applicationService, Mockito.never()).getUniqueApplication(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(awsS3ApplicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(awsS3ApplicationService, Mockito.times(1)).update(Mockito.any());
    }


    @Test
    @DisplayName("Get Application Map ")
    void testGetApplicationMap() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(applicationService.findAllApplicationProfiles()).thenThrow(notFound("Application"));    //List of GetApplicationMap
            manageApplicationService.getApplicationMap();
        });
        Mockito.verify(applicationService, Mockito.never()).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Get Application Map with a valid data")
    void testGetApplicationMap1() {
        Mockito.when(applicationService.findAllApplicationProfiles()).thenReturn(generateList2());
        manageApplicationService.getApplicationMap();
        Mockito.verify(applicationService, Mockito.times(1)).findAllApplicationProfiles();
    }

    @Test
    @DisplayName("Get Application List")
    void testGetApplicationList() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(applicationService.findAllApplicationProfiles()).thenThrow(notFound("Application"));    //List of getApplicationList
            manageApplicationService.getApplicationMap();
        });
        Mockito.verify(applicationService, Mockito.never()).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Get Application List with an valid data")
    void testGetApplicationList1() {
        Mockito.when(applicationService.findAllApplicationProfiles()).thenReturn(generateList2());
        manageApplicationService.getApplicationList();
        Mockito.verify(applicationService, Mockito.times(1)).findAllApplicationProfiles();
    }

    @Test
    @DisplayName("Get Profiles By Protocol")
    void testGetProfilesByProtocol() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(applicationService.finaAllByProtocol(Mockito.any())).thenThrow(notFound("Application"));    //List of getProfilesByProtocol
            manageApplicationService.getProfilesByProtocol("FTP");
        });
        Mockito.verify(applicationService, Mockito.never()).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Get Profiles By Protocol")
    void testGetProfilesByProtocol1() {
        Mockito.when(applicationService.finaAllByProtocol(Mockito.any())).thenReturn(generateList2());    //List of getProfilesByProtocol
        manageApplicationService.getProfilesByProtocol("FTP");
        Mockito.verify(applicationService, Mockito.times(1)).finaAllByProtocol(Mockito.any());
    }

    @Test
    @DisplayName("Get All Template Application List ")
    void testGetAllTemplateApplicationList() {
        Mockito.when(applicationService.getAllTemplateApplicationProfiles()).thenReturn(generateList2());    //List of getAllTemplateApplicationList
        manageApplicationService.getAllTemplateApplicationList();
        Mockito.verify(applicationService, Mockito.times(1)).getAllTemplateApplicationProfiles();
    }

    @Test
    @DisplayName("Search Application")
    void testSearchApplication() {
        Mockito.when(applicationService.search(Mockito.any(), Mockito.any())).thenReturn(generateList());
        manageApplicationService.search(Mockito.any(), Mockito.any());
        Mockito.verify(applicationService, Mockito.times(1)).search(Mockito.any(), Mockito.any());
    }

    @Test
    @DisplayName("Get Application as application info input parameter")
    void TestGetApplicationFtp() {
        Mockito.when(applicationService.find(Mockito.anyString())).thenReturn(Optional.ofNullable(getApplicationEntity("FTP")));
        manageApplicationService.getApplication(getApplicationInfoModelForFtp());
        Mockito.verify(applicationService, Mockito.times(1)).find(Mockito.anyString());
    }

    @Test
    @DisplayName("Get Application as application info input parameter")
    void TestGetApplicationHttp() {
        Mockito.when(applicationService.find(Mockito.anyString())).thenReturn(Optional.ofNullable(getApplicationEntity("HTTP")));
        manageApplicationService.getApplication(getApplicationInfoModelForHttp());
        Mockito.verify(applicationService, Mockito.times(1)).find(Mockito.anyString());
    }

    @Test
    @DisplayName("Get Application as application info input parameter")
    void TestGetApplicationMq() {
        Mockito.when(applicationService.find(Mockito.anyString())).thenReturn(Optional.ofNullable(getApplicationEntity("MQ")));
        manageApplicationService.getApplication(getApplicationInfoModelForMq());
        Mockito.verify(applicationService, Mockito.times(1)).find(Mockito.anyString());
    }

    @Test
    @DisplayName("Get Application as application info input parameter")
    void TestGetApplicationMailBox() {
        Mockito.when(applicationService.find(Mockito.anyString())).thenReturn(Optional.ofNullable(getApplicationEntity("Mailbox")));
        manageApplicationService.getApplication(getApplicationInfoModelForMailbox());
        Mockito.verify(applicationService, Mockito.times(1)).find(Mockito.anyString());
    }

    @Test
    @DisplayName("Get Application as application info input parameter")
    void TestGetApplicationSap() {
        Mockito.when(applicationService.find(Mockito.anyString())).thenReturn(Optional.ofNullable(getApplicationEntity("SAP")));
        manageApplicationService.getApplication(getApplicationInfoModelForSAP());
        Mockito.verify(applicationService, Mockito.times(1)).find(Mockito.anyString());
    }

    @Test
    @DisplayName("Get Application as application info input parameter")
    void TestGetApplicationWebService() {
        Mockito.when(applicationService.find(Mockito.anyString())).thenReturn(Optional.ofNullable(getApplicationEntity("Webservice")));
        manageApplicationService.getApplication(getApplicationInfoModelForWebService());
        Mockito.verify(applicationService, Mockito.times(1)).find(Mockito.anyString());
    }

    @Test
    @DisplayName("Get Application as application info input parameter")
    void TestGetApplicationFileSystem() {
        Mockito.when(applicationService.find(Mockito.anyString())).thenReturn(Optional.ofNullable(getApplicationEntity("FileSystem")));
        manageApplicationService.getApplication(getApplicationInfoModelForFileSystem());
        Mockito.verify(applicationService, Mockito.times(1)).find(Mockito.anyString());
    }

    @Test
    @DisplayName("Get Application as application info input parameter")
    void TestGetApplicationSfgFtp() {
        Mockito.when(applicationService.find(Mockito.anyString())).thenReturn(Optional.ofNullable(getApplicationEntity("SFGFTP")));
        manageApplicationService.getApplication(getApplicationInfoModelForSfgFtp());
        Mockito.verify(applicationService, Mockito.times(1)).find(Mockito.anyString());
    }

    @Test
    @DisplayName("Get Application as application info input parameter")
    void TestGetApplicationConnectDirect() {
        Mockito.when(applicationService.find(Mockito.anyString())).thenReturn(Optional.ofNullable(getApplicationEntity("SFG_CONNECT_DIRECT")));
        manageApplicationService.getApplication(getApplicationInfoModelForConnectDirect());
        Mockito.verify(applicationService, Mockito.times(1)).find(Mockito.anyString());
    }

    ApplicationInfoModel<FtpModel> getApplicationInfoModelForFtp() {
        ApplicationInfoModel<FtpModel> ftpModel = new ApplicationInfoModel<>();
        ftpModel.setProtocol("FTP");
        ftpModel.setApplication(getFtpModel("FTP"));
        return ftpModel;
    }

    ApplicationInfoModel<HttpModel> getApplicationInfoModelForHttp() {
        ApplicationInfoModel<HttpModel> httpModel = new ApplicationInfoModel<>();
        httpModel.setProtocol("HTTP");
        httpModel.setApplication(getHttpModel());
        return httpModel;
    }

    ApplicationInfoModel<MqModel> getApplicationInfoModelForMq() {
        ApplicationInfoModel<MqModel> mqModel = new ApplicationInfoModel<>();
        mqModel.setProtocol("MQ");
        mqModel.setApplication(generateMqModel());
        return mqModel;
    }

    ApplicationInfoModel<MailboxModel> getApplicationInfoModelForMailbox() {
        ApplicationInfoModel<MailboxModel> mailboxModel = new ApplicationInfoModel<>();
        mailboxModel.setProtocol("Mailbox");
        mailboxModel.setApplication(generateMailBoxModel());
        return mailboxModel;
    }

    ApplicationInfoModel<SapModel> getApplicationInfoModelForSAP() {
        ApplicationInfoModel<SapModel> sapModel = new ApplicationInfoModel<>();
        sapModel.setProtocol("SAP");
        sapModel.setApplication(generateSapModel());
        return sapModel;
    }

    ApplicationInfoModel<WebserviceModel> getApplicationInfoModelForWebService() {
        ApplicationInfoModel<WebserviceModel> webserviceModel = new ApplicationInfoModel<>();
        webserviceModel.setProtocol("Webservice");
        webserviceModel.setApplication(generateWebserviceModel());
        return webserviceModel;
    }

    ApplicationInfoModel<FileSystemModel> getApplicationInfoModelForFileSystem() {
        ApplicationInfoModel<FileSystemModel> fileSystemModel = new ApplicationInfoModel<>();
        fileSystemModel.setProtocol("FileSystem");
        fileSystemModel.setApplication(generateFileSystemModel());
        return fileSystemModel;
    }

    ApplicationInfoModel<RemoteProfileModel> getApplicationInfoModelForSfgFtp() {
        ApplicationInfoModel<RemoteProfileModel> remoteFtpModel = new ApplicationInfoModel<>();
        remoteFtpModel.setProtocol("SFGFTP");
        remoteFtpModel.setApplication(generateRemoteFtpModel());
        return remoteFtpModel;
    }

    ApplicationInfoModel<RemoteCdModel> getApplicationInfoModelForConnectDirect() {
        ApplicationInfoModel<RemoteCdModel> cdModel = new ApplicationInfoModel<>();
        cdModel.setProtocol("SFG_CONNECT_DIRECT");
        cdModel.setApplication(generateCdModel());
        return cdModel;
    }


    MqModel generateMqModel() {
        MqModel mqModel = new MqModel();
        mqModel.setPkId("123456");
        mqModel.setProfileName("ApplicationName");
        mqModel.setProfileId("ApplicationId");
        mqModel.setPkId("123564");
        mqModel.setProtocol("MQ");
        mqModel.setEmailId("Mq@email.com");
        mqModel.setPhone("789456123");
        mqModel.setStatus(convertStringToBoolean("y"));
        mqModel.setHostName("12.12.12");
        mqModel.setFileType("fileType");
        mqModel.setQueueManager("mqQueueManager");
        mqModel.setQueueName("QueueName");
        mqModel.setAdapterName("adapterName");
        mqModel.setPoolingInterval("1M");
        mqModel.setHubInfo(convertStringToBoolean("Y"));
        return mqModel;
    }

    FtpModel getFtpModel(String protocol) {
        FtpModel ftpModel = new FtpModel();
        ftpModel.setPkId("123456");
        ftpModel.setProfileName("ProfileName");
        ftpModel.setProfileId("ProfileId");
        ftpModel.setProtocol(protocol);
        ftpModel.setEmailId("Email@email.com");
        ftpModel.setPhone("85635900323");
        ftpModel.setStatus(false);
        ftpModel.setHostName("HostName");
        ftpModel.setPortNumber("0012");
        ftpModel.setUserName("UserName");
        ftpModel.setPassword("password");
        ftpModel.setFileType("FileType");
        ftpModel.setTransferType("TransferType");
        ftpModel.setInDirectory("InDirectory");
        ftpModel.setOutDirectory("OutDirectory");
        ftpModel.setCertificateId("135476");
        ftpModel.setKnownHostKey("FTP");
        ftpModel.setAdapterName("AdapterName");
        ftpModel.setPoolingInterval("PoolingInterval");
        return ftpModel;
    }

    MailboxModel generateMailBoxModel() {
        MailboxModel mailboxModel = new MailboxModel();
        mailboxModel.setPkId("123456");
        mailboxModel.setProfileName("ProfileName");
        mailboxModel.setProfileId("ProfileId");
        mailboxModel.setProtocol("Mailbox");
        mailboxModel.setEmailId("Email@Email.com");
        mailboxModel.setPhone("7894561230");
        mailboxModel.setStatus(convertStringToBoolean("y"));
        mailboxModel.setInMailBox("mailbox");
        mailboxModel.setOutMailBox("outmailbox");
        mailboxModel.setFilter("filter");
        mailboxModel.setPoolingInterval("1M");
        mailboxModel.setHubInfo(convertStringToBoolean("n"));
        return mailboxModel;
    }

    WebserviceModel generateWebserviceModel() {
        WebserviceModel webserviceModel = new WebserviceModel();
        webserviceModel.setPkId("123456");
        webserviceModel.setProfileName("ProfileName");
        webserviceModel.setProfileId("ProfileId");
        webserviceModel.setProtocol("Webservice");
        webserviceModel.setEmailId("Email@email.com");
        webserviceModel.setPhone("789456123");
        webserviceModel.setStatus(convertStringToBoolean("y"));
        webserviceModel.setName("name");
        webserviceModel.setInMailBox("InMailbox");
        webserviceModel.setOutBoundUrl("Outbound");
        webserviceModel.setCertificateId("CertId");
        webserviceModel.setPoolingInterval("1M");
        webserviceModel.setAdapterName("AdapterName");
        webserviceModel.setHubInfo(convertStringToBoolean("n"));
        return webserviceModel;
    }

    FileSystemModel generateFileSystemModel() {
        FileSystemModel fileSystemModel = new FileSystemModel();
        fileSystemModel.setPkId("123456");
        fileSystemModel.setProfileName("ProfileName");
        fileSystemModel.setProfileId("ProfileId");
        fileSystemModel.setProtocol("FileSystem");
        fileSystemModel.setEmailId("Eamil@Email.com");
        fileSystemModel.setPhone("123456789");
        fileSystemModel.setAdapterName("AdapterName");
        fileSystemModel.setUserName("userName");
        fileSystemModel.setPassword("Password");
        fileSystemModel.setFileType("FileType");
        fileSystemModel.setDeleteAfterCollection(convertStringToBoolean("y"));
        fileSystemModel.setAdapterName("AdapterName");
        fileSystemModel.setPoolingInterval("1M");
        fileSystemModel.setStatus(convertStringToBoolean("y"));
        fileSystemModel.setInDirectory("InDirec");
        fileSystemModel.setOutDirectory("OutDirec");
        fileSystemModel.setHubInfo(convertStringToBoolean("y"));
        return fileSystemModel;
    }

    HttpModel getHttpModel() {
        HttpModel httpModel = new HttpModel();
        httpModel.setPkId("123456");
        httpModel.setProfileName("ProfileName");
        httpModel.setProfileId("ProfileId");
        httpModel.setProtocol("HTTP");
        httpModel.setEmailId("Email@email.com");
        httpModel.setPhone("546589795");
        httpModel.setStatus(false);
        httpModel.setAdapterName("AdaptorName");
        httpModel.setPoolingInterval("PoolingInterval");
        httpModel.setCertificate("Certificate");
        httpModel.setInMailBox("Mailbox");
        httpModel.setOutBoundUrl("OutBoundUrl");
        httpModel.setHubInfo(false);
        return httpModel;
    }

    SapModel generateSapModel() {
        SapModel sapModel = new SapModel();
        sapModel.setPkId("123456");
        sapModel.setProfileName("ProfileName");
        sapModel.setProfileId("ProfileID");
        sapModel.setPkId("12345");
        sapModel.setProtocol("SAP");
        sapModel.setEmailId("Email@Email.com");
        sapModel.setPhone("7894561230");
        sapModel.setStatus(convertStringToBoolean("y"));
        sapModel.setAdapterName("adapterName");
        sapModel.setSapRoute("SapRoute");
        sapModel.setHubInfo(convertStringToBoolean("y"));
        return sapModel;
    }

    RemoteProfileModel generateRemoteFtpModel() {
        RemoteProfileModel remoteProfileModel = new RemoteProfileModel();
        remoteProfileModel.setPkId("123456");
        remoteProfileModel.setProfileName("ProfileName");
        remoteProfileModel.setProfileId("ProfileId");
        remoteProfileModel.setProtocol("SFGFTPS");
        remoteProfileModel.setEmailId("Email@Email.com");
        remoteProfileModel.setPhone("7894561230");
        remoteProfileModel.setStatus(convertStringToBoolean("y"));
        remoteProfileModel.setUserName("UserName");
        remoteProfileModel.setPassword("Password");
        remoteProfileModel.setTransferType("transType");
        remoteProfileModel.setInDirectory("InDirec");
        remoteProfileModel.setOutDirectory("outDir");
        remoteProfileModel.setFileType("FileType");
        //Start : Extra adding for Bulk uploads
        remoteProfileModel.setRemoteHost("78.98.168.2");
        remoteProfileModel.setRemotePort("9898");
        remoteProfileModel.setCertificateId("certId");
        remoteProfileModel.setUserIdentityKey("UserIdKey");
        remoteProfileModel.setKnownHostKey("KnownHostKey");
        //End : Extra adding for Bulk uploads
        remoteProfileModel.setDeleteAfterCollection(convertStringToBoolean("y"));
        remoteProfileModel.setAdapterName("AdapterName");
        remoteProfileModel.setPoolingInterval("1M");
        remoteProfileModel.setHubInfo(convertStringToBoolean("n"));
        remoteProfileModel.setSubscriberType("APP");
        if (remoteProfileModel.getHubInfo()) {
            remoteProfileModel.setPreferredAuthenticationType(isNotNull("AuthType") ? "AuthType" : "PASSWORD");
        }
        return remoteProfileModel;
    }

    RemoteCdModel generateCdModel() {
        RemoteCdModel remoteCdModel = new RemoteCdModel();
        remoteCdModel.setPkId("123456");
        remoteCdModel.setProfileName("ProfileName");
        remoteCdModel.setProfileId("ProfileId");
        remoteCdModel.setPkId("123456");
        remoteCdModel.setProtocol("SFG_CONNECT_DIRECT");
        remoteCdModel.setEmailId("Email@Email.com");
        remoteCdModel.setPhone("789456123");
        remoteCdModel.setStatus(convertStringToBoolean("y"));
        remoteCdModel.setHubInfo(convertStringToBoolean("n"));
        remoteCdModel.setLocalNodeName("LNodeName");
        remoteCdModel.setsNodeId("NodeId");
        remoteCdModel.setsNodeIdPassword("NodeIdPassword");
        remoteCdModel.setNodeName("NodeName");
        remoteCdModel.setOperatingSystem("operatingSystem");
        remoteCdModel.setDirectory("Directory");
        remoteCdModel.setHostName("HostName");
        remoteCdModel.setPort(9090);
        remoteCdModel.setCopySisOpts("CopySis");
        remoteCdModel.setCheckPoint("CheckPoint");
        remoteCdModel.setCompressionLevel("CompLevel");
        remoteCdModel.setDisposition("Disposition");
        remoteCdModel.setSubmit("Submit");
        remoteCdModel.setSecure(convertStringToBoolean("y"));
        remoteCdModel.setSecurityProtocol("Protocol");
        remoteCdModel.setRunJob("RunJob");
        remoteCdModel.setRunTask("RunTask");
        if (isNotNull("cert1,cert2,cert3")) {
            remoteCdModel.setCaCertName(Arrays.stream("cert1,cert2,cert3".split(",")).map(CaCertGetModel::new).collect(Collectors.toList()));
        }
        if (isNotNull("cipher1,cipher2,cipher3")) {
            remoteCdModel.setCipherSuits(Arrays.stream("cipher1,cipher2,cipher3".split(",")).map(CipherSuiteNameGetModel::new).collect(Collectors.toList()));
        }
        remoteCdModel.setCdMainFrameModel(
                new CdMainFrameModel().setDcbParam("DcbParam")
                        .setDnsName("DnsName")
                        .setSpace("Space")
                        .setStorageClass("Storage")
                        .setUnit("Unit")
        );
        remoteCdModel.setPoolingInterval("1M");
        remoteCdModel.setAdapterName("AdapterName");
        return remoteCdModel;
    }

    AwsS3Model generateAwsS3Model() {
        AwsS3Model awsS3Model = new AwsS3Model();
        awsS3Model.setPkId("123456");
        awsS3Model.setProfileName("ProfileName");
        awsS3Model.setProfileId("ProfileId");
        awsS3Model.setProtocol("AWS-S3");
        awsS3Model.setEmailId("EmailId@Email.com");
        awsS3Model.setPhone("78945123");
        awsS3Model.setStatus(convertStringToBoolean("y"));
        awsS3Model.setHubInfo(convertStringToBoolean("n"));
        awsS3Model.setFileType("FileType");
        awsS3Model.setAdapterName("AdapterName");
        awsS3Model.setPoolingInterval("1M");
        awsS3Model.setAccessKey("AccessKey");
        awsS3Model.setBucketName("BucketName");
        awsS3Model.setEndpoint("EndPoint");
        awsS3Model.setFileName("FileName");
        awsS3Model.setInMailbox("MailBox");
        awsS3Model.setSecretKey("SecretKey");
        awsS3Model.setSourcePath("SourcePath");
        awsS3Model.setRegion("Region");
        return awsS3Model;
    }

    ApplicationEntity getApplicationEntity(String protocol) {
        ApplicationEntity applicationEntity = new ApplicationEntity();
        applicationEntity.setAppDropFiles("App");
        applicationEntity.setAppIntegrationProtocol(protocol);
        applicationEntity.setAppIsActive("AppIsActive");
        applicationEntity.setApplicationName("ApplicationName");
        applicationEntity.setApplicationId("ApplicationId");
        applicationEntity.setAppPickupFiles("AppPickupFiles");
        applicationEntity.setAppProtocolRef("AppProtocolRef");
        applicationEntity.setEmailId("Email@e.com");
        applicationEntity.setPhone("7598008944");
        applicationEntity.setPkId("123456");
        return applicationEntity;
    }

    ApplicationEntity getApplicationEntity1() {
        ApplicationEntity applicationEntity = new ApplicationEntity();
        applicationEntity.setAppDropFiles("App");
        applicationEntity.setAppIntegrationProtocol("HTTP");
        applicationEntity.setAppIsActive("AppIsActive");
        applicationEntity.setApplicationName("ApplicationName");
        applicationEntity.setApplicationId("ApplicationId");
        applicationEntity.setAppPickupFiles("AppPickupFiles");
        applicationEntity.setAppProtocolRef("AppProtocolRef");
        applicationEntity.setEmailId("Email@e.com");
        applicationEntity.setPhone("7598008944");
        applicationEntity.setPkId("123456");
        return applicationEntity;
    }

    ApplicationEntity getApplicationEntity2() {
        ApplicationEntity applicationEntity = new ApplicationEntity();
        applicationEntity.setAppDropFiles("App");
        applicationEntity.setAppIntegrationProtocol("HTTP");
        applicationEntity.setAppIsActive("AppIsActive");
        applicationEntity.setApplicationName("ApplicationName");
        applicationEntity.setApplicationId("ApplicationId");
        applicationEntity.setAppPickupFiles("AppPickupFiles");
        applicationEntity.setAppProtocolRef("AppProtocolRef");
        applicationEntity.setEmailId("Email@e.com");
        applicationEntity.setPhone("7598008944");
        applicationEntity.setPkId("123456");
        return applicationEntity;
    }

    AppActivityHistoryEntity generateAppActivityHistoryEntity() {
        AppActivityHistoryEntity appActivityHistoryEntity = new AppActivityHistoryEntity();
        appActivityHistoryEntity.setUserName("UserName");
        return appActivityHistoryEntity;
    }

    Page<ApplicationEntity> generateList() {
        List<ApplicationEntity> li = new ArrayList<>();
        Page<ApplicationEntity> pagedTasks = new PageImpl<>(li);
        li.add(getApplicationEntity("FTP"));
        return pagedTasks;
    }

    Page<AppActivityHistoryEntity> generateList1() {
        List<AppActivityHistoryEntity> li1 = new ArrayList<>();
        Page<AppActivityHistoryEntity> pagedTasks1 = new PageImpl<>(li1);
        li1.add(generateAppActivityHistoryEntity());
        return pagedTasks1;
    }

    List<ApplicationEntity> generateList2() {
        List<ApplicationEntity> li = new ArrayList<>();
        li.add(getApplicationEntity("FTP"));
        li.add(getApplicationEntity1());
        li.add(getApplicationEntity2());
        return li;
    }
}
