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
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public ResponseEntity<String> createProduct(@RequestBody ProductDTO productDto) {
        if (productDto.getName().isBlank() || productDto.getDescriptLong().isBlank() || productDto.getDescriptShort().isBlank() ||  productDto.getPrice() <= 0 || productDto.getBrand().isBlank() || productDto.getCategory().isBlank() || productDto.getStock() <= 0 || productDto.getImageUrl().isBlank()) {
            return ResponseEntity.badRequest().body("Product data is invalid.");
        }
        Product existingProduct = productRepository.findByName(productDto.getName());
        if (existingProduct != null) {
            return ResponseEntity.badRequest().body("A product with the same name already exists.");
        }
        String imageUrl = productDto.getImageUrl();
        if (!isValidImageUrl(imageUrl)) {
            return ResponseEntity.badRequest().body("Invalid image URL format.");
        }
      
        Product product = new Product(
                productDto.getName(),
                productDto.getDescriptLong(),
                productDto.getDescriptShort(),
                productDto.getPrice(),
                productDto.getCategory(),
                productDto.getBrand(),
                productDto.getStock(),
                imageUrl,
                true
        );

        productRepository.save(product);

        return ResponseEntity.status(HttpStatus.CREATED).body("Product created successfully.");
    }


    @PatchMapping("/products/update/{productId}")
    public ResponseEntity<String> updateProduct(
            @PathVariable Long productId,
            @RequestBody ProductDTO productDto) {

        Product product = productRepository.findById(productId).orElse(null);

        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        if (productDto.getName() != null && !Objects.equals(product.getName(), productDto.getName())) {
            product.setName(productDto.getName());
        }

        if (productDto.getDescriptLong() != null && !Objects.equals(product.getDescriptLong(), productDto.getDescriptLong())) {
            product.setDescriptLong(productDto.getDescriptLong());
        }

        if (productDto.getDescriptShort() != null && !Objects.equals(product.getDescriptShort(), productDto.getDescriptShort())) {
            product.setDescriptShort(productDto.getDescriptShort());
        }

        if (productDto.getPrice() != null && productDto.getPrice() != product.getPrice()) {
            if (productDto.getPrice() <= 0) {
                return ResponseEntity.badRequest().body("Price must be a positive number.");
            }
            product.setPrice(productDto.getPrice());
        }

        if (productDto.getCategory() != null && !Objects.equals(product.getCategory(), productDto.getCategory())) {
            product.setCategory(productDto.getCategory());
        }

        if (productDto.getBrand() != null && !Objects.equals(product.getBrand(), productDto.getBrand())) {
            product.setBrand(productDto.getBrand());
        }

        if (productDto.getStock() != null && productDto.getStock() != product.getStock()) {
            if (productDto.getStock() <= 0) {
                return ResponseEntity.badRequest().body("Stock must be a positive number.");
            }
            product.setStock(productDto.getStock());
        }

        if (productDto.getImageUrl() != null && !Objects.equals(product.getImageUrl(), productDto.getImageUrl())) {
            String imageUrl = productDto.getImageUrl();
            if (imageUrl.isBlank() || !isValidImageUrl(imageUrl)) {
                return ResponseEntity.badRequest().body("Invalid image URL.");
            }
            product.setImageUrl(imageUrl);
        }

        productRepository.save(product);

        return ResponseEntity.ok("Product updated successfully.");
    }


    @PatchMapping("/products/{productId}")
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

    private boolean isValidImageUrl(String imageUrl) {
        String imageUrlPattern = "^(https?://)?(www\\.)?.+\\.(jpg|jpeg|png|gif)$";
        Pattern pattern = Pattern.compile(imageUrlPattern);
        Matcher matcher = pattern.matcher(imageUrl);
        return matcher.matches();
    }

}