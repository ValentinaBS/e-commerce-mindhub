package com.purity.ecommerce.dtos;
import com.purity.ecommerce.models.Customer;
import com.purity.ecommerce.models.Product;
import com.purity.ecommerce.models.PurchaseOrders;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomerDTO {
    private long id;
    private String name;
    private String email;
    private String address;
    private List<CartItemDTO> cart;
    private List<PurchaseOrderDTO> purchasedOrders;

    public CustomerDTO(Customer customer){
        this.id = customer.getId();
        this.name = customer.getName();
        this.email = customer.getEmail();
        this.address = customer.getAddress();
        this.cart = customer.getCart().getItems()
                .stream()
                .map(CartItemDTO::new)
                .collect(Collectors.toList());
        this.purchasedOrders = customer.getPurchaseOrders().stream()
                .map(PurchaseOrderDTO::new)
                .collect(Collectors.toList());
    }

    public CustomerDTO() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<PurchaseOrderDTO> getPurchasedOrders() {
        return purchasedOrders;
    }

    public void setPurchasedOrders(List<PurchaseOrderDTO> purchasedOrders) {
        this.purchasedOrders = purchasedOrders;
    }

    public List<CartItemDTO> getCart() {
        return cart;
    }

    public void setCart(List<CartItemDTO> cart) {
        this.cart = cart;
    }
}
