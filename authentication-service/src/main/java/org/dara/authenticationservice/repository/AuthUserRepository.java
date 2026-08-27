package org.dara.authenticationservice.repository;

import org.dara.authenticationservice.model.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {

    Optional<AuthUser> findByUsername(String username);
    Optional<AuthUser> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    @Query("""
        select distinct u
        from AuthUser u
        left join fetch u.userRoles ur
        left join fetch ur.role r
        left join fetch r.rolePermissions rp
        left join fetch rp.permission p
        where u.username = :username
    """)
    Optional<AuthUser> findByUsernameWithAuthorities(@Param("username") String username);
    Optional<AuthUser> findByUserUuid(UUID uuid);
}
