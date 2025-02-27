package com.omar.product_reviews.dtos.response;

import com.omar.product_reviews.entities.Rating;

public record ReviewResponseDTO(
        String userFirstName,
        String userLastName,
        Rating rating,
        String comment
) {
}
