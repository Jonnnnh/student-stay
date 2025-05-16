package com.example.studentstay.orm.util;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSourceProvider {

    private static final String URL = System.getenv("JDBC_URL");
    private static final String USER = System.getenv("JDBC_USER");
    private static final String PASSWORD = System.getenv("JDBC_PASSWORD");

    private static DataSource dataSource;

    public static DataSource getDataSource() {
        if (dataSource == null) {
            dataSource = new DataSource() {
                @Override
                public Connection getConnection() throws SQLException {
                    return DriverManager.getConnection(URL, USER, PASSWORD);
                }

                @Override
                public Connection getConnection(String username, String password) throws SQLException {
                    return DriverManager.getConnection(URL, username, password);
                }

                @Override
                public <T> T unwrap(Class<T> iface) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public boolean isWrapperFor(Class<?> iface) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public java.io.PrintWriter getLogWriter() {
                    throw new UnsupportedOperationException();
                }

                @Override
                public void setLogWriter(java.io.PrintWriter o) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public void setLoginTimeout(int t) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public int getLoginTimeout() {
                    throw new UnsupportedOperationException();
                }

                @Override
                public java.util.logging.Logger getParentLogger() {
                    throw new UnsupportedOperationException();
                }
            };
        }
        return dataSource;
    }
}