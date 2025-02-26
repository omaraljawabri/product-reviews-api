package com.omar.product_reviews.services;

import com.omar.product_reviews.dtos.request.ProductPutRequestDTO;
import com.omar.product_reviews.dtos.request.ProductRequestDTO;
import com.omar.product_reviews.dtos.response.ProductResponseDTO;
import com.omar.product_reviews.entities.Product;
import com.omar.product_reviews.entities.User;
import com.omar.product_reviews.exceptions.EntityAlreadyExistsException;
import com.omar.product_reviews.repositories.ProductRepository;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

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
        return mapProductToProductResponse(savedProduct);
    }

    @Transactional
    public ProductResponseDTO updateProduct(MultipartFile file, @Valid ProductPutRequestDTO productPutRequestDTO, User user){
        Optional<Product> optionalProduct = productRepository.findByIdAndUserId(productPutRequestDTO.id(), user.getId());
        if (optionalProduct.isEmpty()){
            throw new ValidationException(String.format("Product with id: %s, does not belong to user with id: %d", productPutRequestDTO.id(), user.getId()));
        }

        if (!Objects.equals(optionalProduct.get().getName(), productPutRequestDTO.name()) ||
                !Objects.equals(optionalProduct.get().getCategory(), productPutRequestDTO.category()) &&
                productRepository.findByNameAndCategory(productPutRequestDTO.name(), productPutRequestDTO.category()).isPresent()){
            throw new EntityAlreadyExistsException(String.format("Entity with name: %s and category: %s, already exists", productPutRequestDTO.name(), productPutRequestDTO.category()));
        }

        Product product = Product.builder().id(productPutRequestDTO.id()).userId(user.getId()).name(productPutRequestDTO.name()).description(productPutRequestDTO.description())
                .price(productPutRequestDTO.price()).category(productPutRequestDTO.category()).reviews(optionalProduct.get().getReviews())
                .createdAt(optionalProduct.get().getCreatedAt()).build();

        if (file != null){
            String imgUrl = cloudinaryService.uploadFile(file);
            product.setImgUrl(imgUrl);
        } else{
            product.setImgUrl(optionalProduct.get().getImgUrl());
        }

        Product savedProduct = productRepository.save(product);

        return mapProductToProductResponse(savedProduct);
    }

    public void removeProduct(String id, User user) {
        Product product = productRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ValidationException(String.format("Product with id: %s does not belong to user with id: %d", id, user.getId())));
        productRepository.delete(product);
    }

    private ProductResponseDTO mapProductToProductResponse(Product product){
        return new ProductResponseDTO(
                product.getId(), product.getUserId(), product.getName(), product.getDescription(),
                product.getPrice(), product.getCategory(), product.getImgUrl(), product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}
