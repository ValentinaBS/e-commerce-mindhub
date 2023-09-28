package com.purity.ecommerce.dtos;

import com.purity.ecommerce.models.CartItem;

public class CartItemDTO {
    private Long id;
    private int quantity;
    private Long cartId;
    private ProductDTO productDTO;

    public CartItemDTO(CartItem cartItem) {
        this.id = cartItem.getId();
        this.quantity = cartItem.getCount();
        //this.cartId = cartItem.getCart().getId();
        this.productDTO = new ProductDTO(cartItem.getProduct());
    }
}
