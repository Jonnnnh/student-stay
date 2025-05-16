package com.example.studentstay.orm.session;

import com.example.studentstay.orm.dialect.Dialect;
import com.example.studentstay.orm.metadata.MetadataParser;
import com.example.studentstay.orm.transaction.Transactional;
import com.example.studentstay.orm.util.ConnectionProvider;

import java.util.ArrayList;
import java.util.List;

public class EntityManager {
    private final PersistenceContext ctx;
    private final Dialect dialect;
    private final ConnectionProvider cp;

    public EntityManager(ConnectionProvider cp, Dialect dialect, PersistenceContext ctx) {
        this.cp = cp;
        this.dialect = dialect;
        this.ctx = ctx;
    }

    public ConnectionProvider getConnectionProvider() {
        return cp;
    }

    public Dialect getDialect() {
        return dialect;
    }

    public <T> T find(Class<T> cls, Object id) {
        T managed = ctx.getManaged(cls, id);
        if (managed != null) return managed;
        String sql = dialect.generateSelectById(cls);
        try (var conn = cp.getConnection();
             var ps = conn.prepareStatement(sql)) {
            ps.setObject(1, id);
            var rs = ps.executeQuery();
            if (rs.next()) {
                T entity = dialect.mapRowToEntity(rs, cls);
                ctx.registerManaged(cls, id, entity);
                return entity;
            }
            return null;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public <T> List<T> findAll(Class<T> cls) {
        String sql = dialect.generateSelectAll(cls);
        try (var conn = cp.getConnection();
             var ps = conn.prepareStatement(sql);
             var rs = ps.executeQuery()) {

            List<T> list = new ArrayList<>();
            while (rs.next()) {
                T e = dialect.mapRowToEntity(rs, cls);
                Object id = MetadataParser.parse(cls).idProperty().getValue(e);
                ctx.registerManaged(cls, id, e);
                list.add(e);
            }
            return list;
        } catch (Exception ex) {
            throw new RuntimeException("findAll failed", ex);
        }
    }

    public void persist(Object entity) {
        ctx.registerNew(entity);
    }

    public void remove(Object entity) {
        ctx.registerDeleted(entity);
    }

    public void markDirty(Object entity) {
        ctx.registerDirty(entity);
    }

    public void flush() {
        new UnitOfWork(cp, dialect, ctx).commit();
    }

    @Transactional
    public void saveChanges() {
        flush();
    }
}