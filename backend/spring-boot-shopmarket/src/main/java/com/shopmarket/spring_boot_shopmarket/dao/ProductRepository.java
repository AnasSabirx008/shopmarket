package com.shopmarket.spring_boot_shopmarket.dao;

import com.shopmarket.spring_boot_shopmarket.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("http://localhost:4200")
@RepositoryRestResource(path = "products")
public interface ProductRepository extends JpaRepository<Product,Long> {
}
