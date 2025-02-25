package com.omar.product_reviews.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record LoginRequestDTO(
        @Email
        @NotNull(message = "email is required")
        String email,
        @NotNull(message = "password is required")
        String password
) {
}
