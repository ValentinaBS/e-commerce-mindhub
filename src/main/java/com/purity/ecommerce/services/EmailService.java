package com.purity.ecommerce.services;
import com.purity.ecommerce.models.Customer;
import com.purity.ecommerce.models.OrderItem;
import com.purity.ecommerce.models.PurchaseOrders;
import com.purity.ecommerce.repositories.CustomerRepository;
import com.purity.ecommerce.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendOrderConfirmationEmail(String recipientEmail, PurchaseOrders order) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(recipientEmail);
            helper.setSubject("Order Confirmation | Purity Makeup ");


            String htmlContent = "<html><body>";
            htmlContent += "<img src='https://i.imgur.com/IQq68cP.png%7D' style='width: 200px; height: auto;' alt='Brand Logo' />";
            htmlContent += "<p style='font-size: 16px;'>Thank you for your order. Your order has been confirmed.</p>";
            htmlContent += "<p>Order ID: " + order.getId() + "</p>";
            String formattedTotalAmount = String.format("%.2f", order.getTotalAmount());
            htmlContent += "<p>Total Amount: $" + formattedTotalAmount + "</p>";
            htmlContent += "<p>Order Items:</p>";

            htmlContent += "<div style='display: flex;'>";
            for (OrderItem orderItem : order.getOrderItems()) {
                htmlContent += "<div style='border: 1px solid #ccc; padding: 10px; margin-bottom: 10px;'>";
                htmlContent += "<img src='" + orderItem.getProduct().getImageUrl() + "' style='width: 100px; height: auto;' alt='Item Image' />";
                htmlContent += "<p><strong>" + orderItem.getProduct().getName() + "</strong></p>";
                htmlContent += "<p>Quantity: <strong>" + orderItem.getQuantity() + "</strong></p>";
                htmlContent += "<p>Price: $" + String.format("%.2f", orderItem.getProduct().getPrice()) + "</p>";
                htmlContent += "</div>";
            }
            htmlContent += "</div>";
            htmlContent += "<p>Follow us on social media:</p>";
            htmlContent += "<a href='https://facebook.com/brand'>Facebook</a><br>";
            htmlContent += "<a href='https://twitter.com/brand'>Twitter</a><br>";
            htmlContent += "<a href='https://instagram.com/brand'>Instagram</a>";
            htmlContent += "</body></html>";

            helper.setText(htmlContent, true);


            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
