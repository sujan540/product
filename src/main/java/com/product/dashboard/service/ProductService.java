package com.product.dashboard.service;

import com.product.dashboard.controller.SearchCriteria;
import com.product.dashboard.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService extends EntityService<Product> {

    Optional<Product> getByUniqueProductId(Long uniqueId);

    List<Product> getByFilters(SearchCriteria searchCriteria);
}
