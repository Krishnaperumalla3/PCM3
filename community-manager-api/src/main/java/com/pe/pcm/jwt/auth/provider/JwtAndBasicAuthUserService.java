package com.pe.pcm.jwt.auth.provider;

import com.pe.pcm.login.CommunityManagerUserModel;
import com.pe.pcm.seas.user.UserDetailsImpl;
import com.pe.pcm.user.UserService;
import com.pe.pcm.user.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

import static com.pe.pcm.constants.ProfilesConstants.*;

/**
 * @author Kiran Reddy.
 */
@Component
@Profile({PCM, CM, CM_API})
public class JwtAndBasicAuthUserService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public JwtAndBasicAuthUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userService.getOnlyUser(username);
        return UserDetailsImpl.build(new CommunityManagerUserModel()
                .setUserId(userEntity.getUserid())
                .setEmail(userEntity.getEmail())
                .setRoles(Collections.singletonList(userEntity.getRole()))
        );
    }

}
