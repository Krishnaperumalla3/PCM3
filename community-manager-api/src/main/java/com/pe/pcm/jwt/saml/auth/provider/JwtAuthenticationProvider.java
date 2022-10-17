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

package com.pe.pcm.jwt.saml.auth.provider;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import com.pe.pcm.jwt.saml.auth.JwtAuthenticationToken;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static com.pe.pcm.constants.SecurityConstants.SAML_JWT_SECRET_KEY;

/**
 * @author Shameer.
 */
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    public Authentication authenticate(Authentication authentication) {

        Assert.notNull(authentication, "Authentication is missing");

        Assert.isInstanceOf(JwtAuthenticationToken.class, authentication,
                "This method only accepts JwtAuthenticationToken");

        String jwtToken = authentication.getName();

        if (authentication.getPrincipal() == null || jwtToken == null) {
            throw new AuthenticationCredentialsNotFoundException("Authentication token is missing");
        }


        final SignedJWT signedJWT;
        try {

            signedJWT = SignedJWT.parse(jwtToken);

            boolean isVerified = signedJWT.verify(new MACVerifier(SAML_JWT_SECRET_KEY));

            if (!isVerified) {
                throw new BadCredentialsException("Invalid token signature");
            }

            // Token expiration checking
            LocalDateTime expirationTime = LocalDateTime.ofInstant(
                    signedJWT.getJWTClaimsSet().getExpirationTime().toInstant(), ZoneId.systemDefault());

            if (LocalDateTime.now(ZoneId.systemDefault()).isAfter(expirationTime)) {
                throw new CredentialsExpiredException("Token expired");
            }

            return new JwtAuthenticationToken(signedJWT, null, null);

        } catch (ParseException e) {
            throw new InternalAuthenticationServiceException("Unreadable token");
        } catch (JOSEException e) {
            throw new InternalAuthenticationServiceException("Unreadable signature");
        }
    }

}
