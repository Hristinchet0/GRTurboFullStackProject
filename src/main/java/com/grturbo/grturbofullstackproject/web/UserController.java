package com.grturbo.grturbofullstackproject.web;

import com.grturbo.grturbofullstackproject.model.entity.User;
import com.grturbo.grturbofullstackproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Optional;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        String username = principal.getName();
        User user = userService.findByEmail(principal.getName()).get();

        model.addAttribute("user", user);
        model.addAttribute("title", "Profile");
        model.addAttribute("page", "Profile");

        return "user-profile";
    }

    @RequestMapping(value = "/update-profile", method = {RequestMethod.GET, RequestMethod.PUT})
    private String updateProfile(@ModelAttribute("user") User user,
                                 Model model,
                                 RedirectAttributes redirectAttributes,
                                 Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        User userSaved = userService.saveProfile(user);

        redirectAttributes.addFlashAttribute("user", userSaved);

        return "redirect:/user-profile";
    }
}
