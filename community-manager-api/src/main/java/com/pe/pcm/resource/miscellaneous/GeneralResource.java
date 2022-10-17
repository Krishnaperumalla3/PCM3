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

import com.pe.pcm.user.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.pe.pcm.constants.AuthoritiesConstants.SA_OB_BA;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

@RestController
@RequestMapping(path = "pcm/general")
@Api(tags = "General APIs", hidden = true)
public class GeneralResource {

    private final UserService userService;

    @Autowired
    public GeneralResource(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "Reset password using Email", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @GetMapping(path = "forgot-password/{email}")
    public ResponseEntity<String> getUserIDByEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(userService.sendOTP(email));
    }

    @ApiOperation(value = "Update Password", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "forgot/update-password")
    public ResponseEntity<String> updatePasswordFromForgot(@RequestParam String userId, @RequestParam String password, @RequestParam String otp) {
        return ResponseEntity.ok(userService.updatePasswordFromForgot(userId, password, otp));
    }

}
