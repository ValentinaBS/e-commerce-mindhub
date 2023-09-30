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

			Product product1 = new Product("TRICLONE SKIN TECH HYDRATING", "DESCRPTION", "description1", 10.6, "Makeup", "HausLabs", 4, "https://raw.githubusercontent.com/nataliafuentesg/project-images/main/603aba80-4d6e-460d-900e-10b7eef48ece-fadzhkjsilo_11LIGHTNEUTRAL_720x.webp");
			productRepository.save(product1);

		};
	}
}
