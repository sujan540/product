package com.product.dashboard.service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class BaseEntityService<E> implements EntityService<E> {

    @Override
    public Optional<E> saveOrUpdate(Optional<E> entity) {
        final Optional<E> preUpdate = preSaveEntity(entity.get());
        validate(preUpdate.get());
        if (preUpdate.isPresent()) {
            return Optional.ofNullable(getEntityRepository().save(preUpdate.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<E> saveOrUpdateAll(List<E> entities) {
        return entities.stream()
                .map(entity -> saveOrUpdate(Optional.ofNullable(entity)).get())
                .collect(Collectors.toList());
    }

    @Override
    public void delete(E entity) {
        getEntityRepository().delete(entity);
    }

    protected Optional<E> preSaveEntity(E entity) {
        return Optional.ofNullable(entity);
    }

    protected abstract JpaRepository<E, Long> getEntityRepository();
}

