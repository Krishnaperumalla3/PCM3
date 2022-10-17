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

package com.pe.pcm.resource.pem;

import com.pe.pcm.common.*;
import com.pe.pcm.miscellaneous.ScriptExecutionService;
import com.pe.pcm.pem.*;
import com.pe.pcm.ssp.InputModel;
import com.pe.pcm.utils.FileInfoModel;
import com.pe.pcm.workflow.pem.PemContentWorkFlowModel;
import io.swagger.annotations.*;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static com.pe.pcm.constants.AuthoritiesConstants.SA_OB_BA;
import static com.pe.pcm.utils.CommonFunctions.*;
import static com.pe.pcm.utils.PCMConstants.REQUEST_OK;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

/**
 * @author Kiran Reddy.
 */
@RestController
@PreAuthorize(SA_OB_BA)
@RequestMapping(value = "pem/utility", produces = APPLICATION_JSON_VALUE)
@Api(tags = {"PEM Utility Resource"})
public class PemUtilityResource {

    private final PemUtilityService pemUtilityService;
    private final ScriptExecutionService scriptExecutionService;

    @Autowired
    public PemUtilityResource(PemUtilityService pemUtilityService, ScriptExecutionService scriptExecutionService) {
        this.pemUtilityService = pemUtilityService;
        this.scriptExecutionService = scriptExecutionService;
    }

