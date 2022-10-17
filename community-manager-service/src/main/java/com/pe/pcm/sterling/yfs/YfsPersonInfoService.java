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

package com.pe.pcm.sterling.yfs;

import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.sterling.dto.YfsPersonInfoDTO;
import com.pe.pcm.sterling.yfs.entity.YfsPersonInfoEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;
import java.util.function.Function;

import static com.pe.pcm.utils.CommonFunctions.isNotNull;
import static com.pe.pcm.utils.PCMConstants.SPACE;

/**
 * @author Kiran Reddy.
 */
@Service
public class YfsPersonInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YfsPersonInfoService.class);

    private final YfsPersonInfoRepository yfsPersonInfoRepository;

    @Autowired
    public YfsPersonInfoService(YfsPersonInfoRepository yfsPersonInfoRepository) {
        this.yfsPersonInfoRepository = yfsPersonInfoRepository;
    }

    public void save(YfsPersonInfoDTO yfsPersonInfoDTO) {
        yfsPersonInfoRepository.save(serialize.apply(yfsPersonInfoDTO));
    }

    public void save(YfsPersonInfoEntity yfsPersonInfoEntity) {
        LOGGER.info("Create/Update YfsPersonInfoEntity.");
        yfsPersonInfoRepository.save(yfsPersonInfoEntity);
    }

    public Optional<YfsPersonInfoEntity> findById(String personInfoKey) {
        return yfsPersonInfoRepository.findById(personInfoKey);
    }

    public YfsPersonInfoDTO getYfsPersonInfoDTO(String personInfoKey) {
        return deSerialize.apply(findById(personInfoKey).orElseThrow(() -> GlobalExceptionHandler.internalServerError("YfsPersonInfo Entity notfound, key : " + personInfoKey)));
    }

    public void delete(String personInfoKey) {
        LOGGER.info("Delete YfsPersonInfoEntity.");
        yfsPersonInfoRepository.deleteById(personInfoKey);
    }

    private final Function<YfsPersonInfoDTO, YfsPersonInfoEntity> serialize = yfsPersonInfoDTO -> {
        YfsPersonInfoEntity yfsPersonInfoEntity = new YfsPersonInfoEntity();
        yfsPersonInfoEntity.setLockid(0);
        yfsPersonInfoEntity.setCreateuserid("PCM-API");
        yfsPersonInfoEntity.setModifyuserid("PCM-API");
        yfsPersonInfoEntity.setCreateprogid(" ");
        yfsPersonInfoEntity.setModifyprogid(" ");

        return yfsPersonInfoEntity.setPersonInfoKey(yfsPersonInfoDTO.getPersonInfoKey())
                .setEmailId(yfsPersonInfoDTO.getEmailId())
                .setAddressLine1(isNotNull(yfsPersonInfoDTO.getAddressLine1()) ? yfsPersonInfoDTO.getAddressLine1() : " ")
                .setAddressLine2(isNotNull(yfsPersonInfoDTO.getAddressLine2()) ? yfsPersonInfoDTO.getAddressLine2() : " ")
                .setCity(isNotNull(yfsPersonInfoDTO.getCity()) ? yfsPersonInfoDTO.getCity() : " ")
                .setState(isNotNull(yfsPersonInfoDTO.getState()) ? yfsPersonInfoDTO.getState() : " ")
                .setZipCode(isNotNull(yfsPersonInfoDTO.getZipCode()) ? yfsPersonInfoDTO.getZipCode() : " ")
                .setCountry(isNotNull(yfsPersonInfoDTO.getCountry()) ? yfsPersonInfoDTO.getCountry() : " ")
                .setDayPhone(isNotNull(yfsPersonInfoDTO.getDayPhone()) ? yfsPersonInfoDTO.getDayPhone() : " ")
                .setPersonId(" ")
                .setTitle(" ")
                .setFirstName(isNotNull(yfsPersonInfoDTO.getFirstName()) ? yfsPersonInfoDTO.getFirstName() : " ")
                .setMiddleName(" ")
                .setLastName(isNotNull(yfsPersonInfoDTO.getLastName()) ? yfsPersonInfoDTO.getLastName() : " ")
                .setSuffix(SPACE)
                .setDepartment(" ")
                .setCompany(" ")
                .setJobTitle(" ")
                .setAddressLine3(" ")
                .setAddressLine4(" ")
                .setAddressLine5(" ")
                .setAddressLine6(" ")
                .setEveningPhone(" ")
                .setMobilePhone(" ")
                .setBeeper(" ")
                .setOtherPhone(" ")
                .setDayFaxNo(" ")
                .setEveningFaxNo(" ")
                .setAlternateEmailId(" ")
                .setPreferredShipAddress(" ")
                .setHttpUrl(" ")
                .setUseCount("0")
                .setVerificationStatus(" ")
                .setErrorTxt(" ")
                .setAddressLine7(" ")
                .setBirthDate(isNotNull(yfsPersonInfoDTO.getBirthDate()) ? new Date(yfsPersonInfoDTO.getBirthDate().getTime()) : null)
                .setAddressType(SPACE)
                .setCountryOfResidence(SPACE)
                .setSubDepartment(SPACE)
                .setStreetName(SPACE)
                .setBuildingNumber(SPACE)
                .setOther(SPACE)
                .setStateOfBirth(SPACE)
                .setCityOfBirth(SPACE)
                .setCountryOfBirth(SPACE)
                .setTimeZone("+00");
    };

    private static final Function<YfsPersonInfoEntity, YfsPersonInfoDTO> deSerialize = yfsPersonInfoEntity ->
            new YfsPersonInfoDTO().setAddressLine1(yfsPersonInfoEntity.getAddressLine1())
                    .setAddressLine2(yfsPersonInfoEntity.getAddressLine2())
                    .setCity(yfsPersonInfoEntity.getCity())
                    .setCompany(yfsPersonInfoEntity.getCompany())
                    .setCountry(yfsPersonInfoEntity.getCountry())
                    .setZipCode(yfsPersonInfoEntity.getZipCode())
                    .setState(yfsPersonInfoEntity.getState())
                    .setEmailId(yfsPersonInfoEntity.getEmailId())
                    .setDayPhone(yfsPersonInfoEntity.getDayPhone());

}
