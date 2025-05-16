package com.example.studentstay.orm.query;

import java.util.ArrayList;
import java.util.List;

public class CriteriaQuery<T> {
    private final Class<T> resultClass;
    private final CriteriaBuilder cb;
    private Root<T> root;
    private final List<Predicate> whereClauses = new ArrayList<>();
    private final List<Order> orderList = new ArrayList<>();

    public CriteriaQuery(Class<T> resultClass, CriteriaBuilder cb) {
        this.resultClass = resultClass;
        this.cb = cb;
    }

    public Root<T> from(Class<T> clazz) {
        this.root = new Root<>(clazz, "e");
        return root;
    }

    public CriteriaQuery<T> where(Predicate... preds) {
        java.util.Collections.addAll(whereClauses, preds);
        return this;
    }

    public CriteriaQuery<T> orderBy(Order... orders) {
        java.util.Collections.addAll(orderList, orders);
        return this;
    }

    public Class<T> getResultClass() {
        return resultClass;
    }

    public Root<T> getRoot() {
        return root;
    }

    String toSql() {
        var meta = com.example.studentstay.orm.metadata.MetadataParser.parse(resultClass);
        String table = meta.tableName() + " " + root.alias();
        StringBuilder sql = new StringBuilder("SELECT * FROM ").append(table);
        if (!whereClauses.isEmpty()) {
            sql.append(" WHERE ")
                    .append(
                            whereClauses.stream()
                                    .map(p -> p.toSql(root))
                                    .collect(java.util.stream.Collectors.joining(" AND "))
                    );
        }
        if (!orderList.isEmpty()) {
            sql.append(" ORDER BY ")
                    .append(
                            orderList.stream()
                                    .map(o -> o.toSql(root))
                                    .collect(java.util.stream.Collectors.joining(", "))
                    );
        }
        return sql.toString();
    }

    Object[] getParameters() {
        return whereClauses.stream()
                .flatMap(p -> java.util.Arrays.stream(p.getParameters()))
                .toArray();
    }
}