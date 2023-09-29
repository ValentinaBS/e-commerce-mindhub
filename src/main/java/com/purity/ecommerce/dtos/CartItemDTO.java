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
        this.cartId = cartItem.getCart().getId();
        this.productDTO = new ProductDTO(cartItem.getProduct());
    }

    public CartItemDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public ProductDTO getProductDTO() {
        return productDTO;
    }

    public void setProductDTO(ProductDTO productDTO) {
        this.productDTO = productDTO;
    }
}
