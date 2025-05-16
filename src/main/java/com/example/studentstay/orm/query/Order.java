package com.example.studentstay.orm.query;

public class Order {
    private final Path<?> path;
    private final boolean asc;

    public Order(Path<?> path, boolean asc) {
        this.path = path;
        this.asc = asc;
    }

    public String toSql(Root<?> root) {
        return root.alias() + "." + path.getColumnName() + (asc ? " ASC" : " DESC");
    }
}