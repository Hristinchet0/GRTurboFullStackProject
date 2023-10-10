package com.grturbo.grturbofullstackproject.web;

import com.grturbo.grturbofullstackproject.global.GlobalDataCard;
import com.grturbo.grturbofullstackproject.model.entity.Product;
import com.grturbo.grturbofullstackproject.service.CategoryService;
import com.grturbo.grturbofullstackproject.service.ProductService;
import org.springframework.stereotype.Controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ShopController {

    private final CategoryService categoryService;

    private final ProductService productService;

    public ShopController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("/shop")
    public String shop(Model model,
                       @PageableDefault(
                               page = 0,
                               size = 5 )
                       Pageable pageable){
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("products", productService.getAllProducts(pageable));
        model.addAttribute("cartCount", GlobalDataCard.cart.size());

        return "shop";
    }

    @GetMapping("/shop/category/{id}")
    public String shopByCategory(@PathVariable Long id, Model model,
                                 @PageableDefault(
                                         page = 0,
                                         size = 5 )
                                 Pageable pageable) {

        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("products", productService.getAllProductsByCategoryId(id, pageable));
        model.addAttribute("cartCount", GlobalDataCard.cart.size());

        return "shop";
    }


    @GetMapping("/shop/viewproduct/{id}")
    public String viewProduct(Model model, @PathVariable Long id){
        model.addAttribute("product", productService.getProductById(id).get());
        model.addAttribute("cartCount", GlobalDataCard.cart.size());

        return "viewProduct";
    }

    @PostMapping("/search")
    public String searchProducts(@RequestParam String query, Model model) {
        List<Product> searchResults = productService.searchProducts(query);

        if(searchResults.isEmpty()) {
            model.addAttribute("noResultsMessage", "No results found");
        }

        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("products", searchResults);

        // Връщайте страницата с резултатите от търсенето
        return "shop-search";
    }
}
