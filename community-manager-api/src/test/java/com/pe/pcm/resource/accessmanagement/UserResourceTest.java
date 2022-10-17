//package com.pe.pcm.resource.accessmanagement;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.pe.pcm.CommunityManagerApiApplication;
//import com.pe.pcm.common.CommunityManagerResponse;
//import com.pe.pcm.resource.accessmanagement.UserResource;
//import com.pe.pcm.resource.application.FtpApplicationResource;
//import com.pe.pcm.user.*;
//import com.pe.pcm.user.entity.UserEntity;
//import liquibase.pro.packaged.U;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentMatchers;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.domain.Page;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.util.LinkedMultiValueMap;
//
//@ExtendWith(SpringExtension.class)
//@ExtendWith(MockitoExtension.class)
//@ActiveProfiles("test")
//public class UserResourceTest {
//    private MockMvc mockMvc;
//
//    @MockBean
//    private UserService userService;
//
//
//  private UserResource userResource;
//
// private final ObjectMapper objectMapper = new ObjectMapper();
//
//  @BeforeEach
//    void inIt() {
//        userResource = new UserResource(userService);
//        mockMvc = MockMvcBuilders.standaloneSetup(userResource).build();
//    }
//
//    @Test
//   public void createUserUseCase1() throws Exception {
//                    UserModel userModel = generateUserModel();
//                    /*Mockito.when(userRepository.save(userEntity)).thenReturn(userEntity);*/
//                 /*   UserService userService =  Mockito.mock(UserService.class);
//                    doNothing().when(userService).create(userModel,"");
//                    userService.create(userModel,"");
//                    verify(userService,times(1)).create(userModel,"");*/
//                  //  objectMapper.writeValueAsString(userModel);
//        MvcResult mvcResult = mockMvc.perform(post("/pcm/user")
//                            .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(userModel)))
//                            .andExpect(status().isOk()).andReturn();
//            String response = mvcResult.getResponse().getContentAsString();
//        CommunityManagerResponse communityManagerResponse =    objectMapper.readValue(response, CommunityManagerResponse.class);
//        assertEquals("User created successfully",communityManagerResponse.getStatusMessage());
//   }
//
//   @Test
//    public void updateUserUseCase() throws Exception {
//        UserModel userModel = generateUserModel();
//        MvcResult mvcResult = mockMvc.perform(put("/pcm/user")
//                        .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(userModel)))
//                .andExpect(status().isOk()).andReturn();
//        String response = mvcResult.getResponse().getContentAsString();
//        CommunityManagerResponse communityManagerResponse =    objectMapper.readValue(response, CommunityManagerResponse.class);
//        assertEquals("User updated successfully",communityManagerResponse.getStatusMessage());
//    }
//
//    @Test
//    public void userStatusUpdateUseCase() throws Exception {
//        UserStatusModel userStatusModel = generateUserStatusModel();
//        MvcResult mvcResult = mockMvc.perform(post("/pcm/user/status")
//                        .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(userStatusModel)))
//                .andExpect(status().isOk()).andReturn();
//        String response = mvcResult.getResponse().getContentAsString();
//        CommunityManagerResponse communityManagerResponse =    objectMapper.readValue(response, CommunityManagerResponse.class);
//        assertEquals("Status updated successfully",communityManagerResponse.getStatusMessage());
//    }
//
//    @Test
//    public void deleteUserUseCase() throws Exception {
//        UserModel userModel = generateUserModel();
//
//        MvcResult mvcResult = mockMvc.perform(delete("/pcm/user/{userId}","123456")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk()).andReturn();
//        String response = mvcResult.getResponse().getContentAsString();
//        CommunityManagerResponse communityManagerResponse =    objectMapper.readValue(response, CommunityManagerResponse.class);
//        assertEquals("User deleted successfully",communityManagerResponse.getStatusMessage());
//    }
//
//
//    @Test
//    public void getUserUseCase() throws Exception {
//        UserModel userModel = generateUserModel();
//        UserEntity userEntity = new UserEntity();
//        userEntity.setUserid("123456");
//        when(userService.get(Mockito.anyString())).thenReturn(userModel);
//                String result =   mockMvc.perform(get("/pcm/user/{userId}",123456)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
//     UserModel userModel1 = objectMapper.readValue(result,UserModel.class);
//     assertEquals("123456",userModel1.getUserId());
//    }
//
//    @Test
//    public void changePasswordUserUsecase() throws Exception {
//        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
//        requestParams.add("pkId", "1");
//        requestParams.add("oldPassword", "john");
//        requestParams.add("newPassword", "smith");
//        String result =   mockMvc.perform(post("/pcm/user/change-password").params(requestParams)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
//        CommunityManagerResponse communityManagerResponse = objectMapper.readValue(result,CommunityManagerResponse.class);
//        assertEquals("Password Changed Successfully",communityManagerResponse.getStatusMessage());
//    }
//
//    @Test
//    public void UpdateUserLangUseCase() throws Exception {
//        UserLangModel userLangModel = new UserLangModel();
//        MvcResult mvcResult = mockMvc.perform(post("/pcm/user/lang")
//                        .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(userLangModel)))
//                .andExpect(status().isOk()).andReturn();
//        String response = mvcResult.getResponse().getContentAsString();
//        CommunityManagerResponse communityManagerResponse =    objectMapper.readValue(response, CommunityManagerResponse.class);
//        assertEquals("Language updated successfully",communityManagerResponse.getStatusMessage());
//    }
//
//    @Test
//    public void searchUserUseCase() throws Exception {
//        UserModel userModel = generateUserModel();
//        UserEntity userEntity = new UserEntity();
//        when(userService.search(Mockito.any(),Mockito.any())).thenReturn((Page<UserEntity>) userEntity);
//        MvcResult mvcResult = mockMvc.perform(post("/pcm/user/search")
//                        .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(userModel)))
//                .andExpect(status().isOk()).andReturn();
//        String response = mvcResult.getResponse().getContentAsString();
//        CommunityManagerResponse communityManagerResponse =    objectMapper.readValue(response, CommunityManagerResponse.class);
//        assertEquals("Language updated successfully",communityManagerResponse.getStatusMessage());
//    }
//
//
//  UserModel generateUserModel() {
//       UserModel userModel = new UserModel();
//     userModel.setUserId("123456");
//       userModel.setEmail("email@email.com");
//       userModel.setFirstName("firstname");
//       userModel.setLastName("lastname");
//       userModel.setUserRole("userrole");
//        userModel.setPhone("654321");
//        userModel.setStatus(false);
//        return userModel;
//  }
//
// UserStatusModel generateUserStatusModel(){
//
//        UserStatusModel userStatusModel = new UserStatusModel();
//        userStatusModel.setStatus(true);
//        userStatusModel.setPkId("statusPKID");
//
//        return userStatusModel;
//  }
//}
