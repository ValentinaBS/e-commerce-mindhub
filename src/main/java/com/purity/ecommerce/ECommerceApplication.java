package com.purity.ecommerce;

import com.purity.ecommerce.models.*;
import com.purity.ecommerce.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

@SpringBootApplication
public class ECommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommerceApplication.class, args);
	}

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Bean
	public CommandLineRunner initData(ProductRepository productRepository, CustomerRepository customerRepository,
									  OrderRepository orderRepository, CartRepository cartRepository, OrderItemRepository orderItemRepository){
		return (args) -> {
			Customer customer1 = new Customer("Customer1", "customer1@example.com", passwordEncoder.encode("password"), "Address 1");
			customerRepository.save(customer1);
			Customer customer2 = new Customer("Customer2", "customer2@example.com", passwordEncoder.encode("password"), "Address 2");
			customerRepository.save(customer2);
			Customer customer3 = new Customer("Customer3", "customer3@example.com", passwordEncoder.encode("password"), "Address 3");
			customerRepository.save(customer3);

			Product product1 = new Product("TRICLONE SKIN TECH HYDRATING", "DESCRPTION", "description1", 10.6, "Makeup", "HausLabs", 4, "C:/Users/Guest/Documents/MindHubJava/images/fadzhkjsilo_11LIGHTNEUTRAL_720x.webp");
			productRepository.save(product1);
			Product product2 = new Product("Product 1", "Description 1", "Short description 1", 10.6, "Makeup", "Brand 1", 4, "Image 1");
			productRepository.save(product2);
			Product product3 = new Product("Product 2", "Description 2", "Short description 2", 15.2, "Skincare", "Brand 2", 8, "Image 2");
			productRepository.save(product3);

			Cart cart1 = new Cart();
			cartRepository.save(cart1);
			Cart cart2 = new Cart();
			cartRepository.save(cart2);
			Cart cart3 = new Cart();
			cartRepository.save(cart3);

			customer1.setCart(cart1);
			customerRepository.save(customer1);
			customer2.setCart(cart2);
			customerRepository.save(customer2);
			customer3.setCart(cart3);
			customerRepository.save(customer3);

			cart1.addItem(new CartItem(2, product1));
			cart1.addItem(new CartItem(3, product3));
			cartRepository.save(cart1);

			cart2.addItem(new CartItem(3, product3));
			cart2.addItem(new CartItem(1, product2));

			cart3.addItem(new CartItem(1, product2));
			cart3.addItem(new CartItem(2, product1));

			OrderItem orderItem1 = new OrderItem(2, product1);
			OrderItem orderItem2 = new OrderItem(3, product3);
			OrderItem orderItem3 = new OrderItem(1, product2);

			orderItemRepository.save(orderItem1);
			orderItemRepository.save(orderItem2);
			orderItemRepository.save(orderItem3);

			PurchaseOrders purchaseOrders1 = new PurchaseOrders(LocalDateTime.now(), OrderStatus.DELIVERED, 500);

			PurchaseOrders purchaseOrders2 = new PurchaseOrders(LocalDateTime.now(), OrderStatus.DELIVERED, 150);

			PurchaseOrders purchaseOrders3 = new PurchaseOrders(LocalDateTime.now(), OrderStatus.DELIVERED, 300);


			customer1.addOrder(purchaseOrders1);
			customer2.addOrder(purchaseOrders2);
			customer3.addOrder(purchaseOrders3);

			orderRepository.save(purchaseOrders1);
			orderRepository.save(purchaseOrders2);
			orderRepository.save(purchaseOrders3);


		};

	};

}
