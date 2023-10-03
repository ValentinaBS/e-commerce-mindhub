package com.purity.ecommerce.controllers;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.purity.ecommerce.dtos.OrderItemDTO;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
            @RequestParam Long id,
            HttpServletResponse response, Authentication authentication) throws IOException, DocumentException {

        Customer customer = customerRepository.findByEmail(authentication.getName());

        // Set the response headers for PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=order-history.pdf");

        // Create a new PDF document
        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());

        // Open the document for writing
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        document.open();
        PdfPTable logo = new PdfPTable(1);
        logo.setWidthPercentage(100);

        Image img = Image.getInstance("C:\\Users\\User\\Downloads\\e-commerce\\e-commerce\\src\\main\\resources\\static\\web\\assets\\logo.png");
        img.scaleToFit(200, 56);
        img.setAlignment(Image.ALIGN_BASELINE);
        PdfPCell imageCell = new PdfPCell(img);
        imageCell.setBorder(PdfPCell.NO_BORDER);
        logo.addCell(imageCell);

        document.add(logo);

        PdfPTable tableTitle = new PdfPTable(1);
        PdfPCell cell = new PdfPCell();
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setPaddingBottom(20);
        cell.addElement(new Paragraph("Your requested order:", new Font(Font.HELVETICA, 16)));
        tableTitle.addCell(cell);
        document.add(tableTitle);

        // Fetch and add order data from your database
        PurchaseOrders order = orderRepository.findById(id).orElse(null);
        Set<OrderItem> orderItems = orderItemRepository.findByPurchaseOrders(order);

        if (order != null) {
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

            PdfPCell orderIdCell = new PdfPCell(new Paragraph(String.valueOf(id)));
            PdfPCell orderDateCell = new PdfPCell(new Paragraph(order.getOrderDate().format(dateFormatter))); // Use DateTimeFormatter
            PdfPCell statusCell = new PdfPCell(new Paragraph(String.valueOf(order.getStatus())));
            PdfPCell totalAmountCell = new PdfPCell(new Paragraph("$" + order.getTotalAmount()));

            table.addCell(orderIdCell);
            table.addCell(orderDateCell);
            table.addCell(statusCell);
            table.addCell(totalAmountCell);

            document.add(table);

            PdfPTable tableItems = new PdfPTable(4);

            tableItems.setSpacingBefore(50f);

            // Table header
            tableItems.addCell("Name");
            tableItems.addCell("Description");
            tableItems.addCell("Price");
            tableItems.addCell("Quantity");

            for (OrderItem orderItem : orderItems) {
                tableItems.addCell(orderItem.getProduct().getName());
                tableItems.addCell(orderItem.getProduct().getDescriptShort());
                tableItems.addCell("$" + orderItem.getProduct().getPrice() * orderItem.getQuantity());
                tableItems.addCell(String.valueOf(orderItem.getQuantity()));
            }

            document.add(tableItems);

        } else {
            document.add(new Paragraph("No orders found."));
        }

        // Close the document
        document.close();
    }

}




