package com.product.dashboard.mapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {

    private Long id;

    private String name;

    private String category;

    private Double retail_price;

    private Double discounted_price;

    private boolean availability;
}
