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

package com.pe.pcm.jwt.saml.auth.filter;

import com.nimbusds.jwt.SignedJWT;
import com.pe.pcm.jwt.saml.auth.JwtAuthenticationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;
import java.util.Map;

import static com.pe.pcm.constants.SecurityConstants.ROLE;
import static com.pe.pcm.constants.SecurityConstants.SAML_USER;

/**
 * @author Shameer.
 */
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String HEADER_SECURITY_TOKEN = "x-auth-token";

    private final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    public JwtAuthenticationFilter(final String matcher, AuthenticationManager authenticationManager) {
        super(matcher);
        super.setAuthenticationManager(authenticationManager);

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        final String token = request.getHeader(HEADER_SECURITY_TOKEN);
        JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(token);
        return getAuthenticationManager().authenticate(jwtAuthenticationToken);
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        final SignedJWT signedJWT = (SignedJWT) authResult.getPrincipal();
        Map<String, Object> claims = null;
        try {
            claims = signedJWT.getJWTClaimsSet().getClaims();
        } catch (ParseException e) {
            log.debug("Error Message {} ", e.getMessage());
        }

        if (claims != null) {
            String userName = (String) claims.get(SAML_USER);
            String role = (String) claims.get(ROLE);
            log.debug("UserName In Jwt AuthFilter{}", userName);
            JwtAuthenticationToken authentication = new JwtAuthenticationToken(signedJWT, userName, Collections.singletonList(new SimpleGrantedAuthority(role)));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        SecurityContextHolder.clearContext();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    }

}
