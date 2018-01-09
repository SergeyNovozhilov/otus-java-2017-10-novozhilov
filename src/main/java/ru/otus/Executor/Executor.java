package ru.otus.Executor;

import ru.otus.DataSet.DataSet;
import ru.otus.ResultMapper.TResultMapper;

import java.sql.*;

public class Executor {
    private final Connection connection;

    public Executor(Connection connection) {
        this.connection = connection;
    }

    public <T extends DataSet> T execQuery(String query, TResultMapper<T> mapper) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(query);
            try (ResultSet result = stmt.getResultSet()) {
                if (result.next()) {
                    return mapper.map(result);
                }
            }
            return null;
        }
    }

    public <T extends DataSet> T execQuery(PreparedStatement query, TResultMapper<T> mapper) throws SQLException {
        try (ResultSet result = query.executeQuery()) {
            if (result.next()) {
                return mapper.map(result);
            }
        }
        return null;
    }

    public int execUpdate(String query) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            return stmt.executeUpdate(query);
        }
    }

}
