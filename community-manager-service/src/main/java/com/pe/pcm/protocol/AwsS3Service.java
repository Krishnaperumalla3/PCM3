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

package com.pe.pcm.protocol;

import com.pe.pcm.protocol.awss3.AwsS3Repository;
import com.pe.pcm.protocol.awss3.entity.AwsS3Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;
import static com.pe.pcm.protocol.function.ProtocolFunctions.mapperToAwsS3Entity;

/**
 * @author Shameer.
 */
@Service
public class AwsS3Service {

    private final AwsS3Repository awsS3Repository;

    @Autowired
    public AwsS3Service(AwsS3Repository awsS3Repository) {
        this.awsS3Repository = awsS3Repository;
    }

    public AwsS3Entity save(AwsS3Entity awsS3Entity) {
        return awsS3Repository.save(awsS3Entity);
    }

    public AwsS3Entity get(String parentPkId) {
        return awsS3Repository.findBySubscriberId(parentPkId).orElseThrow(() -> notFound("Protocol"));
    }

    public void delete(String parentPkId) {
        awsS3Repository.findBySubscriberId(parentPkId).ifPresent(awsS3Repository::delete);
    }

    @Transactional
    public AwsS3Entity saveProtocol(AwsS3Model awsS3Model, String parentPrimaryKey, String childPrimaryKey, String subscriberType) {
        AwsS3Entity awsS3Entity = mapperToAwsS3Entity.apply(awsS3Model);
        awsS3Entity.setPkId(childPrimaryKey)
                .setSubscriberType(subscriberType)
                .setSubscriberId(parentPrimaryKey);
        return save(awsS3Entity);
    }

    public List<AwsS3Entity> findAllBySubscriberId(List<String> subIds) {
        return awsS3Repository.findAllBySubscriberIdIn(subIds).orElse(new ArrayList<>());
    }
}
