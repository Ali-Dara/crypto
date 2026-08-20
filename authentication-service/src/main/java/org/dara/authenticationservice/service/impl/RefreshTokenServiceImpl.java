package org.dara.authenticationservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dara.authenticationservice.config.JwtProperties;
import org.dara.authenticationservice.dto.RefreshTokenRequest;
import org.dara.authenticationservice.dto.RefreshTokenResponse;
import org.dara.authenticationservice.model.AuthUser;
import org.dara.authenticationservice.model.RefreshToken;
import org.dara.authenticationservice.repository.RefreshTokenRepository;
import org.dara.authenticationservice.service.JwtService;
import org.dara.authenticationservice.service.RefreshTokenService;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProperties jwtProperties;
    private final JwtService jwtService;

    @Override
    public String create(AuthUser user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setAuthUser(user);
        refreshToken.setExpiry_date(LocalDateTime.now().plusSeconds(jwtProperties.refreshTokenExpiration()));
        String token = UUID.randomUUID().toString();
        refreshToken.setToken(generateSHA256Hash(token));
        refreshTokenRepository.save(refreshToken);
        return token;
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(generateSHA256Hash(token));
    }

    @Override
    public void delete(RefreshToken token) {
        refreshTokenRepository.delete(token);
    }

    @Override
    public void deleteByAuthUser(AuthUser user) {
        refreshTokenRepository.deleteByAuthUser(user);
    }

    @Override
    public RefreshToken verify(String rawToken) throws Exception {
        RefreshToken refreshToken = findByToken(rawToken)
                .orElseThrow(() ->
                        new Exception("refresh token not found")
                );

        if (refreshToken.getRevoked()) {
            throw new Exception("refresh token revoked");
        }

        if (refreshToken.getExpiry_date().isBefore(LocalDateTime.now())) {
            throw new Exception("refresh token expired");
        }

        return refreshToken;
    }

    @Override
    public RefreshTokenResponse refresh(RefreshTokenRequest request) throws Exception{
        RefreshToken refreshToken = verify(request.refreshToken());
        AuthUser authUser = refreshToken.getAuthUser();
        revoke(refreshToken);
        String newRefreshToken = create(authUser);
        String newAccessToken = jwtService.generateAccessToken(authUser);
        return new RefreshTokenResponse(
                newAccessToken,
                newRefreshToken,
                "Bearer"
        );
    }

    @Override
    public void revoke(RefreshToken refreshToken) {
            refreshToken.setRevoked(true);
            refreshTokenRepository.save(refreshToken);
    }

    @Override
    public void logout(Long userId, String refreshToken) throws Exception {
        RefreshToken token = verify(refreshToken);
        if (!token.getAuthUser().getId().equals(userId))
            throw new Exception("Refresh token does not belong to user");
        token.setRevoked(true);
        refreshTokenRepository.save(token);
    }

    @Override
    public void logoutAllSession(Long userId, String refreshToken) throws Exception {
        RefreshToken token = verify(refreshToken);
        if (!token.getAuthUser().getId().equals(userId))
            throw new Exception("Refresh token does not belong to user");
        refreshTokenRepository.revokeAllByAuthUser(token.getAuthUser());
    }

    private String generateSHA256Hash(String input) {
        try {
            // Create a MessageDigest instance for SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Perform the hash computation
            byte[] encodedHash = digest.digest(input.getBytes());

            // Convert byte array into a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
