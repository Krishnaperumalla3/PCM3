package com.pe.pcm.jwt.auth.filter;

import com.pe.pcm.jwt.seas.SsoSeasAuthFilter;
import com.pe.pcm.login.CommunityManagerLoginModel;
import com.pe.pcm.login.CommunityManagerTokenService;
import com.pe.pcm.login.CommunityManagerUserModel;
import com.pe.pcm.seas.user.UserDetailsImpl;
import com.pe.pcm.utils.PCMConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
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
import java.util.Collections;
import java.util.Optional;

import static com.pe.pcm.constants.ProfilesConstants.*;
import static com.pe.pcm.constants.SecurityConstants.HEADER_STRING;
import static com.pe.pcm.constants.SecurityConstants.TOKEN_PREFIX;
import static org.springframework.util.StringUtils.hasText;

/**
 * @author Kiran Reddy.
 */
@Component
@Profile({PCM, CM, CM_API})
public class JwtAndBasicAuthFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(SsoSeasAuthFilter.class);

    private final Environment environment;
    private final CommunityManagerTokenService pragmaCMTokenService;

    @Autowired
    public JwtAndBasicAuthFilter(CommunityManagerTokenService pragmaCMTokenService, Environment environment) {
        this.pragmaCMTokenService = pragmaCMTokenService;
        this.environment = environment;
    }

    @Override
    protected void doFilterInternal(@Nullable HttpServletRequest request, @Nullable HttpServletResponse response, @Nullable FilterChain filterChain)
            throws ServletException, IOException {
        if (request == null) {
            throw new AuthenticationException("something went wrong, please try again.");
        }
        String profile = environment.getActiveProfiles()[0];
        if (response != null) {
            response.setHeader(PCMConstants.PROFILE_NAMES, profile);
        }
        if (hasText(profile)) {
            String headerAuthInfo = request.getHeader(HEADER_STRING);
            if (hasText(headerAuthInfo)) {
                if (headerAuthInfo.startsWith("Basic") && (profile.equals(CM_API) || profile.equals(CM))) {
                    LOGGER.info("In basic Auth validation");
                    byte[] data = Base64.getDecoder().decode(headerAuthInfo.replace("Basic ", ""));
                    if (data != null && data.length > 0) {
                        String authDetails = new String(data, StandardCharsets.ISO_8859_1);
                        if (authDetails.contains(":")) {
                            String username = authDetails.split(":")[0];
                            String password = authDetails.split(":")[1];
                            Optional<CommunityManagerUserModel> cmUserOptional = pragmaCMTokenService.authenticate(new CommunityManagerLoginModel(username, password), false);
                            if (cmUserOptional.isPresent()) {
                                cmUserOptional.get().setRoles(Collections.singletonList(cmUserOptional.get().getUserRole()));
                                UserDetails userDetails = UserDetailsImpl.build(cmUserOptional.get());
                                UsernamePasswordAuthenticationToken authentication =
                                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                                SecurityContextHolder.getContext().setAuthentication(authentication);
                            }
                        }
                    }
                } else if (headerAuthInfo.startsWith(TOKEN_PREFIX) && (profile.equals(PCM) || profile.equals(CM))) {
                    LOGGER.info("In Token Auth validation");
                    String token = headerAuthInfo.replace(TOKEN_PREFIX, "").trim();
                    if (!pragmaCMTokenService.isTokenExpired(token)) {
                        pragmaCMTokenService.verifyToken(token).ifPresent(communityManagerUserModel -> {
                            communityManagerUserModel.setRoles(Collections.singletonList(communityManagerUserModel.getUserRole()));
                            UserDetails userDetails = UserDetailsImpl.build(communityManagerUserModel);
                            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());
                            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        });
                    }
                }
            }
        }

        if (filterChain != null) {
            filterChain.doFilter(request, response);
        }
    }
}
