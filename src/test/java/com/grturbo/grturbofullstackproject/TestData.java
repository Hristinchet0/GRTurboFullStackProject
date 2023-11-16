package com.grturbo.grturbofullstackproject;

import com.cloudinary.provisioning.Account;
import com.grturbo.grturbofullstackproject.model.entity.*;
import com.grturbo.grturbofullstackproject.model.enums.RoleEnum;
import com.grturbo.grturbofullstackproject.repositority.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class TestData {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

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

    public void createCategoriesAndProducts() {
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("TestCategory1");
        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("TestCategory2");

        categoryRepository.saveAll(List.of(category1, category2));

        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("TestProductName1");
        product1.setDescription("TestProductDescription1");
        product1.setPrice(BigDecimal.TEN);
        product1.setBrand("TestProductBrand1");
        product1.setCategory(category1);
        product1.setImgUrl("http://test.com/images/");

        Product product2 = new Product();
        product2.setId(1L);
        product2.setName("TestProductName1");
        product2.setDescription("TestProductDescription1");
        product2.setPrice(BigDecimal.TEN);
        product2.setBrand("TestProductBrand1");
        product2.setCategory(category2);
        product2.setImgUrl("http://test.com/images/");

        productRepository.saveAll(List.of(product1, product2));
    }




}
