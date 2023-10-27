package com.grturbo.grturbofullstackproject.service;

import com.grturbo.grturbofullstackproject.model.dto.UserDto;
import com.grturbo.grturbofullstackproject.model.dto.UserRegisterDto;
import com.grturbo.grturbofullstackproject.model.dto.UserUpdateDto;
import com.grturbo.grturbofullstackproject.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void init();

    void registerAndLogin(UserRegisterDto userRegisterDto);

    void removeUserById(Long id);

    List<UserDto> getAllUsers();

    Optional<User> getUserById(Long id);

    void save(User user);

    Optional<User> findByEmail(String name);

    User saveProfile(User user);

    UserUpdateDto getUser(String userEmail);

    User update(UserUpdateDto userUpdateDto);

    void changePass(UserUpdateDto userUpdate);
}
