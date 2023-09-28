package com.purity.ecommerce.repositories;

import com.purity.ecommerce.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByCustomer(String email);
}
