package com.pe.pcm.logout;

import com.pe.pcm.constants.ProfilesConstants;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.logout.entity.UserTokenExpEntity;
import com.pe.pcm.miscellaneous.UserUtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import static com.pe.pcm.constants.SecurityConstants.HEADER_STRING;
import static com.pe.pcm.constants.SecurityConstants.TOKEN_PREFIX;
import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;
import static java.lang.Boolean.TRUE;

/**
 * @author Kiran Reddy.
 */
@Service
public class LogoutService {
    private final UserTokenExpRepository userTokenExpRepository;
    private final UserUtilityService userUtilityService;

    @Autowired
    public LogoutService(UserTokenExpRepository userTokenExpRepository, UserUtilityService userUtilityService) {
        this.userTokenExpRepository = userTokenExpRepository;
        this.userUtilityService = userUtilityService;
    }

    @Transactional
    public void invalidateToken(HttpServletRequest httpServletRequest) {
        String profileName = userUtilityService.getProfileName();
        String headerAuthInfo = httpServletRequest.getHeader(HEADER_STRING);
        String token;
        if (!headerAuthInfo.startsWith("Basic")) {
            switch (profileName) {
                case ProfilesConstants.SSO_SSP_SEAS:
                    token = httpServletRequest.getHeader("x-auth-token").trim();
                    break;
                case ProfilesConstants.CM:
                case ProfilesConstants.PCM:
                    token = headerAuthInfo.replace(TOKEN_PREFIX, "").trim();
                    break;
                default:
                    throw internalServerError("There is no token found for Invalidation.");
            }
            String userNameFromToken = userUtilityService.getUserFromToken(token);
            if (userUtilityService.getUserOrRole(TRUE).equals(userNameFromToken)) {
                userTokenExpRepository.save(new UserTokenExpEntity()
                        .setToken(token)
                        .setUserid(userNameFromToken));
            }
        } else {
            throw internalServerError("There is no token found for Invalidation.");
        }


    }

}
