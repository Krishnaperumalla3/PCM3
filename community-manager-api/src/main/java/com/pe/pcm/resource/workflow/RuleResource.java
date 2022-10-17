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

package com.pe.pcm.resource.workflow;

import com.pe.pcm.common.CommunityManagerResponse;
import com.pe.pcm.rule.RuleModel;
import com.pe.pcm.rule.RuleService;
import com.pe.pcm.rule.entity.RuleEntity;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.pe.pcm.constants.AuthoritiesConstants.*;
import static javax.servlet.http.HttpServletResponse.SC_CONFLICT;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Kiran Reddy.
 */
@RestController
@RequestMapping(path = "pcm/rule", produces = APPLICATION_JSON_VALUE)
@Api(tags = {"Rule Resource"})
public class RuleResource {

    private final RuleService ruleService;

    @Autowired
    public RuleResource(RuleService ruleService) {
        this.ruleService = ruleService;
    }

    @ApiOperation(value = "Create Rule", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "Rule already exist"),

    })
    @PostMapping
    @PreAuthorize(SA_OB_BA)
    public ResponseEntity<CommunityManagerResponse> create(@Validated @RequestBody RuleModel ruleModel) {
        ruleService.create(ruleModel);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Rule created successfully"));
    }

    @ApiOperation(value = "Update Rule", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "Rule Not exist"),

    })
    @PutMapping
    @PreAuthorize(SA_OB_BA)
    public ResponseEntity<CommunityManagerResponse> update(@Validated @RequestBody RuleModel ruleModel) {
        ruleService.update(ruleModel);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Rule updated successfully"));
    }

    @ApiOperation(value = "Get Rule", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "Rule Not exist"),

    })
    @GetMapping(path = "{ruleId}")
    @PreAuthorize(SA_OB_BA_BU)
    public ResponseEntity<RuleModel> getRule(@PathVariable("ruleId") String ruleId) {
        return ResponseEntity.ok(ruleService.get(ruleId));
    }

    @ApiOperation(value = "Search Rule", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "No Data Found"),

    })
    @PostMapping(path = "search")
    @PreAuthorize(SA_OB_BA_BU)
    public Page<RuleEntity> search(@RequestBody RuleModel ruleModel,
                                   @PageableDefault(size = 10, page = 0, sort = {"ruleName"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return ruleService.search(ruleModel, pageable);
    }

    @ApiOperation(value = "Delete Rule", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "Rule Not exist"),

    })
    @PreAuthorize(SA_OB_BA)
    @DeleteMapping(path = "{ruleId}")
    public ResponseEntity<CommunityManagerResponse> delete(@PathVariable("ruleId") String ruleId) {
        ruleService.delete(ruleId);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Rule Deleted successfully"));
    }

    @ApiOperation(value = "Get Rules", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "Rule Not exist"),

    })
    @GetMapping
    @PreAuthorize(SA_OB_BA_BU_DP_DPR)
    public ResponseEntity<List<RuleEntity>> getRules() {
        return ResponseEntity.ok(ruleService.getRulesList());
    }

    //WorkFlow new Design Search - support API
    @ApiOperation(value = "Get Rules (Models)", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "Rule Not exist"),

    })
    @PreAuthorize(SA_OB_BA_BU_DP_DPR)
    @PostMapping(path = "rules-models")
    public ResponseEntity<List<RuleModel>> getRulesModel() {
        return ResponseEntity.ok(ruleService.getRulesModel());
    }

}
