package com.grturbo.grturbofullstackproject.global;

import com.grturbo.grturbofullstackproject.model.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class GlobalDataCard {

    public static List<Product> cart;

    static {
        cart = new ArrayList<Product>();
    }
}
