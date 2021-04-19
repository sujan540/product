package com.product.dashboard.validation;

import com.product.dashboard.exception.ServiceValidationException;

import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public interface Validation<E> {

    default List<String> doValidate(E e) {
        return Arrays.asList();
    }

    default void validate(E e) {
        final List<String> issues = doValidate(e);
        if (!CollectionUtils.isEmpty(issues)) {
            throw new ServiceValidationException(issues.stream()
                    .collect(Collectors.joining(",")));
        }

    }
}

