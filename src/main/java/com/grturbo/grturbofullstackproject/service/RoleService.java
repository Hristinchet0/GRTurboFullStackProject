package com.grturbo.grturbofullstackproject.service;

import com.grturbo.grturbofullstackproject.model.entity.UserRole;
import com.grturbo.grturbofullstackproject.repositority.UserRoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final UserRoleRepository roleRepository;

    public RoleService(UserRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<UserRole> listRoles() {
        return roleRepository.findAll();
    }
}
