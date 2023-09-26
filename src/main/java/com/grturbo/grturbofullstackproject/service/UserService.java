package com.grturbo.grturbofullstackproject.service;

import com.grturbo.grturbofullstackproject.model.entity.UserRole;
import com.grturbo.grturbofullstackproject.model.enums.RoleEnum;
import com.grturbo.grturbofullstackproject.repositority.UserRoleRepository;
import com.grturbo.grturbofullstackproject.repositority.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    public void init() {
        if(userRoleRepository.count() == 0 && userRepository.count() == 0) {
            UserRole roleAdmin = new UserRole();
            roleAdmin.setRole(RoleEnum.ADMIN);

            userRoleRepository.save(roleAdmin);

            UserRole roleUser = new UserRole();
            roleUser.setRole(RoleEnum.USER);

            userRoleRepository.save(roleUser);
        }
    }
}
