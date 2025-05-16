package com.example.studentstay.orm.query;

public class CriteriaBuilder {

    public <T> CriteriaQuery<T> createQuery(Class<T> resultClass) {
        return new CriteriaQuery<>(resultClass, this);
    }

    public <Y> Predicate equal(Path<Y> path, Y value) {
        return new Predicate() {
            @Override
            public String toSql(Root<?> root) {
                return root.alias() + "." + path.getColumnName() + " = ?";
            }

            @Override
            public Object[] getParameters() {
                return new Object[]{value};
            }
        };
    }

    public Predicate isNull(Path<?> path) {
        return new Predicate() {
            @Override
            public String toSql(Root<?> root) {
                return root.alias() + "." + path.getColumnName() + " IS NULL";
            }

            @Override
            public Object[] getParameters() {
                return new Object[0];
            }
        };
    }

    public Predicate and(Predicate... preds) {
        return new Predicate() {
            @Override
            public String toSql(Root<?> root) {
                String joined = String.join(" AND ",
                        java.util.Arrays.stream(preds)
                                .map(p -> "(" + p.toSql(root) + ")")
                                .toArray(String[]::new)
                );
                return "(" + joined + ")";
            }

            @Override
            public Object[] getParameters() {
                return java.util.Arrays.stream(preds)
                        .flatMap(p -> java.util.Arrays.stream(p.getParameters()))
                        .toArray();
            }
        };
    }

    public Order asc(Path<?> path) {
        return new Order(path, true);
    }

    public Order desc(Path<?> path) {
        return new Order(path, false);
    }
}