package com.purity.ecommerce.dtos;

import com.purity.ecommerce.models.Cart;
import java.util.Set;
import java.util.stream.Collectors;

import java.util.List;
import java.util.stream.Collectors;

public class CartDTO {
    private long id;
    private Set<CartItemDTO> cartItems;

    public CartDTO(Cart cart) {
//        this.id = cart.getId();
        this.cartItems = cart.getItems().stream().map(CartItemDTO::new).collect(Collectors.toSet());
    }

    public long getId() {
        return id;
    }

    public Set<CartItemDTO> getCartItems() {
        return cartItems;
    }

    public int getCartCount() {
        return cartItems.stream()
                .mapToInt(CartItemDTO::getCount)
                .sum();
    }
}
