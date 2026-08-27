package org.dara.authenticationservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.dara.authenticationservice.Exception.EmailVerificationTokenAlreadyUsedException;
import org.dara.authenticationservice.Exception.EmailVerificationTokenExpiredException;
import org.dara.authenticationservice.Exception.EmailVerificationTokenNotFoundException;
import org.dara.authenticationservice.Exception.UserNotFoundException;
import org.dara.authenticationservice.model.AuthUser;
import org.dara.authenticationservice.model.EmailVerificationToken;
import org.dara.authenticationservice.repository.EmailVerificationTokenRepository;
import org.dara.authenticationservice.service.AuthUserService;
import org.dara.authenticationservice.service.EmailVerificationTokenService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailVerificationTokenServiceImpl implements EmailVerificationTokenService {

    private final EmailVerificationTokenRepository repository;
    private final AuthUserService authUserService;

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
        EmailVerificationToken emailVerificationToken = repository.findByTokenHash(hashToken)
                .orElseThrow(EmailVerificationTokenNotFoundException::new);

        if(emailVerificationToken.isUsed())
            throw new EmailVerificationTokenAlreadyUsedException();

        if(emailVerificationToken.getExpiresAt().isBefore(LocalDateTime.now()))
            throw new EmailVerificationTokenExpiredException();

        emailVerificationToken.setUsed(true);
        repository.save(emailVerificationToken);

        AuthUser authUser = authUserService.findByUserUuid(emailVerificationToken.getUserUuid()).orElseThrow(UserNotFoundException::new);

        authUser.setEmailVerified(true);
        authUserService.save(authUser);

        return emailVerificationToken;
    }

    @Override
    @Transactional
    public void deleteToken(UUID userUUId) {
        repository.deleteAllByUserUuid(userUUId);
    }
}
