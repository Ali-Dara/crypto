package org.dara.authenticationservice.repository;

import org.dara.authenticationservice.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}
