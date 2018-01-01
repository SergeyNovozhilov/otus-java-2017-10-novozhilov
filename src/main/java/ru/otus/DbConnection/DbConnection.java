package ru.otus.DbConnection;

import java.sql.Connection;

public interface DbConnection {
    Connection getConnection();
}
