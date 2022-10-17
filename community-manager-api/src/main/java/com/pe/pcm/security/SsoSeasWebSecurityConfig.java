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

package com.pe.pcm.security;

import com.pe.pcm.jwt.auth.GlobalAuthenticationEntryPoint;
import com.pe.pcm.jwt.seas.SsoSeasAuthFilter;
import com.pe.pcm.seas.ManageSEASClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.pe.pcm.constants.ProfilesConstants.SSO_SSP_SEAS;
import static com.pe.pcm.constants.SecurityConstants.*;

/**
 * @author Kiran Reddy.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        // jsr250Enabled = true,
        prePostEnabled = true)
@Profile(SSO_SSP_SEAS)
public class SsoSeasWebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final ManageSEASClientService manageSEASClientService;
    private final GlobalAuthenticationEntryPoint unauthorizedHandler;
    private final SsoSeasAuthFilter authenticationJwtTokenFilter;

    @Autowired
    public SsoSeasWebSecurityConfig(ManageSEASClientService manageSEASClientService,
                                    GlobalAuthenticationEntryPoint unauthorizedHandler,
                                    SsoSeasAuthFilter authenticationJwtTokenFilter) {
        this.manageSEASClientService = manageSEASClientService;
        this.unauthorizedHandler = unauthorizedHandler;
        this.authenticationJwtTokenFilter = authenticationJwtTokenFilter;
    }


    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(manageSEASClientService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").denyAll()
                .antMatchers(HttpMethod.HEAD, "/**").denyAll()
                .antMatchers(HttpMethod.PATCH, "/**").denyAll()
                .antMatchers(HttpMethod.TRACE, "/**").denyAll()
                .antMatchers("/api/test/**").permitAll()
                .antMatchers(HttpMethod.POST, "/pcm/seas/generate-token", OAUTH_GEN_TOKEN, GENERATE_TOKEN, GENERAL_APIS, SHUTDOWN_CONTEXT)
                .permitAll()
                .antMatchers(HttpMethod.GET, "/", GET_SEAS_TOKEN_INFO, "/pcm/seas/get-ssp-logout-url", "/pcm", "/activate/**", "/swagger", "/swagger-ui.html", "/webjars/**",
                        "/configuration/**", "/swagger-ui/", "/swagger-ui/index.html", "/swagger-resources/**", "/swagger-ui/**", "/v2/api-docs", "/pcm/login/test",
                        SM_LOGIN, SLASH, CSS, JS, FAV_ICON, GIF, PNG, JPG, ASSETS_IMG, ASSETS_SVG, ACTIVE_PROFILE,
                        GENERAL_APIS, LOGO, ASSETS_I18N, "/assets/i18n/**", "/assets/i18n/reports/**", "/pcm/test", "/pcm/login")
                .permitAll()
                .antMatchers(REST_API_ALL).permitAll()
                .anyRequest()
                .authenticated();

        http.addFilterBefore(authenticationJwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
