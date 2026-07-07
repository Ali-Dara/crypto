package org.dara.authenticationservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dara.authenticationservice.config.JwtProperties;
import org.dara.authenticationservice.model.AuthUser;
import org.dara.authenticationservice.model.RefreshToken;
import org.dara.authenticationservice.repository.RefreshTokenRepository;
import org.dara.authenticationservice.service.RefreshTokenService;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private RefreshTokenRepository refreshTokenRepository;
    private final JwtProperties jwtProperties;

    @Override
    public RefreshToken create(AuthUser user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setAuthUser(user);
        refreshToken.setExpiry_date(jwtProperties.refreshTokenExpiration());
        String token = UUID.randomUUID().toString();
        refreshToken.setToken(generateSHA256Hash(token));
        refreshTokenRepository.save(refreshToken);
        refreshToken.setToken(token);
        return refreshToken;
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
