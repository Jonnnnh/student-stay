package com.example.studentstay.connection;


import com.example.studentstay.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionStrategy {
    Connection createConnection(DatabaseConfig config) throws SQLException;
}
