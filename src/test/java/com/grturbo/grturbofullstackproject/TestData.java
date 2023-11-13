package com.grturbo.grturbofullstackproject;

import com.cloudinary.provisioning.Account;
import com.grturbo.grturbofullstackproject.model.entity.ShoppingCart;
import com.grturbo.grturbofullstackproject.model.entity.User;
import com.grturbo.grturbofullstackproject.model.entity.UserRole;
import com.grturbo.grturbofullstackproject.model.enums.RoleEnum;
import com.grturbo.grturbofullstackproject.repositority.ShoppingCartRepository;
import com.grturbo.grturbofullstackproject.repositority.UserRepository;
import com.grturbo.grturbofullstackproject.repositority.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestData {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    public User createUser(String userName) {

        UserRole roleAdmin = new UserRole();
        roleAdmin.setRole(RoleEnum.ADMIN);
        userRoleRepository.save(roleAdmin);

        User userEntity = new User();

        userEntity.setEmail(userName);
        userEntity.setFirstName("Test");
        userEntity.setLastName("Test");
        userEntity.setPassword("topsecret");
        userEntity.setUsername("testUser");
        userEntity.setCity("Razlog");
        userEntity.setAddress("Liuben Karavelov 3");
        userEntity.addRole(roleAdmin);

        return userRepository.save(userEntity);
    }

    public ShoppingCart createShoppingCart(User customer, int totalItems) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setTotalItems(totalItems);
        shoppingCart.setCustomer(customer);

        shoppingCartRepository.save(shoppingCart);
        return shoppingCart;
    }

}
