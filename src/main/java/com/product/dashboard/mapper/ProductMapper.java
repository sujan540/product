package com.product.dashboard.mapper;

import com.product.dashboard.model.Product;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProductMapper extends BaseMapper<Product, ProductRequest, ProductResponse> {

    @Override
    public List<String> doValidate(ProductRequest productRequest) {
        return super.doValidate(productRequest);
    }

    @Override
    public Optional<ProductResponse> mapToResponse(Product product) {
        return Optional.ofNullable(ProductResponse.builder()
                .id(product.getProductId())
                .name(product.getName())
                .category(product.getCategory())
                .retail_price(product.getRetailPrice())
                .discounted_price(product.getDiscountedPrice())
                .availability(product.isAvailability())
                .build());
    }

    @Override
    public Optional<Product> mapToEntity(ProductRequest request, String requestType) {
        return Optional.ofNullable(Product.builder()
                .productId(request.getId())
                .name(request.getName())
                .category(request.getCategory())
                .retailPrice(request.getRetail_price())
                .discountedPrice(request.getDiscounted_price())
                .availability(request.isAvailability())
                .isNew(ProductRequest.REQUEST_TYPE_ADD.equals(requestType))
                .build());
    }
}

