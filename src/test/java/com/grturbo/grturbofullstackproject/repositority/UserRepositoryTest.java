package com.grturbo.grturbofullstackproject.repositority;

import com.grturbo.grturbofullstackproject.model.entity.User;
import com.grturbo.grturbofullstackproject.model.entity.UserRole;
import com.grturbo.grturbofullstackproject.model.enums.RoleEnum;
import com.grturbo.grturbofullstackproject.model.mapper.UserMapper;
import com.grturbo.grturbofullstackproject.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserRepositoryTest {

    @MockBean
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @BeforeEach
    public void createUsers() {
        UserRole userRole = new UserRole();
        userRole.setRole(RoleEnum.USER);
        this.userRoleRepository.save(userRole);

        UserRole adminRole = new UserRole();
        userRole.setRole(RoleEnum.ADMIN);
        this.userRoleRepository.save(userRole);

        User user = new User();
        user.setUsername("Pesho");
        user.setFirstName("Peshov");
        user.setLastName("Petrov");
        user.setAddress("L.Karavelov 3");
        user.setEmail("pesho@abv.bg");
        user.setPhoneNumber("0879469446");
        user.setPassword("12345");
        user.setCity("Razlog");
        user.setRoles((List<UserRole>) adminRole);
        this.userRepository.save(user);

        User user2 = new User();
        user2.setUsername("Gosho");
        user2.setFirstName("Georgiev");
        user2.setLastName("Georgiev");
        user2.setAddress("Sofia");
        user2.setEmail("gosho@test.com");
        user2.setPhoneNumber("0878363618");
        user2.setPassword("asdasd");
        user2.setRoles((List<UserRole>) userRole);
        this.userRepository.save(user2);
    }

    @Test
    void testFindUserByEmail() {
        Optional<User> findByEmail = this.userRepository.findUserByEmail("gosho@test.com");
        Assertions.assertEquals(findByEmail.get().getEmail(), "gosho@test.com");
    }

    @Test
    void testFindByUsername() {
        Optional<User> findByUsername = this.userRepository.findUserByEmail("Gosho");
        Assertions.assertEquals(findByUsername.get().getEmail(), "Gosho");
    }

    @Test
    void testFindByEmail() {
        User findByEmail = this.userRepository.findByEmail("pesho@abv.bg");
        Assertions.assertEquals(findByEmail.getEmail(), "pesho@abv.bg");
    }
}

