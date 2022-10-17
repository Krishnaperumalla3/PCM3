package com.pe.pcm.resource.file;

import com.pe.pcm.file.PartnerMailBoxService;
import com.pe.pcm.partner.FilesModel;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.SortedSet;

import static javax.servlet.http.HttpServletResponse.*;
import static org.springframework.http.MediaType.*;

/**
 * @author Shameer.v.
 */
@RestController
@RequestMapping(path = "pcm/partner-mailbox")
@Api(tags = {"Partners MailBoxes Resource"})
public class PartnersMailBoxResource {

    private final PartnerMailBoxService partnerMailBoxService;

    @Autowired
    public PartnersMailBoxResource(PartnerMailBoxService partnerMailBoxService) {
        this.partnerMailBoxService = partnerMailBoxService;
    }

    @ApiOperation(value = "API To Get Partners Mailboxes", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "method not implemented")
    })
    @GetMapping(path = "/getPartnerMailboxes", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public ResponseEntity<SortedSet<String>> getPartnerMailBoxes() {
        return ResponseEntity.ok(partnerMailBoxService.getAllMailboxesByCurrentUser());
    }

    @ApiOperation(value = "API To Get Available Files From Mailboxes", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "method not implemented")
    })
    @GetMapping(path = "/getFilesFromMailboxes", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public ResponseEntity<List<FilesModel>> getAvailableFilesInMailbox(@RequestParam String mailbox) {
        return ResponseEntity.ok(partnerMailBoxService.getAvailableFilesInMailbox(mailbox));
    }

    @ApiOperation(value = "API To download File From Mailbox", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "method not implemented")
    })
    @GetMapping(path = "downloadFile", produces = APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<FileSystemResource> downloadFileFromMailbox(@RequestParam String mailBox,
                                                                      @RequestParam String fileName) throws IOException {
        FileSystemResource resource = partnerMailBoxService.loadFileAsFsResourceForDownload(mailBox, fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

}
