package com.omar.product_reviews.infra.handler;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorMessage(
        LocalDateTime timestamp,
        String title,
        String message,
        HttpStatus httpStatus,
        int status
) {
}
