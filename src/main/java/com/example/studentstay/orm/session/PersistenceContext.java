package com.example.studentstay.orm.session;

import java.util.*;

public class PersistenceContext {
    private final Map<Class<?>, Map<Object, Object>> identityMap = new HashMap<>();
    private final Set<Object> newEntities = new LinkedHashSet<>();
    private final Set<Object> dirtyEntities = new LinkedHashSet<>();
    private final Set<Object> deletedEntities = new LinkedHashSet<>();

    public <T> void registerManaged(Class<T> cls, Object id, T entity) {
        identityMap
                .computeIfAbsent(cls, c -> new HashMap<>())
                .put(id, entity);
    }

    @SuppressWarnings("unchecked")
    public <T> T getManaged(Class<T> cls, Object id) {
        Map<Object, Object> m = identityMap.get(cls);
        return m == null ? null : (T) m.get(id);
    }

    public void registerNew(Object entity) {
        newEntities.add(entity);
    }

    public void registerDirty(Object entity) {
        if (!newEntities.contains(entity)) dirtyEntities.add(entity);
    }

    public void registerDeleted(Object entity) {
        if (!newEntities.remove(entity)) {
            dirtyEntities.remove(entity);
            deletedEntities.add(entity);
        }
    }

    public Set<Object> getNewEntities() {
        return newEntities;
    }

    public Set<Object> getDirtyEntities() {
        return dirtyEntities;
    }

    public Set<Object> getDeletedEntities() {
        return deletedEntities;
    }

    public void clear() {
        identityMap.clear();
        newEntities.clear();
        dirtyEntities.clear();
        deletedEntities.clear();
    }
}