package com.example.studentstay.orm.query;

import com.example.studentstay.orm.session.EntityManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Query<T> {
    private final EntityManager em;
    private final CriteriaQuery<T> criteria;

    public Query(EntityManager em, CriteriaQuery<T> criteria) {
        this.em = em;
        this.criteria = criteria;
    }

    public List<T> getResultList() {
        try {
            Connection conn = em.getConnectionProvider().getConnection();
            String sql = criteria.toSql();
            PreparedStatement ps = conn.prepareStatement(sql);

            Object[] params = criteria.getParameters();
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            ResultSet rs = ps.executeQuery();
            List<T> results = new ArrayList<>();
            Class<T> cls = criteria.getResultClass();
            while (rs.next()) {
                T entity = em.getDialect().mapRowToEntity(rs, cls);
                results.add(entity);
            }
            rs.close();
            ps.close();
            conn.close();
            return results;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public T getSingleResult() {
        List<T> list = getResultList();
        if (list.isEmpty()) return null;
        if (list.size() > 1) throw new RuntimeException("Non-unique result");
        return list.get(0);
    }
}