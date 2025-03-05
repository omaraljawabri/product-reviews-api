package com.omar.product_reviews.dtos.response;

import java.time.LocalDateTime;

public record ReviewResponseDTO(
        String userFirstName,
        String userLastName,
        Double rating,
        String comment,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
