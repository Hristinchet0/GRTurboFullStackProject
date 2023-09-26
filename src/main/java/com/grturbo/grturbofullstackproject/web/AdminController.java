package com.grturbo.grturbofullstackproject.web;

import com.grturbo.grturbofullstackproject.model.dto.UserEditDto;
import com.grturbo.grturbofullstackproject.model.entity.User;
import com.grturbo.grturbofullstackproject.model.entity.UserRole;
import com.grturbo.grturbofullstackproject.service.CategoryService;
import com.grturbo.grturbofullstackproject.service.CloudinaryService;
import com.grturbo.grturbofullstackproject.service.CustomUserDetailService;
import com.grturbo.grturbofullstackproject.service.ProductService;
import com.grturbo.grturbofullstackproject.service.RoleService;
import com.grturbo.grturbofullstackproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    private final CategoryService categoryService;

    private final ProductService productService;

    private final CustomUserDetailService customUserDetailService;

    private final UserService userService;

    private final RoleService roleService;

    private final CloudinaryService cloudinaryService;

    public AdminController(CategoryService categoryService, ProductService productService, CustomUserDetailService customUserDetailService, UserService userService, RoleService roleService, CloudinaryService cloudinaryService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.customUserDetailService = customUserDetailService;
        this.userService = userService;
        this.roleService = roleService;
        this.cloudinaryService = cloudinaryService;
    }

    @GetMapping("/admin")
    public String adminHome() {
        return "adminHome";
    }

    @GetMapping("/admin/user")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "user";
    }

    @GetMapping("/admin/user/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.removeUserById(id);

        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/update/{id}")
    public String updateUser(@PathVariable("id") Long id, Model model) {
        User user = userService.getUserById(id).get();

        List<UserRole> listRoles = roleService.listRoles();

        model.addAttribute("user", user);
        model.addAttribute("roles", listRoles);

        return "updateUser";
    }

    @PostMapping("/admin/user/save")
    public String saveUser(User user) {
        userService.save(user);

        return "redirect:/admin/user";
    }


}
