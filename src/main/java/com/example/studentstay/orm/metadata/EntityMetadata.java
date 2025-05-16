package com.example.studentstay.orm.metadata;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;

public record EntityMetadata(Class<?> entityClass, String tableName, PropertyMetadata idProperty,
                             Map<String, PropertyMetadata> properties) {
    public EntityMetadata(Class<?> entityClass,
                          String tableName,
                          PropertyMetadata idProperty,
                          Map<String, PropertyMetadata> properties) {
        this.entityClass = entityClass;
        this.tableName = tableName;
        this.idProperty = idProperty;
        this.properties = Collections.unmodifiableMap(properties);
    }

    public String getIdColumnName() {
        return idProperty.columnName();
    }

    public Object getIdValue(Object entity) {
        try {
            Field f = idProperty.field();
            f.setAccessible(true);
            return f.get(entity);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}