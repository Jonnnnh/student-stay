package com.example.studentstay.orm.repository;

import com.example.studentstay.orm.metadata.EntityMetadata;
import com.example.studentstay.orm.metadata.MetadataParser;
import com.example.studentstay.orm.session.EntityManager;

import java.util.List;

public class JdbcRepository<T, ID> implements Repository<T, ID> {

    private final EntityManager em;
    private final EntityMetadata meta;
    private final Class<T> entityClass;

    public JdbcRepository(EntityManager em, Class<T> entityClass) {
        this.em = em;
        this.entityClass = entityClass;
        this.meta = MetadataParser.parse(entityClass);
    }

    @Override
    public T find(ID id) {
        return em.find(entityClass, id);
    }

    @Override
    public List<T> findAll() {
        return em.findAll(entityClass);
    }

    @Override
    public void save(T entity) {
        Object id = meta.getIdValue(entity);
        if (id == null) {
            em.persist(entity);
        } else {
            em.markDirty(entity);
        }
        em.flush();
    }

    @Override
    public void delete(T entity) {
        em.remove(entity);
        em.flush();
    }
}