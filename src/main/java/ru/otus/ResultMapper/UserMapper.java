package ru.otus.ResultMapper;

import org.apache.commons.lang3.StringUtils;
import ru.otus.DataSet.DataSet;
import ru.otus.DataSet.UserDataSet;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements TResultMapper<UserDataSet>{
    private String COLUMN_ID = "id";
    private String COLUMN_NAME = "name";
    private String COLUMN_AGE = "age";

    public UserMapper() {
    }

    @Override
    public UserDataSet map(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(COLUMN_ID);
        String name = resultSet.getString(COLUMN_NAME);
        name = StringUtils.equalsIgnoreCase("null", name) ? null : name;
        int age = resultSet.getInt(COLUMN_AGE);
        return new UserDataSet(id, name, age);
    }
}
