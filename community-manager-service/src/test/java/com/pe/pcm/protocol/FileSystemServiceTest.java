package com.pe.pcm.protocol;

import com.pe.pcm.protocol.filesystem.FileSystemRepository;
import com.pe.pcm.protocol.filesystem.entity.FileSystemEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class FileSystemServiceTest {
    @MockBean
    private FileSystemRepository fileSystemRepository;
    @InjectMocks
    private FileSystemService fileSystemService;

    @Test
    @DisplayName("Save File System")
    public void testSave() {
        fileSystemService.save(getFileSystemEntity());
        Mockito.verify(fileSystemRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Save Protocol File System")
    public void testSaveProtocol() {
        Mockito.when(fileSystemRepository.findBySubscriberId(Mockito.anyString())).thenReturn(Optional.of(getFileSystemEntity()));
        fileSystemService.saveProtocol(generateFileSystemModel("FileSystem"), "1", "2", "Sub");
        Mockito.verify(fileSystemRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Get File System")
    public void testGet() {
        Mockito.when(fileSystemRepository.findBySubscriberId(Mockito.anyString())).thenReturn(Optional.of(getFileSystemEntity()));
        String pkId = "123456";
        fileSystemService.get(pkId);
        Mockito.verify(fileSystemRepository, Mockito.times(1)).findBySubscriberId(Mockito.anyString());
    }

    @Test
    @DisplayName("Delete File System")
    public void testDelete() {
        Mockito.when(fileSystemRepository.findBySubscriberId(Mockito.anyString())).thenReturn(Optional.of(getFileSystemEntity()));
        String pkId = "123456";
        fileSystemService.delete(pkId);
        Mockito.verify(fileSystemRepository, Mockito.times(1)).findBySubscriberId(Mockito.anyString());
        Mockito.verify(fileSystemRepository, Mockito.times(1)).delete(Mockito.any());
    }

    FileSystemEntity getFileSystemEntity() {
        FileSystemEntity fileSystemEntity = new FileSystemEntity();
        fileSystemEntity.setPassword("password");
        fileSystemEntity.setPkId("123456");
        fileSystemEntity.setIsActive("Active");
        fileSystemEntity.setPoolingIntervalMins("poolingInterval");
        fileSystemEntity.setDeleteAfterCollection("Delete");
        return fileSystemEntity;
    }

    FileSystemModel generateFileSystemModel(String protocol) {
        FileSystemModel fileSystemModel = new FileSystemModel();
        fileSystemModel.setFileType("FileType");
        fileSystemModel.setUserName("UserName");
        fileSystemModel.setAdapterName("AdapterName");
        fileSystemModel.setPoolingInterval("poolingInterval");
        fileSystemModel.setDeleteAfterCollection(false);
        fileSystemModel.setProtocol(protocol);
        fileSystemModel.setHubInfo(false);
        fileSystemModel.setStatus(false);
        return fileSystemModel;
    }
}
