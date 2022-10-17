package com.pe.pcm.login;

import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.exception.GlobalAuthenticationException;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.miscellaneous.CmCloseableHttpClient;
import com.pe.pcm.oauth2.OAuthInputModel;
import com.pe.pcm.properties.CMJwtProperties;
import com.pe.pcm.properties.OAuth2Properties;
import com.pe.pcm.user.UserAttemptsService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.io.IOUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.pe.pcm.constants.AuthoritiesConstants.SUPER_ADMIN;
import static com.pe.pcm.constants.SecurityConstants.*;
import static com.pe.pcm.utils.PCMConstants.CONTENT_TYPE;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.time.ZoneOffset.UTC;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.util.StringUtils.hasText;

/**
 * @author Kiran Reddy.
 */
@Service
public class CommunityMangerOAuthTokenService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommunityMangerOAuthTokenService.class);
    private final CMJwtProperties cmJwtProperties;
    private final OAuth2Properties oAuth2Properties;
    private final UserAttemptsService userAttemptsService;
    final CmCloseableHttpClient cmCloseableHttpClient;
    private Integer maxWrongLoginAttempts;
    private Integer resetFalseAttempts;

    @Autowired
    public CommunityMangerOAuthTokenService(CMJwtProperties cmJwtProperties,
                                            OAuth2Properties oAuth2Properties,
                                            UserAttemptsService userAttemptsService,
                                            CmCloseableHttpClient cmCloseableHttpClient,
                                            @Value("${login.max-false-attempts}") Integer maxWrongLoginAttempts,
                                            @Value("${login.reset-false-attempts}") Integer resetFalseAttempts) {
        this.cmJwtProperties = cmJwtProperties;
        this.oAuth2Properties = oAuth2Properties;
        this.userAttemptsService = userAttemptsService;
        this.cmCloseableHttpClient = cmCloseableHttpClient;
        this.maxWrongLoginAttempts = maxWrongLoginAttempts;
        this.resetFalseAttempts = resetFalseAttempts;
    }

    public Optional<CommunityManagerUserModel> authenticate(OAuthInputModel oAuthInputModel, HttpServletRequest
            httpServletRequest) {
        validateAndLoadDataFromHeaders(oAuthInputModel, httpServletRequest);
        oAuthValidation(oAuthInputModel);
        userAttemptsService.getUserAttempts(oAuthInputModel.getUsername()).ifPresent(userAttemptsEntity -> {
            boolean isAccountRest = userAttemptsService.autoResetFailAttempts(userAttemptsEntity, resetFalseAttempts);
            if (!isAccountRest && userAttemptsEntity.getAttempts() >= maxWrongLoginAttempts) {
                throw new CommunityManagerServiceException(HttpStatus.NOT_ACCEPTABLE.value(), "Exceeded max no of failed login attempts, please try after some time or contact system admin.");
            }
        });
        CommunityManagerUserModel communityManagerUserModel = new CommunityManagerUserModel(oAuthInputModel.getUsername(), SUPER_ADMIN);
        return Optional.of(communityManagerUserModel);
    }

    public String tokenFor(CommunityManagerUserModel user) {
        LOGGER.debug("In CMTokenService, tokenFor Method");
        Date expiration = Date.from(LocalDateTime.now(UTC).plusMinutes(cmJwtProperties.getSessionExpire()).toInstant(UTC));
        return Jwts.builder().setSubject(SUBJECT).setExpiration(expiration)
                .setIssuer(ISSUER)
                .claim(USERNAME, user.getUserId())
                .claim(ROLE, user.getUserRole())
                .claim(IS_SI_USER, user.isSiUser())
                .signWith(SignatureAlgorithm.HS512, cmJwtProperties.getSecretkey())
                .compact();
    }

    private void validateAndLoadDataFromHeaders(OAuthInputModel oAuthInputModel, HttpServletRequest httpServletRequest) {
        if (!hasText(oAuthInputModel.getGrantType())) {
            oAuthInputModel.setGrantType(httpServletRequest.getHeader("grant-type"))
                    .setClientId(httpServletRequest.getHeader("client-id"))
                    .setClientSecret(httpServletRequest.getHeader("client-secret"))
                    .setScope(httpServletRequest.getHeader("scope"))
                    .setUsername(httpServletRequest.getHeader("username"))
                    .setPassword(httpServletRequest.getHeader("password"));
        }
        if (!hasText(oAuthInputModel.getGrantType())) {
            throw GlobalExceptionHandler.customError(BAD_REQUEST.value(), "grant-type should be provided.");
        }
    }

    private void oAuthValidation(OAuthInputModel oAuthInputModel) {
        try (CloseableHttpClient client = cmCloseableHttpClient.getCloseableHttpClient(oAuth2Properties.getTokenUrl())) {
            HttpPost httpPost = new HttpPost(oAuth2Properties.getTokenUrl());
            List<NameValuePair> urlParameters = new ArrayList<>();
            urlParameters.add(new BasicNameValuePair("grant_type", oAuthInputModel.getGrantType()));
            urlParameters.add(new BasicNameValuePair("client_id", oAuthInputModel.getClientId()));
            urlParameters.add(new BasicNameValuePair("client_secret", oAuthInputModel.getClientSecret()));
            urlParameters.add(new BasicNameValuePair("scope", oAuthInputModel.getScope()));
            urlParameters.add(new BasicNameValuePair("resource", oAuthInputModel.getResource()));
            urlParameters.add(new BasicNameValuePair("username", oAuthInputModel.getUsername()));
            urlParameters.add(new BasicNameValuePair("password", oAuthInputModel.getPassword()));
            httpPost.setEntity(new UrlEncodedFormEntity(urlParameters));
            LOGGER.debug("Request {}", urlParameters);
            httpPost.setHeader(CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
            CloseableHttpResponse response = client.execute(httpPost);
            String finalResponse = IOUtils.toString(response.getEntity().getContent(), UTF_8);
            if (response.getStatusLine().getStatusCode() == 200) {
                LOGGER.debug(finalResponse);
            } else {
                throw new GlobalAuthenticationException(new JSONObject(finalResponse).getString("error_description"));
            }
        } catch (IOException | NoSuchAlgorithmException | KeyManagementException e) {
            throw new GlobalAuthenticationException(e.getMessage());
        }

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
