package com.purity.ecommerce.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private int count;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public CartItem() {
    }

    public CartItem(int count, Product product) {
        this.count = count;
        this.product = product;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
