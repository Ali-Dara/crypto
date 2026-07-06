package org.dara.authenticationservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String permissionName;
    @OneToMany(mappedBy = "permission")
    private Set<RolePermission> rolePermissions;
}
