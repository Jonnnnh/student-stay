package com.example.studentstay.orm.dialect;

import com.example.studentstay.orm.metadata.EntityMetadata;
import com.example.studentstay.orm.metadata.MetadataParser;
import com.example.studentstay.orm.metadata.PropertyMetadata;
import com.example.studentstay.orm.mapping.DefaultRowMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.stream.Collectors;

public class PostgreSqlDialect implements Dialect {

    @Override
    public String openQuote() {
        return "\"";
    }

    @Override
    public String closeQuote() {
        return "\"";
    }

    @Override
    public String getIdentityColumn() {
        return "SERIAL";
    }

    @Override
    public String paginate(String sql, int offset, int limit) {
        return sql + " LIMIT " + limit + " OFFSET " + offset;
    }

    @Override
    public String generateSelectById(Class<?> cls) {
        EntityMetadata meta = MetadataParser.parse(cls);
        return "SELECT * FROM " + meta.tableName()
                + " WHERE " + meta.idProperty().columnName() + " = ?";
    }

    @Override
    public String generateSelectAll(Class<?> cls) {
        EntityMetadata meta = MetadataParser.parse(cls);
        return "SELECT * FROM " + meta.tableName();
    }

    @Override
    public <T> T mapRowToEntity(ResultSet rs, Class<T> cls) throws Exception {
        return DefaultRowMapper.map(rs, cls);
    }

    @Override
    public String generateInsert(Object entity) {
        EntityMetadata meta = MetadataParser.parse(entity.getClass());
        List<PropertyMetadata> cols = meta.properties().values().stream()
                .filter(p -> !p.id())
                .toList();

        String colList = cols.stream()
                .map(PropertyMetadata::columnName)
                .collect(Collectors.joining(", "));
        String placeholders = cols.stream()
                .map(p -> "?")
                .collect(Collectors.joining(", "));

        return String.format(
                "INSERT INTO %s (%s) VALUES (%s)",
                meta.tableName(), colList, placeholders
        );
    }

    @Override
    public void bindInsert(PreparedStatement ps, Object entity) throws Exception {
        EntityMetadata meta = MetadataParser.parse(entity.getClass());
        List<PropertyMetadata> cols = meta.properties().values().stream()
                .filter(p -> !p.id())
                .toList();
        for (int i = 0; i < cols.size(); i++) {
            Object value = cols.get(i).getValue(entity);
            ps.setObject(i + 1, value);
        }
    }

    @Override
    public String generateUpdate(Object entity) {
        EntityMetadata meta = MetadataParser.parse(entity.getClass());
        List<PropertyMetadata> cols = meta.properties().values().stream()
                .filter(p -> !p.id())
                .toList();

        String setClause = cols.stream()
                .map(p -> p.columnName() + " = ?")
                .collect(Collectors.joining(", "));

        return String.format(
                "UPDATE %s SET %s WHERE %s = ?",
                meta.tableName(), setClause, meta.getIdColumnName()
        );
    }

    @Override
    public void bindUpdate(PreparedStatement ps, Object entity) throws Exception {
        EntityMetadata meta = MetadataParser.parse(entity.getClass());
        List<PropertyMetadata> cols = meta.properties().values().stream()
                .filter(p -> !p.id())
                .toList();
        int idx = 1;
        for (PropertyMetadata p : cols) {
            ps.setObject(idx++, p.getValue(entity));
        }
        Object id = meta.getIdValue(entity);
        ps.setObject(idx, id);
    }

    @Override
    public String generateDelete(Object entity) {
        EntityMetadata meta = MetadataParser.parse(entity.getClass());
        return String.format(
                "DELETE FROM %s WHERE %s = ?",
                meta.tableName(), meta.getIdColumnName()
        );
    }

    @Override
    public void bindDelete(PreparedStatement ps, Object entity) throws Exception {
        EntityMetadata meta = MetadataParser.parse(entity.getClass());
        Object id = meta.getIdValue(entity);
        ps.setObject(1, id);
    }
}