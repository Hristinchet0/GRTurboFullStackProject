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
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final CartItemRepository cartItemRepository;

    private final ShoppingCartRepository shoppingCartRepository;

    private final EntityManager entityManager;

    public ShoppingCartServiceImpl(CartItemRepository cartItemRepository,
                                   ShoppingCartRepository shoppingCartRepository,
                                   EntityManager entityManager) {
        this.cartItemRepository = cartItemRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.entityManager = entityManager;
    }

    @Override
    public ShoppingCart addItemToCart(Product product, Integer quantity, User user) {
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
            cartItem.setTotalPrice(new BigDecimal(quantity).multiply(product.getPrice()));
            cartItem.setShoppingCart(cart);
            cartItems.add(cartItem);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setTotalPrice(new BigDecimal(quantity).multiply(product.getPrice()).add(cartItem.getTotalPrice()));
        }

        cart.setCartItems(cartItems);
        cart.setTotalItems(totalItems(cartItems));
        cart.setTotalPrice(totalPrice(cartItems));

        return shoppingCartRepository.save(cart);
    }

    @Override
    public ShoppingCart updateItemInCart(Product product, Integer quantity, User user) {
        ShoppingCart cart = shoppingCartRepository.findShoppingCartByCustomer_Id(user.getId());

        Set<CartItem> cartItems = cart.getCartItems();

        CartItem item = findCartItem(cartItems, product.getId());

        item.setQuantity(quantity);
        item.setTotalPrice(new BigDecimal(quantity).multiply(item.getTotalPrice()));
        cartItemRepository.save(item);

        Integer totalItems = totalItems(cart.getCartItems());
        BigDecimal totalPrice = totalPrice(cartItems);
        cart.setTotalItems(totalItems);
        cart.setTotalPrice(totalPrice);

        return shoppingCartRepository.save(cart);
    }

    @Override
    @Transactional
    public ShoppingCart deleteItemFromCart(Long cartItemId, User user) {
        ShoppingCart userShoppingCart = shoppingCartRepository.findShoppingCartByCustomer_Id(user.getId());
        CartItem cartItemForDelete = cartItemRepository.findByIdAndShoppingCart_Id(cartItemId, userShoppingCart.getId());

        Set<CartItem> cartItems = userShoppingCart.getCartItems();
        cartItems.remove(cartItemForDelete);

        cartItemRepository.deleteById(cartItemForDelete.getId());

        Integer totalItems = totalItems(cartItems);
        BigDecimal totalPrice = totalPrice(cartItems);
        userShoppingCart.setTotalItems(totalItems);
        userShoppingCart.setTotalPrice(totalPrice);
        userShoppingCart.setCartItems(cartItems);
        shoppingCartRepository.save(userShoppingCart);

        return userShoppingCart;
    }

    @Override
    public void deleteCartItemsByShoppingCartId(Long id) {
        ShoppingCart shoppingCart = shoppingCartRepository.getById(id);
        shoppingCart.getCartItems().clear();
        shoppingCart.setTotalPrice(BigDecimal.valueOf(0.0));
        shoppingCart.setTotalItems(0);
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public void deleteAllCartItemsInShoppingCarts() {
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findAllCartsWithItems();

        for (ShoppingCart shoppingCart : shoppingCarts) {
            shoppingCart.getCartItems().clear();
            shoppingCart.setTotalPrice(BigDecimal.valueOf(0.0));
            shoppingCart.setTotalItems(0);
            shoppingCartRepository.save(shoppingCart);
        }
    }

    @Override
    public ShoppingCart findByUserId(Long id) {
        return shoppingCartRepository.findShoppingCartByCustomer_Id(id);
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

    private Integer totalItems(Set<CartItem> cartItems) {
        int totalItems = 0;

        for (CartItem item : cartItems) {
            totalItems += item.getQuantity();
        }

        return totalItems;
    }

    private BigDecimal totalPrice(Set<CartItem> cartItems) {
        return cartItems.stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
