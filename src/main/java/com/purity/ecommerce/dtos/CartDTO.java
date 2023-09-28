package com.purity.ecommerce.dtos;

import com.purity.ecommerce.models.Cart;

public class CartDTO {
    private long id;

    public CartDTO(Cart cart) {
        this.id = cart.getId();
    }

    public long getId() {
        return id;
    }
}
