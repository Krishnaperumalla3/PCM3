package com.pe.pcm.security.apiconnectauth;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Kiran Reddy.
 */
public interface ApiConnectAuthFilter {
    void authenticate(HttpServletRequest request, String apiName, String webMethodName);
}
