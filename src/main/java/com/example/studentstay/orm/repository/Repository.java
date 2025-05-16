package com.example.studentstay.orm.repository;

import java.util.List;

public interface Repository<T, ID> {
    T find(ID id);

    List<T> findAll();

    void save(T entity);

    void delete(T entity);
}
