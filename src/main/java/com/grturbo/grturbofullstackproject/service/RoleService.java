package com.grturbo.grturbofullstackproject.service;

import com.grturbo.grturbofullstackproject.repositority.UserRoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final UserRoleRepository roleRepository;

    public RoleService(UserRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
}
