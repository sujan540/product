package com.product.dashboard.controller;

import static com.product.dashboard.controller.ProductsController.BASE_URL;

import com.product.dashboard.exception.ServiceValidationException;
import com.product.dashboard.mapper.ProductMapper;
import com.product.dashboard.mapper.ProductRequest;
import com.product.dashboard.mapper.ProductResponse;
import com.product.dashboard.model.Product;
import com.product.dashboard.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(BASE_URL)
@RequiredArgsConstructor
@Log4j2
public class ProductsController {

    public static final String BASE_URL = "/products";

    private final ProductMapper productMapper;
    private final ProductService productService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ProductResponse> addProduct(@RequestBody ProductRequest productRequest) {
        try {
            final Optional<Product> newProduct = productMapper.buildEntity(productRequest, ProductRequest.REQUEST_TYPE_ADD);
            final Optional<Product> product = productService.saveOrUpdate(newProduct);
            final Optional<ProductResponse> productResponse = productMapper.mapToResponse(product.get());

            return new ResponseEntity(productResponse.get(), HttpStatus.CREATED);
        } catch (ServiceValidationException e) {
            log.error(e);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "{uniqueProductId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ProductResponse> getByUniqueProductId(@PathVariable Long uniqueProductId) {
        final Optional<Product> product = productService.getByUniqueProductId(uniqueProductId);

        if (product.isPresent()) {
            return ResponseEntity.ok(productMapper.mapToResponse(product.get()).get());
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);

    }

    @PutMapping(value = "{uniqueProductId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long uniqueProductId, @RequestBody ProductRequest productRequest) {

        try {
            productRequest.setId(uniqueProductId);
            final Optional<Product> updateProduct = productMapper.buildEntity(productRequest, ProductRequest.REQUEST_TYPE_UPDATE);
            final Optional<Product> product = productService.saveOrUpdate(updateProduct);
            final Optional<ProductResponse> productResponse = productMapper.mapToResponse(product.get());

            return ResponseEntity.ok(productResponse.get());
        } catch (ServiceValidationException e) {
            log.error(e);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<ProductResponse>> getByFilters(@RequestParam(required = false) String category,
                                                              @RequestParam(required = false) Integer availability) throws UnsupportedEncodingException {
        final SearchCriteria searchCriteria = SearchCriteria.builder()
                .category(Objects.nonNull(category) ? URLDecoder.decode(category, "UTF-8") : null)
                .availability(availability)
                .build();
        final List<Product> products = productService.getByFilters(searchCriteria);

        return ResponseEntity.ok(productMapper.buildResponses(products));

    }

}
