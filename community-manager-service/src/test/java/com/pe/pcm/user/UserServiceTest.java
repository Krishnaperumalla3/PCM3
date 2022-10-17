package com.pe.pcm.user;

import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.mail.MailService;
import com.pe.pcm.miscellaneous.IndependentService;
import com.pe.pcm.miscellaneous.PasswordUtilityService;
import com.pe.pcm.miscellaneous.UserUtilityService;
import com.pe.pcm.user.entity.TpUserEntity;
import com.pe.pcm.user.entity.UserEntity;
import com.pe.pcm.utils.CommonFunctions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class UserServiceTest {
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private TpUserRepository tpUserRepository;
    @MockBean
    private MailService mailService;
    //@InjectMocks
    private UserService userService;
    @MockBean
    private PasswordUtilityService passwordUtilityService;
    @MockBean
    private UserUtilityService userUtilityService;
    @MockBean
    private IndependentService independentService;

    @MockBean
    private RoleRepository roleRepository;

    @BeforeEach
    void inIt() {
        userService = new UserService(userRepository, tpUserRepository, mailService,
                passwordUtilityService, userUtilityService, independentService, roleRepository);
    }

    @Test
    @DisplayName("Create User throws an User")
    public void testCreate() {
        // Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
        Mockito.when(independentService.getActiveProfile()).thenReturn("cm");
        Mockito.when(userRepository.findAllByUseridOrEmailOrExternalId(anyString(), anyString(), anyString()))
                .thenReturn(Optional.of(generateList1()));
        userService.create(getUserModel(), "test");
        //});
        Mockito.verify(userRepository, Mockito.never()).findById(anyString());
    }

    //TODO : update method test case is pending


    @Test
    @DisplayName("Create user with passing an valid data")
    public void testCreate2() {
        Mockito.when(userRepository.findById(anyString())).thenReturn(Optional.ofNullable(null));
        Mockito.when(independentService.getActiveProfile()).thenReturn("cm");
        userService.create(getUserModel(), "test");
        Mockito.verify(userRepository, Mockito.never()).findById(Mockito.any());
        Mockito.verify(mailService, Mockito.times(1)).sendActivationEmail(Mockito.any(), Mockito.any());
    }

    @Test
    @DisplayName("Status Change with passing an pkId ")
    public void testStatusChange() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            String pkId = "123456";
            userService.statusChange(pkId, false);
        });
    }

    @Test
    @DisplayName("Delete a userService with a passing valid data")
    public void testDelete() {
        String UserId = "123456";
        userService.delete(UserId);
        Mockito.verify(userRepository, Mockito.times(1)).findById(anyString());
        Mockito.verify(userRepository, Mockito.never()).delete(getUserEntity());
    }


    @Test
    @DisplayName("Delete a userService with a passing valid data1")
    public void testDelete2() {
        String UserId = "123456";
        userService.delete(UserId);
        Mockito.verify(tpUserRepository, Mockito.times(1)).findById(anyString());
        Mockito.verify(tpUserRepository, Mockito.never()).delete(getTpUserEntity());
    }

    @Test
    @DisplayName("Get User throwing a remote User Model")
    public void testGet1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(userRepository.findById(anyString())).thenThrow(notFound("RemoteUserModel"));
            Mockito.when(tpUserRepository.findById(anyString())).thenReturn(Optional.of(getTpUserEntity()));
            String userId = "123456";
            userService.get(userId);
        });
        Mockito.verify(userRepository, Mockito.times(1)).findById(anyString());
        Mockito.verify(tpUserRepository, Mockito.never()).findById(anyString());
    }

    @Test
    @DisplayName("Get User")
    public void testGet2() {
        Mockito.when(userRepository.findById(anyString())).thenReturn(Optional.of(getUserEntity()));
        Mockito.when(tpUserRepository.findById(anyString())).thenReturn(Optional.of(getTpUserEntity()));
        String userId = "123456";
        userService.get(userId);
        Mockito.verify(userRepository, Mockito.times(1)).findById(anyString());
        Mockito.verify(tpUserRepository, Mockito.times(1)).findById(anyString());
    }

    @Test
    @DisplayName("Search User")
    public void testSearchUsers() {
        Pageable pageable = Mockito.mock(Pageable.class);
        Mockito.when(userRepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class))).thenReturn(generateList());
        userService.search(getUserModel(), pageable);
        Mockito.verify(userRepository, Mockito.times(1)).findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class));
    }

    UserModel getUserModel() {
        UserModel userModel = new UserModel();
        userModel.setUserId("123456");
        userModel.setStatus(false);
        userModel.setEmail("Email@email.com");
        userModel.setFirstName("FirstName");
        userModel.setLastName("LastName");
        userModel.setPhone("42598718748");
        userModel.setUserRole("super_admin");
        userModel.setMiddleName("MiddleName");
        return userModel;
    }

    UserEntity getUserEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("Email@email.com");
        userEntity.setFirstName("FirstName");
        userEntity.setLastName("LastName");
        userEntity.setMiddleName("MiddleName");
        userEntity.setPassword("Password");
        userEntity.setPhone("359072987");
        userEntity.setRole("Role");
        userEntity.setStatus("N");
        userEntity.setUserid("123456");
        return userEntity;
    }

    TpUserEntity getTpUserEntity() {
        TpUserEntity tpUserEntity = new TpUserEntity();
        tpUserEntity.setGroupList("Group");
        tpUserEntity.setPartnerList("PartnerList");
        tpUserEntity.setUserid("UserId");
        return tpUserEntity;
    }

    Page<UserEntity> generateList() {
        List<UserEntity> li = new ArrayList<>();
        Page<UserEntity> pagedTasks = new PageImpl<>(li);
        li.add(getUserEntity());
        li.add(getUserEntity());
        return pagedTasks;
    }

    List<UserEntity> generateList1() {
        List<UserEntity> li = new ArrayList<>();
        li.add(getUserEntity());
        li.add(getUserEntity());
        return li;
    }

}
