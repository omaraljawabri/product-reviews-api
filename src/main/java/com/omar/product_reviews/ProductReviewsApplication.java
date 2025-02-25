package com.omar.product_reviews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ProductReviewsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductReviewsApplication.class, args);
	}

}
