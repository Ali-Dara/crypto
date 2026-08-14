package org.dara.authenticationservice.repository;

import org.dara.authenticationservice.model.AuthUser;
import org.dara.authenticationservice.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);
    List<RefreshToken> findAllByAuthUser(AuthUser authUser);
    void deleteByAuthUser(AuthUser authUser);
    @Modifying
    @Query("""
    update RefreshToken r
        set r.revoked = true
        where r.authUser = :authUser
    """)
    void revokeAllByAuthUser(AuthUser authUser);
}
