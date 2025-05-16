package com.example.studentstay.orm.session;

import com.example.studentstay.orm.dialect.Dialect;
import com.example.studentstay.orm.util.ConnectionProvider;

public class SessionFactory {
    private final ConnectionProvider cp;
    private final Dialect dialect;

    public SessionFactory(ConnectionProvider cp, Dialect dialect) {
        this.cp = cp;
        this.dialect = dialect;
    }

    public EntityManager createEntityManager() {
        PersistenceContext ctx = new PersistenceContext();
        return new EntityManager(cp, dialect, ctx);
    }
}