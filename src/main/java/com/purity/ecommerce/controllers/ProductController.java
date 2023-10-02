package com.purity.ecommerce.controllers;
import com.purity.ecommerce.dtos.ProductDTO;
import com.purity.ecommerce.models.Product;
import com.purity.ecommerce.repositories.ProductRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class ProductController {


    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/products")
    public List<ProductDTO> getproducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductDTO::new)
                .collect(toList());

    }

    @GetMapping("/product/{id}")
    public ProductDTO getProduct(@PathVariable Long id) {
        return productRepository
                .findById(id)
                .map(ProductDTO::new)
                .orElse(null);
    }

    @PostMapping("/products/create")
    public ResponseEntity<String> createProduct(
            @ModelAttribute ProductDTO productDto,
            @RequestParam("imageFile") MultipartFile imageFile) throws IOException {

        // Validate product data
        if (productDto.getName().isBlank() || productDto.getDescriptLong().isBlank() || productDto.getPrice() <= 0 || productDto.getBrand().isBlank() || productDto.getCategory().isBlank()) {
            return ResponseEntity.badRequest().body("Product data is required.");
        }

        // Validate image file
        if (imageFile == null || imageFile.isEmpty()) {
            return ResponseEntity.badRequest().body("Image file is required.");
        }

        try {
            // Convert the image to Base64
            byte[] imageBytes = imageFile.getBytes();
            String base64Image = Base64.encodeBase64String(imageBytes);
            String uniqueFileName = UUID.randomUUID().toString() + "-" + imageFile.getOriginalFilename();

            // Construct the GitHub API URL for creating a new file
            String githubApiUrl = "https://api.github.com/repos/nataliafuentesg/project-images/contents/" + uniqueFileName;

            // Create a JSON request body with the Base64-encoded image
            String requestBody = "{\"message\":\"Add image\",\"content\":\"" + base64Image + "\"}";

            // Create an HTTP POST request to the GitHub API
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer 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"); // Replace with your GitHub token
            headers.set("Content-Type", "application/json");
            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            // Send the request to create the image file
            ResponseEntity<String> response = restTemplate.exchange(
                    githubApiUrl,
                    HttpMethod.PUT,
                    entity,
                    String.class
            );

            if (response.getStatusCode() == HttpStatus.CREATED) {
                String githubRawImageUrl = "https://raw.githubusercontent.com/nataliafuentesg/project-images/main/" + uniqueFileName;


                productDto.setImageUrl(githubRawImageUrl);

                Product product = new Product(
                        productDto.getName(),
                        productDto.getDescriptLong(),
                        productDto.getDescriptShort(),
                        productDto.getPrice(),
                        productDto.getCategory(),
                        productDto.getBrand(),
                        productDto.getStock(),
                        productDto.getImageUrl(),
                        productDto.isActive()
                );

                productRepository.save(product);

                return ResponseEntity.status(HttpStatus.CREATED).body("Product created successfully.");

            } else {
                // Handle any error responses from GitHub here
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image to GitHub.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image to GitHub.");
        }
    }

    @PatchMapping("/products/update/{productId}")
    public ResponseEntity<String> updateProduct(
            @PathVariable Long productId,
            @RequestBody ProductDTO productDto) {

        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Product existingProduct = optionalProduct.get();

        if (productDto.getName() != null) {
            existingProduct.setName(productDto.getName());
        }

        if (productDto.getDescriptLong() != null) {
            existingProduct.setDescriptLong(productDto.getDescriptLong());
        }

        if (productDto.getDescriptShort() != null) {
            existingProduct.setDescriptShort(productDto.getDescriptShort());
        }

        if (productDto.getPrice() != null) {
            existingProduct.setPrice(productDto.getPrice());
        }

        if (productDto.getCategory() != null) {
            existingProduct.setCategory(productDto.getCategory());
        }

        if (productDto.getBrand() != null) {
            existingProduct.setBrand(productDto.getBrand());
        }

        if (productDto.getStock() != null) {
            existingProduct.setStock(productDto.getStock());
        }

        productRepository.save(existingProduct);

        return ResponseEntity.ok("Product updated successfully.");
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Product product = optionalProduct.get();
        product.setActive(false);
        productRepository.save(product);
        return ResponseEntity.ok("Product deactivated successfully.");
    }



}