package com.grturbo.grturbofullstackproject.web;

import com.grturbo.grturbofullstackproject.model.dto.ProductRecentDto;
import com.grturbo.grturbofullstackproject.model.entity.Product;
import com.grturbo.grturbofullstackproject.service.impl.ProductServiceImpl;
import com.grturbo.grturbofullstackproject.service.impl.CategoryServiceImpl;
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

    private final CategoryServiceImpl categoryServiceImpl;

    private final ProductServiceImpl productServiceImpl;

    public ShopController(CategoryServiceImpl categoryServiceImpl, ProductServiceImpl productServiceImpl) {
        this.categoryServiceImpl = categoryServiceImpl;
        this.productServiceImpl = productServiceImpl;
    }

    @GetMapping("/shop")
    public String shop(Model model,
                       @PageableDefault(
                               page = 0,
                               size = 10 )
                       Pageable pageable){
        List<ProductRecentDto> recentProducts = productServiceImpl.findRecentProducts(10);

        model.addAttribute("recentProducts", recentProducts);
        model.addAttribute("categories", categoryServiceImpl.getAllCategory());
        model.addAttribute("products", productServiceImpl.getAllProducts(pageable));

        return "shop";
    }

    @GetMapping("/shop/category/{id}")
    public String shopByCategory(@PathVariable Long id, Model model,
                                 @PageableDefault(
                                         page = 0,
                                         size = 10 )
                                 Pageable pageable) {
        List<ProductRecentDto> recentProducts = productServiceImpl.findRecentProducts(10);

        model.addAttribute("recentProducts", recentProducts);
        model.addAttribute("categories", categoryServiceImpl.getAllCategory());
        model.addAttribute("products", productServiceImpl.getAllProductsByCategoryId(id, pageable));

        return "shop";
    }


    @GetMapping("/shop/viewproduct/{id}")
    public String viewProduct(Model model, @PathVariable Long id){
        model.addAttribute("product", productServiceImpl.getProductById(id).get());
        model.addAttribute("success", "Add order successfully");

        return "viewProduct";
    }

    @GetMapping("/search")
    public String searchProduct(Model model) {
        model.addAttribute("categories", categoryServiceImpl.getAllCategory());
        model.addAttribute("noResultsMessage", "No results found");

        return "shop-search";
    }

    @PostMapping("/search")
    public String searchProducts(@RequestParam String query, Model model) {
        List<Product> searchResults = productServiceImpl.searchProducts(query);

        if(searchResults.isEmpty()) {
            model.addAttribute("noResultsMessage", "No results found");
        }

        model.addAttribute("categories", categoryServiceImpl.getAllCategory());
        model.addAttribute("products", searchResults);

        return "shop-search";
    }
}
