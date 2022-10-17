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

package com.pe.pcm;

import com.pe.pcm.config.properties.DatabaseSSLProperties;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.miscellaneous.PasswordUtilityService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static com.pe.pcm.constants.AuthoritiesConstants.CM_APP_VERSION;
import static com.pe.pcm.constants.ProfilesConstants.CM_API;
import static com.pe.pcm.constants.ProfilesConstants.DEFAULT;
import static com.pe.pcm.constants.ProfilesConstants.*;
import static com.pe.pcm.utils.CommonFunctions.isNotNull;
import static com.pe.pcm.utils.CommonFunctions.removeENC;
import static com.pe.pcm.utils.PCMConstants.*;
import static java.lang.Boolean.TRUE;
import static java.lang.Boolean.parseBoolean;
import static org.springframework.util.StringUtils.hasLength;
import static org.springframework.util.StringUtils.hasText;

/**
 * @author Kiran Reddy.
 */

@EnableAsync
@Configuration
@EnableScheduling
@SpringBootApplication
@EnableTransactionManagement
public class CommunityManagerApiApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommunityManagerApiApplication.class);
    private static final String SPRING_PROF_ACTIVE = "spring.profiles.active";

    @Value("${accept-licence}")
    private Boolean licenceAgreed;

    @Value("${spring.mail.cmks}")
    private String mailPassword;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    private static String APP_NAME;
    private static String SERVER_PORT;
    private static boolean SSL_ENABLED;

    @Autowired
    private DatabaseSSLProperties databaseSSLProperties;

    @Autowired
    private ServerProperties serverProperties;

    @Autowired
    private Environment environment;

    public static void main(String[] args) {


        System.setProperty("pcm.version", CM_APP_VERSION);
        //To Handle: Failed to start bean 'documentationPluginsBootstrapper'
        System.setProperty("spring.mvc.pathmatch.matching-strategy", "ant_path_matcher");

        String profile = System.getProperty(SPRING_PROF_ACTIVE);

        if (hasText(profile)) {
            List<String> profiles = Arrays.asList(CM, PCM, SAML, CM_API, SSO_SSP_SEAS, DEFAULT);
            if (!profiles.contains(profile.trim())) {
                shutdownApp("Invalid profile name. Please provide the valid profile.");
            }
        } else {
            shutdownApp("Please provide the valid profile name to start the application.");
        }


        String pass = System.getProperty("spring.datasource.cmks");
        if (pass != null && pass.length() > 0) {
            if (pass.startsWith("ENC")) {
                try {
                    pass = new PasswordUtilityService().decrypt(removeENC(pass));
                } catch (CommunityManagerServiceException e) {
                    shutdownApp("Unable to Decrypt the Value of spring.datasource.cmks, Please Provide the Proper Encrypted Value");
                }

            }
            System.setProperty("spring.datasource.password", pass);
        } else {
            shutdownApp("You should pass the value for spring.datasource.cmks");
        }

        SpringApplication.run(CommunityManagerApiApplication.class, args);
        logCMApplicationStartup();
    }

    public static void start() {
        SpringApplication.run(CommunityManagerApiApplication.class);
    }

    @Bean
    public MessageSource messageSource() {
        LOGGER.info("Licence Agreed : {}", licenceAgreed);
        licenceVerify(licenceAgreed);
        cmCheckForSSLLoading();
        loadDbSSLData();
        loadDbType();
        loadMailPassword();
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean validator(MessageSource messageSource) {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource);
        return bean;
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.US);
        return localeResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    private void licenceVerify(Boolean licenceAgreed) {
        if (licenceAgreed == null || !licenceAgreed) {
            shutdownApp("Accept the licence (accept-licence = true in application.yml file), before start the application.");
        }
    }

    private void loadDbType() {
        final String DB_TYPE_KEY = "dbType";
        if (isNotNull(driverClassName)) {
            if (driverClassName.endsWith("OracleDriver")) {
                System.setProperty(DB_TYPE_KEY, ORACLE);
                LOGGER.info("DB Type: Oracle");
            } else if (driverClassName.endsWith("DB2Driver")) {
                System.setProperty(DB_TYPE_KEY, DB2);
                LOGGER.info("DB Type: DB2");
            } else if (driverClassName.endsWith("SQLServerDriver")) {
                System.setProperty(DB_TYPE_KEY, SQL_SERVER);
                LOGGER.info("DB Type: SQL Server");
            } else {
                LOGGER.error("Community Manager Database DriverClass Name Not Found.");
            }
        } else {
            shutdownApp("Please provide the Database Driver Class Name in yml file(spring.datasource.driver-class-name).");
        }
    }

    private void loadMailPassword() {
        if (hasText(mailPassword)) {
            if (mailPassword.startsWith("ENC")) {
                try {
                    System.setProperty("spring.mail.password", new PasswordUtilityService().decrypt(removeENC(mailPassword)));
                } catch (CommunityManagerServiceException e) {
                    if (e.getErrorMessage().equals("IllegalBlockSizeException")) {
                        shutdownApp("Mail Password is not Encrypted properly(spring.mail.cmks)");
                    }
                }
            } else {
                System.setProperty("spring.mail.password", mailPassword);
            }
        }
    }

    private void cmCheckForSSLLoading() {
        String cmDeployment = environment.getProperty("cm.cm-deployment");
        if (hasText(cmDeployment)) {
            if (parseBoolean(cmDeployment)) {
                SSL_ENABLED = true;
                LOGGER.info("Deployment mode: CM");
                serverProperties.getSsl().setEnabled(TRUE);
                System.setProperty("server.ssl.enabled", "true");
            } else {
                //TODO KIRAN , need to check the ssl enabled or not
                SSL_ENABLED = false;
                LOGGER.info("Normal deployment mode");
            }
        } else {
            SSL_ENABLED = true;
            LOGGER.info("Deployment mode: CM");
            serverProperties.getSsl().setEnabled(TRUE);
            System.setProperty("server.ssl.enabled", "true");
        }
    }

    private void loadDbSSLData() {
        SSL_ENABLED = serverProperties.getSsl().isEnabled();
        if (databaseSSLProperties.getEnabled()) {
            LOGGER.info("Database SSL info loading.");
            if (hasLength(databaseSSLProperties.getTrustStore())) {
                System.setProperty("javax.net.ssl.trustStore", databaseSSLProperties.getTrustStore());
            } else {
                shutdownApp("Please provide the Database TrustStore in YML file (spring.datasource.trust-store)");
            }
            if (hasLength(databaseSSLProperties.getTrustStoreType())) {
                System.setProperty("javax.net.ssl.trustStoreType", databaseSSLProperties.getTrustStoreType());
            } else {
                shutdownApp("Please provide the Database TrustStore Type in YML file (spring.datasource.trust-store-type)");
            }
            if (hasLength(databaseSSLProperties.getTrustStoreCmks())) {
                System.setProperty("javax.net.ssl.trustStorePassword", databaseSSLProperties.getTrustStoreCmks());
            } else {
                shutdownApp("Please provide the Database TrustStore password Type in YML file (spring.datasource.trust-store-cmks)");
            }
        } else {
            LOGGER.info("Skipped Database SSL info loading.");
        }
    }

    private static void logCMApplicationStartup() {
        String protocol;

        if (SSL_ENABLED) {
            protocol = "https";
        } else {
            protocol = "http";
        }

        String contextPath = System.getProperty("server.servlet.context-path");
        if (StringUtils.isBlank(contextPath)) {
            contextPath = "/";
        }
        String hostAddress = "localhost";
        try {
            final String HOST_ADDRESS = System.getProperty("server.host");
            if (hasText(HOST_ADDRESS)) {
                hostAddress = HOST_ADDRESS;
            } else {
                hostAddress = InetAddress.getLocalHost().getHostAddress();
            }
        } catch (UnknownHostException e) {
            LOGGER.warn("The host name could not be determined, using `localhost` as fallback");
        }

        String externalURL = protocol + "://" + hostAddress + ":" + SERVER_PORT;

        System.out.println("------------------------------------------------------------------------------------------");
        System.out.println("\t\tApplication '" + APP_NAME + ", " + CM_APP_VERSION + "' is running! Access URLs:");
        System.out.println("\t\tLocal: \t\t" + protocol + "://localhost:" + SERVER_PORT + contextPath);
        System.out.println("\t\tExternal: \t" + externalURL + contextPath);
        System.out.println("\t\tSwagger:  \t" + externalURL + "/swagger" + contextPath);
        System.out.println("\t\tProfile(s): " + System.getProperty(SPRING_PROF_ACTIVE));
        System.out.println("------------------------------------------------------------------------------------------");

        LOGGER.info("\n------------------------------------------------------------------------------------------\n\t" +
                        "Application '{}, {}' is running! Access URLs:\n\t" +
                        "Local: \t\t{}://localhost:{}{}\n\t" +
                        "External: \t{}://{}:{}{}\n\t" +
                        "Swagger: \t{}://{}:{}/swagger{}\n\t" +
                        "Profile(s): {}\n-------------------------------------------------------------------------------------------",
                APP_NAME, CM_APP_VERSION,
                protocol,
                SERVER_PORT,
                contextPath,
                protocol,
                hostAddress,
                SERVER_PORT,
                contextPath,
                protocol,
                hostAddress,
                SERVER_PORT,
                contextPath,
                System.getProperty(SPRING_PROF_ACTIVE));
    }

    @Value("${server.serverHeader}")
    public void setAppNameStatic(String name) {
        APP_NAME = name;
    }

    @Value("${server.port}")
    public void setAppPortStatic(String port) {
        SERVER_PORT = port;
    }


    private static void shutdownApp(String reason) {
        System.out.println("Reason: " + reason);
        LOGGER.error("Reason: {}", reason);
        System.exit(0);
    }

}
