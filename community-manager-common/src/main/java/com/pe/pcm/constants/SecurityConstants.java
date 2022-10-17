/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc, Version 6.1 (the "License");
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

package com.pe.pcm.constants;

/**
 * @author Kiran Reddy.
 */
public class SecurityConstants {

    private SecurityConstants() {
    }

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String GENERATE_TOKEN = "/pcm/generate-token";
    public static final String OAUTH_GEN_TOKEN = "/pcm/oauth/generate-token";
    public static final String GET_SEAS_TOKEN_INFO = "/pcm/seas/get-token-info";
    public static final String ISSUER = "PragmaEdge PCM service";
    public static final String SUBJECT = "PCM Service";
    public static final String USERNAME = "userName";
    public static final String ROLE = "role";
    public static final String IS_SI_USER = "siUser";
    public static final String SM_LOGIN = "/pcm/utility/is-sm-login";
    public static final String ACTIVE_PROFILE = "/pcm/utility/active-profile";
    public static final String LOGO = "/pcm/utility/get-logo";
    public static final String GENERAL_APIS = "/pcm/general/**";
    public static final String SLASH = "/";
    public static final String FAV_ICON = "/favicon.ico";
    public static final String CSS = "/*.css";
    public static final String JS = "/*.js";
    public static final String GIF = "/*.gif";
    public static final String PNG = "/*.png";
    public static final String JPG = "/*.jpg";
    public static final String ASSETS_IMG = "/assets/img/**";

    public static final String ASSETS_SVG = "/assets/svg/**";
    public static final String ASSETS_I18N = "/assets/i18n/**/**";
    public static final String SHUTDOWN_CONTEXT = "/pcm/app/shutdown-context";

    //SAML Constants
    public static final String SAMLTOKEN = "/auth/token";
    public static final String SAML_USER = "username";
    public static final String SAML_JWT_SECRET_KEY = "yeWAgVDfb$!MFn@MCJVN7uqkznHbDLR#";
    public static final String REST_API_ALL = "/restapi/**";
}
