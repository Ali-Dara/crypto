package org.dara.cryptosecurity.util;

import org.dara.cryptosecurity.model.CurrentUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

public final class SecurityUtils {

    private SecurityUtils() {}

    private static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static CurrentUser getCurrentUser() {
        return (CurrentUser) getAuthentication().getPrincipal();
    }

    public static Collection<? extends GrantedAuthority> getAuthorities() {
        return getAuthentication().getAuthorities();
    }
}
