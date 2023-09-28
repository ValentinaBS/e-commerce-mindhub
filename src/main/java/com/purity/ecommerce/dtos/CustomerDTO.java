package com.purity.ecommerce.dtos;
import com.purity.ecommerce.models.Customer;
import com.purity.ecommerce.models.Product;

public class CustomerDTO {
    private long id;
    private String name;
    private String email;
    private String address;

    public CustomerDTO(Customer customer){
        this.id = customer.getId();
        this.name = customer.getName();
        this.email = customer.getEmail();
        this.address = customer.getAddress();
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
}
