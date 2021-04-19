package com.product.dashboard.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

@Entity(name = "product")
@Table(name = "product")
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product implements Serializable {

    private static final long serialVersionUID = 213412343214L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    private String name;

    private String category;

    @Column(name = "retail_price")
    private Double retailPrice;

    @Column(name = "discounted_price")
    private Double discountedPrice;

    private boolean availability;

    @Transient
    private transient Boolean isNew;

    @Transient
    public boolean isNew() {
        return Boolean.TRUE.equals(isNew);
    }


}
