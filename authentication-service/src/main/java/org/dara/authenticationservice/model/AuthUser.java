package org.dara.authenticationservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
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
    private boolean emailVerified = false;
    @OneToMany(mappedBy = "user",
               cascade = CascadeType.ALL)
    private Set<UserRole> userRoles = new HashSet<>();;

    @OneToMany(mappedBy = "authUser",
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    private Set<RefreshToken> refreshTokens = new HashSet<>();;

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
        userRoles.add(userRole);
    }

    public List<Role> getRoleList(){
        return userRoles.stream().map(UserRole::getRole).collect(Collectors.toList());
    }
}
