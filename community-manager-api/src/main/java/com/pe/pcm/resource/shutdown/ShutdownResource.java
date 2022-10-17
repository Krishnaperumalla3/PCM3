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

package com.pe.pcm.resource.shutdown;

import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.miscellaneous.PasswordUtilityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import static com.pe.pcm.constants.AuthoritiesConstants.SA_AD;
import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;
import static com.pe.pcm.utils.CommonFunctions.isNotNull;
import static com.pe.pcm.utils.CommonFunctions.removeENC;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Kiran Reddy.
 */
@RestController
@RequestMapping(path = "pcm/app/shutdown-context")
@PreAuthorize(SA_AD)
public class ShutdownResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShutdownResource.class);

    private String userPassKey;
    private final ApplicationContext appContext;
    private final PasswordUtilityService passwordUtilityService;


    @Autowired
    public ShutdownResource(@Value("${cm.cmks}") String userPassKey, ApplicationContext appContext, PasswordUtilityService passwordUtilityService) {
        this.userPassKey = userPassKey;
        this.appContext = appContext;
        this.passwordUtilityService = passwordUtilityService;
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public void shutdownContext(HttpServletRequest request, @RequestParam String shutdownUser, @RequestParam String keyPhrase) {
        if (!request.getRequestURL().toString().contains("localhost")) {
            throw internalServerError("We can't call this API from External. Only local call is allowed.");
        } else {
            LOGGER.info("Stopping the Application using shutdown Resource.");
        }

        if (isNotNull(shutdownUser) && shutdownUser.equals("cmsysadmin") && isNotNull(keyPhrase) && keyPhrase.equals(userPassKey)) {
            initiateShutdown();
        } else {
            throw internalServerError("Please Provide valid details.");
        }
    }

    private void initiateShutdown() {
        LOGGER.info("**************************************");
        LOGGER.info("     APPLICATION GETTING SHUTDOWN     ");
        LOGGER.info("**************************************");
        System.exit(SpringApplication.exit(appContext, (ExitCodeGenerator) () -> 0));
    }

    @PostConstruct
    public void loadData() {
        if (isNotNull(userPassKey)) {
            if (userPassKey.startsWith("ENC")) {
                try {
                    userPassKey = passwordUtilityService.decrypt(removeENC(userPassKey));
                } catch (CommunityManagerServiceException e) {
                    if (e.getErrorMessage().equals("IllegalBlockSizeException")) {
                        LOGGER.error("Community Manager ShutDown Resource Password is Not properly Encrypted (cm.cmks)");
                        initiateShutdown();
                    }
                }
            }
        } else {
            userPassKey = ".A4(SI@KPa#9-Z";
        }
    }

}
