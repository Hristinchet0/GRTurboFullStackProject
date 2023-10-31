package com.grturbo.grturbofullstackproject.service;

import com.grturbo.grturbofullstackproject.model.entity.UserRole;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RoleService {

    @Transactional
    List<UserRole> getAllRoles();
}
