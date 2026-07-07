package org.dara.authenticationservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Entity
@Data
public class AuthUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Email
    @Column(nullable = false)
    private String email;
    @Column(nullable = false, unique = true, updatable = false)
    private UUID userUuid;
    private boolean enabled = true;
    private boolean locked = false;
    @OneToMany(mappedBy = "user")
    private Set<UserRole> userRoles;
    @OneToMany(mappedBy = "authUser",
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    private Set<RefreshToken> refreshTokens;

    @PrePersist
    public void prePersist() {
        if (userUuid == null) {
            userUuid = UUID.randomUUID();
        }
    }

    public void addRole(Role role) {
        UserRole userRole = new UserRole();
        userRole.setUser(this);
        userRole.setRole(role);
        this.setUserRoles(Set.of(userRole));
        userRoles.add(userRole);
    }
}
