/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc.
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

package com.pe.pcm.b2b;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pe.pcm.b2b.deserialize.RemoteUserDeserializeModel;
import com.pe.pcm.b2b.usermailbox.RemoteUserModel;
import com.pe.pcm.login.YfsUserRepository;
import com.pe.pcm.login.entity.YfsUserEntity;
import com.pe.pcm.pem.PemAccountExpiryModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;
import static com.pe.pcm.generator.PasswordGenerator.generateValidPassword;
import static com.pe.pcm.utils.CommonFunctions.isNotNull;

/**
 * @author Kiran Reddy.
 */
@Service
public class B2BUserService {

    private final B2BApiService b2BApiService;
    private final YfsUserRepository yfsUserRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public B2BUserService(B2BApiService b2BApiService, YfsUserRepository yfsUserRepository) {
        this.b2BApiService = b2BApiService;
        this.yfsUserRepository = yfsUserRepository;
    }

    public void restUserAccountsByB2BiApiPatch(String password, String userName, boolean updatePwdLastChangeDone) {
        password = isNotNull(password) ? password : generateValidPassword();
        b2BApiService.updateUserInSIWithPatch(password, userName);
        if (updatePwdLastChangeDone) {
            yfsUserRepository.findFirstByUsername(userName.trim()).ifPresent(yfsUserEntity -> {
                YfsUserEntity yfsUserEntity1 = new YfsUserEntity();
                BeanUtils.copyProperties(yfsUserEntity, yfsUserEntity1);
                yfsUserEntity1.setPwdlastchangedon(new Timestamp(new Date().getTime()));
                yfsUserEntity1.setModifyuserid("CM-API");
                yfsUserRepository.save(yfsUserEntity1);
            });
        }
    }

    public void restUserAccountsByB2BiApi(PemAccountExpiryModel pemAccountExpiryModel, boolean updatePwdLastChangeDone) {
        RemoteUserDeserializeModel remoteUserDeserializeModel;
        try {
            remoteUserDeserializeModel = objectMapper.readValue(b2BApiService.getUserFromSI(pemAccountExpiryModel.getUserName()), RemoteUserDeserializeModel.class);
        } catch (JsonProcessingException je) {
            throw internalServerError(je.getMessage());
        }
        String password;
        if (isNotNull(pemAccountExpiryModel.getPass())) {
            password = pemAccountExpiryModel.getPass();
        } else {
            password = generateValidPassword();
        }
        RemoteUserModel remoteUserModel = B2BFunctions.userDsModelToUserMod.apply(remoteUserDeserializeModel);
        remoteUserModel.setPassword(password);
        b2BApiService.updateUserInSI(remoteUserModel);
        if (updatePwdLastChangeDone) {
            yfsUserRepository.findFirstByUsername(pemAccountExpiryModel.getUserName().trim()).ifPresent(yfsUserEntity -> {
                YfsUserEntity yfsUserEntity1 = new YfsUserEntity();
                BeanUtils.copyProperties(yfsUserEntity, yfsUserEntity1);
                yfsUserEntity1.setPwdlastchangedon(new Timestamp(new Date().getTime()));
                yfsUserEntity1.setModifyuserid("CM-API");
                yfsUserRepository.save(yfsUserEntity1);
            });
        }

    }
}
