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

package com.pe.pcm.config.tomcat;


import com.pe.pcm.config.properties.CustomPropertiesManager;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.AbstractProtocol;
import org.apache.coyote.ajp.AbstractAjpProtocol;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.apache.coyote.http11.Http11NioProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.hasText;

/**
 * @author Kiran Reddy.
 */
@Configuration
@PropertySource("application.yml")
public class EmbeddedTomcatConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmbeddedTomcatConfig.class);

    private final int ajpPort;
    private final boolean tomcatAjpEnabled;
    private final Environment environment;
    private final ServerProperties serverProperties;
    private final ApplicationContext applicationContext;
    private final CustomPropertiesManager customPropertiesManager;

    @Autowired
    public EmbeddedTomcatConfig(@Value("${server.ajp.port}") int ajpPort,
                                @Value("${server.ajp.enabled}") boolean tomcatAjpEnabled,
                                Environment environment,
                                ServerProperties serverProperties,
                                ApplicationContext applicationContext,
                                CustomPropertiesManager customPropertiesManager) {
        this.ajpPort = ajpPort;
        this.tomcatAjpEnabled = tomcatAjpEnabled;
        this.environment = environment;
        this.serverProperties = serverProperties;
        this.applicationContext = applicationContext;
        this.customPropertiesManager = customPropertiesManager;
    }

    @Bean
    public TomcatServletWebServerFactory servletContainer() {
        loadSSLPassword();
        customPropertiesManager.preLoadPropertiesAndValidations();
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addConnectorCustomizers(connector -> {
            ((AbstractProtocol) connector.getProtocolHandler()).setConnectionTimeout(8000);
            ((AbstractHttp11Protocol<?>) connector.getProtocolHandler()).setUseServerCipherSuitesOrder(true);
            connector.setAllowTrace(true);
        });
        if (tomcatAjpEnabled) {
            Connector ajpConnector = new Connector("AJP/1.3");
            ajpConnector.setPort(ajpPort);
            ajpConnector.setSecure(false);
            ajpConnector.setAllowTrace(false);
            ajpConnector.setScheme(serverProperties.getSsl().isEnabled() ? "https" : "http");
            ((AbstractAjpProtocol) ajpConnector.getProtocolHandler()).setSecretRequired(false);
            ((AbstractProtocol) ajpConnector.getProtocolHandler()).setConnectionTimeout(8000);
            tomcat.addAdditionalTomcatConnectors(ajpConnector);
        }
        Set<Integer> allPorts = new HashSet<>();
        allPorts.add(serverProperties.getPort());
        String serverStringPorts = environment.getProperty("server.ports");
        Set<Integer> serverPorts;
        if (hasText(serverStringPorts)) {
            serverPorts = Arrays.stream(serverStringPorts.split(","))
                    .filter(StringUtils::hasText)
                    .map(String::trim)
                    .collect(Collectors.toSet())
                    .stream()
                    .map(Integer::parseInt)
                    .collect(Collectors.toSet());
            allPorts.addAll(serverPorts);
            serverPorts.forEach(serverPort -> {
                if (serverPort != 0 && !serverPort.equals(serverProperties.getPort())
                        && !(tomcatAjpEnabled && serverPort.equals(ajpPort))
                ) {
                    if (serverProperties.getSsl().isEnabled()) {
                        tomcat.addAdditionalTomcatConnectors(createSslConnector(serverPort));
                    } else {
                        tomcat.addAdditionalTomcatConnectors(createConnector(serverPort));
                    }
                } else {
                    LOGGER.info("Starting on {} port has been skipped, because this port may be null or already in use", serverPort);
                }
            });
            LOGGER.info("Loading additional ports.");
        } else {
            LOGGER.info("No additional ports configured.");
        }

        String httpStringPorts = environment.getProperty("server.http-ports");
        List<Integer> httpPortsList;
        if (serverProperties.getSsl().isEnabled() && hasText(httpStringPorts)) {
            LOGGER.info("Loading Custom HTTP Ports");
            httpPortsList = Arrays.stream(httpStringPorts.split(","))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList())
                    .stream()
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            httpPortsList.forEach(httpPort -> {
                if (!allPorts.contains(httpPort)) {
                    tomcat.addAdditionalTomcatConnectors(createConnector(httpPort));
                } else {
                    LOGGER.info("Port already in use.");
                }
            });
        } else {
            LOGGER.info("Custom HTTP Ports are not loading because application is not starting on SSL");
        }

        return tomcat;
    }

    private void loadSSLPassword() {
        if (serverProperties.getSsl().isEnabled()) {
            LOGGER.info("Loading Application keystore password/cmks.");
            if (!hasText(serverProperties.getSsl().getKeyStorePassword())) {
                String password = environment.getProperty("server.ssl.key-store-cmks");
                if (hasText(password)) {
                    System.setProperty("server.ssl.key-store-password", password);
                    serverProperties.getSsl().setKeyStorePassword(password);
                    LOGGER.info("Application keystore cmks loaded.");
                } else {
                    final String reason = "You should pass the password for server.ssl.key-store-cmks or server.ssl.key-store-password in YML we start the application on SSL";
                    System.out.println("Reason: ");
                    LOGGER.error("Reason: {}", reason);
                    System.exit(0);
                }
            } else {
                LOGGER.info("Application keystore password loaded.");
            }
        } else {
            LOGGER.info("Application SSL disabled.");
        }
    }

    private Connector createConnector(int port) {
        Connector connector = new Connector("HTTP/1.1");
        connector.setPort(port);
        connector.setScheme("http");
        return connector;
    }

    private Connector createSslConnector(int port) {

        String keystorePath = filePath(serverProperties.getSsl().getKeyStore());
        if (keystorePath != null) {
            Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
            Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
            connector.setScheme("https");
            connector.setSecure(true);
            connector.setPort(port);
            protocol.setConnectionTimeout(8000);
            protocol.setSSLEnabled(true);
            protocol.setKeystoreFile(keystorePath);
            protocol.setKeystorePass(serverProperties.getSsl().getKeyStorePassword());
//            protocol.setTruststoreFile(truststore.getAbsolutePath());
//            protocol.setTruststorePass("changeit");
//            protocol.setKeyAlias("apitester");
            return connector;
        }
        shutdownApp("Provided keystore is not available, keystore: " + serverProperties.getSsl().getKeyStore());
        return new Connector();
    }

    private String filePath(String path) {
        if (hasText(path)) {
            if (path.startsWith("classpath")) {
                try {
                    return applicationContext.getResource(path.trim()).getFile().getAbsolutePath();
                } catch (IOException e) {
                    return null;
                }
            } else {
                Resource resource = applicationContext.getResource("file:" + path.trim());
                if (resource.exists()) {
                    return path.trim();
                }
            }
        }
        return null;
    }

    private static void shutdownApp(String reason) {
        System.out.println("Reason: " + reason);
        LOGGER.error("Reason: {}", reason);
        System.exit(0);
    }

}
