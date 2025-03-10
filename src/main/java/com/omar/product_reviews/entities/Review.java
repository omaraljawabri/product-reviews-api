package com.omar.product_reviews.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Review {

    private Long userId;

    private Double rating;

    private String comment;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
