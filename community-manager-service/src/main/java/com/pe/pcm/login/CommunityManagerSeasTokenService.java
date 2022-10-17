package com.pe.pcm.login;

import com.pe.pcm.constants.ProfilesConstants;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.seas.ManageSEASClientService;
import com.pe.pcm.user.UserAttemptsService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import static com.pe.pcm.constants.SecurityConstants.*;
import static java.time.ZoneOffset.UTC;

/**
 * @author Kiran Reddy.
 */
@Service
@Profile(ProfilesConstants.SSO_SSP_SEAS)
public class CommunityManagerSeasTokenService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommunityManagerSeasTokenService.class);
    private final String secretKey;
    private final Long sessionExpire;
    private Integer maxWrongLoginAttempts;
    private Integer resetFalseAttempts;
    private final UserAttemptsService userAttemptsService;
    private final ManageSEASClientService manageSEASClientService;

    public CommunityManagerSeasTokenService(@Value("${jwt.secretkey}") String secretKey,
                                            @Value("${jwt.session-expire}") Long sessionExpire,
                                            @Value("${login.max-false-attempts}") Integer maxWrongLoginAttempts,
                                            @Value("${login.reset-false-attempts}") Integer resetFalseAttempts,
                                            UserAttemptsService userAttemptsService,
                                            ManageSEASClientService manageSEASClientService) {
        this.secretKey = secretKey;
        this.sessionExpire = sessionExpire;
        this.maxWrongLoginAttempts = maxWrongLoginAttempts;
        this.resetFalseAttempts = resetFalseAttempts;
        this.userAttemptsService = userAttemptsService;
        this.manageSEASClientService = manageSEASClientService;
    }

    public Optional<CommunityManagerUserModel> authenticate(CommunityManagerLoginModel cmProfile) {
        //TODO : needs to check on SSO case
        userAttemptsService.getUserAttempts(cmProfile.getUserName()).ifPresent(userAttemptsEntity -> {
            boolean isAccountRest = userAttemptsService.autoResetFailAttempts(userAttemptsEntity, resetFalseAttempts);
            if (!isAccountRest && userAttemptsEntity.getAttempts() >= maxWrongLoginAttempts) {
                throw new CommunityManagerServiceException(HttpStatus.NOT_ACCEPTABLE.value(), "Exceeded max no of failed login attempts, please try after some time or contact system admin.");
            }
        });
        return Optional.of(manageSEASClientService.validateAndManage(cmProfile));

    }

    public Optional<CommunityManagerUserModel> getTokenDetails(String userId, String token, Cookie[] cookies) {
        return manageSEASClientService.getTokenDetails(userId, token, cookies);
    }

    public String tokenFor(CommunityManagerUserModel user) {
        LOGGER.debug("In CMTokenService, tokenFor Method");
        Date expiration = Date.from(LocalDateTime.now(UTC).plusMinutes(sessionExpire).toInstant(UTC));
        return Jwts.builder().setSubject(SUBJECT).setExpiration(expiration)
                .setIssuer(ISSUER)
                .claim(USERNAME, user.getUserId())
                .claim(ROLE, user.getUserRole())
                .claim(IS_SI_USER, user.isSiUser())
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public String getSspLogoutURL() {
        return manageSEASClientService.getSspLogoutURL();
    }


    @PostConstruct
    void loadDefaultValues() {
        if (maxWrongLoginAttempts == null || maxWrongLoginAttempts == 0) {
            maxWrongLoginAttempts = 5;
        }

        if (resetFalseAttempts == null || resetFalseAttempts == 0) {
            resetFalseAttempts = 30;
        }
    }

}
