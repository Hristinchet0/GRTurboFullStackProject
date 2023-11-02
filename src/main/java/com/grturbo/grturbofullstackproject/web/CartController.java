package com.grturbo.grturbofullstackproject.web;

import com.grturbo.grturbofullstackproject.model.entity.Product;
import com.grturbo.grturbofullstackproject.model.entity.ShoppingCart;
import com.grturbo.grturbofullstackproject.model.entity.User;
import com.grturbo.grturbofullstackproject.service.impl.CartItemServiceImpl;
import com.grturbo.grturbofullstackproject.service.impl.ProductServiceImpl;
import com.grturbo.grturbofullstackproject.service.impl.ShoppingCartServiceImpl;
import com.grturbo.grturbofullstackproject.service.impl.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.security.Principal;

@Controller
public class CartController {

    private final UserServiceImpl userServiceImpl;

    private final ShoppingCartServiceImpl shoppingCartServiceImpl;

    private final ProductServiceImpl productServiceImpl;

    private final CartItemServiceImpl cartItemServiceImpl;

    public CartController(UserServiceImpl userServiceImpl, ShoppingCartServiceImpl shoppingCartServiceImpl, ProductServiceImpl productServiceImpl, CartItemServiceImpl cartItemServiceImpl) {
        this.userServiceImpl = userServiceImpl;
        this.shoppingCartServiceImpl = shoppingCartServiceImpl;
        this.productServiceImpl = productServiceImpl;
        this.cartItemServiceImpl = cartItemServiceImpl;
    }

    @GetMapping("/cart")
    public String cart(Model model, Principal principal, HttpSession session) {

        if (principal == null) {
            return "redirect:/login";
        }

        User user = userServiceImpl.findByEmail(principal.getName()).get();
        ShoppingCart cart = shoppingCartServiceImpl.findByUserId(user.getId());

        if (cart == null) {
            model.addAttribute("check", "No item in your shopping cart");
        }

        if (cart != null) {
            model.addAttribute("subTotal", cart.getTotalPrice());
        }

        session.setAttribute("totalItems", cart != null ? cart.getTotalItems() : 0);
        model.addAttribute("shoppingCart", cart);

        return "cart";
    }

    @PostMapping("/add-to-cart")
    public String addItemToCart(@RequestParam("id") Long productId,
                                @RequestParam(value = "quantity", required = false, defaultValue = "1") Integer quantity,
                                Principal principal,
                                HttpServletRequest request,
                                Model model) {

        if (principal == null) {
            return "redirect:/login";
        }

        Product product = productServiceImpl.getProductByID(productId);

        User user = userServiceImpl.findByEmail(principal.getName()).get();

        ShoppingCart cart = shoppingCartServiceImpl.addItemToCart(product, quantity, user);
        model.addAttribute("addToCartSuccessMessage", "The product has been successfully added to your cart.");

        return "redirect:/cart";
    }

    @PostMapping("/update-cart")
    public String updateCart(@RequestParam("quantity") Integer quantity,
                             @RequestParam("id") Long productId,
                             Model model,
                             Principal principal) {

        if (principal == null) {
            return "redirect:/login";
        }

        User user = userServiceImpl.findByEmail(principal.getName()).get();
        Product product = productServiceImpl.getProductByID(productId);
        ShoppingCart cart = shoppingCartServiceImpl.updateItemInCart(product, quantity, user);

        model.addAttribute("shoppingCart", cart);

        return "redirect:/cart";
    }

    @GetMapping("/cart/delete/product/{id}")
    public String deleteProduct(@PathVariable("id") Long id, Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }

        User user = userServiceImpl.findByEmail(principal.getName()).get();

        ShoppingCart shoppingCart = shoppingCartServiceImpl.deleteItemFromCart(id, user);

        model.addAttribute("shoppingCart", shoppingCart);

        return "redirect:/cart";
    }
}
