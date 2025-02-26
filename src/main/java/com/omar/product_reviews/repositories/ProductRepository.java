package com.omar.product_reviews.repositories;

import com.omar.product_reviews.entities.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, String> {

    @Query(value = "{ name: ?0, category: ?1 }")
    Optional<Product> findByNameAndCategory(String name, String category);
}
