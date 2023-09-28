package com.grturbo.grturbofullstackproject.service;

import com.grturbo.grturbofullstackproject.model.CustomUserDetail;
import com.grturbo.grturbofullstackproject.model.dto.UserDto;
import com.grturbo.grturbofullstackproject.model.dto.UserRegisterDto;
import com.grturbo.grturbofullstackproject.model.entity.User;
import com.grturbo.grturbofullstackproject.model.entity.UserRole;
import com.grturbo.grturbofullstackproject.model.enums.RoleEnum;
import com.grturbo.grturbofullstackproject.model.mapper.UserMapper;
import com.grturbo.grturbofullstackproject.repositority.UserRoleRepository;
import com.grturbo.grturbofullstackproject.repositority.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    @Value("${app.admin.password}")
    public String adminPassword;

    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public void init() {
        if(userRoleRepository.count() == 0 && userRepository.count() == 0) {
            UserRole roleAdmin = new UserRole();
            roleAdmin.setRole(RoleEnum.ADMIN);
            userRoleRepository.save(roleAdmin);

            UserRole roleUser = new UserRole();
            roleUser.setRole(RoleEnum.USER);
            userRoleRepository.save(roleUser);

            User admin = new User();
            admin.setEmail("bansko.sport@gmail.com");
            String password = passwordEncoder.encode(adminPassword);
            admin.setPassword(password);
            admin.setFirstName("Petar");
            admin.setLastName("Rachev");
            admin.setAddress("Luben Karavelov 3");
            admin.setCity("Razlog");
            admin.setUsername("GrturboAdmin");
            admin.setPhoneNumber("0878363618");
            admin.setCart(null);
            admin.setOrders(null);
            admin.addRole(roleAdmin);

            userRepository.save(admin);

        }
    }

    public UserDetails registerAndLogin(UserRegisterDto userRegisterDto) {

        User newUser = userMapper.userDtoToUserEntity(userRegisterDto);
        newUser.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        newUser.addRole(userRoleRepository.findByRole(RoleEnum.USER).get());

        userRepository.save(newUser);

        return login(newUser.getUsername());

    }

    private CustomUserDetail login(String userName) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        userDetails.getPassword(),
                        userDetails.getAuthorities()
                );

        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);

        return (CustomUserDetail) authentication.getPrincipal();

    }

    public void removeUserById(Long id) {
        userRepository.deleteById(id);
    }

    public List<UserDto> getAllUsers() {
        List<UserDto> users = new ArrayList<>();
        for (User user : userRepository
                .findAll()) {
            UserDto userDto = userMapper.userEntityToUserDto(user);
            users.add(userDto);
        }
        return users;
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public User findByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    public Optional<User> findByEmail(String name) {
        return userRepository.findUserByEmail(name);
    }
}
