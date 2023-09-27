package com.grturbo.grturbofullstackproject.web;

import com.grturbo.grturbofullstackproject.global.GlobalDataCard;
import com.grturbo.grturbofullstackproject.model.entity.Product;
import com.grturbo.grturbofullstackproject.repositority.OrderRepository;
import com.grturbo.grturbofullstackproject.repositority.ShippingDetailsRepository;
import com.grturbo.grturbofullstackproject.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CartController {

    private final ProductService productService;

    private final OrderRepository ordersRepository;

    private final ShippingDetailsRepository shippingDetailsRepository;

    public CartController(ProductService productService, OrderRepository ordersRepository, ShippingDetailsRepository shippingDetailsRepository) {
        this.productService = productService;
        this.ordersRepository = ordersRepository;
        this.shippingDetailsRepository = shippingDetailsRepository;
    }

    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable Long id) {
        GlobalDataCard.cart.add(productService.getProductById(id).get());
        return "redirect:/shop";
    }

    @GetMapping("/cart")
    public String cartGet(Model model) {
        model.addAttribute("cartCount", GlobalDataCard.cart.size());
        model.addAttribute("total",GlobalDataCard.cart.stream().mapToDouble(Product::getPrice).sum());
        model.addAttribute("cart",GlobalDataCard.cart);
        return "cart";
    }
}
