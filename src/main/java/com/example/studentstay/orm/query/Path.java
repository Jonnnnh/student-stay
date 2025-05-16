package com.example.studentstay.orm.query;

public class Path<T> {
    private final Root<?> root;
    private final String attribute;

    public Path(Root<?> root, String attribute) {
        this.root = root;
        this.attribute = attribute;
    }

    public String getColumnName() {
        StringBuilder sb = new StringBuilder();
        for (char c : attribute.toCharArray()) {
            if (Character.isUpperCase(c)) {
                sb.append('_')
                        .append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public Root<?> getRoot() {
        return root;
    }

}