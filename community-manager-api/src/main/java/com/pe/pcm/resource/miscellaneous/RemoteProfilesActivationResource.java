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

package com.pe.pcm.resource.miscellaneous;

import com.pe.pcm.miscellaneous.OtherUtilityService;
import com.pe.pcm.common.CommunityManagerResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.pe.pcm.constants.AuthoritiesConstants.SA_OB_BA;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

/**
 * @author Kiran Reddy.
 */
@RestController
@RequestMapping(path = "pcm")
@Api(tags = {"Remote Profiles Activation Resource[Profiles On Boarding into both SI and PCM Resource]"})
@PreAuthorize(SA_OB_BA)
public class RemoteProfilesActivationResource {

    private final OtherUtilityService otherUtilityService;

    @Autowired
    public RemoteProfilesActivationResource(OtherUtilityService otherUtilityService) {
        this.otherUtilityService = otherUtilityService;
    }


    @ApiOperation(value = "On-boarding Bulk Profiles: On-boarding the Profiles into both PCM and SI using the profiles data in staging tables",
            authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @GetMapping(path = "bulk-remote-profiles-active")
    public ResponseEntity<CommunityManagerResponse> bulkRemoteProfilesActive() {
        otherUtilityService.activateRemoteProfiles();
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Bulk Profiles import to SI, successfully completed"));
    }
}
