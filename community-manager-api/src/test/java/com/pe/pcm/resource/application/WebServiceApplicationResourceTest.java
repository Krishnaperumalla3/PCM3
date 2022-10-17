//package com.pe.pcm.resource.application;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.pe.pcm.application.ApplicationModel;
//import com.pe.pcm.application.WsApplicationService;
//import com.pe.pcm.common.CommunityManagerResponse;
//import com.pe.pcm.protocol.HttpModel;
//import com.pe.pcm.protocol.WebserviceModel;
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
//public class WebServiceApplicationResourceTest {
//
//    @MockBean
//    private WsApplicationService wsApplicationService;
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    void inIt() {
//        WebServiceApplicationResource webServiceApplicationResource = new WebServiceApplicationResource(wsApplicationService);
//        mockMvc = MockMvcBuilders.standaloneSetup(webServiceApplicationResource).build();
//    }
//
//    @Test
//    public void createWebApplication() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInString = new String(readAllBytes(java.nio.file.Paths.get(new ClassPathResource("application.json").getURI())));
//        WebserviceModel webserviceModel = mapper.readValue(jsonInString, WebserviceModel.class);
//        webserviceModel.setDeleteAfterCollection(false);
//        MvcResult mvcResult = mockMvc.perform(post("/pcm/application/webservice").contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(webserviceModel))).andExpect(status().isOk()).andReturn();
//        CommunityManagerResponse communityManagerResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), CommunityManagerResponse.class);
//        assertNotNull(communityManagerResponse);
//        assertEquals(200, communityManagerResponse.getStatusCode().intValue());
//        assertEquals("Application created successfully", communityManagerResponse.getStatusMessage());
//    }
//
//    @Test
//    public void createApplicationWithNoProfileName() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInString = new String(readAllBytes(java.nio.file.Paths.get(new ClassPathResource("application.json").getURI())));
//        WebserviceModel webserviceModel = mapper.readValue(jsonInString, WebserviceModel.class);
//        webserviceModel.setProfileName(null);
//        MvcResult mvcResult = mockMvc.perform(post("/pcm/application/webservice").contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(webserviceModel))).andExpect(status().is(400)).andReturn();
//    }
//
//    @Test
//    public void updateHttpApplication() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInString = new String(readAllBytes(java.nio.file.Paths.get(new ClassPathResource("application.json").getURI())));
//        WebserviceModel webserviceModel = mapper.readValue(jsonInString, WebserviceModel.class);
//        MvcResult mvcResult = mockMvc.perform(put("/pcm/application/webservice").contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(webserviceModel))).andExpect(status().isOk()).andReturn();
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
//        WebserviceModel webserviceModel = mapper.readValue(jsonInString, WebserviceModel.class);
//        webserviceModel.setProfileName(null);
//        mockMvc.perform(put("/pcm/application/webservice").contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(webserviceModel))).andExpect(status().is(400)).andReturn();
//    }
//
//    @Test
//    public void getApplication() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        WebserviceModel webserviceModel = new WebserviceModel();
//        webserviceModel.setPkId("123456");
//        webserviceModel.setProfileName("JunitHOSTNAME");
//        when(wsApplicationService.get("123456")).thenReturn(webserviceModel);
//        MvcResult mvcResult = mockMvc.perform(get("/pcm/application/webservice/{pkId}",123456).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
//        HttpModel httpModel1 = mapper.readValue(mvcResult.getResponse().getContentAsString(), HttpModel.class);
//        assertEquals(webserviceModel.getProfileName(),httpModel1.getProfileName());
//    }
//
//    @Test
//    public void getApplicationWithOutId() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        mockMvc.perform(get("/pcm/application/webservice/{pkId}", (Object) null).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isMethodNotAllowed()).andReturn();
//
//    }
//
//    @Test
//    public void deleteApplication() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        MvcResult mvcResult = mockMvc.perform(delete("/pcm/application/webservice/{pkId}",123456).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
//        CommunityManagerResponse communityManagerResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), CommunityManagerResponse.class);
//        assertEquals(APPLICATION_DELETE, communityManagerResponse.getStatusMessage());
//    }
//
//}
