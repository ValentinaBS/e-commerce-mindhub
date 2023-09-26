package com.purity.ecommerce;

import com.purity.ecommerce.models.Product;
import com.purity.ecommerce.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ECommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommerceApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ProductRepository productRepository){
		return (args) -> {
			Product product1 = new Product("TRICLONE SKIN TECH HYDRATING", 10.99, "description1", "Makeup", "HausLabs", "C:/Users/Guest/Documents/MindHubJava/images/fadzhkjsilo_11LIGHTNEUTRAL_720x.webp");
			productRepository.save(product1);
		};
	}
}
