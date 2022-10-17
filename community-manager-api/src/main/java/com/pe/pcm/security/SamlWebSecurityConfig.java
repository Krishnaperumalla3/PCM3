/*
package com.pe.pcm.security;


import com.google.common.collect.ImmutableList;
import com.pe.pcm.constants.ProfilesConstants;
import com.pe.pcm.jwt.saml.auth.SamlWithRelayStateEntryPoint;
import com.pe.pcm.jwt.saml.auth.filter.JwtAuthenticationFilter;
import com.pe.pcm.jwt.saml.auth.provider.JwtAuthenticationProvider;
import com.pe.pcm.saml.SamlUserDetailsServiceImpl;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.velocity.app.VelocityEngine;
import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.saml2.metadata.provider.ResourceBackedMetadataProvider;
import org.opensaml.util.resource.FilesystemResource;
import org.opensaml.util.resource.ResourceException;
import org.opensaml.xml.parse.StaticBasicParserPool;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.saml.*;
import org.springframework.security.saml.context.SAMLContextProviderImpl;
import org.springframework.security.saml.context.SAMLContextProviderLB;
import org.springframework.security.saml.key.JKSKeyManager;
import org.springframework.security.saml.key.KeyManager;
import org.springframework.security.saml.log.SAMLDefaultLogger;
import org.springframework.security.saml.metadata.*;
import org.springframework.security.saml.parser.ParserPoolHolder;
import org.springframework.security.saml.processor.HTTPPostBinding;
import org.springframework.security.saml.processor.HTTPRedirectDeflateBinding;
import org.springframework.security.saml.processor.SAMLBinding;
import org.springframework.security.saml.processor.SAMLProcessorImpl;
import org.springframework.security.saml.trust.httpclient.TLSProtocolConfigurer;
import org.springframework.security.saml.trust.httpclient.TLSProtocolSocketFactory;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;
import org.springframework.security.saml.util.VelocityFactory;
import org.springframework.security.saml.websso.*;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;

import java.util.*;

import static com.pe.pcm.constants.SecurityConstants.*;

*
 * @author Shameer.


@Configuration
@Profile(ProfilesConstants.SAML)
@EnableWebSecurity
@ComponentScan(basePackages = "com.pe.pcm.jwt.saml.auth")
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SamlWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${saml.host}")
    private String host;

    @Value("${saml.scheme}")
    private String scheme;

    @Value("${server.port}")
    private int port;

    @Value("${saml.url.entity}")
    private String entityURL;

    @Value("${saml.idp.entity-id}")
    private String entityIdp;

    @Value("${saml.ssl.store-cmks}")
    private String storePass;

    @Value("${saml.ssl.key-alias}")
    private String alias;

    @Value("${saml.ssl.key-cmks}")
    private String keyPass;

    @Value("${saml.ssl.key-store}")
    private String jksFile;

    @Value("${saml.url.client}")
    private String clientHostUrl;

    @Value("${saml.idp.metadata}")
    private String idpMetadata;


    @Bean
    public WebSSOProfileOptions defaultWebSSOProfileOptions() {
        WebSSOProfileOptions webSSOProfileOptions = new WebSSOProfileOptions();
        webSSOProfileOptions.setIncludeScoping(false);
        webSSOProfileOptions.setBinding("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST");
        // Relay state can also be set here
        // webSSOProfileOptions.setRelayState("...")
        return webSSOProfileOptions;
    }

    @Bean
    public SAMLEntryPoint samlEntryPoint() {

        SAMLEntryPoint samlEntryPoint = new SamlWithRelayStateEntryPoint();

        samlEntryPoint.setDefaultProfileOptions(defaultWebSSOProfileOptions());

        return samlEntryPoint;
    }

    @Bean
    public MetadataDisplayFilter metadataDisplayFilter() {
        return new MetadataDisplayFilter();
    }

    @Bean
    public SimpleUrlAuthenticationFailureHandler authenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler();
    }

    @Bean
    public SavedRequestAwareAuthenticationSuccessHandler successRedirectHandler() {
        SavedRequestAwareAuthenticationSuccessHandler successRedirectHandler = new SAMLRelayStateSuccessHandler();
        return successRedirectHandler;
    }

    @Bean
    public SAMLProcessingFilter samlWebSSOProcessingFilter() throws Exception {
        SAMLProcessingFilter samlWebSSOProcessingFilter = new SAMLProcessingFilter();
        samlWebSSOProcessingFilter.setAuthenticationManager(authenticationManager());
        samlWebSSOProcessingFilter.setAuthenticationSuccessHandler(successRedirectHandler());
        samlWebSSOProcessingFilter.setAuthenticationFailureHandler(authenticationFailureHandler());
        return samlWebSSOProcessingFilter;
    }

    @Bean
    public HttpStatusReturningLogoutSuccessHandler successLogoutHandler() {
        return new HttpStatusReturningLogoutSuccessHandler();
    }

    // TODO : Handler for successful logout
     * @Bean public SimpleUrlLogoutSuccessHandler successLogoutHandler() {
     * SimpleUrlLogoutSuccessHandler successLogoutHandler = new
     * SimpleUrlLogoutSuccessHandler();
     * //System.out.println("==============default url ");
     * successLogoutHandler.setTargetUrlParameter("targetUrlParameter");; return
     * successLogoutHandler; }



    @Bean
    public SecurityContextLogoutHandler logoutHandler() {

        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.setInvalidateHttpSession(true);
        logoutHandler.setClearAuthentication(true);
        return logoutHandler;
    }

    @Bean
    public SAMLLogoutFilter samlLogoutFilter() {
        return new SAMLLogoutFilter(successLogoutHandler(), new LogoutHandler[]{logoutHandler()},
                new LogoutHandler[]{logoutHandler()});
    }

    @Bean
    public SAMLLogoutProcessingFilter samlLogoutProcessingFilter() {
        return new SAMLLogoutProcessingFilter(successLogoutHandler(), logoutHandler());
    }

    @Bean
    public MetadataGeneratorFilter metadataGeneratorFilter() {
        return new MetadataGeneratorFilter(metadataGenerator());
    }

    @Bean
    public MetadataGenerator metadataGenerator() {
        MetadataGenerator metadataGenerator = new MetadataGenerator();
        metadataGenerator.setEntityId(entityIdp);
        metadataGenerator.setEntityBaseURL(entityURL);
        metadataGenerator.setExtendedMetadata(extendedMetadata());
        metadataGenerator.setIncludeDiscoveryExtension(false);
        metadataGenerator.setKeyManager(keyManager());
        return metadataGenerator;
    }

    @Bean
    public KeyManager keyManager() {

        Map<String, String> passwords = new HashMap<>();
        passwords.put(this.alias, this.keyPass);
        return new JKSKeyManager(new FileSystemResource(jksFile), this.storePass, passwords, this.alias);
    }

    @Bean
    public ExtendedMetadata extendedMetadata() {
        ExtendedMetadata extendedMetadata = new ExtendedMetadata();
        extendedMetadata.setIdpDiscoveryEnabled(false);
        extendedMetadata.setSignMetadata(false);
        return extendedMetadata;
    }

    @Bean
    public FilterChainProxy samlFilter() throws Exception {
        List<SecurityFilterChain> chains = new ArrayList<>();

        chains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher("/saml/metadata/**"),
                metadataDisplayFilter()));

        chains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher("/saml/login/**"), samlEntryPoint()));

        chains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher("/saml/SSO/**"),
                samlWebSSOProcessingFilter()));

        chains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher("/saml/logout/**"), samlLogoutFilter()));

        chains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher("/saml/SingleLogout/**"),
                samlLogoutProcessingFilter()));

        return new FilterChainProxy(chains);
    }

    @Bean
    public TLSProtocolConfigurer tlsProtocolConfigurer() {
        return new TLSProtocolConfigurer();
    }

    @Bean
    public ProtocolSocketFactory socketFactory() {
        return new TLSProtocolSocketFactory(keyManager(), null, "default");
    }

    @Bean
    public Protocol socketFactoryProtocol() {
        return new Protocol("https", socketFactory(), 443);
    }

    @Bean
    public MethodInvokingFactoryBean socketFactoryInitialization() {
        MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
        methodInvokingFactoryBean.setTargetClass(Protocol.class);
        methodInvokingFactoryBean.setTargetMethod("registerProtocol");
        Object[] args = {"https", socketFactoryProtocol()};
        methodInvokingFactoryBean.setArguments(args);
        return methodInvokingFactoryBean;
    }

    @Bean
    public VelocityEngine velocityEngine() {
        return VelocityFactory.getEngine();
    }

    @Bean(initMethod = "initialize")
    public StaticBasicParserPool parserPool() {
        return new StaticBasicParserPool();
    }

    @Bean(name = "parserPoolHolder")
    public ParserPoolHolder parserPoolHolder() {
        return new ParserPoolHolder();
    }

    @Bean
    public HTTPPostBinding httpPostBinding() {
        return new HTTPPostBinding(parserPool(), velocityEngine());
    }

    @Bean
    public HTTPRedirectDeflateBinding httpRedirectDeflateBinding() {
        return new HTTPRedirectDeflateBinding(parserPool());
    }

    @Bean
    public SAMLProcessorImpl processor() {
        Collection<SAMLBinding> bindings = new ArrayList<>();
        bindings.add(httpRedirectDeflateBinding());
        bindings.add(httpPostBinding());
        return new SAMLProcessorImpl(bindings);
    }

    @Bean
    public HttpClient httpClient() {
        return new HttpClient(multiThreadedHttpConnectionManager());
    }

    @Bean
    public MultiThreadedHttpConnectionManager multiThreadedHttpConnectionManager() {
        return new MultiThreadedHttpConnectionManager();
    }

    @Bean
    public static SAMLBootstrap sAMLBootstrap() {
        return new SAMLBootstrap();
    }

    @Bean
    public SAMLDefaultLogger samlLogger() {
        return new SAMLDefaultLogger();
    }

    @Bean
    public SAMLContextProviderImpl contextProvider() {
        SAMLContextProviderLB samlContextProviderLB = new SAMLContextProviderLB();
        samlContextProviderLB.setScheme(scheme);
        samlContextProviderLB.setServerName(host);
        samlContextProviderLB.setServerPort(port);
        samlContextProviderLB.setIncludeServerPortInRequestURL(true);
        samlContextProviderLB.setContextPath("/");
        return samlContextProviderLB;
    }

    // SAML 2.0 WebSSO Assertion Consumer
    @Bean
    public WebSSOProfileConsumer webSSOprofileConsumer() {
        WebSSOProfileConsumerImpl consumer = new WebSSOProfileConsumerImpl();
        consumer.setMaxAuthenticationAge(21600);
        consumer.setMaxAssertionTime(21600);
        consumer.setResponseSkew(21600);
        return consumer;
    }

    // SAML 2.0 Web SSO profile
    @Bean
    public WebSSOProfile webSSOprofile() {
        return new WebSSOProfileImpl();
    }

    // not used but autowired...
    // SAML 2.0 Holder-of-Key WebSSO Assertion Consumer
    @Bean
    public WebSSOProfileConsumerHoKImpl hokWebSSOprofileConsumer() {
        return new WebSSOProfileConsumerHoKImpl();
    }

    // not used but autowired...
    // SAML 2.0 Holder-of-Key Web SSO profile
    @Bean
    public WebSSOProfileConsumerHoKImpl hokWebSSOProfile() {
        return new WebSSOProfileConsumerHoKImpl();
    }

    @Bean
    public SingleLogoutProfile logoutprofile() {
        return new SingleLogoutProfileImpl();
    }

    @Bean
    public ExtendedMetadataDelegate idpMetadata() throws MetadataProviderException, ResourceException {

        Timer backgroundTaskTimer = new Timer(true);
        ResourceBackedMetadataProvider resourceBackedMetadataProvider = new ResourceBackedMetadataProvider(
                backgroundTaskTimer, new FilesystemResource(idpMetadata.trim()));

        resourceBackedMetadataProvider.setParserPool(parserPool());

        ExtendedMetadataDelegate extendedMetadataDelegate = new ExtendedMetadataDelegate(resourceBackedMetadataProvider,
                extendedMetadata());
        extendedMetadataDelegate.setMetadataTrustCheck(true);
        extendedMetadataDelegate.setMetadataRequireSignature(false);
        return extendedMetadataDelegate;
    }

    @Bean
    @Qualifier("metadata")
    public CachingMetadataManager metadata() throws MetadataProviderException, ResourceException {
        List<MetadataProvider> providers = new ArrayList<>();
        providers.add(idpMetadata());
        return new CachingMetadataManager(providers);
    }

    @Bean
    public SAMLUserDetailsService samlUserDetailsService() {
        return new SamlUserDetailsServiceImpl();
    }

    @Bean
    public SAMLAuthenticationProvider samlAuthenticationProvider() {
        SAMLAuthenticationProvider samlAuthenticationProvider = new SAMLAuthenticationProvider();
        samlAuthenticationProvider.setUserDetails(samlUserDetailsService());
        samlAuthenticationProvider.setForcePrincipalAsString(false);
        return samlAuthenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(samlAuthenticationProvider());
    }

     *     SAML Security Configurations


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.exceptionHandling().authenticationEntryPoint(samlEntryPoint());
        http.csrf().disable();

        http.addFilterBefore(metadataGeneratorFilter(), ChannelProcessingFilter.class).addFilterAfter(samlFilter(),
                BasicAuthenticationFilter.class);

        http.csrf().disable().authorizeRequests().antMatchers("/payment/S2S", "/payment/B2S", "/payment/get-request-data/**", "/payment/get-info/**")
                .permitAll()
                .antMatchers("/error").permitAll().antMatchers("/saml/**", "/pcm", "/activate/**", "/swagger", "/swagger-ui.html", "/webjars/**", "/configuration/**", "/swagger-resources/**", "/v2/api-docs", SLASH, CSS, JS, FAV_ICON, GIF, PNG, JPG, ACTIVE_PROFILE, LOGO, ASSETS_I18N).permitAll().anyRequest()
                .authenticated();

        http.logout().logoutSuccessUrl("/").deleteCookies("JSESSIONID").invalidateHttpSession(true);
    }

     *   JWT Security Configuration Class


    @Configuration
    @Profile(value = "saml")
    @Order(1)
    public static class RestApiSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            final CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(ImmutableList.of("*"));
            configuration.setAllowedMethods(ImmutableList.of("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
            configuration.setAllowCredentials(true);
            configuration.setAllowedHeaders(ImmutableList.of("*"));
            http.csrf().disable().
                    antMatcher("/").
                    authorizeRequests().
                    antMatchers("/").
                    hasAnyAuthority("admin", "super_admin").
                    anyRequest().authenticated().and().exceptionHandling().and().
                    addFilterBefore(new JwtAuthenticationFilter("/", super.authenticationManager()), UsernamePasswordAuthenticationFilter.class);
        }

        @Override
        public void configure(final WebSecurity web) {
            web.ignoring().antMatchers("/pcm", "/pcm/utility/**", "/pcm/correlation/**", "/pcm/pooling-interval/**", "/activate/**", "/swagger", "/swagger-ui.html", "/webjars/**", "/configuration/**", "/swagger-resources/**", "/v2/api-docs",
                    SLASH, CSS, JS, FAV_ICON, GIF, PNG, JPG, ACTIVE_PROFILE, LOGO, ASSETS_I18N);
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) {
            auth.authenticationProvider(new JwtAuthenticationProvider());
        }
    }

 *
     * Rest security configuration for /api/


    @Configuration
    @Profile(value = "saml")
    @Order(2)
    public static class AuthSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher(SAMLTOKEN).authorizeRequests()
                    .anyRequest().authenticated();
        }
    }

}
*/
