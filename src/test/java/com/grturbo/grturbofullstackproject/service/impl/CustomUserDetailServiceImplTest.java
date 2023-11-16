package com.grturbo.grturbofullstackproject.service.impl;

import com.grturbo.grturbofullstackproject.model.CustomUserDetail;
import com.grturbo.grturbofullstackproject.model.entity.User;
import com.grturbo.grturbofullstackproject.model.entity.UserRole;
import com.grturbo.grturbofullstackproject.model.enums.RoleEnum;
import com.grturbo.grturbofullstackproject.repositority.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailServiceImplTest {

    @Mock
    private UserRepository mockUserRepo;

    private CustomUserDetailService toTest;

    @BeforeEach
    void setUp() {
        toTest = new CustomUserDetailService(
                mockUserRepo
        );
    }

    @Test
    void testLoadUserByUsername_UserExists() {

        User testUser = new User();
        testUser.setUsername("test");
        testUser.setFirstName("Pesho");
        testUser.setLastName("Peshov");
        testUser.setCity("Test");
        testUser.setAddress("Test");
        testUser.setPassword("topsecret");
        testUser.setPhoneNumber("123456789");
        testUser.setEmail("test@test.com");
        testUser.setRoles(List.of(
                new UserRole().setRole(RoleEnum.ADMIN)
        ));

        when(mockUserRepo.findUserByEmail(testUser.getEmail())).
                thenReturn(Optional.of(testUser));

        CustomUserDetail userDetails = (CustomUserDetail) toTest.loadUserByUsername(testUser.getEmail());

        Assertions.assertEquals(testUser.getEmail(), userDetails.getUsername());
        Assertions.assertEquals(testUser.getFirstName(), userDetails.getFirstName());
        Assertions.assertEquals(testUser.getLastName(), userDetails.getLastName());
        Assertions.assertEquals(testUser.getPassword(), userDetails.getPassword());
        Assertions.assertEquals(testUser.getFirstName() + " " + testUser.getLastName(),
                userDetails.getFullName());

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        Assertions.assertEquals(1, authorities.size());

        Iterator<? extends GrantedAuthority> authoritiesIter = authorities.iterator();

        Assertions.assertEquals("ROLE_" + RoleEnum.ADMIN.name(),
                authoritiesIter.next().getAuthority());

    }

    @Test
    void testLoadUserByUsername_UserDoesNotExist() {

        Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> toTest.loadUserByUsername("non-existant@example.com")
        );
    }

}
