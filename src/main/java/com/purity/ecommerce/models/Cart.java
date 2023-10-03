package com.purity.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String sesionToken;

    @OneToMany(mappedBy = "cart", fetch= FetchType.EAGER)
    private Set<CartItem> items = new HashSet<>();
  
    @OneToOne(mappedBy = "cart", fetch= FetchType.EAGER)
    private Customer customer;

    public Cart() {
        this.items = new HashSet<>(); // Initialize the items collection
    }
  
    public Cart(long id, Set<CartItem> items) {
        this.id = id;
        this.items = items;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<CartItem> getItems() {
        return items;
    }

    public void setItems(Set<CartItem> items) {
        this.items = items;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getSesionToken() {
        return sesionToken;
    }

    public void setSesionToken(String sesionToken) {
        this.sesionToken = sesionToken;
    }

    public void addItem(CartItem cartItem) {
        items.add(cartItem);
        cartItem.setCart(this);
    }


}
