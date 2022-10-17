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

package com.pe.pcm.b2b;

import com.pe.pcm.b2b.protocol.RemoteAs2OrgProfile;
import com.pe.pcm.b2b.protocol.RemoteAs2PartnerProfile;
import com.pe.pcm.protocol.As2Model;
import com.pe.pcm.protocol.function.ProtocolFunctions;
import com.pe.pcm.protocol.as2.ProfileService;
import com.pe.pcm.protocol.as2.si.entity.SciProfileEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;

/**
 * @author Chenchu Kiran.
 */
@Service
public class B2BRemoteAS2Service {

    private final B2BApiService b2BApiService;

    private final ProfileService profileService;

    @Autowired
    public B2BRemoteAS2Service(B2BApiService b2BApiService, ProfileService profileService) {
        this.b2BApiService = b2BApiService;
        this.profileService = profileService;
    }

    public void saveRemoteAs2Profile(As2Model as2Model, Boolean isUpdate) {
        if (as2Model.getHubInfo()) {
            saveRemoteAs2OrgProfile(as2Model, isUpdate, getAs2RemoteProfileObjectId(as2Model.getProfileId()));
        } else {
            saveRemoteAs2PartnerProfile(as2Model, isUpdate, isUpdate ? getAs2RemoteProfileObjectId(as2Model.getProfileId()) : "");
        }
    }

    private void saveRemoteAs2OrgProfile(As2Model as2Model, Boolean isUpdate, String profileId) {
        RemoteAs2OrgProfile remoteAs2OrgProfile = ProtocolFunctions.mapperToRemoteAs2OrgProfile.apply(as2Model);
        if (isUpdate) {
            b2BApiService.updateRemoteAs2OrgProfile(remoteAs2OrgProfile, profileId);
        } else {
            b2BApiService.createRemoteAs2OrgProfile(remoteAs2OrgProfile);
        }
    }

    private void saveRemoteAs2PartnerProfile(As2Model as2Model, Boolean isUpdate, String profileId) {
        RemoteAs2PartnerProfile remoteAs2PartnerProfile = ProtocolFunctions.mapperToRemoteAs2PartnerProfile.apply(as2Model);
        if (isUpdate) {
            b2BApiService.updateRemoteAs2PartnerProfile(remoteAs2PartnerProfile, profileId);
        } else {
            b2BApiService.createRemoteAs2PartnerProfile(remoteAs2PartnerProfile);
        }
    }

    public String getRemoteAs2OrgProfile(String profileId) {
        return b2BApiService.getRemoteAs2OrgProfile(getAs2RemoteProfileObjectId(profileId));
    }

    public String getRemoteAs2PartnerProfile(String profileId) {
        return b2BApiService.getRemoteAs2PartnerProfile(getAs2RemoteProfileObjectId(profileId));
    }

    public void deleteRemoteAs2OrgProfile(String profileId) {
        b2BApiService.deleteRemoteAs2OrgProfile(getAs2RemoteProfileObjectId(profileId));
    }

    public void deleteRemoteAs2PartnerProfile(String profileId) {
        b2BApiService.deleteRemoteAs2PartnerProfile(getAs2RemoteProfileObjectId(profileId));
    }

    private String getAs2RemoteProfileObjectId(String profileId) {
        Optional<SciProfileEntity> optionalSCIProfile =  profileService.get(profileId);
        if(optionalSCIProfile.isPresent()){
            return optionalSCIProfile.get().getObjectId().trim();
        }else{
            throw internalServerError("Opps.., As2Profile not available in Sterling.");
        }
    }
}
