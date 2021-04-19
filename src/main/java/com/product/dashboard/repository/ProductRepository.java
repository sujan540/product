package com.product.dashboard.repository;

import com.product.dashboard.model.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
    @Query(value = "SELECT * FROM product WHERE product_id=:uniqueId LIMIT 1", nativeQuery = true)
    Optional<Product> getUniqueProduct(@Param("uniqueId") Long uniqueId);
}
