package com.omar.product_reviews.controllers;

import com.omar.product_reviews.dtos.request.ReviewRequestDTO;
import com.omar.product_reviews.dtos.response.ReviewResponseDTO;
import com.omar.product_reviews.entities.User;
import com.omar.product_reviews.infra.handler.ErrorMessage;
import com.omar.product_reviews.services.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
@SecurityRequirement(name = "securityConfig")
@Tag(name = "ReviewController", description = "These endpoints are responsible for create, update and delete a review")
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "This endpoint is responsible for create a review for a product",
            method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful operation!"),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content())
    })
    @PostMapping
    public ResponseEntity<ReviewResponseDTO> createReview(@RequestBody ReviewRequestDTO reviewRequestDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.createReview(reviewRequestDTO, user));
    }

    @Operation(summary = "This endpoint is responsible for update a review",
            method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation!"),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content())
    })
    @PutMapping
    public ResponseEntity<ReviewResponseDTO> updateReview(@RequestBody ReviewRequestDTO reviewRequestDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok().body(reviewService.updateReview(reviewRequestDTO, user));
    }

    @Operation(summary = "This endpoint is responsible for delete a review by user id and product id",
            method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful operation!"),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content())
    })
    @DeleteMapping("/productId/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable("id") String productId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        reviewService.deleteReview(user, productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
