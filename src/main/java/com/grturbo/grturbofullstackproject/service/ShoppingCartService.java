package com.grturbo.grturbofullstackproject.service;

import com.grturbo.grturbofullstackproject.model.entity.CartItem;
import com.grturbo.grturbofullstackproject.model.entity.Product;
import com.grturbo.grturbofullstackproject.model.entity.ShoppingCart;
import com.grturbo.grturbofullstackproject.model.entity.User;
import com.grturbo.grturbofullstackproject.repositority.CartItemRepository;
import com.grturbo.grturbofullstackproject.repositority.ShoppingCartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class ShoppingCartService {

    private final CartItemRepository cartItemRepository;

    private final ShoppingCartRepository shoppingCartRepository;

    private final UserService userService;

    public ShoppingCartService(CartItemRepository cartItemRepository, ShoppingCartRepository shoppingCartRepository, UserService userService) {
        this.cartItemRepository = cartItemRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.userService = userService;
    }


    public ShoppingCart addItemToCart(Product product, int quantity, User user) {
        ShoppingCart cart = user.getCart();

        if (cart == null) {
            cart = new ShoppingCart();
            cart.setCustomer(user);
        }

        Set<CartItem> cartItems = cart.getCartItems();

        // Check if the product is already in the cart
        CartItem cartItem = findCartItem(cartItems, product.getId());

        if (cartItem == null) {
            // Create a new cart item
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setTotalPrice(quantity * product.getPrice());
            cartItem.setCart(cart);
            cartItems.add(cartItem);
        } else {
            // Update existing cart item
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setTotalPrice(cartItem.getTotalPrice() + (quantity * product.getPrice()));
        }

        // Update cart properties
        cart.setCartItems(cartItems);
        cart.setTotalItems(totalItems(cartItems));
        cart.setTotalPrice(totalPrice(cartItems));

        // Save the changes to the database
        return shoppingCartRepository.save(cart);
    }

    public ShoppingCart updateItemInCart(Product product, int quantity, User user) {
        ShoppingCart cart = user.getCart();

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

    @Transactional
    public ShoppingCart deleteItemFromCart(Product product, String user) {

        Optional<User> customer = userService.findByEmail(user);

        ShoppingCart cart = customer.get().getCart();

        Set<CartItem> cartItems = cart.getCartItems();

        CartItem item = findCartItem(cartItems, product.getId());

        cartItems.remove(item);

        cartItemRepository.delete(item);

        double totalPrice = totalPrice(cartItems);

        int totalItems = totalItems(cartItems);

        cart.setCartItems(cartItems);

        cart.setTotalItems(totalItems);

        cart.setTotalPrice(totalPrice);

        return shoppingCartRepository.save(cart);
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


}
