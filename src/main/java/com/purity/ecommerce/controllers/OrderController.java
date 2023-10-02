package com.purity.ecommerce.controllers;
import com.purity.ecommerce.dtos.PurchaseOrderDTO;
import com.purity.ecommerce.models.*;
import com.purity.ecommerce.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PurchaseOrderDTO> getOrderDetails(@PathVariable Long orderId) {

        PurchaseOrders purchasedOrder = orderRepository.findById(orderId).orElse(null);
        if (purchasedOrder != null) {
            PurchaseOrderDTO  orderDTO = new PurchaseOrderDTO(purchasedOrder);
            return ResponseEntity.ok(orderDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/order/create")
    public ResponseEntity<String> createOrder(Authentication authentication) {
            Customer customer = customerRepository.findByEmail(authentication.getName());

            if (customer == null) {
                return ResponseEntity.badRequest().body("User not found.");
            }


            Cart cart = customer.getCart();

            if (cart == null || cart.getItems().isEmpty()) {
                return ResponseEntity.badRequest().body("Cart is empty.");
            }


            PurchaseOrders order = new PurchaseOrders();
            order.setOrderDate(LocalDateTime.now());
            order.setStatus(OrderStatus.PENDING);
            order.setCustomer(customer);


            Set<CartItem> cartItems = cart.getItems();
            for (CartItem cartItem : cartItems) {
                OrderItem orderItem = new OrderItem();
                orderItem.setQuantity(cartItem.getCount());
                orderItem.setProduct(cartItem.getProduct());
                orderItem.setPurchaseOrders(order);
                order.getOrderItems().add(orderItem);
            }

            double totalAmount = cartItems.stream()
                    .mapToDouble(cartItem -> cartItem.getProduct().getPrice() * cartItem.getCount())
                    .sum();
            order.setTotalAmount(totalAmount);

            orderRepository.save(order);
            cart.getItems().clear();

            return ResponseEntity.ok("Order created successfully.");
        }



}
