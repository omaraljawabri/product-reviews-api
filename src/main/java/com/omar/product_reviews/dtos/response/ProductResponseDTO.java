package com.omar.product_reviews.dtos.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponseDTO(
        String id,
        Long userId,
        String name,
        String description,
        BigDecimal price,
        String category,
        String imgUrl,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
