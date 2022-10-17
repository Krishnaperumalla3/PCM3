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

package com.pe.pcm.config.properties;

import com.pe.pcm.properties.CMJwtProperties;
import com.pe.pcm.properties.CMProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static com.pe.pcm.utils.CommonFunctions.isNotNull;

/**
 * @author Kiran Reddy.
 */

@Component
public class CustomPropertiesManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomPropertiesManager.class);

    private final Boolean sfgEnabled;
    private final String appCustomName;

    private final CMProperties cmProperties;
    private final Environment environment;
    private final CMJwtProperties cmJwtProperties;


    @Autowired
    public CustomPropertiesManager(@Value("${sterling-b2bi.b2bi-api.sfg-api.active}") Boolean sfgEnabled,
                                   @Value("${server.serverHeader}") String appCustomName,
                                   CMProperties cmProperties,
                                   Environment environment, CMJwtProperties cmJwtProperties) {
        this.sfgEnabled = sfgEnabled;
        this.appCustomName = appCustomName;
        this.cmProperties = cmProperties;
        this.environment = environment;
        this.cmJwtProperties = cmJwtProperties;
    }

    public void preLoadPropertiesAndValidations() {
        loadAppName();
        loadAppColor();
        loadSfgEnable();
        loadDeploymentType();
        loadJwtSchedulerCron();
        loadSfgV6UpdatableValue();
        LOGGER.info("Custom Properties are Loaded successfully.");
    }

    private void loadSfgV6UpdatableValue() {
        String sfgV6UpdateTag = "sterling-b2bi.sfg-v6-update";
        String sfgV6Update = environment.getProperty(sfgV6UpdateTag);
        if (isNotNull(sfgV6Update)) {
            System.setProperty(sfgV6UpdateTag, sfgV6Update);
        } else {
            System.setProperty(sfgV6UpdateTag, "false");
        }
    }

    private void loadAppColor() {
        String colorTag = "cm.color";
        List<String> availableColors = Arrays.asList("red", "green", "yellow", "grey", "black");
        if (isNotNull(cmProperties.getColor())) {
            System.setProperty(colorTag, availableColors.contains(cmProperties.getColor()) ? cmProperties.getColor() : "black");
        } else {
            System.setProperty(colorTag, "black");
        }

        LOGGER.info("{}, {} theme enabled.", appCustomName, cmProperties.getColor());
    }

    private void loadSfgEnable() {
        if (!isNotNull(sfgEnabled)) {
            System.setProperty("sterling-b2bi.b2bi-api.sfg-api.active", "false");
        }
    }

    private void loadAppName() {
        if (!isNotNull(appCustomName)) {
            System.setProperty("server.serverHeader", "PragmaEdge Community Manager");
        }
    }

    private void loadDeploymentType() {
        System.setProperty("cm.cm-deployment", String.valueOf(cmProperties.getCmDeployment()));
        LOGGER.info("CM Deployment: {}", cmProperties.getCmDeployment());
    }

    private void loadJwtSchedulerCron() {
        System.setProperty("jwt.cron.expression", String.valueOf(cmJwtProperties.getSchedulerCron()));
    }

}
