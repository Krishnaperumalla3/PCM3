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

package com.pe.pcm.resource.sterling;

import com.pe.pcm.common.CommunityManagerResponse;
import com.pe.pcm.si.SterlingMailboxModel;
import com.pe.pcm.sterling.mailbox.SterlingMailboxService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.pe.pcm.constants.AuthoritiesConstants.SA_OB_BA;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Kiran Reddy.
 */
@RestController
@RequestMapping(path = "pcm/si/mailbox", produces = APPLICATION_JSON_VALUE)
@Api(tags = {"IBM Sterling B2B Integrator Mailbox"}, description = "IBM Sterling B2B Integrator Mailbox Resource")
@PreAuthorize(SA_OB_BA)
public class MbxMailboxResource {

    private final SterlingMailboxService sterlingMailboxService;

    @Autowired
    public MbxMailboxResource(SterlingMailboxService sterlingMailboxService) {
        this.sterlingMailboxService = sterlingMailboxService;
    }

    @ApiOperation(value = "Create Mailbox in IBM Sterling B2B Integrator", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping
    public ResponseEntity<CommunityManagerResponse> createMailbox(@RequestBody SterlingMailboxModel sterlingMailboxModel) {
        sterlingMailboxService.create(sterlingMailboxModel);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Mailbox Created successfully."));
    }

    @ApiOperation(value = "Get ALl Mailboxes", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @GetMapping("get-list")
    public ResponseEntity<List<String>> getMailboxNamesList() {
        return ResponseEntity.ok(sterlingMailboxService.getMailboxNamesList());
    }

}
