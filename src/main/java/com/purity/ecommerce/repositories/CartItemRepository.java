package com.purity.ecommerce.repositories;

import com.purity.ecommerce.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findById(long id);
}
