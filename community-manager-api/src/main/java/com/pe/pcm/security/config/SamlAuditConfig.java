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

package com.pe.pcm.security.config;


import com.pe.pcm.constants.AuthoritiesConstants;
import com.pe.pcm.constants.ProfilesConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static com.pe.pcm.constants.ProfilesConstants.SAML;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@Profile(SAML)
public class SamlAuditConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of(getCurrentUser().orElse(AuthoritiesConstants.SUPER_ADMIN));
    }

    private Optional<String> getCurrentUser() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getDetails().toString());
    }

}
