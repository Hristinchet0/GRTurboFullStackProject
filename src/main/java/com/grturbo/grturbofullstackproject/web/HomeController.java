package com.grturbo.grturbofullstackproject.web;

import com.grturbo.grturbofullstackproject.global.GlobalDataCard;
import com.grturbo.grturbofullstackproject.model.entity.ShoppingCart;
import com.grturbo.grturbofullstackproject.model.entity.User;
import com.grturbo.grturbofullstackproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = {"/index", "/"}, method = RequestMethod.GET)
    public String home(Model model, Principal principal, HttpSession session){
        if(principal != null){
            session.setAttribute("username", principal.getName());
            User user = userService.findByEmail(principal.getName()).get();
            ShoppingCart cart = user.getCart();
//            session.setAttribute("totalItems", cart.getTotalItems());
        }else{
            session.removeAttribute("username");
        }
//        model.addAttribute("cartCount", GlobalDataCard.cart.size());
        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/contactus")
    public String contactus() {
        return "contactus";
    }

    @GetMapping("/faq")
    public String faq() {
        return "faq";
    }
}
