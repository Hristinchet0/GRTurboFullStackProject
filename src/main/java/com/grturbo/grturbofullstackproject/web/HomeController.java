package com.grturbo.grturbofullstackproject.web;

import com.grturbo.grturbofullstackproject.model.entity.ShoppingCart;
import com.grturbo.grturbofullstackproject.model.entity.User;
import com.grturbo.grturbofullstackproject.service.impl.ShoppingCartServiceImpl;
import com.grturbo.grturbofullstackproject.service.impl.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class HomeController {

    private final UserServiceImpl userServiceImpl;

    private final ShoppingCartServiceImpl shoppingCartServiceImpl;

    public HomeController(UserServiceImpl userServiceImpl, ShoppingCartServiceImpl shoppingCartServiceImpl) {
        this.userServiceImpl = userServiceImpl;
        this.shoppingCartServiceImpl = shoppingCartServiceImpl;
    }

    @RequestMapping(value = {"/index", "/"}, method = RequestMethod.GET)
    public String home(Model model, Principal principal, HttpSession session){
        if(principal != null){
            session.setAttribute("username", principal.getName());
            User user = userServiceImpl.findByEmail(principal.getName()).get();
            ShoppingCart cart = shoppingCartServiceImpl.findByUserId(user.getId());

            session.setAttribute("totalItems", cart != null ? cart.getTotalItems() : 0);
        }else{
            session.removeAttribute("username");
        }

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
