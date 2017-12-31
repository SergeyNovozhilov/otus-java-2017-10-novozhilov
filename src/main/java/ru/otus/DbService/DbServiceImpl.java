package ru.otus.DbService;

import ru.otus.DataSet.DataSet;
import ru.otus.DbConnectionHelper.DbConnectionHelper;
import ru.otus.Executor.Executor;
import ru.otus.ResultMapper.TResultMapper;
import ru.otus.ResultMapper.UserMapper;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DbServiceImpl implements DbService, AutoCloseable {
    private final Connection connection;

    private Map<Class, TResultMapper> mappersMap = new HashMap<>();
    private Map<Class, String> tablesMap = new HashMap<>();

    public DbServiceImpl(Connection connection) {
        this.connection = connection;
    }

    public DbServiceImpl(DbConnectionHelper connectionHelper) {
        this.connection = connectionHelper.getConnection();
    }

    public <T extends DataSet> void save(T object) {
        String tableName = tablesMap.get(object.getClass());
        if (tableName == null) {
            return;
        }
        String request = prepareInsertRequest(object, tableName);
        Executor executor = new Executor(connection);
        try {
            executor.execUpdate(request);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public <T extends DataSet> T load(long id, Class<T> clazz) {
        TResultMapper mapper = mappersMap.get(clazz);
        String tableName = tablesMap.get(clazz);
        if (mapper == null || tableName == null) {
            return null;
        }

            String request = "SELECT * from " + tableName + (" where id = %s");
            Executor executor = new Executor(connection);
            try {
                return (T) executor.execQuery(String.format(request, id), new UserMapper());
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }

    public <T extends DataSet> void register(Class<T> clazz, TResultMapper<T> mapper) {
        try {
            String tableName = getTableName(clazz);
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet tables = dbm.getTables(null, null, tableName, null);
            if (!tables.next()) {
                createTable(clazz);
            }
            tablesMap.put(clazz, tableName);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return;
        }
        mappersMap.put(clazz, mapper);
    }

    public <T extends DataSet> void unregister(Class<T> clazz) {
        tablesMap.remove(clazz);
        mappersMap.remove(clazz);
    }

    public <T extends DataSet> void createTable(Class<T> clazz) {
        String tableName = getTableName(clazz);
        StringBuilder request = new StringBuilder("CREATE TABLE ").append(tableName).append(" ( id bigserial");
        for (Field field : clazz.getDeclaredFields()) {
            boolean access = field.isAccessible();
            try {
                field.setAccessible(true);
                request.append(", ");
                request.append(field.getName().toLowerCase()).append(" ").append(convertToProstgresType(field.getType().getSimpleName()));
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            } finally {
                field.setAccessible(access);
            }
        }
        request.append(")");
        Executor executor = new Executor(connection);
        try {
            executor.execUpdate(request.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        tablesMap.put(clazz, tableName);
    }

    public <T extends DataSet> void dropTable(Class<T> clazz) {
        String tableName = getTableName(clazz);
        if (tableName == null) {
            return;
        }
        String request = "DROP TABLE " + tableName;
        Executor executor = new Executor(connection);
        try {
            executor.execUpdate(request);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String convertToProstgresType(String type) {
        switch (type) {
            case "int" :
                return "int";
            case "String" :
                return "varchar(256)";
            case "long" :
                return "bigint";
            case "boolean" :
                return "boolean";
            case "double" :
                return "double precision";
            default :
                return null;
        }
    }

    private <T extends DataSet> String getTableName(Class<T> clazz) {
        return clazz.getSimpleName().toLowerCase() + "_tbl";
    }


    private <T extends DataSet> String prepareInsertRequest(T object, String tableName) {
        StringBuilder request = new StringBuilder("INSERT INTO ").append(tableName).append(" (");
        StringBuilder values = new StringBuilder(" VALUES (");
        for (Field field : object.getClass().getDeclaredFields()) {
            boolean access = field.isAccessible();
            try {
                field.setAccessible(true);
                Object value = field.get(object);
                request.append(field.getName()).append(",");
                String val = value == null ? null : value.toString();
                values.append("'").append(val).append("',");
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                ex.printStackTrace();
            } finally {
                field.setAccessible(access);
            }
        }
        request.deleteCharAt(request.lastIndexOf(","));
        values.deleteCharAt(values.lastIndexOf(","));
        values.append(")");
        request.append(")").append(values.toString());
        return request.toString();
    }

}
