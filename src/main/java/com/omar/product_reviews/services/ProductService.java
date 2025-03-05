package com.omar.product_reviews.services;

import com.omar.product_reviews.dtos.request.ProductPutRequestDTO;
import com.omar.product_reviews.dtos.request.ProductRequestDTO;
import com.omar.product_reviews.dtos.response.ProductGetResponseDTO;
import com.omar.product_reviews.dtos.response.ProductResponseDTO;
import com.omar.product_reviews.dtos.response.ReviewResponseDTO;
import com.omar.product_reviews.entities.Product;
import com.omar.product_reviews.entities.Review;
import com.omar.product_reviews.entities.User;
import com.omar.product_reviews.exceptions.EntityAlreadyExistsException;
import com.omar.product_reviews.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CloudinaryService cloudinaryService;
    private final UserService userService;

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

        if (productRepository.findById(id).isEmpty()){
            throw new EntityNotFoundException(String.format("Product with id: %s, not found", id));
        }

        Product product = productRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ValidationException(String.format("Product with id: %s does not belong to user with id: %d", id, user.getId())));
        productRepository.delete(product);
    }

    public ProductGetResponseDTO findProductByName(String name) {
        Optional<Product> optionalProduct = productRepository.findByName(name);
        if (optionalProduct.isEmpty()){
            return null;
        }

        Product product = optionalProduct.get();

        return setProductGetResponseDTO(product);
    }

    public List<ProductGetResponseDTO> findProductsByCategory(String category, int page, int quantity) {
        Page<Product> pageableProducts = productRepository.findByCategory(category, PageRequest.of(page, quantity));
        if (pageableProducts.isEmpty()){
            return Collections.emptyList();
        }
        List<Product> productList = pageableProducts.stream().toList();
        List<ProductGetResponseDTO> productGetResponseDTOS = new ArrayList<>();

        for (Product product: productList){
            productGetResponseDTOS.add(setProductGetResponseDTO(product));
        }

        return productGetResponseDTOS;
    }

    public List<ProductGetResponseDTO> findProductsByBestRating(int page, int quantity) {
        Page<Product> products = productRepository.findAll(PageRequest.of(page, quantity, Sort.by(Sort.Direction.DESC, "averageRating")));
        if (products.isEmpty()){
            return Collections.emptyList();
        }

        List<Product> productList = products.stream().toList();
        List<ProductGetResponseDTO> productGetResponseDTOS = new ArrayList<>();

        for (Product product: productList){
            productGetResponseDTOS.add(setProductGetResponseDTO(product));
        }

        return productGetResponseDTOS;
    }

    public List<ProductGetResponseDTO> findProductsByUserId(Long id) {
        List<Product> products = productRepository.findByUserId(id);
        if (products.isEmpty()) {
            return null;
        }

        List<ProductGetResponseDTO> productGetResponseDTOS = new ArrayList<>();

        for (Product product: products){
            productGetResponseDTOS.add(setProductGetResponseDTO(product));
        }

        return productGetResponseDTOS;
    }

    public boolean reviewExistsByProductIdAndUserId(String id, Long userId){
        return productRepository.findByIdAndReviews_UserId(id, userId).isPresent();
    }

    private ProductResponseDTO mapProductToProductResponse(Product product){
        return new ProductResponseDTO(
                product.getId(), product.getUserId(), product.getName(), product.getDescription(),
                product.getPrice(), product.getCategory(), product.getImgUrl(), product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }

    private ProductGetResponseDTO setProductGetResponseDTO(Product product){

        List<ReviewResponseDTO> reviewsResponseDTO = new ArrayList<>();
        User user;

        if (product.getReviews() != null) {
            for (Review review : product.getReviews()) {
                user = userService.findUserById(review.getUserId());
                reviewsResponseDTO.add(new ReviewResponseDTO(user.getFirstName(), user.getLastName(), review.getRating(),
                        review.getComment(), review.getCreatedAt(), review.getUpdatedAt()));
            }
        }

        return new ProductGetResponseDTO(product.getId(), product.getUserId(), product.getName(), product.getDescription(),
                product.getPrice(), product.getCategory() ,product.getImgUrl(), product.getAverageRating(), product.getCreatedAt(), product.getUpdatedAt(),
                reviewsResponseDTO);
    }

    public Product findById(String id){
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Product with id: %s, not found", id)));

    }

    public Product saveProduct(Product product){
        return productRepository.save(product);
    }
}