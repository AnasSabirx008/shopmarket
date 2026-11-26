package com.shopmarket.spring_boot_shopmarket.product.service;

import com.shopmarket.spring_boot_shopmarket.product.dao.ProductRepository;
import com.shopmarket.spring_boot_shopmarket.common.dto.PageResponse;
import com.shopmarket.spring_boot_shopmarket.product.dto.ProductDto;
import com.shopmarket.spring_boot_shopmarket.common.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductService {

    static final int MAX_PAGE_SIZE = 100;

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public PageResponse<ProductDto> findAll(Long categoryId, int page, int size) {
        PageRequest pageable = PageRequest.of(
                Math.max(page, 0),
                Math.min(Math.max(size, 1), MAX_PAGE_SIZE),
                Sort.by("id"));

        Page<ProductDto> result = (categoryId == null
                ? productRepository.findAll(pageable)
                : productRepository.findByCategoryId(categoryId, pageable))
                .map(ProductDto::from);

        return PageResponse.from(result);
    }

    public ProductDto findById(Long id) {
        return productRepository.findById(id)
                .map(ProductDto::from)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id));
    }

    public List<ProductDto> findAllByIds(Collection<Long> ids) {
        return productRepository.findAllById(ids).stream()
                .map(ProductDto::from)
                .toList();
    }
}