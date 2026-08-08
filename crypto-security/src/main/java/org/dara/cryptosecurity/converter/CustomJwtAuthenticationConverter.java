package org.dara.cryptosecurity.converter;


import org.dara.cryptosecurity.config.JwtAuthenticationToken;
import org.dara.cryptosecurity.model.CurrentUser;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CustomJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private Collection<? extends GrantedAuthority> extractAuthorities(Jwt jwt){
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<String> roles = jwt.getClaimAsStringList("roles");
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
        List<String> permissions = jwt.getClaimAsStringList("permissions");
        permissions.forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission)));
        return authorities;
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        CurrentUser currentUser = new CurrentUser(UUID.fromString(jwt.getClaimAsString("userId")),jwt.getClaimAsString("username"),jwt.getClaimAsString("email"));
        return new JwtAuthenticationToken(currentUser, extractAuthorities(jwt));
    }
}
