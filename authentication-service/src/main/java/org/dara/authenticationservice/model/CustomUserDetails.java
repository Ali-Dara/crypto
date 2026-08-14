package org.dara.authenticationservice.model;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class CustomUserDetails implements UserDetails {

    private final AuthUser authUser;

    public CustomUserDetails(AuthUser authUser) {
        this.authUser = authUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authUser.getRoleList().forEach(role -> {
           authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
           role.getPermissions().forEach(permission -> {
               authorities.add(new SimpleGrantedAuthority(permission.getPermissionName()));
           });
        });
        return authorities;
    }

    @Override
    public @Nullable String getPassword() {
        return authUser.getPassword();
    }

    @Override
    public String getUsername() {
        return authUser.getUsername();
    }

    public AuthUser getAuthUser() {
        return authUser;
    }

    public CurrentUser getCurrentUser() {
        return new CurrentUser(
                authUser.getId(),
                authUser.getUsername()
        );
    }
}
