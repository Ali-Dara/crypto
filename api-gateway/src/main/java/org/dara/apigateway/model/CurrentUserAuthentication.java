package org.dara.apigateway.model;

import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CurrentUserAuthentication extends AbstractAuthenticationToken {

    private final CurrentUser currentUser;

    public CurrentUserAuthentication(CurrentUser currentUser, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.currentUser = currentUser;
        super.setAuthenticated(true);
    }
    @Override
    public @Nullable Object getCredentials() {
        return null;
    }

    @Override
    public @Nullable Object getPrincipal() {
        return currentUser;
    }
}
