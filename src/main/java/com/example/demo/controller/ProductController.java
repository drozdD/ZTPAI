package com.example.demo.controller;

import com.example.demo.dto.ProductMapper;
import com.example.demo.dto.ProductRequest;
import com.example.demo.dto.ProductResponse;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    public List<ProductResponse> getAll() {
        return service.getAllProducts().stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    // controller/ProductController.java
    @GetMapping("/{id}")
    public ProductResponse getById(@PathVariable Long id) {
        Product product = service.getProductById(id);
        return ProductMapper.toResponse(product);
    }

    @PostMapping
    public ProductResponse create(
            @Valid @RequestBody ProductRequest request) {
        Product product = service.createProduct(
                ProductMapper.toEntity(request)
        );
        return ProductMapper.toResponse(product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteProduct(id);
    }
}