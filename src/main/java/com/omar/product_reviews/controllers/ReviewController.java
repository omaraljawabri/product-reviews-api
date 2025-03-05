package com.omar.product_reviews.controllers;

import com.omar.product_reviews.dtos.request.ReviewRequestDTO;
import com.omar.product_reviews.dtos.response.ReviewResponseDTO;
import com.omar.product_reviews.entities.User;
import com.omar.product_reviews.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponseDTO> createReview(@RequestBody ReviewRequestDTO reviewRequestDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.createReview(reviewRequestDTO, user));
    }

    @PutMapping
    public ResponseEntity<ReviewResponseDTO> updateReview(@RequestBody ReviewRequestDTO reviewRequestDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok().body(reviewService.updateReview(reviewRequestDTO, user));
    }
}
