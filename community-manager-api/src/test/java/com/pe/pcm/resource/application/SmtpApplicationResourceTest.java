//package com.pe.pcm.resource.application;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.pe.pcm.application.ApplicationModel;
//import com.pe.pcm.application.SmtpApplicationService;
//import com.pe.pcm.common.CommunityManagerResponse;
//import com.pe.pcm.protocol.FtpModel;
//import com.pe.pcm.protocol.SmtpModel;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import static com.pe.pcm.utils.PCMConstants.APPLICATION_DELETE;
//import static com.pe.pcm.utils.PCMConstants.APPLICATION_UPDATE;
//import static java.nio.file.Files.readAllBytes;
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(SpringExtension.class)
//@ExtendWith(MockitoExtension.class)
//@ActiveProfiles("test")
//public class SmtpApplicationResourceTest {
//
//    @MockBean
//    private SmtpApplicationService smtpApplicationService;
//    private SmtpApplicationResource smtpApplicationResource;
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    void inIt() {
//        smtpApplicationResource = new SmtpApplicationResource(smtpApplicationService);
//        mockMvc = MockMvcBuilders.standaloneSetup(smtpApplicationResource).build();
//    }
//
//    @Test
//    public void createApplication() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInString = new String(readAllBytes(java.nio.file.Paths.get(new ClassPathResource("application.json").getURI())));
//        SmtpModel smtpModel  = mapper.readValue(jsonInString, SmtpModel.class);
//        MvcResult mvcResult = mockMvc.perform(post("/pcm/application/smtp").contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(smtpModel))).andExpect(status().isOk()).andReturn();
//        CommunityManagerResponse communityManagerResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), CommunityManagerResponse.class);
//        Assertions.assertNotNull(communityManagerResponse);
//        Assertions.assertEquals(200, communityManagerResponse.getStatusCode().intValue());
//        Assertions.assertEquals("Application created successfully", communityManagerResponse.getStatusMessage());
//    }
//
//    /*
//     * Negative TestCase with profileName = 'null'*/
//    @Test
//    public void createApplicationWithNoProfileName() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInString = new String(readAllBytes(java.nio.file.Paths.get(new ClassPathResource("application.json").getURI())));
//        SmtpModel smtpModel = mapper.readValue(jsonInString, SmtpModel.class);
//        smtpModel.setProfileName(null);
//        MvcResult mvcResult = mockMvc.perform(post("/pcm/application/smtp").contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(smtpModel))).andExpect(status().is(400)).andReturn();
//    }
//
//    @Test
//    public void updateApplication() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInString = new String(readAllBytes(java.nio.file.Paths.get(new ClassPathResource("application.json").getURI())));
//        SmtpModel smtpModel = mapper.readValue(jsonInString, SmtpModel.class);
//        MvcResult  mvcResult = mockMvc.perform(put("/pcm/application/smtp").contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(smtpModel))).andExpect(status().isOk()).andReturn();
//        CommunityManagerResponse communityManagerResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), CommunityManagerResponse.class);
//        Assertions.assertEquals(APPLICATION_UPDATE, communityManagerResponse.getStatusMessage());
//    }
//
//    /* Negative TestCase: Update Application without requiredfield*/
//    @Test
//    public void updateApplicationWithOutRequiredField() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInString = new String(readAllBytes(java.nio.file.Paths.get(new ClassPathResource("application.json").getURI())));
//        SmtpModel smtpModel = mapper.readValue(jsonInString, SmtpModel.class);
//        smtpModel.setProfileName(null);
//        mockMvc.perform(put("/pcm/application/smtp").contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(smtpModel))).andExpect(status().isBadRequest()).andReturn();
//    }
//
//    @Test
//    public void getApplication() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        SmtpModel smtpModel = new SmtpModel();
//        smtpModel.setPkId("123456");
//        smtpModel.setProfileName("JunitHOSTNAME");
//        when(smtpApplicationService.get("123456")).thenReturn(smtpModel);
//        MvcResult mvcResult = mockMvc.perform(get("/pcm/application/smtp/{pkId}",123456).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
//        SmtpModel smtpModel1 = mapper.readValue(mvcResult.getResponse().getContentAsString(), SmtpModel.class);
//        assertEquals(smtpModel.getProfileName(),smtpModel1.getProfileName());
//    }
//
//    @Test
//    public void getApplicationWithOutId() throws Exception {
//        mockMvc.perform(get("/pcm/application/smtp/{pkId}", (Object) null).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isMethodNotAllowed()).andReturn();
//
//    }
//
//    @Test
//    public void deleteApplication() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        MvcResult mvcResult = mockMvc.perform(delete("/pcm/application/smtp/{pkId}",123456).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
//        CommunityManagerResponse communityManagerResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), CommunityManagerResponse.class);
//        assertEquals(APPLICATION_DELETE, communityManagerResponse.getStatusMessage());
//    }
//
//}
