package com.purity.ecommerce.dtos;

import com.purity.ecommerce.models.OrderItem;

public class OrderItemDTO {
    private Long id;
    private String productName;
    private String productDescription;
    private double productPrice;
    private int quantity;

    public OrderItemDTO() {
    }

    public OrderItemDTO(OrderItem orderItem) {
        this.id = orderItem.getId();
        this.productName = orderItem.getProduct().getName();
        this.productDescription = orderItem.getProduct().getDescriptShort();
        this.productPrice = orderItem.getProduct().getPrice();
        this.quantity = orderItem.getQuantity();
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
