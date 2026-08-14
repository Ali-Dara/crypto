package org.dara.authenticationservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String roleName;
    @OneToMany(mappedBy = "role")
    private Set<UserRole> userRoles;
    @OneToMany(mappedBy = "role")
    private Set<RolePermission> rolePermissions;

    public List<Permission> getPermissions(){
        return rolePermissions.stream().map(RolePermission::getPermission).collect(Collectors.toList());
    }
}
