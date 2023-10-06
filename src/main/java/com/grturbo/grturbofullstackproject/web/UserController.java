package com.grturbo.grturbofullstackproject.web;

import com.grturbo.grturbofullstackproject.model.dto.UserUpdateDto;
import com.grturbo.grturbofullstackproject.model.entity.InvoiceData;
import com.grturbo.grturbofullstackproject.model.entity.User;
import com.grturbo.grturbofullstackproject.service.InvoiceDataService;
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

    private final InvoiceDataService invoiceDataService;

    public UserController(UserService userService, InvoiceDataService invoiceDataService) {
        this.userService = userService;
        this.invoiceDataService = invoiceDataService;
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

            String userEmail = principal.getName();

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

        // Пренасочване към страницата за потребителски профил или друга страница по ваш избор
        return "redirect:/profile-invoice";
    }
}
