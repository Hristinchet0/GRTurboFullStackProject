package com.grturbo.grturbofullstackproject.web;

import com.grturbo.grturbofullstackproject.model.dto.UserUpdateDto;
import com.grturbo.grturbofullstackproject.model.entity.InvoiceData;
import com.grturbo.grturbofullstackproject.model.entity.User;
import com.grturbo.grturbofullstackproject.service.InvoiceDataService;
import com.grturbo.grturbofullstackproject.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class UserController {


    private final UserService userService;

    private final InvoiceDataService invoiceDataService;

    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService,
                          InvoiceDataService invoiceDataService,
                          PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.invoiceDataService = invoiceDataService;
        this.passwordEncoder = passwordEncoder;
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
    public String updateProfile(@Valid @ModelAttribute("user") UserUpdateDto userUpdateDto,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes,
                                 Model model,
                                 Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        } else {
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

    @GetMapping("/profile-invoice")
    public String profileInvoice(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";

        } else {

            User user = userService.findByEmail(principal.getName()).get();

            InvoiceData initialInvoiceData = user.getInvoiceData();

            if (initialInvoiceData == null) {
                initialInvoiceData = new InvoiceData();
            }

            model.addAttribute("invoiceData", initialInvoiceData);

            return "user-invoice-data";
        }
    }

    @PostMapping("/update-profile-invoice")
    public String updateInvoiceData(@ModelAttribute("invoiceData") InvoiceData invoiceData,
                                    Principal principal,
                                    Model model) {
        if (principal == null) {
            return "redirect:/login";
        }

        User user = userService.findByEmail(principal.getName()).get();

        if(invoiceData == null) {
            invoiceData = new InvoiceData();
        }

        invoiceDataService.saveInvoiceData(invoiceData, user);

        model.addAttribute("success", "Invoice information updated successfully!");

        return "redirect:/profile-invoice";
    }

    @GetMapping("/change-password")
    public String changePassword(Model model, Principal principal) {

        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("title", "Change password");
        model.addAttribute("page", "Change password");

        return "change-password";
    }

    @PostMapping("/change-password")
    public String changePass(@RequestParam("oldPassword") String oldPassword,
                             @RequestParam("newPassword") String newPassword,
                             @RequestParam("repeatNewPassword") String repeatPassword,
                             RedirectAttributes attributes,
                             Model model,
                             Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            UserUpdateDto userUpdate = userService.getUser(principal.getName());
            if (passwordEncoder.matches(oldPassword, userUpdate.getPassword())
                    && !passwordEncoder.matches(newPassword, oldPassword)
                    && !passwordEncoder.matches(newPassword, userUpdate.getPassword())
                    && repeatPassword.equals(newPassword) && newPassword.length() >= 5) {
                userUpdate.setPassword(passwordEncoder.encode(newPassword));
                userService.changePass(userUpdate);

                attributes.addFlashAttribute("success", "Your password has been changed successfully!");
                return "redirect:/profile";
            } else {
                model.addAttribute("message", "Your password is wrong");

                return "change-password";
            }
        }
    }
}
