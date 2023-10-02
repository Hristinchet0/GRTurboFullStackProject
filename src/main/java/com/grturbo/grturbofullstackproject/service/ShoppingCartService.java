package com.grturbo.grturbofullstackproject.service;

import com.grturbo.grturbofullstackproject.model.entity.CartItem;
import com.grturbo.grturbofullstackproject.model.entity.Product;
import com.grturbo.grturbofullstackproject.model.entity.ShoppingCart;
import com.grturbo.grturbofullstackproject.model.entity.User;
import com.grturbo.grturbofullstackproject.repositority.CartItemRepository;
import com.grturbo.grturbofullstackproject.repositority.ShoppingCartRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class ShoppingCartService {

    private final CartItemRepository cartItemRepository;

    private final ShoppingCartRepository shoppingCartRepository;

    private final UserService userService;

    private final EntityManager entityManager;

    public ShoppingCartService(CartItemRepository cartItemRepository, ShoppingCartRepository shoppingCartRepository, UserService userService, EntityManager entityManager) {
        this.cartItemRepository = cartItemRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.userService = userService;
        this.entityManager = entityManager;
    }


    public ShoppingCart addItemToCart(Product product, int quantity, User user) {
        ShoppingCart cart = user.getShoppingCart();

        if (cart == null) {
            cart = new ShoppingCart();
            cart.setCustomer(user);
        }

        Set<CartItem> cartItems = cart.getCartItems();

        CartItem cartItem = findCartItem(cartItems, product.getId());

        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setTotalPrice(quantity * product.getPrice());
            cartItem.setShoppingCart(cart);
            cartItems.add(cartItem);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setTotalPrice(cartItem.getTotalPrice() + (quantity * product.getPrice()));
        }

        cart.setCartItems(cartItems);
        cart.setTotalItems(totalItems(cartItems));
        cart.setTotalPrice(totalPrice(cartItems));

        return shoppingCartRepository.save(cart);
    }

    public ShoppingCart updateItemInCart(Product product, int quantity, User user) {
        ShoppingCart cart = user.getShoppingCart();

        Set<CartItem> cartItems = cart.getCartItems();

        CartItem item = findCartItem(cartItems, product.getId());

        item.setQuantity(quantity);
        item.setTotalPrice(quantity * product.getPrice());
        cartItemRepository.save(item);

        int totalItems = totalItems(cart.getCartItems());
        double totalPrice = totalPrice(cartItems);
        cart.setTotalItems(totalItems);
        cart.setTotalPrice(totalPrice);

        return shoppingCartRepository.save(cart);
    }

    public ShoppingCart deleteItemFromCart(Long cartItemId, User user) {
        ShoppingCart userShoppingCart = user.getShoppingCart();

        CartItem cartItemForDelete = cartItemRepository.findByIdAndShoppingCart_Id(cartItemId, userShoppingCart.getId());

        this.cartItemRepository.deleteCartItemById((cartItemForDelete.getId()));

        Set<CartItem> cartItems = cartItemRepository.findByShoppingCart_Id(userShoppingCart.getId());

        int totalItems = totalItems(cartItems);
        double totalPrice = totalPrice(cartItems);

        userShoppingCart.setCartItems(cartItems);
        userShoppingCart.setTotalItems(totalItems);
        userShoppingCart.setTotalPrice(totalPrice);

        return shoppingCartRepository.save(userShoppingCart);

    }

    private CartItem findCartItem(Set<CartItem> cartItems, Long productId) {

        if (cartItems == null) {
            return null;
        }
        CartItem cartItem = null;

        for (CartItem item : cartItems) {
            if (Objects.equals(item.getProduct().getId(), productId)) {
                cartItem = item;
            }
        }
        return cartItem;
    }

    private int totalItems(Set<CartItem> cartItems) {
        int totalItems = 0;

        for (CartItem item : cartItems) {
            totalItems += item.getQuantity();
        }

        return totalItems;
    }

    private double totalPrice(Set<CartItem> cartItems) {
        double totalPrice = 0.0;

        for (CartItem item : cartItems) {
            totalPrice += item.getTotalPrice();
        }

        return totalPrice;
    }


    public void deleteCartById(Long id) {
        ShoppingCart shoppingCart = shoppingCartRepository.getById(id);
        shoppingCartRepository.delete(shoppingCart);

    }
}
