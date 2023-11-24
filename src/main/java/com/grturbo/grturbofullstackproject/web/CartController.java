package com.grturbo.grturbofullstackproject.web;

import com.grturbo.grturbofullstackproject.model.entity.Product;
import com.grturbo.grturbofullstackproject.model.entity.ShoppingCart;
import com.grturbo.grturbofullstackproject.model.entity.User;
import com.grturbo.grturbofullstackproject.service.ProductService;
import com.grturbo.grturbofullstackproject.service.ShoppingCartService;
import com.grturbo.grturbofullstackproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.security.Principal;

@Controller
public class CartController {

    private final UserService userService;

    private final ShoppingCartService shoppingCartService;

    private final ProductService productService;


    public CartController(UserService userService,
                          ShoppingCartService shoppingCartService,
                          ProductService productService) {
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
        this.productService = productService;
    }

    @GetMapping("/cart")
    public String cart(Model model, Principal principal, HttpSession session) {

        if (principal == null) {
            return "redirect:/login";
        }

        User user = userService.findByEmail(principal.getName()).get();
        ShoppingCart cart = shoppingCartService.findByUserId(user.getId());

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

        Product product = productService.getProductByID(productId);

        User user = userService.findByEmail(principal.getName()).get();

        ShoppingCart cart = shoppingCartService.addItemToCart(product, quantity, user);
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

        User user = userService.findByEmail(principal.getName()).get();
        Product product = productService.getProductByID(productId);
        ShoppingCart cart = shoppingCartService.updateItemInCart(product, quantity, user);

        model.addAttribute("shoppingCart", cart);

        return "redirect:/cart";
    }

    @RequestMapping(value = "/cart/delete/cartItem/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String deleteProduct(@PathVariable("id") Long cartItem, Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }

        User user = userService.findByEmail(principal.getName()).get();

        ShoppingCart cart = shoppingCartService.deleteItemFromCart(cartItem, user);

        model.addAttribute("shoppingCart", cart);

        return "redirect:/cart";
    }
}
