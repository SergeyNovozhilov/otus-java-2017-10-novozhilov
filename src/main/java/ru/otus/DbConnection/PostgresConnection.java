package ru.otus.DbConnection;

import java.sql.*;

public class PostgresConnectionHelper implements DbConnection {

    @Override
    public Connection getConnection() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            Connection conn = DriverManager.getConnection
                    ("jdbc:postgresql://localhost:5433/mydb?user=otus");
            return conn;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
