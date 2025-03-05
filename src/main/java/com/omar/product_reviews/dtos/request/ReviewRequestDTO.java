package com.omar.product_reviews.dtos.request;

import com.omar.product_reviews.entities.Rating;

public record ReviewRequestDTO(
        String productId,
        Rating rating,
        String comment
) {
}
