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

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * @author Kiran Reddy.
 */
public class AuthoritiesConstants {

    private AuthoritiesConstants() {
    }

    public static final String SUPER_ADMIN = "super_admin";
    public static final String ADMIN = "admin";
    public static final String ON_BOARDER = "on_boarder";
    public static final String BUSINESS_ADMIN = "business_admin";
    public static final String BUSINESS_USER = "business_user";
    public static final String FILE_OPERATOR = "file_operator";
    public static final String DATA_PROCESSOR = "data_processor";
    public static final String DATA_PROCESSOR_RESTRICTED = "data_processor_restricted";
    public static final List<String> ROLES = Collections.unmodifiableList(asList(SUPER_ADMIN, ADMIN, ON_BOARDER, BUSINESS_ADMIN, BUSINESS_USER, FILE_OPERATOR, DATA_PROCESSOR, DATA_PROCESSOR_RESTRICTED));
    public static final String CM_APP_VERSION = "Version: 6.2.1.0.8";

    public static final String AUTHENTICATION_FAILURE = "AUTHENTICATION_FAILURE";
    public static final String AUTHENTICATION_SUCCESS = "AUTHENTICATION_SUCCESS";

    public static final String ONLY_SA = "hasAnyAuthority('" + SUPER_ADMIN + "')";
    public static final String SA_AD = "hasAnyAuthority('" + SUPER_ADMIN + "','" + ADMIN + "')";
    public static final String SA_OB_BA = "hasAnyAuthority('" + SUPER_ADMIN + "','" + ON_BOARDER + "','" + BUSINESS_ADMIN + "')";
    public static final String SA_AD_OB_BA_BU = "hasAnyAuthority('" + SUPER_ADMIN + "','" + ADMIN + "','" + ON_BOARDER + "','" + BUSINESS_ADMIN + "','" + BUSINESS_USER + "')";
    public static final String SA_OB_BA_BU = "hasAnyAuthority('" + SUPER_ADMIN + "','" + ON_BOARDER + "','" + BUSINESS_ADMIN + "','" + BUSINESS_USER + "')";
    public static final String SA_OB_BA_BU_DP = "hasAnyAuthority('" + SUPER_ADMIN + "','" + ON_BOARDER + "','" + BUSINESS_ADMIN + "','" + BUSINESS_USER + "','" + FILE_OPERATOR + "','" + DATA_PROCESSOR + "')";
    public static final String SA_OB_BA_BU_DP_DPR = "hasAnyAuthority('" + SUPER_ADMIN + "','" + ON_BOARDER + "','" + BUSINESS_ADMIN + "','" + BUSINESS_USER + "','" + FILE_OPERATOR + "','" + DATA_PROCESSOR + "','" + DATA_PROCESSOR_RESTRICTED + "')";

    public static final String DP_DPR = "hasAnyAuthority('" + DATA_PROCESSOR + "','" + DATA_PROCESSOR_RESTRICTED + "')";

}
