package com.pe.pcm.resource.stats;

import com.pe.pcm.utils.MemoryStatsModel;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.pe.pcm.constants.AuthoritiesConstants.ONLY_SA;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Kiran Reddy.
 */
@RestController
@RequestMapping(path = "pcm", produces = APPLICATION_JSON_VALUE)
@Api(tags = {"Community Manager Memory Stats"}, description = "Community Manager Memory Stats Resource")
@PreAuthorize(ONLY_SA)
public class MemoryStatsResource {

    @ApiOperation(value = "Heap Memory", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @GetMapping("/heap/memory-status")
    public ResponseEntity<MemoryStatsModel> getMemoryStatistics() {
        final String BYTES = " bytes";
        return ResponseEntity.ok(new MemoryStatsModel()
                .setHeapSize(Runtime.getRuntime().totalMemory() + BYTES)
                .setHeapMaxSize(Runtime.getRuntime().maxMemory() + BYTES)
                .setHeapFreeSize(Runtime.getRuntime().freeMemory() + BYTES));

    }
}
