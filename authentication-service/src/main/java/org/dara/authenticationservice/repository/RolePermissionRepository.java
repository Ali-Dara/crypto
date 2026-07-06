package org.dara.authenticationservice.repository;

import org.dara.authenticationservice.model.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {
}
