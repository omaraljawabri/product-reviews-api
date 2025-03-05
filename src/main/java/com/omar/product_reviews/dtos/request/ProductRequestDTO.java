package com.omar.product_reviews.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRequestDTO(
        @NotNull(message = "product name is required")
        @Schema(description = "product name", example = "Playstation 5", type = "string", requiredMode = Schema.RequiredMode.REQUIRED)
        String name,

        @NotNull(message = "product should have a description")
        @Schema(description = "product description", example = "A new generation video game", type = "string", requiredMode = Schema.RequiredMode.REQUIRED)
        String description,

        @NotNull(message = "price is required")
        @Schema(description = "product price", example = "1999.99", type = "BigDecimal", requiredMode = Schema.RequiredMode.REQUIRED)
        BigDecimal price,

        @NotNull(message = "product should have a category")
        @Schema(description = "product category", example = "Technology", type = "string", requiredMode = Schema.RequiredMode.REQUIRED)
        String category
) {
}
