package com.grturbo.grturbofullstackproject.web;

import com.grturbo.grturbofullstackproject.model.dto.CategoryAddDto;
import com.grturbo.grturbofullstackproject.model.dto.ProductAddDto;
import com.grturbo.grturbofullstackproject.model.dto.ProductViewDto;
import com.grturbo.grturbofullstackproject.model.dto.UserEditDto;
import com.grturbo.grturbofullstackproject.model.entity.Category;
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
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
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

    @GetMapping("/admin/categories")
    public String getCat(Model model) {
        model.addAttribute("categories", categoryService.getAllCategory());
        return "categories";
    }

    @GetMapping("/admin/categories/add")
    public String getCatAdd(Model model) {
        model.addAttribute("category", new CategoryAddDto());
        return "categoriesAdd";
    }

    @PostMapping("/admin/categories/add")
    public String postCatAdd(@ModelAttribute("category") CategoryAddDto categoryAddDto) {
        categoryService.addCategory(categoryAddDto);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/delete/{id}")
    public String deleteCat(@PathVariable Long id) {
        categoryService.removeCategoryById(id);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/update/{id}")
    public String updateCat(@PathVariable Long id, Model model) {
        Optional<Category> category = categoryService.getCategoryById(id);
        if (category.isPresent()) {
            model.addAttribute("category", category.get());
            return "categoriesAdd";
        } else
            return "404";
    }

    @GetMapping("/admin/products")
    public String products(Model model) {

        List<ProductViewDto> productViewDto = productService.findAll();

        model.addAttribute("products", productViewDto);

        return "products";
    }


    @GetMapping("/admin/products/add")
    public String productAddGet(Model model) {
        model.addAttribute("productAddDto", new ProductAddDto());
        model.addAttribute("categories", categoryService.getAllCategory());
        return "productsAdd";
    }

    @PostMapping("/admin/products/add")
    public String productAddPost(Model model, @ModelAttribute("productAddDto")
    ProductAddDto productAddDto) throws IOException {

        this.productService.addProduct(productAddDto);
        model.addAttribute("products", this.productService.findAll());

        return "redirect:/admin/products";
    }




}