package com.purity.ecommerce.controllers;
import com.purity.ecommerce.dtos.CustomerDTO;
import com.purity.ecommerce.models.Cart;
import com.purity.ecommerce.models.Customer;
import com.purity.ecommerce.repositories.CartRepository;
import com.purity.ecommerce.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/customers")
    public List<CustomerDTO> getCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(CustomerDTO::new)
                .collect(toList());
    }

    @GetMapping("/customer/current")
    public ResponseEntity<CustomerDTO> getAuthenticatedCustomer(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.ok(new CustomerDTO(customerRepository.findByEmail(authentication.getName())));
    }

    @PostMapping ("/customer")
    public ResponseEntity<Object> registerNewCustomer(@RequestBody Customer customer, HttpServletRequest httpServletRequest){

        if (customer.getName().isBlank() || customer.getEmail().isBlank() || customer.getAddress().isBlank() || customer.getPassword().isBlank()) {

            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);

        }
        if (customerRepository.findByEmail(customer.getEmail()) !=  null) {

            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);

        }

        Customer newCustomer = new Customer(
                customer.getName(),
                customer.getEmail(),
                passwordEncoder.encode(customer.getPassword()),
                customer.getAddress()
                );
        customerRepository.save(newCustomer);

        String sesionToken = (String) httpServletRequest.getSession(true).getAttribute("sesionToken");
        if (sesionToken != null) {
            Cart cart = cartRepository.findBySesionToken(sesionToken);

            if (cart != null) {
                newCustomer.setCart(cart);
                cart.setSesionToken(null);
                customerRepository.save(newCustomer);
            }
        }

        return new ResponseEntity<>("Customer created successfully",HttpStatus.CREATED);
    }
}
