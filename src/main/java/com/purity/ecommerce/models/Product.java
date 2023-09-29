package com.purity.ecommerce.models;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Product {
  
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String name;
    private String descriptLong;
    private String descriptShort;
    private double price;
    private String category;
    private String brand;
    private int stock;
    private String imageUrl;

    @OneToMany(mappedBy = "product", fetch= FetchType.EAGER)
    private Set<CartItem> cartItems = new HashSet<>();

    public Product() {
    }

    public Product(String name, String descriptLong, String descriptShort, double price, String category, String brand, int stock, String imageUrl) {
        this.name = name;
        this.descriptLong = descriptLong;
        this.descriptShort = descriptShort;
        this.price = price;
        this.category = category;
        this.brand = brand;
        this.stock = stock;
        this.imageUrl = imageUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public int getStock() {
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
}
