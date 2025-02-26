package com.omar.product_reviews.dtos.request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductPutRequestDTO(
        @NotNull(message = "id is required")
        String id,

        @NotNull(message = "product name is required")
        String name,

        @NotNull(message = "product should have a description")
        String description,

        @NotNull(message = "price is required")
        BigDecimal price,

        @NotNull(message = "product should have a category")
        String category
) {
}
