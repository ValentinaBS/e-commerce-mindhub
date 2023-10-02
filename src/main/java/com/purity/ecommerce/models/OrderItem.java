package com.purity.ecommerce.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private PurchaseOrders purchaseOrders;

    public OrderItem() {
    }

    public OrderItem(int quantity, Product product) {
        this.quantity = quantity;
        this.product = product;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setPurchaseOrders(PurchaseOrders purchaseOrders) {

        this.purchaseOrders = purchaseOrders;
    }

    public PurchaseOrders getPurchaseOrders() {
        return purchaseOrders;
    }
}
