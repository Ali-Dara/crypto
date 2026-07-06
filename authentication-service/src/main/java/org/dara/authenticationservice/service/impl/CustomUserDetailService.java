package org.dara.authenticationservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dara.authenticationservice.model.AuthUser;
import org.dara.authenticationservice.model.CustomUserDetails;
import org.dara.authenticationservice.service.AuthUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final AuthUserService authUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AuthUser> authUser= authUserService.findByUsername(username);
        if(authUser.isPresent())
            return new CustomUserDetails(authUser.get());
        else
            throw new UsernameNotFoundException(username);
    }
}
