package com.product.dashboard.repository;

import com.product.dashboard.controller.SearchCriteria;
import com.product.dashboard.model.Product;

import java.util.List;

public interface ProductRepositoryCustom {

    List<Product> getByFilters(SearchCriteria searchCriteria);
}
