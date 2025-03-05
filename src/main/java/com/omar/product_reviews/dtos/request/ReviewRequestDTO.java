package com.omar.product_reviews.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record ReviewRequestDTO(
        @NotNull(message = "product id is required")
        @Schema(description = "id of the product which is going to be reviewed", example = "67bf51c73187774160e5a60c", type = "string", requiredMode = Schema.RequiredMode.REQUIRED)
        String productId,
        @NotNull(message = "review rating is required")
        @Schema(description = "product rating", example = "4", type = "Double", requiredMode = Schema.RequiredMode.REQUIRED)
        Double rating,
        @NotNull(message = "review comment is required")
        @Schema(description = "comment about the product", example = "This is a very good product, it's almost perfect for me, but the game which come with it wasn't good", type = "string", requiredMode = Schema.RequiredMode.REQUIRED)
        String comment
) {
}
