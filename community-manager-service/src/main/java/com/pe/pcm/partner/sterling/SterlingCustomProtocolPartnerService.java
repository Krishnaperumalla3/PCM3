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

package com.pe.pcm.partner.sterling;

import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.sterling.partner.sci.SterlingSfgProfileService;
import com.pe.pcm.sterling.profile.SterlingProfilesModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.pe.pcm.enums.Protocol.findProtocol;
import static com.pe.pcm.utils.PCMConstants.CONSUMER;
import static java.util.Optional.ofNullable;

/**
 * @author Kiran Reddy.
 */
@Service
public class SterlingCustomProtocolPartnerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SterlingCustomProtocolPartnerService.class);

    private final SterlingSfgProfileService sterlingSfgProfileService;

    @Autowired
    public SterlingCustomProtocolPartnerService(SterlingSfgProfileService sterlingSfgProfileService) {
        this.sterlingSfgProfileService = sterlingSfgProfileService;
    }

    @Transactional
    public void create(SterlingProfilesModel sterlingProfilesModel) {
        LOGGER.info("In SterlingCustomProfileService create().");
        save(sterlingProfilesModel, false);
        LOGGER.info("End SterlingCustomProfileService create().");
    }

    @Transactional
    public void update(SterlingProfilesModel sterlingProfilesModel) {
        LOGGER.info("In SterlingCustomProfileService update().");
        save(sterlingProfilesModel, true);
        LOGGER.info("End SterlingCustomProfileService update().");
    }

    @Transactional
    public void delete(String profileName, Boolean deleteMailbox, Boolean deleteUser) {
        sterlingSfgProfileService
                .delete(
                        profileName,
                        ofNullable(findProtocol("Mailbox")).orElseThrow(GlobalExceptionHandler::unknownProtocol),
                        true,
                        true,
                        deleteMailbox,
                        deleteUser,
                        true,
                        false
                );
    }

    public SterlingProfilesModel get(String profileName) {
        SterlingProfilesModel sterlingProfilesModel = new SterlingProfilesModel();
        sterlingProfilesModel.setProfileName(profileName);
        sterlingProfilesModel.setSubscriberType("TP");
        return sterlingSfgProfileService.get(sterlingProfilesModel, null, CONSUMER, true);
    }

    private void save(SterlingProfilesModel sterlingProfilesModel, Boolean isUpdate) {
        sterlingProfilesModel.setSubscriberType("TP")
                .setCreateProducerProfile(true)
                .setProtocol("Mailbox");
        sterlingSfgProfileService.save(sterlingProfilesModel,
                ofNullable(findProtocol(sterlingProfilesModel.getProtocol()))
                        .orElseThrow(GlobalExceptionHandler::unknownProtocol),
                sterlingProfilesModel.getProfileName(),
                isUpdate
        );
    }

}
