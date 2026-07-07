package org.dara.authenticationservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dara.authenticationservice.model.Role;
import org.dara.authenticationservice.repository.RoleRepository;
import org.dara.authenticationservice.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Optional<Role> findRoleByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }
}
