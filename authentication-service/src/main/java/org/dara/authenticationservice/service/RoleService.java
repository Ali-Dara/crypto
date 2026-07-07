package org.dara.authenticationservice.service;

import org.dara.authenticationservice.model.Role;

import java.util.Optional;

public interface RoleService {

    Optional<Role> findRoleByRoleName(String roleName);
}
