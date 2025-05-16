package com.example.studentstay.orm.query;

public record Root<T>(Class<T> entityClass, String alias) {
    public <Y> Path<Y> get(String attributeName) {
        return new Path<>(this, attributeName);
    }
}