package com.omar.product_reviews.controllers;

import com.omar.product_reviews.dtos.request.ProductPutRequestDTO;
import com.omar.product_reviews.dtos.request.ProductRequestDTO;
import com.omar.product_reviews.dtos.response.ProductGetResponseDTO;
import com.omar.product_reviews.dtos.response.ProductResponseDTO;
import com.omar.product_reviews.entities.User;
import com.omar.product_reviews.infra.handler.ErrorMessage;
import com.omar.product_reviews.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@SecurityRequirement(name = "securityConfig")
@Tag(name = "ProductController", description = "These endpoints are responsible for create, update, delete and find products")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "This endpoint is responsible for create a product",
            method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful operation!"),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content()),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content())
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestPart(value = "file", required = false) MultipartFile file,
                                                            @RequestPart("product") @Valid ProductRequestDTO productRequestDTO) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return new ResponseEntity<>(productService.createProduct(file, productRequestDTO, user), HttpStatus.CREATED);
    }

    @Operation(summary = "This endpoint is responsible for update a product",
            method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation!"),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content()),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content())
    })
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductResponseDTO> updateProduct(@RequestPart(value = "file", required = false) MultipartFile file,
                                                            @RequestPart("product") @Valid ProductPutRequestDTO productPutRequestDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok().body(productService.updateProduct(file, productPutRequestDTO, user));
    }

    @Operation(summary = "This endpoint is responsible for delete a product by id",
            method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful operation!"),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content())
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeProduct(@PathVariable("id") String id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        productService.removeProduct(id, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "This endpoint is responsible for find a product by name",
            method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation!"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content()),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content())
    })
    @GetMapping("/name")
    public ResponseEntity<ProductGetResponseDTO> findProductByName(@RequestParam("name") String name){
        return ResponseEntity.ok().body(productService.findProductByName(name));
    }

    @Operation(summary = "This endpoint is responsible for find all products by category",
            method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation!"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content())
    })
    @GetMapping("/category")
    public ResponseEntity<List<ProductGetResponseDTO>> findProductsByCategory(@RequestParam("category") String category,
                                                                @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                                @RequestParam(value = "quantity", defaultValue = "10", required = false) int quantity){
        return ResponseEntity.ok().body(productService.findProductsByCategory(category, page, quantity));
    }

    @Operation(summary = "This endpoint is responsible for find all products by user id",
            method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation!"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content())
    })
    @GetMapping("/user/{id}")
    public ResponseEntity<List<ProductGetResponseDTO>> findProductsByUserId(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(productService.findProductsByUserId(id));
    }

    @Operation(summary = "This endpoint is responsible for find all products in descending order by averageRating",
            method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation!"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content())
    })
    @GetMapping("/rating/best")
    public ResponseEntity<List<ProductGetResponseDTO>> findProductsByBestRating(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                                                @RequestParam(value = "quantity", defaultValue = "10", required = false) int quantity){
        return ResponseEntity.ok().body(productService.findProductsByBestRating(page, quantity));
    }

    @Operation(summary = "This endpoint is responsible for find all products in ascending order by averageRating",
            method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation!"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content())
    })
    @GetMapping("/rating/worst")
    public ResponseEntity<List<ProductGetResponseDTO>> findProductsByWorstRating(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                                                 @RequestParam(value = "quantity", defaultValue = "10", required = false) int quantity){
        return ResponseEntity.ok().body(productService.findProductsByWorstRating(page, quantity));
    }
}
