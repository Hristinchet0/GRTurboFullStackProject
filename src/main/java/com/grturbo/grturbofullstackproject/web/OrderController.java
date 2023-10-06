package com.grturbo.grturbofullstackproject.web;

import com.grturbo.grturbofullstackproject.model.entity.Order;
import com.grturbo.grturbofullstackproject.model.entity.ShoppingCart;
import com.grturbo.grturbofullstackproject.model.entity.User;
import com.grturbo.grturbofullstackproject.service.OrderService;
import com.grturbo.grturbofullstackproject.service.ShoppingCartService;
import com.grturbo.grturbofullstackproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Set;

@Controller
public class OrderController {

    private final UserService userService;

    private final ShoppingCartService shoppingCartService;

    private final OrderService orderService;

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    public OrderController(UserService userService, ShoppingCartService shoppingCartService, OrderService orderService) {
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
        this.orderService = orderService;
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
                ShoppingCart cart = userService.findByEmail(principal.getName()).get().getShoppingCart();
                model.addAttribute("user", user);
                model.addAttribute("title", "Check-Out");
                model.addAttribute("page", "Check-Out");
                model.addAttribute("shoppingCart", cart);
                model.addAttribute("grandTotal", cart.getTotalItems());
                return "checkout";
            }
        }
    }

    @GetMapping("/orders")
    public String getOrders(Model model, Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        } else {
            User user = userService.findByEmail(principal.getName()).get();
            Set<Order> orders = user.getOrders();

            model.addAttribute("orders", orders);
            model.addAttribute("title", "Orders");
            model.addAttribute("page", "Order");

            return "order";
        }
    }

    @RequestMapping(value = "/add-order", method = {RequestMethod.POST})
    public String createOrder(Model model, Principal principal, HttpSession session,
                              @RequestParam(value = "additionalInformation", required = false) String additionalInformation) {
        if(principal == null) {
            return "redirect:/login";
        } else {
            User user = userService.findByEmail(principal.getName()).get();
            ShoppingCart cart = user.getShoppingCart();
            Order order = orderService.saveOrder(cart, additionalInformation);

            session.removeAttribute("totalItems");
            model.addAttribute("order", order);
            model.addAttribute("title", "Order Detail");
            model.addAttribute("page", "Order Detail");
            model.addAttribute("success", "Add order successfully");

            return "order-detail";
        }
    }

//    @Transactional
//    @GetMapping("/cancel-order/{id}")
//    public String cancelOrder(@PathVariable("id") Long id, Model model, Principal principal) {
//        if(principal == null) {
//            return "redirect:/login";
//        }
//
//        logger.info("Canceling order with ID: {}", id);
//
//        User user = userService.findByEmail(principal.getName()).orElse(null);
//        if (user == null) {
//            return "redirect:/login";
//        }
//
//        orderService.cancelOrder(id);
//
//        logger.info("Order with ID {} canceled successfully", id);
//
////        if (isCanceled) {
////            model.addAttribute("success", "Order canceled successfully.");
////        } else {
////            model.addAttribute("error", "Unable to cancel the order.");
////        }
//
//        Set<Order> orders = user.getOrders();
//        model.addAttribute("orders", orders);
//        model.addAttribute("title", "Orders");
//        model.addAttribute("page", "Order");
//
//        return "redirect:/orders";
//    }
}