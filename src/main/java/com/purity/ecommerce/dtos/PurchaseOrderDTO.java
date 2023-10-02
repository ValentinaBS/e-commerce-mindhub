package com.purity.ecommerce.dtos;

import com.purity.ecommerce.models.OrderStatus;
import com.purity.ecommerce.models.PurchaseOrders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PurchaseOrderDTO {
    private Long id;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private double totalAmount;
    private String customerName;
    private Set<OrderItemDTO> orderItems;

    public PurchaseOrderDTO() {
    }

    public PurchaseOrderDTO(PurchaseOrders purchaseOrders) {
        this.id = purchaseOrders.getId();
        this.orderDate = purchaseOrders.getOrderDate();
        this.status = purchaseOrders.getStatus();
        this.totalAmount = purchaseOrders.getTotalAmount();
        this.customerName = purchaseOrders.getCustomer().getName();
        this.orderItems = purchaseOrders.getOrderItems()
                .stream()
                .map(orderItem -> new OrderItemDTO(orderItem)).collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Set<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }
}
