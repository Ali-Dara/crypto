package org.dara.authenticationservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "email_verification_tokens")
@Getter
@Setter
@NoArgsConstructor
public class EmailVerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private UUID userUuid;
    @Column(nullable = false, unique = true, length = 64)
    private String tokenHash;
    @Column(nullable = false)
    private LocalDateTime expiresAt;
    @Column(nullable = false)
    private boolean used;
    @Column(nullable = false)
    private LocalDateTime createdAt;

    public EmailVerificationToken(UUID userUuid, String tokenHash, LocalDateTime expiresAt) {
        this.userUuid = userUuid;
        this.tokenHash = tokenHash;
        this.expiresAt = expiresAt;
    }
}
