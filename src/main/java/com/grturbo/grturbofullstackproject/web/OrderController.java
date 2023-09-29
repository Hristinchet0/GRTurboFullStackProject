package com.grturbo.grturbofullstackproject.web;

import com.grturbo.grturbofullstackproject.model.entity.ShoppingCart;
import com.grturbo.grturbofullstackproject.model.entity.User;
import com.grturbo.grturbofullstackproject.service.ShoppingCartService;
import com.grturbo.grturbofullstackproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class OrderController {

    private final UserService userService;

    private final ShoppingCartService shoppingCartService;

    public OrderController(UserService userService, ShoppingCartService shoppingCartService) {
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/check-out")
    public String checkOut(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            String username = principal.getName();
            User user = userService.findByEmail(principal.getName()).get();
            if (user.getAddress() == null || user.getCity() == null || user.getPhoneNumber() == null) {
                model.addAttribute("information", "You need update your information before check out");
                model.addAttribute("user", user);
                model.addAttribute("title", "Profile");
                model.addAttribute("page", "Profile");
                return "user-profile";
            } else {
                ShoppingCart cart = userService.findByEmail(principal.getName()).get().getCart();
                model.addAttribute("user", user);
                model.addAttribute("title", "Check-Out");
                model.addAttribute("page", "Check-Out");
                model.addAttribute("shoppingCart", cart);
                model.addAttribute("grandTotal", cart.getTotalItems());
                return "checkout";
            }
        }
    }
}
