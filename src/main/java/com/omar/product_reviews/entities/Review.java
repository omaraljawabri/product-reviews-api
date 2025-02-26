package com.omar.product_reviews.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Review {

    private Long userId;

    private Rating rating;

    private String comment;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
