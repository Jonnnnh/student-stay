package com.example.studentstay.orm.transaction;

import com.example.studentstay.orm.util.ConnectionProvider;
import com.example.studentstay.orm.util.exceptions.TransactionException;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
    private static final ConnectionProvider cp = new ConnectionProvider();
    private static final ThreadLocal<Connection> context = new ThreadLocal<>();

    public static void begin() {
        try {
            Connection conn = cp.getConnection();
            conn.setAutoCommit(false);
            context.set(conn);
        } catch (SQLException e) {
            throw new TransactionException("Не удалось начать транзакцию", e);
        }
    }

    public static void commit() {
        Connection conn = context.get();
        if (conn == null) return;
        try {
            conn.commit();
        } catch (SQLException e) {
            throw new TransactionException("Ошибка при commit транзакции", e);
        } finally {
            closeConnection();
        }
    }

    public static void rollback() {
        Connection conn = context.get();
        if (conn == null) return;
        try {
            conn.rollback();
        } catch (SQLException e) {
            throw new TransactionException("Ошибка при rollback транзакции", e);
        } finally {
            closeConnection();
        }
    }

    public static Connection getCurrentConnection() {
        Connection conn = context.get();
        if (conn != null) {
            return conn;
        }
        try {
            return cp.getConnection();
        } catch (SQLException e) {
            throw new TransactionException("Не удалось получить соединение", e);
        }
    }

    private static void closeConnection() {
        Connection conn = context.get();
        context.remove();
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ignored) {
            }
        }
    }
}