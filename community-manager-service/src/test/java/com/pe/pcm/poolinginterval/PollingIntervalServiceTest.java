package com.pe.pcm.poolinginterval;

import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.poolinginterval.entity.PoolingIntervalEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class PollingIntervalServiceTest {
    @MockBean
    private PoolingIntervalRepository poolingIntervalRepository;
    //@InjectMocks
    private PollingIntervalService pollingIntervalService;

    @BeforeEach
    void inIt() {
        pollingIntervalService = new PollingIntervalService(poolingIntervalRepository);
    }

    @Test
    @DisplayName("Get Pooling Interval")
    public void testGet() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(poolingIntervalRepository.findAll()).thenThrow(notFound("poolingIntervals"));
            pollingIntervalService.get();
        });
        Mockito.verify(poolingIntervalRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Get Pooling Interval1")
    public void testGet1() {
        Mockito.when(poolingIntervalRepository.findAll()).thenReturn(getPoolingIntervalEntities());
        pollingIntervalService.get();
        Mockito.verify(poolingIntervalRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Get List Pooling Interval")
    public void testGetList() {
        pollingIntervalService.getList();
        Mockito.verify(poolingIntervalRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Save Pooling Interval")
    public void testSave() {
        pollingIntervalService.save(generatePoolingIntervalModel1());
        Mockito.verify(poolingIntervalRepository, Mockito.times(1)).deleteAll();
        Mockito.verify(poolingIntervalRepository, Mockito.times(1)).save(Mockito.any());
    }

    List<PoolingIntervalModel> generatePoolingIntervalModel1() {
        List<PoolingIntervalModel> poolingIntervalEntities = new ArrayList<>();
        poolingIntervalEntities.add(generatePoolingIntervalModel());
        return poolingIntervalEntities;
    }

    PoolingIntervalModel generatePoolingIntervalModel() {
        PoolingIntervalModel poolingIntervalModel = new PoolingIntervalModel();
        poolingIntervalModel.setPkId("123456");
        return poolingIntervalModel;
    }

    PoolingIntervalEntity getPoolingIntervalEntity0() {
        PoolingIntervalEntity poolingIntervalEntity = new PoolingIntervalEntity();
        poolingIntervalEntity.setSeq(0);
        poolingIntervalEntity.setPkId("123456");
        poolingIntervalEntity.setDescription("Description");
        return poolingIntervalEntity;
    }


    PoolingIntervalEntity getPoolingIntervalEntity1() {
        PoolingIntervalEntity poolingIntervalEntity = new PoolingIntervalEntity();
        poolingIntervalEntity.setSeq(1);
        poolingIntervalEntity.setPkId("123456");
        poolingIntervalEntity.setDescription("Description");
        return poolingIntervalEntity;
    }

    PoolingIntervalEntity getPoolingIntervalEntity2() {
        PoolingIntervalEntity poolingIntervalEntity = new PoolingIntervalEntity();
        poolingIntervalEntity.setSeq(2);
        poolingIntervalEntity.setPkId("123456");
        poolingIntervalEntity.setDescription("Description");
        return poolingIntervalEntity;
    }

    List<PoolingIntervalEntity> getPoolingIntervalEntities() {
        List<PoolingIntervalEntity> poolingIntervalEntities = new ArrayList<>();
        poolingIntervalEntities.add(getPoolingIntervalEntity0());
        poolingIntervalEntities.add(getPoolingIntervalEntity1());
        poolingIntervalEntities.add(getPoolingIntervalEntity2());
        return poolingIntervalEntities;
    }
}
