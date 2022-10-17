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

package com.pe.pcm.miscellaneous;

import com.nimbusds.jwt.SignedJWT;
import com.pe.pcm.constants.AuthoritiesConstants;
import com.pe.pcm.properties.CMJwtProperties;
import com.pe.pcm.seas.user.UserDetailsImpl;
import com.pe.pcm.user.TpUserRepository;
import com.pe.pcm.utils.CommonFunctions;
import com.pe.pcm.utils.PCMConstants;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Map;
import java.util.stream.Collectors;

import static com.pe.pcm.constants.ProfilesConstants.*;
import static com.pe.pcm.constants.SecurityConstants.*;
import static com.pe.pcm.utils.PCMConstants.TP;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * @author Kiran Reddy.
 */
@Service
public class UserUtilityService {

    private final CMJwtProperties cmJwtProperties;
    private final TpUserRepository tpUserRepository;
    private final IndependentService independentService;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserUtilityService.class);

    @Autowired
    public UserUtilityService(CMJwtProperties cmJwtProperties, TpUserRepository tpUserRepository, IndependentService independentService) {
        this.cmJwtProperties = cmJwtProperties;
        this.tpUserRepository = tpUserRepository;
        this.independentService = independentService;
    }

    public String getUserOrRole(boolean isUser) {

        String profile = getProfileName();
        switch (profile) {
            case SAML: {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                final SignedJWT signedJWT = (SignedJWT) authentication.getPrincipal();
                Map<String, Object> claims;
                try {
                    claims = signedJWT.getJWTClaimsSet().getClaims();
                    String userName = (String) claims.get(SAML_USER);
                    String role = (String) claims.get(ROLE);
                    return isUser ? userName : role;
                } catch (ParseException e) {
                    LOGGER.debug("Error Message {} ", e.getMessage());
                }
                break;
            }
            case CM:
            case PCM:
            case CM_API:
            case SSO_SSP_SEAS:
                UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                return isUser ? userDetails.getUsername() : userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()).get(0);
            default:
        }
        return isUser ? PCMConstants.PCM_ADMIN : AuthoritiesConstants.SUPER_ADMIN;
    }

    public String getUserFromToken(String token) {
        String profile = getProfileName();
        switch (profile) {
            case SAML: {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                final SignedJWT signedJWT = (SignedJWT) authentication.getPrincipal();
                Map<String, Object> claims;
                try {
                    claims = signedJWT.getJWTClaimsSet().getClaims();
                    return (String) claims.get(SAML_USER);
                } catch (ParseException e) {
                    LOGGER.debug("Error Message {} ", e.getMessage());
                }
                break;
            }
            case CM:
            case PCM:
            case CM_API:
            case SSO_SSP_SEAS:
                return Jwts.parser().setSigningKey(cmJwtProperties.getSecretkey()).parseClaimsJws(token).getBody().get(USERNAME).toString().trim();
            default:
        }
        return null;
    }


    public void addPartnerToCurrentUser(String pkId, String type) {
        String userRole = getUserOrRole(FALSE);
        if (!(userRole.equalsIgnoreCase(AuthoritiesConstants.SUPER_ADMIN) || userRole.equalsIgnoreCase(AuthoritiesConstants.ADMIN))
                && independentService.getActiveProfile().equals(PCM)) {
            tpUserRepository.findById(getUserOrRole(TRUE)).ifPresent(tpUserEntity -> {
                if (type.equals(TP)) {
                    tpUserRepository.save(SerializationUtils
                            .clone(tpUserEntity.setPartnerList(CommonFunctions.isNotNull(tpUserEntity.getPartnerList()) ? tpUserEntity.getPartnerList() + "," + pkId : pkId)));
                } else {
                    tpUserRepository.save(SerializationUtils
                            .clone(tpUserEntity.setPartnerList(CommonFunctions.isNotNull(tpUserEntity.getGroupList()) ? tpUserEntity.getGroupList() + "," + pkId : pkId)));
                }
            });
        }

    }

    public String getProfileName() {
        return independentService.getActiveProfile();
    }
}
