package com.purity.ecommerce.controllers;

import com.purity.ecommerce.dtos.CartDTO;
import com.purity.ecommerce.models.Cart;
import com.purity.ecommerce.models.CartItem;
import com.purity.ecommerce.models.Customer;
import com.purity.ecommerce.models.Product;
import com.purity.ecommerce.repositories.CartRepository;
import com.purity.ecommerce.repositories.CustomerRepository;
import com.purity.ecommerce.repositories.ProductRepository;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class CartController {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/cart/add")
    @Transactional
    public ResponseEntity<String> addToCart(
        @PathVariable long productID,
        @RequestParam int count,
        Authentication authentication,
        HttpServletRequest httpServletRequest
    ) {
        String sesionToken = (String) httpServletRequest.getSession(true).getAttribute("sesionToken");
        Cart cart;

        if (authentication != null) {
            Customer customer = customerRepository.findByEmail(authentication.getName());
            if (customer == null) {
                return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
            }

            if (customer.getCart() == null) {
                cart = new Cart();
                customer.setCart(cart);
            } else {
                cart = customer.getCart();
            }


        } else {
            if (sesionToken == null) {
                sesionToken = UUID.randomUUID().toString();
                httpServletRequest.getSession().setAttribute("sesionToken", sesionToken);
            }
            cart = cartRepository.findBySesionToken(sesionToken);
            if (cart == null) {
                cart = new Cart();
                cart.setSesionToken(sesionToken);
            }
        }

        Product product = productRepository.findById(productID);
        if (product == null) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }

        CartItem existItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId() == product.getId())
                .findFirst()
                .orElse(null);
        if (existItem != null) {
            existItem.setCount(count);
        } else {
            CartItem cartItem = new CartItem(count, product);
            cart.getItems().add(cartItem);
        }

        cartRepository.save(cart);

        return new ResponseEntity<>("Product add successfully", HttpStatus.OK);
    }
}
