package org.dara.walletservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "wallets")
@Getter
@Setter
@NoArgsConstructor
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private UUID userUuid;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Wallet(UUID userUuid) {
        this.userUuid = userUuid;
        this.createdAt = LocalDateTime.now();
    }

}
