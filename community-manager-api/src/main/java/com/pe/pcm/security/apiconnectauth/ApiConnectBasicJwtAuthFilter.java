package com.pe.pcm.security.apiconnectauth;

import com.pe.pcm.apiconnecct.APIAuthDataRepository;
import com.pe.pcm.apiconnecct.APIProxyEndpointService;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.login.CommunityManagerLoginModel;
import com.pe.pcm.login.CommunityManagerTokenService;
import com.pe.pcm.login.CommunityManagerUserModel;
import com.pe.pcm.seas.user.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.pe.pcm.constants.ProfilesConstants.*;
import static com.pe.pcm.constants.SecurityConstants.HEADER_STRING;
import static com.pe.pcm.constants.SecurityConstants.TOKEN_PREFIX;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.springframework.util.StringUtils.hasText;

/**
 * @author Kiran Reddy.
 */
@Component
@Profile({PCM, CM_API, CM})
public class ApiConnectBasicJwtAuthFilter implements ApiConnectAuthFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiConnectBasicJwtAuthFilter.class);

    private final APIProxyEndpointService apiProxyEndpointService;
    private final APIAuthDataRepository apiAuthDataRepository;
    private final CommunityManagerTokenService communityManagerTokenService;

    @Autowired
    public ApiConnectBasicJwtAuthFilter(APIProxyEndpointService apiProxyEndpointService,
                                        APIAuthDataRepository apiAuthDataRepository,
                                        CommunityManagerTokenService communityManagerTokenService) {
        this.apiProxyEndpointService = apiProxyEndpointService;
        this.apiAuthDataRepository = apiAuthDataRepository;
        this.communityManagerTokenService = communityManagerTokenService;
    }

    public void authenticate(HttpServletRequest request, String apiName, String webMethodName) {
        AtomicBoolean status = new AtomicBoolean(FALSE);
        apiProxyEndpointService.findByApiNameAndWebMethod(apiName, webMethodName)
                .flatMap(apiProxyEndpointEntity ->
                        apiAuthDataRepository.findFirstByApiConfigTypeAndApiId(
                                "PROXY_AUTH",
                                apiProxyEndpointEntity.getPkId())
                ).ifPresent(apiAuthDataEntity -> {
                    switch (apiAuthDataEntity.getAuthType()) {
                        case "NO_AUTH":
                            LOGGER.info("No Authentication enabled.");
                            status.set(TRUE);
                            break;
                        case "BASIC_AUTH":
                            LOGGER.info("In Basic Auth Validation");
                            status.set(basicAuth(request));
                            break;
                        case "TOKEN_AUTH":
                        case "OAUTH2_2_0":
                            LOGGER.info("In Token/OAuth base Auth");
                            status.set(tokenAuth(request));
                            break;
                        default:
                            //Implementation not required here
                    }
                });
        if (!status.get()) {
            throw GlobalExceptionHandler.customError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized to access the resource.");
        }
    }

    private boolean basicAuth(HttpServletRequest request) {
        AtomicBoolean result = new AtomicBoolean(FALSE);
        String headerAuthInfo = request.getHeader(HEADER_STRING);
        if (hasText(headerAuthInfo) && headerAuthInfo.startsWith("Basic")) {
            byte[] data = Base64.getDecoder().decode(headerAuthInfo.replace("Basic ", ""));
            if (data != null && data.length > 0) {
                String authDetails = new String(data, StandardCharsets.ISO_8859_1);
                if (authDetails.contains(":")) {
                    String[] auth = authDetails.split(":");
                    communityManagerTokenService.authenticate(new CommunityManagerLoginModel(auth[0], auth[1]), false).ifPresent(
                            communityManagerUserModel -> result.set(loadAuhDetails(request, communityManagerUserModel))
                    );
                }
            }
        }
        return result.get();
    }

    private boolean tokenAuth(HttpServletRequest request) {
        AtomicBoolean result = new AtomicBoolean(FALSE);
        String headerAuthInfo = request.getHeader(HEADER_STRING);
        if (headerAuthInfo.startsWith(TOKEN_PREFIX)) {
            String token = headerAuthInfo.replace(TOKEN_PREFIX, "").trim();
            if (!communityManagerTokenService.isTokenExpired(token)) {
                communityManagerTokenService.verifyToken(token).ifPresent(
                        communityManagerUserModel -> result.set(loadAuhDetails(request, communityManagerUserModel))
                );
            }
        }
        return result.get();
    }

    private boolean loadAuhDetails(HttpServletRequest request, CommunityManagerUserModel communityManagerUserModel) {
        communityManagerUserModel.setRoles(Collections.singletonList(communityManagerUserModel.getUserRole()));
        UserDetails userDetails = UserDetailsImpl.build(communityManagerUserModel);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return TRUE;
    }

}
