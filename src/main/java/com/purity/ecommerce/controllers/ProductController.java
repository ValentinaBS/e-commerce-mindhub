package com.purity.ecommerce.controllers;
import com.purity.ecommerce.dtos.ProductDTO;
import com.purity.ecommerce.models.Product;
import com.purity.ecommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/products")
    public List<ProductDTO> getproducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductDTO::new)
                .collect(toList());

    }

    @PostMapping("/products/create")
    public ResponseEntity<String> createProduct(
            @ModelAttribute ProductDTO productDto,
            @RequestParam("imageFile") MultipartFile imageFile) throws IOException{

        // Validate product data
        if (productDto.getName().isBlank() || productDto.getDescriptLong().isBlank() || productDto.getPrice() <= 0 || productDto.getBrand().isBlank() || productDto.getCategory().isBlank()) {
            return ResponseEntity.badRequest().body("Product data is required.");
        }

        // Validate image file
        if (imageFile == null || imageFile.isEmpty()) {
            return ResponseEntity.badRequest().body("Image file is required.");
        }

        String uniqueFileName = UUID.randomUUID().toString() + "-" + imageFile.getOriginalFilename();
        File dest = new File(uploadDir + File.separator + uniqueFileName);

        imageFile.transferTo(dest);

        productDto.setImageUrl("/uploads/" + uniqueFileName);

        Product product = new Product(
                productDto.getName(),
                productDto.getDescriptLong(),
                productDto.getDescriptShort(),
                productDto.getPrice(),
                productDto.getCategory(),
                productDto.getBrand(),
                productDto.getStock(),
                productDto.getImageUrl()
        );

        productRepository.save(product);

        return ResponseEntity.status(HttpStatus.CREATED).body("Product created successfully.");

    }





}
