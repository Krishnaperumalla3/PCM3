/*
package com.pe.pcm.saml;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;

import java.util.ArrayList;
import java.util.List;

*/
/**
 * @author Shameer.
 *//*

public class SamlUserDetailsServiceImpl implements SAMLUserDetailsService {

    @Override
    public Object loadUserBySAML(SAMLCredential credential)
            throws UsernameNotFoundException {

        // The method is supposed to identify local account of user referenced by
        // data in the SAML assertion and return UserDetails object describing the user.

        List<GrantedAuthority> authorities = new ArrayList<>();
        GrantedAuthority authority = new SimpleGrantedAuthority(credential.getAttributeAsString("role"));
        authorities.add(authority);

        // In a real scenario, this implementation has to locate user in a arbitrary
        // dataStore based on information present in the SAMLCredential and
        // returns such a date in a form of application specific UserDetails object.
        return new SamlUserDetails(credential.getAttributeAsString("username"), credential.getNameID().getValue(), true, true, true, authorities);
    }
}
*/
