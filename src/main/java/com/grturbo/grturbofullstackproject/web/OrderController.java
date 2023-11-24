package com.grturbo.grturbofullstackproject.web;

import com.grturbo.grturbofullstackproject.model.dto.OrderDetailViewDto;
import com.grturbo.grturbofullstackproject.model.entity.Order;
import com.grturbo.grturbofullstackproject.model.entity.ShoppingCart;
import com.grturbo.grturbofullstackproject.model.entity.User;
import com.grturbo.grturbofullstackproject.service.EmailService;
import com.grturbo.grturbofullstackproject.service.OrderDetailService;
import com.grturbo.grturbofullstackproject.service.OrderService;
import com.grturbo.grturbofullstackproject.service.ShoppingCartService;
import com.grturbo.grturbofullstackproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final UserService userService;

    private final ShoppingCartService shoppingCartService;

    private final OrderService orderService;

    private final OrderDetailService orderDetailService;

    private final EmailService emailService;

    public OrderController(UserService userService,
                           ShoppingCartService shoppingCartService,
                           OrderService orderService,
                           OrderDetailService orderDetailService,
                           EmailService emailService) {
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
        this.orderService = orderService;
        this.orderDetailService = orderDetailService;
        this.emailService = emailService;
    }

    @GetMapping("/check-out")
    public String checkOut(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            String username = principal.getName();
            User user = userService.findByEmail(username).get();
            if (user.getAddress() == null || user.getCity() == null || user.getPhoneNumber() == null ||
                    user.getAddress().isEmpty() || user.getCity().isEmpty() || user.getPhoneNumber().isEmpty()) {
                model.addAttribute("information", "You need update your information before check out");
                model.addAttribute("user", user);
                model.addAttribute("title", "Profile");
                model.addAttribute("page", "Profile");

                return "user-profile";
            } else {
                ShoppingCart cart = shoppingCartService.findByUserId(user.getId());

                if(cart == null) {
                    model.addAttribute("information", "Your shopping cart is empty!");
                    return "redirect:/cart";
                } else {
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

    @GetMapping("/orders")
    public String getOrders(Model model, Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        } else {
            User user = userService.findByEmail(principal.getName()).get();
            Set<Order> ordersSet = orderService.findAllOrdersByCustomerId(user.getId());

            model.addAttribute("orders", ordersSet);
            model.addAttribute("title", "Orders");
            model.addAttribute("page", "Order");

            return "order";
        }
    }

    @GetMapping("/successful-order")
    public String successful() {
        return "success";
    }

    @RequestMapping(value = "/add-order", method = {RequestMethod.POST})
    public String createOrder(Model model, Principal principal, HttpSession session,
                              @RequestParam(value = "additionalInformation", required = false) String additionalInformation) {
        if(principal == null) {
            return "redirect:/login";
        } else {
            User user = userService.findByEmail(principal.getName()).get();
            ShoppingCart cart = shoppingCartService.findByUserId(user.getId());
            Order order = orderService.processOrder(cart, additionalInformation, user);

            String to = user.getEmail();
            String subject = "Потвърждение на поръчка";
            String text = "Получихме Вашата поръчка с номер: " + order.getId() + ". Ще се свържем с Вас за потвърждение. Благодарим ви, че избрахте нашия магазин. Поздрави, GR TURBO team!";

            emailService.sendEmail(to, subject, text);

            session.removeAttribute("totalItems");
            model.addAttribute("order", order);
            model.addAttribute("title", "Order Detail");
            model.addAttribute("page", "Order Detail");

            return "redirect:/successful-order";
        }
    }

    @ModelAttribute
    public OrderDetailViewDto orderDetailViewDto() {
        return new OrderDetailViewDto();
    }

    @GetMapping("/order-details/{id}")
    public String getOrderDetails(@PathVariable Long id,
                                  Principal principal,
                                  Model model) {
        if(principal == null) {
            return "redirect:/login";
        }

        List<OrderDetailViewDto> orderDetail = orderDetailService.findByOrderId(id);

        model.addAttribute("order", orderDetail);

        return "order-detail";

    }
}