package com.omar.product_reviews;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableMongoAuditing
@OpenAPIDefinition(info = @Info(
		title = "Product Reviews API",
		description = "A simple product reviews API when user can register, log in, create products and review products from other users",
		version = "1.0"
))
public class ProductReviewsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductReviewsApplication.class, args);
	}

}
