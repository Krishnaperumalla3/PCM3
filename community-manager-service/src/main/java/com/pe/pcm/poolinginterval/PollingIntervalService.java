/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://pragmaedge.com/licenseagreement
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pe.pcm.poolinginterval;

import com.pe.pcm.common.CommunityManagerKeyValueModel;
import com.pe.pcm.poolinginterval.entity.PoolingIntervalEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import static com.pe.pcm.exception.GlobalExceptionHandler.badRequest;
import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;

/**
 * @author Chenchu Kiran.
 */
@Service
public class PollingIntervalService {

    private final PoolingIntervalRepository poolingIntervalRepository;

    @Autowired
    public PollingIntervalService(PoolingIntervalRepository poolingIntervalRepository) {
        this.poolingIntervalRepository = poolingIntervalRepository;
    }

    public List<PoolingIntervalModel> get() {
        List<PoolingIntervalModel> poolingIntervalModels = StreamSupport.stream(poolingIntervalRepository.findAll().spliterator(), false)
                .sorted(Comparator.comparing(PoolingIntervalEntity::getSeq))
                .map(poolingIntervalEntity -> {
                    PoolingIntervalModel poolingIntervalModel = new PoolingIntervalModel();
                    BeanUtils.copyProperties(poolingIntervalEntity, poolingIntervalModel);
                    return poolingIntervalModel;
                })
                .collect(Collectors.toList());
        if (poolingIntervalModels.isEmpty()) {
            throw notFound("poolingIntervals");
        }
        return poolingIntervalModels;
    }

    public List<CommunityManagerKeyValueModel> getList() {
        return StreamSupport.stream(poolingIntervalRepository.findAll().spliterator(), false)
                .sorted(Comparator.comparing(PoolingIntervalEntity::getSeq))
                .map(poolingIntervalEntity -> new CommunityManagerKeyValueModel(poolingIntervalEntity.getPkId(), poolingIntervalEntity.getInterval()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void save(List<PoolingIntervalModel> poolingIntervalModelList) {
        poolingIntervalRepository.deleteAll();
        IntStream.range(0, poolingIntervalModelList.size()).forEach(index -> {
            PoolingIntervalEntity poolingIntervalEntity = new PoolingIntervalEntity();
            BeanUtils.copyProperties(poolingIntervalModelList.get(index), poolingIntervalEntity);
            poolingIntervalEntity.setSeq(index + 1);
            poolingIntervalRepository.save(poolingIntervalEntity);
        });
    }

    public List<CommunityManagerKeyValueModel> getPoolingInterval(String pInterval) {
        List<CommunityManagerKeyValueModel> poolingIntervalList = new ArrayList<>();
        if (pInterval.endsWith("Minute")) {
            poolingIntervalList.add(new CommunityManagerKeyValueModel(pInterval, pInterval.replace("Minute", "")));
            return poolingIntervalList;
        }
        if (pInterval.endsWith("Minutes")) {
            poolingIntervalList.add(new CommunityManagerKeyValueModel(pInterval, pInterval.replace("Minutes", "")));
            return poolingIntervalList;
        }
        if (pInterval.endsWith("Hours")) {
            poolingIntervalList.add(new CommunityManagerKeyValueModel(pInterval, pInterval.replace("Hours", "H")));
            return poolingIntervalList;
        }
        if (pInterval.endsWith("Week")) {
            poolingIntervalList.add(new CommunityManagerKeyValueModel(pInterval, pInterval.replace("Week", "W")));
            return poolingIntervalList;
        }
        if (pInterval.endsWith("Days")) {
            poolingIntervalList.add(new CommunityManagerKeyValueModel(pInterval, pInterval.replace("Days", "D")));
            return poolingIntervalList;
        }
        if (pInterval.endsWith("Month")) {
            poolingIntervalList.add(new CommunityManagerKeyValueModel(pInterval, pInterval.replace("Month", "M")));
            return poolingIntervalList;
        }
        if (pInterval.endsWith("Months")) {
            poolingIntervalList.add(new CommunityManagerKeyValueModel(pInterval, pInterval.replace("Months", "M")));
            return poolingIntervalList;
        }
        if (pInterval.endsWith("OnArrival")) {
            poolingIntervalList.add(new CommunityManagerKeyValueModel(pInterval, pInterval.replace("OnArrival", "ON")));
            return poolingIntervalList;
        } else {
            throw badRequest("PoolingInterval");
        }
    }
}
