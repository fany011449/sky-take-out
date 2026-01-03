package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    boolean addShoppingCart1(ShoppingCartDTO shoppingCartDTO);

    /**
     * 添加購物車
     * @param shoppingCartDTO
     */
    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);

    List<ShoppingCart> listShoppingCart();

    boolean clearShoppingCart();

    boolean subShoppingCart(ShoppingCartDTO shoppingCartDTO);

    ShoppingCart getShoppingCartItem(Long userId, ShoppingCartDTO shoppingCartDTO);
}
