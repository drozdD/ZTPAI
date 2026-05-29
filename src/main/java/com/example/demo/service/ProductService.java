package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository productRepository) {
        this.repository = productRepository;
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Product createProduct(Product product) {
        if (product.getPrice() == null || product.getPrice() <= 0) {
            throw new IllegalArgumentException("Cena produktu musi być większa od zera.");
        }
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Nazwa produktu nie może być pusta.");
        }
        return repository.save(product);
    }

    public Product getProductById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produkt o ID " + id + " nie istnieje."));
    }

    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        repository.delete(product);
    }
}
