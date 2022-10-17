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

package com.pe.pcm.jwt.seas;

import com.pe.pcm.exception.GlobalAuthenticationException;
import com.pe.pcm.login.CommunityManagerLoginModel;
import com.pe.pcm.login.CommunityManagerTokenService;
import com.pe.pcm.login.CommunityManagerUserModel;
import com.pe.pcm.seas.ManageSEASClientService;
import com.pe.pcm.seas.user.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static com.pe.pcm.constants.ProfilesConstants.SSO_SSP_SEAS;
import static com.pe.pcm.utils.PCMConstants.CUSTOM_ERROR_ATTRIBUTE;
import static com.pe.pcm.utils.PCMConstants.PROFILE_NAMES;
import static org.springframework.util.StringUtils.hasLength;
import static org.springframework.util.StringUtils.hasText;

/**
 * @author Kiran Reddy.
 */
@Component
@Profile(SSO_SSP_SEAS)
public class SsoSeasAuthFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(SsoSeasAuthFilter.class);

    private final ManageSEASClientService manageSEASClientService;
    private final CommunityManagerTokenService communityManagerTokenService;

    @Autowired
    public SsoSeasAuthFilter(ManageSEASClientService manageSEASClientService,
                             CommunityManagerTokenService communityManagerTokenService) {
        this.manageSEASClientService = manageSEASClientService;
        this.communityManagerTokenService = communityManagerTokenService;
    }

    @Override
    protected void doFilterInternal(@Nullable HttpServletRequest request,
                                    @Nullable HttpServletResponse response,
                                    @Nullable FilterChain filterChain)
            throws ServletException, IOException {

        if (request == null) {
            throw new AuthenticationException("something went wrong, please try again.");
        }
        try {
            String headerAuthInfo = request.getHeader("Authorization");
            String xAuthToken = request.getHeader("x-auth-token");
            if (hasText(headerAuthInfo) && headerAuthInfo.startsWith("Basic")) {
                byte[] data = Base64.getDecoder().decode(headerAuthInfo.replace("Basic ", ""));
                if (data != null && data.length > 0) {
                    String authDetails = new String(data, StandardCharsets.ISO_8859_1);
                    if (authDetails.contains(":")) {
                        String username = authDetails.split(":")[0];
                        String password = authDetails.split(":")[1];
                        CommunityManagerUserModel communityManagerUserModel
                                = manageSEASClientService.authenticate(new CommunityManagerLoginModel(username, password));
                        if (communityManagerUserModel.getAuthenticated()) {
                            UserDetails userDetails = UserDetailsImpl.build(communityManagerUserModel);
                            UsernamePasswordAuthenticationToken authentication
                                    = new UsernamePasswordAuthenticationToken(userDetails,
                                    null,
                                    userDetails.getAuthorities());
                            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        } else {
                            throw new GlobalAuthenticationException("username/password invalid.");
                        }
                    }
                }
            } else if (hasLength(xAuthToken) && !xAuthToken.equals("null")) {
                if (!communityManagerTokenService.isTokenExpired(xAuthToken.trim())) {
                    communityManagerTokenService.verifyToken1(xAuthToken).ifPresent(communityManagerUserModel -> {
                        UserDetails userDetails = UserDetailsImpl.build(communityManagerUserModel);
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    });
                }
            }
        } catch (GlobalAuthenticationException gae) {
            request.setAttribute(CUSTOM_ERROR_ATTRIBUTE, gae.getMessage());
        } catch (Exception e) {
            LOGGER.error("Validation failed in doFilter: ", e);
        }

        if (filterChain != null) {
            if (response != null) {
                response.setHeader(PROFILE_NAMES, SSO_SSP_SEAS);
            }
            filterChain.doFilter(request, response);
        }

    }

}
