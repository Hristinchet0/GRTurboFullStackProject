package com.grturbo.grturbofullstackproject.service.impl;

import com.grturbo.grturbofullstackproject.model.entity.UserRole;
import com.grturbo.grturbofullstackproject.repositority.UserRoleRepository;
import com.grturbo.grturbofullstackproject.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final UserRoleRepository roleRepository;

    public RoleServiceImpl(UserRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<UserRole> listRoles() {
        return roleRepository.findAll();
    }
}
