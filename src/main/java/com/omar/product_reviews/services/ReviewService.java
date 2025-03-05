package com.omar.product_reviews.services;

import com.omar.product_reviews.dtos.request.ReviewRequestDTO;
import com.omar.product_reviews.dtos.response.ReviewResponseDTO;
import com.omar.product_reviews.entities.Product;
import com.omar.product_reviews.entities.Review;
import com.omar.product_reviews.entities.User;
import com.omar.product_reviews.exceptions.EntityAlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ProductService productService;

    public ReviewResponseDTO createReview(ReviewRequestDTO reviewRequestDTO, User user) {
        if (productService.reviewExistsByProductIdAndUserId(reviewRequestDTO.productId(), user.getId())){
            throw new EntityAlreadyExistsException(String.format("User with id: %d, already posted a review about this product", user.getId()));
        }

        Product product = productService.findById(reviewRequestDTO.productId());

        if (product.getUserId().equals(user.getId())){
            throw new ValidationException("A Product cannot be reviewed by the user who created it");
        }

        Review review = Review.builder().userId(user.getId()).rating(reviewRequestDTO.rating()).comment(reviewRequestDTO.comment())
                .createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build();
        if (product.getReviews() == null){
            product.setReviews(new ArrayList<>());
        }
        product.getReviews().add(review);
        Product savedProduct = productService.saveProduct(product);
        return new ReviewResponseDTO(user.getFirstName(), user.getLastName(), review.getRating(), review.getComment(),
                savedProduct.getReviews().getLast().getCreatedAt(), savedProduct.getReviews().getLast().getUpdatedAt());
    }

    public ReviewResponseDTO updateReview(ReviewRequestDTO reviewRequestDTO, User user) {
        if (!productService.reviewExistsByProductIdAndUserId(reviewRequestDTO.productId(), user.getId())){
            throw new EntityNotFoundException(String.format("Review from user with id: %d of product with id: %s, not found", user.getId(), reviewRequestDTO.productId()));
        }

        Product product = productService.findById(reviewRequestDTO.productId());
        LocalDateTime createdAt = LocalDateTime.now();

        for (int i = 0; i < product.getReviews().size(); i++) {
            if (product.getReviews().get(i).getUserId().equals(user.getId())){
                createdAt = product.getReviews().get(i).getCreatedAt();
                product.getReviews().remove(i);
                break;
            }
        }

        Review review = Review.builder().userId(user.getId()).rating(reviewRequestDTO.rating()).comment(reviewRequestDTO.comment())
                .createdAt(createdAt).updatedAt(LocalDateTime.now()).build();
        product.getReviews().add(review);
        Product savedProduct = productService.saveProduct(product);
        return new ReviewResponseDTO(user.getFirstName(), user.getLastName(), reviewRequestDTO.rating(), reviewRequestDTO.comment(),
                savedProduct.getReviews().getLast().getCreatedAt(), savedProduct.getReviews().getLast().getUpdatedAt());
    }

    public void deleteReview(User user, String productId) {
        if (!productService.reviewExistsByProductIdAndUserId(productId, user.getId())){
            throw new EntityNotFoundException(String.format("Review from user with id: %d of product with id: %s, not found", user.getId(), productId));
        }

        Product product = productService.findById(productId);

        for (int i = 0; i < product.getReviews().size(); i++) {
            if (product.getReviews().get(i).getUserId().equals(user.getId())){
                product.getReviews().remove(i);
                break;
            }
        }

        productService.saveProduct(product);
    }
}
