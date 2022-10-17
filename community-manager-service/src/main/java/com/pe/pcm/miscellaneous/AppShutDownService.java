/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc.
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

package com.pe.pcm.miscellaneous;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * @author Kiran Reddy.
 */
@Service
public class AppShutDownService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppShutDownService.class);

    private final ApplicationContext appContext;
    private static final String STAR_LINE = "************************************";
    private static final String SHUTDOWN = "    APPLICATION GETTING SHUTDOWN    ";

    @Autowired
    public AppShutDownService(ApplicationContext appContext) {
        this.appContext = appContext;
    }

    public void initiateShutdown(String reason) {
        System.out.println();
        System.out.println(STAR_LINE);
        System.out.println(SHUTDOWN);
        System.out.println(STAR_LINE);
        System.out.println("Reason : " + reason);
        LOGGER.error(STAR_LINE);
        LOGGER.error(SHUTDOWN);
        LOGGER.error(STAR_LINE);
        LOGGER.error("Reason : {}", reason);
        System.exit(SpringApplication.exit(appContext, (ExitCodeGenerator) () -> 0));
    }
}
