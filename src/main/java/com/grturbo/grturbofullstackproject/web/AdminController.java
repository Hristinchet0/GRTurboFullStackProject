package com.grturbo.grturbofullstackproject.web;

import com.grturbo.grturbofullstackproject.model.dto.*;
import com.grturbo.grturbofullstackproject.model.entity.*;
import com.grturbo.grturbofullstackproject.service.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {

    private final CategoryService categoryService;

    private final ProductService productService;

    private final CustomUserDetailService customUserDetailService;

    private final UserService userService;

    private final RoleService roleService;

    private final CloudinaryService cloudinaryService;

    private final OrderService orderService;

    public AdminController(CategoryService categoryService, ProductService productService, CustomUserDetailService customUserDetailService, UserService userService, RoleService roleService, CloudinaryService cloudinaryService, OrderService orderService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.customUserDetailService = customUserDetailService;
        this.userService = userService;
        this.roleService = roleService;
        this.cloudinaryService = cloudinaryService;
        this.orderService = orderService;
    }

//    @GetMapping("/admin")
//    public String adminHome() {
//        return "admin-home";
//    }

    @GetMapping("/admin")
    public String adminHome() {
        return "admin-index";
    }

    @GetMapping("/admin/user")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin-user-all";
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

        return "admin-user-update";
    }

    @PostMapping("/admin/user/save")
    public String saveUser(User user) {
        userService.save(user);

        return "redirect:/admin/user";
    }

    @GetMapping("/categories")
    public String categories(Model model) {

        model.addAttribute("title", "Manage Category");
        List<Category> categories = categoryService.getAllCategory();
        model.addAttribute("categories", categories);
        model.addAttribute("size", categories.size());
        model.addAttribute("categoryNew", new Category());
        return "admin-categories";
    }

    @PostMapping("/save-category")
    public String save(@ModelAttribute("categoryNew") CategoryAddDto category, Model model, RedirectAttributes redirectAttributes) {
        try {
            categoryService.addCategory(category);
            model.addAttribute("categoryNew", category);
            redirectAttributes.addFlashAttribute("success", "Add successfully!");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Duplicate name of category, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            model.addAttribute("categoryNew", category);
            redirectAttributes.addFlashAttribute("error",
                    "Error server");
        }
        return "redirect:/categories";
    }

    @RequestMapping(value = "/delete-category", method = {RequestMethod.GET, RequestMethod.PUT})
    public String delete(Long id, RedirectAttributes redirectAttributes) {
        try {
            categoryService.removeCategoryById(id);
            redirectAttributes.addFlashAttribute("success", "Deleted successfully!");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "You cannot delete the category because it is still in use.");
        } catch (Exception e2) {
            e2.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error server");
        }
        return "redirect:/categories";
    }

    @RequestMapping(value = "/findById", method = {RequestMethod.PUT, RequestMethod.GET})
    @ResponseBody
    public Optional<Category> findById(Long id) {
        return categoryService.findCategoryById(id);
    }

    @GetMapping("/update-category")
    public String update(CategoryAddDto category, RedirectAttributes redirectAttributes) {
        try {
            categoryService.addCategory(category);
            redirectAttributes.addFlashAttribute("success", "Update successfully!");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Duplicate name of category, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error from server or duplicate name of category, please check again!");
        }
        return "redirect:/categories";
    }

    @GetMapping("/products")
    public String products(Model model) {
        List<ProductViewDto> products = productService.findAll();
        model.addAttribute("products", products);
        model.addAttribute("size", products.size());

        return "admin-products";
    }

    @GetMapping("/products/{pageNo}")
    public String allProducts(@PathVariable("pageNo") int pageNo,
                              @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                              Model model,
                              Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        Page<ProductViewDto> products = productService.getAllProducts(pageNo, pageSize);
        model.addAttribute("title", "Manage Products");
        model.addAttribute("size", products.getSize());
        model.addAttribute("products", products);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", products.getTotalPages());
        return "admin-products";
    }

    @GetMapping("/add-product")
    public String addProductPage(Model model) {

        List<Category> categories = categoryService.findAll();
        model.addAttribute("title", "Add Product");
        model.addAttribute("categories", categories);
        model.addAttribute("productDto", new ProductAddDto());

        return "admin-add-product";
    }

    @PostMapping("/save-product")
    public String saveProduct(@ModelAttribute("productDto") ProductAddDto product,
                              RedirectAttributes redirectAttributes) {
        try {
            productService.addProduct(product);
            redirectAttributes.addFlashAttribute("success", "Add new product successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Failed to add new product!");
        }
        return "redirect:/products/0";
    }

//    @GetMapping("/admin/product/delete/{id}")
//    public String deleteProduct(@PathVariable("id") Long id) {
//        productService.removeProductById(id);
//        return "redirect:/admin/products";
//    }

    @RequestMapping(value = "/delete-product", method = {RequestMethod.PUT, RequestMethod.GET})
    public String deletedProduct(Long id, RedirectAttributes redirectAttributes) {
        try {
            productService.removeProductById(id);
            redirectAttributes.addFlashAttribute("success", "Deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Deleted failed!");
        }
        return "redirect:/products/0";
    }

    @GetMapping("/update-product/{id}")
    public String updateProductForm(@PathVariable("id") Long id, Model model) {
        List<Category> categories = categoryService.findAll();
        Product product = productService.getProductById(id).get();
        ProductEditDto productEditDto = new ProductEditDto();
        productEditDto.setId(product.getId());
        productEditDto.setName(product.getName());
        productEditDto.setCategoryId(product.getCategory().getId());
        productEditDto.setPrice(product.getPrice());
        productEditDto.setDescription(product.getDescription());
        productEditDto.setImg(productEditDto.getImg());
        model.addAttribute("title", "Add Product");
        model.addAttribute("categories", categories);
        model.addAttribute("productDto", productEditDto);
        return "admin-update-product";
    }

    @PostMapping("/update-product/{id}")
    public String updateProduct(@ModelAttribute("productDto") ProductEditDto productDto,
                                Product product,
                                RedirectAttributes redirectAttributes) {
        try {

            productService.saveProduct(productDto, product);
            redirectAttributes.addFlashAttribute("success", "Update successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error server, please try again!");
        }
        return "redirect:/products/0";
    }

    @GetMapping("/user-orders")
    public String getAll(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            List<Order> orders = orderService.getOrdersWithDetails();

            // Add orders to the model
            model.addAttribute("orders", orders);
            return "admin-user-orders";
        }
    }

    @RequestMapping(value = "/accept-order", method = {RequestMethod.POST})
    public String acceptOrder(Long id, RedirectAttributes attributes, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            orderService.acceptOrder(id);
            attributes.addFlashAttribute("success", "Order Accepted");
            return "redirect:/user-orders";
        }
    }

    @GetMapping("/view-order-detail")
    public String viewOrderDetail(@RequestParam Long orderId, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        Order order = orderService.getOrderWithDetails(orderId);
        model.addAttribute("order", order);

        return "admin-user-order-details";
    }

    @PostMapping("/view-order-detail")
    public String viewOrderDetailPost(@RequestParam Long orderId, Model model) {

        return "redirect:/view-order-detail?orderId=" + orderId;
    }

    @PostMapping("/cancel-order")
    public String cancelOrder(@RequestParam Long id, Model model) {
        orderService.cancelOrder(id);
        return "redirect:/user-orders";
    }

    @PostMapping("/send-order")
    public String shippingConfirmation(@RequestParam Long id, Model model) {
        orderService.sendOrder(id);
        return "redirect:/user-orders";
    }





}