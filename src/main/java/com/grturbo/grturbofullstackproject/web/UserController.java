package com.grturbo.grturbofullstackproject.web;

import com.grturbo.grturbofullstackproject.model.dto.UserUpdateDto;
import com.grturbo.grturbofullstackproject.model.entity.User;
import com.grturbo.grturbofullstackproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;


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

        } else {

            User user = userService.findByEmail(principal.getName()).get();

            model.addAttribute("user", user);
            model.addAttribute("title", "Profile");
            model.addAttribute("page", "Profile");

            return "user-profile";
        }
    }

    @PostMapping("/update-profile")
    private String updateProfile(@Valid @ModelAttribute("user") UserUpdateDto userUpdateDto,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes,
                                 Model model,
                                 Principal principal) {
        if (principal == null) {
            return "redirect:/login";

        } else {

            String userEmail = principal.getName();
            UserUpdateDto user = userService.getUser(userEmail);

            if (result.hasErrors()) {
                return "user-profile";
            }

            userService.update(userUpdateDto);

            UserUpdateDto userUpdate = userService.getUser(principal.getName());

            redirectAttributes.addFlashAttribute("success", "Update successfully!");

            model.addAttribute("user", userUpdate);

            return "redirect:/profile";
        }
    }
}
