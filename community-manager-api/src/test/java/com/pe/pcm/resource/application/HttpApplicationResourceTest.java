//package com.pe.pcm.resource.application;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.pe.pcm.application.HttpApplicationService;
//import com.pe.pcm.common.CommunityManagerResponse;
//import com.pe.pcm.protocol.FtpModel;
//import com.pe.pcm.protocol.HttpModel;
//import com.pe.pcm.resource.application.HttpApplicationResource;
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
//import static org.junit.Assert.assertNotNull;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(SpringExtension.class)
//@ExtendWith(MockitoExtension.class)
//@ActiveProfiles("test")
//public class HttpApplicationResourceTest {
//
//    @MockBean
//    private  HttpApplicationService httpApplicationService;
//    private  MockMvc mockMvc;
//
//    @BeforeEach
//    void inIt() {
//        HttpApplicationResource httpApplicationResource = new HttpApplicationResource(httpApplicationService);
//        mockMvc = MockMvcBuilders.standaloneSetup(httpApplicationResource).build();
//    }
//
//    @Test
//    public void createHttpApplication() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInString = new String(readAllBytes(java.nio.file.Paths.get(new ClassPathResource("application.json").getURI())));
//         HttpModel httpModel = mapper.readValue(jsonInString, HttpModel.class);
//        MvcResult mvcResult = mockMvc.perform(post("/pcm/application/http").contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(httpModel))).andExpect(status().isOk()).andReturn();
//        CommunityManagerResponse communityManagerResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), CommunityManagerResponse.class);
//        assertNotNull(communityManagerResponse);
//        assertEquals(200, communityManagerResponse.getStatusCode().intValue());
//        assertEquals("Application created successfully", communityManagerResponse.getStatusMessage());
//    }
//
//    @Test
//    public void createHttpApplicationNullValue() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInString = new String(readAllBytes(java.nio.file.Paths.get(new ClassPathResource("application.json").getURI())));
//        HttpModel httpModel = mapper.readValue(jsonInString, HttpModel.class);
//        httpModel.setProfileName(null);
//        mockMvc.perform(post("/pcm/application/http").contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(httpModel))).andExpect(status().is(400)).andReturn();
//    }
//
//
//    @Test
//    public void updateHttpApplication() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInString = new String(readAllBytes(java.nio.file.Paths.get(new ClassPathResource("application.json").getURI())));
//        HttpModel httpModel = mapper.readValue(jsonInString, HttpModel.class);
//        MvcResult mvcResult = mockMvc.perform(put("/pcm/application/http").contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(httpModel))).andExpect(status().isOk()).andReturn();
//        CommunityManagerResponse communityManagerResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), CommunityManagerResponse.class);
//        assertNotNull(communityManagerResponse);
//        assertEquals(200, communityManagerResponse.getStatusCode().intValue());
//        assertEquals(APPLICATION_UPDATE, communityManagerResponse.getStatusMessage());
//    }
//
//    @Test
//    public void updateHttpApplicationNullValue() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInString = new String(readAllBytes(java.nio.file.Paths.get(new ClassPathResource("application.json").getURI())));
//        HttpModel httpModel = mapper.readValue(jsonInString, HttpModel.class);
//        httpModel.setProfileName(null);
//        mockMvc.perform(put("/pcm/application/http").contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(httpModel))).andExpect(status().is(400)).andReturn();
//    }
//
//    @Test
//    public void getApplication() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        HttpModel httpModel = new HttpModel();
//        httpModel.setPkId("123456");
//        httpModel.setProfileName("JunitHOSTNAME");
//        when(httpApplicationService.get("123456")).thenReturn(httpModel);
//        MvcResult mvcResult = mockMvc.perform(get("/pcm/application/http/{pkId}",123456).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
//        HttpModel httpModel1 = mapper.readValue(mvcResult.getResponse().getContentAsString(), HttpModel.class);
//        assertEquals(httpModel.getProfileName(),httpModel1.getProfileName());
//    }
//
//    @Test
//    public void getApplicationWithOutId() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        mockMvc.perform(get("/pcm/application/http/{pkId}", (Object) null).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isMethodNotAllowed()).andReturn();
//
//    }
//
//    @Test
//    public void deleteApplication() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        MvcResult mvcResult = mockMvc.perform(delete("/pcm/application/http/{pkId}",123456).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
//        CommunityManagerResponse communityManagerResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), CommunityManagerResponse.class);
//        assertEquals(APPLICATION_DELETE, communityManagerResponse.getStatusMessage());
//    }
//}
