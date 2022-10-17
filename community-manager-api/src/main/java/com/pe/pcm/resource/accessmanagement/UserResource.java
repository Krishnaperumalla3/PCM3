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

package com.pe.pcm.resource.accessmanagement;

import com.pe.pcm.common.CommunityManagerKeyValueModel;
import com.pe.pcm.common.CommunityManagerResponse;
import com.pe.pcm.user.UserLangModel;
import com.pe.pcm.user.UserModel;
import com.pe.pcm.user.UserService;
import com.pe.pcm.user.UserStatusModel;
import com.pe.pcm.user.entity.UserEntity;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import java.util.List;

import static com.pe.pcm.constants.AuthoritiesConstants.SA_AD;
import static com.pe.pcm.constants.AuthoritiesConstants.SA_OB_BA;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

/**
 * @author Kiran Reddy.
 */
@RestController
@RequestMapping(path = "pcm/user", produces = APPLICATION_JSON_VALUE)
@Api(tags = {"Community Manger Internal/External User Resource"})
public class UserResource {

    private final UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "Create Internal/External User", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(produces = APPLICATION_JSON_VALUE)
    @PreAuthorize(SA_AD)
    public ResponseEntity<CommunityManagerResponse> create(@Validated @RequestBody UserModel userModel, HttpServletRequest request) {
        userService.create(userModel, request.getRequestURL().toString().replace("/pcm/user", ""));
        return ResponseEntity.ok(new CommunityManagerResponse(OK.value(), "User created successfully"));
    }

    @ApiOperation(value = "Update Internal/External User", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PutMapping(produces = APPLICATION_JSON_VALUE)
    @PreAuthorize(SA_AD)
    public ResponseEntity<CommunityManagerResponse> update(@Validated @RequestBody UserModel userModel) {
        userService.update(userModel);
        return ResponseEntity.ok(new CommunityManagerResponse(OK.value(), "User updated successfully"));
    }

    @ApiOperation(value = "Get Internal/External User", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @GetMapping(path = "{userId}", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize(SA_AD)
    public ResponseEntity<UserModel> getUser(@Validated @PathVariable("userId") String userId) {
        return ResponseEntity.ok(userService.get(userId));
    }

    @ApiOperation(value = "Search Internal/External User", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "search", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize(SA_AD)
    public Page<UserEntity> search(@RequestBody UserModel userModel,
                                   @PageableDefault(size = 10, page = 0, sort = {"userid"}, direction = Direction.ASC) Pageable pageable) {
        return userService.search(userModel, pageable);
    }

    @ApiOperation(value = "Delete User", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @DeleteMapping(path = "{userId}", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize(SA_OB_BA)
    public ResponseEntity<CommunityManagerResponse> delete(@Validated @PathVariable("userId") String userId) {
        userService.delete(userId);
        return ResponseEntity.ok(new CommunityManagerResponse(OK.value(), "User deleted successfully"));
    }

    @ApiOperation(value = "Update Internal/External User status", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "status")
    @PreAuthorize(SA_AD)
    public ResponseEntity<CommunityManagerResponse> statusChange(@RequestBody UserStatusModel userStatusModel) {
        userService.statusChange(userStatusModel.getPkId(), userStatusModel.getStatus());
        return ResponseEntity.ok(new CommunityManagerResponse(OK.value(), "Status updated successfully"));
    }

    @ApiOperation(value = "Change Internal User Password", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "change-password")
    public ResponseEntity<CommunityManagerResponse> passwordChange(@RequestParam("pkId") String pkId,
                                                                   @RequestParam("oldPassword") String oldPassword,
                                                                   @RequestParam("newPassword") String newPassword) {
        userService.changePassword(pkId, oldPassword, newPassword);
        return ResponseEntity.ok(new CommunityManagerResponse(OK.value(), "Password Changed Successfully"));
    }

    @ApiOperation(value = "Update Internal/External User Language", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "lang")
    public ResponseEntity<CommunityManagerResponse> langChange(@RequestBody UserLangModel userLangModel) {
        userService.changeLang(userLangModel);
        return ResponseEntity.ok(new CommunityManagerResponse(OK.value(), "Language updated successfully"));
    }

    @ApiOperation(value = "UnLock And Active The User", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
    })
    @GetMapping(path = "/unlock-user",produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public ResponseEntity<CommunityManagerResponse> unLockAndActiveUser(@RequestParam String userId) {
        userService.unLockAndActivateUser(userId);
        return ResponseEntity.ok(new CommunityManagerResponse(OK.value(),"User UnLocked and Activated Successfully.."));
    }

    @ApiOperation(value = "PCM User Roles", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @GetMapping(path = "roles")
    public ResponseEntity<List<CommunityManagerKeyValueModel>> getRoles() {
        return ResponseEntity.ok(userService.getRoles());
    }


//    @GetMapping(path = "enc/{text}", produces = APPLICATION_JSON_VALUE)
//    public ResponseEntity<String> enc(@Validated @PathVariable("text") String enc) {
//        return ResponseEntity.ok(userService.enc(enc));
//    }
//
//    @GetMapping(path = "dec/{text}", produces = APPLICATION_JSON_VALUE)
//    public ResponseEntity<String> dec(@Validated @PathVariable("text") String enc) {
//        return ResponseEntity.ok(userService.dec(enc));
//    }

}
