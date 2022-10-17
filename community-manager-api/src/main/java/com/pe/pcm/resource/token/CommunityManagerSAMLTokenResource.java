///*
// * Copyright (c) 2020 Pragma Edge Inc
// *
// * Licensed under the Pragma Edge Inc
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// * https://pragmaedge.com/licenseagreement
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package com.pe.pcm.resource.token;
//
//import com.nimbusds.jose.JOSEException;
//import com.nimbusds.jose.JWSAlgorithm;
//import com.nimbusds.jose.JWSHeader;
//import com.nimbusds.jose.crypto.MACSigner;
//import com.nimbusds.jwt.JWTClaimsSet;
//import com.nimbusds.jwt.SignedJWT;
//import com.pe.pcm.constants.ProfilesConstants;
//import com.pe.pcm.saml.GenericResponse;
//import com.pe.pcm.saml.SamlUserDetails;
//import io.swagger.annotations.Api;
//import org.joda.time.DateTime;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Profile;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import static com.pe.pcm.constants.SecurityConstants.*;
//
///**
// * @author Shameer.
// */
//@RestController
//@RequestMapping("/auth")
//@Profile(ProfilesConstants.SAML)
//@Api(tags = "SAML Resource", hidden = true)
//public class CommunityManagerSAMLTokenResource {
//
//    private static final Logger logger = LoggerFactory.getLogger(CommunityManagerSAMLTokenResource.class);
//
//    @Value("${saml.jwt.session-expire}")
//    private int jwtSessionExpire;
//
//    @GetMapping("/token")
//    public GenericResponse token(Authentication authentication) throws JOSEException {
//        if (authentication != null) {
//            logger.debug("user principal {}", authentication.getPrincipal());
//        }
//
//        final DateTime dateTime = DateTime.now();
//        SamlUserDetails details = (authentication != null && authentication.getPrincipal() instanceof SamlUserDetails) ? (SamlUserDetails) authentication.getPrincipal() : new SamlUserDetails();
//
//        //build claims
//        JWTClaimsSet.Builder jwtClaimsSetBuilder = new JWTClaimsSet.Builder();
//        jwtClaimsSetBuilder.expirationTime(dateTime.plusMinutes(jwtSessionExpire).toDate());
//        jwtClaimsSetBuilder.claim("PCM", "Service");
//        jwtClaimsSetBuilder.claim(SAML_USER, details.getUsername());
//        details.getAuthorities()
//                .stream()
//                .findFirst()
//                .ifPresent(grantedAuthority -> jwtClaimsSetBuilder.claim(ROLE, grantedAuthority.getAuthority()));
//
//        logger.debug("AuthCon username {}", details.getUsername());
//        logger.debug("AuthCon role {}", details.getAuthorities());
//
//        //signature
//        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), jwtClaimsSetBuilder.build());
//        signedJWT.sign(new MACSigner(SAML_JWT_SECRET_KEY));
//        details.setToken(signedJWT.serialize());
//
//        return new GenericResponse(true, details);
//    }
//
//
//}
