package com.purity.ecommerce.controllers;

import com.purity.ecommerce.models.Cart;
import com.purity.ecommerce.models.CartItem;
import com.purity.ecommerce.repositories.CartRepository;
import com.purity.ecommerce.repositories.ProductRepository;
import org.apache.catalina.connector.Response;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
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
    @PostMapping("/addToCart")
    public ResponseEntity<String> addToCart(
        @PathVariable long productID,
        @RequestParam int count,
        Authentication authentication,
        HttpServletRequest httpServletRequest
    ) {
        String sesionToken = (String) httpServletRequest.getSession(true).getAttribute("sesionToken");

        if (authentication != null) {

        } else {
            if (sesionToken == null) {
                sesionToken = UUID.randomUUID().toString();
                httpServletRequest.getSession().setAttribute("sesionToken", sesionToken);
                Cart cart = new Cart();
                CartItem cartItem = new CartItem();
                cartItem.setCount(count);
                cartItem.setProduct(productRepository.getReferenceById(productID));
                cart.getItems().add(cartItem);
                cart.setSesionToken(sesionToken);
                cartRepository.save(cart);
            }
        }
    }
}
