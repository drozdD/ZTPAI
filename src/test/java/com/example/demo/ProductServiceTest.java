package com.example.demo;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService service;

    @Test
    void shouldReturnProductWhenExists() {
        // Given
        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setPrice(3000.0);

        when(repository.findById(1L)).thenReturn(Optional.of(product));
        // When
        Product result = service.getProductById(1L);
        // Then
        assertNotNull(result);
        assertEquals("Laptop", result.getName());
    }

    @Test
    void shouldSaveProductWhenCreating() {
        // Given
        Product product = new Product();
        product.setId(null);
        product.setName("Phone");
        product.setPrice(2000.0);

        Product saved = new Product();
        saved.setId(1L);
        saved.setName("Phone");
        saved.setPrice(2000.0);

        when(repository.save(any(Product.class))).thenReturn(saved);
        // When
        Product result = service.createProduct(product);
        // Then
        assertNotNull(result.getId());
        assertEquals("Phone", result.getName());

        verify(repository, times(1)).save(any(Product.class));
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {

        when(repository.findById(99L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> {
            service.getProductById(99L);
        });
    }

    @Test
    void shouldThrowExceptionWhenPriceIsInvalid() {
        // Given
        Product product = new Product();
        product.setName("Valid Name");
        product.setPrice(-10.0);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.createProduct(product);
        });
        assertEquals("Cena produktu musi być większa od zera.", exception.getMessage());
        verify(repository, never()).save(any(Product.class));
    }

    @Test
    void shouldThrowExceptionWhenNameIsInvalid() {
        // Given
        Product product = new Product();
        product.setName("");
        product.setPrice(100.0);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.createProduct(product);
        });
        assertEquals("Nazwa produktu nie może być pusta.", exception.getMessage());
        verify(repository, never()).save(any(Product.class));
    }
}
