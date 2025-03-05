package com.omar.product_reviews.dtos.request;

import jakarta.validation.constraints.NotNull;

public record ReviewRequestDTO(
        @NotNull(message = "product id is required")
        String productId,
        @NotNull(message = "review rating is required")
        Double rating,
        @NotNull(message = "review comment is required")
        String comment
) {
}
