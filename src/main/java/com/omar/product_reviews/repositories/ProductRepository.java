package com.omar.product_reviews.repositories;

import com.omar.product_reviews.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, String> {

    @Query(value = "{ name: ?0, category: ?1 }")
    Optional<Product> findByNameAndCategory(String name, String category);

    @Query(value = "{ id: ?0, userId: ?1 }")
    Optional<Product> findByIdAndUserId(String id, Long userId);

    @Query(value = "{ name: ?0 }")
    Optional<Product> findByName(String name);

    Page<Product> findByCategory(String category, Pageable pageable);

    @Query(value = "{ userId: ?0 }")
    List<Product> findByUserId(Long id);

    Optional<Product> findByIdAndReviews_UserId(String id, Long userId);

}
