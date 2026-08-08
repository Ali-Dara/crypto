package org.dara.cryptosecurity.config;


import org.dara.cryptosecurity.model.CurrentUser;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthenticationToken  extends AbstractAuthenticationToken {

    private final CurrentUser currentUser;

    public JwtAuthenticationToken (CurrentUser currentUser, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.currentUser = currentUser;
        super.setAuthenticated(true);
    }
    @Override
    public @Nullable Object getCredentials() {
        return null;
    }

    @Override
    public CurrentUser  getPrincipal() {
        return currentUser;
    }
}
