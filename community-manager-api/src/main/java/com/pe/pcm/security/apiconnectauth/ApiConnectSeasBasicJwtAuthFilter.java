package com.pe.pcm.security.apiconnectauth;

import com.pe.pcm.apiconnecct.APIAuthDataRepository;
import com.pe.pcm.apiconnecct.APIProxyEndpointService;
import com.pe.pcm.exception.GlobalAuthenticationException;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.login.CommunityManagerLoginModel;
import com.pe.pcm.login.CommunityManagerTokenService;
import com.pe.pcm.login.CommunityManagerUserModel;
import com.pe.pcm.seas.ManageSEASClientService;
import com.pe.pcm.seas.user.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.concurrent.atomic.AtomicBoolean;

import static com.pe.pcm.constants.ProfilesConstants.SSO_SSP_SEAS;
import static com.pe.pcm.constants.SecurityConstants.HEADER_STRING;
import static com.pe.pcm.constants.SecurityConstants.TOKEN_PREFIX;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.springframework.util.StringUtils.hasLength;
import static org.springframework.util.StringUtils.hasText;

/**
 * @author Kiran Reddy.
 */
@Component
@Profile({SSO_SSP_SEAS})
public class ApiConnectSeasBasicJwtAuthFilter implements ApiConnectAuthFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiConnectSeasBasicJwtAuthFilter.class);

    private final ManageSEASClientService manageSEASClientService;
    private final APIProxyEndpointService apiProxyEndpointService;
    private final APIAuthDataRepository apiAuthDataRepository;
    private final CommunityManagerTokenService communityManagerTokenService;

    public ApiConnectSeasBasicJwtAuthFilter(
            ManageSEASClientService manageSEASClientService,
            APIProxyEndpointService apiProxyEndpointService,
            APIAuthDataRepository apiAuthDataRepository,
            CommunityManagerTokenService communityManagerTokenService) {
        this.manageSEASClientService = manageSEASClientService;
        this.apiProxyEndpointService = apiProxyEndpointService;
        this.apiAuthDataRepository = apiAuthDataRepository;
        this.communityManagerTokenService = communityManagerTokenService;
    }

    @Override
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
                            status.set(basicAuth(request));
                            break;
                        case "TOKEN_AUTH":
                        case "OAUTH2_2_0":
                            status.set(tokenAuth(request));
                            break;
                        default:
                            //Implementation not required here
                    }
                });
        if (!status.get()) {
            throw GlobalExceptionHandler.customError(HttpStatus.NOT_ACCEPTABLE.value(), "Unauthorized to access the resource.");
        }
    }

    private boolean basicAuth(HttpServletRequest request) {
        LOGGER.info("In SSO-SEAS basic Auth validation");
        String headerAuthInfo = request.getHeader(HEADER_STRING);
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
                        return TRUE;
                    } else {
                        throw new GlobalAuthenticationException("username/password invalid.");
                    }
                }
            }
        }
        return FALSE;
    }

    private boolean tokenAuth(HttpServletRequest request) {
        LOGGER.info("In SSO-SEAS Token Auth validation");
        AtomicBoolean result = new AtomicBoolean(FALSE);
        String headerAuthInfo = request.getHeader(HEADER_STRING);
        String xAuthToken = request.getHeader("x-auth-token");
        if (headerAuthInfo.startsWith(TOKEN_PREFIX)) {
            throw new GlobalAuthenticationException("Please provide the proper header name for token.");
        } else if (hasLength(xAuthToken) && !xAuthToken.equals("null") && !communityManagerTokenService.isTokenExpired(xAuthToken.trim())) {
            communityManagerTokenService.verifyToken1(xAuthToken).ifPresent(communityManagerUserModel -> {
                UserDetails userDetails = UserDetailsImpl.build(communityManagerUserModel);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                result.set(TRUE);
            });
        }
        return result.get();
    }
}
