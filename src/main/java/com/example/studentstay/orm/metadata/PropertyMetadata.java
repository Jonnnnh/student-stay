package com.example.studentstay.orm.metadata;

import java.lang.reflect.Field;

public record PropertyMetadata(Field field, String columnName, boolean id) {
    public PropertyMetadata(Field field, String columnName, boolean id) {
        this.field = field;
        this.columnName = columnName;
        this.id = id;
        this.field.setAccessible(true);
    }

    public Object getValue(Object entity) {
        try {
            return field.get(entity);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}