package com.purity.ecommerce.repositories;

import ch.qos.logback.core.net.server.Client;
import com.purity.ecommerce.models.Cart;
import com.purity.ecommerce.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findBySesionToken(String sesionToken);
    Cart findByCustomer(Customer customer);
}
