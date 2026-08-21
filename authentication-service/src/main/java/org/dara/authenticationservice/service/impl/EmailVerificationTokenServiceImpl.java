package org.dara.authenticationservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.dara.authenticationservice.model.EmailVerificationToken;
import org.dara.authenticationservice.repository.EmailVerificationTokenRepository;
import org.dara.authenticationservice.service.EmailVerificationTokenService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailVerificationTokenServiceImpl implements EmailVerificationTokenService {

    private final EmailVerificationTokenRepository repository;

    @Override
    @Transactional
    public String createToken(UUID userUUId) {
        repository.deleteAllByUserUuid(userUUId);
        String rawToken = UUID.randomUUID().toString();
        String tokenHash = DigestUtils.sha256Hex(rawToken);
        EmailVerificationToken emailVerificationToken = new EmailVerificationToken(userUUId,tokenHash, LocalDateTime.now().plusHours(24));
        repository.save(emailVerificationToken);
        return rawToken;
    }

    @Override
    @Transactional
    public EmailVerificationToken verifyToken(String token) {
        String hashToken = DigestUtils.sha256Hex(token);
        EmailVerificationToken emailVerificationToken = repository.findByHashToken(hashToken)
                .orElseThrow(() -> new IllegalArgumentException("email Verification Token Not found"));

        if(emailVerificationToken.isUsed())
            throw new IllegalArgumentException("email Verification Token already used");

        if(emailVerificationToken.getExpiresAt().isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException("email Verification Token expired");

        return emailVerificationToken;
    }

    @Override
    @Transactional
    public void deleteToken(UUID userUUId) {
        repository.deleteAllByUserUuid(userUUId);
    }
}
