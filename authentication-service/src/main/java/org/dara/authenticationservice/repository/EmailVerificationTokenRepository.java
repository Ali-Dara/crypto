package org.dara.authenticationservice.repository;

import org.dara.authenticationservice.model.EmailVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerificationToken, Long> {

    Optional<EmailVerificationToken> findByHashToken(String token);
    void deleteAllByUserUuid(UUID userUuid);
}
