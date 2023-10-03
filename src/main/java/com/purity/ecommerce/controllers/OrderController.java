package com.purity.ecommerce.controllers;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.purity.ecommerce.dtos.PurchaseOrderDTO;
import com.purity.ecommerce.models.*;
import com.purity.ecommerce.repositories.*;
import com.purity.ecommerce.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
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

    @Autowired
    private EmailService emailService;

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PurchaseOrderDTO> getOrderDetails(@PathVariable Long orderId) {

        PurchaseOrders purchasedOrder = orderRepository.findById(orderId).orElse(null);
        if (purchasedOrder != null) {
            PurchaseOrderDTO orderDTO = new PurchaseOrderDTO(purchasedOrder);
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

        emailService.sendOrderConfirmationEmail(authentication.getName());
        return ResponseEntity.ok("Order created successfully.");
    }

    @GetMapping("/current/orders/generate-pdf")
    public void generatePdf(
            @RequestParam("startDate")
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate, // Adjust the pattern to match your date format
            @RequestParam("endDate")
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            HttpServletResponse response, Authentication authentication) throws IOException, DocumentException {

        Customer customer = customerRepository.findByEmail(authentication.getName());


        // Set the response headers for PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=order-history.pdf");

        // Create a new PDF document
        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());

        // Open the document for writing
        document.open();

        // Add content to the PDF
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String formattedStartDate = startDate.format(dateFormatter);
        String formattedEndDate = endDate.format(dateFormatter);


        // Fetch and add order history data from your database
        Set<PurchaseOrders> orderHistoryList = customer.getPurchaseOrders();

        if (!orderHistoryList.isEmpty()) {
            PdfPTable table = new PdfPTable(4); // 4 columns: Order ID, Order Date, Quantity, Total Amount

            // Table header
            PdfPCell cell1 = new PdfPCell(new Paragraph("Order ID"));
            PdfPCell cell2 = new PdfPCell(new Paragraph("Order Date"));
            PdfPCell cell3 = new PdfPCell(new Paragraph("Status"));
            PdfPCell cell4 = new PdfPCell(new Paragraph("Total Amount"));

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);

            // Table data
            for (PurchaseOrders orderHistory : orderHistoryList) {
                PdfPCell orderIdCell = new PdfPCell(new Paragraph(String.valueOf(orderHistory.getId())));
                PdfPCell orderDateCell = new PdfPCell(new Paragraph(orderHistory.getOrderDate().format(dateFormatter))); // Use DateTimeFormatter
                PdfPCell statusCell = new PdfPCell(new Paragraph(String.valueOf(orderHistory.getStatus())));
                PdfPCell totalAmountCell = new PdfPCell(new Paragraph(String.valueOf(orderHistory.getTotalAmount())));

                table.addCell(orderIdCell);
                table.addCell(orderDateCell);
                table.addCell(statusCell);
                table.addCell(totalAmountCell);
            }

            document.add(table);
        } else {
            document.add(new Paragraph("No orders found between the specified dates."));
        }

        // Close the document
        document.close();
    }

}




