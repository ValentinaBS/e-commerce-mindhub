package com.purity.ecommerce.dtos;


import com.purity.ecommerce.models.Product;

public class ProductDTO {
    private Long id;
    private String name;
    private String descriptLong;
    private String descriptShort;
    private Double price;

    private String category;
    private String brand;
    private Integer stock;
    private boolean active;
    private String imageUrl;

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.descriptLong = product.getDescriptLong();
        this.descriptShort = product.getDescriptShort();
        this.price = product.getPrice();
        this.category = product.getCategory();
        this.brand = product.getBrand();
        this.stock = product.getStock();
        this.active = product.isActive();
        this.imageUrl = product.getImageUrl();
    }

    public ProductDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptLong() {
        return descriptLong;
    }

    public void setDescriptLong(String descriptLong) {
        this.descriptLong = descriptLong;
    }

    public String getDescriptShort() {
        return descriptShort;
    }

    public void setDescriptShort(String descriptShort) {
        this.descriptShort = descriptShort;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
