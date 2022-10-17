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
import com.pe.pcm.group.GroupModel;
import com.pe.pcm.group.GroupService;
import com.pe.pcm.user.entity.GroupEntity;
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

import java.util.List;

import static com.pe.pcm.constants.AuthoritiesConstants.SA_AD;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Kiran Reddy.
 */
@RestController
@RequestMapping(path = "pcm/group")
@Api(tags = {"Group Resource"})
public class GroupResource {

    private final GroupService groupservice;

    @Autowired
    public GroupResource(GroupService groupservice) {
        this.groupservice = groupservice;
    }

    @ApiOperation(value = "Create Group", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_AD)
    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityManagerResponse> create(@Validated @RequestBody GroupModel groupModel) {
        groupservice.create(groupModel);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Group created successfully"));
    }

    @ApiOperation(value = "Update Group", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_AD)
    @PutMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityManagerResponse> update(@Validated @RequestBody GroupModel groupModel) {
        groupservice.update(groupModel);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Group updated successfully"));
    }

    @ApiOperation(value = "Delete Group", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_AD)
    @DeleteMapping(path = "{pkId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityManagerResponse> delete(@Validated @PathVariable("pkId") String pkId) {
        groupservice.delete(pkId);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Group deleted successfully"));
    }

    @ApiOperation(value = "Get Group", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_AD)
    @GetMapping(path = "{pkId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<GroupModel> get(@Validated @PathVariable("pkId") String pkId) {
        return ResponseEntity.ok(groupservice.get(pkId));
    }

    @ApiOperation(value = "Search Group", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_AD)
    @PostMapping(path = "search", produces = APPLICATION_JSON_VALUE)
    public Page<GroupEntity> search(@RequestBody GroupModel groupModel, @PageableDefault(size = 10, page = 0, sort = {"groupname"}, direction = Direction.ASC) Pageable pageable) {
        return groupservice.search(groupModel, pageable);
    }

    @ApiOperation(value = "Get Group Names", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_AD)
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CommunityManagerKeyValueModel>> getGroupsMap() {
        return ResponseEntity.ok(groupservice.getGroupsMap());
    }


}