    @ApiOperation(value = "Save Code List", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "partner/save-codes", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityManagerResponse> saveCodes(@RequestBody CommunityMangerModel<CommunityManagerKeyValueModel> communityMangerModel) {
        pemUtilityService.savePartnerCode(communityMangerModel.getContent());
        return ResponseEntity.ok(OK.apply(REQUEST_OK));
    }

    @ApiOperation(value = "Delete Code List", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @DeleteMapping(path = "partner/delete-codes", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityManagerResponse> deleteCodes(@RequestBody CommunityMangerModel<CommunityManagerKeyValueModel> communityMangerModel) {
        pemUtilityService.deletePartnerCode(communityMangerModel.getContent());
        return ResponseEntity.ok(OK.apply(REQUEST_OK));
    }

    @ApiOperation(value = "Get Code List by Partner Name", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @GetMapping(path = "partner/get-code/{partnerName}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityManagerKeyValueModel> searchByName(@PathVariable("partnerName") String key) {

        return ResponseEntity.ok(pemUtilityService.getPartnerCodeByName(key));
    }

    @ApiOperation(value = "Get Routing Channel Template Names", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @GetMapping(path = "si/template-names")
    public ResponseEntity<CommunityMangerModel<CommunityManagerNameModel>> fetchTemplateNames() {
        return ResponseEntity.ok(new CommunityMangerModel<>(pemUtilityService.getTemplateNames()));
    }

    @ApiOperation(value = "Run Script in remote server", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "remote-server/script-run")
    public ResponseEntity<CommunityManagerResponse> runRemoteScript(@RequestBody RemoteServerDetailsModel remoteServerDetailsModel) {
        return ResponseEntity.ok(OK.apply(scriptExecutionService.execute(remoteServerDetailsModel)));
    }

    @ApiOperation(value = "Drop the static file(available in Application) in provided Location", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "file-drop")
    public ResponseEntity<CommunityManagerResponse> fileDrop(@RequestBody FileInfoModel fileInfoModel) {
        pemUtilityService.dropFile(fileInfoModel);
        return ResponseEntity.ok(OK.apply("File dropped Successfully."));
    }

    @ApiOperation(value = "Find index value", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @GetMapping(path = "find-index-value")
    public ResponseEntity<CommunityManagerResponse> findIndexValue(String content, String pattern, int index) {

        return ResponseEntity.ok(new CommunityManagerResponse(200, content.split(pattern)[index]));
    }

    @ApiOperation(value = "Get First Value", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "get-first-value", consumes = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE}, produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public ResponseEntity<CommunityManagerResponse> getFirstValue(@RequestBody StringConcatenationModel stringConcatenationModel) {
        return ResponseEntity.ok(OK.apply(pemUtilityService.getFirstStringValue(stringConcatenationModel)));
    }

    @ApiOperation(value = "Replace the value in String", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "string-replacer")
    public ResponseEntity<CommunityManagerResponse> replacer(@RequestParam("content") String content,
                                                             @RequestParam("regex") String regex, @RequestParam("replacement") String replacement) {
        return ResponseEntity.ok(OK.apply(isNotNull(content) ? content.replace(regex, replacement) : ""));
    }

    @ApiOperation(value = "Replace the Vale in String", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "string-replacer-dup")
    public ResponseEntity<CommunityManagerResponse> stringReplacer(@RequestBody StringReplacerModel stringReplacerModel) {
        return ResponseEntity.ok(OK.apply(isNotNull(stringReplacerModel.getContent()) ? stringReplacerModel.getContent()
                .replace(stringReplacerModel.getRegex(), isNotNull(stringReplacerModel.getReplacement()) ? stringReplacerModel.getReplacement() : "").trim() : ""));
    }

    @ApiOperation(value = "Generate a string for Password", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @GetMapping(path = "generate-password")
    public ResponseEntity<CommunityManagerResponse> generatePassword(int length) {
        return ResponseEntity.ok(new CommunityManagerResponse(200, generatePemPassword.apply(length)));
    }

    @ApiOperation(value = "Remove the special characters from the given string", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "string-remove-special-characters")
    public ResponseEntity<CommunityManagerResponse> replacer1(@RequestParam("content") String content) {
        return ResponseEntity.ok(OK.apply(isNotNull(content) ? content.replaceAll("[^a-zA-Z0-9]", "") : ""));
    }

    @ApiOperation(value = "Get sub string from given string", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "get-sub-string")
    public ResponseEntity<CommunityManagerResponse> getSubString(@RequestParam("content") String content,
                                                                 @RequestParam("isStart") Boolean isStart,
                                                                 @RequestParam("position") Integer position) {
        if (position >= content.length()) {
            return ResponseEntity.ok(OK.apply(content));
        } else {
            String value;
            if (isNotNull(content)) {
                if (isStart) {
                    value = content.substring(0, position);
                } else {
                    value = content.substring(position);
                }
            } else {
                value = "";
            }
            return ResponseEntity.ok(OK.apply(value));
        }
    }

    @ApiOperation(value = "Get current date", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @GetMapping(path = "get-current-date")
    public ResponseEntity<CommunityManagerResponse> getCurrentDate(String format) {
        return ResponseEntity.ok(new CommunityManagerResponse(200, pemUtilityService.getCurrentDate(format)));
    }

    @ApiOperation(value = "Encode string using Apache Base 64", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "convert-base64-string-apache")
    public ResponseEntity<CommunityManagerResponse> base64ConvertApache(@RequestBody PemStringDataModel pemStringDataModel) {
        return ResponseEntity.ok(OK.apply(Base64.encodeBase64String(pemStringDataModel.getData().getBytes())));
    }

    @ApiOperation(value = "Encode string using java lib", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "convert-base64-string-java")
    public ResponseEntity<CommunityManagerResponse> base64ConvertJava(@RequestBody PemStringDataModel pemStringDataModel) {
        return ResponseEntity.ok(OK.apply(java.util.Base64.getEncoder().encodeToString(pemStringDataModel.getData().getBytes())));
    }

    @ApiOperation(value = "Encode Workflow model using apache and return as string", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "encode-workflow-model-to-apache-base64")
    public ResponseEntity<PemStringDataModel> encodeWorkflowModelToApacheBase64(@RequestBody PemContentWorkFlowModel pemContentWorkFlowModel) {
        return ResponseEntity.ok(new PemStringDataModel().setData(Base64.encodeBase64String(pemContentWorkFlowModel.toJsonString().getBytes())));
    }

    @ApiOperation(value = "Convert string into List using provided separator", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "string-to-list")
    public ResponseEntity<CommunityMangerModel<CommunityManagerNameModel>> stringToList(@RequestParam("content") String content, @RequestParam("separator") String separator) {

        return ResponseEntity.ok(new CommunityMangerModel<>(Arrays.stream(content.split(separator))
                .map(CommunityManagerNameModel::new)
                .collect(Collectors.toList())));
    }

    @ApiOperation(value = "Convert List data into String", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "list-to-String")
    public ResponseEntity<CommunityManagerResponse> listToString(@RequestBody CommunityMangerModel<CommunityManagerNameModel> communityMangerModel, @RequestParam("delimiter") String delimiter) {

        return ResponseEntity.ok(OK.apply(
                communityMangerModel.getContent().stream().map(CommunityManagerNameModel::getName).collect(Collectors.joining(delimiter))
        ));
    }

    @ApiOperation(value = "List the users who who will expires n day's", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @GetMapping(path = "get-expiry-users", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public ResponseEntity<CommunityMangerModel<PemAccountExpiryModel>> getExpiryUsers(int remainDays) {
        return ResponseEntity.ok(new CommunityMangerModel<>(pemUtilityService.getExpiryUsersWithMailIds(remainDays)));
    }

    @ApiOperation(value = "List the partners using UIDDKeys grater than given day's", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @GetMapping(path = "get-expiry-user-identity-keys", produces = {APPLICATION_XML_VALUE, APPLICATION_JSON_VALUE})
    public ResponseEntity<CommunityMangerModel<PemAccountExpiryModel>> getExpiryUserIdentityKeys(@RequestParam(required = false) Integer noOfDays, String uidKeyName, Boolean isAuthKey) {
        return ResponseEntity.ok(new CommunityMangerModel<>(pemUtilityService.getExpiryUserIdentityKeys(noOfDays, uidKeyName, isAuthKey)));
    }

    //TODO : This can be removed , in above method we can handle XML response
    @ApiOperation(value = "List the users who will expires in n day's(XML o/p)", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @GetMapping(path = "get-expiry-users-xml", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public ResponseEntity<CommunityMangerModel<PemAccountExpiryModel>> getExpiryUsersWithXml(int remainDays) {
        return ResponseEntity.ok(new CommunityMangerModel<>(pemUtilityService.getExpiryUsersWithMailIds(remainDays)));
    }

    @ApiOperation(value = "List Expiry users by updated time", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @GetMapping(path = "get-expiry-users-by-updated-time", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public ResponseEntity<CommunityMangerModel<PemAccountExpiryModel>> getExpiryUsersByUpdatedTime(int daysBefore) {
        return ResponseEntity.ok(new CommunityMangerModel<>(pemUtilityService.getExpiryUsersWithMailIdsByLastUpdated(daysBefore)));
    }

    @ApiOperation(value = "Check given data is null or not", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @GetMapping(path = "find-null")
    public ResponseEntity<CommunityManagerBooleanModel> findNullFromArray(String value) {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        Arrays.stream(value.replace(",", "),)").split(",")).forEach(s -> {
            if (!isNotNull(s) || s.equals(")") || s.equals("))")) {
                atomicBoolean.set(true);
            }
        });
        return ResponseEntity.ok(new CommunityManagerBooleanModel().setValue(atomicBoolean.get()));
    }

    @ApiOperation(value = "Check given String is null or not", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "find-null")
    public ResponseEntity<CommunityManagerBooleanModel> findNullFromArrayPost(@RequestBody PemStringDataModel pemStringDataModel) {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        Arrays.stream(pemStringDataModel.getData().replace(",", "),)").split(",")).forEach(s -> {
            if (!isNotNull(s) || s.equals(")") || s.equals("))")) {
                atomicBoolean.set(true);
            }
        });
        return ResponseEntity.ok(new CommunityManagerBooleanModel().setValue(atomicBoolean.get()));
    }

    @ApiOperation(value = "Load the data from CSV data", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "get-values-from-csv")
    public ResponseEntity<CommunityMangerModel<CommunityManagerNameModel>> getValuesFromCSV(@RequestBody PemConvertCSVModel pemConvertCSVModel) {
        return ResponseEntity.ok(new CommunityMangerModel<>(pemUtilityService.getValuesFromCSV(pemConvertCSVModel)));
    }

    @ApiOperation(value = "Generate data for password", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "generate-random-password")
    public ResponseEntity<CommunityManagerResponse> generateRandomPassword(Integer upperCaseLettersCnt, Integer lowerCaseLettersCnt, Integer numberCnt, Integer specialCharCnt) {
        return ResponseEntity.ok(new CommunityManagerResponse(200, pemUtilityService.getCustomPassword(upperCaseLettersCnt, lowerCaseLettersCnt, numberCnt, specialCharCnt)));
    }

    @ApiOperation(value = "Convert string ASCII to HEX", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "convert-asciiToHex")
    public ResponseEntity<CommunityManagerNameModel> convertAsciiToHex(String ascii) {
        return ResponseEntity.ok(new CommunityManagerNameModel(pemUtilityService.asciiToHex(ascii)));
    }

    @ApiOperation(value = "Convert String ASCII to HEX ", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "convert-ascii-to-hex-hh")
    public ResponseEntity<CommunityManagerNameModel> convertAsciiToHexHH(@RequestBody PemStringDataModel pemStringDataModel) {
        return ResponseEntity.ok(new CommunityManagerNameModel(pemUtilityService.asciiToHexHH(pemStringDataModel.getData())));
    }

    @ApiOperation(value = "Convert Hex to Ascii ", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "convert-hex-to-ascii")
    public ResponseEntity<CommunityManagerNameModel> convertHexToAscii(@RequestBody PemStringDataModel pemStringDataModel) {
        return ResponseEntity.ok(new CommunityManagerNameModel(pemUtilityService.hexToAscii(pemStringDataModel.getData())));
    }

    @ApiOperation(value = "Get Custom Protocol List", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "custom-protocol-list", produces = APPLICATION_XML_VALUE)
    public ResponseEntity<CustomProtocolResponseModel> getCustomProtocol(String filePath) {
        return ResponseEntity.ok(pemUtilityService.getCustomProtocolNames(filePath));
    }

    @ApiOperation(value = "Get CustomProtocol Var names", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "custom-protocol-var-names", produces = APPLICATION_XML_VALUE)
    public ResponseEntity<CustomProtocolResponseModel> getVarNames(String filePath, String customProtocolName) {
        return ResponseEntity.ok(pemUtilityService.getVarNames(filePath, customProtocolName));
    }

    @ApiOperation(value = "Encoding with apache", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "xml/encode-with-apache", consumes = APPLICATION_XML_VALUE, produces = APPLICATION_XML_VALUE)
    public ResponseEntity<DataModel> encodingWithApache(@RequestBody String data) {
        isNullThrowError.apply(data, "input");
        return ResponseEntity.ok(new DataModel().setData(Base64.encodeBase64String(data.getBytes())));
    }

    @ApiOperation(value = "decode with apache", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "xml/decode-with-apache", consumes = APPLICATION_XML_VALUE, produces = APPLICATION_XML_VALUE)
    public ResponseEntity<String> decodingWithApache(@RequestBody DataModel data) {
        isNullThrowError.apply(data.getData(), "data tag");
        return ResponseEntity.ok(new String(Base64.decodeBase64(data.getData())));
    }

    @ApiOperation(value = "match data with given regex", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "regex-finder", consumes = APPLICATION_XML_VALUE, produces = APPLICATION_XML_VALUE)
    public ResponseEntity<PemRegexOutPutModel> regexFinder(@RequestBody InputModel data) {
        return ResponseEntity.ok(pemUtilityService.regexFinder(data));
    }

    @ApiOperation(value = "Compliance text(Encoded data) to XML", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "convert-compliance-report-base64-to-xml")
    public ResponseEntity<String> complianceTextToXML(@RequestBody ComplianceInputModel complianceInputModel) {
        return ResponseEntity.ok(pemUtilityService.convertComplianceTextReportToXML(complianceInputModel));
    }

}
