//package com.pe.pcm.resource.application;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.pe.pcm.application.SapApplicationService;
//import com.pe.pcm.common.CommunityManagerResponse;
//import com.pe.pcm.protocol.SapModel;
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
//import static com.pe.pcm.utils.PCMConstants.APPLICATION_UPDATE;
//import static java.nio.file.Files.readAllBytes;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(SpringExtension.class)
//@ExtendWith(MockitoExtension.class)
//@ActiveProfiles("test")
//public class SapApplicationResourceTest {
//
//    @MockBean
//    private SapApplicationService sapApplicationService;
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    void inIt() {
//        SapApplicationResource sapApplicationResource = new SapApplicationResource(sapApplicationService);
//        mockMvc = MockMvcBuilders.standaloneSetup(sapApplicationResource).build();
//    }
//
//    @Test
//    public void createWebApplication() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInString = new String(readAllBytes(java.nio.file.Paths.get(new ClassPathResource("application.json").getURI())));
//        SapModel sapModel = mapper.readValue(jsonInString, SapModel.class);
//        sapModel.setSapRoute("JUNITROUTE");
//        sapModel.setAdapterName("JUNITADAPTERNAME");
//        MvcResult mvcResult = mockMvc.perform(post("/pcm/application/sap").contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(sapModel))).andExpect(status().isOk()).andReturn();
//        String communityManagerResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), String.class);
//        assertNotNull(communityManagerResponse);
//       /* assertEquals(200, communityManagerResponse.getStatusCode().intValue());
//        assertEquals("Application created successfully", communityManagerResponse.getStatusMessage());*/
//    }
//
//    @Test
//    public void createApplicationWithNoProfileName() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInString = new String(readAllBytes(java.nio.file.Paths.get(new ClassPathResource("application.json").getURI())));
//        SapModel sapModel = mapper.readValue(jsonInString, SapModel.class);
//        sapModel.setProfileName(null);
//        MvcResult mvcResult = mockMvc.perform(post("/pcm/application/sap").contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(sapModel))).andExpect(status().is(400)).andReturn();
//    }
//
//    @Test
//    public void updateHttpApplication() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInString = new String(readAllBytes(java.nio.file.Paths.get(new ClassPathResource("application.json").getURI())));
//        SapModel sapModel = mapper.readValue(jsonInString, SapModel.class);
//        sapModel.setAdapterName("adapter");
//        sapModel.setSapRoute("sapRoute");
//        MvcResult mvcResult = mockMvc.perform(put("/pcm/application/sap").contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(sapModel))).andExpect(status().isOk()).andReturn();
//        CommunityManagerResponse communityManagerResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), CommunityManagerResponse.class);
//        assertNotNull(communityManagerResponse);
//        assertEquals(200, communityManagerResponse.getStatusCode().intValue());
//        assertEquals(APPLICATION_UPDATE, communityManagerResponse.getStatusMessage());
//    }
//}
