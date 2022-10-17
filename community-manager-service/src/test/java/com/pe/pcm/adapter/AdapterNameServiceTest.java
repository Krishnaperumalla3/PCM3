package com.pe.pcm.adapter;

import com.pe.pcm.common.CommunityManagerKeyValueModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class AdapterNameServiceTest {

    private AdapterNamesService adapterNamesService;

    @BeforeEach
    void inIt() {
        adapterNamesService = new AdapterNamesService("PragmaFTPServerAdapter",
                "FTP Client Adapter",
                "Pragma_FTPS_ServerAdapter",
                "FTP Client Adapter",
                "Pragma_SFTPServerAdapter",
                "Pragma_SFTPClientAdapter",
                "Pragma_AS2ServerAdapter",
                "Pragma_AS2ClientAdapter",
                "HTTPClientAdapter",
                "Pragma_CDClientAdapter",
                "Pragma_HTTPServerSync",
                "Pragma_HTTPSServerSync",
                "Pragma_MQAdapter",
                "Pragma_HTTPSServerSync",
                "PragmaFileSystem",
                "Pragma_SFTPClientAdapter",
                "Pragma_SFTPServerAdapter",
                "Pragma_FTPClientAdapter",
                "PragmaFTPServerAdapter",
                "Pragma_FTPSClientAdapter",
                "Pragma_FTPS_ServerAdapter");
    }

    @Test
    @DisplayName(value = "Adapter Names")
    void getAdapterNamesTest() {
        AdapterNamesModel actual = adapterNamesService.getAdapterNames();
        AdapterNamesModel expected = loadAdapters();
        assertThat(actual.getFtpServerAdapterName(), hasToString(expected.getFtpServerAdapterName()));
        assertEquals(actual.getAs2ClientAdapterName(), expected.getAs2ClientAdapterName());
        assertNotEquals(actual.getAs2HttpClientAdapter(), expected.getAs2ServerAdapterName());
        assertEquals(actual.getFtpClientAdapterName(), expected.getFtpClientAdapterName());
        assertEquals(actual.getFtpsServerAdapterName(), expected.getFtpsServerAdapterName());
        assertEquals(actual.getFtpsClientAdapterName(), expected.getFtpsClientAdapterName());
        assertEquals(actual.getSftpClientAdapterName(), expected.getSftpClientAdapterName());
        assertEquals(actual.getSftpServerAdapterName(), expected.getSftpServerAdapterName());
        assertEquals(actual.getCdClientAdapterName(), expected.getCdClientAdapterName());
        assertEquals(actual.getHttpServerAdapterName(), expected.getHttpServerAdapterName());
        assertEquals(actual.getHttpsServerAdapterName(), expected.getHttpsServerAdapterName());
        assertEquals(actual.getMqAdapterName(), expected.getMqAdapterName());
        assertEquals(actual.getWsServerAdapterName(), expected.getWsServerAdapterName());
        assertEquals(actual.getFsAdapter(), expected.getFsAdapter());
        assertEquals(actual.getSfgFtpClientAdapterName(), expected.getSfgFtpClientAdapterName());
        assertEquals(actual.getSfgFtpsClientAdapterName(), expected.getSfgFtpsClientAdapterName());
        assertEquals(actual.getSfgFtpServerAdapterName(), expected.getSfgFtpServerAdapterName());
        assertEquals(actual.getSfgFtpsServerAdapterName(), expected.getSfgFtpsServerAdapterName());
        assertEquals(actual.getSfgSftpClientAdapterName(), expected.getSfgSftpClientAdapterName());
        assertEquals(actual.getSfgSftpServerAdapterName(), expected.getSfgSftpServerAdapterName());
        assertEquals(actual.toString(), expected.toString());
    }

    @Test
    @DisplayName("Adapter Names List")
    void getAdapterNameList() {
        List<CommunityManagerKeyValueModel> actual = adapterNamesService.getAdapterNamesForPem();
        List<CommunityManagerKeyValueModel> expected = loadAdaptersForPem();
        assertThat(actual.size(), is(21));
        assertThat(actual.size(), is(expected.size()));
    }


    private AdapterNamesModel loadAdapters() {
        return new AdapterNamesModel()
                .setFtpServerAdapterName("PragmaFTPServerAdapter")
                .setFtpClientAdapterName("FTP Client Adapter")
                .setFtpsServerAdapterName("Pragma_FTPS_ServerAdapter")
                .setFtpsClientAdapterName("FTP Client Adapter")
                .setSftpServerAdapterName("Pragma_SFTPServerAdapter")
                .setSftpClientAdapterName("Pragma_SFTPClientAdapter")
                .setAs2ServerAdapterName("Pragma_AS2ServerAdapter")
                .setAs2ClientAdapterName("Pragma_AS2ClientAdapter")
                .setAs2HttpClientAdapter("HTTPClientAdapter")
                .setCdClientAdapterName("Pragma_CDClientAdapter")
                .setHttpServerAdapterName("Pragma_HTTPServerSync")
                .setHttpsServerAdapterName("Pragma_HTTPSServerSync")
                .setMqAdapterName("Pragma_MQAdapter")
                .setWsServerAdapterName("Pragma_HTTPSServerSync")
                .setFsAdapter("PragmaFileSystem")
                .setSfgSftpClientAdapterName("Pragma_SFTPClientAdapter")
                .setSfgSftpServerAdapterName("Pragma_SFTPServerAdapter")
                .setSfgFtpClientAdapterName("Pragma_FTPClientAdapter")
                .setSfgFtpServerAdapterName("PragmaFTPServerAdapter")
                .setSfgFtpsClientAdapterName("Pragma_FTPSClientAdapter")
                .setSfgFtpsServerAdapterName("Pragma_FTPS_ServerAdapter");
    }

    private List<CommunityManagerKeyValueModel> loadAdaptersForPem() {
        List<CommunityManagerKeyValueModel> adapterList = new ArrayList<>();
        adapterList.add(new CommunityManagerKeyValueModel("ftpServerAdapterName", "PragmaFTPServerAdapter"));
        adapterList.add(new CommunityManagerKeyValueModel("ftpClientAdapterName", "FTP Client Adapter"));
        adapterList.add(new CommunityManagerKeyValueModel("ftpsServerAdapterName", "Pragma_FTPS_ServerAdapter"));
        adapterList.add(new CommunityManagerKeyValueModel("ftpsClientAdapterName", "FTP Client Adapter"));
        adapterList.add(new CommunityManagerKeyValueModel("sftpServerAdapterName", "Pragma_SFTPServerAdapter"));
        adapterList.add(new CommunityManagerKeyValueModel("sftpClientAdapterName", "Pragma_SFTPClientAdapter"));
        adapterList.add(new CommunityManagerKeyValueModel("as2ServerAdapterName", "Pragma_AS2ServerAdapter"));
        adapterList.add(new CommunityManagerKeyValueModel("as2ClientAdapterName", "Pragma_AS2ClientAdapter"));
        adapterList.add(new CommunityManagerKeyValueModel("as2HttpClientAdapter", "HTTPClientAdapter"));
        adapterList.add(new CommunityManagerKeyValueModel("cdClientAdapterName", "Pragma_CDClientAdapter"));
        adapterList.add(new CommunityManagerKeyValueModel("httpServerAdapterName", "Pragma_HTTPServerSync"));
        adapterList.add(new CommunityManagerKeyValueModel("httpsServerAdapterName", "Pragma_HTTPSServerSync"));
        adapterList.add(new CommunityManagerKeyValueModel("mqAdapterName", "Pragma_MQAdapter"));
        adapterList.add(new CommunityManagerKeyValueModel("wsServerAdapterName", "Pragma_HTTPSServerSync"));
        adapterList.add(new CommunityManagerKeyValueModel("fsAdapter", "PragmaFileSystem"));
        adapterList.add(new CommunityManagerKeyValueModel("sfgSftpClientAdapterName", "Pragma_SFTPClientAdapter"));
        adapterList.add(new CommunityManagerKeyValueModel("sfgSftpServerAdapterName", "Pragma_SFTPServerAdapter"));
        adapterList.add(new CommunityManagerKeyValueModel("sfgFtpClientAdapterName", "Pragma_FTPClientAdapter"));
        adapterList.add(new CommunityManagerKeyValueModel("sfgFtpServerAdapterName", "PragmaFTPServerAdapter"));
        adapterList.add(new CommunityManagerKeyValueModel("sfgFtpsClientAdapterName", "Pragma_FTPSClientAdapter"));
        adapterList.add(new CommunityManagerKeyValueModel("sfgFtpsServerAdapterName", "Pragma_FTPS_ServerAdapter"));
        return adapterList;
    }

}
