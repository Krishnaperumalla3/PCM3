//package com.pe.pcm.resource.application.sfg;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.pe.pcm.application.sfg.RemoteFtpApplicationService;
//import com.pe.pcm.common.CommunityManagerResponse;
//import com.pe.pcm.protocol.RemoteProfileModel;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(SpringExtension.class)
//@ExtendWith(MockitoExtension.class)
//@ActiveProfiles("test")
//public class RemoteFtpApplicationResourcetest {
//
//    @MockBean
//    private RemoteFtpApplicationService remoteFtpApplicationService;
//    private RemoteFtpApplicationResource remoteFtpApplicationResource;
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    void inIt() {
//        remoteFtpApplicationResource = new RemoteFtpApplicationResource(remoteFtpApplicationService);
//        mockMvc = MockMvcBuilders.standaloneSetup(remoteFtpApplicationResource).build();
//    }
//
//    @Test
//    public void createRemoteFtpApplication() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        RemoteProfileModel remoteProfileModel = getRemoteFtpModel();
//        MvcResult mvcResult = mockMvc.perform(post("/pcm/application/remoteftp").contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(remoteProfileModel))).andExpect(status().isOk()).andReturn();
//        CommunityManagerResponse communityManagerResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), CommunityManagerResponse.class);
//        Assertions.assertNotNull(communityManagerResponse);
//        Assertions.assertEquals(200, communityManagerResponse.getStatusCode().intValue());
//        Assertions.assertEquals("Application created successfully", communityManagerResponse.getStatusMessage());
//    }
//
//    @Test
//    public void createRemoteFtpApplicationBadRequest() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        RemoteProfileModel remoteProfileModel = getRemoteFtpModel();
//        MvcResult mvcResult = mockMvc.perform(post("/pcm/application/remoteftp").contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(remoteProfileModel))).andExpect(status().is(400)).andReturn();
//    }
//
//
//
//    RemoteProfileModel getRemoteFtpModel() {
//        RemoteProfileModel remoteProfileModel = new RemoteProfileModel();
//        remoteProfileModel.setPkId("123456");
//        remoteProfileModel.setProfileName("ProfileName");
//        remoteProfileModel.setProfileId("ProfileId");
//        remoteProfileModel.setProtocol("SFGFTP");
//        remoteProfileModel.setEmailId("Email@email.com");
//        remoteProfileModel.setPhone("458979869507");
//        remoteProfileModel.setStatus(false);
//        remoteProfileModel.setUserName("UserName");
//        remoteProfileModel.setPassword("password");
//        remoteProfileModel.setTransferType("TransferType");
//        remoteProfileModel.setInDirectory("InDirectory");
//        remoteProfileModel.setOutDirectory("Outdirectory");
//        remoteProfileModel.setFileType("FileType");
//        remoteProfileModel.setRemotePort("RemotePort");
//        remoteProfileModel.setRemoteHost("RemoteHost");
//        remoteProfileModel.setConnectionType("ConnectionType");
//        remoteProfileModel.setAdapterName("adapterName");
//        remoteProfileModel.setPemIdentifier("pemIdentifier");
//        return remoteProfileModel;
//    }
//
//}
