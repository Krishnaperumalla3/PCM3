//package com.pe.pcm.resource.application;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.pe.pcm.application.ApplicationModel;
//import com.pe.pcm.application.FtpApplicationService;
//import com.pe.pcm.application.entity.ApplicationEntity;
//import com.pe.pcm.common.CommunityManagerResponse;
//import com.pe.pcm.exception.CommunityManagerServiceException;
//import com.pe.pcm.exception.ErrorMessage;
//import com.pe.pcm.protocol.FtpModel;
//import com.pe.pcm.resource.application.FtpApplicationResource;
//import org.apache.coyote.Response;
//import org.apache.http.HttpStatus;
//import org.json.JSONObject;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.mockito.stubbing.OngoingStubbing;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.server.ResponseStatusException;
//import org.springframework.web.util.NestedServletException;
//
//import java.util.Optional;
//
//import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;
//import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;
//import static com.pe.pcm.utils.PCMConstants.APPLICATION_DELETE;
//import static com.pe.pcm.utils.PCMConstants.APPLICATION_UPDATE;
//import static java.nio.file.Files.readAllBytes;
//import static org.junit.Assert.*;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(SpringExtension.class)
//@ExtendWith(MockitoExtension.class)
//@ActiveProfiles("test")
//public class FtpApplicationResourceTest {
//
//    @MockBean
//    private FtpApplicationService ftpApplicationService;
//    private MockMvc mockMvc;
//    FtpApplicationResource ftpApplicationResource;
//
//   @BeforeEach
//    void inIt() {
//        ftpApplicationResource = new FtpApplicationResource(ftpApplicationService);
//        mockMvc = MockMvcBuilders.standaloneSetup(ftpApplicationResource).build();
//    }
//
//    @Test
//    public void createApplication() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInString = new String(readAllBytes(java.nio.file.Paths.get(new ClassPathResource("application.json").getURI())));
//        ApplicationModel applicationModel = mapper.readValue(jsonInString, ApplicationModel.class);
//        MvcResult mvcResult = mockMvc.perform(post("/pcm/application/ftp").contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(applicationModel))).andExpect(status().isOk()).andReturn();
//        CommunityManagerResponse communityManagerResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), CommunityManagerResponse.class);
//        Assertions.assertNotNull(communityManagerResponse);
//        Assertions.assertEquals(200, communityManagerResponse.getStatusCode().intValue());
//        Assertions.assertEquals("Application created successfully", communityManagerResponse.getStatusMessage());
//    }
//
//    /*
//    * Negative TestCase with profileName = 'null'*/
//    @Test
//    public void createApplicationWithNoProfileName() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInString = new String(readAllBytes(java.nio.file.Paths.get(new ClassPathResource("application.json").getURI())));
//        ApplicationModel applicationModel = mapper.readValue(jsonInString, ApplicationModel.class);
//        applicationModel.setProfileName(null);
//        MvcResult mvcResult = mockMvc.perform(post("/pcm/application/ftp").contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(applicationModel))).andExpect(status().is(400)).andReturn();
//    }
//
//    @Test
//    public void updateApplication() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInString = new String(readAllBytes(java.nio.file.Paths.get(new ClassPathResource("application.json").getURI())));
//        ApplicationModel applicationModel = mapper.readValue(jsonInString, ApplicationModel.class);
//      MvcResult  mvcResult = mockMvc.perform(put("/pcm/application/ftp").contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(applicationModel))).andExpect(status().isOk()).andReturn();
//        CommunityManagerResponse communityManagerResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), CommunityManagerResponse.class);
//        Assertions.assertEquals(APPLICATION_UPDATE, communityManagerResponse.getStatusMessage());
//    }
//
//    @Test
//    public void updateNonExistingApplication() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInString = new String(readAllBytes(java.nio.file.Paths.get(new ClassPathResource("application.json").getURI())));
//        ApplicationModel applicationModel = mapper.readValue(jsonInString, ApplicationModel.class);
//        FtpModel ftpModel = mapper.readValue(jsonInString, FtpModel.class);
//        try {
//            doThrow(new CommunityManagerServiceException(400, "No Record Found")).when(ftpApplicationService).update(ftpModel);
//            MvcResult mvcResult = mockMvc.perform(put("/pcm/application/ftp").contentType(MediaType.APPLICATION_JSON)
//                    .content(new ObjectMapper().writeValueAsString(applicationModel))).andExpect(status().isOk()).andReturn();
//        } catch (Exception e) {
//            CommunityManagerServiceException communityManagerServiceException = (CommunityManagerServiceException) e.getCause();
//            Assertions.assertEquals(400,communityManagerServiceException.getStatusCode());
//            Assertions.assertEquals("No Record Found",communityManagerServiceException.getErrorMessage());
//        }
//    }
//
//  /* Negative TestCase: Update Application without requiredfield*/
//    @Test
//    public void updateApplicationWithOutRequiredField() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInString = new String(readAllBytes(java.nio.file.Paths.get(new ClassPathResource("application.json").getURI())));
//        ApplicationModel applicationModel = mapper.readValue(jsonInString, ApplicationModel.class);
//        applicationModel.setProfileName(null);
//        mockMvc.perform(put("/pcm/application/ftp").contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(applicationModel))).andExpect(status().isBadRequest()).andReturn();
//    }
//
//
//    @Test
//    public void getApplication() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        FtpModel ftpModel = new FtpModel();
//        ftpModel.setPkId("123456");
//        ftpModel.setHostName("JunitHOSTNAME");
//        when(ftpApplicationService.get("123456")).thenReturn(ftpModel);
//        MvcResult mvcResult = mockMvc.perform(get("/pcm/application/ftp/{pkId}",123456).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
//        FtpModel ftpModel1 = mapper.readValue(mvcResult.getResponse().getContentAsString(), FtpModel.class);
//        Assertions.assertEquals(ftpModel.getHostName(),ftpModel1.getHostName());
//    }
//
//
//    @Test
//    public void getApplicationWithOutId() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        mockMvc.perform(get("/pcm/application/ftp/{pkId}", (Object) null).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isMethodNotAllowed()).andReturn();
//
//    }
//
//    @Test
//    public void getNonExistingApplication() {
//        FtpModel ftpModel = new FtpModel();
//        ftpModel.setPkId("123456");
//        ftpModel.setHostName("JunitHOSTNAME");
//        try {
//            doReturn(ftpModel).when(ftpApplicationService).get("1234");
//            doThrow(new CommunityManagerServiceException(400,"No Record Found")).when(ftpApplicationService).get("999");
//            mockMvc.perform(get("/pcm/application/ftp/{pkId}","999").contentType(MediaType.APPLICATION_JSON)).andReturn();
//        } catch (Exception e){
//            CommunityManagerServiceException communityManagerServiceException = (CommunityManagerServiceException) e.getCause();
//            Assertions.assertEquals(communityManagerServiceException.getStatusCode(),400);
//            Assertions.assertEquals(communityManagerServiceException.getErrorMessage(),"No Record Found");
//        }
//    }
//
//
//    @Test
//    public void deleteApplication() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        MvcResult mvcResult = mockMvc.perform(delete("/pcm/application/ftp/{pkId}",123456).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
//        CommunityManagerResponse communityManagerResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), CommunityManagerResponse.class);
//        Assertions.assertEquals(APPLICATION_DELETE, communityManagerResponse.getStatusMessage());
//    }
//
//
//    @Test
//    public void deleteNonExistingApplication() {
//        FtpModel ftpModel = new FtpModel();
//        ftpModel.setPkId("123456");
//        ftpModel.setHostName("JunitHOSTNAME");
//        try {
//            doThrow(new CommunityManagerServiceException(400, "No Record Found")).when(ftpApplicationService).delete("999");
//            mockMvc.perform(delete("/pcm/application/ftp/{pkId}", "999").contentType(MediaType.APPLICATION_JSON)).andReturn();
//        } catch (Exception e){
//            CommunityManagerServiceException cause = (CommunityManagerServiceException) e.getCause();
//            Assertions.assertEquals(400,cause.getStatusCode());
//            Assertions.assertEquals("No Record Found",cause.getErrorMessage());
//        }
//    }
//
//
//    @Test
//    public void searchApplication() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInString = new String(readAllBytes(java.nio.file.Paths.get(new ClassPathResource("application.json").getURI())));
//        ApplicationModel applicationModel = mapper.readValue(jsonInString, ApplicationModel.class);
//        MvcResult mvcResult = mockMvc.perform(post("/pcm/application/").contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(applicationModel))).andExpect(status().isOk()).andReturn();
//        ApplicationEntity applicationEntity = mapper.readValue(mvcResult.getResponse().getContentAsString(), ApplicationEntity.class);
//        assertNotNull(applicationEntity.getPkId());
//        assertEquals("junitApplicationId", applicationEntity.getApplicationId());
//        assertEquals("junitApplicationName", applicationEntity.getApplicationName());
//        mvcResult = mockMvc.perform(post("/pcm/application/search").contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(applicationModel))).andExpect(status().isOk()).andReturn();
//        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
//        int numberOfElements = jsonObject.getInt("numberOfElements");
//        assertEquals(1, numberOfElements);
//    }
//
//}
