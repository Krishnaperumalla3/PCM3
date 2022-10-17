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

import com.pe.pcm.protocol.oracleebs.OracleEBSRepository;
import com.pe.pcm.protocol.oracleebs.entity.OracleEbsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static com.pe.pcm.protocol.function.ProtocolFunctions.mapperToOracleEBSEntity;
import javax.transaction.Transactional;

import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;

/**
 * @author Shameer.
 *
 */

@Service
public class OracleEbsService {

    private OracleEBSRepository oracleEBSRepository;

    @Autowired
    public OracleEbsService(OracleEBSRepository oracleEBSRepository) {this.oracleEBSRepository = oracleEBSRepository;}

    public OracleEbsEntity save(OracleEbsEntity oracleEBSEntity){return oracleEBSRepository.save(oracleEBSEntity);}

    public OracleEbsEntity get(String pkId){return oracleEBSRepository.findBySubscriberId(pkId).orElseThrow(() -> notFound("Protocol"));}

    public void delete(String pkId){oracleEBSRepository.findBySubscriberId(pkId).ifPresent(smtpEntity -> oracleEBSRepository.delete(smtpEntity));}

    @Transactional
    public OracleEbsEntity saveProtocol(OracleEbsModel oracleEBSModel, String parentPrimaryKey, String childPrimaryKey, String subscriberType) {
        OracleEbsEntity oracleEBSEntity = mapperToOracleEBSEntity.apply(oracleEBSModel);
        oracleEBSEntity.setPkId(childPrimaryKey)
                .setSubscriberType(subscriberType)
                .setSubscriberId(parentPrimaryKey);
        return save(oracleEBSEntity);
    }
}
