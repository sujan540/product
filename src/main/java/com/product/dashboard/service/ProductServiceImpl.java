package com.product.dashboard.service;

import com.product.dashboard.controller.SearchCriteria;
import com.product.dashboard.exception.ServiceValidationException;
import com.product.dashboard.model.Product;
import com.product.dashboard.repository.ProductRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl extends BaseEntityService<Product> implements ProductService {

    private final ProductRepository productRepository;

    @Override
    protected Optional<Product> preSaveEntity(Product product) {
        if (!product.isNew()) {
            preValidateAndUpdateEntity(product);
        }
        return super.preSaveEntity(product);
    }

    protected Optional<Product> preValidateAndUpdateEntity(Product product) {
        final Optional<Product> existingProduct = getByUniqueProductId(product.getProductId());
        if (existingProduct.isPresent()) {
            product.setId(existingProduct.get().getId());

            if (Objects.isNull(product.getName())) {
                product.setName(existingProduct.get().getName());
            }
            if (Objects.isNull(product.getCategory())) {
                product.setCategory(existingProduct.get().getCategory());
            }
            if (Objects.isNull(product.getRetailPrice())) {
                product.setRetailPrice(existingProduct.get().getRetailPrice());
            }
            if (Objects.isNull(product.getDiscountedPrice())) {
                product.setDiscountedPrice(existingProduct.get().getDiscountedPrice());
            }
            if (Objects.isNull(product.isAvailability())) {
                product.setAvailability(existingProduct.get().isAvailability());
            }
        } else {
            throw new ServiceValidationException("Product id " + product.getProductId() + " does not exists");
        }
        return Optional.of(product);
    }

    @Override
    public List<String> doValidate(Product product) {
        final List<String> issues = new ArrayList<>();
        if (product.isNew()) {
            final Optional<Product> existingProduct = getByUniqueProductId(product.getProductId());
            if (existingProduct.isPresent()) {
                issues.add("Product id " + product.getProductId() + " already exists");
            }
        }
        return issues;
    }

    @Override
    public Optional<Product> getByUniqueProductId(Long uniqueId) {
        return productRepository.getUniqueProduct(uniqueId);
    }

    @Override
    public List<Product> getByFilters(SearchCriteria searchCriteria) {
        return productRepository.getByFilters(searchCriteria);
    }

    @Override
    protected JpaRepository<Product, Long> getEntityRepository() {
        return productRepository;
    }

}
