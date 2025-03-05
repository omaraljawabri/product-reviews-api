package com.omar.product_reviews.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record LoginRequestDTO(
        @Email
        @NotNull(message = "email is required")
        @Schema(description = "user email", example = "adam@example.com", type = "string", requiredMode = Schema.RequiredMode.REQUIRED)
        String email,
        @NotNull(message = "password is required")
        @Schema(description = "user password", example = "adam10", type = "string", requiredMode = Schema.RequiredMode.REQUIRED)
        String password
) {
}
