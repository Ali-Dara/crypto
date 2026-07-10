package org.dara.authenticationservice.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.dara.authenticationservice.config.JwtProperties;
import org.dara.authenticationservice.model.AuthUser;
import org.dara.authenticationservice.model.Role;
import org.dara.authenticationservice.service.JwtService;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtProperties jwtProperties;

    @Override
    public String generateAccessToken(AuthUser user) {
        Map<String, Object> claims = buildClaims(user);
        return buildToken(user, claims);
    }

    @Override
    public boolean isTokenValid(String token) {
        try {
            extractClaims(token);
            return true;
        }catch (JwtException | IllegalArgumentException e){
            return false;
        }
    }

    @Override
    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    @Override
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    @Override
    public UUID extractUserId(String token) {
        return extractClaims(token).get("userId", UUID.class);
    }

    private Map<String, Object> buildClaims(AuthUser authUser){
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", authUser.getId());
        claims.put("roles", authUser.getRoleList().stream().map(Role::getRoleName).toList());
        return claims;
    }

    private String buildToken(AuthUser authUser, Map<String, Object> claims){
        return Jwts.builder()
                    .claims(claims)
                    .subject(authUser.getUsername())
                    .expiration(new Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration()))
                    .issuedAt(new Date())
                    .signWith(getSecretKey())
                    .compact();
    }

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.secretKey()));
    }

    private Claims extractClaims(String token){
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
