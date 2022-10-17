/*
package com.pe.pcm.jwt.saml.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.saml.SAMLEntryPoint;
import org.springframework.security.saml.context.SAMLMessageContext;
import org.springframework.security.saml.websso.WebSSOProfileOptions;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.pe.pcm.constants.SecurityConstants.SAMLTOKEN;

*/
/**
 * @author Shameer.
 *//*

@PropertySource("classpath:application.yml")
public class SamlWithRelayStateEntryPoint extends SAMLEntryPoint {

    @Value("${saml.url.entity}")
    private String entityURL;

    @Value("${saml.url.client}")
    private String clientURL;

    private String relayState;

    @Override
    protected WebSSOProfileOptions getProfileOptions(SAMLMessageContext context, AuthenticationException exception) {


        WebSSOProfileOptions ssoProfileOptions;
        if (defaultOptions != null) {
            ssoProfileOptions = defaultOptions.clone();
        } else {
            ssoProfileOptions = new WebSSOProfileOptions();
        }

        // Note :
        // Add your custom logic here if you need it.
        // Original HttpRequest can be extracted from the context param
        // So you can let the caller pass you some special param which can be used to build an on-the-fly custom
        // relay state param
        ssoProfileOptions.setRelayState(getRelayState() != null ? getRelayState() : entityURL + SAMLTOKEN);

        return ssoProfileOptions;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException, ServletException {

        if (request.getParameter("isWeb") != null && request.getParameter("isWeb").equals("true")) {
            setRelayState(clientURL);
        } else {
            setRelayState(entityURL + SAMLTOKEN);
        }
        super.commence(request, response, authenticationException);

    }

    private void setRelayState(String relayState) {
        this.relayState = relayState;
    }

    private String getRelayState() {
        return relayState;
    }

}
*/
