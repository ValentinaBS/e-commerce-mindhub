package com.purity.ecommerce.controllers;
import com.purity.ecommerce.dtos.PaymentRequestDTO;
import com.purity.ecommerce.models.Customer;
import com.purity.ecommerce.repositories.CustomerRepository;
import com.purity.ecommerce.services.EmailService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class PaymentController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmailService emailService;
    @Value("${stripe.secret.key}")
    private String stripeSecretKey; // Your Stripe public key

    @PostMapping("/process-payment")
    public ResponseEntity<String> processPayment(@RequestBody PaymentRequestDTO paymentRequest, Authentication authentication) {

        if (authentication ==  null) {
            return new ResponseEntity<>("You must login before checkout", HttpStatus.FORBIDDEN);

        }
        try {
            Stripe.apiKey = stripeSecretKey;

            PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder()
                    .setAmount(paymentRequest.getAmount())
                    .setCurrency("usd")
                    .setPaymentMethod(paymentRequest.getPaymentMethodId())
                    .build();

            PaymentIntent paymentIntent = PaymentIntent.create(createParams);

            if ("requires_action".equals(paymentIntent.getStatus())) {
                return ResponseEntity.badRequest().body("Payment processing failed.");
            }
            emailService.sendOrderConfirmationEmail(authentication.getName());
            return ResponseEntity.ok("Payment processed successfully.");
        } catch (StripeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Payment processing failed.");
        }
    }


}

