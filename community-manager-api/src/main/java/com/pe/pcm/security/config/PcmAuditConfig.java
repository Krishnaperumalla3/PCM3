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
//package com.pe.pcm.security.config;
//
//import com.pe.pcm.constants.ProfilesConstants;
//import com.pe.pcm.jwt.auth.JwtAuthToken;
//import com.pe.pcm.jwt.auth.JwtAuthenticatedProfile;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jws;
//import io.jsonwebtoken.Jwts;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.data.domain.AuditorAware;
//import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//import java.util.Optional;
//
//import static com.pe.pcm.constants.AuthoritiesConstants.SUPER_ADMIN;
//import static com.pe.pcm.constants.ProfilesConstants.PCM;
//import static com.pe.pcm.constants.SecurityConstants.USERNAME;
//
///**
// * @author Kiran Reddy.
// */
//@Configuration
//@EnableJpaAuditing(auditorAwareRef = "auditorAware")
//@Profile(PCM)
//public class PcmAuditConfig {
//
//    @Value("${jwt.secretkey}")
//    private String secretKey;
//
//    @Bean
//    public AuditorAware<String> auditorAware() {
//        return () -> Optional.of(getCurrentUser().orElse(SUPER_ADMIN));
//    }
//
//    private Optional<String> getCurrentUser() {
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//
//        return Optional.ofNullable(securityContext.getAuthentication())
//                .map(authentication -> {
//                    if (authentication instanceof JwtAuthToken) {
//                        JwtAuthToken jwtAuthToken = (JwtAuthToken) authentication;
//                        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtAuthToken.getCredentials().toString());
//                        return claims.getBody().get(USERNAME).toString().trim();
//                    } else if (authentication instanceof JwtAuthenticatedProfile) {
//                        JwtAuthenticatedProfile jwtAuthenticatedProfile = (JwtAuthenticatedProfile) authentication;
//                        return jwtAuthenticatedProfile.getName();
//                    }
//                    return null;
//                });
//    }
//}
