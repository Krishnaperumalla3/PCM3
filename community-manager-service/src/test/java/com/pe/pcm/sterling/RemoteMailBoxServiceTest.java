package com.pe.pcm.sterling;


import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.sterling.mailbox.entity.MbxMailBoxEntity;
import com.pe.pcm.sterling.mailbox.MbxMailBoxRepository;
import org.junit.jupiter.api.Assertions;
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

import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class RemoteMailBoxServiceTest {
    @MockBean
    private MbxMailBoxRepository mbxMailBoxRepository;

    @Test
    @DisplayName("Remote Mail Box Service")
    public void testFindAllByPath() {
        Mockito.when(mbxMailBoxRepository.findAllByPath(Mockito.anyString())).thenReturn(Optional.of(getMbxMailBoxEntities()));
        mbxMailBoxRepository.findAllByPath("Path");
        Mockito.verify(mbxMailBoxRepository, Mockito.times(1)).findAllByPath(Mockito.anyString());
    }

    @Test
    @DisplayName("Remote Mail Box Service1")
    public void testFindAllByPath1() {
        Mockito.when(mbxMailBoxRepository.findAllByPath(Mockito.anyString())).thenReturn(null);
        mbxMailBoxRepository.findAllByPath("path");
        Mockito.verify(mbxMailBoxRepository, Mockito.times(1)).findAllByPath(Mockito.anyString());
    }

    @Test
    @DisplayName("Get Id By Mail Box Name")
    public void testGetIdByMailBoxName() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(mbxMailBoxRepository.findAllByPath(Mockito.anyString())).thenThrow(notFound("Mailbox"));
            mbxMailBoxRepository.findAllByPath("path");
        });
        Mockito.verify(mbxMailBoxRepository, Mockito.times(1)).findAllByPath(Mockito.anyString());
    }

    MbxMailBoxEntity getMbxMailBoxEntity() {
        MbxMailBoxEntity mbxMailBoxEntity = new MbxMailBoxEntity();
        mbxMailBoxEntity.setDescription("description");
        mbxMailBoxEntity.setMailboxId(1234L);
        mbxMailBoxEntity.setParentId(123456L);
        mbxMailBoxEntity.setPath("path");
        return mbxMailBoxEntity;
    }

    List<MbxMailBoxEntity> getMbxMailBoxEntities() {
        List<MbxMailBoxEntity> mbxMailBoxEntity = new ArrayList<>();
        mbxMailBoxEntity.add(getMbxMailBoxEntity());
        return mbxMailBoxEntity;
    }

}
