package com.grturbo.grturbofullstackproject.web;

import com.grturbo.grturbofullstackproject.global.GlobalDataCard;
import com.grturbo.grturbofullstackproject.model.dto.ShippingDetailsDto;
import com.grturbo.grturbofullstackproject.model.entity.Product;
import com.grturbo.grturbofullstackproject.repositority.OrderRepository;
import com.grturbo.grturbofullstackproject.repositority.ShippingDetailsRepository;
import com.grturbo.grturbofullstackproject.service.ProductService;
import com.grturbo.grturbofullstackproject.service.ShippingDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CartController {

    private final ProductService productService;

    private final OrderRepository ordersRepository;

    private final ShippingDetailsRepository shippingDetailsRepository;

    private final ShippingDetailsService shippingDetailsService;

    public CartController(ProductService productService, OrderRepository ordersRepository, ShippingDetailsRepository shippingDetailsRepository, ShippingDetailsService shippingDetailsService) {
        this.productService = productService;
        this.ordersRepository = ordersRepository;
        this.shippingDetailsRepository = shippingDetailsRepository;
        this.shippingDetailsService = shippingDetailsService;
    }


}
