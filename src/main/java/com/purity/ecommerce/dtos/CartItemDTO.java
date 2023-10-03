package com.purity.ecommerce.dtos;

import com.purity.ecommerce.models.CartItem;

public class CartItemDTO {
    private Long id;
    private int count;
    private ProductDTO productDTO;

    public CartItemDTO(CartItem cartItem) {
        this.id = cartItem.getId();
        this.count = cartItem.getCount();
        this.productDTO = new ProductDTO(cartItem.getProduct());
    }

    public Long getId() {
        return id;
    }

    public int getCount() {
        return count;
    }

    public ProductDTO getProductDTO() {
        return productDTO;
    }
}
