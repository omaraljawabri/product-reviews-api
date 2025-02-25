package com.omar.product_reviews.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record RegisterRequestDTO(
        @NotNull(message = "first name is required")
        String firstName,
        String lastName,
        @Email
        @NotNull(message = "email is required")
        String email,
        @NotNull(message = "password is required")
        String password
) {
}
