package com.purity.ecommerce.repositories;
import com.purity.ecommerce.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
