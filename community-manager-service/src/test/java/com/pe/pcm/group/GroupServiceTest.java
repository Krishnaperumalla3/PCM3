package com.pe.pcm.group;


import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.miscellaneous.UserUtilityService;
import com.pe.pcm.user.GroupRepository;
import com.pe.pcm.user.TpUserRepository;
import com.pe.pcm.user.entity.GroupEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;
import static com.pe.pcm.constants.AuthoritiesConstants.SUPER_ADMIN;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class GroupServiceTest {

    @Mock
    private GroupRepository grouprepository;
    @Mock
    private UserUtilityService userUtilityService;
    @Mock
    private TpUserRepository tpUserRepository;
    //@InjectMocks
    private GroupService groupService;

    @BeforeEach
    void inIt() {
        groupService = new GroupService(grouprepository,userUtilityService,tpUserRepository);
    }

    @Test
    @DisplayName("Create Group")
    void testCreate1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(grouprepository.findByGroupname(Mockito.anyString())).thenThrow(notFound("Group"));
            groupService.create(generateGroupModel());
        });
        Mockito.verify(grouprepository, Mockito.times(1)).findByGroupname(Mockito.anyString());
    }

    @Test
    @DisplayName("Create Group with an valid data")
    void testCreate() {
        Mockito.when(grouprepository.findByGroupname(Mockito.anyString())).thenReturn(Optional.ofNullable(null));
        groupService.create(generateGroupModel());
        Mockito.verify(grouprepository, Mockito.times(1)).findByGroupname(Mockito.anyString());
    }


    @Test
    @DisplayName("Update Group")
    void testUpdate() {
        Mockito.when(grouprepository.save(Mockito.any())).thenReturn(getGroupEntity());
        groupService.update(generateGroupModel());
        Mockito.verify(grouprepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Search Group")
    void testSearch() {
        Pageable pageable = Mockito.mock(Pageable.class);
        Mockito.when(grouprepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class))).thenReturn(generateList());
        groupService.search(generateGroupModel(), pageable);
        Mockito.verify(grouprepository, Mockito.times(1)).findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class));
    }


    @Test
    @DisplayName("Delete group ")
    void testDelete1() {
        Mockito.when(grouprepository.findById(Mockito.anyString())).thenReturn(Optional.of(getGroupEntity()));
        String pkId = "1234445";
        groupService.delete(pkId);
        Mockito.verify(grouprepository, Mockito.times(1)).findById(Mockito.anyString());
        Mockito.verify(grouprepository, Mockito.times(1)).delete(Mockito.any());
    }


    @Test
    @DisplayName("Delete Group passing null")
    void testDelete() {
        groupService.delete(null);
        Mockito.verify(grouprepository, Mockito.never()).delete(Mockito.any());

    }

    @Test
    @DisplayName("Get Group Repository throws a Group")
    void testGet1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(grouprepository.findById(Mockito.anyString())).thenThrow(notFound("Group"));
            String pkId = "123456";
            groupService.get(pkId);
        });
        Mockito.verify(grouprepository, Mockito.times(1)).findById(Mockito.anyString());
    }

    @Test
    @DisplayName("Get Group")
    void testGet() {
        Mockito.when(grouprepository.findById(Mockito.anyString())).thenReturn(Optional.of(getGroupEntity()));
        String pkId = "123456";
        groupService.get(pkId);
        Mockito.verify(grouprepository, Mockito.times(1)).findById(Mockito.anyString());
    }

    @Test
    @DisplayName("List of Group Map")
    void testGroupMap() {
        Mockito.when(grouprepository.findAllByOrderByGroupnameAsc()).thenReturn(generateList2());
        Mockito.when(userUtilityService.getUserOrRole(Mockito.anyBoolean())).thenReturn(SUPER_ADMIN);
        groupService.getGroupsMap();
        Mockito.verify(grouprepository, Mockito.times(1)).findAllByOrderByGroupnameAsc();
    }

    @Test
    @DisplayName("Find ALL Id's ")
    void testFindAllIdsIn() {
        Mockito.when(grouprepository.findAllByPkIdIn(Mockito.anyList())).thenReturn(Optional.of(generateList2()));
        groupService.findAllIdsIn(Mockito.anyList());
        Mockito.verify(grouprepository, Mockito.times(1)).findAllByPkIdIn(Mockito.anyList());

    }

    GroupModel generateGroupModel() {
        GroupModel groupModel = new GroupModel();
        groupModel.setPkId("123456");
        groupModel.setGroupName("Group");
        groupModel.setPartnerList(Collections.singletonList("p1,p2,p3"));
        return groupModel;
    }

    GroupEntity getGroupEntity() {
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setPkId("123456");
        groupEntity.setGroupname("GroupName");
        groupEntity.setPartnerList("Partner");
        groupEntity.setLastUpdatedBy("LastUpdateBy");
        return groupEntity;
    }

    GroupEntity getGroupEntity1() {
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setPkId("123456");
        groupEntity.setGroupname("GroupName");
        groupEntity.setPartnerList("Partner");
        groupEntity.setLastUpdatedBy("LastUpdateBy");
        return groupEntity;
    }

    Page<GroupEntity> generateList() {
        List<GroupEntity> Li = new ArrayList<>();
        Page<GroupEntity> pagedTasks = new PageImpl<>(Li);
        Li.add(getGroupEntity());
        Li.add(getGroupEntity1());
        return pagedTasks;
    }


    List<GroupEntity> generateList2() {
        List<GroupEntity> li = new ArrayList<>();
        li.add(getGroupEntity());
        li.add(getGroupEntity1());
        return li;
    }


}

