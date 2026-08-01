package org.dara.apigateway.config;

import org.dara.apigateway.model.CurrentUser;
import org.dara.apigateway.model.CurrentUserAuthentication;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CustomJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        Set<String> roles = jwt.getClaim("roles");
        assert roles != null;
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
        Set<String> permissions = jwt.getClaim("permissions");
        assert permissions != null;
        permissions.forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission)));
        CurrentUser currentUser = new CurrentUser(jwt.getClaim("userId"),jwt.getClaim("username"),jwt.getClaim("email"),roles,permissions);
        return new CurrentUserAuthentication(currentUser, authorities);
    }
}
