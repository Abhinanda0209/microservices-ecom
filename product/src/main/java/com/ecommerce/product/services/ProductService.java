package com.ecommerce.product.services;


import com.ecommerce.product.dto.ProductRequest;
import com.ecommerce.product.dto.ProductResponse;
import com.ecommerce.product.model.Product;
import com.ecommerce.product.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper mapper;

    public ProductService(ProductRepository productRepository, ModelMapper mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = mapper.map(productRequest, Product.class);
        Product savedProduct = productRepository.save(product);
        return mapper.map(product, ProductResponse.class);
    }

    public ProductResponse updateProduct(Long productId, ProductRequest productRequest) {
        Product productFromDB = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        productFromDB.setName(productRequest.getName());
        productFromDB.setDescription(productRequest.getDescription());
        productFromDB.setPrice(productRequest.getPrice());
        productFromDB.setStockQuantity(productRequest.getStockQuantity());
        productFromDB.setCategory(productRequest.getCategory());
        productFromDB.setImageUrl(productRequest.getImageUrl());

        productRepository.save(productFromDB);

        return mapper.map(productFromDB, ProductResponse.class);
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> productsList = productRepository.findByActiveTrue();
//        List<Product> productsList = productRepository.findAll();

        List<ProductResponse> productResponsesList = productsList.stream()
                .map(product -> mapper.map(product, ProductResponse.class))
                .toList();

        return productResponsesList;
    }

    public ProductResponse deleteProduct(Long productId) {
        Product productFromDB = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        productFromDB.setActive(false);
        productRepository.save(productFromDB);

        return mapper.map(productFromDB, ProductResponse.class);
    }

    public List<ProductResponse> searchProducts(String keyword) {

        List<ProductResponse> productReponsesList = productRepository.searchProducts(keyword).stream()
                .map(product -> mapper.map(product, ProductResponse.class))
                .toList();
        return productReponsesList;
    }

    public Optional<ProductResponse> getProductById(String productId) {

        Optional<Product> product = productRepository.findByIdAndActiveTrue(Long.valueOf(productId));

        return Optional.ofNullable(mapper.map(product, ProductResponse.class));
    }
}
