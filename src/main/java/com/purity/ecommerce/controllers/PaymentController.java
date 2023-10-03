package com.purity.ecommerce.controllers;
import com.purity.ecommerce.dtos.PaymentRequestDTO;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class PaymentController {

    @Value("${stripe.secret.key}")
    private String stripeSecretKey; // Your Stripe public key

    @PostMapping("/process-payment")
    public ResponseEntity<String> processPayment(@RequestBody PaymentRequestDTO paymentRequest) {
        try {
            Stripe.apiKey = stripeSecretKey;

            PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder()
                    .setAmount(paymentRequest.getAmount())
                    .setCurrency("usd")
                    .setPaymentMethod(paymentRequest.getPaymentMethodId())
                    .build();

            PaymentIntent paymentIntent = PaymentIntent.create(createParams);

            if ("requires_action".equals(paymentIntent.getStatus())) {

            }
            return ResponseEntity.ok("Payment processed successfully.");
        } catch (StripeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Payment processing failed.");
        }
    }


}

