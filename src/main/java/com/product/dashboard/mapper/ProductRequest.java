package com.product.dashboard.mapper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductRequest {

    @JsonIgnore
    public static final String REQUEST_TYPE_ADD = "ADD";
    @JsonIgnore
    public static final String REQUEST_TYPE_UPDATE = "UPDATE";

    private Long id;

    private String name;

    private String category;

    private Double retail_price;

    private Double discounted_price;

    private boolean availability;

}
