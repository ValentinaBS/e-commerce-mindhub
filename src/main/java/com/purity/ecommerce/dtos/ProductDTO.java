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
        this.imageUrl = product.getImageUrl();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescriptLong() {
        return descriptLong;
    }

    public String getDescriptShort() {
        return descriptShort;
    }

    public Double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getBrand() {
        return brand;
    }

    public Integer getStock() {
        return stock;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
