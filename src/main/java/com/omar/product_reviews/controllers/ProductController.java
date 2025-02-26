package com.omar.product_reviews.controllers;

import com.omar.product_reviews.dtos.request.ProductPutRequestDTO;
import com.omar.product_reviews.dtos.request.ProductRequestDTO;
import com.omar.product_reviews.dtos.response.ProductResponseDTO;
import com.omar.product_reviews.entities.User;
import com.omar.product_reviews.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestPart(value = "file", required = false) MultipartFile file,
                                                            @RequestPart("product") @Valid ProductRequestDTO productRequestDTO) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return new ResponseEntity<>(productService.createProduct(file, productRequestDTO, user), HttpStatus.CREATED);
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductResponseDTO> updateProduct(@RequestPart(value = "file", required = false) MultipartFile file,
                                                            @RequestPart("product") @Valid ProductPutRequestDTO productPutRequestDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok().body(productService.updateProduct(file, productPutRequestDTO, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeProduct(@PathVariable("id") String id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        productService.removeProduct(id, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
