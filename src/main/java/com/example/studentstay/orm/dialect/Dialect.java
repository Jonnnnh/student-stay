package com.example.studentstay.orm.dialect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface Dialect {
    String openQuote();

    String closeQuote();

    String getIdentityColumn();

    String paginate(String sql, int offset, int limit);

    String generateSelectById(Class<?> cls);

    String generateSelectAll(Class<?> cls);

    <T> T mapRowToEntity(ResultSet rs, Class<T> cls) throws Exception;

    String generateInsert(Object entity);

    void bindInsert(PreparedStatement ps, Object entity) throws Exception;

    String generateUpdate(Object entity);

    void bindUpdate(PreparedStatement ps, Object entity) throws Exception;

    String generateDelete(Object entity);

    void bindDelete(PreparedStatement ps, Object entity) throws Exception;
}