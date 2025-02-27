package com.omar.product_reviews.dtos.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ProductGetResponseDTO(
        String id,
        Long userId,
        String name,
        String description,
        BigDecimal price,
        String category,
        String imgUrl,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<ReviewResponseDTO> reviews
) {
}
