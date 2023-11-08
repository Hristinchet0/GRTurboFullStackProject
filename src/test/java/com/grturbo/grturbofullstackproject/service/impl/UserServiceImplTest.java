package com.grturbo.grturbofullstackproject.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.grturbo.grturbofullstackproject.model.CustomUserDetail;
import com.grturbo.grturbofullstackproject.model.dto.UserDto;
import com.grturbo.grturbofullstackproject.model.dto.UserRegisterDto;
import com.grturbo.grturbofullstackproject.model.dto.UserRoleDto;
import com.grturbo.grturbofullstackproject.model.entity.InvoiceData;
import com.grturbo.grturbofullstackproject.model.entity.User;
import com.grturbo.grturbofullstackproject.model.entity.UserRole;
import com.grturbo.grturbofullstackproject.model.enums.RoleEnum;
import com.grturbo.grturbofullstackproject.model.mapper.UserMapper;
import com.grturbo.grturbofullstackproject.repositority.UserRepository;
import com.grturbo.grturbofullstackproject.repositority.UserRoleRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserRoleRepository userRoleRepository;

    @MockBean
    private CustomUserDetailService customUserDetailService;

    @MockBean
    private UserServiceImpl userServiceImpl;

    @Value("${app.admin.password}")
    private String adminPassword;

    @BeforeEach
    void setUp() {
        userServiceImpl = new UserServiceImpl(
                userRepository,
                userRoleRepository,
                userDetailsService,
                passwordEncoder,
                userMapper);
    }

    @Test
    void testInit() {
        if (userRoleRepository.count() == 0 && userRepository.count() == 0) {
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
            admin.setOrders(null);
            admin.addRole(roleAdmin);

            userRepository.save(admin);
        }

        when(userRepository.count()).thenReturn(0L);
        when(userRoleRepository.count()).thenReturn(0L);

        User userToReturn = new User();
        when(userRepository.save(any(User.class))).thenReturn(userToReturn);

        userServiceImpl.init();

        verify(userRepository, times(2)).save(any(User.class));

    }

