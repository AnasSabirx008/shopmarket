package com.shopmarket.spring_boot_shopmarket.dao;

import com.shopmarket.spring_boot_shopmarket.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestResource(path = "product-category")
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Long> {
}
