package com.purity.ecommerce;

import com.purity.ecommerce.models.Customer;
import com.purity.ecommerce.models.Product;
import com.purity.ecommerce.repositories.CustomerRepository;
import com.purity.ecommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ECommerceApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(ECommerceApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ProductRepository productRepository, CustomerRepository customerRepository){
		return (args) -> {
			Customer customer1 = new Customer("Quione", "quione@mindhub.com", passwordEncoder.encode("123456"), "Cl 1A 5B 53");
			customerRepository.save(customer1);
			Customer admin = new Customer("admin", "admin@mindhub.com", passwordEncoder.encode("123456"), "Cl 1A 5B 53");
			customerRepository.save(admin);

			Product product1 = new Product("Ultra Plush Powder Makeup Brush", "Real Techniques Ultra Plush Powder Makeup Brush helps you create a flawless finish with its large, domed shape for all-over application. RT 201 powder brush has a plush large head for sheer application and a smooth, mattified finish. Best used with powder foundations, powder bronzers, and setting powders. Provides smooth, high-definition results with tapered bristles to help blend powders seamlessly. Extended aluminum ferrules that are light weight, easy to use, and color coded. 100% Cruelty-Free and Vegan. Flawless Results. Easy to clean with Real Techniques Brush Cleansing Gel or Spray. Long Lasting Makeup Application. UltraPlush Synthetic Bristles.", "Long Lasting Makeup Application.", 6.99, "Brushes", "Real Techniques", 6, "https://i.imgur.com/xbcGA7J.jpg");
			productRepository.save(product1);

		};
	}
}
