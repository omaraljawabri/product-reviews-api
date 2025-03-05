package com.omar.product_reviews.infra.handler;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorMessage(
        @Schema(description = "error timestamp", example = "2025-02-27T09:57:17.835", type = "date")
        LocalDateTime timestamp,
        @Schema(description = "error title", example = "Bad Request", type = "string")
        String title,
        @Schema(description = "error message", example = "User with id: 1, already exists", type = "string")
        String message,
        @Schema(description = "http status of error", example = "BAD REQUEST", type = "Http Status")
        HttpStatus httpStatus,
        @Schema(description = "http status code of error", example = "400", type = "int")
        int status
) {
}
