package io.github.fernanda.maia.supplier.app.rest.dao;


import java.util.List;
import java.util.Optional;

public interface DAO<T> {

    List<T> getAll();
    Optional<T> getById(Long id);
    Optional<T> save(T object);
    Optional<T> update(Long id, T object);
    Long deleteById(Long id);
}
