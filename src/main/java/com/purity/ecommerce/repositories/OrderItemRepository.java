package com.purity.ecommerce.repositories;
import com.purity.ecommerce.models.OrderItem;
import com.purity.ecommerce.models.PurchaseOrders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    Set<OrderItem> findByPurchaseOrders(PurchaseOrders purchaseOrders);
}
