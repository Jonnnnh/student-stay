package com.example.studentstay.orm.util;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionProvider {

    public Connection getConnection() throws SQLException {
        return DataSourceProvider.getDataSource().getConnection();
    }

}