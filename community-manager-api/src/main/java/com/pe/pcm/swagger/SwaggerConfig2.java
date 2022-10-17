/*
 *
 *  * Copyright (c) 2020 Pragma Edge Inc
 *  *
 *  * Licensed under the Pragma Edge Inc
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  * https://pragmaedge.com/licenseagreement
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.pe.pcm.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;

import static com.pe.pcm.constants.ProfilesConstants.CM;
import static com.pe.pcm.constants.ProfilesConstants.SSO_SSP_SEAS;
import static java.util.Collections.singletonList;
import static org.springframework.util.StringUtils.hasLength;

@Configuration
@EnableSwagger2
@Profile({SSO_SSP_SEAS, CM})
public class SwaggerConfig2 {

    private final Environment environment;

    @Autowired
    public SwaggerConfig2(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("^(?!.*(error)).*"))
                .build()
                .apiInfo(apiInfo())
                .protocols(loadProtocol())
                .securitySchemes(apiKeyAndBasicScheme())
                .securityContexts(Arrays.asList(securityContext(), securityContext1()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("Community Manager REST API",
                "CM Endpoints for consumption",
                "1.0",
                null,
                new Contact("CommunityManager", "", null),
                null, null, Collections.emptyList());
    }

    private SecurityContext securityContext1() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth1())
                .forPaths(PathSelectors.regex("/api/v1.*"))
                .build();
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/api/v1.*"))
                .build();
    }

    private List<SecurityReference> defaultAuth1() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return singletonList(new SecurityReference("basicAuth", authorizationScopes));
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return singletonList(new SecurityReference("apiKey", authorizationScopes));
    }

    private List<SecurityScheme> apiKeyAndBasicScheme() {
        String activeProfile = environment.getActiveProfiles()[0];

        List<SecurityScheme> schemeList = new ArrayList<>();
        schemeList.add(new BasicAuth("basicAuth"));
        schemeList.add(new ApiKey("apiKey", activeProfile.equals(CM) ? "Authorization" : "x-auth-token", "header"));
        return schemeList;
    }

    @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder()
                .appName("Community Manager REST API")
                .scopeSeparator(",")
                .useBasicAuthenticationWithAccessCodeGrant(false) //change gere to false
                .build();
    }

    @Bean
    UiConfiguration uiConfiguration() {
        return UiConfigurationBuilder.builder().build();
    }

    Set<String> loadProtocol() {
        Set<String> protocol = new HashSet<>();
        String sslEnabled = environment.getProperty("server.ssl.enabled");
        if (hasLength(sslEnabled)) {
            if (Boolean.parseBoolean(sslEnabled)) {
                protocol.add("HTTPS");
            } else {
                protocol.add("HTTP");
            }
        } else {
            String isHttps = environment.getProperty("server.ssl.key-store");
            if (hasLength(isHttps)) {
                protocol.add("HTTPS");
            } else {
                protocol.add("HTTP");
            }
        }
        return protocol;
    }

//    private List<Parameter> globalParameterList() {
//        Parameter authTokenHeader = new ParameterBuilder()
//                .name("SM_USER") // name of the header
//                .modelRef(new ModelRef("string")) // data-type of the header
//                .required(false) // required/optional
//                .parameterType("header") // for query-param, this value can be 'query'
//                .description("SSO-SSP-SEAS Token UserName")
//                .build();
//        return Collections.singletonList(authTokenHeader);
//    }

}
