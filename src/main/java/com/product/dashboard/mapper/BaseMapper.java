package com.product.dashboard.mapper;

import com.product.dashboard.validation.Validation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class BaseMapper<E, R, R1> implements Validation<R> {

    public List<R1> buildResponses(List<E> entities) {
        return entities.stream()
                .map(e -> mapToResponse(e).get())
                .collect(Collectors.toList());
    }

    abstract Optional<R1> mapToResponse(E e);

    abstract Optional<E> mapToEntity(R request, String requestType);

    public Optional<E> buildEntity(R request, String requestType) {
        validate(request);
        return mapToEntity(request, requestType);
    }
}

