package com.omar.product_reviews.services;

import com.omar.product_reviews.dtos.request.ProductRequestDTO;
import com.omar.product_reviews.dtos.response.ProductResponseDTO;
import com.omar.product_reviews.entities.Product;
import com.omar.product_reviews.entities.User;
import com.omar.product_reviews.exceptions.EntityAlreadyExistsException;
import com.omar.product_reviews.repositories.ProductRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CloudinaryService cloudinaryService;

    @Transactional
    public ProductResponseDTO createProduct(MultipartFile file, @Valid ProductRequestDTO productRequestDTO, User user) throws IOException {
        if (productRepository.findByNameAndCategory(productRequestDTO.name(), productRequestDTO.category()).isPresent()){
            throw new EntityAlreadyExistsException(String.format("Entity with name: %s and category: %s, already exists", productRequestDTO.name(), productRequestDTO.category()));
        }

        Product product = Product.builder().userId(user.getId()).name(productRequestDTO.name()).category(productRequestDTO.category())
                .description(productRequestDTO.description()).price(productRequestDTO.price()).build();

        if (file != null){
            String imgUrl = cloudinaryService.uploadFile(file);
            product.setImgUrl(imgUrl);
        }

        Product savedProduct = productRepository.save(product);
        return new ProductResponseDTO(
                savedProduct.getId(), savedProduct.getUserId(), savedProduct.getName(), savedProduct.getDescription(),
                savedProduct.getPrice(), savedProduct.getCategory(), savedProduct.getImgUrl(), savedProduct.getCreatedAt()
        );
    }
}
