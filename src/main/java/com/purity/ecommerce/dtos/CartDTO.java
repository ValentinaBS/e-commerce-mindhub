package com.purity.ecommerce.dtos;

import com.purity.ecommerce.models.Cart;

import java.util.List;
import java.util.stream.Collectors;

public class CartDTO {
    private long id;
    private List<CartItemDTO> cartItems;

    public CartDTO(Cart cart) {
        this.id = cart.getId();
        this.cartItems = cart.getItems().stream().map(cartItem -> new CartItemDTO(cartItem)).collect(Collectors.toList());
    }

    public long getId() {
        return id;
    }

    public List<CartItemDTO> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemDTO> cartItems) {
        this.cartItems = cartItems;
    }
}
