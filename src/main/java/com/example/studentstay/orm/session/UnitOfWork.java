package com.example.studentstay.orm.session;

import com.example.studentstay.orm.dialect.Dialect;
import com.example.studentstay.orm.util.ConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;


public class UnitOfWork {
    private final PersistenceContext ctx;
    private final Dialect dialect;
    private final ConnectionProvider cp;

    public UnitOfWork(ConnectionProvider cp, Dialect dialect, PersistenceContext ctx) {
        this.cp = cp;
        this.dialect = dialect;
        this.ctx = ctx;
    }

    public void commit() {
        try (Connection conn = cp.getConnection()) {
            conn.setAutoCommit(false);

            for (Object entity : ctx.getNewEntities()) {
                String sql = dialect.generateInsert(entity);
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    dialect.bindInsert(ps, entity);
                    ps.executeUpdate();
                }
            }

            for (Object entity : ctx.getDirtyEntities()) {
                String sql = dialect.generateUpdate(entity);
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    dialect.bindUpdate(ps, entity);
                    ps.executeUpdate();
                }
            }

            for (Object entity : ctx.getDeletedEntities()) {
                String sql = dialect.generateDelete(entity);
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    dialect.bindDelete(ps, entity);
                    ps.executeUpdate();
                }
            }

            conn.commit();
            ctx.clear();

        } catch (Exception ex) {
            throw new RuntimeException("Ошибка в UnitOfWork.commit()", ex);
        }
    }
}