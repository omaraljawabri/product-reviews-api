package com.omar.product_reviews.dtos.request;

public record ReviewRequestDTO(
        String productId,
        Double rating,
        String comment
) {
}
