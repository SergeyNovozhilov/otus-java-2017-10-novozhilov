package ru.otus.ResultMapper;

import ru.otus.DataSet.DataSet;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface TResultMapper<T extends DataSet> {
    T map(ResultSet resultSet) throws SQLException;
}
