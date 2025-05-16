package com.example.studentstay.orm.query;

public interface Predicate {
    String toSql(Root<?> root);

    Object[] getParameters();
}