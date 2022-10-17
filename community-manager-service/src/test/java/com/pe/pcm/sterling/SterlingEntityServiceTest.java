package com.pe.pcm.sterling;


import com.pe.pcm.common.CommunityManagerNameModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class SterlingEntityServiceTest {
    @MockBean
    private WfdRepository wfdRepository;
    @MockBean
    private MapRepository mapRepository;

    @Test
    @DisplayName("Get All BPs")
    public void testGetAllBps() {
        Mockito.when(wfdRepository.findDistinctOrderByName()).thenReturn(Optional.of(getString()));
        wfdRepository.findDistinctOrderByName();
        Mockito.verify(wfdRepository, Mockito.times(1)).findDistinctOrderByName();
    }

    @Test
    @DisplayName("Get All BPs1")
    public void testGetAllBps1() {
        Mockito.when(wfdRepository.findDistinctOrderByName()).thenReturn(Optional.of(new ArrayList<>()));
        wfdRepository.findDistinctOrderByName();
        Mockito.verify(wfdRepository, Mockito.times(1)).findDistinctOrderByName();
    }

    @Test
    @DisplayName("Get All Maps")
    public void testGetAllMaps() {
        Mockito.when(mapRepository.findAllByOrderByMapIdentity()).thenReturn(Optional.of(getString()));
        mapRepository.findAllByOrderByMapIdentity();
        Mockito.verify(mapRepository, Mockito.times(1)).findAllByOrderByMapIdentity();
    }

    @Test
    @DisplayName("Get All Maps1")
    public void testGetAllMaps1() {
        Mockito.when(mapRepository.findAllByOrderByMapIdentity()).thenReturn(Optional.of(new ArrayList<>()));
        mapRepository.findAllByOrderByMapIdentity();
        Mockito.verify(mapRepository, Mockito.times(1)).findAllByOrderByMapIdentity();
    }


    CommunityManagerNameModel generateCommunityManagerListModel() {
        CommunityManagerNameModel communityManagerNameModel = new CommunityManagerNameModel("Name");
        communityManagerNameModel.setName("Name");
        return communityManagerNameModel;
    }

    List<CommunityManagerNameModel> getCommunityManagerListModel() {
        List<CommunityManagerNameModel> communityManagerNameModel = new ArrayList<>();
        communityManagerNameModel.add(generateCommunityManagerListModel());
        return communityManagerNameModel;
    }

    List<String> getString() {
        List<String> strings = new ArrayList<>();
        return strings;
    }
}
