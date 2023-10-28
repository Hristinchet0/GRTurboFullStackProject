package com.grturbo.grturbofullstackproject.service.impl;

import com.grturbo.grturbofullstackproject.model.entity.CartItem;
import com.grturbo.grturbofullstackproject.model.entity.Product;
import com.grturbo.grturbofullstackproject.model.entity.ShoppingCart;
import com.grturbo.grturbofullstackproject.model.entity.User;
import com.grturbo.grturbofullstackproject.repositority.CartItemRepository;
import com.grturbo.grturbofullstackproject.repositority.ShoppingCartRepository;
import com.grturbo.grturbofullstackproject.service.ShoppingCartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final CartItemRepository cartItemRepository;

    private final ShoppingCartRepository shoppingCartRepository;

    private final UserServiceImpl userServiceImpl;

    private final EntityManager entityManager;

    public ShoppingCartServiceImpl(CartItemRepository cartItemRepository, ShoppingCartRepository shoppingCartRepository, UserServiceImpl userServiceImpl, EntityManager entityManager) {
        this.cartItemRepository = cartItemRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.userServiceImpl = userServiceImpl;
        this.entityManager = entityManager;
    }

    @Override
    public ShoppingCart addItemToCart(Product product, int quantity, User user) {
        ShoppingCart cart = shoppingCartRepository.findShoppingCartByCustomer_Id(user.getId());

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

    @Override
    public ShoppingCart updateItemInCart(Product product, int quantity, User user) {
        ShoppingCart cart = shoppingCartRepository.findShoppingCartByCustomer_Id(user.getId());

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

    @Override
    @Transactional
    public ShoppingCart deleteItemFromCart(Long cartItemId, User user) {
        ShoppingCart userShoppingCart = shoppingCartRepository.findShoppingCartByCustomer_Id(user.getId());

        ShoppingCart managedCart = entityManager.find(ShoppingCart.class, userShoppingCart.getId());

        CartItem cartItemForDelete = cartItemRepository.findByIdAndShoppingCart_Id(cartItemId, managedCart.getId());

        if (cartItemForDelete != null) {
            Set<CartItem> cartItems = managedCart.getCartItems();
            cartItems.remove(cartItemForDelete);

            int totalItems = totalItems(cartItems);
            double totalPrice = totalPrice(cartItems);
            managedCart.setTotalItems(totalItems);
            managedCart.setTotalPrice(totalPrice);

            entityManager.merge(managedCart);

            cartItemRepository.delete(cartItemForDelete);
        }

        return managedCart;
    }

    @Override
    public void deleteCartItemsByShoppingCartId(Long id) {
        ShoppingCart shoppingCart = shoppingCartRepository.getById(id);
        shoppingCart.getCartItems().clear();
        shoppingCart.setTotalPrice(0.0);
        shoppingCart.setTotalItems(0);
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public void deleteAllCartItemsInShoppingCarts() {
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findAllCartsWithItems();

        for (ShoppingCart shoppingCart : shoppingCarts) {
            shoppingCart.getCartItems().clear();
            shoppingCart.setTotalPrice(0.0);
            shoppingCart.setTotalItems(0);
            shoppingCartRepository.save(shoppingCart);
        }
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

    @Override
    public ShoppingCart findByUserId(Long id) {
        return shoppingCartRepository.findShoppingCartByCustomer_Id(id);
    }
}
