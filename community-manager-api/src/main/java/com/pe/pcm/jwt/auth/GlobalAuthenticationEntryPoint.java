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

package com.pe.pcm.jwt.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pe.pcm.exception.ErrorMessage;
import com.pe.pcm.miscellaneous.IndependentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.pe.pcm.constants.ProfilesConstants.*;
import static com.pe.pcm.utils.PCMConstants.CUSTOM_ERROR_ATTRIBUTE;

/**
 * @author Kiran Reddy.
 */
@Component
@Profile({PCM, CM_API, CM, SSO_SSP_SEAS})
public class GlobalAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final IndependentService independentService;

    @Autowired
    public GlobalAuthenticationEntryPoint(IndependentService independentService) {
        this.independentService = independentService;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException, ServletException {
        String authError;
        String customError = (String) request.getAttribute(CUSTOM_ERROR_ATTRIBUTE);
        if (StringUtils.hasText(customError)) {
            authError = customError;
        } else {
            authError = "UnAuthorized to access the resource";
        }
        if (independentService.getActiveProfile().equals(SSO_SSP_SEAS)) {
            response.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
        } else {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        byte[] body = new ObjectMapper().writeValueAsBytes(
                new ErrorMessage(HttpStatus.UNAUTHORIZED.value(), authError));
        response.getOutputStream().write(body);
    }

}
