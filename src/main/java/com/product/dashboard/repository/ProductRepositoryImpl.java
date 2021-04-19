package com.product.dashboard.repository;

import com.product.dashboard.controller.SearchCriteria;
import com.product.dashboard.model.Product;

import lombok.RequiredArgsConstructor;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<Product> getByFilters(SearchCriteria searchCriteria) {

        final StringBuilder sql = queryBuilder(searchCriteria);

        final Query query = entityManager.createNativeQuery(sql.toString(), Product.class);

        if (hasCategoryCriteria(searchCriteria)) {
            query.setParameter("category", searchCriteria.getCategory());
        }
        if (hasAvailabilityCriteria(searchCriteria)) {
            query.setParameter("availability", searchCriteria.getAvailability().intValue() == 1);
        }

        return query.getResultList();
    }

    // This method can be simplified using CriteriaBuilder or custom QueryBuilder
    private StringBuilder queryBuilder(SearchCriteria searchCriteria) {
        final StringBuilder sql = new StringBuilder();
        sql.append("SELECT p.*, ((retail_price-discounted_price)/retail_price)*100 AS discounted_percentage FROM product AS p ");
        if (hasCategoryCriteria(searchCriteria)
                || hasAvailabilityCriteria(searchCriteria)) {
            sql.append("WHERE ");
        }
        if (hasCategoryCriteria(searchCriteria)) {
            sql.append("category=:category ");
            if (hasAvailabilityCriteria(searchCriteria)) {
                sql.append("AND ");
            }
        }
        if (hasAvailabilityCriteria(searchCriteria)) {
            sql.append("availability=:availability ");
        }
        sql.append("ORDER BY ");
        if (hasCategoryCriteria(searchCriteria) && !hasAvailabilityCriteria(searchCriteria)) {
            sql.append("availability desc, discounted_price, ");
        }
        if (hasCategoryCriteria(searchCriteria) && hasAvailabilityCriteria(searchCriteria)) {
            sql.append("discounted_percentage desc, discounted_price, ");
        }
        sql.append("product_id");
        return sql;
    }

    private boolean hasAvailabilityCriteria(SearchCriteria searchCriteria) {
        return Objects.nonNull(searchCriteria.getAvailability()) &&
                (searchCriteria.getAvailability().intValue() == 0 || searchCriteria.getAvailability().intValue() == 1);
    }

    private boolean hasCategoryCriteria(SearchCriteria searchCriteria) {
        return StringUtils.isNotEmpty(searchCriteria.getCategory());
    }

}