//    @Test
//    public void testRegisterAndLogin() {
//        UserRegisterDto userRegisterDto = new UserRegisterDto();
//        userRegisterDto.setUsername("testUser");
//        userRegisterDto.setPassword("password");
//        userRegisterDto.setFirstName("firstName");
//        userRegisterDto.setLastName("lastName");
//        userRegisterDto.setEmail("email@example.com");
//        userRegisterDto.setConfirmPassword("password");
//
//        UserRole roleUser = new UserRole();
//        roleUser.setRole(RoleEnum.USER);
//
//        User newUser = new User();
//
//        Mockito.when(userRoleRepository.findByRole(RoleEnum.USER)).thenReturn(Optional.of(roleUser));
//        Mockito.when(userMapper.userDtoToUserEntity(userRegisterDto)).thenReturn(newUser);
//
//        Mockito.when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(newUser);
//
//        CustomUserDetail userDetails = (CustomUserDetail) customUserDetailService.loadUserByUsername(userRegisterDto.getUsername());
//
//        Mockito.when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userDetails);
//
//        userServiceImpl.registerAndLogin(userRegisterDto);
//    }


    @Test
    void testRemoveUserById() {
        doNothing().when(userRepository).deleteById((Long) any());
        userServiceImpl.removeUserById(123L);
        verify(userRepository).deleteById((Long) any());
    }

    @Test
    void testGetAllUsers() {

        List<User> mockUserList = new ArrayList<>();

        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("User1");

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("User2");

        mockUserList.add(user1);
        mockUserList.add(user2);

        Mockito.when(userRepository.findAll()).thenReturn(mockUserList);

        UserDto userDto1 = new UserDto();
        userDto1.setId(1L);
        userDto1.setEmail("User1@test.com");

        UserDto userDto2 = new UserDto();
        userDto2.setId(2L);
        userDto2.setEmail("User2@test.com");

        Mockito.when(userMapper.userEntityToUserDto(user1)).thenReturn(userDto1);
        Mockito.when(userMapper.userEntityToUserDto(user2)).thenReturn(userDto2);

        List<UserDto> result = userServiceImpl.getAllUsers();

        assertEquals(2, result.size());
        assertEquals(userDto1, result.get(0));
        assertEquals(userDto2, result.get(1));
    }

    @Test
    void testGetUserById() {
        assertFalse(userServiceImpl.getUserById(123L).isPresent());
        assertFalse(userServiceImpl.getUserById(-2121178829L).isPresent());
    }

    @Test
    void testSave() {
        User user = new User();
        user.setAddress("42 Main St");
        user.setCity("Oxford");
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setInvoiceData(new InvoiceData());
        user.setLastName("Doe");
        user.setOrders(new HashSet<>());
        user.setPassword("iloveyou");
        user.setPhoneNumber("4105551212");
        user.setRoles(new ArrayList<>());
        user.setUsername("janedoe");

        InvoiceData invoiceData = new InvoiceData();
        invoiceData.setCompanyName("Company Name");
        invoiceData.setCustomer(user);
        invoiceData.setFinanciallyAccountablePersonName("Dr Jane Doe");
        invoiceData.setId(123L);
        invoiceData.setIdentificationNumberUIC("42");
        invoiceData.setPhoneNumber("4105551212");
        invoiceData.setRegisteredAddress("42 Main St");
        invoiceData.setVatRegistration(true);

        User user1 = new User();
        user1.setAddress("42 Main St");
        user1.setCity("Oxford");
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setInvoiceData(invoiceData);
        user1.setLastName("Doe");
        user1.setOrders(new HashSet<>());
        user1.setPassword("iloveyou");
        user1.setPhoneNumber("4105551212");
        user1.setRoles(new ArrayList<>());
        user1.setUsername("janedoe");

        InvoiceData invoiceData1 = new InvoiceData();
        invoiceData1.setCompanyName("Company Name");
        invoiceData1.setCustomer(user1);
        invoiceData1.setFinanciallyAccountablePersonName("Dr Jane Doe");
        invoiceData1.setId(123L);
        invoiceData1.setIdentificationNumberUIC("42");
        invoiceData1.setPhoneNumber("4105551212");
        invoiceData1.setRegisteredAddress("42 Main St");
        invoiceData1.setVatRegistration(true);

        User user2 = new User();
        user2.setAddress("42 Main St");
        user2.setCity("Oxford");
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setId(123L);
        user2.setInvoiceData(invoiceData1);
        user2.setLastName("Doe");
        user2.setOrders(new HashSet<>());
        user2.setPassword("iloveyou");
        user2.setPhoneNumber("4105551212");
        user2.setRoles(new ArrayList<>());
        user2.setUsername("janedoe");
        when(userRepository.save((User) any())).thenReturn(user2);
        when(passwordEncoder.encode((CharSequence) any())).thenReturn("secret");

        InvoiceData invoiceData2 = new InvoiceData();
        invoiceData2.setCompanyName("Company Name");
        invoiceData2.setCustomer(new User());
        invoiceData2.setFinanciallyAccountablePersonName("Dr Jane Doe");
        invoiceData2.setId(123L);
        invoiceData2.setIdentificationNumberUIC("42");
        invoiceData2.setPhoneNumber("4105551212");
        invoiceData2.setRegisteredAddress("42 Main St");
        invoiceData2.setVatRegistration(true);

        User user3 = new User();
        user3.setAddress("42 Main St");
        user3.setCity("Oxford");
        user3.setEmail("jane.doe@example.org");
        user3.setFirstName("Jane");
        user3.setId(123L);
        user3.setInvoiceData(invoiceData2);
        user3.setLastName("Doe");
        user3.setOrders(new HashSet<>());
        user3.setPassword("iloveyou");
        user3.setPhoneNumber("4105551212");
        user3.setRoles(new ArrayList<>());
        user3.setUsername("janedoe");

        InvoiceData invoiceData3 = new InvoiceData();
        invoiceData3.setCompanyName("Company Name");
        invoiceData3.setCustomer(user3);
        invoiceData3.setFinanciallyAccountablePersonName("Dr Jane Doe");
        invoiceData3.setId(123L);
        invoiceData3.setIdentificationNumberUIC("42");
        invoiceData3.setPhoneNumber("4105551212");
        invoiceData3.setRegisteredAddress("42 Main St");
        invoiceData3.setVatRegistration(true);

        User user4 = new User();
        user4.setAddress("42 Main St");
        user4.setCity("Oxford");
        user4.setEmail("jane.doe@example.org");
        user4.setFirstName("Jane");
        user4.setId(123L);
        user4.setInvoiceData(invoiceData3);
        user4.setLastName("Doe");
        user4.setOrders(new HashSet<>());
        user4.setPassword("iloveyou");
        user4.setPhoneNumber("4105551212");
        user4.setRoles(new ArrayList<>());
        user4.setUsername("janedoe");
        userServiceImpl.save(user4);
        verify(userRepository).save((User) any());
        verify(passwordEncoder).encode((CharSequence) any());
        assertEquals("secret", user4.getPassword());
    }

    @Test
    void testFindByUsername() {
        assertNull(userServiceImpl.findByUsername("Name"));
    }

    @Test
    void testFindByEmail() {
        assertFalse(userServiceImpl.findByEmail("Name").isPresent());
    }


}

