package com.pe.pcm.security;

import com.pe.pcm.jwt.auth.GlobalAuthenticationEntryPoint;
import com.pe.pcm.jwt.auth.filter.JwtAndBasicAuthFilter;
import com.pe.pcm.jwt.auth.provider.JwtAndBasicAuthUserService;
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

import static com.pe.pcm.constants.ProfilesConstants.*;
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
@Profile({PCM, CM, CM_API})
public class JwtAndBasicWebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAndBasicAuthFilter jwtAndBasicAuthFilter;
    private final GlobalAuthenticationEntryPoint unauthorizedHandler;
    private final JwtAndBasicAuthUserService jwtAndBasicAuthUserService;

    @Autowired
    public JwtAndBasicWebSecurityConfig(JwtAndBasicAuthFilter jwtAndBasicAuthFilter,
                                        JwtAndBasicAuthUserService jwtAndBasicAuthUserService,
                                        GlobalAuthenticationEntryPoint unauthorizedHandler) {
        this.jwtAndBasicAuthFilter = jwtAndBasicAuthFilter;
        this.jwtAndBasicAuthUserService = jwtAndBasicAuthUserService;
        this.unauthorizedHandler = unauthorizedHandler;
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(jwtAndBasicAuthUserService).passwordEncoder(passwordEncoder());
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
        http
                .cors()
                .and()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/api/auth/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").denyAll()
                .antMatchers(HttpMethod.HEAD, "/**").denyAll()
                .antMatchers(HttpMethod.PATCH, "/**").denyAll()
                .antMatchers(HttpMethod.TRACE, "/**").denyAll()
                .antMatchers("/api/test/**").permitAll()
                .antMatchers(HttpMethod.POST, GENERATE_TOKEN, OAUTH_GEN_TOKEN, GENERAL_APIS, SHUTDOWN_CONTEXT).permitAll()
                .antMatchers(HttpMethod.GET, "/", "/pcm", "/activate/**", "/swagger", "/swagger-ui.html",
                        "/swagger-ui/", "/swagger-ui/index.html", "/swagger-ui/**", "/webjars/**",
                        "/configuration/**", "/swagger-resources/**", "/v2/api-docs", "/pcm/login/test",
                        SM_LOGIN, SLASH, CSS, JS, FAV_ICON, GIF, PNG, JPG, ASSETS_IMG, ASSETS_SVG, ACTIVE_PROFILE,
                        GENERAL_APIS, LOGO, ASSETS_I18N,
                        "/assets/i18n/**", "/assets/i18n/reports/**", "/pcm/login").permitAll()
                .antMatchers(REST_API_ALL).permitAll()
                .anyRequest()
                .authenticated();

        http.addFilterBefore(jwtAndBasicAuthFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
