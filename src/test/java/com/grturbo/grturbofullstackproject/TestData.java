package com.grturbo.grturbofullstackproject;

import com.grturbo.grturbofullstackproject.model.entity.ShoppingCart;
import com.grturbo.grturbofullstackproject.model.entity.User;
import com.grturbo.grturbofullstackproject.repositority.ShoppingCartRepository;
import com.grturbo.grturbofullstackproject.repositority.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestData {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    public User createUser(String userName) {
        User userEntity = new User();

        userEntity.setEmail(userName);
        userEntity.setFirstName("Test");
        userEntity.setLastName("Test");
        userEntity.setPassword("topsecret");

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
