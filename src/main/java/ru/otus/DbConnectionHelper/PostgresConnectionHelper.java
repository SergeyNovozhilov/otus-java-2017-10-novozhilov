package ru.otus.DbConnectionHelper;

import ru.otus.DbConnectionHelper.DbConnectionHelper;

import java.sql.*;

public class PostgresConnectionHelper implements DbConnectionHelper {

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
