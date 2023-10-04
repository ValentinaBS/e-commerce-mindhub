package com.purity.ecommerce.repositories;

import com.purity.ecommerce.models.Customer;
import com.purity.ecommerce.models.PurchaseOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface OrderRepository extends JpaRepository<PurchaseOrders, Long> {
    @Query("SELECT o FROM PurchaseOrders o WHERE o.customer = :customer ORDER BY o.orderDate DESC")
    PurchaseOrders getLatestOrderForCustomer(Customer customer);
}
