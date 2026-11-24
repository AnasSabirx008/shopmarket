package com.shopmarket.spring_boot_shopmarket.service;

import com.shopmarket.spring_boot_shopmarket.dao.ProductCategoryRepository;
import com.shopmarket.spring_boot_shopmarket.dto.ProductCategoryDto;
import com.shopmarket.spring_boot_shopmarket.exception.ResourceNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductCategoryService {

    private final ProductCategoryRepository categoryRepository;

    public ProductCategoryService(ProductCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<ProductCategoryDto> findAll() {
        return categoryRepository.findAll(Sort.by("categoryName")).stream()
                .map(ProductCategoryDto::from)
                .toList();
    }

    public ProductCategoryDto findById(Long id) {
        return categoryRepository.findById(id)
                .map(ProductCategoryDto::from)
                .orElseThrow(() -> new ResourceNotFoundException("Category", id));
    }
}