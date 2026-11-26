package com.shopmarket.spring_boot_shopmarket.product.service;

import com.shopmarket.spring_boot_shopmarket.product.dao.ProductRepository;
import com.shopmarket.spring_boot_shopmarket.common.dto.PageResponse;
import com.shopmarket.spring_boot_shopmarket.product.dto.ProductDto;
import com.shopmarket.spring_boot_shopmarket.product.entity.Product;
import com.shopmarket.spring_boot_shopmarket.product.entity.ProductCategory;
import com.shopmarket.spring_boot_shopmarket.common.exception.ResourceNotFoundException;
import com.shopmarket.spring_boot_shopmarket.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductServiceTest {

    private ProductRepository repository;
    private ProductService service;

    @BeforeEach
    void setUp() {
        repository = mock(ProductRepository.class);
        service = new ProductService(repository);
    }

    @Test
    void findById_returnsDtoWithCategory() {
        // Given
        when(repository.findById(1L)).thenReturn(Optional.of(sampleProduct()));

        // When
        ProductDto dto = service.findById(1L);

        // Then
        assertThat(dto.sku()).isEqualTo("BOOK-001");
        assertThat(dto.categoryId()).isEqualTo(10L);
        assertThat(dto.categoryName()).isEqualTo("Books");
    }

    @Test
    void findById_unknownId_throwsNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void findAll_byCategory_capsPageSize() {
        when(repository.findByCategoryId(eq(10L), any(Pageable.class)))
                .thenAnswer(inv -> new PageImpl<>(List.of(sampleProduct()), inv.getArgument(1), 1));

        PageResponse<ProductDto> page = service.findAll(10L, 0, 1000);

        assertThat(page.content()).hasSize(1);
        assertThat(page.size()).isEqualTo(ProductService.MAX_PAGE_SIZE);
    }

    private static Product sampleProduct() {
        ProductCategory category = new ProductCategory();
        category.setId(10L);
        category.setCategoryName("Books");

        Product p = new Product();
        p.setId(1L);
        p.setSku("BOOK-001");
        p.setName("Clean Code");
        p.setUnitPrice(new BigDecimal("29.99"));
        p.setActive(true);
        p.setUnitsInStock(5);
        p.setCategory(category);
        return p;
    }
}