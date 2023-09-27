package com.purity.ecommerce.controllers;
import com.purity.ecommerce.dtos.CustomerDTO;
import com.purity.ecommerce.models.Customer;
import com.purity.ecommerce.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/customers")
    public List<CustomerDTO> getCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(CustomerDTO::new)
                .collect(toList());
    }

    @PostMapping ("/register")
    public ResponseEntity<Object> registerNewCustomer(@RequestBody Customer customer){

        if (customer.getName().isBlank() || customer.getEmail().isBlank() || customer.getAddress().isBlank() || customer.getPassword().isBlank()) {

            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);

        }
        if (customerRepository.findByEmail(customer.getEmail()) !=  null) {

            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);

        }


        Customer newCustomer = new Customer(
                customer.getName(),
                customer.getEmail(),
                customer.getAddress(),
                customer.getPassword());
        customerRepository.save(newCustomer);

        return new ResponseEntity<>("Customer created successfully!",HttpStatus.CREATED);
    }
}
